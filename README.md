# Skip List Implementation in Java (Generic)

![Skip List](https://upload.wikimedia.org/wikipedia/commons/8/86/Skip_list.svg)

This repo contains a Java implementation of a Skip List data structure that works with generic data types. This data structure provides searching, insertion, and deletion operations with an average time complexity of O(log n).

## Features

- Generic data type support: You can use the Skip List with any data type.
- Efficient searching, insertion, and deletion operations.
- Height rebalancing: The Skip List includes a function to rebalance/rerandomize the heights of the nodes automatically as needed or manually(call reBalance())

## Usage

To use the Skip List in your Java project, follow these steps:

1. Clone this repository or download the source code.
2. Import the `SkipListSet.java` class into your project.
3. Create a new instance of the `SkipListSet` class, specifying the data type you want to use (e.g., `SkipListSet<Integer> skipList = new SkipListSet<>();`).

Here's an example of how to create and use a basic Skip List with integer values:

```java
// Create a Skip List for integers
SkipListSet<Integer> skipList = new SkipListSet<>();

// Insert values
skipList.add(10);
skipList.add(20);
skipList.add(30);

// Print the Skip List Size
skipList.size();

// Search for a value
boolean found = skipList.contains(20);

// Delete a value
skipList.remove(30);

// Rebalance the Skip List
skipList.reBalance();

// Print the Skip List Size
skipList.size();
