# Algorithm and Data Structure
## About
   通过自己重复造一些轮子,更好的理解算法和数据结构
## Algorithmic
### Sort
- [冒泡排序（Bubble Sort）](https://github.com/leomy/algorithm/blob/master/src/main/java/com/leo/util/algorithm/Arrays.java#L226)
- [选择排序（Select Sort）](https://github.com/leomy/algorithm/blob/master/src/main/java/com/leo/util/algorithm/Arrays.java#L249)
- [插入排序（Insert Sort）](https://github.com/leomy/algorithm/blob/master/src/main/java/com/leo/util/algorithm/Arrays.java#L279)
- [希尔排序（Shell Sort）](https://github.com/leomy/algorithm/blob/master/src/main/java/com/leo/util/algorithm/Arrays.java#L338)
- [归并排序（Merge Sort）](https://github.com/leomy/algorithm/blob/master/src/main/java/com/leo/util/algorithm/Arrays.java#L449)
- [快速排序（Quick Sort）](https://github.com/leomy/algorithm/blob/master/src/main/java/com/leo/util/algorithm/Arrays.java#L525)

    |排序类型|平均情况|最好情况|最坏情况|辅助空间|稳定性|
    |:-|:-|:-|:-|:-|:-|
    |冒泡排序|O(n<sup>2</sup>)|O(n)|o(n<sup>2</sup>)|O(1)|稳定|
    |选择排序|O(n<sup>2</sup>)|O(n<sup>2</sup>)|O(n<sup>2</sup>)|O(1)|不稳定|
    |直接插入排序|O(n<sup>2</sup>)|O(n)|O(n<sup>2</sup>)|O(1)|稳定|
    |折半插入排序|O(n<sup>2</sup>)|O(n)|O(n<sup>2</sup>)|O(1)|稳定|
    |希尔排序|O(n<sup>1.3</sup>)|O(nlogn)|O(n<sup>2</sup>)|O(1)|不稳定|
    |归并排序|O(nlog<sub>2</sub>n)|O(nlog<sub>2</sub>n)|O(n<sup>2</sup>)|O(n)|稳定|
    |快速排序|O(nlog<sub>2</sub>n)|O(nlog<sub>2</sub>)|O(n<sup>2</sup>n)|O(nlog<sub>2</sub>n)|不稳定|
    |堆排序|O(nlog<sub>2</sub>n)|O(nlog<sub>2</sub>n)|O(nlog<sub>2</sub>n)|O(1)|不稳定|
    |计数排序|O(n+k)|O(n+k)|O(n+k)|O(k)|稳定|
    |桶排序|O(n+k)|O(n+k)|O(n<sup>2</sup>)|O(n+k)|(不)稳定|
    |基数排序|O(d(n+k))|O(d(n+k))|O(d(n+k))|O(n+kd)|稳定|
### Search
- [Binary Search](https://github.com/leomy/algorithm/blob/master/src/main/java/com/leo/util/algorithm/Arrays.java#L29)
- Balanced Search Tree(Binary Search Tree)
    - [2-3 Tree](https://github.com/leomy/algorithm/blob/master/src/main/java/com/leo/util/algorithm/btree/TwoThreeTree.java)
    - Red Black Tree
- Hash Table
    - [HashMap](https://github.com/leomy/algorithm/blob/master/src/main/java/com/leo/util/datastructure/map/HashMap.java)
## Data Structure
### List
- [ArrayList](https://github.com/leomy/algorithm/blob/master/src/main/java/com/leo/util/datastructure/list/ArrayList.java)
- [LinkedList](https://github.com/leomy/algorithm/blob/master/src/main/java/com/leo/util/datastructure/list/LinkedList.java)
### Map
- [HashMap](https://github.com/leomy/algorithm/blob/master/src/main/java/com/leo/util/datastructure/map/HashMap.java)
### Set
- [HashSet](https://github.com/leomy/algorithm/blob/master/src/main/java/com/leo/util/datastructure/set/HashSet.java)
- TreeSet
