package com.stf.service;

import com.stf.domain.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface BookService {
    public List<Book> findAll();

    public Page<Book> findAllByPage(Pageable pageable);

    public Book save(Book book);

    public Optional<Book> findOne(long id);

    public void deleteById(long id);

    public List<Book> findByAuthor(String author);

    public List<Book> findByAuthorAndStatus(String author, int status);

    public List<Book> findBooksByDescriptionEndingWith(String description);

    public List<Book> findBooksByDescriptionContaining(String description);

    public List<Book> findBooksByBooksNameLength(int length);

    public int updateBookStatusById(int status, long id);

    public int deleteBookById(long id);

    public int deleteAndUpdate(long id, int status, long uId);
}
