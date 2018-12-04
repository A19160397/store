package neau.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neau.domain.User;

/**
 * Servlet Filter implementation class PrivilegeFilter
 */
public class PrivilegeFilter implements Filter {

    
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		//1.强转
		HttpServletResponse response=(HttpServletResponse) arg1;
		HttpServletRequest  request=(HttpServletRequest)  arg0;
		//2.逻辑
		User user=(User) request.getSession().getAttribute("user");
		if(user==null)
		{
			//未登录,转发到信息提示页面
			request.setAttribute("msg","请先登录");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request,response);
			return;
		}
		//如果用在全部页面过滤的话：：判断是否为登陆页面或者注册的页面
		/*String path=request.getRequestURI();
		  if(path.indexOf("/login.jsp")>-1){//登录页面不过滤
	            chain.doFilter(arg0, arg1);//递交给下一个过滤器
	            return;
	        }
		  if(path.indexOf("/register.jsp")>-1){//注册页面不过滤
	            chain.doFilter(request, response);
	            return;
	        }
	        
		*/
		
		
		
		
		
		
		//配置过滤器是显示订单详情页面是查询是否登陆（某些页面需要验证是否登陆可以用或者某些功能我们只允许登陆的人访问，可以用）
		//3.放行
		chain.doFilter(request,response);
		
	}

}
