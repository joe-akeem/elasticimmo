package de.joeakeem.elasticimmo.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import de.joeakeem.elasticimmo.model.ImportRecord;

public interface ImportRecordRepository extends ElasticsearchCrudRepository<ImportRecord, String> {

}
