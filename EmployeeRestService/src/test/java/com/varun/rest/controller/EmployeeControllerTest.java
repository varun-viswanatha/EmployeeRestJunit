package com.varun.rest.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.google.gson.Gson;
import com.varun.rest.model.Employee;
import com.varun.rest.service.EmployeeServiceImpl;
import com.varun.rest.util.Constants;

public class EmployeeControllerTest {

	MockMvc mockMvc;

	@Mock
	private EmployeeServiceImpl userService;

	@InjectMocks
	private EmployeeController userController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

	}

	@Test
	public void test_get_all_employees_success() throws Exception {
		List<Employee> employees = Arrays.asList(new Employee("1", "user1", "01", "user1@user.com", "1234567890"),
				new Employee("2", "user2", "02", "user2@user.com", "2345678901"));

		when(userService.getAllEmployees()).thenReturn(employees);
		mockMvc.perform(get("/employee")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is("1")))
				.andExpect(jsonPath("$[0].name", is("user1"))).andExpect(jsonPath("$[0].bu", is("01")))
				.andExpect(jsonPath("$[0].email", is("user1@user.com")))
				.andExpect(jsonPath("$[0].phone", is("1234567890"))).andExpect(jsonPath("$[1].id", is("2")))
				.andExpect(jsonPath("$[1].name", is("user2"))).andExpect(jsonPath("$[1].bu", is("02")))
				.andExpect(jsonPath("$[1].email", is("user2@user.com")))
				.andExpect(jsonPath("$[1].phone", is("2345678901")));
		verify(userService, times(1)).getAllEmployees();
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void test_get_all_employees_failure() throws Exception {
		List<Employee> employees = Arrays.asList();

		when(userService.getAllEmployees()).thenReturn(employees);
		mockMvc.perform(get("/employee")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", is("No Employees found ")));
		verify(userService, times(1)).getAllEmployees();
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void test_get_employee_by_id_success() throws Exception {
		List<Employee> list = new ArrayList<Employee>();
		Employee employee = new Employee("1", "user1", "01", "user1@user.com", "1234567890");
		list.add(employee);
		when(userService.getEmployee("1")).thenReturn(list);
		mockMvc.perform(get("/employee/{id}", 1)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].id", is("1")))
				.andExpect(jsonPath("$[0].name", is("user1"))).andExpect(jsonPath("$[0].bu", is("01")))
				.andExpect(jsonPath("$[0].email", is("user1@user.com")))
				.andExpect(jsonPath("$[0].phone", is("1234567890")));
		verify(userService, times(1)).getEmployee("1");
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void test_get_employee_by_id_failure() throws Exception {
		List<Employee> list = new ArrayList<Employee>();
		when(userService.getEmployee("1")).thenReturn(list);
		mockMvc.perform(get("/employee/{id}", 1)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", is("User with id 1 not found")));
		verify(userService, times(1)).getEmployee("1");
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void test_add_employee_success() throws Exception {
		Employee employee = new Employee("3", "user3", "03", "user3@user.com", "3456789012");
		when(userService.addEmployee(employee)).thenReturn(Constants.SUCCESS);
		mockMvc.perform(
				post("/addemployee").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(asJsonString(employee)))
				.andExpect(status().isOk());
		verify(userService, times(1)).addEmployee(employee);
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void test_add_employee_failure() throws Exception {
		Employee employee = new Employee("5", "user5", "05", "user5@user.com", "5678901234");
		when(userService.addEmployee(employee))
				.thenReturn("An Employee with id " + employee.getId() + " already exist");
		mockMvc.perform(
				post("/addemployee").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(asJsonString(employee)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is("An Employee with id " + employee.getId() + " already exist")));
		verify(userService, times(1)).addEmployee(employee);
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void test_update_employee_success() throws Exception {
		Employee employee = new Employee("3", "user3", "03", "user3@user.com", "3456789012");
		Employee employee1 = new Employee("1", "user1", "01", "user1@user.com", "1234567890");
		List<Employee> list = new ArrayList<Employee>();
		list.add(employee);
		list.add(employee1);
		when(userService.getEmployee(employee.getId())).thenReturn(list);
		when(userService.updateEmployee(employee1, employee.getId())).thenReturn(Constants.SUCCESS);
		mockMvc.perform(put("/employee/{id}", employee.getId()).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(asJsonString(employee1))).andExpect(status().isOk());
		verify(userService, times(1)).getEmployee(employee.getId());
		verify(userService, times(1)).updateEmployee(employee1, employee.getId());
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void test_update_employee_not_found_failure() throws Exception {
		Employee employee = new Employee("3", "user3", "03", "user3@user.com", "3456789012");
		List<Employee> list = new ArrayList<Employee>();
		when(userService.getEmployee("2")).thenReturn(list);
		mockMvc.perform(
				put("/employee/{id}", "2").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(asJsonString(employee)))
				.andExpect(status().isOk()).andExpect(jsonPath("$", is("Employee with id 2 not found")));
		verify(userService, times(1)).getEmployee("2");
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void test_delete_employee_success() throws Exception {
		Employee employee = new Employee("1", "user1", "01", "user1@user.com", "1234567890");
		List<Employee> list = new ArrayList<Employee>();
		list.add(employee);
		when(userService.getEmployee(employee.getId())).thenReturn(list);
		when(userService.deleteEmployee(employee.getId())).thenReturn(Constants.SUCCESS);
		mockMvc.perform(delete("/employee/{id}", employee.getId())).andExpect(status().isOk());
		verify(userService, times(1)).getEmployee(employee.getId());
		verify(userService, times(1)).deleteEmployee(employee.getId());
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void test_delete_employee_failure() throws Exception {
		List<Employee> list = new ArrayList<Employee>();
		when(userService.getEmployee("4")).thenReturn(list);
		mockMvc.perform(delete("/employee/{id}", "4")).andExpect(status().isOk())
				.andExpect(jsonPath("$", is("Employee with id 4 not found")));
		verify(userService, times(1)).getEmployee("4");
		verifyNoMoreInteractions(userService);
	}

	/*
	 * converts a Java object into JSON representation
	 */
	public static String asJsonString(final Object obj) {
		try {

			return new Gson().toJson(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
