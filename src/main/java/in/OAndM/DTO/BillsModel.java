package in.OAndM.DTO;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class BillsModel {

	private Integer  billId;
	private Integer  workId;
	private Integer  agreementId;
	 private Double  workDoneAmount;
	 private Double  cumWorkDoneAmount;
	 private Integer  statusId;
	 private Double  billNo;
	 private String  billType;
	 private String  locFileUrl;
	 private Date  locUpdatedOn;
	 private Double  locReleasedAmt;
	 private boolean  deleteFlag;
	 private boolean  isLatest;
	 private String  updatedBy;
	 private Date updatedOn;
	 private String statusName;
	 private Integer type;
	 private String workName;
	 private String agreementNumber;
	 
	 private List<BillsModel> billList=new ArrayList<>();
}
