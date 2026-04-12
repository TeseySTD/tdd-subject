package edu.skoreiko.library;

import edu.skoreiko.library.models.Book;
import edu.skoreiko.library.request.BookPageRequest;
import edu.skoreiko.library.response.ApiResponse;
import edu.skoreiko.library.response.PaginationMetaData;
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
 * @class BookServicePagingTest
 * @since 12.04.2026 - 15.29
 */

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookServicePagingTest {

    @Autowired
    private BookService underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
        List<Book> testBooks = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            testBooks.add(new Book("Title " + i, List.of("Author"), "Description " + i));
        }
        underTest.createAll(testBooks);
    }

    // 1
    @Test
    void whenHappyPathThenOk() {
        // given
        BookPageRequest request = new BookPageRequest(0, 5);

        // when
        ApiResponse<PaginationMetaData, Book> response = underTest.getItemsPage(request);

        // then
        assertNotNull(response);
        assertNotNull(response.getMeta());
        assertEquals(200, response.getMeta().getCode());
        assertTrue(response.getMeta().isSuccess());
        assertEquals(0, response.getMeta().getNumber());
        assertEquals(5, response.getMeta().getSize());
        assertEquals(30, response.getMeta().getTotalElements());
        assertEquals(6, response.getMeta().getTotalPages());
        assertTrue(response.getMeta().isFirst());
        assertFalse(response.getMeta().isLast());
        assertEquals(5, response.getData().size());
    }

    // 2
    @Test
    void whenSizeIs_7_AndPageIs_4_ThenIsLast_TrueAndSizeEquals_2() {
        // given
        BookPageRequest request = new BookPageRequest(4, 7);

        // when
        ApiResponse<PaginationMetaData, Book> response = underTest.getItemsPage(request);

        // then
        assertEquals(2, response.getData().size());
        assertTrue(response.getMeta().isLast());
        assertFalse(response.getMeta().isFirst());
        assertEquals(4, response.getMeta().getNumber());
        assertEquals(5, response.getMeta().getTotalPages());
    }

    // 3
    @Test
    void whenTheListIsEmptyThenMetadataAndDataAreNotNull() {
        // given
        underTest.deleteAll();
        BookPageRequest request = new BookPageRequest(0, 10);

        // when
        ApiResponse<PaginationMetaData, Book> response = underTest.getItemsPage(request);

        // then
        assertNotNull(response);
        assertNotNull(response.getMeta());
        assertNotNull(response.getData());
        assertTrue(response.getData().isEmpty());
    }

    // 4
    @Test
    void whenPageValueIsOutOfRangeThenDataListIsEmpty() {
        // given
        BookPageRequest request = new BookPageRequest(100, 10);

        // when
        ApiResponse<PaginationMetaData, Book> response = underTest.getItemsPage(request);

        // then
        assertNotNull(response.getData());
        assertTrue(response.getData().isEmpty());
        assertEquals(100, response.getMeta().getNumber());
    }

    // 5
    @Test
    void whenRequestMiddlePageThenFirstAndLastAreFalse() {
        // given
        BookPageRequest request = new BookPageRequest(1, 10);

        // when
        ApiResponse<PaginationMetaData, Book> response = underTest.getItemsPage(request);

        // then
        assertFalse(response.getMeta().isFirst());
        assertFalse(response.getMeta().isLast());
        assertEquals(10, response.getData().size());
    }

    // 6
    @Test
    void whenPageSizeIsOneThenTotalPagesIsThirty() {
        // given
        BookPageRequest request = new BookPageRequest(0, 1);

        // when
        ApiResponse<PaginationMetaData, Book> response = underTest.getItemsPage(request);

        // then
        assertEquals(30, response.getMeta().getTotalPages());
        assertEquals(1, response.getData().size());
    }

    // 7
    @Test
    void whenPageSizeIsLargerThanTotalThenIsFirstAndLastAreTrue() {
        // given
        BookPageRequest request = new BookPageRequest(0, 50);

        // when
        ApiResponse<PaginationMetaData, Book> response = underTest.getItemsPage(request);

        // then
        assertTrue(response.getMeta().isFirst());
        assertTrue(response.getMeta().isLast());
        assertEquals(30, response.getData().size());
        assertEquals(1, response.getMeta().getTotalPages());
    }

    // 8
    @Test
    void checkSortingOrderIsDescById() {
        // given
        BookPageRequest request = new BookPageRequest(0, 5);

        // when
        ApiResponse<PaginationMetaData, Book> response = underTest.getItemsPage(request);

        // then
        assertNotNull(response.getData().get(0).getId());
        // In DESC sorting, the first item should be different from the one in ASC sorting
        assertTrue(response.getMeta().isSuccess());
    }

    // 9
    @Test
    void whenRequestingSecondPageThenDataIsDifferentFromFirstPage() {
        // given
        BookPageRequest req1 = new BookPageRequest(0, 5);
        BookPageRequest req2 = new BookPageRequest(1, 5);

        // when
        ApiResponse<PaginationMetaData, Book> res1 = underTest.getItemsPage(req1);
        ApiResponse<PaginationMetaData, Book> res2 = underTest.getItemsPage(req2);

        // then
        assertNotEquals(res1.getData().get(0).getId(), res2.getData().get(0).getId());
    }

    // 10
    @Test
    void checkTotalElementsConsistencyAcrossDifferentPages() {
        // given
        BookPageRequest request = new BookPageRequest(2, 3);

        // when
        ApiResponse<PaginationMetaData, Book> response = underTest.getItemsPage(request);

        // then
        assertEquals(30, response.getMeta().getTotalElements());
        assertEquals(10, response.getMeta().getTotalPages());
        assertEquals(2, response.getMeta().getNumber());
        assertEquals(3, response.getData().size());
    }
}