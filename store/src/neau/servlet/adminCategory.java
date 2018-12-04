package neau.servlet;

import java.io.IOException;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neau.domain.Category;
import neau.service.CategoryService;
import neau.utils.BeanFactory;
import neau.utils.UUIDUtils;

/**
 * Servlet implementation class adminCategory
 */
public class adminCategory extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
	//1.查询所有分类
	//2.添加分类
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		try {
			//调用service查询列表
			
			List<Category>list =cs.findList();
			//查询后放入request中，然后转发
			request.setAttribute("list", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/category/list.jsp";	
	}
	//添加分类跳转页面
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		return "/admin/category/add.jsp";
	}
	
	/**
	 * 
	 * 添加分类
	 * 1.获取参数
	 * 2.调用service保存分类
	 * (用数据库保存分类，需要传入分类对象，所以需要封装分类对象)
	 * 3.重定向到分类页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取参数
		String cname=request.getParameter("cname");
		Category  category=new Category();
		category.setCid(UUIDUtils.getId());
		category.setCname(cname);
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		try {
			cs.add(category);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		return null;
	
	}
	/**
	 * 
	 * 删除分类
	 * 判断该分类下商品是否为空，如果不为空，不能删除
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		return null;
	
	}
	/**
	 * 
	 * 更新分类
	 * 只能更新名字
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String upd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		return null;
	
	}
	
}
