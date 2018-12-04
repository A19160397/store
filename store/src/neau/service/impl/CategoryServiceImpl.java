package neau.service.impl;

import java.util.List;

import neau.dao.CategoryDao;
import neau.domain.Category;
import neau.utils.BeanFactory;
import neau.utils.JsonUtil;

public class CategoryServiceImpl implements neau.service.CategoryService {

	/**
	 * 
	 * 查询所有分类，返回列表
	 */
	@Override
	public List<Category> findList() throws Exception {
		CategoryDao cd=(CategoryDao) BeanFactory.getBean("CategoryDao");
		List<Category> list=cd.findAll();
		return list;
	}
	@Override
	/**
	 * 
	 * 查询所有分类，返回字符串
	 * 
	 */
	public String findAll() throws Exception {
		// TODO Auto-generated method stub
		//调用dao层查询分类，返回值list
		//CategoryDao cd=new CategoryDaoImpl();
		
		
		//调用自身方法查询，返回list列表
		List<Category> list=findList();
		//将list转换成字符串类型???为什么要转换成字符串
		if(list!=null&&list.size()>0)
		{
			return JsonUtil.list2json(list);
		}
		return null;
	}
	/**
	 * 
	 * 保存分类
	 */
	@Override
	public void add(Category category) throws Exception {
		CategoryDao cd=(CategoryDao) BeanFactory.getBean("CategoryDao");
	cd.add(category);
		
	}
	@Override
	public String getCid(String pid) throws Exception {
		CategoryDao cd=(CategoryDao) BeanFactory.getBean("CategoryDao");
		return cd.getCid(pid);
		
	}

	
}
