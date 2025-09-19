# Java POJO, Enum, and Final Classes

This document provides a comprehensive summary of POJO (Plain Old Java Object), Enum, and Final classes in Java, based on the provided transcription.

---

## 1. POJO (Plain Old Java Object)

### Key Concepts & Definitions

*   **POJO** stands for **Plain Old Java Object**.
*   It is a simple Java class with no special restrictions or fancy features. It primarily contains variables and their corresponding getter and setter methods.

### Important Points (Properties)

*   **Public Class:** The class itself should be declared as `public`.
*   **Public Default Constructor:** It must have a public, no-argument constructor.
*   **No Annotations:** It should not use framework-specific annotations like `@Entity`, `@Table`, etc.
*   **No Inheritance/Implementation:** It should not extend any other class or implement any interface.
*   **No Restrictions on Variables:** Fields can have any access modifier (public, private, protected, default).

### Example: Student POJO

```java
public class Student {
    // Variables can have any access modifier
    String defaultName;
    private String privateName;
    protected int protectedAge;
    public String publicId;

    // Public default constructor (implicitly present if no other constructor is defined)
    public Student() {
    }

    // Getter and Setter methods for its variables
    public String getPrivateName() {
        return privateName;
    }

    public void setPrivateName(String privateName) {
        this.privateName = privateName;
    }

    // ... other getters and setters
}
```

### Real-World Use Cases

1.  **Data Transfer Object (DTO):** To map incoming request data (e.g., from a JSON payload) to a component-specific object. This decouples the component's internal data structure from the external API contract.
    *   **Scenario:** A client sends a request with `{"id": 123, "name": "John"}`. Instead of using this raw object throughout the application, you map it to a `CustomerPojo` with fields like `customerId` and `customerName`. This isolates your internal logic from any changes in the request format.

2.  **Data Transfer Between Layers:** To pass data between different layers of an application (e.g., Controller -> Service -> Repository).

### Diagram: POJO for Request Mapping

```
+--------+      +---------------------------+      +---------------------+
| Client |----->|        My Component       |----->|   Internal Classes  |
+--------+      |                           |      | (Class 1, Class 2)  |
(Request:      | +-----------------------+ |      +---------------------+
 id, name)     | |   POJO (customerId,   | |               ^
                | |   customerName)       | |               |
                | +-----------------------+ |               |
                | (1-to-1 Mapping)          |---------------+
                +---------------------------+
```

### Best Practices & Common Pitfalls

*   **Best Practice:** Use POJOs to create a boundary between external data and your application's core logic. This makes your code more maintainable and resilient to API changes.
*   **Pitfall:** Directly using request/response objects throughout your business logic. This can lead to tight coupling and widespread code changes if the external API is modified.

---

## 2. Enum (Enumeration)

### Key Concepts & Definitions

*   An **enum** is a special class that represents a collection of **constants**.
*   The constants defined within an enum are implicitly `public`, `static`, and `final`.

### Important Points (Properties)

*   **Implicit Inheritance:** Every enum in Java implicitly extends the `java.lang.Enum` class.
*   **Cannot Extend Other Classes:** Because it already extends `java.lang.Enum`, an enum cannot extend any other class.
*   **Can Implement Interfaces:** An enum can implement one or more interfaces.
*   **Private Constructors:** Enum constructors are always `private`. You cannot create an instance of an enum using the `new` keyword.
*   **Cannot Be Extended:** No other class can extend an enum.
*   **Can have:**
    *   Variables
    *   Methods
    *   Constructors (private)
    *   Abstract methods (which must be implemented by every constant).

### Examples

#### a. Simple Enum

Constants have default integer values (`ordinals`) starting from 0.

```java
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY; // Semicolon is important
}
```

#### b. Using `values()` and `ordinal()`

*   `values()`: Returns an array of all enum constants.
*   `ordinal()`: Returns the zero-based position of a constant.

```java
// Iterate over all constants
for (Day day : Day.values()) {
    // Prints the name and its default index (0 for MONDAY, 1 for TUESDAY, etc.)
    System.out.println(day.name() + ": " + day.ordinal());
}
```

#### c. Enum with Custom Values and Methods

You can add custom fields and methods to enums.

```java
public enum Day {
    // Each constant calls the private constructor
    MONDAY(101, "First day"),
    TUESDAY(102, "Second day"),
    // ...
    SUNDAY(107, "Weekend!");

    private final int value;
    private final String comment;

    // Constructor is always private
    private Day(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    // Getter methods
    public int getValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }
}
```

#### d. Enum with Abstract Method

Each constant must provide an implementation for the abstract method.

```java
public enum Operation {
    PLUS {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    };

    public abstract double apply(double x, double y);
}

// Usage:
// double result = Operation.PLUS.apply(5, 3); // result is 8.0
```

### Comparison: Enum vs. `static final` Constants

| Feature             | `enum`                                                              | `static final` variables (e.g., `int`)                               |
| ------------------- | ------------------------------------------------------------------- | -------------------------------------------------------------------- |
| **Type Safety**     | **High.** A method expecting `Day` cannot accept an `int`.          | **None.** A method expecting an `int` can be passed any integer value. |
| **Readability**     | **High.** `isWeekend(Day.SUNDAY)` is self-explanatory.              | **Low.** `isWeekend(6)` is ambiguous without context.                  |
| **Namespace**       | Provides its own namespace (e.g., `Day.MONDAY`).                    | Pollutes the class namespace.                                        |
| **Functionality**   | Can have constructors, methods, and implement interfaces.           | Just a constant value.                                               |
| **Control**         | You can only pass valid, predefined constants.                      | No control over the range of values passed to a method.              |

### Common Interview Questions

*   **Q: Why use enums when we can use `static final` constants?**
    *   **A:** Enums provide type safety, improved readability, and a dedicated namespace. They prevent passing invalid values to methods and allow for adding more complex behavior (fields, methods) to the constants themselves.

*   **Q: Can an enum extend a class?**
    *   **A:** No, because every enum implicitly extends `java.lang.Enum`.

*   **Q: Can an enum's constructor be public?**
    *   **A:** No, enum constructors must be private to ensure that only the predefined set of constants can be created.

---

## 3. Final Class

### Key Concepts & Definitions

*   A class declared with the `final` keyword is a **final class**.
*   The primary purpose of a final class is to **prevent inheritance**.

### Important Points

*   A final class cannot be subclassed (extended).
*   This is often done for security or design reasons, to ensure the behavior of the class cannot be altered through overriding.

### Example

```java
// This class cannot be extended
public final class SecureSystemConfig {
    private final String apiKey;

    public SecureSystemConfig(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }
}

// The following code will cause a compilation error:
// class MaliciousConfig extends SecureSystemConfig { ... }
// ERROR: cannot inherit from final SecureSystemConfig
```

### Real-World Use Cases

*   **Immutable Classes:** Classes like `String` and `Integer` in Java are `final` to ensure their immutability. If `String` were not final, one could create a subclass that alters its behavior, breaking the contract of immutability.
*   **Security:** To prevent subclasses from overriding methods and compromising the intended functionality of a security-critical class.
