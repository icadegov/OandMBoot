package in.OAndM.mappers;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import in.OAndM.core.BaseMapperImpl;
import in.OAndM.DTO.AgreementsModel;
import in.OAndM.Entities.AgreementsEntity;

@Component
public class AgreementsMapper extends BaseMapperImpl<AgreementsEntity, AgreementsModel> {
	

	@Override
	public AgreementsModel mapEntityToModel(AgreementsEntity entity) {
		// TODO Auto-generated method stub
		AgreementsModel model = new AgreementsModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}

	@Override
	public AgreementsEntity mapModelToEntity(AgreementsModel model) {
		// TODO Auto-generated method stub
		AgreementsEntity entity = new AgreementsEntity();
		BeanUtils.copyProperties(model, entity);
		return entity;
	}

}

