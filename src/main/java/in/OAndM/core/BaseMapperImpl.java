package in.OAndM.core;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMapperImpl<E, M> implements BaseMapper<E, M> {
	
	@Override
	public void mapModelToEntity(E entity, M model) {
		BeanUtils.copyProperties(model, entity);
	}

	@Override
	public void mapEntityToModel(E entity, M model) {
		BeanUtils.copyProperties(entity, model);		
	}

	@Override
	public List<M> mapEntityToModel(List<E> entities) {
		List<M> models = new ArrayList<M>();
		if(entities!=null) {
			for (E entity : entities) {
				models.add(mapEntityToModel(entity));
			}
		}
		return models;
	}
	
	@Override
	public List<E> mapModelToEntity(List<M> models) {
		List<E> entities = new ArrayList<E>();
		for (M model : models) {
			entities.add(mapModelToEntity(model));
		}
		return entities;
	}
}
