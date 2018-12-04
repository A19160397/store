/*package test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import neau.domain.User;

public class MySessionListener implements HttpSessionListener {

	int count=0;
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("创建了session");
		count++;
		System.out.println("当前访客人数："+count);
		//保存到context域中
		ServletContext context = arg0.getSession().getServletContext();
		context.setAttribute("visit", count);
		User user=new User();
		user.setName("Liming");
		
		arg0.getSession().setAttribute("user", user);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("销毁了session");
		
	}

}
*/