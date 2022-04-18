package per.mooc.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import per.mooc.reader.entity.Book;

import java.util.Map;

public interface BookMapper extends BaseMapper<Book> {
    public void updateScore();
    public IPage<Map> selectBookMap(IPage page);//Mybatis requires to have a function declaration that take an IPage object and return an IPage object. Only this way, it will perform pagination
}