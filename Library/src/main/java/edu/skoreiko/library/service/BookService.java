package edu.skoreiko.library.service;

import edu.skoreiko.library.models.Book;
import edu.skoreiko.library.repository.BookRepository;
import edu.skoreiko.library.request.BookCreateRequest;
import edu.skoreiko.library.request.BookUpdateRequest;
import edu.skoreiko.library.response.ApiResponse;
import edu.skoreiko.library.response.BaseMetaData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rout
 * @version 1.0.0
 * @project Library
 * @class BookService
 * @since 12.04.2026 - 12.24
 */
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private List<Book> books = new ArrayList<>();

    {
        books.add(new Book("1984", List.of("George Orwell"), "Dystopian fiction"));
        books.add(new Book("The Hobbit", List.of("J.R.R. Tolkien"), "High-fantasy"));
        books.add(new Book("Fahrenheit 451", List.of("Ray Bradbury"), "Dystopian novel"));
        books.add(new Book("The Great Gatsby", List.of("F. Scott Fitzgerald"), "Classic literature"));
        books.add(new Book("Brave New World", List.of("Aldous Huxley"), "Social science fiction"));
        books.add(new Book("Dune", List.of("Frank Herbert"), "Epic science fiction"));
        books.add(new Book("The Witcher", List.of("Andrzej Sapkowski"), "Fantasy short stories"));
    }

    // @PostConstruct
    void init() {
        this.bookRepository.deleteAll();
        for (Book book : books) {
            create(book);
        }
    }

    // CRUD - create read update delete

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(String id) {
        return bookRepository.findById(id).get();
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public Book create(BookCreateRequest request) {
        return bookRepository.save(mapToItem(request));
    }

    public Book update(Book book) {
        return bookRepository.save(book);
    }

    public void delById(String id) {
        bookRepository.deleteById(id);
    }

    private Book mapToItem(BookCreateRequest request) {
        return new Book(request.title(), request.authors(), request.description());
    }

    public Book update(BookUpdateRequest request) {
        Book bookPersisted = bookRepository.findById(request.id()).orElse(null);
        if (bookPersisted != null) {
            Book bookToUpdate = Book.builder()
                    .id(request.id())
                    .title(request.title())
                    .authors(request.authors())
                    .description(request.description())
                    .build();
            return bookRepository.save(bookToUpdate);
        }
        return null;
    }

    public List<Book> createAll(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

    // ------------------------- ApiResponse Methods -------------------------

    public ApiResponse<BaseMetaData, Book> getByIdAsApiResponse(String id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            return new ApiResponse<>(new BaseMetaData(200, true), book);
        }
        return new ApiResponse<>(new BaseMetaData(404, false, "Book not found"));
    }

    public ApiResponse<BaseMetaData, Book> getAllAsApiResponse() {
        List<Book> all = bookRepository.findAll();
        BaseMetaData meta = new BaseMetaData(200, true);
        return ApiResponse.<BaseMetaData, Book>builder()
                .meta(meta)
                .data(all)
                .build();
    }

    public ApiResponse<BaseMetaData, Book> updateAsApiResponse(Book book) {
        if (book.getId() != null && bookRepository.existsById(book.getId())) {
            Book updated = bookRepository.save(book);
            return new ApiResponse<>(new BaseMetaData(200, true), updated);
        }
        return new ApiResponse<>(new BaseMetaData(404, false, "Cannot update: Book not found"));
    }
}