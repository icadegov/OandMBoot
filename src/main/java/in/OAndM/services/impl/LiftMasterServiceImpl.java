package in.OAndM.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import in.OAndM.DTO.AdminSanctionsModel;
import in.OAndM.DTO.LiftsMasterModel;
import in.OAndM.DTO.TechnicalSanctionsModel;
import in.OAndM.DTO.WorksTypeMasterModel;
import in.OAndM.Entities.AdminSanctionsEntity;
import in.OAndM.Entities.LiftMasterEntity;
import in.OAndM.Entities.TechnicalSanctionEntity;
import in.OAndM.Entities.WorkTypeMstEntity;
import in.OAndM.config.AppConstant;
import in.OAndM.core.BaseResponse;
import in.OAndM.core.BaseServiceImpl;
import in.OAndM.repositories.AdminSanctionRepo;
import in.OAndM.repositories.LiftMasterRepo;
import in.OAndM.services.AdminSanctionService;
import in.OAndM.services.LiftMasterService;
import jakarta.transaction.Transactional;

@Service
@Transactional

public class LiftMasterServiceImpl extends BaseServiceImpl<LiftMasterEntity, LiftsMasterModel, Integer>
		implements LiftMasterService {
	@Autowired
	LiftMasterRepo liftMasterRepo;

	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	@Override
	public BaseResponse<HttpStatus, List<LiftsMasterModel>> findbyProjectId(Integer projectId) {
		// TODO Auto-generated method stub
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_STARTED));
		BaseResponse<HttpStatus, List<LiftsMasterModel>> responseJson = new BaseResponse<>();
		List<LiftMasterEntity> entities = liftMasterRepo.findByProjectIdAndDeleteFlagFalse(projectId);
		List<LiftsMasterModel> models = mapper.mapEntityToModel(entities);
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setSuccess(true);
		responseJson.setData(models);

		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		return responseJson;
	}


}
