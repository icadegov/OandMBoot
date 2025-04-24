package in.OAndM.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AgreementsModel {
	
	 private Integer  agreementId;
	 private String  agreementNumber;
	 private Double  agreementAmount;
	 private Date  agreementDate;
	 private Date tenderDate;
	 private Double tenderPercentage;
	private String agencyName;
	 private boolean  isLatest;
	 private boolean  deleteFlag;
	 private String  updatedBy;
	 private Integer typeId;
	private String workCalledType;
	private Integer workId;
	private Integer techId;
	 private Integer  agencyId;
	 private LocalDateTime updatedOn;
	 
	 private List<BillsModel> billsList=new ArrayList<BillsModel>();

}
