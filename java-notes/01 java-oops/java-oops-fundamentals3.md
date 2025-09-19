# Java OOP Fundamentals - Summary Notes

This document provides a comprehensive summary of Object-Oriented Programming (OOP) concepts in Java, based on the provided transcription. It's structured for quick revision and interview preparation.

---

## 1. OOP Overview

### Key Concepts & Definitions

*   **Object-Oriented Programming (OOP):** A programming paradigm that revolves around "objects" rather than functions and logic. It focuses on the objects that developers want to manipulate rather than the logic required to manipulate them.
*   **Procedural Programming:** A paradigm based on the concept of procedure calls, where statements are structured into procedures (or functions). It focuses on functions over data.

### Comparison: OOP vs. Procedural Programming

| Feature | Object-Oriented Programming (OOP) | Procedural Programming |
| :--- | :--- | :--- |
| **Focus** | Data | Functions/Procedures |
| **Data Security** | High (Data Hiding via Encapsulation) | Low (Data moves freely between functions) |
| **Coupling** | Promotes Loosely Coupled code | Often results in Tightly Coupled code |
| **Core Concepts** | Objects, Classes, Inheritance, Polymorphism | Functions, Data Structures |
| **Code Reusability**| High (achieved via Inheritance) | Low |
| **Example** | Java, Python, C++ | C, Pascal |

---

## 2. Objects and Classes

### Key Concepts & Definitions

*   **Object:** A real-world entity that has two main characteristics:
    *   **Properties/State:** Attributes that describe the object (e.g., a dog's color, breed, age).
    *   **Behavior/Functionality:** Actions that the object can perform (e.g., a dog can bark, sleep, eat).
*   **Class:** A blueprint or template from which objects are created. It defines the properties (data variables) and behaviors (methods) that all objects of that type will have.

### Example: `Student` Class

A `Student` class can define the common properties and behaviors of all students.

```java
// Blueprint for a Student
public class Student {
    // 1. Properties (Data Variables)
    String name;
    int age;
    String address;

    // 2. Behavior (Data Methods)
    public void updateAddress(String newAddress) {
        this.address = newAddress;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int newAge) {
        this.age = newAge;
    }
}
```

### Creating Objects from a Class

From a single class, multiple objects can be created, each with its own state.

```java
// Creating two different student objects from the Student class
Student engineeringStudent = new Student(); // Object 1
engineeringStudent.setAge(23);

Student mbaStudent = new Student(); // Object 2
mbaStudent.setAge(25);
```

---

## 3. The Four Pillars of OOP

### 1. Data Abstraction

*   **Definition:** The concept of hiding complex implementation details and showing only the essential features of the object. It answers **"What it does"** but hides **"How it does it"**.
*   **Real-World Use Case:** When you press the brake pedal in a car, you know it will reduce the speed, but the internal mechanics of how the braking system works are hidden from you.
*   **How to Achieve in Java:** Using `abstract classes` and `interfaces`.
*   **Advantages:**
    *   **Simplicity:** Simplifies the client's interaction with the object.
    *   **Security & Confidentiality:** Hides internal logic and prevents the user from manipulating the internal state in an unintended way.
*   **Example:** An interface for a `Car` exposes what a user can do, but not how it's done.

    ```java
    // Interface showing only essential functionality
    interface Car {
        void applyBrake();
        void pressAccelerator();
    }

    // Implementation is hidden from the user
    class MyCar implements Car {
        @Override
        public void applyBrake() {
            // Complex internal logic:
            // Step 1: Reduce fuel flow
            // Step 2: Engage brake pads
            // Step 3: ...
            System.out.println("Car speed reduced.");
        }

        @Override
        public void pressAccelerator() {
            // ... internal implementation
            System.out.println("Car speed increased.");
        }
    }
    ```

### 2. Data Encapsulation (Data Hiding)

*   **Definition:** The bundling of data (properties) and the methods that operate on that data into a single unit (a class). It restricts direct access to some of an object's components, which is a means of preventing accidental interference and misuse of the data.
*   **Real-World Use Case:** A medicine capsule encloses several combinations of medicine in a single unit. The outer layer (capsule) protects the inner contents from the outside world.
*   **How to Achieve in Java:**
    1.  Declare the variables of a class as `private`.
    2.  Provide `public` setter and getter methods to modify and view the variables' values.
*   **Advantages:**
    *   **Control:** The class maintains full control over its data.
    *   **Loose Coupling:** Reduces dependencies between components, making the code more modular and easier to maintain.
*   **Example:** A `Dog` class encapsulates the `color` variable.

    ```java
    public class Dog {
        // 1. Data is private, hidden from other classes
        private String color;

        // 2. Public getter provides controlled access
        public String getColor() {
            return color;
        }

        // 3. Public setter allows controlled modification
        public void setColor(String newColor) {
            // We can add validation logic here
            this.color = newColor;
        }
    }
    ```

### 3. Inheritance

*   **Definition:** A mechanism where a new class (child/subclass) derives properties and behaviors from an existing class (parent/superclass). It represents an **"is-a"** relationship.
*   **Real-World Use Case:** You inherit certain traits from your parents. A `Car` is a type of `Vehicle`.
*   **How to Achieve in Java:** Using the `extends` keyword for classes and `implements` for interfaces.
*   **Advantages:**
    *   **Code Reusability:** Avoids redundant code by reusing the parent's logic.
    *   **Polymorphism:** Enables method overriding.
*   **Types of Inheritance & Diagram:**

    *   **Single:** `B -> A` (Class B extends Class A)
    *   **Multi-level:** `C -> B -> A` (Class C extends B, which extends A)
    *   **Hierarchical:** `B -> A` and `C -> A` (Two classes extend the same parent)
    *   **Multiple (Not supported in Java via classes):** `C -> A` and `C -> B`. This is not allowed to prevent the "Diamond Problem".

*   **Common Interview Question: The Diamond Problem**
    *   **Question:** Why does Java not support multiple inheritance with classes?
    *   **Answer:** Consider a `Class C` that extends both `Class A` and `Class B`. If both `A` and `B` have a method with the same signature (e.g., `void myMethod()`), the compiler gets confused about which version of `myMethod()` to inherit for `Class C`. This ambiguity is called the Diamond Problem. Java solves this by allowing multiple inheritance only through interfaces, which declare methods without implementing them, forcing the child class to provide a single implementation.

### 4. Polymorphism

*   **Definition:** From "poly" (many) and "morphism" (forms). It is the ability of an object to take on many forms. In practice, it means a single method name can have different behaviors in different contexts.
*   **Real-World Use Case:** A person can be a father, a husband, and an employee at the same time, behaving differently in each role. Water can exist as liquid, solid (ice), or gas (steam).

#### Types of Polymorphism

**1. Compile-Time Polymorphism (Static / Method Overloading)**

*   **How it works:** Occurs within the same class, where two or more methods have the same name but different parameters (different number, type, or order of parameters). The correct method to call is decided at compile time.
*   **Example:**

    ```java
    class Calculator {
        // Method 1
        public int add(int a, int b) {
            return a + b;
        }

        // Method 2 (Overloaded): Same name, different parameters
        public int add(int a, int b, int c) {
            return a + b + c;
        }

        // Method 3 (Overloaded): Same name, different parameter types
        public double add(double a, double b) {
            return a + b;
        }
    }
    ```
*   **Common Pitfall:** Method overloading cannot be achieved by changing only the return type of the method. This will cause a compilation error.

**2. Run-Time Polymorphism (Dynamic / Method Overriding)**

*   **How it works:** Occurs when a subclass provides a specific implementation for a method that is already defined in its superclass. The method to be called is determined at runtime based on the object type.
*   **Prerequisites:** Inheritance is required. The method signature (name, parameters) must be the same in both the parent and child class.
*   **Example:**

    ```java
    class Animal {
        public void makeSound() {
            System.out.println("Animal makes a sound");
        }
    }

    class Dog extends Animal {
        // Overriding the parent's method
        @Override
        public void makeSound() {
            System.out.println("Dog barks");
        }
    }

    // At runtime, the JVM decides which method to call
    Animal myDog = new Dog();
    myDog.makeSound(); // Output: "Dog barks"
    ```

---

## 4. Relationships Between Classes

### 1. Is-A Relationship (Inheritance)

*   **Concept:** Represents inheritance. A `Car` **is a** `Vehicle`. A `Dog` **is an** `Animal`.
*   **Implementation:** Achieved using the `extends` keyword.

### 2. Has-A Relationship (Association)

*   **Concept:** Represents containment. An object of one class "has" an object of another class as a member variable. A `School` **has a** `Student`. A `Bike` **has an** `Engine`.
*   **Implementation:** Achieved by having an instance of one class as a field in another class.

#### Types of Has-A Relationship

**1. Aggregation (Weak "has-a" relationship)**

*   **Concept:** Both objects can exist independently. If the containing object is destroyed, the contained object can still exist.
*   **Example:** A `School` has a list of `Student`s. If the school closes down, the students still exist and can enroll in another school.

**2. Composition (Strong "has-a" relationship)**

*   **Concept:** The contained object is a part of the containing object and cannot exist independently. If the containing object is destroyed, the contained object is also destroyed.
*   **Example:** A `School` has `Room`s. If the school is demolished, the rooms within it are also destroyed. They cannot exist without the school.
