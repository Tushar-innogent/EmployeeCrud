package com.innogent.jpaCRUD.service;

import java.util.List;
import java.util.Map;

import com.innogent.jpaCRUD.entities.Employee;
import com.innogent.jpaCRUD.model.EmployeeM;

public interface IEmployeeService {

	//Get All Employees
	public List<Employee> getAllEmployees();
//	public List<EmployeeM> getAllEmployees();
	
	//Add a Employee
	public Employee addEmployee(Employee e);
	
	//Edit a Employee detail
	public Employee editEmployeeById(Long id, Employee e);
	
	//Delete a Employee
	public String deleteEmployeeById(Long id);
	
	//To get a Employee by name
	public EmployeeM findEmployeeByName(String name);
	
	//Order by name in asc/dsc
	public List<EmployeeM> orderByName(String orderType);
	
	//find employees whoes salary is greater than provided salary
	public List<EmployeeM> findBySalaryGreaterThan(Double salary);
	
	//find employees whoes salary is less than provided salary
	public List<EmployeeM> findBySalaryLessThan(Double salary);
	
	//employees whose salary is between given range
	public List<EmployeeM> findBySalaryBetween(double minSalary, double maxSalary);
	
	//find by name containing a certain string
	public List<EmployeeM> findByNameContaining(String name);
	
	//add in batch
	public String addEmployeeInBatch(List<Employee> list);
	
	// list of departments
	public List<String> listDepartments();
		
	// pagination - find first 1 to 5
	public List<EmployeeM> findByPage(Integer pageNumber, Integer pageSize);
	
	//find no. of employee in each department
	public Map<String, Integer> groupByDept();
	
	//find sum of salaries of each department
	public Map<String, Double> salaryOfEachDepartment();

	public Employee getEmployee(Long id);
}
