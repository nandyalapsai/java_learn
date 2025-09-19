# Java Classes (Part 3) – POJO, `enum`, and `final` Class

> Focus of this session: POJO classes, Enums in depth, and `final` classes. (Singleton, Immutable, Wrapper will come later.)

---
## 1. Key Concepts & Definitions

### POJO (Plain Old Java Object)
A simple, lightweight Java object with **no special restrictions**. Typically:
- Public class (not required—but common)
- Fields (any access modifier)
- Getters/Setters (optional but common)
- Public no‑arg (default) constructor (implicit if none declared)
- No framework annotations (e.g., `@Entity`, `@Table`)
- Does **not** have to extend a class or implement an interface

> Goal: Carry data with minimal overhead. Flexible; not constrained like JavaBeans (which require private fields & public getters/setters).

### Enum (`enum`)
A special Java type representing a **fixed set of named constants**.
Key traits:
- Each constant is an instance of the enum type
- All constants are **implicitly `public static final`**
- Enum type **implicitly extends `java.lang.Enum`** (so cannot extend another class)
- May implement interfaces
- May define: fields, constructors (always implicitly `private`), and methods
- Supports overriding behavior per constant

### Final Class (`final class`)
A class declared with `final` **cannot be inherited**.
- Used to prevent extension (security, immutability, API stability)

### Other Related Terms (Referenced Indirectly)
- **Constants via `static final`**: Traditional way to define primitive/string constants
- **Ordinal**: Zero-based position of enum constant (from declaration order)
- **`values()`**: Auto-generated static method returning an array of all constants
- **`valueOf(String)`**: Returns the enum constant matching the exact name

---
## 2. Important Points Explained

### Why Use POJOs?
- Decouple incoming request models from internal domain models
- Provide a layer for mapping / transformation (avoid direct dependency on external contract)
- Reduce ripple effects when external API changes (e.g., `id` → `customer_id`)

### Where POJOs Are Commonly Used
- API layer: Request/response payload mapping
- Data persistence layer: Entity ⇄ DTO transformations
- Service layer interchange objects

### Enum Advantages Over `static final` Constants
| Aspect | `static final` constants | `enum` |
|--------|--------------------------|--------|
| Type safety | Weak (any invalid int can pass) | Strong (only declared constants allowed) |
| Readability | Magic numbers or strings | Self-descriptive (`Day.SUNDAY`) |
| Namespace grouping | Manual | Built-in |
| Behavior attachment | Requires extra structures | Methods inside enum / per constant |
| Switch safety | No exhaustiveness check on primitives | Compiler can warn on missing constants |
| Custom metadata | Separate maps/arrays needed | Fields + constructors |

### Enum Behavior Per Constant
- Each constant can **override** methods using an anonymous class-like block
- Enums can declare **abstract methods** that each constant must implement

### Enum Internal Values
- Default **ordinal** (0..n-1) is auto-assigned but should NOT be persisted (fragile if order changes)
- You can define **custom fields** (e.g., code, label) and retrieve via accessors

### Final Class Use
- Prevent subclass misuse
- Lock down logic (e.g., security-related utilities)
- Often combined with `private` constructor + `static` methods to model utility classes

---
## 3. Code Examples

### 3.1 POJO Example
```java
public class Student {        // POJO
    String id;                // package-private
    private String name;      // private
    protected int age;        // protected
    public String grade;      // public

    public Student() {}       // default constructor

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    // Other getters/setters omitted
}
```

### 3.2 Mapping Request Object to Internal POJO
```java
// Incoming request model (external)
public class CreateStudentRequest { public String id; public String name; }

// Internal domain model (POJO)
public class StudentProfile { 
    private String customerId; 
    private String customerName; 
    // getters/setters
}

// Mapping layer
StudentProfile map(CreateStudentRequest req) {
    StudentProfile profile = new StudentProfile();
    profile.setCustomerId(req.id);
    profile.setCustomerName(req.name);
    return profile; // Rest of code uses StudentProfile, not raw request
}
```

### 3.3 Basic Enum
```java
public enum Day { 
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
}
```

### 3.4 Enum Methods & Core API
```java
for (Day d : Day.values()) {            // values()
    System.out.println(d + " -> " + d.ordinal()); // ordinal()
}
Day friday = Day.valueOf("FRIDAY");     // valueOf()
System.out.println(friday.name());       // name()
```

### 3.5 Enum with Custom Fields & Lookup
```java
public enum Status { 
    NEW(100, "Just created"),
    IN_PROGRESS(200, "Work started"),
    DONE(300, "Completed");

    private final int code; 
    private final String description;

    Status(int code, String description) {
        this.code = code; this.description = description;
    }
    public int getCode() { return code; }
    public String getDescription() { return description; }

    public static Status fromCode(int code) {
        for (Status s : values()) {
            if (s.code == code) return s;
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
```

### 3.6 Per-Constant Method Override
```java
public enum Operation {
    ADD { double apply(double a,double b){ return a + b; } },
    SUB { double apply(double a,double b){ return a - b; } },
    MUL { double apply(double a,double b){ return a * b; } },
    DIV { double apply(double a,double b){ return a / b; } };  // common semicolon

    abstract double apply(double a, double b); // each must implement
}
```

### 3.7 Enum Implementing Interface
```java
interface LowercaseName { String toLower(); }

public enum Planet implements LowercaseName {
    EARTH, MARS;
    @Override
    public String toLower() { return name().toLowerCase(); }
}
```

### 3.8 Comparing Static Constants vs Enum
```java
// Approach 1: static final ints
public class DayConst {
    public static final int MONDAY=0, TUESDAY=1, WEDNESDAY=2,
        THURSDAY=3, FRIDAY=4, SATURDAY=5, SUNDAY=6;
    public static boolean isWeekend(int day) {
        return day == SATURDAY || day == SUNDAY; // fragile: accepts 999
    }
}

// Approach 2: enum
public enum DayEnum { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY; 
    public boolean isWeekend() { return this == SATURDAY || this == SUNDAY; }
}
```

### 3.9 Final Class
```java
public final class MathUtils { 
    private MathUtils() {}            // prevent instantiation
    public static int square(int x) { return x * x; }
}

// class ExtendedMath extends MathUtils { } // Compilation error
```

---
## 4. Visual Diagrams (Textual)

### 4.1 Layered Use of POJOs
```
Client Request JSON
       |
       v
Controller  -->  Request POJO  -->  Mapper  -->  Domain POJO  -->  Service  -->  Entity  -->  Repository/DB
```

### 4.2 Enum Internal Model
```
           +---------------------------+
           |        MyEnum             |
           | (extends java.lang.Enum)  |
           +---------------------------+
             ^    ^    ^
             |    |    |
        CONST_A CONST_B CONST_C   (All are singleton instances)
```

### 4.3 Static Constants vs Enum (Conceptual)
```
static final ints            enum Type
------------------          -------------------
Loose integers      -->      Strong typed symbols
Invalid values pass         Invalid values rejected at compile time
Behavior external           Behavior colocated
```

---
## 5. Real-World Use Cases
- **POJOs**: DTOs, API request/response models, caching objects, serialization payloads
- **Enums**: Workflow states (`PENDING`, `APPROVED`), error codes, strategy selection, feature flags
- **Final Classes**: Utility helpers (`Math`, `Collections`), cryptographic helpers (prevent tampering), immutable value types

---
## 6. Best Practices & Common Pitfalls
### POJO
- Keep POJO lean (no business logic)
- Avoid leaking external contract models internally
- Use builders if many fields (readability & immutability support)

### Enum
- Do NOT persist `ordinal()` (breaks if order changes)
- Prefer custom `code` field for persistence
- Avoid putting heavy logic directly in enum constants (can bloat)
- Use `EnumSet` / `EnumMap` for performance & memory efficiency
- Avoid `null`; consider an explicit `UNKNOWN` constant

### Final Class
- Only mark final when you intentionally forbid extension
- For utility classes: add private constructor
- Prefer composition over forcing `final` unless there’s a clear rationale

---
## 7. Comparisons with Related Concepts
| Concept | POJO | JavaBean | DTO | Entity | Enum | Final Class |
|---------|------|----------|-----|--------|------|-------------|
| Purpose | General data | Framework-friendly component | Data transfer across layers | Persisted model | Fixed set of constants | Prevent inheritance |
| Requires getters/setters | Optional | Yes | Often | Yes (ORM needs) | N/A | N/A |
| Annotations allowed | Yes (but then not “plain”) | Yes | Yes | Yes (e.g., JPA) | Limited | Yes |
| Extends classes? | Optional | Optional | Optional | Often extends nothing | Cannot extend custom | Can’t be extended |

### `enum` vs `static final` Constants
- Type safety
- Encapsulated behavior
- Readability
- Enumeration utilities (`values()`, iteration)

### `final` Keyword (Context Reminder)
| Usage | Meaning |
|-------|---------|
| final variable | Value cannot change after assignment |
| final method | Cannot be overridden in subclass |
| final class | Cannot be subclassed |

---
## 8. Interview Questions & Answers
1. What is a POJO?  
   A lightweight, plain Java object without special restrictions, used mainly to hold data.
2. Difference between POJO and JavaBean?  
   JavaBean enforces private fields + public getters/setters + no-arg constructor + serializable (often). POJO has no such formal rules.
3. Why use enums over `static final` constants?  
   Type safety, readability, scoping, ability to attach behavior & metadata.
4. Can an enum extend a class?  
   No. It implicitly extends `java.lang.Enum`.
5. Can enums implement interfaces?  
   Yes, any number.
6. Why are enum constructors private?  
   To enforce controlled instantiation—only predefined singleton instances exist.
7. How to map from an integer code to enum?  
   Provide a static lookup (e.g., loop or precomputed map) over `values()`.
8. Is using `ordinal()` for persistence recommended?  
   No. Use a stable explicit field.
9. What happens if you add a new enum constant?  
   Code using switches may break (if not covering default). Persisted ordinals become invalid.
10. When to use a `final` class?  
    When you want to prohibit subclassing for security, integrity or stable API design.
11. Can enum constants override methods?  
    Yes—each constant can implement abstract methods or override base ones.
12. How to iterate over enum constants?  
    Use `EnumType.values()`.

---
## 9. Quick Revision Sheet
| Topic | Flash Point |
|-------|-------------|
| POJO | Plain data holder; minimal rules |
| Enum Basics | Named, type-safe constants |
| Enum Advanced | Custom fields, per-constant behavior, implements interfaces |
| Ordinal | Position index (don’t persist) |
| Lookup | Use static method + iterate or map |
| Final Class | Not inheritable |
| Static vs Enum | Enum safer & richer |

---
## 10. Practice Suggestions
- Convert a set of `public static final int` constants into an enum with behavior
- Add a `code` + `label` to an enum and implement a reverse lookup
- Create a mapping layer between request model and domain POJO
- Write an enum implementing an interface (`Strategy` pattern demo)

---
## 11. Common Mistakes to Avoid
- Using `ordinal()` in DB or across services
- Overloading enums with unrelated responsibilities
- Treating JavaBeans + annotations as POJO (adds framework coupling)
- Making everything `final` without justification
- Exposing mutable fields in POJOs (breaks encapsulation)

---
## 12. Real-World Scenario Example
Imagine an order system:
```java
enum OrderState { NEW, PAID, SHIPPED, DELIVERED, CANCELLED; 
    boolean isTerminal() { return this == DELIVERED || this == CANCELLED; }
}
```
Service logic becomes expressive:
```java
if (order.getState().isTerminal()) { archive(order); }
```

---
## 13. Summary
- **POJO**: Simplicity & flexibility for data modeling
- **Enum**: Type-safe, behavior-capable grouped constants; superior to primitive constants
- **Final Class**: Enforces non-inheritability for control & design integrity

These constructs enhance clarity, maintainability, and correctness when used judiciously.

---
Happy learning – revisit this sheet before interviews or coding design sessions!
