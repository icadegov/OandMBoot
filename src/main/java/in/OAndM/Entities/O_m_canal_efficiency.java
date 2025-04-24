package in.OAndM.Entities;

import jakarta.persistence.Column;

public class O_m_canal_efficiency {
@Column(name="eff_id")
 
 private Integer  effId;

@Column(name="project_id")
 
 private Integer  projectId;

@Column(name="canal_id")
 
 private Integer  canalId;

@Column(name="reach_id")
 
 private Integer  reachId;

@Column(name="year")
 
 private Integer  year;

@Column(name="month")
 
 private Integer  month;

@Column(name="fortnight")
 
 private Integer  fortnight;

@Column(name="design_discharge_cusecs")
 
 private Double  designDischargeCusecs;

@Column(name="discharge_start_reach_cusecs")
 
 private Double  dischargeStartReachCusecs;

@Column(name="discharge_end_reach_cusecs")
 
 private Double  dischargeEndReachCusecs;



@Column(name="is_latest")
 
 private boolean  isLatest;

@Column(name="delete_flag")
 
 private boolean  deleteFlag;

@Column(name="total_utilizations_cusecs")
 
 private Double  totalUtilizationsCusecs;

@Column(name="other_utilizations_cusecs")
 
 private Double  otherUtilizationsCusecs;

@Column(name="remarks")
 
 private String  remarks;


}
