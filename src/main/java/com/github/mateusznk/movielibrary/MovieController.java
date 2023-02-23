package com.github.mateusznk.movielibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @GetMapping("")
    public List<Movie> getAll() {
        return movieRepository.getAll();
    }

    @GetMapping("{id}")
    public Movie getMovieById(@PathVariable("id") int id) {
        return movieRepository.getMovieById(id);
    }

    @PostMapping("")
    public int add(@RequestBody List<Movie> movies) {
        return movieRepository.save(movies);
    }

    @PutMapping("/{id}") // zapytanie PUT powinniśmy podać wszystkie paramaery w wywołaniu
    public int update(@PathVariable("id") int id, @RequestBody Movie updatedMovie) {
        Movie movieById = movieRepository.getMovieById(id);
        if (movieById != null) {
            movieById.setMovie(updatedMovie.getMovie());
            movieById.setRating(updatedMovie.getRating());

            movieRepository.update(movieById);

            return 1;
        } else {
            return -1;
        }
    }

    @PatchMapping("/{id}")// PATCH jest lepsza w przypadku, gdy nie podajemy wszystkich zapytań
    public int partiallyUpdate(@PathVariable("id") int id, @RequestBody Movie updatedMovie) {
        Movie movieById = movieRepository.getMovieById(id);
        if (movieById != null) {
            if (updatedMovie.getMovie() != null) movieById.setMovie(updatedMovie.getMovie());
            if (updatedMovie.getRating() > 0) movieById.setRating(updatedMovie.getRating());
            movieRepository.update(movieById);

            return 1;
        } else {
            return -1;
        }
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") int id) {
        return movieRepository.delete(id);
    }
}
