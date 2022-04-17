package per.mooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import per.mooc.reader.entity.Book;
import per.mooc.reader.mapper.BookMapper;
import per.mooc.reader.service.BookService;

import javax.annotation.Resource;

/**
 * @author Jiawen
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
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
}