package com.example.fileuploadprac;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository repository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    void testCreateDocument() throws Exception {

        File file = new File("/Users/desirel/Desktop/screenshot.png");
        byte[] content = Files.readAllBytes(file.toPath());

        Document document = Document.builder()
                .name(file.getName())
                .size(content.length)
                .content(content)
                .build();

        Document savedDocument = repository.save(document);
        Document findDocument = entityManager.find(Document.class, document.getId());

        assertEquals(savedDocument, findDocument, "둘은 서로 같아야 한다.");
        assertEquals(document.getSize(), findDocument.getSize());

    }
}