package neau.service;

import java.util.List;

import neau.domain.Order;
import neau.domain.PageBean;

public interface OrderService {

	void save(Order order)throws Exception;

	PageBean<Order> findMyOrderByPage(int pageNumber, int pageSize, String uid)throws Exception;

	Order getById(String oid)throws Exception;

	void update(Order order)throws Exception;

	void delete(String oid)throws Exception;

	List<Order> findAllBystate(String state)throws Exception;
	

}
