package in.OAndM.services;


import java.util.List;

import org.springframework.http.HttpStatus;

import in.OAndM.DTO.WorksTypeMasterModel;
import in.OAndM.core.BaseResponse;



public interface WorkTypeMasterService  {
													
	public BaseResponse<HttpStatus, List<WorksTypeMasterModel>>  getworktypemaster();
}
