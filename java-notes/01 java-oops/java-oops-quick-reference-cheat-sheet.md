# Java OOP Quick Reference Cheat Sheet

## 🚀 Core Concepts

### Object vs Class
| **Object** | **Class** |
|------------|-----------|
| Instance of a class | Blueprint/Template |
| Has actual values | Defines structure |
| Memory allocated | No memory until instantiated |
| `Dog myDog = new Dog()` | `class Dog { }` |

---

## 🏛️ Four Pillars - Quick Reference

### 1️⃣ Abstraction
- **What**: Hide implementation, show essential features
- **How**: Interfaces, Abstract classes
- **Example**: Car brake pedal (press → stop, internal mechanism hidden)

### 2️⃣ Encapsulation  
- **What**: Bundle data + methods, control access
- **How**: Private fields + Public methods
- **Example**: Medicine capsule protects medicine inside

### 3️⃣ Inheritance
- **What**: Child inherits parent properties
- **How**: `extends` keyword
- **Example**: Car extends Vehicle

### 4️⃣ Polymorphism
- **What**: One method, many forms
- **Types**: Overloading (compile-time) + Overriding (runtime)
- **Example**: Person as father/husband/employee

---

## 🔐 Access Modifiers (Most to Least Restrictive)

| **Modifier** | **Same Class** | **Same Package** | **Subclass** | **Everywhere** |
|-------------|----------------|------------------|--------------|----------------|
| `private` | ✅ | ❌ | ❌ | ❌ |
| `default` | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |

---

## 🔄 Inheritance Types

```
Single: A → B
Multi-level: A → B → C
Hierarchical: A ← B, A ← C
Multiple: A,B → C (❌ Not allowed in Java)
```

### Diamond Problem
```java
// ❌ NOT ALLOWED
class C extends A, B { }  // Ambiguous method calls

// ✅ SOLUTION
class C implements InterfaceA, InterfaceB { }  // Must override
```

---

## 🔄 Polymorphism Quick Guide

### Method Overloading (Compile-time)
```java
class Calculator {
    int add(int a, int b) { }           // 2 parameters
    int add(int a, int b, int c) { }    // 3 parameters  
    String add(String a, String b) { }   // Different type
}
```

**Rules**: ✅ Different parameters | ❌ Only return type different

### Method Overriding (Runtime)
```java
class Animal {
    void sound() { System.out.println("Animal sound"); }
}
class Dog extends Animal {
    @Override
    void sound() { System.out.println("Woof!"); }  // Same signature
}
```

**Rules**: ✅ Same signature | ✅ Parent-child relationship

---

## 🔗 Relationships

### Is-A (Inheritance)
```java
class Car extends Vehicle { }  // Car IS-A Vehicle
```

### Has-A (Composition/Aggregation)
```java
class School {
    List<Student> students;  // School HAS-A Students
}
```

#### Aggregation vs Composition
- **Aggregation** (Weak): School-Students (independent existence)
- **Composition** (Strong): School-Rooms (dependent existence)

---

## 💡 Memory Tricks

### Four Pillars: **A-E-I-P**
- **A**bstraction = **A**TM (hide complexity)
- **E**ncapsulation = **E**ncapsule (medicine capsule)
- **I**nheritance = **I**nherit from parents
- **P**olymorphism = **P**erson (many roles)

### Access Modifiers: **"Private → Public"**
- **Private**: Personal diary (only you)
- **Protected**: Parents and kids can see
- **Public**: Published book (everyone reads)

---

## 🎯 Interview Questions (30-second answers)

**Q: Four pillars of OOP?**
A: Abstraction (hide complexity), Encapsulation (bundle data+methods), Inheritance (reuse parent features), Polymorphism (one method, many forms)

**Q: Overloading vs Overriding?**
A: Overloading = same method name, different parameters, same class. Overriding = same signature, parent-child classes

**Q: Why no multiple inheritance in Java?**
A: Diamond problem - ambiguity when two parents have same method. Solved using interfaces.

**Q: Is-A vs Has-A?**
A: Is-A = inheritance (Car is Vehicle). Has-A = composition (Car has Engine)

---

## ⚡ Common Syntax

### Class Definition
```java
public class ClassName {
    private int field;           // Data member
    public void method() { }     // Data method
}
```

### Object Creation
```java
ClassName obj = new ClassName();
```

### Inheritance
```java
class Child extends Parent { }
```

### Interface Implementation
```java
class MyClass implements MyInterface { }
```

### Method Override
```java
@Override
public void method() { }
```

---

## ❌ Common Mistakes

1. **Public fields** instead of private + getters/setters
2. **Multiple inheritance** with classes (use interfaces)
3. **Overloading only by return type** (not allowed)
4. **Breaking parent contract** in overriding
5. **Null checks** missing in has-a relationships

---

## ✅ Best Practices

1. **Encapsulate** - private fields, public methods
2. **Favor composition** over inheritance
3. **Use @Override** annotation
4. **Single responsibility** per class
5. **Program to interfaces**, not implementations

---

## 🔧 Quick Debugging

**NullPointerException**: Check object initialization
**Method not found**: Check inheritance hierarchy
**Access denied**: Check access modifiers
**Ambiguous method**: Check overloading parameters

---

## 📚 Real-World Examples

**Banking**: Account → SavingsAccount (inheritance)
**E-commerce**: ShoppingCart has Products (composition)
**GUI**: Button extends Component (inheritance)
**Game**: Character → Warrior/Mage (polymorphism)

---

*🎯 Master these fundamentals → Advanced topics (Abstract classes, Interfaces, Inner classes)*