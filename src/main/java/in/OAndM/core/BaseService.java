package in.OAndM.core;


import java.util.List;

import org.springframework.http.HttpStatus;

import in.OAndM.requests.PaginationRequest;


public interface BaseService<M, T> {
	BaseResponse<HttpStatus, List<M>> get();
	BaseResponse<HttpStatus, List<M>> get(PaginationRequest pagination);
	BaseResponse<HttpStatus, M> get(T id);

	BaseResponse<HttpStatus, List<M>> get(List<T> ids);
	BaseResponse<HttpStatus, M> create(M model);
	BaseResponse<HttpStatus, M> update(T id, M model);
	BaseResponse<HttpStatus, M> delete(T id);	
	BaseResponse<HttpStatus, List<M>> delete(List<T> ids);
	BaseResponse<HttpStatus, List<M>> saveAll(List<M> models);
}
