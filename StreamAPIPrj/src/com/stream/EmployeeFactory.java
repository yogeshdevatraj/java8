package com.stream;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.summarizingDouble;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Featuring Stream.collect functionality
 * 
 * @author yogesh.devatraj
 *
 */
public class EmployeeFactory {
	
	public static void main(String[] args) {
		/* Finding minimum/maximum employee age by country using usual collection framework */
		avgMaxMinEmployeeAgeByCounter();
		
		/* Same thing implemented using Stream collect */
		avgMaxMinEmployeeAgeByCounter2();
		
		/* Other feature from collect */
		countEmployeeByCountry("US");
		avaerageSalaryForCountry("US");
		maxSalaryForCountry("IND");
		personNameWithMaxSalary("IND");
		groupPersonsByCountry();
		seperatePersonByEmployeementType();
		averageAgeByCountry();
		maxSalaryByCountry();
		maxSalaryByEmployeeTypeByCountry();
	}
	
	
	public static void maxSalaryByCountry() {
		System.out.println("\n## Max Salary by country ## \n");
		Map<String, Optional<Employee>> collect = Employee.EMPLOYEES.stream()
				.collect(Collectors.groupingBy(Employee::getCountry, // First group by employee country
						Collectors.maxBy(Comparator.comparing(Employee::getSalary)))); //find max of grouped records
		
		collect.keySet().stream().forEach(k-> {
			System.out.println(k + " - " + (collect.get(k).isPresent() ? collect.get(k).get().getSalary():0));
		});
	}
	
	public static void maxSalaryByEmployeeTypeByCountry() {
		System.out.println("\n## Max Salary by country ##");
		Map<String, Map<Boolean, DoubleSummaryStatistics>> collect = Employee.EMPLOYEES.stream()
				.collect(Collectors.groupingBy(Employee::getCountry,//First group by employee country
							Collectors.groupingBy(Employee::isPermanent,//partition by employment type
									summarizingDouble(Employee::getSalary))));//summarize record for find all details
		
		collect.keySet().stream().forEach(k-> {
			System.out.println("\n" + k);
			Map<Boolean, DoubleSummaryStatistics> map = collect.get(k);
			map.keySet().stream().forEach(sk -> {
				if(sk)
					System.out.print("\n\tPermanent employees : ");
				else 
					System.out.print("\n\tContracted employees : ");
				System.out.print(map.get(sk).getMax());
			});
			
		});
	}

	public static void averageAgeByCountry() {
		System.out.println("\n\n## Average age by country ##\n");
		Map<String, IntSummaryStatistics> collect = Employee.EMPLOYEES
				.stream()
				.collect(Collectors.groupingBy(Employee::getCountry, Collectors.summarizingInt(Employee::getAge)));
		collect.keySet().stream().forEach(k-> {
			System.out.println(k + " - " + collect.get(k).getAverage());
		});
	}

	public static void countEmployeeByCountry(String country) {
		Long collect = Employee.EMPLOYEES
				.stream()
				.filter(e-> e.getCountry().equals(country)) //filtering by country
				.collect(counting());//Using terminal operator to count all rows
		System.out.println("\nNo of employee from " + country + " = " + collect);
	}
	
	public static void avaerageSalaryForCountry(String country) {
		Double collect = Employee.EMPLOYEES
				.stream()
				.filter(e->e.getCountry().equals(country)) //filtering by country name
				.collect(averagingDouble(Employee::getSalary));//average by employee salary
		
		System.out.println("\nAverage Salary of Emplyee from " + country +" = " + collect);
	}
	
	public static void maxSalaryForCountry(String country) {
		OptionalDouble max = Employee.EMPLOYEES.stream()
				.filter(e->e.getCountry().equals(country))//filter by country
				.mapToDouble(Employee::getSalary)//mapping employee to their salary and generates double stream Stream<Double>
				.max();//max of all double values from stream
		
		System.out.println("\nMax Salary of Emplyee from " + country +" = " + (max.isPresent()? max.getAsDouble():0));
	}
	
	public static void personNameWithMaxSalary(String country) {
		Optional<Employee> collect = Employee.EMPLOYEES.stream()
				.filter(e->e.getCountry().equals(country))//filter by country name
				.collect(Collectors.maxBy(Comparator.comparing(Employee::getSalary)));// calculate max of stream values by their salasy
		
		System.out.println("\nPerson with max salary from " + country +" = " + (collect.isPresent()? collect.get().getfName():0));
	}
	
	public static void groupPersonsByCountry() {
		System.out.print("\n## Group by Country ##");
		Map<String, List<Employee>> collect = Employee.EMPLOYEES
				.stream()
				.collect(Collectors.groupingBy(Employee::getCountry)); //grouping by employee country, equivalent to <code>Collectors.groupingBy(Employee::getCountry, toList())</code>
		
		collect.keySet().stream().forEach(k -> {
			System.out.print("\n\t"+ k + "->");
			System.out.print("\t"+ collect.get(k));
		});
		
	}
	
	
	public static void seperatePersonByEmployeementType() {
		System.out.println("\n\n## Partition by Employement type ##");
		Map<Boolean, List<Employee>> collect = Employee.EMPLOYEES
				.stream()
				.collect(Collectors.partitioningBy(Employee::isPermanent));//Partition the stream values by their employment type
		
		collect.keySet().stream().forEach(k-> {
			if(k)
				System.out.print("\nPermanent employees : ");
			else 
				System.out.print("\nContracted employees : ");
			System.out.print(collect.get(k));
		});
		
	}
	
	public static void avgMaxMinEmployeeAgeByCounter() {
		String AVG = "AVG";
		String MAX = "MAX";
		String MIN = "MIN";
		String SUM = "SUM";
		String COUNT = "COUNT";
		
		List<Employee> employees = Employee.EMPLOYEES;//LIst of all employees, pojos
		// Mapping country name to average, max, min age of employee
		Map<String, Map<String, Double>> summary = new HashMap<>();
		
		for(Employee e: employees) {
			if(summary.containsKey(e.getCountry())){
				Map<String, Double> map = summary.get(e.getCountry());
				map.put(SUM, (map.get(SUM) + e.getAge()));
				map.put(COUNT, map.get(COUNT) +1);
				map.put(MAX, map.get(MAX) < e.getAge() ? e.getAge():map.get(MAX));
				map.put(MIN, map.get(MIN) > e.getAge() ? e.getAge():map.get(MIN));
			} else {
				Map<String, Double> map = new HashMap<>();
				map.put(AVG, Double.valueOf(e.getAge()));
				map.put(MAX,Double.valueOf(e.getAge()));
				map.put(MIN, Double.valueOf(e.getAge()));
				map.put(SUM, Double.valueOf(e.getAge()));
				map.put(COUNT, Double.valueOf(1));
				summary.put(e.getCountry(), map);
			}
		}
		//Printing result
		Set<String> keySet = summary.keySet();
		for(String country: keySet) {
			System.out.println(country);
			System.out.println("\t Average "+summary.get(country).get(SUM)/summary.get(country).get(COUNT));
			System.out.println("\t Max "+summary.get(country).get(MAX));
			System.out.println("\t MIN "+summary.get(country).get(MIN));
		}
	}
	
	public static void avgMaxMinEmployeeAgeByCounter2() {
		Map<String, DoubleSummaryStatistics> collect = Employee.EMPLOYEES
				.stream()
				.collect(Collectors.groupingBy(Employee::getCountry, Collectors.summarizingDouble(Employee::getAge)));
		collect.keySet().stream().forEach(country-> {
			System.out.println(country);
			System.out.println( "\t Average - " + collect.get(country).getAverage());
			System.out.println( "\t Max - " + collect.get(country).getMax());
			System.out.println( "\t Min - " + collect.get(country).getMin());
		});

	}

}
