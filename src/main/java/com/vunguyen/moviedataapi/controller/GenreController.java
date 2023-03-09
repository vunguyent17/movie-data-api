package com.vunguyen.moviedataapi.controller;

import com.vunguyen.moviedataapi.model.Genre;
import com.vunguyen.moviedataapi.model.Movie;
import com.vunguyen.moviedataapi.model.ResponseObject;
import com.vunguyen.moviedataapi.repositories.GenreRepository;
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
@RequestMapping(path="/movies/api/v1/genres")
public class GenreController {
    @Autowired
    private GenreRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("")
    ResponseEntity<ResponseObject> findAllGenres()
    {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "query complete successfully", repository.getGenreIdAndName())
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findGenre(@PathVariable Integer id)
    {
        List<GenreRepository.GenreDetail> foundGenre = repository.getGenreDetail(id);
        if (!foundGenre.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "query complete successfully", foundGenre)
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Cannot find genre with id = " + id, ""));
        }
    }
    @GetMapping("/{id}/movies")
    public Page<Movie> findMoviesByGenre(@PathVariable Integer id, Pageable pagi) {
        return movieRepository.findAllByGenres_Id(id, pagi);
    }
    @PostMapping("/insert")
    ResponseEntity<ResponseObject>postGenre(@RequestBody Genre newGenre) {
        Optional<Genre> foundGenre = repository.findById(newGenre.getId());
        if (foundGenre.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("fail", "database already have genre id = " + newGenre.getId().toString(), "")
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "create new genre successfully in he database", repository.save(newGenre)));
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject>updateGenre(@RequestBody Genre updatedGenreInfo, @PathVariable Integer id) {
        Genre updatedGenre = repository.findById(id).
                map(genre -> {
                    genre.setGenre(updatedGenreInfo);
                    return repository.save(genre);
                }).orElseGet(() -> {
                    updatedGenreInfo.setId(id);
                    return repository.save(updatedGenreInfo);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Updated database successfully", updatedGenre)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject>deleteGenre(@PathVariable Integer id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "delete genre successfully", ""));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("fail", "Delete fail. Genre not found", ""));
        }
    }

}
