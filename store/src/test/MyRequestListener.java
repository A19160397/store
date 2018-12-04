/*package test;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import neau.domain.User;

public class MyRequestListener implements ServletRequestListener{

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("销毁了请求");
		
	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("创建了请求");
		//得到请求源对象
		HttpServletRequest request = (HttpServletRequest)arg0.getServletRequest();
		String ip = request.getRemoteHost();
		//把得到的ip放到session中
		System.out.println("ip:"+ip);
		
	
		
		
		//request.getSession().setAttribute("user", user);
	//HttpSession session = request.getSession();//在此处如果没有session会创建session，执行request创建后才会执行jsp页面加载，然后创建session如果有不会创建
	//User user =(User) request.getSession().getAttribute("user");
//	System.out.println(user.getName());
	//user.setName("ABC");
	//request.getSession().setAttribute("user", user);
	//session.setAttribute("ip", ip);
	}

}
*/