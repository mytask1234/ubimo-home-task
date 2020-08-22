package com.ubimo.util;

import java.util.Set;

public interface ItemValueSet<I> {

	void add(I item);
	int getValue(I item);
	boolean remove(I item);
	Set<I> getMaxValues();
}
