package com.varun.rest.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.varun.rest.model.Employee;
import com.varun.rest.service.EmployeeService;
import com.varun.rest.util.Constants;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService service;

	Gson gson = new Gson();
	// --------------Returns all Employees -------------------

	@RequestMapping(value = "/employee", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> listEmployees() {

		List<Employee> employeeList = service.getAllEmployees();
		if (employeeList.isEmpty()) {
			String jsonList = gson.toJson("No Employees found ");
			return new ResponseEntity<String>(jsonList, HttpStatus.OK);
		}
		System.out.println(employeeList);

		String jsonList = gson.toJson(employeeList);
		return new ResponseEntity<String>(jsonList, HttpStatus.OK);

	}

	// --------------Return a specific Employee ------------------
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> getUser(@PathVariable("id") long id) {

		System.out.println("Fetching User with id " + id);
		String employeeID = String.valueOf(id);
		List<Employee> employeeList = service.getEmployee(employeeID);
		Gson gson = new Gson();
		if (employeeList.isEmpty()) {
			String json = gson.toJson("User with id " + id + " not found");
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<String>(json, HttpStatus.OK);
		}

		String employeeJsonList = gson.toJson(employeeList);
		System.out.println("jsonList: " + employeeJsonList);

		return new ResponseEntity<String>(employeeJsonList, HttpStatus.OK);
	}

	// --------------Add an Employee -------------------------------------
	@RequestMapping(value = "/addemployee", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> createUser(@RequestBody String s, UriComponentsBuilder ucBuilder) {

		System.out.println("Creating Employee ");
		Gson gson = new Gson();
		Employee bean = gson.fromJson(s, Employee.class);
		System.out.println("Creating Employee " + bean.getName());
		String result = service.addEmployee(bean);

		if (!result.equals(Constants.SUCCESS)) {
			String json = gson.toJson("An Employee with id " + bean.getId() + " already exist");
			System.out.println("An Employee with id " + bean.getId() + " already exist");
			return new ResponseEntity<String>(json, HttpStatus.OK);
		} else {
			/*
			 * HttpHeaders headers = new HttpHeaders();
			 * headers.setLocation(ucBuilder.path("/employee/{id}").
			 * buildAndExpand(bean.getId()).toUri());
			 */

			String json = gson.toJson("An Employee with id " + bean.getId() + " Successfully created");
			return new ResponseEntity<String>(json, HttpStatus.OK);
		}
	}

	// --------------Update a specific Employee ----------------
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUser(@PathVariable("id") long id, @RequestBody String s) {

		Gson gson = new Gson();
		System.out.println("Updating User " + id);
		String employeeID = String.valueOf(id);
		List<Employee> currentEmp = service.getEmployee(employeeID);

		if (currentEmp.isEmpty()) {
			String json = gson.toJson("Employee with id " + id + " not found");
			System.out.println("Employee with id " + id + " not found");
			return new ResponseEntity<String>(json, HttpStatus.OK);
		} else {

			Employee bean = gson.fromJson(s, Employee.class);
			System.out.println("Creating Employee " + bean.getName());
			@SuppressWarnings("unused")
			String result = service.updateEmployee(bean, employeeID);
			String json = gson.toJson("Employee update successful");
			return new ResponseEntity<String>(json, HttpStatus.OK);

		}
	}

	// --------------Delete a specific Employee ---------------
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
		System.out.println("Deleting User with id " + id);
		String employeeID = String.valueOf(id);
		Gson gson = new Gson();
		List<Employee> currentEmp = service.getEmployee(employeeID);

		if (currentEmp.isEmpty()) {
			String json = gson.toJson("Employee with id " + id + " not found");
			System.out.println("Employee with id " + id + " not found");
			return new ResponseEntity<String>(json, HttpStatus.OK);
		} else {
			@SuppressWarnings("unused")
			String result = service.deleteEmployee(employeeID);
			String json = gson.toJson("Employee with id " + id + "successfully deleted");
			return new ResponseEntity<String>(json, HttpStatus.OK);

		}
	}

}
