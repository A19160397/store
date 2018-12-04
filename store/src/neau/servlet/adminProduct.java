package neau.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import neau.constant.Constant;
import neau.domain.Category;
import neau.domain.PageBean;
import neau.domain.Product;
import neau.service.CategoryService;
import neau.service.ProductService;
import neau.service.impl.ProductServiceImpl;
import neau.utils.BeanFactory;
import neau.utils.UUIDUtils;
import neau.utils.UploadUtils;

/**
 * Servlet implementation class adminProduct
 */
public class adminProduct extends BaseServlet {
	private static final long serialVersionUID = 1L;
/**
 * 
 * 查看所有商品
 * @param request
 * @param response
 * @return
 * @throws ServletException
 * @throws IOException
 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
	List<Product> list;
	try {
		list = ps.findAll();
		request.setAttribute("list", list);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println("adminProtect::findAll管理员查询商品失败");
		e.printStackTrace();
	}
		return "/admin/product/list.jsp";
	}
	/**
	 * 
	 * 跳转到增加页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//增加商品页面需要得到分类列表，为上品增加分类，所以先获取分类信息，在转发到增加商品页面
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		try {
			List list=cs.findList();
			request.setAttribute("list", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/product/add.jsp";
		
	}
	/**
	 * 跳转到编辑页面的准备工作，查询该商品的信息，分类
	 * Category--findList查询分类列表加载
	 * Product--getbyId查询商品信息
	 * Category--getCid查询商品对应的分类id
	 * 封装后返回到编辑页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			//查询分类到编辑页面
			CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
			List list=cs.findList();
			request.setAttribute("list", list);
			
			
			String pid=	request.getParameter("pid");
			ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
			//从数据库中得到商品信息，在编辑页面默认显示
			Product pd=ps.getById(pid);
			//从数据库中得到商品的属性，封装在商品中
			Category category=new Category();
			String cid=cs.getCid(pid);
			
			//System.out.println(cid);
			category.setCid(cid);
			//封装商品的完整信息
			pd.setCategory(category);
			request.setAttribute("pro", pd);
			//输出是否热门
			/**
			 * 
			 * 数据库中查询到的是cid而不是category，需要手动查询并设置
			 */
			
			//System.out.println(pd.getCategory());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/product/edit.jsp";
	}
/**
 * 
 * 删除商品
 * @param request
 * @param response
 * @return
 * @throws ServletException
 * @throws IOException
 */
	
	public String del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid=request.getParameter("pid");
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
		try {
			ps.delete(pid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			request.setAttribute("msg","删除失败，该商品已经在订单中存放，您可以尝试先下架商品");
			
			e.printStackTrace();
			return "/jsp/msg.jsp";
		}
		//重定向到列表页面
		response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll");
		return null;	
	}
	/**
	 * 
	 * 下架商品
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String out(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid=request.getParameter("pid");
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
		try {
			ps.out(pid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			//request.setAttribute("msg","下架失败，该商品已经在订单中存放，您可以尝试先下架商品");
			
			e.printStackTrace();
			System.out.println("下架失败");
			//return "/jsp/msg.jsp";
		}
		//重定向到列表页面
		response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll");
		return null;
	}
  
}
