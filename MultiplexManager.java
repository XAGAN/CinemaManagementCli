import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")

public class MultiplexManager implements Serializable {

	private ArrayList<Multiplex> multiplexes;

	public MultiplexManager() {
		multiplexes = new ArrayList<Multiplex>();
	}


	public Multiplex getMultiplexById(String multiplexId) {
		for (Multiplex multiplex : multiplexes) {
			if (multiplex.getMultiplexId().equals(multiplexId)) {
				return multiplex;
			}
		}
		System.out.println("Couldn't find multiplex with that ID!");
		return null;
	}


	public void addMultiplex(Multiplex multiplex) {
		multiplexes.add(multiplex);
	}

	public void printMultiplexes() {
		for (Multiplex multiplex : multiplexes) {
			System.out.println(multiplex.getName());
		}
	}

}
