# Java Generics – Comprehensive Notes

## 1. Overview
Generics allow you to create classes, interfaces, and methods with type parameters so that you can write reusable, type-safe code without excessive casting. They enforce compile-time type checking and improve API clarity.

---
## 2. Key Concepts & Definitions
- **Generic Type / Type Parameter**: A placeholder (e.g., `T`, `E`, `K`, `V`) for an actual reference type provided at usage time.
- **Diamond Operator (`<>`)**: Syntax used to specify or infer generic types, e.g., `List<String>`.
- **Raw Type**: Using a generic class without specifying a type parameter, e.g., `List list;`. Treated as legacy; compiles with warnings.
- **Generic Class**: A class declared with type parameters: `class Box<T> { ... }`.
- **Generic Method**: A method with its own type parameter(s): `<T> void print(T val)`.
- **Type Inference**: Compiler deduces the actual type (e.g., `var box = new Box<>(10);`).
- **Bounded Type Parameter**: Constrains type arguments, e.g., `<T extends Number>`.
- **Multiple Bounds**: `<T extends Base & Interface1 & Interface2>` (class first, then interfaces).
- **Wildcards**: Unknown type placeholder `?` with optional bounds.
  - `? extends T` (Upper bound – read / covariant-like)
  - `? super T` (Lower bound – write / contravariant-like)
  - `?` (Unbounded)
- **Type Erasure**: Generics are a compile-time feature; type info is removed in bytecode.

---
## 3. Why Generics? (Problems They Solve)
| Problem (Pre-generics) | With Generics |
|------------------------|---------------|
| Need casting from `Object` | Eliminates unsafe casts |
| Runtime `ClassCastException` | Catches errors at compile time |
| Ambiguous API contracts | Clear intent (e.g., `List<String>`) |
| Code duplication | Reusable generic algorithms |

---
## 4. Basic Generic Class Example
```java
public class Print<T> {       // T is a type parameter
    private T value;
    public void setValue(T value) { this.value = value; }
    public T getValue() { return value; }
}

public class Main {
    public static void main(String[] args) {
        Print<Integer> p = new Print<>();
        p.setValue(10);              // Only Integer allowed
        int val = p.getValue();      // No cast needed
    }
}
```

### Without Generics (Problematic)
```java
public class Print {           // Raw style
    private Object value;
    public void setValue(Object v) { value = v; }
    public Object getValue() { return value; }
}

Print p = new Print();
p.setValue("Hello");
Integer x = (Integer) p.getValue(); // Runtime failure (ClassCastException)
```

---
## 5. Generic Subclasses
### Non-Generic Subclass of Generic Superclass
```java
class Print<T> { /* ... */ }
class ColorPrint extends Print<String> { /* Fixed to String */ }
```
### Generic Subclass
```java
class ColorPrint<T> extends Print<T> { /* Still generic */ }
```

---
## 6. Multiple Type Parameters
```java
public class Pair<K, V> {
    private K key; private V value;
    public void put(K k, V v) { this.key = k; this.value = v; }
    public K getKey() { return key; }
    public V getValue() { return value; }
}

Pair<String, Integer> p = new Pair<>();
p.put("age", 30);
```

---
## 7. Generic Methods
```java
public class Util {
    // <T> before return type declares a method-level type parameter
    public static <T> void printTwice(T value) {
        System.out.println(value);
        System.out.println(value);
    }
}
Util.printTwice(42);        // T inferred as Integer
Util.printTwice("Hi");      // T inferred as String
```
- Scope of `T` here is limited to the method (unlike class-level generic parameters).

---
## 8. Raw Types
```java
Print raw = new Print();          // Raw type – discouraged
raw.setValue(10);
raw.setValue("Hi");              // Allowed (no type safety)
Integer v = (Integer) raw.getValue(); // May fail at runtime
```
- Internally treated as `Print<Object>` but with unchecked warnings.
- Avoid except for interoperability with legacy code.

---
## 9. Bounded Type Parameters
### Upper Bound
```java
class NumberBox<T extends Number> {  // Accepts Integer, Double, Long, etc.
    T num;
}
```
### Multiple Bounds
```java
class A {}
interface I1 {}
interface I2 {}

class Multi<T extends A & I1 & I2> { /* ... */ }
```
Rules:
- At most one class bound.
- Class bound must appear first.
- Any number of interface bounds may follow.

---
## 10. Wildcards
### Motivation
`List<Vehicle>` is NOT a supertype of `List<Car>` even if `Car extends Vehicle`.

### 10.1 Upper Bounded Wildcard – `? extends T`
Read (covariant-like). You can read as `T` but cannot safely add (except `null`).
```java
void process(List<? extends Number> list) {
    Number n = list.get(0); // OK
    // list.add(5);         // Compile error
}
```
Use when: You only need to read, not mutate.

### 10.2 Lower Bounded Wildcard – `? super T`
Write (contravariant-like). Accepts `T` and its supertypes. Safe to add `T`.
```java
void addAllIntegers(List<? super Integer> list) {
    list.add(10);  // OK
    // Integer i = list.get(0); // Returns Object
}
```
Use when: You want to insert `T` values.

### 10.3 Unbounded Wildcard – `?`
```java
void printAll(List<?> list) {
    for (Object o : list) System.out.println(o);
}
```
Use when: Logic depends only on methods of `Object`.

### 10.4 PECS Principle
Producer Extends, Consumer Super:
- If a parameter produces data for you: `? extends T`
- If a parameter consumes data you put in: `? super T`

### 10.5 Wildcards vs Generic Type Parameters
| Use Case | Prefer |
|----------|--------|
| Require relationship between multiple parameters | Generic type param `<T>` |
| Only one parameter & variance needed | Wildcard |
| Need lower bound (`super`) | Wildcard |
| Need multiple independent types | Generic type params |

Example Difference:
```java
// Wildcard – allows different actual types for src and dst
void copyWildcard(List<? extends Number> src, List<? super Number> dst) { }

// Generic – enforces same type for src & dst
<T extends Number> void copySame(List<T> src, List<T> dst) { }
```

---
## 11. Variance Analogy (Informal)
- `List<? extends Number>` ~ read-only (cannot add except `null`).
- `List<? super Integer>` ~ write-focused (can add Integer, read as Object).
- `List<Number>` ~ invariant (exact match required).

---
## 12. Type Erasure
At compile time:
- All generic type info removed (erased) in bytecode.
- Unbounded type params → `Object`.
- Bounded params → first bound.
- Bridge methods may be synthesized to preserve polymorphism.

Example:
```java
class Box<T> { T val; T get() { return val; } }
// Erased form (conceptually):
class Box { Object val; Object get() { return val; } }
```
Implications:
- No runtime generic type checks (type erasure).
- Cannot use reflection to get `T` at runtime reliably (unless via tricks like TypeToken patterns).

---
## 13. Common Interview Questions (with Brief Answers)
1. Why use generics? – Type safety, no casting, better APIs, reuse.
2. Difference between `List<Object>` and `List<?>`? – `List<Object>` allows adding any object; `List<?>` is read-only (unknown element type).
3. Can primitives be used as type arguments? – No, use wrappers (`int` → `Integer`).
4. Why can’t we do `new T()`? – Type erasure removes actual type; no guarantee of constructor.
5. What is PECS? – Producer Extends, Consumer Super (guideline for choosing wildcards).
6. Difference between `? super T` and `? extends T`? – `extends` for reading, `super` for writing.
7. What is type erasure? – Removal of generic type info during compilation.
8. Can you overload methods by generic type? – No, type erasure causes signature clash.
9. Raw type vs unbounded wildcard? – Raw loses all type safety; `?` keeps safety (read-only).
10. Multiple bounds syntax? – `<T extends Class & Interface1 & Interface2>`.

---
## 14. Real-World Use Cases
- Collections API (`List<T>`, `Map<K,V>`)
- DAO layers returning domain objects
- Cache utilities (`Cache<K,V>`)
- Event systems (Listener patterns with payload types)
- Generic response wrappers in REST (`Response<T>`) 
- Comparator / Strategy abstractions
- Stream API intermediate representations

---
## 15. Best Practices
- Prefer descriptive type names in complex contexts: `<T>` → `<E>`, `<K,V>`, `<R>`.
- Use wildcards (`?`) for API flexibility when callers don’t need to supply/ensure exact types.
- Apply PECS consistently.
- Avoid raw types except for legacy interop.
- Don’t overcomplicate signatures—favor clarity.
- Validate assumptions with `Objects.requireNonNull` when needed.
- Minimize exposure of mutable generic internal structures (return unmodifiable views).

### Common Pitfalls
| Pitfall | Explanation |
|---------|-------------|
| Using raw types | Loses type safety (unchecked warnings). |
| Overusing `? extends` and then needing mutation | You can’t add elements. |
| Assuming inheritance with generics (`List<Dog>` → `List<Animal>`) | Invariance breaks assumption. |
| Casting to concrete generic types | Causes `ClassCastException` risk. |
| Creating arrays of parameterized types (`new List<String>[10]`) | Not allowed (use `List<List<String>>`). |
| Relying on runtime type of `T` | Erased at runtime. |
| Shadowing type parameters | Confuses API users. |

---
## 16. Comparisons with Related Concepts
| Concept | Generics | Inheritance | Overloading |
|---------|----------|------------|-------------|
| Purpose | Type abstraction | Behavior reuse | Alternative method signatures |
| Time | Compile-time | Runtime dispatch | Compile-time resolution |
| Safety | High (static) | Depends | High |
| Flexibility | Parameterized types | Polymorphism | Arity/type variations |

| Feature | Wildcards | Type Params |
|---------|-----------|-------------|
| Lower bound (`super`) | Yes | No (method generic param cannot do `super`) |
| Enforce same type across params | Hard | Easy |
| Multiple independent types | No | Yes |
| Simplicity for one-off | Good | Overhead |

---
## 17. Visual Diagrams
### 17.1 Class / Type Hierarchy (Example)
```
Object
 └── Number
      ├── Integer
      ├── Double
      ├── Long
      └── Float
```

### 17.2 Wildcard Relations (Read vs Write)
```
List<? extends Number>   // Read as Number, no adds
List<? super Integer>    // Can add Integer, read as Object
List<Number>             // Exact type
```

### 17.3 Multiple Bounds Skeleton
```
<T extends Base & Interface1 & Interface2>
        ^class first  ^interfaces follow
```

---
## 18. When to Choose What
| Goal | Use |
|------|-----|
| API wants max flexibility (read-only) | `? extends` |
| Need to insert values safely | `? super` |
| Require same param types | `<T>` generic method |
| Only work with `Object`-level ops | `?` (unbounded) |
| Limit accepted domain models | Bounded: `<T extends Base>` |
| Combine traits | Multiple bounds |

---
## 19. Mini Cheat Sheet
- Invariant: `List<Number>` ≠ `List<Integer>`
- Covariant-like: `? extends T` (can read T, cannot add)
- Contravariant-like: `? super T` (can add T, reads as Object)
- PECS: Producer Extends, Consumer Super
- Prefer composition of generics over deep hierarchy complexity

---
## 20. Practice Suggestions
- Implement a generic cache: `Cache<K,V>` with eviction.
- Write a `copy` utility using both wildcard & generic versions.
- Experiment with compile errors when adding to `? extends` collections.
- Create a bounded generic for numeric aggregation.

---
## 21. Summary
Generics enhance type safety, readability, and reusability. Understanding the distinctions among raw types, parameterized types, wildcards, bounds, and erasure equips you to design clean, robust Java APIs. Master PECS, choose between wildcards and type parameters based on constraints, and avoid overengineering signatures.

---
### Quick Revision (Flash Points)
- No primitives as type args.
- Wildcards for flexibility; type params for relationships.
- `? extends T` → cannot add (except null).
- `? super T` → can add T; reads are Object.
- Type erasure removes generics at runtime.
- Multiple bounds: one class + many interfaces.
- Raw types = warnings + unsafe.

---
Happy Learning!
