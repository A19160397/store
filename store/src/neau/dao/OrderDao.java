package neau.dao;

import java.util.List;

import neau.domain.Order;
import neau.domain.OrderItem;
import neau.domain.PageBean;

public interface OrderDao {

	void save(Order order)throws Exception;

	void saveItem(OrderItem oi)throws Exception;

	int getTotalRecord(String uid)throws Exception;

	List<Order> findMyOrderByPage(PageBean<Order> bean, String uid) throws Exception;

	Order getById(String oid)throws Exception;

	void update(Order order)throws Exception;

	void delete(String oid)throws Exception;
//管理员查询订单
	List<Order> findAllByState(String state)throws Exception;

}
