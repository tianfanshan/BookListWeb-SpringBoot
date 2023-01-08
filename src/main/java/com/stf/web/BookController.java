package com.stf.web;

import com.stf.domain.Book;
import com.stf.service.BookServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookServiceImp bookService;

    /**
     * 获取书单列表
     * 使用PageableDefault方法来得到分页查询的效果
     * @param model
     * @return
     */
    @GetMapping("/books")
    public String list(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {
        Page<Book> page1 = bookService.findAllByPage(pageable);
        model.addAttribute("page", page1);
        return "books";
    }

    /**
     * 获取书单详情
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/books/{id}")
    public String detail(@PathVariable long id, Model model) {
        Optional<Book> book = bookService.findOne(id);
        model.addAttribute("book", book);
        return "book";
    }

    /**
     * 跳转到input提交页面
     *
     * @return
     */
    @GetMapping("/books/input")
    public String inputPage(Model model) {
        model.addAttribute("book", Optional.of(new Book()));
        return "input";
    }

    /**
     * 跳转到更新页面input
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/books/{id}/input")
    public String editBook(@PathVariable long id, Model model) {
        Optional<Book> book = bookService.findOne(id);
        model.addAttribute("book", book);
        return "input";
    }

    /**
     * 提交一个书单信息后跳转到books
     *
     * @param book
     * @return
     */
    @PostMapping("/books")
    public String post(Book book, final RedirectAttributes attributes) {
        Book book1 = bookService.save(book);
        if (book1 != null) {
            attributes.addFlashAttribute("message", "《" + book1.getName() + "》信息提交成功!");
        }
        return "redirect:/books";//通过使用redirect使用当前第27行的方法跳转回books，并重新加载全部书单
    }
    /**
     * POST ----> REDIRECT ----> GET
     * model只能使用在当前页面，redirect到其他页面后当前保存在attribute中的值也就无法被使用了
     * POST ----> RedirectAttributes保存message ----> REDIRECT ----> GET ----> 使用用存在RedirectAttributes中的message
     * 这里我们使用RedirectAttributes保存书名，这样在redirect到books页面后也能访问到message
     * **/

    @GetMapping("/books/{id}/delete")
    public String delete(@PathVariable long id, final RedirectAttributes attributes){
        bookService.deleteBookById(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/books";
    }
}
