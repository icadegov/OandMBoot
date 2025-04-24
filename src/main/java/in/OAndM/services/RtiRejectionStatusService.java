package in.OAndM.services;

//import com.tgs.ir.dto.RtiRejectionStatus;
import in.OAndM.Entities.RtiRejectionStatus;

import java.util.List;

import org.springframework.http.HttpStatus;

import in.OAndM.core.BaseResponse;
import in.OAndM.core.BaseService;

public interface RtiRejectionStatusService extends BaseService<RtiRejectionStatus, Integer> {
    
	 
	BaseResponse<HttpStatus, List<RtiRejectionStatus>> findAllByDeleteFalgFalse();
}
