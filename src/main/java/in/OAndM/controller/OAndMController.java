package in.OAndM.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.OAndM.DTO.AdminAssignWorksModel;
import in.OAndM.DTO.AdminSanctionViewModel;
import in.OAndM.DTO.AdminSanctionsModel;
import in.OAndM.DTO.AgreementsModel;
import in.OAndM.DTO.BillsModel;
import in.OAndM.DTO.LiftsMasterModel;
import in.OAndM.DTO.SmallLiftsMasterModel;
import in.OAndM.DTO.TechnicalSanctionsModel;
import in.OAndM.DTO.UploadGOsModel;
import in.OAndM.DTO.WorkApprovedAuthorityModel;
import in.OAndM.DTO.WorkDetailsViewModel;
import in.OAndM.DTO.WorksTypeMasterModel;
import in.OAndM.core.BaseResponse;
import in.OAndM.services.AdminSanctionService;
import in.OAndM.services.AdminSanctionViewService;
import in.OAndM.services.AgreementsService;
import in.OAndM.services.AssignAdminSanctionService;
import in.OAndM.services.BillsService;
import in.OAndM.services.LiftMasterService;
import in.OAndM.services.SmallLiftService;
import in.OAndM.services.TechnicalSanctionService;
import in.OAndM.services.UploadGOsService;
import in.OAndM.services.WorkApprovedAuthorityService;
import in.OAndM.services.WorkDetailsViewService;
import in.OAndM.services.WorkTypeMasterService;
import in.OAndM.utils.DateUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/OandMWorks")
/* @CrossOrigin(origins = "http://localhost:3002") */
@RequiredArgsConstructor
public class OAndMController {

	@Autowired
	AdminSanctionService adminSanctionService;

	@Autowired
	TechnicalSanctionService technicalSanctionService;

	@Autowired
	AgreementsService agreementsService;

	@Autowired
	WorkDetailsViewService workDetailsService;
	
	@Autowired
	AdminSanctionViewService adminViewService;

	@Autowired
	BillsService billsService;
	
	@Autowired
	UploadGOsService gosService;
	@Autowired
	WorkTypeMasterService  workTypeMasterService;
	
	@Autowired
	LiftMasterService  liftMasterService;
	
	@Autowired
	SmallLiftService  smallLiftService;
	
	@Autowired
	WorkApprovedAuthorityService workApprovedAuthorityService;

	/*
	 * Date date=new Date(System.currentTimeMillis()); SimpleDateFormat formatter =
	 * new SimpleDateFormat("ddMMyyyy"); String
	 * formattedDate=formatter.format(date);
	 */
	
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	Long uploadTime = timestamp.getTime();


	@GetMapping("/adminSanctions")
	public ResponseEntity<BaseResponse<HttpStatus, List<AdminSanctionsModel>>> getAdminSanctions(Integer unit,
			Integer finyear) {
		BaseResponse<HttpStatus, List<AdminSanctionsModel>> response = adminSanctionService
				.getAdminSanctionByFinYearByUnit(unit, finyear);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@GetMapping("/adminSanctionsByworkId")

	@ResponseBody
	public ResponseEntity<BaseResponse<HttpStatus, AdminSanctionsModel>> getAdminSanctionsByworkId(
			@RequestParam Integer workId) {
		BaseResponse<HttpStatus, AdminSanctionsModel> response = adminSanctionService.findbyWorkId(workId);
		//System.out.println("response" + response);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@GetMapping("/adminSanctionsByworkIdAndTechnicalSanctionTechId")
	public ResponseEntity<BaseResponse<HttpStatus, AdminSanctionsModel>> getAdminSanctionsByTechId(Integer unit,
			Integer finYear, Integer tsId) {
		BaseResponse<HttpStatus, AdminSanctionsModel> response = adminSanctionService
				.findByunitIdAndFinancialYearAndIsLatestAndDeleteFlagAndTechnEntriesIsLatestAndTechnEntriesTsId(unit,
						finYear, true, false, true, tsId);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@GetMapping("/O&MWorksTechnicalSanction")
	@ResponseBody
	public ResponseEntity<BaseResponse<HttpStatus, List<AdminSanctionsModel>>> getAdminSanctionsForDEE(
			@ModelAttribute AdminSanctionsModel admin) {
		
		Integer finyear, unitId, circleId, divisionId, subDivisionId, designationId = 0;

		finyear = admin.getFinancialYear() != null ? admin.getFinancialYear() : 0;
		unitId = admin.getUnitId() != null ? admin.getUnitId() : 0;
		circleId = admin.getCircleId() != null ? admin.getCircleId() : 0;
		divisionId = admin.getDivisionId() != null ? admin.getDivisionId() : 0;
		subDivisionId = admin.getSubDivisionId() != null ? admin.getSubDivisionId() : 0;

		designationId = admin.getDesignationId()!= null ? admin.getDesignationId() : 0;

		
		BaseResponse<HttpStatus, List<AdminSanctionsModel>> response = adminSanctionService.getAdminSanctionForDEE(
				admin.getUnitId(),admin.getCircleId(), admin.getDivisionId(), admin.getSubDivisionId(), admin.getFinancialYear());


		return new ResponseEntity<>(response, response.getStatus());
	}

	@GetMapping("/technicalSanctionsByworkId")
	public ResponseEntity<BaseResponse<HttpStatus, List<TechnicalSanctionsModel>>> getTechSanctionsByworkId(
			Integer workId) {
		BaseResponse<HttpStatus, List<TechnicalSanctionsModel>> response = technicalSanctionService
				.getTechnicalSanctionByWorkId(workId);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@GetMapping("/AgreementsByworkId")
	public ResponseEntity<BaseResponse<HttpStatus, List<AgreementsModel>>> getAgreementsByworkId(Integer workId) {
		BaseResponse<HttpStatus, List<AgreementsModel>> response = agreementsService.getAgreementsByworkId(workId);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@GetMapping("/agmtAndBillDetailsByworkId")
	@ResponseBody
	public ResponseEntity<BaseResponse<HttpStatus, AgreementsModel>> getAgmtAndBillDetailsByworkId(
			@RequestParam Integer workId, @RequestParam Integer agreementId) {
		BaseResponse<HttpStatus, AgreementsModel> response = agreementsService.getAgmtAndBillDetailsByworkId(workId,
				agreementId);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@PostMapping(value = "/submitTechnicalSanctions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

	public ResponseEntity<BaseResponse<HttpStatus, List<TechnicalSanctionsModel>>> submitTechnicalSanctions(
			@ModelAttribute TechnicalSanctionsModel techlist) {

		MultipartFile tsFile = null, tsEstimateFile = null;
		List<TechnicalSanctionsModel> tsList1 = new ArrayList<TechnicalSanctionsModel>();

		List<TechnicalSanctionsModel> tsList = techlist.getTechList();

		String tsValidFile, tsEstValidFile = null;
		if (techlist != null) {
			for (int i = 0; i < tsList.size(); i++) {
//					tsList.get(i).setUpdatedByUserName(uname);

				tsFile = tsList.get(i).getTechSancUrl();
				tsEstimateFile = tsList.get(i).getTechEstimateUrl();
				if (null != tsFile && tsFile.getSize() > 0) {
//					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//					Long uploadTime = timestamp.getTime();
					String fileName = tsFile.getOriginalFilename().replaceAll("\\s+", "");
					;
					String FileType = tsFile.getContentType();
					String rootPath = System.getProperty("catalina.home");
					File dir = new File(rootPath + File.separator + "webapps" + File.separator + "PMSWebApp"
							+ File.separator + "O&MWorks" + File.separator + "TechSanctionFiles" + File.separator);
					String[] temps = fileName.split(Pattern.quote("."));
					if (!dir.exists())
						dir.mkdirs();
					String saveFileName = temps[0] + "_" + uploadTime + "." + temps[temps.length - 1];
					try {
						tsFile.transferTo(new File(dir.getAbsolutePath() + File.separator + saveFileName));
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tsList.get(i).setTsFileUrl(
							"O&MWorks" + File.separator + "TechSanctionFiles" + File.separator + saveFileName);
					tsValidFile = temps[temps.length - 1];
					tsList.get(i).setSancFileType(tsValidFile);
				}

				if (null != tsEstimateFile && tsEstimateFile.getSize() > 0) {
//					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//					Long uploadTime = timestamp.getTime();
					String fileName = tsEstimateFile.getOriginalFilename().replaceAll("\\s+", "");
					;
					String FileType = tsEstimateFile.getContentType();
					String rootPath = System.getProperty("catalina.home");
					File dir = new File(rootPath + File.separator + "webapps" + File.separator + "PMSWebApp"
							+ File.separator + "O&MWorks" + File.separator + "TechEstimateFiles" + File.separator);
					String[] temps = fileName.split(Pattern.quote("."));
					if (!dir.exists())
						dir.mkdirs();
					String saveFileName = temps[0] + "_" + uploadTime + "." + temps[temps.length - 1];
					try {
						tsEstimateFile.transferTo(new File(dir.getAbsolutePath() + File.separator + saveFileName));
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tsList.get(i).setTsEstFileUrl(
							"O&MWorks" + File.separator + "TechSanctionFiles" + File.separator + saveFileName);
					tsEstValidFile = temps[temps.length - 1];
					tsList.get(i).setEstFileType(tsEstValidFile);
				}
				java.sql.Date sqlDate = null;
				if (tsList.get(i).getTsDate() != null) {
					try {
						sqlDate = DateUtil.getSQLDate1(tsList.get(i).getTsDate());
						if (sqlDate != null) {
							tsList.get(i).setTsApprovedDate(sqlDate);
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		BaseResponse<HttpStatus, List<TechnicalSanctionsModel>> response = technicalSanctionService
				.insertTechnicalSanctions(tsList);

		return new ResponseEntity<>(response, response.getStatus());

	}

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> testUpload(@RequestParam("file") MultipartFile file) {
		return ResponseEntity.ok("File uploaded: " + file.getOriginalFilename());
	}

	@PostMapping(value = "/submitAgreements")
	public ResponseEntity<BaseResponse<HttpStatus, AgreementsModel>> submitAgreements(@RequestBody AgreementsModel agreements) {
		 BaseResponse<HttpStatus, AgreementsModel> response = new BaseResponse<>();
		  if(agreements!=null) {
			  
			  response =agreementsService.insertAgreements(agreements);
		    } else {
		        response.setStatus(HttpStatus.BAD_REQUEST);
		        response.setMessage("Error in Submitting!");
		    }
		    return new ResponseEntity<>(response, response.getStatus());
		  }


	@PostMapping(value = "/submitBillDetails")
	public ResponseEntity<BaseResponse<HttpStatus, BillsModel>> submitBillDetails(@RequestBody BillsModel bills) {
		  BaseResponse<HttpStatus, BillsModel> response = new BaseResponse<>();
		  if(bills!=null) {
			  
			  response =billsService.insertBills(bills);
		    } else {
		        response.setStatus(HttpStatus.BAD_REQUEST);
		        response.setMessage("Error in Submitting!");
		    }
		    return new ResponseEntity<>(response, response.getStatus());
		  }
	
	
	@PostMapping(value = "/submitGos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BaseResponse<HttpStatus, UploadGOsModel>> submitGos( @ModelAttribute UploadGOsModel gos ) {
	    BaseResponse<HttpStatus, UploadGOsModel> response = new BaseResponse<>();
	    String goValidFile = null;
		/*
		 * Timestamp timestamp = new Timestamp(System.currentTimeMillis()); Long
		 * uploadTime = timestamp.getTime();
		 */
	    String fileName = gos.getGoFileUrl().getOriginalFilename().replaceAll("\\s+", "");
	    String[] temps = fileName.split(Pattern.quote("."));
	    
	    String rootPath = System.getProperty("catalina.home");
	    File dir = new File(rootPath + File.separator + "webapps" + File.separator + "PMSWebApp"
	            + File.separator + "O&MWorks" + File.separator + "OandMGos");

	    if (!dir.exists()) dir.mkdirs();
	    
	    String saveFileName = temps[0] + "_" + uploadTime + "." + temps[temps.length - 1];
	    try {
	    	gos.getGoFileUrl().transferTo(new File(dir.getAbsolutePath() + File.separator + saveFileName));
	    } catch (IllegalStateException | IOException e) {
	        e.printStackTrace();
	    }

	  
	    String goUrl = "O&MWorks" + File.separator + "OandMGos" + File.separator + saveFileName;
	    goValidFile = temps[temps.length - 1].toLowerCase(); 
	    
	    java.sql.Date sqlDate = null;
		if (gos.getGoDate() != null) {
			try {
				sqlDate = DateUtil.getSQLDate1(gos.getGoDate());
				if (sqlDate != null) {
					gos.setGoDt(sqlDate);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    
	    

	    if ("pdf".equals(goValidFile)) {
	        gos.setGoUrl(goUrl);
	        gos.setGoFileType(goValidFile);
	      

	        response = gosService.insertGOs(gos);
	    } else {
	        response.setStatus(HttpStatus.BAD_REQUEST);
	        response.setMessage("Only PDF files are allowed.");
	    }
	    return new ResponseEntity<>(response, response.getStatus());
	}

	
	@GetMapping("/getGosCirculars")
	@ResponseBody
	public ResponseEntity<BaseResponse<HttpStatus, List<UploadGOsModel>>> getGosCirculars(@ModelAttribute UploadGOsModel uploadGOsModel) {
		BaseResponse<HttpStatus, List<UploadGOsModel>> response;
		String type = uploadGOsModel.getUploadType()!=null ? uploadGOsModel.getUploadType() : "";
		response= gosService.getGosCirculars(type);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	
	@GetMapping("/getAbsRepSanctionAuthorityWiseFinyear")
	@ResponseBody
	public ResponseEntity<BaseResponse<HttpStatus, List<WorkDetailsViewModel>>> getWorksByFinyear(
			@ModelAttribute WorkDetailsViewModel workDetailsViewModel) {
		BaseResponse<HttpStatus, List<WorkDetailsViewModel>> response;
		Integer finyear, unitId, circleId, divisionId, subDivisionId, designationId = 0;
		finyear = workDetailsViewModel.getFinyear() != null ? workDetailsViewModel.getFinyear() : 0;
		unitId = workDetailsViewModel.getUnitId() != null ? workDetailsViewModel.getUnitId() : 0;
		circleId = workDetailsViewModel.getCircleId() != null ? workDetailsViewModel.getCircleId() : 0;
		divisionId = workDetailsViewModel.getDivisionId() != null ? workDetailsViewModel.getDivisionId() : 0;
		subDivisionId = workDetailsViewModel.getSubDivisionId() != null ? workDetailsViewModel.getSubDivisionId() : 0;
		designationId = workDetailsViewModel.getDesignationId() != null ? workDetailsViewModel.getDesignationId() : 0;
		response = workDetailsService.getAbsRepSanctionAuthorityWiseByFinyear(finyear, unitId, circleId, divisionId,
				subDivisionId,designationId);
		
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@GetMapping("/getAbsRepHOAWiseFinyear")
	@ResponseBody
	public ResponseEntity<BaseResponse<HttpStatus, List<AdminSanctionViewModel>>> getAbsRepHOAWiseFinyear(
			@ModelAttribute AdminSanctionViewModel adminSanctionsModel) {
		BaseResponse<HttpStatus, List<AdminSanctionViewModel>> response;
		Integer finyear= 0;
		finyear = adminSanctionsModel.getFinancialYear() != null ? adminSanctionsModel.getFinancialYear() : 0;
		
		response = adminViewService.getAbsRepHOAWiseByFinancialyear(finyear);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@GetMapping("/getAbsRepUnitWiseFinyear")
	@ResponseBody
	public ResponseEntity<BaseResponse<HttpStatus, List<WorkDetailsViewModel>>> getAbsRepUnitWiseFinyear(@ModelAttribute WorkDetailsViewModel workDetailsViewModel) {
		BaseResponse<HttpStatus, List<WorkDetailsViewModel>> response;
		Integer finyear=0;
		finyear = workDetailsViewModel.getFinyear()!=null ? workDetailsViewModel.getFinyear() : 0;
		response = workDetailsService.getAbsRepUnitWiseFinyear(finyear);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@GetMapping("/getAbsRepUnitWiseSCSTSdfFinyear")
	@ResponseBody
	public ResponseEntity<BaseResponse<HttpStatus, List<AdminSanctionViewModel>>> getAbsRepUnitWiseSCSTSdfFinyear(
			@ModelAttribute AdminSanctionViewModel adminSanctionsModel) {
		BaseResponse<HttpStatus, List<AdminSanctionViewModel>> response;
		Integer finyear= 0;
		finyear = adminSanctionsModel.getFinancialYear() != null ? adminSanctionsModel.getFinancialYear() : 0;
		
		response = adminViewService.getAbsRepUnitWiseSCSTSdfFinyear(finyear);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@GetMapping("/getAbsRepWorkTypeHOAWiseFinyear")
	@ResponseBody
	public ResponseEntity<BaseResponse<HttpStatus, List<WorkDetailsViewModel>>> getAbsRepWorkTypeHOAWiseFinyear(@ModelAttribute WorkDetailsViewModel workDetailsViewModel) {
		BaseResponse<HttpStatus, List<WorkDetailsViewModel>> response;
		Integer finyear=0;
		finyear = workDetailsViewModel.getFinyear()!=null ? workDetailsViewModel.getFinyear() : 0;
		response = workDetailsService.getAbsRepWorkTypeHOAWiseFinyear(finyear);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@GetMapping("/getAbsRepSanctionAuthWorkTypeWiseFinyear")
	@ResponseBody
	public ResponseEntity<BaseResponse<HttpStatus, List<WorkDetailsViewModel>>> getAbsRepSanctionAuthWorkTypeWiseFinyear(@ModelAttribute WorkDetailsViewModel workDetailsViewModel) {
		BaseResponse<HttpStatus, List<WorkDetailsViewModel>> response;
		Integer finyear=0;
		finyear = workDetailsViewModel.getFinyear()!=null ? workDetailsViewModel.getFinyear() : 0;
		response = workDetailsService.getAbsRepSanctionAuthWorkTypeWiseFinyear(finyear);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@GetMapping("/getAbsRepWorkTypeWiseFinyear")
	@ResponseBody
	public ResponseEntity<BaseResponse<HttpStatus, List<WorkDetailsViewModel>>> getAbsRepWorkTypeWiseFinyear(@ModelAttribute WorkDetailsViewModel workDetailsViewModel) {
		BaseResponse<HttpStatus, List<WorkDetailsViewModel>> response;
		Integer finyear=0;
		finyear = workDetailsViewModel.getFinyear()!=null ? workDetailsViewModel.getFinyear() : 0;
		response = workDetailsService.getAbsRepWorkTypeWiseFinyear(finyear);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	

@GetMapping("/getAbsRepUnitHOAWiseFinyear")
	@ResponseBody
	public ResponseEntity<BaseResponse<HttpStatus, List<AdminSanctionViewModel>>> getAbsRepUnitHOAWiseFinyear(
			@ModelAttribute AdminSanctionViewModel adminSanctionsModel) {
		BaseResponse<HttpStatus, List<AdminSanctionViewModel>> response;
		Integer finyear= 0;
		finyear = adminSanctionsModel.getFinancialYear() != null ? adminSanctionsModel.getFinancialYear() : 0;
		
		response = adminViewService.getAbsRepUnitHOAWiseFinyear(finyear);
		return new ResponseEntity<>(response, response.getStatus());
	}

		
	@GetMapping("/getAbsRepSanctionAuthorityAndOfcWiseFinyear")
	@ResponseBody
	public ResponseEntity<BaseResponse<HttpStatus, List<AdminSanctionViewModel>>> getWorksByFinYearAndOffice(@ModelAttribute AdminSanctionViewModel works) {

		BaseResponse<HttpStatus, List<AdminSanctionViewModel>> response;
		
		Integer finyear, unitId, circleId, divisionId, subDivisionId, approvedId = 0;

		finyear = works.getFinancialYear() != null ? works.getFinancialYear() : 0;
		unitId = works.getUnitId() != null ? works.getUnitId() : 0;
		circleId = works.getCircleId() != null ? works.getCircleId() : 0;
		divisionId = works.getDivisionId() != null ? works.getDivisionId() : 0;
		subDivisionId = works.getSubDivisionId() != null ? works.getSubDivisionId() : 0;
		approvedId = works.getApprovedById() != null ? works.getApprovedById() : 0;
		
		response= adminViewService.getWorksByFinancialyearAndOffice(unitId,circleId,divisionId,subDivisionId,finyear,approvedId);
		
		return new ResponseEntity<>(response,response.getStatus());
	}


	

	@GetMapping("/getWorkTypeMst")
	public ResponseEntity<BaseResponse<HttpStatus, List<WorksTypeMasterModel>>> getWorkTypeMst() {
		BaseResponse<HttpStatus, List<WorksTypeMasterModel>> response = workTypeMasterService.getworktypemaster();
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@GetMapping("/getLiftsByProjectId/{projectId}")
	public ResponseEntity<BaseResponse<HttpStatus, List<LiftsMasterModel>>> getLiftsByProjectId(@PathVariable Integer projectId) {
		BaseResponse<HttpStatus, List<LiftsMasterModel>> response = liftMasterService.findbyProjectId(projectId);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@GetMapping("/getUserSmallLifts/{unitId}")
	public ResponseEntity<BaseResponse<HttpStatus, List<SmallLiftsMasterModel>>> getUserSmallLifts(@PathVariable Integer unitId) {
		BaseResponse<HttpStatus, List<SmallLiftsMasterModel>> response = smallLiftService.findbyUnitId(unitId);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@GetMapping("/getAuthorityList")
	public ResponseEntity<BaseResponse<HttpStatus, List<WorkApprovedAuthorityModel>>> getAuthorityList() {
		BaseResponse<HttpStatus, List<WorkApprovedAuthorityModel>> response =workApprovedAuthorityService.getAuthorityList();
		return new ResponseEntity<>(response, response.getStatus());
	}

	@PostMapping(value = "/submitAdminSanctions")
	public ResponseEntity<BaseResponse<HttpStatus,AdminSanctionsModel>> submitAdminSanctions(
			@ModelAttribute AdminSanctionsModel admin) {
		String adminFileType = null;
		/*
		 * Date date = new Date(System.currentTimeMillis());
		 * 
		 * String formattedDate = formatter.format(date);
		 */
		String fileName = admin.getAdminFileUrl().getOriginalFilename().replaceAll("\\s+", "");
		String[] temps = fileName.split(Pattern.quote("."));
		BaseResponse<HttpStatus, AdminSanctionsModel> response = new BaseResponse<>();
		String rootPath = System.getProperty("catalina.home");
		File dir = new File(rootPath + File.separator + "webapps" + File.separator + "PMSWebApp" + File.separator
				+ "O&MWorks" + File.separator + "AdminSanctionFiles" + File.separator);

		if (!dir.exists())
			dir.mkdirs();

		String saveFileName = temps[0] + "_" + uploadTime + "." + temps[temps.length - 1];
		try {
			admin.getAdminFileUrl().transferTo(new File(dir.getAbsolutePath() + File.separator + saveFileName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

		String adminURL = "O&MWorks" + File.separator + "AdminSanctionFiles" + File.separator + saveFileName;
		adminFileType = temps[temps.length - 1].toLowerCase();

		if ("pdf".equals(adminFileType)) {
			admin.setAaFileUrl(adminURL);
			response = adminSanctionService.insertAdminSanctions(admin);
		} else {

			response.setMessage("Only PDF files are allowed.");
			response.setStatus(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(response, response.getStatus());
	}
	

	    		@GetMapping("/O&MWorksAADetailedReport")
	    		
	    		public ResponseEntity<BaseResponse<HttpStatus, List<AdminSanctionViewModel>>> getOMWorksAADetailedReport( 
	    				//	@RequestBody AdminSanctionViewModel admin ){
	    			
	    				@ModelAttribute AdminSanctionViewModel admin ){
//	    				@RequestParam Integer unitId, @RequestParam Integer authorityId, 
//                        @RequestParam Integer scst, @RequestParam Integer financialYear, 
//                        @RequestParam Integer hoaId,  @RequestParam Integer workType, @RequestParam Integer ProjSubType, 
//                        @RequestParam Integer projectId, @RequestParam Integer type){
	    				
	    		    			//getUserOfficeDetails(9812);
	    			BaseResponse<HttpStatus, List<AdminSanctionViewModel>> response = null;
	    			response= adminViewService.getOMWorksAADetailedReport(admin);
	    			
	    			return new ResponseEntity<>(response,response.getStatus());
	    		}
	    		
	    		@GetMapping("/O&MWorksHoaAADetailedReport")
	    		public ResponseEntity<BaseResponse<HttpStatus, List<AdminSanctionViewModel>>> getOMWorksHoaAADetailedReport( 
	    				@ModelAttribute AdminSanctionViewModel admin){
		    						
		    			BaseResponse<HttpStatus, List<AdminSanctionViewModel>> response = null;
		    			response= adminViewService.getOMWorksHoaAADetailedReport(admin);
		    			
		    			return new ResponseEntity<>(response,response.getStatus());
		    		}
	    		
	    		@GetMapping("/O&MWorksSanctionAADetailedReport")
	    		public ResponseEntity<BaseResponse<HttpStatus, List<AdminSanctionViewModel>>> getOMWorksSanctionAADetailedReport( 
	    				@ModelAttribute AdminSanctionViewModel admin){
	    						
	    			BaseResponse<HttpStatus, List<AdminSanctionViewModel>> response = null;
	    			
	    			
	    		 	response= adminViewService.getOMWorksSanctionAADetailedReport(admin);
	    		 	
	    			return new ResponseEntity<>(response,response.getStatus());
	    		}
	    		
	    		@GetMapping("/O&MWorksTSAgmtBillsDetailedReport")
	    		public ResponseEntity<BaseResponse<HttpStatus, ?>> getOMWorksTSAgmtBillsDetailedReport( 
	    				@RequestParam Integer unitId, @RequestParam Integer approvedById, 
                        @RequestParam Integer scstFunds, @RequestParam Integer financialYear, 
                        @RequestParam Integer hoaId,  @RequestParam Integer workTypeId, @RequestParam Integer ProjSubType, 
                        @RequestParam Integer projectId, @RequestParam Integer type ){
	    		
	    			//BaseResponse<HttpStatus, List<WorkDetailsViewModel>> response = null;
	    			BaseResponse<HttpStatus, ?> response;
	    			ResponseEntity<BaseResponse<HttpStatus, ?>> responseEntity;

	    			if (type == 5 || type == 6) {
	    			    response = billsService.getOMWorkBillsDetailedReport(unitId, approvedById, scstFunds, financialYear, hoaId, workTypeId, ProjSubType, projectId, type);
	    			    responseEntity = new ResponseEntity<>(response, response.getStatus());
	    			} else {
	    			    response = workDetailsService.getOMWorksTSAgmtBillsDetailedReport(unitId, approvedById, scstFunds, financialYear, hoaId, workTypeId, ProjSubType, projectId, type);
	    			    responseEntity = new ResponseEntity<>(response, response.getStatus());
	    			}

	    			return responseEntity;

	    			
	    		}
	    		
	    		@GetMapping("/O&MWorksHoaTSAgmtBillsDetailedReport")
	    		public ResponseEntity<BaseResponse<HttpStatus, ?>> getOMWorksHoaTSAgmtBillsDetailedReport( 
	    				@RequestParam Integer unitId, @RequestParam Integer approvedById, 
                        @RequestParam Integer scstFunds, @RequestParam Integer financialYear, 
                        @RequestParam Integer hoaId,  @RequestParam Integer workTypeId, @RequestParam Integer ProjSubType, 
                        @RequestParam Integer projectId, @RequestParam Integer type ){
	    		
	    			BaseResponse<HttpStatus, ?> response;
	    			ResponseEntity<BaseResponse<HttpStatus, ?>> responseEntity;
	    			if (type == 5 || type == 6) {
	    				 response = billsService.getOMWorkHoaBillsDetailedReport(unitId, approvedById, scstFunds, financialYear, hoaId, workTypeId, ProjSubType, projectId, type);
	    				responseEntity = new ResponseEntity<>(response, response.getStatus());
	    			}else {
	    		 	response= workDetailsService.getOMWorksHoaTSAgmtBillsDetailedReport(unitId,approvedById,scstFunds,financialYear,hoaId,workTypeId,ProjSubType,projectId,type);
	    		 	responseEntity = new ResponseEntity<>(response, response.getStatus());
	    			}
	    			return responseEntity;
	    		}
	    		
	    		@GetMapping("/O&MWorksSanctionTSAgmtBillsDetailedReport")
	    		public ResponseEntity<BaseResponse<HttpStatus, ?>> getOMWorksSanctionTSAgmtBillsDetailedReport( 
	    				@RequestParam Integer unitId, @RequestParam Integer approvedById, 
                        @RequestParam Integer scstFunds, @RequestParam Integer financialYear, 
                        @RequestParam Integer hoaId,  @RequestParam Integer workTypeId, @RequestParam Integer ProjSubType, 
                        @RequestParam Integer projectId, @RequestParam Integer type ){
	    		
	    			BaseResponse<HttpStatus, ?> response;
	    			ResponseEntity<BaseResponse<HttpStatus, ?>> responseEntity;
	    			if (type == 5 || type == 6) {
	    		 	response= billsService.getOMWorkSanctionBillsDetailedReport(unitId,approvedById,scstFunds,financialYear,hoaId,workTypeId,ProjSubType,projectId,type);
	    		 	responseEntity = new ResponseEntity<>(response, response.getStatus());
	    			}else {
	    				response= workDetailsService.getOMWorksSanctionTSAgmtBillsDetailedReport(unitId,approvedById,scstFunds,financialYear,hoaId,workTypeId,ProjSubType,projectId,type);
		    		 	responseEntity = new ResponseEntity<>(response, response.getStatus());
	    			}
	    			return responseEntity;
	    		}
	    		
	 
	    		@GetMapping("/getYearWiseReport")
	    		@ResponseBody
	    		public ResponseEntity<BaseResponse<HttpStatus, List<AdminSanctionViewModel>>> getYearWiseReport(
	    				@ModelAttribute AdminSanctionViewModel adminSanctionViewModel) {
	    			BaseResponse<HttpStatus, List<AdminSanctionViewModel>> response;
	    			Integer unitId, circleId, divisionId, subDivisionId = 0;
	    			unitId = adminSanctionViewModel.getUnitId() != null ? adminSanctionViewModel.getUnitId() : 0;
	    			circleId = adminSanctionViewModel.getCircleId() != null ? adminSanctionViewModel.getCircleId() : 0;
	    			divisionId = adminSanctionViewModel.getDivisionId() != null ? adminSanctionViewModel.getDivisionId() : 0;
	    			subDivisionId = adminSanctionViewModel.getSubDivisionId() != null ? adminSanctionViewModel.getSubDivisionId() : 0;
	    			response = adminViewService.getYearWiseReport( unitId, circleId, divisionId, subDivisionId);
	    			
	    			return new ResponseEntity<>(response, response.getStatus());
	    		}
	    		
	    		
	    		@GetMapping("/getWorkOverViewReport")
	    		@ResponseBody
	    		public ResponseEntity<BaseResponse<HttpStatus, List<WorkDetailsViewModel>>> getWorkOverViewReport(@ModelAttribute WorkDetailsViewModel workDetailsViewModel) {
	    			BaseResponse<HttpStatus, List<WorkDetailsViewModel>> response;
	    			Integer unitId, circleId, divisionId, subDivisionId = 0;
	    			unitId = workDetailsViewModel.getUnitId() != null ? workDetailsViewModel.getUnitId() : 0;
	    			circleId = workDetailsViewModel.getCircleId() != null ? workDetailsViewModel.getCircleId() : 0;
	    			divisionId = workDetailsViewModel.getDivisionId() != null ? workDetailsViewModel.getDivisionId() : 0;
	    			subDivisionId = workDetailsViewModel.getSubDivisionId() != null ? workDetailsViewModel.getSubDivisionId() : 0;
	    			response = workDetailsService.getWorkOverViewReport(unitId, circleId, divisionId, subDivisionId);
	    			return new ResponseEntity<>(response, response.getStatus());
	    		}
	    		
	    		@GetMapping("/getUnAssignedWorks")
	    		public ResponseEntity<BaseResponse<HttpStatus, List<AdminSanctionsModel>>> getUnAssignedWorks(
	    				@RequestParam Integer unit,@RequestParam Integer circle,@RequestParam Integer division,@RequestParam Integer subdivision) {
	    			BaseResponse<HttpStatus, List<AdminSanctionsModel>> response= new BaseResponse<>();
//	    			unit = unit != null ? unit: 0;
//	    			circle =circle != null ? circle : 0;
//	    			division = division != null ?division : 0;
//	    			subdivision = subdivision != null ? subdivision : 0;
	    			response =  adminSanctionService.getUnAssignedAdminSanctions(unit, circle, division, subdivision);
	    			return new ResponseEntity<>(response, response.getStatus());
	    		}
	    		
	    		@GetMapping("/getAdminSanctionAmounts")
	    		public ResponseEntity<BaseResponse<HttpStatus, List<AdminSanctionsModel>>> getAdminSanctionByUserByAuthorityByFinyear(
	    				@RequestParam Integer unit,@RequestParam Integer circle,@RequestParam Integer division,@RequestParam Integer subdivision,@RequestParam Integer approvedId, Integer finyear) {
	    			BaseResponse<HttpStatus, List<AdminSanctionsModel>> response;
	    			finyear = finyear!=null ? finyear : 0;
	    			unit = unit != null ? unit: 0;
	    			circle =circle != null ? circle : 0;
	    			division = division != null ?division : 0;
	    			subdivision = subdivision != null ? subdivision : 0;
	    			approvedId =approvedId != null ? approvedId: 0;
	    			response =  adminSanctionService.getAdminSanctionByUserByAuthorityByFinyear(unit,circle, division, subdivision,
	    					approvedId,finyear);
	    			return new ResponseEntity<>(response, response.getStatus());
	    		}
	    		@Autowired
	    		AssignAdminSanctionService assignAdminSanctionService; 
	    		
	    		@PostMapping("/submitAssignAdminSanction")
	    		public ResponseEntity<BaseResponse<HttpStatus, AdminAssignWorksModel>> submitAssignAdmiSanction(@RequestBody AdminAssignWorksModel assignModel){
	    			
	    			BaseResponse<HttpStatus, AdminAssignWorksModel>  response=new BaseResponse<>();
	    			if(assignModel!=null) {
	    				response=assignAdminSanctionService.insertAssignAdminSanctions(assignModel);
	    			}else {
	    				response.setMessage("Error in submission");
	    				response.setStatus(HttpStatus.BAD_REQUEST);
	    			}
	    			return new ResponseEntity<>(response, response.getStatus());
	    			
	    		}
	    		@GetMapping("/getAbsReportProjectUnitWise")
	    		@ResponseBody
	    		public ResponseEntity<BaseResponse<HttpStatus, List<WorkDetailsViewModel>>> getAbsReportProjectUnitWise(@ModelAttribute AdminSanctionViewModel adminSanctionViewModel) {
	    			BaseResponse<HttpStatus, List<WorkDetailsViewModel>> response;
	    			Integer finyear=0, projectId=0;
	    			finyear = adminSanctionViewModel.getFinancialYear()!=null ? adminSanctionViewModel.getFinancialYear() : 0;
	    			projectId = adminSanctionViewModel.getProjectId()!=null ? adminSanctionViewModel.getProjectId() :0;
	    			response = workDetailsService.getAbsReportProjectUnitWise(finyear, projectId);
	    			return new ResponseEntity<>(response, response.getStatus());
	    		}	
}
