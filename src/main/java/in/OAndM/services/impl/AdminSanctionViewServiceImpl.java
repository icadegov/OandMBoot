package in.OAndM.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import in.OAndM.DTO.AdminSanctionViewModel;
import in.OAndM.Entities.AdminSanctionViewEntity;
import in.OAndM.config.AppConstant;
import in.OAndM.core.BaseResponse;
import in.OAndM.core.BaseServiceImpl;
import in.OAndM.repositories.AdminSanctionViewRepo;
import in.OAndM.services.AdminSanctionViewService;
import jakarta.transaction.Transactional;

@Service
@Transactional

public class AdminSanctionViewServiceImpl extends BaseServiceImpl<AdminSanctionViewEntity, AdminSanctionViewModel, Integer> implements AdminSanctionViewService {
	
	@Autowired
	AdminSanctionViewRepo adminRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
	
	@Override
	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>> getWorksByFinancialyearAndOffice(Integer unitId,Integer circleId,Integer divisionId,Integer subDivisionId,Integer finYear,Integer approvedId) {
		// TODO Auto-generated method stub
		
		BaseResponse<HttpStatus, List<AdminSanctionViewModel>> responseJson = new BaseResponse<>();
		
		List<AdminSanctionViewEntity> list = null;
		
		if(unitId > 0 && circleId == 0) {
			list = adminRepo.findByFinancialYearAndUnitIdAndApprovedById(finYear,unitId,approvedId);
		}
		else if (circleId > 0 && divisionId == 0 && subDivisionId == 0) {
			list = adminRepo.findByFinancialYearAndUnitIdAndCircleIdAndApprovedById(finYear,unitId,circleId,approvedId);
		}
		else if (divisionId > 0 && subDivisionId == 0) {
			list = adminRepo.findByFinancialYearAndUnitIdAndCircleIdAndDivisionIdAndApprovedById(finYear,unitId,circleId,divisionId,approvedId);
		}
		else if (subDivisionId > 0) {
			list = adminRepo.findByFinancialYearAndUnitIdAndCircleIdAndDivisionIdAndSubDivisionIdAndApprovedById(finYear,unitId,circleId,divisionId,subDivisionId,approvedId);
		}
		 
		List<AdminSanctionViewModel> adminViewModel=new ArrayList<>();
		if( list.size()>0) {
			for(AdminSanctionViewEntity  work: list) {
				
				AdminSanctionViewModel adminModel=new AdminSanctionViewModel();
				adminModel.setWorkId(work.getWorkId());
				adminModel.setWorkName(work.getWorkName());
				adminModel.setWorkTypeName(work.getWorkTypeName());
				adminModel.setReferenceNumber(work.getReferenceNumber());
				adminModel.setAdminSanctionAmt(work.getAdminSanctionAmt());
				adminModel.setHeadOfAccount(work.getHeadOfAccount());
				adminModel.setApprovedByName(work.getApprovedByName());
				adminModel.setFinancialYear(work.getFinancialYear());
				adminViewModel.add(adminModel);
			}
			}
			
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setSuccess(true);
		responseJson.setData(adminViewModel);

		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		//System.out.println("responseJson" +responseJson);
		return responseJson;
	}

	@Override
	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>> getAbsRepHOAWiseByFinancialyear(Integer financialYear) {
		// TODO Auto-generated method stub
		BaseResponse<HttpStatus, List<AdminSanctionViewModel>> responseJson = new BaseResponse<>();
		
		List<AdminSanctionViewModel> list = null;
		
		if(financialYear > 0) {
			 list=adminRepo.getAbsRepHOAWiseByFinancialyear(financialYear);
		}
		responseJson.setSuccess(true);
		responseJson.setData(list);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		
		return responseJson;
	}

	@Override
	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>> getAbsRepUnitHOAWiseFinyear(Integer financialYear) {
		// TODO Auto-generated method stub
		BaseResponse<HttpStatus, List<AdminSanctionViewModel>> responseJson = new BaseResponse<>();
		
		List<AdminSanctionViewModel> list = null;
		
		if(financialYear > 0) {
			 list=adminRepo.getAbsRepUnitHOAWiseFinyear(financialYear);
		}
		responseJson.setSuccess(true);
		responseJson.setData(list);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		
		return responseJson;
	}

	@Override
	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>> getAbsRepUnitWiseSCSTSdfFinyear(
			Integer financialYear) {
		// TODO Auto-generated method stub
		BaseResponse<HttpStatus, List<AdminSanctionViewModel>> responseJson = new BaseResponse<>();
		
		List<AdminSanctionViewModel> list = null;
		
		if(financialYear > 0) {
			 list=adminRepo.getAbsRepUnitWiseSCSTSdfFinyear(financialYear);
		}
		responseJson.setSuccess(true);
		responseJson.setData(list);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		
		return responseJson;
	}

	@Override
	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>> getOMWorksAADetailedReport(AdminSanctionViewModel admin) {
		// TODO Auto-generated method stub
		BaseResponse<HttpStatus, List<AdminSanctionViewModel>> responseJson = new BaseResponse<>();
		List<AdminSanctionViewEntity> list = null;
		Integer financialYear = admin.getFinancialYear(), type=admin.getType(), projectId=admin.getProjectId(),
				authorityId=admin.getApprovedById(),scst = admin.getScstFunds(),unitId=admin.getUnitId();
		if(financialYear > 0 && type==1) { //Admin Sanctions
			if (projectId == 0) {
				if (authorityId == 0 && scst==0) {
					list=adminRepo.findByFinancialYearAndUnitId(financialYear,unitId);
				}else if (authorityId == 0 && scst!= 0 && scst!=3) {
					list=adminRepo.findByFinancialYearAndUnitIdAndScstFunds(financialYear,unitId,scst);
				}else if (authorityId != 1 && authorityId != 2) {
					 list = adminRepo.findByFinancialYearAndUnitIdAndApprovedByIdIn(financialYear, unitId, Arrays.asList(3, 4, 5, 6));
				} else {
					list = adminRepo.findByFinancialYearAndUnitIdAndApprovedById(financialYear, unitId, authorityId);
				}
			}else {
				if (authorityId == 0) {
					list = adminRepo.findByFinancialYearAndUnitIdAndProjectId(financialYear,unitId,projectId);
							
				} else if (authorityId != 1 && authorityId != 2) {
					list = adminRepo.findByFinancialYearAndUnitIdAndProjectIdAndApprovedByIdIn(financialYear,unitId,projectId,Arrays.asList(3, 4, 5, 6));
					
				} else {
					list = adminRepo.findByFinancialYearAndUnitIdAndProjectIdAndApprovedById(financialYear, unitId, projectId,authorityId);
				}
			}
			}
		
		else if(financialYear > 0 && type!=1) {//2 = Technical Sanctions, 3= Tender/Agreement Details, 4 = ACtion to be taken, 5=Bills Paid, 6=Bills pending
			
				if (authorityId == 0 && projectId==0) {
					list=adminRepo.findAdminSanctionViewEntityByFinancialYearAndUnitId(financialYear,unitId, type);
				}
			
		}
		List<AdminSanctionViewModel> models = mapper.mapEntityToModel(list);
			
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setSuccess(true);
		responseJson.setData(models);

		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		//System.out.println("responseJson" +responseJson);
		return responseJson;

		
	}

	@Override
	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>> getOMWorksHoaAADetailedReport(AdminSanctionViewModel admin) {
		// TODO Auto-generated method stub
		BaseResponse<HttpStatus, List<AdminSanctionViewModel>> responseJson = new BaseResponse<>();
		List<AdminSanctionViewEntity> list = null;
		Integer financialYear = admin.getFinancialYear(), type=admin.getType(), projectId=admin.getProjectId(),projectSubType=admin.getProjSubType(),
				authorityId=admin.getApprovedById(),scst = admin.getScstFunds(),unitId=admin.getUnitId(),hoaId=admin.getHoaId(), workTypeId=admin.getWorkTypeId();
	if(type==1) { //Admin Sancions
		if(!unitId.equals(0)) {
		if(!hoaId.equals(0)) {
			if (!authorityId.equals(0)) {
					list=adminRepo.findByFinancialYearAndUnitIdAndApprovedByIdAndHoaId(financialYear,unitId,authorityId,hoaId);
				} else {
					list = adminRepo.findByFinancialYearAndUnitIdAndHoaId(financialYear, unitId, hoaId);
				}
			}else {
				if (!workTypeId.equals(0) && !authorityId.equals(3)) {
					list = adminRepo.findByFinancialYearAndUnitIdAndApprovedByIdAndWorkTypeIdAndProjSubType(financialYear,unitId,authorityId,workTypeId,projectSubType);
							
				} else if (!workTypeId.equals(0) && authorityId.equals(3)) {
					list = adminRepo.findByFinancialYearAndUnitIdAndWorkTypeIdAndProjSubTypeAndApprovedByIdIn(financialYear,unitId,workTypeId,projectSubType,Arrays.asList(3, 4, 5, 6));
					
				} else if(workTypeId.equals(0) && !authorityId.equals(3)){
					list = adminRepo.findByFinancialYearAndUnitIdAndApprovedById(financialYear, unitId,authorityId);
				}else {
					list = adminRepo.findByFinancialYearAndUnitIdAndApprovedByIdIn(financialYear, unitId,Arrays.asList(3, 4, 5, 6));
				}
			}
		}else {
			if (!hoaId.equals(0)&& authorityId.equals(0) && workTypeId.equals(0) ) {
				list = adminRepo.findByFinancialYearAndHoaId(financialYear, hoaId);
			} else if (!hoaId.equals(0) && !authorityId.equals(0) && workTypeId.equals(0) ){
				list = adminRepo.findByFinancialYearAndHoaIdAndApprovedById(financialYear, hoaId, authorityId);
			}else if(!hoaId.equals(0) && !workTypeId.equals(0) ) {
				list = adminRepo.findByFinancialYearAndHoaIdAndWorkTypeId(financialYear, hoaId, workTypeId);
			}
		}
		
	}
	
	else {//2 = Technical Sanctions, 3= Tender/Agreement Details, 4 = ACtion to be taken, 5=Bills Paid, 6=Bills pending
		if(unitId.equals(0)&& !hoaId.equals(0) && !workTypeId.equals(0) ) {
				list = adminRepo.findAdminSanctionViewEntityByFinancialYearAndHoaIdAndWorkTypeId(financialYear, hoaId, workTypeId,type);
		
		}
	}

	
	List<AdminSanctionViewModel> models = mapper.mapEntityToModel(list);
			
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setSuccess(true);
		responseJson.setData(models);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		//System.out.println("responseJson" +responseJson);
		return responseJson;

	}

	@Override
	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>> getOMWorksSanctionAADetailedReport(AdminSanctionViewModel admin) {
		// TODO Auto-generated method stub
		BaseResponse<HttpStatus, List<AdminSanctionViewModel>> responseJson = new BaseResponse<>();
		List<AdminSanctionViewEntity> list = null;
		Integer financialYear = admin.getFinancialYear(), type=admin.getType(), projectId=admin.getProjectId(),projectSubType=admin.getProjSubType(),
				authorityId=admin.getApprovedById(),scst = admin.getScstFunds(),unitId=admin.getUnitId(),hoaId=admin.getHoaId(), workTypeId=admin.getWorkTypeId();
	if (type==1) {// Admin Sanctions
		
		if (workTypeId == 0 && authorityId != 0 && hoaId == 0) {
			list = adminRepo.findByFinancialYearAndApprovedById(financialYear,authorityId);
		}else if (workTypeId != 0 && authorityId == 0 && hoaId == 0 && projectSubType!=0) {
			list = adminRepo.findByFinancialYearAndProjSubType(financialYear,projectSubType);
		}else if ((workTypeId != 0 && authorityId == 0 && hoaId != 0)) {
			list = adminRepo.findByFinancialYearAndHoaIdAndWorkTypeId(financialYear,hoaId,workTypeId);
		}else if((workTypeId != 0 && authorityId == 0 && hoaId == 0)) {
			list = adminRepo.findByFinancialYearAndWorkTypeId(financialYear,workTypeId);
		}else if (workTypeId != 0 && authorityId != 0 && hoaId == 0) {
			if (authorityId != 9999) {
				list = adminRepo.findByFinancialYearAndApprovedByIdAndWorkTypeId(financialYear,authorityId,workTypeId);
			}else {
				list = adminRepo.findByFinancialYearAndWorkTypeIdAndApprovedByIdIn(financialYear,workTypeId,Arrays.asList(3, 4, 5, 6));
			}
		}
		
	}else {//2 = Technical Sanctions, 3= Tender/Agreement Details, 4 = ACtion to be taken, 5=Bills Paid, 6=Bills pending
			
		if (workTypeId == 0 && authorityId != 0 && hoaId == 0) {
			list = adminRepo.findAdminSanctionViewEntityByFinancialYearAndApprovedById(financialYear,authorityId,type);
		}
		else if(workTypeId != 0 && authorityId == 0 && hoaId == 0) {
			list = adminRepo.findAdminSanctionViewEntityByFinancialYearAndWorkTypeId(financialYear,workTypeId, type);
		}else if (workTypeId != 0 && authorityId != 0 && hoaId == 0) {
			if (authorityId != 9999) {
			list = adminRepo.findAdminSanctionViewEntityByFinancialYearAndWorkTypeIdAndApprovedById(financialYear,workTypeId,authorityId,type);
			}else {
				list = adminRepo.findAdminSanctionViewEntityByFinancialYearAndWorkTypeIdAndApprovedByIdIn(financialYear,workTypeId,Arrays.asList(3, 4, 5, 6),type);
			}
		}
		
	}
		
		List<AdminSanctionViewModel> models = mapper.mapEntityToModel(list);
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setSuccess(true);
		responseJson.setData(models);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		//System.out.println("responseJson" +responseJson);
		return responseJson;
	}

	@Override
	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>> getYearWiseReport(Integer unitId, Integer circleId,
			Integer divisionId, Integer subDivisionId) {
		// TODO Auto-generated method stub
			
		BaseResponse<HttpStatus, List<AdminSanctionViewModel>> responseJson = new BaseResponse<>();
		
		List<AdminSanctionViewModel> list = null;
		
		list=adminRepo.findByFinancialYear();
		
		
		responseJson.setSuccess(true);
		responseJson.setData(list);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		//System.out.println("responseJson" +responseJson);
		return responseJson;
	}

	

}
