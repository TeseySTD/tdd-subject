package edu.skoreiko.library.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author Rout
 * @version 1.0.0
 * @project Library
 * @class Book
 * @since 12.04.2026 - 11.55
 */


@Data
@Document
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Book extends AuditMetadata {
    @Id
    private String id;
    private String title;
    private List<String> authors;
    private String description;

    public Book(String title, List<String> authors, String description) {
        this.title = title;
        this.authors = authors;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;
        return getId().equals(book.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
