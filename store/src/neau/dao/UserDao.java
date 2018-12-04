package neau.dao;

import neau.domain.User;

public interface UserDao {

	void save(User user) throws Exception;

	//User getByCode(String code) throws Exception;

	void update(User user) throws Exception;

	User getByUsernameAndPwd(String username, String password) throws Exception;

}
