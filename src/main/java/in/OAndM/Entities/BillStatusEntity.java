package in.OAndM.Entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "o_m_bills_status_mst")
public class BillStatusEntity {
	@Id 
	@Column(name="status_id")
	 
	 private Integer  statusId;

	@Column(name="status_name")
	 
	 private String  statusName;

	@Column(name="delete_flag")
	 
	 private boolean  deleteFlag;

}
