package in.OAndM.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import in.OAndM.DTO.AdminAssignWorksModel;
import in.OAndM.DTO.AdminSanctionsModel;
import in.OAndM.DTO.TechnicalSanctionsModel;
import in.OAndM.Entities.AdminAssignWorksEntity;
import in.OAndM.Entities.AdminSanctionsEntity;
import in.OAndM.Entities.TechnicalSanctionEntity;
import in.OAndM.config.AppConstant;
import in.OAndM.core.BaseResponse;
import in.OAndM.core.BaseServiceImpl;
import in.OAndM.repositories.AdminSanctionRepo;
import in.OAndM.services.AdminSanctionService;
import in.OAndM.services.AssignAdminSanctionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional

public class AssignAdminSanctionServiceImpl extends BaseServiceImpl<AdminAssignWorksEntity, AdminAssignWorksModel, Integer>
		implements AssignAdminSanctionService {
	
	
	@Autowired
	AdminSanctionRepo adminSanctionRepo;
	
	

	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	@Override
	@Transactional
	public BaseResponse<HttpStatus, AdminAssignWorksModel> insertAssignAdminSanctions(AdminAssignWorksModel admin) {
		// TODO Auto-generated method stub

		BaseResponse<HttpStatus, AdminAssignWorksModel> responseJson = new BaseResponse<>();
			int i=0;

			responseJson = create(admin);
			 i=adminSanctionRepo.updateAdminSanctionAssignFlag(admin.getWorkId());
			
			 if(i>0) {
					logger.debug(appConstant.getValue(AppConstant.CREATE_SERVICE_SUCCESS));
			responseJson.setMessage("Successfully Submitted");
			responseJson.setStatus(HttpStatus.CREATED);
		} else {
			logger.debug(appConstant.getValue(AppConstant.CREATE_SERVICE_FAILED));
			responseJson.setMessage("Error in submission");
			responseJson.setStatus(HttpStatus.BAD_REQUEST);
		}
		return responseJson;
	}


}
