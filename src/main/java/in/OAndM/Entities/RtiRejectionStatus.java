package in.OAndM.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;



@Data//(@Getter, @Setter, @RequiredArgsConstructor, @ToString, and @EqualsAndHashCode)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@ToString//It generates a toString()
@NoArgsConstructor 
@Entity
@Table(name = "rti_rejection_status")
public class RtiRejectionStatus {
	
	 @Id
	@Column(name = "reject_section_id")	
	private Integer rejectSectionId;
	 
	@Column(name = "rti_rejection_section")
	private String rtiRejectionSection;
  
    
    @Column(name = "delete_flag", nullable = false)
    private Boolean deleteFlag = false;
    

}
