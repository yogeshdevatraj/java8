package com.stream;

import java.util.stream.Stream;

public class StreamCreation {
	
	public static void main(String[] args) {
		generateSerier(5);
	}
	
	public static void generateSerier(int limit) {
		Stream<Integer> iterate = Stream.iterate(10, n -> n + 10);
		Integer sum = iterate.limit(limit).reduce(0, Integer::sum);
		System.out.println(sum);
	}
}
