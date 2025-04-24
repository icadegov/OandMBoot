package in.OAndM.Entities;

import jakarta.persistence.Column;

public class O_m_canal_eff_structure_wise {
@Column(name="eff_str_id")
 
 private Integer  effStrId;

@Column(name="canal_eff_id")
 
 private Integer  canalEffId;

@Column(name="structure_id")
 
 private Integer  structureId;

@Column(name="design_discharge_cusecs")
 
 private Double  designDischargeCusecs;

@Column(name="discharge_released_cusecs")
 
 private Double  dischargeReleasedCusecs;

@Column(name="tanks_fed_above_100ac")
 
 private Integer  tanksFedAbove100ac;

@Column(name="tanks_fed_below_100ac")
 
 private Integer  tanksFedBelow100ac;

@Column(name="remarks")
 
 private String  remarks;

@Column(name="is_latest")
 
 private boolean  isLatest;

@Column(name="delete_flag")
 
 private boolean  deleteFlag;

@Column(name="updated_by")
 
 private String  updatedBy;


}
