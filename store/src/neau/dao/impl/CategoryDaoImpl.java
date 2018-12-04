package neau.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import neau.dao.CategoryDao;
import neau.domain.Category;
import neau.utils.DataSourceUtils;

public class CategoryDaoImpl implements CategoryDao {



	@Override
	public List<Category> findAll() throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select *from category";
//执行查询代码，返回值是list
		return qr.query(sql, new BeanListHandler<>(Category.class));
	
	}
//增加分类
	@Override
	public void add(Category category) throws Exception {
		QueryRunner qr=new QueryRunner();
		String sql="insert into category values(?,?)";
		qr.update(DataSourceUtils.getConnection(), sql, category.getCid(),category.getCname());
		
	}
	//通过商品id得到该商品的分类id(由于数据库中与domain的属性关于商品分类方向没有完全匹配（一个是对象，一个是存的id所以需要额外的进行查询然后进行封装)
	@Override
	public String getCid(String pid) throws Exception {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select cid from product where pid= ? ";
		return (String) qr.query(sql, new ScalarHandler(),pid);
		
	}
	

}
