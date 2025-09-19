# Java Variables - Comprehensive Notes

## Table of Contents
1. [Variable Fundamentals](#variable-fundamentals)
2. [Java Language Characteristics](#java-language-characteristics)
3. [Variable Naming Conventions](#variable-naming-conventions)
4. [Primitive Data Types](#primitive-data-types)
5. [Type Conversion](#type-conversion)
6. [Variable Categories](#variable-categories)
7. [Interview Questions](#interview-questions)
8. [Best Practices](#best-practices)
9. [Common Pitfalls](#common-pitfalls)

---

## Variable Fundamentals

### What is a Variable?
A **variable** is a container that holds a value. Just like a physical container holds water, a variable in Java holds data.

### Variable Declaration Syntax
```java
dataType variableName = value;
```

**Example:**
```java
int age = 32;
// int: data type
// age: variable name  
// 32: value stored
```

---

## Java Language Characteristics

### Static Typed Language
- **Definition**: Every variable must have a defined data type
- **Requirement**: You must specify the data type when declaring variables
- **Example**: `int number;` - must specify `int` as data type

### Strongly Typed Language  
- **Definition**: Variables have restrictions on what values they can store
- **Range Limitations**: Each data type has a specific range of values
- **Type Safety**: Cannot assign incompatible types without explicit conversion

---

## Variable Naming Conventions

### Rules and Guidelines

| Rule | Description | Example |
|------|-------------|---------|
| **Case Sensitive** | `variable` and `Variable` are different | `int a` ≠ `int A` |
| **Unicode Support** | Can contain Unicode letters and digits | `int café`, `int 变量` |
| **Valid Start Characters** | Must start with letter, underscore, or dollar sign | `_var`, `$var`, `myVar` ✓ |
| **Invalid Start** | Cannot start with digits | `9var` ✗ |
| **No Reserved Words** | Cannot use Java keywords | `int`, `class`, `new` ✗ |

### Naming Conventions

| Type | Convention | Example |
|------|------------|---------|
| **Single Word** | All lowercase | `int jaipur;` |
| **Multiple Words** | camelCase | `int jaipurCity;` |
| **Constants** | ALL_UPPERCASE | `static final int MAX_SIZE;` |

### Valid Examples
```java
int _age;        // ✓ Starts with underscore
int $salary;     // ✓ Starts with dollar sign
int userName;    // ✓ CamelCase for multiple words
```

### Invalid Examples
```java
int 9name;       // ✗ Starts with digit
int class;       // ✗ Reserved keyword
int user-name;   // ✗ Contains hyphen
```

---

## Primitive Data Types

Java has **8 primitive data types** divided into categories:

### 1. Character Type

#### char
- **Size**: 2 bytes (16 bits)
- **Range**: 0 to 65,535
- **Purpose**: Stores single character or ASCII value
- **Default**: '\u0000' (null character)

```java
char letter = 'A';
char asciiValue = 65;  // Also prints 'A'
char smallA = 97;      // Prints 'a'
```

### 2. Integer Types

#### byte
- **Size**: 1 byte (8 bits)
- **Range**: -128 to 127
- **Signed**: Yes (uses two's complement)
- **Default**: 0

```java
byte age = 25;
byte maxByte = 127;
```

#### short
- **Size**: 2 bytes (16 bits)  
- **Range**: -32,768 to 32,767
- **Signed**: Yes
- **Default**: 0

```java
short population = 30000;
```

#### int
- **Size**: 4 bytes (32 bits)
- **Range**: -2³¹ to 2³¹-1
- **Signed**: Yes
- **Default**: 0

```java
int salary = 50000;
```

#### long
- **Size**: 8 bytes (64 bits)
- **Range**: -2⁶³ to 2⁶³-1  
- **Signed**: Yes
- **Default**: 0L
- **Suffix**: Use 'L' or 'l'

```java
long bigNumber = 100L;
long veryBig = 9876543210L;
```

### 3. Floating Point Types

#### float
- **Size**: 4 bytes (32 bits)
- **Standard**: IEEE 754
- **Precision**: ~7 decimal digits
- **Default**: 0.0f
- **Suffix**: Use 'F' or 'f'

```java
float price = 63.20F;
float rate = 0.3F;
```

#### double
- **Size**: 8 bytes (64 bits)
- **Standard**: IEEE 754  
- **Precision**: ~15 decimal digits
- **Default**: 0.0d
- **Suffix**: Use 'D' or 'd' (optional)

```java
double precise = 63.20D;
double pi = 3.14159265359;
```

### 4. Boolean Type

#### boolean
- **Values**: `true` or `false` only
- **Size**: Typically 1 bit
- **Default**: false

```java
boolean isActive = true;
boolean isComplete = false;
```

### Primitive Types Summary Table

| Type | Size | Range | Default | Suffix |
|------|------|-------|---------|--------|
| char | 2 bytes | 0 to 65,535 | '\u0000' | - |
| byte | 1 byte | -128 to 127 | 0 | - |
| short | 2 bytes | -32,768 to 32,767 | 0 | - |
| int | 4 bytes | -2³¹ to 2³¹-1 | 0 | - |
| long | 8 bytes | -2⁶³ to 2⁶³-1 | 0L | L |
| float | 4 bytes | IEEE 754 | 0.0f | F |
| double | 8 bytes | IEEE 754 | 0.0d | D |
| boolean | 1 bit | true/false | false | - |

---

## Type Conversion

### 1. Automatic Conversion (Widening)

**Direction**: Smaller → Larger data type
**Requirement**: No explicit casting needed

```java
// Automatic conversion examples
byte b = 10;
int i = b;        // byte → int (automatic)

int num = 100;
long bigNum = num; // int → long (automatic)

float f = 3.14F;
double d = f;      // float → double (automatic)
```

**Conversion Hierarchy:**
```
byte → short → int → long → float → double
       char → int
```

### 2. Explicit Conversion (Narrowing)

**Direction**: Larger → Smaller data type
**Requirement**: Manual casting required
**Risk**: Potential data loss

```java
// Explicit conversion examples
int i = 128;
byte b = (byte) i;  // int → byte (explicit)
// Result: b = -128 (overflow due to range limit)

double d = 9.78;
int i = (int) d;    // double → int (explicit)
// Result: i = 9 (decimal part lost)
```

### 3. Promotion During Expressions

#### Automatic Promotion Rules:
1. **byte, short** → **int** when used in expressions
2. If any operand is **long** → entire expression becomes **long**
3. If any operand is **float** → entire expression becomes **float**  
4. If any operand is **double** → entire expression becomes **double**

```java
// Promotion examples
byte a = 127;
byte b = 1;
// byte sum = a + b;  // ✗ Error: expression promoted to int
int sum = a + b;      // ✓ Correct: result is int (128)

// Mixed type promotion
int x = 10;
double y = 20.5;
// int result = x + y;  // ✗ Error: expression promoted to double
double result = x + y;  // ✓ Correct: result is 30.5
```

### Two's Complement Representation

**Example** (4-bit for simplicity):
- **Positive 3**: `0011`
- **Negative 3**: `1101` (first complement of 0011 = 1100, then +1 = 1101)

**Verification**: `0011 + 1101 = 10000` → `0000` (ignoring overflow bit)

---

## Variable Categories

### 1. Member Variables (Instance Variables)

**Location**: Declared inside class, outside methods
**Scope**: Throughout the class
**Default Values**: Automatically assigned
**Memory**: Each object gets its own copy

```java
public class Employee {
    int salary;        // Member variable - default value: 0
    String name;       // Member variable - default value: null
    
    public void displayInfo() {
        System.out.println("Salary: " + salary); // Prints: 0
    }
}

// Usage
Employee emp1 = new Employee();
Employee emp2 = new Employee();
// Each object has its own copy of salary and name
```

### 2. Local Variables

**Location**: Declared inside methods, constructors, or blocks
**Scope**: Only within the declaring block
**Default Values**: NOT automatically assigned
**Lifecycle**: Created when block is entered, destroyed when exited

```java
public void calculateBonus() {
    int bonus = 1000;     // Local variable - must initialize
    // int tax;           // ✗ Error if used without initialization
    int tax = 200;        // ✓ Correct
    
    System.out.println(bonus + tax);
} // bonus and tax destroyed here
```

### 3. Static Variables (Class Variables)

**Location**: Declared with `static` keyword
**Scope**: Belongs to class, not instances
**Memory**: Only one copy exists for all objects
**Access**: Through class name

```java
public class Student {
    static int totalStudents = 0;  // Static variable
    String name;                   // Instance variable
    
    public Student(String name) {
        this.name = name;
        totalStudents++;           // Shared among all objects
    }
}

// Usage
Student s1 = new Student("Alice");
Student s2 = new Student("Bob");
System.out.println(Student.totalStudents); // Prints: 2
```

### 4. Method Parameters

**Location**: Defined in method signature
**Scope**: Only within the method
**Values**: Passed by caller

```java
public int calculateSum(int a, int b) {  // a, b are parameters
    return a + b;
}

// Usage
int result = calculateSum(5, 10);  // 5 and 10 are arguments
```

### 5. Constructor Parameters

**Location**: Defined in constructor signature
**Purpose**: Initialize object state

```java
public class Car {
    String model;
    int year;
    
    // Constructor with parameters
    public Car(String model, int year) {  // Parameters
        this.model = model;               // Initialize instance variables
        this.year = year;
    }
}

// Usage
Car myCar = new Car("Toyota", 2023);  // Arguments passed to constructor
```

### Variable Categories Summary

| Variable Type | Location | Scope | Default Values | Memory |
|---------------|----------|-------|----------------|---------|
| **Member** | Inside class | Class-wide | Yes | Per object |
| **Local** | Inside method/block | Block only | No | Temporary |
| **Static** | Class level | Class-wide | Yes | Shared |
| **Parameter** | Method signature | Method only | From caller | Temporary |

---

## Interview Questions

### Q1: Is Java a static typed or dynamic typed language?
**Answer**: Java is a **static typed** language. Every variable must have a declared data type at compile time.

### Q2: What is the difference between static and strong typing?
**Answer**: 
- **Static Typing**: Variables must have declared types (compile-time)
- **Strong Typing**: Variables have restrictions on what values they can store (runtime safety)

### Q3: What happens when you assign 128 to a byte variable?
**Answer**: 
```java
byte b = (byte) 128;  // Result: b = -128
```
Due to byte range (-128 to 127), 128 wraps around to -128.

### Q4: Why shouldn't you use float/double for currency calculations?
**Answer**: 
```java
float a = 0.3F;
float b = 0.1F;
float result = a - b;  // Expected: 0.2, Actual: 0.2000002
```
Use `BigDecimal` for precise decimal calculations.

### Q5: What is promotion in expressions?
**Answer**: When performing operations, smaller data types are automatically promoted to larger ones to prevent data loss.

### Q6: What's the difference between member and local variables?
**Answer**:
- **Member**: Class-level, default values assigned, per-object copy
- **Local**: Method-level, no default values, temporary scope

### Q7: How many primitive data types does Java have?
**Answer**: **8 primitive types**: byte, short, int, long, float, double, char, boolean

### Q8: What is two's complement?
**Answer**: A method to represent negative numbers in binary. To get negative of a number: invert all bits and add 1.

---

## Best Practices

### 1. Naming Conventions
```java
// ✓ Good
int studentAge;
String firstName;
final int MAX_SIZE = 100;

// ✗ Avoid
int a;
String n;
int studentage;
```

### 2. Variable Initialization
```java
// ✓ Always initialize local variables
int count = 0;
String name = "";

// ✗ Don't use uninitialized variables
int count;
// System.out.println(count);  // Compilation error
```

### 3. Use Appropriate Data Types
```java
// ✓ Choose based on requirements
byte age = 25;           // Age never exceeds 127
long phoneNumber = 9876543210L;  // Large numbers need long

// ✗ Unnecessarily large types
long age = 25L;          // Wasteful for age
int phoneNumber = 12345; // May cause overflow
```

### 4. Constants Declaration
```java
// ✓ Use static final for constants
public static final int MAX_ATTEMPTS = 3;
public static final String APP_NAME = "MyApp";

// ✗ Magic numbers
if (attempts > 3) { ... }  // What does 3 represent?
```

### 5. Precision-Critical Calculations
```java
// ✓ Use BigDecimal for money
BigDecimal price = new BigDecimal("19.99");
BigDecimal tax = new BigDecimal("0.08");

// ✗ Don't use float/double for currency
double price = 19.99;
double tax = 0.08;
```

---

## Common Pitfalls

### 1. Floating Point Precision Issues
```java
// ⚠️ Problem
float a = 0.3F;
float b = 0.1F;
System.out.println(a - b);  // Prints: 0.20000005 (not 0.2)

// ✓ Solution
BigDecimal a = new BigDecimal("0.3");
BigDecimal b = new BigDecimal("0.1");
System.out.println(a.subtract(b));  // Prints: 0.2
```

### 2. Integer Overflow
```java
// ⚠️ Problem
byte b = 127;
b++;                      // b becomes -128 (overflow)

// ✓ Solution - Use appropriate data type
int i = 127;
i++;                      // i becomes 128 (no overflow)
```

### 3. Uninitialized Local Variables
```java
// ⚠️ Problem
public void method() {
    int count;
    // System.out.println(count);  // Compilation error
}

// ✓ Solution
public void method() {
    int count = 0;           // Always initialize
    System.out.println(count);  // Works fine
}
```

### 4. Mixing Data Types Without Understanding Promotion
```java
// ⚠️ Problem
byte a = 10;
byte b = 20;
// byte result = a + b;     // Compilation error (promoted to int)

// ✓ Solution
byte a = 10;
byte b = 20;
int result = a + b;        // Correct
// OR
byte result = (byte)(a + b);  // Explicit casting
```

### 5. Incorrect Static Variable Access
```java
public class Counter {
    static int count = 0;
}

// ⚠️ Problem
Counter c1 = new Counter();
// c1.count++;              // Works but misleading

// ✓ Solution
Counter.count++;            // Clear that it's a class variable
```

### 6. Default Values Confusion
```java
public class Example {
    int memberVar;           // Default: 0
    
    public void method() {
        int localVar;        // No default value
        System.out.println(memberVar);   // Prints: 0
        // System.out.println(localVar); // Compilation error
    }
}
```

---

## Real-World Use Cases

### 1. Banking Application
```java
public class BankAccount {
    private static int totalAccounts = 0;     // Static: shared across all accounts
    private String accountNumber;             // Instance: unique per account
    private BigDecimal balance;               // Precise decimal for money
    
    public BankAccount(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = BigDecimal.ZERO;
        totalAccounts++;                      // Increment shared counter
    }
    
    public void deposit(BigDecimal amount) {
        BigDecimal newBalance = balance.add(amount);  // Precise arithmetic
        this.balance = newBalance;
    }
}
```

### 2. Game Development
```java
public class Player {
    private static final int MAX_HEALTH = 100;  // Constant
    private byte level;                         // Small range (1-255)
    private int experience;                     // Larger range needed
    private float positionX, positionY;         // Coordinates
    private boolean isAlive;                    // Simple flag
    
    public void levelUp() {
        if (experience >= getRequiredExp()) {
            level++;                            // Type promotion handled automatically
            experience = 0;
        }
    }
}
```

### 3. Configuration Management
```java
public class AppConfig {
    // Constants for configuration
    public static final String APP_VERSION = "1.0.0";
    public static final int MAX_CONNECTIONS = 1000;
    public static final long SESSION_TIMEOUT = 3600000L;  // 1 hour in milliseconds
    
    // Instance variables for user-specific settings
    private boolean darkMode;
    private String language;
    private int fontSize;
}
```

---

## Memory Layout Diagram

```
┌─────────────────────────────────────────────────────┐
│                    JVM Memory                       │
├─────────────────────────────────────────────────────┤
│  Method Area (Static Variables)                     │
│  ┌─────────────────────────────────────────────────┐│
│  │ static int totalStudents = 0;                   ││
│  │ static final String APP_NAME = "MyApp";         ││
│  └─────────────────────────────────────────────────┘│
├─────────────────────────────────────────────────────┤
│  Heap Memory (Objects & Instance Variables)         │
│  ┌─────────────────────────────────────────────────┐│
│  │ Student obj1: {name="Alice", age=20}            ││
│  │ Student obj2: {name="Bob", age=22}              ││
│  └─────────────────────────────────────────────────┘│
├─────────────────────────────────────────────────────┤
│  Stack Memory (Local Variables & Parameters)        │
│  ┌─────────────────────────────────────────────────┐│
│  │ method1() {                                     ││
│  │   int localVar = 10;                            ││
│  │   String temp = "hello";                        ││
│  │ }                                               ││
│  └─────────────────────────────────────────────────┘│
└─────────────────────────────────────────────────────┘
```

---

*This document provides comprehensive coverage of Java variables as explained in the video. Use it for both learning and interview preparation.*