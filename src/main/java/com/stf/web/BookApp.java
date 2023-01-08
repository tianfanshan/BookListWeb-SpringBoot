package com.stf.web;

import com.stf.domain.Book;
import com.stf.service.BookServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BookApp {

    @Autowired
    private BookServiceImp bookService;

//    /**
//     * 获取读书清单列表
//     * @return
//     */
//    @GetMapping("/books")
//    public List<Book> getAll(){
//
//        return bookService.findAll();
//    }

    /**
     * 分页查询书单列表
     * 使用springboot提供的PageableDefault方法来得到分页查询的效果
     * @return
     */
    @GetMapping("/books")
    public Page<Book> getAll(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable){
        return bookService.findAllByPage(pageable);
    }

    /**
     * 新增一个书单
     * @return
     */
    @PostMapping("/books")
    public Book create(Book book){
        return bookService.save(book);
    }

    /**
     * 获取一条书单
     * @param id
     * @return
     */
    @GetMapping("/books/{id}")
    public Optional<Book> getById(@PathVariable long id){
        return bookService.findOne(id);
    }

    /**
     * 更新一个书单
     * @param id
     * @param name
     * @param author
     * @param description
     * @param status
     * @return
     */
    @PutMapping("/books/{id}")
    public Book update(@PathVariable long id,
                       @RequestParam String name,
                       @RequestParam String author,
                       @RequestParam String description,
                       @RequestParam int status){

        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        book.setDescription(description);
        book.setStatus(status);

        return bookService.save(book);
    }

    /**
     * 删除一条书单
     * @param id
     */
    @DeleteMapping("/books/{id}")
    public void deleteById(@PathVariable long id){
        bookService.deleteById(id);
    }

    /**
     * 根据author查询一个书单
     * @param author
     * @return
     */
    @GetMapping("/books/byAuthor")
    public List<Book> findByAuthor(@RequestParam String author){
        return bookService.findByAuthor(author);
    }

    /**
     * 根据author和status查询一个书单
     * @param author
     * @param status
     * @return
     */
    @GetMapping("/books/byAuthorAndStatus")
    public List<Book> findByAuthorAndStatus(@RequestParam("author") String author,
                                            @RequestParam("status") int status){
        return bookService.findByAuthorAndStatus(author,status);
    }

    /**
     * 根据description结束的单词查询一个书单
     * @param description
     * @return
     */
    @GetMapping("/books/byDescriptionEndingWith")
    public List<Book> findBooksByDescriptionEndingWith(String description){
        return bookService.findBooksByDescriptionEndingWith(description);
    }

    /**
     * 根据description包含的单词查询一个书单
     * @param description
     * @return
     */
    @GetMapping("/books/byDescriptionContaining")
    public List<Book> findBooksByDescriptionContaining(@RequestParam String description){
        return bookService.findBooksByDescriptionContaining(description);
    }

    /**
     * 根据书本名字长度查询书单
     * @param length
     * @return
     */
    @GetMapping("/books/byBooksNameLength")
    public List<Book> findBooksByBooksNameLength(@RequestParam int length){
        return bookService.findBooksByBooksNameLength(length);
    }

    /**
     * 根据id更新书的status
     * @param status
     * @param id
     * @return
     */
    @PutMapping("/books/updateBookStatusById")
    public int updateBookStatusById(@RequestParam int status,@RequestParam long id){
        return bookService.updateBookStatusById(status,id);
    }

    /**
     * 根据书的id删除书
     * @param id
     * @return
     */
    @DeleteMapping("/books")
    public int deleteBookById(@RequestParam long id){
        return bookService.deleteBookById(id);
    }

    /**
     * 测试transactional，详情请看BookService
     * @param id
     * @param status
     * @param uId
     * @return
     */
    @PutMapping("/books/testTransactional")
    public int deleteAndUpdate(@RequestParam long id,
                               @RequestParam int status,
                               @RequestParam long uId){
        return bookService.deleteAndUpdate(id, status, uId);
    }

}
