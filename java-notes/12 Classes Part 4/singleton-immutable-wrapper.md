# Classes Part 4: Singleton, Immutable Classes & Wrapper Classes

Use these notes for quick revision and interview prep. Covers patterns, memory model nuances, examples, pitfalls, and FAQs.

---
## 1. Key Concepts & Definitions
- Singleton: A design pattern ensuring only ONE instance of a class exists per JVM and providing a global access point to it.
- Immutable Class: A class whose instances' state cannot change after construction.
- Wrapper Classes: Object (reference type) representations of Java's primitive data types (int → Integer, boolean → Boolean, etc.). Enable features like collections usage, generics, and nullability.
- Eager Initialization: Instance created at class load time.
- Lazy Initialization: Instance created only when first requested.
- Double-Checked Locking: Optimization to reduce synchronization overhead while keeping thread safety.
- Volatile: Keyword ensuring visibility and ordering guarantees for a variable across threads.
- Bill Pugh / Inner Static Holder Pattern: Uses a static inner class to defer instance creation until needed (thread-safe + fast). 
- Autoboxing / Unboxing: Automatic conversion between primitive and its wrapper (int ↔ Integer).

---
## 2. Singleton Implementations (6 Popular Ways)
### 2.1 Eager Initialization
- Creates instance when the class is loaded.
- Pros: Simple, thread-safe (class loading is synchronized by JVM).
- Cons: Wastes memory if never used.

```java
public class DBConnection {
    private static final DBConnection INSTANCE = new DBConnection();
    private DBConnection() {}
    public static DBConnection getInstance() { return INSTANCE; }
}
```

### 2.2 Lazy Initialization (NOT Thread-Safe)
- Creates instance only when first requested.
- Cons: Race condition in multithreaded scenarios.
```java
public class DBConnection {
    private static DBConnection instance; // null initially
    private DBConnection() {}
    public static DBConnection getInstance() {
        if (instance == null) {               // race window
            instance = new DBConnection();
        }
        return instance;
    }
}
```

### 2.3 Synchronized Method
- Thread-safe but slow (every call acquires monitor even after instance created).
```java
public class DBConnection {
    private static DBConnection instance;
    private DBConnection() {}
    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
}
```

### 2.4 Double-Checked Locking (WITH volatile)
- Avoids unnecessary synchronization after initialization.
- volatile prevents instruction reordering + ensures visibility across cores.
```java
public class DBConnection {
    private static volatile DBConnection instance; // MUST be volatile
    private DBConnection() {}
    public static DBConnection getInstance() {
        if (instance == null) {                // 1st check (no locking)
            synchronized (DBConnection.class) {
                if (instance == null) {        // 2nd check (with lock)
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }
}
```

WHY volatile?
- Object creation is not atomic (roughly: allocate → assign fields → assign reference).
- Without volatile, reordering may expose a partially constructed object to another thread.
- volatile enforces: write to instance reference happens AFTER constructor completes (happens-before relationship).

### 2.5 Bill Pugh Static Holder (Preferred)
- Leverages class loader initialization guarantees.
- Fast, lazy, thread-safe, no synchronization, no volatile.
```java
public class DBConnection {
    private DBConnection() {}
    private static class Holder {
        private static final DBConnection INSTANCE = new DBConnection();
    }
    public static DBConnection getInstance() {
        return Holder.INSTANCE; // Holder loaded on first call
    }
}
```

### 2.6 Enum Singleton (Simplest & Serialization-Safe)
- JVM guarantees single enum instance per constant.
- Handles serialization & reflection attacks naturally.
```java
public enum DBConnection {
    INSTANCE;
    public void query(String sql) { /* ... */ }
}
// Usage: DBConnection.INSTANCE.query("SELECT * FROM users");
```

---
## 3. Memory Model & Multithreading Notes (Double-Checked Locking)
Problem without volatile:
1. Thread A starts constructing object.
2. Reference assigned before full initialization completes (due to reordering).
3. Thread B sees non-null reference and uses an incompletely constructed object → Undefined behavior.

CPU Cache Scenario (Simplified):
- Each core caches variables (L1/L2). Without proper memory barriers, writes may not immediately be visible to other cores.
- volatile enforces visibility + ordering (flush & read from main memory).

---
## 4. Choosing the Right Singleton Strategy
Use When:
- Eager: Very small footprint + always used.
- Lazy (unsaf e): Never in production; only for single-threaded demos.
- Synchronized: Quick fix; low traffic apps.
- Double-Checked + volatile: Performance-sensitive legacy-compatible code.
- Bill Pugh: General best-practice for most scenarios.
- Enum: When you just need one instance with simple behavior (e.g., configuration, logger). 

---
## 5. Immutable Classes
Goal: Once constructed, state cannot change.

Design Checklist:
- Declare class final (prevents subclass mutation loopholes).
- Make all fields private and final.
- No setters.
- Defensive copies for mutable inputs (constructor) and mutable outputs (getters).
- If holding references to mutable objects (List, Date, arrays) → clone/copy them.

Example:
```java
import java.util.*;
public final class Person {
    private final String name;          // Immutable (String itself is immutable)
    private final List<String> pets;    // Mutable type → needs defensive handling

    public Person(String name, List<String> pets) {
        this.name = name;
        this.pets = List.copyOf(pets); // Java 10+ unmodifiable copy (defensive)
    }

    public String getName() { return name; }

    public List<String> getPets() { 
        return List.copyOf(pets); // Return defensive copy
    }
}
```

If using pre-Java 10 defensive copy style:
```java
this.pets = new ArrayList<>(pets);
...
return new ArrayList<>(pets);
```

Why not just return the original list?
- Caller could modify it → breaks immutability.

---
## 6. Wrapper Classes
Primitive → Wrapper:
- byte → Byte
- short → Short
- int → Integer
- long → Long
- float → Float
- double → Double
- char → Character
- boolean → Boolean

Autoboxing Example:
```java
Integer a = 10;      // auto boxing (int -> Integer)
int b = a;           // unboxing
List<Integer> nums = List.of(1, 2, 3); // primitives cannot go into generics
```

Caveats:
- Comparing wrappers with == compares references (unless cached small integers: -128..127 for Integer, etc.). Use equals().
- Null unboxing throws NullPointerException.
```java
Integer x = null;
int y = x; // NPE
```

---
## 7. Diagrams (Textual)
### 7.1 Singleton Structure (Bill Pugh)
```
DBConnection
  - private DBConnection()
  + getInstance(): DBConnection
  Holder (static inner)
    - INSTANCE: DBConnection
Load Sequence:
  1. DBConnection loaded (no instance yet)
  2. getInstance() called → Holder class loaded
  3. INSTANCE created
```

### 7.2 Double-Checked Locking Flow
```
Thread calls getInstance()
  ├─ instance == null? yes
  │   ├─ enter synchronized block
  │   │   ├─ instance == null? yes
  │   │   └─ create new instance (volatile write)
  └─ return instance
Subsequent calls:
  └─ instance == null? no → fast return (no lock)
```

### 7.3 Immutable Object Access
```
Caller ---> Person.getPets() ---> defensive copy ---> caller modifies copy (original safe)
```

---
## 8. Real-World Use Cases
Singleton:
- Database connection pool manager
- Logger (e.g., LogManager)
- Configuration registry / Feature flags
- Thread pool manager
- Caching service

Immutable Objects:
- Value Objects (Money, Point, Color)
- DTOs crossing thread boundaries
- Keys in Maps/Sets (immutability ensures consistent hashCode)
- Functional programming style data containers

Wrapper Classes:
- Collections & Generics (cannot store primitives)
- Reflection / Framework metadata (e.g., Spring method parameters)
- Optional primitive intent (nullable fields)

---
## 9. Common Interview Questions (With Brief Answers)
1. Why is volatile required in double-checked locking? 
   Ensures visibility + prevents instruction reordering exposing partially constructed object.
2. Difference between synchronized method vs block?
   Method-level locks entire object/class; block allows finer granularity (better performance).
3. Can reflection break a singleton?
   Yes (via accessible constructor) unless you guard (throw if instance already created) or use enum.
4. Why is Enum singleton safe?
   JVM guarantees single instance, handles serialization, resists reflection attacks.
5. How to make a class immutable?
   Private final fields, no setters, defensive copies, class final.
6. Are all final fields immutable?
   No. final prevents re-assignment of reference; internal state of mutable objects may still change.
7. Difference between == and equals() for Wrapper types?
   == compares references (except cached small values); equals() compares value.
8. Why prefer Bill Pugh over double-checked locking?
   Simpler, no volatile, no synchronization overhead after first access.
9. Can singleton hinder unit testing?
   Yes, introduces global mutable state; mitigate by dependency injection or using enum carefully.
10. What problems do singletons introduce? 
   Hidden dependencies, tight coupling, difficulty in scaling/testing, potential for state leakage.

---
## 10. Best Practices
Singleton:
- Prefer dependency injection over raw global singleton access when possible.
- Make instance field private and final (when eager/holder) to prevent reassignment.
- Defend against reflection (throw exception in constructor if instance != null) unless using enum.
- Implement readResolve() if Serializable (unless enum used):
```java
private Object readResolve() { return getInstance(); }
```

Immutable:
- Use records (Java 16+) for simple immutable data carriers.
- Avoid exposing internals (no direct arrays or collections without copying).
- Pre-compute hashCode if frequently used and object is large.

Wrapper Usage:
- Avoid unnecessary boxing in performance-sensitive loops.
- Use primitive streams (IntStream, LongStream, DoubleStream) to reduce overhead.
- Always use equals() for value comparison.

---
## 11. Common Pitfalls
- Forgetting volatile in double-checked locking (leads to subtle bugs).
- Using synchronized method everywhere → performance bottleneck.
- Returning internal mutable collection directly (breaks immutability).
- Assuming final makes collections unmodifiable.
- Using == to compare Integer values (fails outside cache range).
- Overusing singletons → turning application into a bag of globals.

---
## 12. Comparison Summary
Singleton vs Static Methods:
- Singleton allows interface implementation, inheritance & polymorphism; static methods do not.
- Singleton can maintain state; static utility should be stateless.

Immutable vs Mutable:
- Immutable: Thread-safe inherently, easier reasoning, safe as map keys.
- Mutable: Flexible, fewer allocations, but must manage synchronization.

Enum Singleton vs Holder Pattern:
- Enum simpler + serialization-proof.
- Holder pattern more flexible (can extend interfaces, delay adding enum semantics).

Wrapper vs Primitive:
- Wrapper nullable, heavier (object overhead), stored on heap.
- Primitive faster, stored on stack or optimized by JVM.

---
## 13. Performance Notes
Approximate relative overhead (fastest → slowest):
Holder ≈ Eager < Enum (tiny constant cost) < Double-Checked (volatile read) < Synchronized Method (lock each call) < Lazy Unsynchronized (buggy under concurrency).

---
## 14. When NOT to Use a Singleton
- When object lifecycle should be controlled (scoped beans in DI frameworks).
- When unit testing requires multiple isolated instances.
- When future scaling may require sharding/multiple instances.

---
## 15. Defensive Singleton Against Reflection (Optional Hardening)
```java
public class SecureSingleton {
    private static final SecureSingleton INSTANCE = new SecureSingleton();
    private static boolean created = false;
    private SecureSingleton() {
        if (created) throw new IllegalStateException("Instance already created");
        created = true;
    }
    public static SecureSingleton getInstance() { return INSTANCE; }
}
```
(Still vulnerable to unsafe hacks; enum remains strongest.)

---
## 16. Quick Revision Flashcards
Singleton Order to Remember: Eager → Lazy → Synchronized → Double-Checked + volatile → Holder → Enum.
Immutable Rules: final class + private final fields + no setters + defensive copies.
Wrapper Hazards: == vs equals, null unboxing, boxing in loops.

---
## 17. Practice Exercises
1. Implement a thread-safe logger using Holder pattern.
2. Convert a mutable DTO into an immutable record.
3. Demonstrate a bug from missing volatile in double-checked locking (thought experiment).
4. Measure difference between int sum loop and Integer sum loop (autoboxing overhead).

---
## 18. Suggested Improvements / Extensions
- Explore Supplier-based lazy singletons.
- Analyze singleton misuse in large architectures (anti-pattern in some contexts).
- Learn about Java Memory Model (JMM) happens-before rules.
- Replace manual immutability patterns with record or Lombok (@Value).

---
## 19. Summary
- Singleton ensures single instance but must be implemented carefully in multithreaded contexts.
- Volatile + memory visibility crucial for double-checked locking.
- Bill Pugh and Enum are the cleanest production patterns.
- Immutable classes enhance safety, readability, and thread-friendliness.
- Wrapper classes bridge primitives with object-oriented APIs; use judiciously to avoid overhead.

End of Notes. Happy Revising! 🚀
