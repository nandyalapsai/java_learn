# Java Class Types - Quick Reference Cheat Sheet

## 🎯 Class Types Overview

| Class Type | Can Instantiate? | Abstract Methods? | Access to Outer Class | Key Use Case |
|------------|------------------|-------------------|---------------------|--------------|
| **Concrete** | ✅ Yes | ❌ No | N/A | Regular objects |
| **Abstract** | ❌ No | ✅ Yes | N/A | Template/Framework |
| **Static Nested** | ✅ Yes | ✅ Can have | Only static members | Utility classes |
| **Inner (Member)** | ✅ Yes (with outer) | ✅ Can have | All members | Closely related functionality |
| **Local Inner** | ✅ Yes (in block) | ✅ Can have | All members + local vars | Method-specific logic |
| **Anonymous** | ✅ Yes (implicit) | Must implement | All members | One-time implementations |

---

## 🔥 Syntax Quick Reference

### Class Declarations
```java
// Concrete class
public class ConcreteClass { }

// Abstract class
public abstract class AbstractClass {
    public abstract void abstractMethod();
    public void concreteMethod() { /* implementation */ }
}

// Static nested class
public class Outer {
    public static class StaticNested { }
}

// Inner class
public class Outer {
    public class Inner { }
}

// Anonymous class
Interface obj = new Interface() {
    @Override
    public void method() { /* implementation */ }
}; // Don't forget semicolon!
```

### Object Creation
```java
// Concrete class
ConcreteClass obj = new ConcreteClass();

// Static nested class
Outer.StaticNested nested = new Outer.StaticNested();

// Inner class
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();

// Abstract class (ILLEGAL)
// AbstractClass obj = new AbstractClass(); // ❌ Compilation error
```

---

## ⚡ Key Rules to Remember

### Abstract Classes
- ❌ Cannot instantiate directly
- ✅ Can have constructors
- ✅ Can have instance variables
- ✅ Can have both abstract and concrete methods
- ✅ Single inheritance only

### Nested Classes Access Rules
```java
public class Outer {
    private static String staticVar = "Static";
    private String instanceVar = "Instance";
    
    static class StaticNested {
        void method() {
            System.out.println(staticVar);      // ✅ OK
            // System.out.println(instanceVar); // ❌ Error
        }
    }
    
    class Inner {
        void method() {
            System.out.println(staticVar);    // ✅ OK
            System.out.println(instanceVar);  // ✅ OK
        }
    }
}
```

### Local Inner Class Rules
- ✅ Can access outer class members
- ✅ Can access method parameters and local variables (if final/effectively final)
- ❌ Cannot have access modifiers
- ❌ Cannot be static

---

## 🎯 Decision Flowchart

```
Need to create objects?
├── Yes ────────────────┐
│                       │
├── No (Abstract) ──────┤
│                       │
└── Template needed? ───┘
    ├── Yes → Abstract Class
    └── No → Interface

Nested class needed?
├── Need outer instance data?
│   ├── Yes → Inner Class
│   └── No → Static Nested Class
├── Used only in one method?
│   └── Yes → Local Inner Class
└── One-time implementation?
    └── Yes → Anonymous Class
```

---

## 🧠 Memory Tricks

### ASONIC Rule for Class Types
- **A**bstract: Can't instantiate
- **S**tatic Nested: No outer instance needed
- **O**bject: Parent of all classes
- **N**ested: Class within class
- **I**nner: Needs outer instance
- **C**oncrete: Can create objects

### Access Level Pyramid
```
        Object (Ultimate Parent)
           /        \
    YourClass1    YourClass2
        |             |
   Subclass1     Subclass2
```

### Static vs Instance Memory Hook
- **Static Nested**: "**S**tatic = **S**eparate from instance"
- **Inner Class**: "**I**nner = **I**nstance dependent"

---

## 🔥 Common Interview Traps

### Trap 1: Abstract Class Instantiation
```java
// ❌ WRONG
AbstractClass obj = new AbstractClass(); // Compilation error

// ✅ CORRECT
ConcreteSubclass obj = new ConcreteSubclass();
AbstractClass ref = obj; // Polymorphism
```

### Trap 2: Nested Class Creation
```java
// ❌ WRONG
Inner inner = new Inner(); // Error: needs outer instance

// ✅ CORRECT
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
```

### Trap 3: Static Nested Class Access
```java
public class Outer {
    private String instanceVar = "test";
    
    static class StaticNested {
        void method() {
            // ❌ WRONG
            // System.out.println(instanceVar); // Compilation error
            
            // ✅ CORRECT
            Outer outer = new Outer();
            System.out.println(outer.instanceVar);
        }
    }
}
```

---

## 📝 Quick Practice Problems

### Problem 1: Identify Class Types
```java
// What type of class is this?
public abstract class Vehicle {
    protected String brand;
    public abstract void start();
    public void stop() { System.out.println("Stopped"); }
}
```
**Answer**: Abstract class (has `abstract` keyword and abstract method)

### Problem 2: Fix the Code
```java
// What's wrong with this code?
public class Outer {
    private String name = "Outer";
    
    static class Nested {
        void display() {
            System.out.println(name); // Error here
        }
    }
}
```
**Answer**: Static nested class cannot access instance variable directly. Need to create Outer instance.

### Problem 3: Complete the Implementation
```java
interface Drawable {
    void draw();
}

public class Test {
    public static void main(String[] args) {
        // Create anonymous class implementing Drawable
        Drawable circle = ____________________;
        circle.draw();
    }
}
```
**Answer**: 
```java
Drawable circle = new Drawable() {
    @Override
    public void draw() {
        System.out.println("Drawing circle");
    }
};
```

---

## 🎯 Final Tips for Success

### Before Coding
1. **Identify the relationship**: Is-A vs Has-A
2. **Check instantiation needs**: Can objects be created?
3. **Determine scope**: Where will the class be used?

### During Implementation
1. **Abstract classes**: Don't forget to implement abstract methods in subclasses
2. **Nested classes**: Choose static vs non-static based on outer class dependency
3. **Anonymous classes**: Keep them simple and focused

### For Interviews
1. **Know the Object class**: Methods like `toString()`, `equals()`, `hashCode()`
2. **Understand polymorphism**: Parent references to child objects
3. **Practice syntax**: Especially nested class creation patterns

---

## 🚀 What's Next?

In **Classes Part 2**, we'll cover:
- **Generic Classes**: Type safety with `<T>`
- **POJOs**: Plain Old Java Objects
- **Enums**: Type-safe constants
- **Final Classes**: Immutable class design
- **Singleton Pattern**: One instance per JVM
- **Wrapper Classes**: Primitive to Object conversion

---

### 📚 Additional Resources
- **Practice Platform**: LeetCode, HackerRank Java problems
- **Documentation**: Oracle Java Documentation on nested classes
- **Books**: "Effective Java" by Joshua Bloch
- **Real Examples**: Study Spring Framework source code for practical usage

**Remember**: Practice makes perfect! Try implementing each class type with your own examples. 🎯