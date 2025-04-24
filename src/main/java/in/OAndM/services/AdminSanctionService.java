package in.OAndM.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;


import in.OAndM.DTO.AdminSanctionsModel;

import in.OAndM.core.BaseResponse;


public interface AdminSanctionService  {
													
	public BaseResponse<HttpStatus, List<AdminSanctionsModel>>  getAdminSanctionByFinYearByUnit(Integer unit,Integer year);
	
	public BaseResponse<HttpStatus, AdminSanctionsModel> findbyWorkId(Integer workId);
	
	public BaseResponse<HttpStatus, AdminSanctionsModel> findByunitIdAndFinancialYearAndIsLatestAndDeleteFlagAndTechnEntriesIsLatestAndTechnEntriesTsId(Integer unit,Integer finyear,Boolean isLatest,Boolean deleteFlag,Boolean tech_is_latest,Integer tsId);

	public BaseResponse<HttpStatus, List<AdminSanctionsModel>>  getAdminSanctionForDEE(Integer unit,Integer circle,Integer divisionId,Integer subDivisionId,Integer year);
	
	public BaseResponse<HttpStatus, List<AdminSanctionsModel>>  getAdminSanctions(Integer unit,Integer year);
	
	public BaseResponse<HttpStatus, List<AdminSanctionsModel>>  getUnAssignedAdminSanctions(Integer unit,Integer circle,Integer division,Integer subDivision);
	
	public BaseResponse<HttpStatus, AdminSanctionsModel> insertAdminSanctions(AdminSanctionsModel admin);

	public BaseResponse<HttpStatus , List<AdminSanctionsModel>> getAdminSanctionByUserByAuthorityByFinyear(Integer unit,Integer circle,Integer division,Integer subdivision,Integer approvedId,Integer finyear);
	
	public BaseResponse<HttpStatus, AdminSanctionsModel> getcircle(Integer unitId);
}
