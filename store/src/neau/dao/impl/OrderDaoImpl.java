package neau.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import neau.dao.OrderDao;
import neau.domain.Order;
import neau.domain.OrderItem;
import neau.domain.PageBean;
import neau.domain.Product;
import neau.utils.DataSourceUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	/**
	 * 保存订单到数据库中
	 * 
	 */
	public void save(Order or) throws Exception {
		QueryRunner qr=new QueryRunner();
		String sql="insert into orders values(?,?,?,?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(), sql, or.getOid(),or.getOrdertime(),or.getTotal(),
				or.getState(),or.getAddress(),or.getName(),
				or.getTelephone(),or.getUser().getUid());
		
	}

	/**
	 * 
	 * 保存订单中的订单项到数据库中
	 */
	@Override
	public void saveItem(OrderItem oi) throws Exception {
		QueryRunner qr=new QueryRunner();
		String sql="insert into orderitem values(?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(), sql, oi.getItemid(),oi.getCount(),oi.getSum(),
				oi.getProduct().getPid(),oi.getOrder().getOid());
		
	}
	/**
	 * 通过id查询订单总数量
	 * 
	 * */
	@Override
	public int getTotalRecord(String uid) throws Exception {
		//System.out.println("查询总条数");
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from orders where uid = ?";
		return ((Long) qr.query(sql, new ScalarHandler(),uid)).intValue();

	}
/**
 * 
 * 我的订单页面
 * 获取当前页面数据
 */
	@Override
	public List<Order> findMyOrderByPage(PageBean<Order> bean, String uid) throws Exception {
		
		//查询订单数据（订单列表的信息以及每个订单中的订单项查询出来放在订单对象的订单项数组中，并且与pagebean结合(决定查询的起始页面结束条数)）
		//1.查询需要页面的所有订单信息，（与bean结合）bean中的信息决定查询条数，起始位置
	
		QueryRunner  qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from orders where uid = ? order by ordertime desc limit ?,?";
		List<Order>list=qr.query(sql, new BeanListHandler<>(Order.class),uid,bean.getStartIndex(),bean.getPageSize());
		
		//2.得到订单列表后开始遍历，每一个订单都要查询其订单项（因为查询出来的存在数据库中的订单其没有订单项列表，要在数据库中根据订单id，查其订单项封装金订单中，然后再显示）
		//可以直接显示么？？？可以尝试一下！！！
		
		for(Order order:list)
		{
			//查询了订单项以及其中的商品对象
			sql="SELECT * FROM orderitem oi,product p WHERE oi.pid = p.pid AND oi.oid = ?";
			//查询出的结果（多个订单项）放在map集合中，然后通过map集合进行封装
			List<Map<String,Object>> maplist= qr.query(sql, new MapListHandler(), order.getOid());
			
			//System.out.println(maplist);
			//全部信息以map数组对应方式保存在map集合里（orderitem与product的全部信息）
			 //依次封装成orderitem，再放入order中
			 for(Map<String,Object>map:maplist)
			 {
				
				 OrderItem oi=new OrderItem();
				 BeanUtils.populate(oi,map);
				 Product p=new Product();
				 BeanUtils.populate(p, map);
				 oi.setProduct(p);
				 //封装完毕后放入集合中
				 //此处的封装不包含所属对象即商品对象中包含的分类属性以及orderitem对象中所包含的order与product属性，但是刚好，封装好的product放在oi中了，且orderitem放在order中了因此只差product中的分类属性没有封装
				 //同时前台也不现实其分类，如果需要封装则需要，增加封装分类的方法，所以在定义对象是，未必一定需要其属性为对象，也可以直接是id，与数据库中的属性定义一直，但是也可能会存在·访问一些数据不方便的问题
				 order.getItems().add(oi); 
			 }
		}
		System.out.println("OrderDao::查询订单信息列表预成功!");
		//返回查询完的列表
		return list;
	}
	/**
	 * 定单页面详情
	 * 通过orderid查询
	 * 
	 */
@Override
public Order getById(String oid) throws Exception {
	QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
	//查询订单基本信息 总金额 日期 状态 id
	//得到order
	String sql ="select * from orders where oid = ?";
	Order order = qr.query(sql, new BeanHandler<>(Order.class), oid);
	
	/*查询此订单的所有订单项以及相应的商品项数据，放在map集合里，方便进行订单项封装与订单列表不同的是，
	我们此次查询只封装一个订单，显示一个订单的订单项列表，且显示的内容具有一些细微的差距，并且发送的页面不同
	但是封装order对象的过程是相同的
	查询了订单项以及其中的商品对象，所以代码复制了上述的
	*/
	sql="SELECT * FROM orderitem oi,product p WHERE oi.pid = p.pid AND oi.oid = ?";
	//查询出的结果放在map集合中，然后通过map集合进行封装
	List<Map<String,Object>> maplist= qr.query(sql, new MapListHandler(), order.getOid());
	 //依次封装成orderitem，再放入order中（完善order）
	 for(Map<String,Object>map:maplist)
	 {
		
		 OrderItem oi=new OrderItem();
		 BeanUtils.populate(oi,map);
		 Product p=new Product();
		 BeanUtils.populate(p, map);
		 oi.setProduct(p);
		 //封装完毕后放入集合中
		 order.getItems().add(oi); 
	 }
	//封装完毕订单项（以及每个订单项中包含的商品属性）放入到此订单中的订单项数组items中
	return order;
}
/**
 * 
 * 更新订单（通过传入的参数得到订单id，通过id在数据库中查询需要更新的订单，进行更新）
 */
	@Override
	public void update(Order order) throws Exception {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql="update orders set state = ?,ordertime=?,address = ?,name =?,telephone = ? where oid = ?";
		qr.update(sql,order.getState(),order.getOrdertime(),order.getAddress(),order.getName(),
				order.getTelephone(),order.getOid());
		
	}
/**
 * 
 * 删除订单
 */
@Override
public void delete(String oid) throws Exception {
	//连接外键！！删除语句出错
	QueryRunner qr=new QueryRunner();
	String sql = "DELETE FROM orderitem WHERE oid=?";
	qr.update(DataSourceUtils.getConnection(),sql,oid);
	 sql = "DELETE FROM orders WHERE oid=?";
	 qr.update(DataSourceUtils.getConnection(),sql,oid);
	
System.out.println("删除完毕");
	
	
}

@Override
/**
 * 
 * 管理员按类别进行订单查询
 */
public List<Order> findAllByState(String state) throws Exception {
	List<Order> list;
	QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
	//设置查询语句
	String sql="select * from orders";
	if(state==null||state.trim().length()==0)
	{
		//如果state为空 查询全部
		return qr.query(sql, new BeanListHandler<>(Order.class));
		
	}
	sql+=" where state = ? order by ordertime desc";
	return qr.query(sql, new BeanListHandler<>(Order.class),state);
	}
	
}
