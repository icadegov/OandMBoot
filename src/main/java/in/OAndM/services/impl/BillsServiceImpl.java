package in.OAndM.services.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import in.OAndM.DTO.BillsModel;
import in.OAndM.DTO.UploadGOsModel;
import in.OAndM.Entities.BillsEntity;
import in.OAndM.config.AppConstant;
import in.OAndM.core.BaseResponse;
import in.OAndM.core.BaseServiceImpl;
import in.OAndM.repositories.BillsRepo;
import in.OAndM.services.BillsService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class BillsServiceImpl extends BaseServiceImpl<BillsEntity, BillsModel, Integer> implements BillsService{

	@Autowired
	private BillsRepo billsRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	public BaseResponse<HttpStatus, BillsModel> insertBills(BillsModel bills) {
				
		BaseResponse<HttpStatus, BillsModel> responseJson = new BaseResponse<>();
		System.out.println("bills:"+ bills);
		
		if(bills!=null) {
			responseJson=create(bills);
			responseJson.setMessage("Submitted SuccessFully");
		}else {
			responseJson.setMessage("Error in submitting");
		}
		return responseJson;
		
	}

	@Override
	public BaseResponse<HttpStatus, List<BillsModel>> getOMWorkBillsDetailedReport(Integer unitId, Integer approvedById,
			Integer scstFunds, Integer financialYear, Integer hoaId, Integer workTypeId, Integer ProjSubType,
			Integer projectId, Integer type) {
		// TODO Auto-generated method stub
		BaseResponse<HttpStatus, List<BillsModel>> responseJson = new BaseResponse<>();
		List<BillsModel> list = null;
		
		if (financialYear > 0 && approvedById == 0 && unitId>0) {
				if(type==5 || type==6) {//2 = Technical Sanctions, 3= Tender/Agreement Details, 4 = ACtion to be taken, 5=Bills Paid, 6=Bills pending
					list=billsRepo.findbillsEntityByFinancialYearAndUnitIdAndProjectId(financialYear,unitId, type,projectId);
			}
			}
		responseJson.setSuccess(true);
		responseJson.setData(list);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		
		return responseJson;
	}

	@Override
	public BaseResponse<HttpStatus, List<BillsModel>> getOMWorkHoaBillsDetailedReport(Integer unitId,
			Integer approvedById, Integer scstFunds, Integer financialYear, Integer hoaId, Integer workTypeId,
			Integer ProjSubType, Integer projectId, Integer type) {
		// TODO Auto-generated method stub
		BaseResponse<HttpStatus, List<BillsModel>> responseJson = new BaseResponse<>();
		List<BillsModel> list = null;
		
		if(unitId.equals(0)&& !hoaId.equals(0) && !workTypeId.equals(0) ) {
			list = billsRepo.findbillsEntityByFinancialYearAndHoaIdAndWorkTypeId(financialYear, hoaId, workTypeId,type);
	}
		responseJson.setSuccess(true);
		responseJson.setData(list);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		
		return responseJson;
	}

	@Override
	public BaseResponse<HttpStatus, List<BillsModel>> getOMWorkSanctionBillsDetailedReport(Integer unitId,
			Integer approvedById, Integer scstFunds, Integer financialYear, Integer hoaId, Integer workTypeId,
			Integer ProjSubType, Integer projectId, Integer type) {
		// TODO Auto-generated method stub
		BaseResponse<HttpStatus, List<BillsModel>> responseJson = new BaseResponse<>();
		List<BillsModel> list = null;
		if (workTypeId == 0 && approvedById != 0 && hoaId == 0) {
			list = billsRepo.findbillsEntityViewEntityByFinancialYearAndApprovedById(financialYear,approvedById,type);
		}
		else if(workTypeId != 0 && approvedById == 0 && hoaId == 0) {
			list = billsRepo.findbillsEntityViewEntityByFinancialYearAndWorkTypeId(financialYear,workTypeId, type);
		}else if (workTypeId != 0 && approvedById != 0 && hoaId == 0) {
			if (approvedById==1 || approvedById==2) {
				list = billsRepo.findbillsEntityViewEntityByFinancialYearAndWorkTypeIdAndApprovedById(financialYear,workTypeId,approvedById,type);
			}else if(approvedById.equals(9999)){
				list = billsRepo.findbillsEntityViewEntityByFinancialYearAndWorkTypeIdAndApprovedByIdIn(financialYear,workTypeId,Arrays.asList(3, 4, 5, 6),type);
			}
		}
		
		responseJson.setSuccess(true);
		responseJson.setData(list);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		
		return responseJson;
	}
}
