package im.dadoo.dadooauth.service;

import java.util.List;
import java.util.Map;

import im.dadoo.dadooauth.dao.UserDao;
import im.dadoo.dadooauth.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public User update(Integer id, String name, String email, String password, Integer state) {
		User user = this.userDao.fetchById(id);
		user.setName(name);
		user.setPassword(password);
		user.setEmail(email);
		if (state != null) user.setState(state);
		this.userDao.update(user);
		return user;
	}
	
	public User fetchById(Integer id) {
		return this.userDao.fetchById(id);
	}
	
	public User fetchByName(String name) {
		return this.userDao.fetchByName(name);
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
