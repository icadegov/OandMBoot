package in.OAndM.services;

import java.util.List;


import org.springframework.http.HttpStatus;


import in.OAndM.DTO.SCSTBenefitedModel;
import in.OAndM.core.BaseResponse;


public interface SCSTBenefitedVillagesService  {
												
	public BaseResponse<HttpStatus, List<SCSTBenefitedModel>> insertSCSTBenefitedVillages(List<SCSTBenefitedModel> admin);
	
}
