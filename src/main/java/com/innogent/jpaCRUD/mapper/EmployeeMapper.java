package com.innogent.jpaCRUD.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.innogent.jpaCRUD.entities.Employee;
import com.innogent.jpaCRUD.model.EmployeeM;

@Component
public class EmployeeMapper {

    private ModelMapper modelMapper;

    public EmployeeMapper() {
        modelMapper = new ModelMapper();
    }

    public EmployeeM employeeEntityToModel(Employee employee) {
        return modelMapper.map(employee, EmployeeM.class);
    }

    public Employee modelToEntity(EmployeeM employeeM) {
        return modelMapper.map(employeeM, Employee.class);
    }
    
    public List<EmployeeM> mapEntitiesToModel(List<Employee> list){
    	return list.stream().map(this::employeeEntityToModel).collect(Collectors.toList());
    }

    public List<Employee> mapModelToEntities(List<EmployeeM> list){
    	return list.stream().map(this::modelToEntity).collect(Collectors.toList());
    }
}