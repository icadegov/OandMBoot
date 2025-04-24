package in.OAndM.DTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SmallLiftsMasterModel {
	
	
	private Integer lisId;
	private String lisType;
	private String nameOfLis;
	private String locationOfLis;
	private String createdByUserid;

	private boolean deleteFlag;

	private String postId;


	private Integer projectId;


	private Integer unitId;


	private Integer circleId;


	private Integer divisionId;


	private Integer subDivisionId;


	private Integer civilComIdcId;


}
