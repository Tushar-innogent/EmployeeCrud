package com.innogent.jpaCRUD.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.innogent.jpaCRUD.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, PagingAndSortingRepository<Employee, Long>{
	
	static final String salaryoOfEachDept = "SELECT e.department, sum(e.salary) Salary_sum FROM Employee e GROUP BY e.department";
	static final String countEmpInEachDept = "SELECT DEPARTMENT, COUNT(*) EMPLOYEE_COUNT FROM EMPLOYEE GROUP BY DEPARTMENT";
	
	//by name
	public Employee findByName(String name);
	
	//employees by salary greater than 
	public List<Employee> findBySalaryGreaterThan(Double salary);
	
	//employees by salary less than
	public List<Employee> findBySalaryLessThan(Double salary);
	
	//employees whose salary is between given range
	public List<Employee> findBySalaryBetween(double minSalary, double maxSalary);
	
	//find by name containing a certain string
	public List<Employee> findByNameContaining(String name);
	
	@Query(value = countEmpInEachDept, nativeQuery = true)
	public List<Object[]>  groupByDepartment();
	
	//find sum of salaries of each department
	@Query(value = salaryoOfEachDept)
	public List<Object[]>  getSalaryOfDepartment();
	
	//get employee using jpql queries
	@Query("SELECT e FROM Employee e")
	public List<Employee> findAllEmployees();
}
