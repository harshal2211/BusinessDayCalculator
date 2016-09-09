package businessdaycalc;

import static businessdaycalc.BusinessDayCalc.TOTAL_MINS_IN_BUSINESS_DAY;

public class BusinessTime {

	private int days;
	private int hours;
	private int mins;

	public BusinessTime(int mins){
		int days = mins / TOTAL_MINS_IN_BUSINESS_DAY;
		mins  = mins % TOTAL_MINS_IN_BUSINESS_DAY;
		int hours = mins /60;
		mins = mins % 60;
		this.days = days;
		this.hours = hours;
		this.mins = mins;
	}

	public int getDays(){
		return days;
	}

	public int getHours(){
		return hours;
	}

	public int getMins(){
		return mins;
	}

	public int getTotalMinutes(){
		return ((days*TOTAL_MINS_IN_BUSINESS_DAY) + (hours*60) + mins);
	}

	@Override
	public String toString(){
		return String.format("%sd %shrs %smins",days,hours,mins);
	}

}
