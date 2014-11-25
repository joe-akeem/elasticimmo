package de.joeakeem.elasticimmo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import de.joeakeem.elasticimmo.model.Estate;

public interface EstateRepository extends
        ElasticsearchCrudRepository<Estate, String> {

    Long countByLivingEstate(boolean livingEstate, SearchQuery query);

    @Query("{\"nested\" : {\"path\" : \"estateGeo\",\"query\" : {\"query_string\" : {\"fields\" : [\"city\", \"zipCode\"],\"query\" : \"?0\"}}}}")
    List<Estate> findByLocation(String location);

    @Query("{\"nested\" : {\"path\" : \"estateGeo\",\"query\" : {\"query_string\" : {\"fields\" : [\"city\", \"zipCode\"],\"query\" : \"?0\"}}}}")
    Page<Estate> findByLocation(String location, Pageable pageable);

    @Query("{\"nested\" : {\"path\" : \"estateGeo\", \"query\" : {\"fuzzy_like_this\" : {\"fields\" : [\"city\"], \"like_text\": \"?0\"}}}}")
    Page<Estate> findByFuzzyLikeThisCity(String city, Pageable pageable);
}
