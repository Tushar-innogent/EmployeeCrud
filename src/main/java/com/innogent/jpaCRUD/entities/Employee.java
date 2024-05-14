package com.innogent.jpaCRUD.entities;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "E_EMPLOYEE")
@Getter
@Setter
public class Employee implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	@Column
	private String name;
	@Column
	private String department;
	@Column
	private Double salary; 
	@Column
	private String address;
	
	@ManyToOne(targetEntity = City.class, fetch = FetchType.EAGER)
	@JoinColumn(name="CITY_ID", referencedColumnName = "cityId")
	private City city;
}
