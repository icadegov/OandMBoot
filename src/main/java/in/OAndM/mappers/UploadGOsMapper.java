package in.OAndM.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import in.OAndM.DTO.UploadGOsModel;
import in.OAndM.Entities.UploadGOsEntity;
import in.OAndM.core.BaseMapperImpl;
	

@Component
public class UploadGOsMapper extends BaseMapperImpl<UploadGOsEntity, UploadGOsModel> {
	

	@Override
	public UploadGOsModel mapEntityToModel(UploadGOsEntity entity) {
		// TODO Auto-generated method stub
		UploadGOsModel model = new UploadGOsModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}

	@Override
	public UploadGOsEntity mapModelToEntity(UploadGOsModel model) {
		// TODO Auto-generated method stub
		UploadGOsEntity entity = new UploadGOsEntity();
		BeanUtils.copyProperties(model, entity);
		return entity;
	}

}
