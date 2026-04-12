package edu.skoreiko.library.controller;

import edu.skoreiko.library.models.Book;
import edu.skoreiko.library.request.BookCreateRequest;
import edu.skoreiko.library.request.BookUpdateRequest;
import edu.skoreiko.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Rout
 * @version 1.0.0
 * @project Library
 * @class BookRestController
 * @since 12.04.2026 - 12.43
 */


@RestController
@RequestMapping("api/v1/books/")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    // CRUD - create read update delete

    // read all
    @GetMapping
    public List<Book> showAll() {
        return bookService.getAll();
    }

    // read one
    @GetMapping("{id}")
    public Book showOneById(@PathVariable String id) {
        return bookService.getById(id);
    }

    @PostMapping
    public Book insert(@RequestBody Book book) {
        return bookService.create(book);
    }

    //============== request =====================
    @PostMapping("/dto")
    public Book insert(@RequestBody BookCreateRequest request) {
        return bookService.create(request);
    }

    @PutMapping
    public Book edit(@RequestBody Book book) {
        return bookService.update(book);
    }

    //============== request =====================
    @PutMapping("/dto")
    public Book edit(@RequestBody BookUpdateRequest request) {
        return bookService.update(request);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        bookService.delById(id);
    }

}