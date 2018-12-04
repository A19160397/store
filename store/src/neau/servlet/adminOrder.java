package neau.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import com.sun.java.swing.plaf.windows.resources.windows;

import neau.constant.Constant;
import neau.domain.Order;
import neau.domain.OrderItem;
import neau.service.OrderService;
import neau.utils.BeanFactory;
import neau.utils.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * Servlet implementation class adminOrder
 */
public class adminOrder extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 查询订单
	 * 得到参数 ：订单状态
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllByState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//得到状态信息
		String state=request.getParameter("state");
		OrderService os=(OrderService) BeanFactory.getBean("OrderService");
		//得到list，放入request域中，请求转发到列表页面
		try {
			List<Order>list=os.findAllBystate(state);
			request.setAttribute("list", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/order/list.jsp";
	}
	/**
	 * 
	 * 展示订单详情
	 * 通过id获取订单对象(包含订单项)，但是会对获取的对象详细信息进行处理，过滤掉一些信)，然后转换成json，所以只会返回部分信息到浏览器中
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String showDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String oid=request.getParameter("oid");
		OrderService os=(OrderService) BeanFactory.getBean("OrderService");
		System.out.println("adminOrder::订单详情页面跳转到后台servlet成功... ...");
		try {
			Order order=os.getById(oid);
		//以上代码得到order对象，一下代码是对order对象的处理
		if(order!=null)
		{
			//得到订单项
			List<OrderItem> list=order.getItems();
			if(list!=null&&list.size()>0)
			{
				System.out.println("adminOrder::订单详情页面查询订单项成功成功... ...");
				//过滤掉不需要的信息，剩下的信息通过json方式传给浏览器
				JsonConfig config = JsonUtil.configJson(new String[]{"order","pdate","pdesc","itemid"});
				response.getWriter().println(JSONArray.fromObject(list, config));
			}
			
		}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * 修改订单状态（发货按钮，点击后变成已发货）
	 * 参数：订单id,订单状态
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//得到前台传来的参数
		
		
		String oid=request.getParameter("oid");
		try {
			
			OrderService os=(OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
		//发货：已付款--已发货
			
		order.setState(Constant.ORDER_YIFAHUO);
		os.update(order);
		System.out.println("发货完毕");
		//发货完成后重定向到订单页面、state==1 已支付 （待发货）state==2 已发货（确认订单） state==3 已完成
		response.sendRedirect(request.getContextPath()+"/adminOrder?method=findAllByState&state=1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("重定向异常");
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 
	 * 删除订单
	 * 参数：：得到订单id
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String oid=request.getParameter("oid");
			OrderService os=(OrderService) BeanFactory.getBean("OrderService");
			os.delete(oid);
			response.sendRedirect(request.getContextPath()+"/adminOrder?method=findAllByState");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			System.out.println("adminOrder::delete::删除失败");
			e.printStackTrace();
			
		}
		return null;
	}
	
	
  
}
