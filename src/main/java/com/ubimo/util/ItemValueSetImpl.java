package com.ubimo.util;

import static com.ubimo.util.Constants.ZERO;
import static com.ubimo.util.Constants.ONE;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ItemValueSetImpl<I> implements ItemValueSet<I> {

	private final MaxItemValue maxItemValue;
	private final Map<I, Integer> itemToValueMap;
	private final Map<Integer, Set<I>> valueToItemsMap;

	public ItemValueSetImpl() {

		maxItemValue = new MaxItemValueImpl();
		itemToValueMap = new HashMap<I, Integer>();
		valueToItemsMap = new HashMap<Integer, Set<I>>();
	}

	@Override
	public void add(I item) {

		Integer value = itemToValueMap.getOrDefault(item, ZERO);

		Integer newValue = value + ONE;

		maxItemValue.addOrUpdate(newValue);

		itemToValueMap.put(item, newValue);

		if (value > ZERO) { // if it is NOT the first time we add that item

			removeFromItemSet(item, value);
		}

		valueToItemsMap.computeIfAbsent(newValue, k -> new HashSet<>()).add(item);
	}

	@Override
	public int getValue(I item) {

		return itemToValueMap.getOrDefault(item, ZERO);
	}

	@Override
	public boolean remove(I item) {

		Integer value = itemToValueMap.remove(item);

		if (value != null) {

			boolean isEmptyItemSet = removeFromItemSet(item, value);

			if (isEmptyItemSet) {

				maxItemValue.remove(value);
			}
		}

		return (value != null);
	}

	@Override
	public Set<I> getMaxValues() {

		int maxValue = maxItemValue.getMaxValue();

		return valueToItemsMap.getOrDefault(maxValue, Collections.emptySet());
	}

	private boolean removeFromItemSet(I item, Integer value) {

		Set<I> items = valueToItemsMap.get(value);

		items.remove(item);

		if (items.isEmpty()) {

			valueToItemsMap.remove(value);

			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ItemValueSetImpl [itemToValueMap=");
		builder.append(itemToValueMap);
		builder.append(", valueToItemsMap=");
		builder.append(valueToItemsMap);
		builder.append(", maxItemValue.getMaxValue()=");
		builder.append(maxItemValue.getMaxValue());
		builder.append("]");
		return builder.toString();
	}
}
