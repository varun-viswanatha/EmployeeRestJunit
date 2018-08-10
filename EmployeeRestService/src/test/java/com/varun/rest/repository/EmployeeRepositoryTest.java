package com.varun.rest.repository;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import com.varun.rest.model.Employee;
import com.varun.rest.util.Constants;

public class EmployeeRepositoryTest {

	@InjectMocks
	private EmployeeRepository repository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void test_get_all_employees_success() throws Exception {
		System.out.println("");
		System.out.println("test_get_all_employees_success()");
		List<Employee> employees = repository.getAllEmployees();
		assertNotNull(repository.getAllEmployees());
		assertEquals(repository.getAllEmployees(), employees);

	}

	@Test
	public void test_get_all_employees_failure() throws Exception {
		System.out.println("");
		System.out.println("test_get_all_employees_failure()");
		List<Employee> employees = null;
		assertNotEquals(repository.getAllEmployees(), employees);

	}

	@Test
	public void test_get_employee_by_id_success() throws Exception {
		System.out.println("");
		System.out.println("test_get_employee_by_id_success()");
		List<Employee> employees = Arrays.asList(new Employee("1001", "User1", "02", "user1@user.com", "1234567890"));
		assertNotNull(repository.getEmployee("1001"));
		assertEquals(repository.getEmployee("1001"), employees);
	}

	@Test
	public void test_get_employee_by_id_failure() throws Exception {
		System.out.println("");
		System.out.println("test_get_employee_by_id_failure()");
		List<Employee> employees = null;
		assertNotEquals(repository.getEmployee("1001"), employees);

	}

	@Test
	public void test_add_employee_success() throws Exception {
		System.out.println("");
		System.out.println("test_add_employee_success()");
		String id = repository.getLastId();
		System.out.println("last id : " + id);
		int num = Integer.parseInt(id);
		id = Integer.toString(++num);
		System.out.println("id added : " + id);
		Employee employee = new Employee(id, "User1", "02", "user1@user.com", "1234567890");
		assertEquals(repository.addEmployee(employee), Constants.SUCCESS);
	}

	@Test
	public void test_add_employee_failure() throws Exception {
		System.out.println("");
		System.out.println("test_add_employee_failure()");
		String id = repository.getLastId();
		System.out.println("id addition failed : " + id + " as Id already exists.");
		Employee employee = new Employee(id, "User1", "02", "user1@user.com", "1234567890");
		assertEquals(repository.addEmployee(employee), "Id already exists. Please enter a new id");
	}

	@Test
	public void test_update_employee_success() throws Exception {
		System.out.println("");
		System.out.println("test_update_employee_success()");
		String id = repository.getLastId();
		System.out.println("id before update : " + id);
		int num = Integer.parseInt(id);
		String num1 = Integer.toString(num);
		id = Integer.toString(++num);
		Employee employee = new Employee(id, "User1", "02", "user1@user.com", "1234567890");
		assertEquals(repository.updateEmployee(employee, num1), Constants.SUCCESS);
		System.out.println("id after update : " + id);

	}

	@Test
	public void test_update_employee_failure() throws Exception {
		System.out.println("");
		System.out.println("test_update_employee_failure()");
		String id = repository.getLastId();
		int num = Integer.parseInt(id);
		id = Integer.toString(++num);
		System.out.println("failed to update id : 10000 with id " +id );
		Employee employee = new Employee(id, "User1", "02", "user1@user.com", "1234567890");
		assertNotEquals(repository.updateEmployee(employee, "10000"), Constants.SUCCESS);
		assertEquals(repository.updateEmployee(employee, "10000"), "id not found");
		

	}
	
	@Test
	public void test_delete_employee_success() throws Exception {
		System.out.println("");
		System.out.println("test_delete_employee_success()");
		String id = repository.getLastId();
		System.out.println("id deleted : " + id);
		assertEquals(repository.deleteEmployee(id), Constants.SUCCESS);
	}
	
	@Test
	public void test_delete_employee_failure() throws Exception {
		System.out.println("");
		System.out.println("test_delete_employee_failure()");
		String id = repository.getLastId();
		int num = Integer.parseInt(id);
		id = Integer.toString(++num);
		System.out.println("id "+id+" deletion failed : " );
		assertNotEquals(repository.deleteEmployee(id), Constants.SUCCESS);
		assertEquals(repository.deleteEmployee(id), "id not found");
	}
}
