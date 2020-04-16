package com.zero.activeMQ;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public interface AppService {
	static void hha() {
		List<String> list = Arrays.<String>asList(new String[] { "", "" });
		list.forEach(System.out::println);
	}

	static <T, SOURCE extends Collection<T>, DEST extends Collection<T>> DEST transferElements(SOURCE sourceCollection,
			Supplier<DEST> collectionFactory) {

		DEST result = collectionFactory.get();
		for (T t : sourceCollection) {
			result.add(t);
		}
		return result;
	}
}
