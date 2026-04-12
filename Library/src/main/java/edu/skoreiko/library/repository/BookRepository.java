package edu.skoreiko.library.repository;

import edu.skoreiko.library.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rout
 * @version 1.0.0
 * @project Library
 * @class BookRepository
 * @since 12.04.2026 - 12.00
 */

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    public boolean existsByTitle(String title);
}
