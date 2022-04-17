package per.mooc.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.mooc.reader.entity.Book;
import per.mooc.reader.service.BookService;
import per.mooc.reader.utils.ResponseUtils;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/book")
public class BookController {
    @Resource
    private BookService bookService;
    @GetMapping("/list")
    public ResponseUtils list(Long categoryId, String order, Integer page){
        ResponseUtils resp = null;
        try{
            IPage<Book> p = bookService.selectPage(categoryId,order,page,10);
            resp = new ResponseUtils().put("page",p);
        }catch (Exception e){
            e.printStackTrace();
            resp = new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        return resp;
    }
    @GetMapping("/id/{id}")
    public ResponseUtils selectByid(@PathVariable("id") Long BookId){
        ResponseUtils resp = null;
        try{

            Book book = bookService.selectById(BookId);
            resp = new ResponseUtils().put("book",book);
        }catch (Exception e){
            e.printStackTrace();
            resp = new ResponseUtils().put(e.getClass().getSimpleName(),e.getMessage());
        }
        return resp;
    }

}