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

public class RtiProformaGDto {

    private Integer proGId;
    private String appealNum;
    private LocalDate appealDate;
    private String nameOfAppellant;
    private String appellantAddress;
    private LocalDate appealReceiptDate;
    private String pioName;
    private String pioDesignation;
    private String appnNum;
    private LocalDate appnDate;   
    private String appellateAuthorityName;
    private String appellateAuthorityAddre;
    private LocalDate appellateFirstDecisionDate;
    private Integer appellateFirstDecisionAllowRejec;
    private String appellateFirstDecisionAllowOrRejec;	
    private Integer rejectSectionId;
    private Integer chargeForInfo;
    private String secondAppealMade;
    private String secondAppealNoticeNum;
    private LocalDate secondAppealNoticeDate;
    private LocalDate secondAppealHearingDate;
    private String remarks;
    private Integer unit;
    private String unitName;
    private Integer circle;
    private Integer division;
    private Integer subDivision;
    private Long post;
    
    private Boolean deleteFlag;
    private Boolean isLatest;
    private String createdBy;
    private LocalDateTime createdTime;
    private String deletedBy;
    private LocalDateTime deletedTime;
    private String editedBy;
    private LocalDateTime editedTime;
    private Integer applicationFee;
    private String rejectionSectionStatus;
    private Integer year;
    private Integer quarter;
    
    
    
    //private Integer rejectSectionId; // ID of the rejection section
    //private String rtiRejSecName; // Name of the rejection section  
    private String rtiRejectionSection;
    
    
 // Add User Details
    private UserDetailsDto user;
    
    private List<CircleListForUnitId> circles;
    private List<DivisionListForCircleId> divisions;
    
    
    
    private Integer selectedCircleId;
    
    private Integer selectedUnitId;
}