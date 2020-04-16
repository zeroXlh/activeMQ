package com.zero.activeMQ;

import java.util.Arrays;
import java.util.Spliterator;

public class Test {

	public static void main(String[] args) {
		Integer[] intArray = { 0,1, 2, 3, 4, 4097 };
		// String[] strArray = {"","",""};
		Arrays.asList(intArray).forEach(i -> System.out.println(i & Spliterator.CONCURRENT));
		// Arrays.asList(intArray).forEach(i -> System.out.println(i |
		// Spliterator.CONCURRENT));
		// System.out.println(Integer.MAX_VALUE & Spliterator.CONCURRENT);
		// System.out.println(Spliterator.CONCURRENT);
		// System.out.println(Integer.toBinaryString(Spliterator.CONCURRENT));
		// System.out.println(Integer.toHexString(8));
		int a = 2 & 3;
		System.out.println(a);
		System.out.println(3 ^ 5);

		System.out.println(((0 | Spliterator.SIZED | Spliterator.SUBSIZED)
				& Spliterator.SORTED) == Spliterator.SORTED);
	}
}
