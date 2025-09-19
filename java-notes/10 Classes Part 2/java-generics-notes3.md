# Java Generics: Comprehensive Notes

This document provides a detailed summary of Java Generics, based on the provided video transcript. It's structured for quick revision and interview preparation.

---

### 1. The Problem with Using `Object`

Before generics, creating a class that could work with multiple data types involved using the `Object` class.

- **How it worked:** A class would use `Object` for fields and method parameters/return types. Since `Object` is the superclass of all classes in Java, it could hold any object reference.

- **The Drawback:** This approach lacks type safety.
    - **Constant Typecasting:** When retrieving the data, you must explicitly cast it back to its original type.
    - **Runtime Errors:** If you cast to the wrong type, you get a `ClassCastException` at runtime.
    - **Verbose Code:** You need `instanceof` checks to determine the object's actual type before casting, making the code clunky.

**Example:**

```java
// Pre-generics approach
public class Print {
    Object value;

    public void setPrintValue(Object value) {
        this.value = value;
    }

    public Object getPrintValue() {
        return this.value;
    }
}

// Usage
Print p = new Print();
p.setPrintValue(123); // Storing an Integer
Integer intValue = (Integer) p.getPrintValue(); // Requires explicit cast

p.setPrintValue("Hello"); // Now storing a String
// Integer wrongCast = (Integer) p.getPrintValue(); // Throws ClassCastException at runtime
```

---

### 2. Introduction to Generics

Generics were introduced in Java 5 to solve the problems of the `Object`-based approach.

- **Key Concept:** Generics allow you to write a class or method that can work with any data type in a type-safe manner. The type is specified as a parameter.

- **Benefits:**
    - **Stronger Type-Safety:** The compiler enforces type correctness at compile time.
    - **Elimination of Casts:** No need for explicit typecasting.
    - **Compile-Time Error Detection:** Catches invalid type usage during compilation, not at runtime.

#### 2.1. Generic Classes

A generic class is defined with a type parameter in angle brackets (`<>`).

- **Syntax:** `class ClassName<T> { ... }`
    - `T` is a **type parameter**. It's a placeholder for the actual type that will be provided when an object is created.
    - By convention, type parameters are single, uppercase letters (e.g., `T` for Type, `E` for Element, `K` for Key, `V` for Value).

**Example:**

```java
// Generic version of the Print class
public class Print<T> {
    T value;

    public void setPrintValue(T value) {
        this.value = value;
    }

    public T getPrintValue() {
        return this.value;
    }
}

// Usage
// Create a Print object for Integers
Print<Integer> intPrint = new Print<>();
intPrint.setPrintValue(123);
// intPrint.setPrintValue("hello"); // Compile-time error!

Integer val = intPrint.getPrintValue(); // No cast needed

// Create a Print object for Strings
Print<String> stringPrint = new Print<>();
stringPrint.setPrintValue("Hello Generics");
String strVal = stringPrint.getPrintValue(); // No cast needed
```

> **Important Point:** Generic type parameters can only be replaced by **non-primitive types** (e.g., `Integer`, `String`, `Double`). You cannot use primitives like `int`, `double`, etc.

#### 2.2. Multiple Type Parameters

A class can have more than one type parameter.

**Example:**

```java
public class Pair<K, V> {
    private K key;
    private V value;

    public void put(K key, V value) {
        this.key = key;
        this.value = value;
    }
    // ... getters
}

// Usage
Pair<String, Integer> studentGrade = new Pair<>();
studentGrade.put("John Doe", 95);
```

---

### 3. Generic Methods

You can have a generic method within a non-generic (or generic) class. The method has its own type parameter.

- **Syntax:** The type parameter is declared *before* the method's return type.
- **Scope:** The scope of the type parameter is limited to the method itself.

**Example:**

```java
public class GenericMethodExample {
    // This is a generic method
    public <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
}

// Usage
GenericMethodExample gm = new GenericMethodExample();
Integer[] intArray = {1, 2, 3};
String[] stringArray = {"A", "B", "C"};

gm.printArray(intArray);   // T is inferred as Integer
gm.printArray(stringArray); // T is inferred as String
```

---

### 4. Bounded Generics

Bounded generics restrict the types that can be used as arguments for a type parameter.

#### 4.1. Upper Bounded Generics (`extends`)

This restricts the type parameter to be a subtype of a specific class or interface.

- **Syntax:** `<T extends UpperBoundClass>`
- **Meaning:** `T` can be `UpperBoundClass` or any of its subclasses.
- **Keyword:** `extends` is used for both classes and interfaces.

**Example:**

```java
// This class only works with Number and its subclasses (Integer, Double, etc.)
public class NumericPrint<T extends Number> {
    T value;

    public void setValue(T value) {
        this.value = value;
    }
    // ...
}

// Usage
NumericPrint<Integer> intPrint = new NumericPrint<>(); // Valid
NumericPrint<Double> doublePrint = new NumericPrint<>(); // Valid
// NumericPrint<String> stringPrint = new NumericPrint<>(); // Compile-time error!
```

#### 4.2. Multi-Bound Generics

A type parameter can have multiple bounds.

- **Syntax:** `<T extends ClassA & InterfaceB & InterfaceC>`
- **Rule:** The class (if any) must be listed first, followed by interfaces.

**Example:**

```java
// T must be a subclass of ParentClass AND implement both Interface1 and Interface2
class MultiBound<T extends ParentClass & Interface1 & Interface2> {
    // ...
}
```

---

### 5. Wildcards

Wildcards (`?`) are used to create more flexible methods that can handle different generic types. They are particularly useful with collections.

> **Core Problem Wildcards Solve:** A `List<Integer>` is **not** a subtype of `List<Number>`, even though `Integer` is a subtype of `Number`. This is to prevent runtime errors. Wildcards provide a safe way to handle these relationships.

#### 5.1. Upper Bounded Wildcard (`? extends Type`)

- **Meaning:** Represents an unknown type that is a subtype of `Type`. It can be `Type` or any of its children.
- **Use Case:** When you want a method to accept a collection of a certain type *or any of its subtypes*. This is for **read-only** access.
- **PECS Principle:** **P**roducer **E**xtends - If the collection is a producer (you're reading from it), use `extends`.

**Example:**

```java
// This method can print a list of Numbers, Integers, Doubles, etc.
public void printList(List<? extends Number> list) {
    for (Number n : list) {
        System.out.println(n);
    }
    // list.add(5); // Compile-time error! You cannot add to an upper-bounded list.
}
```

#### 5.2. Lower Bounded Wildcard (`? super Type`)

- **Meaning:** Represents an unknown type that is a supertype of `Type`. It can be `Type` or any of its parents.
- **Use Case:** When you want a method to accept a collection where you can safely *add* elements of type `Type`. This is for **write-only** access.
- **PECS Principle:** **C**onsumer **S**uper - If the collection is a consumer (you're writing to it), use `super`.

**Example:**

```java
// This method can add Integers to a List<Integer>, List<Number>, or List<Object>
public void addIntegers(List<? super Integer> list) {
    list.add(10);
    list.add(20);
    // Object obj = list.get(0); // You can only read into an Object.
}
```

#### 5.3. Unbounded Wildcard (`?`)

- **Meaning:** Represents an unknown type. It's equivalent to `? extends Object`.
- **Use Case:** When the method's logic does not depend on the type at all (e.g., methods from the `Object` class like `size()` or `clear()`).

**Example:**

```java
public void printAnyList(List<?> list) {
    // You can call methods that don't depend on the type
    System.out.println("List has " + list.size() + " elements.");
    // You can't add elements (except null) or assume any specific type
}
```

### 6. Type Erasure

- **Concept:** Generics are a compile-time feature. The Java compiler uses generic type information to ensure type safety, but then **erases** it during compilation.
- **How it works:**
    1.  The compiler replaces all type parameters in generic types with their bounds, or with `Object` if the type parameter is unbounded.
    2.  It inserts necessary type casts to preserve type safety.
    3.  The resulting bytecode contains only ordinary classes, interfaces, and methods, ensuring backward compatibility with older JVMs.

**Example:**

```java
// Your generic code
public class Print<T> {
    private T value;
    // ...
}

// What the compiler generates (simplified)
public class Print {
    private Object value; // T is replaced by Object
    // ...
}
```

If the type was bounded (`<T extends Number>`), `T` would be replaced by `Number`.

---

### 7. Interview Questions & Answers

1.  **What are Java Generics and why do we need them?**
    -   Generics provide a way to create type-safe classes and methods. We need them to avoid `ClassCastException` at runtime, eliminate the need for manual typecasting, and allow the compiler to catch type errors early.

2.  **What is Type Erasure?**
    -   It's the process where the compiler removes generic type information after compilation. Type parameters are replaced by their bounds (or `Object`), and casts are inserted. This makes the resulting bytecode compatible with older JVMs that don't understand generics.

3.  **Can you use primitives with generics?**
    -   No. Generics only work with object types. You must use wrapper classes (e.g., `Integer` for `int`, `Double` for `double`).

4.  **What's the difference between `List<?>`, `List<Object>`, and a raw `List`?**
    -   **`List<?>` (Unbounded Wildcard):** A list of an *unknown* type. You cannot safely add any element to it (except `null`) because the compiler doesn't know what type it holds. It's type-safe.
    -   **`List<Object>`:** A list that can hold *any* type of object. You can add any object to it.
    -   **`List` (Raw Type):** A pre-generics legacy type. It behaves like `List<Object>` but bypasses compile-time checks, making it unsafe. You should always avoid raw types.

5.  **Explain the PECS principle.**
    -   **P**roducer **E**xtends, **C**onsumer **S**uper.
    -   Use `extends` (`? extends T`) when you only get (produce) values from a generic structure.
    -   Use `super` (`? super T`) when you only put (consume) values into a generic structure.

---

### 8. Real-World Use Cases

-   **Collections Framework:** The most common use case. `List<String>`, `Map<Integer, Customer>`, etc., are all generic.
-   **Custom Data Structures:** Creating reusable data structures like `Stack<T>`, `Queue<T>`, or `Pair<K, V>`.
-   **API Design:** Designing flexible and type-safe APIs, such as a `Repository<T>` in a data access layer that can handle different entity types (`UserRepository` extends `Repository<User>`).
-   **Optional Class:** `Optional<T>` is a generic container that may or may not contain a non-null value, helping to avoid `NullPointerException`.

---

### 9. Best Practices & Common Pitfalls

-   **Avoid Raw Types:** Never use a generic class without its type parameter (e.g., use `List<String>` not `List`). Raw types defeat the purpose of generics.
-   **Use Specific Bounding:** When possible, use bounded wildcards (`? extends T`, `? super T`) over unbounded (`?`) to make your API clearer and safer.
-   **Follow PECS:** Apply the Producer-Extends, Consumer-Super principle when designing methods that accept generic collections.
-   **Don't Create Generic Arrays:** You cannot create an array of a generic type (e.g., `new T[]` is illegal) due to type erasure. Use `List<T>` instead.
