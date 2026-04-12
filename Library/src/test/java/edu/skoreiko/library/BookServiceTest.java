package edu.skoreiko.library;

import edu.skoreiko.library.models.Book;
import edu.skoreiko.library.repository.BookRepository;
import edu.skoreiko.library.response.ApiResponse;
import edu.skoreiko.library.response.BaseMetaData;
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

    @Autowired
    private BookRepository bookRepository;

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

// Comment for third lab
//    @Test
//    void whenGetAllBooksListThenSizeIs30() {
//        // given
//
//        // when
//        int size = underTest.getAll().size();
//
//        // then
//        assertEquals(30, size);
//    }

    // 1
    @Test
    void whenBookIsPresentThenReturnOkApiResponse() {
        // given
        Book existing = bookRepository.findAll().get(0);

        // when
        ApiResponse<BaseMetaData, Book> response = underTest.getByIdAsApiResponse(existing.getId());

        // then
        assertNotNull(response);
        assertEquals(200, response.getMeta().getCode());
        assertEquals(existing.getTitle(), response.getData().get(0).getTitle());
    }

    // 2
    @Test
    void whenBookIsNotPresentThenReturn404() {
        // given
        String id = "non_existent_id";

        // when
        ApiResponse<BaseMetaData, Book> response = underTest.getByIdAsApiResponse(id);

        // then
        assertEquals(404, response.getMeta().getCode());
        assertFalse(response.getMeta().isSuccess());
        assertEquals("Book not found", response.getMeta().getErrorMessage());
    }

    // 3
    @Test
    void whenGetAllAsApiResponseThenReturnOk() {
        // given (data from setup)

        // when
        ApiResponse<BaseMetaData, Book> response = underTest.getAllAsApiResponse();

        // then
        assertTrue(response.getMeta().isSuccess());
        assertEquals(30, response.getData().size());
    }

    // 4
    @Test
    void whenUpdateExistingBookThenReturnOk() {
        // given
        Book book = bookRepository.findAll().get(0);
        book.setTitle("Updated Title");

        // when
        ApiResponse<BaseMetaData, Book> response = underTest.updateAsApiResponse(book);

        // then
        assertEquals(200, response.getMeta().getCode());
        assertEquals("Updated Title", response.getData().get(0).getTitle());
    }

    // 5
    @Test
    void whenUpdateNonExistentThenReturn404() {
        // given
        Book fakeBook = new Book("Ghost", List.of("Unknown"), "Empty");
        fakeBook.setId("fake_id_123");

        // when
        ApiResponse<BaseMetaData, Book> response = underTest.updateAsApiResponse(fakeBook);

        // then
        assertEquals(404, response.getMeta().getCode());
        assertFalse(response.getMeta().isSuccess());
    }

    // 6
    @Test
    void whenSuccessThenErrorMessageIsNull() {
        // given

        // when
        ApiResponse<BaseMetaData, Book> response = underTest.getAllAsApiResponse();

        // then
        assertNull(response.getMeta().getErrorMessage());
    }

    // 7
    @Test
    void whenNotFoundDataListIsNotNull() {
        // given
        String wrongId = "wrong";

        // when
        ApiResponse<BaseMetaData, Book> response = underTest.getByIdAsApiResponse(wrongId);

        // then
        assertNotNull(response.getData());
    }

    // 8
    @Test
    void checkMetaDataConsistency() {
        // given
        int code = 200;
        boolean success = true;

        // when
        BaseMetaData meta = new BaseMetaData(code, success);

        // then
        assertEquals(code, meta.getCode());
        assertTrue(meta.isSuccess());
    }

    // 9
    @Test
    void whenDeleteThenGetByIdReturns404() {
        // given
        Book book = bookRepository.findAll().get(0);
        String id = book.getId();

        // when
        underTest.delById(id);
        ApiResponse<BaseMetaData, Book> response = underTest.getByIdAsApiResponse(id);

        // then
        assertEquals(404, response.getMeta().getCode());
    }

    // 10
    @Test
    void whenCreateNewBookThenSizeIs31() {
        // given
        Book newBook = new Book("New Book", List.of("New Author"), "New Desc");

        // when
        underTest.create(newBook);
        int size = underTest.getAll().size();

        // then
        assertEquals(31, size);
    }

    // 11
    @Test
    void whenGetBookThenAuthorsListIsCorrect() {
        // given
        Book book = bookRepository.findAll().get(0);

        // when
        List<String> authors = book.getAuthors();

        // then
        assertFalse(authors.isEmpty());
        assertTrue(authors.get(0).contains("Author"));
    }

    // 12
    @Test
    void whenDeleteAllThenSizeIsZero() {
        // given

        // when
        underTest.deleteAll();
        int size = underTest.getAll().size();

        // then
        assertEquals(0, size);
    }

    // 13
    @Test
    void whenGetByIdStandardThenReturnCorrectBook() {
        // given
        Book existing = bookRepository.findAll().get(5);
        String id = existing.getId();

        // when
        Book found = underTest.getById(id);

        // then
        assertNotNull(found);
        assertEquals(existing.getTitle(), found.getTitle());
    }

    // 14
    @Test
    void whenUpdateFailsErrorMessageIsPresent() {
        // given
        Book book = new Book("Fail", List.of("Fail"), "Fail");
        book.setId("invalid");

        // when
        ApiResponse<BaseMetaData, Book> response = underTest.updateAsApiResponse(book);

        // then
        assertNotNull(response.getMeta().getErrorMessage());
        assertFalse(response.getMeta().isSuccess());
    }

    // 15
    @Test
    void whenCreateAllThenSizeIsSum() {
        // given
        underTest.deleteAll();
        List<Book> moreBooks = List.of(
                new Book("B1", List.of("A1"), "D1"),
                new Book("B2", List.of("A2"), "D2")
        );

        // when
        underTest.createAll(moreBooks);
        int size = underTest.getAll().size();

        // then
        assertEquals(2, size);
    }
}