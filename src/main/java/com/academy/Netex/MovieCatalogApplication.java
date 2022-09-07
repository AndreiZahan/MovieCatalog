package com.academy.Netex;

import com.academy.Netex.model.Movie;
import com.academy.Netex.model.QMovie;
import com.academy.Netex.service.MovieService;
import com.academy.Netex.solrj.SolrjConnection;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
@PropertySource({"application.properties"})
public class MovieCatalogApplication implements CommandLineRunner {

    @Autowired
    MovieService movieService;

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        // link example: http://www.omdbapi.com/?i=tt1234567&apikey=17e9d15f&type=movie


        while (getJpaQueryFactory().selectFrom(QMovie.movie).fetch().size() < 20) {
            int min = 1212121;
            int max = 1919191;
            final String IMDB_ID_PARAMETER = "i";
            final String OBJECT_TYPE_PARAMETER = "type";
            final String API_KEY = "apikey";
            final String API_KEY_VALUE = "a78ee03a";
            final String OBJECT_TYPE_VALUE = "movie";
            final String BASE_URL = "https://omdbapi.com";

            final RestTemplate restTemplate = new RestTemplate();
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            int imdbId = (int) (Math.random() * (max - min) + min);
            String imdbIdValue = "tt" + imdbId;


            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                    .queryParam(API_KEY, API_KEY_VALUE)
                    .queryParam(IMDB_ID_PARAMETER, imdbIdValue)
                    .queryParam(OBJECT_TYPE_PARAMETER, OBJECT_TYPE_VALUE);

            ResponseEntity<String> response = restTemplate.getForEntity(builder.build().toUri(), String.class);

            Movie movie = mapper.readValue(response.getBody(), Movie.class);

            assert movie != null;
            String addMovieURL = "http://localhost:7070/MovieCatalog/addMovie";
            if (movie.getTitle() == null) {
                continue;
            }
            restTemplate.postForObject(addMovieURL, movie, Movie.class);
            //movieService.saveMovie(movie);
        }
        SolrjConnection solrjConnection = new SolrjConnection();
    }


    public JPAQueryFactory getJpaQueryFactory() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MovieCatalog");
        EntityManager em = emf.createEntityManager();
        return new JPAQueryFactory(em);
    }

}
