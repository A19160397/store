package neau.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neau.domain.PageBean;
import neau.domain.Product;
import neau.service.ProductService;
import neau.service.impl.ProductServiceImpl;
import neau.utils.BeanFactory;

public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	

/**
 * 
 * 商品详情
 * Servlet implementation class ProductServlet
 */	
public String getById(HttpServletRequest request,HttpServletResponse response) {
	try {
		String id=request.getParameter("pid");
		ProductService ps=new ProductServiceImpl();
		
		Product pd=ps.getById(id);
		request.setAttribute("pro", pd);
		} catch (Exception e) {
		request.setAttribute("msg", "查询单个商品失败！！！");
		
		//e.printStackTrace();
		return "/jsp/msg.jsp";
		}
	
	return "/jsp/product_info.jsp";
	}
/*
 * 
 * 分类商品展示--分页
 */
public String findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try {
		//1.获取pagenumber cid  设置pagesize
		/*String parameter = request.getParameter("pageNumber");*/
		int pageNumber = 1;
		
		try {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		} catch (NumberFormatException e) {
		}
		
		int pageSize = 12;
		String cid = request.getParameter("cid");
		
		//2.调用service 分页查询商品 参数:3个, 返回值:pagebean
		ProductService ps = new ProductServiceImpl();
		PageBean<Product> bean=ps.findByPage(pageNumber,pageSize,cid);
		
		//3.将pagebean放入request中,请求转发 product_list.jsp
		request.setAttribute("pb", bean);
	} catch (Exception e) {
		request.setAttribute("msg", "分页查询失败");
		return "/jsp/msg.jsp";
	}
	return "/jsp/product_list.jsp";
}


}
