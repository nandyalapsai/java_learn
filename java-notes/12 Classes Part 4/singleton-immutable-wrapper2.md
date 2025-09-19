# Java Classes Part 4: Singleton, Immutable & Wrapper Classes

## Table of Contents
- [Singleton Classes](#singleton-classes)
- [Immutable Classes](#immutable-classes)
- [Wrapper Classes](#wrapper-classes)
- [Interview Questions](#interview-questions)
- [Best Practices](#best-practices)

---

## Singleton Classes

### Definition & Purpose
A **Singleton class** ensures that only one instance of the class can be created throughout the application lifecycle.

### Key Characteristics
- **One object per JVM**: Only one instance exists in the entire application
- **Thread-safe**: Must handle concurrent access properly
- **Global access point**: Provides a way to access the single instance

### Real-world Use Cases
- **Database connections**: One connection pool for the entire application
- **Logger instances**: Single logging mechanism
- **Configuration managers**: One configuration object
- **Cache managers**: Single cache instance

---

## Six Popular Implementation Patterns

### 1. Eager Initialization

```java
public class DBConnection {
    // Object created eagerly at class loading time
    private static final DBConnection connection = new DBConnection();
    
    // Private constructor prevents external instantiation
    private DBConnection() {}
    
    // Public method to get the instance
    public static DBConnection getInstance() {
        return connection;
    }
}
```

**Advantages:**
- Simple implementation
- Thread-safe by default

**Disadvantages:**
- Object created even if not used (memory waste)
- No lazy loading

---

### 2. Lazy Initialization

```java
public class DBConnection {
    private static DBConnection connection;
    
    private DBConnection() {}
    
    public static DBConnection getInstance() {
        if (connection == null) {
            connection = new DBConnection(); // Thread safety issue!
        }
        return connection;
    }
}
```

**Advantages:**
- Object created only when needed

**Disadvantages:**
- **Not thread-safe**: Multiple threads can create multiple instances
- Race condition possible

---

### 3. Synchronized Method

```java
public class DBConnection {
    private static DBConnection connection;
    
    private DBConnection() {}
    
    public static synchronized DBConnection getInstance() {
        if (connection == null) {
            connection = new DBConnection();
        }
        return connection;
    }
}
```

**Advantages:**
- Thread-safe
- Lazy initialization

**Disadvantages:**
- **Performance overhead**: Every method call is synchronized
- Slow execution due to locking

---

### 4. Double-Checked Locking

```java
public class DBConnection {
    private static volatile DBConnection connection;
    
    private DBConnection() {}
    
    public static DBConnection getInstance() {
        if (connection == null) {           // First check
            synchronized (DBConnection.class) {
                if (connection == null) {   // Second check
                    connection = new DBConnection();
                }
            }
        }
        return connection;
    }
}
```

**Key Points:**
- **Double checking**: Checks null condition twice
- **Volatile keyword**: Prevents memory consistency issues
- **Synchronized block**: Only when object creation is needed

**Why Volatile is Important:**
```
CPU Core 1 (Thread 1)     Memory     CPU Core 2 (Thread 2)
    Cache                              Cache
    -----                              -----
connection = new Object()  ------>    connection = null
(stored in cache)         Memory     (reads from memory)
                         not yet
                         updated
```

Without `volatile`:
- Thread 1 creates object, stores in L1 cache
- Memory not immediately updated
- Thread 2 reads from memory, sees null
- Two objects get created

With `volatile`:
- All reads/writes go directly to memory
- No caching for volatile variables
- Ensures memory consistency

---

### 5. Bill Pugh Solution (Initialization-on-Demand Holder)

```java
public class DBConnection {
    private DBConnection() {}
    
    // Static nested class - loaded only when referenced
    private static class ConnectionHelper {
        private static final DBConnection connection = new DBConnection();
    }
    
    public static DBConnection getInstance() {
        return ConnectionHelper.connection;
    }
}
```

**Advantages:**
- **Lazy loading**: Nested class loaded only when used
- **Thread-safe**: JVM handles class loading synchronization
- **No synchronization overhead**: Fast performance
- **No volatile needed**: JVM guarantees proper initialization

**How it works:**
- Nested classes are not loaded during main class loading
- `ConnectionHelper` loaded only when `getInstance()` is called
- JVM ensures thread-safe class loading

---

### 6. Enum Singleton

```java
public enum DBConnection {
    INSTANCE;
    
    public void connect() {
        // Database connection logic
    }
}

// Usage: DBConnection.INSTANCE.connect();
```

**Advantages:**
- **Simplest implementation**: Just 2 lines
- **Thread-safe by default**: JVM handles it
- **Serialization safe**: Enum handles serialization properly
- **Reflection safe**: Cannot be broken by reflection

**Why Enums are Singleton:**
- JVM ensures only one instance per enum constant per JVM
- Constructor is implicitly private
- Cannot be subclassed

---

## Memory Issues in Double-Checked Locking

### Problem 1: Caching Issue
```
Thread 1 (Core 1)                    Thread 2 (Core 2)
-----------------                    -----------------
1. Check: connection == null ✓        1. Check: connection == null ✓
2. Acquire lock                      2. Wait for lock
3. Create new DBConnection()         
4. Store in L1 cache (not memory)    
5. Release lock                      6. Acquire lock
                                    7. Check memory: still null ✓
                                    8. Create another object!
```

### Problem 2: Instruction Reordering
```java
// This line: connection = new DBConnection();
// Can be reordered by JVM to:
1. Allocate memory for DBConnection
2. Assign memory reference to connection (connection != null now!)
3. Initialize DBConnection object

// Problem: Another thread might see connection != null before object is fully initialized
```

### Solution: Volatile Keyword
```java
private static volatile DBConnection connection;
```

**What volatile does:**
- **Prevents caching**: All reads/writes go directly to main memory
- **Prevents reordering**: JVM cannot reorder instructions around volatile variables
- **Ensures visibility**: Changes by one thread immediately visible to other threads

---

## Immutable Classes

### Definition
An **immutable class** is a class whose state cannot be changed after object creation.

### Key Characteristics
- Object state remains constant throughout its lifetime
- Thread-safe by nature
- No setter methods allowed
- All fields are final and private

### Real-world Examples
- `String` class
- `Integer`, `Double` (wrapper classes)
- `LocalDate`, `LocalDateTime`
- Custom value objects (Person, Address, etc.)

---

## How to Create Immutable Classes

### Step-by-Step Implementation

```java
public final class Person {                    // 1. Make class final
    private final String name;                 // 2. Make fields private final
    private final List<String> petNames;
    
    // 3. Initialize through constructor only
    public Person(String name, List<String> petNames) {
        this.name = name;
        this.petNames = new ArrayList<>(petNames);  // Defensive copy
    }
    
    // 4. Provide only getter methods
    public String getName() {
        return name;                           // Safe to return directly (String is immutable)
    }
    
    // 5. Return defensive copies for mutable objects
    public List<String> getPetNames() {
        return new ArrayList<>(petNames);      // Return copy, not original
    }
}
```

### Why Defensive Copying is Important

**Without defensive copying:**
```java
public List<String> getPetNames() {
    return petNames;  // Returns reference to original list
}

// Usage:
Person person = new Person("John", Arrays.asList("Rex", "Buddy"));
List<String> pets = person.getPetNames();
pets.add("Charlie");  // This modifies the original internal list!
System.out.println(person.getPetNames());  // Now shows ["Rex", "Buddy", "Charlie"]
```

**With defensive copying:**
```java
public List<String> getPetNames() {
    return new ArrayList<>(petNames);  // Returns new list with copied elements
}

// Usage:
Person person = new Person("John", Arrays.asList("Rex", "Buddy"));
List<String> pets = person.getPetNames();  // Gets a copy
pets.add("Charlie");  // Modifies only the copy
System.out.println(person.getPetNames());  // Still shows ["Rex", "Buddy"]
```

### Collection Final vs Immutable

```java
private final List<String> petNames;  // Final reference, mutable content

// What final means:
petNames = new ArrayList<>();  // ❌ Cannot reassign reference
petNames.add("newPet");        // ✅ Can modify content
petNames.remove(0);            // ✅ Can modify content
```

**Memory representation:**
```
petNames (final reference) -----> [Memory Block]
     |                            [  "Rex"   ]
     |                            [ "Buddy"  ]
     |                            [    ?     ] <- Can add/remove here
     |
     └── Cannot point to different memory block
```

---

## Wrapper Classes

### Definition
Wrapper classes provide object representation of primitive data types, enabling them to be used where objects are required.

### Primitive to Wrapper Mapping
| Primitive | Wrapper Class |
|-----------|---------------|
| `byte`    | `Byte`        |
| `short`   | `Short`       |
| `int`     | `Integer`     |
| `long`    | `Long`        |
| `float`   | `Float`       |
| `double`  | `Double`      |
| `char`    | `Character`   |
| `boolean` | `Boolean`     |

### Autoboxing and Unboxing

```java
// Autoboxing: primitive → wrapper
int primitive = 10;
Integer wrapper = primitive;  // Automatic conversion

// Unboxing: wrapper → primitive  
Integer wrapperObj = 20;
int primitiveValue = wrapperObj;  // Automatic conversion

// Collections require objects
List<Integer> numbers = new ArrayList<>();
numbers.add(5);  // Autoboxing: int → Integer
int value = numbers.get(0);  // Unboxing: Integer → int
```

---

## Comparison Table

| Pattern | Lazy Loading | Thread Safe | Performance | Complexity | Best For |
|---------|-------------|-------------|-------------|------------|----------|
| Eager | ❌ | ✅ | High | Low | Simple apps, always-used objects |
| Lazy | ✅ | ❌ | High | Low | Single-threaded apps |
| Synchronized | ✅ | ✅ | Low | Low | Small applications |
| Double-checked | ✅ | ✅ | Medium | High | Multi-threaded apps |
| Bill Pugh | ✅ | ✅ | High | Medium | **Most recommended** |
| Enum | ✅ | ✅ | High | Low | Simple singletons |

---

## Interview Questions

### Singleton Class Questions

**Q1: What is a Singleton pattern and why is it used?**
- **Answer**: Ensures only one instance of a class exists per JVM. Used for database connections, loggers, configuration managers where only one instance should exist.

**Q2: Why is the constructor made private in Singleton?**
- **Answer**: To prevent external instantiation using the `new` keyword. Only the class itself can create its instance.

**Q3: What problems does lazy initialization have?**
- **Answer**: Thread safety issues. Multiple threads can create multiple instances if they check the null condition simultaneously.

**Q4: Why is volatile keyword important in Double-checked locking?**
- **Answer**: Prevents memory consistency issues and instruction reordering. Ensures all reads/writes happen from main memory, not CPU cache.

**Q5: Which Singleton implementation is best?**
- **Answer**: Bill Pugh solution (Initialization-on-demand holder) is most recommended as it provides lazy loading, thread safety, and high performance without synchronization overhead.

### Immutable Class Questions

**Q6: How do you create an immutable class?**
- **Answer**: 
  1. Make class final
  2. Make all fields private and final
  3. No setter methods
  4. Initialize through constructor only
  5. Return defensive copies for mutable objects

**Q7: Why return defensive copies in getter methods?**
- **Answer**: To prevent external modification of internal mutable objects. Final keyword only prevents reassignment of reference, not modification of object content.

**Q8: Are immutable classes thread-safe?**
- **Answer**: Yes, since their state cannot be changed after creation, multiple threads can safely access them without synchronization.

---

## Best Practices

### Singleton Classes
1. **Prefer Bill Pugh solution** for most use cases
2. **Use Enum singleton** for simple cases
3. **Always consider if you really need Singleton** - dependency injection might be better
4. **Make singleton classes final** to prevent subclassing
5. **Handle serialization properly** if needed

### Immutable Classes
1. **Always use defensive copying** for mutable objects
2. **Make the class final** to prevent inheritance
3. **Consider using builder pattern** for complex immutable objects
4. **Cache hash codes** for better performance
5. **Use factory methods** for common instances

### Common Pitfalls

#### Singleton Pitfalls
- **Forgetting volatile** in double-checked locking
- **Using synchronized method** instead of synchronized block
- **Not handling serialization/deserialization**
- **Breaking singleton with reflection**

#### Immutable Pitfalls
- **Returning direct references** to mutable objects
- **Forgetting to make class final**
- **Not making defensive copies** in constructor
- **Assuming final collections are immutable**

---

## Memory Management Notes

### Singleton Memory Allocation
```
JVM Memory Layout:
┌─────────────────┐
│   Method Area   │ ← Static variables stored here
│                 │   (Singleton instances)
├─────────────────┤
│      Heap       │ ← Object instances
│                 │
├─────────────────┤
│     Stack       │ ← Method calls, local variables
└─────────────────┘
```

### Volatile Memory Model
```
Without volatile:
Thread 1 ─── CPU Cache ─── Main Memory ─── CPU Cache ─── Thread 2
         (may not sync)              (may not sync)

With volatile:
Thread 1 ────────────── Main Memory ──────────────── Thread 2
         (direct access)          (direct access)
```

---

## Related Concepts

| Concept | Relationship | Key Difference |
|---------|-------------|----------------|
| **Static vs Singleton** | Both ensure single instance | Static: compile-time, Singleton: runtime control |
| **Immutable vs Final** | Final can help create immutable | Final: reference/inheritance, Immutable: object state |
| **Singleton vs Factory** | Both control object creation | Singleton: one instance, Factory: multiple instances |

---

*Note: This content is part of Java Classes series. Previous topics covered OOP fundamentals, generics, and POJO/Enum concepts.*