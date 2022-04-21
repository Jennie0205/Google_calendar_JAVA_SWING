package proj;

/**
 * 
 * @author Leon, Chirag, Jennie
 * @Version 1.0
 * @Since 8/4/2021
 *
 */
import java.time.LocalDate;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class DatePicker extends JPanel implements CalendarChangeState {
	private LocalDate now;
	private CalendarModel model;
	
	/**
	 * This method initializes different things like model, now from localDate and calls the display Method
	 * @param model
	 */
	public DatePicker(CalendarModel model) {
		this.model = model;
		now = model.getDate();
		display();
	}
	
	/**
	 * This method is the gui part of the date picker on the left side. It creates buttons and values and displays them all on the left side
	 * It also adds the left and right button and increased and decreases month
	 */
	public void display() {
		this.removeAll();
		this.revalidate();

		JPanel allButtonsPanel = new JPanel();
		allButtonsPanel.setLayout(new BorderLayout());
		JPanel calendarPanel = new JPanel();
		calendarPanel.setLayout(new GridLayout(8, 7));

		JLabel titleLabel = new JLabel("   " + now.getMonth().name() + " " + now.getYear(), SwingConstants.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		JButton left = new JButton("<");
		JButton right = new JButton(">");
		
		buttonPanel.add(left);
		buttonPanel.add(right);
		
		left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previous();
			}
		});

		right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});

		allButtonsPanel.add(titleLabel, BorderLayout.WEST);
		allButtonsPanel.add(buttonPanel, BorderLayout.EAST);
		
		JButton sunday = new JButton("Sun");
		sunday.setEnabled(false);
		JButton monday = new JButton("Mon");
		monday.setEnabled(false);
		JButton tuesday = new JButton("Tue");
		tuesday.setEnabled(false);
		JButton wednesday = new JButton("Wed");
		wednesday.setEnabled(false);
		JButton thursday = new JButton("Thu");
		thursday.setEnabled(false);
		JButton friday = new JButton("Fri");
		friday.setEnabled(false);
		JButton saturday = new JButton("Sat");
		saturday.setEnabled(false);
		
		calendarPanel.add(sunday);
		calendarPanel.add(monday);
		calendarPanel.add(tuesday);
		calendarPanel.add(wednesday);
		calendarPanel.add(thursday);
		calendarPanel.add(friday);
		calendarPanel.add(saturday);

		JButton[][] daysButtons = new JButton[6][7];
		LocalDate firstDayOfWeek = now.withDayOfMonth(1);
		int day = 1;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				JButton pickedDay = new JButton();
				pickedDay.setBackground(new Color(230, 230, 230));
				pickedDay.setOpaque(true);
				pickedDay.setEnabled(false);

				if (i == 0 && j < firstDayOfWeek.getDayOfWeek().getValue()) {}
				else if (day <now.lengthOfMonth() + 1) {
					pickedDay.setText("" + day);
					pickedDay.setEnabled(true);

					LocalDate Selectedbutton = now.withDayOfMonth(Integer.parseInt(pickedDay.getText()));

					if (Selectedbutton.equals(LocalDate.now())) {
						pickedDay.setBackground(Color.blue);
						pickedDay.setForeground(Color.WHITE);
					} else if (Selectedbutton.equals(model.getDate())) {
						pickedDay.setBackground(Color.GRAY);
					} else {
						pickedDay.setBackground(Color.WHITE);
					}

					pickedDay.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							model.setDate(Selectedbutton);
						}
					});
					
					day++;
				}

				daysButtons[i][j] = pickedDay;
				calendarPanel.add(daysButtons[i][j]);
			}
		}

		this.setLayout(new BorderLayout());
		this.add(allButtonsPanel, BorderLayout.NORTH);
		this.add(calendarPanel, BorderLayout.CENTER);
	}
	
	/**
	 * This method stateChanged is from an implementation and just changes the state from the event
	 */
	public void stateChanged(ChangeEvent e) {
		now = model.getDate();
		this.display();
	}
	
	/**
	 * This method gets the next month,
	 * then displays
	 */
	public void next() {
		now = now.plusMonths(1);
		this.display();
	}
	
	/**
	 * This method gets the previous month,
	 * then displays
	 */
	public void previous() {
		now = now.plusMonths(-1);
		this.display();
	}
}