package in.OAndM.services.impl;

import in.OAndM.DTO.CircleListForUnitId;
import in.OAndM.DTO.DivisionListForCircleId;
import in.OAndM.DTO.RtiApplicationDto;
import in.OAndM.DTO.RtiProformaGDto;
import in.OAndM.DTO.UnitLevelDataDto;
import in.OAndM.DTO.UnitLevelRequest;
import in.OAndM.DTO.UserDetailsDto;
import in.OAndM.Entities.RTIApplication;
import in.OAndM.Entities.RtiProformaG;
import in.OAndM.mappers.RtiApplicationMapper; // Assuming your mapper exists here
import in.OAndM.repositories.RtiApplicationRepository;
import in.OAndM.requests.PaginationRequest;
import in.OAndM.core.BaseResponse;
import in.OAndM.services.RTIApplicationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RTIApplicationServiceImpl implements RTIApplicationService {
	private static final Logger log = LoggerFactory.getLogger(RTIApplicationServiceImpl.class);
    private final RtiApplicationRepository rtiApplicationRepository;
    private final RtiApplicationMapper rtiApplicationMapper;
    
    @Autowired
    public RTIApplicationServiceImpl(RtiApplicationRepository rtiApplicationRepository, RtiApplicationMapper rtiApplicationMapper) {
        this.rtiApplicationRepository = rtiApplicationRepository;
        this.rtiApplicationMapper = rtiApplicationMapper;
    }

    @Override
    public BaseResponse<HttpStatus, List<RtiApplicationDto>> get() {
        //List<RTIApplication> applications = rtiApplicationRepository.findAll();
    	//List<RTIApplication> applications = rtiApplicationRepository.findAllByDeleteFlagFalse();findTop10ByDeleteFlagFalse
    	//List<RTIApplication> applications = rtiApplicationRepository.findTop10ByDeleteFlagFalse();
    	List<RTIApplication> applications = rtiApplicationRepository.findTop10ByDeleteFlagFalseOrderByApplicationIdDesc();
        List<RtiApplicationDto> dtos = rtiApplicationMapper.mapEntityToModel(applications);
        
        BaseResponse<HttpStatus, List<RtiApplicationDto>> response = new BaseResponse<>();
        response.setStatus(HttpStatus.OK);
        response.setMessage("Successfully retrieved all active  RTI applications.");
        response.setData(dtos);
        response.setSuccess(true);
        response.setTotal(dtos.size());
        
        return response;
    }

    @Override
    public BaseResponse<HttpStatus, RtiApplicationDto> get(Integer id) {
       // Optional<RTIApplication> application = rtiApplicationRepository.findById(id)
    	 Optional<RTIApplication> application = rtiApplicationRepository.findByIdAndDeleteFlagFalse(id); // Use inherited method
    	// System.out.println("application.size() "+application);
    	 BaseResponse<HttpStatus, RtiApplicationDto> response = new BaseResponse<>();
        
        if (application.isPresent()) {
            RtiApplicationDto dto = rtiApplicationMapper.mapEntityToModel(application.get());
            response.setStatus(HttpStatus.OK);
            response.setMessage("Application retrieved (active) successfully.");
            response.setData(dto);
            response.setSuccess(true);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("Application not found.");
            response.setSuccess(false);
        }
        
        return response;
    }

    @Override
    public BaseResponse<HttpStatus, RtiApplicationDto> create(RtiApplicationDto model) {
    	
        RTIApplication entity = rtiApplicationMapper.mapModelToEntity(model);
        entity.setIsLatest(true);
        entity.setDeleteFlag(false);
       
        entity.setCreateDate(LocalDateTime.now());
        //System.out.println("entity in service implca;"+entity);
        RTIApplication savedApplication = rtiApplicationRepository.save(entity);
        
        BaseResponse<HttpStatus, RtiApplicationDto> response = new BaseResponse<>();
        response.setStatus(HttpStatus.CREATED);
        response.setMessage("Application created successfully.");
        response.setData(rtiApplicationMapper.mapEntityToModel(savedApplication));
        response.setSuccess(true);
        
        return response;
    }

    @Override
    public BaseResponse<HttpStatus, RtiApplicationDto> update(Integer id, RtiApplicationDto model) {
       // Optional<RTIApplication> optionalApplication = rtiApplicationRepository.findById(id);
    	//System.out.println(" id "+id+ " model "+model);
    	Optional<RTIApplication> optionalApplication = rtiApplicationRepository.findByIdAndDeleteFlagFalse(id);    	
        BaseResponse<HttpStatus, RtiApplicationDto> response = new BaseResponse<>();        
        if (optionalApplication.isPresent()) {
            RTIApplication entity = optionalApplication.get();
                        
         // Map the model to entity but retain certain fields from the existing entity
            // Retain createdDate and createdBy, don't overwrite them with the incoming model
            rtiApplicationMapper.mapModelToEntity(entity, model);

            // Manually set fields that should not be updated--here handled in frontend make sure to use same names as entity in frontend 
           // entity.setCreateDate(entity.getCreateDate());  // Ensure createdDate is retained
           // entity.setCreatedBy(entity.getCreatedBy());  // Ensure createdBy is retained

            // Perform the update
            entity.setUpdatedDate(LocalDateTime.now());
            entity.setUpdatedBy(model.getUser().getUsername());
            RTIApplication updatedApplication = rtiApplicationRepository.save(entity);
            
            response.setStatus(HttpStatus.OK);
            response.setMessage("Application updated successfully.");
            response.setData(rtiApplicationMapper.mapEntityToModel(updatedApplication));
            response.setSuccess(true);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("Application not found.");
            response.setSuccess(false);
        }        
        return response;
    }

   
    
//    @Override
//    public BaseResponse<HttpStatus, RtiApplicationDto> delete(Integer id) {
//        Optional<RTIApplication> optionalApplication = rtiApplicationRepository.findById(id);
//        BaseResponse<HttpStatus, RtiApplicationDto> response = new BaseResponse<>();
//        
//        if (optionalApplication.isPresent()) {
//            rtiApplicationRepository.delete(optionalApplication.get());
//            response.setStatus(HttpStatus.OK);
//            response.setMessage("Application deleted successfully.");
//            response.setSuccess(true);
//        } else {
//            response.setStatus(HttpStatus.NOT_FOUND);
//            response.setMessage("Application not found.");
//            response.setSuccess(false);
//        }
//        
//        return response;
//    }
    @Override
    public BaseResponse<HttpStatus, RtiApplicationDto> delete(Integer id, String username) {
        //Optional<RTIApplication> optionalApplication = rtiApplicationRepository.findById(id);
    	Optional<RTIApplication> optionalApplication = rtiApplicationRepository.findByIdAndDeleteFlagFalse(id);
        if (!optionalApplication.isPresent()) {
            return createNotFoundResponse("Application not found.");
        }

        RTIApplication application = optionalApplication.get();
        application.setDeleteFlag(true); // Soft delete
        application.setDeletedDate(LocalDateTime.now());
        application.setDeletedBy(username);
        rtiApplicationRepository.save(application);

        BaseResponse<HttpStatus, RtiApplicationDto> response = new BaseResponse<>();
        response.setStatus(HttpStatus.OK);
        response.setMessage("Application marked as deleted.");
        response.setSuccess(true);

        return response;
    }

 // Helper method for creating not found responses
    private <T> BaseResponse<HttpStatus, T> createNotFoundResponse(String message) {
        BaseResponse<HttpStatus, T> response = new BaseResponse<>();
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setMessage(message);
        response.setSuccess(false);
        return response;
    }

//	@Override
//	public BaseResponse<HttpStatus, List<RtiApplicationDto>> get(PaginationRequest pagination) {
//		// TODO Auto-generated method stub
//		return null;
//	} 
    
    //consider pagination using pageable
//    @Override
//    public BaseResponse<HttpStatus, List<RtiApplicationDto>> get(PaginationRequest pagination) {
//        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize());
//        Page<RTIApplication> applicationsPage = rtiApplicationRepository.findAll(pageable);
//        
//        List<RtiApplicationDto> dtos = rtiApplicationMapper.mapEntityToModel(applicationsPage.getContent());
//        BaseResponse<HttpStatus, List<RtiApplicationDto>> response = new BaseResponse<>();
//        response.setStatus(HttpStatus.OK);
//        response.setMessage("Paginated applications retrieved successfully.");
//        response.setData(dtos);
//        response.setSuccess(true);
//        response.setTotal((int) applicationsPage.getTotalElements());
//        
//        return response;
//    }


	@Override
	public BaseResponse<HttpStatus, List<RtiApplicationDto>> get(List<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse<HttpStatus, List<RtiApplicationDto>> delete(List<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse<HttpStatus, List<RtiApplicationDto>> saveAll(List<RtiApplicationDto> models) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse<HttpStatus, List<RtiApplicationDto>> get(PaginationRequest pagination) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse<HttpStatus, RtiApplicationDto> delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse<HttpStatus, List<RtiApplicationDto>> getRTIAppnRegisterEntryListEE(UserDetailsDto u,
			LocalDate firstDayInPreviousQuarter, LocalDate lastDayInPreviousQuarter) {
		BaseResponse<HttpStatus, List<RtiApplicationDto>> response = new BaseResponse<>();
		
		  try {
		Integer unitId = u.getUnitId();
        Integer circleId = u.getCircleId();
        Integer divId = u.getDivisionId();
        Integer desgId = u.getDesignationId();

        // Adjust for special user scenario
        if ("Kavit070381".equals(u.getUsername()) && unitId == 9815 && circleId == 21588) {
            desgId = 5;
        }

        // Convert LocalDate to Date
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(lastDayInPreviousQuarter.atStartOfDay(defaultZoneId).toInstant());
        System.out.println("date "+date);
        Date fdate = Date.from(firstDayInPreviousQuarter.atStartOfDay(defaultZoneId).toInstant());
        System.out.println("fdate "+fdate);

        log.info("Fetching EE edit data for quarter desgId: {}, divId: {}, circleId: {}, unitId: {},fdate: {},date: {}",desgId,divId,circleId,unitId,fdate,date);
        List<UnitLevelDataDto> unitLevelData=new ArrayList<>(); 
        List<Map<String, Object>> rawData = rtiApplicationRepository.getRTIAppnRegisterEntryListEE(desgId,divId,circleId,unitId,fdate,date);
        System.out.println("rawData "+rawData);
        if (rawData == null || rawData.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("No records found to display");
            response.setData(null);
            return response;
        }
      	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      	
       List<RtiApplicationDto> rtiEdit=new ArrayList<>(); 
       if(rawData.size()>0) {
    	   for (int i = 0; i < rawData.size(); i++) {
    		    Map<String, Object> row = rawData.get(i);
    		    System.out.println("Row " + i + ": " + row);

    		    for (Map.Entry<String, Object> entry : row.entrySet()) {
    		        System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
    		    }
    	   }
       for(int i=0;i<rawData.size();i++) {
    	   RtiApplicationDto dto = new RtiApplicationDto();
    	   Map<String, Object> row = rawData.get(i);
    	    System.out.println("Row " + i + ": " + row);
       	dto.setApplicationId(Integer.parseInt(rawData.get(i).get(("application_id")).toString()));     	
           
       	
       	dto.setAppnNum((rawData.get(i).get(("application_num")).toString()));
     // Assuming application_date is a String in "yyyy-MM-dd" format
       	Timestamp appnDateTimestamp = (Timestamp) rawData.get(i).get("application_date");
        LocalDate appnDate = appnDateTimestamp.toLocalDateTime().toLocalDate();
        dto.setAppnDate(appnDate);    
            	
      dto.setApptName((rawData.get(i).get(("applicant_name")).toString()));
      dto.setApptAddress((rawData.get(i).get(("applicant_addrs")).toString())); 
      
      Timestamp pioRecDateTimestamp = (Timestamp) rawData.get(i).get("pio_receipt_date");
      dto.setPioRecDate(pioRecDateTimestamp.toLocalDateTime().toLocalDate());
      
       //	dto.setPioRecDate(LocalDate.parse(rawData.get(i).get("pio_receipt_date").toString(), formatter));
        dto.setApptCategory((rawData.get(i).get(("applicant_category")).toString()));
        dto.setDescInfoReq((rawData.get(i).get(("desc_info_req")).toString()));
        dto.setThirdParty((rawData.get(i).get(("third_party")).toString()));
        dto.setAppnFee(Integer.parseInt(rawData.get(i).get(("application_fee")).toString()));
        dto.setChargesCollected(Double.parseDouble(rawData.get(i).get(("charges_collected")).toString()));
        dto.setTotAmt(Double.parseDouble(rawData.get(i).get(("tot_amt")).toString()));
        dto.setIsTransferred((rawData.get(i).get(("is_transferred")).toString()));   
        Timestamp transDateTimestamp = (Timestamp) rawData.get(i).get("trans_date");
        if (transDateTimestamp != null) {
            dto.setTransDate(transDateTimestamp.toLocalDateTime().toLocalDate());
        } else {
            dto.setTransDate(null); // Or set a default value if required
        }
       
      
        
       //	dto.setTransDate(LocalDate.parse(rawData.get(i).get("trans_date").toString(), formatter));
        //dto.setTransName((rawData.get(i).get(("trans_name")).toString()));
        dto.setTransName(Optional.ofNullable(rawData.get(i).get("trans_name")).map(Object::toString).orElse(null));
        dto.setDeemedPio(Optional.ofNullable(rawData.get(i).get("deemed_pio")).map(Object::toString).orElse(null));
        //dto.setDeemedPio((rawData.get(i).get(("deemed_pio")).toString())); 
        dto.setInfoPartFull(Optional.ofNullable(rawData.get(i).get("info_part_full")).map(Object::toString).orElse(null));
       // dto.setInfoPartFull((rawData.get(i).get(("info_part_full")).toString()));
        
        Timestamp rejectDateTimestamp = (Timestamp) rawData.get(i).get("rejection_date");
        if (rejectDateTimestamp != null) {
        	 dto.setRejectDate(rejectDateTimestamp.toLocalDateTime().toLocalDate());
        } else {
            dto.setRejectDate(null); // Or set a default value if required
        }
       
        dto.setRejectSectionId(
        	    Optional.ofNullable(rawData.get(i).get("rejected_section_id"))
        	        .map(val -> Integer.parseInt(val.toString()))
        	        .orElse(null)
        	);
       // dto.setRejectSectionId(Integer.parseInt(rawData.get(i).get(("rejected_section_id")).toString()));
        dto.setRtiRejectionSection(Optional.ofNullable(rawData.get(i).get("rti_rejection_section")).map(Object::toString).orElse(null));
       // dto.setRtiRejectionSection((rawData.get(i).get(("rti_rejection_section")).toString()));
        dto.setDeemedRefusal(Optional.ofNullable(rawData.get(i).get("deemed_refusal")).map(Object::toString).orElse(null));
       // dto.setDeemedRefusal((rawData.get(i).get(("deemed_refusal")).toString()));
        Timestamp refusedDateTimestamp = (Timestamp) rawData.get(i).get("refused_date");
        if (refusedDateTimestamp != null) {
        	 dto.setRefusedDate(refusedDateTimestamp.toLocalDateTime().toLocalDate());
       } else {
           dto.setInfoFurnDate(null); // Or set a default value if required
       }
        dto.setAppealMade(Optional.ofNullable(rawData.get(i).get("appeal_made")).map(Object::toString).orElse(null));
       // dto.setAppealMade((rawData.get(i).get(("appeal_made")).toString()));
        dto.setRemarks(Optional.ofNullable(rawData.get(i).get("remarks")).map(Object::toString).orElse(null));
       // dto.setRemarks((rawData.get(i).get(("remarks")).toString()));
        Timestamp infoFurnDateTimestamp = (Timestamp) rawData.get(i).get("info_furnished_date");
        if (infoFurnDateTimestamp != null) {
        	 dto.setInfoFurnDate(infoFurnDateTimestamp.toLocalDateTime().toLocalDate());
       } else {
           dto.setInfoFurnDate(null); // Or set a default value if required
       }
       
       // dto.setInfoFurnDate(LocalDate.parse(rawData.get(i).get("info_furnished_date").toString(), formatter));
        dto.setUnit(Integer.parseInt(rawData.get(i).get("unit_id").toString()));
        dto.setCircle(Integer.parseInt(rawData.get(i).get("circle_id").toString()));
        dto.setDivision(Integer.parseInt(rawData.get(i).get("division_id").toString()));
        dto.setDesignation(Integer.parseInt(rawData.get(i).get("designation_id").toString()));
        dto.setCreatedPostId(Long.parseLong(rawData.get(i).get("created_postid").toString()));
        dto.setSubdivision(Integer.parseInt(rawData.get(i).get("subdivision_id").toString()));
        dto.setTransMode((rawData.get(i).get(("trans_mode")).toString()));
        Object transAmtObj = rawData.get(i).get("trans_amt");
        Integer transAmt = transAmtObj != null ? Integer.parseInt(transAmtObj.toString()) : 0;
        dto.setTransAmt(transAmt);
       // dto.setTransAmt(Integer.parseInt(rawData.get(i).get("trans_amt").toString()));  
  
           rtiEdit.add(dto);
       }
       
       response.setStatus(HttpStatus.OK);
       response.setMessage("Rti EE edit data retrieved successfully.");
       response.setData(rtiEdit);
       response.setSuccess(true);
   } }
	   
  catch (IllegalArgumentException e) {
       log.error("Validation error: {}", e.getMessage());
       response.setStatus(HttpStatus.BAD_REQUEST);
       response.setMessage(e.getMessage());
       response.setSuccess(false);
       response.setData(Collections.emptyList());
   } catch (Exception e) {
       log.error("Unexpected error while fetching Rti EE edit data", e);
       response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
       response.setMessage("An unexpected error occurred.");
       response.setSuccess(false);
       response.setData(Collections.emptyList());
   }

   return response;

}
	
	@Override
	public BaseResponse<HttpStatus, List<RtiApplicationDto>> getAppnYrQtrEEReport(UserDetailsDto u,Integer year, Integer Quarter) {
		BaseResponse<HttpStatus, List<RtiApplicationDto>> response = new BaseResponse<>();
		
		try {	
		Integer unit = u.getUnitId();
        Integer circle = u.getCircleId();
        Integer div = u.getDivisionId();
        Integer desg = u.getDesignationId();

        // Adjust for special user scenario
        if ("Kavit070381".equals(u.getUsername()) && unit == 9815 && circle == 21588) {
            desg = 5;
        }
        List<UnitLevelDataDto> unitLevelData=new ArrayList<>(); 
        List<RTIApplication> applications;
        List<Map<String, Object>> rawData;
        if(Quarter==5)
    	{
        	 rawData = rtiApplicationRepository.getAppnYrEEReport( desg,  div,  circle,  unit,  year );
    		    	}
    	else{
    		 //List<RTIApplication> applications = rtiApplicationRepository.findByUnitAndCircleAndDivisionAndDesignationAndDeleteFlagFalseOrderByPioReceiptDate(unit,circle,div,desg);
    		 //applications = rtiApplicationRepository.findByFilters(unit,circle,div,desg, year, Quater);
    		 rawData = rtiApplicationRepository.getAppnYrQtrEEReport( desg,  div,  circle,  unit,  year, Quarter );	
    	}
        //log.info("Fetching EE edit data for quarter desgId: {}, divId: {}, circleId: {}, unitId: {},fdate: {},date: {}",desgId,divId,circleId,unitId,fdate,date);
        
//        List<Map<String, Object>> rawData = rtiApplicationRepository.getAppnYrQtrEEReport(desgId,divId,circleId,unitId);
       
        List<RtiApplicationDto> rtiEdit=new ArrayList<>(); 
        for(int i=0;i<rawData.size();i++) {
     	   RtiApplicationDto dto = new RtiApplicationDto();
        	dto.setApplicationId(Integer.parseInt(rawData.get(i).get(("application_id")).toString()));  
        	dto.setAppnNum((rawData.get(i).get(("application_num")).toString()));
      // Assuming application_date is a String in "yyyy-MM-dd" format
        	Timestamp appnDateTimestamp = (Timestamp) rawData.get(i).get("application_date");
         LocalDate appnDate = appnDateTimestamp.toLocalDateTime().toLocalDate();
         dto.setAppnDate(appnDate);
       dto.setApptName((rawData.get(i).get(("applicant_name")).toString()));
       dto.setApptAddress((rawData.get(i).get(("applicant_addrs")).toString())); 
       
       Timestamp pioRecDateTimestamp = (Timestamp) rawData.get(i).get("pio_receipt_date");
       dto.setPioRecDate(pioRecDateTimestamp.toLocalDateTime().toLocalDate());
	     
	     
        //	dto.setPioRecDate(LocalDate.parse(rawData.get(i).get("pio_receipt_date").toString(), formatter));
         dto.setApptCategory((rawData.get(i).get(("applicant_category")).toString()));
         dto.setDescInfoReq((rawData.get(i).get(("desc_info_req")).toString()));
         dto.setThirdParty((rawData.get(i).get(("third_party")).toString()));
         dto.setAppnFee(Integer.parseInt(rawData.get(i).get(("application_fee")).toString()));
         dto.setChargesCollected(Double.parseDouble(rawData.get(i).get(("charges_collected")).toString()));
         dto.setTotAmt(Double.parseDouble(rawData.get(i).get(("tot_amt")).toString()));
         dto.setIsTransferred((rawData.get(i).get(("is_transferred")).toString()));   
         Timestamp transDateTimestamp = (Timestamp) rawData.get(i).get("trans_date");
         if (transDateTimestamp != null) {
             dto.setTransDate(transDateTimestamp.toLocalDateTime().toLocalDate());
         } else {
             dto.setTransDate(null); // Or set a default value if required
         }
        //	dto.setTransDate(LocalDate.parse(rawData.get(i).get("trans_date").toString(), formatter));
         //dto.setTransName((rawData.get(i).get(("trans_name")).toString()));
         dto.setTransName(Optional.ofNullable(rawData.get(i).get("trans_name")).map(Object::toString).orElse(null));
         dto.setDeemedPio(Optional.ofNullable(rawData.get(i).get("deemed_pio")).map(Object::toString).orElse(null));
         //dto.setDeemedPio((rawData.get(i).get(("deemed_pio")).toString())); 
         dto.setInfoPartFull(Optional.ofNullable(rawData.get(i).get("info_part_full")).map(Object::toString).orElse(null));
        // dto.setInfoPartFull((rawData.get(i).get(("info_part_full")).toString()));
         
         Timestamp rejectDateTimestamp = (Timestamp) rawData.get(i).get("rejection_date");
         if (rejectDateTimestamp != null) {
         	 dto.setRejectDate(rejectDateTimestamp.toLocalDateTime().toLocalDate());
         } else {
             dto.setRejectDate(null); // Or set a default value if required
         }
      
         dto.setRejectSectionId(
         	    Optional.ofNullable(rawData.get(i).get("rejected_section_id"))
         	        .map(val -> Integer.parseInt(val.toString()))
         	        .orElse(null)
         	);
        // dto.setRejectSectionId(Integer.parseInt(rawData.get(i).get(("rejected_section_id")).toString()));
         dto.setRtiRejectionSection(Optional.ofNullable(rawData.get(i).get("rti_rejection_section")).map(Object::toString).orElse(null));
        // dto.setRtiRejectionSection((rawData.get(i).get(("rti_rejection_section")).toString()));
         dto.setDeemedRefusal(Optional.ofNullable(rawData.get(i).get("deemed_refusal")).map(Object::toString).orElse(null));
        // dto.setDeemedRefusal((rawData.get(i).get(("deemed_refusal")).toString()));
         Timestamp refusedDateTimestamp = (Timestamp) rawData.get(i).get("refused_date");
         if (refusedDateTimestamp != null) {
         	 dto.setRefusedDate(refusedDateTimestamp.toLocalDateTime().toLocalDate());
        } else {
            dto.setRefusedDate(null); // Or set a default value if required
        }
         dto.setAppealMade(Optional.ofNullable(rawData.get(i).get("appeal_made")).map(Object::toString).orElse(null));
        // dto.setAppealMade((rawData.get(i).get(("appeal_made")).toString()));
         dto.setRemarks(Optional.ofNullable(rawData.get(i).get("remarks")).map(Object::toString).orElse(null));
        // dto.setRemarks((rawData.get(i).get(("remarks")).toString()));
         Timestamp infoFurnDateTimestamp = (Timestamp) rawData.get(i).get("info_furnished_date");
         if (infoFurnDateTimestamp != null) {
         	 dto.setInfoFurnDate(infoFurnDateTimestamp.toLocalDateTime().toLocalDate());
        } else {
            dto.setInfoFurnDate(null); // Or set a default value if required
        }
        
        // dto.setInfoFurnDate(LocalDate.parse(rawData.get(i).get("info_furnished_date").toString(), formatter));
         dto.setUnit(Integer.parseInt(rawData.get(i).get("unit_id").toString()));
         dto.setCircle(Integer.parseInt(rawData.get(i).get("circle_id").toString()));
         dto.setDivision(Integer.parseInt(rawData.get(i).get("division_id").toString()));
         dto.setDesignation(Integer.parseInt(rawData.get(i).get("designation_id").toString()));
         dto.setCreatedPostId(Long.parseLong(rawData.get(i).get("created_postid").toString()));
         dto.setSubdivision(Integer.parseInt(rawData.get(i).get("subdivision_id").toString()));
         dto.setTransMode((rawData.get(i).get(("trans_mode")).toString()));
         dto.setTransAmt(Integer.parseInt(rawData.get(i).get("trans_amt").toString()));  
//         Timestamp refusedDateTimestamp = (Timestamp) rawData.get(i).get("refused_date");
//         if (refusedDateTimestamp != null) {
//         	 dto.setRefusedDate(refusedDateTimestamp.toLocalDateTime().toLocalDate());
//        } else {
//            dto.setRefusedDate(null); // Or set a default value if required
//        }
         rtiEdit.add(dto);
        }
        
        response.setStatus(HttpStatus.OK);
        response.setMessage("Rti EE edit data retrieved successfully.");
        response.setData(rtiEdit);
        response.setSuccess(true);
    } catch (IllegalArgumentException e) {
        log.error("Validation error: {}", e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMessage(e.getMessage());
        response.setSuccess(false);
        response.setData(Collections.emptyList());
    } catch (Exception e) {
        log.error("Unexpected error while fetching Rti EE edit data", e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage("An unexpected error occurred.");
        response.setSuccess(false);
        response.setData(Collections.emptyList());
    }

    return response;

 }
	
	@Override
    public BaseResponse<HttpStatus, List<UnitLevelDataDto>> getrtiAppnConsolidatedProformaC(UserDetailsDto user, Integer year,
			Integer quarter) {
        BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = new BaseResponse<>();

        try {
            //int year =year
           // int qtr = rtiar.getQuarter();
            int month = getMonthForQuarter(quarter);
            //Integer unitId=user.getUnit();
            
            // Calculate the last day of the previous quarter
            LocalDate currentQuarterDate = LocalDate.of(year, month, 1);
            LocalDate previousQuarter = currentQuarterDate.minus(1, IsoFields.QUARTER_YEARS);
            long lastDayOfQuarter = IsoFields.DAY_OF_QUARTER.rangeRefinedBy(previousQuarter).getMaximum();
            LocalDate lastDayInPreviousQuarter = previousQuarter.with(IsoFields.DAY_OF_QUARTER, lastDayOfQuarter);
            java.sql.Date dt = java.sql.Date.valueOf(lastDayInPreviousQuarter);
            
            LocalDateTime lastDayWithTime = lastDayInPreviousQuarter.atStartOfDay(); // This gives 00:00:00 time
//dt2023-12-31 lastDayWithTime 2023-12-31T00:00  timestamp 2023-12-31 00:00:00.0
            // Convert to java.sql.Timestamp (which includes time)
            Timestamp timestamp = Timestamp.valueOf(lastDayWithTime);

            log.info("Fetching unit-level data for Year: {}, Quarter: {}, Date: {}", year, quarter, timestamp);
            List<UnitLevelDataDto> unitLevelData=new ArrayList<>(); 
            List<Map<String, Object>> rawData = rtiApplicationRepository.getrtiAppnConsolidatedProformaC(year,quarter,timestamp);
            log.debug("Raw data retrieved: {}", rawData);
            rawData.forEach(row -> row.forEach((key, value) -> 
           log.info("Key: {}, Value: {}, Type: {}", key, value, (value != null ? value.getClass() : "null"))
          
          
        ));
         
         //   coalesce((trans) ,0)as six  ,n.unit_id,0,0,rs15
            
            for(int i=0;i<rawData.size();i++) {
            	UnitLevelDataDto dto = new UnitLevelDataDto();
            	dto.setQpending(Integer.parseInt(rawData.get(i).get("pending").toString()));
            	
            	dto.setTotapp(Integer.parseInt(rawData.get(i).get(("appreceived")).toString()));
            	
            	dto.setTotdispo(Integer.parseInt(rawData.get(i).get("disposed").toString()));
            	dto.setTotPending(
            		    Integer.parseInt(rawData.get(i).get("pending").toString()) +
            		    Integer.parseInt(rawData.get(i).get("appreceived").toString()) -
            		    Integer.parseInt(rawData.get(i).get("disposed").toString())
            		);
            	dto.setInfor(Integer.parseInt(rawData.get(i).get("infofur").toString()));
            	dto.setDeemrefus(Integer.parseInt(rawData.get(i).get("deemrefus").toString()));            	
            	Object amountValue = rawData.get(i).get("sumtotamt");
                if (amountValue != null) {
                    try {
                        dto.setTotAmt(Double.parseDouble(amountValue.toString()));
                    } catch (NumberFormatException e) {
                        log.error("Invalid value for amount: {}", amountValue);
                        dto.setTotAmt(0.0); // Default to 0 in case of invalid number format
                    }
                } else {
                    dto.setTotAmt(0.0); // Default to 0 if "amount" is null
                }
            	dto.setRs1(Integer.parseInt(rawData.get(i).get("rs1").toString()));
            	dto.setRs2(Integer.parseInt(rawData.get(i).get("rs2").toString()));
            	dto.setRs3(Integer.parseInt(rawData.get(i).get("rs3").toString()));
            	dto.setRs4(Integer.parseInt(rawData.get(i).get("rs4").toString()));
            	dto.setRs5(Integer.parseInt(rawData.get(i).get("rs5").toString()));
            	dto.setRs6(Integer.parseInt(rawData.get(i).get("rs6").toString()));
            	dto.setRs7(Integer.parseInt(rawData.get(i).get("rs7").toString()));
            	dto.setRs8(Integer.parseInt(rawData.get(i).get("rs8").toString()));
            	dto.setRs9(Integer.parseInt(rawData.get(i).get("rs9").toString()));
            	dto.setRs10(Integer.parseInt(rawData.get(i).get("rs10").toString()));
            	dto.setRs11(Integer.parseInt(rawData.get(i).get("rs11").toString()));
            	dto.setRs12(Integer.parseInt(rawData.get(i).get("rs12").toString()));
            	dto.setRs13(Integer.parseInt(rawData.get(i).get("rs13").toString()));
            	dto.setRstot(Integer.parseInt(rawData.get(i).get("rstot").toString()));
            	dto.setUnitName((rawData.get(i).get(("unitName")).toString()));
            	dto.setRej6(Integer.parseInt(rawData.get(i).get("six").toString()));
            	dto.setUnitId(Integer.parseInt(rawData.get(i).get(("unitId")).toString()));
            	dto.setCircleId(0);
            	dto.setDivisionId(0);   
            	dto.setRs15(Integer.parseInt(rawData.get(i).get("rs15").toString()));
            	
                unitLevelData.add(dto);
            }
            
            response.setStatus(HttpStatus.OK);
            response.setMessage("Unit-level data retrieved successfully.");
            response.setData(unitLevelData);
            response.setSuccess(true);
        } catch (IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            response.setData(Collections.emptyList());
        } catch (Exception e) {
            log.error("Unexpected error while fetching unit-level data", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("An unexpected error occurred.");
            response.setSuccess(false);
            response.setData(Collections.emptyList());
        }

        return response;
    }
	
//	@Override
//    public BaseResponse<HttpStatus, List<UnitLevelDataDto>> getrtiAppnDivisionConsolidatedProformaC(UserDetailsDto u, Integer year,
//			Integer quarter) {
//        BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = new BaseResponse<>();
//
//        try {
//            //int year =year
//           // int qtr = rtiar.getQuarter();
//            int month = getMonthForQuarter(quarter);
//           
//            Integer unit=u.getUnit();
//        	Integer circle=u.getCircle();
//        	Integer	 division=u.getDivision();
//        	
//            // Calculate the last day of the previous quarter
//            LocalDate currentQuarterDate = LocalDate.of(year, month, 1);
//            LocalDate previousQuarter = currentQuarterDate.minus(1, IsoFields.QUARTER_YEARS);
//            long lastDayOfQuarter = IsoFields.DAY_OF_QUARTER.rangeRefinedBy(previousQuarter).getMaximum();
//            LocalDate lastDayInPreviousQuarter = previousQuarter.with(IsoFields.DAY_OF_QUARTER, lastDayOfQuarter);
//            java.sql.Date dt = java.sql.Date.valueOf(lastDayInPreviousQuarter);
//           // System.out.print("dt"+dt);
//            LocalDateTime lastDayWithTime = lastDayInPreviousQuarter.atStartOfDay(); // This gives 00:00:00 time
//            //System.out.print("lastDayWithTime"+lastDayWithTime);
//            // Convert to java.sql.Timestamp (which includes time)
//            Timestamp timestamp = Timestamp.valueOf(lastDayWithTime);
//            //System.out.print("timestamp"+timestamp);
//            log.info("Fetching unit-level data for Year: {}, Quarter: {}, Date: {}", year, quarter, timestamp);
//            List<UnitLevelDataDto> unitLevelData=new ArrayList<>(); 
//            List<Map<String, Object>> rawData = null;
//        	if(u.getUnit()!=4){
//        		if (u.getDesignationId()==12){
//        			unit=u.getUnit();//this was rtiar.unitId
//        			 circle=u.getCircle();//this was rtiar.circleId
//        			 System.out.print("1    Year "+year+"Qtr "+quarter+"unitId"+unit+"cid"+circle);
//        			 rawData = rtiApplicationRepository.getrtiAppnDivisionUCConsolidatedProformaC(year,quarter,timestamp,unit,circle);
//        		}
//        		if (u.getDesignationId()==7){        			
//        			 System.out.print("2  Year "+year+"Qtr "+quarter+"unitId"+unit+"cid"+circle);
//        			 rawData = rtiApplicationRepository.getrtiAppnDivisionUCConsolidatedProformaC(year,quarter,timestamp,unit,circle);
//        		}
//        		if (u.getDesignationId()==5){        			        			 
//        			  System.out.print("3   Year "+year+"Qtr "+quarter+"unitId"+unit+"cid"+circle+"division"+division);
//        			//  System.out.println("Desg  "+u.getDesignationId()+"did"+divisionId+"dt "+dt);
//        			 //sql=sql+ " where n.unit_id=" +unitId +" and n.circle_id="+circleId+" and n.division_id="+divisionId ;
//        			  rawData = rtiApplicationRepository.getrtiAppnDivisionUCDConsolidatedProformaC(year,quarter,timestamp,unit,circle,division);
//        		}
//        		
//        	}
//        	if(u.getUnit()==4){
//        		  rawData = rtiApplicationRepository.getrtiAppnDivisionUCConsolidatedProformaC(year,quarter,timestamp,unit,circle);
//        	System.out.print("4   Year "+year+"Qtr "+quarter+"unitId"+unit+"cid"+circle);
//        	
//        	}
//                        
//            log.debug("Raw data retrieved: {}", rawData);
////            rawData.forEach(row -> row.forEach((key, value) -> 
////           log.info("Key: {}, Value: {}, Type: {}", key, value, (value != null ? value.getClass() : "null")) ));
//         
//           // rawData.forEach(row -> log.info("Row keys: {}", row.keySet()));
//    
//            for(int i=0;i<rawData.size();i++) {
//            	UnitLevelDataDto dto = new UnitLevelDataDto();
//            	dto.setQpending(Integer.parseInt(rawData.get(i).get("pending").toString()));
//            	
//            	dto.setTotapp(Integer.parseInt(rawData.get(i).get(("appreceived")).toString()));
//            	//System.out.print("appreceived "+rawData.get(i).get(("appreceived")).toString());
//            	
//            	Object appReceivedValue = rawData.get(i).get("appreceived");
//            	log.info("Raw value for appreceived: {}", appReceivedValue);
//            	
//            	dto.setTotdispo(Integer.parseInt(rawData.get(i).get("disposed").toString()));
//            	dto.setTotPending(
//            		    Integer.parseInt(rawData.get(i).get("pending").toString()) +
//            		    Integer.parseInt(rawData.get(i).get("appreceived").toString()) -
//            		    Integer.parseInt(rawData.get(i).get("disposed").toString())
//            		);
//            	dto.setInfor(Integer.parseInt(rawData.get(i).get("infofur").toString()));
//            	dto.setDeemrefus(Integer.parseInt(rawData.get(i).get("deemrefus").toString()));            	
//            	Object amountValue = rawData.get(i).get("sumtotamt");
//                if (amountValue != null) {
//                    try {
//                        dto.setTotAmt(Double.parseDouble(amountValue.toString()));
//                    } catch (NumberFormatException e) {
//                        log.error("Invalid value for amount: {}", amountValue);
//                        dto.setTotAmt(0.0); // Default to 0 in case of invalid number format
//                    }
//                } else {
//                    dto.setTotAmt(0.0); // Default to 0 if "amount" is null
//                }
//            	dto.setRs1(Integer.parseInt(rawData.get(i).get("rs1").toString()));
//            	dto.setRs2(Integer.parseInt(rawData.get(i).get("rs2").toString()));
//            	dto.setRs3(Integer.parseInt(rawData.get(i).get("rs3").toString()));
//            	dto.setRs4(Integer.parseInt(rawData.get(i).get("rs4").toString()));
//            	dto.setRs5(Integer.parseInt(rawData.get(i).get("rs5").toString()));
//            	dto.setRs6(Integer.parseInt(rawData.get(i).get("rs6").toString()));
//            	dto.setRs7(Integer.parseInt(rawData.get(i).get("rs7").toString()));
//            	dto.setRs8(Integer.parseInt(rawData.get(i).get("rs8").toString()));
//            	dto.setRs9(Integer.parseInt(rawData.get(i).get("rs9").toString()));
//            	dto.setRs10(Integer.parseInt(rawData.get(i).get("rs10").toString()));
//            	dto.setRs11(Integer.parseInt(rawData.get(i).get("rs11").toString()));
//            	dto.setRs12(Integer.parseInt(rawData.get(i).get("rs12").toString()));
//            	dto.setRs13(Integer.parseInt(rawData.get(i).get("rs13").toString()));
//            	dto.setRstot(Integer.parseInt(rawData.get(i).get("rstot").toString()));
//            	dto.setUnitName((rawData.get(i).get(("unit_name")).toString()));
//            	dto.setRej6(Integer.parseInt(rawData.get(i).get("six").toString()));
//            	dto.setUnitId(Integer.parseInt(rawData.get(i).get(("unitId")).toString()));
//            	dto.setCircleId(Integer.parseInt(rawData.get(i).get(("circle_id")).toString()));
//            	dto.setDivisionId(Integer.parseInt(rawData.get(i).get(("division_id")).toString()));   
//            	
//            	 if (dto.getDivisionId() != 0) {
//                    
//                         dto.setDivisionName(u.getDivisionName());
//                     
//                 } else {
//                     dto.setDivisionName("circle office"); // Default to 0 if "amount" is null
//                 }
//            	dto.setRs15(Integer.parseInt(rawData.get(i).get("rs15").toString()));
//                unitLevelData.add(dto);
//            }
//            
//            response.setStatus(HttpStatus.OK);
//            response.setMessage("division-level data retrieved successfully.");
//            response.setData(unitLevelData);
//            response.setSuccess(true);
//        } catch (IllegalArgumentException e) {
//            log.error("Validation error: {}", e.getMessage());
//            response.setStatus(HttpStatus.BAD_REQUEST);
//            response.setMessage(e.getMessage());
//            response.setSuccess(false);
//            response.setData(Collections.emptyList());
//        } catch (Exception e) {
//            log.error("Unexpected error while fetching unit-level data", e);
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//            response.setMessage("An unexpected error occurred.");
//            response.setSuccess(false);
//            response.setData(Collections.emptyList());
//        }
//
//        return response;
//    }
	
	@Override
	public BaseResponse<HttpStatus, List<UnitLevelDataDto>> getrtiAppnDivisionConsolidatedProformaC(
	        UserDetailsDto u, Integer year, Integer quarter, List<CircleListForUnitId> circles, 
	        List<DivisionListForCircleId> divisions,Integer clickedUnitId, Integer clickedCircleId) {
	    
	    BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = new BaseResponse<>();
	    
	    try {
	        int month = getMonthForQuarter(quarter);
	        Integer unit = u.getUnitId();
	        Integer circle = u.getCircleId();
	        Integer division = u.getDivisionId();
	        
	        LocalDate currentQuarterDate = LocalDate.of(year, month, 1);
	        LocalDate previousQuarter = currentQuarterDate.minus(1, IsoFields.QUARTER_YEARS);
	        long lastDayOfQuarter = IsoFields.DAY_OF_QUARTER.rangeRefinedBy(previousQuarter).getMaximum();
	        LocalDate lastDayInPreviousQuarter = previousQuarter.with(IsoFields.DAY_OF_QUARTER, lastDayOfQuarter);
	        Timestamp timestamp = Timestamp.valueOf(lastDayInPreviousQuarter.atStartOfDay());
	        
	        log.info("Fetching unit-level data for Year: {}, Quarter: {}, Date: {}", year, quarter, timestamp);
	        
	        List<Map<String, Object>> rawData = null;
	        if (unit != 4) {
	            if (u.getDesignationId() == 12 || u.getDesignationId() == 7||u.getDesignationId() == 9||u.getDesignationId() == 10) {
	            	  System.out.println("unit not 4 and desg ! =5 "+unit);
	            	 log.info("Fetching drill down division data unit not 4 desg not 5 ", unit);
	            	  
	                rawData = rtiApplicationRepository.getrtiAppnDivisionUCConsolidatedProformaC(year, quarter, timestamp, unit, circle);
	            } else if (u.getDesignationId() == 5) {
	            	  System.out.println("unit not 4 and desg =5 "+unit);
	            	 log.info("Fetching drill down division data unit not 4 desg 5", unit);
	                rawData = rtiApplicationRepository.getrtiAppnDivisionUCDConsolidatedProformaC(year, quarter, timestamp, unit, circle, division);
	            }
	        } else {
	        	  System.out.println("unit =4 "+unit);
	        	  log.info("Fetching drill down division data unit 4", unit);
	        	  unit=clickedUnitId;
	        	  circle=clickedCircleId;
	            rawData = rtiApplicationRepository.getrtiAppnDivisionUCConsolidatedProformaC(year, quarter, timestamp, unit, circle);
	        }
	        
	        if (rawData == null || rawData.isEmpty()) {
	            response.setStatus(HttpStatus.NOT_FOUND);
	            response.setMessage("No records found to display");
	            response.setData(null);
	            return response;
	        }
	        
	        List<UnitLevelDataDto> unitLevelData = new ArrayList<>();
	        for (Map<String, Object> row : rawData) {
	            UnitLevelDataDto dto = new UnitLevelDataDto();
	            dto.setQpending(Integer.parseInt(row.get("pending").toString()));
	            dto.setTotapp(Integer.parseInt(row.get("appreceived").toString()));
	            dto.setTotdispo(Integer.parseInt(row.get("disposed").toString()));
	            dto.setTotPending(dto.getQpending() + dto.getTotapp() - dto.getTotdispo());
	            dto.setInfor(Integer.parseInt(row.get("infofur").toString()));
	            dto.setDeemrefus(Integer.parseInt(row.get("deemrefus").toString()));
	            dto.setTotAmt(row.get("sumtotamt") != null ? Double.parseDouble(row.get("sumtotamt").toString()) : 0.0);
	            dto.setRs1(Integer.parseInt(row.get("rs1").toString()));
	            dto.setRs2(Integer.parseInt(row.get("rs2").toString()));
	            dto.setRs3(Integer.parseInt(row.get("rs3").toString()));
	            dto.setRs4(Integer.parseInt(row.get("rs4").toString()));
	            dto.setRs5(Integer.parseInt(row.get("rs5").toString()));
	            dto.setRs6(Integer.parseInt(row.get("rs6").toString()));
	            dto.setRs7(Integer.parseInt(row.get("rs7").toString()));
	            dto.setRs8(Integer.parseInt(row.get("rs8").toString()));
	            dto.setRs9(Integer.parseInt(row.get("rs9").toString()));
	            dto.setRs10(Integer.parseInt(row.get("rs10").toString()));
	            dto.setRs11(Integer.parseInt(row.get("rs11").toString()));
	            dto.setRs12(Integer.parseInt(row.get("rs12").toString()));
	            dto.setRs13(Integer.parseInt(row.get("rs13").toString()));
	            dto.setRstot(Integer.parseInt(row.get("rstot").toString()));
	            dto.setUnitName(row.get("unit_name").toString());
	            dto.setRej6(Integer.parseInt(row.get("six").toString()));
	            dto.setUnitId(Integer.parseInt(row.get("unitId").toString()));
	            dto.setCircleId(Integer.parseInt(row.get("circle_id").toString()));
	            dto.setDivisionId(Integer.parseInt(row.get("division_id").toString()));
	            dto.setRs15(Integer.parseInt(row.get("rs15").toString()));
	            unitLevelData.add(dto);
	        }
	              
         //for unitId !=4 and deg=5 0r 7 we straight away use user unit and circle id ( for assigning names)  when unit =4 we are using rtiar circle id have to check for unit=4 case
	        
	        for (UnitLevelDataDto data : unitLevelData) {
	            for (CircleListForUnitId circleObj : circles) {
	                if (circleObj.getCircleId().equals(data.getCircleId())) {
	                    data.setCircleName(circleObj.getCircleName());
	                    break;
	                }
	            }
	            
	            for (DivisionListForCircleId divisionObj : divisions) {
	                if (divisionObj.getDivisionId().equals(data.getDivisionId())) {
	                    data.setDivisionName(divisionObj.getDivisionName());
	                    break;
	                }
	            }
	            
	            if (data.getDivisionId().equals(0)) {
	                data.setDivisionName("Circle Office");
	            }
	        }
	        
	        response.setStatus(HttpStatus.OK);
	        response.setMessage("Success");
	        response.setData(unitLevelData);
	        return response;
	        
	    } catch (Exception e) {
	        log.error("Unexpected error while fetching Division-level data", e);
	        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        response.setMessage("An unexpected error occurred.");
	        response.setData(null);
	        return response;
	    }
	}


	
	
	@Override
    public BaseResponse<HttpStatus, List<UnitLevelDataDto>> getrtiAppnCircleConsolidatedProformaC(UserDetailsDto u, Integer year,
			Integer quarter, List<CircleListForUnitId> circles, 
	        List<DivisionListForCircleId> divisions) {
        BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = new BaseResponse<>();

        try {
           
            int month = getMonthForQuarter(quarter);
            Integer unit=u.getUnitId();
        	
            // Calculate the last day of the previous quarter
            LocalDate currentQuarterDate = LocalDate.of(year, month, 1);
            LocalDate previousQuarter = currentQuarterDate.minus(1, IsoFields.QUARTER_YEARS);
            long lastDayOfQuarter = IsoFields.DAY_OF_QUARTER.rangeRefinedBy(previousQuarter).getMaximum();
            LocalDate lastDayInPreviousQuarter = previousQuarter.with(IsoFields.DAY_OF_QUARTER, lastDayOfQuarter);
            java.sql.Date dt = java.sql.Date.valueOf(lastDayInPreviousQuarter);
           // System.out.print("dt"+dt);
            LocalDateTime lastDayWithTime = lastDayInPreviousQuarter.atStartOfDay(); // This gives 00:00:00 time
            //System.out.print("lastDayWithTime"+lastDayWithTime);
            // Convert to java.sql.Timestamp (which includes time)
            Timestamp timestamp = Timestamp.valueOf(lastDayWithTime);
            //System.out.print("timestamp"+timestamp);
            log.info("Fetching unit-level data for Year: {}, Quarter: {}, Date: {}", year, quarter, timestamp);
            
            List<UnitLevelDataDto> unitLevelData=new ArrayList<>(); 
            List<Map<String, Object>> rawData = null;
        	if(u.getUnitId()!=4){
        		if (u.getDesignationId()==12||u.getDesignationId()==9||u.getDesignationId()==10){
        			unit=u.getUnitId();  
        		}
        	}
        	 
        	rawData = rtiApplicationRepository.getrtiAppnCircleConsolidatedProformaC(year,quarter,timestamp,unit);              
           
        	log.debug("Raw data retrieved: {}", rawData);
            rawData.forEach(row -> row.forEach((key, value) -> 
           log.info("Key: {}, Value: {}, Type: {}", key, value, (value != null ? value.getClass() : "null")) ));
        	 
        	if (rawData == null || rawData.isEmpty()) {
	            response.setStatus(HttpStatus.NOT_FOUND);
	            response.setMessage("No records found to display");
	            response.setData(null);
	            return response;
	        }
        	
            rawData.forEach(row -> log.info("Row keys: {}", row.keySet()));
    
            for(int i=0;i<rawData.size();i++) {
            	UnitLevelDataDto dto = new UnitLevelDataDto();
            	dto.setQpending(Integer.parseInt(rawData.get(i).get("pending").toString()));
            	
            	dto.setTotapp(Integer.parseInt(rawData.get(i).get(("appreceived")).toString()));
            	//System.out.print("appreceived "+rawData.get(i).get(("appreceived")).toString());
            	
            	Object appReceivedValue = rawData.get(i).get("appreceived");
            	//log.info("Raw value for appreceived: {}", appReceivedValue);
            	
            	dto.setTotdispo(Integer.parseInt(rawData.get(i).get("disposed").toString()));
            	dto.setTotPending(
            		    Integer.parseInt(rawData.get(i).get("pending").toString()) +
            		    Integer.parseInt(rawData.get(i).get("appreceived").toString()) -
            		    Integer.parseInt(rawData.get(i).get("disposed").toString())
            		);
            	
            	
            	dto.setInfor(Integer.parseInt(rawData.get(i).get("infofur").toString()));
            	dto.setDeemrefus(Integer.parseInt(rawData.get(i).get("deemrefus").toString()));            	
            	Object amountValue = rawData.get(i).get("sumtotamt");
                if (amountValue != null) {
                    try {
                        dto.setTotAmt(Double.parseDouble(amountValue.toString()));
                    } catch (NumberFormatException e) {
                        log.error("Invalid value for amount: {}", amountValue);
                        dto.setTotAmt(0.0); // Default to 0 in case of invalid number format
                    }
                } else {
                    dto.setAmount(0); // Default to 0 if "amount" is null
                }
            	dto.setRs1(Integer.parseInt(rawData.get(i).get("rs1").toString()));
            	dto.setRs2(Integer.parseInt(rawData.get(i).get("rs2").toString()));
            	dto.setRs3(Integer.parseInt(rawData.get(i).get("rs3").toString()));
            	dto.setRs4(Integer.parseInt(rawData.get(i).get("rs4").toString()));
            	dto.setRs5(Integer.parseInt(rawData.get(i).get("rs5").toString()));
            	dto.setRs6(Integer.parseInt(rawData.get(i).get("rs6").toString()));
            	dto.setRs7(Integer.parseInt(rawData.get(i).get("rs7").toString()));
            	dto.setRs8(Integer.parseInt(rawData.get(i).get("rs8").toString()));
            	dto.setRs9(Integer.parseInt(rawData.get(i).get("rs9").toString()));
            	dto.setRs10(Integer.parseInt(rawData.get(i).get("rs10").toString()));
            	dto.setRs11(Integer.parseInt(rawData.get(i).get("rs11").toString()));
            	dto.setRs12(Integer.parseInt(rawData.get(i).get("rs12").toString()));
            	dto.setRs13(Integer.parseInt(rawData.get(i).get("rs13").toString()));
            	dto.setRstot(Integer.parseInt(rawData.get(i).get("rstot").toString()));
            	dto.setUnitName((rawData.get(i).get(("unit_name")).toString()));
            	dto.setRej6(Integer.parseInt(rawData.get(i).get("six").toString()));
            	dto.setUnitId(Integer.parseInt(rawData.get(i).get(("unitId")).toString()));
            	dto.setCircleId(Integer.parseInt(rawData.get(i).get(("circle_id")).toString()));
            	dto.setDivisionId(0);
            	dto.setRs15(Integer.parseInt(rawData.get(i).get("rs15").toString()));
                unitLevelData.add(dto);
            }
            
            for (UnitLevelDataDto data : unitLevelData) {
	            for (CircleListForUnitId circleObj : circles) {
	                if (circleObj.getCircleId().equals(data.getCircleId())) {
	                    data.setCircleName(circleObj.getCircleName());
	                    System.out.print("circleName "+data.getCircleName());
	                    break;
	                }
	            }
	            
	            if (data.getCircleId().equals(0)) {
	                data.setCircleName("Unit Office");
	            }
	        }
	        
	        response.setStatus(HttpStatus.OK);
	        response.setMessage("Success");
	        response.setData(unitLevelData);
	        return response;
	        
	    } catch (Exception e) {
	        log.error("Unexpected error while fetching Circle-level data", e);
	        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        response.setMessage("An unexpected error occurred.");
	        response.setData(null);
	        return response;
	    }
	}
	@Override
	public BaseResponse<HttpStatus, List<UnitLevelDataDto>> getrtiAppnCEDashboard(UserDetailsDto user, Integer year,
			Integer quarter){
        BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = new BaseResponse<>();

        try {
            //int year =year
           // int qtr = rtiar.getQuarter();
            int month = getMonthForQuarter(quarter);
            Integer unit = user.getUnitId();
            System.out.println("unit CE dashboard"+unit);
            // Calculate the last day of the previous quarter
            LocalDate currentQuarterDate = LocalDate.of(year, month, 1);
            LocalDate previousQuarter = currentQuarterDate.minus(1, IsoFields.QUARTER_YEARS);
            long lastDayOfQuarter = IsoFields.DAY_OF_QUARTER.rangeRefinedBy(previousQuarter).getMaximum();
            LocalDate lastDayInPreviousQuarter = previousQuarter.with(IsoFields.DAY_OF_QUARTER, lastDayOfQuarter);
            java.sql.Date dt = java.sql.Date.valueOf(lastDayInPreviousQuarter);
            
            LocalDateTime lastDayWithTime = lastDayInPreviousQuarter.atStartOfDay(); // This gives 00:00:00 time
//dt2023-12-31 lastDayWithTime 2023-12-31T00:00  timestamp 2023-12-31 00:00:00.0
            // Convert to java.sql.Timestamp (which includes time)
            Timestamp timestamp = Timestamp.valueOf(lastDayWithTime);

            log.info("Fetching unit-level data for Year: {}, Quarter: {}, Date: {}", year, quarter, timestamp);
            List<UnitLevelDataDto> unitLevelData=new ArrayList<>(); 
            List<Map<String, Object>> rawData = rtiApplicationRepository.getrtiAppnCEDashboard(year,quarter,timestamp,unit);
            log.debug("Raw data retrieved: {}", rawData);
            rawData.forEach(row -> row.forEach((key, value) -> 
           log.info("Key: {}, Value: {}, Type: {}", key, value, (value != null ? value.getClass() : "null"))
          
          
        ));
         
         //   coalesce((trans) ,0)as six  ,n.unit_id,0,0,rs15
            
            for(int i=0;i<rawData.size();i++) {
            	UnitLevelDataDto dto = new UnitLevelDataDto();
            	dto.setQpending(Integer.parseInt(rawData.get(i).get("pending").toString()));
            	
            	dto.setTotapp(Integer.parseInt(rawData.get(i).get(("appreceived")).toString()));
            	
            	dto.setTotdispo(Integer.parseInt(rawData.get(i).get("disposed").toString()));
            	dto.setTotPending(
            		    Integer.parseInt(rawData.get(i).get("pending").toString()) +
            		    Integer.parseInt(rawData.get(i).get("appreceived").toString()) -
            		    Integer.parseInt(rawData.get(i).get("disposed").toString())
            		);
            	dto.setInfor(Integer.parseInt(rawData.get(i).get("infofur").toString()));
            	dto.setDeemrefus(Integer.parseInt(rawData.get(i).get("deemrefus").toString()));            	
            	
            	
            	dto.setUnitName((rawData.get(i).get(("unitName")).toString()));
            	dto.setRej6(Integer.parseInt(rawData.get(i).get("six").toString()));
            	dto.setUnitId(Integer.parseInt(rawData.get(i).get(("unitId")).toString()));
            	dto.setCircleId(0);
            	dto.setDivisionId(0);   
            	
            	
                unitLevelData.add(dto);
            }
            
            response.setStatus(HttpStatus.OK);
            response.setMessage("Unit-level dashboard data retrieved successfully.");
            response.setData(unitLevelData);
            response.setSuccess(true);
        } catch (IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            response.setData(Collections.emptyList());
        } catch (Exception e) {
            log.error("Unexpected error while fetching unit-level data", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("An unexpected error occurred.");
            response.setSuccess(false);
            response.setData(Collections.emptyList());
        }

        return response;
    }
	@Override
public	BaseResponse<HttpStatus, List<UnitLevelDataDto>> getrtiAppnDseDashboard(UserDetailsDto u, Integer year,
		Integer quarter) {
    BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = new BaseResponse<>();

    try {
       
        int month = getMonthForQuarter(quarter);
        Integer unit=u.getUnitId();
        Integer circle=u.getCircleId();
    	
        // Calculate the last day of the previous quarter
        LocalDate currentQuarterDate = LocalDate.of(year, month, 1);
        LocalDate previousQuarter = currentQuarterDate.minus(1, IsoFields.QUARTER_YEARS);
        long lastDayOfQuarter = IsoFields.DAY_OF_QUARTER.rangeRefinedBy(previousQuarter).getMaximum();
        LocalDate lastDayInPreviousQuarter = previousQuarter.with(IsoFields.DAY_OF_QUARTER, lastDayOfQuarter);
        java.sql.Date dt = java.sql.Date.valueOf(lastDayInPreviousQuarter);
       // System.out.print("dt"+dt);
        LocalDateTime lastDayWithTime = lastDayInPreviousQuarter.atStartOfDay(); // This gives 00:00:00 time
        //System.out.print("lastDayWithTime"+lastDayWithTime);
        // Convert to java.sql.Timestamp (which includes time)
        Timestamp timestamp = Timestamp.valueOf(lastDayWithTime);
        //System.out.print("timestamp"+timestamp);
        log.info("Fetching unit-level data for Year: {}, Quarter: {}, Date: {}", year, quarter, timestamp);
        
        List<UnitLevelDataDto> unitLevelData=new ArrayList<>(); 
        List<Map<String, Object>> rawData = null;
    	if(u.getUnitId()!=4){
    		if (u.getDesignationId()==12||u.getDesignationId()==9||u.getDesignationId()==10){
    			unit=u.getUnitId();  
    		}
    	}
    	 
    	rawData = rtiApplicationRepository.getrtiAppnDseDashboard(year,quarter,timestamp,unit,circle);              
       
    	log.debug("Raw data retrieved: {}", rawData);
        rawData.forEach(row -> row.forEach((key, value) -> 
       log.info("Key: {}, Value: {}, Type: {}", key, value, (value != null ? value.getClass() : "null")) ));
    	 
    	if (rawData == null || rawData.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("No records found to display");
            response.setData(null);
            return response;
        }
    	
        rawData.forEach(row -> log.info("Row keys: {}", row.keySet()));

        for(int i=0;i<rawData.size();i++) {
        	UnitLevelDataDto dto = new UnitLevelDataDto();
        	dto.setQpending(Integer.parseInt(rawData.get(i).get("pending").toString()));
        	
        	dto.setTotapp(Integer.parseInt(rawData.get(i).get(("appreceived")).toString()));
        	//System.out.print("appreceived "+rawData.get(i).get(("appreceived")).toString());
        	
        	Object appReceivedValue = rawData.get(i).get("appreceived");
        	//log.info("Raw value for appreceived: {}", appReceivedValue);
        	
        	dto.setTotdispo(Integer.parseInt(rawData.get(i).get("disposed").toString()));
        	dto.setTotPending(
        		    Integer.parseInt(rawData.get(i).get("pending").toString()) +
        		    Integer.parseInt(rawData.get(i).get("appreceived").toString()) -
        		    Integer.parseInt(rawData.get(i).get("disposed").toString())
        		);
        	
        	
        	dto.setInfor(Integer.parseInt(rawData.get(i).get("infofur").toString()));
        	dto.setDeemrefus(Integer.parseInt(rawData.get(i).get("deemrefus").toString()));        	
        	       	
        	dto.setUnitName((rawData.get(i).get(("unit_name")).toString()));
        	dto.setRej6(Integer.parseInt(rawData.get(i).get("six").toString()));
        	dto.setUnitId(Integer.parseInt(rawData.get(i).get(("unitId")).toString()));
        	dto.setCircleId(Integer.parseInt(rawData.get(i).get(("circle_id")).toString()));
        	dto.setDivisionId(0);
        	
            unitLevelData.add(dto);
        }
        
//        for (UnitLevelDataDto data : unitLevelData) {
//            for (CircleListForUnitId circleObj : circles) {
//                if (circleObj.getCircleId().equals(data.getCircleId())) {
//                    data.setCircleName(circleObj.getCircleName());
//                    System.out.print("circleName "+data.getCircleName());
//                    break;
//                }
//            }
//            
//            if (data.getCircleId().equals(0)) {
//                data.setCircleName("Unit Office");
//            }
//        }
        
        response.setStatus(HttpStatus.OK);
        response.setMessage("Success");
        response.setData(unitLevelData);
        return response;
        
    } catch (Exception e) {
        log.error("Unexpected error while fetching Circle-level data", e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage("An unexpected error occurred.");
        response.setData(null);
        return response;
    }
}
	@Override
	public BaseResponse<HttpStatus, List<UnitLevelDataDto>> getrtiAppnDivDashboard(
	        UserDetailsDto u, Integer year, Integer quarter) {
	    
	    BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = new BaseResponse<>();
	    
	    try {
	        int month = getMonthForQuarter(quarter);
	        Integer unit = u.getUnitId();
	        Integer circle = u.getCircleId();
	        Integer division = u.getDivisionId();
	        
	        LocalDate currentQuarterDate = LocalDate.of(year, month, 1);
	        LocalDate previousQuarter = currentQuarterDate.minus(1, IsoFields.QUARTER_YEARS);
	        long lastDayOfQuarter = IsoFields.DAY_OF_QUARTER.rangeRefinedBy(previousQuarter).getMaximum();
	        LocalDate lastDayInPreviousQuarter = previousQuarter.with(IsoFields.DAY_OF_QUARTER, lastDayOfQuarter);
	        Timestamp timestamp = Timestamp.valueOf(lastDayInPreviousQuarter.atStartOfDay());
	        
	        log.info("Fetching unit-level data for Year: {}, Quarter: {}, Date: {}", year, quarter, timestamp);
	        
	        List<Map<String, Object>> rawData = null;
	        if (unit != 4) {
	            if (u.getDesignationId() == 12 || u.getDesignationId() == 7||u.getDesignationId() == 9||u.getDesignationId() == 10) {
	                rawData = rtiApplicationRepository.getrtiAppnDivisionUCDashboard(year, quarter, timestamp, unit, circle);
	            } else if (u.getDesignationId() == 5) {
	                rawData = rtiApplicationRepository.getrtiAppnDivisionUCDDashboard(year, quarter, timestamp, unit, circle, division);
	            }
	        } else {
	            rawData = rtiApplicationRepository.getrtiAppnDivisionUCDashboard(year, quarter, timestamp, unit, circle);
	        }
	        
	        if (rawData == null || rawData.isEmpty()) {
	            response.setStatus(HttpStatus.NOT_FOUND);
	            response.setMessage("No records found to display");
	            response.setData(null);
	            return response;
	        }
	        
	        List<UnitLevelDataDto> unitLevelData = new ArrayList<>();
	        for (Map<String, Object> row : rawData) {
	            UnitLevelDataDto dto = new UnitLevelDataDto();
	            dto.setQpending(Integer.parseInt(row.get("pending").toString()));
	            dto.setTotapp(Integer.parseInt(row.get("appreceived").toString()));
	            dto.setTotdispo(Integer.parseInt(row.get("disposed").toString()));
	            dto.setTotPending(dto.getQpending() + dto.getTotapp() - dto.getTotdispo());
	            dto.setInfor(Integer.parseInt(row.get("infofur").toString()));
	            dto.setDeemrefus(Integer.parseInt(row.get("deemrefus").toString()));
	           
	            dto.setUnitName(row.get("unit_name").toString());
	            dto.setRej6(Integer.parseInt(row.get("six").toString()));
	            dto.setUnitId(Integer.parseInt(row.get("unitId").toString()));
	            dto.setCircleId(Integer.parseInt(row.get("circle_id").toString()));
	            dto.setDivisionId(Integer.parseInt(row.get("division_id").toString()));
	           
	            unitLevelData.add(dto);
	        }
	              
         //for unitId !=4 and deg=5 0r 7 we straight away use user unit and circle id ( for assigning names)  when unit =4 we are using rtiar circle id have to check for unit=4 case
	        
//	        for (UnitLevelDataDto data : unitLevelData) {
//	            for (CircleListForUnitId circleObj : circles) {
//	                if (circleObj.getCircleId().equals(data.getCircleId())) {
//	                    data.setCircleName(circleObj.getCircleName());
//	                    break;
//	                }
//	            }
//	            
//	            for (DivisionListForCircleId divisionObj : divisions) {
//	                if (divisionObj.getDivisionId().equals(data.getDivisionId())) {
//	                    data.setDivisionName(divisionObj.getDivisionName());
//	                    break;
//	                }
//	            }
//	            
//	            if (data.getDivisionId().equals(0)) {
//	                data.setDivisionName("Circle Office");
//	            }
//	        }
	        
	        response.setStatus(HttpStatus.OK);
	        response.setMessage("Success");
	        response.setData(unitLevelData);
	        return response;
	        
	    } catch (Exception e) {
	        log.error("Unexpected error while fetching Division-level data", e);
	        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        response.setMessage("An unexpected error occurred.");
	        response.setData(null);
	        return response;
	    }
	}

	   private int getMonthForQuarter(int quarter) {
	        switch (quarter) {
	            case 1: return 2;  // February
	            case 2: return 5;  // May
	            case 3: return 8;  // August
	            case 4: return 11; // November
	            default: throw new IllegalArgumentException("Invalid quarter: " + quarter);
	        }
	    }
}
