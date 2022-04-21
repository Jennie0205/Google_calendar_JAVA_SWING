package proj;

/**
 * 
 * @author Leon, Chirag, Jennie
 * @Version 1.0
 * @Since 8/4/2021
 *
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalendarModel {
	private HashSet<Event> events;
	private ArrayList<ChangeListener> listeners;
	private static LocalDate dates;
	
	/**
	 * This is a constructor that initializes the events, change listener and dates
	 *  to the now
	 * 
	 */
	public CalendarModel() {
		events = new HashSet<>();
		listeners = new ArrayList<ChangeListener>();
		dates = LocalDate.now();
	}
	
	/**
	 * This is a method that is used to return the events in the hashSet
	 * @return returns the events which is a hashset
	 */
	public HashSet<Event> getEvents() {
		return events;
	}
	
	/**
	 * This is method that adds single events into the the hashset and then returns it based on the date
	 * @param date is a parameter that gets the date
	 * @return the event which is a hashset
	 */
	public HashSet<Event> singleEvents(LocalDate date) {
		HashSet<Event> event = new HashSet<>();
		for (Event e : events) {
			if (e.getDate().equals(date))	
				event.add(e);
		}
		return event;
	}
	
	/**
	 * This is a getter method that returns the date
	 * @return the LocalDate date variable that was instantiated 
	 */
	public LocalDate getDate() {
		return dates;
	}
	
	/**
	 * This method is a setter and sets the localDate variable. It also updates the the new value for the changeListener
	 * @param date is a parameter that is set
	 */
	public void setDate(LocalDate date) {
		dates = date;
		update();
	}
	
	/**
	 * This method increases the date by one using the localdate
	 * @param i parameter that lets us choose how much to increase it by
	 */
	public void increaseDay(int i) {
		dates = dates.plusDays(i);
		update();
	}
	
	/**
	 * This method increase the amount of weeks by i
	 * @param i is a parameter the increases the amount of weeks by input
	 */
	public void increaseWeek(int i) {
		dates = dates.plusWeeks(i);
		update();
	}
	
	/**
	 * This method increases the month by amount i
	 * @param i is a parameter that increase the month based on input
	 */
	public void increaseMonth(int i) {
		dates = dates.plusMonths(i).withDayOfMonth(1);
		update();
	}
	
	/**
	 * This is a method that is part of change listener that adds the arraylist to listeners
	 * @param listener takes in listener paramete
	 */
	public void attach(ChangeListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * This is a method that updates the state of something when called
	 */
	private void update() {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 *  This method is created to add an event and return true
	 * @param e is a parameter that takes in the event
	 * @return true when the event is added
	 */
	public boolean addEvent(Event e) {
		events.add(e);
		update();
		return true;
	}
}