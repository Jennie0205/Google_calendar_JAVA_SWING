package proj;

/**
 * 
 * @author Leon, Chirag, Jennie
 * @Version 1.0
 * @Since 8/4/2021
 *
 */
import javax.swing.event.ChangeListener;

public interface CalendarChangeState extends ChangeListener {
	/**
	 * 	This helps move the calendar state forward
	 */
	public void next();

	/**
	 * 	This helps move the calendar state backward
	 */
	public void previous();
}
