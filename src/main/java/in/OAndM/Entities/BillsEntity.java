package in.OAndM.Entities;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "o_m_work_bills")
public class BillsEntity {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bills_seq")
	@SequenceGenerator(name = "bills_seq", sequenceName = "o_m_work_bills_bill_id_seq", allocationSize = 1)
	@Column(name="bill_id")
	 
	 private Integer  billId;

	@Column(name="work_id")
	 
	 private Integer  workId;

	@Column(name="work_done_amount")
	 
	 private Double  workDoneAmount;

	@Column(name="cum_work_done_amount")
	 
	 private Double  cumWorkDoneAmount;

	@Column(name="is_latest")
	 
	 private boolean  isLatest;

	@Column(name="updated_by")
	 
	 private String  updatedBy;

	@Column(name = "updated_on", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime updatedOn;
	
	@Column(name="bill_no")
	 
	 private Double  billNo;

	@Column(name="bill_type")
	 
	 private String  billType;

	@Column(name="delete_flag")
	 
	 private boolean  deleteFlag;

	@Column(name="loc_file_url")
	 
	 private String  locFileUrl;

	@Column(name="loc_updated_on")
	 
	 private Date  locUpdatedOn;

	@Column(name="loc_released_amt")
	 
	 private Double  locReleasedAmt;
	
	@ManyToOne
	@JoinColumn(name = "status_id", referencedColumnName = "status_id", insertable=false, updatable=false)  // "parent_id" is the FK column in the child table
	  @SQLRestriction("delete_flag = 'false' AND is_latest='true'")
	private BillStatusEntity billStatusEntity;

	@Column(name="status_id" , insertable = true, updatable = true)

	private Integer  statusId;

	@ManyToOne
	@JoinColumn(name = "agreement_id", referencedColumnName = "agreement_id", insertable=false, updatable=false)  // "parent_id" is the FK column in the child table
	  @SQLRestriction("delete_flag = 'false' AND is_latest='true'")
	private AgreementsEntity agreementsEntity;

	@Column(name="agreement_id" , insertable = true, updatable = true)

	private Integer  agreementId;
	
	@PrePersist
	public void prePersist() {
	    LocalDateTime now = LocalDateTime.now(); // Get the current timestamp
	    this.updatedOn = now; // Optionally set updatedAt to the current timestamp as well
	    this.isLatest = true; 
	    this.deleteFlag = false; 
	    
	    if (this.agreementsEntity != null && this.agreementId == null) {
	        this.agreementId = this.agreementsEntity.getAgreementId();  // Manually set tech_id before saving
	    }
	}


	public AgreementsEntity getAgreementsEntity() {
	    return agreementsEntity;
	}

	public void setAgreementsEntity(AgreementsEntity agreementsEntity) {
	    this.agreementsEntity = agreementsEntity;
	    if (agreementsEntity != null) {
	        this.agreementId = agreementsEntity.getAgreementId(); // Manually set the tech_id when tech is set
	    }
	}

	public Integer getAgreementId() {
	    return agreementId;
	}

	public void setAgreementId(Integer agreementId) {
	    this.agreementId = agreementId;
	}


}
