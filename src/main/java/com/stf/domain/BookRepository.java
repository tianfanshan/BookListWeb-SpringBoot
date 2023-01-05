package com.stf.domain;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findAll(Pageable pageable);

    List<Book> findBooksByAuthor(String author);

    List<Book> findBooksByAuthorAndStatus(String author, int status);

    List<Book> findBooksByDescriptionEndingWithIgnoreCase(String description);

    List<Book> findBooksByDescriptionContaining(String description);

    @Query("SELECT b FROM Book b WHERE LENGTH(b.name) > ?1")
//    @Query(value = "SELECT * FROM book WHERE LENGTH(name) > ?1",nativeQuery = true)
    List<Book> findBooksByBooksNameLength(int length);

    @Transactional
    @Modifying
    @Query("UPDATE Book b SET b.status = ?1 WHERE b.id = ?2")
    int updateBookStatusById(int status,long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Book b WHERE b.id = ?1")
    int deleteBookById(long id);
}
