package com.varun.rest.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.varun.rest.model.Employee;
import com.varun.rest.repository.EmployeeRepository;
import com.varun.rest.util.Constants;

public class EmployeeServiceImplTest {

	@Mock
	EmployeeRepository repository;

	@InjectMocks
	private EmployeeServiceImpl userService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void test_get_all_employees_success() throws Exception {
		List<Employee> employees = Arrays.asList(new Employee("1", "user1", "01", "user1@user.com", "1234567890"),
				new Employee("2", "user2", "02", "user2@user.com", "2345678901"));
		when(repository.getAllEmployees()).thenReturn(employees);
		assertEquals(employees, userService.getAllEmployees());
		verify(repository, times(1)).getAllEmployees();
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void test_get_all_employees_failure() throws Exception {
		List<Employee> employees = new ArrayList<>();
		when(repository.getAllEmployees()).thenReturn(null);
		assertNotEquals(employees, userService.getAllEmployees());
		assertNull(repository.getAllEmployees());
		verify(repository, times(2)).getAllEmployees();
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void test_get_employee_by_id_success() throws Exception {
		List<Employee> employees = Arrays.asList(new Employee("1", "user1", "01", "user1@user.com", "1234567890"),
				new Employee("2", "user2", "02", "user2@user.com", "2345678901"));
		when(repository.getEmployee("2")).thenReturn(employees);
		assertEquals(employees, userService.getEmployee("2"));
		verify(repository, times(1)).getEmployee("2");
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void test_get_employee_by_id_failure() throws Exception {
		List<Employee> employees = new ArrayList<>();
		when(repository.getEmployee("2")).thenReturn(null);
		assertNotEquals(employees, userService.getEmployee("2"));
		assertNull(userService.getEmployee("2"));
		verify(repository, times(2)).getEmployee("2");
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void test_add_employee_success() throws Exception {
		Employee employee = new Employee("1", "user1", "01", "user1@user.com", "1234567890");
		when(repository.addEmployee(employee)).thenReturn(Constants.SUCCESS);
		assertEquals(Constants.SUCCESS, userService.addEmployee(employee));
		verify(repository, times(1)).addEmployee(employee);
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void test_add_employee_failure() throws Exception {
		Employee employee = new Employee("1", "user1", "01", "user1@user.com", "1234567890");
		when(repository.addEmployee(employee)).thenReturn(null);
		assertNotEquals(Constants.SUCCESS, userService.addEmployee(employee));
		assertNull(userService.addEmployee(employee));
		verify(repository, times(2)).addEmployee(employee);
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void test_update_employee_success() throws Exception {
		Employee employee = new Employee("3", "user3", "03", "user3@user.com", "3456789012");
		when(repository.updateEmployee(employee, "1")).thenReturn(Constants.SUCCESS);
		assertEquals(Constants.SUCCESS, userService.updateEmployee(employee, "1"));
		verify(repository, times(1)).updateEmployee(employee, "1");
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void test_update_employee_failure() throws Exception {
		Employee employee = new Employee("3", "user3", "03", "user3@user.com", "3456789012");
		when(repository.updateEmployee(employee, "1")).thenReturn(null);
		assertNotEquals(Constants.SUCCESS, userService.updateEmployee(employee, "1"));
		assertNull(userService.updateEmployee(employee, "1"));
		verify(repository, times(2)).updateEmployee(employee, "1");
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void test_delete_employee_success() throws Exception {
		when(repository.deleteEmployee("1")).thenReturn(Constants.SUCCESS);
		assertEquals(Constants.SUCCESS, userService.deleteEmployee("1"));
		verify(repository, times(1)).deleteEmployee("1");
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void test_delete_employee_failure() throws Exception {
		when(repository.deleteEmployee("1")).thenReturn(null);
		assertNotEquals(Constants.SUCCESS, userService.deleteEmployee("1"));
		assertNull(userService.deleteEmployee("1"));
		verify(repository, times(2)).deleteEmployee("1");
		verifyNoMoreInteractions(repository);
	}
}
