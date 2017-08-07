package chris_test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeThis {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime dateTime1=  LocalDateTime.now();
		LocalDateTime dateTime2= LocalDateTime.parse("2019-03-03 17:00:00", formatter);
		
		long daysLeft = java.time.Duration.between(dateTime1, dateTime2).toDays();
		
		LocalDate today = LocalDate.now();
		LocalDate birthday = LocalDate.of(2019, Month.MARCH, 3);
		
		Period p = Period.between(today, birthday);

		long p2 = ChronoUnit.DAYS.between(today, birthday);
		
		System.out.println("Time Difference: " + p.getYears() + " years, " + p.getMonths() +
		                   " months, and " + p.getDays() +
		                   " days. (" + p2 + " days total)");
	
		System.out.print("\nTotal Days: " + daysLeft);
		
	}

}
