# Java Collections: Deque and List - Comprehensive Notes

## Learning Objectives

After studying these notes, you should be able to:

1. **Understand Deque Interface**: Explain what a double-ended queue is and how it differs from regular queue
2. **Master Deque Methods**: Use all 12 new methods added in Deque interface effectively
3. **Implement Stack using Deque**: Understand how Deque can be used as both queue and stack
4. **Understand List Interface**: Explain list characteristics and index-based operations
5. **Compare ArrayList vs LinkedList**: Choose appropriate implementation based on use case
6. **Handle Thread Safety**: Know when to use synchronized versions of collections
7. **Optimize Performance**: Understand time complexity of different operations

## Key Concepts & Definitions

### Deque (Double-Ended Queue)
- **Definition**: A queue that allows insertion and removal from both ends
- **Stands for**: Double Ended Queue
- **Extends**: Queue interface
- **Key Feature**: Bidirectional operations (front and rear)

### List Interface
- **Definition**: An ordered collection where duplicate values can be stored
- **Key Feature**: Index-based access (0, 1, 2, 3...)
- **Difference from Queue**: Can insert, remove, or access from anywhere using index

## Deque Interface Deep Dive

### Deque vs Regular Queue

| Operation | Regular Queue | Deque |
|-----------|---------------|-------|
| Insert | Only at rear | Front OR rear |
| Remove | Only from front | Front OR rear |
| Access | Front only | Front OR rear |

### Deque Methods (12 New Methods)

#### 1. Insertion Operations
```java
// Add at front
addFirst(element)     // Throws exception on failure
offerFirst(element)   // Returns true/false

// Add at rear
addLast(element)      // Throws exception on failure  
offerLast(element)    // Returns true/false
```

#### 2. Removal Operations
```java
// Remove from front
removeFirst()         // Throws exception if empty
pollFirst()          // Returns null if empty

// Remove from rear
removeLast()         // Throws exception if empty
pollLast()          // Returns null if empty
```

#### 3. Examination Operations
```java
// Examine front
getFirst()           // Throws exception if empty
peekFirst()         // Returns null if empty

// Examine rear
getLast()           // Throws exception if empty  
peekLast()          // Returns null if empty
```

### Legacy Queue Methods Behavior in Deque
```java
add()     → calls addLast()
offer()   → calls offerLast()
remove()  → calls removeFirst()
poll()    → calls pollFirst()
peek()    → calls peekFirst()
element() → calls getFirst()
```

### Using Deque as Stack
```java
// Stack operations using Deque
push(element) → calls addFirst()
pop()        → calls removeFirst()

// Example: Stack behavior
Deque<Integer> stack = new ArrayDeque<>();
stack.addFirst(1);  // [1]
stack.addFirst(2);  // [2, 1]
stack.addFirst(3);  // [3, 2, 1]

stack.removeFirst(); // Returns 3, stack becomes [2, 1]
```

## ArrayDeque Implementation

### Characteristics
- **Concrete class** implementing Deque interface
- **Underlying structure**: Resizable array
- **Initial capacity**: 8 elements
- **Growth strategy**: Doubles when full
- **Thread safety**: Not thread-safe

### Example Usage
```java
// Using as Queue
ArrayDeque<Integer> queue = new ArrayDeque<>();
queue.addLast(1);    // [1]
queue.addLast(5);    // [1, 5]
queue.addLast(10);   // [1, 5, 10]

queue.removeFirst(); // Returns 1, queue becomes [5, 10]

// Using as Stack  
ArrayDeque<Integer> stack = new ArrayDeque<>();
stack.addFirst(1);   // [1]
stack.addFirst(5);   // [5, 1]
stack.addFirst(10);  // [10, 5, 1]

stack.removeFirst(); // Returns 10, stack becomes [5, 1]
```

### Time Complexity
| Operation | Time Complexity | Notes |
|-----------|----------------|-------|
| Insert (first/last) | O(1) amortized | O(n) when resize needed |
| Delete (first/last) | O(1) | Always constant |
| Search/Access | O(1) | For peek operations |
| Space | O(n) | n = number of elements |

### Thread-Safe Alternative
```java
// Instead of ArrayDeque, use:
ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
```

## List Interface Deep Dive

### List vs Queue Comparison

| Aspect | Queue | List |
|--------|-------|------|
| Access Pattern | FIFO (front/rear only) | Random access by index |
| Insertion | Front or rear | Anywhere by index |
| Removal | Front or rear | Anywhere by index |
| Use Case | Sequential processing | Random access needed |

### List Interface Methods

#### Index-based Operations
```java
// Insertion
add(index, element)           // Insert at specific position
addAll(index, collection)     // Insert collection at position

// Modification  
set(index, element)          // Replace element at index
replaceAll(UnaryOperator)    // Transform all elements

// Retrieval
get(index)                   // Get element at index
indexOf(object)              // First occurrence index
lastIndexOf(object)          // Last occurrence index

// Removal
remove(index)                // Remove element at index

// Sublisting
subList(fromIndex, toIndex)  // Returns view of portion

// Sorting
sort(Comparator)             // Sort using comparator
```

#### List Iterator
```java
ListIterator<T> listIterator()         // From beginning
ListIterator<T> listIterator(index)    // From specific index

// ListIterator methods (extends Iterator)
// Forward: hasNext(), next()
// Backward: hasPrevious(), previous()  
// Modification: add(), set(), remove()
// Index info: nextIndex(), previousIndex()
```

### Add vs Set Difference

```java
List<Integer> list = new ArrayList<>();
list.add(0, 100);  // [100] - inserts at index 0
list.add(1, 200);  // [100, 200] - inserts at index 1
list.add(1, 300);  // [100, 300, 200] - shifts 200 to right

list.set(1, 400);  // [100, 400, 200] - replaces 300 with 400
```

## ArrayList Implementation

### Characteristics
- **Underlying structure**: Dynamic array
- **Index-based**: Fast random access
- **Ordered**: Maintains insertion order
- **Duplicates**: Allows duplicate elements
- **Null values**: Allows null elements
- **Thread safety**: Not thread-safe

### Example Operations
```java
List<Integer> list = new ArrayList<>();

// Index-based insertion
list.add(0, 100);  // Insert at index 0
list.add(1, 200);  // Insert at index 1
list.add(3, 300);  // Insert at index 3
list.add(2, 300);  // Insert at index 2, shifts right

// Collection insertion  
List<Integer> list2 = Arrays.asList(400, 500, 600);
list.addAll(2, list2); // Insert collection at index 2

// Functional operations
list.replaceAll(x -> x * -1);  // Multiply all by -1
list.sort((a, b) -> a - b);    // Sort ascending

// Access operations
int element = list.get(2);      // Get element at index 2
list.set(2, -4000);            // Replace element at index 2
list.remove(2);                // Remove element at index 2

// Search operations  
int firstIndex = list.indexOf(-200);     // First occurrence
int lastIndex = list.lastIndexOf(-200);  // Last occurrence
```

### ListIterator Example
```java
List<Integer> list = Arrays.asList(-600, -500, -300, -200, -100);

// Forward iteration
ListIterator<Integer> iter = list.listIterator();
while (iter.hasNext()) {
    int value = iter.next();
    System.out.println("Value: " + value);
    System.out.println("Next index: " + iter.nextIndex());
    System.out.println("Previous index: " + iter.previousIndex());
    
    if (value == -200) {
        iter.add(-100); // Insert before next element
    }
}

// Backward iteration  
ListIterator<Integer> backIter = list.listIterator(list.size());
while (backIter.hasPrevious()) {
    int value = backIter.previous();
    if (value == -100) {
        backIter.set(-50); // Replace last returned element
    }
}
```

### Time Complexity
| Operation | Time Complexity | Explanation |
|-----------|----------------|-------------|
| Insert at end | O(1) amortized | O(n) when resize needed |
| Insert at index | O(n) | Must shift elements right |
| Delete by index | O(n) | Must shift elements left |
| Access by index | O(1) | Direct array access |
| Search by value | O(n) | Linear scan required |
| Space | O(n) | Array storage |

### Thread-Safe Alternative
```java
// Instead of ArrayList, use:
CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
```

## LinkedList Implementation

### Characteristics
- **Implements**: Both List and Deque interfaces
- **Underlying structure**: Doubly-linked list
- **Dual functionality**: Can work as list or deque
- **Node-based**: Each element is a separate node

### Dual Interface Usage
```java
LinkedList<Integer> linkedList = new LinkedList<>();

// Using as Deque
linkedList.addLast(200);   // [200]
linkedList.addLast(300);   // [200, 300]  
linkedList.addLast(400);   // [200, 300, 400]
linkedList.addFirst(100);  // [100, 200, 300, 400]

int first = linkedList.getFirst(); // Returns 100

// Using as List (index-based)
linkedList.add(0, 100);    // Add at index 0
linkedList.add(2, 400);    // Add at index 2  
linkedList.add(1, 200);    // Add at index 1

int element = linkedList.get(1); // Get element at index 1
```

### Time Complexity
| Operation | Time Complexity | Explanation |
|-----------|----------------|-------------|
| Insert at start/end | O(1) | Direct node manipulation |
| Insert at index | O(n) | O(n) for lookup + O(1) for insertion |
| Delete from start/end | O(1) | Direct node manipulation |
| Delete by index | O(n) | O(n) for lookup + O(1) for deletion |
| Search by value | O(n) | Must traverse nodes |
| Access by index | O(n) | Must traverse to index |

### LinkedList vs ArrayList

| Aspect | ArrayList | LinkedList |
|--------|-----------|------------|
| Random Access | O(1) - Fast | O(n) - Slow |
| Sequential Access | Good | Excellent |
| Insert/Delete at ends | O(1)* | O(1) |
| Insert/Delete in middle | O(n) - shifting | O(n) - traversal |
| Memory Overhead | Lower | Higher (node pointers) |
| Cache Performance | Better | Worse |

## Vector and Stack

### Vector
- **Legacy class** from Java 1.0
- **Thread-safe** version of ArrayList
- **Synchronized methods** - less efficient
- **Growth strategy**: Doubles when full

```java
Vector<Integer> vector = new Vector<>();
vector.add(100);  // All methods are synchronized
vector.add(200);
```

### Stack
- **Extends Vector** - inherits thread safety
- **LIFO operations**: push(), pop(), peek()
- **Legacy class** - prefer ArrayDeque for new code

```java
Stack<Integer> stack = new Stack<>();
stack.push(1);   // Add to top
stack.push(2);   // Add to top  
stack.push(3);   // Add to top

int top = stack.pop(); // Remove and return top (3)
int peek = stack.peek(); // Return top without removing (2)
```

## Thread-Safe Collections Summary

| Non-Thread-Safe | Thread-Safe Alternative |
|-----------------|------------------------|
| ArrayList | CopyOnWriteArrayList |
| LinkedList | Collections.synchronizedList() |
| ArrayDeque | ConcurrentLinkedDeque |
| Vector | Already thread-safe |
| Stack | Already thread-safe |

## Common Interview Questions

### Q1: What is the difference between Deque and Queue?
**Answer**: Queue allows insertion at rear and removal from front only (FIFO). Deque allows insertion and removal from both ends, making it more flexible.

### Q2: How can you implement Stack using Deque?
**Answer**: Use addFirst() for push and removeFirst() for pop operations, or use the built-in push() and pop() methods.

### Q3: ArrayList vs LinkedList - when to use which?
**Answer**: 
- Use ArrayList for frequent random access and when memory is a concern
- Use LinkedList for frequent insertions/deletions at ends and when you don't need random access

### Q4: What is the difference between add() and set() in List?
**Answer**: 
- add(index, element): Inserts element at index, shifts existing elements right
- set(index, element): Replaces element at index, no shifting

### Q5: Why is Vector synchronized but ArrayList is not?
**Answer**: Vector is a legacy class from Java 1.0 when thread safety was built-in. ArrayList was introduced later with better performance by removing synchronization.

## Hands-on Exercises

### Exercise 1: Deque as Stack and Queue
```java
// Implement a method that uses same Deque as both stack and queue
public static void demonstrateDeque() {
    Deque<Integer> deque = new ArrayDeque<>();
    
    // Use as queue: add rear, remove front
    deque.addLast(1);
    deque.addLast(2);
    deque.addLast(3);
    
    System.out.println("Queue behavior:");
    while (!deque.isEmpty()) {
        System.out.println(deque.removeFirst());
    }
    
    // Use as stack: add front, remove front
    deque.addFirst(1);
    deque.addFirst(2);
    deque.addFirst(3);
    
    System.out.println("Stack behavior:");
    while (!deque.isEmpty()) {
        System.out.println(deque.removeFirst());
    }
}
```

### Exercise 2: List Operations
```java
// Practice all list operations
public static void practiceListOperations() {
    List<String> list = new ArrayList<>();
    
    // Add elements
    list.add("A");
    list.add("B");
    list.add("D");
    list.add(2, "C"); // Insert C at index 2
    
    // Replace all vowels with uppercase
    list.replaceAll(s -> s.matches("[aeiouAEIOU]") ? s.toUpperCase() : s);
    
    // Sort the list
    list.sort(String::compareTo);
    
    // Use ListIterator to traverse backwards
    ListIterator<String> iter = list.listIterator(list.size());
    while (iter.hasPrevious()) {
        System.out.println(iter.previous());
    }
}
```

### Exercise 3: Performance Comparison
```java
// Compare ArrayList vs LinkedList performance
public static void comparePerformance() {
    int n = 100000;
    
    // ArrayList - random access
    List<Integer> arrayList = new ArrayList<>();
    for (int i = 0; i < n; i++) arrayList.add(i);
    
    long start = System.nanoTime();
    for (int i = 0; i < 1000; i++) {
        arrayList.get(n/2); // Random access
    }
    long arrayListTime = System.nanoTime() - start;
    
    // LinkedList - random access  
    List<Integer> linkedList = new LinkedList<>();
    for (int i = 0; i < n; i++) linkedList.add(i);
    
    start = System.nanoTime();
    for (int i = 0; i < 1000; i++) {
        linkedList.get(n/2); // Random access
    }
    long linkedListTime = System.nanoTime() - start;
    
    System.out.println("ArrayList random access: " + arrayListTime + " ns");
    System.out.println("LinkedList random access: " + linkedListTime + " ns");
}
```

## Real-World Use Cases

### 1. Browser History (Deque)
```java
public class BrowserHistory {
    private Deque<String> history = new ArrayDeque<>();
    
    public void visit(String url) {
        history.addLast(url);
    }
    
    public String back() {
        if (history.size() > 1) {
            history.removeLast();
            return history.peekLast();
        }
        return history.peekLast();
    }
    
    public String forward() {
        // Implementation for forward navigation
        return null;
    }
}
```

### 2. Shopping Cart (ArrayList)
```java
public class ShoppingCart {
    private List<Product> items = new ArrayList<>();
    
    public void addItem(Product product) {
        items.add(product);
    }
    
    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }
    
    public Product getItem(int index) {
        return items.get(index);
    }
    
    public double getTotalPrice() {
        return items.stream()
                   .mapToDouble(Product::getPrice)
                   .sum();
    }
}
```

### 3. Music Playlist (LinkedList)
```java
public class MusicPlaylist {
    private LinkedList<Song> playlist = new LinkedList<>();
    private int currentIndex = 0;
    
    public void addSong(Song song) {
        playlist.add(song);
    }
    
    public void addSongAtPosition(int index, Song song) {
        playlist.add(index, song);
    }
    
    public Song nextSong() {
        if (currentIndex < playlist.size() - 1) {
            currentIndex++;
            return playlist.get(currentIndex);
        }
        return null;
    }
    
    public Song previousSong() {
        if (currentIndex > 0) {
            currentIndex--;
            return playlist.get(currentIndex);
        }
        return null;
    }
}
```

## Best Practices

### 1. Choose Right Collection
```java
// ✅ Good: Use ArrayList for frequent random access
List<String> usernames = new ArrayList<>();  // Frequent get(index)

// ✅ Good: Use LinkedList for frequent insertions at ends
List<LogEntry> logs = new LinkedList<>();   // Frequent add to end

// ✅ Good: Use ArrayDeque instead of Stack
Deque<Integer> stack = new ArrayDeque<>();  // Better performance than Stack
```

### 2. Thread Safety
```java
// ❌ Avoid: Using non-thread-safe collections in multi-threaded environment
List<String> list = new ArrayList<>(); // Not thread-safe

// ✅ Good: Use thread-safe alternatives
List<String> safeList = new CopyOnWriteArrayList<>();
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
```

### 3. Capacity Planning
```java
// ✅ Good: Initialize with expected size
List<Integer> list = new ArrayList<>(1000); // Avoid resizing

// ❌ Avoid: Default capacity when size is known
List<Integer> list2 = new ArrayList<>(); // Will resize multiple times
```

## Common Pitfalls

### 1. ConcurrentModificationException
```java
// ❌ Wrong: Modifying list while iterating
List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
for (Integer num : list) {
    if (num % 2 == 0) {
        list.remove(num); // Throws ConcurrentModificationException
    }
}

// ✅ Correct: Use iterator's remove method
Iterator<Integer> iter = list.iterator();
while (iter.hasNext()) {
    if (iter.next() % 2 == 0) {
        iter.remove(); // Safe removal
    }
}
```

### 2. Wrong Collection Choice
```java
// ❌ Wrong: Using LinkedList for random access
List<String> data = new LinkedList<>(); // Poor choice
for (int i = 0; i < 1000; i++) {
    data.get(i); // O(n) for each access = O(n²) total
}

// ✅ Correct: Use ArrayList for random access  
List<String> data2 = new ArrayList<>(); // Better choice
for (int i = 0; i < 1000; i++) {
    data2.get(i); // O(1) for each access = O(n) total
}
```

### 3. Null Pointer Issues
```java
// ❌ Risky: Not checking for null
List<String> list = new ArrayList<>();
list.add(null);
list.add("test");

String first = list.get(0).toLowerCase(); // NullPointerException

// ✅ Safe: Check for null
String first = list.get(0);
if (first != null) {
    first = first.toLowerCase();
}
```

## Memory Hooks / Mnemonics

### DEQUE = "Deck of Cards"
- **D**ouble **E**nded **QUE**ue
- Like a deck of cards: can add/remove from top OR bottom
- **A**dd/**O**ffer: "**A**lways **O**n both sides"
- **R**emove/**P**oll: "**R**emove from **P**olar ends"

### LIST = "Library Index System"
- **L**inear **I**ndexed **S**torage **T**ype  
- Each book (element) has a number (index)
- Can insert anywhere: "**I**nsert **A**nywhere by **I**ndex"
- Can access directly: "**R**andom **A**ccess **R**eady"

### ArrayList vs LinkedList
- **Array**List = **Array** = **Fast Access** (like array index)
- **Linked**List = **Linked** nodes = **Fast Insert/Delete** at ends
- "**A**rray for **A**ccess, **L**inked for **L**oading at ends"

## Quick Revision Cheat Sheet

### Deque Quick Reference
| Operation | Method | Exception? | Returns |
|-----------|--------|------------|---------|
| Insert Front | addFirst() | Yes | void |
|              | offerFirst() | No | boolean |
| Insert Rear  | addLast() | Yes | void |
|              | offerLast() | No | boolean |
| Remove Front | removeFirst() | Yes | element |
|              | pollFirst() | No | element/null |
| Remove Rear  | removeLast() | Yes | element |
|              | pollLast() | No | element/null |
| Peek Front   | getFirst() | Yes | element |
|              | peekFirst() | No | element/null |
| Peek Rear    | getLast() | Yes | element |
|              | peekLast() | No | element/null |

### List Quick Reference
| Operation | ArrayList | LinkedList | Notes |
|-----------|-----------|------------|-------|
| get(index) | O(1) | O(n) | Random access |
| add(element) | O(1)* | O(1) | At end |
| add(index, element) | O(n) | O(n) | In middle |
| remove(index) | O(n) | O(n) | Shifting required |
| contains(element) | O(n) | O(n) | Linear search |

### Thread-Safe Alternatives
```java
ArrayList       → CopyOnWriteArrayList
LinkedList      → Collections.synchronizedList()
ArrayDeque      → ConcurrentLinkedDeque
Vector/Stack    → Already thread-safe
```

### Time Complexity Summary
| Collection | Access | Insert | Delete | Search | Space |
|------------|--------|---------|---------|---------|-------|
| ArrayList | O(1) | O(1)* | O(n) | O(n) | O(n) |
| LinkedList | O(n) | O(1) | O(1) | O(n) | O(n) |
| ArrayDeque | O(1) | O(1)* | O(1) | O(n) | O(n) |
| Vector | O(1) | O(1)* | O(n) | O(n) | O(n) |
| Stack | - | O(1) | O(1) | O(n) | O(n) |

*Amortized time complexity (occasionally O(n) during resize)

---

## Summary

**Deque** provides double-ended queue functionality with 12 new methods for bidirectional operations, making it suitable for both queue and stack implementations. **ArrayDeque** is the preferred implementation due to better performance than legacy Stack class.

**List** interface adds index-based operations to collections, with **ArrayList** optimal for random access and **LinkedList** better for frequent insertions/deletions at ends. Choose based on access patterns and performance requirements.

**Thread safety** requires careful consideration - use concurrent collections or synchronized wrappers in multi-threaded environments, but be aware of performance trade-offs.

Understanding these collections and their characteristics is crucial for writing efficient Java applications and succeeding in technical interviews.