package in.OAndM.utils;

import java.text.ParseException;



import java.text.SimpleDateFormat;


import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;

public class DateUtil {

	/**
	 * 
	 * This method used to get the SQL Date and Time
	 * 
	 * @return Timestamp
	 */
	public static Timestamp getSQLDateWithTime() {

		java.util.Date javaDate = new java.util.Date();
		long javaTime = javaDate.getTime();

		// -- converting util date to sql data
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(javaTime);
		return sqlTimestamp;
	}
	
	public static String getSQLDateWithTime(String dateString) throws ParseException {
        SimpleDateFormat sd= new SimpleDateFormat("dd/MM/yyyy");
        
       
		java.util.Date javaDate = new java.util.Date();
		 javaDate= sd.parse(dateString);
		 
		 
		 
		long javaTime = javaDate.getTime();

		// -- converting util date to sql data
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(javaTime);
		return sqlTimestamp.toString();
	}
	
	public static Timestamp getSQLTimestamp(String dateString) throws ParseException {
        SimpleDateFormat sd= new SimpleDateFormat("dd/MM/yyyy");
        
       
		java.util.Date javaDate = new java.util.Date();
		javaDate= sd.parse(dateString);
		 
		long javaTime = javaDate.getTime();

		// -- converting util date to sql data
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(javaTime);
		return sqlTimestamp;
	}
	
	public static java.sql.Date getSQLDate(String dateString) throws ParseException {
        SimpleDateFormat sd= new SimpleDateFormat("dd/MM/yyyy");
        
       
		java.util.Date javaDate = new java.util.Date();
		 javaDate= sd.parse(dateString);
		 
		 
		 
		long javaTime = javaDate.getTime();

		// -- converting util date to sql data
		java.sql.Date sqlDate = new java.sql.Date(javaTime);
		return sqlDate;
	}
	
	public static java.sql.Date getSQLDate1(String dateString) throws ParseException {
        SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd");
        
       
		java.util.Date javaDate = new java.util.Date();
		 javaDate= sd.parse(dateString);
		 
		 
		 
		long javaTime = javaDate.getTime();

		// -- converting util date to sql data
		java.sql.Date sqlDate = new java.sql.Date(javaTime);
		return sqlDate;
	}
	
	public static java.sql.Date getSQLDateUtilDate(Date utilDate) throws ParseException {
        SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd");
        
       
//		java.util.Date javaDate = new java.util.Date();
//		 javaDate= sd.parse(dateString);
//		 
		 
		 
		long javaTime = utilDate.getTime();

		// -- converting util date to sql data
		java.sql.Date sqlDate = new java.sql.Date(javaTime);
		return sqlDate;
	}
	
	
	public static java.sql.Date getSQLDate2(String dateString) throws ParseException {
        SimpleDateFormat sd= new SimpleDateFormat("dd-MM-yyyy");
        
       
		java.util.Date javaDate = new java.util.Date();
		 javaDate= sd.parse(dateString);
		 
		 
		 
		long javaTime = javaDate.getTime();

		// -- converting util date to sql data
		java.sql.Date sqlDate = new java.sql.Date(javaTime);
		return sqlDate;
	}
	

	public static String addDays(String date, int days) throws ParseException {
		String dt = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date javaDate = new java.util.Date();
		javaDate = format.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(javaDate);
		cal.add(Calendar.MONTH, days); // minus number would decrement the days
		StringBuffer ret = new StringBuffer();
		ret.append(cal.get(Calendar.YEAR));
		ret.append("-");
		if (cal.get(Calendar.MONTH)>= 1 && cal.get(Calendar.MONTH) <= 9) {
			ret.append("0");
			ret.append(cal.get(Calendar.MONTH) + 1);
		} else {
			ret.append(cal.get(Calendar.MONTH) + 1);
		}

		ret.append("-");
		ret.append(cal.get(Calendar.DATE));
		return ret.toString();

	}




}
