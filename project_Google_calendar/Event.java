package proj;

/**
 * 
 * @author Leon, Chirag, Jennie
 * @Version 1.0
 * @Since 8/4/2021
 *
 */
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

public class Event implements Comparable<Event> {
	private LocalDate startDate, endDate;
	private TimeInterval timeInterval;
	private boolean normal;
	private String daysStr = "MTWHFAS";
	private String name;
	private LocalDate date;
	private ArrayList<DayOfWeek> days;
	private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm").withResolverStyle(ResolverStyle.STRICT);
	
	/**
	 * This method initializes and event and creates the event. It also adds the day of the week based on
	 * @param name is a parameter that gets the name of the event
	 * @param days initializes the arraylist
	 * @param startTime initializes the start time of the event
	 * @param endTime initializes the end time of the event
	 * @param startDate initializes the start date of the event
	 * @param endDate  initializes the end date of the event
	 */
	public Event(String name, String days, LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate) {
		this.name = name;
		this.date = startDate;
		this.days = new ArrayList<>();
		char[] daysChar = days.toUpperCase().toCharArray();
		for (char c : daysChar) {
			this.days.add(DayOfWeek.of(daysStr.indexOf(c) + 1));
		}

		this.timeInterval = new TimeInterval(startTime, endTime);
		this.startDate = startDate;
		this.endDate = endDate;
		this.normal = true;
	}
	
	/**
	 * This method  also creates an event but also initializes the boolean normal to make sure its an event
	 * @param name initializes the name of the event
	 * @param date initializes the date of the event
	 * @param startTime initializes the start time of the event
	 * @param endTime initializes the end time of the event
	 */
	public Event(String name, LocalDate date, LocalTime startTime, LocalTime endTime) {
		this.name = name;
		this.date = date;
		this.timeInterval = new TimeInterval(startTime, endTime);
		this.normal = false;
	}
	
	/**
	 * This method returns the name of the evnet
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method returns the date variable of LocalDate
	 * @return the date variable
	 */
	public LocalDate getDate() {
		return date;
	}
	
	/**
	 * This checks to see if the method occursOn a specific date so there aren't mutiple dates
	 * @param date takes the current date
	 * @return returns false 
	 */
	public boolean occursOn(LocalDate date) {
		if (!this.normal && this.date.equals(date)) {
			return true;
		} else if (this.isRecursive() && !this.getStartDate().isAfter(date) && !this.getEndDate().isBefore(date)
				&& this.getDays().contains(date.getDayOfWeek())) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method returns the start date
	 * @return date of the start
	 */
	public LocalDate getStartDate() {
		return startDate;
	}
	
	/**
	 * This method returns the end date
	 * @return gets the end date
	 */
	public LocalDate getEndDate() {
		return endDate;
	}
	
	/**
	 * This method returns the arraylist of the days
	 * @return returns days
	 */
	public ArrayList<DayOfWeek> getDays() {
		return days;
	}
	
	/**
	 * This method returns time interval to be able to call TimeInterval
	 * @return timeinterval
	 */
	public TimeInterval getTimeInterval() {
		return timeInterval;
	}
	
	/**
	 * This method checks the date is recursive and calls upon itself
	 * @return normal for not recursive
	 */
	public boolean isRecursive() {
		return normal;
	}
	
	/**
	 * This method is a compareto method that compares the two different times so they dont overlap
	 * @return
	 */
	public int compareTo(Event that) {
		LocalDateTime startTime1 = LocalDateTime.of(this.date, this.timeInterval.getStartTime());
		LocalDateTime startTime2 = LocalDateTime.of(that.date, that.timeInterval.getStartTime());
		if (startTime1.compareTo(startTime2) != 0) {
			return startTime1.compareTo(startTime2);
		}
		
		return this.getName().compareTo(that.getName());
	}
	
	/**
	 * This is a to string method that formats the correct time for the month
	 * @return a string
	 */
	public String toStringDay() {
		return String.format("%s: %s-%s", this.name, this.timeInterval.getStartTime().format(timeFormatter), this.timeInterval.getEndTime().format(timeFormatter));
	}
}