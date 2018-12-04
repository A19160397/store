package neau.dao;

import java.util.Date;
import java.util.List;

import neau.domain.Category;
import neau.domain.PageBean;
import neau.domain.Product;

public interface ProductDao {

	

	List<Product> findHot()throws Exception;
	
	List<Product> findNew()throws Exception;
	
	Product getById(String id)throws Exception;

	List<Product> findByPage(PageBean<Product> pb, String cid)throws Exception;

	int getTotalRecord(String cid)throws Exception;
//管理员查询功能
	List<Product> findAll()throws Exception;
//保存商品信息
	void save(Product p)throws Exception;

	void update(Product p)throws Exception;

	void delete(String pid)throws Exception;
//删除商品
	void out(String pid)throws Exception;
	
	
	

}
