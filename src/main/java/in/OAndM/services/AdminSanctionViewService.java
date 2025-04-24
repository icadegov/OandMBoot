package in.OAndM.services;

import java.util.List;

import org.springframework.http.HttpStatus;

import in.OAndM.DTO.AdminSanctionViewModel;
import in.OAndM.core.BaseResponse;

public interface AdminSanctionViewService {

	

	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>>  getWorksByFinancialyearAndOffice(Integer unitId,Integer circleId,Integer divisionId,Integer subDivisionId,Integer finYear,Integer approvedId);
	
	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>> getAbsRepHOAWiseByFinancialyear(Integer financialYear);
	
	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>> getAbsRepUnitHOAWiseFinyear(Integer financialYear);
	
	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>> getAbsRepUnitWiseSCSTSdfFinyear(Integer financialYear);
	
	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>> getOMWorksAADetailedReport(AdminSanctionViewModel admin);
	
	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>> getOMWorksHoaAADetailedReport(AdminSanctionViewModel admin);
	
	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>> getOMWorksSanctionAADetailedReport(AdminSanctionViewModel admin);
	
	public BaseResponse<HttpStatus, List<AdminSanctionViewModel>> getYearWiseReport (Integer unitId,Integer circleId,Integer divisionId,Integer subDivisionId);
	
	
}
