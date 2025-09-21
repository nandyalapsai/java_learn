# Java Generics - Quick Reference Guide 🚀

## 🎯 Essential Concepts (30-Second Review)

| Concept | Syntax | Purpose |
|---------|--------|---------|
| Generic Class | `class MyClass<T>` | Type-safe collections/classes |
| Generic Method | `<T> void method(T param)` | Type-safe methods |
| Upper Bound | `<T extends Number>` | Restrict to Number and subclasses |
| Multi-Bound | `<T extends Class & Interface>` | Multiple constraints |
| Upper Wildcard | `List<? extends Number>` | Read-only, accepts Number subtypes |
| Lower Wildcard | `List<? super Integer>` | Write-only, accepts Integer supertypes |
| Unbounded | `List<?>` | Unknown type, read as Object |

---

## 📋 Syntax Cheat Sheet

### Generic Class Declaration
```java
// Basic
public class Box<T> { private T item; }

// Multiple types
public class Pair<K, V> { private K key; private V value; }

// Bounded
public class NumberBox<T extends Number> { private T number; }

// Multi-bound
public class ConstrainedBox<T extends MyClass & Serializable> { }
```

### Generic Method Declaration
```java
// Basic generic method
public <T> void process(T item) { }

// Multiple type parameters
public <T, U> void combine(T first, U second) { }

// Bounded generic method
public <T extends Comparable<T>> T max(T a, T b) { }

// Generic method with return type
public <T> List<T> createList(T... items) { }
```

### Object Creation
```java
// Parameterized (recommended)
List<String> list = new ArrayList<String>();
List<String> list = new ArrayList<>();  // Diamond operator

// Raw type (avoid)
List list = new ArrayList();  // ⚠️ Generates warnings
```

---

## 🔧 Wildcards Quick Guide

### Upper Bound Wildcards (? extends)
```java
List<? extends Number> numbers;
// ✅ Can read as Number
// ❌ Cannot add (except null)
// ✅ Accept: List<Integer>, List<Double>, List<Number>

void process(List<? extends Shape> shapes) {
    for (Shape shape : shapes) {  // ✅ Safe to read
        shape.draw();
    }
    // shapes.add(new Circle());  // ❌ Compile error
}
```

### Lower Bound Wildcards (? super)
```java
List<? super Integer> numbers;
// ✅ Can add Integer and subtypes
// ❌ Reading returns Object
// ✅ Accept: List<Integer>, List<Number>, List<Object>

void addNumbers(List<? super Integer> list) {
    list.add(42);           // ✅ Can add Integer
    list.add(new Integer(5)); // ✅ Can add Integer
    Object obj = list.get(0); // ❌ Returns Object only
}
```

### Unbounded Wildcards (?)
```java
List<?> anything;
// ✅ Accept any type of List
// ❌ Cannot add anything (except null)
// ❌ Can only read as Object

void printSize(List<?> list) {
    System.out.println(list.size());  // ✅ Object methods only
    for (Object item : list) {         // ✅ Read as Object
        System.out.println(item);
    }
}
```

---

## 🧠 PECS Principle

**P**roducer **E**xtends, **C**onsumer **S**uper

```java
// Producer (you READ from it) → use extends
List<? extends T> producer;  // Can read T objects

// Consumer (you WRITE to it) → use super  
List<? super T> consumer;    // Can write T objects

// Example
Collections.copy(List<? super T> dest,      // Consumer
                List<? extends T> src);     // Producer
```

---

## ⚡ Common Patterns

### 1. Generic DAO Pattern
```java
public interface GenericDAO<T, ID> {
    T findById(ID id);
    List<T> findAll();
    void save(T entity);
    void delete(ID id);
}
```

### 2. Builder Pattern
```java
public class Builder<T> {
    public Builder<T> with(Consumer<T> setter) { return this; }
    public T build() { return object; }
}
```

### 3. Response Wrapper
```java
public class ApiResponse<T> {
    private T data;
    private String message;
    private boolean success;
}
```

### 4. Generic Utility Methods
```java
public static <T> List<T> asList(T... items) { }
public static <T extends Comparable<T>> T max(Collection<T> items) { }
public static <T> void swap(List<T> list, int i, int j) { }
```

---

## 🚫 Common Mistakes & Fixes

| ❌ Wrong | ✅ Correct | 💡 Explanation |
|----------|------------|----------------|
| `List<Object> list = new ArrayList<String>();` | `List<String> list = new ArrayList<>();` | No inheritance between generic types |
| `List rawList = new ArrayList();` | `List<String> list = new ArrayList<>();` | Avoid raw types |
| `new T[10]` | `(T[]) new Object[10]` | Cannot create generic arrays directly |
| `List<? extends Number> list; list.add(5);` | `List<Integer> list; list.add(5);` | Cannot add to ? extends |
| `if (obj instanceof List<String>)` | `if (obj instanceof List<?>)` | Type erasure removes generic info |

---

## 🔄 Type Erasure Rules

| Before Compilation | After Compilation (Bytecode) |
|-------------------|------------------------------|
| `<T>` | `Object` |
| `<T extends Number>` | `Number` |
| `<T extends Interface>` | `Object` |
| `List<String>` | `List` |
| `Map<K,V>` | `Map` |

---

## 🎯 Interview Quick Answers

**Q: Difference between `List<?>` and `List<Object>`?**
A: `List<?>` can reference any parameterized list; `List<Object>` only holds Object references.

**Q: Can you add to `List<? extends Number>`?**
A: No, only null. You don't know the exact type.

**Q: Why use wildcards?**
A: Flexibility in APIs. `List<? extends Number>` accepts `List<Integer>`, `List<Double>`, etc.

**Q: What is type erasure?**
A: Generic type information is removed at runtime for backward compatibility.

**Q: Raw types vs Parameterized types?**
A: Raw types lose type safety; parameterized types provide compile-time type checking.

---

## 📊 Bounds Comparison

| Type | Syntax | Can Read | Can Write | Accepts |
|------|--------|----------|-----------|---------|
| Exact | `List<Integer>` | ✅ as Integer | ✅ Integer | Only `List<Integer>` |
| Upper | `List<? extends Number>` | ✅ as Number | ❌ (except null) | `List<Integer>`, `List<Double>`, etc. |
| Lower | `List<? super Integer>` | ❌ (only Object) | ✅ Integer+ | `List<Integer>`, `List<Number>`, `List<Object>` |
| Unbounded | `List<?>` | ❌ (only Object) | ❌ (except null) | Any `List<T>` |

---

## 🏃‍♂️ 2-Minute Practice

```java
// Fix these code snippets:

// 1. Generic class
class Box { private Object item; } // Add generics

// 2. Method signature  
public void process(List<Object> items) { } // Make flexible

// 3. Bounded generic
class NumberContainer<T> { } // Restrict to numbers only

// 4. Generic method
public Object max(Object a, Object b) { } // Make generic & comparable

// 5. Wildcard usage
List<Vehicle> vehicles = new ArrayList<Car>(); // Fix assignment
```

### Solutions:
```java
// 1. class Box<T> { private T item; }
// 2. public void process(List<?> items) { }
// 3. class NumberContainer<T extends Number> { }
// 4. public <T extends Comparable<T>> T max(T a, T b) { }
// 5. List<? extends Vehicle> vehicles = new ArrayList<Car>();
```

---

## 📱 Mobile-Friendly Summary

**Generics = Type Safety + No Casting**

**Classes:** `MyClass<T>`  
**Methods:** `<T> void method(T param)`  
**Bounds:** `<T extends SomeClass>`  
**Wildcards:** `<?>`, `<? extends>`, `<? super>`  
**PECS:** Producer Extends, Consumer Super  
**Erasure:** Generics removed at runtime  

**Remember:** Use generics for type safety, wildcards for flexibility! 🎯

---

*Keep this handy for quick revision! 📚✨*