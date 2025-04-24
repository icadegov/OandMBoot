package in.OAndM.Entities;

import jakarta.persistence.Column;

public class O_m_work_called_type {
@Column(name="type_id")
 
 private Integer  typeId;

@Column(name="type_name")
 
 private String  typeName;

@Column(name="delete_flag")
 
 private boolean  deleteFlag;


}
