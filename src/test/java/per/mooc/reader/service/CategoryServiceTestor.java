package per.mooc.reader.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import per.mooc.reader.entity.Category;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CategoryServiceTestor {
    @Resource
    private CategoryService categoryService;
    @Test
    public void selectAll() {
        List<Category> list =  categoryService.selectAll();
        for(Category c:list){
            System.out.println(c);
        }
    }
}