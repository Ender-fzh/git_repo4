package com.example.moviesystem.controller;

import com.example.moviesystem.entity.Movie;
import com.example.moviesystem.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;
    private static final Logger log = LoggerFactory.getLogger(MovieController.class); // 添加日志

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        log.info("Fetching all movies"); // 日志记录
        return movieService.getAllMovies();
    }

    @GetMapping("/search")
    public List<Movie> searchMovies(@RequestParam String title) {
        log.info("Searching movies with title: {}", title);
        return movieService.searchMovies(title);
    }

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        log.info("Adding new movie: {}", movie.getTitle());
        return movieService.saveMovie(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        try {
            Movie existingMovie = movieService.getMovieById(id);
            if (existingMovie == null) {
                return ResponseEntity.notFound().build();
            }
            // 避免空值覆盖
            if (movie.getTitle() != null) {
                existingMovie.setTitle(movie.getTitle());
            }
            if (movie.getDescription() != null) {
                existingMovie.setDescription(movie.getDescription());
            }
            Movie updatedMovie = movieService.saveMovie(existingMovie);
            return ResponseEntity.ok(updatedMovie);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        log.info("Deleting movie with ID: {}", id);
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }
}