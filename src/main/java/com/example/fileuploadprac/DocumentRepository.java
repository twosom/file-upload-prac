package com.example.fileuploadprac;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT new Document(d.id, d.name, d.size) " +
            "FROM Document d " +
            "ORDER BY d.uploadTime DESC ")
    List<Document> findAll();
}
