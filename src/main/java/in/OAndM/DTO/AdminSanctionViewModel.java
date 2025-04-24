package in.OAndM.DTO;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AdminSanctionViewModel {

	private long longWorkId;
	
	private Integer workId;
	
	private Integer unitId;

	private Integer circleId;

	private Integer divisionId;

	private Integer subDivisionId;
	
	private Integer approvedById;
	
	private String workName;
	
	private String workTypeName;
	
	private String unitName;
	private String circleName;
	private String divisionName;
	private String subDivisionName;
	
	private long govtSanction;
	
	private BigDecimal govtSancAmount;
	
	private long omCommiteeSanction;
	
	private BigDecimal committeeSancAmount;
	
	private long go45Sanction;
	
	private BigDecimal go45SancAmount;
	
	private long go45SanctionCe;
	
	private BigDecimal go45SancAmountCe;
	
	private long go45SanctionSe;
	
	private BigDecimal go45SancAmountSe;
	
	private long go45SanctionEe;
	
	private BigDecimal go45SancAmountEe;
	
	private long go45SanctionDee;
	
	private BigDecimal go45SancAmountDee;
	
	private String approvedByName;

	private String referenceNumber;

	private String referenceDate;

	private BigDecimal adminSanctionAmt;
	
	private String headOfAccount;
	
	private Integer financialYear;
	
	private String finYear;
	
	private Integer hoaId;
	
	private long scCount;
	
	private BigDecimal scAmount;
	
	private long stCount;
	
	private BigDecimal stAmount;
	
	private Integer scstFunds;
	
	private Integer projectId;
	
	private Integer workTypeId;
	
	private String projectName;
	
	private String tankName;
	
	private Integer projSubType;
	
	private Integer type;
}
