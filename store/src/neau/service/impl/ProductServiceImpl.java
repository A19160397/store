package neau.service.impl;

import java.util.List;

import neau.dao.ProductDao;
import neau.dao.impl.ProductDaoImpl;
import neau.domain.PageBean;
import neau.domain.Product;
import neau.service.ProductService;
import neau.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {

	/**
	 * 
	 * 查询热门商品
	 */
	@Override
	public List<Product> findHot() {
		
		//ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
		ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
		//System.out.println("Productservice::findHot");
		
		try {
			return pd.findHot();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
		
	}
/**
 * 
 * 查询最新商品
 */
	@Override
	public List<Product> findNew() throws Exception {
		
			ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
		//	System.out.println("Productservice::findNew");
			try {
				return pd.findNew();
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 return null;

	}
/**
 * 
 * 通过ID查商品详情
 * 
 */
	@Override
	public Product getById(String id) throws Exception {
		// TODO Auto-generated method stub
		ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
		
		return pd.getById(id);
	//	System.out.println(pd.getById(id));
		
	}
@Override
/**
 * 分类展示商品--分页展示
 * 
 */
public PageBean<Product> findByPage(int pageNumber, int pageSize, String cid) throws Exception {
	ProductDao pDao= (ProductDao) BeanFactory.getBean("ProductDao");
	//1.创建pagebean
	PageBean<Product> pb = new PageBean<>(pageNumber, pageSize);
	
	//2.设置当前页数据
	List<Product> data = pDao.findByPage(pb,cid);
	pb.setData(data);
	
	//3.设置总记录数
	int totalRecord = pDao.getTotalRecord(cid);
	pb.setTotalRecord(totalRecord);
	
	return pb;
}
/**
 * 
 * 管理员查询::查询所有商品
 */
@Override
public List<Product> findAll() throws Exception {
	ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
	return pd.findAll();
	
}

/**
 * 新建保存商品
 */
@Override
public void save(Product p) throws Exception {
	ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
	pd.save(p);
	
	}
/**
 * 
 * 更新商品信息
 */
@Override
public void update(Product p) throws Exception {
	
	ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
	pd.update(p);
}
/**
 * 
 * 删除商品
 */
@Override
public void delete(String pid) throws Exception {
	ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
	pd.delete(pid);
	
}
@Override
public void out(String pid) throws Exception {
	ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
	pd.out(pid);
	
}
}

