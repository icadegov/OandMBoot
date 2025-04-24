package in.OAndM.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import in.OAndM.Entities.AdminSanctionsEntity;
import in.OAndM.Entities.LiftMasterEntity;
import in.OAndM.core.BaseRepository;


public interface LiftMasterRepo  extends  BaseRepository<LiftMasterEntity, Integer>{
	
	public List<LiftMasterEntity> findByProjectIdAndDeleteFlagFalse(Integer projectId);
}
