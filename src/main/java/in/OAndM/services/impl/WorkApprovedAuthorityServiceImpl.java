package in.OAndM.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import in.OAndM.DTO.WorkApprovedAuthorityModel;
import in.OAndM.DTO.WorksTypeMasterModel;
import in.OAndM.Entities.WorkApprovedAuthorityMst;
import in.OAndM.Entities.WorkTypeMstEntity;
import in.OAndM.config.AppConstant;
import in.OAndM.core.BaseResponse;
import in.OAndM.core.BaseServiceImpl;
import in.OAndM.repositories.WorkApprovedAuthorityRepo;
import in.OAndM.repositories.WorkTypeMasterRepo;
import in.OAndM.services.WorkApprovedAuthorityService;
import in.OAndM.services.WorkTypeMasterService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class WorkApprovedAuthorityServiceImpl extends BaseServiceImpl<WorkApprovedAuthorityMst, WorkApprovedAuthorityModel


, Integer>
		implements WorkApprovedAuthorityService {
	@Autowired
	WorkApprovedAuthorityRepo workApprovedAuthorityRepo;

	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	@Override
	public BaseResponse<HttpStatus, List<WorkApprovedAuthorityModel>> getAuthorityList() {
		// TODO Auto-generated method stub
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_STARTED));
		BaseResponse<HttpStatus, List<WorkApprovedAuthorityModel>> responseJson = new BaseResponse<>();
		List<WorkApprovedAuthorityMst> entities = workApprovedAuthorityRepo.findAll();
		List<WorkApprovedAuthorityModel> models = mapper.mapEntityToModel(entities);
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setSuccess(true);
		responseJson.setData(models);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		return responseJson;
	}


}
