package com.varun.rest.service;

import java.util.List;
import com.varun.rest.model.Employee;

public interface EmployeeService {

	public List<Employee> getAllEmployees();

	public List<Employee> getEmployee(String id);

	public String addEmployee(Employee bean);

	public String updateEmployee(Employee bean, String id);

	public String deleteEmployee(String id);

}
