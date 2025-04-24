package in.OAndM.DTO;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok for getters and setters
@AllArgsConstructor
@NoArgsConstructor
public class UnitLevelDataDto {
    
	
	
		// TODO Auto-generated constructor stub
	
	private Integer unitId;
    private String unitName;  
    private Integer qpending;
    private Integer totapp;
    private Integer totdispo;
    private Integer totPending;
    private Integer infor;
    private Integer rs1;
    private Integer rs2;
    private Integer rs3;
    private Integer rs4;
    private Integer rs5;
    private Integer rs6;
    private Integer rs7;
    private Integer rs8;
    private Integer rs9;
    private Integer rs10;
    private Integer rs11;
    private Integer rs12;
    private Integer rs13;
    private Integer rstot;
    private Integer rs15;
    private Integer amount;
    private Integer rej6;//trans
    private Integer circleId;
    private Integer divisionId;
    private Integer appreceived;
   
    private Integer deemrefus;

	public String divisionName; 
	public String circleName; 
	private Double totAmt;	
		// TODO Auto-generated method stub
		
	
    
    
    
}
