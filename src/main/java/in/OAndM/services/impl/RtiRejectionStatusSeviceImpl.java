package in.OAndM.services.impl;


import in.OAndM.Entities.RtiRejectionStatus;
import in.OAndM.repositories.RtiRejectionStatusRepository;
import in.OAndM.requests.PaginationRequest;
import in.OAndM.core.BaseResponse;
import in.OAndM.services.RtiRejectionStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public  class RtiRejectionStatusSeviceImpl implements RtiRejectionStatusService {

    private final RtiRejectionStatusRepository rtiRejStatusRepository;
 

    @Autowired
    public RtiRejectionStatusSeviceImpl(RtiRejectionStatusRepository rtiRejStatusRepository) {
        this.rtiRejStatusRepository = rtiRejStatusRepository;
       
    }

    @Override
    public BaseResponse<HttpStatus, List<RtiRejectionStatus>> findAllByDeleteFalgFalse() {
        List<RtiRejectionStatus> rejStatus = rtiRejStatusRepository.findAllByDeleteFlagFalse();
       // List<RtiProformaGDto> dtos = rtiProformaGMapper.mapEntityToModel(proformas);
        
        BaseResponse<HttpStatus, List<RtiRejectionStatus>> response = new BaseResponse<>();
        response.setStatus(HttpStatus.OK);
        response.setMessage("Successfully retrieved all RTI Proforma G applications.");
       // response.setData(dtos);
        response.setData(rejStatus);
        response.setSuccess(true);
        //response.setTotal(rejStatus.size());
        
        return response;
    }

	@Override
	public BaseResponse<HttpStatus, List<RtiRejectionStatus>> get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse<HttpStatus, List<RtiRejectionStatus>> get(PaginationRequest pagination) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse<HttpStatus, RtiRejectionStatus> get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse<HttpStatus, List<RtiRejectionStatus>> get(List<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse<HttpStatus, RtiRejectionStatus> create(RtiRejectionStatus model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse<HttpStatus, RtiRejectionStatus> update(Integer id, RtiRejectionStatus model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse<HttpStatus, RtiRejectionStatus> delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse<HttpStatus, List<RtiRejectionStatus>> delete(List<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse<HttpStatus, List<RtiRejectionStatus>> saveAll(List<RtiRejectionStatus> models) {
		// TODO Auto-generated method stub
		return null;
	}

	
   
    

   
   

  

	
}

