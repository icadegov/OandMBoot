package in.OAndM.services;

import in.OAndM.DTO.CircleListForUnitId;
import in.OAndM.DTO.DivisionListForCircleId;
import in.OAndM.DTO.RtiApplicationDto;
import in.OAndM.DTO.UnitLevelDataDto;
import in.OAndM.DTO.UnitLevelRequest;
import in.OAndM.DTO.UserDetailsDto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import in.OAndM.core.BaseResponse;
import in.OAndM.core.BaseService;


public interface RTIApplicationService extends BaseService<RtiApplicationDto, Integer> {

	BaseResponse<HttpStatus, RtiApplicationDto> delete(Integer id, String username);
    // You can define any additional methods specific to RTI applications here

	BaseResponse<HttpStatus,  List<RtiApplicationDto>> getRTIAppnRegisterEntryListEE(UserDetailsDto user, LocalDate firstDayInPreviousQuarter,
			LocalDate lastDayInPreviousQuarter);

	BaseResponse<HttpStatus, List<RtiApplicationDto>> getAppnYrQtrEEReport(UserDetailsDto user, Integer year,Integer quarter);

	

	BaseResponse<HttpStatus, List<UnitLevelDataDto>> getrtiAppnConsolidatedProformaC(UserDetailsDto user, Integer year,
			Integer quarter);

	BaseResponse<HttpStatus, List<UnitLevelDataDto>> getrtiAppnDivisionConsolidatedProformaC(UserDetailsDto user,
			Integer year, Integer quarter, List<CircleListForUnitId> circles, List<DivisionListForCircleId> divisions);

	BaseResponse<HttpStatus, List<UnitLevelDataDto>> getrtiAppnCircleConsolidatedProformaC(UserDetailsDto user,
			Integer year, Integer quarter, List<CircleListForUnitId> circles, List<DivisionListForCircleId> divisions);
}
