package in.OAndM.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "district", uniqueConstraints = { @UniqueConstraint(columnNames = { "EMAIL" }),  @UniqueConstraint(columnNames = { "UUID" }) })
public class District {
	private static final long serialVersionUID = 1L;

	@Column(name="dcode")
	@Id
	private String dcode;
	
	private String tableName;
	
	
	private String columnname;
	
	private String cname;
	
	private String datatype;
	

	@Column(name="dname")
	private String dname;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="district", fetch = FetchType.LAZY)
	private List<Mandal> mandals;

	public String getDcode() {
		return dcode;
	}

	public void setDcode(String dcode) {
		this.dcode = dcode;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public List<Mandal> getMandals() {
		return mandals;
	}
	public void setMandals(List<Mandal> mandals) {
		this.mandals = mandals;
	}

}
