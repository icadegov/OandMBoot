package in.OAndM.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import in.OAndM.DTO.AdminAssignWorksModel;
import in.OAndM.DTO.AdminSanctionsModel;
import in.OAndM.Entities.AdminAssignWorksEntity;
import in.OAndM.Entities.AdminSanctionsEntity;
import in.OAndM.core.BaseMapperImpl;

@Component
public class AssignAdminSanctionMapper extends BaseMapperImpl<AdminAssignWorksEntity, AdminAssignWorksModel>{

	@Override
	public AdminAssignWorksModel mapEntityToModel(AdminAssignWorksEntity entity) {
		// TODO Auto-generated method stub
		
		AdminAssignWorksModel model = new AdminAssignWorksModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}

	@Override
	public AdminAssignWorksEntity mapModelToEntity(AdminAssignWorksModel model) {
		// TODO Auto-generated method stub
		AdminAssignWorksEntity entity = new AdminAssignWorksEntity();
		BeanUtils.copyProperties(model, entity);
		return entity;
	}

}
