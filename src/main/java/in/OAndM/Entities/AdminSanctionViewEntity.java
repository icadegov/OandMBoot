package in.OAndM.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="o_m_admin_sanctions_view")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminSanctionViewEntity {
	
	
	@Id
	@Column(name = "admin_id")
	private Integer adminId;
	
	@Column(name = "admin_work_id")
	 private Integer workId ;
	
	@Column(name = "project_id")
	  private Integer projectId ;
	
	@Column(name = "project_name")
	  private String projectName ;
	
	@Column(name = "unit_id")
	private Integer unitId ;
	
	@Column(name = "unit_name")
	private String unitName ;
	
	@Column(name = "circle_id")
	private Integer circleId ;
	
	
	@Column(name = "division_id")
	private Integer divisionId ;
	
	
	@Column(name = "sub_division_id")
	private Integer subDivisionId ;
	

	@Column(name = "work_name")
	private String workName ;
	
	@Column(name = "hoa_id")
	private Integer hoaId ;
	
	@Column(name = "head_of_account")
	private String headOfAccount ;
	
	@Column(name = "authority_name")
	private String approvedByName ;
	

	@Column(name = "reference_number")
	private String referenceNumber ;
	

	@Column(name = "reference_date")
	private String referenceDate ;

	@Column(name = "admin_approved_amount")
	  private BigDecimal adminSanctionAmt ;
	

	@Column(name = "tank_id")
	private Integer tankId ;
	
	/*
	 * @Column(name = "lift_id") private Integer liftId ;
	 * 
	 * @Column(name = "small_lift_id") private Integer smallLiftId ;
	 * 
	 * @Column(name = "res_id") private Integer resId ;
	 * 
	 * @Column(name = "proj_sub_type_id") private Integer projSubTypeId ;
	 */
	
	@Column(name = "district_id")
	private Integer districtId ;
	
	@Column(name = "mandal_id")
	private Integer mandalId ;
	
	@Column(name = "village_id")
	private Integer villageId ;
	
	@Column(name = "district_name")
	  private String districtName ;
	
	@Column(name = "mandal_name")
	  private String mandalName ;
	
	@Column(name = "village_name")
	  private String villageName ;
	
	@Column(name = "financial_year")
	private Integer financialYear ;
	
	@Column(name = "fin_year")
	private String finYear ;
	
	@Column(name = "tank_code")
	private String tank_code ;
	
	
	@Column(name = "tank_name")
	private String tank_name ;	
	
	@Column(name = "aa_file_url")
	private String aaFileUrl ;
	  

	
	@Column(name = "approved_by_id")
	private Integer approvedById;
	
	@Column(name = "work_type_id")
	private Integer workTypeId;
	
	@Column(name = "work_type_name")
	private String workTypeName;

	@Column(name = "project_res_lift_name")
	private String projectResLiftName;
	
	@Column(name = "govt_sanction")
	private Integer govtSanction;
	
	@Column(name = "govt_sanc_amount")
	private BigDecimal govtSancAmount;
	
	@Column(name = "om_commitee_sanction")
	private Integer omCommiteeSanction;
	
	@Column(name = "committee_sanc_amount")
	private BigDecimal committeeSancAmount;
	
	@Column(name = "go_45_sanction")
	private Integer go45Sanction;
	
	@Column(name = "go_45_sanc_amount")
	private BigDecimal go45SancAmount;
	
	@Column(name = "go_45_sanction_ce")
	private Integer go45SanctionCe;
	
	@Column(name = "go_45_sanc_amount_ce")
	private BigDecimal go45SancAmountCe;
	
	@Column(name = "go_45_sanction_se")
	private Integer go45SanctionSe;
	
	@Column(name = "go_45_sanc_amount_se")
	private BigDecimal go45SancAmountSe;
	
	@Column(name = "go_45_sanction_ee")
	private Integer go45SanctionEe;
	
	@Column(name = "go_45_sanc_amount_ee")
	private BigDecimal go45SancAmountEe;
	
	@Column(name = "go_45_sanction_dee")
	private Integer go45SanctionDee;
	
	@Column(name = "go_45_sanc_amount_dee")
	private BigDecimal go45SancAmountDee;
	
	@Column(name = "admin_approved_amount_lakhs")
	private BigDecimal adminApprovedAmountLakh;
	
	@Column(name = "authority_type")
	private String authorityType;
	
	@Column(name = "sc_count")
	private Integer scCount;
	
	@Column(name = "sc_amount")
	private BigDecimal scAmount;
	
	@Column(name = "st_count")
	private Integer stCount;
	
	@Column(name = "st_amount")
	private BigDecimal stAmount;
	
	@Column(name = "sc_st_funds")
	private Integer scstFunds;
	
	@Column(name="proj_sub_type_id")
	private Integer projSubType;
}
