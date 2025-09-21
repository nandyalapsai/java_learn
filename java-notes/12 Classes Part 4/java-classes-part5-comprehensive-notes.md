# Java Classes Part 5: Singleton, Immutable & Wrapper Classes
*Comprehensive Study Notes from Concept and Coding*

---

## 🎯 Learning Objectives

After studying these notes, you should be able to:
- ✅ Understand and implement all 6 types of Singleton design patterns
- ✅ Create thread-safe singleton classes with proper memory management
- ✅ Design immutable classes following best practices
- ✅ Handle collections properly in immutable classes
- ✅ Explain the volatile keyword and its importance in concurrent programming
- ✅ Apply wrapper classes with autoboxing/unboxing concepts
- ✅ Answer common interview questions on these topics

---

## 📚 Key Concepts & Definitions

### 🔹 Singleton Class
A design pattern that ensures **only one instance** of a class is created throughout the application lifecycle.

**Purpose**: Control object creation for resources like:
- Database connections
- Logger instances
- Configuration objects
- Cache managers

### 🔹 Immutable Class
A class whose **state cannot be modified** after object creation.

**Characteristics**:
- No setter methods
- All fields are private and final
- Class is declared final (no inheritance)
- Deep copying for mutable objects

### 🔹 Wrapper Classes
Classes that encapsulate primitive data types, providing object-oriented functionality.

---

## 🛠️ Singleton Design Patterns (6 Types)

### 1️⃣ Eager Initialization

```java
public class DatabaseConnection {
    // Object created at class loading time
    private static final DatabaseConnection instance = new DatabaseConnection();
    
    // Private constructor prevents external instantiation
    private DatabaseConnection() {}
    
    // Public method to get instance
    public static DatabaseConnection getInstance() {
        return instance;
    }
}
```

**✅ Pros**: Thread-safe, simple implementation
**❌ Cons**: Object created even if never used (memory waste)

### 2️⃣ Lazy Initialization

```java
public class DatabaseConnection {
    private static DatabaseConnection instance;
    
    private DatabaseConnection() {}
    
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}
```

**✅ Pros**: Object created only when needed
**❌ Cons**: Not thread-safe (multiple objects possible in multithreading)

### 3️⃣ Synchronized Method

```java
public class DatabaseConnection {
    private static DatabaseConnection instance;
    
    private DatabaseConnection() {}
    
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}
```

**✅ Pros**: Thread-safe
**❌ Cons**: Performance overhead (synchronization on every call)

### 4️⃣ Double-Checked Locking

```java
public class DatabaseConnection {
    private static volatile DatabaseConnection instance;
    
    private DatabaseConnection() {}
    
    public static DatabaseConnection getInstance() {
        if (instance == null) {                    // First check
            synchronized (DatabaseConnection.class) {
                if (instance == null) {            // Second check
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
}
```

**Key Points**:
- `volatile` keyword prevents memory consistency issues
- Two null checks prevent race conditions
- Synchronization only when instance is null

### 5️⃣ Bill Pugh Solution (Initialization-on-demand)

```java
public class DatabaseConnection {
    private DatabaseConnection() {}
    
    // Static nested class loaded only when referenced
    private static class ConnectionHelper {
        private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    }
    
    public static DatabaseConnection getInstance() {
        return ConnectionHelper.INSTANCE;
    }
}
```

**✅ Pros**: Lazy loading, thread-safe, high performance
**✅ Best Practice**: Most recommended approach

### 6️⃣ Enum Singleton

```java
public enum DatabaseConnection {
    INSTANCE;
    
    public void connect() {
        // Database connection logic
    }
}

// Usage: DatabaseConnection.INSTANCE.connect();
```

**✅ Pros**: Thread-safe, serialization-safe, reflection-proof
**✅ JVM Guarantee**: Only one instance per JVM

---

## 🔒 Understanding Volatile Keyword

### Memory Model Problem

```
CPU Core 1          CPU Core 2
┌─────────┐        ┌─────────┐
│ Thread1 │        │ Thread2 │
└─────────┘        └─────────┘
     │                   │
┌─────────┐        ┌─────────┐
│L1 Cache │        │L1 Cache │  <- Each core has its own cache
└─────────┘        └─────────┘
     │                   │
     └───────────────────┘
            │
    ┌─────────────┐
    │Main Memory  │  <- Shared memory
    └─────────────┘
```

### Issues Without Volatile:
1. **Caching Issue**: Updates in one thread's cache not visible to other threads
2. **Instruction Reordering**: Compiler optimizations can change execution order

### Solution: Volatile
```java
private static volatile DatabaseConnection instance;
```

**Volatile Guarantees**:
- All reads/writes happen directly from/to main memory
- No caching in CPU cores
- Prevents instruction reordering

---

## 🔐 Immutable Classes

### Implementation Rules

```java
public final class Person {                    // 1. Class is final
    private final String name;                 // 2. All fields private & final
    private final List<String> hobbies;        // 3. Mutable objects need special handling
    
    // 4. Constructor for one-time initialization
    public Person(String name, List<String> hobbies) {
        this.name = name;
        this.hobbies = new ArrayList<>(hobbies);  // Defensive copy
    }
    
    // 5. Only getter methods, no setters
    public String getName() {
        return name;  // String is immutable, safe to return
    }
    
    // 6. Return copy of mutable objects
    public List<String> getHobbies() {
        return new ArrayList<>(hobbies);  // Return defensive copy
    }
}
```

### Critical Point: Defensive Copying

```java
// ❌ Wrong way - Breaks immutability
public List<String> getHobbies() {
    return hobbies;  // Returns reference to internal state
}

// ✅ Correct way - Maintains immutability  
public List<String> getHobbies() {
    return new ArrayList<>(hobbies);  // Returns copy
}
```

### Why Final Collections Need Copies

```java
private final List<String> hobbies = Arrays.asList("Reading", "Gaming");

// Final means: hobbies will always point to the SAME list object
// But you can still modify the LIST CONTENTS:
hobbies.add("Swimming");    // This would work without defensive copying!
hobbies.remove("Gaming");   // This would also work!

// Final prevents this:
// hobbies = new ArrayList<>();  // ❌ Compilation error
```

---

## 📊 Comparison Diagrams

### Singleton Pattern Performance Comparison

```
Performance Ranking (Best to Worst):
1. Bill Pugh Solution      ████████████████████ (Best)
2. Enum Singleton         ███████████████████
3. Eager Initialization   ██████████████████
4. Double-Checked Lock    ████████████████
5. Lazy Initialization    ██████████████
6. Synchronized Method    ████████████ (Worst)
```

### Thread Safety Matrix

| Pattern | Thread Safe | Lazy Loading | Performance |
|---------|-------------|--------------|-------------|
| Eager | ✅ | ❌ | High |
| Lazy | ❌ | ✅ | High |
| Synchronized | ✅ | ✅ | Low |
| Double-Checked | ✅ | ✅ | Medium |
| Bill Pugh | ✅ | ✅ | High |
| Enum | ✅ | ❌ | High |

---

## 🎭 Common Interview Questions & Answers

### Singleton Questions

**Q1: Why use Singleton pattern?**
**A:** Control object creation for expensive resources like DB connections, ensure single point of access, save memory by reusing one instance.

**Q2: What problems can occur without synchronized keyword in lazy initialization?**
**A:** Race condition - multiple threads can create multiple instances simultaneously when checking null condition.

**Q3: Why is volatile important in double-checked locking?**
**A:** Prevents memory visibility issues and instruction reordering. Without volatile, one thread's object creation might not be visible to other threads due to CPU caching.

**Q4: Which singleton pattern is most recommended?**
**A:** Bill Pugh solution - provides lazy loading, thread safety, and high performance without synchronization overhead.

**Q5: How does enum singleton prevent reflection attacks?**
**A:** JVM internally handles enum instantiation and prevents multiple instances even through reflection.

### Immutable Class Questions

**Q6: What makes a class immutable?**
**A:** Final class, private final fields, no setters, defensive copying for mutable objects, constructor-only initialization.

**Q7: Why return copy of collections in getter methods?**
**A:** Final keyword prevents reassigning the collection reference but allows modification of collection contents. Returning copy prevents external modification.

**Q8: Can we have mutable objects in immutable class?**
**A:** Yes, but they must be handled with defensive copying in constructor and getter methods.

---

## 💡 Hands-on Exercises

### Exercise 1: Implement Logger Singleton
```java
// Create a Logger class using Bill Pugh pattern
// Add methods: info(), error(), debug()
// Test thread safety with multiple threads
```

### Exercise 2: Create Immutable Student Class
```java
// Fields: name (String), grades (List<Integer>), address (Address object)
// Implement proper defensive copying
// Test immutability by trying to modify returned collections
```

### Exercise 3: Volatile Demonstration
```java
// Create a class showing volatile vs non-volatile behavior
// Use two threads - one writing, one reading
// Demonstrate visibility issues
```

---

## 🌍 Real-World Use Cases

### Singleton Applications
- **Database Connection Pools**: Manage limited connections efficiently
- **Configuration Managers**: Single source of truth for app settings
- **Logging Services**: Centralized logging across application
- **Cache Managers**: Single cache instance for performance
- **Thread Pools**: Manage thread creation and reuse

### Immutable Class Examples
- **String class**: Once created, value never changes
- **Wrapper classes**: Integer, Double, etc. are immutable
- **LocalDate, LocalTime**: Date/time objects in Java 8+
- **Configuration objects**: Settings that shouldn't change after loading
- **Value objects**: DTOs, coordinates, money amounts

---

## ⚠️ Best Practices & Common Pitfalls

### Singleton Best Practices
✅ **Do**:
- Use Bill Pugh pattern for most cases
- Use enum for simple singletons
- Always make constructor private
- Consider using dependency injection frameworks

❌ **Don't**:
- Forget volatile in double-checked locking
- Use lazy initialization without synchronization
- Make singleton fields non-static
- Create setter methods for singleton properties

### Immutable Class Best Practices
✅ **Do**:
- Always use defensive copying for mutable objects
- Make class and all fields final
- Validate inputs in constructor
- Use builder pattern for complex immutable objects

❌ **Don't**:
- Return references to mutable internal state
- Forget to copy collections in getters
- Allow inheritance (make class final)
- Provide any setter methods

### Common Debugging Issues
1. **Multiple singleton instances**: Check thread safety and initialization
2. **Memory leaks**: Ensure singleton doesn't hold unnecessary references
3. **Immutability broken**: Verify defensive copying implementation
4. **Performance issues**: Avoid synchronization in hot paths

---

## 🧠 Memory Hooks & Mnemonics

### Singleton Patterns Memory Aid
**"Every Lazy Student Definitely Builds Excellent"**
- **E**ager
- **L**azy  
- **S**ynchronized
- **D**ouble-checked
- **B**ill Pugh
- **E**num

### Immutable Class Rules - "PFNDC"
- **P**rivate fields
- **F**inal class and fields
- **N**o setters
- **D**efensive copying
- **C**onstructor initialization

### Volatile Memory Hook
**"Volatile Variables Visit Main Memory"**
- No caching, direct memory access

---

## 📋 Quick Revision Cheat Sheet

### Singleton Quick Reference
```java
// Best Practice - Bill Pugh
public class Singleton {
    private Singleton() {}
    private static class Helper {
        private static final Singleton INSTANCE = new Singleton();
    }
    public static Singleton getInstance() { return Helper.INSTANCE; }
}

// Simple Case - Enum
public enum Singleton { INSTANCE; }
```

### Immutable Class Template
```java
public final class ImmutableClass {
    private final Type field;
    private final List<Type> list;
    
    public ImmutableClass(Type field, List<Type> list) {
        this.field = field;
        this.list = new ArrayList<>(list);  // Defensive copy
    }
    
    public Type getField() { return field; }
    public List<Type> getList() { return new ArrayList<>(list); }  // Copy
}
```

### Key Interview Points
- **Singleton**: Bill Pugh > Enum > Others
- **Thread Safety**: Always consider in multithreaded environment
- **Volatile**: Prevents caching and reordering issues
- **Immutable**: Final + Private + No setters + Defensive copying
- **Wrapper**: Autoboxing/unboxing, covered in previous videos

---

## 🔗 Related Concepts & Next Topics

### Previous Dependencies
- **Java Variables Part 2**: Wrapper classes, autoboxing/unboxing
- **Memory Management**: Understanding heap, stack, garbage collection
- **Constructors**: Private constructors in singleton

### Next Topics to Study
- **Volatile keyword in detail** (mentioned for next video)
- **Concurrency and Threading**
- **Design Patterns**
- **Exception Handling**

---

*Remember: Practice implementing these patterns, understand the trade-offs, and always consider thread safety in real applications!*