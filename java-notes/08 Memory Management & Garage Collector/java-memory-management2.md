# Java Memory Management & Garbage Collector - Complete Notes

## Overview
Java Memory Management is a crucial topic that covers how JVM allocates, manages, and deallocates memory for Java applications. Understanding this is essential for both interviews and real-world development.

---

## Table of Contents
1. [Key Concepts & Definitions](#key-concepts--definitions)
2. [Memory Types in Java](#memory-types-in-java)
3. [Stack Memory](#stack-memory)
4. [Heap Memory](#heap-memory)
5. [Reference Types](#reference-types)
6. [Garbage Collection](#garbage-collection)
7. [GC Algorithms](#gc-algorithms)
8. [Examples & Code Snippets](#examples--code-snippets)
9. [Interview Questions](#interview-questions)
10. [Best Practices & Pitfalls](#best-practices--pitfalls)
11. [Real-world Scenarios](#real-world-scenarios)

---

## Key Concepts & Definitions

### Memory Management
- **JVM (Java Virtual Machine)** manages all memory allocation and deallocation
- Memory is divided into **Stack** and **Heap**
- **Automatic Memory Management** - developers don't need to manually free memory
- **Garbage Collection** - automatic cleanup of unreferenced objects

### Memory Allocation
- **Stack Memory**: Stores method frames, local variables, primitive data types, and object references
- **Heap Memory**: Stores actual objects and arrays
- **Metaspace/PermGen**: Stores class metadata, constants, and static variables

---

## Memory Types in Java

```
┌─────────────────────────────────────────┐
│                 JVM MEMORY              │
├─────────────────┬───────────────────────┤
│   STACK MEMORY  │     HEAP MEMORY       │
│                 │                       │
│ • Method Frames │ ┌─── Young Generation │
│ • Local Vars    │ │   ├── Eden Space    │
│ • Primitives    │ │   ├── Survivor S0   │
│ • References    │ │   └── Survivor S1   │
│                 │ └─── Old Generation   │
│ Thread 1 Stack  │     (Tenured)         │
│ Thread 2 Stack  │                       │
│ Thread N Stack  │                       │
└─────────────────┴───────────────────────┘
│              METASPACE (Non-Heap)       │
│          • Class Metadata               │
│          • Static Variables             │
│          • Constants                    │
└─────────────────────────────────────────┘
```

---

## Stack Memory

### Characteristics
- **Thread-specific**: Each thread has its own stack memory
- **LIFO (Last In, First Out)**: Method frames are added/removed in LIFO order
- **Scope-based**: Variables are only visible within their scope
- **Fast access**: Faster than heap memory
- **Limited size**: Smaller than heap memory

### What Stack Stores
1. **Temporary variables** (local variables within methods)
2. **Method frames** (separate memory block for each method call)
3. **Primitive data types** (int, float, double, etc.)
4. **Object references** (not the actual objects)

### Stack Memory Lifecycle
```java
public class StackExample {
    public static void main(String[] args) {        // Main method frame created
        int primitiveVar = 10;                      // Stored in main frame
        Person obj = new Person();                  // Reference stored in main frame
        String literal = "Hello";                   // Reference stored in main frame
        
        MemoryManagement mem = new MemoryManagement();
        mem.test(obj);                              // New method frame created
    }                                               // Main method frame destroyed
    
    public void test(Person personObj) {            // Test method frame created
        Person personObj2 = personObj;              // Reference stored in test frame
        String literal2 = "Hello";                  // Reference stored in test frame
        String obj = new String("Hello");           // Reference stored in test frame
    }                                               // Test method frame destroyed (LIFO)
}
```

### Stack Overflow Error
- Occurs when stack memory is full
- Common causes: infinite recursion, deeply nested method calls

---

## Heap Memory

### Structure
Heap memory is divided into different generations to optimize garbage collection:

#### Young Generation
- **Eden Space**: Where new objects are initially created
- **Survivor Spaces (S0, S1)**: Temporary storage for objects that survive initial GC
- **Minor GC**: Fast garbage collection in young generation

#### Old Generation (Tenured)
- **Long-lived objects**: Objects that have survived multiple GC cycles
- **Major GC**: Slower garbage collection in old generation
- **Promotion**: Objects move from young to old generation after surviving threshold age

#### Metaspace (Non-Heap)
- **Class metadata**: Information about loaded classes
- **Static variables**: Class-level variables
- **Constants**: Final static variables
- **Expandable**: Unlike old PermGen, can grow as needed

### Object Lifecycle in Heap
```
New Object Creation → Eden Space → (GC) → Survivor S0 → (GC) → Survivor S1 
                                    ↓                      ↓
                                 Deleted              Age Increment
                                    ↓                      ↓
                             (if no reference)    (Age reaches threshold)
                                                          ↓
                                                 Old Generation
```

---

## Reference Types

### 1. Strong Reference (Default)
```java
Person person = new Person();  // Strong reference
// Object will NOT be garbage collected as long as strong reference exists
```
- **Characteristics**: Default reference type
- **GC Behavior**: Object is NOT eligible for garbage collection
- **Usage**: Most common reference type in applications

### 2. Weak Reference
```java
import java.lang.ref.WeakReference;

WeakReference<Person> weakRef = new WeakReference<>(new Person());
// Object CAN be garbage collected even if weak reference exists
```
- **Characteristics**: Doesn't prevent garbage collection
- **GC Behavior**: Object can be collected when GC runs
- **Usage**: Caches, listeners to prevent memory leaks

### 3. Soft Reference
```java
import java.lang.ref.SoftReference;

SoftReference<Person> softRef = new SoftReference<>(new Person());
// Object is collected only when memory is critically low
```
- **Characteristics**: Collected only when memory is urgently needed
- **GC Behavior**: GC removes object only during memory pressure
- **Usage**: Memory-sensitive caches

### 4. Phantom Reference
```java
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

ReferenceQueue<Person> queue = new ReferenceQueue<>();
PhantomReference<Person> phantomRef = new PhantomReference<>(new Person(), queue);
// Used for cleanup actions before object is removed
```
- **Characteristics**: Cannot access object through this reference
- **Usage**: Cleanup operations, finalizer alternative

---

## Garbage Collection

### What is Garbage Collection?
- **Automatic memory management**: JVM automatically reclaims memory
- **Unreferenced object cleanup**: Removes objects with no references
- **Periodic execution**: Runs automatically based on memory pressure
- **JVM controlled**: Cannot guarantee when GC will run

### GC Triggering
```java
// Suggesting GC (no guarantee it will run)
System.gc();  // Request to JVM to run GC

// GC automatically triggered when:
// 1. Heap memory is getting full
// 2. Young generation is full (Minor GC)
// 3. Old generation is full (Major GC)
```

### Mark and Sweep Algorithm
1. **Mark Phase**: Identify all objects that are no longer referenced
2. **Sweep Phase**: Remove marked objects and free memory
3. **Compaction** (optional): Move surviving objects together to reduce fragmentation

```
Before GC: [Obj1][DEAD][Obj3][DEAD][Obj5]
After GC:  [Obj1][Obj3][Obj5][FREE_SPACE___]
```

### Minor vs Major GC
| Aspect | Minor GC | Major GC |
|--------|----------|----------|
| **Location** | Young Generation | Old Generation |
| **Frequency** | High (every few seconds) | Low (every few minutes) |
| **Speed** | Fast | Slow |
| **Pause Time** | Short | Long |
| **Objects** | Short-lived | Long-lived |

---

## GC Algorithms

### 1. Serial GC
```bash
-XX:+UseSerialGC
```
- **Threads**: Single thread for GC
- **Pause**: "Stop the world" - all application threads pause
- **Best for**: Small applications, single-core machines
- **Disadvantage**: Slow, high pause times

### 2. Parallel GC (Default in Java 8)
```bash
-XX:+UseParallelGC
```
- **Threads**: Multiple threads for GC
- **Pause**: "Stop the world" but faster due to parallel processing
- **Best for**: Multi-core machines, throughput-focused applications
- **Advantage**: Faster than Serial GC

### 3. Concurrent Mark Sweep (CMS)
```bash
-XX:+UseConcMarkSweepGC
```
- **Threads**: Concurrent GC with application threads
- **Pause**: Minimal pause times
- **Best for**: Low-latency applications
- **Disadvantage**: No memory compaction, higher memory usage

### 4. G1 Garbage Collector
```bash
-XX:+UseG1GC
```
- **Threads**: Concurrent with application threads
- **Pause**: Predictable pause times
- **Best for**: Large heaps (>4GB), low-latency applications
- **Advantage**: Memory compaction, predictable performance

### Performance Comparison
```
Throughput: Parallel GC > Serial GC > G1 GC > CMS GC
Latency:    G1 GC > CMS GC > Parallel GC > Serial GC
Memory:     Serial GC > Parallel GC > G1 GC > CMS GC
```

---

## Examples & Code Snippets

### Memory Allocation Example
```java
public class MemoryExample {
    public static void main(String[] args) {
        // Stack: method frame for main()
        int primitive = 10;              // Stack: primitive value
        Person person = new Person();    // Stack: reference, Heap: Person object
        String literal = "Hello";        // Stack: reference, Heap: String in pool
        
        processData(person);            // Stack: new method frame
    } // Stack: main() frame destroyed
    
    public static void processData(Person p) {
        // Stack: method frame for processData()
        Person localPerson = p;         // Stack: reference (same object)
        String newStr = new String("World"); // Stack: reference, Heap: new String object
    } // Stack: processData() frame destroyed
}
```

### Reference Management Example
```java
public class ReferenceExample {
    public void demonstrateReferences() {
        // Creating objects
        Person person1 = new Person("Alice");    // Strong reference
        Person person2 = new Person("Bob");      // Strong reference
        
        // Removing references
        person1 = null;                          // Object eligible for GC
        person1 = person2;                       // person1 now points to Bob's object
                                                // Alice's object eligible for GC
        
        // Weak reference example
        WeakReference<Person> weakRef = new WeakReference<>(person2);
        person2 = null;                          // Object might be collected
        
        // Check if object still exists
        Person retrieved = weakRef.get();        // Might return null after GC
        if (retrieved != null) {
            System.out.println("Object still alive");
        }
    }
}
```

### GC Monitoring Example
```java
public class GCMonitoring {
    public static void main(String[] args) {
        // Get memory information
        Runtime runtime = Runtime.getRuntime();
        
        System.out.println("Total Memory: " + runtime.totalMemory());
        System.out.println("Free Memory: " + runtime.freeMemory());
        System.out.println("Used Memory: " + (runtime.totalMemory() - runtime.freeMemory()));
        
        // Create many objects to trigger GC
        for (int i = 0; i < 100000; i++) {
            new String("Object " + i);
        }
        
        // Suggest GC (may or may not run)
        System.gc();
        
        System.out.println("After GC:");
        System.out.println("Free Memory: " + runtime.freeMemory());
    }
}
```

---

## Interview Questions

### Basic Questions

**Q1: What are the different types of memory in Java?**
**A:** Java has Stack memory (stores method frames, local variables, primitives, references) and Heap memory (stores objects). Each thread has its own stack, but all threads share the heap.

**Q2: What is the difference between Stack and Heap memory?**
**A:** 
- **Stack**: Thread-specific, stores primitives and references, LIFO, faster access, smaller size
- **Heap**: Shared among threads, stores objects, slower access, larger size, managed by GC

**Q3: What triggers Garbage Collection?**
**A:** GC is triggered when heap memory is filling up, when young generation is full (Minor GC), or when old generation is full (Major GC). It can also be suggested using `System.gc()`.

### Intermediate Questions

**Q4: Explain the different generations in heap memory.**
**A:** 
- **Young Generation**: Eden (new objects), Survivor spaces S0/S1 (objects surviving first GC)
- **Old Generation**: Long-lived objects promoted from young generation
- **Metaspace**: Class metadata, static variables, constants

**Q5: What are the different types of references in Java?**
**A:**
- **Strong**: Default, prevents GC
- **Weak**: Allows GC even if reference exists
- **Soft**: GC only when memory is critically low
- **Phantom**: For cleanup operations, can't access object

**Q6: What is the difference between Minor GC and Major GC?**
**A:**
- **Minor GC**: Fast, frequent, cleans young generation
- **Major GC**: Slow, infrequent, cleans old generation, longer pause times

### Advanced Questions

**Q7: Explain Mark and Sweep algorithm.**
**A:** Two-phase process: Mark phase identifies unreferenced objects, Sweep phase removes them. Optional compaction phase moves surviving objects together to reduce fragmentation.

**Q8: What are the different GC algorithms and when to use them?**
**A:**
- **Serial GC**: Single-threaded, small applications
- **Parallel GC**: Multi-threaded, throughput-focused
- **CMS GC**: Concurrent, low-latency applications
- **G1 GC**: Large heaps, predictable pause times

**Q9: How can you optimize garbage collection?**
**A:** 
- Choose appropriate GC algorithm
- Tune heap size (-Xms, -Xmx)
- Monitor GC logs
- Minimize object creation
- Use appropriate reference types
- Avoid memory leaks

---

## Best Practices & Pitfalls

### Best Practices

#### 1. Memory Management
```java
// Good: Minimize object creation
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append("Item ").append(i);
}

// Bad: Creates many String objects
String result = "";
for (int i = 0; i < 1000; i++) {
    result += "Item " + i;  // Creates new String each time
}
```

#### 2. Reference Management
```java
// Good: Nullify references when done
List<String> largeList = new ArrayList<>();
// ... use the list
largeList = null;  // Make eligible for GC

// Good: Use appropriate reference types
Map<String, String> cache = new WeakHashMap<>();  // Weak references for cache
```

#### 3. GC Tuning
```bash
# Set appropriate heap size
-Xms512m -Xmx2g

# Choose appropriate GC
-XX:+UseG1GC

# Monitor GC
-XX:+PrintGC -XX:+PrintGCDetails
```

### Common Pitfalls

#### 1. Memory Leaks
```java
// Bad: Static collections holding references
public class MemoryLeak {
    private static List<Object> cache = new ArrayList<>();
    
    public void addToCache(Object obj) {
        cache.add(obj);  // Objects never removed!
    }
}

// Good: Proper cleanup
public class ProperCache {
    private Map<String, Object> cache = new WeakHashMap<>();  // Use weak references
    
    public void cleanup() {
        cache.clear();  // Explicit cleanup
    }
}
```

#### 2. Excessive Object Creation
```java
// Bad: Creating objects in loops
for (int i = 0; i < 1000000; i++) {
    String temp = new String("Processing " + i);  // Unnecessary object creation
    process(temp);
}

// Good: Reuse objects
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000000; i++) {
    sb.setLength(0);  // Reset instead of creating new
    sb.append("Processing ").append(i);
    process(sb.toString());
}
```

#### 3. Ignoring GC Impact
```java
// Bad: Frequent System.gc() calls
public void processData() {
    // ... process data
    System.gc();  // Don't force GC frequently
}

// Good: Let JVM manage GC
public void processData() {
    // ... process data
    // Let JVM decide when to run GC
}
```

---

## Real-world Scenarios

### 1. Web Application Memory Optimization
```java
@Controller
public class UserController {
    
    // Bad: Static cache grows indefinitely
    private static Map<String, User> userCache = new HashMap<>();
    
    // Good: Use proper caching with TTL
    @Autowired
    private CacheManager cacheManager;
    
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable String id) {
        return cacheManager.getCache("users").get(id, User.class);
    }
}
```

### 2. Batch Processing Memory Management
```java
public class DataProcessor {
    
    public void processBatchData(List<String> filePaths) {
        for (String filePath : filePaths) {
            // Process one file at a time to manage memory
            processFile(filePath);
            
            // Suggest GC after processing large files
            if (isLargeFile(filePath)) {
                System.gc();  // Optional suggestion
            }
        }
    }
    
    private void processFile(String filePath) {
        List<Record> records = null;
        try {
            records = loadRecords(filePath);
            processRecords(records);
        } finally {
            // Explicit cleanup for large collections
            if (records != null) {
                records.clear();
                records = null;
            }
        }
    }
}
```

### 3. Microservice Memory Tuning
```bash
# Docker container with memory limits
FROM openjdk:11-jre-slim

# Set heap size relative to container memory
ENV JAVA_OPTS="-Xms256m -Xmx1g -XX:+UseG1GC -XX:MaxGCPauseMillis=200"

ENTRYPOINT ["java", "$JAVA_OPTS", "-jar", "app.jar"]
```

### 4. Monitoring and Alerting
```java
public class MemoryMonitor {
    
    private final MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
    
    public void checkMemoryUsage() {
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        
        long used = heapUsage.getUsed();
        long max = heapUsage.getMax();
        double percentage = (double) used / max * 100;
        
        if (percentage > 80) {
            // Alert: High memory usage
            sendAlert("High memory usage: " + percentage + "%");
        }
    }
    
    @Scheduled(fixedRate = 60000)  // Check every minute
    public void monitorMemory() {
        checkMemoryUsage();
    }
}
```

---

## Key Takeaways

1. **Understanding Memory Layout**: Stack for method frames and references, Heap for objects
2. **GC Knowledge**: Know when and how garbage collection works
3. **Reference Types**: Use appropriate reference types based on requirements
4. **Performance Impact**: GC can significantly impact application performance
5. **Monitoring**: Always monitor memory usage in production applications
6. **Tuning**: Choose appropriate GC algorithm based on application requirements

---

## Related Topics for Further Study

- **JVM Internals**: Class loading, bytecode execution
- **Performance Tuning**: JVM parameters, profiling tools
- **Memory Profiling**: Tools like JProfiler, VisualVM
- **OutOfMemoryError**: Types and debugging techniques
- **Memory Leaks**: Detection and prevention strategies

---

*This document provides comprehensive coverage of Java Memory Management and Garbage Collection concepts essential for both interview preparation and practical application development.*