package edu.skoreiko.library;

import edu.skoreiko.library.models.Book;
import edu.skoreiko.library.repository.BookRepository;
import edu.skoreiko.library.service.BookService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Rout
 * @version 1.0.0
 * @project Library
 * @class BookLoggingTest
 * @since 12.04.2026 - 15.41
 */


@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookLoggingTest {

    @Autowired
    private BookService underTest;

    @Autowired
    private BookRepository bookRepository;

    private String testId;

    @BeforeEach
    void setUp() {
        testId = bookRepository.findAll().get(0).getId();
    }

    // 1
    @Test
    void testLoggingOutputBeforeMethodGetById(CapturedOutput output) {
        // given
        String id = testId;

        // when
        underTest.getById(id);

        // then
        String logs = output.toString();
        assertTrue(logs.contains("Entering method: BookService.getById"));
        assertTrue(logs.contains(id));
    }

    // 2
    @Test
    void testLoggingOutputAfterMethodGetById(CapturedOutput output) {
        // given
        String id = testId;

        // when
        underTest.getById(id);

        // then
        String logs = output.toString();
        assertTrue(logs.contains("Method completed: BookService.getById"));
        assertTrue(logs.contains(id));
    }

    // 3
    @Test
    void testLoggingOutputBeforeMethodGetAll(CapturedOutput output) {
        // given

        // when
        underTest.getAll();

        // then
        String logs = output.toString();
        assertTrue(logs.contains("Entering method: BookService.getAll"));
    }

    // 4
    @Test
    void testLoggingOutputAfterMethodGetAll(CapturedOutput output) {
        // given

        // when
        List<Book> result = underTest.getAll();

        // then
        String logs = output.toString();
        assertTrue(logs.contains("Method completed: BookService.getAll"));
        assertTrue(logs.contains(result.get(0).getTitle()));
    }

    // 5
    @Test
    void testLoggingOutputBeforeMethodCreate(CapturedOutput output) {
        // given
        Book book = new Book("Log Test", List.of("Author"), "Desc");

        // when
        underTest.create(book);

        // then
        String logs = output.toString();
        assertTrue(logs.contains("Entering method: BookService.create"));
        assertTrue(logs.contains("Log Test"));
    }

    // 6
    @Test
    void testLoggingOutputAfterMethodCreate(CapturedOutput output) {
        // given
        Book book = new Book("After Log Test", List.of("Author"), "Desc");

        // when
        underTest.create(book);

        // then
        String logs = output.toString();
        assertTrue(logs.contains("Method completed: BookService.create"));
        assertTrue(logs.contains("After Log Test"));
    }

    // 7
    @Test
    void testLoggingOutputBeforeMethodUpdate(CapturedOutput output) {
        // given
        Book existing = bookRepository.findById(testId).get();
        existing.setTitle("Updated Title");

        // when
        underTest.update(existing);

        // then
        String logs = output.toString();
        assertTrue(logs.contains("Entering method: BookService.update"));
        assertTrue(logs.contains(testId));
    }

    // 8
    @Test
    void testLoggingOutputAfterMethodUpdate(CapturedOutput output) {
        // given
        Book existing = bookRepository.findById(testId).get();
        String newTitle = "Loggable Updated Title";
        existing.setTitle(newTitle);

        // when
        underTest.update(existing);

        // then
        String logs = output.toString();
        assertTrue(logs.contains("Method completed: BookService.update"));
        assertTrue(logs.contains(newTitle));
    }

    // 9
    @Test
    void testLoggingOutputBeforeMethodDelById(CapturedOutput output) {
        // given
        String id = testId;

        // when
        underTest.delById(id);

        // then
        String logs = output.toString();
        assertTrue(logs.contains("Entering method: BookService.delById"));
        assertTrue(logs.contains(id));
    }

    // 10
    @Test
    void testLoggingOutputAfterMethodDelById(CapturedOutput output) {
        // given
        String id = testId;

        // when
        underTest.delById(id);

        // then
        String logs = output.toString();
        assertTrue(logs.contains("Method completed: BookService.delById"));
        assertTrue(logs.contains("result: null"));
    }
}