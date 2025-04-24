package in.OAndM.services;

import java.util.List;

import org.springframework.http.HttpStatus;

import in.OAndM.DTO.AgreementsModel;
import in.OAndM.DTO.BillsModel;
import in.OAndM.core.BaseResponse;

public interface AgreementsService {
	
	public BaseResponse<HttpStatus, AgreementsModel>  insertAgreements(AgreementsModel agreements);
	
	public BaseResponse<HttpStatus, List<AgreementsModel>>  getAgreementsByworkId(Integer workId);
	
	public BaseResponse<HttpStatus, AgreementsModel> getAgmtAndBillDetailsByworkId(Integer workId, Integer agreementId);
	

}
