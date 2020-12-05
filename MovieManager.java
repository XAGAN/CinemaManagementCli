import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;
import java.util.Scanner;

@SuppressWarnings("serial")

public class MovieManager implements Serializable {

	private ArrayList<Movie> movies;



	public void printMovies() {
		if(movies.size()==0) {
			System.out.print("\n");
			System.out.println("No movies currently available.");
			return;
		}
		for (int i = 1; i <= movies.size(); i++) {
			System.out.print(i + ". ");
			movies.get(i - 1).printMovieInfo();
			System.out.print("\n");
		}
	}

	public void printTopMoviesBySales() {
		if(movies.size()==0) {
			System.out.print("\n");
			System.out.println("No movie");
			return;
		}
			System.out.print("\n");
			System.out.println("Top Movies by Sales: ");
	        
			Collections.sort(movies);

			for(Movie movie:movies) {
				System.out.println(movie.getTitle());
				System.out.println(movie.getSales());
			}
	}

	public void printTopMoviesByRatings() {
		if(movies.size()==0) {
			System.out.print("\n");
			System.out.println("No movie");
			return;
		}
		try {
			System.out.print("\n");
			System.out.println("Top Movies by Ratings: ");
			
		}
		catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	public void createMovie() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\n");
		System.out.println("Please enter the details: ");

		System.out.print("\n");
		System.out.println("Title: ");
		String title = sc.nextLine();

		System.out.print("\n");
		System.out.println("Director: ");
		String director = sc.nextLine();


		String lengthInMinutesOld="";
		int lengthInMinutes=0;
		do {
			System.out.print("\n");
			System.out.println("Duration(minutes): ");
			lengthInMinutesOld = sc.nextLine();
			if(lengthInMinutesOld.matches("^[0-9]+$")) {
				lengthInMinutes = Integer.parseInt(lengthInMinutesOld);
				break;
			}
			System.out.println("Please do not enter non-numeric value.");
		}while(true);
		
		String showingStatus="";
		do {
			System.out.print("\n");
			System.out.println("Showing status - (Coming Soon, Preview, Now Showing): ");
			showingStatus = sc.nextLine();
			if(showingStatus.equals("Coming Soon")||showingStatus.equals("Preview")||showingStatus.equals("Now Showing"))
				break;
			System.out.println("Please enter the choice available only.");
		}while(true);

		String movieType="";
		do {
			System.out.print("\n");
			System.out.println("Type - (2D, 3D): ");
			movieType = sc.nextLine();
			if(movieType.equals("2D")||movieType.equals("3D"))
				break;
			System.out.println("Please enter the choice available only.");
		}while(true);


		String movieRating="";
		do {
			System.out.print("\n");
			System.out.println("Rating (G, PG13, NC16, M18, R21): ");
			movieRating = sc.nextLine();
			if(movieRating.equals("G")||movieRating.equals("PG13")||movieRating.equals("M18")||movieRating.equals("NC16")||movieRating.equals("R21"))
				break;
			System.out.println("Please enter the choice available only.");
		}while(true);
		
		Movie movie = new Movie(title, lengthInMinutes, showingStatus, movieType, director,
				movieRating);
		this.movies.add(movie);
	}

	public void updateMovie() {
		Scanner sc = new Scanner(System.in);
		Movie movie = selectMovie();

		if(movie==null) {
			System.out.print("\n");
			System.out.println("No movie");
			return;
		}
		
		String showingStatus="";
		do {
			System.out.print("\n");
			System.out.println("Showing status - (Coming Soon, Preview, Now Showing, End of Showing): ");
			showingStatus = sc.nextLine();
			if(showingStatus.equals("End of Showing")) {
				movie.setShowingStatus(showingStatus);
				movies.remove(movie);
				return;
			}
			if(showingStatus.equalsIgnoreCase("coming soon")||showingStatus.equalsIgnoreCase("preview")||showingStatus.equalsIgnoreCase("now showing"))
				break;
			System.out.println("Please enter the choice available only.");
		}while(true);
		
		
		System.out.print("\n");
		System.out.print("Director: ");
		String director = sc.nextLine();

		movie.setDirector(director);
		movie.setShowingStatus(showingStatus);
		
	}

	public void deleteMovie() {
		Movie movie = selectMovie();
		if(movie==null) {
			System.out.print("/n");
			System.out.println("No movie");
			return;
		}
		movies.remove((movies.indexOf(movie)));
	}

	public void createComment() {
		Movie movie = selectMovie();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Name: ");
		String name = sc.nextLine();

		String ratingOld = "";
		int rating=0;
		System.out.print("\n");
		System.out.println("Rate (0 to 5): ");
		do {
			ratingOld = sc.nextLine();
			if(ratingOld.matches("^[0-5]+$")) {
				rating = Integer.parseInt(ratingOld);
				break;
			}
			System.out.println("Please only enter integers (0-5):");
		}while(true);
		
		System.out.print("\n");
		System.out.println("Subject: ");
		String title = sc.nextLine();

		System.out.print("\n");
		System.out.println("Your comment: ");
		String content = sc.nextLine();

		Comments comments = new Comments(name, rating, title, content);
		movie.addComment(comments);
	}

	public Movie selectMovie() {
		if(movies.size()==0)
			return null;
		for (int i = 1; i <= movies.size(); i++) {
			System.out.print(i + ". ");
			movies.get(i - 1).printMovieInfo();
			System.out.print("\n");
		}
		Scanner sc = new Scanner(System.in);
		int x=0;
		String y = "";
		retry: while(true){
			System.out.print("\n");
			System.out.print("Enter the movie number: ");
			try {
				y = sc.next();
				x = Integer.parseInt(y);
				if(x > movies.size()) {
					System.out.print("\n");
					System.out.println("Unavailable");
					continue retry;
				}
				break;
			}
			catch(NumberFormatException e) {
				System.out.println("Please enter a numeric value.");
			}
			
		}
		return movies.get(x - 1);
	}

	public MovieManager() {
		this.movies = new ArrayList<Movie>();
	}

}
