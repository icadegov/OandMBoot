package in.OAndM.services;

import java.util.List;

import org.springframework.http.HttpStatus;

import in.OAndM.DTO.TechnicalSanctionsModel;
import in.OAndM.core.BaseResponse;

public interface TechnicalSanctionService {
	
	



public  BaseResponse<HttpStatus, List<TechnicalSanctionsModel>> insertTechnicalSanctions(List<TechnicalSanctionsModel> list);

public BaseResponse<HttpStatus, List<TechnicalSanctionsModel>>  getTechnicalSanctionByWorkId(Integer workId);

}
