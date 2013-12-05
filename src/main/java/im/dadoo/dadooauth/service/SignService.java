package im.dadoo.dadooauth.service;

import im.dadoo.dadooauth.dao.UserDao;
import im.dadoo.dadooauth.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SignService {

	@Autowired
	private UserDao userDao;
	
	public Boolean existByName(String name) {
		if (null != this.userDao.fetchByName(name)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean existByEmail(String email) {
		if (null != this.userDao.fetchByEmail(email)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public User signup(String name, String email, String password) {
		if (null == this.userDao.fetchByName(name) && 
				null == this.userDao.fetchByEmail(email)) {
			User user = User.create(name, email, password, System.currentTimeMillis(), User.PREPARE);
			this.userDao.save(user);
			return user;
		}
		else {
			return null;
		}
	}
	
	public User signin(String name, String password) {
		User user = this.userDao.fetchByName(name);
		if (user != null && 
				user.getPassword().equals(password) && 
				user.getState() == User.NORMAL) {
			return user;
		}
		else {
			return null;
		}
	}
	
	public User verify(Integer id) {
		User user = this.userDao.fetchById(id);
		if (user != null && user.getState() == User.PREPARE) {
			user.setState(User.NORMAL);
			this.userDao.update(user);
			return user;
		}
		else {
			return null;
		}
	}
	
	public User lock(Integer id) {
		User user = this.userDao.fetchById(id);
		if (user != null && user.getState() == User.NORMAL) {
			user.setState(User.LOCK);
			this.userDao.update(user);
			return user;
		}
		else {
			return null;
		}
		
	}
	
	public User unlock(Integer id) {
		User user = this.userDao.fetchById(id);
		if (user != null && user.getState() == User.LOCK) {
			user.setState(User.NORMAL);
			this.userDao.update(user);
			return user;
		}
		else {
			return null;
		}
		
	}
}
