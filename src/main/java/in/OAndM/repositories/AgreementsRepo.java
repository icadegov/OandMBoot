package in.OAndM.repositories;

import java.util.List;

import in.OAndM.core.BaseRepository;
import in.OAndM.Entities.AgreementsEntity;

public interface AgreementsRepo extends  BaseRepository<AgreementsEntity, Integer>{
	
	public List<AgreementsEntity> findByworkIdAndIsLatestTrueAndDeleteFlagFalse(Integer workId);
	
	public AgreementsEntity findByworkIdAndAgreementIdAndIsLatestAndDeleteFlagAndBillsIsLatestAndBillsDeleteFlag(Integer workId,Integer agreementId,Boolean isLatest,Boolean deleteFlag,Boolean billsIsLatest,Boolean billsDeleteFlag);

	public AgreementsEntity findByworkIdAndAgreementIdAndIsLatestAndDeleteFlag(Integer workId,Integer agreementId,Boolean isLatest,Boolean deleteFlag);
}
