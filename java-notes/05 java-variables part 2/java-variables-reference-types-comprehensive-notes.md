# Java Variables Part 2: Reference Data Types, Wrapper Classes & Constants

## 📚 Learning Objectives

After studying these notes, you should be able to:

- ✅ Understand the difference between primitive and reference data types
- ✅ Explain how object references work in memory (heap vs stack)
- ✅ Demonstrate string immutability and string constant pool concepts
- ✅ Implement and use wrapper classes for primitive types
- ✅ Apply autoboxing and unboxing in practical scenarios
- ✅ Create and use constant variables with static final
- ✅ Understand why Java uses "pass by value" for everything
- ✅ Work with arrays, interfaces, and classes as reference types

---

## 🔑 Key Concepts & Definitions

### **Reference Data Types**
Non-primitive data types that store references (addresses) to memory locations in the heap rather than actual values.

### **Four Main Reference Data Types:**
1. **Class** - User-defined objects
2. **String** - Immutable character sequences
3. **Interface** - Contract definitions for classes
4. **Array** - Collections of elements of the same type

### **String Literal**
A string value enclosed in double quotes that gets stored in the String Constant Pool.

### **String Constant Pool**
A special memory area within the heap where string literals are stored to optimize memory usage.

### **Wrapper Classes**
Reference type equivalents for each primitive data type, providing object-oriented capabilities.

### **Autoboxing/Unboxing**
- **Autoboxing**: Automatic conversion from primitive to wrapper class
- **Unboxing**: Automatic conversion from wrapper class to primitive

---

## 📋 Step-by-Step Explanations

### 1. Understanding Reference Data Types with Classes

#### Memory Allocation Process:
```
Step 1: Define a class
Step 2: Create object using 'new' keyword
Step 3: Memory allocated in heap
Step 4: Reference variable stores memory address
Step 5: Access object through reference
```

#### Why Everything is "Pass by Value" in Java:
```
1. Primitive types: Actual value is copied
2. Reference types: Reference (address) is copied
3. No pointers like C/C++
4. But reference copying achieves similar functionality
```

### 2. String Immutability and String Pool

#### String Creation Process:
```
Step 1: Check if literal exists in String Constant Pool
Step 2: If exists, point to existing literal
Step 3: If not exists, create new literal in pool
Step 4: For 'new String()', always create new object in heap
```

#### String Comparison Rules:
```
.equals() → Compares content/value
== → Compares memory references
```

### 3. Wrapper Classes and Their Purpose

#### Two Main Reasons for Wrapper Classes:
1. **Reference Capability**: Pass objects to methods for modification
2. **Collection Compatibility**: Java collections only work with objects

#### Autoboxing/Unboxing Process:
```
Autoboxing: int → Integer (primitive to wrapper)
Unboxing: Integer → int (wrapper to primitive)
```

---

## 💻 Code Examples

### Class Reference Example
```java
// Employee class definition
class Employee {
    int employeeId;
    
    public int getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}

// Creating and using objects
public class Main {
    public static void main(String[] args) {
        // Object creation - memory allocated in heap
        Employee emp = new Employee();
        emp.setEmployeeId(10);
        
        // Pass by value of reference
        modifyEmployee(emp);
        System.out.println(emp.getEmployeeId()); // Output: 20
    }
    
    static void modifyEmployee(Employee employee) {
        employee.setEmployeeId(20); // Changes original object
    }
}
```

### String Examples
```java
public class StringExample {
    public static void main(String[] args) {
        // String literals - stored in String Constant Pool
        String s1 = "Hello";
        String s2 = "Hello";
        
        // New string object - stored in heap
        String s3 = new String("Hello");
        
        // Comparisons
        System.out.println(s1 == s2);        // true (same reference)
        System.out.println(s1.equals(s2));   // true (same content)
        System.out.println(s1 == s3);        // false (different references)
        System.out.println(s1.equals(s3));   // true (same content)
        
        // String immutability
        s1 = "Hello World";  // s1 now points to new literal
        // Original "Hello" still exists in pool
    }
}
```

### Interface Reference Example
```java
// Interface definition
interface Person {
    String profession();
}

// Implementations
class Teacher implements Person {
    @Override
    public String profession() {
        return "Teaching";
    }
}

class Engineer implements Person {
    @Override
    public String profession() {
        return "Software Engineer";
    }
}

// Usage
public class InterfaceExample {
    public static void main(String[] args) {
        // Valid - parent interface reference to child object
        Person person1 = new Teacher();
        Person person2 = new Engineer();
        
        // Valid - same type reference
        Teacher teacher = new Teacher();
        Engineer engineer = new Engineer();
        
        // Invalid - cannot instantiate interface
        // Person person3 = new Person(); // Compilation error
    }
}
```

### Array Examples
```java
public class ArrayExample {
    public static void main(String[] args) {
        // 1D Array creation methods
        int[] arr1 = new int[5];  // Creates array of size 5
        arr1[0] = 10;
        arr1[3] = 40;
        
        int[] arr2 = {30, 20, 10, 40, 50};  // Direct initialization
        
        // 2D Array
        int[][] arr2D = new int[5][4];  // 5 rows, 4 columns
        arr2D[2][2] = 20;  // Set value at row 2, column 2
        
        // Direct 2D initialization
        int[][] arr2DInit = {{1, 5, 7}, {4, 2, 3}};  // 2 rows, 3 columns
        
        System.out.println(arr2DInit[1][2]);  // Output: 3
    }
}
```

### Wrapper Classes and Autoboxing
```java
public class WrapperExample {
    public static void main(String[] args) {
        // Primitive types
        int a = 10;
        char c = 'A';
        boolean b = true;
        
        // Autoboxing - primitive to wrapper
        Integer intWrapper = a;        // int → Integer
        Character charWrapper = c;     // char → Character
        Boolean boolWrapper = b;       // boolean → Boolean
        
        // Unboxing - wrapper to primitive
        int primitiveInt = intWrapper;     // Integer → int
        char primitiveChar = charWrapper;  // Character → char
        boolean primitiveBool = boolWrapper; // Boolean → boolean
        
        // Why wrapper classes are needed
        modifyPrimitive(a);           // Original value unchanged
        modifyWrapper(intWrapper);    // Original object can be changed
        
        System.out.println("Primitive: " + a);      // 10
        System.out.println("Wrapper: " + intWrapper); // Depends on implementation
    }
    
    static void modifyPrimitive(int x) {
        x = 20;  // Only local copy changed
    }
    
    static void modifyWrapper(Integer x) {
        // Note: Integer is immutable, but concept applies to mutable objects
        x = 20;  // Creates new Integer object
    }
}
```

### Constant Variables
```java
public class ConstantExample {
    // Constant variable - static final
    public static final int EMPLOYEE_ID = 10;
    public static final String COMPANY_NAME = "TechCorp";
    
    // Non-constant static (can be changed)
    public static int counter = 0;
    
    public static void main(String[] args) {
        // Accessing constants
        System.out.println(EMPLOYEE_ID);    // 10
        System.out.println(COMPANY_NAME);   // TechCorp
        
        // This would cause compilation error:
        // EMPLOYEE_ID = 20;  // Cannot assign to final variable
        
        // This is allowed:
        counter = 5;  // Can change non-final static variable
    }
}
```

---

## 📊 Memory Diagrams

### Object Reference Diagram
```
Stack Memory          Heap Memory
+----------+         +-------------------+
| emp      |-------->| Employee Object   |
| (ref)    |         | +---------------+ |
+----------+         | | employeeId: 10| |
                     | +---------------+ |
                     +-------------------+
                     Memory Address: A1J1246987
```

### String Constant Pool Diagram
```
Heap Memory
+------------------------+
| String Constant Pool   |
| +--------------------+ |
| | "Hello"            | |<---- s1, s2 point here
| +--------------------+ |
| | "Hello World"      | |<---- s1 after reassignment
| +--------------------+ |
+------------------------+
| Regular Heap           |
| +--------------------+ |
| | String("Hello")    | |<---- s3 points here
| +--------------------+ |
+------------------------+
```

### Wrapper Class Memory Model
```
Stack Memory              Heap Memory
+----------------+       +------------------+
| primitiveInt   |       | Integer Object   |
| value: 10      |       | +-------------+  |
+----------------+       | | value: 10   |  |<---- intWrapper points here
| intWrapper     |------>| +-------------+  |
| (reference)    |       +------------------+
+----------------+
```

---

## ❓ Common Interview Questions

### Q1: What's the difference between primitive and reference data types?
**Answer**: Primitive types store actual values in stack memory (int, char, boolean, etc.), while reference types store memory addresses pointing to objects in heap memory (classes, strings, arrays, interfaces).

### Q2: Why are strings immutable in Java?
**Answer**: Strings are immutable for security, thread safety, and memory optimization. Once created in the String Constant Pool, they cannot be changed. Any modification creates a new string object.

### Q3: Explain the difference between == and .equals() for strings.
**Answer**: 
- `==` compares references (memory addresses)
- `.equals()` compares actual content/values
- For string literals, `==` may return true due to String Constant Pool

### Q4: What are wrapper classes and why do we need them?
**Answer**: Wrapper classes are object equivalents of primitive types (Integer for int, Character for char, etc.). Needed for:
1. Using primitives with collections (ArrayList, HashMap)
2. Getting object-oriented capabilities (methods, null values)
3. Passing primitives by reference-like behavior

### Q5: What is autoboxing and unboxing?
**Answer**: 
- **Autoboxing**: Automatic conversion from primitive to wrapper (int → Integer)
- **Unboxing**: Automatic conversion from wrapper to primitive (Integer → int)
- Introduced in Java 5 to simplify code

### Q6: Can you create an object of an interface?
**Answer**: No, you cannot instantiate an interface directly because interfaces only define contracts (method signatures) without implementations. You can only create objects of classes that implement the interface.

### Q7: How does Java achieve "pass by reference" behavior without actual pointers?
**Answer**: Java uses "pass by value" for everything, but for reference types, the value being passed is the reference (memory address). This allows modifications to the original object through the copied reference.

---

## 🛠️ Hands-on Exercises

### Exercise 1: Reference vs Primitive Behavior
```java
// Complete this code to demonstrate the difference
public class Exercise1 {
    public static void main(String[] args) {
        int primitive = 10;
        Integer wrapper = 10;
        
        // TODO: Create methods to modify both
        // TODO: Show which one changes the original value
    }
}
```

### Exercise 2: String Pool Investigation
```java
// Predict the output and verify
public class Exercise2 {
    public static void main(String[] args) {
        String s1 = "Java";
        String s2 = "Java";
        String s3 = new String("Java");
        String s4 = s3.intern();
        
        // TODO: Compare all combinations with == and .equals()
        // TODO: Explain each result
    }
}
```

### Exercise 3: Wrapper Class Collections
```java
// Create a collection that stores both primitive and wrapper types
import java.util.ArrayList;

public class Exercise3 {
    public static void main(String[] args) {
        // TODO: Create ArrayList of Integer
        // TODO: Add both int and Integer values
        // TODO: Demonstrate autoboxing/unboxing
    }
}
```

### Exercise 4: Interface Implementation
```java
// Create a complete interface example
public class Exercise4 {
    // TODO: Create Vehicle interface with methods
    // TODO: Implement Car and Bike classes
    // TODO: Create objects using interface references
    // TODO: Demonstrate polymorphism
}
```

---

## 🌍 Real-World Use Cases

### 1. Configuration Management
```java
public class DatabaseConfig {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    public static final int CONNECTION_TIMEOUT = 30000;
    public static final boolean ENABLE_LOGGING = true;
}
```

### 2. Collection Processing
```java
// Working with collections requires wrapper classes
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
Optional<Integer> max = numbers.stream()
    .max(Integer::compareTo);
```

### 3. API Response Objects
```java
public class ApiResponse {
    private String message;
    private Integer statusCode;  // Wrapper allows null values
    private List<String> errors;
    
    // Constructor, getters, setters
}
```

### 4. Caching with String Pool
```java
// String literals are automatically cached
public class CacheExample {
    private static final String CACHE_KEY = "user_data";  // Reused across application
    
    public String getCacheKey(String userId) {
        return CACHE_KEY + "_" + userId;  // New string created
    }
}
```

---

## ⚠️ Best Practices & Common Pitfalls

### **Best Practices**

1. **Use String literals when possible** for memory efficiency
2. **Prefer wrapper classes in collections** rather than primitive arrays
3. **Use constants (static final)** for unchanging values
4. **Be mindful of string concatenation** in loops (use StringBuilder)
5. **Understand autoboxing overhead** in performance-critical code

### **Common Pitfalls**

1. **String comparison with ==**
   ```java
   // WRONG
   if (str1 == str2) { ... }
   
   // CORRECT
   if (str1.equals(str2)) { ... }
   ```

2. **Null pointer with autoboxing**
   ```java
   Integer wrapper = null;
   int primitive = wrapper;  // NullPointerException!
   ```

3. **Thinking you can modify string literals**
   ```java
   String str = "Hello";
   str.toUpperCase();  // Creates new string, doesn't modify original
   str = str.toUpperCase();  // Correct way
   ```

4. **Interface instantiation**
   ```java
   // WRONG
   List list = new List();  // Compilation error
   
   // CORRECT
   List list = new ArrayList();
   ```

5. **Performance issues with excessive autoboxing**
   ```java
   // INEFFICIENT
   List<Integer> list = new ArrayList<>();
   for (int i = 0; i < 1000000; i++) {
       list.add(i);  // Autoboxing creates Integer objects
   }
   ```

### **Debugging Tips**

1. **Use `.intern()` method** to understand string pool behavior
2. **Print object references** to see memory addresses
3. **Use debugger** to visualize heap vs stack memory
4. **Be explicit about autoboxing** in complex expressions

---

## 🔄 Comparisons with Related Concepts

### Primitive vs Reference Data Types
| Aspect | Primitive | Reference |
|--------|-----------|-----------|
| **Memory Location** | Stack | Heap |
| **Storage** | Actual value | Memory address |
| **Default Value** | 0, false, '\0' | null |
| **Method Parameters** | Value copied | Reference copied |
| **Performance** | Faster | Slower (object overhead) |
| **Null Assignment** | Not possible | Possible |

### String Creation Methods
| Method | Memory Location | Pool Benefit | Example |
|--------|----------------|--------------|---------|
| **String Literal** | String Constant Pool | Yes | `String s = "Hello";` |
| **new String()** | Heap | No | `String s = new String("Hello");` |
| **StringBuilder** | Heap | No | `new StringBuilder().append("Hello");` |

### Wrapper vs Primitive Performance
| Operation | Primitive | Wrapper | Performance Impact |
|-----------|-----------|---------|-------------------|
| **Arithmetic** | Direct | Autoboxing overhead | Primitive 10x faster |
| **Collections** | Not supported | Native support | Wrapper required |
| **Null handling** | Not possible | Supported | Wrapper advantage |
| **Memory usage** | 4-8 bytes | Object overhead + value | Primitive more efficient |

---

## 🧠 Memory Hooks & Mnemonics

### **Reference Data Types Mnemonic: "CASI"**
- **C**lass
- **A**rray  
- **S**tring
- **I**nterface

### **String Comparison Memory Hook**
- **==** sounds like "equals equals" → **E**quals **A**ddresses
- **.equals()** is longer → checks **content** (longer comparison)

### **Autoboxing Direction Memory Hook**
- **Auto-BOX-ing** → putting primitive **INTO** a box (wrapper)
- **UN-box-ing** → taking primitive **OUT OF** the box

### **String Pool Visual Memory**
```
Think of String Pool as a "LIBRARY"
- Same books (literals) are shared
- No duplicate copies
- Everyone references the same book
- New books only added if not existing
```

### **Wrapper Classes Memory Pattern**
```
Each primitive has a "TWIN":
int → Integer (add "eger")
char → Character (add "acter") 
bool → Boolean (add "ean")
```

---

## 📋 Quick Revision Cheat Sheet

### **Reference Data Types Summary**
```java
// 4 Main Types
Class obj = new Class();        // User-defined objects
String str = "literal";         // Immutable character sequences
Interface ref = new Impl();     // Contract implementations
int[] arr = new int[5];         // Collections of same type
```

### **String Operations Quick Reference**
```java
String s1 = "Hello";           // String pool
String s2 = new String("Hi");  // Heap memory
s1.equals(s2)                  // Compare content
s1 == s2                       // Compare references
s1.intern()                    // Force into string pool
```

### **Wrapper Classes Quick Map**
```java
byte    → Byte       |  int     → Integer
short   → Short      |  long    → Long
char    → Character  |  float   → Float
boolean → Boolean    |  double  → Double
```

### **Autoboxing/Unboxing Quick Examples**
```java
// Autoboxing (primitive → wrapper)
Integer i = 10;        // int → Integer
Boolean b = true;      // boolean → Boolean

// Unboxing (wrapper → primitive)  
int x = i;            // Integer → int
boolean y = b;        // Boolean → boolean
```

### **Constant Variables Quick Template**
```java
public static final TYPE NAME = VALUE;
// Example:
public static final String API_URL = "https://api.example.com";
public static final int MAX_RETRIES = 3;
```

### **Memory Model Quick Visualization**
```
STACK           HEAP
+-------+      +------------------+
|ref var|----->|  Actual Object   |
+-------+      +------------------+
               |String Pool (part)|
               +------------------+
```

### **Common Method Signatures**
```java
// Collections require wrapper types
List<Integer> list = new ArrayList<>();    // ✓ Correct
// List<int> list = new ArrayList<>();     // ✗ Wrong

// Interface instantiation
// Shape s = new Shape();                  // ✗ Wrong
Shape s = new Circle();                    // ✓ Correct (Circle implements Shape)
```

---

## 🎯 Key Takeaways

1. **Reference types store addresses, not values** - fundamental difference from primitives
2. **String immutability and pool optimization** - critical for memory management
3. **Wrapper classes bridge primitive and object worlds** - essential for collections
4. **Java has no pointers but achieves similar benefits** - through reference copying
5. **Autoboxing/unboxing happens automatically** - but has performance implications
6. **Constants use static final combination** - for thread-safe unchanging values
7. **Interface references point to implementation objects** - enables polymorphism

---

*Next Topics: Java Methods → Memory Management & Garbage Collection*