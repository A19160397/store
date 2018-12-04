package neau.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import neau.constant.Constant;
import neau.domain.Cart;
import neau.domain.CartItem;
import neau.domain.Order;
import neau.domain.OrderItem;
import neau.domain.PageBean;
import neau.domain.User;
import neau.service.OrderService;
import neau.utils.BeanFactory;
import neau.utils.PaymentUtil;
import neau.utils.UUIDUtils;

/**
 * Servlet implementation class OrderServlet
 */

public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       /**
        * 
        * 保存订单
        * 显示订单详情
        * @param request
        * @param response
        * @return
        * @throws ServletException
        * @throws IOException
        */
	public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*1.新建一个订单
		 * 封装订单对象（包括购物人信息，封装好购物项放入到订单的购物列表中）
		 * 调用service方法保存在数据库中
		 * 转发到订单页面（带着订单对象的数据）
		 */
		System.out.println("OrderServlet::");
		System.out.println(request.getContextPath());
		try {
			Order order=new Order();
			
			//获取购物车（因为一会封装会用到）
			Cart cart=(Cart) request.getSession().getAttribute("cart");
			//获取用户（一会封装会用到）
			User user=(User) request.getSession().getAttribute("user");
			if(user==null) {
				//如果用户为空，未登录，返回登陆页面或者信息提示页面
				request.setAttribute("msg", "您还未登录，请回到登陆页面");
				return "/jsp/msg.jsp";
			}
			
			order.setOid(UUIDUtils.getId());
			/*
			 * 时间格式设置
			 */
			Date date=new Date();                             
	         SimpleDateFormat temp=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
	         String date2=temp.format(date);  
	         Date date3=temp.parse(date2);  
	        order.setOrdertime(date3);
			
	        
			order.setTotal(cart.getTotal());
			
			order.setState(Constant.ORDER_WEIZHIFU);
			order.setUser(user);
			//还有三项属性未设置，会在支付的时候设置
			//在显示订单信息的时候，会显示订单中的商品情况（订单项），所以此处回把订单项放入订单对象中，然后更新到数据库中，
			//也可以不放在订单对象中，在显示订单的时候，根据订单id在数据库中查找绑定好id的订单项也可以，但是会增加数据库任务量
			//封装成订单项放入到订单对象的订单项数组中，需要从购物车遍历购物项，依次封装成订单项，再放入
			//订单对象包含一个订单项数组（数据库中并不存在此数组，为了前台显示方便），每一个订单项对象存在一个订单属性，与其所属订单相连，数据库中存放id
			for(CartItem ci:cart.getCartItems()) {
				OrderItem oi=new OrderItem();
				oi.setCount(ci.getCount());
				oi.setSum(ci.getSum());
				oi.setItemid(UUIDUtils.getId());
				//在此处订单与订单项连接起来
				oi.setOrder(order);
				oi.setProduct(ci.getProduct ());
			//得到订单的订单项数组，放入oi
				order.getItems().add(oi);
			
			}
			//订单项完全封装完毕，订单部分信息封装完毕，调用数据库
			OrderService os=(OrderService) BeanFactory.getBean("OrderService");
		//	System.out.println("OrderServlet::获取os成功");
			//此处的save方法传入order，不仅更新数据库中的订单表，还更新数据库中的订单项表
			os.save(order);
			cart.clear();
			System.out.println("数据库保存订单更新成功");
			request.setAttribute("order", order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("OrderServlet::os.save报错");
			e.printStackTrace();
		}
		return "/jsp/order_pay.jsp";
	}
	/**
	 * 
	 * 我的订单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findMyOrderByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取准备数据 pagenumber,pagesize,userid
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			int pageSize=3;
			User user=(User) request.getSession().getAttribute("user");
			if(user==null) {
				request.setAttribute("msg", "您还未登陆，请先登录");
				return "/jsp/msg.jsp";
			}
			
			//2.调用service获取当前页面所有数据，得到pagebean
			 OrderService os= (OrderService) BeanFactory.getBean("OrderService");
			 PageBean<Order> bean = os.findMyOrderByPage(pageNumber,pageSize,user.getUid());
			 
			 
			 
			//3.将pagebean放入到request中，转发页面
			 request.setAttribute("bean", bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msg", "查看我的订单页面失败");
			return "/jsp/msg.jsp";
		}
		
		return "/jsp/order_list.jsp";
		
	}
	/**
	 * 
	 * 
	 * 查看订单详情//付款页面//
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String oid=request.getParameter("oid");
			//得到封装好的订单
			OrderService os=(OrderService) BeanFactory.getBean("OrderService");
			Order order=os.getById(oid);
			
			request.setAttribute("order", order);
			//如果已付款返回详情页面，如果未付款，返回付款页面
			if(order.getState()>=Constant.ORDER_YIFUKUAN)
			{
				return "/jsp/order_infoo.jsp";
			}
			//System.out.println("OrderServlet::去付款功能getById::查询订单项详情页面成功");
		} catch (Exception e) {
			request.setAttribute("msg", "查询订单详情失败！");
			e.printStackTrace();
			return "/jsp/msg.jsp";
		}
		
		return "/jsp/order_pay.jsp";
	}
	/**
	 * 
	 * 支付代码实现
	 * 更新收货人信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取参数
		//!!!得到order对象，调用service 更新order（再存储一遍到数据库中）update不可以用save替换因为更新与存储的数据库原理不太一样
		//拼接连接，重定向到支付页面
		try {
			String address=request.getParameter("address");
			String telephone=request.getParameter("telephone");
			String name=request.getParameter("name");
			String oid=request.getParameter("oid");
			OrderService os=(OrderService) BeanFactory.getBean("OrderService");
			
			Order order=os.getById(oid);
			//完善新增加的order信息
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			//向数据库中更新order信息
			os.update(order);
			
			
			// 组织发送支付公司需要哪些数据设置参数
				String pd_FrpId = request.getParameter("pd_FrpId");
				String p0_Cmd = "Buy";
				String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
				String p2_Order = oid;
				String p3_Amt = "0.01";
				String p4_Cur = "CNY";
				String p5_Pid = "";
				String p6_Pcat = "";
				String p7_Pdesc = "";
				// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
					// 第三方支付可以访问网址
				String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
				String p9_SAF = "";
				String pa_MP = "";
				String pr_NeedResponse = "1";
				// 加密hmac 需要密钥
				String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
				//PaymentUtils类为一个加密算法
				String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
						p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
						pd_FrpId, pr_NeedResponse, keyValue);

							
			//发送给第三方（重定向的地址）拼接参数
				
				StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
				sb.append("p0_Cmd=").append(p0_Cmd).append("&");
				sb.append("p1_MerId=").append(p1_MerId).append("&");
				sb.append("p2_Order=").append(p2_Order).append("&");
				sb.append("p3_Amt=").append(p3_Amt).append("&");
				sb.append("p4_Cur=").append(p4_Cur).append("&");
				sb.append("p5_Pid=").append(p5_Pid).append("&");
				sb.append("p6_Pcat=").append(p6_Pcat).append("&");
				sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
				sb.append("p8_Url=").append(p8_Url).append("&");
				sb.append("p9_SAF=").append(p9_SAF).append("&");
				sb.append("pa_MP=").append(pa_MP).append("&");
				sb.append("pd_FrpId=").append(pd_FrpId).append("&");
				sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
				sb.append("hmac=").append(hmac);
				
				
				//重定向
				response.sendRedirect(sb.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msg", "支付失败");
			return "/jsp/msg.jsp";
		}		
		return null;
	}
	/**
	 * 支付成功之后的回调（网上代码！！！）未测试
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String callback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取第三方发送过来的数据
		
		//2.获取订单 修改订单状态
		
		//3.更新订单
		try {
			String p1_MerId = request.getParameter("p1_MerId");
			String r0_Cmd = request.getParameter("r0_Cmd");
			String r1_Code = request.getParameter("r1_Code");
			String r2_TrxId = request.getParameter("r2_TrxId");
			String r3_Amt = request.getParameter("r3_Amt");
			String r4_Cur = request.getParameter("r4_Cur");
			String r5_Pid = request.getParameter("r5_Pid");
			String r6_Order = request.getParameter("r6_Order");
			String r7_Uid = request.getParameter("r7_Uid");
			String r8_MP = request.getParameter("r8_MP");
			String r9_BType = request.getParameter("r9_BType");
			String rb_BankId = request.getParameter("rb_BankId");
			String ro_BankOrderId = request.getParameter("ro_BankOrderId");
			String rp_PayDate = request.getParameter("rp_PayDate");
			String rq_CardNo = request.getParameter("rq_CardNo");
			String ru_Trxtime = request.getParameter("ru_Trxtime");
			// 身份校验 --- 判断是不是支付公司通知你
			String hmac = request.getParameter("hmac");
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
					"keyValue");

			// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
			boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
					r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
					r8_MP, r9_BType, keyValue);
			if (isValid) {
				// 响应数据有效
				if (r9_BType.equals("1")) {
					// 浏览器重定向
					System.out.println("111");
					request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"已经支付成功,等待发货~~");
					
				} else if (r9_BType.equals("2")) {
					// 服务器点对点 --- 支付公司通知你
					System.out.println("付款成功！222");
					// 修改订单状态 为已付款
					// 回复支付公司
					response.getWriter().print("success");
				}
				
				//修改订单状态
				OrderService s=(OrderService) BeanFactory.getBean("OrderService");
				Order order = s.getById(r6_Order);
				order.setState(Constant.ORDER_YIFUKUAN);
				s.update(order);
				
			} else {
				// 数据无效
				System.out.println("数据被篡改！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "支付失败");
		
		}
		return "/jsp/msg.jsp";
	}
	/**
	 * 
	 *	假设支付页面 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String pay2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//得到信息
			String address=request.getParameter("address");
			String telephone=request.getParameter("telephone");
			String name=request.getParameter("name");
			String oid=request.getParameter("oid");
			//得到order
			OrderService os=(OrderService) BeanFactory.getBean("OrderService");
			Order order=os.getById(oid);
			//完善新增加的order信息
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			//支付状态为1
			order.setState(Constant.ORDER_YIFUKUAN);
			
			//设置时间
			Date date=new Date();                             
	         SimpleDateFormat temp=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
	         String date2=temp.format(date);  
	         Date date3=temp.parse(date2);  
	        order.setOrdertime(date3);
			
			//向数据库中更新order信息（新增了支付人信息）
			os.update(order);
			request.setAttribute("msg", "您的订单号为:"+order.getOid()+",金额为:"+order.getTotal()+"已经支付成功,等待发货~~");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msg", "支付失败");
			
		}
		
		return "jsp/msg.jsp";

	}
	/**
	 * 删除订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pagenumber=request.getParameter("pagenumber");
		Integer state=Integer.parseInt(request.getParameter("state"));
		
		//已付款（已完成和未付款可以删除）
		if(state==Constant.ORDER_YIFUKUAN) {
			
			request.setAttribute("msg", "不可删除已付款的订单  ");
			return "/jsp/msg.jsp";
		}
		//已发货
		if(state==Constant.ORDER_YIFAHUO) {
			
			request.setAttribute("msg", "不可删除未完成的订单  ");
			return "/jsp/msg.jsp";
		}
		try {
			String oid=request.getParameter("oid");
			OrderService os=(OrderService) BeanFactory.getBean("OrderService");
			os.delete(oid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("msg", "删除失败");
			
			e.printStackTrace();
			return "/jsp/msg.jsp";
		}
		PrintWriter out = response.getWriter();
		//${pageContext.request.contextPath }/order?method=findMyOrderByPage&pagenumber=${bean.pageNumber }
		//out.print("<script>alert('删除成功!');window.location.href='request.getContextPath()+\"/order?method=findMyOrderByPage&pageNumber=\"+pagenumber'</script>");
		response.sendRedirect(request.getContextPath()+"/order?method=findMyOrderByPage&pageNumber="+pagenumber);
		return null;
	
	}
	/**
	 * 
	 * 确认收货
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//提示 确认收获成功，重定向到订单列表
		String oid=request.getParameter("oid");
		OrderService os=(OrderService) BeanFactory.getBean("OrderService");
		try {
			//改变订单状态：：已完成（已收货）
			Order order=os.getById(oid);
			order.setState(Constant.ORDER_YIWANCHENG);
			os.update(order);
			//发货完成后重定向到订单页面、state==1 已支付 （待发货）state==2 已发货（确认订单） state==3 已完成
			response.sendRedirect(request.getContextPath()+"/order?method=findMyOrderByPage&pageNumber=1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	

}
