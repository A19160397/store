package neau.service;

import java.util.List;

import neau.domain.Category;

public interface CategoryService {
 String findAll()throws Exception;

List<Category> findList()throws Exception;

void add(Category category)throws Exception;

String getCid(String pid) throws Exception;




}
