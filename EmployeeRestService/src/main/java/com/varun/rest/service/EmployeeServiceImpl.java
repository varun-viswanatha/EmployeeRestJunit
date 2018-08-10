package com.varun.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varun.rest.model.Employee;
import com.varun.rest.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.getAllEmployees();
	}

	@Override
	public List<Employee> getEmployee(String id) {
		return employeeRepository.getEmployee(id);
	}

	@Override
	public String addEmployee(Employee bean) {
		return employeeRepository.addEmployee(bean);
	}

	@Override
	public String updateEmployee(Employee bean, String id) {
		return employeeRepository.updateEmployee(bean, id);
	}

	@Override
	public String deleteEmployee(String id) {
		return employeeRepository.deleteEmployee(id);
	}

}
