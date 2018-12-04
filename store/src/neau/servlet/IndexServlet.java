package neau.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neau.domain.Product;
import neau.service.CategoryService;
import neau.service.ProductService;
import neau.service.impl.ProductServiceImpl;
import neau.utils.BeanFactory;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ProductService ps=new ProductServiceImpl();
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
		try {
			
			List<Product> hotList=ps.findHot();
		//	System.out.println("hotList");
		//	System.out.println(hotList);
			List<Product> newList=ps.findNew();
		//	System.out.println("newList");
		//	System.out.println(newList);
			request.setAttribute("hotList", hotList);
			request.setAttribute("newList", newList);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/jsp/index.jsp";	
	}
}