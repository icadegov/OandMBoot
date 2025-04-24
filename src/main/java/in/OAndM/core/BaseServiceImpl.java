package in.OAndM.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import in.OAndM.config.AppConstant;
import in.OAndM.requests.PaginationRequest;



public abstract class BaseServiceImpl<E, M, T> implements BaseService<M, T> {

	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	@Autowired
	protected BaseRepository<E, T> baseRepository;

	@Autowired
	protected BaseMapper<E, M> mapper;
	@Autowired
	protected AppConstant appConstant;

	@Autowired
	private BaseSpecification<E> baseSpecification;

	@Override
	public BaseResponse<HttpStatus, List<M>> get() {
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_STARTED));
		BaseResponse<HttpStatus, List<M>> responseJson = new BaseResponse<>();
		List<E> entities = baseRepository.findAll();
		List<M> models = mapper.mapEntityToModel(entities);
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setSuccess(true);
		responseJson.setData(models);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		return responseJson;
	}

	public BaseResponse<HttpStatus, List<M>> get(PaginationRequest pagination) {

		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_STARTED));
		BaseResponse<HttpStatus, List<M>> responseJson = new BaseResponse<>();

		Specification<E> specification = (pagination.getFilters() != null && !pagination.getFilters().isEmpty()) ?
				baseSpecification.buildSpecification(pagination.getFilters()) : null;
		Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize(), pagination.getAscending() ? Sort.by(pagination.getSortBy()).ascending() : Sort.by(pagination.getSortBy()).descending());
		Page<E> pageResult = baseRepository.findAll(specification, pageable);

		List<M> models = mapper.mapEntityToModel(pageResult.getContent());

		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setSuccess(true);
		responseJson.setData(models);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		return responseJson;
	}


	@Override
	public BaseResponse<HttpStatus, M> get(T id) {
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_STARTED));
		BaseResponse<HttpStatus, M> responseJson = null;
		M model;
		E entity = baseRepository.getOne(id);
		model = mapper.mapEntityToModel(entity);
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));

		responseJson = new BaseResponse<>();
		responseJson.setSuccess(true);
		responseJson.setData(model);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		return responseJson;
	}

	@Override
	public BaseResponse<HttpStatus, List<M>> get(List<T> ids) {
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_STARTED));
		BaseResponse<HttpStatus, List<M>> responseJson = null;
		List<M> model;
		List<E> entity = baseRepository.findAllById(ids);
		model = mapper.mapEntityToModel(entity);
		logger.debug(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));

		responseJson = new BaseResponse<>();
		responseJson.setSuccess(true);
		responseJson.setData(model);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		return responseJson;
	}

	@Override
	public BaseResponse<HttpStatus, M> create(M model) {
		logger.debug(appConstant.getValue(AppConstant.CREATE_SERVICE_STARTED));
		BaseResponse<HttpStatus, M> responseJson = null;
		E entity;
		entity = mapper.mapModelToEntity(model);
		entity = baseRepository.save(entity);
		model = mapper.mapEntityToModel(entity);
		logger.debug(appConstant.getValue(AppConstant.CREATE_SERVICE_SUCCESS));

		responseJson = new BaseResponse<>();
		responseJson.setSuccess(true);
		responseJson.setData(model);
		responseJson.setMessage(appConstant.getValue(AppConstant.CREATE_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.CREATED);
		return responseJson;
	}

	@Override
	public BaseResponse<HttpStatus, M> update(T id, M model) {
		logger.debug(appConstant.getValue(AppConstant.UPDATE_SERVICE_STARTED));
		BaseResponse<HttpStatus, M> responseJson = null;
		E entity;
		entity = baseRepository.getOne(id);
		mapper.mapModelToEntity(entity, model);
		entity = baseRepository.save(entity);
		model = mapper.mapEntityToModel(entity);
		logger.debug(appConstant.getValue(AppConstant.UPDATE_SERVICE_SUCCESS));

		responseJson = new BaseResponse<>();
		responseJson.setSuccess(true);
		responseJson.setData(model);
		responseJson.setMessage(appConstant.getValue(AppConstant.UPDATE_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.ACCEPTED);
		return responseJson;
	}

	@Override
	public BaseResponse<HttpStatus, M> delete(T id) {
		logger.debug(appConstant.getValue(AppConstant.DELETE_SERVICE_STARTED));
		BaseResponse<HttpStatus, M> responseJson = null;
		E entity = baseRepository.getOne(id);
		baseRepository.delete(entity);
		logger.debug(appConstant.getValue(AppConstant.DELETE_SERVICE_SUCCESS));

		responseJson = new BaseResponse<>();
		responseJson.setSuccess(true);
		responseJson.setMessage(appConstant.getValue(AppConstant.DELETE_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		return responseJson;
	}

	@Override
	public BaseResponse<HttpStatus, List<M>> delete(List<T> ids) {
		logger.debug(appConstant.getValue(AppConstant.DELETE_SERVICE_STARTED));
		BaseResponse<HttpStatus, List<M>> responseJson = null;
		List<E> entities = baseRepository.findAllById(ids);
		ValidationResponse vr = new ValidationResponse();
		boolean isValid = true;
		baseRepository.deleteAll(entities);
		logger.debug(appConstant.getValue(AppConstant.DELETE_SERVICE_SUCCESS));

		responseJson = new BaseResponse<>();
		responseJson.setSuccess(true);
		responseJson.setMessage(appConstant.getValue(AppConstant.DELETE_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		return responseJson;
	}

	public BaseResponse<HttpStatus, List<M>> saveAll(List<M> models) {
		logger.debug(appConstant.getValue(AppConstant.UPDATE_SERVICE_STARTED));
		BaseResponse<HttpStatus, List<M>> responseJson = new BaseResponse<>();
		List<E> entities = mapper.mapModelToEntity(models);
		ValidationResponse vr = new ValidationResponse();
		List<E> entityList = baseRepository.saveAll(entities);
		models = mapper.mapEntityToModel(entityList);

		responseJson.setSuccess(true);
		responseJson.setData(models);
		responseJson.setMessage(appConstant.getValue(AppConstant.UPDATE_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		return responseJson;
	}

	public BaseResponse<HttpStatus, M> prepareDataResponse(boolean isSuccess, String message, M data,
			HttpStatus status) {
		BaseResponse<HttpStatus, M> responseJson = new BaseResponse<>();
		responseJson.setSuccess(isSuccess);
		responseJson.setMessage(message);
		responseJson.setData(data);
		responseJson.setStatus(status);
		return responseJson;
	}

	public BaseResponse<HttpStatus, List<M>> prepareDataListResponse(boolean isSuccess, String message, List<M> data,
			HttpStatus status) {
		BaseResponse<HttpStatus, List<M>> responseJson = new BaseResponse<>();
		responseJson.setSuccess(isSuccess);
		responseJson.setMessage(message);
		responseJson.setData(data);
		responseJson.setStatus(status);
		return responseJson;
	}

}
