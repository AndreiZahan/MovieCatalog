package com.academy.Netex.controller;

import com.academy.Netex.model.Movie;
import com.academy.Netex.model.QMovie;
import com.academy.Netex.service.MovieService;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/MovieCatalog")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/showMoviesBetween/{x}/{y}")
    public List<Movie> showMoviesBetweenXY (@PathVariable Integer x, @PathVariable Integer y) {
        List<Movie> movieList = new ArrayList<>();
        List<Movie> movieList2 = movieService.getMovieListForController();

        for (Movie movie : movieList2) {
            if (x < Integer.parseInt(movie.getYear()) && Integer.parseInt(movie.getYear()) < y) {
                movieList.add(movie);
            }
        }

        return movieList;
    }

    @GetMapping("/search-{name}")
    public List<Movie>  showMoviesWithName (@PathVariable String name) {
        return movieService.getJpaQueryFactory().selectFrom(QMovie.movie).where(QMovie.movie.title.contains(name)).fetch();
    }

    @GetMapping("/orderByTitleAsc")
    public List<Movie> orderMoviesByTitleAsc () {
        return movieService.getJpaQueryFactory().selectFrom(QMovie.movie).orderBy(QMovie.movie.title.asc()).fetch();
    }

    @GetMapping("/orderByTitleDesc")
    public List<Movie> orderMoviesByTitleDesc () {
        return movieService.getJpaQueryFactory().selectFrom(QMovie.movie).orderBy(QMovie.movie.title.desc()).fetch();
    }

    @PostMapping("/addMovie")
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    @PostMapping("/addMovies")
    public List<Movie> addMovies(@RequestBody List<Movie> movies) {
        return movieService.saveMovies(movies);
    }

    @GetMapping("/movies")
    public List<Movie> findAllMovies() {
        return movieService.getMovies();
    }

    @GetMapping("/movie/{id}")
    public Movie findMovieById(@PathVariable Integer id) {
        return movieService.getMovieById(id);
    }

    @GetMapping("/movie/{title}")
    public Movie findMovieByTitle(@PathVariable String title) {
        return movieService.getMovieByTitle(title);
    }

    @PutMapping("/update")
    public Movie updateMovie(@RequestBody Movie movie) {
        return movieService.updateMovie(movie);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        return movieService.deleteMovie(id);
    }

}
