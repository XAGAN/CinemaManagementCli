import java.io.Serializable;

@SuppressWarnings("serial")



public class Comments implements Comparable<Comments>, Serializable {
	

	private String name;
	private String comment;
	private int rating;
	private String title;






	public void printComment() {
		System.out.print("\n");
		System.out.print("Name: " + name + "\n" + "\n" + "Rating: " + rating
				+ "\n" +"Title: "+title+ "\n"+ "Comments: " + comment + "\n");
	}


	public int compareTo(Comments comments) {
		return (int) (this.rating - comments.rating);
	}

	public Comments(String name, int rating, String title, String comment) {
		this.name = name;
		this.rating = rating;
		this.title = title;
		this.comment = comment;
	}

	public int getRating()
	{
		return rating;
	}

}
