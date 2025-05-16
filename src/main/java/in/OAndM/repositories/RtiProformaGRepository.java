package in.OAndM.repositories;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.OAndM.Entities.RtiProformaG;
import in.OAndM.core.BaseRepository;


@Repository
public interface RtiProformaGRepository extends BaseRepository<RtiProformaG, Integer> {
	
	

	@Query("SELECT p FROM RtiProformaG p WHERE p.deleteFlag = false")
    List<RtiProformaG> findAllByDeleteFlagFalse();

    @Query("SELECT p FROM RtiProformaG p WHERE p.proGId = :id AND p.deleteFlag = false")
    Optional<RtiProformaG> findByIdAndDeleteFlagFalse(@Param("id") Integer id);
	    
	  @Query("SELECT p FROM RtiProformaG p LEFT JOIN FETCH p.rejectionSectionStatus WHERE p.deleteFlag = false")
	    List<RtiProformaG> findAllWithRejectionStatus();
	
//GET_RTI_CONSOLIDATED_APPEAL_REPORT	  
	  
	  @Query(value = """

select u.unit_id  as unitId, u.unit_name AS unitName, coalesce(qpending,0) as qpending ,coalesce(totapp,0) as totapp,coalesce((infor + rej),0) as totdispo,coalesce(infor,0) as infor, coalesce(rs1,0) as rs1,coalesce(rs2,0) as rs2,coalesce(rs3,0) as rs3,coalesce(rs4,0) as rs4,coalesce(rs5,0) as rs5,coalesce(rs6,0) as rs6,coalesce(rs7,0) as rs7,coalesce(rs8,0) as rs8,coalesce(rs9,0) as rs9,  coalesce(rs10,0) as rs10, coalesce(rs11,0) as rs11,coalesce(rs12,0) as rs12,coalesce(rs13,0) as rs13,coalesce(rstot,0) as rstot,coalesce(rs15,0) as rs15,amount  from unit_mst u 

left join  ( select unit_id,year,quarter,sum(totapp) as totapp,sum(amount) as amount from (select unit_id,EXTRACT(YEAR FROM appeal_receipt_date)::int as year, EXTRACT(quarter FROM appeal_receipt_date)::int as quarter,count(pro_g_id) as totapp,sum(charges_collect_forfurnish) as amount from rti_proforma_g where delete_flag='f' and is_latest='t' and EXTRACT(YEAR FROM appeal_receipt_date)::int=:year and EXTRACT(quarter FROM appeal_receipt_date)::int=:qtr  group by unit_id,appellate_1st_desicion_date,appellate_1st_desicion_allow_rejec,appeal_receipt_date)f group by unit_id,year,quarter)e on e.unit_id=u.unit_id 

left join  ( select unit_id,coalesce(sum(infofur),0) as infor ,coalesce(sum(rej),0) as rej from (SELECT 	case when appellate_1st_desicion_allow_rejec=1 and appellate_1st_desicion_date is not null then ( count( pro_g_id) ) end as infofur, case when appellate_1st_desicion_allow_rejec=2 and appellate_1st_desicion_date is not null then ( count( pro_g_id) ) end as rej, unit_id FROM rti_proforma_g rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.appellate_1st_desicion_date)::int=:year  and EXTRACT(quarter FROM rar.appellate_1st_desicion_date)::int=:qtr  group by unit_id,appellate_1st_desicion_allow_rejec,appellate_1st_desicion_date) b group by unit_id) i on i.unit_id=u.unit_id 

left join  ( select pe.unit_id, coalesce(coalesce(preappn,0)-(coalesce(disposed_pr,0)),0) as qpending from (select unit_id, coalesce(sum(preappn),0) as preappn from ( select count(pro_g_id) as preappn,unit_id from rti_proforma_g where delete_flag=false and is_latest=true and appeal_receipt_date::date <= :dt group by unit_id) pr group by unit_id) pe 
			
			left join (select unit_id,coalesce(sum(dispo),0) as disposed_pr from (SELECT case when appellate_1st_desicion_date is not null and appellate_1st_desicion_allow_rejec is not null then  count( pro_g_id) end as dispo, unit_id FROM rti_proforma_g rar where rar.delete_flag=false and rar.is_latest=true and  rar.appellate_1st_desicion_date<= :dt   group by unit_id,appellate_1st_desicion_allow_rejec,appellate_1st_desicion_date) b  group by unit_id)tr on tr.unit_id=pe.unit_id ) g on g.unit_id=u.unit_id 
			
			left join ( select unit_id,coalesce(sum(rs1),0)as rs1,coalesce(sum(rs2),0) as rs2,coalesce(sum(rs3),0) as rs3,coalesce(sum(rs4),0) as rs4,coalesce(sum(rs5),0) as rs5,coalesce(sum(rs6),0) as rs6,coalesce(sum(rs7),0) as rs7,coalesce(sum(rs8),0) as rs8,coalesce(sum(rs9),0) as rs9,coalesce(sum(rs10),0) as rs10, coalesce(sum(rs11),0) as rs11,coalesce(sum(rs12),0) as rs12,coalesce(sum(rs13),0) as rs13,coalesce(sum(rstot),0) as rstot,
			coalesce(sum(rs15),0) as rs15,year, quarter from (select unit_id,EXTRACT(YEAR FROM appellate_1st_desicion_date)::int as year,
			EXTRACT(quarter FROM appellate_1st_desicion_date)::int as quarter, case when appellate_1st_desicion_rej_section=1 then count(pro_g_id) end as rs1, 
			case when appellate_1st_desicion_rej_section=2 then count(pro_g_id) end as rs2,case when appellate_1st_desicion_rej_section=3 then 
			count(pro_g_id) end as rs3, case when appellate_1st_desicion_rej_section=4 then count(pro_g_id) end as rs4,
			case when appellate_1st_desicion_rej_section=5 then count(pro_g_id) end as rs5,case when appellate_1st_desicion_rej_section=6 then count(pro_g_id) end as rs6,
			case when appellate_1st_desicion_rej_section=7 then count(pro_g_id) end as rs7, case when appellate_1st_desicion_rej_section=8 then count(pro_g_id) end as rs8,
			case when appellate_1st_desicion_rej_section=9 then count(pro_g_id) end as rs9, case when appellate_1st_desicion_rej_section=10 then count(pro_g_id) end as rs10,
			 case when appellate_1st_desicion_rej_section=11 then count(pro_g_id) end as rs11, case when appellate_1st_desicion_rej_section=12 then count(pro_g_id) end as rs12,
			 case when appellate_1st_desicion_rej_section=13 then count(pro_g_id) end as rs13, case when appellate_1st_desicion_rej_section in (1,2,3,4,5,6,7,8,9,10,11,12,13,15) then count(pro_g_id) end as rstot, 
			 case when appellate_1st_desicion_rej_section=15 then count(pro_g_id) end as rs15  from rti_proforma_g where delete_flag='f' and is_latest='t' 
			 and appellate_1st_desicion_allow_rejec=2  group by unit_id,appellate_1st_desicion_rej_section,appellate_1st_desicion_date )a 
			 where year=:year and quarter=:qtr group by unit_id,year,quarter)f on f.unit_id=u.unit_id 
			  where u.unit_id not in (9808,30) order by u.unit_name desc
   
""", nativeQuery = true)	  
	
List<Map<String, Object>> getUnitGroupedData(@Param("year") Integer year, @Param("qtr") Integer quarter,@Param("dt") Timestamp timestamp);

//GET_RTI_PROF_G_CIRCLE_ABS=	  
	  
	  @Query(value = """
select n.unit_id as unitId,n.unit_name AS unitName,coalesce(qpending,0) as qpending ,coalesce(totapp,0) as totapp,coalesce((infor + rej),0) as  totdispo,infor,rs1,rs2,rs3,rs4,rs5,rs6,rs7,rs8,rs9,rs10,rs11,rs12,rs13,rstot,rs15,amount,n.circle_id from  ( select d.unit_id,coalesce(qpending,0) as qpending ,coalesce(totapp,0) as totapp ,coalesce(infor,0) as infor,coalesce(rej,0) as rej,	  coalesce(rs1,0) as rs1,coalesce(rs2,0) as rs2,coalesce(rs3,0) as rs3,coalesce(rs4,0) as rs4,coalesce(rs5,0) as rs5,coalesce(rs6,0) as rs6,coalesce(rs7,0) as rs7,coalesce(rs8,0) as rs8,coalesce(rs9,0) as rs9,coalesce(rs10,0) as rs10, coalesce(rs11,0) as rs11,coalesce(rs12,0) as rs12,coalesce(rs13,0) as rs13,coalesce(rstot,0) as rstot,coalesce(rs15,0) as rs15,amount,d.circle_id,d.unit_name  from (select distinct r.unit_id,circle_id,unit_name  from rti_proforma_g r 
left join unit_mst u on u.unit_id=r.unit_id  where r.delete_flag='f' and r.is_latest='t')d 
left join ( select unit_id,circle_id,year,quarter,sum(totapp)  as totapp ,sum(amount) as amount from (select unit_id,circle_id,EXTRACT(YEAR FROM appeal_receipt_date)::int as year,EXTRACT(quarter FROM appeal_receipt_date)::int as quarter,	count(pro_g_id) as totapp,sum(charges_collect_forfurnish) as amount from rti_proforma_g where delete_flag='f' and is_latest='t' and  EXTRACT(YEAR FROM appeal_receipt_date)::int=:year and EXTRACT(quarter FROM appeal_receipt_date)::int=:qtr	group by unit_id,appellate_1st_desicion_date,appellate_1st_desicion_allow_rejec,appeal_receipt_date,circle_id)f group by unit_id,year,quarter,circle_id)e on e.unit_id=d.unit_id and e.circle_id=d.circle_id 
left join  ( select unit_id,circle_id,coalesce(sum(infofur),0) as infor ,coalesce(sum(rej),0) as rej from (SELECT 	case when appellate_1st_desicion_allow_rejec=1 and appellate_1st_desicion_date is not null then ( count( pro_g_id) ) end as infofur, case when appellate_1st_desicion_allow_rejec=2 and appellate_1st_desicion_date is not null then ( count( pro_g_id) ) end as rej,  unit_id,circle_id FROM rti_proforma_g rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.appellate_1st_desicion_date)::int=:year and EXTRACT(quarter FROM rar.appellate_1st_desicion_date)::int=:qtr group by unit_id,appellate_1st_desicion_allow_rejec,circle_id,appellate_1st_desicion_date) b group by unit_id,circle_id) i on i.unit_id=d.unit_id and i.circle_id=d.circle_id 
left join  ( select pe.unit_id,pe.circle_id, coalesce(coalesce(preappn,0)-(coalesce(disposed_pr,0)),0) as qpending from (select unit_id,circle_id, coalesce(sum(preappn),0) as preappn from ( select count(pro_g_id) as preappn,unit_id,circle_id from rti_proforma_g where delete_flag=false and is_latest=true and appeal_receipt_date::date <=:dt group by unit_id,circle_id) pr group by unit_id,circle_id) pe 
			left join (select unit_id,circle_id, coalesce(sum(dispo),0) as disposed_pr from (SELECT case when appellate_1st_desicion_date is not null and appellate_1st_desicion_allow_rejec is not null then  count( pro_g_id) end as dispo, unit_id,circle_id FROM rti_proforma_g rar where rar.delete_flag=false and rar.is_latest=true and  rar.appellate_1st_desicion_date<=:dt  group by unit_id,circle_id,appellate_1st_desicion_allow_rejec,appellate_1st_desicion_date) b  group by unit_id,circle_id)tr on tr.unit_id=pe.unit_id and tr.circle_id=pe.circle_id) g on g.unit_id=d.unit_id and g.circle_id=d.circle_id 
left join (select unit_id,circle_id,coalesce(sum(rs1),0)as rs1,coalesce(sum(rs2),0) as rs2,coalesce(sum(rs3),0) as rs3,coalesce(sum(rs4),0) as rs4,
 coalesce(sum(rs5),0) as rs5,coalesce(sum(rs6),0) as rs6,coalesce(sum(rs7),0) as rs7,coalesce(sum(rs8),0) as rs8,coalesce(sum(rs9),0) as rs9,
 coalesce(sum(rs10),0) as rs10,coalesce(sum(rs11),0) as rs11,coalesce(sum(rs12),0) as rs12,coalesce(sum(rs13),0) as rs13,
 coalesce(sum(rstot),0) as rstot,coalesce(sum(rs15),0) as rs15,year, quarter
  from (select unit_id,circle_id,EXTRACT(YEAR FROM appellate_1st_desicion_date)::int as year,EXTRACT(quarter FROM appellate_1st_desicion_date)::int as quarter,
  case when appellate_1st_desicion_rej_section=1 then count(pro_g_id) end as rs1,case when appellate_1st_desicion_rej_section=2 then count(pro_g_id) end as rs2,
  case when appellate_1st_desicion_rej_section=3 then count(pro_g_id) end as rs3,	case when appellate_1st_desicion_rej_section=4 then count(pro_g_id) end as rs4,	
  case when appellate_1st_desicion_rej_section=5 then count(pro_g_id) end as rs5,case when appellate_1st_desicion_rej_section=6 then count(pro_g_id) end as rs6,
  case when appellate_1st_desicion_rej_section=7 then count(pro_g_id) end as rs7,case when appellate_1st_desicion_rej_section=8 then count(pro_g_id) end as rs8,
  case when appellate_1st_desicion_rej_section=9 then count(pro_g_id) end as rs9,case when appellate_1st_desicion_rej_section=10 then count(pro_g_id) end as rs10,
  case when appellate_1st_desicion_rej_section=11 then count(pro_g_id) end as rs11,case when appellate_1st_desicion_rej_section=12 then count(pro_g_id) end as rs12, 
  case when appellate_1st_desicion_rej_section=13 then count(pro_g_id) end as rs13,case when appellate_1st_desicion_rej_section in (1,2,3,4,5,6,7,8,9,10,11,12,13,15) 
  then count(pro_g_id) end as rstot,case when appellate_1st_desicion_rej_section=15 then count(pro_g_id) end as rs15 
  from rti_proforma_g where delete_flag='f' and is_latest='t' and appellate_1st_desicion_allow_rejec=2  
  group by unit_id,appellate_1st_desicion_rej_section,appellate_1st_desicion_date,circle_id )a where year=:year and quarter=:qtr
   group by unit_id,year,quarter,circle_id)f on f.unit_id=d.unit_id and f.circle_id=d.circle_id ) n		   
		     where n.unit_id=:unit
"""
			  , nativeQuery = true)
	  
	
List<Map<String, Object>> getCircleGroupedData(@Param("year") Integer year, @Param("qtr") Integer quarter,@Param("dt") Timestamp timestamp,@Param("unit") Integer Unit);
	  
//	  @Query(value = """
//			
//		   
//		""", nativeQuery = true)
//			  
//			
//		List<Map<String, Object>> getUnitGroupedData(@Param("year1") Integer year, @Param("quarter1") Integer quarter,@Param("date1") Timestamp timestamp);

	  @Query(value = """
				select appeal_no,appeal_date,name_of_appellant,appellant_address,appeal_receipt_date,pio_name,pio_designation,application_no,application_date,name_of_appellate ,appellate_address, appellate_1st_desicion_date,
(case when appellate_1st_desicion_allow_rejec=1 then 'Allowed' when appellate_1st_desicion_allow_rejec=2 then 'Rejected' else '' end) as decision,
rti_rejection_section,charges_collect_forfurnish,second_appeal_made_19_3,remarks,
rtg.unit_id, circle_id, division_id,unit_name,pro_g_id 
from rti_proforma_g rtg 
left join rti_rejection_status rt on rtg.appellate_1st_desicion_rej_section=rt.reject_section_id 
left join unit_mst u on u.unit_id=rtg.unit_id 
where rtg.delete_flag='f' and division_id=:divId and circle_id=:circleId and rtg.unit_id=:unitId and appeal_receipt_date>=:fdate
union 
select appeal_no,appeal_date,name_of_appellant,appellant_address,appeal_receipt_date,pio_name,pio_designation,application_no,application_date,name_of_appellate ,appellate_address, appellate_1st_desicion_date,
(case when appellate_1st_desicion_allow_rejec=1 then 'Allowed' when appellate_1st_desicion_allow_rejec=2 then 'Rejected' else '' end) as decision, rti_rejection_section,charges_collect_forfurnish,second_appeal_made_19_3,remarks, rtg.unit_id, circle_id, division_id,unit_name,pro_g_id 
from rti_proforma_g rtg left join rti_rejection_status rt on rtg.appellate_1st_desicion_rej_section=rt.reject_section_id
left join unit_mst u on u.unit_id=rtg.unit_id where rtg.delete_flag='f' and division_id=:divId and circle_id=:circleId and rtg.unit_id=:unitId and appellate_1st_desicion_date is null and appeal_receipt_date<=:date order by appeal_receipt_date
	 		   
	    		""", nativeQuery = true)
	    			  
	    			
	    		List<Map<String, Object>> getRTIAppealEditList( @Param("divId") Integer divId,@Param("circleId") Integer circleId,@Param("unitId") Integer unitId, @Param("fdate") Date fdate,@Param("date") Date date);		

	  @Query(value = """
	    	   select appeal_no,appeal_date,name_of_appellant,appellant_address,appeal_receipt_date,pio_name,pio_designation,application_no,application_date,name_of_appellate ,
appellate_address, appellate_1st_desicion_date,
(case when appellate_1st_desicion_allow_rejec=1 then 'Allowed' when appellate_1st_desicion_allow_rejec=2 then 'Rejected' else '' end) as decision,rti_rejection_section,charges_collect_forfurnish,second_appeal_made_19_3,remarks, rtg.unit_id, 
circle_id, division_id,unit_name,pro_g_id 
from rti_proforma_g rtg 
left join rti_rejection_status rt on rtg.appellate_1st_desicion_rej_section=rt.reject_section_id 
left join unit_mst u on u.unit_id=rtg.unit_id where rtg.delete_flag='f'  and division_id=:divId and circle_id=:circleId and rtg.unit_id=:unitId 
and EXTRACT(year FROM rtg.appeal_receipt_date)::int=:year  order by appeal_receipt_date 
	    	    	""", nativeQuery = true)	    	    		
	    	    		
	    List<Map<String, Object>> getAppealYrEEReport   (@Param("divId") Integer divId,  @Param("circleId") Integer circleId,   @Param("unitId") Integer unitId, @Param("year") Integer year);
	  
	  @Query(value = """
	    	   select appeal_no,appeal_date,name_of_appellant,appellant_address,appeal_receipt_date,pio_name,pio_designation,application_no,application_date,name_of_appellate ,
appellate_address, appellate_1st_desicion_date,
(case when appellate_1st_desicion_allow_rejec=1 then 'Allowed' when appellate_1st_desicion_allow_rejec=2 then 'Rejected' else '' end) as decision,rti_rejection_section,charges_collect_forfurnish,second_appeal_made_19_3,remarks, rtg.unit_id, 
circle_id, division_id,unit_name,pro_g_id 
from rti_proforma_g rtg 
left join rti_rejection_status rt on rtg.appellate_1st_desicion_rej_section=rt.reject_section_id 
left join unit_mst u on u.unit_id=rtg.unit_id where rtg.delete_flag='f' and division_id=:divId and circle_id=:circleId and rtg.unit_id=:unitId  
and EXTRACT(year FROM rtg.appeal_receipt_date)::int=:year  and EXTRACT(quarter FROM rtg.appeal_receipt_date)::int=:quarter order by appeal_receipt_date
	    	    	""", nativeQuery = true)	 
	  List<Map<String, Object>> getAppealYrQtrEEReport   (@Param("divId") Integer divId,  @Param("circleId") Integer circleId,
              @Param("unitId") Integer unitId, @Param("year") Integer year,@Param("quarter") Integer quarter);
	  
	  @Query(value = """	  
	  select n.unit_id as unitId,n.unit_name as unitName,coalesce(qpending,0) as qpending ,coalesce(totapp,0) as totapp,coalesce((infor + rej),0) as  totdispo,infor,rs1,rs2,rs3,rs4,rs5,rs6,rs7,rs8,rs9,rs10,rs11,rs12,rs13,rstot,rs15,amount,n.circle_id,n.division_id from  ( select d.unit_id,coalesce(qpending,0) as qpending ,coalesce(totapp,0) as totapp ,coalesce(infor,0) as infor,coalesce(rej,0) as rej,	  coalesce(rs1,0) as rs1,coalesce(rs2,0) as rs2,coalesce(rs3,0) as rs3,coalesce(rs4,0) as rs4,coalesce(rs5,0) as rs5,coalesce(rs6,0) as rs6,coalesce(rs7,0) as rs7,coalesce(rs8,0) as rs8,coalesce(rs9,0) as rs9, coalesce(rs10,0) as rs10, coalesce(rs11,0) as rs11,coalesce(rs12,0) as rs12,coalesce(rs13,0) as rs13,coalesce(rstot,0) as rstot,coalesce(rs15,0) as rs15,amount,d.circle_id,d.division_id,d.unit_name from (select distinct r.unit_id,circle_id,division_id,unit_name from rti_proforma_g r 

			  left join unit_mst u on u.unit_id=r.unit_id  where r.delete_flag='f' and r.is_latest='t')d 

			  left join ( select unit_id,circle_id,division_id,year,quarter,sum(totapp)  as totapp ,sum(amount) as amount from (select unit_id,circle_id,division_id,EXTRACT(YEAR FROM appeal_receipt_date)::int as year,EXTRACT(quarter FROM appeal_receipt_date)::int as quarter,	count(pro_g_id) as totapp,sum(charges_collect_forfurnish) as amount from rti_proforma_g where delete_flag='f' and is_latest='t' and  EXTRACT(YEAR FROM appeal_receipt_date)::int=:year and EXTRACT(quarter FROM appeal_receipt_date)::int=:qtr group by unit_id,appellate_1st_desicion_date,appellate_1st_desicion_allow_rejec,appeal_receipt_date,circle_id,division_id)f group by unit_id,year,quarter,circle_id,division_id)e on e.unit_id=d.unit_id and e.circle_id=d.circle_id and e.division_id=d.division_id 

			  left join  ( select unit_id,circle_id,division_id,coalesce(sum(infofur),0) as infor ,coalesce(sum(rej),0) as rej from (SELECT 	case when appellate_1st_desicion_allow_rejec=1 and appellate_1st_desicion_date is not null then ( count( pro_g_id) ) end as infofur, case when appellate_1st_desicion_allow_rejec=2 and appellate_1st_desicion_date is not null then ( count( pro_g_id) ) end as rej, unit_id,circle_id,division_id FROM rti_proforma_g rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.appellate_1st_desicion_date)::int=:year and EXTRACT(quarter FROM rar.appellate_1st_desicion_date)::int=:qtr group by unit_id,appellate_1st_desicion_allow_rejec,circle_id,division_id,appellate_1st_desicion_date) b group by unit_id,circle_id,division_id) i on i.unit_id=d.unit_id and i.circle_id=d.circle_id and i.division_id=d.division_id  

			  left join  ( select pe.unit_id,pe.circle_id,pe.division_id, coalesce(coalesce(preappn,0)-(coalesce(disposed_pr,0)),0) as qpending from (select unit_id,circle_id,division_id, coalesce(sum(preappn),0) as preappn from ( select count(pro_g_id) as preappn,unit_id,division_id,circle_id from rti_proforma_g where delete_flag=false and is_latest=true and appeal_receipt_date::date <= :dt group by unit_id,circle_id,division_id) pr group by unit_id,circle_id,division_id) pe
			  			left join (select unit_id,circle_id,division_id, coalesce(sum(dispo),0) as disposed_pr from (SELECT case when appellate_1st_desicion_date is not null and appellate_1st_desicion_allow_rejec is not null then  count( pro_g_id) end as dispo, unit_id,circle_id ,division_id FROM rti_proforma_g rar where rar.delete_flag=false and rar.is_latest=true and  rar.appellate_1st_desicion_date<= :dt  group by unit_id,circle_id,division_id,appellate_1st_desicion_allow_rejec,appellate_1st_desicion_date) b group by unit_id,circle_id,division_id)tr on tr.unit_id=pe.unit_id and tr.circle_id=pe.circle_id  and tr.division_id=pe.division_id ) g on g.unit_id=d.unit_id and g.circle_id=d.circle_id and g.division_id=d.division_id 
			  			
			  			left join (select unit_id,circle_id,division_id,coalesce(sum(rs1),0)as rs1,coalesce(sum(rs2),0) as rs2,
			  			coalesce(sum(rs3),0) as rs3,coalesce(sum(rs4),0) as rs4, coalesce(sum(rs5),0) as rs5,coalesce(sum(rs6),0) as rs6,
			  			coalesce(sum(rs7),0) as rs7,coalesce(sum(rs8),0) as rs8,coalesce(sum(rs9),0) as rs9, coalesce(sum(rs10),0) as rs10,
			  			coalesce(sum(rs11),0) as rs11,coalesce(sum(rs12),0) as rs12,coalesce(sum(rs13),0) as rs13,coalesce(sum(rstot),0) as rstot, 
			  			coalesce(sum(rs15),0) as rs15,year, quarter from (select unit_id,circle_id,division_id,
			  			EXTRACT(YEAR FROM appellate_1st_desicion_date)::int as year,EXTRACT(quarter FROM appellate_1st_desicion_date)::int as quarter,
			  			case when appellate_1st_desicion_rej_section=1 then count(pro_g_id) end as rs1,	
			  			case when appellate_1st_desicion_rej_section=2 then count(pro_g_id) end as rs2,
			  			case when appellate_1st_desicion_rej_section=3 then count(pro_g_id) end as rs3,
			  			case when appellate_1st_desicion_rej_section=4 then count(pro_g_id) end as rs4,
			  			case when appellate_1st_desicion_rej_section=5 then count(pro_g_id) end as rs5,case when appellate_1st_desicion_rej_section=6 then count(pro_g_id) end as rs6,
			  			case when appellate_1st_desicion_rej_section=7 then count(pro_g_id) end as rs7,	case when appellate_1st_desicion_rej_section=8 then count(pro_g_id) end as rs8,
			  			 case when appellate_1st_desicion_rej_section=9 then count(pro_g_id) end as rs9,case when appellate_1st_desicion_rej_section=10 then count(pro_g_id) end as rs10,
			  			 case when appellate_1st_desicion_rej_section=11 then count(pro_g_id) end as rs11,case when appellate_1st_desicion_rej_section=12 then count(pro_g_id) end as rs12,
			  			 case when appellate_1st_desicion_rej_section=13 then count(pro_g_id) end as rs13,case when appellate_1st_desicion_rej_section in (1,2,3,4,5,6,7,8,9,10,11,12,13,15) 
			  			 then count(pro_g_id) end as rstot,case when appellate_1st_desicion_rej_section=15 then count(pro_g_id) end as rs15 
			  			 from rti_proforma_g where delete_flag='f' and is_latest='t' group by unit_id,appellate_1st_desicion_rej_section,appellate_1st_desicion_date,
			  			 circle_id,division_id )a where year=:year and quarter=:qtr group by unit_id,year,quarter,circle_id,division_id)f 
			  			 on f.unit_id=d.unit_id and f.circle_id=d.circle_id and f.division_id=d.division_id ) n
			  			 where n.unit_id=:unit and n.circle_id=:circle
			  			 			  """ , nativeQuery = true)	  
	
List<Map<String, Object>> getrtiAppealDivisionUCConsolidatedPrfmG(@Param("year") Integer year, @Param("qtr") Integer quarter,@Param("dt") Timestamp timestamp,@Param("unit") Integer Unit,@Param("circle") Integer Circle);
	 
	  @Query(value = """	  
			  select n.unit_id as unitId ,n.unit_name as unitName,coalesce(qpending,0) as qpending ,coalesce(totapp,0) as totapp,coalesce((infor + rej),0) as  totdispo,infor,rs1,rs2,rs3,rs4,rs5,rs6,rs7,rs8,rs9,rs10,
			  rs11,rs12,rs13,rstot,rs15,amount,n.circle_id,n.division_id from  ( select d.unit_id,coalesce(qpending,0) as qpending ,
			  coalesce(totapp,0) as totapp ,coalesce(infor,0) as infor,coalesce(rej,0) as rej,	  coalesce(rs1,0) as rs1,coalesce(rs2,0) as rs2,
			  coalesce(rs3,0) as rs3,coalesce(rs4,0) as rs4,coalesce(rs5,0) as rs5,coalesce(rs6,0) as rs6,coalesce(rs7,0) as rs7,
			  coalesce(rs8,0) as rs8,coalesce(rs9,0) as rs9, coalesce(rs10,0) as rs10, coalesce(rs11,0) as rs11,coalesce(rs12,0) as rs12,
			  coalesce(rs13,0) as rs13,coalesce(rstot,0) as rstot,coalesce(rs15,0) as rs15,amount,d.circle_id,d.division_id,d.unit_name 
			  from (select distinct r.unit_id,circle_id,division_id,unit_name from rti_proforma_g r 

					  left join unit_mst u on u.unit_id=r.unit_id  where r.delete_flag='f' and r.is_latest='t')d 

					  left join ( select unit_id,circle_id,division_id,year,quarter,sum(totapp)  as totapp ,sum(amount) as amount from (select unit_id,circle_id,division_id,EXTRACT(YEAR FROM appeal_receipt_date)::int as year,EXTRACT(quarter FROM appeal_receipt_date)::int as quarter,	count(pro_g_id) as totapp,sum(charges_collect_forfurnish) as amount from rti_proforma_g where delete_flag='f' and is_latest='t' and  EXTRACT(YEAR FROM appeal_receipt_date)::int=:year and EXTRACT(quarter FROM appeal_receipt_date)::int=:qtr group by unit_id,appellate_1st_desicion_date,appellate_1st_desicion_allow_rejec,appeal_receipt_date,circle_id,division_id)f group by unit_id,year,quarter,circle_id,division_id)e on e.unit_id=d.unit_id and e.circle_id=d.circle_id and e.division_id=d.division_id 

					  left join  ( select unit_id,circle_id,division_id,coalesce(sum(infofur),0) as infor ,coalesce(sum(rej),0) as rej from (SELECT 	case when appellate_1st_desicion_allow_rejec=1 and appellate_1st_desicion_date is not null then ( count( pro_g_id) ) end as infofur, case when appellate_1st_desicion_allow_rejec=2 and appellate_1st_desicion_date is not null then ( count( pro_g_id) ) end as rej, unit_id,circle_id,division_id FROM rti_proforma_g rar where rar.delete_flag=false and rar.is_latest=true and EXTRACT(year FROM rar.appellate_1st_desicion_date)::int=:year and EXTRACT(quarter FROM rar.appellate_1st_desicion_date)::int=:qtr group by unit_id,appellate_1st_desicion_allow_rejec,circle_id,division_id,appellate_1st_desicion_date) b group by unit_id,circle_id,division_id) i on i.unit_id=d.unit_id and i.circle_id=d.circle_id and i.division_id=d.division_id  

					  left join  ( select pe.unit_id,pe.circle_id,pe.division_id, coalesce(coalesce(preappn,0)-(coalesce(disposed_pr,0)),0) as qpending from (select unit_id,circle_id,division_id, coalesce(sum(preappn),0) as preappn from ( select count(pro_g_id) as preappn,unit_id,division_id,circle_id from rti_proforma_g where delete_flag=false and is_latest=true and appeal_receipt_date::date <= :dt group by unit_id,circle_id,division_id) pr group by unit_id,circle_id,division_id) pe
					  			left join (select unit_id,circle_id,division_id, coalesce(sum(dispo),0) as disposed_pr from (SELECT case when appellate_1st_desicion_date is not null and appellate_1st_desicion_allow_rejec is not null then  count( pro_g_id) end as dispo, unit_id,circle_id ,division_id FROM rti_proforma_g rar where rar.delete_flag=false and rar.is_latest=true and  rar.appellate_1st_desicion_date<= :dt  group by unit_id,circle_id,division_id,appellate_1st_desicion_allow_rejec,appellate_1st_desicion_date) b group by unit_id,circle_id,division_id)tr on tr.unit_id=pe.unit_id and tr.circle_id=pe.circle_id  and tr.division_id=pe.division_id ) g on g.unit_id=d.unit_id and g.circle_id=d.circle_id and g.division_id=d.division_id 
					  			
					  			left join (select unit_id,circle_id,division_id,coalesce(sum(rs1),0)as rs1,coalesce(sum(rs2),0) as rs2,
					  			coalesce(sum(rs3),0) as rs3,coalesce(sum(rs4),0) as rs4, coalesce(sum(rs5),0) as rs5,coalesce(sum(rs6),0) as rs6,
					  			coalesce(sum(rs7),0) as rs7,coalesce(sum(rs8),0) as rs8,coalesce(sum(rs9),0) as rs9, coalesce(sum(rs10),0) as rs10,
					  			coalesce(sum(rs11),0) as rs11,coalesce(sum(rs12),0) as rs12,coalesce(sum(rs13),0) as rs13,coalesce(sum(rstot),0) as rstot, 
					  			coalesce(sum(rs15),0) as rs15,year, quarter from (select unit_id,circle_id,division_id,
					  			EXTRACT(YEAR FROM appellate_1st_desicion_date)::int as year,EXTRACT(quarter FROM appellate_1st_desicion_date)::int as quarter,
					  			case when appellate_1st_desicion_rej_section=1 then count(pro_g_id) end as rs1,	
					  			case when appellate_1st_desicion_rej_section=2 then count(pro_g_id) end as rs2,
					  			case when appellate_1st_desicion_rej_section=3 then count(pro_g_id) end as rs3,
					  			case when appellate_1st_desicion_rej_section=4 then count(pro_g_id) end as rs4,
					  			case when appellate_1st_desicion_rej_section=5 then count(pro_g_id) end as rs5,case when appellate_1st_desicion_rej_section=6 then count(pro_g_id) end as rs6,
					  			case when appellate_1st_desicion_rej_section=7 then count(pro_g_id) end as rs7,	case when appellate_1st_desicion_rej_section=8 then count(pro_g_id) end as rs8,
					  			 case when appellate_1st_desicion_rej_section=9 then count(pro_g_id) end as rs9,case when appellate_1st_desicion_rej_section=10 then count(pro_g_id) end as rs10,
					  			 case when appellate_1st_desicion_rej_section=11 then count(pro_g_id) end as rs11,case when appellate_1st_desicion_rej_section=12 then count(pro_g_id) end as rs12,
					  			 case when appellate_1st_desicion_rej_section=13 then count(pro_g_id) end as rs13,case when appellate_1st_desicion_rej_section in (1,2,3,4,5,6,7,8,9,10,11,12,13,15) 
					  			 then count(pro_g_id) end as rstot,case when appellate_1st_desicion_rej_section=15 then count(pro_g_id) end as rs15 
					  			 from rti_proforma_g where delete_flag='f' and is_latest='t' group by unit_id,appellate_1st_desicion_rej_section,appellate_1st_desicion_date,
					  			 circle_id,division_id )a where year=:year and quarter=:qtr group by unit_id,year,quarter,circle_id,division_id)f 
					  			 on f.unit_id=d.unit_id and f.circle_id=d.circle_id and f.division_id=d.division_id ) n
					  			 where n.unit_id=:unit and n.circle_id=:circle and n.division_id=:division
					  			 			  """ , nativeQuery = true)	  
			
		List<Map<String, Object>> getrtiAppealDivisionUCDConsolidatedPrfmG(@Param("year") Integer year, @Param("qtr") Integer quarter,@Param("dt") Timestamp timestamp,@Param("unit") Integer Unit,@Param("circle") Integer Circle,@Param("division") Integer Division);

}
