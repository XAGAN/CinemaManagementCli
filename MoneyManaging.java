import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.*;

@SuppressWarnings("serial")


public class MoneyManaging implements Comparable<MoneyManaging>, Serializable {

	private String ticketId;
	private double price;
	private String movieTitle;
	private Date startTime;
	private String[] seatIds;
	private String name;
	private String mobileNumber;
	private String email;
	private String screenId;



	public void printInfo() {
		System.out.print("\n");
		System.out.println("Your TicketID is: " + ticketId);
		System.out.println("Name: " + name);
		System.out.println("Mobile number: " + mobileNumber);
		System.out.println("Email: " + email);
		System.out.println("Screen ID: " + screenId);
		System.out.println("Movie: " + movieTitle);
		System.out.println("Time: " + startTime);
		System.out.print("Seat: ");
		for (String seatId : seatIds)
			System.out.print(seatId + " ");
		System.out.print("\n");
		System.out.println("Price:" + price);
	}

	public int compareTo(MoneyManaging moneyManaging) {
		return this.ticketId.compareTo(moneyManaging.ticketId);
	}

	public String getEmail() {
		return this.email;
	}

	public MoneyManaging(double price, String movieTitle, String screenId, Date startTime, String[] seatIds,
						 String name, String mobileNumber, String email) {
		this.price = price;
		this.movieTitle = movieTitle;
		this.screenId = screenId;
		this.startTime = startTime;
		this.seatIds = seatIds;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.email = email;

		LocalDateTime localDateTime = LocalDateTime.now();
		Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		this.ticketId = screenId + simpleDateFormat.format(date);
	}

}
