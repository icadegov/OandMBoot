package in.OAndM.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import in.OAndM.DTO.AdminSanctionsModel;
import in.OAndM.DTO.WorksTypeMasterModel;
import in.OAndM.Entities.AdminSanctionsEntity;
import in.OAndM.Entities.WorkTypeMstEntity;
import in.OAndM.core.BaseMapperImpl;

@Component
public class WorkTypeMstMapper extends BaseMapperImpl<WorkTypeMstEntity, WorksTypeMasterModel>{

	@Override
	public WorksTypeMasterModel mapEntityToModel(WorkTypeMstEntity entity) {
		// TODO Auto-generated method stub
		
		WorksTypeMasterModel model = new WorksTypeMasterModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}

	@Override
	public WorkTypeMstEntity mapModelToEntity(WorksTypeMasterModel model) {
		// TODO Auto-generated method stub
		WorkTypeMstEntity entity = new WorkTypeMstEntity();
		BeanUtils.copyProperties(model, entity);
		return entity;
	}

}
