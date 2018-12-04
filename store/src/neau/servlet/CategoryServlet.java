package neau.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neau.dao.UserDao;
import neau.service.CategoryService;
import neau.service.impl.CategoryServiceImpl;
import neau.utils.BeanFactory;

/**
 * Servlet implementation class categoryServlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
//首页的分类模块
	 public String findAll(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		 /*
		  * 
		  *查询分类
		  * 
		  */
		 try {
			 //0.设置响应编码
			 response.setContentType("text/html;charset=utf-8");
			 //1.调用service服务，查询分类，service返回json字符串
		// CategoryService cs=new CategoryServiceImpl();
		 CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		 //mysql获取列表
			String value=cs.findAll();
			//从redis中获取列表
			
			//String value = cs.findAllFromRedis();
			//-----------------------------------------
			//！！！得到字符串写到浏览器？？？存在疑问如果不写，首页就不会出现查询到的列表
			//-----------------------------------------
			response.getWriter().println(value);
			
			//System.out.println(value);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//此处异常不抛出，为了不影响其他功能的显示
			//e.printStackTrace();
		}
		 
		 return null;
	 }

}
