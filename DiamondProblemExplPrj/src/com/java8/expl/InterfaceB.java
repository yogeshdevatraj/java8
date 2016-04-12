package com.java8.expl;

public interface InterfaceB {
	default long calculateArea(long height, long width) {
		return height * width;
	}
}
