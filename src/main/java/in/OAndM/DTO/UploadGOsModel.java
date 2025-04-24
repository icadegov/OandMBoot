package in.OAndM.DTO;


import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UploadGOsModel {

	private Integer  goId;
	 private String  goNumber;
	 private String  goDesc;
	 private String  goUrl;
	 private MultipartFile goFileUrl;
	 private boolean  deleteFlag;
	 private String  goDate;
	 private java.sql.Date goDt;
	 private String  uploadType;
	 private Double  goAmount;
	 private Integer  financialYear;
	 private String goFileType;
}
