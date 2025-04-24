package in.OAndM.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import in.OAndM.DTO.AgreementsModel;
import in.OAndM.DTO.BillsModel;
import in.OAndM.Entities.AgreementsEntity;
import in.OAndM.Entities.BillsEntity;
import in.OAndM.config.AppConstant;
import in.OAndM.core.BaseResponse;
import in.OAndM.core.BaseServiceImpl;
import in.OAndM.repositories.AgreementsRepo;
import in.OAndM.services.AgreementsService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AgreementsServiceImpl  extends BaseServiceImpl<AgreementsEntity, AgreementsModel, Integer> implements AgreementsService{
	
	@Autowired
	private AgreementsRepo agreementsRepo;

	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	public BaseResponse<HttpStatus, AgreementsModel> insertAgreements(AgreementsModel agreements) {
		
		BaseResponse<HttpStatus, AgreementsModel> responseJson = new BaseResponse<>();
		System.out.println("agreements:"+ agreements);
		
		if(agreements!=null) {
			responseJson=create(agreements);
			responseJson.setMessage("Submitted SuccessFully");
		}else {
			responseJson.setMessage("Error in submitting");
		}
		return responseJson;
		
	}

	public BaseResponse<HttpStatus, List<AgreementsModel>>  getAgreementsByworkId(Integer workId){
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_STARTED));
		BaseResponse<HttpStatus, List<AgreementsModel>> responseJson = new BaseResponse<>();
		List<AgreementsEntity> entities = agreementsRepo.findByworkIdAndIsLatestTrueAndDeleteFlagFalse(workId);
		List<AgreementsModel> techmodels =new ArrayList<>();
		for(AgreementsEntity  admin: entities) {
			AgreementsModel model=new AgreementsModel();
			model.setAgreementId(admin.getAgreementId());
			model.setAgreementNumber(admin.getAgreementNumber());
			
			techmodels.add(model);
		}
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setSuccess(true);
		responseJson.setData(techmodels);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		return responseJson;
		
	}



	public BaseResponse<HttpStatus, AgreementsModel> getAgmtAndBillDetailsByworkId(Integer workId, Integer agreementId){
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_STARTED));
		BaseResponse<HttpStatus, AgreementsModel> responseJson = new BaseResponse<>();
		AgreementsEntity entities = agreementsRepo.findByworkIdAndAgreementIdAndIsLatestAndDeleteFlagAndBillsIsLatestAndBillsDeleteFlag(workId,agreementId,true,false,true,false) ;
		if(entities ==null ) {
			entities = agreementsRepo.findByworkIdAndAgreementIdAndIsLatestAndDeleteFlag(workId,agreementId,true,false) ;
		}
		
		AgreementsModel model=new AgreementsModel();
			model.setWorkId(entities.getWorkId());
			model.setAgreementAmount(entities.getAgreementAmount());
			model.setAgreementDate(entities.getAgreementDate());
			model.setAgreementNumber(entities.getAgreementNumber());
			
			List<BillsModel> billEntryModel=new ArrayList<>();
			
			if( entities.getBills().size()>0) {
			for(BillsEntity  bills: entities.getBills()) {
				
				BillsModel billsModel=new BillsModel();
				billsModel.setBillId(bills.getBillId());
				billsModel.setBillNo(bills.getBillNo());
				billsModel.setBillType(bills.getBillType());
				billsModel.setWorkDoneAmount(bills.getWorkDoneAmount());
				billsModel.setCumWorkDoneAmount(bills.getCumWorkDoneAmount());
				billsModel.setStatusId(bills.getStatusId());
				billEntryModel.add(billsModel);
			}
			}
			model.setBillsList(billEntryModel);
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setSuccess(true);
		responseJson.setData(model);

		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		
		return responseJson;
		
	}

}
