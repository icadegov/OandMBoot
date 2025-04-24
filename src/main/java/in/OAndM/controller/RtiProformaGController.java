package in.OAndM.controller;

import in.OAndM.core.BaseController;
import in.OAndM.Entities.RtiProformaG;
import in.OAndM.Entities.RtiRejectionStatus;
import in.OAndM.DTO.CircleListForUnitId;
import in.OAndM.DTO.DivisionListForCircleId;
import in.OAndM.DTO.RtiApplicationDto;
import in.OAndM.DTO.RtiProformaGDto;
import in.OAndM.DTO.UnitLevelDataDto;
import in.OAndM.DTO.UnitLevelRequest;
import in.OAndM.DTO.UserDetailsDto;
import in.OAndM.core.BaseResponse;
import in.OAndM.services.RtiProformaGService;
import in.OAndM.services.RtiRejectionStatusService;

import jakarta.validation.Valid;

import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.System;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/oandm/rti/prfmG")

public class RtiProformaGController extends BaseController<RtiProformaG, RtiProformaGDto, Integer> {
	
	 private static final Logger logger = LoggerFactory.getLogger(RTIController.class);

    private final RtiProformaGService rtiProformaGService;
    private final RtiRejectionStatusService rtiRejectionStatusService;

    public RtiProformaGController(RtiProformaGService rtiProformaGService,RtiRejectionStatusService rtiRejectionStatusService) {
        this.rtiProformaGService = rtiProformaGService;
        this.rtiRejectionStatusService=rtiRejectionStatusService;
    }

    // Retrieve all RtiProformaG entries
    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse<HttpStatus, List<RtiProformaGDto>>> getAll() {
   	 //System.out.println("in controller");
        BaseResponse<HttpStatus, List<RtiProformaGDto>> response = rtiProformaGService.get();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/getGYrQtrEEReport")
    public ResponseEntity<BaseResponse<HttpStatus, List<RtiProformaGDto>>> getAppealYrQtrEEReport(@Valid @RequestBody RtiProformaGDto rtiProformaGDto ) {
    	if (rtiProformaGDto.getUser() == null) {
    	    logger.error("User details are null in the request payload: {}", rtiProformaGDto);
    	    throw new IllegalArgumentException("User details are null");
    	}
    	 UserDetailsDto	user = rtiProformaGDto.getUser();
         java.lang.System.out.println("Received user details: " + user);
    	if (user.getUnitId() == null) {
    	    logger.error("Unit is null in user details: {}", rtiProformaGDto.getUser());
    	    throw new IllegalArgumentException("Unit is null");
    	}
    	// Extract user details
    	Integer year = rtiProformaGDto.getYear();
        Integer quarter =  rtiProformaGDto.getQuarter();
       
        java.lang.System.out.println("Received RtiProformaGDto details: " + rtiProformaGDto.getYear() + rtiProformaGDto.getQuarter());
			// String username = user != null ? user.getUsername() : "Unknown";

		        //java.lang.System.out.println("Received request from user: " + user);
		       // logger.info("Received request from user: {}", user);
		
       
		        BaseResponse<HttpStatus, List<RtiProformaGDto>> response = rtiProformaGService.getAppealYrQtrEEReport(user,year,quarter);
		        return new ResponseEntity<>(response, HttpStatus.OK);
       // return ResponseEntity.status(HttpStatus.CREATED).body(rtiApplicationService.create(rtiApplicationDto));
    }
    
    
    // Retrieve an RtiProformaG entry by ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<BaseResponse<HttpStatus, RtiProformaGDto>> getById(@PathVariable Integer id) {
        BaseResponse<HttpStatus, RtiProformaGDto> response = rtiProformaGService.get(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Create a new RtiProformaG entry
    @PostMapping("/entry")
    public ResponseEntity<BaseResponse<HttpStatus, RtiProformaGDto>> create(@Valid @RequestBody RtiProformaGDto rtiProformaGDto) {
    	
    	// Extract user details
        UserDetailsDto user = rtiProformaGDto.getUser();
        String username = user != null ? user.getUsername() : "Unknown";

       // java.lang.System.out.println("Received request from user: " + user);
        logger.info("Received request from user: {}", user);
        
        BaseResponse<HttpStatus, RtiProformaGDto> response = rtiProformaGService.create(rtiProformaGDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update an RtiProformaG entry by ID
    @PutMapping("/updateById/{id}")
    public ResponseEntity<BaseResponse<HttpStatus, RtiProformaGDto>> update(@PathVariable Integer id, @Valid @RequestBody RtiProformaGDto rtiProformaGDto) {
        BaseResponse<HttpStatus, RtiProformaGDto> response = rtiProformaGService.update(id, rtiProformaGDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete an RtiProformaG entry by ID
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<BaseResponse<HttpStatus, RtiProformaGDto>> deleteProformaG(@PathVariable Integer id, @RequestParam String username, @RequestHeader(value = "Authorization", required = false) String token) {
    	// java.lang.System.out.println("Received Token: " + token);
    	//java.lang.System.out.println("Received delete request from user: " + username);
        logger.info("Received delete request from user: {}", username);
        logger.info("Authorization Token: {}", token); // Log token to verify it's received
        if (token == null) {
            logger.error("Authorization token is missing");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        
        BaseResponse<HttpStatus, RtiProformaGDto> response = rtiProformaGService.delete(id,username);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response); // Return HTTP 200 for successful deletion
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Return HTTP 404 if not found
        }
    }
    
    // Optional: Batch Delete based on a list of IDs
    @DeleteMapping("/deleteByIds")
    public ResponseEntity<BaseResponse<HttpStatus, List<RtiProformaGDto>>> deleteByIds(@RequestBody List<Integer> ids) {
        BaseResponse<HttpStatus, List<RtiProformaGDto>> response = rtiProformaGService.delete(ids);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
   
    
    
    @GetMapping("/rejSections")
    public ResponseEntity<BaseResponse<HttpStatus, List<RtiRejectionStatus>>> findAllByDeleteFlagFalse() {
    	BaseResponse<HttpStatus, List<RtiRejectionStatus>> response = rtiRejectionStatusService.findAllByDeleteFalgFalse();
        return new ResponseEntity<>(response, HttpStatus.OK);
    	
    }
    
    @PostMapping("/getEditList")
    public ResponseEntity<BaseResponse<HttpStatus, List<RtiProformaGDto>>> getAllEditApplications(@Valid @RequestBody UserDetailsDto user) {
    LocalDate myLocal = LocalDate.now();
    java.lang.System.out.println("Received user details: " + user);
	LocalDate previousQuarter = myLocal.minus(1, IsoFields.QUARTER_YEARS);
	long lastDayOfQuarter = IsoFields.DAY_OF_QUARTER.rangeRefinedBy(previousQuarter).getMaximum();
	LocalDate lastDayInPreviousQuarter = previousQuarter.with(IsoFields.DAY_OF_QUARTER, lastDayOfQuarter);
	 java.lang.System.out.println("last "+lastDayInPreviousQuarter);

	long firstDayOfpreQuarter = IsoFields.DAY_OF_QUARTER.rangeRefinedBy(previousQuarter).getMinimum();
	LocalDate firstDayInPreviousQuarter = previousQuarter.with(IsoFields.DAY_OF_QUARTER, firstDayOfpreQuarter);
	// System.out.println("firstDayInPreviousQuarter"+firstDayInPreviousQuarter);
	//UserDetailsDto user;
	//User u = (User) session.getAttribute("userObj");

	BaseResponse<HttpStatus,  List<RtiProformaGDto>> response = rtiProformaGService.getRTIAppealEditList(user, firstDayInPreviousQuarter, lastDayInPreviousQuarter);
    
	
	
	  // Return the response with HTTP status
    return new ResponseEntity<>(response, HttpStatus.OK);

    }
    //    @GetMapping("/units")
//    public ResponseEntity<List<Object[]>> getUnitData() {
//        return ResponseEntity.ok(rtiProformaGService.getUnitLevelData());
//    }
//
//    @GetMapping("/circles/{unitId}")
//    public ResponseEntity<List<Object[]>> getCircleData(@PathVariable Integer unitId) {
//        return ResponseEntity.ok(rtiProformaGService.getCircleLevelData(unitId));
//    }
//
//    @GetMapping("/divisions/{circleId}")
//    public ResponseEntity<List<Object[]>> getDivisionData(@PathVariable Integer circleId) {
//        return ResponseEntity.ok(rtiProformaGService.getDivisionLevelData(circleId));
//    }
    
    @PostMapping("/unitConsolidated")
    public ResponseEntity<BaseResponse<HttpStatus, List<UnitLevelDataDto>>> getUnitLevelData(
    		@RequestBody RtiProformaGDto rtiProformaGDto) {
        // Validate and process the incoming data
       
    	Integer year = rtiProformaGDto.getYear();
        Integer quarter =  rtiProformaGDto.getQuarter();
        java.lang.System.out.println("Received rtiProformaGDto details: " + rtiProformaGDto.getYear() + rtiProformaGDto.getQuarter());
        if (rtiProformaGDto.getUser() == null) {
    	    logger.error("User details are null in the request payload: {}", rtiProformaGDto);
    	    throw new IllegalArgumentException("User details are null");
    	}
    	 UserDetailsDto	user = rtiProformaGDto.getUser();
    	 java.lang. System.out.println("Received user details: " + user);
            

        // Call the service layer to fetch data
        BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = rtiProformaGService.getUnitLevelData(user,year,quarter);

        // Return the response with HTTP status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    @PostMapping("/circleConsolidated")
    public ResponseEntity<BaseResponse<HttpStatus, List<UnitLevelDataDto>>> getCircleLevelData(
    		@RequestBody RtiProformaGDto rtiProformaGDto) {
    	java.lang.System.out.println("Received circleConsolidated rtiProformaGDto details: ");
        // Validate and process the incoming data
    	Integer year = rtiProformaGDto.getYear();
        Integer quarter =  rtiProformaGDto.getQuarter();
        Integer clickedUnitId=0;
        if ( rtiProformaGDto.getSelectedUnitId()!= null) {
        	clickedUnitId  =  rtiProformaGDto.getSelectedUnitId();
        	java.lang.System.out.println("Received clickedUnitId1 : " + clickedUnitId);
        rtiProformaGDto.getUser().setUnitId(clickedUnitId);
        java.lang.System.out.println("Received clickedUnitId2 : " + rtiProformaGDto.getUser().getUnitId());
        }
        java.lang.System.out.println("Received circleConsolidated rtiProformaGDto details: " + rtiProformaGDto.getYear() + rtiProformaGDto.getQuarter());
        if (rtiProformaGDto.getUser() == null) {
    	    logger.error("User details are null in the request payload: {}", rtiProformaGDto);
    	    throw new IllegalArgumentException("User details are null");
    	}
    	 UserDetailsDto	user = rtiProformaGDto.getUser();
    	 List<CircleListForUnitId> circles =rtiProformaGDto.getCircles();
    	 List<DivisionListForCircleId> divisions =rtiProformaGDto.getDivisions();
    	 java.lang.System.out.println("Received user details: " + user);
    	 java.lang.System.out.println("Received circles details: " + circles);
    	 java.lang.System.out.println("Received division details: " + divisions);	
    	 
        // Call the service layer to fetch data
        BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = rtiProformaGService.getCircleLevelData(user,year,quarter,circles,divisions);

        // Return the response with HTTP status
        return new ResponseEntity<>(response, HttpStatus.OK);
        
        
    }



@PostMapping("/divisionConsolidated")
public ResponseEntity<BaseResponse<HttpStatus, List<UnitLevelDataDto>>> getDivisionLevelData(
		@RequestBody RtiProformaGDto rtiProformaGDto ) {
	//System.out.println("Received divisionConsolidated rtiApplicationDto details: ");
    // Validate and process the incoming data
	Integer year = rtiProformaGDto.getYear();
    Integer quarter =  rtiProformaGDto.getQuarter();
    Integer clickedCircleId=0;
    Integer clickedUnitId=0;
    if ( rtiProformaGDto.getSelectedUnitId()!= null) {
    	clickedUnitId  =  rtiProformaGDto.getSelectedUnitId();
    	java.lang.System.out.println("Received clickedUnitId : " + clickedUnitId);
    rtiProformaGDto.getUser().setUnitId(clickedUnitId);
    }
    
    if ( rtiProformaGDto.getSelectedCircleId()!= null) {
    	clickedCircleId  =  rtiProformaGDto.getSelectedCircleId();
    	java.lang.System.out.println("Received clickedCircleId : " + clickedCircleId);
    rtiProformaGDto.getUser().setCircleId(clickedCircleId);
    }
   // System.out.println("Received divisionConsolidated rtiApplicationDto details: " + rtiApplicationDto.getYear() + rtiApplicationDto.getQuarter());
    if (rtiProformaGDto.getUser() == null) {
	    logger.error("User details are null in the request payload: {}", rtiProformaGDto);
	    throw new IllegalArgumentException("User details are null");
	}
	 UserDetailsDto	user = rtiProformaGDto.getUser();
	 List<CircleListForUnitId> circles =rtiProformaGDto.getCircles();
	 List<DivisionListForCircleId> divisions =rtiProformaGDto.getDivisions();
	 
    // System.out.println("Received user details: " + user);
	 java.lang.System.out.println("Received user details: " + user);
	 java.lang.System.out.println("Received circles details: " + circles);
	 java.lang.System.out.println("Received division details: " + divisions);
     
     

    // Call the service layer to fetch data  getrtiAppnConsolidatedProformaC
    BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = rtiProformaGService.getrtiDivisionAppealConsolidatedProformaG(user,year,quarter,circles,divisions);
    java.lang.System.out.println("response " + response);
    // Return the response with HTTP status
    return new ResponseEntity<>(response, HttpStatus.OK);
}

}
