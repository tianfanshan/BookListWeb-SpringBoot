package com.stf.service;

import com.stf.domain.Book;
import com.stf.domain.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImp implements BookService{
    @Autowired
    private BookRepository bookRepository;

    /**
     * 查询所有的书单列表
     * @return
     */
    @Override
    public List<Book> findAll(){
        return bookRepository.findAll();
    }


    /**
     * 分页查询书单列表
     * @return
     */
    @Override
    public Page<Book> findAllByPage(Pageable pageable){
        return bookRepository.findAll(pageable);
    }

    /**
     * 提交一个书单信息
     * @param book
     * @return
     */
    @Override
    public Book save(Book book){
        return bookRepository.save(book);
    }

    /**
     * 获取一条书单
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Book> findOne(long id){

        return bookRepository.findById(id);
    }

    /**
     * 删除一条书单
     * @param id
     */
    @Override
    public void deleteById(long id){
        bookRepository.deleteAllById(Collections.singleton(id));
    }

    /**
     * 根据author查询一个书单列表
     * @param author
     * @return
     */
    @Override
    public List<Book> findByAuthor(String author){
        return bookRepository.findBooksByAuthor(author);
    }

    /**
     * 根据author和status查询一个书单列表
     * @param author
     * @param status
     * @return
     */
    @Override
    public List<Book> findByAuthorAndStatus(String author,int status){
        return bookRepository.findBooksByAuthorAndStatus(author,status);
    }

    /**
     * 根据description的结尾查询书单
     * @param description
     * @return
     */
    @Override
    public List<Book> findBooksByDescriptionEndingWith(String description){
        return bookRepository.findBooksByDescriptionEndingWithIgnoreCase(description);
    }

    /**
     * 根据description中包含的单词查询书单
     * @param description
     * @return
     */
    @Override
    public List<Book> findBooksByDescriptionContaining(String description){
        return bookRepository.findBooksByDescriptionContaining(description);
    }

    /**
     * 根据书本名字的长度查询书单
     * @param length
     * @return
     */
    @Override
    public List<Book> findBooksByBooksNameLength(int length){
       return bookRepository.findBooksByBooksNameLength(length);
    }

    /**
     * 根据id更新书的status
     * @param status
     * @param id
     * @return
     */
    @Override
    public int updateBookStatusById(int status,long id){
        return bookRepository.updateBookStatusById(status,id);
    }

    /**
     * 根据id删除书(sql语句)
     * @param id
     * @return
     */
    @Override
    public int deleteBookById(long id){
        return bookRepository.deleteBookById(id);
    }

    /**
     * 测试transactional事务管理
     * 添加transactional注释，任何一条数据库访问出现问题(抛出异常)，就会滚回执行前的数据库状态(这里是返回删除之前，或更新之前)
     * 总而言之就是如果执行过程中出现异常，已经执行过的任务会返回到执行前的版本状态
     * @param id
     * @param status
     * @param uId
     * @return
     */
    @Override
    @Transactional
    public int deleteAndUpdate(long id,int status,long uId){
        int deleteCount = bookRepository.deleteBookById(id);
        int updateCount = bookRepository.updateBookStatusById(status, uId);
        return deleteCount + updateCount;
    }

}
