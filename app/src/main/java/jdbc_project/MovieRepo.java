package jdbc_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieRepo {
     
        private static final   String DB_URL = "jdbc:postgresql://localhost:5432/cinemadb";
        private static final String DB_LOGIN = "macbook";
        private static final String DB_PASSWORD = "";

           public boolean updateMovie(Movie movie) throws SQLException{
        String sql = "UPDATE movies SET title = ?, release_year = ?, genre = ?, rating = ?, director = ?, lead_actor_id = ? WHERE movie_id = ?";


        try(Connection connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);
            ) {
                statement.setString(1, movie.getTitle());
                statement.setInt(2, movie.getReleaseYear());
                statement.setString(3, movie.getGenre());
                statement.setDouble(4, movie.getRating());
                statement.setString(5, movie.getDirector());
                statement.setInt(6, movie.getLeadActorId());
                statement.setLong(7, movie.getMovieId());


              int affectedRows = statement.executeUpdate();
                return affectedRows > 0;
            } 
    }
        public Movie createMovie(Movie movie) throws SQLException{
        String sql = "INSERT INTO movies (title, release_year, genre, rating, director, lead_actor_id) VALUES (?, ?, ?, ?, ?, ?)";


        try(Connection connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ) {
                statement.setString(1, movie.getTitle());
                statement.setInt(2, movie.getReleaseYear());
                statement.setString(3, movie.getGenre());
                statement.setDouble(4, movie.getRating());
                statement.setString(5, movie.getDirector());
                statement.setInt(6, movie.getLeadActorId());

              int affectedRows = statement.executeUpdate();
              if(affectedRows>0){
                try(ResultSet generatedKeys = statement.getGeneratedKeys()){
                  if(generatedKeys.next()){
                    movie.setMovieId(generatedKeys.getLong(1));
                  }
                }
              }
            } 
        return movie;
    }

    public Movie getMovieById(long id) throws SQLException{
        String sql = "SELECT movie_id, title, release_year, genre, rating, director, lead_actor_id FROM movies WHERE movie_id = ?";

        Movie movie = null;
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);
            ) {
                statement.setLong(1, id);
                try(ResultSet resultSet= statement.executeQuery()){
                while(resultSet.next()) {
                     movie = new Movie(resultSet.getLong("movie_id"),
                            resultSet.getString("title"),
                            resultSet.getInt("release_year"),
                            resultSet.getString("genre"),
                            resultSet.getDouble("rating"),
                            resultSet.getString("director"),
                            resultSet.getInt("lead_actor_id")
             ); 
                }
              }
            } 
        return movie;
    }

    public List<Movie> getAllMovies() {
        String sql = "SELECT * FROM movies";
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
public boolean deleteMovie(long id) throws SQLException{
        String sql = "DELETE FROM movies WHERE movie_id = ?";

        try(Connection connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);
            ) {
                statement.setLong(1, id);
            
              int affectedRows = statement.executeUpdate();
                return affectedRows > 0;
            } 
    }
    
}
