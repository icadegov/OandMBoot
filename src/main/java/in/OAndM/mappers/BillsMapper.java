package in.OAndM.mappers;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import in.OAndM.core.BaseMapperImpl;
import in.OAndM.DTO.BillsModel;
import in.OAndM.Entities.BillsEntity;

@Component
public class BillsMapper extends BaseMapperImpl<BillsEntity, BillsModel> {
	

	@Override
	public BillsModel mapEntityToModel(BillsEntity entity) {
		// TODO Auto-generated method stub
		BillsModel model = new BillsModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}

	@Override
	public BillsEntity mapModelToEntity(BillsModel model) {
		// TODO Auto-generated method stub
		BillsEntity entity = new BillsEntity();
		BeanUtils.copyProperties(model, entity);
		return entity;
	}

}
