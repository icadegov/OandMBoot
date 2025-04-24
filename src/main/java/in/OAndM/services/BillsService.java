package in.OAndM.services;

import java.util.List;

import org.springframework.http.HttpStatus;

import in.OAndM.DTO.BillsModel;
import in.OAndM.DTO.UploadGOsModel;
import in.OAndM.core.BaseResponse;

public interface BillsService {

	public BaseResponse<HttpStatus, BillsModel> insertBills(BillsModel bills) ;
	
	public BaseResponse<HttpStatus, List<BillsModel>> getOMWorkBillsDetailedReport(Integer unitId,Integer approvedById,Integer scstFunds,Integer financialYear,Integer hoaId,Integer workTypeId,Integer ProjSubType,Integer projectId,Integer type);
	
	public BaseResponse<HttpStatus, List<BillsModel>> getOMWorkHoaBillsDetailedReport(Integer unitId,Integer approvedById,Integer scstFunds,Integer financialYear,Integer hoaId,Integer workTypeId,Integer ProjSubType,Integer projectId,Integer type);
	
	public BaseResponse<HttpStatus, List<BillsModel>> getOMWorkSanctionBillsDetailedReport(Integer unitId,Integer approvedById,Integer scstFunds,Integer financialYear,Integer hoaId,Integer workTypeId,Integer ProjSubType,Integer projectId,Integer type);
}
