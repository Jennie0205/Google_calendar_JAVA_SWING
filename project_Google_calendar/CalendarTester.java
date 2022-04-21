package proj;

/**
 * 
 * @author Leon, Chirag, Jennie
 * @Version 1.0
 * @Since 8/4/2021
 *
 */
public class CalendarTester {
	public static void main(String[] args)  {
		CalendarModel model = new CalendarModel();
		View view = new View(model);
		model.attach(view);
	}
}