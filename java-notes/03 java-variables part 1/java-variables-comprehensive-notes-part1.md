# Java Variables - Part 1: Comprehensive Notes

## 📚 Learning Objectives

After reading these notes, you should be able to:

- [ ] Define what a variable is and how to declare variables in Java
- [ ] Understand Java as a static and strongly typed language
- [ ] Follow proper variable naming conventions
- [ ] Understand all 8 primitive data types and their characteristics
- [ ] Perform type conversions (widening, narrowing, and promotion)
- [ ] Distinguish between different kinds of variables (member, static, local, parameter)
- [ ] Apply signed two's complement representation for integer types
- [ ] Handle floating-point precision issues
- [ ] Use appropriate data types for different scenarios

---

## 🔑 Key Concepts & Definitions

### What is a Variable?
**Variable**: A container that holds a value.
```java
// Syntax: dataType variableName = value;
int age = 25;  // 'age' is a container holding the value 25
```

### Java Language Characteristics

**Static Typed Language**: 
- Every variable must have a declared data type
- Data type determines what kind of data the variable can store

**Strongly Typed Language**: 
- Each data type has a specific range of values
- Values must fit within the defined range limits

---

## 📝 Variable Naming Conventions

### Rules (Must Follow)
1. **Case Sensitive**: `variable` ≠ `Variable` ≠ `VARIABLE`
2. **Valid Characters**: Unicode letters, digits, `$`, `_`
3. **Start with**: Letter, `$`, or `_` (NOT with a digit)
4. **Cannot be**: Java reserved words (int, class, for, while, etc.)

### Best Practices (Should Follow)
```java
// Single word - all lowercase
int age;
String city;

// Multiple words - camelCase
int studentAge;
String cityName;

// Constants - ALL_UPPERCASE
static final int MAX_SIZE = 100;
static final String DEFAULT_NAME = "Unknown";
```

### Valid Examples
```java
int age;           // ✓ Valid
int _count;        // ✓ Valid
int $price;        // ✓ Valid
int user2;         // ✓ Valid
int userName;      // ✓ Valid
```

### Invalid Examples
```java
int 2user;         // ✗ Invalid - starts with digit
int class;         // ✗ Invalid - reserved word
int user-name;     // ✗ Invalid - hyphen not allowed
```

---

## 🎯 Primitive Data Types (8 Types)

### 1. Character Type

| Property | Value |
|----------|-------|
| **Type** | `char` |
| **Size** | 2 bytes (16 bits) |
| **Range** | 0 to 65,535 |
| **Default** | `'\u0000'` (null character) |
| **Purpose** | Store single Unicode character |

```java
char letter = 'A';           // Character literal
char asciiValue = 65;        // ASCII value (prints 'A')
char unicode = '\u0041';     // Unicode (prints 'A')
```

### 2. Integer Types

#### Byte
| Property | Value |
|----------|-------|
| **Type** | `byte` |
| **Size** | 1 byte (8 bits) |
| **Range** | -128 to 127 |
| **Default** | 0 |

#### Short
| Property | Value |
|----------|-------|
| **Type** | `short` |
| **Size** | 2 bytes (16 bits) |
| **Range** | -32,768 to 32,767 |
| **Default** | 0 |

#### Int
| Property | Value |
|----------|-------|
| **Type** | `int` |
| **Size** | 4 bytes (32 bits) |
| **Range** | -2,147,483,648 to 2,147,483,647 |
| **Default** | 0 |

#### Long
| Property | Value |
|----------|-------|
| **Type** | `long` |
| **Size** | 8 bytes (64 bits) |
| **Range** | -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807 |
| **Default** | 0L |

```java
byte age = 25;
short year = 2024;
int population = 1000000;
long distance = 123456789L;  // Note: 'L' suffix for long
```

### 3. Floating-Point Types

#### Float
| Property | Value |
|----------|-------|
| **Type** | `float` |
| **Size** | 4 bytes (32 bits) |
| **Standard** | IEEE 754 |
| **Precision** | ~7 decimal digits |
| **Default** | 0.0f |

#### Double
| Property | Value |
|----------|-------|
| **Type** | `double` |
| **Size** | 8 bytes (64 bits) |
| **Standard** | IEEE 754 |
| **Precision** | ~15 decimal digits |
| **Default** | 0.0d |

```java
float price = 63.20f;        // Note: 'f' suffix for float
double distance = 63.20d;    // Note: 'd' suffix for double (optional)
```

**⚠️ Floating-Point Precision Issue:**
```java
float var1 = 0.3f;
float var2 = 0.1f;
float result = var1 - var2;
System.out.println(result);  // Output: 0.20000002 (not 0.2!)
```

### 4. Boolean Type

| Property | Value |
|----------|-------|
| **Type** | `boolean` |
| **Size** | 1 bit (conceptually) |
| **Values** | `true` or `false` |
| **Default** | `false` |

```java
boolean isActive = true;
boolean isComplete = false;
```

---

## 🔄 Type Conversions

### 1. Automatic Conversion (Widening)
Smaller data type → Larger data type (automatic)

```
byte → short → int → long → float → double
       char → int → long → float → double
```

```java
byte b = 10;
int i = b;          // Automatic conversion (byte to int)
long l = i;         // Automatic conversion (int to long)
double d = l;       // Automatic conversion (long to double)
```

### 2. Explicit Conversion (Narrowing)
Larger data type → Smaller data type (manual casting required)

```java
int i = 128;
byte b = (byte) i;  // Explicit casting required
System.out.println(b);  // Output: -128 (overflow!)
```

**Overflow Example:**
```java
int value = 128;
byte result = (byte) value;
// 128 is outside byte range (-128 to 127)
// Result: -128 (wraps around)
```

### 3. Promotion During Expressions

**Rule 1**: `byte` and `short` are promoted to `int` in expressions
```java
byte a = 127;
byte b = 1;
// byte sum = a + b;     // ✗ Compilation error
int sum = a + b;         // ✓ Correct (promoted to int)
// or
byte sum = (byte)(a + b); // ✓ Explicit casting
```

**Rule 2**: If any operand is of higher type, entire expression is promoted
```java
int i = 10;
double d = 20.5;
// int result = i + d;   // ✗ Error - expression is double
double result = i + d;   // ✓ Correct
// or
int result = (int)(i + d); // ✓ Explicit casting
```

---

## 🧮 Signed Two's Complement (Deep Dive)

### Understanding Negative Numbers
For `byte` (8 bits): Range is -128 to 127

**Representation of +3:** `00000011`
**Representation of -3:** 
1. Start with +3: `00000011`
2. First complement (flip bits): `11111100`
3. Add 1: `11111101`

**Verification (Addition):**
```
  00000011  (+3)
+ 11111101  (-3)
-----------
 100000000  (overflow bit ignored = 00000000 = 0) ✓
```

### Range Calculation
For n-bit signed integer:
- **Range**: -2^(n-1) to 2^(n-1) - 1
- **Example (8-bit byte)**: -2^7 to 2^7 - 1 = -128 to 127

---

## 📂 Types of Variables

### 1. Member Variables (Instance Variables)
```java
public class Employee {
    int age;        // Member variable
    String name;    // Member variable
    
    // Each object has its own copy
}

Employee emp1 = new Employee();  // emp1 has its own age, name
Employee emp2 = new Employee();  // emp2 has its own age, name
```

### 2. Static Variables (Class Variables)
```java
public class Employee {
    static int companyId = 100;  // Static variable - shared by all objects
    
    // Access via class name: Employee.companyId
}
```

### 3. Local Variables
```java
public void calculateSalary() {
    int bonus = 1000;  // Local variable - exists only within this method
    // Must be initialized before use
}
```

### 4. Method Parameters
```java
public int add(int a, int b) {  // a, b are method parameters
    return a + b;
}
```

### 5. Constructor Parameters
```java
public class Employee {
    int age;
    
    public Employee(int empAge) {  // empAge is constructor parameter
        this.age = empAge;
    }
}
```

---

## 🎯 Default Values

| Variable Type | Default Value | Scope |
|---------------|---------------|-------|
| **Member Variables** | Type-specific defaults | Class level |
| **Static Variables** | Type-specific defaults | Class level |
| **Local Variables** | No default (must initialize) | Method level |

### Type-Specific Defaults:
```java
public class DefaultValues {
    // All have default values (member variables)
    byte b;          // 0
    short s;         // 0
    int i;           // 0
    long l;          // 0L
    float f;         // 0.0f
    double d;        // 0.0d
    char c;          // '\u0000'
    boolean bool;    // false
    
    public void method() {
        int local;   // No default - compilation error if used uninitialized
        // System.out.println(local);  // ✗ Error
    }
}
```

---

## ❓ Common Interview Questions

### Q1: What is the difference between static and strongly typed languages?
**Answer**: 
- **Static typed**: Variable data types are determined at compile time
- **Strongly typed**: Each data type has strict rules about what values it can hold

### Q2: Why is the range of byte -128 to 127 and not 0 to 255?
**Answer**: Because `byte` is a signed data type using two's complement representation. The most significant bit represents the sign (0 = positive, 1 = negative).

### Q3: What happens during this operation: `byte a = 127; byte b = 1; byte c = a + b;`?
**Answer**: Compilation error. During expression evaluation, `byte` values are promoted to `int`. The result (128) must be explicitly cast back to `byte`, which will overflow to -128.

### Q4: Why shouldn't we use float/double for currency calculations?
**Answer**: Due to IEEE 754 representation, floating-point numbers cannot precisely represent all decimal values, leading to rounding errors. Use `BigDecimal` for precise decimal arithmetic.

### Q5: What's the difference between member and static variables?
**Answer**: 
- **Member variables**: Each object instance has its own copy
- **Static variables**: One copy shared among all instances of the class

---

## 🛠 Hands-on Exercises

### Exercise 1: Variable Declaration Practice
```java
// Declare variables for the following scenarios:
// 1. Store a person's age (0-120)
// 2. Store world population
// 3. Store a product price with decimals
// 4. Store a single character grade
// 5. Store whether a student passed or failed

// Your answers:
byte age = 25;
long worldPopulation = 8000000000L;
double price = 99.99;
char grade = 'A';
boolean passed = true;
```

### Exercise 2: Type Conversion Challenge
```java
public class ConversionPractice {
    public static void main(String[] args) {
        // Fix the compilation errors:
        byte b = 200;           // Error: value too large
        int i = 3.14;           // Error: precision loss
        char c = -1;            // Error: negative value
        
        // Solutions:
        byte b = (byte) 200;    // Will overflow to -56
        int i = (int) 3.14;     // Will truncate to 3
        char c = '\u0000';      // Use proper char literal
    }
}
```

### Exercise 3: Default Values Quiz
```java
public class DefaultTest {
    static int staticVar;
    int memberVar;
    
    public void testMethod() {
        int localVar;
        
        System.out.println(staticVar);  // What prints?
        System.out.println(memberVar);  // What prints?
        System.out.println(localVar);   // Compilation error or value?
    }
}
```

---

## 🌍 Real-world Use Cases

### 1. Database Field Mapping
```java
public class Customer {
    long customerId;      // Large unique identifier
    byte age;            // Age (0-127 sufficient)
    short yearJoined;    // Year (2024, etc.)
    boolean isActive;    // Status flag
    double creditLimit;  // Monetary value
}
```

### 2. Game Development
```java
public class GameCharacter {
    byte level;          // Level 1-100
    short health;        // Health points
    int experience;      // Experience points
    boolean isAlive;     // Status
    char difficulty;     // 'E', 'M', 'H'
}
```

### 3. IoT Sensor Data
```java
public class SensorReading {
    byte sensorId;       // Sensor identifier
    float temperature;   // Temperature reading
    short humidity;      // Humidity percentage
    boolean isOnline;    // Sensor status
    long timestamp;      // Unix timestamp
}
```

---

## ⚠️ Best Practices & Common Pitfalls

### Best Practices

1. **Choose Appropriate Data Types**
   ```java
   // Good: Use byte for small ranges
   byte percentage = 85;  // 0-100 range
   
   // Bad: Wasting memory
   int percentage = 85;   // Unnecessary 4 bytes
   ```

2. **Initialize Local Variables**
   ```java
   public void calculate() {
       int result = 0;  // Always initialize locals
       // ... calculations
   }
   ```

3. **Use Meaningful Names**
   ```java
   // Good
   int studentAge = 20;
   boolean isAccountActive = true;
   
   // Bad
   int x = 20;
   boolean flag = true;
   ```

### Common Pitfalls

1. **Overflow Errors**
   ```java
   byte max = 127;
   max++;  // Becomes -128 (overflow)
   ```

2. **Floating-Point Precision**
   ```java
   // Avoid for financial calculations
   double price = 0.1 + 0.2;  // Not exactly 0.3
   
   // Use BigDecimal instead
   BigDecimal price = new BigDecimal("0.1").add(new BigDecimal("0.2"));
   ```

3. **Uninitialized Local Variables**
   ```java
   public void method() {
       int count;
       System.out.println(count);  // ✗ Compilation error
   }
   ```

### Debugging Tips

1. **Check for Overflow**
   ```java
   // Add bounds checking
   if (value > Byte.MAX_VALUE) {
       throw new IllegalArgumentException("Value too large for byte");
   }
   ```

2. **Use Wrapper Classes for Debugging**
   ```java
   Integer.MAX_VALUE  // Check maximum values
   Double.isNaN(value)  // Check for NaN
   ```

---

## 🆚 Comparisons with Related Concepts

### Primitive vs Reference Types
| Aspect | Primitive | Reference |
|--------|-----------|-----------|
| **Memory** | Stack | Heap (object), Stack (reference) |
| **Default** | Type-specific | `null` |
| **Comparison** | `==` compares values | `==` compares references |
| **Performance** | Faster | Slower (indirection) |

### Static vs Member Variables
| Aspect | Static | Member |
|--------|--------|--------|
| **Copies** | One per class | One per instance |
| **Access** | ClassName.variable | object.variable |
| **Memory** | Method area | Heap |
| **Initialization** | Class loading | Object creation |

---

## 🧠 Memory Hooks & Mnemonics

### Data Type Sizes
**"Byte Short Int Long"** = **"1 2 4 8"** bytes
- **B**yte = **1** byte
- **S**hort = **2** bytes  
- **I**nt = **4** bytes
- **L**ong = **8** bytes

### Floating Point Precision
**"Float Fails, Double's Decent"**
- Float: ~7 digits precision
- Double: ~15 digits precision

### Signed Ranges Formula
**"Half negative, half positive minus one"**
- For n-bit signed: -2^(n-1) to 2^(n-1) - 1
- Remember: One less positive (zero takes a spot)

### Variable Types Memory
**"Local Lives in Method, Member lives in Object, Static Stays with Class"**

---

## 📋 Quick Reference Cheat Sheet

### Primitive Types Quick Table
| Type | Size | Range | Default | Suffix |
|------|------|-------|---------|--------|
| `byte` | 1 byte | -128 to 127 | 0 | - |
| `short` | 2 bytes | -32,768 to 32,767 | 0 | - |
| `int` | 4 bytes | ~-2B to 2B | 0 | - |
| `long` | 8 bytes | ~-9E18 to 9E18 | 0L | L |
| `float` | 4 bytes | IEEE 754 | 0.0f | f |
| `double` | 8 bytes | IEEE 754 | 0.0d | d |
| `char` | 2 bytes | 0 to 65,535 | '\u0000' | - |
| `boolean` | 1 bit | true/false | false | - |

### Conversion Rules
```
WIDENING (Automatic):
byte → short → int → long → float → double
       char → int → long → float → double

NARROWING (Explicit):
double → float → long → int → short → byte
double → float → long → int → char
```

### Variable Scope Quick Guide
```java
public class Example {
    static int staticVar;    // Class scope - shared
    int memberVar;          // Instance scope - per object
    
    public void method(int param) {  // Parameter scope
        int local = 0;      // Method scope - must initialize
    }
}
```

### Must Remember
- ✅ Java is static + strongly typed
- ✅ Local variables need initialization
- ✅ Expressions promote byte/short to int
- ✅ Use L for long, f for float
- ✅ Avoid float/double for money
- ✅ Static variables accessed via class name

---

*This completes Part 1 of Java Variables. Part 2 will cover non-primitive types, wrapper classes, and advanced concepts.*