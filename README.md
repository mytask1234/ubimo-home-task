# The Home Task / Home Assignment
Implement a data structure that manages a set of items with their corresponding integer values,
providing the following API:  
1. **add(item)** : Adds the item to the collection (with a value 1), or increments the value if it
already exists in the collection.
2. **getValue(item)** : Returns the current value of a given item.
3. **remove(item)** : Removes the item from the collection. A subsequent call to
getValue(item) should return 0. Returns true if and only if the item was found and
removed.
4. **getMaxValues()** : Returns the set of items having the maximum value.  

**Example**:  
Assuming the following sequence of calls (on string items in this case):  
add(“a”)  
add(”b”)  
add(”b”)  
add(”c”)  
add(”a”)  
A subsequent call to getMaxValues() should return {“a”,”b”}, since both “a” and “b” have a value
of 2, which is maximal.

### Important guidelines
- **Code should be well designed and clean, as if it was intended for production.**
- Try taking reasonable decisions regarding the missing specifications, if any.
- **Time/Space complexities matter.** An optimal solution will be granted full score for
complexity. Try to optimize as much as possible, and document the time/space
complexities.  

# The Solution in this project
**Time complexity**:  
add(item) - O(1)  
getValue(item) - O(1)  
remove(item) - O(1)  
getMaxValues() - O(1)  

**Space complexity**: O(n)  

Hope it helps you :)  
