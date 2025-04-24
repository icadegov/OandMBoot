package in.OAndM.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "o_m_work_approved_authority_mst")
@Data
public class WorkApprovedAuthorityMst {
	
@Column(name="authority_name")
 
 private String  authorityName;

@Id
@Column(name="authority_id")
 
 private Integer  authorityId;

@Column(name="delete_flag")
 
 private boolean  deleteFlag;

@Column(name="authority_name_alias")
 
 private String  authorityNameAlias;

@Column(name="authority_type")
 
 private String  authorityType;

@Column(name="adminsanc_limit_fin_year")
 
 private Double  adminsancLimitFinYear;

@Column(name="adminsanc_limit_per_work")
 
 private Double  adminsancLimitPerWork;
//
//@OneToOne(mappedBy = "authoritymst" ,fetch = FetchType.LAZY)
//private AdminSanctionsEntity adminSanctionsEntity;

}
