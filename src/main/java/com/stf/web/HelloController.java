package com.stf.web;

import com.stf.domain.Book;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v2")
public class HelloController {

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello Spring Boot";
    }

    @GetMapping("/books")
    public Object getAll(@RequestParam("page") int page, @RequestParam(value = "size",defaultValue = "10") int size){
        Map<String , Object> book = new HashMap<>();
        book.put("name","互联网世界观");
        book.put("isbn","98239487");
        book.put("author","李善友");
        Map<String , Object> book2 = new HashMap<>();
        book2.put("name","程序员的思维修炼");
        book2.put("isbn","9821237");
        book2.put("author","aui");

        List<Map<String,Object>> content = new ArrayList<>();
        content.add(book);
        content.add(book2);

        Map<String,Object> pageMap = new HashMap<>();
        pageMap.put("page",page);
        pageMap.put("size",size);
        pageMap.put("content",content);
        return pageMap;
    }

    /**
     * 正则表达式：{参数名:正则表达式}
     * @param id
     * @param name
     * @return
     */
    @GetMapping(value = "/books/{id}/{username:[a-z_]+}")
    public Object getById(@PathVariable("id") String id,@PathVariable("username") String name){


        Map<String , Object> book = new HashMap<>();
        book.put("name","互联网世界观");
        book.put("isbn","98239487");
        book.put("author","李善友");
        book.put("username",name);
        book.put("id",id);
        return book;
    }

    @GetMapping(value = "/books/{id}")
    public Object getById2(@PathVariable("id") String id){
        Book book = new Book();
        book.setName("互联网世界观");
        book.setAuthor("李善友");
        book.setDescription("互联网世界观,这本书还不错");
        return book;
    }


    @PostMapping("/books")
    public Object post(@RequestParam("name") String name,
                       @RequestParam("author") String author,
                       @RequestParam("isbn") String isbn){
        Map<String,Object> book = new HashMap<>();
        book.put("name",name);
        book.put("author",author);
        book.put("isbn",isbn);
        return book;
    }
}
