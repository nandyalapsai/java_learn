# Java Variables – Part 2 (Reference Types, Wrapper Classes, Autoboxing/Unboxing, Constants)

> Quick revision + interview-ready notes distilled from the session.

---
## 1. High-Level Map
| Topic | Core Idea | Why It Matters |
|-------|-----------|----------------|
| Reference Data Types | Hold references (addresses) to heap objects | Enable shared mutable state & object behavior |
| Class / Interface / Array / String | Main reference categories | Foundation of OOP & data modeling |
| Pass-by-Value in Java | Always copies the value (incl. object reference value) | Explains mutation vs reassignment behavior |
| String Pool & Immutability | String literals reused from a special pool | Memory efficiency + thread safety |
| Wrapper Classes | Object versions of primitives | Needed for Collections & object semantics |
| Autoboxing / Unboxing | Automatic primitive ↔ wrapper conversion | Cleaner syntax, can hide performance cost |
| `static final` Constants | Single unmodifiable value at class level | Improves clarity and prevents accidental change |

---
## 2. Key Concepts & Definitions
- **Primitive vs Reference**: Primitives store actual value; reference types store an address pointing to heap memory.
- **Reference Type**: Any type where the variable holds a *reference* (pointer-like value) to an object (e.g., `Employee emp`).
- **Heap vs Stack (simplified)**:
  - Stack: method frames, local primitive values, and references.
  - Heap: actual objects (instances, arrays, String objects, wrapper objects).
- **Pass-by-Value (Java ONLY)**: Java does not have pass-by-reference. For objects, the *reference value* is copied; both references point to the same heap object.
- **String Literal**: A string written directly in code (`"Hello"`) that goes into the *String Constant Pool* (SCP).
- **String Constant Pool**: Intern pool inside heap where identical literals are reused.
- **Immutability (String)**: Once created, the character contents of a `String` object cannot change.
- **Wrapper Class**: Object representation of a primitive (`int` → `Integer`, `double` → `Double`, etc.).
- **Autoboxing**: Automatic primitive → wrapper conversion (`Integer x = 5;`).
- **Unboxing**: Wrapper → primitive (`int y = x;`).
- **Constant**: A `public static final` field (naming convention: UPPER_SNAKE_CASE).

---
## 3. Reference Data Types Covered
### 3.1 Class
```java
class Employee {
    private int id;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
}

Employee emp = new Employee(); // emp holds a reference to a heap object
```

### 3.2 Interface
```java
interface Person { String profession(); }
class Teacher implements Person { public String profession() { return "Teaching"; } }
class Engineer implements Person { public String profession() { return "Software Engineer"; } }

Person p1 = new Engineer();  // Upcasting (allowed)
Person p2 = new Teacher();
```
- Cannot instantiate an interface directly: `new Person()` ❌

### 3.3 Array
```java
int[] nums = new int[5];      // default initialized to 0
nums[0] = 10; nums[3] = 40;

int[] nums2 = { 30, 20, 10, 40, 50 }; // shorthand

int[][] matrix = new int[2][3];
int[][] matrix2 = { {1,5,7}, {4,2,3} };
```
Arrays are objects → the variable holds a reference to the array data in heap.

### 3.4 String
```java
String s1 = "Hello";          // literal → SCP
String s2 = "Hello";          // reused (same SCP object)
String s3 = new String("Hello"); // forces new object in heap

System.out.println(s1 == s2); // true  (same reference)
System.out.println(s1 == s3); // false (different objects)
System.out.println(s1.equals(s3)); // true (same content)
```

---
## 4. How References Work (Mutation vs Reassignment)
```java
void modify(Employee e) { e.setId(20); }

Employee emp = new Employee();
emp.setId(10);
modify(emp);
System.out.println(emp.getId()); // 20 (object mutated)
```
- The reference value is copied into `modify`, both point to same object → mutation visible.
```java
void modify(int x) { x = 20; }
int a = 10;
modify(a);
System.out.println(a); // 10 (primitive value copied)
```

### ASCII Diagram – Object Reference
```
emp  ──▶  [ Employee@0xA1 | id=10 ]
modify(emp)
 e   ──▶  [ Employee@0xA1 | id=20 ]  // same heap object
```

---
## 5. String Interning & Immutability
### Lifecycle Example
```java
String a = "Hi";       // SCP: "Hi"
String b = a;           // points to same SCP entry
a = "Hi Java";          // New literal added to SCP if absent; `b` still "Hi"
```
- Old literal not modified → immutability.
- Reassignment changes reference, not object content.

### Diagram
```
SCP (inside heap)
+---------+     +------------+
| "Hi"    |◀─── b
+---------+ 
| "Hi Java" |◀── a (after reassignment)
+------------+
```

---
## 6. Wrapper Classes, Autoboxing & Unboxing
| Primitive | Wrapper    |
|-----------|-----------|
| byte      | Byte      |
| short     | Short     |
| int       | Integer   |
| long      | Long      |
| float     | Float     |
| double    | Double    |
| char      | Character |
| boolean   | Boolean   |

### Why Wrappers?
1. Collections (`List<Integer>`, `Map<String, Boolean>`) require objects.
2. Need nullability / sentinel (e.g., `Integer null` vs `int 0`).
3. Uniform API methods (`Integer.parseInt`, `Double.valueOf`).
4. Pass-by-value of references still allows shared mutation for mutable wrappers (though numeric wrappers are immutable—mutation advantage applies to custom reference types, not wrapper immutability).

### Autoboxing / Unboxing
```java
Integer x = 10;   // autoboxing (int → Integer)
int y = x;        // unboxing (Integer → int)

List<Integer> list = new ArrayList<>();
list.add(5);      // autoboxes to Integer.valueOf(5)
int v = list.get(0); // unboxes
```
### Pitfall
```java
Integer a = 128;
Integer b = 128;
System.out.println(a == b); // false (outside cache range -128..127)
```

---
## 7. Constants (`static final`)
```java
public class Config {
    public static final int MAX_USERS = 1000;
    public static final String APP_NAME = "ConceptAndCoding";
}
```
- `static`: one copy per class loader.
- `final`: cannot be reassigned after initialization.
- Naming: `UPPER_SNAKE_CASE`.

### Not a Constant
```java
public static int TIMEOUT = 30; // can be modified
```

---
## 8. Comparisons & Distinctions
| Concept | This | Not This |
|---------|------|----------|
| Pass-by-value | Java always copies the value (primitive or reference) | Pass-by-reference (not in Java) |
| `==` (objects) | Reference equality | Content equality |
| `.equals` (String) | Content equality | Reference check (unless not overridden) |
| Primitive | Stored directly in stack frame | Object on heap |
| String literal | Reused from SCP | `new String()` (forces new object) |
| Wrapper | Immutable object container | A pointer you can mutate (numeric wrappers immutable) |
| `static final` | Compile-time constant (if literal) | Mutable shared state |

---
## 9. Common Interview Questions (With Brief Answers)
1. Is Java pass-by-reference? → No. Always pass-by-value (object reference value is copied).
2. Difference between `==` and `.equals()` for Strings? → `==` compares references; `.equals()` compares content.
3. Why are Strings immutable? → Security, caching (SCP), thread safety, class loader & hashcode caching.
4. What is String interning? → Reusing a single object instance for identical literals in the SCP.
5. When does autoboxing introduce performance issues? → In tight loops or large collections due to object creation & unboxing overhead.
6. Can you store primitives in Collections? → No, only objects (use wrappers like `Integer`).
7. Why use `static final`? → Shared constant, prevents accidental modification, may enable compiler inlining.
8. Difference between array and `ArrayList`? → Array has fixed size & stores primitives or refs; `ArrayList` resizes dynamically and stores only references (wrappers for primitives).
9. Are wrapper classes mutable? → No (for numeric, boolean, char). Each value is a separate object.
10. Can two different references point to same object? → Yes (aliasing), mutation via one visible via others.

---
## 10. Real-World Use Cases
| Scenario | Concept Applied |
|----------|-----------------|
| Caching environment config | `static final` constants |
| Parsing user input numbers | Wrapper parsing (`Integer.parseInt`) |
| Storing scores in a list | Autoboxing → `List<Integer>` |
| Sharing domain entities across layers | Reference semantics (mutations reflect globally) |
| Comparing user-entered Strings | Use `.equals()`, not `==` |
| Memory optimization of repeated labels | String pool reuse |
| Multidimensional board game grid | 2D int array `int[][] board` |

---
## 11. Best Practices
- Prefer literals for Strings: `"Hello"` over `new String("Hello")`.
- Always use `.equals()` / `.equalsIgnoreCase()` for String content comparison.
- Use `static final` for constants; avoid “magic numbers”.
- Be mindful of autoboxing inside loops—consider primitives for performance-critical paths.
- Avoid mutating shared objects without clear ownership (can cause side effects).
- Initialize arrays with meaningful sizes; prefer collections if dynamic sizing needed.
- Use `List<int[]>` (primitive arrays) if you need performance and primitive storage.
- Prefer immutable data patterns where feasible.

### Common Pitfalls
| Pitfall | Issue | Fix |
|---------|-------|-----|
| Using `==` for String comparison | Fails unpredictably | Use `.equals()` |
| Excessive autoboxing | GC pressure | Use primitives in hot paths |
| Forgetting `final` on constants | Accidental mutation | Add `final` |
| Creating unnecessary `new String()` | Wastes memory | Use literals |
| Assuming wrapper mutation | Value never changes | Reassign explicitly |
| Aliasing side effects | Hidden shared mutations | Clone / copy if needed |

---
## 12. Quick Cheat Sheet
```
String s1 = "Hello";            // pooled
String s2 = "Hello";            // same ref
String s3 = new String("Hello"); // new object
s1 == s2  // true
s1 == s3  // false
s1.equals(s3) // true

Integer x = 10;   // autobox
int y = x;        // unbox
Integer a = 128, b = 128; a == b // false
Integer c = 100, d = 100; c == d // true (cache)

public static final int LIMIT = 500;

int[] arr = {1,2,3};
int[][] grid = { {1,2}, {3,4} };

// Pass-by-value demonstration
void set(Employee e) { e.setId(5); } // affects caller's object
void set(int v) { v = 5; }           // no effect
```

---
## 13. Mini Flowcharts
### Passing Object vs Primitive
```
[Caller] --emp--> [Method]            [Caller] --a--> [Method]
        (ref copied)                        (value copied)
        mutate emp.id                      change local 'a'
        visible outside                    NOT visible outside
```

### String Reassignment
```
s1 = "A"  -> SCP: "A"
s1 = "AB" -> SCP: "A", "AB" (s1 now -> "AB")
```

---
## 14. Practice Exercises
1. Write a method that takes `Employee` and reassigns parameter to `new Employee()`—does caller see it? Why?
2. Compare `Integer i1 = 127; Integer i2 = 127; i1 == i2` vs `Integer i3 = 128; Integer i4 = 128;`.
3. Build a 3×3 `char[][]` tic-tac-toe board and fill diagonals.
4. Show impact of autoboxing in a loop of 1M iterations (measure time).
5. Create your own immutable class and explain differences with `String`.

---
## 15. Summary (One-Liners)
- Java passes everything by value—object references are values pointing to heap objects.
- Strings are immutable and pooled; use `.equals()` for comparison.
- Wrapper classes bridge primitives to the object world (Collections, nullability).
- Autoboxing is convenient but can hide performance costs.
- `static final` defines constants; immutable intent should be explicit.
- Arrays are fixed-size heap objects; prefer collections when size changes.

---
> Master these foundations before moving to Methods and Memory Management (next modules).
