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
import in.OAndM.DTO.SmallLiftsMasterModel;
import in.OAndM.DTO.TechnicalSanctionsModel;
import in.OAndM.Entities.AdminSanctionsEntity;
import in.OAndM.Entities.LiftMasterEntity;
import in.OAndM.Entities.SmallLiftsMaster;
import in.OAndM.Entities.TechnicalSanctionEntity;
import in.OAndM.config.AppConstant;
import in.OAndM.core.BaseResponse;
import in.OAndM.core.BaseServiceImpl;
import in.OAndM.repositories.AdminSanctionRepo;
import in.OAndM.repositories.SmallLiftMasterRepo;
import in.OAndM.services.AdminSanctionService;
import in.OAndM.services.SmallLiftService;
import jakarta.transaction.Transactional;

@Service
@Transactional

public class SmallLiftsServiceImpl extends BaseServiceImpl<SmallLiftsMaster, SmallLiftsMasterModel, Integer>
		implements SmallLiftService {
	@Autowired
	SmallLiftMasterRepo smallLiftMasterRepo;

	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	@Override
	public BaseResponse<HttpStatus, List<SmallLiftsMasterModel>> findbyUnitId(Integer unitId) {
		// TODO Auto-generated method stub
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_STARTED));
		BaseResponse<HttpStatus, List<SmallLiftsMasterModel>> responseJson = new BaseResponse<>();
		List<SmallLiftsMaster> entities = smallLiftMasterRepo.findByUnitIdAndDeleteFlagFalse(unitId);
		List<SmallLiftsMasterModel> models = mapper.mapEntityToModel(entities);
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setSuccess(true);
		responseJson.setData(models);

		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		return responseJson;
	}
}
