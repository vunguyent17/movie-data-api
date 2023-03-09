package com.vunguyen.moviedataapi.repositories;

import com.vunguyen.moviedataapi.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@CrossOrigin(origins = "*",
        methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT },
        maxAge = 3600)
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    @Query("select g.id as id, g.name as name from Genre g")
    List<GenreIdAndName> getGenreIdAndName();

    @Query("select g.id as id, g.name as name from Genre g where g.id = ?1")
    List<GenreDetail> getGenreDetail(Integer id);

    interface GenreIdAndName{
        String getId();
        String getName();
    }

    interface GenreDetail{
        String getId();
        String getName();
        default String getMovies(){
            return "genre/"+ getId() + "/movies";
        };
    }

}
