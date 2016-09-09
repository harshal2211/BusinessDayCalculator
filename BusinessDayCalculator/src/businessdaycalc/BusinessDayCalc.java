package businessdaycalc;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BusinessDayCalc {

	private static final int OFFICE_START_HOUR = 9;
	private static final int OFFICE_CLOSE_HOUR = 17;
	public static final int TOTAL_MINS_IN_BUSINESS_DAY = (OFFICE_CLOSE_HOUR - OFFICE_START_HOUR) * 60;
	private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

	public String calculateWorkingDayDifference(Timestamp timestamp1, Timestamp timestamp2){
		Calendar startDate = Calendar.getInstance();
		startDate.setTimeInMillis(timestamp1.getTime());
		Calendar endDate = Calendar.getInstance();
		endDate.setTimeInMillis(timestamp2.getTime());

		startDate = adjustDate(startDate);
		endDate = adjustDate(endDate);
		
		System.out.println("adjusted Start Date : "+ startDate.getTime());
		System.out.println("adjusted End Date : "+ endDate.getTime());
		
		int workDays = 0;
		int startDayDiffInMins = 0;
		int endDayDiffInMins = 0;
		if(isSameDay(startDate, endDate)){
			long diffInMillis = endDate.getTimeInMillis() - startDate.getTimeInMillis();
			int mins = (int)(diffInMillis/1000)/60;
			System.out.println(diffInMillis+ " : "+ mins);
			return new BusinessTime(mins).toString();
		}else{
			Calendar tempDate = (Calendar)startDate.clone();
			while(!isSameDay(tempDate,endDate)){
				tempDate.add(Calendar.DAY_OF_MONTH,1);
				if (tempDate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && tempDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					workDays++;
				}
			}
			workDays = workDays -1 ;
			startDayDiffInMins = getStartDayDifference(startDate);
			endDayDiffInMins = getEndDayDifference(endDate);
			System.out.println(startDayDiffInMins+"+"+endDayDiffInMins+"="+(startDayDiffInMins+endDayDiffInMins));
			int totalMinutes = (workDays*TOTAL_MINS_IN_BUSINESS_DAY)+startDayDiffInMins+endDayDiffInMins;
			return new BusinessTime(totalMinutes).toString();
		}
	}

	public Timestamp convertStringtoTimestamp(String inputDate) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		java.util.Date date = sdf.parse(inputDate);
		return new Timestamp(date.getTime());
	}

	private boolean isSameDay(Calendar startDate, Calendar endDate){
		int startDayOfMonth = startDate.get(Calendar.DAY_OF_MONTH);
		int endDayOfMonth = endDate.get(Calendar.DAY_OF_MONTH);
		int startMonth = startDate.get(Calendar.MONTH)+1;
		int endMonth = endDate.get(Calendar.MONTH)+1;
		int startYear = startDate.get(Calendar.YEAR);
		int endYear = endDate.get(Calendar.YEAR);
		/*System.out.println(String.format("%d/%d/%d",startDayOfMonth,startMonth,startYear) + " : " +
				String.format("%d/%d/%d",endDayOfMonth,endMonth,endYear));*/
		if(startDayOfMonth==endDayOfMonth && startMonth==endMonth && startYear == endYear)
			return true;
		else
			return false;
	}

	private Calendar adjustDate(Calendar inputDate){
		int hour = inputDate.get(Calendar.HOUR_OF_DAY);
		if(inputDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY 
	            || inputDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			inputDate = adjustDateForweekendActivity(inputDate);
	    }

		if(hour >= OFFICE_CLOSE_HOUR ){
			inputDate.set(Calendar.HOUR_OF_DAY, OFFICE_CLOSE_HOUR);
			inputDate.set(Calendar.MINUTE,0);
		}
		if(hour < OFFICE_START_HOUR){
			inputDate.set(Calendar.HOUR_OF_DAY, OFFICE_START_HOUR);
			inputDate.set(Calendar.MINUTE,0);
		}
		return inputDate;
	}
	
	private Calendar adjustDateForweekendActivity(Calendar inputDate) {
	    do{
	    	inputDate.add(Calendar.DAY_OF_MONTH, -1);
	    }while(isHoliday(inputDate));
	    inputDate.set(Calendar.HOUR_OF_DAY, OFFICE_START_HOUR);
	    inputDate.set(Calendar.MINUTE,0);
	    return inputDate;
	}

	private boolean isHoliday(Calendar inputDate) {
	    if(inputDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY 
	            || inputDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
	        return true;
	    }
	    return false;
	}

	private int getStartDayDifference(Calendar inputDate){
		Calendar endDate = (Calendar)inputDate.clone();
		endDate.set(Calendar.HOUR_OF_DAY,OFFICE_CLOSE_HOUR);
		endDate.set(Calendar.MINUTE,0);
		long diffInMillis = endDate.getTimeInMillis() - inputDate.getTimeInMillis();
		int mins = (int)(diffInMillis/1000)/60;
		return mins;
	}

	private int getEndDayDifference(Calendar inputDate){
		Calendar startDate = (Calendar)inputDate.clone();
		startDate.set(Calendar.HOUR_OF_DAY,OFFICE_START_HOUR);
		startDate.set(Calendar.MINUTE,0);
		long diffInMillis = inputDate.getTimeInMillis() - startDate.getTimeInMillis();
		int mins = (int)(diffInMillis/1000)/60;
		return mins;
	}

}
