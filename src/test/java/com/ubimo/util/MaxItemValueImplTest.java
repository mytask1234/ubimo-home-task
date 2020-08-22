package com.ubimo.util;

import static com.ubimo.util.Constants.ZERO;
import static com.ubimo.util.Constants.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaxItemValueImplTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MaxItemValueImplTest.class);

	@Test		
	public void test1() {

		MaxItemValue maxItemValue = new MaxItemValueImpl();

		assertEquals(ZERO, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		addOneToTen(maxItemValue);

		assertEquals(10, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		//-----------------------------------------------------------------------

		maxItemValue.remove(4);

		assertEquals(10, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		//-----------------------------------------------------------------------

		maxItemValue.remove(3);

		assertEquals(10, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		//-----------------------------------------------------------------------

		maxItemValue.remove(7);
		maxItemValue.remove(6);
		maxItemValue.remove(8);
		maxItemValue.remove(9);

		assertEquals(10, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		//-----------------------------------------------------------------------

		maxItemValue.remove(10);

		assertEquals(5, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		//-----------------------------------------------------------------------

		maxItemValue.remove(5);

		assertEquals(2, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		//-----------------------------------------------------------------------

		maxItemValue.addOrUpdate(3);

		assertEquals(3, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);
	}

	@Test		
	public void test2() {

		MaxItemValue maxItemValue = new MaxItemValueImpl();

		assertEquals(ZERO, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		addOneToTen(maxItemValue);

		assertEquals(10, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		//-----------------------------------------------------------------------

		maxItemValue.remove(4);

		assertEquals(10, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		//-----------------------------------------------------------------------

		maxItemValue.remove(3);

		assertEquals(10, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		//-----------------------------------------------------------------------

		maxItemValue.remove(7);
		maxItemValue.remove(6);
		maxItemValue.remove(8);
		maxItemValue.remove(10);

		assertEquals(9, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		//-----------------------------------------------------------------------

		maxItemValue.remove(9);

		assertEquals(5, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		//-----------------------------------------------------------------------

		Exception exception = assertThrows(RuntimeException.class, () -> {
			maxItemValue.addOrUpdate(4);
		});

		String expectedMessage = "illegal valueIndex: 4";
		String actualMessage = exception.getMessage();

		assertEquals(expectedMessage, actualMessage);

		//-----------------------------------------------------------------------

		maxItemValue.addOrUpdate(3);

		assertEquals(5, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);
	}

	@Test		
	public void test3() {

		MaxItemValue maxItemValue = new MaxItemValueImpl();

		assertEquals(ZERO, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		addOneToTen(maxItemValue);

		assertEquals(10, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		//-----------------------------------------------------------------------

		maxItemValue.remove(4);
		maxItemValue.remove(5);
		maxItemValue.remove(6);
		maxItemValue.remove(7);

		assertEquals(10, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		//-----------------------------------------------------------------------

		maxItemValue.addOrUpdate(4);

		assertEquals(10, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		//-----------------------------------------------------------------------

		Exception exception = assertThrows(RuntimeException.class, () -> {
			maxItemValue.addOrUpdate(7);
		});

		String expectedMessage = "illegal valueIndex: 7";
		String actualMessage = exception.getMessage();

		assertEquals(expectedMessage, actualMessage);

		//-----------------------------------------------------------------------

		maxItemValue.remove(9);
		maxItemValue.remove(8);
		maxItemValue.remove(10);

		assertEquals(4, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);
	}

	@Test		
	public void test4() {

		MaxItemValue maxItemValue = new MaxItemValueImpl();

		assertEquals(ZERO, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		//-----------------------------------------------------------------------

		for (int i = ZERO ; i < 5 ; i++) {
			
			maxItemValue.addOrUpdate(ONE);

			assertEquals(ONE, maxItemValue.getMaxValue());

			LOGGER.debug("maxItemValue={}", maxItemValue);
		}
	}
	
	@Test		
	public void test5() {
		
		MaxItemValue maxItemValue = new MaxItemValueImpl();

		assertEquals(ZERO, maxItemValue.getMaxValue());

		LOGGER.debug("maxItemValue={}", maxItemValue);

		for (int i = ZERO ; i < 5 ; i++) {

			addOneToTen(maxItemValue);
			
			assertEquals(10, maxItemValue.getMaxValue());

			LOGGER.debug("maxItemValue={}", maxItemValue);
		}
	}
	
	private void addOneToTen(MaxItemValue maxItemValue) {
		
		for (int i = ONE ; i <= 10 ; i++) {

			maxItemValue.addOrUpdate(i);
		}
	}
}
