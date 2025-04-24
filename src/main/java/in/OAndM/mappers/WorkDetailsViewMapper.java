package in.OAndM.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import in.OAndM.DTO.AdminSanctionsModel;
import in.OAndM.DTO.WorkDetailsViewModel;
import in.OAndM.Entities.AdminSanctionsEntity;
import in.OAndM.Entities.WorkDetailsViewEntity;
import in.OAndM.core.BaseMapperImpl;

@Component
public class WorkDetailsViewMapper extends BaseMapperImpl<WorkDetailsViewEntity, WorkDetailsViewModel>{

	@Override
	public WorkDetailsViewModel mapEntityToModel(WorkDetailsViewEntity entity) {
		// TODO Auto-generated method stub
		
		WorkDetailsViewModel model = new WorkDetailsViewModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}

	@Override
	public WorkDetailsViewEntity mapModelToEntity(WorkDetailsViewModel model) {
		// TODO Auto-generated method stub
		WorkDetailsViewEntity entity = new WorkDetailsViewEntity();
		BeanUtils.copyProperties(model, entity);
		return entity;
	}

}
