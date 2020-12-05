import java.io.*;
import java.util.*;
import java.text.*;

@SuppressWarnings("serial")

public class TicketManging implements Serializable {

	private MultiplexManager multiplexManager;
	private ArrayList<MoneyManaging> moneyManagings;
	private HashMap<String, Double> pricesByMovieType, pricesByCinemaClass, pricesByAge, pricesByDay;
	private ArrayList<Date> holidays;





	public void buySeat() {
		Scanner sc = new Scanner(System.in);
		Screen screen = this.selectCinema();
		if(screen ==null)
			return;
		screen.printShowtimes();
		if(screen.screentimes.size()==0)
			return;
		String showtimeId;
		Screentime screentime = null;
		while (true) {
			System.out.print("\n");
			showtimeId = "";
			System.out.print("Enter screentime ID \ntype 'exit' to exit: ");
			showtimeId = sc.nextLine();
			if(showtimeId.equalsIgnoreCase("exit"))
				return;
			screentime = screen.getShowtimeById(showtimeId);
			if (Objects.nonNull(screentime))
				break;
			else 
				System.out.println("Please try again.");
		}
		screentime.printSeats();
		String seatId;
		String age;
		String option;
		double amount = 0;
		HashMap<String, Double> seatFees = new HashMap<String, Double>();
		retry: while (true) {
			System.out.print("\n");
			seatId = "";
			System.out.println("Enter seat ID \ntype 'exit' to exit: ");
			seatId = sc.nextLine();
			if(seatId.equalsIgnoreCase("exit"))
				return;
			System.out.println("This seat is for a/an 1-Child 2-Adult 3-Senior \ntype in 'exit' to exit: ");
			option = sc.nextLine();
			if(option.equalsIgnoreCase("exit"))
				return;
			switch (option) {
			case "1":
				age = "Child";
				break;
			case "2":
				age = "Adult";
				break;
			case "3":
				age = "Senior";
				break;
			default:
				System.out.println("Please try again.");
				continue retry;
			}
			if (screentime.reserve(seatId)) {
				seatFees.put(seatId, pricesByAge.get(age));
				if (pricesByMovieType.get(screentime.getMovie().getMovieType()) != null)
					amount += pricesByMovieType.get(screentime.getMovie().getMovieType());
				if (pricesByCinemaClass.get(screen.getScreenClass()) != null)
					amount += pricesByCinemaClass.get(screen.getScreenClass());
				Date date = screentime.getStartTime();
				boolean flag = false;
				for (Date holiday : holidays) {
					if (holiday.getYear() == date.getYear() && holiday.getMonth() == date.getMonth() && holiday.getDay() == date.getDay())
						flag = true;
				}
				if (flag) {
					System.out.print("\n");
					System.out.println("The day you booked is a holiday.");
					amount += pricesByDay.get("holiday");
				} 
				else {
					System.out.print("\n");
					System.out.println("The day you booked is a weekday.");
					amount += pricesByDay.get("weekday");
				}
			}
			else 
				System.out.println("Unavailable");
			boolean wrongOption = true;
			do {
				System.out.print("Do you want to reserve another seat? (Y/N): ");
				option = sc.nextLine();
				if (option.equalsIgnoreCase("n")||option.equalsIgnoreCase("y"))
					break;
			}while(wrongOption);
			if(option.equalsIgnoreCase("n"))
				break;
		}
		for (double d : seatFees.values()) 
			amount += d;
		screentime.getMovie().increaseSales((int) amount);
		String title = screentime.getMovie().getTitle();
		String cinemaId = screen.getScreenId();
		Date startTime = screentime.getStartTime();
		String[] seatIds = seatFees.keySet().toArray(new String[seatFees.keySet().size()]);
		
		System.out.println(" ");
		System.out.println("Enter your name \ntype 'exit' to cancel buying: ");
		String name = sc.nextLine();
		if(name.equalsIgnoreCase("exit")) {
			System.out.println("Buying is cancelled.");
			return;
		}
		boolean nonnumber = true;
		String mobileNumber;
		do {
			System.out.print("\n");
			System.out.println("Enter your mobile number \ntype 'exit' to cancel booking: ");
			mobileNumber = sc.nextLine();
			if(mobileNumber.matches("^[0-9]+$"))
				nonnumber = false;
			else if(mobileNumber.equalsIgnoreCase("exit")) {
				System.out.println("Buying is cancelled.");
				return;
			}
		}while(nonnumber);
		System.out.print("\n");
		System.out.println("Enter your email address \ntype 'exit' to cancel booking: ");
		String email = sc.nextLine();
		if(email.equalsIgnoreCase("exit")) {
			System.out.println("Buying is cancelled.");
			return;
		}
		MoneyManaging moneyManaging = new MoneyManaging(amount, title, cinemaId, startTime, seatIds,
				name, mobileNumber, email);
		moneyManagings.add(moneyManaging);
	}

	public void showHistory() {
		Scanner sc = new Scanner(System.in);
		boolean flag = false;
		String email;
		System.out.print("\n");
		System.out.println("Enter your email address \ntype 'exit' to exit: ");
		email = sc.nextLine();
		if(email.equalsIgnoreCase("exit"))
			return;
		for (MoneyManaging p : moneyManagings) {
			if (p.getEmail().equals(email)) {
				p.printInfo();
				flag = true;
			}
		}
		if(!flag)
			System.out.println("No booking history is found.");
	}

	public void createShowtime(MovieManager movieManager) throws ParseException {
		Scanner sc = new Scanner(System.in);
		Screen screen = this.selectCinema();
		String showtimeId  = "";
		System.out.print("\n");
		System.out.println("Create showtime ID \ntype 'exit' to exit: ");
		retry: while(true) {
			showtimeId = sc.nextLine();
			if(showtimeId.equalsIgnoreCase("exit"))
				return;
			for(int i = 0; i< screen.screentimes.size(); i++) {
				if(showtimeId.equalsIgnoreCase(screen.screentimes.get(i).getShowtimeId())) {
					System.out.println("The showtime ID exists. Please create another one!");
					System.out.println("Create showtime ID \ntype 'exit' to exit: ");
					continue retry;
				}
			}
			break;
		}
		Movie movie = movieManager.selectMovie();
		
		sc = new Scanner(System.in);
		String strStartTime;
		Date startTime;
		System.out.println(" ");
		System.out.println("Enter movie start time (yyyy/mm/dd/hh/mm) \ntype 'exit' to exit: ");
		doAgain: do{
			try {
				strStartTime = sc.nextLine();
				if(strStartTime.equalsIgnoreCase("exit"))
					return;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
				startTime = simpleDateFormat.parse(strStartTime);
				ParsePosition parsePosition = new ParsePosition(0);
				startTime = simpleDateFormat.parse(strStartTime, parsePosition); 
				
				if(strStartTime.equals(simpleDateFormat.format(startTime))) {
					for(int i = 0; i< screen.screentimes.size(); i++) {
						String strDate = simpleDateFormat.format(screen.screentimes.get(i).getStartTime());
						if(strStartTime.equals(strDate)) {
							System.out.println("The showtime is not available for the cinema. Please create another one!");
							System.out.println("Enter movie start time (yyyy/mm/dd/hh/mm) \ntype 'exit' to exit: ");
							continue doAgain;
						}
					}
					break;
				}
				
				System.out.println(" ");
				System.out.println("Wrong date format.");
				System.out.println("Enter movie start time (yyyy/mm/dd/hh/mm) \ntype 'exit' to exit: ");
			}
			catch(ParseException e) {
				System.out.println(" ");
				System.out.println("Wrong date format.");
				System.out.println("Enter movie start time (yyyy/mm/dd/hh/mm) \ntype 'exit' to exit: ");
			}
		}while(true);
		Screentime screentime = new Screentime(showtimeId, movie, startTime);
		screen.addShowtime(screentime);
	}

	public void deleteShowtime() {
		Scanner sc = new Scanner(System.in);
		Screen screen = this.selectCinema();
		System.out.print("\n");
		screen.printShowtimes();
		System.out.println(" ");
		System.out.println("Enter showtime ID \ntype 'exit' to exit: ");
		String showtimeId = sc.nextLine();
		if(showtimeId.equalsIgnoreCase("exit"))
			return;
		for(int i = 0; i< screen.screentimes.size(); i++) {
			if(showtimeId.equals(screen.screentimes.get(i).getShowtimeId())) {
				screen.deleteShowtime(showtimeId);
				System.out.println("The showtime with ID "+showtimeId+" is deleted successfully.");
				return;
			}
		}
		System.out.println("The showtime does not exist!");
	}

	public void updatePrices() {
		HashMap<String, Double> targetHashMap = null;
		Scanner sc = new Scanner(System.in);
		retry: while(true) {
			System.out.print("\n");
			System.out.println("1- Movie type \n 2- Screen class \n 3- Age \n 4- Day");
			System.out.println("Enter option number \n type 'exit' to exit.");
			String option = sc.nextLine();
			if(option.equalsIgnoreCase("exit"))
				return;
			switch (option) {
			case "1":
				targetHashMap = pricesByMovieType;
				break;
			case "2":
				targetHashMap = pricesByCinemaClass;
				break;
			case "3":
				targetHashMap = pricesByAge;
				break;
			case "4":
				targetHashMap = pricesByDay;
				break;
			default: 
				System.out.print("\n");
				System.out.println("Unavailable. Please try again.");
				continue retry;
			}
			if(option.equals("1")||option.equals("2")||option.equals("3")||option.equals("4"))
				break;
		}
		targetHashMap.entrySet().forEach((entry) -> System.out.println(entry));
		String category="";
		System.out.print("\n");
		System.out.println("Enter category \n type 'exit' to exit: ");
		do {
			category = sc.nextLine();
			if(category.equalsIgnoreCase("exit"))
				return;
			else if(category.equals("holiday")||category.equals("weekday"))
				break;
			else if(category.equals("2D")||category.equals("3D"))
				break;
			else if(category.equals("Gold")||category.equals("Silver")||category.equals("Bronze"))
				break;
			else if(category.equals("Senior")||category.equals("Adult")||category.equals("Child"))
				break;
			System.out.print("\n");
			System.out.println("Please try again \n type 'exit' to exit.");
		}while(true);
		
		double price = 0.0;
		String priceOld = "";
		
		while(true) {
			System.out.print("\n");
			System.out.println("Enter price \n type 'exit' to exit: ");
			try {	
				priceOld = sc.next();
				if(priceOld.equalsIgnoreCase("exit"))
					return;
				price = Double.parseDouble(priceOld);
				break;
			}
			catch(NumberFormatException e1){
				System.out.print("\n");
				System.out.println("Try again.");
			}	
		}	
		targetHashMap.put(category, price);
	}

	private Screen selectCinema() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\n");
		multiplexManager.printMultiplexes();
		System.out.println("Enter multiplex ID \ntype 'exit' to exit: ");
		String multiplexId;
		do {
			multiplexId = sc.nextLine();
			if(multiplexId.equalsIgnoreCase("exit"))
				return null;
			else if(multiplexId.equals("Naji")||multiplexId.equals("Farhang")||multiplexId.equals("Azadi"))
				break;
			System.out.print("\n");
			System.out.println("The multiplex ID does not exist.");
			System.out.println("Try again \ntype 'exit' to exit: ");
		}while(true);
		Multiplex multiplex = multiplexManager.getMultiplexById(multiplexId);
		if (Objects.isNull(multiplex))
			return null;
		multiplex.printScreens();
		System.out.print("\n");
		System.out.println("Enter multiplex ID \ntype 'exit' to exit: ");
		String screenId;
		do {
			screenId = sc.nextLine();
			if(screenId.equalsIgnoreCase("exit"))
				return null;
			else if(multiplexId.equals("Naji")&&screenId.equals("N1")||screenId.equals("N2")||screenId.equals("N3"))
				break;
			else if(multiplexId.equals("Farhang")&&screenId.equals("F1")||screenId.equals("F2")||screenId.equals("F3"))
				break;
			else if(multiplexId.equals("Azadi")&&screenId.equals("A1")||screenId.equals("A2")||screenId.equals("A3"))
				break;
			System.out.print("\n");
			System.out.println("The screen ID does not exist.");
			System.out.println("Try again \ntype 'exit' to exit: ");
		}while(true);
		if (Objects.isNull(screenId))
			return null;
		return multiplex.getScreenById(screenId);
	}

	public void updateHolidays() {
		for (Date d : holidays)
			System.out.println(d);
		Scanner sc = new Scanner(System.in);
		Date holidayDate;
		Date date;
		System.out.print("\n");
		System.out.println("Enter holiday (yyyy/mm/dd) \ntype 'exit' to exit: ");
		do{
			try {
			String holiday = sc.nextLine();
			if(holiday.equalsIgnoreCase("exit"))
				return;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			date = simpleDateFormat.parse(holiday);
			ParsePosition parsePosition = new ParsePosition(0);
			holidayDate = simpleDateFormat.parse(holiday, parsePosition);
			if(holiday.equals(simpleDateFormat.format(date)))
				break;
			System.out.print("\n");
			System.out.println("Wrong date format.");
			System.out.println("Enter holiday \ntype 'exit' to exit: ");
			}
			catch(ParseException e) {
				System.out.print("\n");
				System.out.println("Wrong date format.");
				System.out.println("Enter holiday \ntype 'exit' to exit: ");
			}
		}while(true);	
		boolean flag = false;
		for (Date d : holidays) {
			if (d.equals(holidayDate))
				flag = true;
		}
		if (!flag) {
			holidays.add(holidayDate);
			System.out.println("The date is added.");
		}
		else {
			holidays.remove(holidayDate);
			System.out.println("The date is removed.");
		}
		holidays.remove(null);
	}

	public TicketManging() {
		multiplexManager = new MultiplexManager();
		moneyManagings = new ArrayList<MoneyManaging>();
		pricesByMovieType = new HashMap<String, Double>();
		pricesByMovieType.put("2D", 1.0);
		pricesByMovieType.put("3D", 2.0);
		pricesByCinemaClass = new HashMap<String, Double>();
		pricesByCinemaClass.put("Gold", 12.0);
		pricesByCinemaClass.put("Silver", 6.0);
		pricesByCinemaClass.put("Bronze", 3.0);
		pricesByAge = new HashMap<String, Double>();
		pricesByAge.put("Child", 6.0);
		pricesByAge.put("Adult", 9.5);
		pricesByAge.put("Senior", 7.0);
		pricesByDay = new HashMap<String, Double>();
		pricesByDay.put("weekday", 0.0);
		pricesByDay.put("holiday", 1.5);
		holidays = new ArrayList<Date>();
	}

	public MultiplexManager getMultiplexManager() {
		return multiplexManager;
	}
}
