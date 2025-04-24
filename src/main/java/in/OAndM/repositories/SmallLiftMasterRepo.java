package in.OAndM.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import in.OAndM.Entities.AdminSanctionsEntity;
import in.OAndM.Entities.LiftMasterEntity;
import in.OAndM.Entities.SmallLiftsMaster;
import in.OAndM.core.BaseRepository;


public interface SmallLiftMasterRepo  extends  BaseRepository<SmallLiftsMaster, Integer>{
	
	public List<SmallLiftsMaster> findByUnitIdAndDeleteFlagFalse(Integer unitId);

}
