package im.dadoo.dadooauth.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDao<T> {

	private Class<T> c;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	public BaseDao(Class<T> c) {
		this.c = c;
	}
	
	public Serializable save(T obj) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.save(obj);
	}
	
	public void update(T obj) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(obj);
	}
	
	public void delete(T obj) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(obj);
	}
	
	public void deleteById(Serializable id) {
		this.delete(this.fetchById(id));
	}
	
	@SuppressWarnings("unchecked")
	public T fetchById(Serializable id) {
		Session session = this.sessionFactory.getCurrentSession();
		return (T) session.get(this.c, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> list() {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(this.c);
		criteria.addOrder(Order.desc("id"));
		return (List<T>)criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> list(Integer pagecount, Integer pagesize) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(this.c);
		criteria.addOrder(Order.desc("id"));
		criteria.setFirstResult((pagecount - 1) * pagesize);
		criteria.setMaxResults(pagesize);
		return (List<T>)criteria.list();
	}
	
	public Serializable size() {
		String hql = "select count(*) from " + this.c.getName();
		return (Serializable)this.sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
	}
	
	protected Criteria makeQuery(Criteria criteria, Map<String, Object> params) {
		return criteria;
	}
	
	protected Criteria makeOrder(Criteria criteria, Map<String, Boolean> orders) {
		if (orders != null) {
			for (Map.Entry<String, Boolean> entry : orders.entrySet()) {
				if (entry.getValue()) {
					criteria.addOrder(Order.asc(entry.getKey()));
				}
				else {
					criteria.addOrder(Order.desc(entry.getKey()));
				}
			}
		}
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<T> query(Map<String, Object> params, Map<String, Boolean> orders) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(this.c);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		criteria = this.makeQuery(criteria, params);
		criteria = this.makeOrder(criteria, orders);
		return (List<T>)criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> query(Map<String, Object> params, Map<String, Boolean> orders, 
			Integer pagecount, Integer pagesize) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(this.c);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		criteria = this.makeQuery(criteria, params);
		criteria = this.makeOrder(criteria, orders);
		criteria.setFirstResult((pagecount - 1) * pagesize);
		criteria.setMaxResults(pagesize);
		return (List<T>)criteria.list();
	}
}
