package in.OAndM.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mandal", uniqueConstraints = { @UniqueConstraint(columnNames = { "EMAIL" }),  @UniqueConstraint(columnNames = { "UUID" }) })
public class Mandal {
	private static final long serialVersionUID = 1L;

	@Column(name="mcode")
	@Id
	private String mcode;

	@Column(name="mname")
	private String mname;

	@Column(name="active")
	private Boolean active;

	@ManyToOne
	@JoinColumn(name = "dcode")
	private District district;

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public String getMcode() {
		return mcode;
	}

	public void setMcode(String mcode) {
		this.mcode = mcode;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
