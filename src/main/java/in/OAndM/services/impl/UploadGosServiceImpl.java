package in.OAndM.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import in.OAndM.DTO.UploadGOsModel;
import in.OAndM.Entities.UploadGOsEntity;
import in.OAndM.config.AppConstant;
import in.OAndM.core.BaseResponse;
import in.OAndM.core.BaseServiceImpl;
import in.OAndM.services.UploadGOsService;
import in.OAndM.repositories.UploadGOsRepo;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UploadGosServiceImpl  extends BaseServiceImpl<UploadGOsEntity, UploadGOsModel, Integer> implements UploadGOsService{
	
	@Autowired
	private UploadGOsRepo gosRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	public BaseResponse<HttpStatus, UploadGOsModel>  insertGOs(UploadGOsModel gos) {
		
		BaseResponse<HttpStatus, UploadGOsModel> responseJson = new BaseResponse<>();
		System.out.println("gos:"+ gos);
		
		if(gos!=null) {
			responseJson=create(gos);
			responseJson.setMessage("Submitted SuccessFully");
		}else {
			responseJson.setMessage("Error in submitting");
		}
		return responseJson;
	}

	@Override
	public BaseResponse<HttpStatus, List<UploadGOsModel>> getGosCirculars(String type) {
		// TODO Auto-generated method stub
		BaseResponse<HttpStatus, List<UploadGOsModel>> responseJson = new BaseResponse<>();
		List<UploadGOsEntity> list;
		List<UploadGOsModel> models = new ArrayList<>();
		 Sort sort = Sort.by(Sort.Order.desc("goDt"));
		list = gosRepo.findByuploadTypeAndDeleteFlagFalse(type,sort);
		for(UploadGOsEntity en : list) {
			UploadGOsModel model = new UploadGOsModel();
			model.setGoAmount(en.getGoAmount());
			model.setGoDesc(en.getGoDesc());
			model.setGoUrl(en.getGoUrl());
			model.setGoNumber(en.getGoNumber());
			model.setGoDt(en.getGoDt());
			model.setGoId(en.getGoId());
			model.setUploadType(en.getUploadType());
			models.add(model);
		}
		responseJson.setSuccess(true);
		responseJson.setData(models);
		responseJson.setMessage(appConstant.getValue(AppConstant.GET_SERVICE_SUCCESS));
		responseJson.setStatus(HttpStatus.OK);
		
		return responseJson;
	}

	
}
