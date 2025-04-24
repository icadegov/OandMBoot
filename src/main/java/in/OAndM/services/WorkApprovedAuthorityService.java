package in.OAndM.services;


import java.util.List;

import org.springframework.http.HttpStatus;

import in.OAndM.DTO.WorkApprovedAuthorityModel;
import in.OAndM.DTO.WorksTypeMasterModel;
import in.OAndM.core.BaseResponse;



public interface WorkApprovedAuthorityService  {
													
	public BaseResponse<HttpStatus, List<WorkApprovedAuthorityModel>>  getAuthorityList();
}
