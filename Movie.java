import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")

/*
to know about the movies ratings visit :
https://en.wikipedia.org/wiki/Motion_Picture_Association_film_rating_system
 */

public class Movie implements Comparable<Movie>, Serializable {

	private ArrayList<Comments> comments;
	private double rating;
	private String movieRating;
	private String title;
	private int lengthInMinutes;
	private String showingStatus;
	private String movieType;
	private String director;
	private long sales;



	public void printMovieInfo() {
		System.out.print("\n");
		System.out.print("Title: " + title +"\t");
		System.out.print(" Type: " + movieType + "\t");
		System.out.print(" Length: " + lengthInMinutes + " minutes" + "\t");
		System.out.println("Director: " + showingStatus);
	}

	public void printInfo() {
		System.out.print("\n");
		String ratingLessThanOne = "";
		if(comments.size()==0|| comments.size()==1) {
			ratingLessThanOne = String.valueOf(rating);
			ratingLessThanOne = "Unavailable";
			System.out.print("Movie Type: " + movieType + "     " + "Title: "+ title + "     " + "Duration of movie: " + lengthInMinutes + " minutes" + "\n" + showingStatus
					+ "\n" + "Director: " + director + "\n" + "Movie ratings: " + ratingLessThanOne + "\n");
			comments.forEach((reviews) -> reviews.printComment());
			return;
		}
		else {
			rating = Math.round(rating * 10) / 10.0;
			System.out.print("Movie Type: " + movieType + "     " + "Title: "+ title + "     " + "Duration of movie: " + lengthInMinutes + " minutes" + "\n" + showingStatus
					+ "\n" + "Director: " + director + "\n" + "Movie ratings: " + rating + "\n");
			comments.forEach((reviews) -> reviews.printComment());
		}

	}

	public void addComment(Comments comments) {
		this.comments.add(comments);
		rating = (rating*(this.comments.size()-1))+ comments.getRating();
		rating = rating/ this.comments.size();
	}

	public String getTitle() {
		return this.title;
	}

	public String getShowingStatus() {
		return this.showingStatus;
	}

	public void setShowingStatus(String showingStatus) {
		this.showingStatus = showingStatus;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getMovieRating() {
		return this.movieRating;
	}

	public void setMovieRating(String movieRating) {
		this.movieRating = movieRating;
	}


	public String getMovieType() {
		return this.movieType;
	}


	public void increaseSales(int amount) {
		this.sales += (long)amount;
	}

	public long getSales() {
		return sales;
	}

	public int compareTo(Movie movie) {
		return (int)(movie.getSales()-this.sales);
	}

	public Movie(String title, int lengthInMinutes, String status, String type, String showingStatus, String movieType){
		this.title = title;
		this.lengthInMinutes = lengthInMinutes;
		this.movieType = movieType;
		this.showingStatus = showingStatus;
		this.director = director;
		this.movieRating = movieRating;
		this.comments = new ArrayList<Comments>();
		this.sales = this.getSales();
	}

}
