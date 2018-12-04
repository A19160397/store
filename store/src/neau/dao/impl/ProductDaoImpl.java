package neau.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import neau.constant.Constant;
import neau.dao.ProductDao;
import neau.domain.PageBean;
import neau.domain.Product;
import neau.utils.DataSourceUtils;


public class ProductDaoImpl implements ProductDao{	
/**
 * 
 * 查询热门商品
 */
	@Override
	public List<Product> findHot() throws Exception {
	QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
	String sql="select * from product where is_hot = ? and pflag = ? order by pdate desc limit 9";
	//System.out.println("ProductDao::findHot"+sql);
	return qr.query(sql, new BeanListHandler<>(Product.class), Constant.PRODUCT_IS_HOT,Constant.PRODUCT_IS_UP);
	
	}
	/**
	 * 查询最新商品
	 * 
	 */
	@Override
	public List<Product> findNew() throws Exception {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where pflag = ? order by pdate desc limit 9";
		//System.out.println("ProductDao::findNew"+sql);
		return qr.query(sql, new BeanListHandler<>(Product.class),Constant.PRODUCT_IS_UP);
	
	}
	/**
	 * 查询商品详情
	 * 
	 */
	@Override
	public Product getById(String id) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where pid = ? limit 1";
		return qr.query(sql, new BeanHandler<>(Product.class), id);
	
	}
	@Override
	/**
	 * 查询当前页数据
	 */
	public List<Product> findByPage(PageBean<Product> pb, String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid = ? and pflag = ? order by pdate desc limit ?,?";
		return qr.query(sql, new BeanListHandler<>(Product.class), cid,Constant.PRODUCT_IS_UP,pb.getStartIndex(),pb.getPageSize());
	}
	@Override
	/**
	 * 获取总记录数
	 */
	public int getTotalRecord(String cid) throws Exception {
		return ((Long)new QueryRunner(DataSourceUtils.getDataSource()).query("select count(*) from product where cid = ? and pflag = ?", new ScalarHandler(), cid,Constant.PRODUCT_IS_UP)).intValue();
	}
	/**
	 * 
	 * 管理员查询所有上架商品
	 */
	@Override
	public List<Product> findAll() throws Exception{
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pflag = ? order by pdate desc";
		return qr.query(sql, new BeanListHandler<>(Product.class), Constant.PRODUCT_IS_UP);
	
	}
	/**
	 * 
	 * 保存商品信息(新增商品)
	 */
	@Override
	public void save(Product p) throws Exception {
		QueryRunner qr=new QueryRunner();
		String sql="insert into product values (?,?,?,?,?,?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(),sql,p.getPid(),p.getPname(),p.getMarket_price(),p.getShop_price(),
				p.getPimage(),p.getPdate(),p.getIs_hot(),p.getPdesc(),p.getPflag(),p.getCategory().getCid());
		
	}
	/**
	 * 更新商品信息
	 * 可以不更新图片信息
	 */
	@Override
	public void update(Product p) throws Exception{
		System.out.println("数据库—管理员—商品更新任务执行开始");
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		System.out.println(p.getPname()+"**"+p.getMarket_price()+"##"+p.getShop_price()+"##"+p.getPimage()+"##"+p.getPdate()+"##"+p.getIs_hot()+"##"+p.getPdesc()+"##"+p.getPflag()+"##"+p.getCategory().getCid()+"##"+p.getPid());
		String sql="update product set pname=?,market_price = ?,shop_price =? ,pdate=?,is_hot =?,pdesc=?,pflag=?,cid=? where pid=?";
		qr.update(sql,p.getPname(),p.getMarket_price(),p.getShop_price(),p.getPdate(),p.getIs_hot(),p.getPdesc(),p.getPflag(),p.getCategory().getCid(),p.getPid());
		System.out.println("数据库—管理员—商品更新任务执行完毕");
	}
	
	
	
	
	/**
	 * 
	 * 删除商品信息
	 */
	@Override
	public void delete(String pid) throws Exception {
		QueryRunner qr=new QueryRunner();
		String sql = "DELETE FROM product WHERE pid=?";
		qr.update(DataSourceUtils.getConnection(),sql,pid);
		
	}
	@Override
	public void out(String pid) throws Exception {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		
				String sql="update product set pflag=? where pid=?";
				qr.update(sql,Constant.PRODUCT_IS_DOWN,pid);
		
	}
}
