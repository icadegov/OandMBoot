package in.OAndM.repositories;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.OAndM.DTO.AdminSanctionViewModel;
import in.OAndM.Entities.AdminSanctionViewEntity;
import in.OAndM.core.BaseRepository;

public interface AdminSanctionViewRepo extends BaseRepository<AdminSanctionViewEntity, Integer> {


	public List<AdminSanctionViewEntity> findByFinancialYearAndUnitIdAndApprovedById(Integer finYear, Integer unitId,
			Integer approvedId);

	public List<AdminSanctionViewEntity> findByFinancialYearAndUnitIdAndCircleIdAndApprovedById(Integer finYear, Integer unitId,
			Integer circleId, Integer approvedId);

	public List<AdminSanctionViewEntity> findByFinancialYearAndUnitIdAndCircleIdAndDivisionIdAndApprovedById(Integer finYear,
			Integer unitId, Integer circleId, Integer divisionId, Integer approvedId);

	public List<AdminSanctionViewEntity> findByFinancialYearAndUnitIdAndCircleIdAndDivisionIdAndSubDivisionIdAndApprovedById(
			Integer finYear, Integer unitId, Integer circleId, Integer divisionId, Integer subDivisionId,
			Integer approvedId);
	
	@Query( "select  new in.OAndM.DTO.AdminSanctionViewModel (hoaId as hoaId,headOfAccount as headOfAccount, count(distinct(workId)) as longWorkId "
			+ ",count(distinct(govtSanction)) as govtSanction,COALESCE(sum(govtSancAmount/ 100000),0) as govtSancAmount,"
			+ "count(distinct(omCommiteeSanction)) as omCommiteeSanction, COALESCE(sum(committeeSancAmount/ 100000),0) as committeeSancAmount, "
			+ "count(distinct(go45SanctionCe)) as go45SanctionCe, COALESCE(sum(go45SancAmountCe/ 100000),0) as go45SancAmountCe,  "
			+ "count(distinct(go45SanctionSe)) as go45SanctionSe, COALESCE(sum(go45SancAmountSe/ 100000),0) as go45SancAmountSe,"
			+ "count(distinct(go45SanctionEe)) as go45SanctionEe, COALESCE(sum(go45SancAmountEe/ 100000),0) as go45SancAmountEe,"
			+ "count(distinct(go45SanctionDee)) as go45SanctionDee, COALESCE(sum(go45SancAmountDee/ 100000),0) as go45SancAmountDee, "
			+ " COALESCE(sum(adminSanctionAmt/ 100000),0) as adminSanctionAmt )"
			+ " from AdminSanctionViewEntity where financialYear = :financialYear group by hoaId,headOfAccount ")
	public List<AdminSanctionViewModel> getAbsRepHOAWiseByFinancialyear(Integer financialYear);
	
	@Query( "select  new in.OAndM.DTO.AdminSanctionViewModel (unitId as unitId,unitName as unitName,hoaId as hoaId,headOfAccount as headOfAccount, count(distinct(workId)) as longWorkId "
			+ ",count(distinct(govtSanction)) as govtSanction, COALESCE(SUM(govtSancAmount / 100000), 0) as govtSancAmount, "
			+ "count(distinct(omCommiteeSanction)) as omCommiteeSanction, COALESCE(sum(committeeSancAmount/ 100000),0) as committeeSancAmount, "
			+ "count(distinct(go45SanctionCe)) as go45SanctionCe, COALESCE(sum(go45SancAmountCe/ 100000),0) as go45SancAmountCe,  "
			+ "count(distinct(go45SanctionSe)) as go45SanctionSe, COALESCE(sum(go45SancAmountSe/ 100000),0) as go45SancAmountSe,"
			+ "count(distinct(go45SanctionEe)) as go45SanctionEe, COALESCE(sum(go45SancAmountEe/ 100000),0) as go45SancAmountEe,"
			+ "count(distinct(go45SanctionDee)) as go45SanctionDee, COALESCE(sum(go45SancAmountDee/ 100000),0) as go45SancAmountDee, "
			+ " COALESCE(sum(adminSanctionAmt/ 100000),0) as adminSanctionAmt )"
			+ " from AdminSanctionViewEntity where financialYear = :financialYear group by hoaId,headOfAccount,unitId,unitName order by unitId,unitName,hoaId")
	public List<AdminSanctionViewModel> getAbsRepUnitHOAWiseFinyear(Integer financialYear);
	
	@Query( "select  new in.OAndM.DTO.AdminSanctionViewModel (unitId as unitId,unitName as unitName, count(distinct(workId)) as longWorkId "
			+ ",count(distinct(govtSanction)) as govtSanction, COALESCE(SUM(govtSancAmount / 100000), 0) as govtSancAmount, "
			+ "count(distinct(omCommiteeSanction)) as omCommiteeSanction, COALESCE(sum(committeeSancAmount/ 100000),0) as committeeSancAmount, "
			+ "count(distinct(go45Sanction)) as go45Sanction, COALESCE(sum(go45SancAmount/ 100000),0) as go45SancAmount,  "
			+ "count(distinct(scCount)) as scCount, COALESCE(sum(scAmount/ 100000),0) as scAmount,"
			+ "count(distinct(stCount)) as stCount, COALESCE(sum(stAmount/ 100000),0) as stAmount,"
			+ " COALESCE(sum(adminSanctionAmt/ 100000),0) as adminSanctionAmt )"
			+ " from AdminSanctionViewEntity where financialYear = :financialYear group by unitId,unitName order by unitId,unitName")
	public List<AdminSanctionViewModel> getAbsRepUnitWiseSCSTSdfFinyear(Integer financialYear);
	
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndUnitId(Integer finYear, Integer unitId);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndUnitIdAndScstFunds(Integer finYear, Integer unitId, Integer scst);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndUnitIdAndApprovedByIdIn(Integer financialYear, Integer unitId, List<Integer> authorityIds);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndUnitIdAndProjectId(Integer financialYear,Integer unitId,Integer projectId);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndUnitIdAndProjectIdAndApprovedByIdIn(Integer financialYear,Integer unitId,Integer projectId,List<Integer> authorityIds);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndUnitIdAndProjectIdAndApprovedById(Integer financialYear, Integer unitId, Integer projectId,Integer authorityId);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndUnitIdAndApprovedByIdAndHoaId(Integer financialYear,Integer unitId,Integer authorityId,Integer hoaId);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndUnitIdAndHoaId(Integer financialYear,Integer unitId,Integer hoaId);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndUnitIdAndApprovedByIdAndWorkTypeIdAndProjSubType(Integer financialYear,Integer unitId,Integer authorityId,Integer workTypeId,Integer projectSubType);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndUnitIdAndWorkTypeIdAndProjSubTypeAndApprovedByIdIn(Integer financialYear,Integer unitId,Integer workTypeId,Integer projectSubType,List<Integer> authorityIds);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndApprovedById(Integer financialYear,Integer authorityId);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndProjSubType(Integer financialYear,Integer projectSubType);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndHoaIdAndWorkTypeId(Integer financialYear,Integer hoaId,Integer workTypeId);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndApprovedByIdAndWorkTypeId(Integer financialYear,Integer authorityId,Integer workTypeId);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndWorkTypeIdAndApprovedByIdIn(Integer financialYear,Integer workTypeId,List<Integer> authorityIds);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndHoaId(Integer financialYear,Integer hoaId);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndHoaIdAndApprovedById(Integer financialYear,Integer hoaId, Integer authorityId);
	
	public List<AdminSanctionViewEntity> findByFinancialYearAndWorkTypeId(Integer financialYear,Integer workTypeId);
	
	@Query("SELECT aa FROM AdminSanctionViewEntity aa left join  WorkDetailsViewEntity wd on aa.workId =wd.techWorkId WHERE ("
			+ "(:type = 2 AND aa.workId > 0 AND wd.techWorkId > 0) OR"
			+ "(:type = 3 AND aa.workId > 0 AND wd.agrWorkId > 0) OR "
			+ "(:type = 4 AND aa.workId > 0 AND wd.techWorkId is null) OR "
			+ "(:type = 5 AND aa.workId > 0 AND wd.billsPaid > 0) OR "
			+ "(:type = 6 AND aa.workId > 0 AND wd.billsPending > 0) "
			+ ")"
			+ " and aa.financialYear=:financialYear and aa.approvedById=:authorityId ")
	public List<AdminSanctionViewEntity> findAdminSanctionViewEntityByFinancialYearAndApprovedById(Integer financialYear,Integer authorityId, Integer type);
	

	@Query("SELECT aa FROM AdminSanctionViewEntity aa left join  WorkDetailsViewEntity wd on aa.workId =wd.techWorkId WHERE ("
			+ "(:type = 2 AND aa.workId > 0 AND wd.techWorkId > 0) OR"
			+ "(:type = 3 AND aa.workId > 0 AND wd.agrWorkId > 0) OR "
			+ "(:type = 4 AND aa.workId > 0 AND wd.techWorkId is null) OR "
			+ "(:type = 5 AND aa.workId > 0 AND wd.billsPaid > 0) OR "
			+ "(:type = 6 AND aa.workId > 0 AND wd.billsPending > 0) "
			+ ")"
			+ "and aa.financialYear=:financialYear and aa.workTypeId=:workTypeId ")
	public List<AdminSanctionViewEntity> findAdminSanctionViewEntityByFinancialYearAndWorkTypeId(Integer financialYear,Integer workTypeId, Integer type);
	
	@Query("SELECT aa FROM AdminSanctionViewEntity aa left join  WorkDetailsViewEntity wd on aa.workId =wd.techWorkId WHERE ("
			+ "(:type = 2 AND aa.workId > 0 AND wd.techWorkId > 0) OR"
			+ "(:type = 3 AND aa.workId > 0 AND wd.agrWorkId > 0) OR "
			+ "(:type = 4 AND aa.workId > 0 AND wd.techWorkId is null) OR "
			+ "(:type = 5 AND aa.workId > 0 AND wd.billsPaid > 0) OR "
			+ "(:type = 6 AND aa.workId > 0 AND wd.billsPending > 0) "
			+ ")"
			+ " and aa.financialYear=:financialYear and aa.hoaId=:hoaId and aa.workTypeId=:workTypeId ")
	public List<AdminSanctionViewEntity> findAdminSanctionViewEntityByFinancialYearAndHoaIdAndWorkTypeId(Integer financialYear,Integer hoaId,Integer workTypeId, Integer type);
	
	@Query("SELECT aa FROM AdminSanctionViewEntity aa left join  WorkDetailsViewEntity wd on aa.workId =wd.techWorkId WHERE ( "
			+ "(:type = 2 AND aa.workId > 0 AND wd.techWorkId > 0) OR"
			+ "(:type = 3 AND aa.workId > 0 AND wd.agrWorkId > 0) OR "
			+ "(:type = 4 AND aa.workId > 0 AND wd.techWorkId is null) OR "
			+ "(:type = 5 AND aa.workId > 0 AND wd.billsPaid > 0) OR "
			+ "(:type = 6 AND aa.workId > 0 AND wd.billsPending > 0) "
			+ ")"
			+ " and aa.financialYear=:financialYear and aa.unitId=:unitId ")
	public List<AdminSanctionViewEntity> findAdminSanctionViewEntityByFinancialYearAndUnitId (Integer financialYear,Integer unitId, Integer type);
	
	@Query("SELECT aa FROM AdminSanctionViewEntity aa left join  WorkDetailsViewEntity wd on aa.workId =wd.techWorkId WHERE ( "
			+ "(:type = 2 AND aa.workId > 0 AND wd.techWorkId > 0) OR"
			+ "(:type = 3 AND aa.workId > 0 AND wd.agrWorkId > 0) OR "
			+ "(:type = 4 AND aa.workId > 0 AND wd.techWorkId is null) OR "
			+ "(:type = 5 AND aa.workId > 0 AND wd.billsPaid > 0) OR "
			+ "(:type = 6 AND aa.workId > 0 AND wd.billsPending > 0) "
			+ ")"
			+ " and aa.financialYear=:financialYear and aa.workTypeId=:workTypeId  and aa.approvedById=:authorityId ")
	public List<AdminSanctionViewEntity> findAdminSanctionViewEntityByFinancialYearAndWorkTypeIdAndApprovedById(Integer financialYear,Integer workTypeId,Integer authorityId,Integer type);
	
	@Query("SELECT aa FROM AdminSanctionViewEntity aa left join  WorkDetailsViewEntity wd on aa.workId =wd.techWorkId WHERE ( "
			+ "(:type = 2 AND aa.workId > 0 AND wd.techWorkId > 0) OR"
			+ "(:type = 3 AND aa.workId > 0 AND wd.agrWorkId > 0) OR "
			+ "(:type = 4 AND aa.workId > 0 AND wd.techWorkId is null) OR "
			+ "(:type = 5 AND aa.workId > 0 AND wd.billsPaid > 0) OR "
			+ "(:type = 6 AND aa.workId > 0 AND wd.billsPending > 0) "
			+ ")"
			+ " and aa.financialYear=:financialYear and aa.workTypeId=:workTypeId  and aa.approvedById IN :authorityIds ")
	public List<AdminSanctionViewEntity> findAdminSanctionViewEntityByFinancialYearAndWorkTypeIdAndApprovedByIdIn(Integer financialYear,Integer workTypeId,List<Integer> authorityIds,Integer type);
	
	@Query( "select  new in.OAndM.DTO.AdminSanctionViewModel (financialYear as financialYear,finYear as finYear,"
			+ "  count(distinct(workId)) as longWorkId,"
			+ " COALESCE(sum(adminSanctionAmt/ 100000),0) as adminSanctionAmt )"
			+ " from AdminSanctionViewEntity group by financialYear,finYear order by financialYear")
	public List<AdminSanctionViewModel> findByFinancialYear();

}
