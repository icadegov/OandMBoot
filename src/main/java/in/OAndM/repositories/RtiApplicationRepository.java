package in.OAndM.repositories;



import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.OAndM.core.BaseRepository;
import in.OAndM.services.impl.RTIApplicationServiceImpl;
import in.OAndM.Entities.RTIApplication;
import in.OAndM.Entities.RtiProformaG;


@Repository
public interface RtiApplicationRepository extends BaseRepository<RTIApplication, Integer> {
	
//	@Query("SELECT r FROM RTIApplication r WHERE r.deleteFlag = false")
//    List<RTIApplication> findAllActive();
//
//    @Query("SELECT r FROM RTIApplication r WHERE r.id = :id AND r.deleteFlag = false")
//    Optional<RTIApplication> findActiveById(@Param("id") Integer id);
	
	// No additional methods needed as findAllByDeleteFlagFalse and findByIdAndDeleteFlagFalse are inherited.

	

	    @Query("SELECT r FROM RTIApplication r WHERE r.applicationId = :id AND r.deleteFlag = false")
	    Optional<RTIApplication> findByIdAndDeleteFlagFalse(@Param("id") Integer id);
	    
	    
	    @Query("SELECT r FROM RTIApplication r WHERE r.deleteFlag = false")
	    List<RTIApplication> findAllByDeleteFlagFalse();
	    
	    //native query for find all with orderBy and limit
	    @Query(value = "SELECT * FROM rti_applications_register WHERE delete_flag = false ORDER BY application_id DESC LIMIT 10", nativeQuery = true)
	    List<RTIApplication> findTop10ByDeleteFlagFalse();
	    
	    
	    //derived query for above
	    List<RTIApplication> findTop10ByDeleteFlagFalseOrderByApplicationIdDesc();
	    
	    @Query(value = """
				SELECT application_id,application_num , application_date , applicant_name, applicant_addrs, pio_receipt_date, applicant_category, desc_info_req, 
third_party, application_fee, charges_collected, tot_amt, is_transferred, trans_date, trans_name, deemed_pio, info_part_full, rejection_date,
rar.rejected_section_id,rrs.rti_rejection_section, deemed_refusal,refused_date,
appeal_made, remarks, info_furnished_date,  unit_id, circle_id, division_id,designation_id, created_postid::bigint, subdivision_id,trans_mode,trans_amt   	
FROM public.rti_applications_register rar 
left join rti_rejection_status rrs on (rrs.reject_section_id=rar.rejected_section_id and rrs.delete_flag=false)  
where rar.delete_flag='false' and rar.is_latest='true' and designation_id=:desgId and division_id=:divId and circle_id=:circleId and unit_id=:unitId and rar.pio_receipt_date>= :fdate
union 
SELECT application_id,application_num , application_date , applicant_name, applicant_addrs, pio_receipt_date, applicant_category, desc_info_req, third_party, application_fee, charges_collected, tot_amt, is_transferred, trans_date, trans_name, deemed_pio, info_part_full, rejection_date, rar.rejected_section_id, rrs.rti_rejection_section, deemed_refusal,refused_date, appeal_made, remarks, info_furnished_date,  unit_id, circle_id, division_id,designation_id, created_postid::bigint, subdivision_id,trans_mode,trans_amt 
FROM public.rti_applications_register rar 
left join rti_rejection_status rrs on (rrs.reject_section_id=rar.rejected_section_id and rrs.delete_flag=false) 
where rar.delete_flag='false' and rar.is_latest='true' and designation_id=:desgId and is_transferred =''  and division_id=:divId and circle_id=:circleId and unit_id=:unitId 
and rar.pio_receipt_date<=:date order by pio_receipt_date
	 		   
	    		""", nativeQuery = true)   			  
	    			
	    		List<Map<String, Object>> getRTIAppnRegisterEntryListEE(@Param("desgId") Integer desgId, @Param("divId") Integer divId,@Param("circleId") Integer circleId,@Param("unitId") Integer unitId, @Param("fdate") Date fdate,@Param("date") Date date);
	    	
	    @Query(value = """
	    SELECT application_id,application_num , application_date , applicant_name, applicant_addrs, pio_receipt_date, applicant_category, desc_info_req,
	     third_party, application_fee, charges_collected, tot_amt, is_transferred, trans_date, trans_name, deemed_pio, info_part_full, 
	     rejection_date, rar.rejected_section_id,rrs.rti_rejection_section, deemed_refusal, appeal_made, remarks, info_furnished_date,  
	     unit_id, circle_id, division_id,designation_id, created_postid::bigint, subdivision_id,trans_mode,trans_amt,refused_date   
FROM public.rti_applications_register rar left join rti_rejection_status rrs on (rrs.reject_section_id=rar.rejected_section_id and rrs.delete_flag=false) 
where rar.delete_flag='false' and rar.is_latest='true'  and division_id=:divId and circle_id=:circleId and rar.unit_id=:unitId and designation_id=:desgId 
and EXTRACT(year FROM rar.pio_receipt_date)::int=:year and EXTRACT(quarter FROM rar.pio_receipt_date)::int=:quarter order by pio_receipt_date
	    	""", nativeQuery = true)
	    		
	    		
	    List<Map<String, Object>> getAppnYrQtrEEReport(@Param("desgId") Integer desgId,@Param("divId") Integer divId,  @Param("circleId") Integer circleId,
                @Param("unitId") Integer unitId, @Param("year") Integer year, @Param("quarter") Integer quarter );	
	    
	    @Query(value = """
	    	    SELECT application_id,application_num , application_date , applicant_name, applicant_addrs, pio_receipt_date, applicant_category, desc_info_req,
	    	     third_party, application_fee, charges_collected, tot_amt, is_transferred, trans_date, trans_name, deemed_pio, info_part_full, 
	    	     rejection_date, rar.rejected_section_id,rrs.rti_rejection_section, deemed_refusal, appeal_made, remarks, info_furnished_date,  
	    	     unit_id, circle_id, division_id,designation_id, created_postid::bigint, subdivision_id,trans_mode,trans_amt,refused_date   
	    FROM public.rti_applications_register rar left join rti_rejection_status rrs on (rrs.reject_section_id=rar.rejected_section_id and rrs.delete_flag=false) 
	    where rar.delete_flag='false' and rar.is_latest='true'  and division_id=:divId and circle_id=:circleId and rar.unit_id=:unitId and designation_id=:desgId 
	    and EXTRACT(year FROM rar.pio_receipt_date)::int=:year  order by pio_receipt_date
	    	    	""", nativeQuery = true)	    	    		
	    	    		
	    List<Map<String, Object>> getAppnYrEEReport(@Param("desgId") Integer desgId,@Param("divId") Integer divId,  @Param("circleId") Integer circleId,
	                    @Param("unitId") Integer unitId, @Param("year") Integer year);	
	    
	    


		List<RTIApplication> findByUnitAndCircleAndDivisionAndDesignationAndDeleteFlagFalseOrderByPioReceiptDate(
				Integer unit, Integer circle, Integer div, Integer desg);
		
		//working for getAppnYrQtrEEReport same case can be handled like this
		@Query(value = """
			    SELECT * 
			    FROM rti_applications_register rar 
			    WHERE rar.unit_id = :unit 
			      AND rar.circle_id = :circle 
			      AND rar.division_id = :div 
			      AND rar.designation_id = :desg 
			      AND rar.delete_flag = false 
			      AND EXTRACT(YEAR FROM rar.pio_receipt_date) = :yr
			      AND EXTRACT(QUARTER FROM rar.pio_receipt_date) = :qtr
			    ORDER BY rar.pio_receipt_date
			""", nativeQuery = true)
			List<RTIApplication> findByFilters(
			    @Param("unit") Integer unit,
			    @Param("circle") Integer circle,
			    @Param("div") Integer div,
			    @Param("desg") Integer desg,
			    @Param("yr") Integer year,
			    @Param("qtr") Integer quarter
			);

		@Query(value = """
	    	   select  coalesce(pending,0) as pending, coalesce(appreceived,0) as appreceived,coalesce((infofur+rej+trans+deemrefus),0 ) as disposed,infofur,deemrefus,sumtotamt,
	    	   rs1,rs2,rs3,rs4,rs5,rs6,rs7,rs8,rs9,rs10,rs11,rs12,rs13,rstot,unit_name,coalesce((trans) ,0)as six  ,n.unit_id,0,0,rs15 
	    	   from (select coalesce(p,0) as pending,coalesce(appreceived,0) as appreceived, coalesce(infofur,0) as infofur ,coalesce(rej,0) as rej ,
	    	   coalesce(deemrefus,0 )as deemrefus  ,	  coalesce(sumtotamt,0) as sumtotamt , coalesce(trans,0) as trans ,coalesce(rs1,0) as rs1,coalesce(rs2,0) as rs2,
	    	   coalesce(rs3,0) as rs3,coalesce(rs4,0) as rs4, coalesce(rs5,0) as  rs5,coalesce(rs6,0) as  rs6,coalesce(rs7,0) as  rs7,	coalesce(rs8,0) as  rs8,
	    	   coalesce(rs9,0) as rs9,coalesce(rs10,0) as rs10, coalesce(rs11,0) as rs11,coalesce(rs12,0) as rs12,coalesce(rs13,0) as rs13,	coalesce(rstot,0) as rstot,
	    	   unit_name, u.unit_id,0,0,coalesce(rs15,0) as rs15 from unit_mst u left join ( select unit_id,coalesce(sum(appreceived),0 )as appreceived,  
	    	   coalesce(sum(sumtotamt) ,0)as sumtotamt from (SELECT count(distinct application_id) as appreceived,  
	    	   (sum( application_fee)+ sum( charges_collected) ) as sumtotamt, unit_id  
	    	    FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.pio_receipt_date)::int=:year 
	    	    and EXTRACT(quarter FROM rar.pio_receipt_date)::int=:qtr group by unit_id) b group by unit_id) e on e.unit_id=u.unit_id 

left join ( select unit_id, coalesce(sum(rs1),0)as rs1,coalesce(sum(rs2),0) as rs2,coalesce(sum(rs3),0) as rs3,coalesce(sum(rs4),0) as rs4,  coalesce(sum(rs5),0) as rs5,
coalesce(sum(rs6),0) as rs6,coalesce(sum(rs7),0) as rs7,coalesce(sum(rs8),0) as rs8,  coalesce(sum(rs9),0) as rs9, coalesce(sum(rs10),0) as rs10,  
coalesce(sum(rs11),0) as rs11,coalesce(sum(rs12),0) as rs12, coalesce(sum(rs13),0) as rs13,coalesce(sum(rs15),0) as rs15, coalesce(sum(rstot),0) as rstot  
from (SELECT unit_id , case when rejected_section_id=1 then count(application_id) end as rs1, case when rejected_section_id=2 then count(application_id) end as rs2, 
case when rejected_section_id=3 then count(application_id) end as rs3, case when rejected_section_id=4 then count(application_id) end as rs4,  
case when rejected_section_id=5 then count(application_id) end as rs5, case when rejected_section_id=6 then count(application_id) end as rs6, 
case when rejected_section_id=7 then count(application_id) end as rs7, case when rejected_section_id=8 then count(application_id) end as rs8,  
case when rejected_section_id=9 then count(application_id) end as rs9,  case when rejected_section_id=10 then count(application_id) end as rs10, 
case when rejected_section_id=11 then count(application_id) end as rs11, case when rejected_section_id=12 then count(application_id) end as rs12,  
case when rejected_section_id=13 then count(application_id) end as rs13, case when rejected_section_id=15 then count(application_id) end as rs15, 
case when rejected_section_id in (1,2,3,4,5,6,7,8,9,10,11,12,13,15) then count(application_id) end as rstot  
FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.rejection_date)::int=:year 
and EXTRACT(quarter FROM rar.rejection_date)::int=:qtr group by unit_id,rejected_section_id,rejection_date) b group by unit_id) rjd on rjd.unit_id=u.unit_id 

left join  ( select unit_id, coalesce(sum(infofur),0) as infofur 
from (SELECT case when is_transferred!='Yes' and info_furnished_date is not null then  count( application_id) end as infofur, unit_id  
FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.info_furnished_date)::int=:year 
and EXTRACT(quarter FROM rar.info_furnished_date)::int=:qtr group by unit_id,is_transferred,info_furnished_date) b group by unit_id) i on i.unit_id=u.unit_id 

left join  ( select unit_id, coalesce(sum(rej),0) as rej from (SELECT  case when rejection_date is not null then count(application_id) end as  rej ,unit_id
 FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.rejection_date)::int=:year 
 and EXTRACT(quarter FROM rar.rejection_date)::int=:qtr  group by unit_id,rejection_date) b group by unit_id) r on r.unit_id=u.unit_id 
 
 left join  ( select unit_id, coalesce(sum(trans),0) as trans 
 from (SELECT case when is_transferred='Yes' and trans_date is not null then ( count( application_id) ) end as trans,  unit_id 
 FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and EXTRACT(year FROM rar.trans_date)::int=:year 
 and EXTRACT(quarter FROM rar.trans_date)::int=:qtr group by unit_id,is_transferred,trans_date) b group by unit_id) tr on tr.unit_id=u.unit_id 

left join  ( select unit_id, coalesce(sum(deemrefus),0) as deemrefus 
from (SELECT  case when deemed_refusal='7(2)' or  deemed_refusal= '18(1)' then (count( application_id) )  end as deemrefus,  unit_id 
FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and EXTRACT(year FROM rar.refused_date)::int=:year 
and EXTRACT(quarter FROM rar.refused_date)::int=:qtr group by unit_id,deemed_refusal,refused_date) b group by unit_id) dr on dr.unit_id=u.unit_id 

  left  join  ( select pe.unit_id,coalesce(coalesce(preappn,0)-(coalesce(infofur_pr,0)+coalesce(rej_pr,0)+coalesce(trans_pr,0)+coalesce(deemrefus_pr,0)),0) as p 
  from (select unit_id,coalesce(sum(preappn),0) as preappn from ( select count(application_id) as preappn,unit_id 
  from rti_applications_register where delete_flag=false and is_latest=true  and pio_receipt_date::date <= :dt group by unit_id) pr group by unit_id) pe  
  left join (select unit_id, coalesce(sum(infofur),0) as infofur_pr from (SELECT case when is_transferred!='Yes' and info_furnished_date is not null then  
  count( application_id) end as infofur, unit_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  
  and  rar.info_furnished_date<= :dt group by unit_id,is_transferred,info_furnished_date) b group by unit_id)tr on tr.unit_id=pe.unit_id 

left join (select unit_id, coalesce(sum(rej),0) as rej_pr from (SELECT  case when rejection_date is not null then count(application_id) end as  rej ,unit_id 
FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.rejection_date<= :dt group by unit_id,rejection_date)b   
group by unit_id)rj on rj.unit_id=pe.unit_id 
left join ( select unit_id, coalesce(sum(trans),0) as trans_pr 
from (SELECT case when is_transferred='Yes'  and trans_date is not null then ( count( application_id) ) end as trans,  unit_id  
FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and rar.trans_date <=:dt group by unit_id,is_transferred,trans_date) b 
group by unit_id) tn on tn.unit_id=pe.unit_id left join (select unit_id,sum(deemrefus) as deemrefus_pr 
from (select unit_id ,count( application_id) as deemrefus  from rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  
and (deemed_refusal='7(2)' or  deemed_refusal= '18(1)') 
and  rar.refused_date<= :dt  group by unit_id,refused_date)b  group by unit_id )dr on dr.unit_id=pe.unit_id)c on c.unit_id=u.unit_id  ) n 
where n.unit_id not in (9808,30)  order by n.unit_name desc	   	   
	    	   	    	    	""", nativeQuery = true)
		

		List<Map<String, Object>> getrtiAppnConsolidatedProformaC(@Param("year") Integer year,@Param("qtr") Integer qtr, @Param("dt") Timestamp timestamp);
		@Query(value = """
		    	  select coalesce(pending,0) as pending, coalesce(appreceived,0) as appreceived,coalesce((infofur+rej+trans+deemrefus),0) as disposed,infofur,deemrefus,sumtotamt,rs1,rs2,rs3,rs4,rs5,rs6,rs7,rs8,rs9,rs10,rs11,rs12,rs13,rstot,n.unit_name,coalesce((trans) ,0)as six,   n.unit_id,n.circle_id,n.division_id,rs15 from  ( select d.unit_id, coalesce(p,0) as pending,coalesce(appreceived,0)as appreceived,coalesce(infofur,0) as infofur ,coalesce(rej,0) as rej,coalesce(trans,0) as trans, coalesce(deemrefus,0) as deemrefus, coalesce(sumtotamt,0) as sumtotamt, coalesce(rs1,0) as rs1,coalesce(rs2,0) as rs2,coalesce(rs3,0) as rs3,coalesce(rs4,0) as rs4,coalesce(rs5,0) as rs5,coalesce(rs6,0) as rs6,coalesce(rs7,0) as rs7,coalesce(rs8,0) as rs8,coalesce(rs9,0) as rs9,coalesce(rs10,0) as rs10, coalesce(rs11,0) as rs11,coalesce(rs12,0) as rs12,coalesce(rs13,0) as rs13,coalesce(rstot,0) as rstot,coalesce(rs15,0) as rs15,d.unit_name, d.circle_id,d.division_id from (select distinct r.unit_id,circle_id,division_id,u.unit_name from rti_applications_register r 
left join unit_mst u on (u.unit_id=r.unit_id and u.delete_flag='f' and u.status='ACTIVE') where r.delete_flag='f'and r.is_latest='t')d 

left join  ( select unit_id,circle_id,division_id,coalesce(sum(appreceived),0 )as appreceived,  coalesce(sum(sumtotamt),0) as sumtotamt from (SELECT count(distinct application_id) as appreceived, (sum( application_fee)+ sum( charges_collected) ) as sumtotamt,unit_id,circle_id ,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.pio_receipt_date)::int=:year and EXTRACT(quarter FROM rar.pio_receipt_date)::int=:qtr group by unit_id,circle_id,division_id) b group by unit_id,circle_id,division_id) e on e.unit_id=d.unit_id and e.circle_id=d.circle_id and e.division_id=d.division_id 

left join  ( select unit_id,circle_id,division_id, coalesce(sum(rs1),0)as rs1,coalesce(sum(rs2),0) as rs2,coalesce(sum(rs3),0) as rs3,coalesce(sum(rs4),0) as rs4, coalesce(sum(rs5),0) as rs5,coalesce(sum(rs6),0) as rs6,coalesce(sum(rs7),0) as rs7,coalesce(sum(rs8),0) as rs8,coalesce(sum(rs9),0) as rs9, coalesce(sum(rs10),0) as rs10,coalesce(sum(rs11),0) as rs11, coalesce(sum(rs12),0) as rs12, coalesce(sum(rs13),0) as rs13,coalesce(sum(rs15),0) as rs15, coalesce(sum(rstot),0) as rstot from (SELECT  case when rejected_section_id=1 then count(application_id) end as rs1, case when rejected_section_id=2 then count(application_id) end as rs2,case when rejected_section_id=3 then count(application_id) end as rs3, case when rejected_section_id=4 then count(application_id) end as rs4, case when rejected_section_id=5 then count(application_id) end as rs5, case when rejected_section_id=6 then count(application_id) end as rs6, case when rejected_section_id=7 then count(application_id) end as rs7, case when rejected_section_id=8 then count(application_id) end as rs8, case when rejected_section_id=9 then count(application_id) end as rs9,  case when rejected_section_id=10 then count(application_id) end as rs10, case when rejected_section_id=11 then count(application_id) end as rs11, case when rejected_section_id=12 then count(application_id) end as rs12, case when rejected_section_id=13 then count(application_id) end as rs13, case when rejected_section_id=15 then count(application_id) end as rs15,case when rejected_section_id in (1,2,3,4,5,6,7,8,9,10,11,12,13,15) then count(application_id) end as rstot  ,unit_id,circle_id ,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.rejection_date)::int=:year and EXTRACT(quarter FROM rar.rejection_date)::int=:qtr group by unit_id,circle_id,division_id,rejected_section_id,rejection_date) b group by unit_id,circle_id,division_id) rjd on rjd.unit_id=d.unit_id and rjd.circle_id=d.circle_id and rjd.division_id=d.division_id 

left join  ( select unit_id,circle_id,division_id,coalesce(sum(infofur),0) as infofur from (SELECT 	case when is_transferred!='Yes' and info_furnished_date is not null then ( count( application_id) ) end as infofur, unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and EXTRACT(year FROM rar.info_furnished_date)::int=:year and EXTRACT(quarter FROM rar.info_furnished_date)::int=:qtr group by unit_id,is_transferred,circle_id,division_id,info_furnished_date) b group by unit_id,circle_id,division_id) i on i.unit_id=d.unit_id and i.circle_id=d.circle_id and i.division_id=d.division_id

left join ( select unit_id,circle_id,division_id, coalesce(sum(rej),0) as rej from (SELECT  case when is_transferred!='Yes' and rejection_date is not null then count(application_id) end as  rej ,unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.rejection_date)::int=:year and EXTRACT(quarter FROM rar.rejection_date)::int=:qtr  group by unit_id,is_transferred,circle_id,division_id,rejection_date) b group by unit_id,circle_id,division_id) r on r.unit_id=d.unit_id and r.circle_id=d.circle_id and r.division_id=d.division_id 

left join  ( select unit_id,circle_id,division_id,coalesce(sum(trans),0) as trans from (SELECT    case when is_transferred='Yes' and trans_date is not null then ( count( application_id) ) end as trans,unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.trans_date)::int=:year and EXTRACT(quarter FROM rar.trans_date)::int=:qtr  group by unit_id,is_transferred,circle_id,division_id,trans_date) b group by unit_id,circle_id,division_id) tr  on tr.unit_id=d.unit_id and tr.circle_id=d.circle_id and tr.division_id=d.division_id  
left join  ( select unit_id,circle_id,division_id,coalesce(sum(deemrefus),0) as deemrefus from (SELECT    case when deemed_refusal='7(2)' or  deemed_refusal= '18(1)' then (count( application_id) ) end as deemrefus,division_id,unit_id,circle_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.refused_date)::int=:year and EXTRACT(quarter FROM rar.refused_date)::int=:qtr group by unit_id,deemed_refusal,circle_id,division_id,refused_date) b group by unit_id,circle_id,division_id) dr on dr.unit_id=d.unit_id and dr.circle_id=d.circle_id and dr.division_id=d.division_id 

left join  ( select pe.unit_id,pe.circle_id,pe.division_id, coalesce(coalesce(preappn,0)-(coalesce(infofur_pr,0)+coalesce(rej_pr,0)+coalesce(trans_pr,0)+coalesce(deemrefus_pr,0)),0) as p from (select unit_id,circle_id,division_id, coalesce(sum(preappn),0) as preappn from ( select count(application_id) as preappn,unit_id,division_id,circle_id from rti_applications_register where delete_flag=false and is_latest=true and pio_receipt_date::date <=:dt  group by unit_id,circle_id,division_id) pr group by unit_id,circle_id,division_id) pe 
			left join (select unit_id,circle_id,division_id, coalesce(sum(infofur),0) as infofur_pr from (SELECT case when is_transferred!='Yes' and info_furnished_date is not null then  count( application_id) end as infofur, unit_id,circle_id ,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.info_furnished_date<=:dt group by unit_id,circle_id,division_id,is_transferred,info_furnished_date) b  group by unit_id,circle_id,division_id)tr on tr.unit_id=pe.unit_id and tr.circle_id=pe.circle_id  and tr.division_id=pe.division_id 
			left join (select unit_id,circle_id,division_id, coalesce(sum(rej),0) as rej_pr from (SELECT  case when rejection_date is not null then count(application_id) end as  rej ,unit_id ,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.rejection_date<=:dt group by unit_id,circle_id,division_id,rejection_date)b group by unit_id,circle_id,division_id)rj on rj.unit_id=pe.unit_id and rj.circle_id=pe.circle_id and rj.division_id=pe.division_id 
			left join ( select unit_id,circle_id,division_id, coalesce(sum(trans),0) as trans_pr from (SELECT case when is_transferred='Yes'  and trans_date is not null then ( count( application_id) ) end as trans,  unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and rar.trans_date <=:dt group by unit_id,circle_id,division_id,is_transferred,trans_date) b group by unit_id,circle_id,division_id) tn on tn.unit_id=pe.unit_id and tn.circle_id=pe.circle_id and tn.division_id=pe.division_id 
			left join (select unit_id,circle_id,division_id,sum(deemrefus) as deemrefus_pr from (select unit_id ,circle_id,division_id,count( application_id) as deemrefus from rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and  rar.refused_date<=:dt  and (deemed_refusal='7(2)' or  deemed_refusal= '18(1)') group by unit_id,refused_date,circle_id,division_id)b group by unit_id,circle_id,division_id )dr on dr.unit_id=pe.unit_id and dr.circle_id=pe.circle_id and dr.division_id=pe.division_id)c  on c.unit_id=d.unit_id and c.circle_id=d.circle_id and c.division_id=d.division_id)n	   
			where n.unit_id=:unit and n.circle_id=:circle
		    	   	    	    	""", nativeQuery = true)
			List<Map<String, Object>> getrtiAppnDivisionUCConsolidatedProformaC(@Param("year") Integer year,@Param("qtr") Integer qtr,@Param("dt") Timestamp timestamp, @Param("unit") Integer unit,@Param("circle") Integer circle );
		
		@Query(value = """
		    	   select coalesce(pending,0) as pending, coalesce(appreceived,0) as appreceived,coalesce((infofur+rej+trans+deemrefus),0) as disposed,infofur,deemrefus,sumtotamt,rs1,rs2,rs3,rs4,rs5,rs6,rs7,rs8,rs9,rs10,rs11,rs12,rs13,rstot,n.unit_name,coalesce((trans) ,0)as six,   n.unit_id,n.circle_id,n.division_id,rs15 from  ( select d.unit_id, coalesce(p,0) as pending,coalesce(appreceived,0)as appreceived,coalesce(infofur,0) as infofur ,coalesce(rej,0) as rej,coalesce(trans,0) as trans, coalesce(deemrefus,0) as deemrefus, coalesce(sumtotamt,0) as sumtotamt, coalesce(rs1,0) as rs1,coalesce(rs2,0) as rs2,coalesce(rs3,0) as rs3,coalesce(rs4,0) as rs4,coalesce(rs5,0) as rs5,coalesce(rs6,0) as rs6,coalesce(rs7,0) as rs7,coalesce(rs8,0) as rs8,coalesce(rs9,0) as rs9,coalesce(rs10,0) as rs10, coalesce(rs11,0) as rs11,coalesce(rs12,0) as rs12,coalesce(rs13,0) as rs13,coalesce(rstot,0) as rstot,coalesce(rs15,0) as rs15,d.unit_name, d.circle_id,d.division_id from (select distinct r.unit_id,circle_id,division_id,u.unit_name from rti_applications_register r 
left join unit_mst u on (u.unit_id=r.unit_id and u.delete_flag='f' and u.status='ACTIVE') where r.delete_flag='f'and r.is_latest='t')d 

left join  ( select unit_id,circle_id,division_id,coalesce(sum(appreceived),0 )as appreceived,  coalesce(sum(sumtotamt),0) as sumtotamt from (SELECT count(distinct application_id) as appreceived, (sum( application_fee)+ sum( charges_collected) ) as sumtotamt,unit_id,circle_id ,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.pio_receipt_date)::int=:year and EXTRACT(quarter FROM rar.pio_receipt_date)::int=:qtr group by unit_id,circle_id,division_id) b group by unit_id,circle_id,division_id) e on e.unit_id=d.unit_id and e.circle_id=d.circle_id and e.division_id=d.division_id 

left join  ( select unit_id,circle_id,division_id, coalesce(sum(rs1),0)as rs1,coalesce(sum(rs2),0) as rs2,coalesce(sum(rs3),0) as rs3,coalesce(sum(rs4),0) as rs4, coalesce(sum(rs5),0) as rs5,coalesce(sum(rs6),0) as rs6,coalesce(sum(rs7),0) as rs7,coalesce(sum(rs8),0) as rs8,coalesce(sum(rs9),0) as rs9, coalesce(sum(rs10),0) as rs10,coalesce(sum(rs11),0) as rs11, coalesce(sum(rs12),0) as rs12, coalesce(sum(rs13),0) as rs13,coalesce(sum(rs15),0) as rs15, coalesce(sum(rstot),0) as rstot from (SELECT  case when rejected_section_id=1 then count(application_id) end as rs1, case when rejected_section_id=2 then count(application_id) end as rs2,case when rejected_section_id=3 then count(application_id) end as rs3, case when rejected_section_id=4 then count(application_id) end as rs4, case when rejected_section_id=5 then count(application_id) end as rs5, case when rejected_section_id=6 then count(application_id) end as rs6, case when rejected_section_id=7 then count(application_id) end as rs7, case when rejected_section_id=8 then count(application_id) end as rs8, case when rejected_section_id=9 then count(application_id) end as rs9,  case when rejected_section_id=10 then count(application_id) end as rs10, case when rejected_section_id=11 then count(application_id) end as rs11, case when rejected_section_id=12 then count(application_id) end as rs12, case when rejected_section_id=13 then count(application_id) end as rs13, case when rejected_section_id=15 then count(application_id) end as rs15,case when rejected_section_id in (1,2,3,4,5,6,7,8,9,10,11,12,13,15) then count(application_id) end as rstot  ,unit_id,circle_id ,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.rejection_date)::int=:year and EXTRACT(quarter FROM rar.rejection_date)::int=:qtr group by unit_id,circle_id,division_id,rejected_section_id,rejection_date) b group by unit_id,circle_id,division_id) rjd on rjd.unit_id=d.unit_id and rjd.circle_id=d.circle_id and rjd.division_id=d.division_id 

left join  ( select unit_id,circle_id,division_id,coalesce(sum(infofur),0) as infofur from (SELECT 	case when is_transferred!='Yes' and info_furnished_date is not null then ( count( application_id) ) end as infofur, unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and EXTRACT(year FROM rar.info_furnished_date)::int=:year and EXTRACT(quarter FROM rar.info_furnished_date)::int=:qtr group by unit_id,is_transferred,circle_id,division_id,info_furnished_date) b group by unit_id,circle_id,division_id) i on i.unit_id=d.unit_id and i.circle_id=d.circle_id and i.division_id=d.division_id

left join ( select unit_id,circle_id,division_id, coalesce(sum(rej),0) as rej from (SELECT  case when is_transferred!='Yes' and rejection_date is not null then count(application_id) end as  rej ,unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.rejection_date)::int=:year and EXTRACT(quarter FROM rar.rejection_date)::int=:qtr  group by unit_id,is_transferred,circle_id,division_id,rejection_date) b group by unit_id,circle_id,division_id) r on r.unit_id=d.unit_id and r.circle_id=d.circle_id and r.division_id=d.division_id 

left join  ( select unit_id,circle_id,division_id,coalesce(sum(trans),0) as trans from (SELECT    case when is_transferred='Yes' and trans_date is not null then ( count( application_id) ) end as trans,unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.trans_date)::int=:year and EXTRACT(quarter FROM rar.trans_date)::int=:qtr  group by unit_id,is_transferred,circle_id,division_id,trans_date) b group by unit_id,circle_id,division_id) tr  on tr.unit_id=d.unit_id and tr.circle_id=d.circle_id and tr.division_id=d.division_id  
left join  ( select unit_id,circle_id,division_id,coalesce(sum(deemrefus),0) as deemrefus from (SELECT    case when deemed_refusal='7(2)' or  deemed_refusal= '18(1)' then (count( application_id) ) end as deemrefus,division_id,unit_id,circle_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.refused_date)::int=:year and EXTRACT(quarter FROM rar.refused_date)::int=:qtr group by unit_id,deemed_refusal,circle_id,division_id,refused_date) b group by unit_id,circle_id,division_id) dr on dr.unit_id=d.unit_id and dr.circle_id=d.circle_id and dr.division_id=d.division_id 

left join  ( select pe.unit_id,pe.circle_id,pe.division_id, coalesce(coalesce(preappn,0)-(coalesce(infofur_pr,0)+coalesce(rej_pr,0)+coalesce(trans_pr,0)+coalesce(deemrefus_pr,0)),0) as p from (select unit_id,circle_id,division_id, coalesce(sum(preappn),0) as preappn from ( select count(application_id) as preappn,unit_id,division_id,circle_id from rti_applications_register where delete_flag=false and is_latest=true and pio_receipt_date::date <=:dt  group by unit_id,circle_id,division_id) pr group by unit_id,circle_id,division_id) pe 
			left join (select unit_id,circle_id,division_id, coalesce(sum(infofur),0) as infofur_pr from (SELECT case when is_transferred!='Yes' and info_furnished_date is not null then  count( application_id) end as infofur, unit_id,circle_id ,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.info_furnished_date<=:dt group by unit_id,circle_id,division_id,is_transferred,info_furnished_date) b  group by unit_id,circle_id,division_id)tr on tr.unit_id=pe.unit_id and tr.circle_id=pe.circle_id  and tr.division_id=pe.division_id 
			left join (select unit_id,circle_id,division_id, coalesce(sum(rej),0) as rej_pr from (SELECT  case when rejection_date is not null then count(application_id) end as  rej ,unit_id ,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.rejection_date<=:dt group by unit_id,circle_id,division_id,rejection_date)b group by unit_id,circle_id,division_id)rj on rj.unit_id=pe.unit_id and rj.circle_id=pe.circle_id and rj.division_id=pe.division_id 
			left join ( select unit_id,circle_id,division_id, coalesce(sum(trans),0) as trans_pr from (SELECT case when is_transferred='Yes'  and trans_date is not null then ( count( application_id) ) end as trans,  unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and rar.trans_date <=:dt group by unit_id,circle_id,division_id,is_transferred,trans_date) b group by unit_id,circle_id,division_id) tn on tn.unit_id=pe.unit_id and tn.circle_id=pe.circle_id and tn.division_id=pe.division_id 
			left join (select unit_id,circle_id,division_id,sum(deemrefus) as deemrefus_pr from (select unit_id ,circle_id,division_id,count( application_id) as deemrefus from rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and  rar.refused_date<=:dt  and (deemed_refusal='7(2)' or  deemed_refusal= '18(1)') group by unit_id,refused_date,circle_id,division_id)b group by unit_id,circle_id,division_id )dr on dr.unit_id=pe.unit_id and dr.circle_id=pe.circle_id and dr.division_id=pe.division_id)c  on c.unit_id=d.unit_id and c.circle_id=d.circle_id and c.division_id=d.division_id)n	   
			where n.unit_id=:unit and n.circle_id=:circle and n.division_id=:division
		    	   	    	    	""", nativeQuery = true)
			List<Map<String, Object>> getrtiAppnDivisionUCDConsolidatedProformaC(@Param("year") Integer year,@Param("qtr") Integer quarter, @Param("dt") Timestamp timestamp, @Param("unit") Integer unit,@Param("circle") Integer circle,@Param("division") Integer division );
		
		@Query(value = """
		    	 select coalesce(pending,0) as pending,coalesce(appreceived,0) as appreceived,coalesce((infofur+rej+trans+deemrefus),0) as disposed,infofur,deemrefus,sumtotamt, rs1,rs2,rs3,rs4,rs5,rs6,rs7,rs8,rs9,rs10,rs11,rs12,rs13,rstot,n.unit_name,coalesce((trans) ,0)as six,   n.unit_id,n.circle_id,0,rs15 from  ( select d.unit_id, coalesce(p,0) as pending,coalesce(appreceived,0) as appreceived ,coalesce(infofur,0) as infofur ,coalesce(rej,0) as rej,coalesce(trans,0) as trans,coalesce(deemrefus,0 )as deemrefus, coalesce(sumtotamt,0) as sumtotamt,coalesce(rs1,0) as rs1,coalesce(rs2,0) as rs2,coalesce(rs3,0) as rs3,coalesce(rs4,0) as rs4,coalesce(rs5,0) as rs5,coalesce(rs6,0) as rs6, coalesce(rs7,0) as rs7,coalesce(rs8,0) as rs8,coalesce(rs9,0) as rs9,coalesce(rs10,0) as rs10,coalesce(rs11,0) as rs11,coalesce(rs12,0) as rs12,coalesce(rs13,0) as rs13, coalesce(rs15,0) as rs15, coalesce(rstot,0) as rstot,d.unit_name, d.circle_id  from (select distinct r.unit_id,circle_id,u.unit_name from rti_applications_register r 
left join unit_mst u on (u.unit_id=r.unit_id and u.delete_flag='f' and u.status='ACTIVE') where r.delete_flag='f'and r.is_latest='t')d 

left join  ( select unit_id,circle_id,coalesce(sum(appreceived),0 )as appreceived, coalesce(sum(sumtotamt),0) as sumtotamt from (SELECT count(distinct application_id) as appreceived, (sum( application_fee)+ sum( charges_collected) ) as sumtotamt, unit_id,circle_id  FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and EXTRACT(year FROM rar.pio_receipt_date)::int=:year and EXTRACT(quarter FROM rar.pio_receipt_date)::int=:qtr group by unit_id,circle_id) b group by unit_id,circle_id) e on e.unit_id=d.unit_id and e.circle_id=d.circle_id 

left join  ( select unit_id,circle_id,coalesce(sum(rs1),0)as rs1,coalesce(sum(rs2),0) as rs2,coalesce(sum(rs3),0) as rs3,coalesce(sum(rs4),0) as rs4,  coalesce(sum(rs5),0) as rs5,  coalesce(sum(rs6),0) as rs6,coalesce(sum(rs7),0) as rs7,coalesce(sum(rs8),0) as rs8,coalesce(sum(rs9),0) as rs9, coalesce(sum(rs10),0) as rs10,   coalesce(sum(rs11),0) as rs11,coalesce(sum(rs12),0) as rs12, coalesce(sum(rs13),0) as rs13,coalesce(sum(rs15),0) as rs15, coalesce(sum(rstot),0) as rstot from (SELECT  case when rejected_section_id=1 then count(application_id) end as rs1, case when rejected_section_id=2 then count(application_id) end as rs2, case when rejected_section_id=3 then count(application_id) end as rs3, case when rejected_section_id=4 then count(application_id) end as rs4, case when rejected_section_id=5 then count(application_id) end as rs5, case when rejected_section_id=6 then count(application_id) end as rs6, case when rejected_section_id=7 then count(application_id) end as rs7, case when rejected_section_id=8 then count(application_id) end as rs8, case when rejected_section_id=9 then count(application_id) end as rs9,  case when rejected_section_id=10 then count(application_id) end as rs10, case when rejected_section_id=11 then count(application_id) end as rs11, case when rejected_section_id=12 then count(application_id) end as rs12,  case when rejected_section_id=13 then count(application_id) end as rs13, case when rejected_section_id=15 then count(application_id) end as rs15, case when rejected_section_id in (1,2,3,4,5,6,7,8,9,10,11,12,13,15) then count(application_id) end as rstot  ,unit_id,circle_id  FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and EXTRACT(year FROM rar.rejection_date)::int=:year and EXTRACT(quarter FROM rar.rejection_date)::int=:qtr group by unit_id,circle_id,rejected_section_id,rejection_date) b group by unit_id,circle_id) rjd on rjd.unit_id=d.unit_id and rjd.circle_id=d.circle_id 

left join  ( select unit_id,circle_id,coalesce(sum(infofur),0) as infofur from (SELECT 	case when is_transferred!='Yes' and info_furnished_date is not null then ( count( application_id) ) end as infofur, unit_id,circle_id   FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.info_furnished_date)::int=:year and EXTRACT(quarter FROM rar.info_furnished_date)::int=:qtr group by unit_id,is_transferred,circle_id,info_furnished_date) b group by unit_id,circle_id) i on i.unit_id=d.unit_id and i.circle_id=d.circle_id 

left join ( select unit_id,circle_id, coalesce(sum(rej),0) as rej from (SELECT  case when is_transferred!='Yes' and rejection_date is not null then count(application_id) end as  rej ,unit_id,circle_id  FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.rejection_date)::int=:year and EXTRACT(quarter FROM rar.rejection_date)::int=:qtr  group by unit_id,is_transferred,circle_id,rejection_date) b group by unit_id,circle_id) r on r.unit_id=d.unit_id and r.circle_id=d.circle_id

left join  ( select unit_id,circle_id,coalesce(sum(trans),0) as trans from (SELECT    case when is_transferred='Yes' and trans_date is not null then ( count( application_id) ) end as trans,unit_id,circle_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true   and EXTRACT(year FROM rar.trans_date)::int=:year and EXTRACT(quarter FROM rar.trans_date)::int=:qtr group by unit_id,is_transferred,circle_id,trans_date) b group by unit_id,circle_id) tr on tr.unit_id=d.unit_id and tr.circle_id=d.circle_id 

left join  ( select unit_id,circle_id,coalesce(sum(deemrefus),0) as deemrefus from (SELECT    case when deemed_refusal='7(2)' or  deemed_refusal= '18(1)' then (count( application_id) ) end as deemrefus,unit_id,circle_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true   and EXTRACT(year FROM rar.refused_date)::int=:year and EXTRACT(quarter FROM rar.refused_date)::int=:qtr group by unit_id,deemed_refusal,circle_id,refused_date) b group by unit_id,circle_id) dr on dr.unit_id=d.unit_id and dr.circle_id=d.circle_id 

left join  ( select pe.unit_id,pe.circle_id, coalesce(coalesce(preappn,0)-(coalesce(infofur_pr,0)+coalesce(rej_pr,0)+coalesce(trans_pr,0)+coalesce(deemrefus_pr,0)),0) as p from (select unit_id,circle_id, coalesce(sum(preappn),0) as preappn  from ( select count(application_id) as preappn,unit_id,circle_id from rti_applications_register where delete_flag=false and is_latest=true  and pio_receipt_date::date <= :dt group by unit_id,circle_id) pr  group by unit_id,circle_id) pe 	
			left join (select unit_id,circle_id, coalesce(sum(infofur),0) as infofur_pr from (SELECT case when is_transferred!='Yes' and info_furnished_date is not null then  count( application_id) end as infofur, unit_id,circle_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.info_furnished_date<= :dt group by unit_id,circle_id,is_transferred,info_furnished_date) b group by unit_id,circle_id)tr on tr.unit_id=pe.unit_id and tr.circle_id=pe.circle_id 
			left join (select unit_id,circle_id, coalesce(sum(rej),0) as rej_pr from (SELECT  case when rejection_date is not null then count(application_id) end as  rej ,unit_id ,circle_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.rejection_date<= :dt group by unit_id,circle_id,rejection_date)b group by unit_id,circle_id)rj on rj.unit_id=pe.unit_id and rj.circle_id=pe.circle_id 
			left join ( select unit_id,circle_id, coalesce(sum(trans),0) as trans_pr from (SELECT case when is_transferred='Yes'  and trans_date is not null then ( count( application_id) ) end as trans,  unit_id,circle_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and rar.trans_date <=:dt group by unit_id,circle_id,is_transferred,trans_date) b group by unit_id,circle_id) tn on tn.unit_id=pe.unit_id and tn.circle_id=pe.circle_id 
			left join (select unit_id,circle_id,sum(deemrefus) as deemrefus_pr from (select unit_id ,circle_id,count( application_id) as deemrefus  from rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.refused_date<= :dt and (deemed_refusal='7(2)' or  deemed_refusal= '18(1)') group by unit_id,refused_date,circle_id)b  group by unit_id,circle_id )dr on dr.unit_id=pe.unit_id and dr.circle_id=pe.circle_id )c on c.unit_id=d.unit_id and c.circle_id=d.circle_id )n
			
			where n.unit_id=:unit 
		    	   	    	    	""", nativeQuery = true)
			List<Map<String, Object>> getrtiAppnCircleConsolidatedProformaC(@Param("year") Integer year,@Param("qtr") Integer quarter, @Param("dt") Timestamp timestamp, @Param("unit") Integer unit );

@Query(value = """
 select  coalesce(pending,0) as pending, coalesce(appreceived,0) as appreceived,coalesce((infofur+rej+trans+deemrefus),0 ) as disposed,infofur,deemrefus,
	    	   unit_name,coalesce((trans) ,0)as six  ,n.unit_id,0,0
	    	   from (select coalesce(p,0) as pending,coalesce(appreceived,0) as appreceived, coalesce(infofur,0) as infofur ,coalesce(rej,0) as rej ,
	    	   coalesce(deemrefus,0 )as deemrefus  ,	   coalesce(trans,0) as trans ,
	    	   unit_name, u.unit_id,0,0 from unit_mst u left join ( select unit_id,coalesce(sum(appreceived),0 )as appreceived,  
	    	   coalesce(sum(sumtotamt) ,0)as sumtotamt from (SELECT count(distinct application_id) as appreceived,  
	    	   (sum( application_fee)+ sum( charges_collected) ) as sumtotamt, unit_id  
	    	    FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.pio_receipt_date)::int=:year 
	    	    and EXTRACT(quarter FROM rar.pio_receipt_date)::int=:qtr group by unit_id) b group by unit_id) e on e.unit_id=u.unit_id 



left join  ( select unit_id, coalesce(sum(infofur),0) as infofur 
from (SELECT case when is_transferred!='Yes' and info_furnished_date is not null then  count( application_id) end as infofur, unit_id  
FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.info_furnished_date)::int=:year 
and EXTRACT(quarter FROM rar.info_furnished_date)::int=:qtr group by unit_id,is_transferred,info_furnished_date) b group by unit_id) i on i.unit_id=u.unit_id 

left join  ( select unit_id, coalesce(sum(rej),0) as rej from (SELECT  case when rejection_date is not null then count(application_id) end as  rej ,unit_id
 FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.rejection_date)::int=:year 
 and EXTRACT(quarter FROM rar.rejection_date)::int=:qtr  group by unit_id,rejection_date) b group by unit_id) r on r.unit_id=u.unit_id 
 
 left join  ( select unit_id, coalesce(sum(trans),0) as trans 
 from (SELECT case when is_transferred='Yes' and trans_date is not null then ( count( application_id) ) end as trans,  unit_id 
 FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and EXTRACT(year FROM rar.trans_date)::int=:year 
 and EXTRACT(quarter FROM rar.trans_date)::int=:qtr group by unit_id,is_transferred,trans_date) b group by unit_id) tr on tr.unit_id=u.unit_id 

left join  ( select unit_id, coalesce(sum(deemrefus),0) as deemrefus 
from (SELECT  case when deemed_refusal='7(2)' or  deemed_refusal= '18(1)' then (count( application_id) )  end as deemrefus,  unit_id 
FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and EXTRACT(year FROM rar.refused_date)::int=:year 
and EXTRACT(quarter FROM rar.refused_date)::int=:qtr group by unit_id,deemed_refusal,refused_date) b group by unit_id) dr on dr.unit_id=u.unit_id 

  left  join  ( select pe.unit_id,coalesce(coalesce(preappn,0)-(coalesce(infofur_pr,0)+coalesce(rej_pr,0)+coalesce(trans_pr,0)+coalesce(deemrefus_pr,0)),0) as p 
  from (select unit_id,coalesce(sum(preappn),0) as preappn from ( select count(application_id) as preappn,unit_id 
  from rti_applications_register where delete_flag=false and is_latest=true  and pio_receipt_date::date <= :dt group by unit_id) pr group by unit_id) pe  
  left join (select unit_id, coalesce(sum(infofur),0) as infofur_pr from (SELECT case when is_transferred!='Yes' and info_furnished_date is not null then  
  count( application_id) end as infofur, unit_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  
  and  rar.info_furnished_date<=:dt group by unit_id,is_transferred,info_furnished_date) b group by unit_id)tr on tr.unit_id=pe.unit_id 

left join (select unit_id, coalesce(sum(rej),0) as rej_pr from (SELECT  case when rejection_date is not null then count(application_id) end as  rej ,unit_id 
FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.rejection_date<=:dt group by unit_id,rejection_date)b   
group by unit_id)rj on rj.unit_id=pe.unit_id 
left join ( select unit_id, coalesce(sum(trans),0) as trans_pr 
from (SELECT case when is_transferred='Yes'  and trans_date is not null then ( count( application_id) ) end as trans,  unit_id  
FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and rar.trans_date <=:dt group by unit_id,is_transferred,trans_date) b 
group by unit_id) tn on tn.unit_id=pe.unit_id left join (select unit_id,sum(deemrefus) as deemrefus_pr 
from (select unit_id ,count( application_id) as deemrefus  from rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  
and (deemed_refusal='7(2)' or  deemed_refusal= '18(1)') 
and  rar.refused_date<=:dt  group by unit_id,refused_date)b  group by unit_id )dr on dr.unit_id=pe.unit_id)c on c.unit_id=u.unit_id  ) n 
where n.unit_id not in (9808,30) and n.unit_id=:unit    
""", nativeQuery = true)
List<Map<String, Object>> getrtiAppnCEDashboard(@Param("year") Integer year,@Param("qtr") Integer quarter, @Param("dt") Timestamp timestamp, @Param("unit") Integer unit);

@Query(value = """
select coalesce(pending,0) as pending,coalesce(appreceived,0) as appreceived,coalesce((infofur+rej+trans+deemrefus),0) as disposed,infofur,deemrefus, n.unit_name,coalesce((trans) ,0)as six,   n.unit_id,n.circle_id,0 from  ( select d.unit_id, coalesce(p,0) as pending,coalesce(appreceived,0) as appreceived ,coalesce(infofur,0) as infofur ,coalesce(rej,0) as rej,coalesce(trans,0) as trans,coalesce(deemrefus,0 )as deemrefus,d.unit_name, d.circle_id  from (select distinct r.unit_id,circle_id,u.unit_name from rti_applications_register r 
left join unit_mst u on (u.unit_id=r.unit_id and u.delete_flag='f' and u.status='ACTIVE') where r.delete_flag='f'and r.is_latest='t')d 

left join  ( select unit_id,circle_id,coalesce(sum(appreceived),0 )as appreceived, coalesce(sum(sumtotamt),0) as sumtotamt from (SELECT count(distinct application_id) as appreceived, (sum( application_fee)+ sum( charges_collected) ) as sumtotamt, unit_id,circle_id  FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and EXTRACT(year FROM rar.pio_receipt_date)::int=:year and EXTRACT(quarter FROM rar.pio_receipt_date)::int=:qtr group by unit_id,circle_id) b group by unit_id,circle_id) e on e.unit_id=d.unit_id and e.circle_id=d.circle_id 


left join  ( select unit_id,circle_id,coalesce(sum(infofur),0) as infofur from (SELECT 	case when is_transferred!='Yes' and info_furnished_date is not null then ( count( application_id) ) end as infofur, unit_id,circle_id   FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.info_furnished_date)::int=:year and EXTRACT(quarter FROM rar.info_furnished_date)::int=:qtr group by unit_id,is_transferred,circle_id,info_furnished_date) b group by unit_id,circle_id) i on i.unit_id=d.unit_id and i.circle_id=d.circle_id 

left join ( select unit_id,circle_id, coalesce(sum(rej),0) as rej from (SELECT  case when is_transferred!='Yes' and rejection_date is not null then count(application_id) end as  rej ,unit_id,circle_id  FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.rejection_date)::int=:year and EXTRACT(quarter FROM rar.rejection_date)::int=:qtr  group by unit_id,is_transferred,circle_id,rejection_date) b group by unit_id,circle_id) r on r.unit_id=d.unit_id and r.circle_id=d.circle_id

left join  ( select unit_id,circle_id,coalesce(sum(trans),0) as trans from (SELECT    case when is_transferred='Yes' and trans_date is not null then ( count( application_id) ) end as trans,unit_id,circle_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true   and EXTRACT(year FROM rar.trans_date)::int=:year and EXTRACT(quarter FROM rar.trans_date)::int=:qtr group by unit_id,is_transferred,circle_id,trans_date) b group by unit_id,circle_id) tr on tr.unit_id=d.unit_id and tr.circle_id=d.circle_id 

left join  ( select unit_id,circle_id,coalesce(sum(deemrefus),0) as deemrefus from (SELECT    case when deemed_refusal='7(2)' or  deemed_refusal= '18(1)' then (count( application_id) ) end as deemrefus,unit_id,circle_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true   and EXTRACT(year FROM rar.refused_date)::int=:year and EXTRACT(quarter FROM rar.refused_date)::int=:qtr group by unit_id,deemed_refusal,circle_id,refused_date) b group by unit_id,circle_id) dr on dr.unit_id=d.unit_id and dr.circle_id=d.circle_id 

left join  ( select pe.unit_id,pe.circle_id, coalesce(coalesce(preappn,0)-(coalesce(infofur_pr,0)+coalesce(rej_pr,0)+coalesce(trans_pr,0)+coalesce(deemrefus_pr,0)),0) as p from (select unit_id,circle_id, coalesce(sum(preappn),0) as preappn  from ( select count(application_id) as preappn,unit_id,circle_id from rti_applications_register where delete_flag=false and is_latest=true  and pio_receipt_date::date <= :dt group by unit_id,circle_id) pr  group by unit_id,circle_id) pe 	
			left join (select unit_id,circle_id, coalesce(sum(infofur),0) as infofur_pr from (SELECT case when is_transferred!='Yes' and info_furnished_date is not null then  count( application_id) end as infofur, unit_id,circle_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.info_furnished_date<= :dt group by unit_id,circle_id,is_transferred,info_furnished_date) b group by unit_id,circle_id)tr on tr.unit_id=pe.unit_id and tr.circle_id=pe.circle_id 
			left join (select unit_id,circle_id, coalesce(sum(rej),0) as rej_pr from (SELECT  case when rejection_date is not null then count(application_id) end as  rej ,unit_id ,circle_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.rejection_date<= :dt group by unit_id,circle_id,rejection_date)b group by unit_id,circle_id)rj on rj.unit_id=pe.unit_id and rj.circle_id=pe.circle_id 
			left join ( select unit_id,circle_id, coalesce(sum(trans),0) as trans_pr from (SELECT case when is_transferred='Yes'  and trans_date is not null then ( count( application_id) ) end as trans,  unit_id,circle_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and rar.trans_date <=:dt group by unit_id,circle_id,is_transferred,trans_date) b group by unit_id,circle_id) tn on tn.unit_id=pe.unit_id and tn.circle_id=pe.circle_id 
			left join (select unit_id,circle_id,sum(deemrefus) as deemrefus_pr from (select unit_id ,circle_id,count( application_id) as deemrefus  from rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.refused_date<= :dt and (deemed_refusal='7(2)' or  deemed_refusal= '18(1)') group by unit_id,refused_date,circle_id)b  group by unit_id,circle_id )dr on dr.unit_id=pe.unit_id and dr.circle_id=pe.circle_id )c on c.unit_id=d.unit_id and c.circle_id=d.circle_id )n
			
			where n.unit_id=:unit and n.circle_id=:circle
			""", nativeQuery = true)
List<Map<String, Object>> getrtiAppnDseDashboard(@Param("year") Integer year,@Param("qtr") Integer quarter, @Param("dt") Timestamp timestamp,@Param("unit") Integer unit,@Param("circle") Integer circle );
	
@Query(value = """
  select coalesce(pending,0) as pending, coalesce(appreceived,0) as appreceived,coalesce((infofur+rej+trans+deemrefus),0) as disposed,infofur,deemrefus,n.unit_name,coalesce((trans) ,0)as six,   n.unit_id,n.circle_id,n.division_id from  ( select d.unit_id, coalesce(p,0) as pending,coalesce(appreceived,0)as appreceived,coalesce(infofur,0) as infofur ,coalesce(rej,0) as rej,coalesce(trans,0) as trans, coalesce(deemrefus,0) as deemrefus, d.unit_name, d.circle_id,d.division_id from (select distinct r.unit_id,circle_id,division_id,u.unit_name from rti_applications_register r 
left join unit_mst u on (u.unit_id=r.unit_id and u.delete_flag='f' and u.status='ACTIVE') where r.delete_flag='f'and r.is_latest='t')d 

left join  ( select unit_id,circle_id,division_id,coalesce(sum(appreceived),0 )as appreceived,  coalesce(sum(sumtotamt),0) as sumtotamt from (SELECT count(distinct application_id) as appreceived, (sum( application_fee)+ sum( charges_collected) ) as sumtotamt,unit_id,circle_id ,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.pio_receipt_date)::int=:year and EXTRACT(quarter FROM rar.pio_receipt_date)::int=:qtr group by unit_id,circle_id,division_id) b group by unit_id,circle_id,division_id) e on e.unit_id=d.unit_id and e.circle_id=d.circle_id and e.division_id=d.division_id 

left join  ( select unit_id,circle_id,division_id,coalesce(sum(infofur),0) as infofur from (SELECT 	case when is_transferred!='Yes' and info_furnished_date is not null then ( count( application_id) ) end as infofur, unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and EXTRACT(year FROM rar.info_furnished_date)::int=:year and EXTRACT(quarter FROM rar.info_furnished_date)::int=:qtr group by unit_id,is_transferred,circle_id,division_id,info_furnished_date) b group by unit_id,circle_id,division_id) i on i.unit_id=d.unit_id and i.circle_id=d.circle_id and i.division_id=d.division_id

left join ( select unit_id,circle_id,division_id, coalesce(sum(rej),0) as rej from (SELECT  case when is_transferred!='Yes' and rejection_date is not null then count(application_id) end as  rej ,unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.rejection_date)::int=:year and EXTRACT(quarter FROM rar.rejection_date)::int=:qtr  group by unit_id,is_transferred,circle_id,division_id,rejection_date) b group by unit_id,circle_id,division_id) r on r.unit_id=d.unit_id and r.circle_id=d.circle_id and r.division_id=d.division_id 

left join  ( select unit_id,circle_id,division_id,coalesce(sum(trans),0) as trans from (SELECT    case when is_transferred='Yes' and trans_date is not null then ( count( application_id) ) end as trans,unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.trans_date)::int=:year and EXTRACT(quarter FROM rar.trans_date)::int=:qtr  group by unit_id,is_transferred,circle_id,division_id,trans_date) b group by unit_id,circle_id,division_id) tr  on tr.unit_id=d.unit_id and tr.circle_id=d.circle_id and tr.division_id=d.division_id  
left join  ( select unit_id,circle_id,division_id,coalesce(sum(deemrefus),0) as deemrefus from (SELECT    case when deemed_refusal='7(2)' or  deemed_refusal= '18(1)' then (count( application_id) ) end as deemrefus,division_id,unit_id,circle_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.refused_date)::int=:year and EXTRACT(quarter FROM rar.refused_date)::int=:qtr group by unit_id,deemed_refusal,circle_id,division_id,refused_date) b group by unit_id,circle_id,division_id) dr on dr.unit_id=d.unit_id and dr.circle_id=d.circle_id and dr.division_id=d.division_id 

left join  ( select pe.unit_id,pe.circle_id,pe.division_id, coalesce(coalesce(preappn,0)-(coalesce(infofur_pr,0)+coalesce(rej_pr,0)+coalesce(trans_pr,0)+coalesce(deemrefus_pr,0)),0) as p from (select unit_id,circle_id,division_id, coalesce(sum(preappn),0) as preappn from ( select count(application_id) as preappn,unit_id,division_id,circle_id from rti_applications_register where delete_flag=false and is_latest=true and pio_receipt_date::date <=:dt  group by unit_id,circle_id,division_id) pr group by unit_id,circle_id,division_id) pe 
			left join (select unit_id,circle_id,division_id, coalesce(sum(infofur),0) as infofur_pr from (SELECT case when is_transferred!='Yes' and info_furnished_date is not null then  count( application_id) end as infofur, unit_id,circle_id ,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.info_furnished_date<=:dt group by unit_id,circle_id,division_id,is_transferred,info_furnished_date) b  group by unit_id,circle_id,division_id)tr on tr.unit_id=pe.unit_id and tr.circle_id=pe.circle_id  and tr.division_id=pe.division_id 
			left join (select unit_id,circle_id,division_id, coalesce(sum(rej),0) as rej_pr from (SELECT  case when rejection_date is not null then count(application_id) end as  rej ,unit_id ,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.rejection_date<=:dt group by unit_id,circle_id,division_id,rejection_date)b group by unit_id,circle_id,division_id)rj on rj.unit_id=pe.unit_id and rj.circle_id=pe.circle_id and rj.division_id=pe.division_id 
			left join ( select unit_id,circle_id,division_id, coalesce(sum(trans),0) as trans_pr from (SELECT case when is_transferred='Yes'  and trans_date is not null then ( count( application_id) ) end as trans,  unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and rar.trans_date <=:dt group by unit_id,circle_id,division_id,is_transferred,trans_date) b group by unit_id,circle_id,division_id) tn on tn.unit_id=pe.unit_id and tn.circle_id=pe.circle_id and tn.division_id=pe.division_id 
			left join (select unit_id,circle_id,division_id,sum(deemrefus) as deemrefus_pr from (select unit_id ,circle_id,division_id,count( application_id) as deemrefus from rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and  rar.refused_date<=:dt  and (deemed_refusal='7(2)' or  deemed_refusal= '18(1)') group by unit_id,refused_date,circle_id,division_id)b group by unit_id,circle_id,division_id )dr on dr.unit_id=pe.unit_id and dr.circle_id=pe.circle_id and dr.division_id=pe.division_id)c  on c.unit_id=d.unit_id and c.circle_id=d.circle_id and c.division_id=d.division_id)n	   
			where n.unit_id=:unit and n.circle_id=:circle
""", nativeQuery = true)
List<Map<String, Object>> getrtiAppnDivisionUCDashboard(@Param("year") Integer year,@Param("qtr") Integer qtr,@Param("dt") Timestamp timestamp, @Param("unit") Integer unit,@Param("circle") Integer circle );

@Query(value = """
select coalesce(pending,0) as pending, coalesce(appreceived,0) as appreceived,coalesce((infofur+rej+trans+deemrefus),0) as disposed,infofur,deemrefus,n.unit_name,coalesce((trans) ,0)as six,   n.unit_id,n.circle_id,n.division_id from  ( select d.unit_id, coalesce(p,0) as pending,coalesce(appreceived,0)as appreceived,coalesce(infofur,0) as infofur ,coalesce(rej,0) as rej,coalesce(trans,0) as trans, coalesce(deemrefus,0) as deemrefus, d.unit_name, d.circle_id,d.division_id from (select distinct r.unit_id,circle_id,division_id,u.unit_name from rti_applications_register r 
left join unit_mst u on (u.unit_id=r.unit_id and u.delete_flag='f' and u.status='ACTIVE') where r.delete_flag='f'and r.is_latest='t')d 

left join  ( select unit_id,circle_id,division_id,coalesce(sum(appreceived),0 )as appreceived,  coalesce(sum(sumtotamt),0) as sumtotamt from (SELECT count(distinct application_id) as appreceived, (sum( application_fee)+ sum( charges_collected) ) as sumtotamt,unit_id,circle_id ,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.pio_receipt_date)::int=:year and EXTRACT(quarter FROM rar.pio_receipt_date)::int=:qtr group by unit_id,circle_id,division_id) b group by unit_id,circle_id,division_id) e on e.unit_id=d.unit_id and e.circle_id=d.circle_id and e.division_id=d.division_id 

left join  ( select unit_id,circle_id,division_id,coalesce(sum(infofur),0) as infofur from (SELECT 	case when is_transferred!='Yes' and info_furnished_date is not null then ( count( application_id) ) end as infofur, unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and EXTRACT(year FROM rar.info_furnished_date)::int=:year and EXTRACT(quarter FROM rar.info_furnished_date)::int=:qtr group by unit_id,is_transferred,circle_id,division_id,info_furnished_date) b group by unit_id,circle_id,division_id) i on i.unit_id=d.unit_id and i.circle_id=d.circle_id and i.division_id=d.division_id

left join ( select unit_id,circle_id,division_id, coalesce(sum(rej),0) as rej from (SELECT  case when is_transferred!='Yes' and rejection_date is not null then count(application_id) end as  rej ,unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.rejection_date)::int=:year and EXTRACT(quarter FROM rar.rejection_date)::int=:qtr  group by unit_id,is_transferred,circle_id,division_id,rejection_date) b group by unit_id,circle_id,division_id) r on r.unit_id=d.unit_id and r.circle_id=d.circle_id and r.division_id=d.division_id 

left join  ( select unit_id,circle_id,division_id,coalesce(sum(trans),0) as trans from (SELECT    case when is_transferred='Yes' and trans_date is not null then ( count( application_id) ) end as trans,unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.trans_date)::int=:year and EXTRACT(quarter FROM rar.trans_date)::int=:qtr  group by unit_id,is_transferred,circle_id,division_id,trans_date) b group by unit_id,circle_id,division_id) tr  on tr.unit_id=d.unit_id and tr.circle_id=d.circle_id and tr.division_id=d.division_id  
left join  ( select unit_id,circle_id,division_id,coalesce(sum(deemrefus),0) as deemrefus from (SELECT    case when deemed_refusal='7(2)' or  deemed_refusal= '18(1)' then (count( application_id) ) end as deemrefus,division_id,unit_id,circle_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.refused_date)::int=:year and EXTRACT(quarter FROM rar.refused_date)::int=:qtr group by unit_id,deemed_refusal,circle_id,division_id,refused_date) b group by unit_id,circle_id,division_id) dr on dr.unit_id=d.unit_id and dr.circle_id=d.circle_id and dr.division_id=d.division_id 

left join  ( select pe.unit_id,pe.circle_id,pe.division_id, coalesce(coalesce(preappn,0)-(coalesce(infofur_pr,0)+coalesce(rej_pr,0)+coalesce(trans_pr,0)+coalesce(deemrefus_pr,0)),0) as p from (select unit_id,circle_id,division_id, coalesce(sum(preappn),0) as preappn from ( select count(application_id) as preappn,unit_id,division_id,circle_id from rti_applications_register where delete_flag=false and is_latest=true and pio_receipt_date::date <=:dt  group by unit_id,circle_id,division_id) pr group by unit_id,circle_id,division_id) pe 
			left join (select unit_id,circle_id,division_id, coalesce(sum(infofur),0) as infofur_pr from (SELECT case when is_transferred!='Yes' and info_furnished_date is not null then  count( application_id) end as infofur, unit_id,circle_id ,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.info_furnished_date<=:dt group by unit_id,circle_id,division_id,is_transferred,info_furnished_date) b  group by unit_id,circle_id,division_id)tr on tr.unit_id=pe.unit_id and tr.circle_id=pe.circle_id  and tr.division_id=pe.division_id 
			left join (select unit_id,circle_id,division_id, coalesce(sum(rej),0) as rej_pr from (SELECT  case when rejection_date is not null then count(application_id) end as  rej ,unit_id ,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and  rar.rejection_date<=:dt group by unit_id,circle_id,division_id,rejection_date)b group by unit_id,circle_id,division_id)rj on rj.unit_id=pe.unit_id and rj.circle_id=pe.circle_id and rj.division_id=pe.division_id 
			left join ( select unit_id,circle_id,division_id, coalesce(sum(trans),0) as trans_pr from (SELECT case when is_transferred='Yes'  and trans_date is not null then ( count( application_id) ) end as trans,  unit_id,circle_id,division_id FROM rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true  and rar.trans_date <=:dt group by unit_id,circle_id,division_id,is_transferred,trans_date) b group by unit_id,circle_id,division_id) tn on tn.unit_id=pe.unit_id and tn.circle_id=pe.circle_id and tn.division_id=pe.division_id 
			left join (select unit_id,circle_id,division_id,sum(deemrefus) as deemrefus_pr from (select unit_id ,circle_id,division_id,count( application_id) as deemrefus from rti_applications_register rar where rar.delete_flag=false and rar.is_latest=true and  rar.refused_date<=:dt  and (deemed_refusal='7(2)' or  deemed_refusal= '18(1)') group by unit_id,refused_date,circle_id,division_id)b group by unit_id,circle_id,division_id )dr on dr.unit_id=pe.unit_id and dr.circle_id=pe.circle_id and dr.division_id=pe.division_id)c  on c.unit_id=d.unit_id and c.circle_id=d.circle_id and c.division_id=d.division_id)n	   
			where n.unit_id=:unit and n.circle_id=:circle and n.division_id=:division
""", nativeQuery = true)
List<Map<String, Object>> getrtiAppnDivisionUCDDashboard(@Param("year") Integer year,@Param("qtr") Integer quarter, @Param("dt") Timestamp timestamp, @Param("unit") Integer unit,@Param("circle") Integer circle,@Param("division") Integer division );

}
