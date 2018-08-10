package com.varun.rest.repository;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import com.varun.rest.model.Employee;
import com.varun.rest.util.Constants;

@Repository("employeeRepository")
public class EmployeeRepository {

	SessionFactory sf;

	public EmployeeRepository() {
		super();
		Configuration con = new Configuration();
		con.configure();
		sf = con.buildSessionFactory();
	}

	@PostConstruct
	public void initIt() throws Exception {
		System.out.println("Init method called");
		System.out.println("New Configure");
		System.out.println("Building new Session Factory");
		Configuration con = new Configuration();
		con.configure();
		sf = con.buildSessionFactory();
	}

	@PreDestroy
	public void cleanUp() throws Exception {
		System.out.println("CleanUp method called ");
		System.out.println("Closing Session Factory");
		sf.close();
	}

	@SuppressWarnings("unchecked")
	public List<Employee> getAllEmployees() {

		List<Employee> list = new ArrayList<Employee>();
		Session ses = sf.openSession();
		ses.beginTransaction();
		String hql = "from Employee";
		Query q = ses.createQuery(hql);
		list = q.getResultList();
		ses.getTransaction().commit();
		ses.close();
		return list;

	}

	@SuppressWarnings("unchecked")
	public List<Employee> getEmployee(String id) {

		List<Employee> list = new ArrayList<Employee>();
		Session ses = sf.openSession();
		ses.beginTransaction();
		String hql = " from Employee e where e.id = :id";
		Query q = ses.createQuery(hql);
		q.setParameter("id", id);
		list = q.getResultList();
		ses.getTransaction().commit();
		ses.close();
		return list;

	}

	@SuppressWarnings("unchecked")
	public String addEmployee(Employee bean) {

		List<Employee> list = new ArrayList<Employee>();
		Session ses = sf.openSession();
		ses.beginTransaction();

		String hql = " from Employee e where e.id = :id";
		Query q = ses.createQuery(hql);

		q.setParameter("id", bean.getId());

		list = q.getResultList();
		if (list.size() == 0) {
			ses.save(bean);
			ses.getTransaction().commit();
			ses.close();
			return Constants.SUCCESS;

		}
		ses.getTransaction().commit();
		ses.close();
		return "Id already exists. Please enter a new id";
	}

	@SuppressWarnings("unchecked")
	public String updateEmployee(Employee bean, String id) {

		List<Employee> list = new ArrayList<Employee>();
		Session ses = sf.openSession();
		ses.beginTransaction();

		String hql = " from Employee e where e.id = :id";
		Query q1 = ses.createQuery(hql);

		q1.setParameter("id", id);

		list = q1.getResultList();
		if (!list.isEmpty()) {
			
			String hql1 = " update Employee e set e.id = :newId , e.name = :newName ,  e.bu = :newBu ,  e.email = :newEmail , e.phone = :newPhone where e.id like :id";
			Query q = ses.createQuery(hql1);
			q.setParameter("newId", bean.getId());
			q.setParameter("newName", bean.getName());
			q.setParameter("newBu", bean.getBu());
			q.setParameter("newEmail", bean.getEmail());
			q.setParameter("newPhone", bean.getPhone());
			q.setParameter("id", id);
			int result = q.executeUpdate();
			System.out.println("Rows affected: " + result);
			ses.getTransaction().commit();
			ses.close();
			return Constants.SUCCESS;

		}
		ses.getTransaction().commit();
		ses.close();
		return "id not found";

	}

	@SuppressWarnings("unchecked")
	public String deleteEmployee(String id) {
		
		List<Employee> list = new ArrayList<Employee>();
		Session ses = sf.openSession();
		ses.beginTransaction();

		String hql = " from Employee e where e.id = :id";
		Query q1 = ses.createQuery(hql);

		q1.setParameter("id", id);

		list = q1.getResultList();
		if (!list.isEmpty()) {
			
			String hql1 = " delete Employee e where e.id = :id";
			Query q = ses.createQuery(hql1);
			q.setParameter("id", id);
			int result = q.executeUpdate();
			System.out.println("Rows affected: " + result);
			ses.getTransaction().commit();
			ses.close();
			return Constants.SUCCESS;
		}
		
		ses.getTransaction().commit();
		ses.close();
		return "id not found";

		

	}

	@SuppressWarnings("unchecked")
	public String getLastId() {

		List<Employee> list = new ArrayList<Employee>();
		Session ses = sf.openSession();
		ses.beginTransaction();

		// SELECT * FROM Table ORDER BY ID DESC LIMIT 1
		String hql = "from Employee ";
		Query q = ses.createQuery(hql);
		// q.setMaxResults(1);
		list = q.getResultList();
		ses.getTransaction().commit();
		ses.close();

		String id = "";
		for (Employee e : list) {
			id = e.getId();
		}
		return id;

	}

}
