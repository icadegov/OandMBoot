package in.OAndM.Entities;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rti_applications_register")
public class RTIApplication{

       
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Integer applicationId;

    @Column(name = "application_date")
    private LocalDate appndate;//timestamp without time zone,
    
    @Column(name = "applicant_name")
    private String apptName;

    @Column(name = "applicant_addrs")
    private String apptAddress;

    @Column(name = "pio_receipt_date")
    //private LocalDateTime pioReceiptDate;//timestamp without time zone,
    private LocalDate pioReceiptDate;
    
    @Column(name = "applicant_category")
    private String applicantCategory;

    @Column(name = "desc_info_req")
    private String descriptionInfoRequest;

    @Column(name = "third_party")
    private String thirdParty;

    @Column(name = "application_fee")
    private Integer applicationFee;

    @Column(name = "charges_collected")
    private Double chargesCollected;//double precision

    @Column(name = "tot_amt")
    private Double totalAmount;
   
    @Column(name = "is_transferred")
    private String isTransferred;

    @Column(name = "trans_date")
   // private LocalDateTime transferDate;
    private LocalDate transferDate;

    @Column(name = "trans_name")
    private String transferName;

    @Column(name = "deemed_pio")
    private String deemedPIO;
    
    @Column(name = "info_part_full")
    private String infoPartFull;

    @Column(name = "rejection_date")
    //private LocalDateTime rejectionDate;
    private LocalDate rejectionDate;

    @Column(name = "rejected_section_id")
    private Integer rejectedSectionId;

    @Column(name = "deemed_refusal")
    private String deemedRefusal;

    @Column(name = "appeal_made")
    private String appealMade;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "is_latest")
    private Boolean isLatest;

    @Column(name = "delete_flag",nullable = false)
    private Boolean deleteFlag;
    
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "create_date")
    private LocalDateTime createDate;
    
    @Column(name = "unit_id")
    private Integer unit;

    @Column(name = "circle_id")
    private Integer circle;

    @Column(name = "division_id")
    private Integer division;

    @Column(name = "subdivision_id")
    private Integer subDivision;

    @Column(name = "designation_id")
    private Integer designation;

    @Column(name = "update_by")
    private String updatedBy;

    @Column(name = "update_date")
    private LocalDateTime updatedDate;
    
    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "info_furnished_date")
   // private LocalDateTime infoFurnishedDate;
    private LocalDate infoFurnishedDate;

    @Column(name = "info_part_date")
    private LocalDateTime infoPartDate;

    @Column(name = "info_full_date")
    private LocalDateTime infoFullDate;

    @Column(name = "created_postid")
    private Long createdPostId;

    @Column(name = "application_num")
    private String appnnum;

    @Column(name = "trans_mode")
    private String transferMode;

    @Column(name = "trans_amt")
    private Integer transferAmount;

    @Column(name = "refused_date")
    //private LocalDateTime refusedDate;
    private LocalDate refusedDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rejected_section_id", referencedColumnName = "reject_section_id", insertable = false, updatable = false,nullable = true)
    private RtiRejectionStatus rejectionSectionStatus;

    // Getters and Setters

    public void RTIApplication() {
    }

	

   


}







