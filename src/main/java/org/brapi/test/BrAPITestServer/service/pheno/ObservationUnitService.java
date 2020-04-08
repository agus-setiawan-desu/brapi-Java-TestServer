package org.brapi.test.BrAPITestServer.service.pheno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.brapi.test.BrAPITestServer.exceptions.BrAPIServerException;
import org.brapi.test.BrAPITestServer.model.entity.core.SeasonEntity;
import org.brapi.test.BrAPITestServer.model.entity.core.StudyEntity;
import org.brapi.test.BrAPITestServer.model.entity.germ.GermplasmEntity;
import org.brapi.test.BrAPITestServer.model.entity.pheno.ObservationEntity;
import org.brapi.test.BrAPITestServer.model.entity.pheno.ObservationUnitEntity;
import org.brapi.test.BrAPITestServer.model.entity.pheno.ObservationVariableEntity;
import org.brapi.test.BrAPITestServer.model.entity.pheno.TreatmentEntity;
import org.brapi.test.BrAPITestServer.repository.core.SeasonRepository;
import org.brapi.test.BrAPITestServer.repository.core.StudyRepository;
import org.brapi.test.BrAPITestServer.repository.germ.GermplasmRepository;
import org.brapi.test.BrAPITestServer.repository.pheno.ObservationRepository;
import org.brapi.test.BrAPITestServer.repository.pheno.ObservationUnitRepository;
import org.brapi.test.BrAPITestServer.repository.pheno.ObservationVariableRepository;
import org.brapi.test.BrAPITestServer.service.DateUtility;
import org.brapi.test.BrAPITestServer.service.PagingUtility;
import org.brapi.test.BrAPITestServer.service.SearchService;
import org.brapi.test.BrAPITestServer.service.core.ContactService;
import org.brapi.test.BrAPITestServer.service.core.LocationService;
import org.brapi.test.BrAPITestServer.service.germ.GermplasmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.swagger.model.Metadata;
import io.swagger.model.core.Season;
import io.swagger.model.core.Study;
import io.swagger.model.core.StudyNewRequest;
import io.swagger.model.core.StudySearchRequest;
import io.swagger.model.pheno.Observation;
import io.swagger.model.pheno.ObservationNewRequest;
import io.swagger.model.pheno.ObservationTable;
import io.swagger.model.pheno.ObservationTable.HeaderRowEnum;
import io.swagger.model.pheno.ObservationTableObservationVariables;
import io.swagger.model.pheno.ObservationUnit;
import io.swagger.model.pheno.ObservationUnitNewRequest;
import io.swagger.model.pheno.ObservationUnitPosition;
import io.swagger.model.pheno.ObservationVariable;


@Service
public class ObservationUnitService {
	private SeasonRepository seasonRepository;
	private StudyRepository studyRepository;
	private ObservationUnitRepository observationUnitRepository;
	private ObservationRepository observationRepository;
	private ObservationVariableRepository observationVariableRepository;
	private GermplasmRepository germplasmRepository;

	private ObservationVariableService observationVariableService;
	private LocationService locationService;
	private SearchService searchService;
	private GermplasmService germplasmService;
	private ContactService contactService;

	@Autowired
	public ObservationUnitService(SeasonRepository seasonRepository, 
			StudyRepository studyRepository, ObservationUnitRepository observationUnitRepository,
			ObservationRepository observationRepository, GermplasmRepository germplasmRepository,
			ObservationVariableService observationVariableService, LocationService locationService,
			ContactService contactService, SearchService searchService, GermplasmService germplasmService,
			ObservationVariableRepository observationVariableRepository) {
		this.seasonRepository = seasonRepository;
		this.studyRepository = studyRepository;
		this.observationUnitRepository = observationUnitRepository;
		this.observationRepository = observationRepository;
		this.germplasmRepository = germplasmRepository;
		this.observationVariableRepository = observationVariableRepository;

		this.observationVariableService = observationVariableService;
		this.locationService = locationService;
		this.contactService = contactService;
		this.searchService = searchService;
		this.germplasmService = germplasmService;
	}

	private List<List<String>> buildDataMatrix(List<ObservationUnitEntity> units, List<ObservationVariable> variables) {
		List<List<String>> data = new ArrayList<>();
		for (ObservationUnitEntity unit : units) {
			List<String> row = new ArrayList<>();
			row.add(unit.getObservations().get(0).getSeason().getYear().toString());
			row.add(unit.getStudy().getId());
			row.add(unit.getStudy().getStudyName());
			row.add(unit.getStudy().getLocation().getId());
			row.add(unit.getStudy().getLocation().getCountryName());
			row.add(unit.getGermplasm().getId());
			row.add(unit.getGermplasm().getGermplasmName());
			row.add(unit.getId());
			row.add(unit.getPlotNumber().toString());
			row.add(unit.getReplicate());
			row.add(unit.getBlockNumber().toString());
			row.add(DateUtility.toTimeString(unit.getObservations().get(0).getObservationTimeStamp()));
			row.add(unit.getEntryType());
			row.add(unit.getX());
			row.add(unit.getY());

			for (ObservationVariable var : variables) {
				Optional<ObservationEntity> obsOption = unit.getObservations().stream().filter((obs) -> {
					return obs.getObservationVariable().getId() == var.getObservationVariableDbId();
				}).findFirst();
				if (obsOption.isPresent()) {
					row.add(obsOption.get().getValue());
				} else {
					row.add("");
				}
			}
			data.add(row);
		}
		return data;
	}

	private Map<String, String> buildEntryMap(List<ObservationUnitEntity> observationUnitsPage) {
		Map<String, String> entryMap = new HashMap<>();
		for (ObservationUnitEntity obs : observationUnitsPage) {
			if (obs.getGermplasm() != null)
				entryMap.put(obs.getGermplasm().getId(), obs.getEntryNumber());
		}
		return entryMap;
	}

	private List<HeaderRowEnum> buildHeaderRow() {
		return Arrays.asList(HeaderRowEnum.values());
	}

	private Observation convertFromEntity(ObservationEntity entity) {
		Observation unit = new Observation();
		unit.setGermplasmDbId(entity.getObservationUnit().getGermplasm().getId());
		unit.setGermplasmName(entity.getObservationUnit().getGermplasm().getGermplasmName());
		unit.setObservationDbId(entity.getId());
//		unit.setObservationLevel(entity.getObservationUnit().getObservationLevel());
		unit.setObservationTimeStamp(DateUtility.toOffsetDateTime(entity.getObservationTimeStamp()));
		unit.setObservationUnitDbId(entity.getObservationUnit().getId());
		unit.setObservationUnitName(entity.getObservationUnit().getObservationUnitName());
		if (entity.getObservationVariable() != null) {
			unit.setObservationVariableDbId(entity.getObservationVariable().getId());
			unit.setObservationVariableName(entity.getObservationVariable().getName());
		}
//		unit.setOperator(entity.getObservationUnit().getOperator());
		unit.setStudyDbId(entity.getObservationUnit().getStudy().getId());
//		unit.setSeason(convertFromEntity(entity.getSeason()));
		unit.setUploadedBy(entity.getObservationUnit().getUploadedBy());
		unit.setValue(entity.getValue());

		return unit;
	}

	private ObservationUnit convertFromEntity(ObservationUnitEntity entity) {
		ObservationUnit observation = new ObservationUnit();
//		observation.setBlockNumber(entity.getBlockNumber() == null ? "0" : entity.getBlockNumber().toString());
//		observation.setEntryNumber(entity.getEntryNumber());
//		observation.setEntryType(entity.getEntryType());
		if (entity.getGermplasm() != null) {
			observation.setGermplasmDbId(entity.getGermplasm().getId());
			observation.setGermplasmName(entity.getGermplasm().getGermplasmName());
		}
		if (entity.getStudy() != null) {
			observation.setStudyDbId(entity.getStudy().getId());
			observation.setStudyName(entity.getStudy().getStudyName());
			if (entity.getStudy().getLocation() != null) {
				observation.setLocationDbId(entity.getStudy().getLocation().getId());
				observation.setLocationName(entity.getStudy().getLocation().getLocationName());
			}
			if (entity.getStudy().getTrial() != null) {
				observation.setTrialDbId(entity.getStudy().getTrial().getId());
				observation.setTrialName(entity.getStudy().getTrial().getTrialName());
				if (entity.getStudy().getTrial().getProgram() != null) {
					observation.setProgramDbId(entity.getStudy().getTrial().getProgram().getId());
					observation.setProgramName(entity.getStudy().getTrial().getProgram().getName());
				}
			}
		}
//		observation.setObservationLevel(entity.getObservationLevel());
//		observation.setObservationLevels(entity.getObservationLevels());
		observation.setObservationUnitDbId(entity.getId());
		observation.setObservationUnitName(entity.getObservationUnitName());
//		if (entity.getPedigree() != null)
//			observation.setPedigree(entity.getPedigree().getPedigree());
//		observation.setPlantNumber(entity.getPlantNumber() == null ? "0" : entity.getPlantNumber().toString());
//		observation.setPlotNumber(entity.getPlotNumber() == null ? "0" : entity.getPlotNumber().toString());
//		observation.setReplicate(entity.getReplicate());
//		observation.setX(entity.getX());
//		observation.setPositionCoordinateX(entity.getX());
//		observation.setPositionCoordinateXType(PositionCoordinateXTypeEnum.GRID_COL);
//		observation.setY(entity.getY());
//		observation.setPositionCoordinateY(entity.getY());
//		observation.setPositionCoordinateYType(PositionCoordinateYTypeEnum.GRID_ROW);

//		observation.setObservations(
//				entity.getObservations().stream().map(this::convertFromEntityToSummary).collect(Collectors.toList()));

//		observation.setObservationUnitXref(entity.getObservationUnitXref().stream().map(e -> {
//			ObservationUnitXref xref = new ObservationUnitXref();
//			xref.setId(e.getXref());
//			xref.setSource(e.getSource());
//			return xref;
//		}).collect(Collectors.toList()));
//		
//		observation.setTreatments(entity.getTreatments().stream().map(e -> {
//			ObservationTreatment treatment = new ObservationTreatment();
//			treatment.setFactor(e.getFactor());
//			treatment.setModality(e.getModality());
//			return treatment;
//		}).collect(Collectors.toList()));

		return observation;

	}

	private Season convertFromEntity(SeasonEntity entity) {
		Season season = new Season();
		if (entity != null) {
			season.setSeasonName(entity.getSeason());
			season.setSeasonDbId(entity.getId());
			season.setYear(entity.getYear());
		}
		return season;
	}

	private ObservationUnitPosition convertFromEntityToPosition(ObservationUnitEntity entity) {
		ObservationUnitPosition plot = new ObservationUnitPosition();
//		plot.setBlockNumber(entity.getBlockNumber() == null ? null : entity.getBlockNumber().toString());
//		plot.setEntryType(EntryTypeEnum.valueOf(entity.getEntryType()));
		plot.setObservationLevel(entity.getObservationLevel());
//		plot.setObservationUnitDbId(entity.getId());
//		plot.setObservationUnitName(entity.getObservationUnitName());
//		plot.setReplicate(entity.getReplicate());
//		plot.setStudyDbId(entity.getStudy().getId());
//		plot.setX(entity.getX());
		plot.setPositionCoordinateX(entity.getX());
//		plot.setPositionCoordinateXType(io.swagger.model.ObservationUnitPosition.PositionCoordinateXTypeEnum.GRID_COL);
//		plot.setY(entity.getY());
		plot.setPositionCoordinateY(entity.getY());
//		plot.setPositionCoordinateYType(io.swagger.model.ObservationUnitPosition.PositionCoordinateYTypeEnum.GRID_ROW);
//		plot.setAdditionalInfo(new HashMap<>());

		if (entity.getGermplasm() != null) {
//			plot.setGermplasmDbId(entity.getGermplasm().getId());
//			plot.setGermplasmName(entity.getGermplasm().getGermplasmName());
		}

		return plot;
	}

	private Observation convertFromEntityToSummary(ObservationEntity entity) {
		Observation ob = new Observation();
		ob.setCollector(entity.getCollector());
		ob.setObservationDbId(entity.getId());
		ob.setObservationTimeStamp(DateUtility.toOffsetDateTime(entity.getObservationTimeStamp()));
//		ob.setSeason(convertFromEntity(entity.getSeason()));
		ob.setValue(entity.getValue());
		if (entity.getObservationVariable() != null) {
			ob.setObservationVariableDbId(entity.getObservationVariable().getId());
			ob.setObservationVariableName(entity.getObservationVariable().getName());
		}
		return ob;
	}

//	private ObservationEntity convertToEntity(NewObservationsRequestObservations observation) {
//		ObservationEntity obe = observationRepository.findById(observation.getObservationDbId())
//				.orElse(new ObservationEntity());
//		obe.setCollector(observation.getCollector());
//		obe.setObservationTimeStamp(DateUtility.toDate(observation.getObservationTimeStamp()));
//		obe.setValue(observation.getValue());
//		obe.setObservationVariable(
//				observationVariableService.getVariableEntity(observation.getObservationVariableDbId()));
//		return obe;
//	}

	public List<String> getObservationLevels(Metadata metaData) {
		Pageable pageReq = PagingUtility.getPageRequest(metaData);
		Page<String> levelsPage = observationUnitRepository.findObservationLevels(pageReq);
		PagingUtility.calculateMetaData(metaData, levelsPage);
		return levelsPage.getContent();
	}

	public List<Observation> getObservationUnits(String studyDbId, List<String> observationVariableDbIds,
			Metadata metaData) {
		Pageable pageReq = PagingUtility.getPageRequest(metaData);
		Page<ObservationEntity> observationsPage;
		if (observationVariableDbIds == null || observationVariableDbIds.isEmpty()) {
			observationsPage = observationRepository.findAllByObservationUnit_Study_Id(studyDbId, pageReq);
		} else {
			observationsPage = observationRepository.findAllByObservationUnit_Study_IdAndObservationVariable_IdIn(
					studyDbId, observationVariableDbIds, pageReq);
		}
		PagingUtility.calculateMetaData(metaData, observationsPage);
		List<Observation> units = observationsPage.map(this::convertFromEntity).getContent();

		return units;
	}

	public List<ObservationUnit> getStudyObservations(String studyDbId, String observationLevel, Metadata metaData) {
		Pageable pageReq = PagingUtility.getPageRequest(metaData);
		Page<ObservationUnitEntity> unitsPage;
		if (observationLevel == null) {
			unitsPage = observationUnitRepository.findAllByStudy_Id(studyDbId, pageReq);
		} else {
			unitsPage = observationUnitRepository.findAllByStudy_IdAndObservationLevel(studyDbId, observationLevel,
					pageReq);
		}
		PagingUtility.calculateMetaData(metaData, unitsPage);
		List<ObservationUnit> observations = unitsPage.map(this::convertFromEntity).getContent();

		return observations;
	}

	public ObservationTable getStudyObservationUnitTable(String studyDbId, Metadata metadata) {
		ObservationTable tableWrapper = new ObservationTable();

//		tableWrapper.setHeaderRow(buildHeaderRow());
//
//		List<ObservationVariable> variables = observationVariableService.getVariablesForStudy(studyDbId, metadata);
////		tableWrapper.setObservationVariables(
////				variables.stream().map(e -> e.getObservationVariableDbId()).collect(Collectors.toList()));
////		tableWrapper.setObservationVariableNames(variables.stream().map(e -> e.getName()).collect(Collectors.toList()));
//
//		List<ObservationUnitEntity> units = observationUnitRepository.findAllByStudy_Id(studyDbId);
//		tableWrapper.setData(buildDataMatrix(units, variables));

		return tableWrapper;
	}

	public String getStudyObservationUnitTableText(String studyDbId, String sep) {
		ObservationTable table = getStudyObservationUnitTable(studyDbId, new Metadata());
		StringBuilder responseBuilder = new StringBuilder();

		for (HeaderRowEnum header : table.getHeaderRow()) {
			responseBuilder.append("\"" + header.toString() + "\"");
			responseBuilder.append(sep);
		}
		int i = 1;
		for (ObservationTableObservationVariables header : table.getObservationVariables()) {
			responseBuilder.append("\"" + header.getObservationVariableDbId() + "\"");
			if (i < table.getObservationVariables().size()) {
				responseBuilder.append(sep);
			}
			i++;
		}
		responseBuilder.append("\n");

		for (List<String> row : table.getData()) {
			int j = 1;
			for (String item : row) {
				responseBuilder.append("\"" + item + "\"");
				if (j < row.size()) {
					responseBuilder.append(sep);
				}
				j++;
			}
			responseBuilder.append("\n");
		}

		return responseBuilder.toString();
	}

	public List<ObservationUnitPosition> getStudyPlotLayouts(String studyDbId, Metadata metaData) {
		Pageable pageReq = PagingUtility.getPageRequest(metaData);
		Page<ObservationUnitEntity> unitsPage = observationUnitRepository.findAllByStudy_Id(studyDbId, pageReq);
		PagingUtility.calculateMetaData(metaData, unitsPage);
		List<ObservationUnitPosition> plots = unitsPage.map(this::convertFromEntityToPosition).getContent();
		return plots;
	}

//	public List<StudyType> getStudyTypes(String studyTypeDbId, Metadata metaData) {
//		Pageable pageReq = PagingUtility.getPageRequest(metaData);
//		List<StudyType> studyTypes = new ArrayList<>();
//
//		if (studyTypeDbId == null) {
//			Page<StudyTypeEntity> page = studyTypeRepository.findAll(pageReq);
//			PagingUtility.calculateMetaData(metaData, page);
//			studyTypes = page.map(this::convertFromEntity).getContent();
//		} else {
//			Optional<StudyTypeEntity> studyOption = studyTypeRepository.findById(studyTypeDbId);
//			if (studyOption.isPresent()) {
//				metaData.getPagination().setTotalCount(1);
//				PagingUtility.calculateMetaData(metaData);
//				studyTypes.add(convertFromEntity(studyOption.get()));
//			}
//		}
//
//		return studyTypes;
//	}
//
//	private StudyType convertFromEntity(StudyTypeEntity entity) {
//		StudyType studyType = new StudyType();
//		studyType.setDescription(entity.getDescription());
//		studyType.setName(entity.getName());
//		studyType.setStudyTypeDbId(entity.getId());
//		studyType.setStudyTypeName(entity.getName());
//		return studyType;
//	}

	public Observation saveObservations(String studyDbId, @Valid List<ObservationNewRequest> newObservations) {
		Observation newObsIds = new Observation();
		Optional<StudyEntity> studyOpt = studyRepository.findById(studyDbId);
		if (studyOpt.isPresent()) {
			for (ObservationNewRequest newObs : newObservations) {
				Optional<ObservationUnitEntity> unitOpt = observationUnitRepository
						.findById(newObs.getObservationUnitDbId());
				if (unitOpt.isPresent()) {
					ObservationEntity newObsEntity = null;

					List<ObservationEntity> obsEntities = unitOpt.get().getObservations();
//					for (ObservationEntity obsEntity : obsEntities) {
//						if (obsEntity.getId().equals(newObs.getObservationDbId())) {
//							newObsEntity = obsEntity;
//							break;
//						}
//					}
					if (newObsEntity == null) {
						newObsEntity = new ObservationEntity();
						newObsEntity.setObservationUnit(unitOpt.get());
					}

					updateEntity(newObsEntity, newObs);
					ObservationEntity savedObsEntity = observationRepository.save(newObsEntity);

//					Observation newId = new Observation()
//							.observationDbId(savedObsEntity.getId())
//							.observationUnitDbId(savedObsEntity.getObservationUnit().getId());
//					if (savedObsEntity.getObservationVariable() != null)
//						newId.setObservationVariableDbId(savedObsEntity.getObservationVariable().getId());

//					newObsIds.addObservationsItem(newId);
				}
			}
		}

		return newObsIds;
	}

	public ObservationUnit saveObservationUnit(@Valid List<ObservationUnitNewRequest> request, String studyDbId)
			throws BrAPIServerException {

		Optional<StudyEntity> studyEntityOpt = studyRepository.findById(studyDbId);
		if (!studyEntityOpt.isPresent()) {
			throw new BrAPIServerException(HttpStatus.NOT_FOUND, "No study found for the given studyDbId");
		}

		ObservationUnit response = new ObservationUnit();
		for (ObservationUnitNewRequest unit : request) {
			ObservationUnitEntity unitEntity;
			Optional<ObservationUnitEntity> unitEntityOpt = observationUnitRepository.findById("");
//					.findById(unit.getObservationUnitDbId() == null ? "" : unit.getObservationUnitDbId());
			if (unitEntityOpt.isPresent()) {
				unitEntity = updateEntity(unitEntityOpt.get(), unit);
			} else {
				unitEntity = updateEntity(new ObservationUnitEntity(), unit);
			}

			unitEntity.setStudy(studyEntityOpt.get());

			final ObservationUnitEntity newUnitEntity = observationUnitRepository.save(unitEntity);

//			if (unit.getObservationUnitXref() == null) {
//				newUnitEntity.setObservationUnitXref(null);
//			} else {
//				newUnitEntity.setObservationUnitXref(unit.getObservationUnitXref().stream().map((xref) -> {
//					ObservationUnitXrefEntity entity = new ObservationUnitXrefEntity();
//					entity.setObservationUnitDbId(newUnitEntity.getId());
//					entity.setXref(xref.getId());
//					entity.setSource(xref.getSource());
//					return entity;
//				}).collect(Collectors.toList()));
//			}

			if (unit.getTreatments() == null) {
				newUnitEntity.setTreatments(null);
			} else {
				newUnitEntity.setTreatments(unit.getTreatments().stream().map((treatment) -> {
					TreatmentEntity entity = new TreatmentEntity();
					entity.setFactor(treatment.getFactor());
					entity.setModality(treatment.getModality());
					entity.setObservationUnitDbId(newUnitEntity.getId());
					return entity;
				}).collect(Collectors.toList()));
			}

			ObservationUnitEntity newNewUnitEntity = observationUnitRepository.save(newUnitEntity);

//			if (unit.getObservations() != null) {
//				for (Observation observation : unit.getObservations()) {
//					ObservationEntity observationEntity = convertToEntity(observation);
//					observationEntity.setObservationUnit(newNewUnitEntity);
//					observationRepository.save(observationEntity);
//				}
//			}
//
//			response.addObservationUnitDbIdsItem(newNewUnitEntity.getId());
		}
		return response;
	}

//	public List<String> saveObservationUnits(String studyDbId, NewObservationsRequestWrapperDeprecated requestDep)
//			throws BrAPIServerException {
//		List<String> newUnitDbIds = new ArrayList<>();
//		Optional<StudyEntity> studyOpt = studyRepository.findById(studyDbId);
//		if (studyOpt.isPresent()) {
//			List<NewObservationUnitRequest> requests = new ArrayList<>();
//			for (NewObservationsRequestDeprecatedData unit : requestDep.getResult().getData()) {
//				NewObservationUnitRequest request = new NewObservationUnitRequest();
//				request.setStudyDbId(studyOpt.get().getId());
//				request.setObservationUnitDbId(unit.getObservatioUnitDbId());
//				request.setObservations(unit.getObservations().stream().map((oDep) -> {
//					Observation o = new Observation();
//					o.setObservationDbId(oDep.getObservationDbId());
//					o.setObservationTimeStamp(oDep.getObservationTimeStamp());
//					o.setObservationUnitDbId(unit.getObservatioUnitDbId());
//					o.setObservationVariableDbId(oDep.getObservationVariableDbId());
//					o.setValue(oDep.getValue());
//					return o;
//				}).collect(Collectors.toList()));
//
//				requests.add(request);
//
//				newUnitDbIds.addAll(saveObservationUnit(requests, studyDbId).getObservationUnitDbIds());
//			}
//		}
//		return newUnitDbIds;
//	}

//	public NewObservationDbIds saveStudyObservationUnitsTable(String studyDbId,
//			@Valid NewObservationsTableRequest request) {
//		Optional<StudyEntity> studyOpt = studyRepository.findById(studyDbId);
//		Set<NewObservationDbIdsObservations> observationDbIds = new HashSet<>();
//		if (studyOpt.isPresent()) {
//			for (List<String> row : request.getData()) {
//				ObservationUnitEntity entity = buildEntityFromRow(row, request, studyDbId);
//				if (entity.getStudy() == null) {
//					entity.setStudy(studyOpt.get());
//				}
//				ObservationUnitEntity savedEntity = observationUnitRepository.save(entity);
//				observationDbIds.addAll(savedEntity.getObservations().stream().map(e -> {
//					return new NewObservationDbIdsObservations().observationDbId(e.getId())
//							.observationUnitDbId(e.getObservationUnit().getId())
//							.observationVariableDbId(e.getObservationVariable().getId());
//				}).collect(Collectors.toSet()));
//			}
//		}
//		NewObservationDbIds response = new NewObservationDbIds();
//		response.setObservations(new ArrayList<>());
//		response.getObservations().addAll(observationDbIds);
//		return response;
//	}

	private ObservationUnitEntity buildEntityFromRow(List<String> row, @Valid ObservationTable request,
			String studyDbId) {
		ObservationUnitEntity entity = new ObservationUnitEntity();
		Date observationTimeStamp = new Date();
		for (int i = 0; i < row.size(); i++) {
			int varIndex = i - request.getHeaderRow().size();
			if (varIndex < 0) {
				HeaderRowEnum header = request.getHeaderRow().get(i);
				if (header != null) {
					switch (header) {
//					case BLOCKNUMBER:
//						entity.setBlockNumber(Integer.parseInt(row.get(i)));
//						break;
//					case ENTRYTYPE:
//						entity.setEntryType(row.get(i));
//						break;
					case GERMPLASMDBID:
					case GERMPLASMNAME:
						Optional<GermplasmEntity> germOpt = germplasmRepository.findById(row.get(i));
						if (germOpt.isPresent())
							entity.setGermplasm(germOpt.get());
						break;
					case OBSERVATIONTIMESTAMP:
						observationTimeStamp = DateUtility.toDate(row.get(i));
						break;
					case OBSERVATIONUNITDBID:
						Optional<ObservationUnitEntity> unitOpt = observationUnitRepository.findById(row.get(i));
						if (unitOpt.isPresent()) {
							entity.setId(unitOpt.get().getId());
							entity.setObservations(unitOpt.get().getObservations());
						}
						break;
//					case PLOTNUMBER:
//						entity.setPlotNumber(Integer.parseInt(row.get(i)));
//						break;
//					case REPLICATE:
//						entity.setReplicate(row.get(i));
//						break;
					case STUDYDBID:
					case STUDYNAME:
						Optional<StudyEntity> studyOpt = studyRepository.findById(row.get(i));
						if (studyOpt.isPresent())
							entity.setStudy(studyOpt.get());
						break;
//					case X:
//						entity.setX(row.get(i));
//						break;
//					case Y:
//						entity.setY(row.get(i));
//						break;
					case YEAR:
//					case LOCATIONDBID:
//					case LOCATIONNAME:
					default:
						break;
					}
				}
			} else {
				Optional<ObservationVariableEntity> varOpt = observationVariableRepository
						.findById(request.getObservationVariables().get(varIndex).getObservationVariableDbId());
				if (varOpt.isPresent()) {
					ObservationEntity observation = new ObservationEntity();
					observation.setObservationTimeStamp(observationTimeStamp);
					observation.setObservationUnit(entity);
					observation.setObservationVariable(varOpt.get());
					observation.setValue(row.get(i));
					if (entity.getObservations() == null)
						entity.setObservations(new ArrayList<>());
					entity.getObservations().add(observation);
				}
			}
		}
		return entity;
	}

//	public List<ObservationUnitPosition> saveStudyPlotLayout(String studyDbId,
//			@Valid StudyLayoutRequest studyLayoutRequest) {
//		List<ObservationUnitPosition> positions = new ArrayList<>();
//		Optional<StudyEntity> studyOpt = studyRepository.findById(studyDbId);
//		if (studyOpt.isPresent()) {
//			for (StudyLayoutRequestLayout layout : studyLayoutRequest.getLayout()) {
//				Optional<ObservationUnitEntity> unitOpt = observationUnitRepository
//						.findById(layout.getObservationUnitDbId());
//				if (unitOpt.isPresent()) {
//					ObservationUnitEntity unit = unitOpt.get();
//					unit.setBlockNumber(layout.getBlockNumber());
//					unit.setX(layout.getX());
//					unit.setX(layout.getPositionCoordinateX());
//					unit.setY(layout.getY());
//					unit.setY(layout.getPositionCoordinateY());
//					unit.setEntryType(layout.getEntryType().name());
//					unit.setReplicate(layout.getReplicate().toString());
//
//					ObservationUnitEntity newUnit = observationUnitRepository.save(unit);
//					positions.add(convertFromEntityToPosition(newUnit));
//				}
//			}
//		}
//		return positions;
//	}

	private void updateEntity(ObservationEntity entity, ObservationNewRequest obs) {
//		entity.setCollector(obs.getCollector());
//		entity.setObservationTimeStamp(DateUtility.toDate(obs.getObservationTimeStamp()));
//		entity.setValue(obs.getValue());
//
//		if (entity.getObservationVariable() == null
//				|| entity.getObservationVariable().getId() != obs.getObservationVariableDbId()) {
//			ObservationVariableEntity observationVariable = observationVariableService
//					.getVariableEntity(obs.getObservationVariableDbId());
//			entity.setObservationVariable(observationVariable);
//		}
//
//		entity.setSeason(seasonRepository.findById("1").get());

	}

	private ObservationUnitEntity updateEntity(ObservationUnitEntity observationUnitEntity,
			ObservationUnitNewRequest unit) {
//		observationUnitEntity.setBlockNumber(NumberUtils.toInt(unit.getBlockNumber()));
//		observationUnitEntity.setEntryNumber(unit.getEntryNumber());
//		observationUnitEntity.setEntryType(unit.getEntryType());
//		observationUnitEntity.setObservationLevel(unit.getObservationLevel());
		observationUnitEntity.setObservationUnitName(unit.getObservationUnitName());
//		observationUnitEntity.setPlantNumber(NumberUtils.toInt(unit.getPlantNumber()));
//		observationUnitEntity.setPlotNumber(NumberUtils.toInt(unit.getPlotNumber()));
//		observationUnitEntity.setReplicate(unit.getReplicate());
//		observationUnitEntity.setX(unit.getX());
//		observationUnitEntity.setY(unit.getY());

		if (unit.getGermplasmDbId() == null) {
			observationUnitEntity.setGermplasm(null);
		} else {
			Optional<GermplasmEntity> germOpt = germplasmRepository.findById(unit.getGermplasmDbId());
			if (germOpt.isPresent()) {
				observationUnitEntity.setGermplasm(germOpt.get());
			}
		}
		if (unit.getStudyDbId() == null) {
			observationUnitEntity.setStudy(null);
		} else {
			Optional<StudyEntity> studyOpt = studyRepository.findById(unit.getStudyDbId());
			if (studyOpt.isPresent()) {
				observationUnitEntity.setStudy(studyOpt.get());
			}
		}

		return observationUnitEntity;
	}

	public List<Study> findStudies(@Valid String commonCropName, @Valid String studyType, @Valid String programDbId,
			@Valid String locationDbId, @Valid String seasonDbId, @Valid String trialDbId, @Valid String studyDbId,
			@Valid String studyName, @Valid String studyCode, @Valid String studyPUI, @Valid String germplasmDbId,
			@Valid String observationVariableDbId, @Valid String externalReferenceID,
			@Valid String externalReferenceSource, @Valid Boolean active, @Valid String sortBy, @Valid String sortOrder,
			Metadata metadata) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Study> saveStudies(@Valid List<StudyNewRequest> body) {
		// TODO Auto-generated method stub
		return null;
	}

	public Study updateStudy(String studyDbId, @Valid StudyNewRequest body) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Study> findStudies(@Valid StudySearchRequest body, Metadata metadata) {
		// TODO Auto-generated method stub
		return null;
	}

	public ObservationUnitEntity getObservationUnitEntity(String observationUnitDbId) {
		// TODO Auto-generated method stub
		return null;
	}
}
