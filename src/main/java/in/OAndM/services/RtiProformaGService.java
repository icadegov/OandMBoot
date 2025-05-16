package in.OAndM.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;

import in.OAndM.DTO.CircleListForUnitId;
import in.OAndM.DTO.DivisionListForCircleId;
import in.OAndM.DTO.RtiProformaGDto;
import in.OAndM.DTO.UnitLevelDataDto;
import in.OAndM.DTO.UserDetailsDto;
import in.OAndM.core.BaseResponse;
import in.OAndM.core.BaseService;
import jakarta.validation.Valid;

public interface RtiProformaGService extends BaseService<RtiProformaGDto, Integer> {
    // You can define any additional methods specific to RTI proformaG here
	
	// List<RtiProformaGDto> findByAppealNo(String appealNo);
	
	
	// If you want to use a custom query to fetch RtiRejectionStatus eagerly
	// Custom method to fetch RtiProformaG with rejection status
	BaseResponse<HttpStatus, List<RtiProformaGDto>> findAllWithRejectionStatus();
	
	BaseResponse<HttpStatus, RtiProformaGDto> delete(Integer id, String username);

	BaseResponse<HttpStatus, List<RtiProformaGDto>> getRTIAppealEditList(@Valid UserDetailsDto user,
			LocalDate firstDayInPreviousQuarter, LocalDate lastDayInPreviousQuarter);

	BaseResponse<HttpStatus, List<RtiProformaGDto>> getAppealYrQtrEEReport(UserDetailsDto user, Integer year,
			Integer quarter);

	BaseResponse<HttpStatus, List<UnitLevelDataDto>> getrtiDivisionAppealConsolidatedProformaG(UserDetailsDto user,
			Integer year, Integer quarter, List<CircleListForUnitId> circles, List<DivisionListForCircleId> divisions, Integer clickedUnitId, Integer clickedCircleId);

	BaseResponse<HttpStatus, List<UnitLevelDataDto>> getUnitLevelData(UserDetailsDto user, Integer year,
			Integer quarter);

	BaseResponse<HttpStatus, List<UnitLevelDataDto>> getCircleLevelData(UserDetailsDto user, Integer year,
			Integer quarter, List<CircleListForUnitId> circles, List<DivisionListForCircleId> divisions);

}
