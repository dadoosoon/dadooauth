package im.dadoo.dadooauth.service;

import java.util.List;
import java.util.Map;

import im.dadoo.dadooauth.dao.UserDao;
import im.dadoo.dadooauth.domain.User;
import im.dadoo.dadooauth.exception.AuthException;
import im.dadoo.dadooauth.exception.ExceptionCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public User update(Integer id, String name, String email, 
			String password, Integer state) throws AuthException {
		User user = this.userDao.fetchById(id);
		if (user == null) {
			throw new AuthException(404, ExceptionCode.NOT_EXIST, "该用户不存在");
		}
		//判断用户名和邮箱是否被占用
		User u = this.userDao.fetchByName(name);
		if (u != null && u.getId() != id) {
			throw new AuthException(400, ExceptionCode.SAME_NAME, "用户名已被使用");
		}
		u = this.userDao.fetchByEmail(email);
		if (u != null && u.getId() != id) {
			throw new AuthException(400, ExceptionCode.SAME_EMAIL, "邮箱已被使用");
		}
		user.setName(name);
		user.setPassword(password);
		user.setEmail(email);
		if (state != null) {
			user.setState(state);
		}
		this.userDao.update(user);
		return user;
	}
	
	public User fetchById(Integer id) throws AuthException {
		User user = this.userDao.fetchById(id);
		if (user == null) {
			throw new AuthException(404, ExceptionCode.NOT_EXIST, "该用户不存在");
		}
		return user;
	}
	
	public User fetchByName(String name) throws AuthException {
		User user = this.userDao.fetchByName(name);
		if (user == null) {
			throw new AuthException(404, ExceptionCode.NOT_EXIST, "该用户不存在");
		}
		return user;
	}
	
	public void deleteById(Integer id) {
		User user = this.userDao.fetchById(id);
		if (user == null) {
			throw new AuthException(404, ExceptionCode.NOT_EXIST, "该用户不存在");
		}
		this.userDao.deleteById(id);
	}
	
	public List<User> list() {
		return this.userDao.list();
	}
	
	public List<User> list(Integer pagecount, Integer pagesize) {
		return this.userDao.list(pagecount, pagesize);
	}
	
	public List<User> query(Map<String, Object> params, Map<String, Boolean> orders) {
		return this.userDao.query(params, orders);
	}
	
	public List<User> query(Map<String, Object> params, Map<String, Boolean> orders,
			Integer pagecount, Integer pagesize) {
		return this.userDao.query(params, orders, pagecount, pagesize);
	}
	
	public Integer size() {
		return (Integer)this.userDao.size();
	}
}
