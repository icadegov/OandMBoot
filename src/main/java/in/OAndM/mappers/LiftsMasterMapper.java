package in.OAndM.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import in.OAndM.DTO.AdminSanctionsModel;
import in.OAndM.DTO.LiftsMasterModel;
import in.OAndM.Entities.AdminSanctionsEntity;
import in.OAndM.Entities.LiftMasterEntity;
import in.OAndM.core.BaseMapperImpl;

@Component
public class LiftsMasterMapper extends BaseMapperImpl<LiftMasterEntity, LiftsMasterModel>{

	@Override
	public LiftsMasterModel mapEntityToModel(LiftMasterEntity entity) {
		// TODO Auto-generated method stub
		
		LiftsMasterModel model = new LiftsMasterModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}

	@Override
	public LiftMasterEntity mapModelToEntity(LiftsMasterModel model) {
		// TODO Auto-generated method stub
		LiftMasterEntity entity = new LiftMasterEntity();
		BeanUtils.copyProperties(model, entity);
		return entity;
	}

}
