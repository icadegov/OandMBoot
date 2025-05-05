package in.OAndM.DTO;


import lombok.Data;

@Data
public class SCSTBenefitedModel {
	
 
 private Integer  slno;
 private Integer  workId;
	private Integer districtId;

	private Integer mandalId;

	private Integer villageId;
	private String benefitedVname;
	
 private boolean  isLatest;
 
 private boolean  deleteFlag;

 private String  updatedBy;

}
