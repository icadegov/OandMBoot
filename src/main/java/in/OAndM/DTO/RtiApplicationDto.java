package in.OAndM.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RtiApplicationDto {
	
	 private Integer applicationId;	  
	 
	    private LocalDate appnDate;//timestamp without time zone,	   
	    private String apptName;	    
	    private String apptAddress;	  
	    private LocalDate pioRecDate;//timestamp without time zone,	   
	    private String apptCategory;	   
	    private String descInfoReq;	    
	    private String thirdParty;	   
        private Integer appnFee;   
	    private Double chargesCollected;//double precision	    
        private Double totAmt;	   
	    private String isTransferred;	   
	    private LocalDate transDate;	    
	    private String transName;	    
	    private String deemedPio;	   
	    private String infoPartFull;
	    	    private LocalDate rejectDate;	    
	    private Integer rejectSectionId;
	    private String deemedRefusal;
	    private String appealMade;
	    private String remarks;
	    
	    
	    private Boolean isLatest;
	    private Boolean deleteFlag;	    
	    private String createdBy;
	    private LocalDateTime createDate;	    
	    private Integer unit;
	    private Integer circle;
	    private Integer division;
	    private Integer subdivision;
	    private Integer designation;
	    private String updatedBy;
	    private LocalDateTime updateDate;	    
	    private String deletedBy;
	    private LocalDateTime deletedDate;
	    
	    
	    private LocalDate infoFurnDate;
	    
	    
	    private LocalDateTime infoPartDate;
	    private LocalDateTime infoFullDate;
	    private Long createdPostId;
	    private String post;
	    
	    
	    private String appnNum;
	    private String transMode;
	    private Integer transAmt;
	    private LocalDate refusedDate;
	    private String rejectionSectionStatus;
	    private String rtiRejectionSection;
	    
	    private UserDetailsDto user;
	    
	    private List<CircleListForUnitId> circles;
	    private List<DivisionListForCircleId> divisions;
	    
	    private Integer year;
	    
	    private Integer quarter;
	    
	    private Integer selectedCircleId;
	    
	    private Integer selectedUnitId;
;
	    
	    
	
//	    private Integer applicationId;	   
//	    private LocalDateTime applicationDate;//timestamp without time zone,	   
//	    private String applicantName;	    
//	    private String applicantAddress;	  
//	    private LocalDateTime pioReceiptDate;//timestamp without time zone,	   
//	    private String applicantCategory;	   
//	    private String descriptionInfoRequest;	    
//	    private String thirdParty;	   
//	    private Integer applicationFee;   
//	    private Double chargesCollected;//double precision	    
//	    private Double totalAmount;	   
//	    private String isTransferred;	   
//	    private LocalDateTime transferDate;	    
//	    private String transferName;	    
//	    private String deemedPIO;	   
//	    private String infoPartFull;
//	    	    private LocalDateTime rejectionDate;	    
//	    private Integer rejectedSectionId;
//	    private String deemedRefusal;
//	    private String appealMade;
//	    private String remarks;
//	    private Boolean isLatest;
//	    private Boolean deleteFlag;	    
//	    private String createdBy;
//	    private LocalDateTime createDate;	    
//	    private Integer unitId;
//	    private Integer circleId;
//	    private Integer divisionId;
//	    private Integer subdivisionId;
//	    private Integer designationId;
//	    private String updatedBy;
//	    private LocalDateTime updateDate;	    
//	    private String deletedBy;
//	    private LocalDateTime deletedDate;
//	    private LocalDateTime infoFurnishedDate;
//	    private LocalDateTime infoPartDate;
//	    private LocalDateTime infoFullDate;
//	    private String createdPostId;
//	    private String applicationNum;
//	    private String transferMode;
//	    private Integer transferAmount;
//	    private LocalDateTime refusedDate;
	    }
