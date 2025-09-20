# Java Memory Management & Garbage Collection - Comprehensive Notes

## Learning Objectives
After studying these notes, you should be able to:
- Understand the structure and organization of Java memory (Stack vs Heap)
- Explain how objects and variables are allocated in memory
- Describe the garbage collection process and different GC algorithms
- Differentiate between types of object references (Strong, Weak, Soft)
- Analyze memory lifecycle in Java applications
- Optimize Java applications for better memory performance
- Answer technical interview questions about Java memory management

---

## Key Concepts & Definitions

### Memory Management Fundamentals
- **JVM (Java Virtual Machine)**: Manages memory allocation and deallocation automatically
- **RAM Memory Division**: JVM divides memory into two main areas: Stack and Heap
- **Automatic Memory Management**: No manual memory allocation/deallocation required (unlike C/C++)

### Stack Memory
- **Purpose**: Stores temporary variables, method parameters, and object references
- **Structure**: LIFO (Last In, First Out) - like a stack of plates
- **Scope**: Variables are only visible within their scope (method/block)
- **Thread-specific**: Each thread has its own stack memory
- **Size**: Smaller compared to heap memory

### Heap Memory
- **Purpose**: Stores actual objects and instance variables
- **Sharing**: All threads share the same heap memory
- **Size**: Larger than stack memory
- **Management**: Managed by Garbage Collector

---

## Step-by-Step Memory Allocation Process

### Example Code Analysis
```java
public class MemoryManagement {
    public static void main(String[] args) {
        // 1. Primitive variable
        int primitiveVar = 10;
        
        // 2. Object creation
        Person personObj = new Person();
        
        // 3. String literal
        String stringLiteral = "24";
        
        // 4. Method invocation
        MemoryManagement memObj = new MemoryManagement();
        memObj.memoryManagementTest(personObj);
    }
    
    public void memoryManagementTest(Person personObj) {
        // 5. Another object reference
        Person personObj2 = personObj;
        
        // 6. Another string literal
        String stringLiteral2 = "24";
        
        // 7. String object (not literal)
        String stringLiteral3 = new String("24");
    }
}
```

### Memory Allocation Steps

#### Step 1: Main Method Execution
```
Stack Memory:
┌─────────────────────────┐
│ main() method frame     │
│ ├─ primitiveVar: 10     │
│ ├─ personObj: ref→heap  │
│ ├─ stringLiteral: ref→  │
│ │   string pool          │
│ └─ memObj: ref→heap     │
└─────────────────────────┘

Heap Memory:
┌─────────────────────────┐
│ Person object           │
│ MemoryManagement object │
│                         │
│ String Pool:            │
│ └─ "24"                 │
└─────────────────────────┘
```

#### Step 2: Method Call
```
Stack Memory:
┌─────────────────────────────┐
│ memoryManagementTest() frame│
│ ├─ personObj: ref→Person    │
│ ├─ personObj2: ref→Person   │ (same object)
│ ├─ stringLiteral2: ref→"24"│ (string pool)
│ └─ stringLiteral3: ref→new │
│    String object            │
├─────────────────────────────┤
│ main() method frame         │
│ ├─ primitiveVar: 10         │
│ ├─ personObj: ref→heap      │
│ ├─ stringLiteral: ref→pool  │
│ └─ memObj: ref→heap         │
└─────────────────────────────┘
```

#### Step 3: Method Completion (LIFO Cleanup)
When `memoryManagementTest()` completes:
1. Remove method frame from stack
2. Delete all local variables and references
3. Objects in heap may become eligible for garbage collection

---

## Heap Memory Structure

### Heap Division
```
┌─────────────────────────────────────────────────────────┐
│                    HEAP MEMORY                          │
├─────────────────────────────────┬───────────────────────┤
│         YOUNG GENERATION        │   OLD GENERATION      │
├─────────┬─────────┬─────────────┤   (Tenured Space)     │
│  EDEN   │   S0    │     S1      │                       │
│ SPACE   │(Survivor│ (Survivor   │                       │
│         │ Space 0)│  Space 1)   │                       │
└─────────┴─────────┴─────────────┴───────────────────────┘

┌─────────────────────────────────────────────────────────┐
│                   NON-HEAP MEMORY                       │
│                    METASPACE                            │
│    (Class metadata, constants, static variables)        │
└─────────────────────────────────────────────────────────┘
```

### Object Lifecycle in Heap

#### Phase 1: Object Creation
- All new objects are created in **Eden Space**
- Eden fills up with newly created objects

#### Phase 2: First Garbage Collection (Minor GC)
1. **Mark**: Identify unreferenced objects
2. **Sweep**: Remove unreferenced objects
3. **Move**: Surviving objects move to Survivor Space (S0)
4. **Age**: Objects get age = 1

#### Phase 3: Subsequent GC Cycles
- Surviving objects alternate between S0 and S1
- Age increments with each GC cycle
- When age reaches threshold (default: 15), objects promote to Old Generation

#### Phase 4: Major GC
- Garbage collection in Old Generation
- Less frequent but more time-consuming
- Handles long-lived objects

---

## Types of Object References

### 1. Strong Reference (Default)
```java
Person person = new Person(); // Strong reference
```
- **Behavior**: Object will NOT be garbage collected while strong reference exists
- **Usage**: Most common type of reference
- **GC Impact**: Prevents garbage collection

### 2. Weak Reference
```java
import java.lang.ref.WeakReference;

WeakReference<Person> weakPerson = new WeakReference<>(new Person());
Person person = weakPerson.get(); // May return null after GC
```
- **Behavior**: Object can be garbage collected even if weak reference exists
- **Usage**: Caches, memory-sensitive applications
- **GC Impact**: Allows garbage collection

### 3. Soft Reference
```java
import java.lang.ref.SoftReference;

SoftReference<Person> softPerson = new SoftReference<>(new Person());
```
- **Behavior**: Object collected only when memory is critically low
- **Usage**: Memory-sensitive caches
- **GC Impact**: Allows GC only under memory pressure

### 4. Phantom Reference
```java
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

ReferenceQueue<Person> queue = new ReferenceQueue<>();
PhantomReference<Person> phantomPerson = new PhantomReference<>(new Person(), queue);
```
- **Behavior**: Used for cleanup actions before object is garbage collected
- **Usage**: Resource cleanup, finalization
- **GC Impact**: Allows GC but enables post-mortem actions

---

## Garbage Collection Algorithms

### Mark and Sweep Algorithm
```
Phase 1: MARK
┌─────────────────────────────────┐
│ Heap Memory                     │
│ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐│
│ │ Obj1│ │ Obj2│ │ Obj3│ │ Obj4││
│ │ ✓   │ │ ✗   │ │ ✓   │ │ ✗   ││
│ └─────┘ └─────┘ └─────┘ └─────┘│
└─────────────────────────────────┘
✓ = Referenced (Keep)
✗ = Unreferenced (Mark for deletion)

Phase 2: SWEEP
┌─────────────────────────────────┐
│ Heap Memory After Cleanup       │
│ ┌─────┐         ┌─────┐         │
│ │ Obj1│         │ Obj3│         │
│ │     │         │     │         │
│ └─────┘         └─────┘         │
└─────────────────────────────────┘
```

### Mark, Sweep, and Compact
```
Before Compaction:
┌─────┐   [FREE]   ┌─────┐   [FREE]   ┌─────┐
│ Obj1│            │ Obj3│            │ Obj5│
└─────┘            └─────┘            └─────┘

After Compaction:
┌─────┐ ┌─────┐ ┌─────┐              [FREE SPACE]
│ Obj1│ │ Obj3│ │ Obj5│
└─────┘ └─────┘ └─────┘
```

---

## Garbage Collector Types

### 1. Serial GC
- **Threads**: Single thread for GC
- **Performance**: Slow, suitable for small applications
- **Pause Time**: High (stop-the-world)
- **Usage**: Single-core systems, small applications

### 2. Parallel GC (Default in Java 8)
- **Threads**: Multiple threads for GC
- **Performance**: Faster than Serial GC
- **Pause Time**: Reduced due to parallelism
- **Usage**: Multi-core systems, medium-sized applications

### 3. Concurrent Mark Sweep (CMS)
- **Threads**: Concurrent GC with application threads
- **Performance**: Low pause times
- **Limitations**: No memory compaction
- **Usage**: Applications requiring low latency

### 4. G1 Garbage Collector
- **Features**: Low pause times + memory compaction
- **Performance**: Predictable pause times
- **Usage**: Large heap applications, real-time systems

---

## Common Interview Questions

### Q1: What is the difference between Stack and Heap memory?
**Answer**: 
- **Stack**: Stores method calls, local variables, primitive data types, and object references. Thread-specific, LIFO structure.
- **Heap**: Stores actual objects and instance variables. Shared among threads, managed by GC.

### Q2: When does OutOfMemoryError occur?
**Answer**:
- **StackOverflowError**: When stack memory is full (infinite recursion)
- **HeapSpace OutOfMemoryError**: When heap memory is exhausted
- **Metaspace OutOfMemoryError**: When metaspace is full (too many classes loaded)

### Q3: What is the difference between Young and Old generation?
**Answer**:
- **Young Generation**: Short-lived objects, frequent GC (Minor GC), fast collection
- **Old Generation**: Long-lived objects, infrequent GC (Major GC), slower collection

### Q4: Can you force garbage collection in Java?
**Answer**: You can suggest GC using `System.gc()`, but JVM doesn't guarantee execution. GC timing is controlled by JVM for optimal performance.

### Q5: What happens when an object has no references?
**Answer**: The object becomes eligible for garbage collection. GC will eventually remove it from memory during its next cycle.

### Q6: Explain the difference between Minor GC and Major GC.
**Answer**:
- **Minor GC**: Cleans Young Generation, fast and frequent
- **Major GC**: Cleans Old Generation, slower and less frequent
- **Full GC**: Cleans entire heap including Young, Old, and Metaspace

---

## Hands-on Exercises

### Exercise 1: Memory Leak Detection
```java
public class MemoryLeakExample {
    private static List<Object> list = new ArrayList<>();
    
    public void createMemoryLeak() {
        while(true) {
            list.add(new Object()); // Objects never removed!
        }
    }
}
```
**Task**: Identify the memory leak and fix it.

### Exercise 2: Reference Types Practice
```java
import java.lang.ref.*;

public class ReferenceTypesDemo {
    public static void main(String[] args) {
        // Create different reference types
        String strongRef = new String("Strong");
        WeakReference<String> weakRef = new WeakReference<>(new String("Weak"));
        SoftReference<String> softRef = new SoftReference<>(new String("Soft"));
        
        // Force garbage collection
        System.gc();
        
        // Check which references survive
        System.out.println("Strong: " + strongRef);
        System.out.println("Weak: " + weakRef.get());
        System.out.println("Soft: " + softRef.get());
    }
}
```

### Exercise 3: Stack Overflow Simulation
```java
public class StackOverflowDemo {
    public void recursiveMethod() {
        recursiveMethod(); // Infinite recursion
    }
    
    public static void main(String[] args) {
        new StackOverflowDemo().recursiveMethod();
    }
}
```
**Task**: Run this code and observe the StackOverflowError.

---

## Real-world Use Cases

### 1. Web Application Memory Management
```java
@Service
public class UserCacheService {
    // Using weak references for cache to prevent memory leaks
    private Map<String, WeakReference<User>> userCache = new ConcurrentHashMap<>();
    
    public User getUser(String id) {
        WeakReference<User> userRef = userCache.get(id);
        if (userRef != null) {
            User user = userRef.get();
            if (user != null) {
                return user; // Cache hit
            }
        }
        
        // Cache miss - load from database
        User user = loadUserFromDatabase(id);
        userCache.put(id, new WeakReference<>(user));
        return user;
    }
}
```

### 2. Batch Processing Optimization
```java
public class BatchProcessor {
    public void processBatch(List<Data> batch) {
        for (Data data : batch) {
            processData(data);
            // Explicit nullification for large objects
            data = null;
        }
        // Suggest GC after batch processing
        System.gc();
    }
}
```

### 3. Memory Monitoring
```java
public class MemoryMonitor {
    public void printMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        System.out.println("Total Memory: " + totalMemory / (1024 * 1024) + " MB");
        System.out.println("Used Memory: " + usedMemory / (1024 * 1024) + " MB");
        System.out.println("Free Memory: " + freeMemory / (1024 * 1024) + " MB");
    }
}
```

---

## Best Practices & Common Pitfalls

### Best Practices
1. **Avoid Memory Leaks**
   - Close resources (try-with-resources)
   - Remove listeners and callbacks
   - Clear collections when done

2. **Optimize Object Creation**
   - Reuse objects when possible
   - Use object pools for expensive objects
   - Prefer primitives over wrapper classes

3. **String Handling**
   - Use StringBuilder for string concatenation
   - Prefer string literals over new String()

4. **Collection Management**
   - Set initial capacity for collections
   - Use appropriate collection types
   - Remove unused entries from maps

### Common Pitfalls
1. **Memory Leaks**
   ```java
   // BAD - Memory leak
   private static List<Object> cache = new ArrayList<>();
   
   // GOOD - Use weak references or clear cache
   private static Map<String, WeakReference<Object>> cache = new HashMap<>();
   ```

2. **Unnecessary Object Creation**
   ```java
   // BAD
   String result = "";
   for (int i = 0; i < 1000; i++) {
       result += "text"; // Creates new String objects
   }
   
   // GOOD
   StringBuilder sb = new StringBuilder();
   for (int i = 0; i < 1000; i++) {
       sb.append("text");
   }
   String result = sb.toString();
   ```

3. **Ignoring OutOfMemoryError**
   ```java
   // BAD - Catching OOME without handling
   try {
       // memory-intensive operation
   } catch (OutOfMemoryError e) {
       // Ignoring the error
   }
   
   // GOOD - Proper handling
   try {
       // memory-intensive operation
   } catch (OutOfMemoryError e) {
       System.gc();
       logger.error("Memory exhausted", e);
       throw e; // Re-throw or handle appropriately
   }
   ```

---

## Debugging Tips

### 1. Memory Analysis Tools
- **JVisualVM**: Visual profiler for memory analysis
- **Eclipse MAT**: Memory Analyzer Tool
- **JProfiler**: Commercial profiling tool

### 2. JVM Flags for Debugging
```bash
# Enable GC logging
-XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps

# Generate heap dump on OutOfMemoryError
-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/path/to/dump

# Set heap size
-Xms512m -Xmx2g

# Choose garbage collector
-XX:+UseG1GC  # Use G1 collector
```

### 3. Memory Monitoring Code
```java
public class MemoryProfiler {
    private static final MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
    
    public static void logMemoryUsage(String context) {
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeapUsage = memoryBean.getNonHeapMemoryUsage();
        
        System.out.println(context + " - Heap: " + 
            heapUsage.getUsed() / (1024 * 1024) + "MB/" + 
            heapUsage.getMax() / (1024 * 1024) + "MB");
    }
}
```

---

## Memory Hooks & Mnemonics

### Stack vs Heap - "STEP" Rule
- **S**tack: **S**cope-limited, **S**mall, **S**peed (fast access)
- **T**emporary variables
- **E**ach thread has its own
- **P**rimitive values and references

- **H**eap: **H**uge space, **H**olds objects
- **E**veryone (all threads) shares
- **A**ctual objects stored here
- **P**ermanent until GC removes

### GC Generations - "YES" Rule
- **Y**oung: **Y**earning (new objects), **Y**early cleaned (frequent GC)
- **E**den: **E**verything starts here
- **S**urvivor: **S**urviving objects move here, **S**witching between S0 and S1

### Reference Types - "SWSP" Rule
- **S**trong: **S**tays until explicitly removed
- **W**eak: **W**eak grip, easily garbage collected
- **S**oft: **S**oftly held, removed when memory is low
- **P**hantom: **P**ost-mortem cleanup actions

---

## Comparison with Related Concepts

### Java vs C++ Memory Management
| Aspect | Java | C++ |
|--------|------|-----|
| Memory Allocation | Automatic (new keyword) | Manual (new/malloc) |
| Memory Deallocation | Automatic (GC) | Manual (delete/free) |
| Memory Leaks | Possible but less common | Common without careful management |
| Performance | GC overhead | Direct control, potentially faster |
| Safety | Memory-safe (no dangling pointers) | Unsafe (manual management) |

### Stack vs Heap Comparison
| Feature | Stack | Heap |
|---------|-------|------|
| Speed | Faster (LIFO access) | Slower (complex allocation) |
| Size | Smaller | Larger |
| Thread Safety | Thread-local | Shared (needs synchronization) |
| Allocation | Automatic | Managed by GC |
| Data Types | Primitives, references | Objects, arrays |
| Cleanup | Automatic (scope-based) | Garbage Collection |

---

## Performance Tuning Tips

### 1. JVM Tuning Parameters
```bash
# Heap size tuning
-Xms2g -Xmx4g  # Initial and maximum heap size

# Young generation tuning
-XX:NewRatio=3  # Old/Young generation ratio
-XX:SurvivorRatio=8  # Eden/Survivor ratio

# GC tuning
-XX:+UseG1GC  # Use G1 collector for low latency
-XX:MaxGCPauseMillis=200  # Target pause time

# Metaspace tuning
-XX:MetaspaceSize=256m
-XX:MaxMetaspaceSize=512m
```

### 2. Application-level Optimizations
```java
// Object pooling for expensive objects
public class DatabaseConnectionPool {
    private final Queue<Connection> pool = new ConcurrentLinkedQueue<>();
    
    public Connection getConnection() {
        Connection conn = pool.poll();
        return conn != null ? conn : createNewConnection();
    }
    
    public void returnConnection(Connection conn) {
        if (conn != null && !pool.offer(conn)) {
            closeConnection(conn);
        }
    }
}

// Lazy initialization
public class HeavyResource {
    private static volatile HeavyResource instance;
    
    public static HeavyResource getInstance() {
        if (instance == null) {
            synchronized (HeavyResource.class) {
                if (instance == null) {
                    instance = new HeavyResource();
                }
            }
        }
        return instance;
    }
}
```

This comprehensive guide covers all aspects of Java memory management from basic concepts to advanced optimization techniques. Use it for both learning and interview preparation!