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
@Table(name="movies_genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_id_generator")
    @SequenceGenerator(name = "genre_id_generator", sequenceName = "genre_id_seq", allocationSize = 1)
    private Integer id;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.ALL
            }, mappedBy = "genres")
    @JsonIgnoreProperties("genres")
    @BatchSize(size = 20)
    private List<Movie> movies = new ArrayList<Movie>();

    public void setGenre(Genre _genre)
    {
        BeanUtils.copyProperties(_genre, this);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", movies='" + movies.stream()
                .map(Movie::getTitle).collect(Collectors.joining(", ")) + '\'' +
                '}';
    }
}
