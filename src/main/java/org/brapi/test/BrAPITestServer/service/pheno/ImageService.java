package org.brapi.test.BrAPITestServer.service.pheno;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.brapi.test.BrAPITestServer.exceptions.BrAPIServerException;
import org.brapi.test.BrAPITestServer.model.entity.pheno.ImageEntity;
import org.brapi.test.BrAPITestServer.model.entity.pheno.ObservationEntity;
import org.brapi.test.BrAPITestServer.model.entity.pheno.ObservationUnitEntity;
import org.brapi.test.BrAPITestServer.repository.pheno.ImageRepository;
import org.brapi.test.BrAPITestServer.service.DateUtility;
import org.brapi.test.BrAPITestServer.service.GeoJSONUtility;
import org.brapi.test.BrAPITestServer.service.PagingUtility;
import org.brapi.test.BrAPITestServer.service.SearchQueryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import io.swagger.model.Metadata;
import io.swagger.model.pheno.Image;
import io.swagger.model.pheno.ImageNewRequest;
import io.swagger.model.pheno.ImageSearchRequest;

@Service
public class ImageService {

	private ImageRepository imageRepository;
	private ObservationService observationService;
	private ObservationUnitService observationUnitService;

	public ImageService(ImageRepository imageRepository, ObservationService observationService,
			ObservationUnitService observationUnitService) {
		this.imageRepository = imageRepository;
		this.observationService = observationService;
		this.observationUnitService = observationUnitService;
	}

	public List<Image> findImages(@Valid String imageDbId, @Valid String imageName, @Valid String observationUnitDbId,
			@Valid String observationDbId, @Valid String descriptiveOntologyTerm, @Valid String externalReferenceID,
			@Valid String externalReferenceSource, Metadata metadata) {
		ImageSearchRequest request = new ImageSearchRequest();
		if (imageDbId != null)
			request.addImageDbIdsItem(imageDbId);
		if (imageName != null)
			request.addImageNamesItem(imageName);
		if (observationUnitDbId != null)
			request.addObservationUnitDbIdsItem(observationUnitDbId);
		if (observationDbId != null)
			request.addObservationDbIdsItem(observationDbId);
		if (descriptiveOntologyTerm != null)
			request.addDescriptiveOntologyTermsItem(descriptiveOntologyTerm);
		if (externalReferenceID != null)
			request.addExternalReferenceIDsItem(externalReferenceID);
		if (externalReferenceSource != null)
			request.addExternalReferenceSourcesItem(externalReferenceSource);

		return findImages(request, metadata);
	}

	public List<Image> findImages(@Valid ImageSearchRequest request, Metadata metadata) {
		Pageable pageReq = PagingUtility.getPageRequest(metadata);
		SearchQueryBuilder<ImageEntity> searchQuery = new SearchQueryBuilder<ImageEntity>(ImageEntity.class);
		if (request.getDescriptiveOntologyTerms() != null) {
			searchQuery = searchQuery.join("descriptiveOntologyTerms", "term");
		}
		if (request.getObservationDbIds() != null) {
			searchQuery = searchQuery.join("observations", "observation");
		}
		searchQuery = searchQuery.withExRefs(request.getExternalReferenceIDs(), request.getExternalReferenceSources())
				.appendList(request.getDescriptiveOntologyTerms(), "*term").appendList(request.getImageDbIds(), "id")
				.appendList(request.getImageFileNames(), "imageFileName").appendList(request.getImageNames(), "name")
				.appendList(request.getMimeTypes(), "imageMIMEType")
				.appendList(request.getObservationDbIds(), "*observation.id")
				.appendList(request.getObservationUnitDbIds(), "observationUnit.id")
				.appendNumberRange(request.getImageFileSizeMin(), request.getImageFileSizeMax(), "imageFileSize")
				.appendNumberRange(request.getImageHeightMin(), request.getImageHeightMax(), "imageHeight")
				.appendNumberRange(request.getImageWidthMin(), request.getImageWidthMax(), "imageWidth")
				.appendDateRange(request.getImageTimeStampRangeStart(), request.getImageTimeStampRangeEnd(),
						"timeStamp")
				.appendGeoJSONArea(request.getImageLocation());

		Page<ImageEntity> imagePage = imageRepository.findAllBySearch(searchQuery, pageReq);
		PagingUtility.calculateMetaData(metadata, imagePage);

		List<Image> images = imagePage.map(this::convertFromEntity).getContent();
		return images;
	}

	public Image getImage(String imageDbId) throws BrAPIServerException {
		Image image = null;

		if (imageDbId != null && !imageDbId.isEmpty()) {
			Optional<ImageEntity> imageOption = imageRepository.findById(imageDbId);
			if (imageOption.isPresent()) {
				image = convertFromEntity(imageOption.get());
			} else {
				throw new BrAPIServerException(HttpStatus.NOT_FOUND, "DbId not found: " + imageDbId);
			}
		}

		return image;
	}

	public Image updateImageContent(String imageDbId, String requestURL, byte[] imageData) throws BrAPIServerException {
		Image result = null;
		if (imageDbId != null && !imageDbId.isEmpty()) {
			Optional<ImageEntity> imageOption = imageRepository.findById(imageDbId);
			if (imageOption.isPresent()) {
				ImageEntity newEntity = imageOption.get();
				newEntity.setImageData(imageData);
				newEntity.setImageURL(constructURL(newEntity, requestURL));

				ImageEntity saved = imageRepository.save(newEntity);

				result = convertFromEntity(saved);
			} else {
				throw new BrAPIServerException(HttpStatus.NOT_FOUND, "ImageDbId not found");
			}

		}
		return result;
	}

	public Image updateImage(String imageDbId, @Valid ImageNewRequest body) throws BrAPIServerException {
		ImageEntity savedEntity;
		Optional<ImageEntity> entityOpt = imageRepository.findById(imageDbId);
		if (entityOpt.isPresent()) {
			ImageEntity entity = entityOpt.get();
			updateEntity(entity, body);

			savedEntity = imageRepository.save(entity);
		} else {
			throw new BrAPIServerException(HttpStatus.NOT_FOUND, "DbId not found: " + imageDbId);
		}

		return convertFromEntity(savedEntity);
	}

	public List<Image> saveImages(@Valid List<ImageNewRequest> body) throws BrAPIServerException {
		List<Image> savedImages = new ArrayList<>();
		for (ImageNewRequest request : body) {
			ImageEntity newEntity = new ImageEntity();
			updateEntity(newEntity, request);
			ImageEntity saved = imageRepository.save(newEntity);
			savedImages.add(convertFromEntity(saved));
		}

		return savedImages;
	}

	public byte[] getImageData(String imageDbId) {
		byte[] bytes = null;
		if (imageDbId != null && !imageDbId.isEmpty()) {
			Optional<ImageEntity> imageOption = imageRepository.findById(imageDbId);
			if (imageOption.isPresent()) {
				bytes = imageOption.get().getImageData();
			}
		}
		return bytes;
	}

	private String constructURL(ImageEntity newEntity, String requestURL) {
		String name = "image";
		if (newEntity.getImageFileName() != null && !newEntity.getImageFileName().isEmpty()) {
			name = newEntity.getImageFileName().replaceAll(" ", "_");
		} else {
			if (newEntity.getName() != null && !newEntity.getName().isEmpty()) {
				name = newEntity.getName().replaceAll(" ", "_");
			}
			if (newEntity.getImageMIMEType() != null && !newEntity.getImageMIMEType().isEmpty()) {
				name = name + "." + newEntity.getImageMIMEType();
			}
		}
		return requestURL + "/images/" + newEntity.getId() + "/" + name;
	}

	private void updateEntity(ImageEntity entity, ImageNewRequest image) throws BrAPIServerException {
		if (image.getAdditionalInfo() != null)
			entity.setAdditionalInfo(image.getAdditionalInfo());
		if (image.getCopyright() != null)
			entity.setCopyright(image.getCopyright());
		if (image.getDescription() != null)
			entity.setDescription(image.getDescription());
		if (image.getDescriptiveOntologyTerms() != null)
			entity.setDescriptiveOntologyTerms(image.getDescriptiveOntologyTerms());
		if (image.getExternalReferences() != null)
			entity.setExternalReferences(image.getExternalReferences());
		if (image.getImageFileName() != null)
			entity.setImageFileName(image.getImageFileName());
		if (image.getImageFileSize() != null)
			entity.setImageFileSize(image.getImageFileSize());
		if (image.getImageHeight() != null)
			entity.setImageHeight(image.getImageHeight());
		if (image.getImageLocation() != null)
			entity.setCoordinates(GeoJSONUtility.convertToEntity(image.getImageLocation()));
		if (image.getImageTimeStamp() != null)
			entity.setTimeStamp(DateUtility.toDate(image.getImageTimeStamp()));
		if (image.getImageName() != null)
			entity.setName(image.getImageName());
		if (image.getMimeType() != null)
			entity.setImageMIMEType(image.getMimeType());
		if (image.getImageWidth() != null)
			entity.setImageWidth(image.getImageWidth());
		if (image.getObservationDbIds() != null) {
			entity.setObservations(new ArrayList<>());
			for (String observationDbId : image.getObservationDbIds()) {
				ObservationEntity observation = observationService.getObservationEntity(observationDbId);
				entity.getObservations().add(observation);
			}
		}
		if (image.getObservationUnitDbId() != null) {
			ObservationUnitEntity observationUnit = observationUnitService
					.getObservationUnitEntity(image.getObservationUnitDbId());
			entity.setObservationUnit(observationUnit);
		}
	}

	private Image convertFromEntity(ImageEntity entity) {
		Image img = new Image();
		img.setAdditionalInfo(entity.getAdditionalInfoMap());
		img.setCopyright(entity.getCopyright());
		img.setDescription(entity.getDescription());
		img.setDescriptiveOntologyTerms(entity.getDescriptiveOntologyTerms());
		img.setExternalReferences(entity.getExternalReferencesMap());
		img.setImageDbId(entity.getId());
		img.setImageFileName(entity.getImageFileName());
		img.setImageFileSize(entity.getImageFileSize());
		img.setImageHeight(entity.getImageHeight());
		img.setImageLocation(GeoJSONUtility.convertFromEntity(entity.getCoordinates()));
		img.setImageName(entity.getName());
		img.setImageTimeStamp(DateUtility.toOffsetDateTime(entity.getTimeStamp()));
		img.setImageURL(entity.getImageURL());
		img.setImageWidth(entity.getImageWidth());
		img.setMimeType(entity.getImageMIMEType());
		if (entity.getObservations() != null) {
			img.setObservationDbIds(entity.getObservations().stream().map(o -> {
				return o.getId();
			}).collect(Collectors.toList()));
		}
		if (entity.getObservationUnit() != null)
			img.setObservationUnitDbId(entity.getObservationUnit().getId());

		return img;
	}
}
