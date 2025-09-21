# Java Class Types - Comprehensive Notes

## Learning Objectives
After reading these notes, you should be able to:
- ✅ Identify and differentiate between all types of Java classes
- ✅ Create and use concrete, abstract, and nested classes
- ✅ Understand inheritance hierarchies with super and subclasses
- ✅ Implement static nested classes and inner classes
- ✅ Work with local and anonymous inner classes
- ✅ Apply appropriate access modifiers to different class types
- ✅ Answer common interview questions about class types
- ✅ Choose the right class type for specific scenarios

## Table of Contents
1. [Overview of Java Class Types](#overview)
2. [Concrete Classes](#concrete-classes)
3. [Abstract Classes](#abstract-classes)
4. [Super and Subclasses](#super-and-subclasses)
5. [Nested Classes](#nested-classes)
6. [Interview Questions](#interview-questions)
7. [Best Practices](#best-practices)
8. [Quick Reference Cheat Sheet](#cheat-sheet)

---

## Overview of Java Class Types {#overview}

Java supports multiple types of classes to provide flexibility and organization in object-oriented programming:

```
Java Class Types
├── Concrete Classes
├── Abstract Classes
├── Super/Subclasses
├── Nested Classes
│   ├── Static Nested Classes
│   └── Inner Classes (Non-static)
│       ├── Member Inner Classes
│       ├── Local Inner Classes
│       └── Anonymous Inner Classes
├── Generic Classes
├── POJOs (Plain Old Java Objects)
├── Enums
├── Final Classes
├── Singleton Classes
├── Immutable Classes
└── Wrapper Classes
```

> **Note**: This document covers the first 4 types. Generic classes through Wrapper classes will be covered in Part 2.

---

## Concrete Classes {#concrete-classes}

### Definition
A **concrete class** is any class from which you can create an instance using the `new` keyword.

### Key Characteristics
- ✅ All methods have complete implementations
- ✅ Can be instantiated with `new` keyword
- ✅ May extend base classes or implement interfaces
- ✅ Must provide implementations for all abstract methods from parent classes/interfaces

### Code Example
```java
// Concrete class
public class Person {
    private String name;
    private int age;
    
    // Constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // All methods have implementations
    public void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
    
    public String getName() {
        return name;
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        Person person = new Person("John", 25); // ✅ Can create instance
        person.displayInfo();
    }
}
```

### Interface Implementation Example
```java
// Interface
interface Shape {
    void draw();
    double calculateArea();
}

// Concrete class implementing interface
public class Rectangle implements Shape {
    private double length, width;
    
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    
    // Must implement all interface methods
    @Override
    public void draw() {
        System.out.println("Drawing a rectangle");
    }
    
    @Override
    public double calculateArea() {
        return length * width;
    }
}
```

### Access Modifiers for Classes
- `public`: Accessible from any package
- **Default** (no modifier): Package-private, accessible within same package only

```java
public class PublicClass {
    // Accessible from anywhere
}

class DefaultClass {
    // Accessible within same package only
}
```

---

## Abstract Classes {#abstract-classes}

### Definition
An **abstract class** is used to achieve abstraction - hiding implementation details while exposing only essential features to the client.

### Key Characteristics
- ❌ **Cannot be instantiated** with `new` keyword
- ✅ Can contain both abstract and concrete methods
- ✅ Can have instance variables, constructors, and static methods
- ✅ Provides 0-100% abstraction (vs Interface which provides 100% abstraction)
- ✅ Child classes must implement all abstract methods

### Abstraction Comparison
| Feature | Abstract Class | Interface |
|---------|---------------|-----------|
| Abstraction Level | 0-100% | 100% |
| Method Implementation | Can have both abstract and concrete methods | Only abstract methods (default methods in Java 8+) |
| Variables | Can have instance variables | Only static final variables |
| Inheritance | Single inheritance (`extends`) | Multiple inheritance (`implements`) |

### Code Example
```java
// Abstract class
public abstract class Car {
    // Instance variables allowed
    protected String brand;
    protected int wheels = 4;
    
    // Constructor allowed
    public Car(String brand) {
        this.brand = brand;
    }
    
    // Abstract methods (no implementation)
    public abstract void pressBrake();
    public abstract void pressClutch();
    
    // Concrete methods (with implementation)
    public int getNumberOfWheels() {
        return wheels;
    }
    
    public void startEngine() {
        System.out.println("Engine started for " + brand);
    }
}

// Abstract class extending another abstract class
public abstract class LuxuryCar extends Car {
    public LuxuryCar(String brand) {
        super(brand);
    }
    
    // Can provide implementation of parent's abstract method
    @Override
    public void pressBrake() {
        System.out.println("Advanced braking system activated");
    }
    
    // Can add more abstract methods
    public abstract void pressDualBrakeSystem();
}

// Concrete class - must implement all abstract methods
public class Audi extends LuxuryCar {
    public Audi() {
        super("Audi");
    }
    
    @Override
    public void pressClutch() {
        System.out.println("Audi clutch pressed");
    }
    
    @Override
    public void pressDualBrakeSystem() {
        System.out.println("Audi dual brake system activated");
    }
}
```

### Usage Example
```java
public class Main {
    public static void main(String[] args) {
        // Car car = new Car("BMW"); // ❌ Error: Cannot instantiate abstract class
        // LuxuryCar luxury = new LuxuryCar("Mercedes"); // ❌ Error: Cannot instantiate abstract class
        
        Audi audi = new Audi(); // ✅ Concrete class can be instantiated
        
        // Polymorphism: Can store concrete object in abstract reference
        LuxuryCar luxuryRef = new Audi(); // ✅ Valid
        Car carRef = new Audi(); // ✅ Valid
        
        audi.startEngine();
        audi.pressBrake();
        audi.pressClutch();
        audi.pressDualBrakeSystem();
    }
}
```

---

## Super and Subclasses {#super-and-subclasses}

### Definition
- **Superclass**: Parent class from which other classes inherit
- **Subclass**: Child class that inherits from a parent class
- **Object Class**: Ultimate superclass of all Java classes

### The Object Class Hierarchy
```
Object (Ultimate superclass)
├── Person
├── Car
│   └── LuxuryCar
│       └── Audi
└── Any other class
```

### Key Concepts
- Every class in Java implicitly extends `Object` class if no explicit superclass is specified
- `Object` class provides common methods: `toString()`, `equals()`, `clone()`, `notify()`, `wait()`, etc.
- Child objects can be stored in parent class references (polymorphism)

### Code Example
```java
public class Person {
    protected String name;
    
    public Person(String name) {
        this.name = name;
    }
    
    public void displayInfo() {
        System.out.println("Person: " + name);
    }
}

public class Student extends Person {
    private String studentId;
    
    public Student(String name, String studentId) {
        super(name); // Call parent constructor
        this.studentId = studentId;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Student: " + name + ", ID: " + studentId);
    }
}
```

### Object Class Usage Example
```java
public class ObjectExample {
    public static void main(String[] args) {
        // All objects can be stored in Object reference
        Object obj1 = new Person("John");
        Object obj2 = new Student("Alice", "S123");
        
        // Get actual class type at runtime
        System.out.println(obj1.getClass().getSimpleName()); // Output: Person
        System.out.println(obj2.getClass().getSimpleName()); // Output: Student
        
        // Use Object class methods
        System.out.println(obj1.toString());
        System.out.println(obj2.equals(obj1));
    }
}
```

---

## Nested Classes {#nested-classes}

### Overview
A **nested class** is a class defined within another class. It helps organize logically related classes and provides better encapsulation.

```
Nested Classes
├── Static Nested Classes
└── Inner Classes (Non-static)
    ├── Member Inner Classes
    ├── Local Inner Classes
    └── Anonymous Inner Classes
```

### When to Use Nested Classes
- When a class will be used by only one other class
- To group logically related classes in one file
- To achieve better encapsulation and organization

---

### Static Nested Classes

#### Characteristics
- Declared with `static` keyword
- Can only access static members of outer class
- Don't need outer class instance for instantiation
- Can have any access modifier (private, protected, public, default)

#### Code Example
```java
public class OuterClass {
    private static String staticVar = "Static Variable";
    private String instanceVar = "Instance Variable";
    
    // Static nested class
    public static class StaticNestedClass {
        public void display() {
            System.out.println("Can access: " + staticVar); // ✅ Can access static
            // System.out.println(instanceVar); // ❌ Cannot access instance variable
        }
    }
    
    // Private static nested class
    private static class PrivateStaticNestedClass {
        public void show() {
            System.out.println("Private static nested class");
        }
    }
    
    public void createPrivateNestedObject() {
        PrivateStaticNestedClass obj = new PrivateStaticNestedClass();
        obj.show();
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        // Creating static nested class object
        OuterClass.StaticNestedClass nested = new OuterClass.StaticNestedClass();
        nested.display();
        
        // Cannot create private nested class object directly
        // OuterClass.PrivateStaticNestedClass obj = new OuterClass.PrivateStaticNestedClass(); // ❌ Error
        
        // Access through outer class method
        OuterClass outer = new OuterClass();
        outer.createPrivateNestedObject();
    }
}
```

---

### Inner Classes (Non-static Nested Classes)

#### Member Inner Classes

##### Characteristics
- No `static` keyword
- Can access all members (static and instance) of outer class
- Need outer class instance for instantiation
- Can have any access modifier

##### Code Example
```java
public class OuterClass {
    private static String staticVar = "Static Variable";
    private String instanceVar = "Instance Variable";
    
    // Member inner class
    public class InnerClass {
        public void display() {
            System.out.println("Can access static: " + staticVar); // ✅
            System.out.println("Can access instance: " + instanceVar); // ✅
        }
    }
    
    // Private inner class
    private class PrivateInnerClass {
        public void show() {
            System.out.println("Private inner class accessing: " + instanceVar);
        }
    }
    
    public void createPrivateInnerObject() {
        PrivateInnerClass obj = new PrivateInnerClass();
        obj.show();
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        // Must create outer class object first
        OuterClass outer = new OuterClass();
        
        // Create inner class object using outer object
        OuterClass.InnerClass inner = outer.new InnerClass();
        inner.display();
        
        // Alternative syntax
        OuterClass.InnerClass inner2 = new OuterClass().new InnerClass();
        inner2.display();
    }
}
```

#### Local Inner Classes

##### Characteristics
- Defined inside a method, constructor, or block
- Can only be accessed within that block
- Cannot have access modifiers (automatically package-private)
- Can access outer class members and method's local variables (must be final or effectively final)

##### Code Example
```java
public class OuterClass {
    private String outerField = "Outer Field";
    
    public void methodWithLocalInnerClass() {
        final String localVar = "Local Variable"; // Must be final or effectively final
        String effectivelyFinal = "Effectively Final"; // Not modified after initialization
        
        // Local inner class
        class LocalInnerClass {
            public void display() {
                System.out.println("Outer field: " + outerField);
                System.out.println("Local variable: " + localVar);
                System.out.println("Effectively final: " + effectivelyFinal);
            }
        }
        
        // Can only be instantiated within this method
        LocalInnerClass local = new LocalInnerClass();
        local.display();
    } // Local inner class scope ends here
}
```

#### Anonymous Inner Classes

##### Characteristics
- Inner class without a name
- Used to override method behavior without creating a separate subclass
- Created at the point of instantiation
- Compiler generates a class file with name like `OuterClass$1.class`

##### Code Example
```java
// Abstract class for demonstration
abstract class Car {
    public abstract void pressBrake();
    
    public void startEngine() {
        System.out.println("Engine started");
    }
}

public class AnonymousExample {
    public static void main(String[] args) {
        // Anonymous inner class
        Car audiCar = new Car() {
            @Override
            public void pressBrake() {
                System.out.println("Audi brake system activated");
            }
            
            // Can add additional methods (but can't be called through reference)
            public void audiSpecificMethod() {
                System.out.println("Audi specific feature");
            }
        }; // Note the semicolon!
        
        // Use the anonymous class
        audiCar.startEngine();
        audiCar.pressBrake();
        // audiCar.audiSpecificMethod(); // ❌ Cannot access additional methods
        
        // What happens behind the scenes:
        // 1. Compiler creates a class like: AnonymousExample$1 extends Car
        // 2. Implements the pressBrake() method
        // 3. Creates an instance of this generated class
        // 4. Returns reference to the Car type
    }
}
```

##### Interface Implementation with Anonymous Classes
```java
interface Runnable {
    void run();
}

public class AnonymousInterfaceExample {
    public static void main(String[] args) {
        // Anonymous class implementing interface
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("Task is running...");
            }
        };
        
        task.run();
        
        // Modern alternative: Lambda expression (Java 8+)
        Runnable lambdaTask = () -> System.out.println("Lambda task running...");
        lambdaTask.run();
    }
}
```

---

### Inheritance in Nested Classes

#### Static Nested Class Inheritance
```java
// Outer class with static nested class
class OuterClass {
    static class StaticNested {
        public void display() {
            System.out.println("Static nested method");
        }
    }
}

// Another class extending static nested class
class SomeOtherClass extends OuterClass.StaticNested {
    @Override
    public void display() {
        super.display();
        System.out.println("Extended static nested method");
    }
}
```

#### Inner Class Inheritance
```java
class OuterClass {
    class InnerClass {
        public void display() {
            System.out.println("Inner class method");
        }
    }
}

// Extending inner class requires special constructor
class SomeOtherClass extends OuterClass.InnerClass {
    // Must provide outer class instance to super constructor
    public SomeOtherClass(OuterClass outer) {
        outer.super(); // Call inner class constructor
    }
    
    @Override
    public void display() {
        super.display();
        System.out.println("Extended inner class method");
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        SomeOtherClass extended = new SomeOtherClass(outer);
        extended.display();
    }
}
```

---

## Interview Questions {#interview-questions}

### 1. **Q: What is the difference between abstract class and interface?**
**A:** 
- **Abstract Class**: 0-100% abstraction, can have instance variables, constructors, concrete methods, single inheritance
- **Interface**: 100% abstraction, only static final variables, no constructors, multiple inheritance

### 2. **Q: Can you create an object of an abstract class?**
**A:** No, you cannot instantiate an abstract class using `new` keyword. However, you can store a concrete subclass object in an abstract class reference.

### 3. **Q: What is the parent class of all Java classes?**
**A:** `Object` class is the ultimate superclass of all Java classes in Java. Every class implicitly extends Object if no explicit superclass is specified.

### 4. **Q: What access modifiers can be applied to classes?**
**A:** 
- **Top-level classes**: `public` or default (package-private)
- **Nested classes**: `public`, `protected`, `private`, or default

### 5. **Q: What's the difference between static nested class and inner class?**
**A:**
- **Static Nested**: Can only access static members of outer class, instantiated without outer class object
- **Inner Class**: Can access all members of outer class, requires outer class object for instantiation

### 6. **Q: Can you inherit from multiple classes in Java?**
**A:** No, Java doesn't support multiple inheritance for classes (to avoid diamond problem). However, you can implement multiple interfaces.

### 7. **Q: What happens behind the scenes with anonymous classes?**
**A:** Compiler creates a class file with generated name (like `OuterClass$1.class`), extends/implements the specified class/interface, and provides implementations for abstract methods.

### 8. **Q: Can local inner classes access method variables?**
**A:** Yes, but only if the variables are final or effectively final (not modified after initialization).

---

## Best Practices {#best-practices}

### ✅ Do's
1. **Use abstract classes** when you have common code to share among related classes
2. **Use interfaces** when you need to specify behavior that multiple unrelated classes should implement
3. **Use nested classes** when a class is only used by one other class
4. **Make nested classes static** when they don't need access to outer class instance members
5. **Keep anonymous classes small** - if they become complex, create a separate class
6. **Use meaningful names** for concrete classes that describe their purpose

### ❌ Don'ts
1. **Don't overuse nested classes** - they can make code harder to read and maintain
2. **Don't make everything abstract** - use concrete classes when you have complete implementations
3. **Don't forget the semicolon** after anonymous class definitions
4. **Don't access outer class members unnecessarily** in static nested classes
5. **Don't create deep nesting** - limit to 2-3 levels maximum

### Common Pitfalls
1. **Forgetting to implement abstract methods** in concrete subclasses
2. **Trying to instantiate abstract classes** directly
3. **Incorrect syntax for creating inner class objects** (forgetting outer class instance)
4. **Memory leaks with inner classes** holding references to outer class
5. **Accessibility issues** with private nested classes

---

## Hands-on Exercises {#exercises}

### Exercise 1: Abstract Class Implementation
Create an abstract class `Vehicle` with:
- Abstract methods: `start()`, `stop()`
- Concrete method: `getVehicleInfo()`
- Create concrete classes `Car` and `Motorcycle` that extend `Vehicle`

### Exercise 2: Nested Classes Practice
Create an outer class `University` with:
- Static nested class `Department`
- Member inner class `Student`
- Local inner class inside a method `createCourse()`
- Anonymous class implementing a `Gradable` interface

### Exercise 3: Object Class Methods
Create a `Person` class and override:
- `toString()` method
- `equals()` method
- Demonstrate polymorphism using Object references

---

## Memory Hooks & Mnemonics {#memory-hooks}

### 🧠 Memory Aids
1. **"Abstract = Can't Touch"** - Abstract classes cannot be instantiated
2. **"Static = No Instance"** - Static nested classes don't need outer instance
3. **"Inner = Needs Outer"** - Inner classes need outer class object
4. **"Object = Universal Parent"** - Object class is parent of all
5. **"Anonymous = No Name"** - Anonymous classes have no explicit name

### 🎯 Quick Decision Tree
```
Need to create objects? 
├── Yes → Concrete Class
└── No → Abstract Class

Need access to outer instance variables?
├── Yes → Inner Class
└── No → Static Nested Class

Need to override behavior once?
├── Yes → Anonymous Class
└── No → Regular Class
```

---

## Real-world Use Cases {#use-cases}

### 1. **Abstract Classes**
- **Framework design**: Spring Framework uses abstract classes for template patterns
- **GUI frameworks**: Abstract `Component` class in Java Swing
- **Database connections**: Abstract `Connection` class in JDBC

### 2. **Static Nested Classes**
- **Builder pattern**: `StringBuilder.Builder` class
- **Map entries**: `Map.Entry` interface implementations
- **Utility classes**: Helper classes that don't need outer instance

### 3. **Inner Classes**
- **Event handling**: GUI event listeners
- **Iterator implementations**: Custom iterators for collections
- **Adapter patterns**: Adapting interfaces without creating separate files

### 4. **Anonymous Classes**
- **Event listeners**: Button click handlers in GUI applications
- **Comparators**: Custom sorting logic for collections
- **Thread implementations**: Quick `Runnable` implementations

---
