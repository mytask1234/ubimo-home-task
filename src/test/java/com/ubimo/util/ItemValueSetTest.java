package com.ubimo.util;

import static com.ubimo.util.Constants.ZERO;
import static com.ubimo.util.Constants.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(Lifecycle.PER_CLASS)
public class ItemValueSetTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemValueSetTest.class);

	@ParameterizedTest(name = "#{index} - Test with ItemValueSet : {0}")
	@MethodSource("getItemValueSet")
	void test_without_remove(ItemValueSet<String> itemValueSet) {

		// a->2, b->2, c->1
		itemValueSet.add("a");
		itemValueSet.add("b");
		itemValueSet.add("b");
		itemValueSet.add("c");
		itemValueSet.add("a");

		LOGGER.debug("itemValueSet={}", itemValueSet);

		//-----------------------------------------------------------------

		assertEquals(2, itemValueSet.getValue("a"));
		assertEquals(2, itemValueSet.getValue("b"));
		assertEquals(ONE, itemValueSet.getValue("c"));

		//-----------------------------------------------------------------

		Set<String> actualMaxSet = itemValueSet.getMaxValues();

		Set<String> expectedMaxSet = Stream.of("a", "b").collect(Collectors.toSet());

		assertEquals(expectedMaxSet, actualMaxSet);

		//-----------------------------------------------------------------

		itemValueSet.add("c");

		assertEquals(2, itemValueSet.getValue("c"));

		//-----------------------------------------------------------------

		actualMaxSet = itemValueSet.getMaxValues();

		expectedMaxSet = Stream.of("a", "b", "c").collect(Collectors.toSet());

		assertEquals(expectedMaxSet, actualMaxSet);

		//-----------------------------------------------------------------

		itemValueSet.add("c");

		assertEquals(3, itemValueSet.getValue("c"));

		LOGGER.debug("itemValueSet={}", itemValueSet);

		//-----------------------------------------------------------------

		actualMaxSet = itemValueSet.getMaxValues();

		expectedMaxSet = Stream.of("c").collect(Collectors.toSet());

		assertEquals(expectedMaxSet, actualMaxSet);

		//-----------------------------------------------------------------

		itemValueSet.add("a");

		assertEquals(3, itemValueSet.getValue("a"));

		LOGGER.debug("itemValueSet={}", itemValueSet);

		//-----------------------------------------------------------------

		actualMaxSet = itemValueSet.getMaxValues();

		expectedMaxSet = Stream.of("a", "c").collect(Collectors.toSet());

		assertEquals(expectedMaxSet, actualMaxSet);
	}

	@ParameterizedTest(name = "#{index} - Test with ItemValueSet : {0}")
	@MethodSource("getItemValueSet")
	void test_with_remove(ItemValueSet<String> itemValueSet) {

		// a->1, b->2, c->3
		itemValueSet.add("b");
		itemValueSet.add("c");
		itemValueSet.add("b");
		itemValueSet.add("c");
		itemValueSet.add("a");
		itemValueSet.add("c");

		LOGGER.debug("itemValueSet={}", itemValueSet);

		//-----------------------------------------------------------------

		assertEquals(ONE, itemValueSet.getValue("a"));
		assertEquals(2, itemValueSet.getValue("b"));
		assertEquals(3, itemValueSet.getValue("c"));

		//-----------------------------------------------------------------

		Set<String> actualMaxSet = itemValueSet.getMaxValues();

		Set<String> expectedMaxSet = Stream.of("c").collect(Collectors.toSet());

		assertEquals(expectedMaxSet, actualMaxSet);

		//-----------------------------------------------------------------

		assertEquals(true, itemValueSet.remove("c"));
		assertEquals(ZERO, itemValueSet.getValue("c"));

		LOGGER.debug("itemValueSet={}", itemValueSet);

		assertEquals(false, itemValueSet.remove("c"));
		assertEquals(false, itemValueSet.remove("c"));

		//-----------------------------------------------------------------

		actualMaxSet = itemValueSet.getMaxValues();

		expectedMaxSet = Stream.of("b").collect(Collectors.toSet());

		assertEquals(expectedMaxSet, actualMaxSet);

		//-----------------------------------------------------------------

		actualMaxSet = itemValueSet.getMaxValues();

		expectedMaxSet = Stream.of("b").collect(Collectors.toSet());

		assertEquals(expectedMaxSet, actualMaxSet);

		//-----------------------------------------------------------------

		assertEquals(ONE, itemValueSet.getValue("a"));
		assertEquals(2, itemValueSet.getValue("b"));
		assertEquals(ZERO, itemValueSet.getValue("c"));

		//-----------------------------------------------------------------

		itemValueSet.add("A");
		itemValueSet.add("A");
		itemValueSet.add("A");

		LOGGER.debug("itemValueSet={}", itemValueSet);

		actualMaxSet = itemValueSet.getMaxValues();

		expectedMaxSet = Stream.of("A").collect(Collectors.toSet());

		assertEquals(expectedMaxSet, actualMaxSet);
	}

	@ParameterizedTest(name = "#{index} - Test with ItemValueSet : {0}")
	@MethodSource("getItemValueSet")
	void test_empty(ItemValueSet<String> itemValueSet) {

		LOGGER.debug("itemValueSet={}", itemValueSet);

		assertEquals(false, itemValueSet.remove("c"));
		assertEquals(ZERO, itemValueSet.getValue("c"));
		assertEquals(true, itemValueSet.getMaxValues().isEmpty());

		//-----------------------------------------------------------------

		itemValueSet.add("c");

		LOGGER.debug("itemValueSet={}", itemValueSet);

		assertEquals(ONE, itemValueSet.getValue("c"));

		//-----------------------------------------------------------------

		Set<String> actualMaxSet = itemValueSet.getMaxValues();

		Set<String> expectedMaxSet = Stream.of("c").collect(Collectors.toSet());

		assertEquals(expectedMaxSet, actualMaxSet);

		//-----------------------------------------------------------------

		assertEquals(true, itemValueSet.remove("c"));
		assertEquals(false, itemValueSet.remove("c"));

		LOGGER.debug("itemValueSet={}", itemValueSet);
	}
	
	@ParameterizedTest(name = "#{index} - Test with ItemValueSet : {0}")
	@MethodSource("getItemValueSet")
	void test_remove(ItemValueSet<String> itemValueSet) {

		LOGGER.debug("itemValueSet={}", itemValueSet);

		assertEquals(false, itemValueSet.remove("c"));
		assertEquals(ZERO, itemValueSet.getValue("c"));
		assertEquals(true, itemValueSet.getMaxValues().isEmpty());

		//-----------------------------------------------------------------

		itemValueSet.add("c");

		LOGGER.debug("itemValueSet={}", itemValueSet);

		assertEquals(ONE, itemValueSet.getValue("c"));

		//-----------------------------------------------------------------

		Set<String> actualMaxSet = itemValueSet.getMaxValues();

		Set<String> expectedMaxSet = Stream.of("c").collect(Collectors.toSet());

		assertEquals(expectedMaxSet, actualMaxSet);

		//-----------------------------------------------------------------

		assertEquals(true, itemValueSet.remove("c"));
		assertEquals(false, itemValueSet.remove("c"));

		LOGGER.debug("itemValueSet={}", itemValueSet);
	}

	ItemValueSet<?>[] getItemValueSet() {
		
		return new ItemValueSet[] { new ItemValueSetImpl<>() };
	}
}
