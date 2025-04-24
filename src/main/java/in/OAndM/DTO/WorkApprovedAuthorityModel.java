package in.OAndM.DTO;

import lombok.Data;


@Data
public class WorkApprovedAuthorityModel {
	 private String  authorityName;
	 private Integer  authorityId;
	 private boolean  deleteFlag; 
	 private String  authorityNameAlias;
	 private String  authorityType;
	 private Double  adminsancLimitFinYear;
	 private Double  adminsancLimitPerWork;


}
