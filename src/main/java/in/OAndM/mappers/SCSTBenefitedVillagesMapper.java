package in.OAndM.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import in.OAndM.DTO.AdminAssignWorksModel;
import in.OAndM.DTO.AdminSanctionsModel;
import in.OAndM.DTO.SCSTBenefitedModel;
import in.OAndM.Entities.AdminAssignWorksEntity;
import in.OAndM.Entities.AdminSanctionsEntity;
import in.OAndM.Entities.SCSTBenefitedVillages;
import in.OAndM.core.BaseMapperImpl;

@Component
public class SCSTBenefitedVillagesMapper extends BaseMapperImpl<SCSTBenefitedVillages, SCSTBenefitedModel>{

	@Override
	public SCSTBenefitedModel mapEntityToModel(SCSTBenefitedVillages entity) {
		// TODO Auto-generated method stub
		
		SCSTBenefitedModel model = new SCSTBenefitedModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}

	@Override
	public SCSTBenefitedVillages mapModelToEntity(SCSTBenefitedModel model) {
		// TODO Auto-generated method stub
		SCSTBenefitedVillages entity = new SCSTBenefitedVillages();
		BeanUtils.copyProperties(model, entity);
		return entity;
	}

}
