package com.academy.Netex;

import com.academy.Netex.model.Movie;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootApplication
@PropertySource({"application.properties"})
public class MovieCatalogApplication /*implements CommandLineRunner*/ {

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogApplication.class, args);
	}

	// I can only run once the below method and comment it after that. Otherwise, any time I run the application,
    // it will insert another 100 movies into my local DB.

	/*
    @Override
    public void run(String... args) throws Exception {

        // link example: http://www.omdbapi.com/?i=tt1234567&apikey=17e9d15f&type=movie

        for (int i = 0; i < 20; i++) {
            int min = 1212121;
            int max = 1919191;
            final String IMDB_ID_PARAMETER = "i";
            final String OBJECT_TYPE_PARAMETER = "type";
            final String API_KEY = "apikey";
            final String API_KEY_VALUE = "17e9d15f";
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
			String addMovieURL = "http://localhost:7070/addMovie";
			if (movie.getTitle() == null) {
            	continue;
			}
            restTemplate.postForObject(addMovieURL, movie, Movie.class);

        }
    }
	*/

}
