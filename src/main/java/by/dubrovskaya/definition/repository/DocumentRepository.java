package by.dubrovskaya.definition.repository;

import by.dubrovskaya.definition.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
