package proj;

/**
 * 
 * @author Leon, Chirag, Jennie
 * @Version 1.0
 * @Since 8/4/2021
 *
 */
import java.time.LocalTime;

public class TimeInterval {
	private LocalTime startTime, endTime;
	
	/**
	 * A method that gets the time interval between two times
	 * @param startTime initializes the start time
	 * @param endTime initializes the end time
	 */
	public TimeInterval(LocalTime startTime, LocalTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	/**
	 * This gets the start time for localTime
	 * @return the startTime
	 */
	public LocalTime getStartTime() {
		return startTime;
	}
	
	/**
	 * This gets the end time for localTime
	 * @return the end time
	 */
	public LocalTime getEndTime() {
		return endTime;
	}
	
	/**
	 * This checks to see if the time for two events conflict with each other
	 * @param that is an object created to check two different events
	 * @return false if it doesnt conflict
	 */
	public boolean conflictsWith(TimeInterval that) {
		LocalTime startTime1 = this.getStartTime();
		LocalTime endTime1 = this.getEndTime();
		LocalTime startTime2 = that.getStartTime();
		LocalTime endTime2 = that.getEndTime();

		if ((startTime1.isBefore(endTime2) && endTime1.isAfter(startTime2))|| (startTime2.isBefore(endTime1) && endTime2.isAfter(startTime1))) {
			return true;
		}
		return false;
	}
}