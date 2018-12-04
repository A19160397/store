package neau.service;

import java.util.List;

import neau.domain.PageBean;
import neau.domain.Product;

public interface ProductService {

	List<Product> findHot()throws Exception;

	List<Product> findNew()throws Exception;

	Product getById(String id)throws Exception;

	PageBean<Product> findByPage(int pageNumber, int pageSize, String cid)throws Exception;
//以下是管理员功能
	List<Product> findAll()throws Exception;

	void save(Product p)throws Exception;

	void update(Product p)throws Exception;

	void delete(String pid)throws Exception;

	void out(String pid)throws Exception;


}
