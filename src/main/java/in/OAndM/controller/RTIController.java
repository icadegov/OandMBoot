package in.OAndM.controller;

import in.OAndM.core.BaseController;
import in.OAndM.Entities.RTIApplication;
import in.OAndM.DTO.CircleListForUnitId;
import in.OAndM.DTO.DivisionListForCircleId;
import in.OAndM.DTO.RtiApplicationDto;
import in.OAndM.DTO.RtiProformaGDto;
import in.OAndM.DTO.UnitLevelDataDto;
import in.OAndM.DTO.UnitLevelRequest;
import in.OAndM.DTO.UserDetailsDto;
import in.OAndM.core.BaseResponse; // Assuming you have this response in your project
import in.OAndM.services.RTIApplicationService;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.List;

@RestController
//for server
@RequestMapping("/oandm/rti/app") 
//@RequestMapping("/oandm/oandm/rti/app")

//@CrossOrigin(origins = "http://localhost:3002")
public class RTIController extends BaseController<RTIApplication, RtiApplicationDto, Integer> {
	
	private static final Logger logger = LoggerFactory.getLogger(RTIController.class);

    private final RTIApplicationService rtiApplicationService;

    public RTIController(RTIApplicationService rtiApplicationService) {
        this.rtiApplicationService = rtiApplicationService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse<HttpStatus, List<RtiApplicationDto>>> getAllApplications() {
    	return ResponseEntity.ok(rtiApplicationService.get());
    }

    @PostMapping("/getEditList")
    public ResponseEntity<BaseResponse<HttpStatus, List<RtiApplicationDto>>> getAllEditApplications(@Valid @RequestBody UserDetailsDto user) {
    LocalDate myLocal = LocalDate.now();
    System.out.println("Received user details: " + user);
	LocalDate previousQuarter = myLocal.minus(2, IsoFields.QUARTER_YEARS);
	long lastDayOfQuarter = IsoFields.DAY_OF_QUARTER.rangeRefinedBy(previousQuarter).getMaximum();
	LocalDate lastDayInPreviousQuarter = previousQuarter.with(IsoFields.DAY_OF_QUARTER, lastDayOfQuarter);
	 System.out.println("last "+lastDayInPreviousQuarter);

	long firstDayOfpreQuarter = IsoFields.DAY_OF_QUARTER.rangeRefinedBy(previousQuarter).getMinimum();
	LocalDate firstDayInPreviousQuarter = previousQuarter.with(IsoFields.DAY_OF_QUARTER, firstDayOfpreQuarter);
	// System.out.println("firstDayInPreviousQuarter"+firstDayInPreviousQuarter);
	//UserDetailsDto user;
	//User u = (User) session.getAttribute("userObj");

	BaseResponse<HttpStatus,  List<RtiApplicationDto>> response = rtiApplicationService.getRTIAppnRegisterEntryListEE(user, firstDayInPreviousQuarter, lastDayInPreviousQuarter);
    
		
	  // Return the response with HTTP status
    return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @PostMapping("/getCYrQtrEEReport")
    public ResponseEntity<BaseResponse<HttpStatus, List<RtiApplicationDto>>> getAppnYrQtrEEReport(@Valid @RequestBody RtiApplicationDto rtiApplicationDto ) {
    	if (rtiApplicationDto.getUser() == null) {
    	    logger.error("User details are null in the request payload: {}", rtiApplicationDto);
    	    throw new IllegalArgumentException("User details are null");
    	}
    	 UserDetailsDto	user = rtiApplicationDto.getUser();
         System.out.println("Received user details: " + user);
    	if (user.getUnitId() == null) {
    	    logger.error("Unit is null in user details: {}", rtiApplicationDto.getUser());
    	    throw new IllegalArgumentException("Unit is null");
    	}
    	// Extract user details
    	Integer year = rtiApplicationDto.getYear();
        Integer quarter =  rtiApplicationDto.getQuarter();
       
        System.out.println("Received rtiApplicationDto details: " + rtiApplicationDto.getYear() + rtiApplicationDto.getQuarter());
			// String username = user != null ? user.getUsername() : "Unknown";

		        //java.lang.System.out.println("Received request from user: " + user);
		       // logger.info("Received request from user: {}", user);
		
       
		        BaseResponse<HttpStatus, List<RtiApplicationDto>> response = rtiApplicationService.getAppnYrQtrEEReport(user,year,quarter);
		        return new ResponseEntity<>(response, HttpStatus.OK);
       // return ResponseEntity.status(HttpStatus.CREATED).body(rtiApplicationService.create(rtiApplicationDto));
    }
    
    @PostMapping("/unitConsolidated")
    public ResponseEntity<BaseResponse<HttpStatus, List<UnitLevelDataDto>>> getUnitLevelData(
    		@RequestBody RtiApplicationDto rtiApplicationDto ) {
        // Validate and process the incoming data
    	Integer year = rtiApplicationDto.getYear();
        Integer quarter =  rtiApplicationDto.getQuarter();
        System.out.println("Received rtiApplicationDto details: " + rtiApplicationDto.getYear() + rtiApplicationDto.getQuarter());
        if (rtiApplicationDto.getUser() == null) {
    	    logger.error("User details are null in the request payload: {}", rtiApplicationDto);
    	    throw new IllegalArgumentException("User details are null");
    	}
    	 UserDetailsDto	user = rtiApplicationDto.getUser();
         System.out.println("Received user details: " + user);
    	 


        // Call the service layer to fetch data  getrtiAppnConsolidatedProformaC
        BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = rtiApplicationService.getrtiAppnConsolidatedProformaC(user,year,quarter);

        // Return the response with HTTP status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/getCYrQtrDashboardReport")
    public ResponseEntity<BaseResponse<HttpStatus, List<UnitLevelDataDto>>> getDashboardData(
            @RequestBody RtiApplicationDto rtiApplicationDto) {

        Integer year = rtiApplicationDto.getYear();
        Integer quarter = rtiApplicationDto.getQuarter();
        logger.info("Received year: {}, quarter: {}", year, quarter);

        UserDetailsDto user = rtiApplicationDto.getUser();
        if (user == null) {
            logger.error("User details are null in the request payload: {}", rtiApplicationDto);
            throw new IllegalArgumentException("User details are null");
        }
        logger.info("Received user details: {}", user);

        Integer unit = user.getUnitId();
        Integer desgId = user.getDesignationId();
        logger.info("User designationId: {}", desgId);

        BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = null;

        if (unit != 4) {
            if (desgId == 5 && user.getCircleId() != 0) {
                response = rtiApplicationService.getrtiAppnDivDashboard(user, year, quarter);
            } else if (desgId == 7) {
                response = rtiApplicationService.getrtiAppnDseDashboard(user, year, quarter);
            } else if (desgId == 9 || desgId == 10) {
                response = rtiApplicationService.getrtiAppnCEDashboard(user, year, quarter);
            }
        }

        if (response == null) {
            logger.warn("No dashboard data found for given user and parameters.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    @PostMapping("/circleConsolidated")
    public ResponseEntity<BaseResponse<HttpStatus, List<UnitLevelDataDto>>> getCircleLevelData(
    		@RequestBody RtiApplicationDto rtiApplicationDto ) {
    	System.out.println("Received circleConsolidated rtiApplicationDto details: ");
        // Validate and process the incoming data
    	Integer year = rtiApplicationDto.getYear();
        Integer quarter =  rtiApplicationDto.getQuarter();
        Integer clickedUnitId=0;
        if ( rtiApplicationDto.getSelectedUnitId()!= null) {
        	clickedUnitId  =  rtiApplicationDto.getSelectedUnitId();
        System.out.println("Received clickedUnitId1 : " + clickedUnitId);
        rtiApplicationDto.getUser().setUnitId(clickedUnitId);
        System.out.println("Received clickedUnitId2 : " + rtiApplicationDto.getUser().getUnitId());
        }
        System.out.println("Received circleConsolidated rtiApplicationDto details: " + rtiApplicationDto.getYear() + rtiApplicationDto.getQuarter());
        if (rtiApplicationDto.getUser() == null) {
    	    logger.error("User details are null in the request payload: {}", rtiApplicationDto);
    	    throw new IllegalArgumentException("User details are null");
    	}
    	 UserDetailsDto	user = rtiApplicationDto.getUser();
    	 List<CircleListForUnitId> circles =rtiApplicationDto.getCircles();
    	 List<DivisionListForCircleId> divisions =rtiApplicationDto.getDivisions();
         System.out.println("Received user details: " + user);
         System.out.println("Received circles details: " + circles);
         System.out.println("Received division details: " + divisions);
    	 

       

        // Call the service layer to fetch data  getrtiAppnConsolidatedProformaC
        BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = rtiApplicationService.getrtiAppnCircleConsolidatedProformaC(user,year,quarter,circles,divisions);

        // Return the response with HTTP status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    @PostMapping("/divisionConsolidated")
    public ResponseEntity<BaseResponse<HttpStatus, List<UnitLevelDataDto>>> getDivisionLevelData(
    		@RequestBody RtiApplicationDto rtiApplicationDto ) {
    	//System.out.println("Received divisionConsolidated rtiApplicationDto details: ");
        // Validate and process the incoming data
    	Integer year = rtiApplicationDto.getYear();
        Integer quarter =  rtiApplicationDto.getQuarter();
        Integer clickedCircleId=0;
        Integer clickedUnitId=0;
        if ( rtiApplicationDto.getSelectedUnitId()!= null) {
        	clickedUnitId  =  rtiApplicationDto.getSelectedUnitId();
        System.out.println("Received clickedUnitId : " + clickedUnitId);
      //  rtiApplicationDto.getUser().setUnitId(clickedUnitId);
        }
        
        if ( rtiApplicationDto.getSelectedCircleId()!= null) {
        	clickedCircleId  =  rtiApplicationDto.getSelectedCircleId();
        System.out.println("Received clickedCircleId : " + clickedCircleId);
      //  rtiApplicationDto.getUser().setCircleId(clickedCircleId);
        }
       // System.out.println("Received divisionConsolidated rtiApplicationDto details: " + rtiApplicationDto.getYear() + rtiApplicationDto.getQuarter());
        if (rtiApplicationDto.getUser() == null) {
    	    logger.error("User details are null in the request payload: {}", rtiApplicationDto);
    	    throw new IllegalArgumentException("User details are null");
    	}
    	 UserDetailsDto	user = rtiApplicationDto.getUser();
    	 List<CircleListForUnitId> circles =rtiApplicationDto.getCircles();
    	 List<DivisionListForCircleId> divisions =rtiApplicationDto.getDivisions();
    	 
        // System.out.println("Received user details: " + user);
         System.out.println("Received user details: " + user);
         System.out.println("Received circles details: " + circles);
         System.out.println("Received division details: " + divisions);
         
         

        // Call the service layer to fetch data  getrtiAppnConsolidatedProformaC
        BaseResponse<HttpStatus, List<UnitLevelDataDto>> response = rtiApplicationService.getrtiAppnDivisionConsolidatedProformaC(user,year,quarter,circles,divisions,clickedUnitId,clickedCircleId);
        System.out.println("response " + response);
        // Return the response with HTTP status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/getById/{id}")
    public ResponseEntity<BaseResponse<HttpStatus, RtiApplicationDto>> getApplicationById(@PathVariable Integer id) {
        return ResponseEntity.ok(rtiApplicationService.get(id));
    }

    @PostMapping("/entry")
    public ResponseEntity<BaseResponse<HttpStatus, RtiApplicationDto>> createApplication(@Valid @RequestBody RtiApplicationDto rtiApplicationDto) {
    	
    	// Extract user details
        UserDetailsDto user;
		
			user = rtiApplicationDto.getUser();
			 String username = user != null ? user.getUsername() : "Unknown";

		        //java.lang.System.out.println("Received request from user: " + user);
		        logger.info("Received request from user: {}", user);
		
       
		        BaseResponse<HttpStatus, RtiApplicationDto> response = rtiApplicationService.create(rtiApplicationDto);
		        return new ResponseEntity<>(response, HttpStatus.CREATED);
       // return ResponseEntity.status(HttpStatus.CREATED).body(rtiApplicationService.create(rtiApplicationDto));
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<BaseResponse<HttpStatus, RtiApplicationDto>> updateApplication(@PathVariable Integer id, @Valid @RequestBody RtiApplicationDto rtiApplicationDto) {
        return ResponseEntity.ok(rtiApplicationService.update(id, rtiApplicationDto));
    }

//    @DeleteMapping("/app/deleteById/{id}")
//    public ResponseEntity<BaseResponse<HttpStatus, RtiApplicationDto>> deleteApplication(@PathVariable Integer id) {
//        return ResponseEntity.ok(rtiApplicationService.delete(id));
//    }
    
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<BaseResponse<HttpStatus, RtiApplicationDto>> deleteApplication(@PathVariable Integer id,@RequestParam String username) {
    	
    	//java.lang.System.out.println("Received delete request from user: " + username);
        logger.info("Received delete request from user: {}", username);
        
        BaseResponse<HttpStatus, RtiApplicationDto> response = rtiApplicationService.delete(id,username);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response); // Return HTTP 200 for successful deletion
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Return HTTP 404 if not found
        }
    }
}