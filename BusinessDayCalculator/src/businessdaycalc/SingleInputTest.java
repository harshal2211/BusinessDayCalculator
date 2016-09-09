package businessdaycalc;

import java.sql.Timestamp;
import java.text.ParseException;

public class SingleInputTest {

	public static void main(String[] args) {
		String input = "5/09/2016 9:00:00,6/09/2016 9:00:00,1d 0hrs 0mins";
		String[] inputArr = input.split(",");
		if(inputArr.length != 3){
			throw new RuntimeException("Invalid input");
		}
		String startDate = inputArr[0];
		String endDate = inputArr[1];
		String expectedDifference = inputArr[2];
		String actualDifference = null;
		Timestamp startTimestamp;
		Timestamp endTimestamp;
		BusinessDayCalc calculator = new BusinessDayCalc();
		try{
			startTimestamp = calculator.convertStringtoTimestamp(startDate);
			endTimestamp = calculator.convertStringtoTimestamp(endDate);
			actualDifference = calculator.calculateWorkingDayDifference(startTimestamp, endTimestamp);
		}catch(ParseException parseException){
			throw new RuntimeException("Invalid date");
		}
		System.out.println("StartDate : "+startDate+" | EndDate : "+ endDate+ " | ExpectedDifference : "+ expectedDifference+" | Actual Difference : "+actualDifference);
		if(expectedDifference.equals(actualDifference)){
			System.out.println("PASS");
		}else{
			System.out.println("FAIL");
		}
	}
}
