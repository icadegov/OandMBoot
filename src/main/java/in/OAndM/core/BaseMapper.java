package in.OAndM.core;

import java.util.List;

public interface BaseMapper<E, M> {
	public M mapEntityToModel(E entity);
	public E mapModelToEntity(M model);
    public List<M> mapEntityToModel(List<E> entities);
    public List<E> mapModelToEntity(List<M> models);
    public void mapModelToEntity(E entity, M model);
    public void mapEntityToModel(E entity, M model);
}
