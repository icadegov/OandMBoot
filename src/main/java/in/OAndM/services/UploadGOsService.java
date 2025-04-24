package in.OAndM.services;

import java.util.List;

import org.springframework.http.HttpStatus;

import in.OAndM.DTO.UploadGOsModel;
import in.OAndM.core.BaseResponse;

public interface UploadGOsService {
	
	public BaseResponse<HttpStatus, UploadGOsModel> insertGOs(UploadGOsModel gos) ;
	
	public BaseResponse<HttpStatus, List<UploadGOsModel>> getGosCirculars(String type);

}
