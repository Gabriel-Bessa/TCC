package br.com.bessatech.notify.core.mongo.repository;

import br.com.bessatech.notify.core.mongo.document.MArticleDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MArticleRepository extends MongoRepository<MArticleDocument, String> {
}