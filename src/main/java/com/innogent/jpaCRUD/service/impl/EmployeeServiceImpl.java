package com.innogent.jpaCRUD.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.innogent.jpaCRUD.entities.Employee;
import com.innogent.jpaCRUD.mapper.EmployeeMapper;
import com.innogent.jpaCRUD.model.EmployeeM;
import com.innogent.jpaCRUD.repository.EmployeeRepository;
import com.innogent.jpaCRUD.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository repository;
	@Autowired
	private EmployeeMapper eMapper;

	public EmployeeServiceImpl(@Autowired EmployeeRepository repository) {
		this.repository = repository;
	}

	// to get all employees from db
	public List<EmployeeM> getAllEmployees() {
		List<Employee> list = repository.findAllEmployees();
		list.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
		return eMapper.mapEntitiesToModel(list);
	}

	@Override
	public EmployeeM addEmployee(EmployeeM e) {
		Employee employee = eMapper.modelToEntity(e);
		Employee result = repository.save(employee);
		return eMapper.employeeEntityToModel(result);
	}

	@Override
	public EmployeeM editEmployeeById(Long id, EmployeeM e) {
		if (repository.existsById(id)) {
			Employee employee = eMapper.modelToEntity(e);
			employee.setId(id);
			Employee result = repository.save(employee);
			return eMapper.employeeEntityToModel(result);
		}
		return null;
	}

	@Override
	public String deleteEmployeeById(Long id) {
		if (repository.findById(id).equals(null)) {
			return "Data Not Found with provided id!";
		}
		repository.deleteById(id);
		return "Data Deleted";
	}

	@Override
	public EmployeeM findEmployeeByName(String name) {
		Employee e = repository.findByName(name);
		return eMapper.employeeEntityToModel(e);
	}

	@Override
	public List<EmployeeM> orderByName(String orderType) {
		if (orderType.equalsIgnoreCase("asc"))
			return eMapper.mapEntitiesToModel(repository.findAll(Sort.by("name")));
		else if (orderType.equalsIgnoreCase("dsc"))
			return eMapper.mapEntitiesToModel(repository.findAll(Sort.by(Direction.DESC, "name")));
		return null;
	}

	@Override
	public List<EmployeeM> findBySalaryGreaterThan(Double salary) {
		return eMapper.mapEntitiesToModel(repository.findBySalaryGreaterThan(salary));
	}

	@Override
	public List<EmployeeM> findBySalaryLessThan(Double salary) {
		return eMapper.mapEntitiesToModel(repository.findBySalaryLessThan(salary));
	}

	// employees whose salary is between given range
	@Override
	public List<EmployeeM> findBySalaryBetween(double minSalary, double maxSalary) {
		return eMapper.mapEntitiesToModel(repository.findBySalaryBetween(minSalary, maxSalary));
	}

	// find by name containing a certain string
	@Override
	public List<EmployeeM> findByNameContaining(String name) {
		return eMapper.mapEntitiesToModel(repository.findByNameContaining(name));
	}

	// add in batch
	@Override
	public String addEmployeeInBatch(List<Employee> list) {
		List<Employee> result = repository.saveAll(list);
		if (result.equals(null)) {
			return "Error, Not able to add data";
		}
		return "Data added";
	}

	// list of department
	@Override
	public List<String> listDepartments() {
		List<Employee> list = repository.findAll();
		return list.stream().map(Employee::getDepartment).distinct().collect(Collectors.toList());
	}

	// find the page and no. of employee in it
	@Override
	public List<EmployeeM> findByPage(Integer pageNumber, Integer pageSize) {
		Pageable p = PageRequest.of(pageNumber, pageSize);
		List<Employee> result = repository.findAll(p).getContent();
		return eMapper.mapEntitiesToModel(result);
	}

	// get no. of employees in department
	@Override
	public Map<String, Integer> groupByDept() {
		List<Object[]> list = repository.groupByDepartment();
		return list.stream().collect(Collectors.toMap(a -> (String) a[0], a -> Integer.valueOf(a[1] + "")));
	}


	//find sum of salaries of each department
	@Override
	public Map<String, Double> salaryOfEachDepartment() {
		List<Object[]> list = repository.getSalaryOfDepartment();
		return list.stream().collect(Collectors.toMap(a -> (String) a[0], a -> Double.valueOf(a[1] + "")));
	}

	@Override
	public EmployeeM getEmployee(Long id) {
		Employee e = repository.findById(id).get();
		System.out.println("e = "+e);
		return eMapper.employeeEntityToModel(e);
	}
}
