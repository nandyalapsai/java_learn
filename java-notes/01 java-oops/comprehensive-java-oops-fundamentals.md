# Java OOP Fundamentals - Complete Guide

## 📚 Learning Objectives

After studying these notes, you should be able to:
- ✅ Understand the fundamental concepts of Object-Oriented Programming
- ✅ Explain the four pillars of OOP with real-world examples
- ✅ Differentiate between procedural and object-oriented programming
- ✅ Create classes and objects in Java
- ✅ Implement inheritance, encapsulation, abstraction, and polymorphism
- ✅ Handle inheritance types and understand the diamond problem
- ✅ Apply "is-a" and "has-a" relationships in design
- ✅ Answer common OOP interview questions confidently

---

## 🎯 What is Object-Oriented Programming (OOP)?

### Definition
Object-Oriented Programming is a programming paradigm that **revolves around objects**. Everything is thought of from an object perspective, making it easier to model real-world scenarios in code.

### Key Principle
> "Everything revolves around objects" - when designing any project, identify all the objects present in your system.

---

## 🆚 Procedural vs Object-Oriented Programming

| **Procedural Programming** | **Object-Oriented Programming** |
|---------------------------|----------------------------------|
| Focus on **functions** | Focus on **data** |
| Data moves freely between functions | Data is **encapsulated** within classes |
| No data hiding | Provides **data hiding** |
| Tightly coupled | Loosely coupled |
| No code reusability | Code **reusability** through inheritance |
| Functions can freely modify data | Controlled data access through methods |

### Procedural Programming Issues:
- **Tight Coupling**: Functions are heavily dependent on each other
- **No Data Control**: Any function can modify any data
- **No Code Reusability**: Features like inheritance, overloading missing
- **Data Security**: No way to hide sensitive data

---

## 🏗️ Objects and Classes

### Objects
An **object** is a real-world entity that has:
1. **Properties/State** - What you can observe (color, age, height)
2. **Behavior/Functionality** - What it can do (methods/functions)

#### Examples:
```
🐕 Dog Object:
Properties: color=black, age=6, breed=labrador
Behavior: bark(), sleep(), eat()

🚗 Car Object:
Properties: brand=Toyota, type=SUV, color=red
Behavior: applyBrake(), drive(), increaseSpeed()
```

### Classes
A **class** is a **blueprint/template** from which objects are created.

#### Class Structure:
```java
class Student {
    // Data Variables (Properties)
    int age;
    String address;
    
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

#### Creating Objects:
```java
// Creating multiple objects from same class
Student engineeringStudent = new Student();
Student mbaStudent = new Student();
Student caStudent = new Student();

// Each object has its own property values
engineeringStudent.setAge(23);
mbaStudent.setAge(25);
```

---

## 🏛️ Four Pillars of OOP

## 1️⃣ Data Abstraction

### Definition
**Hiding internal implementation** and showing only essential functionality to the user.

### Real-World Examples:
- **Car Brake Pedal**: You press it → speed reduces (you don't know the internal mechanism)
- **Cell Phone**: Dial number + green button → call made (you don't know network protocols)
- **ATM Machine**: Insert card + PIN → access account (internal banking systems hidden)

### Code Example:
```java
// Interface (Abstraction)
interface Car {
    public void applyBrake();
    public void pressAccelerator();
    public void pressHorn();
}

// Implementation (Hidden from user)
class CarImplementation implements Car {
    public void applyBrake() {
        // Step 1: Reduce fuel injection
        // Step 2: Apply brake pads
        // Step 3: Reduce engine power
        // Step 4: Activate ABS system
        // Step 5: Speed reduced
        System.out.println("Speed reduced");
    }
}
```

### Advantages:
- **Security & Confidentiality**: Hide sensitive implementation details
- **Simplified Client Code**: User only needs to know input/output
- **Reduced Information Overload**: User doesn't need to understand complex internal steps

### Achievement Methods:
- **Interfaces**
- **Abstract Classes**

---

## 2️⃣ Data Encapsulation (Data Hiding)

### Definition
**Bundling data and methods** that operate on that data into a single unit (class), providing controlled access.

### Real-World Analogy:
**Medicine Capsule** - The actual medicine (data) is protected inside a capsule (class), and you can only access it through the capsule.

### Code Example:
```java
class Dog {
    // Private data member (hidden)
    private String color;
    
    // Public methods to access data (controlled access)
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
}

// Usage
Dog rottweiler = new Dog();
rottweiler.setColor("black");  // ✅ Allowed
String dogColor = rottweiler.getColor();  // ✅ Allowed
// rottweiler.color = "brown";  // ❌ Not allowed (private)
```

### Access Modifiers:
| **Modifier** | **Access Level** | **Description** |
|-------------|------------------|-----------------|
| `private` | Same class only | Most restrictive |
| `default` | Same package | Package-level access |
| `protected` | Same package + subclasses | Inheritance-friendly |
| `public` | Everywhere | Least restrictive |

### Advantages:
- **Data Security**: Control who can access/modify data
- **Loose Coupling**: Classes are independent
- **Better Control**: Full control over data through methods
- **Maintainability**: Changes to internal implementation don't affect other classes

---

## 3️⃣ Inheritance

### Definition
The capability of a class to **inherit properties and methods** from a parent class.

### Real-World Examples:
- **Child inherits from parents**: Gets parents' traits + own unique traits
- **Water forms**: Liquid, solid, gas - same substance, different forms

### Code Example:
```java
// Parent Class
class Vehicle {
    boolean hasEngine;
    
    public boolean getEngine() {
        return hasEngine;
    }
}

// Child Class
class Car extends Vehicle {  // 'extends' keyword for inheritance
    String type;  // Own property
    
    public String getCarType() {  // Own method
        return type;
    }
}

// Usage
Car swift = new Car();
swift.getEngine();    // ✅ Inherited from Vehicle
swift.getCarType();   // ✅ Own method

Vehicle myVehicle = new Vehicle();
// myVehicle.getCarType();  // ❌ Parent can't access child methods
```

### Types of Inheritance:

#### 1. Single Inheritance
```
A → B
```
```java
class A { }
class B extends A { }
```

#### 2. Multi-level Inheritance
```
A → B → C
```
```java
class A { }
class B extends A { }
class C extends B { }
```

#### 3. Hierarchical Inheritance
```
    A
   ↙ ↘
  B   C
```
```java
class A { }
class B extends A { }
class C extends A { }
```

#### 4. Multiple Inheritance (❌ Not Allowed in Java)
```
  A   B
   ↘ ↙
    C
```
```java
// ❌ This is NOT allowed
// class C extends A, B { }
```

### Diamond Problem 🔶

#### Why Multiple Inheritance is Not Allowed:
```java
class A {
    public void getEngine() { 
        return "Engine A"; 
    }
}

class B {
    public void getEngine() { 
        return "Engine B"; 
    }
}

// If this were allowed:
// class C extends A, B { }

// Problem: Which getEngine() method should C inherit?
// C obj = new C();
// obj.getEngine(); // Ambiguous! A's method or B's method?
```

#### Solution: Interfaces
```java
interface A {
    public void getEngine();  // No implementation
}

interface B {
    public void getEngine();  // No implementation
}

class C implements A, B {
    public void getEngine() {  // Must implement - no ambiguity
        System.out.println("C's implementation");
    }
}
```

### Advantages:
- **Code Reusability**: Don't rewrite parent functionality
- **Polymorphism**: Enables method overriding
- **Logical Hierarchy**: Models real-world relationships

---

## 4️⃣ Polymorphism

### Definition
**Poly** = Many, **Morphism** = Forms
> One method taking many forms - same method behaves differently in different situations

### Real-World Examples:
- **Person**: Can be father, husband, employee (same person, different roles)
- **Water**: Liquid, solid, gas (same substance, different states)

### Types of Polymorphism:

## Method Overloading (Compile-time/Static Polymorphism)

### Definition:
Same method name with **different parameters** in the **same class**.

```java
class Calculator {
    // Different number of parameters
    public int doSum(int a, int b) {
        return a + b;
    }
    
    public int doSum(int a, int b, int c) {
        return a + b + c;
    }
    
    // Different parameter types
    public String doSum(String a, String b) {
        return a + b;
    }
}

// Usage
Calculator calc = new Calculator();
calc.doSum(5, 2);          // Calls doSum(int, int)
calc.doSum(1, 2, 3);       // Calls doSum(int, int, int)
calc.doSum("Hello", "World"); // Calls doSum(String, String)
```

### Rules for Method Overloading:
✅ **Different number of parameters**
✅ **Different types of parameters**
✅ **Different order of parameters**
❌ **Only return type different** (NOT allowed)

```java
// ❌ INVALID - Only return type differs
public int doSum(int x, int y) { return x + y; }
public String doSum(int x, int y) { return String.valueOf(x + y); }
// Compiler confusion: which method to call for doSum(5, 2)?
```

## Method Overriding (Runtime/Dynamic Polymorphism)

### Definition:
Child class provides **specific implementation** of parent class method with **same signature**.

```java
// Parent Class
class Vehicle {
    public void getEngine() {
        System.out.println("Vehicle engine");
    }
}

// Child Class
class Car extends Vehicle {
    @Override
    public void getEngine() {  // Same signature as parent
        System.out.println("Car engine with 4 cylinders");
    }
}

// Usage
Vehicle vehicle = new Vehicle();
vehicle.getEngine();  // Output: "Vehicle engine"

Car car = new Car();
car.getEngine();      // Output: "Car engine with 4 cylinders"
```

### Rules for Method Overriding:
✅ **Same method name**
✅ **Same return type**
✅ **Same parameters** (type, number, order)
✅ **Must be in parent-child relationship**
❌ **Different signature** (would be overloading, not overriding)

### Method Resolution Order:
1. Check if method exists in **current class**
2. If not found, check **parent class**
3. Continue up the inheritance chain

---

## 🔗 Relationships in OOP

## Is-A Relationship (Inheritance)

### Definition:
Represents **inheritance** - child "is a" type of parent.

### Examples:
```java
class Animal { }
class Dog extends Animal { }
// Dog IS-A Animal ✅

class Vehicle { }
class Car extends Vehicle { }
// Car IS-A Vehicle ✅
```

### Test: If you can say "X is a Y", then inheritance is appropriate.

## Has-A Relationship (Composition/Aggregation)

### Definition:
One class **contains an object** of another class as a member variable.

### Types:

#### 1. Aggregation (Weak Relationship)
Objects can exist **independently**. Destroying one doesn't destroy the other.

```java
class School {
    List<Student> students;  // School HAS students
}

class Student {
    String name;
    int age;
}

// If School is destroyed → Students can join other schools
// If Students leave → School still exists (can admit new students)
```

#### 2. Composition (Strong Relationship)
Objects are **dependent**. Destroying parent destroys children.

```java
class School {
    List<Room> rooms;  // School HAS rooms
}

class Room {
    int number;
    int capacity;
}

// If School is destroyed → Rooms are also destroyed
// Rooms cannot exist without School
```

### Cardinality Types:
- **One-to-One**: Student has one ID card
- **One-to-Many**: School has many students
- **Many-to-Many**: Students can take many courses, courses have many students

```java
// One-to-Many example
class Student {
    List<Course> courses;  // One student, many courses
}

// Many-to-Many example
class Course {
    List<Student> enrolledStudents;  // One course, many students
}
```

---

## 🎯 Common Interview Questions & Answers

### Q1: Explain OOP to a 14-year-old child
**Answer**: Imagine building with LEGO blocks. Each block (object) has properties (color, size) and what it can do (connect to other blocks). You can create blueprints (classes) to make similar blocks. You can build bigger structures (inheritance) by combining blocks, hide complex internal mechanisms (abstraction), and use the same block type for different purposes (polymorphism).

### Q2: What are the four pillars of OOP?
**Answer**:
1. **Abstraction** - Hide complexity, show only necessary details
2. **Encapsulation** - Bundle data and methods, control access
3. **Inheritance** - Reuse parent class properties in child classes
4. **Polymorphism** - One interface, multiple implementations

### Q3: Difference between method overloading and overriding?
**Answer**:
| **Overloading** | **Overriding** |
|----------------|----------------|
| Same class | Parent-child classes |
| Different parameters | Same parameters |
| Compile-time binding | Runtime binding |
| Static polymorphism | Dynamic polymorphism |

### Q4: Why is multiple inheritance not allowed in Java?
**Answer**: **Diamond Problem** - ambiguity when two parent classes have methods with the same signature. Java can't decide which method to inherit. Solution: Use interfaces which force implementation in child class.

### Q5: What is the diamond problem?
**Answer**: When a class inherits from two classes that have the same method signature, creating ambiguity about which method to call. Java prevents this by not allowing multiple inheritance with classes, but allows it with interfaces.

### Q6: Difference between is-a and has-a relationships?
**Answer**:
- **Is-a**: Inheritance relationship (Car is-a Vehicle)
- **Has-a**: Composition relationship (Car has-a Engine)

---

## 💡 Memory Hooks & Mnemonics

### Four Pillars: **A-E-I-P**
- **A**bstraction - **A**TM hides complexity
- **E**ncapsulation - **E**ncapsulate like medicine capsule
- **I**nheritance - **I**nherit from parents
- **P**olymorphism - **P**erson plays many roles

### Access Modifiers: **Private → Protected → Public**
- **Private**: **P**ersonal diary (only you)
- **Protected**: **P**arents and kids can read
- **Public**: **P**ublished book (everyone can read)

### Overloading vs Overriding:
- **Over-loading**: **Loading** different parameters
- **Over-riding**: **Riding** over parent's method

---

## 🛠️ Hands-On Exercises

### Exercise 1: Create a Class Hierarchy
```java
// Create Vehicle → Car → SportsCar hierarchy
// Add appropriate properties and methods
// Demonstrate inheritance and polymorphism
```

### Exercise 2: Banking System
```java
// Design Account → SavingsAccount, CheckingAccount
// Implement encapsulation with proper access modifiers
// Add deposit(), withdraw(), getBalance() methods
```

### Exercise 3: Shape Calculator
```java
// Create Shape interface with area() method
// Implement Circle, Rectangle, Triangle classes
// Demonstrate abstraction and polymorphism
```

### Exercise 4: School Management
```java
// Design School, Student, Course classes
// Implement has-a relationships (aggregation/composition)
// Add enrollment functionality
```

---

## 🌟 Real-World Use Cases

### 1. E-Commerce System
```java
class Product { }
class Electronics extends Product { }  // Is-a relationship
class Laptop extends Electronics { }   // Multi-level inheritance

class ShoppingCart {
    List<Product> items;  // Has-a relationship (aggregation)
}
```

### 2. Game Development
```java
abstract class Character {  // Abstraction
    protected int health;   // Encapsulation
    public abstract void attack();  // Polymorphism
}

class Warrior extends Character { }  // Inheritance
class Mage extends Character { }     // Inheritance
```

### 3. GUI Framework
```java
class Component { }
class Button extends Component { }   // Inheritance
class Panel extends Component {      
    List<Component> children;        // Composition
}
```

---

## ⚠️ Common Pitfalls & Best Practices

### ❌ Common Mistakes:

1. **Overusing Inheritance**
   ```java
   // ❌ Bad: Creating inheritance for code reuse only
   class Utils extends DatabaseConnection { }
   
   // ✅ Good: Use composition instead
   class Utils {
       private DatabaseConnection db;
   }
   ```

2. **Breaking Encapsulation**
   ```java
   // ❌ Bad: Public fields
   class Student {
       public String name;  // Anyone can modify
   }
   
   // ✅ Good: Private fields with accessors
   class Student {
       private String name;
       public String getName() { return name; }
       public void setName(String name) { this.name = name; }
   }
   ```

3. **Violating Liskov Substitution Principle**
   ```java
   // ❌ Bad: Child changes parent behavior drastically
   class Bird {
       public void fly() { /* flying logic */ }
   }
   
   class Penguin extends Bird {
       public void fly() { 
           throw new UnsupportedOperationException(); // Breaks LSP
       }
   }
   ```

### ✅ Best Practices:

1. **Favor Composition over Inheritance**
2. **Keep classes focused** (Single Responsibility)
3. **Use interfaces** for contracts
4. **Encapsulate data** properly
5. **Follow naming conventions**
6. **Design for extension** but **code for maintenance**

---

## 🔧 Debugging Tips

### 1. NullPointerException in Has-a Relationships
```java
class School {
    private List<Student> students;
    
    // ❌ Potential NPE
    public void addStudent(Student s) {
        students.add(s);  // students might be null
    }
    
    // ✅ Safe approach
    public void addStudent(Student s) {
        if (students == null) {
            students = new ArrayList<>();
        }
        students.add(s);
    }
}
```

### 2. Method Resolution Issues
```java
// Use @Override annotation to catch errors
class Child extends Parent {
    @Override  // Compiler will catch signature mismatches
    public void someMethod() {
        // implementation
    }
}
```

### 3. Access Modifier Issues
```java
// Use IDE warnings and compile-time checks
// Always test access from different packages
```

---

## 📋 Quick Revision Checklist

### Concepts to Remember:
- [ ] Object = Properties + Behavior
- [ ] Class = Blueprint for objects
- [ ] Four Pillars: Abstraction, Encapsulation, Inheritance, Polymorphism
- [ ] Access Modifiers: private, default, protected, public
- [ ] Inheritance Types: Single, Multi-level, Hierarchical
- [ ] Multiple inheritance not allowed (Diamond Problem)
- [ ] Overloading vs Overriding differences
- [ ] Is-a vs Has-a relationships
- [ ] Aggregation vs Composition

### Interview Preparation:
- [ ] Can explain each pillar with real-world examples
- [ ] Can write code examples for each concept
- [ ] Understand why certain restrictions exist (like multiple inheritance)
- [ ] Can design class hierarchies for given scenarios
- [ ] Know common pitfalls and best practices

---

## 🎯 Summary

Java OOP provides a powerful paradigm for modeling real-world problems through:

1. **Objects and Classes**: Basic building blocks
2. **Four Pillars**: Core principles that enable robust software design
3. **Relationships**: How objects interact and depend on each other
4. **Polymorphism**: Flexibility in method behavior
5. **Inheritance**: Code reusability and logical hierarchies

Master these concepts through practice, and you'll have a solid foundation for Java development and technical interviews.

---

*📚 Continue learning: Next topics → Advanced OOP (Abstract Classes, Interfaces, Inner Classes)*