package businessdaycalc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;

public class MultipleInputTest {

	public static void main(String[] args) throws IOException{
		if(args.length != 1){
			throw new RuntimeException("Input file is required ");
		}
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(args[0])))){
			String line;
			while((line=bufferedReader.readLine())!=null){
				String[] input = line.split(",");
				if(input.length != 3){
					System.err.println("Invalid input, skipping this row");
					continue;
				}
				String startDate = input[0];
				String endDate = input[1];
				String expectedDifference = input[2];
				Timestamp startTimestamp;
				Timestamp endTimestamp;
				BusinessDayCalc calculator = new BusinessDayCalc();
				try{
					startTimestamp = calculator.convertStringtoTimestamp(startDate);
					endTimestamp = calculator.convertStringtoTimestamp(endDate);
				}catch(ParseException parseException){
					System.err.println("Invalid date encountered skipping this row");
					continue;
				}
				String actualDifference = calculator.calculateWorkingDayDifference(startTimestamp, endTimestamp);

				System.out.println("StartDate : "+startDate+" | EndDate : "+ endDate+ " | ExpectedDifference : "+ expectedDifference+" | Actual Difference : "+actualDifference);
				if(expectedDifference.equals(actualDifference)){
					System.out.println("PASS");
				}else{
					System.out.println("FAIL");
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
}
