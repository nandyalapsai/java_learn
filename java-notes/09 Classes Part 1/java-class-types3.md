# Java Class Types: Part 1 - Notes

This document provides a structured summary of different types of classes in Java, based on the provided video transcription. It's designed for quick revision and interview preparation.

---

## 1. Concrete Class

### Key Concepts & Definitions
- A concrete class is a standard class from which you can create objects (instances) using the `new` keyword.
- All methods in a concrete class must have a complete implementation. They cannot contain abstract (unimplemented) methods.

### Important Points
- Any class that is not abstract is a concrete class.
- It can be a standalone class, a subclass of another class, or an implementation of an interface.
- **Access Modifiers**: A top-level concrete class can only be `public` or default (package-private).
    - `public`: Accessible from any other class.
    - `default`: Accessible only by classes within the same package.

### Examples

**Simple Concrete Class:**
```java
// A simple concrete class
class Person {
    void speak() {
        System.out.println("Hello!");
    }
}

// You can create an instance of it
Person p = new Person();
```

**Concrete Class Implementing an Interface:**
```java
interface Shape {
    void draw(); // No implementation
}

// Rectangle is a concrete class because it implements all methods from Shape
class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a rectangle.");
    }
}

// You can create an instance of Rectangle, but not Shape
Shape myShape = new Rectangle(); // Valid
// Shape s = new Shape(); // Invalid
```

### Real-World Use Cases
- Most of the classes you write in everyday programming are concrete classes.
- Examples: `UserService`, `ProductController`, `Employee` model—any class that represents a specific entity or provides a complete service implementation.

---

## 2. Abstract Class

### Key Concepts & Definitions
- An abstract class is a class that cannot be instantiated on its own. It is declared with the `abstract` keyword.
- It serves as a template or base for other classes.
- It can contain both **abstract methods** (methods without a body) and **concrete methods** (methods with a full implementation).

### Important Points
- **Abstraction Level**: Provides 0 to 100% abstraction. You can have a mix of implemented and unimplemented methods.
- **Instantiation**: You **cannot** create an object of an abstract class (e.g., `new Car()`).
- **Inheritance**:
    - A concrete class that extends an abstract class **must** provide an implementation for all of its parent's abstract methods.
    - If a subclass does not implement all abstract methods, it must also be declared `abstract`.
- You can, however, use an abstract class as a reference type to hold an object of its concrete subclass.

### Example
```java
// Abstract base class
abstract class Car {
    // Concrete method with implementation
    public int getNumberOfWheels() {
        return 4;
    }

    // Abstract method - no implementation, must be overridden by subclasses
    public abstract void pressBrake();
}

// Concrete subclass
class Audi extends Car {
    // Providing implementation for the abstract method
    @Override
    public void pressBrake() {
        System.out.println("Audi brake system activated.");
    }
}

// Usage
Car myCar = new Audi(); // OK: Abstract class as reference
myCar.pressBrake();    // Calls the Audi implementation
// Car anotherCar = new Car(); // Error: Cannot instantiate an abstract class
```

### Common Interview Questions
- **Q: What is the difference between an abstract class and an interface?**
  - **A:** An abstract class can have both abstract and non-abstract methods, instance variables, and constructors. An interface (before Java 8) could only have abstract methods and static final constants. Abstract classes are used for "is-a" relationships with close coupling, while interfaces are for "can-do" relationships (implementing a contract).

### Best Practices
- Use an abstract class when you want to provide a common base with some default implementation that subclasses can share or override.

---

## 3. Superclass, Subclass, and the `Object` Class

### Key Concepts & Definitions
- **Superclass**: A class from which another class is derived. Also known as a parent or base class.
- **Subclass**: A class that derives from another class. Also known as a child or derived class.
- **`Object` Class**: In Java, if a class does not explicitly extend another class, it implicitly extends the `java.lang.Object` class. This makes `Object` the ultimate superclass of all classes in Java.

### Diagram: Class Hierarchy
```
      +-----------+
      |  Object   |  (Root Superclass)
      +-----------+
            ^
            | (extends)
      +-----------+
      |   Person  |  (Subclass of Object, Superclass of Student)
      +-----------+
            ^
            | (extends)
      +-----------+
      |  Student  |  (Subclass of Person)
      +-----------+
```

### Important Points
- The `Object` class provides several fundamental methods that are available to all objects, such as:
    - `toString()`: Returns a string representation of the object.
    - `equals(Object obj)`: Compares two objects for equality.
    - `hashCode()`: Returns a hash code value for the object.
    - `getClass()`: Returns the runtime class of the object.
    - `clone()`: Creates a copy of the object.
    - `wait()`, `notify()`, `notifyAll()`: Used for concurrency and thread management.

### Example: `Object` as a Universal Parent
```java
class Person { /* ... */ }
class Audi { /* ... */ }

public class ObjectTest {
    public static void main(String[] args) {
        // Any object can be stored in an Object reference
        Object obj1 = new Person();
        Object obj2 = new Audi();

        // We can find out the original class at runtime
        System.out.println("obj1 is an instance of: " + obj1.getClass().getName());
        // Output: obj1 is an instance of: Person

        System.out.println("obj2 is an instance of: " + obj2.getClass().getName());
        // Output: obj2 is an instance of: Audi
    }
}
```

### Common Interview Questions
- **Q: What is the parent class of all classes in Java?**
  - **A:** The `java.lang.Object` class.
- **Q: Why would you store an object in an `Object` reference?**
  - **A:** It's useful when writing generic code that needs to operate on objects of unknown types, such as in collections frameworks (though generics are now preferred) or reflection.

---

## 4. Nested Classes

A nested class is a class defined within another class. They are used to group logically related classes and to increase encapsulation.

### Diagram: Types of Nested Classes
```
Nested Class
├── 1. Static Nested Class
└── 2. Non-Static Nested Class (Inner Class)
    ├── a. Member Inner Class
    ├── b. Local Inner Class
    └── c. Anonymous Inner Class
```

### When to Use Nested Classes
- Use a nested class if it will **only be used by its enclosing (outer) class**. This improves encapsulation and keeps related logic together in one file.

---

### 4.1. Static Nested Class

- **Definition**: A nested class declared as `static`.
- **Behavior**: It behaves like a regular top-level class but is enclosed within the namespace of the outer class.
- **Access**: It **cannot** access instance (non-static) members of the outer class. It can only access `static` members.
- **Instantiation**: It does not need an instance of the outer class to be created.

#### Example
```java
class OuterClass {
    private static String staticOuterField = "Static Outer Data";
    private String instanceOuterField = "Instance Outer Data";

    static class StaticNestedClass {
        void display() {
            // Can access static members of OuterClass
            System.out.println(staticOuterField);
            // Cannot access instance members
            // System.out.println(instanceOuterField); // Compile Error!
        }
    }
}

// Instantiation
OuterClass.StaticNestedClass nestedObject = new OuterClass.StaticNestedClass();
nestedObject.display();
```

### 4.2. Non-Static Nested Class (Inner Class)

An inner class is associated with an **instance** of its outer class.

#### a. Member Inner Class
- **Definition**: A non-static class defined at the same level as instance variables.
- **Access**: It has access to **all** members (both `static` and `instance`) of the outer class.
- **Instantiation**: It requires an instance of the outer class to be created.

##### Example
```java
class OuterClass {
    private String instanceOuterField = "Instance Outer Data";

    class InnerClass { // This is a Member Inner Class
        void display() {
            // Can access instance members of the outer class
            System.out.println(instanceOuterField);
        }
    }
}

// Instantiation
OuterClass outerObject = new OuterClass();
OuterClass.InnerClass innerObject = outerObject.new InnerClass();
innerObject.display();
```

#### b. Local Inner Class
- **Definition**: A class defined inside a specific block, such as a method or an `if` statement.
- **Scope**: Its scope is limited to the block in which it is defined. It cannot be accessed from outside that block.
- **Access**: It can access members of the outer class and also `final` (or effectively final) local variables of the method.
- **Modifiers**: It cannot be declared `public`, `private`, or `protected`.

##### Example
```java
class OuterClass {
    void display() {
        final String methodVariable = "Local Method Data";

        // Local Inner Class defined inside the display() method
        class LocalInnerClass {
            void print() {
                System.out.println(methodVariable); // Accesses method's local variable
            }
        }

        // Can only be instantiated within this block
        LocalInnerClass lic = new LocalInnerClass();
        lic.print();
    }
}

// Usage
OuterClass outer = new OuterClass();
outer.display();
```

#### c. Anonymous Inner Class
- **Definition**: An inner class without a name. It is declared and instantiated in a single expression.
- **Use Case**: Used for one-time overrides of a method in a class or an interface, without the need to create a full, named subclass.
- **Behavior**: The compiler creates a `.class` file for it behind the scenes (e.g., `OuterClass$1.class`).

##### Example
```java
// An interface or abstract class
abstract class Greeter {
    abstract void greet();
}

class Test {
    public void sayHello() {
        // Anonymous Inner Class that extends Greeter
        Greeter englishGreeter = new Greeter() {
            @Override
            void greet() {
                System.out.println("Hello!");
            }
        }; // Semicolon is required here

        englishGreeter.greet();
    }
}
```
In this example, we are creating an unnamed subclass of `Greeter` on the fly and providing an implementation for its `greet` method.

### Common Interview Questions
- **Q: Can you declare a class as `private`?**
  - **A:** A top-level class cannot be `private`. However, a **nested class** can be declared `private`, `protected`, etc., just like any other class member (field or method).
- **Q: What is the difference between a static nested class and an inner class?**
  - **A:** A static nested class does not have access to the outer class's instance members and does not require an outer class instance to be created. An inner class is tied to an outer class instance and can access all its members.

### Best Practices & Pitfalls
- **Pitfall**: Inner classes (non-static) hold an implicit reference to their outer class instance. This can lead to **memory leaks** if the inner class object's lifecycle is much longer than the outer class object's.
- **Best Practice**: Prefer `static` nested classes over inner classes unless you specifically need access to the outer class's instance members. This avoids the potential for memory leaks.
