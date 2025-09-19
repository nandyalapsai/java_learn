# Java Generics - Comprehensive Notes

## Table of Contents
1. [Key Concepts & Definitions](#key-concepts--definitions)
2. [Why Generics Are Needed](#why-generics-are-needed)
3. [Generic Classes](#generic-classes)
4. [Generic Methods](#generic-methods)
5. [Inheritance with Generics](#inheritance-with-generics)
6. [Raw Types](#raw-types)
7. [Bounded Generics](#bounded-generics)
8. [Wildcards](#wildcards)
9. [Type Erasure](#type-erasure)
10. [Interview Questions](#interview-questions)
11. [Best Practices & Common Pitfalls](#best-practices--common-pitfalls)
12. [Real-World Use Cases](#real-world-use-cases)

---

## Key Concepts & Definitions

### What are Generics?
- **Generics** allow you to write classes and methods in a generic manner to avoid typecasting
- They provide **type safety** at compile time
- Use **diamond syntax** `<T>` where T is a type parameter
- Type parameters are placeholders that get replaced with actual types during compilation

### Important Terminology
- **Type Parameter**: Generic placeholder (e.g., T, K, V)
- **Parameterized Type**: Generic type with specific type argument (e.g., `List<String>`)
- **Raw Type**: Generic type used without type parameters
- **Bounded Type**: Type parameter with restrictions using `extends` or `super`
- **Wildcard**: `?` used in method parameters for flexibility

---

## Why Generics Are Needed

### Problem with Object Class
```java
public class Print {
    private Object value;
    
    public void setPrintValue(Object value) {
        this.value = value;
    }
    
    public Object getPrintValue() {
        return value;
    }
}

// Usage problems:
Print obj = new Print();
obj.setPrintValue(1);                    // Integer
Object result = obj.getPrintValue();
int value = (int) result;                // Manual typecasting required!

obj.setPrintValue("Hello");              // String
// Now we don't know what type is stored!
```

### Issues with Object Approach
- **Manual typecasting required** - Error-prone and verbose
- **No compile-time type safety** - Runtime ClassCastException possible
- **Need instanceof checks** to determine actual type
- **Code readability suffers** due to casting complexity

---

## Generic Classes

### Basic Syntax
```java
public class Print<T> {
    private T value;
    
    public void setPrintValue(T value) {
        this.value = value;
    }
    
    public T getPrintValue() {
        return value;
    }
}
```

### Usage
```java
// Create type-specific instances
Print<Integer> intPrint = new Print<>();
intPrint.setPrintValue(10);
Integer value = intPrint.getPrintValue(); // No casting needed!

Print<String> stringPrint = new Print<>();
stringPrint.setPrintValue("Hello");
String text = stringPrint.getPrintValue();

// Compile-time error prevention
// intPrint.setPrintValue("Hello"); // ❌ Compile error!
```

### Multiple Type Parameters
```java
public class Pair<K, V> {
    private K key;
    private V value;
    
    public void put(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    public K getKey() { return key; }
    public V getValue() { return value; }
}

// Usage
Pair<String, Integer> pair = new Pair<>();
pair.put("age", 25);

// Both syntaxes are valid:
Pair<String, Integer> pair1 = new Pair<String, Integer>();
Pair<String, Integer> pair2 = new Pair<>(); // Diamond operator
```

---

## Generic Methods

### Syntax
```java
public class GenericMethodExample {
    // Generic method syntax: <T> before return type
    public <T> void setValue(T value) {
        // Method implementation
        System.out.println("Value: " + value);
    }
    
    // Multiple type parameters
    public <T, U> void processValues(T first, U second) {
        // Process both values
    }
}
```

### Usage Example
```java
GenericMethodExample obj = new GenericMethodExample();
obj.setValue(new Bus());    // Accepts Bus object
obj.setValue(new Car());    // Accepts Car object
obj.setValue("Hello");      // Accepts String
obj.setValue(123);          // Accepts Integer
```

### Key Properties
- **Type parameter scope**: Limited to the method only
- **Declaration**: Type parameter must be declared before return type
- **Flexibility**: Can accept any type without making entire class generic

---

## Inheritance with Generics

### Non-Generic Subclass
```java
// Parent generic class
public class Print<T> {
    protected T value;
    // ... methods
}

// Non-generic child class - must specify type at inheritance
public class ColorPrint extends Print<String> {
    // Can only work with String now
}

// Usage
ColorPrint colorPrint = new ColorPrint();
colorPrint.setPrintValue("Blue"); // Only String allowed
```

### Generic Subclass
```java
// Generic child class
public class ColorPrint<T> extends Print<T> {
    // Inherits generic behavior
}

// Usage - specify type at object creation
ColorPrint<String> colorPrint = new ColorPrint<>();
colorPrint.setPrintValue("Blue");
```

---

## Raw Types

### Definition and Example
```java
// Parameterized type (recommended)
Print<String> stringPrint = new Print<>();

// Raw type (not recommended)
Print rawPrint = new Print(); // Equivalent to Print<Object>

// Raw type behavior
rawPrint.setPrintValue(1);      // Accepts Integer
rawPrint.setPrintValue("Hello"); // Accepts String
Object value = rawPrint.getPrintValue(); // Returns Object
```

### Problems with Raw Types
- **Loss of type safety**
- **Compiler warnings**
- **Back to manual typecasting**
- **Defeats the purpose of generics**

---

## Bounded Generics

### Upper Bound (`extends`)
```java
// Only Number and its subclasses allowed
public class NumberProcessor<T extends Number> {
    private T value;
    
    public void setValue(T value) {
        this.value = value;
    }
    
    public double getDoubleValue() {
        return value.doubleValue(); // Can call Number methods
    }
}

// Usage
NumberProcessor<Integer> intProcessor = new NumberProcessor<>(); // ✅ OK
NumberProcessor<Double> doubleProcessor = new NumberProcessor<>(); // ✅ OK
// NumberProcessor<String> stringProcessor = new NumberProcessor<>(); // ❌ Error!
```

### Multi-Bound
```java
// Class hierarchy
class ParentClass { }
interface Interface1 { }
interface Interface2 { }

class A extends ParentClass implements Interface1, Interface2 { }

// Multi-bound generic
public class MultiBoundExample<T extends ParentClass & Interface1 & Interface2> {
    // T must extend ParentClass AND implement both interfaces
}

// Usage
MultiBoundExample<A> example = new MultiBoundExample<>(); // ✅ OK
```

### Rules for Multi-Bound
- **First parameter must be a class** (if any)
- **Subsequent parameters must be interfaces**
- **Use `&` to separate multiple bounds**

---

## Wildcards

### Upper Bound Wildcard (`? extends`)
```java
// Method accepting Number and its subtypes
public void processNumbers(List<? extends Number> numbers) {
    // Can read as Number, but cannot add (except null)
    for (Number num : numbers) {
        System.out.println(num.doubleValue());
    }
    // numbers.add(10); // ❌ Compile error!
}

// Usage
List<Integer> intList = Arrays.asList(1, 2, 3);
List<Double> doubleList = Arrays.asList(1.1, 2.2, 3.3);
processNumbers(intList);    // ✅ OK
processNumbers(doubleList); // ✅ OK
```

### Lower Bound Wildcard (`? super`)
```java
// Method accepting Number and its supertypes
public void addNumbers(List<? super Integer> numbers) {
    // Can add Integer and its subtypes
    numbers.add(10);
    numbers.add(20);
    // Object obj = numbers.get(0); // Can only read as Object
}

// Usage
List<Number> numberList = new ArrayList<>();
List<Object> objectList = new ArrayList<>();
addNumbers(numberList);  // ✅ OK
addNumbers(objectList);  // ✅ OK
```

### Unbounded Wildcard (`?`)
```java
public void printList(List<?> list) {
    // Can only use Object methods
    for (Object item : list) {
        System.out.println(item.toString());
    }
}

// Accepts any type of list
printList(Arrays.asList(1, 2, 3));
printList(Arrays.asList("a", "b", "c"));
printList(Arrays.asList(new Bus(), new Car()));
```

### Wildcard vs Generic Method Comparison

| Aspect | Wildcard `<? extends T>` | Generic Method `<T extends Number>` |
|--------|-------------------------|-------------------------------------|
| **Flexibility** | Different types allowed | Same type enforced |
| **Multiple Types** | One wildcard only | Multiple type parameters possible |
| **Lower Bound** | Supports `super` | No `super` support |
| **Type Relationships** | More flexible | More restrictive but structured |

```java
// Wildcard - allows different types
public void wildcardMethod(List<? extends Number> source, 
                          List<? extends Number> destination) {
    // source can be List<Integer>, destination can be List<Double>
}

// Generic - enforces same type
public <T extends Number> void genericMethod(List<T> source, List<T> destination) {
    // Both must be same type: both List<Integer> or both List<Double>
}
```

---

## Type Erasure

### What is Type Erasure?
Type erasure is the process where generic type information is removed during compilation, replaced with actual types or bounds.

### Examples

#### Unbounded Generics
```java
// Source code
public class GenericClass<T> {
    private T value;
    public T getValue() { return value; }
}

// After type erasure (bytecode equivalent)
public class GenericClass {
    private Object value;           // T becomes Object
    public Object getValue() { return value; }
}
```

#### Bounded Generics
```java
// Source code
public class BoundedGeneric<T extends Number> {
    private T value;
    public T getValue() { return value; }
}

// After type erasure
public class BoundedGeneric {
    private Number value;           // T becomes Number
    public Number getValue() { return value; }
}
```

#### Generic Methods
```java
// Source code
public <T> void process(T item) { }

// After type erasure
public void process(Object item) { } // T becomes Object
```

---

## Interview Questions

### 1. **What are Java Generics and why are they important?**
**Answer**: Generics provide type safety at compile time, eliminate the need for explicit typecasting, and enable programmers to write more generic and reusable code. They prevent ClassCastException at runtime by catching type mismatches during compilation.

### 2. **What is the difference between `List<?>` and `List<Object>`?**
**Answer**: 
- `List<?>` is an unbounded wildcard that can represent a list of any type
- `List<Object>` specifically contains Object references
- You cannot add elements to `List<?>` (except null), but you can add to `List<Object>`

### 3. **Can you use primitive types with generics?**
**Answer**: No, generics only work with reference types. For primitives, use wrapper classes:
```java
List<int> numbers;     // ❌ Error
List<Integer> numbers; // ✅ Correct
```

### 4. **What is type erasure and what are its implications?**
**Answer**: Type erasure removes generic type information at runtime. Implications include:
- Cannot use `instanceof` with parameterized types
- Cannot create arrays of parameterized types
- Cannot create instances of type parameters

### 5. **Explain the difference between `extends` and `super` in wildcards**
**Answer**: 
- `? extends T`: Upper bound - accepts T and its subtypes (read-only)
- `? super T`: Lower bound - accepts T and its supertypes (write-friendly)
- **PECS Principle**: Producer Extends, Consumer Super

### 6. **What are raw types and why should they be avoided?**
**Answer**: Raw types are generic classes used without type parameters. They should be avoided because they:
- Lose type safety
- Generate compiler warnings
- Require manual typecasting
- Can cause ClassCastException at runtime

---

## Best Practices & Common Pitfalls

### Best Practices

#### 1. **Use Meaningful Type Parameter Names**
```java
// Good
public class KeyValuePair<K, V> { }
public class Repository<T, ID> { }

// Avoid
public class KeyValuePair<A, B> { }
```

#### 2. **Apply PECS Principle**
- **Producer Extends**: Use `? extends T` when reading from a collection
- **Consumer Super**: Use `? super T` when writing to a collection

```java
// Producer - reading data
public void processData(List<? extends Number> source) {
    for (Number num : source) { /* read data */ }
}

// Consumer - writing data
public void addData(List<? super Integer> destination) {
    destination.add(42); /* write data */
}
```

#### 3. **Prefer Generics Over Raw Types**
```java
// Good
List<String> names = new ArrayList<>();

// Avoid
List names = new ArrayList(); // Raw type
```

#### 4. **Use Bounded Wildcards for API Flexibility**
```java
// More flexible
public void processNumbers(Collection<? extends Number> numbers) { }

// Less flexible
public void processNumbers(Collection<Number> numbers) { }
```

### Common Pitfalls

#### 1. **Generic Array Creation**
```java
// ❌ Cannot create generic arrays
List<String>[] arrays = new List<String>[10]; // Compile error

// ✅ Use List of Lists instead
List<List<String>> listOfLists = new ArrayList<>();
```

#### 2. **Type Parameter Shadowing**
```java
public class Container<T> {
    // ❌ Shadows class-level T
    public <T> void method(T param) { }
    
    // ✅ Use different name
    public <U> void method(U param) { }
}
```

#### 3. **Static Context Issues**
```java
public class GenericClass<T> {
    // ❌ Cannot use T in static context
    private static T staticField;
    
    // ✅ Make method generic instead
    public static <U> void staticMethod(U param) { }
}
```

#### 4. **Wildcard Misuse**
```java
// ❌ Too restrictive
public void process(List<Object> list) { }

// ✅ More flexible
public void process(List<?> list) { }
```

---

## Real-World Use Cases

### 1. **Collections Framework**
```java
List<String> names = new ArrayList<>();
Map<String, User> userCache = new HashMap<>();
Set<Integer> uniqueIds = new HashSet<>();
```

### 2. **Data Access Objects (DAO)**
```java
public interface GenericDAO<T, ID> {
    T findById(ID id);
    List<T> findAll();
    void save(T entity);
    void delete(ID id);
}

public class UserDAO implements GenericDAO<User, Long> {
    // Implementation for User entity
}
```

### 3. **Response Wrappers**
```java
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    
    // Constructor and methods
}

// Usage
ApiResponse<User> userResponse = new ApiResponse<>();
ApiResponse<List<Product>> productsResponse = new ApiResponse<>();
```

### 4. **Builder Pattern**
```java
public class QueryBuilder<T> {
    private StringBuilder query = new StringBuilder();
    
    public QueryBuilder<T> select(String... columns) {
        // Build SELECT clause
        return this;
    }
    
    public QueryBuilder<T> where(String condition) {
        // Build WHERE clause
        return this;
    }
    
    public List<T> execute() {
        // Execute query and return results
        return new ArrayList<>();
    }
}
```

### 5. **Event Handling**
```java
public interface EventListener<T extends Event> {
    void onEvent(T event);
}

public class UserEventListener implements EventListener<UserEvent> {
    @Override
    public void onEvent(UserEvent event) {
        // Handle user-specific events
    }
}
```

### 6. **Repository Pattern**
```java
public abstract class BaseRepository<T, ID> {
    protected Class<T> entityClass;
    
    public BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    public abstract T findById(ID id);
    public abstract List<T> findAll();
    public abstract void save(T entity);
}

public class ProductRepository extends BaseRepository<Product, Long> {
    public ProductRepository() {
        super(Product.class);
    }
    
    // Product-specific methods
}
```

---

## Summary

Generics are a powerful feature in Java that provide:
- **Type safety** at compile time
- **Elimination of explicit typecasting**
- **Better code readability and maintainability**
- **Reusable and flexible code**

Key concepts to remember:
- Use bounded generics for type restrictions
- Apply PECS principle for wildcards
- Understand type erasure implications
- Prefer generics over raw types
- Use meaningful type parameter names

Practice these concepts extensively to master Java generics and prepare for interviews effectively!