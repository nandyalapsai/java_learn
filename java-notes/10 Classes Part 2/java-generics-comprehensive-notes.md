# Java Generics - Comprehensive Study Notes

## Learning Objectives 🎯

After studying these notes, you should be able to:
- Understand why generics are needed and their benefits over using Object class
- Create and use generic classes with type parameters
- Implement generic methods independently of generic classes
- Apply bounded generics with upper bounds and multi-bounds
- Use wildcards (upper bound, lower bound, unbounded) effectively
- Understand inheritance relationships with generic classes
- Differentiate between parameterized types and raw types
- Explain type erasure and its implications
- Choose between wildcards and generic type parameters appropriately

---

## Why Generics Are Needed 🤔

### Problem with Object Class Approach

**Before Generics:**
```java
public class Print {
    private Object value;
    
    public void setPrintValue(Object value) {
        this.value = value;
    }
    
    public Object getPrintValue() {
        return this.value;
    }
}

// Usage - Requires typecasting
Print obj = new Print();
obj.setPrintValue(1);
int result = (int) obj.getPrintValue(); // Manual typecasting required

obj.setPrintValue("Hello");
// Now we don't know if it's int or String - runtime confusion!
```

**Problems:**
1. **Type Safety**: No compile-time type checking
2. **Typecasting Required**: Manual casting needed for retrieval
3. **Runtime Errors**: ClassCastException possible
4. **Code Clarity**: Unclear what types are expected

---

## Generic Classes 📦

### Basic Syntax and Structure

```java
// Generic class syntax
public class ClassName<T> {
    private T value;
    
    public void setValue(T value) {
        this.value = value;
    }
    
    public T getValue() {
        return this.value;
    }
}
```

### Diamond Syntax Example

```java
public class Print<T> {
    private T value;
    
    public void setPrintValue(T value) {
        this.value = value;
    }
    
    public T getPrintValue() {
        return this.value;
    }
}

// Usage - Type-safe without casting
Print<Integer> intPrint = new Print<>();
intPrint.setPrintValue(1);
int result = intPrint.getPrintValue(); // No casting needed!

Print<String> stringPrint = new Print<>();
stringPrint.setPrintValue("Hello");
String text = stringPrint.getPrintValue();
```

### Key Points:
- **T** is a type parameter (can use any letter: A, B, K, V, etc.)
- Type parameter can only be **non-primitive types** (classes, interfaces, arrays)
- Provides **compile-time type safety**
- Eliminates need for **explicit typecasting**

---

## Multiple Type Parameters 🔄

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

// Both syntax valid:
Pair<String, Integer> pair1 = new Pair<String, Integer>();
Pair<String, Integer> pair2 = new Pair<>(); // Diamond operator
```

---

## Generic Methods 🛠️

### Syntax and Structure

```java
public class Utility {
    // Generic method syntax
    public <T> void setValue(T obj) {
        // Method implementation
        System.out.println("Received: " + obj);
    }
    
    // Multiple type parameters
    public <T, U> void processData(T first, U second) {
        // Implementation
    }
    
    // Generic method with return type
    public <T> T processAndReturn(T input) {
        // Process input
        return input;
    }
}

// Usage
Utility util = new Utility();
util.setValue(new Bus());    // T becomes Bus
util.setValue(new Car());    // T becomes Car
util.setValue("Hello");      // T becomes String
```

### Generic Method Properties:
- Type parameter declared **before return type**
- Type parameter scope **limited to method only**
- Can be used in **non-generic classes**
- Can have **multiple type parameters**

---

## Inheritance with Generics 👨‍👩‍👧‍👦

### Non-Generic Subclass

```java
// Parent generic class
public class Print<T> {
    protected T value;
    
    public void setValue(T value) { this.value = value; }
    public T getValue() { return this.value; }
}

// Non-generic subclass - must specify type at inheritance
public class ColorPrint extends Print<String> {
    private String color;
    
    public void setColor(String color) {
        this.color = color;
    }
}

// Usage
ColorPrint colorPrint = new ColorPrint();
colorPrint.setValue("Hello"); // Only String allowed
```

### Generic Subclass

```java
// Generic subclass - type specified at object creation
public class ColorPrint<T> extends Print<T> {
    private String color;
    
    public void setColor(String color) {
        this.color = color;
    }
}

// Usage - Type specified during object creation
ColorPrint<String> stringColorPrint = new ColorPrint<>();
ColorPrint<Integer> intColorPrint = new ColorPrint<>();
```

---

## Raw Types ⚠️

### Definition and Usage

```java
// Parameterized type (recommended)
Print<String> parameterizedPrint = new Print<>();

// Raw type (not recommended)
Print rawPrint = new Print(); // No type parameter

// Raw type behavior
rawPrint.setPrintValue(1);        // Accepts anything
rawPrint.setPrintValue("Hello");  // Accepts anything
Object result = rawPrint.getPrintValue(); // Returns Object
```

### Important Notes:
- Raw types exist for **backward compatibility**
- Internally treated as `Print<Object>`
- **Lose type safety benefits**
- Generate **compiler warnings**

---

## Bounded Generics 🔒

### Upper Bound Wildcards

```java
// Upper bound - T extends Number
public class NumberPrint<T extends Number> {
    private T value;
    
    public void setValue(T value) { this.value = value; }
    public T getValue() { return this.value; }
}

// Usage
NumberPrint<Integer> intPrint = new NumberPrint<>();    // ✅ Valid
NumberPrint<Double> doublePrint = new NumberPrint<>();   // ✅ Valid
NumberPrint<String> stringPrint = new NumberPrint<>();   // ❌ Compile Error!
```

**Number Class Hierarchy:**
```
Object
  └── Number
      ├── Integer
      ├── Double
      ├── Float
      ├── Long
      ├── BigInteger
      └── BigDecimal
```

### Multi-Bound Generics

```java
// Multiple bounds: class + interfaces
public class MultiPrint<T extends ParentClass & Interface1 & Interface2> {
    private T value;
}

// Example class structure
class A extends ParentClass implements Interface1, Interface2 {
    // Implementation
}

// Usage
MultiPrint<A> print = new MultiPrint<>(); // ✅ Valid - meets all bounds
```

**Multi-Bound Rules:**
- **First bound must be a class** (if any)
- **Subsequent bounds must be interfaces**
- Use `&` to separate multiple bounds
- Class can implement unlimited interfaces

---

## Wildcards 🃏

### Understanding the Problem

```java
// This seems logical but doesn't work
List<Vehicle> vehicleList = new ArrayList<>();
List<Bus> busList = new ArrayList<>();

// ❌ Compile Error - List<Bus> is NOT a subtype of List<Vehicle>
vehicleList = busList; // Invalid!

// Why? Type safety!
// If allowed, we could do:
vehicleList.add(new Car()); // Adding Car to what should be Bus list!
```

### Upper Bound Wildcards (? extends)

```java
public void processVehicles(List<? extends Vehicle> vehicles) {
    // Can read from list
    for (Vehicle v : vehicles) {
        v.start(); // Safe to call Vehicle methods
    }
    
    // Cannot add to list (except null)
    // vehicles.add(new Car()); // ❌ Compile Error
}

// Usage
List<Vehicle> vehicleList = new ArrayList<>();
List<Bus> busList = new ArrayList<>();
List<Car> carList = new ArrayList<>();

processVehicles(vehicleList); // ✅ Valid
processVehicles(busList);     // ✅ Valid  
processVehicles(carList);     // ✅ Valid
```

### Lower Bound Wildcards (? super)

```java
public void addVehicles(List<? super Vehicle> vehicles) {
    // Can add Vehicle and its subtypes
    vehicles.add(new Vehicle()); // ✅ Valid
    vehicles.add(new Bus());     // ✅ Valid
    vehicles.add(new Car());     // ✅ Valid
    
    // Reading returns Object
    Object obj = vehicles.get(0); // Returns Object
}

// Usage
List<Object> objectList = new ArrayList<>();
List<Vehicle> vehicleList = new ArrayList<>();
List<Bus> busList = new ArrayList<>(); // ❌ Invalid - Bus is below Vehicle

addVehicles(objectList);  // ✅ Valid - Object is super of Vehicle
addVehicles(vehicleList); // ✅ Valid - Vehicle itself
```

### Unbounded Wildcards (?)

```java
public void printListSize(List<?> list) {
    System.out.println("Size: " + list.size());
    
    // Can only use Object methods
    for (Object obj : list) {
        System.out.println(obj.toString());
    }
    
    // Cannot add anything (except null)
    // list.add("test"); // ❌ Compile Error
}

// Usage - accepts any type of list
printListSize(new ArrayList<String>());
printListSize(new ArrayList<Integer>());
printListSize(new ArrayList<Vehicle>());
```

---

## Wildcards vs Generic Type Parameters 🆚

### When to Use Wildcards

```java
// Flexible - allows different types for source and destination
public void copyData(List<? extends Number> source, 
                    List<? super Number> destination) {
    for (Number num : source) {
        destination.add(num);
    }
}

// Usage
List<Integer> intList = Arrays.asList(1, 2, 3);
List<Number> numList = new ArrayList<>();
copyData(intList, numList); // ✅ Different types allowed
```

### When to Use Generic Type Parameters

```java
// Restrictive - enforces same type for both parameters
public <T extends Number> void copyDataStrict(List<T> source, List<T> destination) {
    for (T item : source) {
        destination.add(item);
    }
}

// Usage
List<Integer> intSource = Arrays.asList(1, 2, 3);
List<Integer> intDest = new ArrayList<>();
List<Double> doubleDest = new ArrayList<>();

copyDataStrict(intSource, intDest);    // ✅ Same type
copyDataStrict(intSource, doubleDest); // ❌ Different types not allowed
```

### Comparison Table

| Feature | Wildcards | Generic Type Parameters |
|---------|-----------|------------------------|
| Flexibility | High - different types allowed | Low - same type enforced |
| Multiple Parameters | Single `?` per parameter | Multiple `<T, U, V>` possible |
| Lower Bound | Supported (`? super`) | Not supported |
| Method Scope | Parameter-specific | Entire method |
| Return Type Usage | Limited | Full usage possible |

---

## Type Erasure 🗑️

### Concept and Implementation

Type erasure removes generic type information at runtime for backward compatibility.

### Before Compilation (Source Code)
```java
// Generic class
public class Container<T> {
    private T value;
    public void setValue(T value) { this.value = value; }
    public T getValue() { return this.value; }
}

// Bounded generic
public class NumberContainer<T extends Number> {
    private T value;
    public void setValue(T value) { this.value = value; }
}

// Generic method
public <T> void process(T item) {
    System.out.println(item);
}
```

### After Compilation (Bytecode)
```java
// Unbounded generic → Object
public class Container {
    private Object value;
    public void setValue(Object value) { this.value = value; }
    public Object getValue() { return this.value; }
}

// Bounded generic → Upper bound type
public class NumberContainer {
    private Number value;
    public void setValue(Number value) { this.value = value; }
}

// Generic method → Object
public void process(Object item) {
    System.out.println(item);
}
```

### Type Erasure Rules:
- **Unbounded types** (`<T>`) → `Object`
- **Bounded types** (`<T extends SomeClass>`) → `SomeClass`
- **Bridge methods** created for method overriding
- **Type information lost** at runtime

---

## Class Hierarchy Diagrams 📊

### Number Class Hierarchy
```
Object
  │
  └── Number (abstract)
      ├── Byte
      ├── Short  
      ├── Integer
      ├── Long
      ├── Float
      ├── Double
      ├── BigInteger
      └── BigDecimal
```

### Collection Hierarchy with Generics
```
Collection<E>
  │
  ├── List<E>
  │   ├── ArrayList<E>
  │   ├── LinkedList<E>
  │   └── Vector<E>
  │
  ├── Set<E>
  │   ├── HashSet<E>
  │   ├── LinkedHashSet<E>
  │   └── TreeSet<E>
  │
  └── Queue<E>
      ├── LinkedList<E>
      └── PriorityQueue<E>
```

### Wildcard Bounds Visualization
```
? extends Vehicle
    ↑ (can read)
    │
Vehicle
    │
    ├── Bus
    ├── Car
    └── Truck

? super Vehicle  
    │ (can write)
    ↓
Object
    │
    └── Vehicle
```

---

## Common Interview Questions 💼

### 1. **Q: What is the difference between `List<?>` and `List<Object>`?**
**A:** 
- `List<?>` is an unbounded wildcard that can hold a list of any unknown type
- `List<Object>` specifically holds Object references
- `List<String>` is a subtype of `List<?>` but NOT of `List<Object>`

### 2. **Q: Can you add elements to `List<? extends Number>`?**
**A:** No, you cannot add elements (except null) because the compiler doesn't know the exact type. You can only read elements as Number.

### 3. **Q: What is PECS principle?**
**A:** **Producer Extends, Consumer Super**
- Use `? extends` when you only read from a collection (producer)
- Use `? super` when you only write to a collection (consumer)

### 4. **Q: Why can't we create arrays of generic types like `new T[10]`?**
**A:** Due to type erasure, generic type information is lost at runtime, but arrays need type information for ArrayStoreException checking.

### 5. **Q: What are raw types and why should we avoid them?**
**A:** Raw types are generic classes used without type parameters. They lose type safety and generate compiler warnings.

---

## Hands-on Exercises 🔧

### Exercise 1: Generic Stack Implementation
```java
public class GenericStack<T> {
    // TODO: Implement using ArrayList
    // Methods: push(T), pop(), peek(), isEmpty(), size()
}
```

### Exercise 2: Bounded Generic Utility
```java
public class NumberUtils {
    // TODO: Create method to find max of two numbers
    // public static <T extends Comparable<T>> T max(T a, T b)
    
    // TODO: Create method to sum list of numbers
    // public static <T extends Number> double sum(List<T> numbers)
}
```

### Exercise 3: Wildcard Practice
```java
public class WildcardPractice {
    // TODO: Method to copy from any Number list to Number list
    // public static void copy(List<? extends Number> source, List<? super Number> dest)
    
    // TODO: Method to print any list
    // public static void printList(List<?> list)
}
```

---

## Real-world Use Cases 🌍

### 1. **Collection Framework**
```java
List<String> names = new ArrayList<>();
Map<String, Integer> ages = new HashMap<>();
Set<Long> ids = new HashSet<>();
```

### 2. **DAO Pattern**
```java
public interface GenericDAO<T, ID> {
    T findById(ID id);
    List<T> findAll();
    void save(T entity);
    void delete(ID id);
}

public class UserDAO implements GenericDAO<User, Long> {
    // Implementation
}
```

### 3. **Builder Pattern**
```java
public class GenericBuilder<T> {
    private T object;
    
    public GenericBuilder<T> with(Consumer<T> setter) {
        setter.accept(object);
        return this;
    }
    
    public T build() {
        return object;
    }
}
```

### 4. **Response Wrapper**
```java
public class ApiResponse<T> {
    private T data;
    private String message;
    private boolean success;
    
    // Constructor and methods
}

// Usage
ApiResponse<User> userResponse = new ApiResponse<>(user, "Success", true);
ApiResponse<List<Product>> productsResponse = new ApiResponse<>(products, "Found", true);
```

---

## Best Practices & Common Pitfalls ⚠️

### ✅ Best Practices

1. **Use diamond operator for cleaner code**
   ```java
   // Good
   List<String> list = new ArrayList<>();
   
   // Verbose
   List<String> list = new ArrayList<String>();
   ```

2. **Prefer bounded wildcards for API flexibility**
   ```java
   // Good - flexible API
   public void addNumbers(List<? extends Number> numbers) { }
   
   // Restrictive
   public void addNumbers(List<Number> numbers) { }
   ```

3. **Use meaningful type parameter names**
   ```java
   // Good
   public class Cache<K, V> { } // Key, Value
   public interface Comparable<T> { } // Type being compared
   
   // Less clear
   public class Cache<X, Y> { }
   ```

### ❌ Common Pitfalls

1. **Raw type usage**
   ```java
   // Avoid
   List list = new ArrayList(); // Raw type
   
   // Use
   List<String> list = new ArrayList<>();
   ```

2. **Incorrect wildcard usage**
   ```java
   // Wrong - cannot add to ? extends
   List<? extends Number> numbers = new ArrayList<Integer>();
   numbers.add(5); // Compile error!
   
   // Correct
   List<Integer> numbers = new ArrayList<>();
   numbers.add(5);
   ```

3. **Confusing wildcards with inheritance**
   ```java
   // Wrong assumption
   List<Integer> intList = new ArrayList<>();
   List<Number> numList = intList; // Compile error!
   
   // Correct with wildcards
   List<? extends Number> numList = intList; // Works!
   ```

---

## Debugging Tips 🐛

### 1. **Generic Type Mismatch Errors**
```java
// Error: Required List<String>, found List<Object>
List<Object> objects = new ArrayList<>();
List<String> strings = objects; // ❌ Error

// Solution: Use appropriate wildcards
List<? super String> container = objects; // ✅ Works
```

### 2. **Cannot Instantiate Generic Array**
```java
// Error
T[] array = new T[10]; // ❌ Cannot create array of T

// Solution
@SuppressWarnings("unchecked")
T[] array = (T[]) new Object[10]; // ✅ Works but unchecked
```

### 3. **ClassCastException with Raw Types**
```java
// Problem
List rawList = new ArrayList();
rawList.add("String");
rawList.add(42);
String s = (String) rawList.get(1); // ❌ Runtime exception!

// Solution: Use parameterized types
List<String> stringList = new ArrayList<>();
stringList.add("String");
// stringList.add(42); // ❌ Compile-time error - much better!
```

---

## Memory Hooks & Mnemonics 🧠

### 1. **PECS Principle**
- **P**roducer **E**xtends
- **C**onsumer **S**uper
- "If you're taking OUT (producing), use extends"
- "If you're putting IN (consuming), use super"

### 2. **Diamond Syntax Memory**
- Think of `<>` as an empty diamond that gets "filled" with the left side type
- `List<String> list = new ArrayList<>();` → Diamond gets filled with String

### 3. **Wildcard Bounds**
- `? extends` → "Question mark going UP the inheritance tree"
- `? super` → "Question mark going DOWN the inheritance tree"

### 4. **Type Erasure**
- Remember: "Java generics are a compile-time feature"
- "At runtime, all cats look like Objects in the dark"

---

## Quick Reference Cheat Sheet 📚

### Generic Class Declaration
```java
public class ClassName<T> { }                    // Single type
public class ClassName<T, U> { }                 // Multiple types
public class ClassName<T extends Number> { }     // Bounded type
public class ClassName<T extends Class & Interface> { } // Multi-bound
```

### Generic Method Declaration
```java
public <T> void method(T param) { }              // Generic method
public <T extends Number> T method(T param) { }  // Bounded generic method
```

### Wildcard Usage
```java
List<? extends Number> list1;    // Upper bound - can read as Number
List<? super Integer> list2;     // Lower bound - can write Integer
List<?> list3;                   // Unbounded - can read as Object
```

### Common Generic Collections
```java
List<E> list = new ArrayList<>();
Set<E> set = new HashSet<>();
Map<K,V> map = new HashMap<>();
Queue<E> queue = new LinkedList<>();
```

### Type Erasure Results
- `<T>` → `Object`
- `<T extends SomeClass>` → `SomeClass`
- `<T extends Interface>` → `Object` (interfaces erase to Object)

### Inheritance Rules
- `List<String>` is NOT a subtype of `List<Object>`
- Use wildcards for flexibility: `List<? extends Object>`
- Raw types lose all type safety

### Method Return Types
```java
<T> T method()                    // Returns T
<T> List<T> method()             // Returns List of T
<T> void method(T param)         // Void return, T parameter
```

---

## Summary 📝

Java Generics provide:
- **Type Safety**: Compile-time type checking
- **Elimination of Casts**: No manual typecasting needed
- **Enabling Generic Algorithms**: Write code that works with different types

Key components:
- **Generic Classes**: `class MyClass<T>`
- **Generic Methods**: `<T> void method(T param)`
- **Bounded Types**: `<T extends SomeClass>`
- **Wildcards**: `<?>`, `<? extends>`, `<? super>`
- **Type Erasure**: Runtime type information removal

Remember: Generics are a compile-time feature that provides type safety without runtime overhead.

---

*Happy Learning! 🚀*