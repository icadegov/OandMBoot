package in.OAndM.services;

import java.util.List;


import org.springframework.http.HttpStatus;


import in.OAndM.DTO.AdminSanctionsModel;
import in.OAndM.DTO.LiftsMasterModel;
import in.OAndM.DTO.SmallLiftsMasterModel;
import in.OAndM.core.BaseResponse;


public interface SmallLiftService  {
													
	public BaseResponse<HttpStatus, List<SmallLiftsMasterModel>> findbyUnitId(Integer unitId);
	
}
