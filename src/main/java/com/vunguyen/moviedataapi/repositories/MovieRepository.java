package com.vunguyen.moviedataapi.repositories;

import com.vunguyen.moviedataapi.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*",
        methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT },
        maxAge = 3600)
public interface  MovieRepository extends JpaRepository<Movie, Integer>, PagingAndSortingRepository<Movie, Integer> {
    Page<Movie> findAllByGenres_Id(Integer id, Pageable pageable);
    List<Movie> findByTitleContainingIgnoreCase(@Param("keyword")String keyword);
    List<Movie> findAllByGenres_NameIgnoreCase(String genreName);

    Page<Movie> findByTitleContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

    Page<Movie> findById(Integer id, Pageable pageable);

}
