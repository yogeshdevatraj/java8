package com.java8.expl;

public class ImplClass implements InterfaceA, InterfaceB {
	/* Compiler forces to provide implementation for ambiguous 
	 * default methods from multiple imterfaces */
	public long calculateArea(long height, long width) {
		/*Programmer have option to either select one the default 
		 * implementation
		 * */
		//return InterfaceA.super.calculateArea(height, width);
		//OR
		//return InterfaceB.super.calculateArea(height, width);
		//OR
		//Own implementation 
		return height * width * 2;
	}
	public static void main(String[] args) {
		ImplClass obj = new ImplClass();
		System.out.println(obj.calculateArea(10, 10));
	}
}
