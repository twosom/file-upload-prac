package com.example.fileuploadprac;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 512, nullable = false, unique = true)
    private String name;

    private long size;

    @Column(name = "upload_time")
    @CreatedDate
    private LocalDateTime uploadTime;

    @Column(columnDefinition = "mediumblob" +
            "")
    private byte[] content;

    public Document(Long id, String name, long size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }
}
