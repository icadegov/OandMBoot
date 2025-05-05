package in.OAndM.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "o_m_sc_st_benefited_villages")
@Data
public class SCSTBenefitedVillages {

	@Column(name = "slno")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "benefited_seq")
	@SequenceGenerator(name = "benefited_seq", sequenceName = "o_m_sc_st_benefited_villages_slno_seq", allocationSize = 1)
	@NotNull
	private Integer slno;

	@Column(name = "work_id")

	private Integer workId;

	@Column(name = "district_id")

	private Integer districtId;

	@Column(name = "mandal_id")

	private Integer mandalId;

	@Column(name = "village_id")

	private Integer villageId;

	@Column(name = "is_latest")

	private boolean isLatest;

	@Column(name = "delete_flag")

	private boolean deleteFlag;

	@Column(name = "updated_by")

	private String updatedBy;

	@Column(name = "updated_date", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime updatedDate;
	
	@PrePersist
	  public void prePersist() {
	      LocalDateTime now = LocalDateTime.now(); // Get the current timestamp
	      this.updatedDate = now; // Optionally set updatedAt to the current timestamp as well
	      this.isLatest = true; 
	      this.deleteFlag = false; 
	     
	      
	    
	  }


}
