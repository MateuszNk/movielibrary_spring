package com.github.mateusznk.movielibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Movie> getAll() {
        return jdbcTemplate.query("SELECT id, movie, rating FROM movie",
                BeanPropertyRowMapper.newInstance(Movie.class));
    }

    public Movie getMovieById(int id) {
        return jdbcTemplate.queryForObject("SELECT id, movie, rating FROM movie WHERE " +
                "id = ?", BeanPropertyRowMapper.newInstance(Movie.class), id);
    }

    public int save(List<Movie> movies) {
        movies.forEach(movie -> jdbcTemplate
                .update("INSERT INTO movie(movie, rating) VALUES(?, ?)",
                        movie.getMovie(), movie.getRating()
                ));
        return 1;
    }

    public int update(Movie movie) {
        return jdbcTemplate.update("UPDATE movie SET movie=?, rating=? WHERE id=?",
                movie.getMovie(), movie.getRating(), movie.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM movie WHERE id=?", id);
    }
}
