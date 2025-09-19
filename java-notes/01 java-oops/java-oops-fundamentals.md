# Java OOP Fundamentals - Complete Notes

## Table of Contents
1. [Introduction to OOP](#introduction-to-oop)
2. [Objects and Classes](#objects-and-classes)
3. [Four Pillars of OOP](#four-pillars-of-oop)
4. [Relationships in OOP](#relationships-in-oop)
5. [Interview Questions](#interview-questions)
6. [Best Practices](#best-practices)

---

## Introduction to OOP

### What is OOP?
**Object-Oriented Programming (OOP)** - A programming paradigm oriented towards **objects**. Everything revolves around objects, which are real-world entities.

### OOP vs Procedural Programming

| **Procedural Programming** | **Object-Oriented Programming** |
|---------------------------|----------------------------------|
| Focuses on **functions** | Focuses on **data** and objects |
| Data moves freely between functions | Provides **data hiding** |
| No control over data manipulation | Full control over data access |
| Tightly coupled code | Loosely coupled code |
| Functions are primary building blocks | Classes and objects are primary building blocks |

### Advantages of OOP
- **Data hiding** and security
- **Code reusability** through inheritance
- **Better organization** of code
- **Modularity** and maintainability
- **Loosely coupled** architecture

---

## Objects and Classes

### What is an Object?
An **object** is a real-world entity that has two essential components:

1. **Properties/State** - Attributes you can observe
2. **Behavior/Functionality** - Actions the object can perform

#### Examples:
```java
// Dog Object
Properties: color = "black", height = "medium", age = 6
Behavior: bark(), sleep(), eat()

// Car Object  
Properties: brand = "Toyota", type = "sedan", color = "red"
Behavior: applyBrake(), drive(), increaseSpeed()
```

### What is a Class?
A **class** is a **blueprint** or **template** from which objects are created.

```java
// Class Definition
public class Student {
    // Data Variables (Properties)
    private int age;
    private String address;
    
    // Data Methods (Behavior)
    public void updateAddress(String newAddress) {
        this.address = newAddress;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
}
```

### Creating Objects from Classes
```java
// Creating multiple objects from same class
Student engineeringStudent = new Student();
Student mbaStudent = new Student();
Student caStudent = new Student();

// Each object has its own property values
engineeringStudent.setAge(23);
mbaStudent.setAge(25);
```

### Key Components of a Class
- **Data Variables** - Store object properties
- **Data Methods** - Define object behavior and work on data variables

---

## Four Pillars of OOP

## 1. Data Abstraction

### Definition
**Abstraction** hides internal implementation details and shows only essential functionality to the user.

### Real-world Examples
- **Car Brake Pedal**: Press → Speed reduces (How it works internally is hidden)
- **Mobile Phone**: Dial number + Press call → Call made (Network protocols hidden)
- **ATM Machine**: Insert card + Enter PIN → Access account (Banking processes hidden)

### Implementation in Java
```java
// Interface (Abstraction)
public interface Car {
    public void applyBrake();
    public void pressAccelerator();
    public void pressHorn();
}

// Implementation class
public class CarImplementation implements Car {
    @Override
    public void applyBrake() {
        // Step 1: Reduce fuel injection
        // Step 2: Apply brake pads
        // Step 3: Reduce wheel rotation
        // Step 4: Decrease speed
        // Step 5: Stop vehicle
        // User only knows: press brake → speed reduces
    }
}
```

### Advantages
- **Security & Confidentiality**
- **Simplified client code**
- **Prevents information overload**
- **Better code organization**

---

## 2. Data Encapsulation

### Definition
**Encapsulation** bundles data and the methods that operate on that data into a single unit (class). Also known as **Data Hiding**.

### Implementation with Access Modifiers
```java
public class Dog {
    // Private data member (hidden from outside)
    private String color;
    
    // Public methods (controlled access)
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
}
```

### Access Modifiers
| Modifier | Access Level |
|----------|-------------|
| `private` | Same class only |
| `public` | Accessible everywhere |
| `protected` | Same package + subclasses |
| `default` | Same package only |

### Benefits
- **Data Control** - Class has full control over its data
- **Security** - Data cannot be directly accessed
- **Loosely Coupled Code** - Reduces dependencies between classes

### Real-world Analogy
Think of a **medicine capsule** - the actual medicine (data) is protected inside the capsule (class), and you can only access it through the proper interface.

---

## 3. Inheritance

### Definition
**Inheritance** is the capability of a class to inherit properties and methods from a parent class.

### Basic Syntax
```java
// Parent class
public class Vehicle {
    protected boolean hasEngine;
    
    public boolean getEngine() {
        return hasEngine;
    }
}

// Child class
public class Car extends Vehicle {
    private String type; // Car's own property
    
    public String getCarType() {
        return type;
    }
}
```

### Usage Example
```java
Car swift = new Car();
swift.getEngine();    // ✅ Valid - inherited from Vehicle
swift.getCarType();   // ✅ Valid - Car's own method

Vehicle myVehicle = new Vehicle();
myVehicle.getCarType(); // ❌ Invalid - Vehicle doesn't have this method
```

### Types of Inheritance

#### 1. Single Inheritance
```
Class A → Class B
```

#### 2. Multi-level Inheritance
```
Class A → Class B → Class C
```

#### 3. Hierarchical Inheritance
```
     Class A
    ↙       ↘
Class B   Class C
```

#### 4. Multiple Inheritance (Not Supported in Java)
```
Class A   Class B
    ↘    ↙
    Class C
```

### Diamond Problem
**Why Multiple Inheritance is not allowed in Java:**

```java
class A {
    public void getEngine() { return true; }
}

class B {
    public void getEngine() { return false; }
}

// This is NOT allowed in Java
class C extends A, B {  // ❌ Compilation Error
    // Which getEngine() method should C inherit?
    // Ambiguity leads to Diamond Problem
}
```

**Solution:** Use interfaces instead of classes for multiple inheritance.

### Benefits
- **Code Reusability**
- **Method Overriding** capability
- **Polymorphism** support

---

## 4. Polymorphism

### Definition
**Polymorphism** = "Many forms" - Same method can behave differently in different situations.

### Real-world Examples
- **Person**: Can be father, husband, employee (same person, different roles)
- **Water**: Can be liquid, solid, gas (same substance, different states)

### Types of Polymorphism

#### 1. Method Overloading (Compile-time/Static Polymorphism)
```java
public class Sum {
    // Same method name, different parameters
    public int doSum(int a, int b) {
        return a + b;
    }
    
    public String doSum(String a, String b) {
        return a + b;
    }
    
    public int doSum(int a, int b, int c) {
        return a + b + c;
    }
}
```

**Rules for Method Overloading:**
- ✅ Different number of parameters
- ✅ Different types of parameters  
- ❌ Different return types only (not allowed)

#### 2. Method Overriding (Runtime/Dynamic Polymorphism)
```java
// Parent class
public class Animal {
    public void makeSound() {
        System.out.println("Animal makes sound");
    }
}

// Child class
public class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Dog barks");
    }
}
```

**Usage:**
```java
Animal animal = new Animal();
animal.makeSound(); // Output: "Animal makes sound"

Dog dog = new Dog();
dog.makeSound();    // Output: "Dog barks"
```

**Rules for Method Overriding:**
- ✅ Same method name
- ✅ Same parameters
- ✅ Same return type
- ✅ Only implementation differs

---

## Relationships in OOP

### 1. IS-A Relationship (Inheritance)
Represents **inheritance** relationship between classes.

```java
class Vehicle { }
class Car extends Vehicle { }

// Car IS-A Vehicle
```

**Examples:**
- Dog IS-A Animal
- Car IS-A Vehicle  
- Student IS-A Person

### 2. HAS-A Relationship (Composition/Aggregation)
One class **contains** an object of another class.

```java
public class Student {
    private Course course;        // 1-to-1 relationship
    private List<Course> courses; // 1-to-many relationship
}
```

#### Types of HAS-A Relationships:

##### A. Aggregation (Weak Relationship)
Objects can exist independently.
```java
public class School {
    private List<Student> students;
}
// If School is destroyed, Students can exist independently
```

##### B. Composition (Strong Relationship)  
Objects cannot exist independently.
```java
public class School {
    private List<Room> rooms;
}
// If School is destroyed, Rooms are also destroyed
```

### Relationship Cardinalities
- **1-to-1**: Student has one Course
- **1-to-Many**: Student has List<Course>
- **Many-to-Many**: Student has List<Course> AND Course has List<Student>

---

## Interview Questions

### Common OOP Interview Questions

#### Q1: Explain OOP to a 14-year-old child
**Answer:** Use simple real-world examples like:
- Objects = Things around us (car, dog, phone)
- Classes = Templates to create those things
- Inheritance = Child getting traits from parents
- Encapsulation = Medicine capsule protecting medicine inside
- Abstraction = Using TV remote without knowing internal circuits
- Polymorphism = Same person being student at school, child at home

#### Q2: Difference between Abstraction and Encapsulation
| **Abstraction** | **Encapsulation** |
|----------------|-------------------|
| Hides implementation complexity | Hides data from unauthorized access |
| Shows only essential features | Bundles data and methods together |
| Achieved through interfaces/abstract classes | Achieved through access modifiers |
| Focus on "what" it does | Focus on "how" to protect data |

#### Q3: Why multiple inheritance is not supported in Java?
**Answer:** Diamond Problem - ambiguity in method resolution when multiple parent classes have same method signature.

#### Q4: Difference between Method Overloading and Overriding
| **Overloading** | **Overriding** |
|----------------|----------------|
| Same class | Parent-child classes |
| Different parameters | Same parameters |
| Compile-time | Runtime |
| Return type can be different | Return type must be same |

#### Q5: What are the advantages of OOP?
- Code reusability through inheritance
- Data security through encapsulation
- Modularity and maintainability
- Real-world problem modeling
- Polymorphism for flexible code

---

## Best Practices

### Do's ✅
1. **Use meaningful class and method names**
2. **Follow single responsibility principle** - One class, one purpose
3. **Use appropriate access modifiers** - Start with most restrictive
4. **Prefer composition over inheritance** when possible
5. **Use interfaces for abstraction**
6. **Keep classes loosely coupled**
7. **Document your code** with comments

### Don'ts ❌
1. **Don't expose internal data** directly (avoid public fields)
2. **Don't create deep inheritance hierarchies** (prefer composition)
3. **Don't violate encapsulation** by providing unnecessary getters/setters
4. **Don't use inheritance just for code reuse** (use composition instead)
5. **Don't ignore access modifiers** (avoid default access unintentionally)

### Common Pitfalls
1. **Over-engineering** - Creating unnecessary abstractions
2. **Tight Coupling** - Classes too dependent on each other
3. **God Classes** - Classes that do too many things
4. **Inappropriate Inheritance** - Using inheritance when composition is better
5. **Missing Abstraction** - Exposing too much implementation detail

---

## Real-world Use Cases

### Banking System Example
```java
// Abstraction
interface BankAccount {
    void deposit(double amount);
    void withdraw(double amount);
    double getBalance();
}

// Encapsulation
public class SavingsAccount implements BankAccount {
    private double balance;        // Hidden data
    private String accountNumber;  // Hidden data
    
    // Controlled access through methods
    public void deposit(double amount) {
        if(amount > 0) {
            balance += amount;
        }
    }
}

// Inheritance
public class FixedDepositAccount extends SavingsAccount {
    private int maturityPeriod;
    
    @Override
    public void withdraw(double amount) {
        // Different withdrawal rules for FD
        if(isMatured()) {
            super.withdraw(amount);
        }
    }
}
```

### E-commerce System
```java
// Polymorphism in action
public abstract class PaymentProcessor {
    public abstract void processPayment(double amount);
}

public class CreditCardProcessor extends PaymentProcessor {
    public void processPayment(double amount) {
        // Credit card specific processing
    }
}

public class PayPalProcessor extends PaymentProcessor {
    public void processPayment(double amount) {
        // PayPal specific processing
    }
}

// Client code
PaymentProcessor processor = getPaymentProcessor(type);
processor.processPayment(amount); // Polymorphic call
```

---

## Summary

**OOP is fundamental to Java programming and provides:**
- **Better code organization** through classes and objects
- **Data security** through encapsulation
- **Code reusability** through inheritance  
- **Flexibility** through polymorphism
- **Simplified interfaces** through abstraction

**Remember:** Master these concepts with practical examples and real-world analogies for both coding proficiency and interview success.