package proj;

/**
 * 
 * @author Leon, Chirag, Jennie
 * @Version 1.0
 * @Since 8/4/2021
 *
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Color;
import java.util.HashSet;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class View implements ChangeListener {
	private static final Insets WEST_INSETS = new Insets(5, 0, 5, 5);
	private static final Insets EAST_INSETS = new Insets(5, 5, 5, 0);
	
	  /**
	   * This method is the gui of the project that creates the frame and panels and buttons and adds them
	   * all the JFrame and essentially creates all the buttons and the create button. It also erases what is on the panel
	   * similar to a refresh that allows a new panel to be revalidated and 
	   * @param model this takes in the calendarModel parameter
	   */
	public View(CalendarModel model) {
		DatePicker selectedMonthView = new DatePicker(model);
		DayView dayView = new DayView(model);
		WeekView weekView = new WeekView(model);
		MonthView monthView = new MonthView(model);

		model.attach(selectedMonthView);
		model.attach(dayView);
		model.attach(weekView);
		model.attach(monthView);

		JFrame frame = new JFrame();
		frame.setTitle("Calendar");
		frame.setMinimumSize(new Dimension(1135,500));

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());

		JPanel currentViewNav = new JPanel();
		currentViewNav.setLayout(new GridBagLayout());

		JButton today = new JButton("Today");
		JButton left = new JButton("<<");
		JButton right = new JButton(">>");
		JButton create = new JButton("Create Event");

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		currentViewNav.add(today, c);
		c.gridx++;
		currentViewNav.add(left, c);
		c.gridx++;
		currentViewNav.add(right, c);
		c.gridx++;
		currentViewNav.add(create, c);

		JPanel navChange = new JPanel();
		navChange.setLayout(new GridLayout(1, 5));

		JButton dayButton = new JButton("Day");
		JButton weekButton = new JButton("Week");
		JButton monthButton = new JButton("Month");
		JButton agendaButton = new JButton("Agenda");
		JButton fromFileButton = new JButton("From File");
		
		today.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setDate(LocalDate.now());
			}
		});

		left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component rightView = rightPanel.getComponent(1);
				if (rightView instanceof CalendarChangeState) {
					((CalendarChangeState) rightView).previous();
				}
			}
		});

		right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component rightView = rightPanel.getComponent(1);
				if (rightView instanceof CalendarChangeState) {
					((CalendarChangeState) rightView).next();
				}
			}
		});

		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel dialogPanel2 = new JPanel(new GridBagLayout());

				Border title = BorderFactory.createTitledBorder("Create Event");
				Border empty = BorderFactory.createEmptyBorder(10, 10, 10, 10);
				Border combined = BorderFactory.createCompoundBorder(title, empty);
				dialogPanel2.setBorder(combined);
				JTextField eventNameText = new JTextField(5);
				JTextField dateText = new JTextField(5);
				JTextField startTimeText = new JTextField(5);
				JTextField endTimeText = new JTextField(5);
				
				dialogPanel2.add(new JLabel("Event Name:"), createGbc(0, 0));
				dialogPanel2.add(eventNameText, createGbc(1, 0));
				dialogPanel2.add(new JLabel("Date:"), createGbc(0, 1));
				dialogPanel2.add(dateText, createGbc(1, 1));
				dialogPanel2.add(new JLabel("Start Time:"), createGbc(0, 2));
				dialogPanel2.add(startTimeText, createGbc(1, 2));
				dialogPanel2.add(new JLabel("End Time:"), createGbc(0, 3));
				dialogPanel2.add(endTimeText, createGbc(1, 3));
				
				int result = JOptionPane.showConfirmDialog(null, dialogPanel2, "Create Event", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				
				if (result == JOptionPane.OK_OPTION) {
					try {
						String name = eventNameText.getText();
						if (name.length() == 0)
							throw new NullPointerException();

						String dates = dateText.getText();
						String[] dateArray = dates.split("/");
						
						int year = Integer.parseInt(dateArray[2]);
						int month = Integer.parseInt(dateArray[0]);
						int dayOfMonth = Integer.parseInt(dateArray[1]);
						
						LocalDate date = LocalDate.of(year, month, dayOfMonth);
						String startTimeStr = startTimeText.getText();
						int starttime = Integer.parseInt(startTimeStr);
						LocalTime startTime = LocalTime.of(starttime, 0);
						String endTimeStr = endTimeText.getText();
						int endtime = Integer.parseInt(endTimeStr);
						LocalTime endTime = LocalTime.of(endtime, 0);

						Event event = new Event(name, date, startTime, endTime);
						model.addEvent(event);
					} catch (Exception ex) {

					}
				}
			}
		});

		dayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();
				rightPanel.add(navChange, BorderLayout.NORTH);
				rightPanel.add(dayView, BorderLayout.CENTER);
				rightPanel.revalidate();
				rightPanel.repaint();
			}
		});

		weekButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();
				rightPanel.add(navChange, BorderLayout.NORTH);
				rightPanel.add(weekView, BorderLayout.CENTER);
				rightPanel.revalidate();
				rightPanel.repaint();
			}
		});

		monthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();
				rightPanel.add(navChange,BorderLayout.NORTH);
				rightPanel.add(monthView,BorderLayout.CENTER);
				rightPanel.revalidate();
				rightPanel.repaint();
			}
		});

		agendaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAgenda();
			}
		});


		fromFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importFile(model);
			}
		});

		navChange.add(dayButton);
		navChange.add(weekButton);
		navChange.add(monthButton);
		navChange.add(agendaButton);
		navChange.add(fromFileButton);

		leftPanel.add(currentViewNav, BorderLayout.NORTH);
		rightPanel.add(navChange, BorderLayout.NORTH);
		rightPanel.add(dayView, BorderLayout.CENTER);
		leftPanel.add(selectedMonthView, BorderLayout.CENTER);

		frame.add(leftPanel, BorderLayout.WEST);
		frame.add(rightPanel, BorderLayout.CENTER);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 *  This method uses JFileChooser and gets the src file and make sure that the extension is a txt or else it wouldn't work
	 *  then it gets the selected file and add events
	 * @param model is the parameter
	 */
	public static void importFile(CalendarModel model) {
		JFileChooser fileChooser;
		fileChooser = new JFileChooser(new File("src"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("Text Documents (*.txt)", "txt"));
		fileChooser.showOpenDialog(null);
		addEvents(fileChooser.getSelectedFile(), model);
	}
	
	/**
	 * This method takes the file and parses the file and creates a new event and adds the event to the HashSet
	 * @param file is a parameter that gets a file
	 * @param model is a parameter that allows us to get the events
	 * @return true when it parses it properly and adds the event
	 */
	private static boolean addEvents(File file, CalendarModel model) {
		try {
			Scanner scan = new Scanner(file);

			while (scan.hasNextLine()) {
				String eventString = scan.nextLine();
				String[] eventArray = eventString.split(";");
				
				String name = eventArray[0];
				int day = 1;
				String days = eventArray[4];
				int startYear = Integer.parseInt(eventArray[1]);
				int startMonth = Integer.parseInt(eventArray[2]);
				int endYear = Integer.parseInt(eventArray[1]);
				int endMonth = Integer.parseInt(eventArray[3]);
				int startTimeHour = Integer.parseInt(eventArray[5]);
				int endTimeHour = Integer.parseInt(eventArray[6]);
				LocalDate startDate = LocalDate.of(startYear, startMonth, day);
				LocalDate endDate = LocalDate.of(endYear, endMonth, day);
				LocalTime startTime = LocalTime.of(startTimeHour, 0);
				LocalTime endTime = LocalTime.of(endTimeHour, 0);
				Event newEvent = new Event(name, days, startTime, endTime, startDate, endDate);

				model.addEvent(newEvent);
			}
			
			scan.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * This method is just used to create a pop up to show the dialogue panel.
	 */
	public static void showAgenda() {
        JPanel dialogPanel = new JPanel(new GridBagLayout());

		Border title = BorderFactory.createTitledBorder("Enter the time frame");
		Border empty = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border combined = BorderFactory.createCompoundBorder(title, empty);
		dialogPanel.setBorder(combined);
		JTextField startDate = new JTextField(5);
		JTextField endDate = new JTextField(5);
		
		dialogPanel.add(new JLabel("Start Date:"), createGbc(0, 0));
		dialogPanel.add(startDate, createGbc(1, 0));
		dialogPanel.add(new JLabel("End Date:"), createGbc(0, 1));
		dialogPanel.add(endDate, createGbc(1, 1));
		
		JOptionPane.showConfirmDialog(null, dialogPanel);
		
	}
	
	/**
	 * This method creates a grid bag constraints and puts the anchor and fill.
	 * @param x takes x  input for gridx 
	 * @param y takes the y input for grid y
	 * @return
	 */
	private static GridBagConstraints createGbc(int x, int y) {
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = x;
	    gbc.gridy = y;
	    gbc.gridwidth = 1;
	    gbc.gridheight = 1;

	    if (x == 0) {
	    	gbc.anchor = GridBagConstraints.WEST;
	    } else {
	    	gbc.anchor = GridBagConstraints.EAST;
	    }
	    
	    if (x == 0) {
	    	gbc.fill = GridBagConstraints.BOTH;
	    } else {
	    	gbc.fill = GridBagConstraints.HORIZONTAL;
	    }

	    if (x == 0) {
	    	gbc.insets = WEST_INSETS;
	    } else {
	    	gbc.insets = EAST_INSETS;
	    }
	    
	    if (x == 0) {
	    	gbc.weightx = 0.1;
	    } else {
	    	gbc.weightx = 1.0;
	    }
	    gbc.weighty = 1.0;
	    
	    return gbc;
    }

	/**
	 * 
	 * This class is used for created the DayView gui component and populating events
	 *
	 */
	static class DayView extends JPanel implements CalendarChangeState {
		private CalendarModel model;
		private LocalDate now;
		private HashSet<Event> events;
		private LocalDate startDate;
		
        /**
		 * This is a constructor that initializes the model and events and then displays
		 * @param model
		 */
		public DayView(CalendarModel model) {
			this.model = model;
			events = model.getEvents();
			display(now);
		}
		
        /**
		 * This method just displays the title of the day and adds it to the panel, and adds the display Events.
		 * @param date
		 */
		public void display(LocalDate date) {
			this.removeAll();
			this.revalidate();

			now = model.getDate();

			JLabel dayTitle = new JLabel(now.getDayOfWeek().name() + ", " + now.getMonth().name() + " " + now.getDayOfMonth() + ", " + now.getYear());
			JPanel dayPanel = new JPanel();
			
			JPanel mainPanel = new JPanel();
			dayPanel.add(dayTitle);
			
			BoxLayout box = new BoxLayout(mainPanel,BoxLayout.X_AXIS);

			mainPanel.setLayout(box);
			mainPanel.add(dayPanel,BorderLayout.NORTH);
			this.add(mainPanel);
			this.add(showEvents());
		}
		
        /**
		 * This method gets the start date and creates the size for the JtextArea and events to it and it also wraps the
		 * text so it doesn't move out of the gui program.
		 * @return the JTextArea
		 */
		public JTextArea showEvents() {
			startDate = LocalDate.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
			JTextArea eventsTextArea = new JTextArea();
			eventsTextArea.setPreferredSize(new Dimension(350,300));
			eventsTextArea.setEditable(false);
			eventsTextArea.setLineWrap(true);
			eventsTextArea.setWrapStyleWord(true);
			
			HashSet<LocalDate> dates = new HashSet<LocalDate>();
			LocalDate currentDate = startDate;
			
			while (currentDate.equals(startDate)) {
				dates.add(currentDate);
				currentDate = currentDate.plusDays(1);
			}

			String eventDisplay = "";
			for (LocalDate day : dates) {
				for (Event e : events) {
					if (e.occursOn(day)) {
						eventDisplay = eventDisplay + e.toStringDay() + "\n";
					}
				}
			}
			
			eventsTextArea.setText(eventDisplay);
			return eventsTextArea;
		}
		
        /**
		 * This stateChanged is from JListener that changes state and displays now
		 * 
		 * @param e
		 */
		public void stateChanged(ChangeEvent e) {
			this.display(now);
		}
		
        /**
		 * This is a method that increases the day by one
		 */
		public void next() {
			model.increaseDay(1);
		}
		
        /**
		 * This is a method that decreases the day by 1
		 */
		public void previous() {
			model.increaseDay(-1);
		}
	}

	/**
	 * 
	 * This class is used for created the WeekView gui component and populating events
	 *
	 */
	static class WeekView extends JPanel implements CalendarChangeState {
		private LocalDate now;
		private LocalDate startDate;
		private LocalDate endDate;
		private CalendarModel model;
		private HashSet<Event> events;
		
        /**
		 * This is a constructor that initializes the model  and gets the events and gets the model
		 * 
		 * @param model
		 */
		public WeekView(CalendarModel model) {
			this.model = model;
			events = model.getEvents();
			display(now);
		}
		
        /**
		 * This method creates panel for the day and month, and creates another panel for the day of the week, and creates another panel for the box
		 * Then create a box layout and adds it to the fourth panel and then displays the panel.
		 * @param date
		 */
		public void display(LocalDate date) {
			this.removeAll();
			this.revalidate();
			now = model.getDate();

			this.setLayout(new BorderLayout());

			String currentMonth = now.getMonth().name();
			LocalDate sunday = giveSundayLocalDate(); 
			
			JLabel dayTitle = new JLabel();
			
			if (checkSunday(sunday, now) == 1) {
				dayTitle = new JLabel(currentMonth.substring(0,3) + " - " + now.plusMonths(1).getMonth().name().substring(0,3) + " " + now.plusMonths(1).getYear());
			} else if (checkSunday(sunday, now) == -1) {
				dayTitle = new JLabel(now.minusMonths(1).getMonth().name().substring(0,3) + " - " + currentMonth.substring(0,3) + " " + now.getYear());
			} else {
				dayTitle = new JLabel(currentMonth + " " + now.getYear());
			}

			JPanel firstPanel = new JPanel();
			GridBagConstraints c = new GridBagConstraints();
			firstPanel.add(dayTitle);

			JPanel secondPanel = new JPanel();
			secondPanel.setLayout(new FlowLayout());
			JPanel ThirdPanel = new JPanel();
			ThirdPanel.setLayout(new FlowLayout());
			
			JPanel fourthPanel = new JPanel();
			fourthPanel.add(secondPanel);
			fourthPanel.add(ThirdPanel);
			BoxLayout boxlayout = new BoxLayout(fourthPanel,BoxLayout.PAGE_AXIS);
			fourthPanel.setLayout(boxlayout);

			c.gridx = 0;
			c.gridy = 0;

			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(firstPanel,BorderLayout.NORTH);
			this.add(fourthPanel);
			displayDayView(secondPanel, c, giveSundayLocalDate());
			showEvents(giveSundayLocalDate(), c, ThirdPanel);
		
			revalidate();
			repaint();
		}
		
        /**
		 * This method checks the value of weekdays in a month and returns the value
		 * @param day is a localdate and returns -1 if its less than the current month
		 * @param now is the current month value
		 * @return
		 */
		public int checkSunday(LocalDate day, LocalDate now) {
			if (day.getMonthValue() < now.getMonthValue()) {
				return -1;
			}
			if (day.getDayOfMonth() + 6 > day.lengthOfMonth()) {
				if (day.getYear() < now.getYear()) {
					return -1;
				}
				return 1;
			}
			return 0;
		}
		
        /**
		 * This method is use to keep track of the localDate and the days before sunday
		 * @return the day before sunday
		 */
		public LocalDate giveSundayLocalDate() {
			LocalDate today = model.getDate();
			
			if (today.getDayOfWeek().ordinal() == 0) {
				return today.minusDays(1);
			} else if (today.getDayOfWeek().ordinal() == 1) {
				return today.minusDays(2);
			} else if (today.getDayOfWeek().ordinal() == 2) {
				return today.minusDays(3);
			} else if (today.getDayOfWeek().ordinal() == 3) {
				return today.minusDays(4);
			} else if (today.getDayOfWeek().ordinal() == 4) {
				return today.minusDays(5);
			} else if (today.getDayOfWeek().ordinal() == 5) {
				return today.minusDays(6);
			} else if (today.getDayOfWeek().ordinal() == 6) {
				return today;
			} else {
				return null;
			}
		}
		
        /**
		 * This method is just a method that displays the day view using gridbag and the second panel
		 * @param secondPanel  that takes in the second panel
		 * @param c is just a variable of c when you use it to get the gridx and grid y
		 * @param days is just a variable for LocalDate
		 */
		public void displayDayView(JPanel secondPanel, GridBagConstraints c, LocalDate days) {
			this.incrementDay(days, secondPanel, c);
		}
		
        /**
		 * This method is used to get to get the string of the day of month and day of the week. then it gets added 
		 * to the jLabel and put into a panel
		 * @param day is parameter for LocalDate
		 * @param secondPanel is just a parameter for JPanel
		 * @param c is just a parameter variable for GridBagConstraints
		 */
		public void incrementDay(LocalDate day, JPanel secondPanel, GridBagConstraints c) {
			c.gridy = 0;
			c.fill = GridBagConstraints.HORIZONTAL;

			for (int i = 0; i < 7; i++) {
				c.gridx = i;

				String monthDayStr = Integer.toString(day.getDayOfMonth()) + " : "+ day.getDayOfWeek();
				JLabel today = new JLabel(monthDayStr);
				today.setHorizontalAlignment(SwingConstants.CENTER);
				today.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
				secondPanel.add(today, c);
				day = day.plusDays(1);
			}
		}
		
		/**
		 *  This method display the events and gets the date and adds the current 
		 *  as long as the current date isnt at the end then it can increase days by 1
		 *  Then it creates a string that display. it Creates a new textArea to add the events
		 * @param sunday
		 * @param c
		 * @param ThirdPanel
		 */
		public void showEvents(LocalDate sunday, GridBagConstraints c, JPanel ThirdPanel) {
			startDate = LocalDate.of(sunday.getYear(), sunday.getMonthValue(), sunday.getDayOfMonth());
			endDate = startDate.plusDays(7);
			HashSet<LocalDate> dates = new HashSet<LocalDate>();
			LocalDate current = startDate;
			c.gridx = 0;
			c.gridy = 1;

			while (!current.equals(endDate)) {
				dates.add(current);
				current = current.plusDays(1);
			}

			String eventText = "";
			int i = 0;

			for (LocalDate day : dates) {
				for (Event e : events) {
					if (e.occursOn(day)) {
						eventText = eventText + e.toStringDay() + "\n";
					}
				}
				
				c.gridx = i;
				c.fill = GridBagConstraints.HORIZONTAL;
				JTextArea eventTextArea = new JTextArea(eventText);
				eventTextArea.setPreferredSize(new Dimension(80, 70));
				eventTextArea.setEditable(false);
				eventTextArea.setLineWrap(true);
				eventTextArea.setWrapStyleWord(true);
				ThirdPanel.add(eventTextArea);

				eventText = "";
				i++;
			}
		}
		
        /**
		 * This stateChanged is from JListener that changes state and displays now
		 * 
		 * @param e
		 */
		public void stateChanged(ChangeEvent e) {
			this.display(now);
		}
		
        /**
		 * This is a method that just increases the week by 1
		 */
		public void next() {
			model.increaseWeek(1);
		}
		
        /**
		 * This is a method that just decreases the week by 1
		 */
		public void previous() {
			model.increaseWeek(-1);
		}
	}
	
	/**
	 * 
	 * This class is used for created the MonthView gui component and populating events
	 *
	 */
	static class MonthView extends JPanel implements CalendarChangeState {
		private LocalDate cal;
		private HashSet<Event> events;
		private String thisMonth;
		private CalendarModel model;
		private int length;
		private boolean month, year;
		private int thisYear;
		private LocalDate now;
		
        /**
		 * This is just a constructor that initializes a lot of the variables created 
		 * @param model
		 */
		public MonthView(CalendarModel model) {
			this.model = model;
			cal = model.getDate();
			length = cal.lengthOfMonth();
			month = true;
			year = true;
			thisMonth = cal.getMonth().name();
			thisYear = cal.getYear();
			events = model.getEvents();
			now = model.getDate();
			display();
		}
		
        /**
		 *  This is a method that returns the current month
		 * @return this month
		 */
		public String getMonth() {
			return thisMonth;
		}
		
        /**
		 * This is a setter method that sets this current month
		 * @param thisMonth
		 */
		public void setMonth(String thisMonth) {
			this.thisMonth = thisMonth;
		}
		
        /**
		 * This method return this year 
		 * @return thisYear
		 */
		public int getYear() {
			return thisYear;
		}
		
        /**
		 * This method sets this year
		 * @param thisYear
		 */
		public void setYear(int thisYear) {
			this.thisYear = thisYear;
		}
		
		/**
		 * This method for loops the month and year and adds it to the Jlabel, and then it creates a string of days
		 * and adds its to the jLabel. It also creates a JTextArea and sets the text and adds each text to it
		 * then it compares the days of the week to each other. Using the If and else statements to populate the MonthView
		 * 
		 */
		public void display() {
			this.removeAll();
			this.revalidate();
			LocalDate cal = model.getDate();
			int today = LocalDate.now().getDayOfMonth();
			
			for (int i = 0; i < 7; i++) {
				if (i == 0) {
					JLabel month = new JLabel(cal.getMonth().name());
					add(month);
				} else if (i == 1) {
					JLabel year = new JLabel("" + cal.getYear());
					add(year);
				} else {
					JLabel empty = new JLabel("");
					add(empty);
				}
			}

			String[] days = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
			Border dayNameBorder = BorderFactory.createLineBorder(Color.cyan);

			for (int i = 0; i < 7; i++) {
				JLabel daysOfWeek = new JLabel(days[i]);
				daysOfWeek.setHorizontalAlignment(JLabel.CENTER);
				daysOfWeek.setBorder(dayNameBorder);
				add(daysOfWeek);
			}

			JTextArea[][] daysText = new JTextArea[6][7];
			this.setLayout(new GridLayout(8, 7));
			for (int m = 0; m < 6; m++) {
				for (int y = 0; y < 7; y++) {
					daysText[m][y] = new JTextArea();
					daysText[m][y].setLineWrap(true);
					daysText[m][y].setWrapStyleWord(true);
					daysText[m][y].setBorder(BorderFactory.createLineBorder(Color.pink));
					daysText[m][y].setEditable(false);
					add(daysText[m][y]);
				}
			}

			cal = LocalDate.of(cal.getYear(), cal.getMonthValue(), 1);
			String first = cal.getDayOfWeek().name();

			int count = 0;
			if (first.compareTo("MONDAY") == 0) {
				count = count + 1;
			} else if (first.compareTo("TUESDAY") == 0) {
				count = count + 2;
			} else if (first.compareTo("WEDNESDAY") == 0) {
				count = count + 3;
			} else if (first.compareTo("THURSDAY") == 0) {
				count = count + 4;
			} else if (first.compareTo("FRIDAY") == 0) {
				count = count + 5;
			} else if (first.compareTo("SATURDAY") == 0) {
				count = count + 6;
			}

			LocalDate firstDayOfWeek = now.withDayOfMonth(1);
		
			int day = 1;
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 7; j++) {
					if (i == 0 && j < firstDayOfWeek.getDayOfWeek().getValue()) {
					} else if (day <= now.lengthOfMonth()) {
						try {
							daysText[i][j].setText(day + ": " + getEvents(LocalDate.of(cal.getYear(), cal.getMonth(), day)));
						} catch (Exception e) {}
						day++;
					}
				}
			}
		}
		
        /**
		 * This method makes an event string and returns the event as a string
		 * @param date is a parameter for LocalDate
		 * @return event
		 */
		public String getEvents(LocalDate date) {
			String event = "";
			for (Event e : events) {
				if (e.occursOn(date)) {
					event = event + e.toStringDay();
				}
			}
			return event;
		}
		
        /**
		 * This stateChanged is from JListener that changes state 
		 * 
		 * @param e
		 */
		@Override
		public void stateChanged(ChangeEvent e) {
			this.display();
		}
		
        /**
		 * This method is used to increase the month by 1 
		 */
		public void next() {
			model.increaseMonth(1);
			display();
		}
		
        /**
		 * This method is used to decrease the month by 1
		 */
		public void previous() {
			model.increaseMonth(-1);
			display();
		}
	}
	
    /**
	 * This stateChanged is from J Listener that changes state 
	 * 
	 * @param e
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
	}
}