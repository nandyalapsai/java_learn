# Java Variables: Comprehensive Notes

This document provides a structured summary of Java variables, based on the "Concept and Coding" video. It's designed for both learning and quick revision for interviews.

---

### 1. What is a Variable?

*   **Definition**: A variable is a container that holds a value during the execution of a Java program.
*   **Declaration**: Variables are declared with a specific data type, a name, and an initial value.

    ```java
    // Syntax: dataType variableName = value;
    int score = 95;
    ```

---

### 2. Java: A Statically & Strongly Typed Language

*   **Static Typing**: You must declare the data type of a variable before you can use it. The data type is fixed at compile time.
    ```java
    // This is mandatory in Java
    String playerName = "John"; 
    ```
*   **Strong Typing**: Once a variable is declared with a data type, you can only assign values of that specific type or compatible types. There are strict rules, and each data type has a defined range of values.
    ```java
    int age = 30;
    // age = "thirty"; // This will cause a compile-time error
    ```

---

### 3. Variable Naming Conventions & Rules

| Rule / Convention | Description | Example |
| :--- | :--- | :--- |
| **Case-Sensitive** | `myVar` and `myvar` are treated as two different variables. | `int count;` `int Count;` |
| **Allowed Characters** | Can include letters, digits, underscore (`_`), and dollar sign (`$`). | `_userScore`, `$price` |
| **Starting Character** | Must start with a letter, `_`, or `$`. **Cannot** start with a digit. | `_id` (valid), `9lives` (invalid) |
| **Reserved Words** | Cannot use Java keywords (like `int`, `class`, `public`) as variable names. | `int class = 5;` (invalid) |
| **Camel Case** | For multi-word names, start with a lowercase letter and capitalize the first letter of subsequent words. | `String firstName;` |
| **Constants** | For `final` variables (constants), use all uppercase letters with underscores to separate words. | `static final int MAX_SCORE = 100;` |

---

### 4. Types of Variables in Java

#### 4.1. Based on Data Type

##### a) Primitive Types (8)
These are the most basic data types and are not objects.

| Type | Size | Range / Values | Default Value | Use Case |
| :--- | :--- | :--- | :--- | :--- |
| **`boolean`** | ~1 bit | `true`, `false` | `false` | Storing flags or conditions. |
| **`char`** | 2 bytes | 0 to 65,535 (`\u0000` to `\uffff`) | `\u0000` | Storing single characters. |
| **`byte`** | 1 byte | -128 to 127 | `0` | Saving memory with large arrays of small integers. |
| **`short`** | 2 bytes | -32,768 to 32,767 | `0` | Memory saving where `byte` is too small. |
| **`int`** | 4 bytes | -2<sup>31</sup> to 2<sup>31</sup>-1 | `0` | Default choice for integer values. |
| **`long`** | 8 bytes | -2<sup>63</sup> to 2<sup>63</sup>-1 | `0L` | For very large integer values (e.g., IDs). |
| **`float`** | 4 bytes | Stores fractional numbers (6-7 decimal digits) | `0.0f` | For single-precision decimal values to save memory. |
| **`double`**| 8 bytes | Stores fractional numbers (15 decimal digits) | `0.0d` | Default choice for decimal values. |

**Code Snippets:**
```java
// Integral types
byte age = 25;
int salary = 80000;
long worldPopulation = 8000000000L; // Note the 'L' suffix

// Floating-point types
float price = 19.99f;   // Note the 'f' suffix
double pi = 3.1415926535; // 'd' is optional

// Other primitives
char grade = 'A';
boolean isLoggedIn = true;
```

##### b) Reference (Non-Primitive) Types
*   These variables refer to objects (instances of a class).
*   Examples include `String`, `Arrays`, and any custom class like `Employee`.
*   They hold the memory address of an object, not the object itself.
*   Their default value is `null`.

#### 4.2. Based on Scope (Kinds of Variables)

| Kind | Scope | Lifetime | Default Value? | Example |
| :--- | :--- | :--- | :--- | :--- |
| **Instance (Member)** | Inside a class, outside any method. | As long as the object exists. | Yes | `class Car { String color; }` |
| **Static (Class)** | Inside a class, with `static` keyword. | As long as the class is loaded in memory. | Yes | `class Car { static int wheelCount = 4; }` |
| **Local** | Inside a method or block. | Only within that method/block. | No (must be initialized) | `void drive() { int speed = 60; }` |
| **Parameter** | In the method signature. | Only within that method. | No (initialized by caller) | `void setSpeed(int newSpeed) { ... }` |

---

### 5. Type Conversion (Casting)

#### a) Widening (Automatic Conversion)
*   Converting a smaller data type to a larger one.
*   It's safe and happens automatically because there is no risk of data loss.
*   **Flow**: `byte` -> `short` -> `int` -> `long` -> `float` -> `double`

```java
int myInt = 100;
long myLong = myInt; // Automatic conversion from int to long
double myDouble = myLong; // Automatic conversion from long to double
```

#### b) Narrowing (Explicit Casting)
*   Converting a larger data type to a smaller one.
*   Requires a manual cast and carries a risk of data loss if the value is out of range.

```java
double price = 99.98;
// int intPrice = price; // Compile error!
int intPrice = (int) price; // Explicit cast. intPrice is now 99.
```

*   **Pitfall**: If the value exceeds the target type's range, it "wraps around".
    ```java
    int largeValue = 130;
    byte myByte = (byte) largeValue; // myByte becomes -126
    // (byte range is -128 to 127. 127 -> -128 -> -127 -> -126)
    ```

#### c) Expression Promotion
*   In an arithmetic expression, smaller types (`byte`, `short`, `char`) are automatically promoted to `int`.
*   If an expression contains different data types, all values are promoted to the largest type in the expression.

```java
byte a = 10;
byte b = 20;
// byte c = a + b; // Error! a and b are promoted to int, so the result is an int.
int c = a + b; // Correct.

int x = 5;
double y = 10.5;
// int result = x + y; // Error! The result is promoted to double.
double result = x + y; // Correct. result is 15.5.
```

---

### 6. Interview Questions & Quick Answers

*   **Q: What's the difference between static and strong typing?**
    *   **A:** Static typing means variable types are declared at compile-time. Strong typing means these types are strictly enforced, preventing, for example, assigning a `String` to an `int`.

*   **Q: Why shouldn't we use `float` or `double` for financial calculations?**
    *   **A:** `float` and `double` are binary floating-point types that cannot represent all decimal fractions precisely, leading to rounding errors. For currency, use the `BigDecimal` class, which provides exact precision.

*   **Q: What is the default value of a local variable?**
    *   **A:** Local variables do not have a default value. They must be explicitly initialized before being used, otherwise, it results in a compile-time error.

*   **Q: What happens when you add two `byte` variables?**
    *   **A:** During an expression, both `byte` variables are automatically promoted to `int`. The result of the addition is an `int`.

---

### 7. Best Practices & Common Pitfalls

*   **Best Practice**: Always choose the most appropriate data type. Use `int` for general-purpose integers and `double` for general-purpose decimals unless you have a specific reason (like memory constraints or need for high precision) to choose another type.
*   **Best Practice**: Give variables clear, descriptive names (e.g., `customerFirstName` instead of `cfn`).
*   **Pitfall**: Using `==` to compare floating-point numbers. Due to precision issues, it's better to check if the absolute difference is within a small tolerance.
*   **Pitfall**: Forgetting to add the `L` suffix for `long` literals or `f` for `float` literals, which can cause unexpected compiler errors.
*   **Pitfall**: Unintentional data loss from narrowing casting. Always be sure the value will fit in the smaller type before casting.
