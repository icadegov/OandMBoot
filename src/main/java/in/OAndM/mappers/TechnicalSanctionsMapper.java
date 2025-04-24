package in.OAndM.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import in.OAndM.DTO.TechnicalSanctionsModel;
import in.OAndM.Entities.TechnicalSanctionEntity;
import in.OAndM.core.BaseMapperImpl;

@Component
public class TechnicalSanctionsMapper extends BaseMapperImpl<TechnicalSanctionEntity, TechnicalSanctionsModel> {

	@Override
	public TechnicalSanctionsModel mapEntityToModel(TechnicalSanctionEntity entity) {
		// TODO Auto-generated method stub
		TechnicalSanctionsModel model = new TechnicalSanctionsModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}

	@Override
	public TechnicalSanctionEntity mapModelToEntity(TechnicalSanctionsModel model) {
		// TODO Auto-generated method stub
		TechnicalSanctionEntity entity = new TechnicalSanctionEntity();
		BeanUtils.copyProperties(model, entity);
		return entity;
	}

}
