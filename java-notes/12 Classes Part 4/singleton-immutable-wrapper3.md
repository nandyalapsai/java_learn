# Java Classes Part 4: Singleton, Immutable, and Wrapper Classes

This document provides a structured summary of Singleton, Immutable, and Wrapper classes in Java, based on the provided transcription.

---

## 1. Singleton Class

### Key Concept & Definition
A Singleton class is a design pattern that restricts the instantiation of a class to one and only one object. This single instance is shared across the entire application, providing a global point of access.

### Real-World Use Cases
- **Database Connections:** Ensuring only one connection pool object is created to manage connections to a database.
- **Logging:** A single logger instance to handle logging throughout the application.
- **Configuration Management:** A single object to hold and provide access to application configuration settings.
- **Hardware Interface Access:** Managing access to a shared resource like a printer or file system.

### Common Interview Questions
- **What is a Singleton class?**
  > A class for which only one instance can be created.
- **Why is it important to make the constructor private?**
  > To prevent external classes from creating new instances using the `new` keyword.
- **Is the lazy initialization approach thread-safe?**
  > No, multiple threads can pass the `null` check simultaneously and create multiple objects.
- **What is the issue with double-checked locking without `volatile`?**
  > It can fail due to CPU caching inconsistencies and instruction reordering, where a thread might see a partially constructed object. The `volatile` keyword resolves this by ensuring reads/writes happen from main memory.

---

### Ways to Implement a Singleton Class

#### a. Eager Initialization
The instance is created at the time of class loading.

- **Best Practice:** Use when the object is not resource-intensive or is always needed.
- **Pitfall:** The object is created even if the application never uses it, leading to wasted resources.

```java
public class DBConnection {
    // 1. Create the object eagerly
    private static DBConnection conObject = new DBConnection();

    // 2. Make constructor private
    private DBConnection() {}

    // 3. Provide a public static method to get the instance
    public static DBConnection getInstance() {
        return conObject;
    }
}
```

#### b. Lazy Initialization
The instance is created only when it's first requested.

- **Best Practice:** Use when the object is resource-heavy and its creation should be delayed.
- **Pitfall:** **Not thread-safe.** Two threads could simultaneously find `conObject` to be `null` and create two separate instances.

```java
public class DBConnection {
    private static DBConnection conObject;

    private DBConnection() {}

    public static DBConnection getInstance() {
        // Check if instance is not created yet
        if (conObject == null) {
            conObject = new DBConnection();
        }
        return conObject;
    }
}
```

#### c. Synchronized Method
This is a thread-safe version of lazy initialization where the `getInstance()` method is synchronized.

- **Best Practice:** A simple way to achieve thread-safety.
- **Pitfall:** High performance cost. The `synchronized` keyword imposes a lock every time the method is called, even after the instance has been created.

```java
public class DBConnection {
    private static DBConnection conObject;

    private DBConnection() {}

    // synchronized makes it thread-safe
    public static synchronized DBConnection getInstance() {
        if (conObject == null) {
            conObject = new DBConnection();
        }
        return conObject;
    }
}
```

#### d. Double-Checked Locking
This approach avoids the performance hit of the synchronized method by synchronizing only the critical section of code.

- **Best Practice:** Use for high-performance, thread-safe lazy initialization.
- **Pitfall:** **Requires the `volatile` keyword.** Without `volatile`, it's not guaranteed to work correctly due to memory reordering and caching issues in multi-threaded environments.

```java
public class DBConnection {
    // volatile is crucial here
    private static volatile DBConnection conObject;

    private DBConnection() {}

    public static DBConnection getInstance() {
        // Check 1 (avoids locking every time)
        if (conObject == null) {
            // Synchronize only when instance is null
            synchronized (DBConnection.class) {
                // Check 2 (ensures only one instance is created)
                if (conObject == null) {
                    conObject = new DBConnection();
                }
            }
        }
        return conObject;
    }
}
```

#### e. Bill Pugh Singleton (Initialization-on-demand holder idiom)
This is the most widely recommended approach. It uses a private static inner class to hold the singleton instance.

- **Best Practice:** Generally the best approach for singletons. It's thread-safe, efficient, and uses lazy initialization.
- **How it works:** The inner class `SingletonHelper` is not loaded into memory until the `getInstance()` method is called, providing lazy loading without the need for `synchronized` blocks.

```java
public class DBConnection {
    private DBConnection() {}

    // Private static inner class
    private static class SingletonHelper {
        private static final DBConnection INSTANCE = new DBConnection();
    }

    public static DBConnection getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
```

#### f. Enum Singleton
The simplest way to implement a singleton. The JVM guarantees that an enum instance is instantiated only once.

- **Best Practice:** Use when you need a simple, robust, and serialization-safe singleton.
- **Advantages:** It's extremely simple and provides built-in protection against creation via reflection and serialization.

```java
public enum DBConnection {
    INSTANCE;

    public void someMethod() {
        // Business logic here
    }
}
```

---

## 2. Immutable Class

### Key Concept & Definition
An immutable class is a class whose object's state (the data stored in its fields) cannot be changed after it has been created. The `String` class in Java is a classic example.

### Best Practices and How to Create
1.  **Declare the class as `final`:** Prevents other classes from extending it and overriding its behavior.
2.  **Make all fields `private` and `final`:** Prevents direct access and ensures fields are initialized only once.
3.  **Provide no `setter` methods:** Disallows modification of field values.
4.  **Initialize all fields in the constructor:** Sets the state of the object at creation time.
5.  **Perform Defensive Copies for Mutable Fields:** If the class contains fields that are mutable objects (e.g., `List`, `Date`), you must return a copy of them in the getter methods to prevent the caller from modifying the internal state.

### Common Pitfalls
- **Returning original mutable objects:** A common mistake is to return the original reference to a mutable field (like a `List`). This allows the caller to modify the internal state of the "immutable" object.

### Example with Defensive Copy

```java
import java.util.ArrayList;
import java.util.List;

// 1. Class is final
public final class ImmutableClass {

    // 2. Fields are private and final
    private final String name;
    private final List<String> petNameList;

    // 4. Initialize all fields in constructor
    public ImmutableClass(String name, List<String> petNameList) {
        this.name = name;
        // 5. Defensive copy in constructor for mutable objects
        this.petNameList = new ArrayList<>(petNameList);
    }

    // 3. No setter methods

    public String getName() {
        return name;
    }

    // 5. Return a copy of the mutable field, not the original
    public List<String> getPetNameList() {
        return new ArrayList<>(petNameList); // Return a copy
    }
}
```

---

## 3. Wrapper Class

### Key Concept & Definition
A Wrapper class in Java is a class that encapsulates (wraps) a primitive data type into an object. This allows primitives to be used in contexts where objects are required, such as in Java Collections (`ArrayList<Integer>`, not `ArrayList<int>`).

### Important Points
- **Primitive to Wrapper Mapping:**
  - `int` -> `Integer`
  - `long` -> `Long`
  - `char` -> `Character`
  - `boolean` -> `Boolean`
  - etc.
- **Autoboxing:** The automatic conversion that the Java compiler makes from a primitive type to its corresponding wrapper class object.
  - `Integer i = 10; // Autoboxing (compiler does Integer.valueOf(10))`
- **Unboxing:** The automatic conversion from a wrapper class object to its corresponding primitive type.
  - `int n = i; // Unboxing (compiler does i.intValue())`

*(As noted in the transcript, this topic is covered in-depth in a previous video, "Java Variables Part 2").*
