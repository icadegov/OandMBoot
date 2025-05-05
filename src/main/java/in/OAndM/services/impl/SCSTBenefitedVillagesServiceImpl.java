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
import in.OAndM.DTO.SCSTBenefitedModel;
import in.OAndM.DTO.TechnicalSanctionsModel;
import in.OAndM.Entities.AdminAssignWorksEntity;
import in.OAndM.Entities.AdminSanctionsEntity;
import in.OAndM.Entities.SCSTBenefitedVillages;
import in.OAndM.Entities.TechnicalSanctionEntity;
import in.OAndM.config.AppConstant;
import in.OAndM.core.BaseResponse;
import in.OAndM.core.BaseServiceImpl;
import in.OAndM.repositories.AdminSanctionRepo;
import in.OAndM.repositories.SCSTBenefitedVillagesRepo;
import in.OAndM.services.AdminSanctionService;
import in.OAndM.services.AssignAdminSanctionService;
import in.OAndM.services.SCSTBenefitedVillagesService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional

public class SCSTBenefitedVillagesServiceImpl extends BaseServiceImpl<SCSTBenefitedVillages, SCSTBenefitedModel, Integer>
		implements SCSTBenefitedVillagesService {
	
	
	@Autowired
	SCSTBenefitedVillagesRepo adminRepo;
	
	

	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);


	@Override
	public BaseResponse<HttpStatus, List<SCSTBenefitedModel>> insertSCSTBenefitedVillages(List<SCSTBenefitedModel> admin) {
		// TODO Auto-generated method stub
		
		BaseResponse<HttpStatus, List<SCSTBenefitedModel>> response=new BaseResponse<>();
		
		int i=0;
		
		response=saveAll(admin);
		
		response.setMessage("Successfully Submitted");
		response.setStatus(HttpStatus.CREATED);
		return response;
	}


}
