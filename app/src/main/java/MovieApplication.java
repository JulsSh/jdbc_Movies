import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import jdbc_project.Movie;
import jdbc_project.MovieRepo;

public class MovieApplication {
    public static void main(String[] args) throws SQLException {
       MovieRepo repository = new MovieRepo();
       Scanner scanner = new Scanner(System.in);

       while(true){System.out.println("\n ---Menu---");
      System.out.println("1. Create Movie by ID");
      System.out.println("2. Update Movie by ID");
      System.out.println("3. Delete Movie by ID");
      System.out.println("4. Get Movie by ID");
      System.out.println("5. Show All Movies");
      System.out.println("6. Exit");
        System.out.print("Enter your choice: ");

       String input = scanner.nextLine();
       switch (input) {
        case "1":
            createMovie(repository, scanner);
            break;
        case "2":
            updateMovie(repository, scanner);
            break;
        case "3":
            deleteMovie(repository, scanner);
            break;
        case "4":
            getMovieById(repository, scanner);
            break;
        case "5":
            getAllMovies(repository);                         
            break;
       case "6":
            System.out.println("Exiting...");
            scanner.close();
            return;
        default:
            break;
       }

    }
}

    private static void createMovie(MovieRepo repository, Scanner scanner) throws SQLException {
        System.out.println("Enter a new movie title: ");
        String name= scanner.nextLine();
        System.out.println("Enter new movie release year: ");
        int releaseYear = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter new movie genre: ");
        String genre = scanner.nextLine();
        System.out.println("Enter new movie rating: ");
        double rating = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter new movie director: ");
        String director = scanner.nextLine();
        System.out.println("Enter new movie lead actor ID: ");
        int leadActorId = Integer.parseInt(scanner.nextLine());    
       
       
        Movie movie = new Movie( name, releaseYear, genre, rating, director, leadActorId);
           Movie newMovie = repository.createMovie(movie);
       
           System.out.println("Created movie: " + newMovie.toString());
    }


    private static void updateMovie(MovieRepo repository, Scanner scanner)throws SQLException     {
        System.out.println("Enter the ID of the movie you want to update: ");
        long id = Long.parseLong(scanner.nextLine());
        Movie updtMovie =repository.getMovieById(id);

        if(updtMovie == null){
            System.out.println("Movie with ID " + id + " not found.");
            return;
        }

        System.out.println("Enter the new title for the movie: ");
        String title = scanner.nextLine();      
        
        System.out.println("Enter the new release year for the movie: ");
        int releaseYear = Integer.parseInt(scanner.nextLine());
        
        System.out.println("Enter the new genre for the movie: ");
        String genre = scanner.nextLine();
        
        System.out.println("Enter the new rating for the movie: ");
        String ratingInput = scanner.nextLine();
        double rating = ratingInput.isEmpty() ? 0.0 : Double.parseDouble(ratingInput);
       
        System.out.println("Enter the new director for the movie: ");
        String director = scanner.nextLine();
        
        System.out.println("Enter the new lead actor ID for the movie: ");
        int leadActorId = Integer.parseInt(scanner.nextLine());

        updtMovie.setTitle(title);
        updtMovie.setReleaseYear(releaseYear);
        updtMovie.setGenre(genre);
        updtMovie.setRating(rating);
        updtMovie.setDirector(director);
        updtMovie.setLeadActorId(leadActorId);

         boolean isUpdated = repository.updateMovie(updtMovie);

        System.out.println("Updating movie with ID: " + id);
        if(isUpdated){
            System.out.println("Movie updated successfully.");
        } else {
            System.out.println("Failed to update movie with ID: " + id);
        }
    }

    private static void deleteMovie(MovieRepo repository, Scanner scanner) throws SQLException
    {
        System.out.println("Enter the ID of the movie you want to delete: ");
        long id = Long.parseLong(scanner.nextLine());
        boolean isDeleted = repository.deleteMovie(id);
        if(isDeleted){
            System.out.println("Movie deleted successfully.");
        } else {
            System.out.println("Failed to delete movie with ID: " + id);
        }
    }

    private static void getMovieById(MovieRepo repository, Scanner scanner) throws SQLException   {
System.out.println("Enter the ID of the movie you want to retrieve: ");
        long id = Long.parseLong(scanner.nextLine());
        Movie movie = repository.getMovieById(id);
        if(movie != null){
            System.out.println("Movie found: " + movie.toString());
        } else {
            System.out.println("Movie with ID " + id + " not found.");
        }
    }

    private static void getAllMovies(MovieRepo repository) {
        List<Movie> movies = repository.getAllMovies();
        if(movies.isEmpty()){
            System.out.println("No movies found.");
        } else {
            System.out.println("All movies:");
            for(Movie movie : movies){
                System.out.println(movie.toString());
            }
        }
    }
}