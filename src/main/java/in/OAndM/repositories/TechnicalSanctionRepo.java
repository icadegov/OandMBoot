package in.OAndM.repositories;

import java.util.List;

import in.OAndM.Entities.TechnicalSanctionEntity;
import in.OAndM.core.BaseRepository;


public interface TechnicalSanctionRepo   extends  BaseRepository<TechnicalSanctionEntity, Integer>{
	
//	public List<TechnicalSanctionEntity> findByworkIdAndIsLatestTrueAndDeleteFlagFalse(Integer workId);
	
	public List<TechnicalSanctionEntity> findByAdminSanctionsWorkIdAndIsLatestTrueAndDeleteFlagFalse(Integer workId);

}
