# Java Variables Part 2 - Reference Data Types & Wrapper Classes

## Overview
This covers the second part of Java variables, focusing on:
- Reference Data Types (Non-Primitive)
- Wrapper Classes
- Autoboxing and Unboxing
- Constant Variables

## Reference Data Types (Non-Primitive)

### What is a Reference?
- Reference data types hold a **reference** to the actual memory location in heap
- The variable doesn't store the actual data, but the memory address where data is stored
- Main types: **Class**, **String**, **Interface**, **Array**

### 1. Class as Reference Data Type

#### Example: Employee Class
```java
class Employee {
    int employeeId;
    
    // Getter
    public int getEmployeeId() {
        return employeeId;
    }
    
    // Setter
    public void setEmployeeId(int id) {
        this.employeeId = id;
    }
}
```

#### Creating Objects and References
```java
// Creating an object
Employee emp = new Employee();
emp.employeeId = 10;

// Calling a method that modifies the object
modify(emp);

public void modify(Employee employee) {
    employee.employeeId = 20;  // This changes the actual object
}

// After method call, emp.employeeId will be 20
System.out.println(emp.employeeId); // Output: 20
```

#### Memory Allocation
- When you create `new Employee()`, memory is allocated in **heap**
- `emp` variable holds a reference to this memory location (e.g., address: AIJ1246987)
- Multiple references can point to the same object:
```java
Employee obj1 = new Employee();
Employee obj2 = obj1;  // Both point to same memory
obj2.employeeId = 30;  // Changes reflect in obj1 as well
```

#### Pass by Value vs Pass by Reference
- **Java has NO pass by reference** - everything is pass by value
- However, reference data types achieve similar functionality
- When you pass an object, you're passing the **value of the reference** (memory address)
- This allows modifications to affect the original object

### 2. String as Reference Data Type

#### String Constant Pool
- Strings are stored in a special area called **String Constant Pool** within heap memory
- **Strings are immutable** - once created, they cannot be changed

#### String Literals vs String Objects
```java
// String literals - stored in String Constant Pool
String s1 = "Hello";
String s2 = "Hello";  // Points to same literal in pool

// String objects - created in heap memory
String s3 = new String("Hello");  // Creates new object in heap
```

#### String Comparison
```java
String s1 = "Hello";
String s2 = "Hello";
String s3 = new String("Hello");

// Content comparison using .equals()
s1.equals(s2);    // true - same content
s1.equals(s3);    // true - same content

// Reference comparison using ==
s1 == s2;         // true - both point to same memory in pool
s1 == s3;         // false - different memory locations
```

#### String Immutability Example
```java
String s1 = "Hello";        // Points to "Hello" in pool
s1 = "Hello World";         // Creates new literal, s1 now points to "Hello World"
                           // Original "Hello" remains unchanged in pool
```

### 3. Interface as Reference Data Type

#### Example: Person Interface
```java
interface Person {
    String profession();
}

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
```

#### Creating Interface References
```java
// Valid ways to create references
Person person1 = new Engineer();    // Interface reference to child object
Person person2 = new Teacher();     // Interface reference to child object
Teacher teacher = new Teacher();    // Direct class reference
Engineer engineer = new Engineer(); // Direct class reference

// Invalid - Cannot instantiate interface directly
// Person person = new Person();    // COMPILATION ERROR
```

**Key Points:**
- Cannot create objects of interfaces directly
- Can hold references to implementing class objects
- Parent interface can hold reference to child class objects

### 4. Array as Reference Data Type

#### One-Dimensional Arrays
```java
// Method 1: Declare size first, then assign values
int[] arr = new int[5];
arr[0] = 10;
arr[3] = 40;

// Method 2: Direct initialization
int[] arr = {30, 20, 10, 40, 50};
```

#### Two-Dimensional Arrays
```java
// Method 1: Declare dimensions
int[][] arr = new int[5][4];  // 5 rows, 4 columns
arr[2][2] = 20;  // Row 2, Column 2
arr[1][3] = 30;  // Row 1, Column 3

// Method 2: Direct initialization
int[][] arr = {{1, 5, 7}, {4, 2, 3}};  // 2 rows, 3 columns
```

#### Array Memory Structure
- Arrays are stored in **continuous memory locations** in heap
- Array variable holds reference to the first element
- All elements must be of the same data type

## Wrapper Classes

### What are Wrapper Classes?
For each of the 8 primitive types, Java provides corresponding **wrapper classes**:

| Primitive | Wrapper Class |
|-----------|---------------|
| int       | Integer       |
| char      | Character     |
| short     | Short         |
| byte      | Byte          |
| long      | Long          |
| float     | Float         |
| double    | Double        |
| boolean   | Boolean       |

### Why Wrapper Classes?

#### 1. Reference Capability
```java
// Primitive - changes don't reflect
int a = 10;
modify(a);  // a remains 10 after method call

public void modify(int x) {
    x = 20;  // Only local copy is changed
}

// Wrapper - changes reflect through reference
Integer a = 10;
modify(a);  // Object can be modified through reference
```

#### 2. Collections Compatibility
- Java Collections (ArrayList, HashMap, etc.) work only with **objects**
- Cannot use primitive types directly in collections
```java
// Invalid
// ArrayList<int> list = new ArrayList<>();

// Valid
ArrayList<Integer> list = new ArrayList<>();
```

### Autoboxing and Unboxing

#### Autoboxing (Primitive → Wrapper)
```java
int a = 10;
Integer a1 = a;  // Automatic conversion from int to Integer
```

#### Unboxing (Wrapper → Primitive)
```java
Integer x = 20;
int x1 = x;      // Automatic conversion from Integer to int
```

## Constant Variables

### Creating Constants
Use `static final` keywords together:

```java
public class Employee {
    // Constant variable
    public static final int EMPLOYEE_ID = 10;
    
    // Other class members...
}
```

### Properties of Constants
- **static**: Belongs to class, not instance (only one copy exists)
- **final**: Cannot be modified after initialization
- **Naming convention**: Use UPPER_CASE with underscores

### Example Usage
```java
Employee obj1 = new Employee();
Employee obj2 = new Employee();

// Both objects share the same constant
System.out.println(Employee.EMPLOYEE_ID);  // 10

// Compilation error - cannot modify final variable
// Employee.EMPLOYEE_ID = 20;  // ERROR
```

## Key Takeaways

1. **Reference Data Types** store references to memory locations, not actual data
2. **Java is always pass-by-value**, but reference types provide similar benefits to pass-by-reference
3. **Strings are immutable** and stored in String Constant Pool for optimization
4. **Wrapper classes** provide object versions of primitive types for reference capability and collections compatibility
5. **Autoboxing/Unboxing** allows seamless conversion between primitives and wrappers
6. **Constants** are created using `static final` keywords together

## Next Topics
- Methods in Java
- Memory Management and Garbage Collection