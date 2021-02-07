package com.academy.Netex.service;

import com.academy.Netex.model.Movie;
import com.academy.Netex.model.QMovie;
import com.academy.Netex.repository.MovieRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public JPAQueryFactory getJpaQueryFactory() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MovieCatalog");
        EntityManager em = emf.createEntityManager();
        return new JPAQueryFactory(em);
    }

    public List<Movie> getMovieListForController() {
        return getJpaQueryFactory().selectFrom(QMovie.movie).fetch();
    }

    public MovieService() {}

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> saveMovies(List<Movie> movies) {
        return movieRepository.saveAll(movies);
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Integer id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    public String deleteMovie (Integer id) {
        movieRepository.deleteById(id);
        return "Movie removed for id: " + id + ".";
    }

    public Movie updateMovie(Movie movie) {
        Movie existingMovie = movieRepository.findById(movie.getId()).orElse(null);
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setYear(movie.getYear());
        existingMovie.setReleased(movie.getReleased());
        existingMovie.setPlot(movie.getPlot());
        existingMovie.setRated(movie.getRated());
        existingMovie.setGenre(movie.getGenre());
        existingMovie.setImdbRating(movie.getImdbRating());
        existingMovie.setActors(movie.getActors());
        existingMovie.setWriter(movie.getWriter());
        existingMovie.setDirector(movie.getDirector());
        return movieRepository.save(existingMovie);
    }
}
