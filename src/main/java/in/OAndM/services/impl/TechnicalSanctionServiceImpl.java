package in.OAndM.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import in.OAndM.DTO.TechnicalSanctionsModel;
import in.OAndM.Entities.TechnicalSanctionEntity;
import in.OAndM.config.AppConstant;
import in.OAndM.core.BaseResponse;
import in.OAndM.core.BaseServiceImpl;
import in.OAndM.repositories.TechnicalSanctionRepo;
import in.OAndM.services.TechnicalSanctionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
@Service
@Transactional
public class TechnicalSanctionServiceImpl extends BaseServiceImpl<TechnicalSanctionEntity, TechnicalSanctionsModel, Integer>  implements TechnicalSanctionService{

	@Autowired
	TechnicalSanctionRepo technicalSanctionRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
	
	@Override
	public  BaseResponse<HttpStatus, List<TechnicalSanctionsModel>> insertTechnicalSanctions(List<TechnicalSanctionsModel> list) {

		
		BaseResponse<HttpStatus, List<TechnicalSanctionsModel>> responseJson = new BaseResponse<>();
		
		
			if(list!=null) {
				responseJson=saveAll(list);
				logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
				responseJson.setMessage("Submitted SuccessFully");
				responseJson.setSuccess(true);
			}else {
				logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_FAILED));
				responseJson.setSuccess(false);
				responseJson.setMessage("Error in Submitting");
			}
			return responseJson;
	}
	
	
	
	public BaseResponse<HttpStatus, List<TechnicalSanctionsModel>>  getTechnicalSanctionByWorkId(Integer workId){

		BaseResponse<HttpStatus, List<TechnicalSanctionsModel>> responseJson = new BaseResponse<>();

		
		try {
		List<TechnicalSanctionEntity> entities = technicalSanctionRepo.findByAdminSanctionsWorkIdAndIsLatestTrueAndDeleteFlagFalse(workId);
		List<TechnicalSanctionsModel> techmodels =new ArrayList<>();
		for(TechnicalSanctionEntity  admin: entities) {
			TechnicalSanctionsModel model=new TechnicalSanctionsModel();
			model.setTsId(admin.getTsId());
			model.setTsNumber(admin.getTsNumber());
			
			techmodels.add(model);
		}
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setSuccess(true);
		responseJson.setData(techmodels);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		}
		catch(Exception e) {
			logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_FAILED));
			responseJson.setSuccess(false);
		
			responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_FAILED));
			responseJson.setStatus(HttpStatus.BAD_GATEWAY);
		}
		return responseJson;
		
	}
}
