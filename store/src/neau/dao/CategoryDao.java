package neau.dao;



import java.util.List;

import neau.domain.Category;

public interface CategoryDao {
	List<Category> findAll()throws Exception;

	void add(Category category)throws Exception;

	
	String getCid(String pid)throws Exception;

}
