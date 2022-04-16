package per.mooc.reader.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import per.mooc.reader.entity.TestTable;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestMapperTestor {
    @Resource
    private TestMapper testMapper;
    @Test //Mybatis way
    public void insertSample() {
        testMapper.insertSample();
    }
    @Test //Mybatis-plus way
    public void testInsert(){
        TestTable test = new TestTable();
        test.setContent("TestMybatisPlus");
        testMapper.insert(test);
    }
    @Test
    public void testUpdate(){
        TestTable test = new TestTable();
        test.setId(1);
        test.setContent("TestMybatisPlusOnUpdate");
        testMapper.updateById(test);
    }

    @Test
    public void testDelete(){
        testMapper.deleteById(3); //因为TestMapper继承了BaseMapper<TestTable>，所以知道了是删除哪个table里的内容
    }
    @Test
    public void testSelectById(){
        TestTable test = testMapper.selectById(2);
        System.out.println(test.getContent());

    }
    @Test
    public void testSelectByList(){
        QueryWrapper query = new QueryWrapper();
        query.gt("id",2);
        List list = testMapper.selectList(query);

    }

    @Test //分页
    public void testPagination(){
        IPage page = new Page(1,2);
        QueryWrapper query = new QueryWrapper();
        query.gt("id",2);
        page = testMapper.selectPage(page,query);
        System.out.println("Total pages:" + page.getPages());
        System.out.println("Total records:" + page.getTotal());
        System.out.println("Current record:"+page.getRecords());


    }


}