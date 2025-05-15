package in.OAndM.repositories;


import in.OAndM.core.BaseRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import in.OAndM.DTO.BillsModel;
import in.OAndM.Entities.BillsEntity;

public interface BillsRepo extends  BaseRepository<BillsEntity, Integer>{
	
	@Query("SELECT new in.OAndM.DTO.BillsModel( aa.workId as workId, aa.workName as workName, "
			+ " wd.workDoneAmount as workDoneAmount, wd.billNo as billNo, wd.billType as billType,st.statusName as statusName ,agmt.agreementNumber as agreementNumber, "
			+ " :type as type )"
			+ " FROM AdminSanctionViewEntity aa left join  BillsEntity wd on aa.workId =wd.workId and wd.isLatest=true and wd.deleteFlag=false join wd.billStatusEntity st left join  wd.agreementsEntity agmt WHERE ( "
			+ "(:type = 5 AND aa.workId > 0 AND wd.statusId = 6) OR"
			+ "(:type = 6 AND aa.workId > 0 AND wd.statusId < 6)  "
			+ ")"
			+ " and aa.financialYear=:financialYear and aa.unitId=:unitId "
			+ " AND (:projectId <= 0 OR aa.projectId = :projectId) ")
	public List<BillsModel> findbillsEntityByFinancialYearAndUnitIdAndProjectId(Integer financialYear,Integer unitId, Integer type, Integer projectId);
	
	@Query("SELECT new in.OAndM.DTO.BillsModel( aa.workId as workId, aa.workName as workName, "
			+ " wd.workDoneAmount as workDoneAmount, wd.billNo as billNo, wd.billType as billType,st.statusName as statusName ,agmt.agreementNumber as agreementNumber, "
			+ " :type as type )"
			+ " FROM AdminSanctionViewEntity aa left join  BillsEntity wd on aa.workId =wd.workId and wd.isLatest=true and wd.deleteFlag=false join wd.billStatusEntity st left join  wd.agreementsEntity agmt WHERE ( "
			+ "(:type = 5 AND aa.workId > 0 AND wd.statusId = 6) OR"
			+ "(:type = 6 AND aa.workId > 0 AND wd.statusId < 6)  "
			+ ")"
			+ " and aa.financialYear=:financialYear and aa.hoaId=:hoaId and aa.workTypeId=:workTypeId")
	public List<BillsModel> findbillsEntityByFinancialYearAndHoaIdAndWorkTypeId(Integer financialYear,Integer hoaId,Integer workTypeId,Integer type);
	
	@Query("SELECT new in.OAndM.DTO.BillsModel( aa.workId as workId, aa.workName as workName, "
			+ " wd.workDoneAmount as workDoneAmount, wd.billNo as billNo, wd.billType as billType,st.statusName as statusName ,agmt.agreementNumber as agreementNumber, "
			+ " :type as type )"
			+ " FROM AdminSanctionViewEntity aa left join  BillsEntity wd on aa.workId =wd.workId and wd.isLatest=true and wd.deleteFlag=false join wd.billStatusEntity st left join  wd.agreementsEntity agmt WHERE ( "
			+ "(:type = 5 AND aa.workId > 0 AND wd.statusId = 6) OR"
			+ "(:type = 6 AND aa.workId > 0 AND wd.statusId < 6)  "
			+ ")"
			+ " and aa.financialYear=:financialYear and aa.approvedById=:approvedById ")
	public List<BillsModel> findbillsEntityViewEntityByFinancialYearAndApprovedById(Integer financialYear,Integer approvedById,Integer type);
	
	@Query("SELECT new in.OAndM.DTO.BillsModel( aa.workId as workId, aa.workName as workName, "
			+ " wd.workDoneAmount as workDoneAmount, wd.billNo as billNo, wd.billType as billType,st.statusName as statusName ,agmt.agreementNumber as agreementNumber, "
			+ " :type as type )"
			+ " FROM AdminSanctionViewEntity aa left join  BillsEntity wd on aa.workId =wd.workId and wd.isLatest=true and wd.deleteFlag=false join wd.billStatusEntity st left join  wd.agreementsEntity agmt WHERE ( "
			+ "(:type = 5 AND aa.workId > 0 AND wd.statusId = 6) OR"
			+ "(:type = 6 AND aa.workId > 0 AND wd.statusId < 6)  "
			+ ")"
			+ " and aa.financialYear=:financialYear and aa.workTypeId=:workTypeId ")
	public List<BillsModel> findbillsEntityViewEntityByFinancialYearAndWorkTypeId(Integer financialYear,Integer workTypeId,Integer type);
	
	@Query("SELECT new in.OAndM.DTO.BillsModel( aa.workId as workId, aa.workName as workName, "
			+ " wd.workDoneAmount as workDoneAmount, wd.billNo as billNo, wd.billType as billType,st.statusName as statusName ,agmt.agreementNumber as agreementNumber, "
			+ " :type as type )"
			+ " FROM AdminSanctionViewEntity aa left join  BillsEntity wd on aa.workId =wd.workId and wd.isLatest=true and wd.deleteFlag=false join wd.billStatusEntity st left join  wd.agreementsEntity agmt WHERE ( "
			+ "(:type = 5 AND aa.workId > 0 AND wd.statusId = 6) OR"
			+ "(:type = 6 AND aa.workId > 0 AND wd.statusId < 6)  "
			+ ")"
			+ " and aa.financialYear=:financialYear and aa.workTypeId=:workTypeId and aa.approvedById=:approvedById ")
	public List<BillsModel> findbillsEntityViewEntityByFinancialYearAndWorkTypeIdAndApprovedById(Integer financialYear,Integer workTypeId,Integer approvedById,Integer type);
	
	@Query("SELECT new in.OAndM.DTO.BillsModel( aa.workId as workId, aa.workName as workName, "
			+ " wd.workDoneAmount as workDoneAmount, wd.billNo as billNo, wd.billType as billType,st.statusName as statusName ,agmt.agreementNumber as agreementNumber, "
			+ " :type as type )"
			+ " FROM AdminSanctionViewEntity aa left join  BillsEntity wd on aa.workId =wd.workId and wd.isLatest=true and wd.deleteFlag=false join wd.billStatusEntity st left join  wd.agreementsEntity agmt WHERE ( "
			+ "(:type = 5 AND aa.workId > 0 AND wd.statusId = 6) OR"
			+ "(:type = 6 AND aa.workId > 0 AND wd.statusId < 6)  "
			+ ")"
			+ " and aa.financialYear=:financialYear and aa.workTypeId=:workTypeId and aa.approvedById IN :approvedByIds ")
	public List<BillsModel> findbillsEntityViewEntityByFinancialYearAndWorkTypeIdAndApprovedByIdIn(Integer financialYear,Integer workTypeId,List<Integer> approvedByIds,Integer type);
	
	

}
