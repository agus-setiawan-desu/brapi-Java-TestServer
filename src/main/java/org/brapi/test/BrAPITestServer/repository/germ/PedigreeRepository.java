package org.brapi.test.BrAPITestServer.repository.germ;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.brapi.test.BrAPITestServer.model.entity.germ.PedigreeEntity;
import org.brapi.test.BrAPITestServer.repository.core.BrAPIRepository;
import org.springframework.data.repository.query.Param;

public interface PedigreeRepository extends BrAPIRepository<PedigreeEntity, String> {
	public PedigreeEntity findByGermplasm_Id(String germplasmDbId);
	public PedigreeEntity findByGermplasm_IdAndNotation(String germplasmDbId, String notation);
//	public List<PedigreeEntity> findByParent1_IdOrParent2_Id(String germplasmDbId, String germplasmDbId2);
	public List<PedigreeEntity> findByParent1_Germplasm_IdOrParent2_Germplasm_Id(String germplasmDbId, String germplasmDbId2);
	

	@Query("select p from PedigreeEntity p "
			+ "where (p.parent1.id = :parent1 OR p.parent2.id = :parent1) "
			+ "AND  (p.parent1.id = :parent2 OR p.parent2.id = :parent2)")
	public List<PedigreeEntity> findSiblings(@Param("parent1") String parent1, @Param("parent2") String parent2);
}
