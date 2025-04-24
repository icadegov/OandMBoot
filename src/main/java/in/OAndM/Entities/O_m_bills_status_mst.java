package in.OAndM.Entities;

import jakarta.persistence.Column;

public class O_m_bills_status_mst {
@Column(name="status_id")
 
 private Integer  statusId;

@Column(name="status_name")
 
 private String  statusName;

@Column(name="delete_flag")
 
 private boolean  deleteFlag;



}
