package in.OAndM.DTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WorkDetailsViewModel {

	private Integer tsId;

	private Integer workId;

	private Integer techWorkId;
	
	private String tsNumber;

	private BigDecimal tsApprovedAmount;
	
	private BigDecimal tsApprovedAmountLakhs;

	private Date tsApprovedDate;
	
	private String tsFileUrl;
	
	private String tsEstFileUrl;

	private Integer tsType;

	private Integer agrWorkId;
	
	private Integer agreementId;
	
	private Integer tenderType;
	
	private String tenderTypeName;
	
	private BigDecimal tenderPercentage;
	
	private String agreementNumber;
	
	private Date agreementDate;
	
	private BigDecimal agreementAmount;
	
	private BigDecimal agreementAmountLakhs;
	
	private String agencyName;
	
	private Date tenderDate;
	
	private Long billsPaid;
	
	private Long billsPending;

	private Long actionToBeTakenCount;

	private BigDecimal actionToBeTakenAmt;

	private BigDecimal paidAmount;

	private BigDecimal pendingAmount;
	
	private BigDecimal tsApprovedAmountLakh;
	
	private BigDecimal agreementAmountLakh;

	private BigDecimal paidAmountLakh;
	
	private BigDecimal pendingAmountLakh;
	
	private String workName;
	
	private Integer unitId;	
	private String unitName;
	
	private Integer circleId;
	private String circleName;
	
	private Integer divisionId;
	private String divisionName;
	
	private Integer subDivisionId;
	private String subDivisionName;

	private BigDecimal adminAmt;

	private Long adminCount;
	
	private String approvedName;
	private Integer workTypeId;
	private String workTypeName;
	private Integer finyear;
	private Integer hoaId;
	private String referenceNumber;
	private String headOfAccount;
	private Integer designationId;
	private Integer approvedId;
	private Long techCount;
	private BigDecimal techAmt;
	private Long agreementCount;
	private BigDecimal agreementAmt;
	
private long govtSanction;
	
	private BigDecimal govtSancAmount;
	
	private long omCommiteeSanction;
	
	private BigDecimal committeeSancAmount;
	
	private long go45Sanction;
	
	private BigDecimal go45SancAmount;
	
	private String authorityType;
	
	private Integer type;
	
	private Integer projectId;
	private String projectName;

}
