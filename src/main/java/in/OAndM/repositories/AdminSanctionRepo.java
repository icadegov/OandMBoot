package in.OAndM.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.OAndM.Entities.AdminSanctionsEntity;
import in.OAndM.core.BaseRepository;


public interface AdminSanctionRepo  extends  BaseRepository<AdminSanctionsEntity, Integer>{
	
public List<AdminSanctionsEntity> findByunitIdAndFinancialYearAndIsLatestAndDeleteFlag(Integer unit,Integer finyear,Boolean isLatest,Boolean deleteFlag);


public AdminSanctionsEntity findByunitIdAndFinancialYearAndIsLatestAndDeleteFlagAndTechnEntriesIsLatestAndTechnEntriesTsId(Integer unit,Integer finyear,Boolean isLatest,Boolean deleteFlag,Boolean tech_is_latest,Integer tsId);

public AdminSanctionsEntity findByworkIdAndIsLatestAndDeleteFlag(Integer workId,Boolean isLatest,Boolean deleteFlag);

public List<AdminSanctionsEntity> findByunitIdAndFinancialYearAndIsLatestAndDeleteFlagAndCircleIdAndDivisionIdAndSubDivisionIdAndIsAssignedTrue
(Integer unit,Integer finyear,Boolean isLatest,Boolean deleteFlag,Integer circleId,Integer DivisionId,Integer SubDivisionId);

public List<AdminSanctionsEntity> findByUnitIdAndCircleIdAndDivisionIdAndSubDivisionIdAndApprovedByIdAndFinancialYearAndIsLatestTrueAndDeleteFlagFalseAndIsAssignedTrue
(Integer unit,Integer circle,Integer division,Integer subdivision,Integer approvedId,Integer finyear);

public List<AdminSanctionsEntity> findByUnitIdAndCircleIdAndDivisionIdAndSubDivisionIdAndIsAssignedFalseAndIsLatestTrueAndDeleteFlagFalse
(Integer unit,Integer circle,Integer division,Integer subdivision);


public List<AdminSanctionsEntity> findByunitIdAndFinancialYearAndIsLatestTrueAndDeleteFlagFalseAndIsAssignedTrue(Integer unit,Integer finyear);

@Modifying
@Query("update AdminSanctionsEntity admin set admin.isAssigned=true where admin.workId=:workId")
public int updateAdminSanctionAssignFlag(Integer workId); 

@Query(value = "SELECT nextval('work_approval_details_work_id_seq')", nativeQuery = true)
public Integer getNextWorkId();





}
