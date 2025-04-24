package in.OAndM.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import in.OAndM.Entities.UploadGOsEntity;
import in.OAndM.core.BaseRepository;

public interface UploadGOsRepo extends  BaseRepository<UploadGOsEntity, Integer>{
	
	public List<UploadGOsEntity> findByuploadTypeAndDeleteFlagFalse(String type,Sort sort);

}

