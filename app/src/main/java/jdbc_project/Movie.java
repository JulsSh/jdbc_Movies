package jdbc_project;
import lombok.Data;

@Data
public class Movie {
    
    private long movieId;
    private String title;
    private int releaseYear;
    private String genre;
    private double rating;
    private String director;
    private int leadActorId;

    public Movie(long movieId, String title, int releaseYear, String genre, double rating, String director, int leadActorId) {
        this.movieId = movieId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.rating = rating;
        this.director = director;
        this.leadActorId = leadActorId;
    }
   public Movie(String title, int releaseYear, String genre, double rating, String director, int leadActorId) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.rating = rating;
        this.director = director;
        this.leadActorId = leadActorId;
       
    }     
}
