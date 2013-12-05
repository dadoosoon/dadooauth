package im.dadoo.dadooauth.dao;

import im.dadoo.dadooauth.domain.User;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<User> {
	public UserDao() {
		super(User.class);
	}
	
	public User fetchByName(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		return (User) session.createCriteria(User.class)
			.add(Restrictions.like("name", name))
			.uniqueResult();
	}
	
	public User fetchByEmail(String email) {
		Session session = this.sessionFactory.getCurrentSession();
		return (User) session.createCriteria(User.class)
			.add(Restrictions.like("email", email))
			.uniqueResult();
	}
}
