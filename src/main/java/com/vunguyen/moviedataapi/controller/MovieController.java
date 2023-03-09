package com.vunguyen.moviedataapi.controller;

import com.vunguyen.moviedataapi.model.Movie;
import com.vunguyen.moviedataapi.model.ResponseObject;
import com.vunguyen.moviedataapi.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/movies/api/v1/movies")
public class MovieController {
    @Autowired
    private MovieRepository repository;

    @GetMapping("")
    public Page<Movie> findAllMovies(Pageable pagi) {
        return repository.findAll(pagi);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject>findById(@PathVariable Integer id) {
        Optional<Movie> foundMovie = repository.findById(id);
        if (foundMovie.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "query complete successfully", repository.findById(id))
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("fail", "Cannot find movie with id = " + id, ""));
        }
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject>postMovie(@RequestBody Movie newMovie) {
        Optional<Movie> foundMovie = repository.findById(newMovie.getId());
        if (foundMovie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("fail", "database already have movie id = " + newMovie.getId().toString(), "")
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "create new movie successfully in he database", repository.save(newMovie)));
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject>updateMovie(@RequestBody Movie updatedMovieInfo, @PathVariable Integer id) {
        Movie updatedMovie = repository.findById(id).
                map(movie -> {
                    movie.setMovie(updatedMovieInfo);
                    return repository.save(movie);
                }).orElseGet(() -> {
                    updatedMovieInfo.setId(id);
                    return repository.save(updatedMovieInfo);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("fail", "Updated database successfully", updatedMovie)
            );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject>deleteMovie(@PathVariable Integer id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "delete movie successfully", ""));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("fail", "Delete fail. Movie not found", ""));
        }
    }

    @GetMapping("/search/title={keywordMovie}")
    ResponseEntity<ResponseObject>findByTitle(@PathVariable String keywordMovie) {
        List<Movie> foundMovie = repository.findByTitleContainingIgnoreCase(keywordMovie);
        if (foundMovie.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "query complete successfully", foundMovie)
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("fail", "Cannot find movie with title contains = " + keywordMovie, ""));
        }
    }

    @GetMapping("/search/genre={genreName}")
    ResponseEntity<ResponseObject>findByGenre(@PathVariable String genreName) {
        List<Movie> foundMovie = repository.findAllByGenres_NameIgnoreCase(genreName);
        if (foundMovie.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "query complete successfully", foundMovie)
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("fail", "Cannot find movie with genre = " + genreName, ""));
        }
    }


}


