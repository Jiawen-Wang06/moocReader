package per.mooc.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import per.mooc.reader.entity.Book;

import java.util.Map;

/**
 * @author Jiawen
 */
public interface BookService {
    /**
     * 分页查询图书
     * @param categoryId which category the book belongs to
     * @param order sort method
     * @param page which page
     * @param rows how many records should be listed in one page
     * @return
     */
    public IPage<Book> selectPage(Long categoryId, String order, Integer page, Integer rows);
    public Book selectById(Long bookId);
    public void updateScore();
    public IPage<Map> selectBookMap(Integer page, Integer rows);
    public Book createBook(Book book);
    public Book updateBook(Book book);
    public void deleteBook(Long bookId);
}