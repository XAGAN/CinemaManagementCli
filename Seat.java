import java.io.*;

@SuppressWarnings("serial")

public class Seat implements Serializable {

	private String seatId;
	private boolean reserved;

	public Seat(String seatId) {
		this.seatId = seatId;
		this.reserved = false;
	}


	public String getSeatId() {
		return this.seatId;
	}


	public boolean busy() {
		return this.reserved;
	}


	public void reserve() {
		this.reserved = true;
	}


	public void unReserve() {
		this.reserved = false;
	}

}