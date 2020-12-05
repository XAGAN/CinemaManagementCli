import java.util.*;
import java.io.*;

public class CinMain {



	private static String password;
	static {
		password = "AP";
	}

	public static void main(String[] args) throws Exception {

		File movieDat = new File("MovieManager.dat");
		File ticketDat = new File("TicketManaging.dat");
		

		TicketManging ticketManging = new TicketManging();
		MovieManager movieManager = new MovieManager();

		if (movieDat.exists()) {
			try {
				movieManager = (MovieManager) readFiles("MovieManager.dat");
				ticketManging = (TicketManging) readFiles("TicketManaging.dat");
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			ticketManging = new TicketManging();
			movieManager = new MovieManager();

			Multiplex Naji = new Multiplex("Naji" , "Naji");
			ticketManging.getMultiplexManager().addMultiplex(Naji);
			Multiplex Farhang = new Multiplex("Farhang", "Farhang");
			ticketManging.getMultiplexManager().addMultiplex(Farhang);
			Multiplex Azadi = new Multiplex("Azadi", "Azadi");
			ticketManging.getMultiplexManager().addMultiplex(Azadi);

			Screen N1 = new Screen("N1", "Gold");
			Screen N2 = new Screen("N2", "Silver");
			Screen N3 = new Screen("N3", "Bronze");
			Screen F1 = new Screen("F1", "Gold");
			Screen F2 = new Screen("F2", "Silver");
			Screen F3 = new Screen("F3", "Bronze");
			Screen A1 = new Screen("A1", "Gold");
			Screen A2 = new Screen("A2", "Silver");
			Screen A3 = new Screen("A3", "Bronze");

			Naji.addScreen(N1);
			Naji.addScreen(N2);
			Naji.addScreen(N3);
			Farhang.addScreen(F1);
			Farhang.addScreen(F2);
			Farhang.addScreen(F3);
			Azadi.addScreen(A1);
			Azadi.addScreen(A2);
			Azadi.addScreen(A3);
		}
		String choice=null;
		do {
			Scanner scanner = new Scanner(System.in);
			Thread.sleep(50);
			System.out.println("\t\t\t\t\t\tWelcome to CinApp");
			System.out.println("\t\t\t\t\t\tThis App is for Cinemas :)");
			Thread.sleep(700);
			System.out.println();
			System.out.println("\t\t\t\t\t\tAdministration\t===>\tPress '1' to Login");
			Thread.sleep(700);
			System.out.println("\t\t\t\t\t\tCostumers\t\t===>\tPress '2'");
			Thread.sleep(700);
			System.out.println("\t\t\t\t\t\tExit\t\t\t===>\tPress '3'");
			choice = scanner.nextLine();
			if (choice.equals("1")) {
				String pass;
				do {
					System.out.print("\n");
					System.out.println("Enter password \nEnter 'exit' to exit");
					pass=scanner.next();
				} while (!authenticate(pass));
				if(pass.equalsIgnoreCase("exit"))
					break;
				System.out.print("\n");
				System.out.println("\t\t\t\t\tWelcome Administrator!");
				choice = scanner.nextLine();
				do {
					System.out.print("\n");
					System.out.println("\t\tChoose:");
					System.out.println("1- Access to movie list");
					System.out.println("2- Access to showtime list");
					System.out.println("3- Settings");
					System.out.println("4- Exit");
					choice = scanner.nextLine();
					switch (choice) {
						case "1":
							System.out.print("\n");
							System.out.println("1- Add Movie");
							System.out.println("2- Update List");
							System.out.println("3- Remove Movie");
							System.out.println("4- Exit");
							choice = scanner.nextLine();
							if (choice.equals("1")) {
								movieManager.createMovie();
								break;
							}
							else if (choice.equals("2")) {
								movieManager.updateMovie();
								break;
							}
							else if (choice.equals("3"))
								movieManager.deleteMovie();
							else if(choice.equals("4"))
								break;
							else
								System.out.println("Unavailable! Please try again.");
							break;
						case "2":
							System.out.print("\n");
							System.out.println("1- Add showtime");
							System.out.println("2- Remove showtime");
							System.out.println("3- Exit");
							choice = scanner.nextLine();
							if (choice.equals("1"))
								ticketManging.createShowtime(movieManager);
							else if (choice.equals("2"))
								ticketManging.deleteShowtime();
							else if (choice.equals("3"))
								break;
							else
								System.out.println("Unavailable! Please try again.");
							break;
						case "3":
							System.out.println(" ");
							System.out.println("1- Set prices");
							System.out.println("2- Set holidays");
							System.out.println("3- Exit");
							choice = scanner.nextLine();
							if (choice.equals("1")) {
								ticketManging.updatePrices();
							} else if (choice.equals("2")) {
								ticketManging.updateHolidays();
							}else if (choice.equals("3"))
								break;
							else
								System.out.println("Unavailable! Please try again.");
							break;
						case "4":
							break;
						default:
							System.out.println("Unavailable! Please try again.");
					}
				} while (!choice.equals("4"));
					
				
			} else if(choice.equals("2")){

				do {
					try {
						System.out.print("\n");
						System.out.println("\t\t\t\t\tWelcome !");
						System.out.println("\t\tChoose:");
						System.out.println("1- List of movies");
						System.out.println("2- Buy ticket");
						System.out.println("3- Tickets history");
						System.out.println("4- List of the top movies");
						System.out.println("5- Enter comment for movie");
						System.out.println("6- Exit");
						choice = scanner.nextLine();
						switch (choice) {
							case "1":
								movieManager.selectMovie().printInfo();
								break;
							case "2":
								ticketManging.buySeat();
								break;
							case "3":
								ticketManging.showHistory();
								break;
							case "4":
								movieManager.printTopMoviesByRatings();
								movieManager.printTopMoviesBySales();
								break;
							case "5":
								movieManager.createComment();
								break;
							case "6":
								break;
							default:
								System.out.println("Unavailable! Please try again.");
						}
					}
					catch(ArrayIndexOutOfBoundsException e2) {
						System.out.println("No movies");
					}
					catch(NullPointerException e1) {
						System.out.println("No movies");
					}
				} while (!choice.equals("6"));

			}
			else if(choice.equals("3")) {
					System.out.print("\n");
					System.out.println("Have a good day!");
					break;
			}
			else continue;
		}while(!choice.equals("1")||!choice.equals("2")||!choice.equals("3"));
		try {
			writeFiles("TicketManaging.dat", ticketManging);
			writeFiles("MovieManager.dat", movieManager);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static boolean authenticate(String pass) {
		if (pass.equals(password)||pass.contentEquals("exit")) {
			return true;
		}
		System.out.println("Wrong password!");
		return false;
	}


	public static void writeFiles(String filename, Object obj) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(filename);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		if (filename.equals("TicketManaging.dat")) {
			objectOutputStream.writeObject((TicketManging) obj);
		} else {
			objectOutputStream.writeObject((MovieManager) obj);
		}
		objectOutputStream.flush();
		objectOutputStream.close();
	}

	public static Object readFiles(String fileName) throws IOException, ClassNotFoundException {

		FileInputStream fileInputStream = new FileInputStream(fileName);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);


		TicketManging ticketManging = new TicketManging();
		MovieManager movieManager = new MovieManager();

		if (fileName.equals("TicketManaging.dat")) {
			ticketManging = (TicketManging) objectInputStream.readObject();
		}
		else if(fileName.contentEquals("MovieManager.dat")){
			movieManager = (MovieManager) objectInputStream.readObject();
		}

		objectInputStream.close();
		if (fileName.equals("TicketManaging.dat")) {
			return ticketManging;
		}
		else
			return movieManager;

	}

}