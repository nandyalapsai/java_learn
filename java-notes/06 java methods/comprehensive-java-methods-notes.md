# Java Methods - Comprehensive Notes

## 📚 Learning Objectives

After reading these notes, you should be able to:

- [ ] Define what methods are and explain their purpose in Java
- [ ] Understand and implement all four access specifiers (public, private, protected, default)
- [ ] Declare methods with proper syntax including return types, parameters, and method names
- [ ] Differentiate between system-defined and user-defined methods
- [ ] Implement method overloading with different parameter combinations
- [ ] Understand method overriding and its relationship with inheritance
- [ ] Work with static methods and understand when to use them
- [ ] Implement final methods and understand their restrictions
- [ ] Create and use abstract methods in abstract classes
- [ ] Use variable arguments (varargs) for flexible parameter handling
- [ ] Apply best practices for method design and naming conventions

## 🎯 What is a Method?

### Definition
A **method** is a collection of instructions that performs a specific task. It takes input (parameters), processes it, and optionally returns an output.

### Purpose
- **Code Reusability**: Write once, use multiple times
- **Code Readability**: Organized, logical structure
- **Task-Oriented**: Each method performs a specific function

### Basic Example
```java
public class Calculator {
    public int sum(int variable1, int variable2) {
        int total = variable1 + variable2;
        System.out.println("Performing addition operation");
        return total;
    }
}
```

## 🔧 Method Declaration Syntax

```java
[access_specifier] [return_type] [method_name]([parameter_list]) [throws_exception] {
    // method body
    // return statement (if not void)
}
```

### Components Breakdown

1. **Access Specifier**: Controls visibility and accessibility
2. **Return Type**: What the method returns after execution
3. **Method Name**: Identifier following naming conventions
4. **Parameter List**: Input variables for the method
5. **Throws Exception**: Optional exception declarations
6. **Method Body**: Implementation code

## 🚪 Access Specifiers

| Access Specifier | Visibility | Access Rules |
|------------------|------------|--------------|
| **public** | Global | Accessible from any class in any package |
| **private** | Same class only | Only accessible within the same class |
| **protected** | Same package + subclasses | Same package OR subclasses in different packages |
| **default** | Same package only | Only accessible within the same package |

### Code Examples

```java
// Package: sales.department
public class Invoice {
    
    // PUBLIC - accessible everywhere
    public void getInvoice() {
        System.out.println("Inside invoice method");
    }
    
    // PRIVATE - only within Invoice class
    private void calculateTax() {
        System.out.println("Calculating tax");
    }
    
    // PROTECTED - same package + subclasses
    protected void generateReport() {
        System.out.println("Generating report");
    }
    
    // DEFAULT - same package only
    void processPayment() {
        System.out.println("Processing payment");
    }
}

// Package: hr.department  
public class JobPortal extends Invoice {
    public void someMethod() {
        // Can access protected method even in different package (inheritance)
        generateReport(); // ✅ Works
        
        // Cannot access private method
        // calculateTax(); // ❌ Error
        
        // Cannot access default method from different package
        // processPayment(); // ❌ Error
    }
}
```

### 📊 Access Specifier Diagram

```
┌─────────────────────────────────────────────────────┐
│                    PUBLIC                           │
│  ┌─────────────────────────────────────────────┐    │
│  │               PROTECTED                     │    │
│  │  ┌─────────────────────────────────────┐    │    │
│  │  │            DEFAULT                  │    │    │
│  │  │  ┌─────────────────────────────┐    │    │    │
│  │  │  │         PRIVATE             │    │    │    │
│  │  │  │    (Same class only)        │    │    │    │
│  │  │  └─────────────────────────────┘    │    │    │
│  │  │     (Same package)                  │    │    │
│  │  └─────────────────────────────────────┘    │    │
│  │        (Same package + Subclasses)           │    │
│  └─────────────────────────────────────────────┘    │
│                (Anywhere)                           │
└─────────────────────────────────────────────────────┘
```

## 🔄 Return Types

### Common Return Types
- **void**: Method doesn't return anything
- **Primitive types**: int, double, boolean, char, etc.
- **Object types**: String, custom classes, arrays, etc.

```java
public class ReturnTypeExamples {
    
    // VOID - returns nothing
    public void printMessage() {
        System.out.println("Hello World");
        return; // optional for void methods
    }
    
    // PRIMITIVE - returns integer
    public int calculateAge(int birthYear) {
        return 2024 - birthYear;
    }
    
    // BOOLEAN - returns true/false
    public boolean isEligible(int age) {
        return age >= 18;
    }
    
    // STRING - returns String object
    public String getFullName(String first, String last) {
        return first + " " + last;
    }
    
    // ARRAY - returns array of integers
    public int[] getScores() {
        return new int[]{85, 90, 78, 92};
    }
}
```

## 📝 Method Naming Conventions

### Rules & Best Practices
1. **Start with lowercase letter**: `calculateTotal()` ✅
2. **Use camelCase**: `getEmployeeDetails()` ✅
3. **Use verbs**: Methods perform actions
4. **Be descriptive**: `validate()` vs `isValidEmail()`
5. **Avoid abbreviations**: `calculateSalary()` vs `calcSal()`

### Good vs Bad Examples
```java
// ✅ GOOD EXAMPLES
public void calculateTotalPrice() { }
public boolean isValidUser() { }
public String getUserName() { }
public void updateEmployeeRecord() { }

// ❌ BAD EXAMPLES
public void Calculate() { }          // Starts with capital
public void calc_price() { }         // Uses underscore
public void method1() { }            // Not descriptive
public void GetData() { }            // Starts with capital
```

## 🔧 Types of Methods

### 1. System-Defined Methods
Pre-built methods provided by Java libraries.

```java
public class SystemMethodExamples {
    public void demonstrateSystemMethods() {
        // Math class methods
        double sqrt = Math.sqrt(25);           // Returns 5.0
        double power = Math.pow(2, 3);         // Returns 8.0
        int max = Math.max(10, 20);            // Returns 20
        
        // String class methods
        String text = "Hello World";
        int length = text.length();            // Returns 11
        String upper = text.toUpperCase();     // Returns "HELLO WORLD"
        
        // System class methods
        System.out.println("Output to console");
        long currentTime = System.currentTimeMillis();
    }
}
```

### 2. User-Defined Methods
Methods created by programmers for specific requirements.

```java
public class UserDefinedMethods {
    
    // Custom method for business logic
    public double calculateDiscount(double price, double discountPercent) {
        return price * (discountPercent / 100);
    }
    
    // Custom method for validation
    public boolean isValidPassword(String password) {
        return password.length() >= 8 && 
               password.matches(".*[A-Z].*") && 
               password.matches(".*[0-9].*");
    }
}
```

## 🔄 Method Overloading

### Definition
Multiple methods with the **same name** but **different parameters** in the same class.

### Rules for Overloading
1. ✅ **Different parameter count**: `method()` vs `method(int a)`
2. ✅ **Different parameter types**: `method(int a)` vs `method(String a)`
3. ✅ **Different parameter order**: `method(int a, String b)` vs `method(String a, int b)`
4. ❌ **Return type alone**: Cannot differentiate by return type only

### Overloading Examples
```java
public class OverloadingExample {
    
    // Method 1: No parameters
    public void display() {
        System.out.println("Display with no parameters");
    }
    
    // Method 2: One integer parameter
    public void display(int number) {
        System.out.println("Display with integer: " + number);
    }
    
    // Method 3: One string parameter
    public void display(String message) {
        System.out.println("Display with string: " + message);
    }
    
    // Method 4: Two parameters
    public void display(int number, String message) {
        System.out.println("Number: " + number + ", Message: " + message);
    }
    
    // Method 5: Different parameter order
    public void display(String message, int number) {
        System.out.println("Message: " + message + ", Number: " + number);
    }
    
    // ❌ INVALID - Cannot overload based on return type alone
    // public int display() { return 0; } // This would cause compilation error
}

// Usage example
public class TestOverloading {
    public static void main(String[] args) {
        OverloadingExample obj = new OverloadingExample();
        
        obj.display();                    // Calls method 1
        obj.display(42);                  // Calls method 2  
        obj.display("Hello");             // Calls method 3
        obj.display(42, "Hello");         // Calls method 4
        obj.display("Hello", 42);         // Calls method 5
    }
}
```

## 🏗️ Method Overriding

### Definition
Subclass provides a **specific implementation** for a method that is already defined in its parent class.

### Rules for Overriding
1. ✅ **Same method signature**: name, parameters, return type
2. ✅ **Inheritance relationship**: Must have parent-child relationship
3. ✅ **@Override annotation**: Recommended for clarity
4. ❌ **Cannot override private methods**: Not inherited
5. ❌ **Cannot override static methods**: Belong to class, not instance
6. ❌ **Cannot override final methods**: Marked as unchangeable

### Overriding Example
```java
// Parent class
public class Person {
    public void profession() {
        System.out.println("I am a person with some profession");
    }
    
    public void displayInfo() {
        System.out.println("Person's general information");
    }
}

// Child class
public class Doctor extends Person {
    
    @Override
    public void profession() {
        System.out.println("I am a doctor and I treat patients");
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo(); // Call parent method
        System.out.println("Specialized in medical field");
    }
}

// Usage and Dynamic Binding
public class TestOverriding {
    public static void main(String[] args) {
        // Dynamic binding example
        Person person = new Doctor(); // Parent reference, child object
        person.profession(); // Calls Doctor's profession() method at runtime
        
        // Output: "I am a doctor and I treat patients"
    }
}
```

### 📊 Overloading vs Overriding Comparison

| Aspect | Overloading | Overriding |
|--------|-------------|------------|
| **Purpose** | Multiple versions of same method | Redefine parent method |
| **Binding** | Compile-time (Static) | Runtime (Dynamic) |
| **Inheritance** | Same class | Parent-child classes |
| **Parameters** | Must be different | Must be same |
| **Return Type** | Can be different | Must be same |
| **Access Modifier** | Can be different | Cannot be more restrictive |

## 🔒 Static Methods

### Definition
Methods that belong to the **class** rather than to any **instance** of the class.

### Key Characteristics
1. **Associated with class**: Called using class name
2. **No object required**: Can be invoked without creating instance
3. **Shared among all instances**: One copy per class
4. **Cannot access instance variables**: Only static variables
5. **Cannot be overridden**: Compile-time binding

### Static Method Example
```java
public class StaticMethodExample {
    
    // Instance variable
    private int instanceVar = 10;
    
    // Static variable
    private static int staticVar = 20;
    
    // Static method
    public static int addNumbers(int a, int b) {
        // ✅ Can access static variables
        System.out.println("Static variable: " + staticVar);
        
        // ❌ Cannot access instance variables
        // System.out.println("Instance variable: " + instanceVar); // Error!
        
        return a + b;
    }
    
    // Instance method
    public void instanceMethod() {
        // ✅ Can access both static and instance variables
        System.out.println("Instance variable: " + instanceVar);
        System.out.println("Static variable: " + staticVar);
    }
    
    // Static method calling instance method
    public static void staticCallingInstance() {
        // ❌ Cannot directly call instance method
        // instanceMethod(); // Error!
        
        // ✅ Must create object first
        StaticMethodExample obj = new StaticMethodExample();
        obj.instanceMethod(); // Now it works
    }
}

// Usage
public class TestStatic {
    public static void main(String[] args) {
        // Calling static method with class name
        int result = StaticMethodExample.addNumbers(5, 3);
        System.out.println("Result: " + result);
        
        // No need to create object for static methods
        // StaticMethodExample obj = new StaticMethodExample(); // Not required
    }
}
```

### 🤔 When to Use Static Methods

**✅ Use Static When:**
- Method doesn't need instance data
- Utility functions (Math.sqrt(), Integer.parseInt())
- Factory methods for object creation
- Helper methods that work only with parameters

**❌ Avoid Static When:**
- Method needs to access instance variables
- Method needs to be overridden
- Method represents object behavior

### Static Method Memory Representation
```
┌─────────────────────────────┐
│         Class Area          │
│  ┌─────────────────────────┐│
│  │   Static Methods        ││
│  │   Static Variables      ││
│  └─────────────────────────┘│
└─────────────────────────────┘
           ↑
      Shared by all
         ↓
┌─────────────┐  ┌─────────────┐  ┌─────────────┐
│   Object 1  │  │   Object 2  │  │   Object 3  │
│ ┌─────────┐ │  │ ┌─────────┐ │  │ ┌─────────┐ │
│ │Instance │ │  │ │Instance │ │  │ │Instance │ │
│ │Variables│ │  │ │Variables│ │  │ │Variables│ │
│ │Methods  │ │  │ │Methods  │ │  │ │Methods  │ │
│ └─────────┘ │  │ └─────────┘ │  │ └─────────┘ │
└─────────────┘  └─────────────┘  └─────────────┘
```

## 🛡️ Final Methods

### Definition
Methods marked with `final` keyword **cannot be overridden** by subclasses.

### Purpose
- **Prevent modification**: Ensure method implementation remains unchanged
- **Security**: Critical methods that shouldn't be altered
- **Design enforcement**: Maintain intended behavior

### Final Method Example
```java
public class Parent {
    
    // Final method - cannot be overridden
    public final void criticalOperation() {
        System.out.println("This is a critical operation that must not change");
        // Important business logic that should remain constant
    }
    
    // Regular method - can be overridden
    public void regularMethod() {
        System.out.println("This method can be overridden");
    }
}

public class Child extends Parent {
    
    // ❌ This will cause compilation error
    // @Override
    // public final void criticalOperation() {
    //     System.out.println("Trying to override final method");
    // }
    
    // ✅ This is allowed
    @Override
    public void regularMethod() {
        System.out.println("Overriding regular method");
    }
}
```

## 📝 Abstract Methods

### Definition
Methods declared without implementation, must be implemented by subclasses.

### Rules for Abstract Methods
1. **Must be in abstract class**: Cannot exist in concrete classes
2. **No method body**: Only declaration, no implementation
3. **Must be overridden**: Subclasses must provide implementation
4. **Cannot be static**: Abstract methods are meant to be overridden
5. **Cannot be final**: Final prevents overriding, abstract requires it

### Abstract Method Example
```java
// Abstract class
public abstract class Animal {
    
    // Abstract method - no implementation
    public abstract void makeSound();
    
    // Abstract method with parameters
    public abstract void move(int distance);
    
    // Concrete method - has implementation
    public void eat() {
        System.out.println("Animal is eating");
    }
}

// Concrete subclass must implement abstract methods
public class Dog extends Animal {
    
    @Override
    public void makeSound() {
        System.out.println("Dog barks: Woof! Woof!");
    }
    
    @Override
    public void move(int distance) {
        System.out.println("Dog runs " + distance + " meters");
    }
}

public class Cat extends Animal {
    
    @Override
    public void makeSound() {
        System.out.println("Cat meows: Meow! Meow!");
    }
    
    @Override
    public void move(int distance) {
        System.out.println("Cat walks " + distance + " meters silently");
    }
}

// Usage
public class TestAbstract {
    public static void main(String[] args) {
        // Cannot instantiate abstract class
        // Animal animal = new Animal(); // Error!
        
        // Can create objects of concrete subclasses
        Animal dog = new Dog();
        Animal cat = new Cat();
        
        dog.makeSound(); // Output: Dog barks: Woof! Woof!
        cat.makeSound(); // Output: Cat meows: Meow! Meow!
        
        dog.move(10);    // Output: Dog runs 10 meters
        cat.move(5);     // Output: Cat walks 5 meters silently
    }
}
```

## 🔢 Variable Arguments (Varargs)

### Definition
Allows a method to accept **variable number of arguments** of the same type.

### Syntax
```java
returnType methodName(dataType... variableName)
```

### Rules for Varargs
1. **Only one varargs per method**: Cannot have multiple varargs
2. **Must be last parameter**: Varargs must come at the end
3. **Treated as array**: Access elements using array syntax

### Varargs Examples
```java
public class VarargsExample {
    
    // Method with varargs
    public int sum(int... numbers) {
        int total = 0;
        for (int num : numbers) {
            total += num;
        }
        return total;
    }
    
    // Varargs with other parameters (varargs must be last)
    public void displayMessage(String prefix, int... numbers) {
        System.out.print(prefix + ": ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
    
    // ❌ INVALID - varargs not at the end
    // public void invalidMethod(int... numbers, String suffix) { }
    
    // ❌ INVALID - multiple varargs
    // public void invalidMethod(int... numbers1, String... strings) { }
}

// Usage examples
public class TestVarargs {
    public static void main(String[] args) {
        VarargsExample obj = new VarargsExample();
        
        // Different ways to call varargs method
        int result1 = obj.sum();                    // No arguments
        int result2 = obj.sum(5);                   // One argument
        int result3 = obj.sum(1, 2, 3);            // Multiple arguments
        int result4 = obj.sum(10, 20, 30, 40, 50); // Many arguments
        
        System.out.println("Results: " + result1 + ", " + result2 + 
                          ", " + result3 + ", " + result4);
        // Output: Results: 0, 5, 6, 150
        
        // Using varargs with other parameters
        obj.displayMessage("Numbers", 1, 2, 3, 4, 5);
        // Output: Numbers: 1 2 3 4 5
        
        obj.displayMessage("Empty");
        // Output: Empty:
    }
}
```

### ⚡ Varargs vs Array Parameter

| Aspect | Varargs `(int... nums)` | Array Parameter `(int[] nums)` |
|--------|-------------------------|--------------------------------|
| **Calling** | `method(1, 2, 3)` | `method(new int[]{1, 2, 3})` |
| **Flexibility** | Can pass 0 to many args | Must pass array object |
| **Null handling** | Cannot pass null directly | Can pass null array |
| **Compilation** | Converts to array internally | Direct array parameter |

## 💡 Best Practices

### ✅ Do's
1. **Use meaningful names**: `calculateTotalPrice()` instead of `calc()`
2. **Keep methods small**: Single responsibility principle
3. **Use appropriate access modifiers**: Minimum required visibility
4. **Use @Override annotation**: When overriding methods
5. **Validate parameters**: Check for null and invalid values
6. **Return consistent types**: Don't mix return types in overloaded methods
7. **Document complex methods**: Use JavaDoc comments

### ❌ Don'ts
1. **Don't make methods too long**: Break into smaller methods
2. **Don't use public unnecessarily**: Use private/protected when possible
3. **Don't ignore naming conventions**: Follow camelCase
4. **Don't modify parameters**: Use local variables instead
5. **Don't create static methods for object behavior**: Use instance methods
6. **Don't overload with confusing parameters**: Make parameter differences clear

### 🔧 Code Quality Examples
```java
public class BestPracticesExample {
    
    // ✅ GOOD: Clear, descriptive method name
    public boolean isValidEmailAddress(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.contains("@") && email.contains(".");
    }
    
    // ❌ BAD: Unclear name, no validation
    public boolean check(String s) {
        return s.contains("@");
    }
    
    // ✅ GOOD: Proper parameter validation
    public double calculateArea(double length, double width) {
        if (length <= 0 || width <= 0) {
            throw new IllegalArgumentException("Dimensions must be positive");
        }
        return length * width;
    }
    
    // ✅ GOOD: Single responsibility
    public void processOrder(Order order) {
        validateOrder(order);
        calculateTotal(order);
        applyDiscounts(order);
        updateInventory(order);
        sendConfirmation(order);
    }
    
    private void validateOrder(Order order) { /* validation logic */ }
    private void calculateTotal(Order order) { /* calculation logic */ }
    private void applyDiscounts(Order order) { /* discount logic */ }
    private void updateInventory(Order order) { /* inventory logic */ }
    private void sendConfirmation(Order order) { /* notification logic */ }
}
```

## ⚠️ Common Pitfalls

### 1. Static Method Pitfalls
```java
public class StaticPitfalls {
    private int instanceVar = 10;
    private static int staticVar = 20;
    
    // ❌ PITFALL: Trying to access instance variable from static method
    public static void badStaticMethod() {
        // System.out.println(instanceVar); // Compilation error!
    }
    
    // ✅ SOLUTION: Use static variables or pass instance as parameter
    public static void goodStaticMethod(StaticPitfalls instance) {
        System.out.println(staticVar);         // ✅ Static variable
        System.out.println(instance.instanceVar); // ✅ Instance via parameter
    }
}
```

### 2. Overriding Pitfalls
```java
public class Parent {
    public void method(Object obj) {
        System.out.println("Parent method");
    }
}

public class Child extends Parent {
    // ❌ PITFALL: This is overloading, not overriding!
    public void method(String str) {
        System.out.println("Child method");
    }
    
    // ✅ SOLUTION: Use exact same signature for overriding
    @Override
    public void method(Object obj) {
        System.out.println("Child overridden method");
    }
}
```

### 3. Varargs Pitfalls
```java
public class VarargsPitfalls {
    
    // ❌ PITFALL: Ambiguous method calls
    public void process(String... args) { }
    public void process(String first, String... rest) { }
    
    // Calling process("hello") is ambiguous - which method to call?
    
    // ✅ SOLUTION: Use different method names or parameter types
    public void processAll(String... args) { }
    public void processWithPrefix(String first, String... rest) { }
}
```

## 🧠 Memory Hooks & Mnemonics

### Access Specifiers Mnemonic
**"Public Protects Private Defaults"**
- **P**ublic: **P**lanet-wide access (anywhere)
- **P**rotected: **P**ackage + **P**rogeny (children)
- **P**rivate: **P**ersonal (same class only)
- **D**efault: **D**omain (same package)

### Method Types Mnemonic
**"SOFA-VS"**
- **S**ystem-defined
- **O**verloaded
- **F**inal
- **A**bstract
- **V**arargs
- **S**tatic

### Static Rules Mnemonic
**"Static Stands Alone"**
- Static methods cannot access **instance** variables
- Static methods cannot call **instance** methods
- Static methods cannot be **overridden**
- Static methods belong to **class**, not objects

## 🎯 Common Interview Questions

### Q1: What is the difference between method overloading and overriding?
**Answer:**
- **Overloading**: Same method name, different parameters, compile-time binding
- **Overriding**: Same method signature, parent-child relationship, runtime binding

### Q2: Can we override static methods?
**Answer:** No, static methods cannot be overridden. They can be hidden but not overridden because static methods are resolved at compile-time, not runtime.

### Q3: What happens if you don't provide access specifier?
**Answer:** The method gets **default** access, meaning it's accessible only within the same package.

### Q4: Can a method be both static and abstract?
**Answer:** No, this is impossible because:
- Abstract methods must be overridden
- Static methods cannot be overridden
- These requirements conflict with each other

### Q5: What are the rules for method overriding?
**Answer:**
1. Same method signature (name, parameters, return type)
2. Parent-child relationship required
3. Cannot override private, static, or final methods
4. Access modifier cannot be more restrictive
5. Can throw same or fewer checked exceptions

### Q6: When should you use static methods?
**Answer:** Use static methods when:
- Method doesn't need instance data
- Creating utility functions
- Implementing factory methods
- Method works only with parameters

### Q7: What is method signature?
**Answer:** Method signature consists of:
- Method name
- Number of parameters
- Type of parameters
- Order of parameters
(Note: Return type is NOT part of method signature)

## 🏃‍♂️ Hands-on Exercises

### Exercise 1: Access Specifiers Practice
Create a class hierarchy demonstrating all access specifiers:

```java
// Create this structure and test access from different packages
public class AccessSpecifierTask {
    // TODO: Create methods with different access specifiers
    // TODO: Test access from same class, same package, different package
    // TODO: Test inheritance scenarios
}
```

### Exercise 2: Method Overloading Challenge
```java
public class Calculator {
    // TODO: Create overloaded methods for:
    // - add(int, int)
    // - add(double, double)  
    // - add(int, int, int)
    // - add(String, String) - for concatenation
    // - add(int[], int[]) - for array addition
}
```

### Exercise 3: Static vs Instance Methods
```java
public class BankAccount {
    private String accountNumber;
    private double balance;
    private static double interestRate = 0.05;
    
    // TODO: Implement appropriate static and instance methods
    // TODO: Create methods that demonstrate static limitations
}
```

### Exercise 4: Varargs Implementation
```java
public class MathOperations {
    // TODO: Implement methods using varargs:
    // - findMaximum(int... numbers)
    // - calculateAverage(double... values)
    // - concatenateStrings(String separator, String... words)
}
```

## 🌍 Real-World Use Cases

### 1. Banking System
```java
public class BankAccount {
    private double balance;
    private static double minBalance = 1000.0;
    
    // Instance method - operates on specific account
    public void withdraw(double amount) {
        if (balance - amount >= minBalance) {
            balance -= amount;
        }
    }
    
    // Static method - utility function
    public static boolean isValidAccountNumber(String accountNum) {
        return accountNum.matches("\\d{10}");
    }
    
    // Overloaded methods for different transaction types
    public void transfer(String toAccount, double amount) { }
    public void transfer(String toAccount, double amount, String memo) { }
}
```

### 2. E-commerce System
```java
public abstract class Product {
    protected String name;
    protected double price;
    
    // Abstract method - each product calculates tax differently
    public abstract double calculateTax();
    
    // Final method - discount calculation shouldn't change
    public final double applyDiscount(double discountPercent) {
        return price * (1 - discountPercent / 100);
    }
    
    // Varargs for bulk operations
    public static double calculateTotalPrice(Product... products) {
        double total = 0;
        for (Product product : products) {
            total += product.price + product.calculateTax();
        }
        return total;
    }
}
```

### 3. Logging System
```java
public class Logger {
    private String className;
    
    // Overloaded logging methods
    public void log(String message) {
        System.out.println("[INFO] " + message);
    }
    
    public void log(String level, String message) {
        System.out.println("[" + level + "] " + message);
    }
    
    public void log(Exception e) {
        System.out.println("[ERROR] " + e.getMessage());
    }
    
    // Static utility method
    public static Logger getLogger(Class<?> clazz) {
        return new Logger(clazz.getSimpleName());
    }
}
```

## 🎯 Interview Preparation Checklist

### Core Concepts Understanding
- [ ] Method declaration syntax
- [ ] Access specifiers and their scope
- [ ] Static vs instance methods
- [ ] Method overloading vs overriding
- [ ] Abstract and final methods
- [ ] Variable arguments (varargs)

### Practical Skills
- [ ] Can implement method overloading correctly
- [ ] Can identify when to use static methods
- [ ] Can design proper method signatures
- [ ] Can apply appropriate access modifiers
- [ ] Can work with inheritance and method overriding

### Advanced Topics
- [ ] Understand method binding (static vs dynamic)
- [ ] Can explain method resolution during compilation
- [ ] Know limitations of static methods
- [ ] Understand varargs vs arrays
- [ ] Can design methods following best practices

## 📋 Quick Revision Checklist

Before interviews or exams, verify you can:
- [ ] Write method declaration syntax from memory
- [ ] Explain all four access specifiers with examples
- [ ] Differentiate between overloading and overriding
- [ ] List rules for static methods
- [ ] Explain when methods cannot be overridden
- [ ] Implement varargs correctly
- [ ] Apply method naming conventions
- [ ] Identify common method-related compilation errors

---

## 📚 Additional Resources

1. **Oracle Java Documentation**: Methods in Java
2. **Effective Java by Joshua Bloch**: Method design principles
3. **Java: The Complete Reference**: Comprehensive method coverage
4. **Practice Platforms**: LeetCode, HackerRank for method implementation

## 💭 Final Notes

Methods are the building blocks of Java programs. Understanding their various forms, access rules, and best practices is crucial for writing maintainable, reusable code. Focus on understanding the "why" behind each concept, not just memorizing syntax. Practice implementing different types of methods in real-world scenarios to solidify your understanding.

Remember: **"Good methods do one thing well and have clear, meaningful names that express their intent."**