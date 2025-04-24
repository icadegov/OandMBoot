package in.OAndM.Entities;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "o_m_gos_mst")
public class UploadGOsEntity {

	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gos_seq")
	@SequenceGenerator(name = "gos_seq", sequenceName = "o_m_gos_mst_go_id_seq", allocationSize = 1)
	@Column(name="go_id")
	 
	 private Integer  goId;

	@Column(name="go_number")
	 
	 private String  goNumber;

	@Column(name="go_desc")
	 
	 private String  goDesc;

	@Column(name="go_url")
	 
	 private String  goUrl;

	@Column(name="delete_flag")
	 
	 private boolean  deleteFlag;

	@Column(name="go_date")
	 
	 private java.sql.Date  goDt;

	@Column(name="upload_type")
	 
	 private String  uploadType;

	@Column(name="go_amount")
	 
	 private Double  goAmount;

	@Column(name="financial_year")
	 
	 private Integer  financialYear;
	
	@Column(name = "posted_time", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime updatedOn;
	
	@PrePersist
	public void prePersist() {
	    LocalDateTime now = LocalDateTime.now(); // Get the current timestamp
	    this.updatedOn = now; // Optionally set updatedAt to the current timestamp as well
	    this.deleteFlag = false; 
	    
	  
	}
}
