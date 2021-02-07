package com.academy.Netex.solrj;

import com.academy.Netex.model.Movie;
import com.academy.Netex.model.QMovie;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@Component
public class SolrjConnection {


     //Constructor that indexes the entire DB to the solr Admin. Part of it is commented because it does that operation
     //everytime we run the application.

    public SolrjConnection() {
        /*try {
            List<Movie> movies = getJpaQueryFactory().selectFrom(QMovie.movie).fetch();
            System.out.printf("Indexing %d movies...\n", movies.size());
            solrClient.addBeans(movies);

            solrClient.commit();

            System.out.printf("%d movies indexed.\n", movies.size());
        } catch (SolrServerException | IOException e) {
            System.err.printf("\nFailed to indexing movies: %s", e.getMessage());
        }*/
        queryingByUsingSolrParams();
    }

    private static final String SOLR_CORE_URL = "http://localhost:8983/solr/MovieSolrCore";
    private static final SolrClient solrClient = getSolrClient();

    private static SolrClient getSolrClient() {
        return new HttpSolrClient.Builder(SOLR_CORE_URL).withConnectionTimeout(5000).withSocketTimeout(3000).build();
    }

    public JPAQueryFactory getJpaQueryFactory() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MovieCatalog");
        EntityManager em = emf.createEntityManager();
        return new JPAQueryFactory(em);
    }


    public void queryingByUsingSolrParams() {
        // constructs a MapSolrParans instance
        final Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("q", "title:Episode");
        queryParamsMap.put("fl", "id, title, year");
        MapSolrParams queryParams = new MapSolrParams(queryParamsMap);

        // sends search request and gets the response
        QueryResponse response = null;
        try {
            response = solrClient.query(queryParams);
        } catch (SolrServerException | IOException e) {
            System.err.printf("Failed to search movies: %s", e.getMessage());
        }

        // printing results
        if (response != null) {
            System.out.println(response.getResults());
        }
    }

}
