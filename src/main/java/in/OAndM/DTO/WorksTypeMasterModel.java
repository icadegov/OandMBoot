package in.OAndM.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class WorksTypeMasterModel {
	
	private Integer workTypeId;
	  private String workTypeName ;
	  private boolean  deleteFlag;

}
