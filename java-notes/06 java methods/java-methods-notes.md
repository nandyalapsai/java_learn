# Java Methods – Comprehensive Summary & Interview-Ready Notes

> Quick revision + conceptual depth + interview prep.

---
## 1. Big Picture
A **method** in Java is a named block of code (collection of instructions) that performs a specific task, optionally receives input (parameters), and may return a value. Methods enable:
- Reusability
- Abstraction & organization of logic
- Readability / intention revealing
- Testability & maintainability

```
                +-------------------------+
Input Params →  |        METHOD           |  → (Optional Return Value)
                |  logic / computation    |
                +-------------------------+
```

---
## 2. Method Declaration Anatomy
```
[Access Specifier] [Optional Modifiers] ReturnType methodName(ParameterList) [throws ExceptionList] {
    // method body
    [return value;]  // if non-void
}
```
| Part | Meaning |
|------|---------|
| Access Specifier | Visibility (public / protected / (default) / private) |
| Modifiers | static, final, abstract, synchronized, etc. |
| Return Type | Type of value returned (or `void`) |
| Name | Verb-like, camelCase identifier |
| Parameter List | Zero or more typed parameters |
| throws clause | Declares checked exceptions that may escape |
| Body | Executable instructions |

### Example
```java
public int sum(int a, int b) {  // public = visible everywhere
    int total = a + b;          // body / logic
    return total;               // returns int
}
```

---
## 3. Access Specifiers (Visibility)
| Specifier | Access Scope | Cross-Package? | In Subclass (Different Package)? | Typical Use |
|-----------|--------------|----------------|----------------------------------|-------------|
| public    | Everywhere   | Yes            | Yes                              | API methods |
| protected | Package + subclasses | Only via inheritance | Yes (if subclass) | Reusable base logic |
| (default) | Package-only | No             | No                               | Internal collaboration |
| private   | Same class only | No          | No                               | Encapsulation of details |

### Protected vs Default Quick Visual
```
Same Package: protected ✓  default ✓
Different Package, Subclass: protected ✓  default ✗
Different Package, Non-subclass: protected ✗ default ✗
```

---
## 4. Return Type
- `void` → no value returned (optional plain `return;` allowed)
- Primitive (int, double, boolean, etc.) → must return matching value
- Reference type → must return object or `null`
- Cannot return a value from a `void` method

```java
public boolean isAdult(int age) { return age >= 18; }
public void log() { System.out.println("Logged"); } // no return value
```

---
## 5. Naming Conventions
- Verb-centric: `calculateTax`, `printInvoice`, `findUser`
- camelCase, start lowercase
- Avoid abbreviations unless common (`getId`, not `gI`)

---
## 6. Parameters vs Arguments
| Term | Context |
|------|---------|
| Parameter | Variable listed in method declaration (`int a`) |
| Argument  | Actual value passed during invocation (`sum(2, 5)`) |

- Pass-by-value in Java (object references are copied by value)

### Varargs (Variable Arguments)
```java
int sum(int... nums) {          // nums behaves like int[] inside
    int total = 0;
    for (int n : nums) total += n;
    return total;
}

sum();            // 0
sum(1);           // 1
sum(1,2,3,4,5);   // 15
```
Rules:
1. Only one varargs parameter allowed
2. Must be the last parameter: `void log(String label, int... values)`

---
## 7. throws Clause (Brief Preview)
Declares checked exceptions a method may propagate.
```java
public void readFile(String path) throws IOException { /* ... */ }
```
(Callers must handle or rethrow.) Detailed handling covered in exception handling topic.

---
## 8. Types / Categories of Methods
### 8.1 System-Defined (Library) Methods
Provided by Java API (e.g., `Math.sqrt(25)`, `Collections.sort(list)`).

### 8.2 User-Defined Methods
Authored by developers to solve application-specific needs.

### 8.3 Overloaded Methods (Compile-Time Polymorphism)
Same method name, different parameter lists (type/order/count). Return type alone does NOT distinguish overload.
```java
public void log() {}
public void log(String msg) {}
public void log(String msg, int level) {}
```
Invalid:
```java
int log() {}      // if only differed by return type vs existing void log()
```

### 8.4 Overridden Methods (Runtime Polymorphism)
Subclass provides new implementation of a superclass method with same signature.
```java
class Person { void profession() { System.out.println("Generic person"); } }
class Doctor extends Person { @Override void profession() { System.out.println("Doctor"); } }
```
Dynamic dispatch based on actual object type at runtime.

### 8.5 Static Methods
- Belong to the class, not instances
- Call via class name: `Math.max(a,b)` or `Calculator.add(2,3)`
- Cannot access instance members directly
- Not truly overridden (can be hidden if re-declared in subclass)

```java
class Util {
    static int square(int n) { return n * n; }
}
int s = Util.square(5);
```

### 8.6 Final Methods
Cannot be overridden.
```java
class Base { final void init() { /* fixed algorithm */ } }
class Derived extends Base { /* cannot override init() */ }
```

### 8.7 Abstract Methods
Declared without body inside an abstract class (or interface).
```java
abstract class Shape { abstract double area(); }
class Circle extends Shape { double area() { return Math.PI * r * r; } double r; }
```

### 8.8 Varargs Methods
Covered earlier—flexible number of arguments; avoids explosion of overloads.

---
## 9. Static vs Instance Methods (Comparison)
| Aspect | Static | Instance |
|--------|--------|----------|
| Bound To | Class | Object |
| Access | `ClassName.method()` | `object.method()` |
| Can access instance fields? | No (unless via object reference) | Yes |
| Overridable? | No (can hide) | Yes |
| Typical Use | Utilities, factories, helpers | Behavior relying on object state |

---
## 10. Overloading vs Overriding (Summary)
| Feature | Overloading | Overriding |
|---------|------------|------------|
| Binding Time | Compile-time | Runtime |
| Location | Same class (or inherited + re-declared w/ diff params) | Parent–child classes |
| Signature Needed | Different parameter list | Identical signature |
| Return Type | Can differ | Must be covariant/same |
| Purpose | Convenience / flexibility | Specialization / polymorphism |

---
## 11. ASCII Class / Inheritance Snapshot
```
Person (profession())
   ^
   |
Doctor (overrides profession())
```
Static hiding example:
```
Base.staticMethod()
Derived.staticMethod()  // Hides, not overrides
```

---
## 12. Real-World Use Cases
| Scenario | Method Type / Pattern |
|----------|-----------------------|
| Utility math operations | Static methods (`Math`, custom `Util`) |
| Service layer operations | Instance methods using entity state |
| Factory creation (`of`, `valueOf`) | Static factory methods |
| Polymorphic calculations (e.g., pricing rules) | Overridden methods in subclass hierarchy |
| Plugin architecture | Abstract methods / interfaces |
| Logging / instrumentation | Overloaded `log()` variants |
| Aggregation of variable inputs | Varargs methods (`sendEmails(String... recipients)`) |

---
## 13. Best Practices
- Keep methods short, single-responsibility
- Use intention-revealing names (avoid generic `doWork()`)
- Prefer immutable parameters (don’t mutate inputs unless documented)
- Use static for pure, stateless utilities only
- Avoid excessive overloading that harms clarity
- Document side effects & thread-safety expectations
- Validate parameters early (fail fast)
- Return specific types (avoid `Object` unless necessary)
- Prefer `Optional<T>` (Java 8+) instead of returning null when absence is meaningful
- Keep visibility as restrictive as possible (`private` > package > protected > public)

### When to Make a Method `static`
- Uses only its parameters (pure function)
- Does not read/modify instance state
- Utility / helper / factory logic

### When NOT to Make it `static`
- Depends on or mutates instance fields
- Needs polymorphic behavior (overriding)
- Needs lazy per-instance caching/state

---
## 14. Common Pitfalls
| Pitfall | Why It’s a Problem | Avoid By |
|---------|--------------------|----------|
| Overusing static | Hinders testability & extensibility | Use DI / instances |
| Confusing overloading with overriding | Leads to unexpected dispatch | Re-check signatures |
| Relying on side effects | Hard to reason | Favor pure functions |
| Returning null collections | Forces null checks | Return empty (`Collections.emptyList()`) |
| Huge parameter lists | Reduces readability | Use parameter object / builder |
| Silent swallowing of exceptions | Masks issues | Log or rethrow appropriately |
| Varargs misuse w/ primitives & boxing | Performance hit | Use overloads for hot paths |

---
## 15. Interview Questions (With Brief Answers)
1. Difference between overloading and overriding? → Compile-time vs runtime polymorphism; differing parameters vs identical signature.
2. Can we overload `main`? → Yes, but only standard signature is JVM entry point.
3. Can a constructor be `static`? → No; constructors are invoked on object creation.
4. Why can't static methods access instance members directly? → No instance context (`this`) is available.
5. Are static methods inherited? → Yes, but they are hidden—not overridden.
6. What is method signature in Java? → Name + parameter list (types/order/arity); excludes return type & access modifiers.
7. Can return type differ in overriding? → Yes, if covariant (subtype of original return).
8. Varargs internal representation? → As an array; `int...` becomes `int[]`.
9. Why use final methods? → To lock down critical algorithms (security, template methods).
10. Abstract vs Interface method (modern Java)? → Interfaces can have default & static; abstract class can hold state.
11. When would you prefer composition over method overriding? → When behavior should be swappable at runtime (strategy pattern).
12. Can a private method be overridden? → No; it’s not visible to subclasses.
13. Order of resolution in overriding call? → Subclass → parent chain upwards until match.
14. What happens if you change only return type attempting overload? → Compilation error (duplicate method).
15. Performance of static vs instance? → Negligible difference; design concern > micro-optimization.

---
## 16. Usage Patterns & Design Notes
| Pattern | Method Role |
|---------|-------------|
| Factory Method | Static creator (`List.of(...)`) |
| Template Method | `final` method calling abstract hooks |
| Strategy | Methods defined in interchangeable strategy classes |
| Fluent APIs | Chained instance methods returning `this` |
| Builder | Methods assembling immutable object state |

---
## 17. Example Consolidated Code
```java
public class Calculator {
    private int base;                // instance state
    private static int globalCount;  // shared state

    public Calculator(int base) { this.base = base; }

    // Pure utility – candidate static
    public static int add(int a, int b) { return a + b; }

    // Uses instance state – instance method
    public int incrementBase(int delta) { return base += delta; }

    // Varargs
    public static int sum(int... nums) {
        int total = 0;
        for (int n : nums) total += n;
        return total;
    }

    // Overloaded
    public void log() { System.out.println("log()"); }
    public void log(String msg) { System.out.println(msg); }

    // Final – cannot be overridden
    public final void audit() { System.out.println("Auditing"); }
}
```

---
## 18. Comparison Cheat Sheet
```
PUBLIC    > Widest scope
PROTECTED > Package + subclass
DEFAULT   > Package only
PRIVATE   > Class only

OVERLOAD  : Same name, diff params (compile-time)
OVERRIDE  : Inheritance, same signature (runtime)
STATIC    : Class-level, no instance context
FINAL     : Cannot be overridden
ABSTRACT  : Declared, implemented by subclass
VARARGS   : Flexible argument count
```

---
## 19. Performance & Design Considerations
- Prefer clarity over premature optimization
- Minimize shared mutable static state (thread-safety risk)
- Use immutability & pure functions for deterministic behavior
- Reduce branching in hot methods; isolate complexity

---
## 20. Quick Revision Block
```
Method = reusable named block
Anatomy = access + modifiers + return + name + params + throws + body
Polymorphism: Overload (compile) vs Override (runtime)
Static = class-bound | Instance = object-bound
Use varargs to avoid overload explosion
Restrict visibility. Favor pure, small methods
```

---
## 21. Practice Prompts
- Implement a `Formatter` class with overloaded `format()` methods.
- Create an abstract `Payment` with `process()`, implement `CardPayment`, `UPIPayment`.
- Refactor repetitive arithmetic helpers into static utility methods.
- Replace multiple `(int,int,int)` overloads with a single varargs method.

---
## 22. Key Takeaways
- Understand the WHY behind method kinds, not just syntax
- Design for readability first, extensibility second, optimization third
- Avoid static abuse; embrace composition & interfaces
- Mastery of method mechanics underpins clean OOP & design patterns

---
Happy learning & revisit before interviews! 🚀
