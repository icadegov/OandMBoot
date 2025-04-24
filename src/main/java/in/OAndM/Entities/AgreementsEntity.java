package in.OAndM.Entities;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="o_m_agreements")
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgreementsEntity {


@NotNull
@Id 
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agreements_seq")
@SequenceGenerator(name = "agreements_seq", sequenceName = "o_m_agreements_agreement_id_seq", allocationSize = 1)
@Column(name="agreement_id")
 
 private Integer  agreementId;

@Column(name="work_id")
 
 private Integer  workId;

@Column(name="type_id")
 
 private Integer  typeId;

@Column(name="tender_percentage")
 
 private Double  tenderPercentage;

@Column(name="agreement_number")
 
 private String  agreementNumber;

@Column(name="agreement_date")
 
 private Date  agreementDate;

@Column(name="agreement_amount")
 
 private Double  agreementAmount;

@Column(name="agency_id")
 
 private Integer  agencyId;

@Column(name="is_latest")
 
 private boolean  isLatest;

@Column(name="delete_flag")
 
 private boolean  deleteFlag;

@Column(name="agency_name")
 
 private String  agencyName;


@Column(name="updated_by")
 
 private String  updatedBy;

@Column(name="tender_date")
 
 private Date  tenderDate;

@Column(name = "updated_on", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
private LocalDateTime updatedOn;

@ManyToOne
@JoinColumn(name = "tech_id", referencedColumnName = "ts_id", insertable=false, updatable=false)  // "parent_id" is the FK column in the child table
@SQLRestriction("delete_flag = 'false' AND is_latest='true'")
private TechnicalSanctionEntity technicalSanctionEntity;

@Column(name="tech_id" , insertable = true, updatable = true)
private Integer  techId;



@PrePersist
public void prePersist() {
    LocalDateTime now = LocalDateTime.now(); // Get the current timestamp
    this.updatedOn = now; // Optionally set updatedAt to the current timestamp as well
    this.isLatest = true; 
    this.deleteFlag = false; 
    
    if (this.technicalSanctionEntity != null && this.techId == null) {
        this.techId = this.technicalSanctionEntity.getTsId();  // Manually set tech_id before saving
    }
}


public TechnicalSanctionEntity getTechnicalSanctionEntity() {
    return technicalSanctionEntity;
}

public void setTechnicalSanctionEntity(TechnicalSanctionEntity technicalSanctionEntity) {
    this.technicalSanctionEntity = technicalSanctionEntity;
    if (technicalSanctionEntity != null) {
        this.techId = technicalSanctionEntity.getTsId();  // Manually set the tech_id when tech is set
    }
}

public Integer getTechId() {
    return techId;
}

public void setTechId(Integer techId) {
    this.techId = techId;
}

@OneToMany	 
@JoinColumn(name = "agreement_id", referencedColumnName = "agreement_id")
@SQLRestriction("delete_flag = 'false' AND is_latest='true'")
private List<BillsEntity> bills;

}
