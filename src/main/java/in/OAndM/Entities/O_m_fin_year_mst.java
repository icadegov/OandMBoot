package in.OAndM.Entities;

import jakarta.persistence.Column;

public class O_m_fin_year_mst {
@Column(name="id")
 
 private Integer  id;

@Column(name="financial_year")
 
 private String  financialYear;

@Column(name="fin_year")
 
 private Integer  finYear;

@Column(name="delete_flag")
 
 private boolean  deleteFlag;



}
