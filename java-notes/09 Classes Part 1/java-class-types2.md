# Java Class Types - Complete Video Notes (Part 1)

> Detailed notes from the Concept and Coding video on Java Class Types - Part 1

---

## Table of Contents
1. [Introduction](#introduction)
2. [Concrete Classes](#concrete-classes)
3. [Abstract Classes](#abstract-classes)
4. [Super and Subclass with Object Class](#super-and-subclass-with-object-class)
5. [Nested Classes Deep Dive](#nested-classes-deep-dive)
6. [Interview Questions & Answers](#interview-questions--answers)
7. [Real-World Applications](#real-world-applications)
8. [Best Practices & Common Pitfalls](#best-practices--common-pitfalls)

---

## Introduction

### Types of Classes Covered in Part 1
✅ **Concrete Class**  
✅ **Abstract Class**  
✅ **Super & Subclass (with Object class)**  
✅ **Nested Classes**  
- Static Nested Class
- Non-Static Nested Class (Inner Class)
  - Member Inner Class
  - Local Inner Class
  - Anonymous Inner Class

### Types for Part 2 (Future Topics)
- Generic Classes
- POJO Classes
- Enum Classes
- Final Classes
- Singleton Classes
- Immutable Classes
- Wrapper Classes

---

## Concrete Classes

### Definition & Key Points
- **Any class which you can create an instance using `new` keyword**
- All methods in the class must have complete implementation
- Can be a child class from an interface or extend a base class
- Access modifiers: Only `public` or package-private (default)

### Why Called "Concrete"?
Unlike abstract classes or interfaces, concrete classes provide complete, solid implementations that can be instantiated.

### Examples

#### Basic Concrete Class
```java
public class Person {
    private String name;
    private int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public void display() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

// Usage - Can create instance
Person obj = new Person("John", 25);
obj.display();
```

#### Concrete Class Implementing Interface
```java
interface Shape {
    void draw();    // No implementation in interface
    double area();  // Just method signatures
}

// Concrete class implementing interface
public class Rectangle implements Shape {
    private double length, width;
    
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a rectangle");
    }
    
    @Override
    public double area() {
        return length * width;
    }
}

// Can create object of concrete class, not interface
Rectangle rect = new Rectangle(10, 5);
// Shape shape = new Shape(); // ERROR - Cannot instantiate interface
```

### Class Access Modifiers Rules
- **Top-level classes**: Only `public` or package-private (no modifier)
- **Nested classes**: Can be `private`, `protected`, `public`, or package-private

```java
// Valid top-level class declarations
public class PublicClass { }
class PackagePrivateClass { }

// Invalid top-level class declarations
// private class PrivateClass { }  // ERROR
// protected class ProtectedClass { } // ERROR
```

---

## Abstract Classes

### Understanding Abstraction
**Abstraction = Hiding implementation details and exposing only necessary features**

Real-world example: Car brake system
- User sees: "Apply Brake" button/pedal
- User doesn't see: Hydraulic system, brake pads, internal mechanics

### Two Ways to Achieve Abstraction

#### 1. Interface (100% Abstraction)
```java
interface Car {
    void applyBrake();    // No implementation
    void accelerate();    // Just method signatures
}
```

#### 2. Abstract Class (0-100% Abstraction)
```java
abstract class Car {
    // Abstract methods (no implementation)
    abstract void applyBrake();
    abstract void pressClutch();
    
    // Concrete methods (with implementation)
    public int getNumberOfWheels() {
        return 4;  // Default implementation
    }
    
    public void startEngine() {
        System.out.println("Engine started");
    }
}
```

### Abstract Class Hierarchy Example

```java
// Base abstract class
abstract class Car {
    abstract void applyBrake();
    abstract void pressClutch();
    
    // Concrete method
    public int getNumberOfWheels() {
        return 4;
    }
}

// Another abstract class extending Car
abstract class LuxuryCar extends Car {
    // Implementing one abstract method
    @Override
    public void applyBrake() {
        System.out.println("Advanced ABS brake system");
    }
    
    // Adding more abstraction
    abstract void pressDualBrakeSystem();
    // Note: pressClutch() still abstract
}

// Concrete class - must implement ALL abstract methods
class Audi extends LuxuryCar {
    @Override
    public void pressClutch() {
        System.out.println("Audi advanced clutch system");
    }
    
    @Override
    public void pressDualBrakeSystem() {
        System.out.println("Audi dual brake system activated");
    }
}
```

### Abstract Class Usage Rules

```java
// CANNOT create objects of abstract classes
// Car car = new Car();           // ERROR
// LuxuryCar luxury = new LuxuryCar(); // ERROR

// CAN create objects of concrete classes
Audi audi = new Audi();  // Valid

// CAN store reference in parent class
LuxuryCar luxuryRef = new Audi();  // Valid - Polymorphism
Car carRef = new Audi();           // Valid - Polymorphism
```

---

## Super and Subclass with Object Class

### Basic Inheritance Concepts
- **Superclass**: Parent class being extended
- **Subclass**: Child class that extends parent

```java
class A {              // Superclass
    void methodA() { }
}

class B extends A {    // Subclass
    void methodB() { }
}
```

### The Object Class - Root of All Classes

**CRITICAL CONCEPT**: Every class in Java implicitly extends `Object` class if no explicit superclass is specified.

```java
// These are equivalent:
class Person { }

class Person extends Object { }
```

### Object Class Hierarchy Visualization
```
                Object (root)
              /      |      \
           Car    Person     A
           |                 |
         Audi               B
```

### Object Class Common Methods
- `toString()` - String representation of object
- `equals(Object obj)` - Object comparison
- `hashCode()` - Hash value for collections
- `clone()` - Object cloning
- `getClass()` - Runtime class information
- `wait()`, `notify()`, `notifyAll()` - Threading methods

### Practical Object Class Usage

```java
public class ObjectTest {
    public static void main(String[] args) {
        // Can store any object in Object reference
        Object obj1 = new Person("Alice");
        Object obj2 = new Audi();
        
        // Find actual class type at runtime
        System.out.println(obj1.getClass()); // class Person
        System.out.println(obj2.getClass()); // class Audi
        
        // Object is parent of all - polymorphism storage
        Object[] objects = {
            new Person("Bob"),
            new Audi(),
            new String("Hello"),
            new ArrayList<>()
        };
        
        for (Object obj : objects) {
            System.out.println("Type: " + obj.getClass().getSimpleName());
        }
    }
}
```

---

## Nested Classes Deep Dive

### When to Use Nested Classes
1. **Single-use relationship**: Class A will ONLY be used by Class B
2. **Logical grouping**: Group related classes in one file
3. **Encapsulation**: Hide helper classes from outside world

### Types of Nested Classes

```
Nested Classes
├── Static Nested Class
└── Inner Class (Non-static)
    ├── Member Inner Class
    ├── Local Inner Class
    └── Anonymous Inner Class
```

---

## 1. Static Nested Class

### Key Characteristics
- Associated with the outer **class**, not instances
- Can only access **static** members of outer class
- Can have any access modifier (including private)
- No outer class instance needed for creation

### Complete Example

```java
class OuterClass {
    private static int staticVariable = 10;
    private int instanceVariable = 20;
    
    // Static nested class
    static class StaticNestedClass {
        void display() {
            System.out.println("Static variable: " + staticVariable);  // ✅ Valid
            // System.out.println(instanceVariable);  // ❌ ERROR - Can't access instance variable
        }
    }
    
    // Private static nested class
    private static class PrivateNestedClass {
        void show() {
            System.out.println("Private static nested class");
        }
    }
    
    // Public method to access private nested class
    public void accessPrivateNested() {
        PrivateNestedClass nested = new PrivateNestedClass();
        nested.show();
    }
}

// Usage
public class Test {
    public static void main(String[] args) {
        // Create static nested class object directly
        OuterClass.StaticNestedClass nested = new OuterClass.StaticNestedClass();
        nested.display();
        
        // Cannot access private nested class directly
        // OuterClass.PrivateNestedClass private = new OuterClass.PrivateNestedClass(); // ERROR
        
        // Access through public method
        OuterClass outer = new OuterClass();
        outer.accessPrivateNested();
    }
}
```

### Access Modifier Rules for Nested Classes
```java
class OuterClass {
    public static class PublicNested { }        // ✅ Accessible everywhere
    protected static class ProtectedNested { }  // ✅ Accessible in package + subclasses
    static class DefaultNested { }              // ✅ Package-private
    private static class PrivateNested { }      // ✅ Only within OuterClass
}
```

---

## 2. Inner Class (Non-Static Nested Class)

### Key Characteristics
- Associated with outer class **instance**
- Can access ALL members (static + instance) of outer class
- Requires outer class object for creation
- Also called "Member Inner Class"

### Complete Example

```java
class OuterClass {
    private static int staticVar = 100;
    private int instanceVar = 200;
    
    // Inner class (non-static)
    class InnerClass {
        void display() {
            System.out.println("Static variable: " + staticVar);     // ✅ Can access static
            System.out.println("Instance variable: " + instanceVar); // ✅ Can access instance
        }
    }
    
    // Private inner class
    private class PrivateInnerClass {
        void show() {
            System.out.println("Private inner class: " + instanceVar);
        }
    }
    
    public void accessPrivateInner() {
        PrivateInnerClass inner = new PrivateInnerClass();
        inner.show();
    }
}

// Usage
public class Test {
    public static void main(String[] args) {
        // MUST create outer class object first
        OuterClass outer = new OuterClass();
        
        // Create inner class object using outer object
        OuterClass.InnerClass inner = outer.new InnerClass();
        inner.display();
        
        // Alternative syntax
        OuterClass.InnerClass inner2 = new OuterClass().new InnerClass();
    }
}
```

### Memory Relationship
- Inner class objects hold implicit reference to outer class instance
- Can cause memory leaks if inner objects are stored long-term

---

## 3. Local Inner Class

### Key Characteristics
- Defined inside a method, constructor, or block
- Cannot have access modifiers (only default)
- Can access outer class members + local variables (must be effectively final)
- Scope limited to the block where it's defined

### Complete Example

```java
class OuterClass {
    private int instanceVar = 10;
    private static int staticVar = 20;
    
    public void display() {
        int localVar = 30;           // Must be effectively final
        final int finalVar = 40;
        
        // Local inner class
        class LocalInnerClass {
            void print() {
                System.out.println("Instance var: " + instanceVar);  // ✅ Outer instance
                System.out.println("Static var: " + staticVar);      // ✅ Outer static
                System.out.println("Local var: " + localVar);        // ✅ Method local
                System.out.println("Final var: " + finalVar);        // ✅ Final local
            }
        }
        
        // Can only instantiate within this method
        LocalInnerClass local = new LocalInnerClass();
        local.print();
        
        // Different blocks can have same-named local inner classes
        if (true) {
            class LocalInnerClass {  // Different class with same name
                void method() { System.out.println("Different local inner class"); }
            }
            new LocalInnerClass().method();
        }
    }
    
    public void anotherMethod() {
        // Cannot access LocalInnerClass from display() method
        // LocalInnerClass local = new LocalInnerClass(); // ERROR
    }
}
```

### Memory Management
- Local inner classes are destroyed when method execution ends
- Stored in stack memory (method-specific)

---

## 4. Anonymous Inner Class

### Key Characteristics
- Inner class **without a name**
- Used for quick implementations without creating separate files
- Compiler generates class with auto-generated name
- Commonly used for interfaces and abstract class implementations

### Complete Example

```java
// Abstract class to implement
abstract class Car {
    abstract void applyBrake();
    abstract void accelerate();
    
    // Concrete method
    void startEngine() {
        System.out.println("Engine started");
    }
}

// Interface to implement
interface Vehicle {
    void move();
    void stop();
}

public class AnonymousExample {
    public static void main(String[] args) {
        // Anonymous class implementing abstract class
        Car audiCar = new Car() {
            @Override
            void applyBrake() {
                System.out.println("Audi ABS brake system");
            }
            
            @Override
            void accelerate() {
                System.out.println("Audi turbo acceleration");
            }
            
            // Can add extra methods (but can't access them via reference)
            void audiSpecificMethod() {
                System.out.println("Audi specific feature");
            }
        }; // Note the semicolon!
        
        audiCar.applyBrake();
        audiCar.startEngine();
        
        // Anonymous class implementing interface
        Vehicle bike = new Vehicle() {
            @Override
            public void move() {
                System.out.println("Bike is moving");
            }
            
            @Override
            public void stop() {
                System.out.println("Bike stopped");
            }
        };
        
        bike.move();
        bike.stop();
    }
}
```

### What Happens Behind the Scenes

When you create an anonymous class, the compiler:

1. **Creates a subclass** with auto-generated name (e.g., `Test$1.class`)
2. **Extends/implements** the specified class/interface
3. **Implements required methods** with your provided code
4. **Creates instance** and assigns reference

```java
// Compiler generates something like:
class Test$1 extends Car {
    @Override
    void applyBrake() {
        System.out.println("Audi ABS brake system");
    }
    
    @Override
    void accelerate() {
        System.out.println("Audi turbo acceleration");
    }
}

// Then creates instance:
Car audiCar = new Test$1();
```

---

## Nested Class Inheritance

### Inheritance Between Inner Classes (Same Outer Class)

```java
class OuterClass {
    // Base inner class
    class InnerClass1 {
        protected String name = "InnerClass1";
        
        void method1() {
            System.out.println("Method from InnerClass1");
        }
    }
    
    // Derived inner class
    class InnerClass2 extends InnerClass1 {
        void method2() {
            System.out.println("Method from InnerClass2");
            System.out.println("Inherited name: " + name);  // Accessing inherited field
            method1();  // Calling inherited method
        }
    }
    
    public void testInheritance() {
        InnerClass2 inner2 = new InnerClass2();
        inner2.method1();  // Inherited method
        inner2.method2();  // Own method
    }
}
```

### Static Nested Class Inheritance (Cross-Class)

```java
class OuterClass {
    static class StaticNestedClass {
        protected void display() {
            System.out.println("Static nested class method");
        }
    }
}

// Another class extending static nested class
class SomeOtherClass extends OuterClass.StaticNestedClass {
    void method() {
        display();  // Can access parent method
        System.out.println("Method in SomeOtherClass");
    }
}

// Usage
SomeOtherClass obj = new SomeOtherClass();
obj.method();
```

### Inner Class Inheritance (Complex Scenario)

```java
class OuterClass {
    private String outerField = "Outer field";
    
    class InnerClass {
        protected void display() {
            System.out.println("Inner class accessing: " + outerField);
        }
    }
}

// Another class extending inner class
class SomeOtherClass extends OuterClass.InnerClass {
    // MUST provide outer class instance in constructor
    SomeOtherClass(OuterClass outer) {
        outer.super();  // Call inner class constructor with outer instance
    }
    
    void method() {
        display();  // Can access parent method
        System.out.println("Method in SomeOtherClass");
    }
}

// Usage - Complex but necessary
OuterClass outer = new OuterClass();
SomeOtherClass obj = new SomeOtherClass(outer);
obj.method();
```

---

## Interview Questions & Answers

### Q1: What is a concrete class and give an example?
**Answer**: A concrete class is one that can be instantiated using the `new` keyword and has all methods implemented (no abstract methods).

```java
public class Student {
    private String name;
    public void study() { System.out.println("Student is studying"); }
}
Student s = new Student(); // Valid instantiation
```

### Q2: Can abstract classes have constructors?
**Answer**: Yes, abstract classes can have constructors. They're used by subclasses during object creation.

```java
abstract class Animal {
    protected String name;
    public Animal(String name) { this.name = name; }  // Constructor
    abstract void makeSound();
}
```

### Q3: What is the parent class of all classes in Java?
**Answer**: `Object` class is the implicit parent of all classes in Java. It provides common methods like `toString()`, `equals()`, `hashCode()`, etc.

### Q4: Difference between static nested class and inner class?

| Aspect | Static Nested Class | Inner Class |
|--------|-------------------|-------------|
| Outer instance needed | No | Yes |
| Access to outer members | Static only | All (static + instance) |
| Creation syntax | `Outer.Nested obj = new Outer.Nested()` | `Outer.Inner obj = outer.new Inner()` |
| Memory overhead | Lower | Higher (holds outer reference) |

### Q5: Can nested classes be private?
**Answer**: Yes, only nested classes can be private or protected. Top-level classes can only be public or package-private.

### Q6: What happens when you create an anonymous class?
**Answer**: 
1. Compiler creates a subclass with auto-generated name (e.g., `Outer$1`)
2. Implements/extends the specified type
3. Provides method implementations
4. Creates instance and assigns reference

### Q7: Can you access local variables from local inner class?
**Answer**: Yes, but the local variables must be effectively final (not modified after initialization).

### Q8: What are the methods available in Object class?
**Answer**: 
- `toString()` - String representation
- `equals(Object)` - Object comparison  
- `hashCode()` - Hash value
- `clone()` - Object cloning
- `getClass()` - Runtime type information
- `wait()`, `notify()`, `notifyAll()` - Threading
- `finalize()` - Cleanup (deprecated)

### Q9: Can you override methods from Object class?
**Answer**: Yes, commonly overridden methods are `toString()`, `equals()`, and `hashCode()`.

### Q10: Memory leak risk with inner classes?
**Answer**: Inner classes hold implicit reference to outer class instance. If inner class objects are stored in static collections, they prevent outer class from being garbage collected.

---

## Real-World Applications

### 1. Static Nested Classes
```java
// Builder Pattern
public class Person {
    private String name;
    private int age;
    
    private Person(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }
    
    public static class Builder {
        private String name;
        private int age;
        
        public Builder setName(String name) { this.name = name; return this; }
        public Builder setAge(int age) { this.age = age; return this; }
        public Person build() { return new Person(this); }
    }
}

// Usage
Person person = new Person.Builder()
    .setName("John")
    .setAge(25)
    .build();
```

### 2. Inner Classes
```java
// Iterator Pattern in Collections
public class MyList<T> {
    private Object[] elements = new Object[10];
    private int size = 0;
    
    public Iterator<T> iterator() {
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<T> {
        private int index = 0;
        
        @Override
        public boolean hasNext() {
            return index < size;  // Accessing outer class field
        }
        
        @Override
        public T next() {
            return (T) elements[index++];  // Accessing outer class field
        }
    }
}
```

### 3. Anonymous Classes
```java
// Event Handling (pre-Java 8)
button.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Button clicked!");
    }
});

// Thread creation
Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("Background task running");
    }
});
```

---

## Best Practices & Common Pitfalls

### Best Practices ✅

1. **Use static nested classes when possible** - Lower memory overhead
```java
// Good: Static nested if no outer instance needed
class Outer {
    static class Helper { }  // ✅ Better
}
```

2. **Keep nested classes small and focused**
```java
// Good: Small, focused nested class
class GameEngine {
    static class Score {
        int points;
        String playerName;
    }
}
```

3. **Override Object methods meaningfully**
```java
@Override
public String toString() {
    return "Person{name='" + name + "', age=" + age + "}";
}

@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Person person = (Person) obj;
    return age == person.age && Objects.equals(name, person.name);
}
```

4. **Use abstract classes for shared implementation**
```java
// Good: Common behavior with customization points
abstract class DatabaseConnection {
    protected void connect() { /* common logic */ }
    abstract void authenticate();  // Customization point
    protected void disconnect() { /* common logic */ }
}
```

### Common Pitfalls ❌

1. **Trying to instantiate abstract classes**
```java
abstract class Vehicle { }
Vehicle v = new Vehicle();  // ❌ Compilation error
```

2. **Accessing instance variables from static nested class**
```java
class Outer {
    int instanceVar = 10;
    static class Nested {
        void method() {
            System.out.println(instanceVar);  // ❌ Error
        }
    }
}
```

3. **Memory leaks with inner classes**
```java
class OuterClass {
    class InnerClass { }
    
    static List<InnerClass> staticList = new ArrayList<>();
    
    void addToStaticList() {
        staticList.add(new InnerClass());  // ❌ Memory leak risk
    }
}
```

4. **Forgetting semicolon in anonymous classes**
```java
Car car = new Car() {
    @Override
    void brake() { }
}  // ❌ Missing semicolon
```

5. **Creating deep nesting hierarchies**
```java
class A {
    class B {
        class C {
            class D {  // ❌ Too deep, hard to maintain
            }
        }
    }
}
```

---

## Summary & Next Steps

### Key Takeaways from Part 1
1. **Concrete classes** are instantiable with complete implementations
2. **Abstract classes** provide 0-100% abstraction with mixed abstract/concrete methods
3. **Object class** is the root of all Java classes, providing common methods
4. **Nested classes** help organize code but should be used judiciously
5. **Static nested** vs **inner classes** - choose based on outer instance dependency

### Preparation for Part 2
The next part will cover:
- **Generic Classes** - Type safety and reusability
- **POJO Classes** - Plain Old Java Objects
- **Enum Classes** - Type-safe constants
- **Final Classes** - Immutable class hierarchies
- **Singleton Classes** - Single instance pattern
- **Immutable Classes** - Thread-safe object design
- **Wrapper Classes** - Primitive to object conversion

### Practice Exercises
1. Create an abstract `Shape` class with concrete and abstract methods
2. Implement a `Builder` pattern using static nested class
3. Write an `Iterator` using inner class
4. Convert anonymous class to lambda expression (where applicable)
5. Override `toString()`, `equals()`, and `hashCode()` in a domain class

---

*Happy learning! Master these concepts before proceeding to Part 2.*