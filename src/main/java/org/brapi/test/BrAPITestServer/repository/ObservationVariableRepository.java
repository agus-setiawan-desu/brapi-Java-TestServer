package org.brapi.test.BrAPITestServer.repository;

import java.util.List;

import org.brapi.test.BrAPITestServer.model.entity.ObservationVariableEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ObservationVariableRepository extends PagingAndSortingRepository<ObservationVariableEntity, String>{
	@Query("select distinct o.scale.dataType from ObservationVariableEntity o")
	public Page<String> findDistinctScale_DatatypeAll(Pageable pageReq);
	
	public Page<ObservationVariableEntity> findAllByTrait_TraitClass(String traitClass, Pageable pageReq);

	@Query("select o from ObservationVariableEntity o "
			+ "where ('' in :datatypes or o.scale.dataType in :datatypes) "
			+ "and ('' in :methodDbIds or o.method.id in :methodDbIds) "
			+ "and ('' in :names or o.name in :names) "
			+ "and ('' in :observationVariableDbIds or o.id in :observationVariableDbIds) "
			+ "and ('' in :ontologyDbIds or o.ontology.id in :ontologyDbIds) "
			+ "and ('' in :ontologyXrefs or o.ontology.xref in :ontologyXrefs) "
			+ "and ('' in :scaleDbIds or o.scale.id in :scaleDbIds) "
			+ "and ('' in :traitClasses or o.trait.traitClass in :traitClasses) ")
	public Page<ObservationVariableEntity> findAllBySearch(
			@Param("datatypes") List<String> datatypes, 
			@Param("methodDbIds") List<String> methodDbIds,
			@Param("names") List<String> names, 
			@Param("observationVariableDbIds") List<String> observationVariableDbIds, 
			@Param("ontologyDbIds") List<String> ontologyDbIds,
			@Param("ontologyXrefs") List<String> ontologyXrefs, 
			@Param("scaleDbIds") List<String> scaleDbIds, 
			@Param("traitClasses") List<String> traitClasses, Pageable pageReq);
	
	@Query("select o.observationVariable from ObservationEntity o "
			+ "where o.observationUnit.study.id = :studyDbId")
	public List<ObservationVariableEntity> findAllForStudy(@Param("studyDbId") String studyDbId);
}
