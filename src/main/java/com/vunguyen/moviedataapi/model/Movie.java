package com.vunguyen.moviedataapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.BatchSize;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name="movies_movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_id_generator")
    @SequenceGenerator(name = "movie_id_generator", sequenceName = "movie_id_seq", allocationSize =1)
    private Integer id;

    @Column(nullable = false)
    private String title;
    private Integer year;
    private Integer runtime;
    private String director;
    private String actors;

    private String plot;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Genre.class,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE})
    @JoinTable(name="movies_movie_genres",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @JsonIgnoreProperties("movies")
    @BatchSize(size = 20)
    private List<Genre> genres = new ArrayList<Genre>();;
    @Column(name="posterUrl")
    private String posterUrl;

    public void setMovie(Movie _movie)
    {
        BeanUtils.copyProperties(_movie, this);
    }
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genres.stream()
                .map(Genre::getName).collect(Collectors.joining(", ")) + '\'' +
                '}';
    }

}
