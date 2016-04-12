package com.stream;

import java.util.ArrayList;
import java.util.List;



public class Employee {
	public static List<Employee> EMPLOYEES = new ArrayList<>();
	
	static {
		EMPLOYEES.add(new Employee(101, 25, "Abc", "Def", "NY", "US", 1000, true));
		EMPLOYEES.add(new Employee(102, 35, "John", "Keh", "SFO", "US", 1800, false));
		EMPLOYEES.add(new Employee(103, 26, "Pqr", "Stv", "SFO", "US", 1500, true));
		EMPLOYEES.add(new Employee(104, 28, "Krish", "D", "NY", "US", 2000, false));
		EMPLOYEES.add(new Employee(105, 45, "Krish", "D", "NY", "US", 11200, true));
		EMPLOYEES.add(new Employee(106, 30, "Shri", "P", "HYD", "IND", 1800, true));
		EMPLOYEES.add(new Employee(107, 30, "Amit", "F", "DEL", "IND", 1450, false));
		EMPLOYEES.add(new Employee(108, 30, "Veena", "G", "DEL", "IND", 2750, true));
	}
	
	private int id, age;
	private String lName, fName, city, country;
	private double salary;
	private boolean isPermanent;//Permanent or on contract 
	
	public boolean isPermanent() {
		return isPermanent;
	}
	
	public boolean isOnContract() {
		return !isPermanent;
	}

	public void setPermanent(boolean isPermanent) {
		this.isPermanent = isPermanent;
	}
	
	public Employee(int id, int age, String fName, String lName, String city,
			String country, double salary, boolean isPermamnent) {
		super();
		//Find any match 
		boolean anyMatch = EMPLOYEES.stream().anyMatch(e-> e.getId() == id);
		if(anyMatch)
			throw new InstantiationError("Dupliacte ID");
		this.id = id;
		this.age = age;
		this.lName = lName;
		this.fName = fName;
		this.city = city;
		this.country = country;
		this.salary = salary;
		this.isPermanent = isPermamnent;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return this.getfName() + " - " + this.getCountry();
	}
	
	@Override
	public int hashCode() {
		return this.id;
	}
	
}