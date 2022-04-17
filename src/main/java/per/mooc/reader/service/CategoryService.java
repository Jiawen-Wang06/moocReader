package per.mooc.reader.service;

import per.mooc.reader.entity.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> selectAll();
}