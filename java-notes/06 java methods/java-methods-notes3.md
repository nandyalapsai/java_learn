# Java Methods: Comprehensive Notes

This document provides a detailed summary of Java methods, based on the "Concept and Coding" video. It's structured for quick revision and interview preparation.

---

### 1. What is a Method?

#### Key Concepts & Definitions
- **Method**: A collection of instructions that performs a specific task. It encapsulates a block of code that can be executed by calling the method's name.
- **Purpose**: To perform a task, process data, and return a result.
- **Advantages**:
    - **Code Reusability**: Write a method once and use it multiple times.
    - **Code Readability**: Methods with clear names make the code easier to understand.

#### Example
A simple method to add two numbers:
```java
public class Calculation {
    // Method definition
    public int sum(int a, int b) {
        int total = a + b;
        System.out.println("Summing numbers...");
        return total;
    }
}
```

---

### 2. Method Declaration

A method declaration has several components:

```java
public int getSum(int a, int b) throws ArithmeticException {
    // method body
}
```

| Component | Example | Description |
| :--- | :--- | :--- |
| **Access Specifier** | `public` | Controls the visibility of the method. |
| **Return Type** | `int` | The data type of the value the method returns. Use `void` if it returns nothing. |
| **Method Name** | `getSum` | The name of the method. Should follow naming conventions. |
| **Parameter List** | `(int a, int b)` | A comma-separated list of input variables. Can be empty. |
| **Exception List** | `throws ...` | Declares exceptions the method might throw. |
| **Method Body** | `{...}` | The code block containing the method's logic. |

---

### 3. Access Specifiers

Access specifiers determine where a method can be accessed.

| Specifier | Accessibility | Diagram |
| :--- | :--- | :--- |
| **`public`** | Global access. Can be accessed from any class in any package. | `[Package A -> Class X]` can access `[Package B -> Class Y -> public method]` |
| **`private`** | Restricted to the same class only. | `[Class X -> method A]` can access `[Class X -> private method B]` |
| **`protected`** | Same package + Subclasses in different packages. | 1. `[Package A -> Class X]` can access `[Package A -> Class Y -> protected method]`<br>2. `[Package B -> Class Z (subclass)]` can access `[Package A -> Class Y -> protected method]` |
| **`default`** | (No keyword) Restricted to the same package only. | `[Package A -> Class X]` can access `[Package A -> Class Y -> default method]` |

---

### 4. Types of Methods

#### 4.1. System-Defined vs. User-Defined

| Type | Description | Example |
| :--- | :--- | :--- |
| **System-Defined** | Methods that are part of the Java API libraries (JRE). | `Math.sqrt(25);` |
| **User-Defined** | Methods created by the programmer to meet application requirements. | `public int sum(int a, int b) { ... }` |

#### 4.2. Overloaded vs. Overridden Methods (Polymorphism)

| Feature | Overloading (Static Polymorphism) | Overriding (Dynamic Polymorphism) |
| :--- | :--- | :--- |
| **Definition** | Multiple methods in the same class with the same name but different parameters. | A subclass provides its own implementation of a method from its parent class. |
| **Purpose** | To have multiple ways of performing a similar action. | To change the behavior of a parent class method. |
| **Parameters** | Must be different (number, type, or order). | Must be the same. |
| **Return Type** | Can be different. Not considered for differentiation. | Must be the same (or a covariant type). |
| **Binding** | Resolved at **compile-time**. | Resolved at **run-time**. |
| **Example** | ```java class Display { void show(int a) {...} void show(String s) {...} } ``` | ```java class Animal { void sound() {...} } class Dog extends Animal { @Override void sound() {...} } ``` |

#### 4.3. Static Methods

- **Definition**: A method that belongs to the **class** rather than an instance (object) of the class.
- **Invocation**: Called using the class name: `ClassName.staticMethod();`. No object creation is needed.
- **Key Rules & Pitfalls**:
    - **Cannot access instance variables/methods**: A static method cannot directly access non-static members because it doesn't belong to a specific object.
    - **Cannot be overridden**: Static methods are resolved at compile-time. A subclass with a same-signature static method *hides* the parent's method, it doesn't override it.
- **Real-World Use Case**:
    - **Utility Methods**: Methods that perform a calculation based only on their arguments, without needing to modify an object's state. `Math.max()`, `Integer.parseInt()`.
    - **Factory Methods**: A static method that returns an instance of the class (e.g., in Factory Design Pattern).

```java
public class MathUtils {
    // Static utility method
    public static int add(int a, int b) {
        return a + b; // Doesn't use any instance variables
    }
}
// Usage:
int result = MathUtils.add(5, 10);
```

#### 4.4. Final Methods

- **Definition**: A method that cannot be overridden by a subclass.
- **Purpose**: To prevent subclasses from altering a critical implementation.
- **Example**:
```java
public class Vehicle {
    public final void startEngine() {
        // This implementation is final and cannot be changed.
    }
}
```

#### 4.5. Abstract Methods

- **Definition**: A method declared without an implementation (no body), marked with the `abstract` keyword.
- **Rules**:
    - Can only be declared in an `abstract` class.
    - The subclass that extends the abstract class **must** provide an implementation for all abstract methods.
- **Purpose**: To force subclasses to provide their own specific implementation for a method.

```java
abstract class Shape {
    // Abstract method - no body
    abstract void draw();
}

class Circle extends Shape {
    // Child class provides the implementation
    @Override
    void draw() {
        System.out.println("Drawing a circle.");
    }
}
```

---

### 5. Variable Arguments (Varargs)

- **Definition**: A feature that allows a method to accept zero or more arguments of a specified type.
- **Syntax**: Use three dots (`...`) after the data type. `void myMethod(String... values)`
- **Internal Representation**: Varargs are treated as an array within the method.
- **Rules & Best Practices**:
    1.  A method can have **only one** varargs parameter.
    2.  The varargs parameter must be the **last argument** in the method's parameter list.

#### Example
```java
public class Calculator {
    // Using varargs to sum an unknown number of integers
    public int sum(int... numbers) {
        int total = 0;
        for (int num : numbers) {
            total += num;
        }
        return total;
    }
}

// Usage:
Calculator calc = new Calculator();
calc.sum();           // Returns 0
calc.sum(1, 2);       // Returns 3
calc.sum(5, 10, 15, 20); // Returns 50
```

---

### 6. Interview Questions & Quick Answers

1.  **What's the difference between method overloading and overriding?**
    - Overloading is in the same class with the same name but different parameters (compile-time polymorphism). Overriding is in a subclass with the same signature as the parent's method (run-time polymorphism).

2.  **Why can't a static method access a non-static variable?**
    - Static methods belong to the class, not a specific object. Non-static variables belong to objects. The static method doesn't know which object's variable to access.

3.  **Can you override a static method?**
    - No. A static method in a subclass with the same signature *hides* the parent's method, which is resolved at compile-time. It is not dynamic run-time overriding.

4.  **When should you declare a method as `static`?**
    - For utility functions that don't depend on or modify an object's state. For example, a method that calculates a value based purely on its input arguments.

5.  **What is a `final` method?**
    - A method that cannot be overridden by subclasses. It's used to enforce a consistent implementation.

6.  **What is the rule for varargs in a method signature?**
    - It must be the last parameter in the argument list, and there can only be one per method.
