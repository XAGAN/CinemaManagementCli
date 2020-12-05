import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")

public class Multiplex implements Serializable {

	private ArrayList<Screen> screens;
	private String name;
	private String multiplexId;

	public Multiplex(String n, String Id) {
		screens = new ArrayList<Screen>();
		name = n;
		multiplexId = Id;
	}

	public String getName() {
		return this.name;
	}

	public Screen getScreenById(String cinemaId) {
		for (Screen screen : screens) {
			if (screen.getScreenId().equals(cinemaId)) {
				return screen;
			}
		}
		System.out.println("Couldn't find screen with that ID!");
		return null;
	}

	public void addScreen(Screen screen) {
		screens.add(screen);
	}

	public String getMultiplexId() {
		return this.multiplexId;
	}

	public void printDetails() {
		System.out.println(" ");
		System.out.println("name: " + name);
		System.out.println("ID: " + multiplexId);
		System.out.println("Number of Screens: " + screens.size());
	}

	public void printScreens() {
		System.out.print("\n");
		System.out.println(name + " multiplex screens.");
		for (Screen screen : screens) {
			System.out.println(screen.getScreenId());
		}
	}

}