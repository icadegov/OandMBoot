package in.OAndM.services;

import java.util.List;


import org.springframework.http.HttpStatus;


import in.OAndM.DTO.AdminSanctionsModel;
import in.OAndM.DTO.LiftsMasterModel;
import in.OAndM.core.BaseResponse;


public interface LiftMasterService  {
													

	
	public BaseResponse<HttpStatus, List<LiftsMasterModel>> findbyProjectId(Integer projectId);

	
}
