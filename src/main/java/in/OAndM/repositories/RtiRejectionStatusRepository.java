package in.OAndM.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import  in.OAndM.core.BaseRepository;
import in.OAndM.Entities.RtiRejectionStatus;

@Repository
public interface RtiRejectionStatusRepository extends BaseRepository<RtiRejectionStatus, Integer> {

	List<RtiRejectionStatus> findAllByDeleteFlagFalse();

	
	
}
