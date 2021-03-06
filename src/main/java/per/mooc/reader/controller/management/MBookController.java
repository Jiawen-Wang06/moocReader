package per.mooc.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import per.mooc.reader.entity.Book;
import per.mooc.reader.service.BookService;
import per.mooc.reader.utils.ResponseUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/management/book")
public class MBookController {
    @Resource
    private BookService bookService;
    @GetMapping("/list")
    public ResponseUtils list(Integer page, Integer rows){
        ResponseUtils resp = null;
        page = page == null?1:page;
        rows = rows == null?10:rows;
        try {
            IPage p = bookService.selectBookMap(page,rows);
            resp = new ResponseUtils().put("list",p.getRecords()).put("count",p.getTotal());

        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        return resp;
    }
    @PostMapping("/upload")
    public Map upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException {
        String uploadPath = request.getServletContext().getResource("/").getPath() + "/upload/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = sdf.format(new Date());
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        file.transferTo(new File(uploadPath+fileName+suffix));
        Map result = new LinkedHashMap();
        result.put("errno",0);
        result.put("data",new String[]{"/upload/"+fileName+suffix});
        return result;
    }

    @PostMapping("/create")
    public ResponseUtils createBook(Book book){
        ResponseUtils resp = null;
        try {
            System.out.println(book);
            Document doc = Jsoup.parse(book.getDescription());
            Elements elements = doc.select("img");
            if(elements.size()==0){
                resp = new ResponseUtils("ImageNotFound","No image found in the description");
            }else{
                String cover = elements.first().attr("src");
                book.setCover(cover);
                book.setEvaluationScore(0f);
                book.setEvaluationQuantity(0);
                Book b =  bookService.createBook(book);
                resp = new ResponseUtils().put("book",b);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        return resp;
    }

    @PostMapping("/update")
    public ResponseUtils updateBook(Book book){
        ResponseUtils resp = null;
        try {
            Book newB = bookService.selectById(book.getBookId());
            newB.setBookName(book.getBookName());
            newB.setSubTitle(book.getSubTitle());
            newB.setAuthor(book.getAuthor());
            newB.setCategoryId(book.getCategoryId());
            newB.setDescription(book.getDescription());
            newB.setCover(book.getCover());
            bookService.updateBook(newB);
            resp = new ResponseUtils().put("book",newB);
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        return resp;
    }

    @PostMapping("/delete")
    public ResponseUtils deleteBook(Long bookId){
        ResponseUtils resp = null;
        try{
            bookService.deleteBook(bookId);
            resp = new ResponseUtils();
        }catch (Exception e){
            e.printStackTrace();
            resp = new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        return resp;
    }
}