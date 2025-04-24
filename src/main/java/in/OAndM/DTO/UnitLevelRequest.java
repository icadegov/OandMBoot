package in.OAndM.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UnitLevelRequest {
    @NotNull(message = "Year is required")
   
    private Integer year;

    @NotNull(message = "Quarter is required")
    
    private Integer quarter;
    
    private Integer unitId;

    
   private Integer previousYear;
   private Integer previousQtr;
   // Getters and Setters
   
}

