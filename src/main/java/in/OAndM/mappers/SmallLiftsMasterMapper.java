package in.OAndM.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import in.OAndM.DTO.SmallLiftsMasterModel;
import in.OAndM.Entities.SmallLiftsMaster;
import in.OAndM.core.BaseMapperImpl;

@Component
public class SmallLiftsMasterMapper extends BaseMapperImpl<SmallLiftsMaster, SmallLiftsMasterModel>{

	@Override
	public SmallLiftsMasterModel mapEntityToModel(SmallLiftsMaster entity) {
		// TODO Auto-generated method stub
		
		SmallLiftsMasterModel model = new SmallLiftsMasterModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}

	@Override
	public SmallLiftsMaster mapModelToEntity(SmallLiftsMasterModel model) {
		// TODO Auto-generated method stub
		SmallLiftsMaster entity = new SmallLiftsMaster();
		BeanUtils.copyProperties(model, entity);
		return entity;
	}

}
