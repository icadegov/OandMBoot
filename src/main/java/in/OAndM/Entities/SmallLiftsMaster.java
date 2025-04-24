package in.OAndM.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "o_m_small_lifts_mst")

@Data
public class SmallLiftsMaster {

	@Id
	@Column(name = "lis_id")
	private Integer lisId;

	@Column(name = "lis_type")
	private String lisType;

	@Column(name = "name_of_lis")
	private String nameOfLis;

	@Column(name = "location_of_lis")
	private String locationOfLis;

	@Column(name = "created_by_userid")
	private String createdByUserid;

	@Column(name = "delete_flag")
	private boolean deleteFlag;

	@Column(name = "post_id")
	private String postId;

	@Column(name = "project_id")
	private Integer projectId;

	@Column(name = "unit_id")
	private Integer unitId;

	@Column(name = "circle_id")
	private Integer circleId;

	@Column(name = "division_id")
	private Integer divisionId;

	@Column(name = "sub_division_id")
	private Integer subDivisionId;

	@Column(name = "civil_com_idc_id")
	private Integer civilComIdcId;

}
