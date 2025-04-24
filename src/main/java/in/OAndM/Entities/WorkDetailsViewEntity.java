package in.OAndM.Entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "o_m_work_details_view")

@Data

public class WorkDetailsViewEntity {

	@Id
	@Column(name = "ts_id")
	private Integer tsId;

	@Column(name = "admin_work_id")
	private Integer workId;

	@Column(name = "tech_work_id")
	private Integer techWorkId;
	
	@Column(name = "ts_number")
	private String tsNumber;

	@Column(name = "ts_approved_amount")
	private BigDecimal tsApprovedAmount;
	
	@Column(name = "ts_approved_amount_lakhs")
	private BigDecimal tsApprovedAmountLakhs;

	@Column(name = "ts_approved_date")
	private Date tsApprovedDate;

	@Column(name = "ts_file_url")
	private String tsFileUrl;
	
	@Column(name = "ts_est_file_url")
	private String tsEstFileUrl;

	@Column(name = "ts_type")
	private Integer tsType;

	@Column(name = "agr_work_id")
	private Integer agrWorkId;
	
	@Column(name = "agreement_id")
	private Integer agreementId;
	
	@Column(name = "tender_type")
	private Integer tenderType;
	
	@Column(name = "tender_type_name")
	private String tenderTypeName;
	
	@Column(name = "tender_percentage")
	private BigDecimal tenderPercentage;
	
	@Column(name = "agreement_number")
	private String agreementNumber;
	
	@Column(name = "agreement_date")
	private Date agreementDate;
	
	@Column(name = "agreement_amount")
	private BigDecimal agreementAmount;
	
	@Column(name = "agreement_amount_lakhs")
	private BigDecimal agreementAmountLakhs;
	
	@Column(name = "agency_name")
	private String agencyName;
	
	@Column(name = "tender_date")
	private Date tenderDate;
	
	@Column(name = "bills_paid")
	private Integer billsPaid;
	
	@Column(name = "bills_pending")
	private Integer billsPending;

//	@Column(name = "action_to_be_taken_cnt")
//	private Integer actionToBeTakenCount;
//
//	@Column(name = "action_to_be_taken_amt")
//	private BigDecimal actionToBeTakenAmt;

	@Column(name = "paid_amount")
	private BigDecimal paidAmount;

	@Column(name = "pendingAmount")
	private BigDecimal pendingAmount;
	
	/*
	 * @Column(name = "ts_approved_amount_lakhs") private BigDecimal
	 * tsApprovedAmountLakh;
	 */
	
	/*
	 * @Column(name = "agreement_amount_lakhs") private BigDecimal
	 * agreementAmountLakh;
	 */
	@Column(name = "paid_amount_lakhs")
	private BigDecimal paidAmountLakh;
	
	@Column(name = "pending_amount_lakhs")
	private BigDecimal pendingAmountLakh;
	
	@Column(name = "financial_year")
	private Integer finyear;
	
	@Column(name = "work_type_id")
	private Integer workTypeId;
	
	

}