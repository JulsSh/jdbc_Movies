package jdbc_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieRepoWithInjections {

        private static final String DB_URL = "jdbc:postgresql://localhost:5432/cinemadb";
        private static final String DB_LOGIN = "macbook";
        private static final String DB_PASSWORD = "";

        public static void main(String[] args) throws SQLException {
            MovieRepoWithInjections repository = new MovieRepoWithInjections();
            
            System.out.println("Search movies by title: ");
Scanner scanner = new Scanner(System.in);
String titleString = scanner.nextLine();
            List<Movie> movies = repository.getMovieByName(titleString);
            
            movies.forEach(System.out::println);  
        }
    public List<Movie> getMovieByName(String title) throws SQLException{
        String sql = "SELECT movie_id, title, release_year, genre, rating, director, lead_actor_id FROM movies WHERE title like '%" + title + "%'";

       List<Movie> movies = new ArrayList<>();
            try(Connection connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);

            
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
                while(resultSet.next()) {
                    movies.add(new Movie(
                        resultSet.getLong("movie_id"),
                        resultSet.getString("title"),
                        resultSet.getInt("release_year"),
                        resultSet.getString("genre"),
                        resultSet.getDouble("rating"),
                        resultSet.getString("director"),
                        resultSet.getInt("lead_actor_id")
                    ));
                }
        
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }
}
