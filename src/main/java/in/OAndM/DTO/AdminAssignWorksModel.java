package in.OAndM.DTO;


import lombok.Data;

@Data
public class AdminAssignWorksModel {
	
 
 private Integer  slno;
 private Integer  workId;
 private Integer  unitId;
 private Integer  circleId;
 
 private Integer  divisionId;
 private Integer  subDivisionId;
 private boolean  isLatest;
 
 private boolean  deleteFlag;

 private String  updatedBy;

}
