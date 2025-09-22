# Java Interfaces - Quick Reference Cheat Sheet

## 🚀 Interface Basics

### Definition Syntax
```java
[public|default] interface InterfaceName [extends Interface1, Interface2...] {
    // Constants (implicitly public static final)
    int CONSTANT = 100;
    
    // Abstract methods (implicitly public abstract)
    void method();
    
    // Nested interfaces
    interface NestedInterface { }
}
```

### Implementation Syntax
```java
class ClassName implements Interface1, Interface2... {
    // Must override ALL interface methods as public
    @Override
    public void method() {
        // Implementation
    }
}
```

---

## 📋 Key Rules Checklist

### ✅ Interface CAN Have:
- Abstract methods (implicitly public)
- Constants (implicitly public static final)
- Nested interfaces
- Multiple inheritance (extends multiple interfaces)
- Default methods (Java 8+)
- Static methods (Java 8+)
- Private methods (Java 9+)

### ❌ Interface CANNOT Have:
- Constructors
- Instance variables (non-static fields)
- Final methods
- Private/protected methods (before Java 9)
- Implementation of abstract methods (before Java 8)
- Extension from classes

---

## 🎯 Memory Hooks

### **PSF Rule** for Interface Fields
- **P**ublic
- **S**tatic  
- **F**inal
All fields are implicitly PSF (constants)

### **PA Rule** for Interface Methods (before Java 8)
- **P**ublic
- **A**bstract
All methods are implicitly PA

### **DIAMOND** for Multiple Inheritance
- **D**iamond problem solved by interfaces
- **I**nterfaces allow multiple inheritance
- **A**mbiguity resolved by single implementation
- **M**ultiple contracts fulfilled
- **O**nly interfaces can be extended (not classes)
- **N**o confusion at runtime
- **D**ynamic method resolution

---

## 🔄 Interface vs Abstract Class

| Feature | Interface | Abstract Class |
|---------|-----------|----------------|
| **Keyword** | `interface` | `abstract class` |
| **Implementation** | `implements` | `extends` |
| **Multiple Inheritance** | ✅ Yes | ❌ No |
| **Constructor** | ❌ No | ✅ Yes |
| **Instance Variables** | ❌ No | ✅ Yes |
| **Method Implementation** | ❌ No (before Java 8) | ✅ Yes |
| **Access Modifiers** | Public only | Any |
| **Abstract Keyword** | Not needed | Required for abstract methods |

---

## 🏗️ Common Patterns

### 1. Polymorphism Pattern
```java
Interface[] objects = {
    new Implementation1(),
    new Implementation2(),
    new Implementation3()
};

for (Interface obj : objects) {
    obj.commonMethod(); // Different implementations called
}
```

### 2. Multiple Capabilities Pattern
```java
interface Flyable { void fly(); }
interface Swimmable { void swim(); }

class Duck implements Flyable, Swimmable {
    public void fly() { /* fly implementation */ }
    public void swim() { /* swim implementation */ }
}
```

### 3. Contract Definition Pattern
```java
interface DatabaseConnection {
    void connect();
    void disconnect();
    ResultSet executeQuery(String sql);
}

// Different database implementations
class MySQLConnection implements DatabaseConnection { /* ... */ }
class PostgreSQLConnection implements DatabaseConnection { /* ... */ }
```

---

## 🎓 Interview Quick Answers

### Q: Why can't interface methods be final?
**A:** Because `final` prevents overriding, but interface methods must be overridden by implementing classes.

### Q: Can we instantiate an interface?
**A:** No, but we can create reference variables of interface type to hold implementing class objects.

### Q: How does Java solve the diamond problem?
**A:** Through interfaces - if multiple interfaces have the same method signature, the implementing class provides one implementation that satisfies all.

### Q: What's the difference between extends and implements?
**A:** `extends` is for class inheritance (single), `implements` is for interface implementation (multiple allowed).

### Q: Can an interface extend multiple interfaces?
**A:** Yes! `interface C extends A, B { }`

---

## 🚦 Common Mistakes & Solutions

### ❌ Mistake 1: Restricting Access
```java
interface Bird { void fly(); } // implicitly public

class Eagle implements Bird {
    protected void fly() { } // ❌ ERROR: Cannot reduce visibility
}
```
**✅ Solution:** Always make implementing methods `public`

### ❌ Mistake 2: Trying to Instantiate
```java
Bird bird = new Bird(); // ❌ ERROR: Cannot instantiate interface
```
**✅ Solution:** `Bird bird = new Eagle();` (Eagle implements Bird)

### ❌ Mistake 3: Forgetting to Implement All Methods
```java
interface Animal {
    void eat();
    void sleep();
}

class Dog implements Animal {
    public void eat() { } // ❌ ERROR: Must implement sleep() too
}
```
**✅ Solution:** Implement ALL interface methods in concrete classes

### ❌ Mistake 4: Using Wrong Keyword
```java
class Dog extends Animal { } // ❌ If Animal is interface
```
**✅ Solution:** `class Dog implements Animal { }`

---

## 🎯 Code Templates

### Basic Interface Template
```java
public interface ServiceName {
    // Constants
    String DEFAULT_VALUE = "default";
    
    // Abstract methods
    void performAction();
    String getResult();
    boolean isValid();
}
```

### Implementation Template
```java
public class ServiceImpl implements ServiceName {
    @Override
    public void performAction() {
        // Implementation
    }
    
    @Override
    public String getResult() {
        return "result";
    }
    
    @Override
    public boolean isValid() {
        return true;
    }
}
```

### Multiple Interface Template
```java
public class MultiService implements Interface1, Interface2, Interface3 {
    // Implement all methods from all interfaces
    
    @Override
    public void method1() { /* from Interface1 */ }
    
    @Override
    public void method2() { /* from Interface2 */ }
    
    @Override
    public void method3() { /* from Interface3 */ }
}
```

---

## 🏃 Quick Practice Drill

### 1-Minute Challenges
1. **Define** an interface `Drawable` with method `draw()`
2. **Implement** it in classes `Circle` and `Square`
3. **Create** polymorphic array and call `draw()` on each
4. **Add** a constant `MAX_SIZE = 100` to the interface
5. **Extend** the interface to create `ColorDrawable` with `setColor()`

---

## 🎪 Real-World Examples

### Payment System
```java
interface PaymentProcessor {
    boolean processPayment(double amount);
    String getPaymentType();
}

class CreditCard implements PaymentProcessor { }
class PayPal implements PaymentProcessor { }
class ApplePay implements PaymentProcessor { }
```

### File Handler
```java
interface FileHandler {
    void readFile(String path);
    void writeFile(String path, String content);
}

class TextFileHandler implements FileHandler { }
class CSVFileHandler implements FileHandler { }
class JSONFileHandler implements FileHandler { }
```

---

## 🎁 Bonus Tips

### Study Strategy
1. **Code daily**: Write interface examples
2. **Think contracts**: Interfaces define what classes promise to do
3. **Practice polymorphism**: Use interface references with different implementations
4. **Real-world connection**: See interfaces everywhere (USB ports, power outlets, etc.)

### Before Interviews
- Review the 10 differences between interfaces and abstract classes
- Practice explaining polymorphism with interfaces
- Know the rules: PSF for fields, PA for methods
- Understand multiple inheritance concepts

---

*Keep this cheat sheet handy for quick reference during coding and interview preparation!*

## 📚 Related Topics to Study Next
- Java 8 Default Methods
- Java 8 Static Methods in Interfaces  
- Java 9 Private Methods in Interfaces
- Functional Interfaces and Lambdas
- Design Patterns using Interfaces