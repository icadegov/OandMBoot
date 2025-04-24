package in.OAndM.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDetailsDto {
	 private String username;
	    private String email;
	    private String role;
	    private Integer empId;
	  
	
	    
	    private Integer unitId;
	    private String unitName;
	    private Integer circleId;
	    private String circleName;
	    private Integer divisionId;
	    private String divisionName;
	    private Integer subDivisionId;
	    private String subDivisionName;
	    
	    private Integer sectionId;
	    private String  sectionName;
	    private Integer designationId;
	    private String designationName;
	    
	    
	    private String postType;
	    private String workingPlace;
	    private String workType;
	    private Long post;
	    
	    private String postFromDate;
	
	    
	    
	   
	   
	   // depOrganisationId	null
	   // deputationId	0
	  //  post	3044887672
	  //  postFromDate	"2021-01-10"
	    


	  
	   
	   
	 
	    
}


