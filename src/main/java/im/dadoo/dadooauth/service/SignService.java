package im.dadoo.dadooauth.service;

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
		User user = null;
		if (null != this.userDao.fetchByName(name)) {
			throw new AuthException(400, ExceptionCode.SAME_NAME, "用户名已存在");
		}
		else if (null != this.userDao.fetchByEmail(email)) {
			throw new AuthException(400, ExceptionCode.SAME_EMAIL, "邮箱已存在");
		}
		else {
			user = User.create(name, email, password, System.currentTimeMillis(), User.PREPARE);
			this.userDao.save(user);
		}
		return user;
	}
	
	public User signin(String name, String password) {
		User user = this.userDao.fetchByName(name);
		if (user == null) {
			throw new AuthException(404, ExceptionCode.NOT_EXIST, "该用户不存在");
		}
		else if (!user.getPassword().equals(password)) {
			throw new AuthException(400, ExceptionCode.WRONG_PASSWORD, "密码错误");
		}
		else if (user.getState() != User.NORMAL) {
			throw new AuthException(400, ExceptionCode.NOT_ALLOWED, "该用户暂不允许登录");
		}
		return user;
	}
	
	public User verify(Integer id) {
		User user = this.userDao.fetchById(id);
		if (user == null) {
			throw new AuthException(404, ExceptionCode.NOT_EXIST, "该用户不存在");
		}
		else if (user.getState() != User.PREPARE) {
			throw new AuthException(400, ExceptionCode.WRONG_STATE, "该用户状态不符");
		}
		user.setState(User.NORMAL);
		this.userDao.update(user);
		return user;
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
