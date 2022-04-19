package per.mooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import per.mooc.reader.entity.Book;
import per.mooc.reader.mapper.BookMapper;
import per.mooc.reader.mapper.EvaluationMapper;
import per.mooc.reader.mapper.MemberReadstateMapper;
import per.mooc.reader.service.BookService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Jiawen
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberReadstateMapper memberReadstateMapper;
    @Override
    public IPage<Book> selectPage(Long categoryId, String order, Integer page, Integer rows) {
        IPage<Book> p = new Page<>(page, rows);
        //Create query accordingly
        QueryWrapper wrapper = new QueryWrapper();
        if(categoryId != null && categoryId != -1){
            wrapper.eq("category_id",categoryId);
        }
        if(order != null){
            if(order.equals("quantity")){
                wrapper.orderByDesc("evaluation_quantity");
            }else if(order.equals("score")){
                wrapper.orderByDesc("evaluation_score");
            }
        }else{
            wrapper.orderByDesc("evaluation_quantity");
        }
        p = bookMapper.selectPage(p,wrapper);
        return p;
    }

    @Override
    public Book selectById(Long BookId){
        Book bk = bookMapper.selectById(BookId);
       return bk;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateScore() {
        bookMapper.updateScore();
    }
    @Override
    public IPage<Map> selectBookMap(Integer page, Integer rows) {
        IPage p = new Page(page,rows);
        p = bookMapper.selectBookMap(p);
        return p;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book;

    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBook(Long bookId) {
        bookMapper.deleteById(bookId);

        QueryWrapper wrapper1 = new QueryWrapper();
        wrapper1.eq("book_id",bookId);

        //为什么这里不再用deleteById, 而是用delete呢？
        evaluationMapper.delete(wrapper1);

        QueryWrapper wrapper2 = new QueryWrapper();
        wrapper2.eq("book_id",bookId);

        //为嘛不直接使用wrapper1，还重新又创建了一个wrapper2呢，条件不是都是bookId吗？
        memberReadstateMapper.delete(wrapper2);




    }
}