package neau.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import neau.dao.UserDao;
import neau.domain.User;
import neau.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao{

	@Override
	/**
	 * 用户注册
	 */
	public void save(User user) throws SQLException {
		System.out.println("UserDao::数据库注册任务开始");
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		/*
		 *  `uid` varchar(32) NOT NULL,
			  `username` varchar(20) DEFAULT NULL,
			  `password` varchar(20) DEFAULT NULL,
			  
			  `name` varchar(20) DEFAULT NULL,
			  `email` varchar(30) DEFAULT NULL,
			  `telephone` varchar(20) DEFAULT NULL,
			  
			  `birthday` date DEFAULT NULL,
			  `sex` varchar(10) DEFAULT NULL,
			  `state` int(11) DEFAULT NULL,
			  
			  `code` varchar(64) DEFAULT NULL,
		 */

		String sql = "insert into user values(?,?,?,?,?,?,?);";
		qr.update(sql, user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),
				user.getSex());
		System.out.println("UserDao数据库注册执行完毕");
	}
/*
	@Override
	//通过激活码获取用户
	public User getByCode(String code) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where code = ? limit 1";
		return qr.query(sql, new BeanHandler<>(User.class), code);
	}
*/
	@Override
	/**
	 * 更新用户
	 */
	public void update(User user) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		/*
		 *  `uid` varchar(32) NOT NULL,
			  `username` varchar(20) DEFAULT NULL,
			  `password` varchar(20) DEFAULT NULL,
			  
			  `name` varchar(20) DEFAULT NULL,
			  `email` varchar(30) DEFAULT NULL,
			  `telephone` varchar(20) DEFAULT NULL,
			  
			  `birthday` date DEFAULT NULL,
			  `sex` varchar(10) DEFAULT NULL,
			  `state` int(11) DEFAULT NULL,
			  
			  `code` varchar(64) DEFAULT NULL,
		 */
		String sql="update user set password = ?,sex = ? where uid = ?";
		qr.update(sql, user.getPassword(),user.getSex(),user.getUid());
	}

	@Override
	/**
	 * 用户登录，返回值User
	 * 如果有匹配的就返回user，没有就返回空
	 */
	public User getByUsernameAndPwd(String username, String password) throws Exception {
		System.out.println("UserDao::数据库登陆任务开始");
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ? limit 1";
		return qr.query(sql, new BeanHandler<>(User.class), username,password);
	}
}
