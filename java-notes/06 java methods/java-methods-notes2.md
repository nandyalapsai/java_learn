# Java Methods - Complete Notes

## Table of Contents
1. [What is a Method?](#what-is-a-method)
2. [Method Declaration Syntax](#method-declaration-syntax)
3. [Access Specifiers](#access-specifiers)
4. [Types of Methods](#types-of-methods)
5. [Static Methods](#static-methods)
6. [Method Overloading vs Overriding](#method-overloading-vs-overriding)
7. [Final Methods](#final-methods)
8. [Abstract Methods](#abstract-methods)
9. [Variable Arguments (Varargs)](#variable-arguments-varargs)
10. [Best Practices](#best-practices)
11. [Common Interview Questions](#common-interview-questions)

---

## What is a Method?

### Definition
- **Method**: A collection of instructions that perform a specific task
- Used to achieve **code reusability** and **readability**
- Takes input (parameters), processes it, and returns output

### Basic Example
```java
public class Calculation {
    public int sum(int variable1, int variable2) {
        int total = variable1 + variable2;
        System.out.println("Performing addition");
        return total;
    }
}
```

### Benefits
- **Reusability**: Same method can be used multiple times
- **Readability**: Code becomes more organized and understandable
- **Modularity**: Break complex problems into smaller, manageable pieces

---

## Method Declaration Syntax

```java
[access_specifier] [return_type] [method_name]([parameter_list]) [throws exception] {
    // method body
}
```

### Components Breakdown

| Component | Description | Example |
|-----------|-------------|---------|
| **Access Specifier** | Controls method visibility | `public`, `private`, `protected`, `default` |
| **Return Type** | Data type of returned value | `int`, `String`, `boolean`, `void` |
| **Method Name** | Identifier for the method | `calculateSum`, `printDetails` |
| **Parameter List** | Input variables | `(int a, String name)` |
| **Method Body** | Actual implementation | `{ /* code */ }` |

### Method Naming Convention
- Start with **lowercase letter**
- Use **camelCase** for multiple words
- Should be a **verb** (action-oriented)
- Examples: `getName()`, `calculateTotalPrice()`, `printEmployeeDetails()`

---

## Access Specifiers

### Overview Table

| Access Specifier | Same Class | Same Package | Different Package (Subclass) | Different Package (Non-subclass) |
|------------------|------------|--------------|------------------------------|-----------------------------------|
| **public** | ✅ | ✅ | ✅ | ✅ |
| **protected** | ✅ | ✅ | ✅ | ❌ |
| **default** | ✅ | ✅ | ❌ | ❌ |
| **private** | ✅ | ❌ | ❌ | ❌ |

### Detailed Explanation

#### 1. Public
```java
public class Invoice {
    public void getInvoice() {
        System.out.println("Inside invoice method");
    }
}

// Can be accessed from anywhere
Invoice inv = new Invoice();
inv.getInvoice(); // Works from any class, any package
```

#### 2. Private
```java
public class Invoice {
    private void getInvoice() {
        System.out.println("Private method");
    }
    
    public void printInvoice() {
        getInvoice(); // Works - same class
    }
}
```

#### 3. Protected
```java
// Parent class in package1
public class Invoice {
    protected void getInvoice() {
        System.out.println("Protected method");
    }
}

// Child class in package2
public class JobPortal extends Invoice {
    public void someMethod() {
        getInvoice(); // Works - subclass in different package
    }
}
```

#### 4. Default (Package-private)
```java
class Invoice {
    void getInvoice() { // No access modifier = default
        System.out.println("Default method");
    }
}
// Only accessible within same package
```

---

## Types of Methods

### 1. System Defined Methods
- **Definition**: Pre-built methods provided by Java libraries
- **Source**: JDK (Java Development Kit)
- **Examples**:
```java
// Math library methods
double result = Math.sqrt(25);      // Returns 5.0
int max = Math.max(10, 20);         // Returns 20
double power = Math.pow(2, 3);      // Returns 8.0

// String methods
String text = "Hello";
int length = text.length();         // Returns 5
String upper = text.toUpperCase();  // Returns "HELLO"
```

### 2. User Defined Methods
- **Definition**: Methods created by programmers based on requirements
- **Example**:
```java
public class Calculator {
    // User defined method
    public int multiply(int a, int b) {
        return a * b;
    }
}
```

---

## Static Methods

### Key Concepts
- **Associated with class**, not with objects
- **Called using class name**, no object creation needed
- **Shared among all instances** of the class

### Example
```java
public class Calculator {
    // Static method
    public static int add(int a, int b) {
        return a + b;
    }
    
    // Non-static method
    public int subtract(int a, int b) {
        return a - b;
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        // Static method - called with class name
        int sum = Calculator.add(5, 3);
        
        // Non-static method - requires object
        Calculator calc = new Calculator();
        int diff = calc.subtract(5, 3);
    }
}
```

### Static Method Rules

#### 1. Cannot Access Non-static Members
```java
public class Example {
    int instanceVar = 10;        // Non-static variable
    static int staticVar = 20;   // Static variable
    
    public static void staticMethod() {
        System.out.println(staticVar);     // ✅ Works
        // System.out.println(instanceVar); // ❌ Error - cannot access
        
        // To access non-static members, create object
        Example obj = new Example();
        System.out.println(obj.instanceVar); // ✅ Works
    }
}
```

#### 2. Cannot be Overridden
```java
class Parent {
    public static void display() {
        System.out.println("Parent static method");
    }
}

class Child extends Parent {
    // This is method hiding, not overriding
    public static void display() {
        System.out.println("Child static method");
    }
}

// Usage
Parent.display(); // Calls Parent's method
Child.display();  // Calls Child's method
```

### When to Use Static Methods

✅ **Use Static When:**
- Method doesn't modify object state
- Method only depends on parameters
- Utility methods (like Math functions)
- Factory methods

❌ **Don't Use Static When:**
- Method uses instance variables
- Method modifies object state
- Need polymorphic behavior

### Example: Good Static Method
```java
public class MathUtils {
    // Good static method - only uses parameters
    public static int calculateArea(int length, int width) {
        return length * width;
    }
}
```

### Example: Bad Static Method
```java
public class BankAccount {
    private double balance = 100.0;
    
    // BAD - static method trying to use instance variable
    public static void withdraw(double amount) {
        // balance -= amount; // ❌ Error - can't access instance variable
    }
}
```

---

## Method Overloading vs Overriding

### Method Overloading

#### Definition
- **Same method name** with **different parameters**
- **Compile-time polymorphism** (Static binding)
- Must differ in **parameter list** (number, type, or order)

#### Example
```java
public class Calculator {
    // Method 1: No parameters
    public void calculate() {
        System.out.println("No calculation");
    }
    
    // Method 2: One parameter
    public void calculate(int a) {
        System.out.println("Result: " + a);
    }
    
    // Method 3: Two parameters
    public void calculate(int a, int b) {
        System.out.println("Result: " + (a + b));
    }
    
    // Method 4: Different parameter type
    public void calculate(double a, double b) {
        System.out.println("Result: " + (a + b));
    }
}

// Usage
Calculator calc = new Calculator();
calc.calculate();           // Calls method 1
calc.calculate(5);          // Calls method 2
calc.calculate(3, 4);       // Calls method 3
calc.calculate(2.5, 3.7);   // Calls method 4
```

#### Overloading Rules
- ✅ Different number of parameters
- ✅ Different types of parameters
- ✅ Different order of parameters
- ❌ **Return type alone cannot differentiate** overloaded methods

```java
// ❌ Invalid overloading - only return type differs
public int calculate(int a, int b) { return a + b; }
public double calculate(int a, int b) { return a + b; } // Error!
```

### Method Overriding

#### Definition
- **Same method signature** in parent and child class
- **Runtime polymorphism** (Dynamic binding)
- Child class provides **specific implementation**

#### Example
```java
class Person {
    public void profession() {
        System.out.println("I am a person");
    }
}

class Doctor extends Person {
    @Override
    public void profession() {
        System.out.println("I am a doctor");
    }
}

// Usage - Runtime polymorphism
Person person = new Doctor(); // Parent reference, child object
person.profession(); // Output: "I am a doctor" (calls child method)
```

#### Overriding Rules
- Method signature must be **exactly the same**
- Return type must be **same or covariant**
- Access modifier **cannot be more restrictive**
- **Cannot override static, final, or private methods**

### Comparison Table

| Aspect | Overloading | Overriding |
|--------|-------------|------------|
| **Purpose** | Multiple ways to call same method | Specific implementation in child |
| **Parameters** | Must be different | Must be same |
| **Return Type** | Can be different | Must be same/covariant |
| **Binding** | Compile-time (static) | Runtime (dynamic) |
| **Inheritance** | Not required | Required |
| **Access Modifier** | Can be any | Cannot be more restrictive |

---

## Final Methods

### Definition
- **Cannot be overridden** by subclasses
- Used when you want to **prevent modification** of method behavior

### Example
```java
class Parent {
    // Final method - cannot be overridden
    public final void display() {
        System.out.println("This implementation cannot be changed");
    }
    
    // Regular method - can be overridden
    public void show() {
        System.out.println("This can be overridden");
    }
}

class Child extends Parent {
    // ❌ This will cause compilation error
    // public void display() { ... }
    
    // ✅ This is allowed
    @Override
    public void show() {
        System.out.println("Overridden implementation");
    }
}
```

### Use Cases
- **Critical business logic** that shouldn't be modified
- **Security-sensitive operations**
- **Template methods** in framework design

---

## Abstract Methods

### Definition
- **Only declaration, no implementation**
- Must be in an **abstract class**
- **Child classes must implement** the abstract method

### Example
```java
// Abstract class
abstract class Person {
    // Concrete method
    public void walk() {
        System.out.println("Person is walking");
    }
    
    // Abstract method - no implementation
    public abstract void profession();
}

// Child class must implement abstract method
class Doctor extends Person {
    @Override
    public void profession() {
        System.out.println("I am a doctor");
    }
}

class Teacher extends Person {
    @Override
    public void profession() {
        System.out.println("I am a teacher");
    }
}
```

### Key Points
- Abstract methods **cannot have a body**
- Class with abstract methods **must be declared abstract**
- **Cannot instantiate abstract class directly**
- Used for **enforcing contract** in inheritance

---

## Variable Arguments (Varargs)

### Definition
- Allows method to accept **variable number of parameters**
- Uses **three dots (...)** syntax
- Treated as an **array** inside the method

### Example
```java
public class VarargsExample {
    // Method with variable arguments
    public int sum(int... numbers) {
        int total = 0;
        for (int num : numbers) {
            total += num;
        }
        return total;
    }
    
    public static void main(String[] args) {
        VarargsExample obj = new VarargsExample();
        
        // Can call with different number of arguments
        System.out.println(obj.sum(1));           // 1
        System.out.println(obj.sum(1, 2));        // 3
        System.out.println(obj.sum(1, 2, 3, 4));  // 10
        System.out.println(obj.sum());            // 0 (no arguments)
    }
}
```

### Varargs Rules

#### 1. Only One Varargs Parameter
```java
// ❌ Invalid - multiple varargs
public void method(int... a, String... b) { }

// ✅ Valid - only one varargs
public void method(int... numbers) { }
```

#### 2. Must be Last Parameter
```java
// ❌ Invalid - varargs not at end
public void method(int... numbers, String name) { }

// ✅ Valid - varargs at end
public void method(String name, int... numbers) { }
```

### Advanced Example
```java
public class AdvancedVarargs {
    public void processData(String operation, int... values) {
        System.out.println("Operation: " + operation);
        System.out.println("Values: " + Arrays.toString(values));
        
        if (operation.equals("sum")) {
            int sum = 0;
            for (int value : values) {
                sum += value;
            }
            System.out.println("Sum: " + sum);
        }
    }
    
    public static void main(String[] args) {
        AdvancedVarargs obj = new AdvancedVarargs();
        obj.processData("sum", 1, 2, 3, 4, 5);
    }
}
```

---

## Best Practices

### 1. Method Naming
- Use **descriptive, action-oriented names**
- Follow **camelCase** convention
- Keep methods **focused on single responsibility**

```java
// ✅ Good naming
public void calculateMonthlyInterest() { }
public boolean isValidEmail(String email) { }
public List<User> getActiveUsers() { }

// ❌ Poor naming
public void doStuff() { }
public void method1() { }
public void calculate() { } // Too generic
```

### 2. Method Size
- Keep methods **small and focused**
- **Single Responsibility Principle**
- Generally **under 20 lines**

### 3. Parameter Guidelines
- **Limit number of parameters** (max 3-4)
- Use **objects or builders** for many parameters
- **Validate parameters** at method start

```java
// ✅ Good parameter validation
public void setAge(int age) {
    if (age < 0 || age > 150) {
        throw new IllegalArgumentException("Invalid age: " + age);
    }
    this.age = age;
}
```

### 4. Return Values
- **Prefer returning values** over modifying parameters
- **Use Optional** for methods that might not return value
- **Be consistent** with return types

---

## Common Pitfalls to Avoid

### 1. Static Method Misuse
```java
// ❌ Avoid - static method accessing instance variables
class BadExample {
    private int count = 0;
    
    public static void increment() {
        // count++; // Error - can't access instance variable
    }
}
```

### 2. Overloading Confusion
```java
// ❌ Confusing overloading
public void process(int a, double b) { }
public void process(double a, int b) { }

// Calling process(1, 2) is ambiguous!
```

### 3. Not Following Naming Conventions
```java
// ❌ Bad naming
public void CalculateTotal() { }  // Should start with lowercase
public void calc_total() { }      // Use camelCase, not snake_case
```

---

## Real-World Use Cases

### 1. Utility Classes
```java
public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    public static String capitalize(String str) {
        if (isEmpty(str)) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
```

### 2. Builder Pattern
```java
public class EmailBuilder {
    private String to;
    private String subject;
    private String body;
    
    public EmailBuilder setTo(String to) {
        this.to = to;
        return this;
    }
    
    public EmailBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }
    
    public EmailBuilder setBody(String body) {
        this.body = body;
        return this;
    }
    
    public Email build() {
        return new Email(to, subject, body);
    }
}

// Usage
Email email = new EmailBuilder()
    .setTo("user@example.com")
    .setSubject("Hello")
    .setBody("Hello World!")
    .build();
```

### 3. Factory Pattern
```java
public class DatabaseConnectionFactory {
    public static Connection getConnection(String type) {
        switch (type.toLowerCase()) {
            case "mysql":
                return new MySQLConnection();
            case "postgresql":
                return new PostgreSQLConnection();
            default:
                throw new IllegalArgumentException("Unknown database type: " + type);
        }
    }
}
```

---

## Common Interview Questions

### Q1: What is the difference between method overloading and overriding?

**Answer:**
- **Overloading**: Same method name, different parameters. Resolved at compile-time.
- **Overriding**: Same method signature in parent and child class. Resolved at runtime.

### Q2: Can we override static methods?

**Answer:**
No, static methods cannot be overridden. They can be hidden in subclasses, but it's not true overriding because static methods are resolved at compile-time based on the class name, not the object type.

### Q3: What happens if we don't provide access modifier for a method?

**Answer:**
The method gets **default (package-private)** access, meaning it can only be accessed by classes within the same package.

### Q4: Can a static method be overloaded?

**Answer:**
Yes, static methods can be overloaded just like instance methods, as long as they have different parameter lists.

### Q5: What is the main method signature and why?

**Answer:**
```java
public static void main(String[] args)
```
- **public**: Accessible by JVM from outside the class
- **static**: JVM can call without creating object
- **void**: Doesn't return anything to JVM
- **String[] args**: Command line arguments

### Q6: Can we have multiple main methods in a class?

**Answer:**
Yes, through method overloading, but JVM will only recognize `public static void main(String[] args)` as the entry point.

### Q7: What is method hiding vs method overriding?

**Answer:**
- **Method hiding**: When a static method in subclass has same signature as static method in parent class
- **Method overriding**: When an instance method in subclass overrides instance method in parent class

### Q8: Can we change return type while overriding?

**Answer:**
- **Same return type**: Always allowed
- **Covariant return type**: Allowed (child class return type can be subtype of parent's return type)
- **Different unrelated types**: Not allowed

### Q9: What are the rules for variable arguments (varargs)?

**Answer:**
1. Only one varargs parameter per method
2. Must be the last parameter
3. Treated as array inside method
4. Can accept zero or more arguments

### Q10: When should a method be declared as final?

**Answer:**
- When you want to prevent overriding
- For critical business logic that shouldn't change
- For security-sensitive operations
- When method is part of class's invariant

---

## Memory Diagram: Method Invocation

```
Stack Memory:
┌─────────────────┐
│     main()      │ ← Current method
├─────────────────┤
│   calculate()   │ ← Called method
├─────────────────┤
│    add()        │ ← Nested call
└─────────────────┘

Heap Memory:
┌─────────────────┐
│  Object Data    │
│  ┌─────────────┐│
│  │ instance    ││ ← Object attributes
│  │ variables   ││
│  └─────────────┘│
└─────────────────┘

Method Area:
┌─────────────────┐
│ Class Metadata  │
│ Static Methods  │ ← Static methods stored here
│ Static Variables│
└─────────────────┘
```

---

## Summary Checklist

✅ **Method Basics**
- [ ] Understand method definition and benefits
- [ ] Know method declaration syntax
- [ ] Understand different access specifiers

✅ **Method Types**
- [ ] Differentiate between static and instance methods
- [ ] Understand when to use static methods
- [ ] Know rules and restrictions of static methods

✅ **Polymorphism**
- [ ] Understand method overloading rules
- [ ] Understand method overriding concepts
- [ ] Know the difference between compile-time and runtime polymorphism

✅ **Special Methods**
- [ ] Understand final methods and their use cases
- [ ] Know abstract methods and abstract classes
- [ ] Understand variable arguments (varargs)

✅ **Best Practices**
- [ ] Follow proper naming conventions
- [ ] Keep methods focused and small
- [ ] Use appropriate access modifiers
- [ ] Validate parameters and handle edge cases

This comprehensive guide covers all aspects of Java methods discussed in the video, organized for both learning and interview preparation.