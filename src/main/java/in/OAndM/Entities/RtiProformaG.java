package in.OAndM.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data//(@Getter, @Setter, @RequiredArgsConstructor, @ToString, and @EqualsAndHashCode)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@ToString//It generates a toString()
@Entity
@Table(name = "rti_proforma_g")
public class RtiProformaG {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_g_id")
    private Integer proGId;

    @Column(name = "appeal_no")
    private String appealNo;

    @Column(name = "appeal_date")
    private LocalDate appealDate;

    @Column(name = "name_of_appellant")
    private String nameOfAppellant;

    @Column(name = "appellant_address")
    private String appellantAddress;

    @Column(name = "appeal_receipt_date")
    private LocalDate appealReceiptDate;

    @Column(name = "pio_name")
    private String pioName;

    @Column(name = "pio_designation")
    private String pioDesignation;

    @Column(name = "application_no")
    private String applicationNo;

    @Column(name = "application_date")
    private LocalDate applicationDate;

    @Column(name = "name_of_appellate")
    private String nameOfAppellate;

    @Column(name = "appellate_address")
    private String appellateAddress;

    @Column(name = "appellate_1st_desicion_date")
    private LocalDate appellateFirstDecisionDate;

    @Column(name = "appellate_1st_desicion_allow_rejec")
    private Integer appellateFirstDecisionAllowRejec;

    @Column(name = "appellate_1st_desicion_rej_section")
    private Integer appellateFirstDecisionRejSection;

    @Column(name = "charges_collect_forfurnish")
    private Integer chargesCollectForFurnish;

    @Column(name = "second_appeal_made_19_3")
    private String secondAppealMade193;

    @Column(name = "second_appeal_notice_num")
    private String secondAppealNoticeNum;

    @Column(name = "second_appeal_notice_date")
    private LocalDate secondAppealNoticeDate;

    @Column(name = "second_appeal_hearing_date")
    private LocalDate secondAppealHearingDate;

    @Column(name = "remarks")
    private String remarks;
    
    @Column(name = "unit_id")
    private Integer unit;

    @Column(name = "circle_id")
    private Integer circle;

    @Column(name = "division_id")
    private Integer division;

    @Column(name = "sub_division_id")
    private Integer subDivision;

    @Column(name = "post_id")
    private Long post;

    @Column(name = "delete_flag", nullable = false)
    private Boolean deleteFlag = false;

    @Column(name = "is_latest")
    private Boolean isLatest;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deleted_time")
    private LocalDateTime deletedTime;

    @Column(name = "edited_by")
    private String editedBy;

    @Column(name = "edited_time")
    private LocalDateTime editedTime;

    @Column(name = "application_fee")
    private Integer applicationFee;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appellate_1st_desicion_rej_section", referencedColumnName = "reject_section_id", insertable = false, updatable = false,nullable = true)
    private RtiRejectionStatus rejectionSectionStatus;

    // Getters and Setters

    public RtiProformaG() {
    }

    // Add all getters and setters here for each field
}


