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
