package in.OAndM.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.Data;


@Entity
@Table(name="o_m_work_type_mst")
@Data
public class WorkTypeMstEntity {
	
	
	@Column(name = "work_type_id")
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	@NotNull
	private Integer workTypeId;
	
	@Column(name = "work_type_name")
	  private String workTypeName ;

@Column(name="delete_flag")
 private boolean  deleteFlag;


}