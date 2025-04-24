package in.OAndM.services.impl;

import in.OAndM.DTO.CircleListForUnitId;
import in.OAndM.DTO.DivisionListForCircleId;
import in.OAndM.DTO.RtiApplicationDto;
import in.OAndM.DTO.RtiProformaGDto;
import in.OAndM.DTO.UnitLevelDataDto;
import in.OAndM.DTO.UnitLevelRequest;
import in.OAndM.DTO.UserDetailsDto;
import in.OAndM.Entities.RtiProformaG;
import in.OAndM.mappers.RtiProformaGMapper;
import in.OAndM.repositories.RtiProformaGRepository;
import in.OAndM.requests.PaginationRequest;
import in.OAndM.core.BaseResponse;
import in.OAndM.services.RtiProformaGService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
import java.util.stream.Collectors;

@Service
public class RtiProformaGServiceImpl implements RtiProformaGService {
	
	private static final Logger log = LoggerFactory.getLogger(RtiProformaGServiceImpl.class);
    private final RtiProformaGRepository rtiProformaGRepository;
    private final RtiProformaGMapper rtiProformaGMapper;

    @Autowired
    public RtiProformaGServiceImpl(RtiProformaGRepository rtiProformaGRepository, RtiProformaGMapper rtiProformaGMapper) {
        this.rtiProformaGRepository = rtiProformaGRepository;
        this.rtiProformaGMapper = rtiProformaGMapper;
    }

    @Override
    public BaseResponse<HttpStatus, List<RtiProformaGDto>> get() {
    	 //System.out.println("in service impl");
        List<RtiProformaG> proformas = rtiProformaGRepository.findAllByDeleteFlagFalse();
        //System.out.println("proformas "+proformas);
        List<RtiProformaGDto> dtos = rtiProformaGMapper.mapEntityToModel(proformas);
        
        //System.out.println(proformas.get(0).getRejectionSectionStatus());
        BaseResponse<HttpStatus, List<RtiProformaGDto>> response = new BaseResponse<>();
        response.setStatus(HttpStatus.OK);
        response.setMessage("Successfully retrieved all RTI Proforma G applications.");
        response.setData(dtos);
        response.setSuccess(true);
        response.setTotal(dtos.size());
        
        return response;
    }

    
    @Override
    public BaseResponse<HttpStatus, List<RtiProformaGDto>> findAllWithRejectionStatus() {
        // Call the custom repository method to fetch data with rejection status
        List<RtiProformaG> proformas = rtiProformaGRepository.findAllWithRejectionStatus();
        
        // Map the entities to DTOs
        List<RtiProformaGDto> dtos = rtiProformaGMapper.mapEntityToModel(proformas);
        
        // Prepare the BaseResponse object
        BaseResponse<HttpStatus, List<RtiProformaGDto>> response = new BaseResponse<>();
        response.setStatus(HttpStatus.OK);
        response.setMessage("Successfully retrieved RTI Proforma G applications with rejection status.");
        response.setData(dtos);
        response.setSuccess(true);
        response.setTotal(dtos.size());
        
        return response;
    }
    @Override
    public BaseResponse<HttpStatus, RtiProformaGDto> get(Integer id) {
        Optional<RtiProformaG> proforma = rtiProformaGRepository.findById(id);
        BaseResponse<HttpStatus, RtiProformaGDto> response = new BaseResponse<>();
        
        if (proforma.isPresent()) {
            RtiProformaGDto dto = rtiProformaGMapper.mapEntityToModel(proforma.get());
            response.setStatus(HttpStatus.OK);
            response.setMessage("Proforma G application retrieved successfully.");
            response.setData(dto);
            response.setSuccess(true);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("Proforma G application not found.");
            response.setSuccess(false);
        }
        
        return response;
    }

    @Override
    public BaseResponse<HttpStatus, RtiProformaGDto> create(RtiProformaGDto model) {
    	
    	//System.out.println("model "+model);
        RtiProformaG entity = rtiProformaGMapper.mapModelToEntity(model);
        entity.setIsLatest(true);
        entity.setDeleteFlag(false);
       
        entity.setCreatedTime(LocalDateTime.now());
        //System.out.println("entity "+entity);
        RtiProformaG savedProforma = rtiProformaGRepository.save(entity);
        
        BaseResponse<HttpStatus, RtiProformaGDto> response = new BaseResponse<>();
        response.setStatus(HttpStatus.CREATED);
        response.setMessage("Proforma G application created successfully.");
        response.setData(rtiProformaGMapper.mapEntityToModel(savedProforma));
        response.setSuccess(true);
        
        return response;
    }

    @Override
    public BaseResponse<HttpStatus, RtiProformaGDto> update(Integer id, RtiProformaGDto model) {
    	//System.out.println(" id "+id+ " model "+model);
        Optional<RtiProformaG> optionalProforma = rtiProformaGRepository.findByIdAndDeleteFlagFalse(id);
        BaseResponse<HttpStatus, RtiProformaGDto> response = new BaseResponse<>();        
        if (optionalProforma.isPresent()) {
            RtiProformaG entity = optionalProforma.get();            
            // Retain createdDate and isLatest
           // LocalDateTime existingCreatedDate = entity.getCreatedTime(); 
           // Boolean existingIsLatest = entity.getIsLatest();            
            //System.out.println("entity"+entity);
            rtiProformaGMapper.mapModelToEntity(entity, model);  // Update entity with new data         
                     // Reassign the retained fields
            //entity.setCreatedTime(existingCreatedDate);
           // entity.setIsLatest(existingIsLatest);
            entity.setEditedTime(LocalDateTime.now());
            entity.setEditedBy( model.getUser().getUsername());
            RtiProformaG updatedProforma = rtiProformaGRepository.save(entity);
            
            response.setStatus(HttpStatus.OK);
            response.setMessage("Proforma G application updated successfully.");
            response.setData(rtiProformaGMapper.mapEntityToModel(updatedProforma));
            response.setSuccess(true);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("Proforma G application not found.");
            response.setSuccess(false);
        }
        
        return response;
    }

   
    //    @Override
//    public BaseResponse<HttpStatus, RtiProformaGDto> delete(Integer id) {
//        Optional<RtiProformaG> optionalProforma = rtiProformaGRepository.findById(id);
//        BaseResponse<HttpStatus, RtiProformaGDto> response = new BaseResponse<>();
//        
//        if (optionalProforma.isPresent()) {
//            rtiProformaGRepository.delete(optionalProforma.get());
//            response.setStatus(HttpStatus.OK);
//            response.setMessage("Proforma G application deleted successfully.");
//            response.setSuccess(true);
//        } else {
//            response.setStatus(HttpStatus.NOT_FOUND);
//            response.setMessage("Proforma G application not found.");
//            response.setSuccess(false);
//        }
//        
//        return response;
//    }
    
    @Override
    public BaseResponse<HttpStatus, RtiProformaGDto> delete(Integer id, String username) {
        //Optional<RtiProformaG> optionalProforma = rtiProformaGRepository.findById(id);
    	Optional<RtiProformaG> optionalProforma = rtiProformaGRepository.findByIdAndDeleteFlagFalse(id);
    	//System.out.println("optionalProforma "+optionalProforma);
        BaseResponse<HttpStatus, RtiProformaGDto> response = new BaseResponse<>();
        if (!optionalProforma.isPresent()) {
           // return createNotFoundResponse("Application not found.");
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("Proforma G application not found.");
            response.setSuccess(false);
        }
        RtiProformaG prfmG=optionalProforma.get();
        //System.out.println("prfmG "+prfmG);
        prfmG.setDeleteFlag(true); // Soft delete
        prfmG.setDeletedTime(LocalDateTime.now());
        prfmG.setDeletedBy( username);
        rtiProformaGRepository.save(prfmG);
        
      
            response.setStatus(HttpStatus.OK);
            response.setMessage("Proforma G marked deleted successfully.");
            response.setSuccess(true);
        return response;
    }
    @Override
    public BaseResponse<HttpStatus, List<RtiProformaGDto>> get(List<Integer> ids) {
        List<RtiProformaG> proformas = rtiProformaGRepository.findAllById(ids);
        List<RtiProformaGDto> dtos = rtiProformaGMapper.mapEntityToModel(proformas);
        
        BaseResponse<HttpStatus, List<RtiProformaGDto>> response = new BaseResponse<>();
        response.setStatus(HttpStatus.OK);
        response.setMessage("Successfully retrieved RTI Proforma G applications.");
        response.setData(dtos);
        response.setSuccess(true);
        response.setTotal(dtos.size());
        
        return response;
    }

    @Override
    public BaseResponse<HttpStatus, List<RtiProformaGDto>> saveAll(List<RtiProformaGDto> models) {
        List<RtiProformaG> entities = rtiProformaGMapper.mapModelToEntity(models);
        List<RtiProformaG> savedProformas = rtiProformaGRepository.saveAll(entities);
        List<RtiProformaGDto> dtos = rtiProformaGMapper.mapEntityToModel(savedProformas);
        
        BaseResponse<HttpStatus, List<RtiProformaGDto>> response = new BaseResponse<>();
        response.setStatus(HttpStatus.CREATED);
        response.setMessage("Proforma G applications created successfully.");
        response.setData(dtos);
        response.setSuccess(true);
        
        return response;
    }
//    @Override
//    public BaseResponse<HttpStatus, List<UnitLevelDataDto>> getUnitLevelData(UnitLevelRequest rtiar) {
//        // Create a response object
//        BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = new BaseResponse<>();
//        
//        Integer Qtr = rtiar.getQuarter();
//        Integer Year = rtiar.getYear();
//        Integer preYear = rtiar.getPreviousYear();
//        Integer preQtr = rtiar.getPreviousQtr();
//        //Integer unitId = u.getUnitId();
//
//        int year = Year;
//        int quarter = Qtr;
//        int month = 0;
//
//        // Determine the month corresponding to the quarter
//        if (quarter == 1) {
//            month = 2; // February for Q1
//        } else if (quarter == 2) {
//            month = 5; // May for Q2
//        } else if (quarter == 3) {
//            month = 8; // August for Q3
//        } else if (quarter == 4) {
//            month = 11; // November for Q4
//        }
//
//        // Calculate the last day of the previous quarter
//        LocalDate currentQuarterDate = LocalDate.of(year, month, 1);  // First day of the current quarter
//        LocalDate previousQuarter = currentQuarterDate.minus(1, IsoFields.QUARTER_YEARS);  // One quarter before
//        long lastDayOfQuarter = IsoFields.DAY_OF_QUARTER.rangeRefinedBy(previousQuarter).getMaximum(); // Last day of the previous quarter
//        LocalDate lastDayInPreviousQuarter = previousQuarter.with(IsoFields.DAY_OF_QUARTER, lastDayOfQuarter);
//
//        // Convert the calculated date to java.sql.Date
//        java.sql.Date dt = java.sql.Date.valueOf(lastDayInPreviousQuarter);
//        System.out.println("dt"+dt);
//        
//        try {
//            // Log the request details
//            log.info("Fetching unit-level data for Year: {}, Qtr: {}, dt: {}", Year, Qtr,dt);
//
//           
//          
//            // Fetch raw data from the repository
//            List<Map<String, Object>> rawData = rtiProformaGRepository.getUnitGroupedData(Year,Qtr,dt);
//            //rawData.forEach(entry -> log.info("Field: {}, Value: {}, Type: {}", key, value, value.getClass()));
//            System.out.println("rawData"+rawData);
//            // Map raw data to DTOs
//            List<UnitLevelDataDto> unitLevelData = rawData.stream()
//                .map(row -> new UnitLevelDataDto(
//                    (Integer) row.get("unitId"),       // unitId
//                    (String) row.get("unitName"),     // unitName
//                    (BigDecimal) row.get("qpending"),    // qpending
//                    (BigDecimal) row.get("totapp"),      // totapp
//                    (BigDecimal) row.get("totdispo"),    // totdispo
//                    (BigDecimal) row.get("infor"),       // infor
//                    (BigDecimal) row.get("rs1"),         // rs1
//                    (BigDecimal) row.get("rs2"),         // rs2
//                    (BigDecimal) row.get("rs3"),         // rs3
//                    (BigDecimal) row.get("rs4"),         // rs4
//                    (BigDecimal) row.get("rs5"),         // rs5
//                    (BigDecimal) row.get("rs6"),         // rs6
//                    (BigDecimal) row.get("rs7"),         // rs7
//                    (BigDecimal) row.get("rs8"),         // rs8
//                    (BigDecimal) row.get("rs9"),         // rs9
//                    (BigDecimal) row.get("rs10"),        // rs10
//                    (BigDecimal) row.get("rs11"),        // rs11
//                    (BigDecimal) row.get("rs12"),        // rs12
//                    (BigDecimal) row.get("rs13"),        // rs13
//                    (BigDecimal) row.get("rstot"),       // rstot
//                    (BigDecimal) row.get("rs15"),        // rs15
//                    row.get("amount") != null ? ((Float) row.get("amount")).floatValue() : 0.0f
//                ))
//                .collect(Collectors.toList());
//            
//            // Populate the response object with the results
//            response.setStatus(HttpStatus.OK);
//            response.setMessage("Unit-level data retrieved successfully.");
//            response.setData(unitLevelData);
//            response.setSuccess(true);
//        } catch (IllegalArgumentException e) {
//            // Handle input validation exceptions
//            log.error("Validation error: {}", e.getMessage());
//            response.setStatus(HttpStatus.BAD_REQUEST);
//            response.setMessage(e.getMessage());
//            response.setSuccess(false);
//            response.setData(Collections.emptyList());
//        } catch (Exception e) {
//            // Handle any unexpected exceptions
//            log.error("Error while fetching unit-level data", e);
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//            response.setMessage("An unexpected error occurred while retrieving unit-level data.");
//            response.setSuccess(false);
//            response.setData(Collections.emptyList());
//        }
//        
//        return response;
//    }

   
    
   
    private Integer parseInteger(Object value) {
        return value != null ? Integer.parseInt(value.toString()) : 0;
    } 
    
//    // Utility Method
//    private BigDecimal safeGetBigDecimal(Map<String, Object> map, String key) {
//        return map.containsKey(key) && map.get(key) instanceof BigDecimal
//            ? (BigDecimal) map.get(key)
//            : BigDecimal.ZERO;
//    }

    private int getMonthForQuarter(int quarter) {
        switch (quarter) {
            case 1: return 2;  // February
            case 2: return 5;  // May
            case 3: return 8;  // August
            case 4: return 11; // November
            default: throw new IllegalArgumentException("Invalid quarter: " + quarter);
        }
    }

    private Integer safeGetInteger(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value != null) {
            try {
                return Integer.parseInt(value.toString());
            } catch (NumberFormatException e) {
                log.error("Invalid integer format for key {}: {}", key, value);
            }
        }
        return 0; // Default value
    }
	@Override
	public BaseResponse<HttpStatus, List<RtiProformaGDto>> get(PaginationRequest pagination) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse<HttpStatus, List<RtiProformaGDto>> delete(List<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse<HttpStatus, RtiProformaGDto> delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<RtiProformaGDto> findByAppealNo(String appealNo) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	
	@Override
	public BaseResponse<HttpStatus, List<RtiProformaGDto>> getRTIAppealEditList(UserDetailsDto u,
			LocalDate firstDayInPreviousQuarter, LocalDate lastDayInPreviousQuarter) {
		BaseResponse<HttpStatus, List<RtiProformaGDto>> response = new BaseResponse<>();
		
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
        Date fdate = Date.from(firstDayInPreviousQuarter.atStartOfDay(defaultZoneId).toInstant());
        //System.out.println("fdate "+fdate);

        log.info("Fetching EE edit data for quarter desgId: {}, divId: {}, circleId: {}, unitId: {},fdate: {},date: {}",desgId,divId,circleId,unitId,fdate,date);
        List<UnitLevelDataDto> unitLevelData=new ArrayList<>(); 
        List<Map<String, Object>> rawData = rtiProformaGRepository.getRTIAppealEditList(divId,circleId,unitId,fdate,date);
        System.out.println("rawData "+rawData);

       
      	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      	
       List<RtiProformaGDto> rtiEdit=new ArrayList<>(); 
       for(int i=0;i<rawData.size();i++) {
    	   RtiProformaGDto dto = new RtiProformaGDto();  
    	   
    	   dto.setAppealNum(rawData.get(i).get(("appeal_no")).toString());
    		Timestamp appealDateTimestamp = (Timestamp) rawData.get(i).get("appeal_date");
            LocalDate appealDate = appealDateTimestamp.toLocalDateTime().toLocalDate();            
    	   dto.setAppealDate(appealDate);
    	   dto.setNameOfAppellant(rawData.get(i).get(("name_of_appellant")).toString());
    	   dto.setAppellantAddress(rawData.get(i).get(("appellant_address")).toString());
    	   Timestamp appealRecDateTimestamp = (Timestamp) rawData.get(i).get("appeal_receipt_date");
           LocalDate appealRecDate = appealRecDateTimestamp.toLocalDateTime().toLocalDate();      
   	  	   dto.setAppealReceiptDate(appealRecDate);
   	  	   
    	   dto.setPioName(rawData.get(i).get(("pio_name")).toString());
    	   dto.setPioDesignation(rawData.get(i).get(("pio_designation")).toString());
    	   dto.setAppnNum(rawData.get(i).get(("application_no")).toString());
    	   Timestamp appnDateTimestamp = (Timestamp) rawData.get(i).get("application_date");
           LocalDate appnDate = appnDateTimestamp.toLocalDateTime().toLocalDate();
           dto.setAppnDate(appnDate);
           dto.setAppellateAuthorityName(rawData.get(i).get(("name_of_appellate")).toString());    	
    	  dto.setAppellateAuthorityAddre(rawData.get(i).get(("appellate_address")).toString()); 
          Timestamp fDecDateTimestamp = (Timestamp) rawData.get(i).get("appellate_1st_desicion_date");
          if (fDecDateTimestamp != null) {
              dto.setAppellateFirstDecisionDate(fDecDateTimestamp.toLocalDateTime().toLocalDate());
          } else {
              dto.setAppellateFirstDecisionDate(null); // Or set a default value if required
          }
          //LocalDate fDecDate = fDecDateTimestamp.toLocalDateTime().toLocalDate();
    	 // dto.setAppellateFirstDecisionDate(fDecDate);
    	  dto.setAppellateFirstDecisionAllowOrRejec(rawData.get(i).get(("decision")).toString());
    	  dto.setRtiRejectionSection(Optional.ofNullable(rawData.get(i).get("rti_rejection_section")).map(Object::toString).orElse(null));
    	  
    	  Object chargeForFurnish = rawData.get(i).get("charges_collect_forfurnish");
    	  if (chargeForFurnish != null) {
    	      dto.setChargeForInfo(Integer.parseInt(chargeForFurnish.toString()));
    	  } else {
    	      // Handle the case where the value is null, e.g., set to a default value or log an error
    	      dto.setChargeForInfo(0); // Set to a default value (you can adjust based on your requirements)
    	  }
    	 // dto.setChargeForInfo(Integer.parseInt(rawData.get(i).get(("charges_collect_forfurnish")).toString()));
    	  dto.setSecondAppealMade(Optional.ofNullable(rawData.get(i).get("second_appeal_made_19_3")).map(Object::toString).orElse(null));
    	 // dto.setSecondAppealMade(rawData.get(i).get(("second_appeal_made_19_3")).toString());
    	  dto.setRemarks(rawData.get(i).get(("remarks")).toString());
        dto.setUnit(Integer.parseInt(rawData.get(i).get("unit_id").toString()));
        dto.setCircle(Integer.parseInt(rawData.get(i).get("circle_id").toString()));
        dto.setDivision(Integer.parseInt(rawData.get(i).get("division_id").toString()));
        dto.setUnitName(rawData.get(i).get(("unit_name")).toString());
        dto.setProGId(Integer.parseInt(rawData.get(i).get(("pro_g_id")).toString()));
        
      
       
           rtiEdit.add(dto);
       }
       
       response.setStatus(HttpStatus.OK);
       response.setMessage("Rti Appeal edit data List retrieved successfully.");
       response.setData(rtiEdit);
       response.setSuccess(true);
   } catch (IllegalArgumentException e) {
       log.error("Validation error: {}", e.getMessage());
       response.setStatus(HttpStatus.BAD_REQUEST);
       response.setMessage(e.getMessage());
       response.setSuccess(false);
       response.setData(Collections.emptyList());
   } catch (Exception e) {
       log.error("Unexpected error while fetching Rti Appeal edit data List", e);
       response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
       response.setMessage("An unexpected error occurred.");
       response.setSuccess(false);
       response.setData(Collections.emptyList());
   }

   return response;
	}   
   @Override
	public BaseResponse<HttpStatus, List<RtiProformaGDto>> getAppealYrQtrEEReport(UserDetailsDto u,Integer year, Integer Quarter) {
		{ 		BaseResponse<HttpStatus, List<RtiProformaGDto>> response = new BaseResponse<>();
		
		  try {
		Integer unit = u.getUnitId();
       Integer circle = u.getCircleId();
       Integer div = u.getDivisionId();
       Integer desg = u.getDesignationId();

       // Adjust for special user scenario
       if ("Kavit070381".equals(u.getUsername()) && unit == 9815 && circle == 21588) {
           desg = 5;
       }


       List<Map<String, Object>> rawData;
       List<UnitLevelDataDto> unitLevelData=new ArrayList<>(); 
   	if(Quarter==5)
   	{
   		rawData = rtiProformaGRepository.getAppealYrEEReport(  div,  circle,  unit,  year );
   		
   	}
   	else{
   		rawData = rtiProformaGRepository.getAppealYrQtrEEReport(  div,  circle,  unit,  year, Quarter );	
   	}
       // Convert LocalDate to Date
       ZoneId defaultZoneId = ZoneId.systemDefault();
     //  Date date = Date.from(lastDayInPreviousQuarter.atStartOfDay(defaultZoneId).toInstant());
    //   Date fdate = Date.from(firstDayInPreviousQuarter.atStartOfDay(defaultZoneId).toInstant());
       //System.out.println("fdate "+fdate);

       log.info("Fetching EE edit data for quarter desgId: {}, divId: {}, circleId: {}, unitId: {},year: {},Qtr: {}",desg,div,circle,unit,year,Quarter);
       System.out.println("rawData "+rawData);

      
     	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
     	
      List<RtiProformaGDto> rtiEdit=new ArrayList<>(); 
      for(int i=0;i<rawData.size();i++) {
   	   RtiProformaGDto dto = new RtiProformaGDto();  
   	   
   	   dto.setAppealNum(rawData.get(i).get(("appeal_no")).toString());
   		Timestamp appealDateTimestamp = (Timestamp) rawData.get(i).get("appeal_date");
           LocalDate appealDate = appealDateTimestamp.toLocalDateTime().toLocalDate();            
   	   dto.setAppealDate(appealDate);
   	   dto.setNameOfAppellant(rawData.get(i).get(("name_of_appellant")).toString());
   	   dto.setAppellantAddress(rawData.get(i).get(("appellant_address")).toString());
   	   Timestamp appealRecDateTimestamp = (Timestamp) rawData.get(i).get("appeal_receipt_date");
          LocalDate appealRecDate = appealRecDateTimestamp.toLocalDateTime().toLocalDate();      
  	  	   dto.setAppealReceiptDate(appealRecDate);
  	  	   
   	   dto.setPioName(rawData.get(i).get(("pio_name")).toString());
   	   dto.setPioDesignation(rawData.get(i).get(("pio_designation")).toString());
   	   dto.setAppnNum(rawData.get(i).get(("application_no")).toString());
   	   Timestamp appnDateTimestamp = (Timestamp) rawData.get(i).get("application_date");
          LocalDate appnDate = appnDateTimestamp.toLocalDateTime().toLocalDate();
          dto.setAppnDate(appnDate);
          dto.setAppellateAuthorityName(rawData.get(i).get(("name_of_appellate")).toString());    	
   	  dto.setAppellateAuthorityAddre(rawData.get(i).get(("appellate_address")).toString()); 
         Timestamp fDecDateTimestamp = (Timestamp) rawData.get(i).get("appellate_1st_desicion_date");
         if (fDecDateTimestamp != null) {
             dto.setAppellateFirstDecisionDate(fDecDateTimestamp.toLocalDateTime().toLocalDate());
         } else {
             dto.setAppellateFirstDecisionDate(null); // Or set a default value if required
         }
         //LocalDate fDecDate = fDecDateTimestamp.toLocalDateTime().toLocalDate();
   	 // dto.setAppellateFirstDecisionDate(fDecDate);
   	  dto.setAppellateFirstDecisionAllowOrRejec(rawData.get(i).get(("decision")).toString());
   	  dto.setRtiRejectionSection(Optional.ofNullable(rawData.get(i).get("rti_rejection_section")).map(Object::toString).orElse(null));
   	  
   	  Object chargeForFurnish = rawData.get(i).get("charges_collect_forfurnish");
   	  if (chargeForFurnish != null) {
   	      dto.setChargeForInfo(Integer.parseInt(chargeForFurnish.toString()));
   	  } else {
   	      // Handle the case where the value is null, e.g., set to a default value or log an error
   	      dto.setChargeForInfo(0); // Set to a default value (you can adjust based on your requirements)
   	  }
   	 // dto.setChargeForInfo(Integer.parseInt(rawData.get(i).get(("charges_collect_forfurnish")).toString()));
   	  dto.setSecondAppealMade(Optional.ofNullable(rawData.get(i).get("second_appeal_made_19_3")).map(Object::toString).orElse(null));
   	 // dto.setSecondAppealMade(rawData.get(i).get(("second_appeal_made_19_3")).toString());
   	  dto.setRemarks(rawData.get(i).get(("remarks")).toString());
       dto.setUnit(Integer.parseInt(rawData.get(i).get("unit_id").toString()));
       dto.setCircle(Integer.parseInt(rawData.get(i).get("circle_id").toString()));
       dto.setDivision(Integer.parseInt(rawData.get(i).get("division_id").toString()));
       dto.setUnitName(rawData.get(i).get(("unit_name")).toString());
       dto.setProGId(Integer.parseInt(rawData.get(i).get(("pro_g_id")).toString()));
       
     
      
          rtiEdit.add(dto);
      }
      
      response.setStatus(HttpStatus.OK);
      response.setMessage("Rti Appeal edit data List retrieved successfully.");
      response.setData(rtiEdit);
      response.setSuccess(true);
  } catch (IllegalArgumentException e) {
      log.error("Validation error: {}", e.getMessage());
      response.setStatus(HttpStatus.BAD_REQUEST);
      response.setMessage(e.getMessage());
      response.setSuccess(false);
      response.setData(Collections.emptyList());
  } catch (Exception e) {
      log.error("Unexpected error while fetching Rti Appeal edit data List", e);
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      response.setMessage("An unexpected error occurred.");
      response.setSuccess(false);
      response.setData(Collections.emptyList());
  }

  return response;


}

   }
   
   @Override
   public BaseResponse<HttpStatus, List<UnitLevelDataDto>> getUnitLevelData(UserDetailsDto user, Integer year,
			Integer quarter) {
       BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = new BaseResponse<>();

       try {
           
           int month = getMonthForQuarter(quarter);
           
           // Calculate the last day of the previous quarter
           LocalDate currentQuarterDate = LocalDate.of(year, month, 1);
           LocalDate previousQuarter = currentQuarterDate.minus(1, IsoFields.QUARTER_YEARS);
           long lastDayOfQuarter = IsoFields.DAY_OF_QUARTER.rangeRefinedBy(previousQuarter).getMaximum();
           LocalDate lastDayInPreviousQuarter = previousQuarter.with(IsoFields.DAY_OF_QUARTER, lastDayOfQuarter);
           java.sql.Date dt = java.sql.Date.valueOf(lastDayInPreviousQuarter);
           
           LocalDateTime lastDayWithTime = lastDayInPreviousQuarter.atStartOfDay(); // This gives 00:00:00 time

           // Convert to java.sql.Timestamp (which includes time)
           Timestamp timestamp = Timestamp.valueOf(lastDayWithTime);

           log.info("Fetching unit-level data for Year: {}, Quarter: {}, Date: {}", year, quarter, timestamp);
           List<UnitLevelDataDto> unitLevelData=new ArrayList<>(); 
           List<Map<String, Object>> rawData = rtiProformaGRepository.getUnitGroupedData(year, quarter, timestamp);
           log.debug("Raw data retrieved: {}", rawData);
           rawData.forEach(row -> row.forEach((key, value) -> 
          log.info("Key: {}, Value: {}, Type: {}", key, value, (value != null ? value.getClass() : "null"))
       ));
           
           for(int i=0;i<rawData.size();i++) {
           	UnitLevelDataDto dto = new UnitLevelDataDto();
           	dto.setUnitId(Integer.parseInt(rawData.get(i).get(("unitId")).toString()));
           	dto.setUnitName((rawData.get(i).get(("unitName")).toString()));
           	dto.setQpending(Integer.parseInt(rawData.get(i).get("qpending").toString()));
           	dto.setTotapp(Integer.parseInt(rawData.get(i).get(("totapp")).toString()));            	
           	dto.setTotdispo(Integer.parseInt(rawData.get(i).get("totdispo").toString()));
           	dto.setTotPending(
           		    Integer.parseInt(rawData.get(i).get("qpending").toString()) +
           		    Integer.parseInt(rawData.get(i).get("totapp").toString()) -
           		    Integer.parseInt(rawData.get(i).get("totdispo").toString())
           		);
           	dto.setInfor(Integer.parseInt(rawData.get(i).get("infor").toString()));
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
           	dto.setRs15(Integer.parseInt(rawData.get(i).get("rs15").toString()));
           	
           	//dto.setAmount(Integer.parseInt(rawData.get(i).get("amount").toString()));
           	Object amountValue = rawData.get(i).get("amount");
               if (amountValue != null) {
                   try {
                       dto.setAmount(Integer.parseInt(amountValue.toString()));
                   } catch (NumberFormatException e) {
                       log.error("Invalid value for amount: {}", amountValue);
                       dto.setAmount(0); // Default to 0 in case of invalid number format
                   }
               } else {
                   dto.setAmount(0); // Default to 0 if "amount" is null
               }
               dto.setRej6(0);
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
   
   @Override
   public BaseResponse<HttpStatus, List<UnitLevelDataDto>> getCircleLevelData(UserDetailsDto u, Integer year,
			Integer quarter, List<CircleListForUnitId> circles, 
	        List<DivisionListForCircleId> divisions) {
       BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = new BaseResponse<>();

       try {
         
           int month = getMonthForQuarter(quarter);
       
           Integer unit=u.getUnitId();
			/*
			 * if(u.getUnitId()!=4){ if (u.getDesignationId()==12){ unitId=u.getUnitId(); }}
			 */
           
           // Calculate the last day of the previous quarter
           LocalDate currentQuarterDate = LocalDate.of(year, month, 1);
           LocalDate previousQuarter = currentQuarterDate.minus(1, IsoFields.QUARTER_YEARS);
           long lastDayOfQuarter = IsoFields.DAY_OF_QUARTER.rangeRefinedBy(previousQuarter).getMaximum();
           LocalDate lastDayInPreviousQuarter = previousQuarter.with(IsoFields.DAY_OF_QUARTER, lastDayOfQuarter);
           java.sql.Date dt = java.sql.Date.valueOf(lastDayInPreviousQuarter);
           
           LocalDateTime lastDayWithTime = lastDayInPreviousQuarter.atStartOfDay(); // This gives 00:00:00 time

           // Convert to java.sql.Timestamp (which includes time)
           Timestamp timestamp = Timestamp.valueOf(lastDayWithTime);
           
           List<UnitLevelDataDto> unitLevelData=new ArrayList<>(); 
           List<Map<String, Object>> rawData = null;
           
           if(u.getUnitId()!=4){
       		if (u.getDesignationId()==12||u.getDesignationId()==9||u.getDesignationId()==10){
       			unit=u.getUnitId();  
       		}
       	}
           log.info("Fetching unit-level data for Year: {}, Quarter: {}, Date: {},unitId: {}", year, quarter, timestamp,unit);
          
            rawData = rtiProformaGRepository.getCircleGroupedData(year, quarter, timestamp,unit);
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
           	dto.setUnitId(Integer.parseInt(rawData.get(i).get(("unitId")).toString()));
           	dto.setUnitName((rawData.get(i).get(("unitName")).toString()));
        	dto.setQpending(Integer.parseInt(rawData.get(i).get("qpending").toString()));
           	dto.setTotapp(Integer.parseInt(rawData.get(i).get(("totapp")).toString()));           
           	dto.setTotdispo(Integer.parseInt(rawData.get(i).get("totdispo").toString()));
           	dto.setTotPending(
           		    Integer.parseInt(rawData.get(i).get("qpending").toString()) +
           		    Integer.parseInt(rawData.get(i).get("totapp").toString()) -
           		    Integer.parseInt(rawData.get(i).get("totdispo").toString())
           		);
           	dto.setInfor(Integer.parseInt(rawData.get(i).get("infor").toString()));
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
           	dto.setRs15(Integer.parseInt(rawData.get(i).get("rs15").toString()));
           	
           	//dto.setAmount(Integer.parseInt(rawData.get(i).get("amount").toString()));
           	Object amountValue = rawData.get(i).get("amount");
               if (amountValue != null) {
                   try {
                       dto.setAmount(Integer.parseInt(amountValue.toString()));
                   } catch (NumberFormatException e) {
                       log.error("Invalid value for amount: {}", amountValue);
                       dto.setAmount(0); // Default to 0 in case of invalid number format
                   }
               } else {
                   dto.setAmount(0); // Default to 0 if "amount" is null
               }
              
              	dto.setCircleId(Integer.parseInt(rawData.get(i).get("circleId").toString()));
              	 dto.setRej6(0);
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
	public BaseResponse<HttpStatus, List<UnitLevelDataDto>> getrtiDivisionAppealConsolidatedProformaG(
	        UserDetailsDto u, Integer year, Integer quarter, List<CircleListForUnitId> circles, 
	        List<DivisionListForCircleId> divisions) {
	    
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
	                rawData = rtiProformaGRepository.getrtiAppealDivisionUCConsolidatedPrfmG(year, quarter, timestamp, unit, circle);
	            } else if (u.getDesignationId() == 5) {
	                rawData = rtiProformaGRepository.getrtiAppealDivisionUCDConsolidatedPrfmG(year, quarter, timestamp, unit, circle, division);
	            }
	        } else {
	            rawData = rtiProformaGRepository.getrtiAppealDivisionUCConsolidatedPrfmG(year, quarter, timestamp, unit, circle);
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
	            dto.setUnitId(Integer.parseInt(row.get("unitId").toString()));
	            dto.setUnitName(row.get("unitName").toString());
	           // dto.setUnitName(row.get("unit_name").toString());
	            dto.setQpending(Integer.parseInt(row.get("qpending").toString()));
	            dto.setTotapp(Integer.parseInt(row.get("totapp").toString()));
	            dto.setTotdispo(Integer.parseInt(row.get("totdispo").toString()));
	            dto.setTotPending(dto.getQpending() + dto.getTotapp() - dto.getTotdispo());
	            dto.setInfor(Integer.parseInt(row.get("infor").toString()));
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
	            dto.setRs15(Integer.parseInt(row.get("rs15").toString()));
	            dto.setAmount(row.get("amount") != null ? Integer.parseInt(row.get("amount").toString()) : 0);
	            dto.setCircleId(Integer.parseInt(row.get("circle_id").toString()));
	            dto.setDivisionId(Integer.parseInt(row.get("division_id").toString()));
	            dto.setRej6(0);
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

   
   }

