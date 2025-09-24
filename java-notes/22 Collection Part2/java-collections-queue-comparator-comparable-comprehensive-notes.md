# Java Collections Part 2: Queue, PriorityQueue, Comparator & Comparable

## Learning Objectives
By the end of these notes, you should be able to:
- ✅ Understand Queue interface and its methods
- ✅ Implement and use PriorityQueue with min/max heap behavior
- ✅ Master Comparator interface and create custom sorting logic
- ✅ Implement Comparable interface in custom classes
- ✅ Differentiate between Comparator vs Comparable
- ✅ Sort collections of primitive and custom objects
- ✅ Handle common sorting scenarios in interviews

---

## Key Concepts & Definitions

### 1. Queue Interface
- **Queue**: Interface that extends Collection, follows FIFO (First In, First Out) principle
- **Front**: Where elements are removed from
- **Rear**: Where elements are added to
- **Exception**: PriorityQueue doesn't follow strict FIFO - uses heap ordering

### 2. PriorityQueue
- **Min Heap**: Default behavior, smallest element at top
- **Max Heap**: Achieved using custom Comparator, largest element at top
- **Natural Ordering**: Default sorting behavior (ascending for integers, lexicographic for strings)

### 3. Comparator vs Comparable
- **Comparator**: External sorting logic, functional interface with compare() method
- **Comparable**: Internal sorting logic, implemented by class itself with compareTo() method

---

## Step-by-Step Explanations

### Queue Interface Methods

#### Core Queue Methods
```java
// Adding elements
boolean add(E e)        // Throws exception if insertion fails
boolean offer(E e)      // Returns false if insertion fails

// Removing elements  
E remove()             // Throws exception if queue is empty
E poll()               // Returns null if queue is empty

// Examining elements
E element()            // Throws exception if queue is empty  
E peek()               // Returns null if queue is empty
```

#### Method Comparison Table
| Operation | Exception Method | Safe Method |
|-----------|-----------------|-------------|
| Insert | `add()` | `offer()` |
| Remove | `remove()` | `poll()` |
| Examine | `element()` | `peek()` |

### PriorityQueue Implementation

#### Min Heap (Default)
```java
PriorityQueue<Integer> minQueue = new PriorityQueue<>();
minQueue.add(5);
minQueue.add(2);
minQueue.add(8);
minQueue.add(1);

// Internal heap structure: [1, 2, 8, 5]
// Poll order: 1, 2, 5, 8
```

#### Max Heap (Using Comparator)
```java
PriorityQueue<Integer> maxQueue = new PriorityQueue<>((a, b) -> b - a);
maxQueue.add(5);
maxQueue.add(2);
maxQueue.add(8);
maxQueue.add(1);

// Internal heap structure: [8, 5, 2, 1]  
// Poll order: 8, 5, 2, 1
```

### Time Complexity
| Operation | Time Complexity |
|-----------|----------------|
| `add()` / `offer()` | O(log n) |
| `peek()` | O(1) |
| `poll()` / `remove()` | O(log n) |
| `remove(Object)` | O(n) |

---

## Comparator Interface

### Basic Syntax
```java
// Lambda expression (preferred)
Comparator<Integer> ascendingOrder = (a, b) -> a - b;
Comparator<Integer> descendingOrder = (a, b) -> b - a;

// Method reference
Arrays.sort(array, Integer::compare);

// Anonymous class
Comparator<Integer> comp = new Comparator<Integer>() {
    @Override
    public int compare(Integer a, Integer b) {
        return a - b;  // ascending
    }
};
```

### Return Values Logic
```java
compare(a, b) returns:
// Positive (> 0): a > b, swap needed for ascending order
// Zero (= 0): a == b, no swap needed  
// Negative (< 0): a < b, no swap needed for ascending order
```

### Custom Object Sorting
```java
class Car {
    String name;
    String type;
    
    public Car(String name, String type) {
        this.name = name;
        this.type = type;
    }
}

// Sort by name (ascending)
Comparator<Car> byName = (c1, c2) -> c1.name.compareTo(c2.name);

// Sort by type (descending)  
Comparator<Car> byTypeDesc = (c1, c2) -> c2.type.compareTo(c1.type);

// Usage
Car[] cars = {new Car("SUV", "Petrol"), new Car("Sedan", "Diesel")};
Arrays.sort(cars, byName);
```

---

## Comparable Interface

### Implementation Requirements
```java
class Car implements Comparable<Car> {
    String name;
    String type;
    
    public Car(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    @Override
    public int compareTo(Car other) {
        return this.name.compareTo(other.name); // Sort by name ascending
    }
}

// Usage - no comparator needed
Car[] cars = {new Car("SUV", "Petrol"), new Car("Sedan", "Diesel")};
Arrays.sort(cars); // Uses natural ordering via compareTo()
```

### Built-in Comparable Examples
```java
// Integer implements Comparable
Integer[] nums = {5, 2, 8, 1};
Arrays.sort(nums); // Uses Integer's compareTo() -> [1, 2, 5, 8]

// String implements Comparable  
String[] words = {"banana", "apple", "cherry"};
Arrays.sort(words); // Lexicographic order -> [apple, banana, cherry]
```

---

## Examples with Code Snippets

### Example 1: PriorityQueue Basic Operations
```java
public class QueueExample {
    public static void main(String[] args) {
        // Min heap
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        // Add elements
        pq.offer(5);
        pq.offer(2);
        pq.offer(8);
        pq.offer(1);
        
        System.out.println("Queue: " + pq); // [1, 2, 8, 5]
        
        // Remove elements in priority order
        while (!pq.isEmpty()) {
            System.out.println("Polled: " + pq.poll());
        }
        // Output: 1, 2, 5, 8
    }
}
```

### Example 2: Custom Object with Comparator
```java
class Student {
    String name;
    int grade;
    
    public Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }
    
    @Override
    public String toString() {
        return name + "(" + grade + ")";
    }
}

public class ComparatorExample {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("Alice", 85),
            new Student("Bob", 92),
            new Student("Charlie", 78)
        );
        
        // Sort by grade (descending)
        Collections.sort(students, (s1, s2) -> s2.grade - s1.grade);
        System.out.println(students); // [Bob(92), Alice(85), Charlie(78)]
        
        // Sort by name (ascending)
        Collections.sort(students, (s1, s2) -> s1.name.compareTo(s2.name));
        System.out.println(students); // [Alice(85), Bob(92), Charlie(78)]
    }
}
```

### Example 3: Comparable Implementation
```java
class Employee implements Comparable<Employee> {
    String name;
    int salary;
    
    public Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }
    
    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.salary, other.salary); // Sort by salary
    }
    
    @Override
    public String toString() {
        return name + "(" + salary + ")";
    }
}

public class ComparableExample {
    public static void main(String[] args) {
        Employee[] employees = {
            new Employee("John", 50000),
            new Employee("Jane", 60000), 
            new Employee("Bob", 45000)
        };
        
        Arrays.sort(employees); // Uses compareTo()
        System.out.println(Arrays.toString(employees));
        // [Bob(45000), John(50000), Jane(60000)]
    }
}
```

---

## Diagrams

### Queue Structure
```
Front -> [1] [2] [3] [4] <- Rear
         ↑               ↑
    Remove here      Add here
```

### PriorityQueue Heap Structure
```
Min Heap:           Max Heap:
     1                   8
   /   \               /   \
  2     8             5     2  
 /                   /
5                   1

Poll order: 1,2,5,8  Poll order: 8,5,2,1
```

### Comparator vs Comparable Flow
```
┌─────────────────┐    ┌──────────────────┐
│   Comparator    │    │    Comparable    │
│   (External)    │    │   (Internal)     │
└─────────────────┘    └──────────────────┘
         │                       │
         ▼                       ▼
┌─────────────────┐    ┌──────────────────┐
│ compare(a, b)   │    │ a.compareTo(b)   │
│ Multiple ways   │    │ Single way       │
│ Flexible        │    │ Natural order    │
└─────────────────┘    └──────────────────┘
```

---

## Common Interview Questions

### Q1: What's the difference between Comparator and Comparable?
**Answer:**
- **Comparable**: Implemented by the class itself, provides natural ordering, single sorting logic
- **Comparator**: External sorting logic, can have multiple implementations, more flexible

### Q2: How do you create a max heap using PriorityQueue?
**Answer:**
```java
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
// or
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
```

### Q3: What happens if you don't implement Comparable and try to sort?
**Answer:**
Throws `ClassCastException` at runtime because sorting algorithms need a way to compare objects.

### Q4: Can you use both Comparator and Comparable together?
**Answer:**
Yes, if both are present, Comparator takes precedence over Comparable's natural ordering.

### Q5: What's the time complexity of PriorityQueue operations?
**Answer:**
- Insert/Delete: O(log n)
- Peek: O(1)
- Search: O(n)

---

## Hands-on Exercises

### Exercise 1: Priority Task Scheduler
```java
// Create a task scheduler that processes tasks by priority
class Task {
    String name;
    int priority; // 1 = highest, 5 = lowest
    
    // TODO: Implement Comparable to sort by priority
}

// Create PriorityQueue and add tasks with different priorities
// Process tasks in priority order
```

### Exercise 2: Student Grade Sorter
```java
// Sort students by multiple criteria:
// 1. Primary: Grade (descending)  
// 2. Secondary: Name (ascending)

// Use Comparator.comparing() and thenComparing()
```

### Exercise 3: Top K Elements
```java
// Find top K largest elements from an array using PriorityQueue
public int[] topKLargest(int[] nums, int k) {
    // Use min heap of size k
    // TODO: Implement
}
```

---

## Real-world Use Cases

### 1. Task Scheduling Systems
```java
// Operating system process scheduling
PriorityQueue<Process> scheduler = new PriorityQueue<>(
    (p1, p2) -> Integer.compare(p1.priority, p2.priority)
);
```

### 2. Dijkstra's Algorithm
```java
// Shortest path algorithm using min heap
PriorityQueue<Node> pq = new PriorityQueue<>(
    (n1, n2) -> Integer.compare(n1.distance, n2.distance)
);
```

### 3. Top K Problems
```java
// Find top K frequent elements, K closest points, etc.
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
```

### 4. Merge K Sorted Arrays/Lists
```java
// LeetCode problem: merge k sorted lists
PriorityQueue<ListNode> pq = new PriorityQueue<>(
    (a, b) -> Integer.compare(a.val, b.val)
);
```

---

## Best Practices & Common Pitfalls

### ✅ Best Practices
1. **Use Lambda expressions** for simple comparators
2. **Implement Comparable** for natural ordering of your classes
3. **Use method references** when possible: `Integer::compare`
4. **Chain comparators** using `thenComparing()`
5. **Handle null values** explicitly in custom comparators

### ❌ Common Pitfalls
1. **Integer overflow** in subtraction-based comparators:
   ```java
   // BAD: Can overflow
   (a, b) -> a - b
   
   // GOOD: Safe comparison
   (a, b) -> Integer.compare(a, b)
   ```

2. **Forgetting null checks**:
   ```java
   // BAD: NullPointerException risk
   (s1, s2) -> s1.name.compareTo(s2.name)
   
   // GOOD: Null-safe
   (s1, s2) -> Objects.compare(s1.name, s2.name, String::compareTo)
   ```

3. **Inconsistent comparison logic** leading to unpredictable sorting

### 🐛 Debugging Tips
- **Print intermediate steps** during sorting
- **Use consistent comparison logic** (transitive property)
- **Test with edge cases**: empty collections, single elements, duplicates
- **Verify heap property** in PriorityQueue with custom comparators

---

## Comparisons with Related Concepts

### Collections.sort() vs Arrays.sort()
| Collections.sort() | Arrays.sort() |
|-------------------|---------------|
| Works with Lists | Works with arrays |
| Uses TimSort | Uses DualPivotQuicksort |
| Stable sort | Stable for objects, unstable for primitives |

### TreeSet vs PriorityQueue
| TreeSet | PriorityQueue |
|---------|---------------|
| Sorted Set | Heap-based Queue |
| No duplicates | Allows duplicates |
| O(log n) access by value | O(1) access to min/max |
| Maintains full sorted order | Only guarantees heap property |

---

## Memory hooks & Mnemonics

### 🧠 Memory Hooks
1. **"Compare EXTERNAL, CompareTo INTERNAL"** - Comparator is external, compareTo is internal
2. **"Comparator = Flexible, Comparable = Fixed"** - Multiple vs single sorting logic
3. **"Poll from Priority = Peek at Priority"** - Both return the priority element
4. **"Add/Remove throw, Offer/Poll don't throw"** - Exception vs safe methods
5. **"Min by default, Max by reverse"** - PriorityQueue defaults to min heap

### 🎯 Quick Rules
- **Positive return = Swap needed** (for ascending order)
- **Zero return = Equal elements**  
- **Negative return = No swap needed** (for ascending order)
- **a - b = Ascending**, **b - a = Descending**

---

## Cheat Sheet / Quick Revision

### Queue Methods Quick Reference
```java
// Adding (prefer offer over add)
queue.offer(element);    // Safe - returns false on failure
queue.add(element);      // Throws exception on failure

// Removing (prefer poll over remove)  
queue.poll();            // Safe - returns null if empty
queue.remove();          // Throws exception if empty

// Examining (prefer peek over element)
queue.peek();            // Safe - returns null if empty  
queue.element();         // Throws exception if empty
```

### PriorityQueue Quick Setup
```java
// Min heap (default)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max heap  
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
// or
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

// Custom objects
PriorityQueue<Student> pq = new PriorityQueue<>(
    Comparator.comparing(Student::getGrade).reversed()
);
```

### Comparator Quick Patterns
```java
// Basic comparisons
Comparator.comparing(Person::getName)                    // By name
Comparator.comparing(Person::getAge).reversed()          // By age desc
Comparator.comparing(Person::getName).thenComparing(Person::getAge) // Multi-level

// Null-safe comparisons
Comparator.comparing(Person::getName, Comparator.nullsLast(String::compareTo))
```

### Comparable Implementation Template
```java
class MyClass implements Comparable<MyClass> {
    @Override
    public int compareTo(MyClass other) {
        // Compare by primary field
        int result = this.primaryField.compareTo(other.primaryField);
        if (result != 0) return result;
        
        // Compare by secondary field if needed
        return this.secondaryField.compareTo(other.secondaryField);
    }
}
```

### Interview Ready Code Snippets
```java
// Top K elements using min heap
public List<Integer> topKElements(int[] nums, int k) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    for (int num : nums) {
        minHeap.offer(num);
        if (minHeap.size() > k) {
            minHeap.poll();
        }
    }
    return new ArrayList<>(minHeap);
}

// Sort by multiple criteria
students.sort(
    Comparator.comparing(Student::getGrade).reversed()
              .thenComparing(Student::getName)
);
```

---

## Summary
This comprehensive guide covers Queue interface, PriorityQueue implementation, and the crucial concepts of Comparator vs Comparable. Master these concepts as they're fundamental for collections manipulation and frequently appear in technical interviews. Practice with custom objects and complex sorting scenarios to build confidence.

**Key Takeaway**: Comparator provides external flexibility, Comparable provides internal natural ordering. PriorityQueue is your go-to for heap-based problems in interviews.