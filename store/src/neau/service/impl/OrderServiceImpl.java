package neau.service.impl;

import java.sql.SQLException;
import java.util.List;

import neau.dao.OrderDao;
import neau.domain.Order;
import neau.domain.OrderItem;
import neau.domain.PageBean;
import neau.service.OrderService;
import neau.utils.BeanFactory;
import neau.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {

	/**
	 * 
	 * 保存订单
	 * 订单信息保存在数据库中
	 * 订单中的所有购物项保存在数据库中
	 */
	@Override
	public void save(Order order) throws Exception{
		try {
			OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
			DataSourceUtils.startTransaction();
			//像订单表中插入一条订单数据
			od.save(order);
			//向orderitem表中依次插入订单项
			for(OrderItem oi:order.getItems())
			{
				od.saveItem(oi);
			}
			DataSourceUtils.commitAndClose();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}
/**
 * 
 * 我的订单
 */
	@Override
	public PageBean<Order> findMyOrderByPage(int pageNumber, int pageSize, String uid) throws Exception {
		OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
		//1.创建pageBean(页面编号，页面存数据大小，数据总条数)
		PageBean<Order>bean=new PageBean<>(pageNumber,pageSize);
		//2.查询页面总条数，封装进pageBean(分页显示的对象)
		int totalRecord=od.getTotalRecord(uid);
		bean.setTotalRecord(totalRecord);
		//System.out.println("总条数："+totalRecord);
		//3.分页显示的数据
		List<Order> data = od.findMyOrderByPage(bean,uid);
		bean.setData(data);
		return bean;
		
	}
	/**
	 * 
	 * 显示订单详情
	 */
	@Override
	public Order getById(String oid) throws Exception {
		OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
		Order order=od.getById(oid);
		return order;
	}
	/**
	 * 
	 * 订单信息更新
	 */
	@Override
	public void update(Order order) throws Exception {
		OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
		od.update(order);
	}
	/**
	 * 删除订单
	 */
	@Override
	public void delete(String oid) throws Exception {
		OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
		od.delete(oid);
		
		
	}
	/**
	 * 管理员：：通过订单状态查询订单
	 * 
	 */
	@Override
	public List<Order> findAllBystate(String state) throws Exception {
		// TODO Auto-generated method stub
		OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
		return od.findAllByState(state);
		
	}

}
