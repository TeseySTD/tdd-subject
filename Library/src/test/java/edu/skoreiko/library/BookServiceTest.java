package edu.skoreiko.library;

import edu.skoreiko.library.models.Book;
import edu.skoreiko.library.service.BookService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Rout
 * @version 1.0.0
 * @project Library
 * @class BookServiceTest
 * @since 12.04.2026 - 12.58
 */


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookServiceTest {

    @Autowired
    private BookService underTest;

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void setUp() {
        underTest.deleteAll();

        List<Book> testBooks = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            testBooks.add(new Book(
                    "Book Title " + i,
                    List.of("Author " + i),
                    "Description for book " + i
            ));
        }

        underTest.createAll(testBooks);
    }

    @AfterEach
    void tearsDown() {
    }

    @Test
    void whenGetAllBooksListThenSizeIs30() {
        // given

        // when
        int size = underTest.getAll().size();

        // then
        assertEquals(30, size);
    }
}