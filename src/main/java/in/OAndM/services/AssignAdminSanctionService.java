package in.OAndM.services;

import java.util.List;


import org.springframework.http.HttpStatus;

import in.OAndM.DTO.AdminAssignWorksModel;
import in.OAndM.DTO.AdminSanctionsModel;

import in.OAndM.core.BaseResponse;


public interface AssignAdminSanctionService  {
													

	
	public BaseResponse<HttpStatus, AdminAssignWorksModel> insertAssignAdminSanctions(AdminAssignWorksModel admin);

	
}
