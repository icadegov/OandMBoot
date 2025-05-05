package in.OAndM.DTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AdminSanctionsModel {

	private Integer adminId;

	private Integer workId;

	private Integer projectId;

	private Integer unitId;

	private Integer circleId;

	private Integer divisionId;

	private Integer subDivisionId;

	private String workName;

	private Integer hoaId;

	private String headOfAccount;

	private Integer approvedById;

	private String approvedByName;

	private String referenceNumber;

	private Date referenceDate;

	private BigDecimal adminSanctionAmt;

	private Boolean isLatest;

	private Boolean delete_flag;

	private String updatedby;

	private Integer liftId;

	private Integer tankId;

	private Integer smallLiftId;

	private Integer resId;

	private Integer districtId;

	private Integer mandalId;

	private Integer villageId;

	private Integer financialYear;

	private String tankCode;

	private String tankName;

	private String aaFileUrl;

	private Boolean isAssigned;

	private Integer projSubTypeId;

	private Integer designationId;

	private Integer techId;

	private String tsNumber;
	
	private Double tsApprovedAmount;

	private List<TechnicalSanctionsModel> techlist = new ArrayList<TechnicalSanctionsModel>();

	private String sancFileType;
	
	private String estFileType;

	private Integer workTypeId;

	private Integer projectResLiftName;
	

	private String projectName;

	private MultipartFile adminFileUrl;

	 
	 private String adminFileType;
	 

	private String scstList; 
	private String unitName;
	
	private String scstVillages;
	private Integer scstFunds;
	
	

}
