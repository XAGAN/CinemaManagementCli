import java.util.*;
import java.io.*;

@SuppressWarnings("serial")

public class Screen implements Serializable {

	public ArrayList<Screentime> screentimes;
	private String screenId;
	private String screenClass;

	public Screen(String screenId, String screenClass) {
		this.screenId = screenId;
		this.screenClass = screenClass;
		screentimes = new ArrayList<Screentime>();
	}


	public String getScreenId() {
		return screenId;
	}

	public String getScreenClass() {
		return screenClass;
	}


	public void printDetails() {
		System.out.print("\n");
		System.out.println("Screen: " + this.screenId);
		System.out.println("Class: " + this.screenClass);
	}


	public void printShowtimes() {
		if(this.screentimes.size()==0) {
			System.out.print("\n");
			System.out.println("No showtime");
			return;
		}
		for (Screentime screentime : this.screentimes) {
			screentime.printDetails();
		}
	}


	public Screentime getShowtimeById(String showtimeId) {
		for (Screentime screentime : screentimes) {
			if (showtimeId.equals(screentime.getShowtimeId()))
				return screentime;
		}
		return null;
	}


	public void addShowtime(Screentime screentime) {
		screentimes.add(screentime);
	}


	public void deleteShowtime(String showtimeId) {
		for (Screentime screentime : screentimes) {
			if (screentime.getShowtimeId().equals(showtimeId)) {
				screentimes.remove(screentime);
				return;
			}
		}
	}
}
