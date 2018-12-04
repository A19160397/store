package neau.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neau.domain.Cart;
import neau.domain.CartItem;
import neau.domain.Product;
import neau.service.ProductService;
import neau.service.UserService;
import neau.service.impl.ProductServiceImpl;
import neau.utils.BeanFactory;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

   /**
    * 
    * @param request
    * @param response
    * @return
    * @throws ServletException
    * @throws IOException
    */
	public String add2cart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		 /* 加入购物车
		    * 1.获取数量和pid
		    * 2.根据pid获取商品对象
		    * 3.商品对象和数量封装成CartItem
		    * 3.得到本用户购物车对象，调用购物车对象的增加物品函数（购物车应该存在session中，从session中获取，不能随便新建购物车）
		    * 4.重定向返回购物车页面（显示商品）
		    */
		String pid=request.getParameter("pid");
		int count=Integer.parseInt(request.getParameter("count"));
		//通过ID得到商品对象，需要用到数据库
		ProductService ps=(ProductService)BeanFactory.getBean("ProductService");
		try {
			
			//ProductService ps=new ProductServiceImpl();
			
			Product product=ps.getById(pid);
			
			
			
			//System.out.println("CartServlet_add2Cart::获取Product对象pid::"+pid);
			//System.out.println("CartServlet_add2Cart::获取Product对象::"+product);
			
			CartItem cartitem=new CartItem(product,count);
			//调用得到购物车函数
			Cart cart=getCart(request);
			//购物车自己具有增删订单项的能力，清空购物车的 能力。在定义对象的时候赋予的，避免了与数据库的接触。
			cart.add2cart(cartitem);
			//重定向
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
		request.setAttribute("msg", "加入购物车失败！");
			e.printStackTrace();
			return "/jsp/msg.jsp";
		}
		return null;
	}
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*获取id
		 * 获取购物车
		 * 执行购物车中的删除方法 
		 * 重定向购物车页面
		 */
		String pid=request.getParameter("pid");
		getCart(request).deleteItem(pid);
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	
	}
	public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 执行购物车中的clear方法
		 * 重定向
		 */
		getCart(request).clear();
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		
		return null;
	}
	
	/**
	 * 
	 * session的使用
	 * @param request
	 * @return
	 */
	
private Cart getCart(HttpServletRequest request) {
	// TODO Auto-generated method stub
	/*得到session中购物车
	 * 判断得到的结果是否为空（不是购物车是否为空）
	 * 如果为空，则新建一个购物车放入到session中
	 */
	Cart cart =(Cart) request.getSession().getAttribute("cart");
	if(cart==null) {
		 cart=new Cart();
		 request.getSession().setAttribute("cart", cart);
	}
	return cart;
}
}
