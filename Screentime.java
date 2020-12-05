import java.io.*;
import java.util.*;


@SuppressWarnings("serial")

public class Screentime implements Comparable<Screentime>, Serializable {

	private Seat[] seats;
	private String showtimeId;
	private int numEmptySeats =0;
	private Movie movie;
	private Date startTime;


	public Screentime(String showtimeId, Movie movie, Date startTime) {
		this.showtimeId = showtimeId;
		this.movie = movie;
		this.startTime = startTime;
		seats = new Seat[90];
		int cc = 1;
		for(int i = 0;i<90;i++)
			{
				if(cc==11)
					cc=1;
				char a = (char)((65+((i)/10)));
				String aa=Character.toString(a);
				String b = Integer.toString(cc);
				seats[i]=new Seat(aa.concat(b));
				cc++;
			}
	}


	public void printDetails() {
		System.out.println(" ");
		System.out.println(showtimeId + " " + movie.getTitle() + " starts at " + startTime.toString());
	}



	public boolean reserve(String seatId) {
		int x1;
		char y1 = ' ';

		if ((!(seatId.length() == 2 || seatId.length() == 3)) || (!Character.isLetter(seatId.charAt(0)))) {
			return false;
		}
		y1 = seatId.charAt(0);
		y1 = Character.toUpperCase(y1);
		char cc = seatId.charAt(1);
		x1 = Character.getNumericValue(cc);
		if (seatId.length() == 3) {
			if (!((Character.getNumericValue(seatId.charAt(1)) == 1)
					&& (Character.getNumericValue(seatId.charAt(2)) == 0))) {
				return false;
			} else
				x1 = 10;
		}

		if (!(y1 >= 'A' && y1 <= 'J' && x1 >= 1 && x1 <= 10)) {
			return false;
		}
		x1--;

		int x = ((int) (y1)) % 65;
		if (seats[((x * 10) + x)].busy())
			return false;
		else {
			seats[((x * 10) + x)].reserve();
			return true;
		}
	}


	public boolean unReserve(String seatId) {
		int x2;
		char y2 = ' ';

		if ((!(seatId.length() == 2 || seatId.length() == 3)) || (!Character.isLetter(seatId.charAt(0)))) {
			return false;
		}
		y2 = seatId.charAt(0);
		y2 = Character.toUpperCase(y2);
		char cc = seatId.charAt(1);
		x2 = Character.getNumericValue(cc);
		if (seatId.length() == 3) {
			if (!((Character.getNumericValue(seatId.charAt(1)) == 1)
					&& (Character.getNumericValue(seatId.charAt(2)) == 0))) {
				return false;
			} else
				x2 = 10;
		}

		if (!(y2 >= 'A' && y2 <= 'J' && x2 >= 1 && x2 <= 10)) {
			return false;
		}

		x2--;

		int x = ((int) (y2)) % 65;

		if (!(seats[((x * 10) + x2)].busy()))
			return false;
		else {
			seats[((x * 10) + x2)].unReserve();
			return true;
		}
	}


	public String getShowtimeId() {
		return showtimeId;
	}


	public Movie getMovie() {
		return movie;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void printSeats() {
		int x = 65;
		int y = 0;
		System.out.println(movie.getSales());
		for (int i = 1; i <= 13; i++) {
			if (i == 1 || i == 6 || i==10)
				System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t");
			else if (i == 13) {
				System.out.print("\t");
				for (int j = 1; j <= 10; j++) {
					System.out.print(j + "\t");
					if (j == 5)
						System.out.print("\t");
				}
				System.out.println("");
			} else {
				System.out.print((char) (x) + "\t");
				x++;
				for (int j = 0; j < 10; j++) {
					if (seats[y + j].busy()) {
						System.out.print("X\t");
					} else {
						System.out.print("--\t");
						numEmptySeats++;
					}
					if (j == 4)
						System.out.print("\t");
				}
				y += 10;
				System.out.print("\n");
			}
		}
		System.out.println(numEmptySeats +" left");
		numEmptySeats =0;
	}


	public int compareTo(Screentime screentime) {
		return this.startTime.compareTo(screentime.startTime);
	}

}
