# Java Variables: Part 2 - Reference Types, Wrappers, and Constants

These notes summarize the key concepts from the "Java Variables Part 2" video, focusing on reference data types, wrapper classes, and constants.

---

## 1. Reference (Non-Primitive) Data Types

In Java, variables are of two types: primitive and reference (or non-primitive). While primitive types store actual values, reference types store an address (a "reference") to the memory location where an object is stored.

*   **Key Concept**: A reference is a variable that holds the memory address of an object. The object itself resides in the **Heap memory**.
*   **Important Point**: All objects in Java, including arrays and strings, are reference types.

### Pass-by-Value in Java

A common point of confusion is whether Java is pass-by-value or pass-by-reference.

*   **Golden Rule**: **Java is always pass-by-value.**
*   **Explanation**:
    *   When a **primitive type** is passed to a method, its value is copied. Changes inside the method do not affect the original variable.
    *   When a **reference type** (an object) is passed, the value of the reference (the memory address) is copied. Both the original reference and the method's parameter now point to the *same* object in the heap. Therefore, if the method modifies the object's internal state, the change is visible outside the method. This behavior *simulates* pass-by-reference but is technically pass-by-value (of the reference).

---

### Types of Reference Data Types

#### a. Class

When you create an object of a class, you are creating a reference variable.

*   **Key Concept**: The `new` keyword allocates memory on the heap for a new object and returns a reference to that object.

*   **Example**:
    ```java
    class Employee {
        int employeeId;
    }

    // In another class/method
    Employee empObject = new Employee(); // 'empObject' holds a reference to the new Employee object.
    empObject.employeeId = 10; // Modifies the object in the heap.

    modify(empObject);

    System.out.println(empObject.employeeId); // Output: 20

    public void modify(Employee emp) { // 'emp' is a copy of the reference, pointing to the SAME object.
        emp.employeeId = 20; // The original object is modified.
    }
    ```

*   **Diagram (Memory Representation)**:
    ```
    Stack Memory              Heap Memory
    +----------------+        +--------------------------------+
    | empObject (ref) | ----> | Employee Object (employeeId: 20) |
    +----------------+        +--------------------------------+
    | emp (ref copy)  | ----> | (Same Object)                  |
    +----------------+        +--------------------------------+
    ```

#### b. String

Strings are a special reference type in Java. Although they seem to work like primitives, they are objects.

*   **Key Concepts**:
    *   **String Immutability**: Once a String object is created, its value cannot be changed. Any modification creates a *new* String object.
    *   **String Constant Pool (SCP)**: A special area in the heap memory. When you create a string literal (e.g., `"Hello"`), Java checks the SCP. If the literal already exists, it returns a reference to the existing one; otherwise, it creates a new one in the pool.
    *   **`new` Keyword**: Using `new String("Hello")` forces the creation of a new object in the heap, outside the SCP.

*   **Example**:
    ```java
    String s1 = "Hello"; // Goes into String Constant Pool
    String s2 = "Hello"; // s2 points to the SAME object as s1 in the pool
    String s3 = new String("Hello"); // A new object is created in the heap

    // Comparison
    System.out.println(s1 == s2);      // true (both references point to the same memory location in SCP)
    System.out.println(s1 == s3);      // false (s1 is in SCP, s3 is a separate object in the heap)
    System.out.println(s1.equals(s3)); // true (the content/value is the same)
    ```

*   **Best Practices & Pitfalls**:
    *   **Best Practice**: Use the `.equals()` method to compare string values for equality.
    *   **Pitfall**: Using `==` to compare string values. It only checks if two references point to the exact same object, which can lead to unexpected results.

#### c. Interface

Like classes, interfaces are reference types. You cannot create an object of an interface directly, but you can create a reference variable of an interface type.

*   **Key Concept**: An interface reference can hold an object of any class that *implements* that interface. This is a core principle of polymorphism.

*   **Example**:
    ```java
    interface Person {
        void profession();
    }

    class Engineer implements Person {
        public void profession() { System.out.println("Software Engineer"); }
    }

    // This is valid: Parent interface reference holding a child class object
    Person personObj = new Engineer();
    personObj.profession(); // Output: Software Engineer

    // This is invalid and causes a compilation error:
    // Person p = new Person();
    ```

#### d. Array

Arrays are objects and therefore reference types.

*   **Key Concept**: An array variable holds a reference to a contiguous block of memory in the heap that stores the array elements.

*   **Example**:
    ```java
    // Declaration and instantiation
    int[] arr = new int[5]; // 'arr' is a reference to an array object of 5 integers.

    // Initialization
    arr[0] = 10;
    arr[1] = 20;

    // Another way to create and initialize
    int[] arr2 = {10, 20, 30, 40, 50};
    ```

---

## 2. Wrapper Classes

For every primitive data type in Java, there is a corresponding wrapper class.

*   **Key Concept**: Wrapper classes "wrap" a primitive value inside an object, allowing primitives to be treated like objects.

*   **Primitive -> Wrapper**:
    *   `int` -> `Integer`
    *   `char` -> `Character`
    *   `double` -> `Double`
    *   `boolean` -> `Boolean`
    *   (and so on for `byte`, `short`, `long`, `float`)

*   **Autoboxing & Unboxing**:
    *   **Autoboxing**: The automatic conversion of a primitive type to its corresponding wrapper class object.
        ```java
        int primitiveInt = 10;
        Integer wrapperInt = primitiveInt; // Autoboxing
        ```
    *   **Unboxing**: The automatic conversion of a wrapper class object back to its primitive type.
        ```java
        Integer wrapperInt = Integer.valueOf(20);
        int primitiveInt = wrapperInt; // Unboxing
        ```

*   **Real-World Use Cases / Why We Need Them**:
    1.  **Java Collections**: The Java Collections Framework (e.g., `ArrayList`, `HashMap`) can only store objects, not primitive types. Wrapper classes are essential to store primitive values in collections.
        ```java
        // ArrayList<int> list = new ArrayList<>(); // This is NOT allowed
        ArrayList<Integer> list = new ArrayList<>(); // This is correct
        list.add(10); // Autoboxing happens here
        ```
    2.  **Reference Capabilities**: To achieve reference-like behavior (e.g., modifying a value within a method and having it reflect outside) with primitive values.

---

## 3. Constant Variables

A constant is a variable whose value cannot be changed once it has been assigned.

*   **Key Concept**: In Java, constants are created using the `static` and `final` keywords.
    *   `final`: Ensures the value cannot be modified.
    *   `static`: Ensures there is only one copy of the variable, shared among all instances of the class.

*   **Best Practice (Naming Convention)**: Constant variable names should be in all uppercase letters, with words separated by underscores (`_`).

*   **Example**:
    ```java
    public class Configuration {
        // This is a constant variable
        public static final int MAX_CONNECTIONS = 100;
    }

    // In another class
    int max = Configuration.MAX_CONNECTIONS; // Accessing the constant

    // Configuration.MAX_CONNECTIONS = 200; // This will cause a compilation error
    ```

---

## 4. Interview Questions & Answers

1.  **Is Java pass-by-value or pass-by-reference?**
    *   **Answer**: Java is strictly pass-by-value. For primitives, the value is copied. For objects, the reference (memory address) is copied. Because the reference is copied, modifications to the object's state inside a method are visible to the caller.

2.  **What is the difference between `==` and `.equals()` for Strings?**
    *   **Answer**: `==` is a reference comparison operator; it checks if two variables point to the exact same object in memory. `.equals()` is a method that compares the actual content (sequence of characters) of the strings.

3.  **Why are Strings immutable in Java?**
    *   **Answer**: Immutability provides security, thread-safety, and allows for performance optimizations like the String Constant Pool. Since multiple string references can point to the same object in the pool, making it immutable prevents one reference from changing the value for all others.

4.  **Why do we need wrapper classes?**
    *   **Answer**: Primarily for two reasons: 1) To use primitive values in Java Collections, which only accept objects. 2) To provide utility methods related to primitives and to allow primitives to be treated as objects when needed (e.g., in generics).

5.  **Can you create an object of an interface?**
    *   **Answer**: No, you cannot instantiate an interface directly using the `new` keyword because interfaces can contain abstract methods with no implementation. However, you can create an object of a class that *implements* the interface and assign it to a reference variable of the interface type.
