package com.ubimo.util;

import static com.ubimo.util.Constants.ZERO;
import static com.ubimo.util.Constants.ONE;
import static com.ubimo.util.Constants.MINUS_ONE;

import java.util.ArrayList;
import java.util.List;

public class MaxItemValueImpl implements MaxItemValue {

	private int currMaxValueIndex;
	private final List<Element> elementList;

	public MaxItemValueImpl() {
		
		currMaxValueIndex = ZERO;
		
		elementList = new ArrayList<>(1001);
		
		elementList.add(new Element(ZERO, true, MINUS_ONE, MINUS_ONE));
	}
	
	@Override
	public int getMaxValue() {
		return currMaxValueIndex;
	}

	@Override
	public void addOrUpdate(int valueIndex) {
		
		if (valueIndex == elementList.size() && valueIndex == currMaxValueIndex + ONE) {
			
			add(valueIndex);
			
		} else if (valueIndex > ZERO && valueIndex < currMaxValueIndex || valueIndex == currMaxValueIndex + ONE) {
			
			update(valueIndex);
			
		} else if (valueIndex != currMaxValueIndex) {
			
			throw new RuntimeException("illegal valueIndex: " + valueIndex);

		}
	}

	private void add(int valueIndex) {
		
		Element previousElement = elementList.get(valueIndex - ONE);
		
		previousElement.setNextValueIndex(valueIndex);
		
		Element nextElement = new Element(valueIndex, true, MINUS_ONE, valueIndex - ONE);
		
		elementList.add(nextElement);
		
		currMaxValueIndex = valueIndex;
	}
	
	private void update(int valueIndex) {
		
		Element currElement = elementList.get(valueIndex);
		
		if (currElement.isExists() == false) {
			
			if (elementList.get(valueIndex - ONE).isExists() == false) {
				
				throw new RuntimeException("illegal valueIndex: " + valueIndex);
			}
			
			currElement.setExists(true);
			
			//--------------------------------------------------------------------
			
			Element previousElement = elementList.get(valueIndex - ONE);
			
			int nextValueIndex = previousElement.getNextValueIndex();
			
			previousElement.setNextValueIndex(valueIndex);
			
			//--------------------------------------------------------------------
			
			currElement.setNextValueIndex(nextValueIndex);
			currElement.setPreviousValueIndex(valueIndex - ONE);
			
			if (nextValueIndex != MINUS_ONE) {
				
				elementList.get(nextValueIndex).setPreviousValueIndex(valueIndex);
			}
			
			if (valueIndex > currMaxValueIndex) {
				
				currMaxValueIndex = valueIndex;
			}
		}
	}
	
	@Override
	public void remove(int valueIndex) {
		
		if (valueIndex > ZERO && valueIndex < currMaxValueIndex) {
			
			Element currElement = elementList.get(valueIndex);
			
			if (currElement.isExists() == true) {
				
				currElement.setExists(false);
				
				//--------------------------------------------------------------------
				
				int previousValueIndex = currElement.getPreviousValueIndex();
				int nextValueIndex     = currElement.getNextValueIndex();
				
				elementList.get(previousValueIndex).setNextValueIndex(nextValueIndex);
				elementList.get(nextValueIndex).setPreviousValueIndex(previousValueIndex);
				
				//--------------------------------------------------------------------
				
				currElement.setNextValueIndex(MINUS_ONE);
				currElement.setPreviousValueIndex(MINUS_ONE);
			}
			
		} else if (valueIndex == currMaxValueIndex) {
			
			Element currElement = elementList.get(valueIndex);
			
			if (currElement.isExists() == true) {
				
				currElement.setExists(false);
				
				//--------------------------------------------------------------------
				
				int previousValueIndex = currElement.getPreviousValueIndex();
				
				elementList.get(previousValueIndex).setNextValueIndex(MINUS_ONE);
				
				//--------------------------------------------------------------------
				
				currElement.setPreviousValueIndex(MINUS_ONE);
				
				//--------------------------------------------------------------------
				
				currMaxValueIndex = previousValueIndex;
			}
			
		} else {
			
			throw new RuntimeException("illegal valueIndex: " + valueIndex);
		}
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("start MaxItemValueImpl [")
		.append("\n")
		.append("currMaxValueIndex=")
		.append(currMaxValueIndex)
		.append(",\n")
		.append("elementList=")
		.append("\n");
		
		elementList.stream().forEachOrdered(element -> sb.append(element.toString()).append("\n"));
		
		sb.append("end MaxItemValueImpl ]");
		
		return sb.toString();
	}

	private static class Element {
		
		private int value;
		private boolean isExists;
		private int nextValueIndex;
		private int previousValueIndex;
		
		public Element(int value, boolean isExists, int nextValueIndex, int previousValueIndex) {
			super();
			this.value = value;
			this.isExists = isExists;
			this.nextValueIndex = nextValueIndex;
			this.previousValueIndex = previousValueIndex;
		}
		
		private boolean isExists() {
			return isExists;
		}
		
		private void setExists(boolean isExists) {
			this.isExists = isExists;
		}
		
		private int getNextValueIndex() {
			return nextValueIndex;
		}
		
		private void setNextValueIndex(int nextValueIndex) {
			this.nextValueIndex = nextValueIndex;
		}
		
		private int getPreviousValueIndex() {
			return previousValueIndex;
		}
		
		private void setPreviousValueIndex(int previousValueIndex) {
			this.previousValueIndex = previousValueIndex;
		}

		@Override
		public String toString() {
			return "Element [value=" + value + ", isExists=" + isExists + ", nextValueIndex=" + nextValueIndex
					+ ", previousValueIndex=" + previousValueIndex + "]";
		}
	}
}
