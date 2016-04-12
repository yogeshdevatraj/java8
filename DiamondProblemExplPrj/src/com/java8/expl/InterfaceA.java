package com.java8.expl;

public interface InterfaceA {
	default long calculateArea(long height, long width) {
		return height * width;
	}
}
