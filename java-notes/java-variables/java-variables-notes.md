# Java Variables – Comprehensive Notes (Part 1)

> Focus: Primitive types, naming conventions, typing system, conversions, kinds of variables. (Reference types, wrappers, `final`, floating-point deep dive promised for Part 2.)

---
## 1. Core Definition
**Variable**: A named container that holds a value in memory.

```java
int age = 32;  // int = data type, age = variable name, 32 = value
```

---
## 2. Static vs Strong Typing
| Aspect | Meaning in Java | Example |
|--------|-----------------|---------|
| Static Typed | Data type must be declared at compile time | `int x = 10;` |
| Strong Typed | Type rules enforced; incompatible assignments require explicit conversion | Cannot assign `long` to `short` without cast |

---
## 3. Variable Naming Conventions
- Case-sensitive: `count` ≠ `Count`
- Can contain: letters, digits, `_`, `$`
- Can start with: letter, `_`, `$` (NOT a digit)
- Cannot use Java reserved words: `class`, `int`, `new`, `for`, etc.
- Style:
  - Single word: lowercase (`city`)
  - Multi-word: camelCase (`cityPopulation`)
  - Constants: UPPER_SNAKE_CASE (`MAX_SPEED`)
- Unicode allowed (avoid unless necessary)

Invalid examples:
```java
int 9value;       // starts with digit
int class;        // reserved word
```

---
## 4. Primitive Data Types Overview
| Type | Size (bits) | Category | Range | Default (fields) | Notes |
|------|-------------|----------|-------|------------------|-------|
| `byte` | 8  | Integral | -128 to 127 | 0 | Two's complement |
| `short` | 16 | Integral | -32768 to 32767 | 0 | Two's complement |
| `int` | 32 | Integral | -2^31 to 2^31-1 | 0 | Most common |
| `long` | 64 | Integral | -2^63 to 2^63-1 | 0L | Literal suffix `L` |
| `char` | 16 | UTF-16 code unit | 0 to 65535 | '\u0000' | Unsigned |
| `float` | 32 | Floating | ~6-7 decimal digits | 0.0f | IEEE 754 (binary) |
| `double` | 64 | Floating | ~15 decimal digits | 0.0d | Default for decimals |
| `boolean` | JVM-dependent (logical) | Logical | `true` / `false` | `false` | Not numerically convertible |

### 4.1 Integral vs Floating vs Logical
- Integral: `byte`, `short`, `int`, `long`, `char`
- Floating: `float`, `double` (binary approximation)
- Logical: `boolean`

### 4.2 Character Examples
```java
char c1 = 'A';        // direct
char c2 = 65;         // ASCII / Unicode code point -> 'A'
char c3 = '\u0041';   // Unicode escape
System.out.println(c2); // A
```

### 4.3 Long & Literal Suffixes
```java
long id = 5000000000L;  // Needs L (literal exceeds int range)
float rate = 3.5f;      // f suffix required
```

### 4.4 Floating-Point Precision Pitfall
```java
float a = 0.3f;
float b = 0.1f;
float c = a - b;          // Expected 0.2
System.out.println(c);    // 0.19999999 or 0.20000002 (approx)
```
Use `BigDecimal` for money / precise decimals.

---
## 5. Default Values vs Local Requirements
| Context | Default Applied? | Example |
|---------|------------------|---------|
| Instance (member) variable | Yes | `int x; // becomes 0` |
| Static variable | Yes | `static boolean flag; // false` |
| Local variable | No (must initialize) | `int x; System.out.println(x); // ERROR` |
| Method parameter | N/A | Value passed by caller |

---
## 6. Signed Two's Complement (Integral Types)
- All integer primitives except `char` use two's complement.
- Highest bit: sign (0 = positive, 1 = negative)
- Range formula (for n bits): `-2^(n-1)` to `2^(n-1) - 1`

Example (8-bit `byte`):
```
Binary  Max positive:  0111 1111 = 127
Binary  Min negative:  1000 0000 = -128
```

Two's complement of +3 (4-bit example for teaching):
1. +3 = 0011
2. Invert bits → 1100
3. Add 1 → 1101  (represents -3)

---
## 7. Type Conversions
### 7.1 Widening (Implicit / Safe / Automatic)
Small → Large numeric type auto-converts.
Order (simplified): `byte -> short -> int -> long -> float -> double`
```java
int i = 10;
long l = i;      // OK (widening)
float f = l;     // OK
```

### 7.2 Narrowing (Explicit / Potential Loss)
Large → Small requires cast; may overflow.
```java
int i = 128;
byte b = (byte) i;   // b == -128 (overflow wrap)
```
Overflow rule: wraps modulo 2^n (n = target bits).

### 7.3 Promotion in Expressions
- `byte`, `short`, `char` → promoted to `int` in expressions.
- Mixed types promote to the widest participating type.
- If `double` present → whole expression becomes `double`.

```java
byte a = 127, b = 1;
int sum = a + b;        // promoted to int

int x = 5;
double y = 2.5;
var z = x + y;          // z inferred as double (Java 10 var)
```

Casting back:
```java
byte result = (byte)(a + b); // Overflow → -128
```

### 7.4 Common Misunderstanding (Addition vs Concatenation)
```java
int a = 5, b = 6;
System.out.println("Sum=" + a + b);   // Sum=56
System.out.println("Sum=" + (a + b)); // Sum=11
```

---
## 8. Kinds (Scopes / Roles) of Variables
| Kind | Declared Where | Lifetime | Access Pattern | Default Value? |
|------|----------------|----------|----------------|----------------|
| Instance (member) | Inside class, outside methods | Per object | `obj.field` | Yes |
| Static (class) | `static` in class | Per class (single copy) | `ClassName.field` | Yes |
| Local | Inside method/constructor/block | Execution of block | Direct name | No |
| Parameter | Method/constructor signature | Call duration | Direct name | Supplied by caller |
| Constructor parameter | In constructor signature | During construction | Direct name | Supplied |

### 8.1 Example
```java
class Employee {
    int id;                 // instance
    static int count = 0;   // static

    Employee(int id) {      // constructor parameter
        this.id = id;
        count++;            // shared counter
    }

    void work(int hours) {  // parameter
        int tasks = hours * 2; // local
        System.out.println("Tasks: " + tasks);
    }
}
```

---
## 9. Code Snippet Roundup
### 9.1 Primitive Declarations
```java
byte b = 10;
short s = 200;
int n = 1000;
long big = 10000000000L;
char ch = 'Z';
float rate = 4.5f;
double pi = 3.1415926535;
boolean active = true;
```

### 9.2 Static vs Instance
```java
class Counter {
    static int totalCreated = 0; // shared
    int id;                      // per object
    Counter(int id) { this.id = id; totalCreated++; }
}
```

### 9.3 Overflow Demo
```java
byte max = 127;
byte wrap = (byte)(max + 1); // -128
```

---
## 10. ASCII / Unicode Illustration (char)
```
Index:  65 66 67 68  97  98  99
Char :  A  B  C  D   a   b   c
```

---
## 11. Comparison: float/double vs BigDecimal
| Aspect | float/double | BigDecimal |
|--------|--------------|-----------|
| Representation | Binary IEEE 754 | Arbitrary precision decimal |
| Speed | Faster | Slower |
| Precision errors | Yes (0.1 issues) | No (exact decimal) |
| Use for money? | No | Yes |

---
## 12. Common Interview Questions (With Brief Answers)
1. Q: Difference between primitive and reference types?  
   A: Primitives store actual values (stack); references store object addresses (heap). (Details in Part 2.)
2. Q: Why is Java called strongly typed?  
   A: Enforces type safety; implicit unsafe conversions are disallowed.
3. Q: What is variable shadowing?  
   A: Inner scope variable with same name hides outer variable (not shown here; avoid for clarity).
4. Q: Default value of local variables?  
   A: None; must be explicitly initialized.
5. Q: What happens on `byte b = (byte) 130;`?  
   A: Wrap via two's complement → -126.
6. Q: Why float subtraction `0.3f - 0.1f` is not exact?  
   A: Binary floating-point cannot represent many decimal fractions precisely (IEEE 754).
7. Q: Order of numeric promotion?  
   A: `byte/short/char -> int -> long -> float -> double`
8. Q: Difference between widening and narrowing?  
   A: Widening is automatic (safe); narrowing requires explicit cast (may lose data).
9. Q: Is `char` signed?  
   A: No, it's an unsigned 16-bit UTF-16 code unit.
10. Q: When use `long` literal suffix?  
    A: When value exceeds int range or to clarify intent (e.g., `10000000000L`).

---
## 13. Real-World Use Cases
| Scenario | Variable Considerations |
|----------|-------------------------|
| Counting items/users | `int` (unless exceeding 2B → `long`) |
| Monetary calculations | `BigDecimal` (avoid `double`) |
| Flags / feature toggles | `boolean` |
| Character processing (e.g., grade) | `char` (beware surrogate pairs) |
| Memory-constrained embedded logic | `byte` / `short` (rare in modern JVM apps) |
| Timestamps / IDs | `long` (epoch millis, Snowflake IDs) |

---
## 14. Best Practices
- Prefer `int` for whole numbers unless range requires `long`.
- Use `BigDecimal` for currency or precise decimals.
- Initialize variables close to usage (improves readability & avoids stale values).
- Avoid hungarian notation (`int iCount` → just `count`).
- Keep scope as small as possible (limit lifetime of locals).
- Use meaningful names: `employeeCount`, not `ec`.
- Avoid magic numbers → use constants (`static final int MAX_RETRIES = 3;`).
- Use `L` (uppercase) not `l` (lowercase) for long literals to avoid confusion with `1`.

---
## 15. Common Pitfalls
| Pitfall | Why It Hurts | Fix |
|---------|--------------|-----|
| Using `float/double` for money | Rounding errors | Use `BigDecimal` |
| Forgetting casts on narrowing | Compilation error | Add explicit cast (and validate range) |
| Integer division surprise | `5/2 = 2` not `2.5` | Cast one operand to `double` |
| Overflow assumptions | Silent wrap | Check with `Math.addExact` (Java 8+) |
| Relying on default values | Reduces clarity | Initialize explicitly |
| Shadowing variables | Confusing logic | Use distinct names |
| Misusing static for per-object state | Shared unintendedly | Keep instance fields non-static |

---
## 16. Quick Revision Sheet
- 8 Primitives: `byte short int long char float double boolean`
- Default numeric literal = `int`, decimal literal = `double`
- Widening = automatic; narrowing = explicit
- Expression promotion: smallest → largest type involved
- `char` is unsigned; others signed (two's complement)
- Floating-point imprecision → use `BigDecimal` when exactness required
- Kinds: instance, static, local, parameter, constructor parameter

---
## 17. ASCII/Flow Illustration: Variable Lifecycle (Instance vs Local)
```mermaid
digraph G {
  rankdir=LR;
  subgraph cluster_heap { label="Heap"; obj1[Employee obj1]; obj2[Employee obj2]; }
  subgraph cluster_stack1 { label="Stack (Thread)"; mainFn[main() frame]; workFn[work() frame]; }
  mainFn -> obj1;
  mainFn -> obj2;
  workFn -> obj1;
  obj1 -> field1[instance field id];
  workFn -> local1[local int tasks];
}
```

---
## 18. Comparison: Instance vs Static
| Feature | Instance Field | Static Field |
|---------|----------------|--------------|
| Storage | Per object | One per class |
| Access | `obj.field` | `ClassName.field` |
| Use case | Object state | Shared counters/config |
| Lifecycle | With object | Class loaded → JVM unload |

---
## 19. When to Introduce Constants
```java
public static final double TAX_RATE = 0.18;  // Immutable shared value
```
Use for: limits, configuration defaults, domain constants.

---
## 20. Mini Practice Checklist
- Declare a `long` with value > `Integer.MAX_VALUE`.
- Show overflow of `byte`.
- Demonstrate widening (`byte` → `int`).
- Show `BigDecimal` fixing a rounding issue.

---
## 21. Suggested Next (Will Be in Part 2)
- Reference types & memory model
- Wrapper classes (`Integer`, `Double`, etc.)
- `final` with variables
- Deep dive IEEE 754 & `BigDecimal`
- Autoboxing & unboxing
- `var` (Java 10+) inference nuances

---
## 22. Summary
Understanding primitive types, scopes, and conversions forms the foundation for memory efficiency, correctness, and clarity in Java applications. Mastering these basics prevents subtle bugs (overflow, precision loss) and prepares you for more advanced topics (collections, generics, concurrency).

---
> End of Part 1 – Use this as a revision + interview prep sheet.
