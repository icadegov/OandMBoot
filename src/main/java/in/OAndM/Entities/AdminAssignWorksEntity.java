package in.OAndM.Entities;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name="o_m_admin_assign_works")
@Data
public class AdminAssignWorksEntity {
	
	
	@Column(name = "slno")
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "assign_seq")
	@SequenceGenerator(name="assign_seq",sequenceName ="o_m_admin_assign_works_slno_seq",allocationSize = 1 )
	@NotNull
	private Integer  slno;
	
@Column(name="work_id")
 
 private Integer  workId;

@Column(name="unit_id")
 
 private Integer  unitId;

@Column(name="circle_id")
 
 private Integer  circleId;

@Column(name="division_id")
 
 private Integer  divisionId;

@Column(name="sub_division_id")
 
 private Integer  subDivisionId;

@Column(name="is_latest")
 
 private boolean  isLatest;

@Column(name="delete_flag")
 
 private boolean  deleteFlag;

@Column(name="updated_by")
 
 private String  updatedBy;

@OneToOne
@JoinColumn(name = "work_id",referencedColumnName = "work_id" ,insertable = false,updatable = false)
private AdminSanctionsEntity assignAdminSanction;

//@ManyToOne
//@JoinColumn(name = "work_id", referencedColumnName = "work_id",insertable=false, updatable=false)
//private AdminSanctionsEntity adminSanctions ;

  
}
