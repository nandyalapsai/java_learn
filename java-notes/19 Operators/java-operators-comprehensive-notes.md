# Java Operators - Comprehensive Notes

## Learning Objectives
After studying these notes, you should be able to:
- Understand all types of operators in Java and their categories
- Apply operator precedence and associativity rules correctly
- Use bitwise operators effectively for performance optimization
- Implement bit manipulation techniques for DSA problems
- Distinguish between prefix and postfix increment/decrement
- Apply ternary operators as alternatives to if-else statements
- Use instanceof operator for type checking in inheritance hierarchies

## Key Concepts & Definitions

### What is an Operator?
- **Operator**: Indicates what action to perform (e.g., +, -, *, /)
- **Operand**: The items on which the action is performed (variables or constants)
- **Expression**: Consists of one or more operands and zero or more operators

**Example:**
```java
int result = 5 + 3;  // '+' is operator, '5' and '3' are operands, whole thing is expression
```

## Categories of Operators in Java

### 1. Arithmetic Operators
Basic mathematical operations similar to all programming languages.

| Operator | Description | Example |
|----------|-------------|---------|
| `+` | Addition | `5 + 3 = 8` |
| `-` | Subtraction | `5 - 3 = 2` |
| `*` | Multiplication | `5 * 3 = 15` |
| `/` | Division | `5 / 2 = 2` (integer division) |
| `%` | Modulus | `5 % 2 = 1` |

```java
public class ArithmeticDemo {
    public static void main(String[] args) {
        int a = 5, b = 2;
        System.out.println("Addition: " + (a + b));      // 7
        System.out.println("Division: " + (a / b));      // 2 (integer division)
        System.out.println("Modulus: " + (a % b));       // 1
    }
}
```

### 2. Relational Operators
Compare two operands and return boolean (true/false).

| Operator | Description | Example |
|----------|-------------|---------|
| `==` | Equal to | `4 == 7` → false |
| `!=` | Not equal to | `4 != 7` → true |
| `>` | Greater than | `4 > 7` → false |
| `<` | Less than | `4 < 7` → true |
| `>=` | Greater than or equal | `4 >= 7` → false |
| `<=` | Less than or equal | `4 <= 7` → true |

```java
int a = 4, b = 7;
System.out.println(a == b);  // false
System.out.println(a != b);  // true
System.out.println(a < b);   // true
```

### 3. Logical Operators
Combine two or more conditions and return boolean result.

#### Logical AND (`&&`)
- Returns true only if **both** conditions are true
- **Short-circuit evaluation**: If first condition is false, second is not evaluated

#### Logical OR (`||`)
- Returns true if **any** condition is true
- **Short-circuit evaluation**: If first condition is true, second is not evaluated

```java
int a = 4, b = 7;

// Logical AND
boolean result1 = (a < 3) && (a != b);  // false && true = false
boolean result2 = (a > 3) && (a != b);  // true && true = true

// Logical OR
boolean result3 = (a < 3) || (a != b);  // false || true = true
boolean result4 = (a > 3) || (a != b);  // true || true = true (second not evaluated)
```

**Truth Tables:**
```
AND (&&):    OR (||):
T && T = T   T || T = T
T && F = F   T || F = T
F && T = F   F || T = T
F && F = F   F || F = F
```

### 4. Unary Operators
Work on single operand only.

#### Increment/Decrement Operators

| Type | Operator | Description | Example |
|------|----------|-------------|---------|
| Postfix | `a++` | Return current value, then increment | `a = 5; print(a++) → 5, a becomes 6` |
| Prefix | `++a` | Increment first, then return value | `a = 5; print(++a) → 6, a becomes 6` |
| Postfix | `a--` | Return current value, then decrement | `a = 5; print(a--) → 5, a becomes 4` |
| Prefix | `--a` | Decrement first, then return value | `a = 5; print(--a) → 4, a becomes 4` |

```java
int a = 5;
System.out.println(a++);  // Prints 5, a becomes 6
System.out.println(++a);  // Prints 7, a becomes 7
System.out.println(a--);  // Prints 7, a becomes 6
System.out.println(--a);  // Prints 5, a becomes 5
```

#### Other Unary Operators
- **Logical NOT (`!`)**: Inverts boolean value (`!true = false`)
- **Unary minus (`-`)**: Makes number negative (`-5`)
- **Unary plus (`+`)**: Makes number positive (`+5`)

### 5. Assignment Operators
Assign values to variables.

| Operator | Equivalent | Example |
|----------|------------|---------|
| `=` | Basic assignment | `a = 5` |
| `+=` | `a = a + b` | `a += 4` |
| `-=` | `a = a - b` | `a -= 3` |
| `*=` | `a = a * b` | `a *= 2` |
| `/=` | `a = a / b` | `a /= 5` |
| `%=` | `a = a % b` | `a %= 3` |

```java
int a = 5;
a += 4;  // a = a + 4 → a = 9
a *= 2;  // a = a * 2 → a = 18
a /= 3;  // a = a / 3 → a = 6
```

### 6. Bitwise Operators ⭐ (Very Important)

#### Why Bitwise Operations?
- **Ultra-fast**: Processor directly supports 1s and 0s
- **Memory efficient**: Work at bit level
- **DSA optimization**: Many algorithms use bit manipulation

#### Basic Bitwise Operations

| Operator | Name | Description | Truth Table |
|----------|------|-------------|-------------|
| `&` | Bitwise AND | 1 only if both bits are 1 | `1&1=1, others=0` |
| `\|` | Bitwise OR | 1 if any bit is 1 | `0\|0=0, others=1` |
| `^` | Bitwise XOR | 1 if bits are different | `0^0=0, 1^1=0, 0^1=1, 1^0=1` |
| `~` | Bitwise NOT | Flips all bits | `~1=0, ~0=1` |

#### Bitwise Examples
```java
int a = 4;  // Binary: 0100
int b = 6;  // Binary: 0110

System.out.println(a & b);  // 0100 & 0110 = 0100 → 4
System.out.println(a | b);  // 0100 | 0110 = 0110 → 6
System.out.println(a ^ b);  // 0100 ^ 0110 = 0010 → 2
```

#### Bitwise NOT - Special Case
```java
int a = 4;  // Binary: 00000100 (32-bit)
System.out.println(~a);  // Output: -5

// Formula: ~n = -(n + 1)
// ~4 = -(4 + 1) = -5
```

**Why -5?**
- Java integers are **signed** (can be positive/negative)
- Most Significant Bit (MSB) is sign bit: 0 = positive, 1 = negative
- `~4` flips all bits: `00000100` → `11111011`
- MSB = 1, so it's negative
- To find value: Use two's complement or formula `-(n+1)`

### 7. Bitwise Shift Operators ⭐ (Very Important for DSA)

#### Memory Trick for Direction
- **Left Shift (`<<`)**: Arrow points LEFT ←
- **Right Shift (`>>`)**: Arrow points RIGHT →
- **Unsigned Right Shift (`>>>`)**: Three arrows RIGHT >>>

#### Left Shift (`<<`)
- Shifts bits to the left
- **Effect**: Multiplication by 2^n (where n = shift amount)
- **LSB filling**: Always filled with 0

```java
int a = 4;  // Binary: 0100
System.out.println(a << 1);  // 0100 → 1000 = 8 (4 * 2^1)
System.out.println(a << 2);  // 0100 → 10000 = 16 (4 * 2^2)
```

#### Right Shift (`>>`) - Signed
- Shifts bits to the right
- **Effect**: Division by 2^n (where n = shift amount)
- **MSB filling**: Preserves sign bit (sign extension)

```java
int a = 8;   // Binary: 1000
System.out.println(a >> 1);  // 1000 → 0100 = 4 (8 / 2^1)

int b = -8;  // Binary: 11111000 (in two's complement)
System.out.println(b >> 1);  // MSB filled with 1 (sign preserved)
```

#### Unsigned Right Shift (`>>>`)
- Shifts bits to the right
- **MSB filling**: Always filled with 0 (no sign preservation)

```java
int a = -8;
System.out.println(a >> 1);   // Sign-preserving right shift
System.out.println(a >>> 1);  // Zero-fill right shift (always positive result)
```

### 8. Ternary Operator (Conditional)
Shorthand for if-else statements.

**Syntax:** `condition ? expression1 : expression2`

```java
int a = 4, b = 5;
int max = (a > b) ? a : b;  // Equivalent to if-else
System.out.println(max);    // Output: 5

// Equivalent if-else:
// if (a > b) {
//     max = a;
// } else {
//     max = b;
// }
```

### 9. Type Comparison Operator (`instanceof`)
Checks if an object is an instance of a specific class.

```java
class Parent {}
class Child1 extends Parent {}
class Child2 extends Parent {}

public class InstanceofDemo {
    public static void main(String[] args) {
        Child2 obj = new Child2();
        
        System.out.println(obj instanceof Child2);  // true
        System.out.println(obj instanceof Child1);  // false
        System.out.println(obj instanceof Parent);  // true (inheritance)
        
        String str = "Hello";
        System.out.println(str instanceof String);  // true
    }
}
```

## Operator Precedence and Associativity

### Precedence Table (High to Low Priority)

| Priority | Operators | Associativity |
|----------|-----------|---------------|
| 1 (Highest) | `()` `[]` `.` | Left to Right |
| 2 | `++` `--` `+` `-` `!` `~` (unary) | Right to Left |
| 3 | `*` `/` `%` | Left to Right |
| 4 | `+` `-` (binary) | Left to Right |
| 5 | `<<` `>>` `>>>` | Left to Right |
| 6 | `<` `<=` `>` `>=` `instanceof` | Left to Right |
| 7 | `==` `!=` | Left to Right |
| 8 | `&` (bitwise) | Left to Right |
| 9 | `^` | Left to Right |
| 10 | `\|` (bitwise) | Left to Right |
| 11 | `&&` | Left to Right |
| 12 | `\|\|` | Left to Right |
| 13 | `?:` (ternary) | Right to Left |
| 14 (Lowest) | `=` `+=` `-=` `*=` `/=` `%=` | Right to Left |

### Precedence Examples
```java
int result = 5 + 2 * 3;  // Multiplication first: 5 + 6 = 11

int a = 5;
int complex = ++a + a++ * --a + a--;
// Step by step evaluation needed based on precedence
```

## Common Interview Questions

### Q1: What's the difference between `++i` and `i++`?
**Answer:** 
- `++i` (prefix): Increments first, then returns the new value
- `i++` (postfix): Returns current value, then increments

### Q2: What does `~5` return and why?
**Answer:** `-6`. Formula: `~n = -(n+1)`. Java uses two's complement for negative numbers.

### Q3: Explain short-circuit evaluation in logical operators.
**Answer:**
- `&&`: If first condition is false, second is not evaluated
- `||`: If first condition is true, second is not evaluated
- This prevents unnecessary computation and potential errors

### Q4: What's the difference between `>>` and `>>>`?
**Answer:**
- `>>`: Signed right shift (preserves sign bit)
- `>>>`: Unsigned right shift (always fills with 0)

### Q5: How do you check if a number is even using bitwise operations?
**Answer:** `(n & 1) == 0` - If last bit is 0, number is even.

## Hands-on Exercises

### Exercise 1: Operator Precedence Challenge
```java
int x = 2;
int z = ++x + x++ / x++ - 1;
// What is the value of z? Calculate step by step.
```

### Exercise 2: Bitwise Magic
```java
// Implement these using bitwise operations:
// 1. Check if number is power of 2
// 2. Swap two numbers without temp variable
// 3. Find the only non-duplicate number in array
```

### Exercise 3: Ternary Chain
```java
// Convert this if-else chain to nested ternary operators:
int grade = 85;
String result;
if (grade >= 90) result = "A";
else if (grade >= 80) result = "B";
else if (grade >= 70) result = "C";
else result = "F";
```

## Real-world Use Cases

### 1. Bitwise Operations in Graphics/Games
```java
// RGB color manipulation
int color = 0xFF5733;  // Red: FF, Green: 57, Blue: 33
int red = (color >> 16) & 0xFF;    // Extract red component
int green = (color >> 8) & 0xFF;   // Extract green component
int blue = color & 0xFF;           // Extract blue component
```

### 2. Permission Systems
```java
// File permissions using bitwise OR
final int READ = 1;    // 001
final int WRITE = 2;   // 010
final int EXECUTE = 4; // 100

int permission = READ | WRITE;  // 011 (read + write)
boolean canRead = (permission & READ) != 0;
```

### 3. Fast Mathematical Operations
```java
// Fast multiplication/division by powers of 2
int fastMultiply = n << 3;  // Multiply by 8 (2^3)
int fastDivide = n >> 2;    // Divide by 4 (2^2)
```

## Best Practices & Common Pitfalls

### ✅ Best Practices
1. **Use parentheses** for clarity: `(a + b) * c` instead of relying on precedence
2. **Prefer `++i`** over `i++` when return value isn't used (slight performance benefit)
3. **Use bitwise operations** for performance-critical code
4. **Validate before division** to avoid `ArithmeticException`

### ❌ Common Pitfalls
1. **Integer division truncation**: `5 / 2 = 2` (not 2.5)
2. **Operator precedence confusion**: `a + b * c` vs `(a + b) * c`
3. **Bitwise vs logical confusion**: `&` vs `&&`, `|` vs `||`
4. **Signed vs unsigned shifts**: `>>` vs `>>>`

### 🐛 Debugging Tips
1. **Print intermediate values** in complex expressions
2. **Use IDE debugger** to step through operator evaluation
3. **Convert to binary** when debugging bitwise operations
4. **Watch for overflow** in arithmetic operations

## Memory Hooks & Mnemonics

### 📝 Remember Operator Symbols
- **Shift Direction**: Follow the arrow! `<<` goes LEFT, `>>` goes RIGHT
- **XOR Logic**: "eXclusive OR" - different bits give 1
- **Assignment Chains**: Right to left like reading Arabic
- **Instanceof**: "Is this object AN INSTANCEof that class?"

### 🧠 Precedence Memory Trick
**"Please Excuse My Dear Aunt Sally Loves Candy"**
- **P**arentheses (highest)
- **E**xponents/Unary
- **M**ultiplication/**D**ivision
- **A**ddition/**S**ubtraction
- **L**ogical operations
- **C**onditional/assignment (lowest)

## Cheat Sheet / Quick Revision

### Arithmetic
```java
+ - * / %  // Basic math operations
```

### Relational  
```java
== != < > <= >=  // Always return boolean
```

### Logical
```java
&& || !  // Combine conditions, short-circuit evaluation
```

### Unary
```java
++a (prefix), a++ (postfix)  // Pre/post increment
--a (prefix), a-- (postfix)  // Pre/post decrement
! + - ~  // Logical NOT, unary plus/minus, bitwise NOT
```

### Assignment
```java
= += -= *= /= %=  // Basic and compound assignment
```

### Bitwise
```java
& | ^ ~     // AND, OR, XOR, NOT
<< >> >>>   // Left shift, signed right, unsigned right
```

### Special
```java
condition ? true_expr : false_expr  // Ternary
object instanceof Class             // Type checking
```

### Quick Formulas
```java
~n = -(n + 1)           // Bitwise NOT
n << k = n * (2^k)      // Left shift multiplication  
n >> k = n / (2^k)      // Right shift division
n & 1 == 0             // Check if even
n & (n-1) == 0         // Check if power of 2
```

### Performance Tips
- Use `<<` and `>>` for multiplication/division by powers of 2
- Use `&` instead of `%` for modulo with powers of 2: `n & 3` instead of `n % 4`
- Prefer prefix increment when return value not needed

---

## Summary
Java operators are fundamental building blocks that enable you to manipulate data effectively. Master bitwise operations for performance optimization and DSA problems, understand precedence to write bug-free expressions, and practice with real-world scenarios to solidify your understanding. The key is consistent practice and understanding the underlying binary representations, especially for bitwise operations.