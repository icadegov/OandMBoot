package in.OAndM.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import in.OAndM.DTO.AdminSanctionsModel;
import in.OAndM.Entities.AdminSanctionsEntity;
import in.OAndM.core.BaseMapperImpl;

@Component
public class AdminSanctionMapper extends BaseMapperImpl<AdminSanctionsEntity, AdminSanctionsModel>{

	@Override
	public AdminSanctionsModel mapEntityToModel(AdminSanctionsEntity entity) {
		// TODO Auto-generated method stub
		
		AdminSanctionsModel model = new AdminSanctionsModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}

	@Override
	public AdminSanctionsEntity mapModelToEntity(AdminSanctionsModel model) {
		// TODO Auto-generated method stub
		AdminSanctionsEntity entity = new AdminSanctionsEntity();
		BeanUtils.copyProperties(model, entity);
		return entity;
	}

}
