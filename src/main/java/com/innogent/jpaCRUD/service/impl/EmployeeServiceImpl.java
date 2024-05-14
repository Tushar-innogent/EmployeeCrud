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

import com.innogent.jpaCRUD.entities.City;
import com.innogent.jpaCRUD.entities.Employee;
import com.innogent.jpaCRUD.mapper.EmployeeMapper;
import com.innogent.jpaCRUD.model.EmployeeM;
import com.innogent.jpaCRUD.repository.ICityRepository;
import com.innogent.jpaCRUD.repository.ICountryRepository;
import com.innogent.jpaCRUD.repository.IEmployeeRepository;
import com.innogent.jpaCRUD.repository.IStateRepository;
import com.innogent.jpaCRUD.service.ICityService;
import com.innogent.jpaCRUD.service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
	private IEmployeeRepository employeeRepository;
	@Autowired
	private ICountryRepository countryRepository;
	@Autowired
	private IStateRepository stateRepository;
	@Autowired
	private ICityRepository cityRepository;
	@Autowired
	private ICityService cityService;
	@Autowired
	private EmployeeMapper eMapper;

	public EmployeeServiceImpl(@Autowired IEmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	// to get all employees from db
	public List<Employee> getAllEmployees() {
		List<Employee> list = employeeRepository.findAllEmployees();
		list.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
		return list;
	}
//	public List<EmployeeM> getAllEmployees() {
//		List<Employee> list = employeeRepository.findAllEmployees();
//		list.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
//		return eMapper.mapEntitiesToModel(list);
//	}

	@Override
	public Employee addEmployee(Employee e) {
		City existingCity = cityService.addCity(e.getCity());
		e.setCity(existingCity);
		return employeeRepository.save(e);
	}

	@Override
	public Employee editEmployeeById(Long id, Employee e) {
		if (id!=null && employeeRepository.existsById(id)) {
			e.setId(id);
			return this.addEmployee(e);
//			Employee employee = eMapper.modelToEntity(e);
//			employee.setId(id);
//			Employee result = employeeRepository.save(employee);
//			return eMapper.employeeEntityToModel(result);
		}
		return null;
	}

	@Override
	public String deleteEmployeeById(Long id) {
		if (employeeRepository.findById(id).equals(null)) {
			return "Data Not Found with provided id!";
		}
		employeeRepository.deleteById(id);
		return "Data Deleted";
	}

	@Override
	public EmployeeM findEmployeeByName(String name) {
		Employee e = employeeRepository.findByName(name);
		return eMapper.employeeEntityToModel(e);
	}

	@Override
	public List<EmployeeM> orderByName(String orderType) {
		if (orderType.equalsIgnoreCase("asc"))
			return eMapper.mapEntitiesToModel(employeeRepository.findAll(Sort.by("name")));
		else if (orderType.equalsIgnoreCase("dsc"))
			return eMapper.mapEntitiesToModel(employeeRepository.findAll(Sort.by(Direction.DESC, "name")));
		return null;
	}

	@Override
	public List<EmployeeM> findBySalaryGreaterThan(Double salary) {
		return eMapper.mapEntitiesToModel(employeeRepository.findBySalaryGreaterThan(salary));
	}

	@Override
	public List<EmployeeM> findBySalaryLessThan(Double salary) {
		return eMapper.mapEntitiesToModel(employeeRepository.findBySalaryLessThan(salary));
	}

	// employees whose salary is between given range
	@Override
	public List<EmployeeM> findBySalaryBetween(double minSalary, double maxSalary) {
		return eMapper.mapEntitiesToModel(employeeRepository.findBySalaryBetween(minSalary, maxSalary));
	}

	// find by name containing a certain string
	@Override
	public List<EmployeeM> findByNameContaining(String name) {
		return eMapper.mapEntitiesToModel(employeeRepository.findByNameContaining(name));
	}

	// add in batch
	@Override
	public String addEmployeeInBatch(List<Employee> list) {
		List<Employee> result = employeeRepository.saveAll(list);
		if (result.equals(null)) {
			return "Error, Not able to add data";
		}
		return "Data added";
	}

	// list of department
	@Override
	public List<String> listDepartments() {
		List<Employee> list = employeeRepository.findAll();
		return list.stream().map(Employee::getDepartment).distinct().collect(Collectors.toList());
	}

	// find the page and no. of employee in it
	@Override
	public List<EmployeeM> findByPage(Integer pageNumber, Integer pageSize) {
		Pageable p = PageRequest.of(pageNumber, pageSize);
		List<Employee> result = employeeRepository.findAll(p).getContent();
		return eMapper.mapEntitiesToModel(result);
	}

	// get no. of employees in department
	@Override
	public Map<String, Integer> groupByDept() {
		List<Object[]> list = employeeRepository.groupByDepartment();
		return list.stream().collect(Collectors.toMap(a -> (String) a[0], a -> Integer.valueOf(a[1] + "")));
	}


	//find sum of salaries of each department
	@Override
	public Map<String, Double> salaryOfEachDepartment() {
		List<Object[]> list = employeeRepository.getSalaryOfDepartment();
		return list.stream().collect(Collectors.toMap(a -> (String) a[0], a -> Double.valueOf(a[1] + "")));
	}

	@Override
	public Employee getEmployee(Long id) {
		Employee e = employeeRepository.findById(id).get();
		return e;
	}
}
