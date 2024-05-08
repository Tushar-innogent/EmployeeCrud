package com.innogent.jpaCRUD.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innogent.jpaCRUD.entities.Employee;
import com.innogent.jpaCRUD.exceptionHandler.GlobalExceptionHandler;
import com.innogent.jpaCRUD.model.EmployeeM;
import com.innogent.jpaCRUD.service.EmployeeService;

@CrossOrigin
@RestController
public class MainController {
	
	private EmployeeService employeeService;
	private GlobalExceptionHandler exceptionHandler;
	
	public MainController(@Autowired EmployeeService service, @Autowired GlobalExceptionHandler exceptionHandler) {
		this.employeeService = service;
		this.exceptionHandler = exceptionHandler;
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getEmployees(){
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAllEmployees());
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getEmpById(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployee(id));
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addEmployee(@RequestBody EmployeeM employeeM){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.addEmployee(employeeM));
		}
		catch(IllegalArgumentException exception) {
			return exceptionHandler.illegalArgumentException(exception);
		}
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<?> editEmployee(@PathVariable Long id, @RequestBody EmployeeM employee){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.editEmployeeById(id, employee));
		}
		catch(NullPointerException exception) {
			return exceptionHandler.handleNullPointerException(exception);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.deleteEmployeeById(id));
	}
	
	@GetMapping("/findbyname/{name}")
	public ResponseEntity<?> findEmployeeByName(@PathVariable String name){
		try {
			EmployeeM em = employeeService.findEmployeeByName(name);
			return ResponseEntity.status(HttpStatus.OK).body(em);
		}
		catch(NullPointerException exception) {	
            return exceptionHandler.handleNullPointerException(exception);
        }
	}
	
	//order list based on orderType asc/dsc
	@GetMapping("/orderbyname/{orderType}")
	public ResponseEntity<?> getDataOrderByName(@PathVariable String orderType) {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.orderByName(orderType));
	}
	
	//employees whoes salary is greater than 
	@GetMapping("salarygreaterthan/{salary}")
	public ResponseEntity<?> getEmployeesSalaryGreaterThan(@PathVariable Double salary) {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.findBySalaryGreaterThan(salary));
	}

	//employees whose salary is less than 
	@GetMapping("salarylessthan/{salary}")
	public ResponseEntity<?> getEmployeesSalaryLessThan(@PathVariable Double salary) {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.findBySalaryLessThan(salary));
	}
	
	//employees whose salary is between given range
	@GetMapping("salarybetween")
	public ResponseEntity<?> getBySalaryBetween(@RequestParam Double minSalary, @RequestParam Double maxSalary) {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.findBySalaryBetween(minSalary, maxSalary));
	}
	
	//find by name containing a certain string
	@GetMapping("namecontainning/{name}")
	public ResponseEntity<?> getByNameBetween(@PathVariable String name) {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.findByNameContaining(name));
	}
	
	// add batch of employees
	@PostMapping("/addall")
	public ResponseEntity<String> addMultipleEMployees(@RequestBody List<Employee> list) {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.addEmployeeInBatch(list));
	}
	
	//list departments
	@GetMapping("/listdept")
	public ResponseEntity<List<String>> listDepartments() {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.listDepartments());
	}

	//pagination find by give parameters
	@GetMapping("/page")
	public ResponseEntity<?> pagination(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.findByPage(pageNumber, pageSize));
	}
	
	//number of employees in each department
	@GetMapping("/countOfEmp")
	public ResponseEntity<Map<String, Integer>> groupByDept() {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.groupByDept());
	}

	//Sum of salary in each department
	@GetMapping("/getSalaryByDept")
	public ResponseEntity<Map<String, Double>> sumOfSalaryOfDept() {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.salaryOfEachDepartment());
	}
}
