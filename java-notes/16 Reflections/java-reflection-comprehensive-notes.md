# Java Reflection - Comprehensive Notes

## Learning Objectives
After studying these notes, you should be able to:
- **Understand** what Java Reflection is and when to use it
- **Explain** how the `Class` class works and how JVM creates class objects
- **Demonstrate** three ways to obtain class objects
- **Perform** reflection on classes, methods, fields, and constructors
- **Invoke** methods dynamically using reflection
- **Access and modify** both public and private fields
- **Understand** how reflection breaks encapsulation and singleton patterns
- **Identify** when reflection should and shouldn't be used
- **Apply** best practices to minimize reflection-related issues

---

## Key Concepts & Definitions

### What is Reflection?
**Reflection** is a feature in Java that allows you to:
- **Examine** classes, methods, fields, and interfaces at **runtime**
- **Change the behavior** of classes by modifying field values during execution
- **Invoke methods** and **access fields** dynamically
- **Create objects** using constructors dynamically

### The `Class` Class
- **Definition**: A special class in Java whose instances represent classes and interfaces during runtime
- **Creation**: JVM automatically creates one `Class` object for each class that gets loaded
- **Purpose**: Contains metadata information about the associated class (methods, fields, constructors, modifiers, etc.)
- **Package**: `java.lang.Class`

### Metadata Information Available
- Method names, return types, parameters
- Field names, types, modifiers
- Constructor signatures
- Class modifiers (public, private, abstract, etc.)
- Implemented interfaces
- Superclass information

---

## Step-by-Step Explanation

### 1. Getting a Class Object (3 Ways)

#### Method 1: Using `Class.forName()`
```java
Class<?> clazz = Class.forName("Eagle");
```
- **Use case**: When you have the class name as a string
- **Throws**: `ClassNotFoundException` if class not found

#### Method 2: Using `.class` literal
```java
Class<?> clazz = Eagle.class;
```
- **Use case**: When you know the class at compile time
- **Advantage**: No exception handling needed

#### Method 3: Using `getClass()` method
```java
Eagle eagle = new Eagle();
Class<?> clazz = eagle.getClass();
```
- **Use case**: When you already have an object instance
- **Note**: Called on object instances, not class names

### 2. Reflecting on Classes

```java
public class Eagle {
    public String breed;
    private boolean canSwim;
    
    public void fly() { System.out.println("Flying..."); }
    private void eat() { System.out.println("Eating..."); }
}

// Reflection example
Class<?> eagleClass = Eagle.class;
System.out.println(eagleClass.getName());        // "Eagle"
System.out.println(eagleClass.getModifiers());   // Returns modifier constants
```

### 3. Reflecting on Methods

#### Getting Public Methods Only
```java
Class<?> eagleClass = Eagle.class;
Method[] methods = eagleClass.getMethods(); // Returns ALL public methods (including inherited)

for (Method method : methods) {
    System.out.println("Method: " + method.getName());
    System.out.println("Return Type: " + method.getReturnType());
    System.out.println("Declaring Class: " + method.getDeclaringClass());
}
```

#### Getting All Methods (Public + Private) of Current Class Only
```java
Method[] declaredMethods = eagleClass.getDeclaredMethods(); // Only current class methods

for (Method method : declaredMethods) {
    System.out.println("Method: " + method.getName());
}
```

### 4. Invoking Methods Dynamically

```java
public class Eagle {
    public void fly(int speed, boolean isHigh, String direction) {
        System.out.println("Flying at speed: " + speed + ", High: " + isHigh + ", Direction: " + direction);
    }
}

// Reflection to invoke method
Class<?> eagleClass = Class.forName("Eagle");
Object eagleInstance = eagleClass.newInstance(); // Create object

// Get specific method with parameters
Method flyMethod = eagleClass.getMethod("fly", int.class, boolean.class, String.class);

// Invoke the method
flyMethod.invoke(eagleInstance, 100, true, "North");
```

### 5. Reflecting on Fields

#### Getting Public Fields Only
```java
Class<?> eagleClass = Eagle.class;
Field[] fields = eagleClass.getFields(); // Only public fields

for (Field field : fields) {
    System.out.println("Field: " + field.getName());
    System.out.println("Type: " + field.getType());
    System.out.println("Modifier: " + field.getModifiers());
}
```

#### Getting All Fields (Public + Private)
```java
Field[] declaredFields = eagleClass.getDeclaredFields();

for (Field field : declaredFields) {
    System.out.println("Field: " + field.getName());
    System.out.println("Type: " + field.getType());
}
```

### 6. Setting Field Values

#### Setting Public Field Values
```java
Class<?> eagleClass = Eagle.class;
Object eagleInstance = eagleClass.newInstance();

Field breedField = eagleClass.getDeclaredField("breed");
breedField.set(eagleInstance, "Golden Eagle");

// Access the value
Eagle eagle = (Eagle) eagleInstance;
System.out.println(eagle.breed); // "Golden Eagle"
```

#### Setting Private Field Values
```java
Field canSwimField = eagleClass.getDeclaredField("canSwim");
canSwimField.setAccessible(true); // IMPORTANT: Make private field accessible
canSwimField.set(eagleInstance, true);
```

### 7. Reflecting on Constructors

```java
public class Eagle {
    private Eagle() { // Private constructor
        System.out.println("Eagle created");
    }
    
    public void fly() {
        System.out.println("Flying...");
    }
}

// Breaking private constructor
Class<?> eagleClass = Eagle.class;
Constructor<?>[] constructors = eagleClass.getDeclaredConstructors();

for (Constructor<?> constructor : constructors) {
    System.out.println("Modifier: " + constructor.getModifiers());
    
    constructor.setAccessible(true); // Make private constructor accessible
    Object eagleInstance = constructor.newInstance(); // Create object using private constructor
    
    // Now we can call methods
    Method flyMethod = eagleClass.getMethod("fly");
    flyMethod.invoke(eagleInstance);
}
```

---

## Examples with Code Snippets

### Complete Reflection Example

```java
import java.lang.reflect.*;

public class Eagle {
    public String breed = "Unknown";
    private boolean canSwim = false;
    
    public Eagle() {}
    
    private Eagle(String breed) {
        this.breed = breed;
    }
    
    public void fly() {
        System.out.println(breed + " is flying");
    }
    
    private void eat(String food) {
        System.out.println("Eating " + food);
    }
}

public class ReflectionDemo {
    public static void main(String[] args) throws Exception {
        // 1. Get Class object
        Class<?> eagleClass = Eagle.class;
        
        // 2. Create instance
        Object eagleInstance = eagleClass.newInstance();
        
        // 3. Access and modify public field
        Field breedField = eagleClass.getField("breed");
        breedField.set(eagleInstance, "Golden Eagle");
        
        // 4. Access and modify private field
        Field canSwimField = eagleClass.getDeclaredField("canSwim");
        canSwimField.setAccessible(true);
        canSwimField.set(eagleInstance, true);
        
        // 5. Invoke public method
        Method flyMethod = eagleClass.getMethod("fly");
        flyMethod.invoke(eagleInstance);
        
        // 6. Invoke private method
        Method eatMethod = eagleClass.getDeclaredMethod("eat", String.class);
        eatMethod.setAccessible(true);
        eatMethod.invoke(eagleInstance, "Fish");
        
        // 7. Use private constructor
        Constructor<?> privateConstructor = eagleClass.getDeclaredConstructor(String.class);
        privateConstructor.setAccessible(true);
        Object privateEagle = privateConstructor.newInstance("Private Eagle");
    }
}
```

---

## Diagrams

### Class Object Relationship
```
┌─────────────────┐    JVM Creates    ┌──────────────────┐
│   Your Class    │ ────────────────► │   Class Object   │
│   (e.g., Eagle) │                   │   (Metadata)     │
└─────────────────┘                   └──────────────────┘
                                              │
                                              ▼
                                    ┌──────────────────┐
                                    │   Metadata:      │
                                    │   • Methods      │
                                    │   • Fields       │
                                    │   • Constructors │
                                    │   • Modifiers    │
                                    └──────────────────┘
```

### Reflection Process Flow
```
┌─────────────────┐
│ 1. Get Class    │
│    Object       │
└─────────┬───────┘
          ▼
┌─────────────────┐
│ 2. Get Method/  │
│    Field/       │
│    Constructor  │
└─────────┬───────┘
          ▼
┌─────────────────┐
│ 3. Set          │
│    Accessible   │
│    (if private) │
└─────────┬───────┘
          ▼
┌─────────────────┐
│ 4. Invoke/      │
│    Access/      │
│    Modify       │
└─────────────────┘
```

---

## Common Interview Questions

### Q1: What is Reflection in Java?
**Answer**: Reflection is a feature that allows examining and modifying classes, methods, fields, and constructors at runtime. It provides the ability to inspect and manipulate objects dynamically without knowing their types at compile time.

### Q2: How does Reflection break the Singleton pattern?
**Answer**: 
```java
// Singleton with private constructor
public class Singleton {
    private static Singleton instance;
    private Singleton() { }
    
    public static Singleton getInstance() {
        if (instance == null) instance = new Singleton();
        return instance;
    }
}

// Breaking singleton using reflection
Constructor<?> constructor = Singleton.class.getDeclaredConstructor();
constructor.setAccessible(true);
Singleton instance1 = (Singleton) constructor.newInstance();
Singleton instance2 = (Singleton) constructor.newInstance();
// Now we have two instances!
```

### Q3: What's the difference between `getFields()` and `getDeclaredFields()`?
**Answer**:
- `getFields()`: Returns only **public** fields from current class and **all inherited** fields
- `getDeclaredFields()`: Returns **all fields** (public + private) from **current class only**

### Q4: Why is Reflection slower than direct method calls?
**Answer**: Reflection involves runtime type checking, method lookup, security checks, and dynamic dispatch, while direct calls are resolved at compile time.

### Q5: How can you protect Singleton from Reflection?
**Answer**:
```java
private Singleton() {
    if (instance != null) {
        throw new RuntimeException("Use getInstance() method");
    }
}
```

---

## Hands-on Exercises

### Exercise 1: Basic Reflection
Create a `Student` class with private fields `name`, `age`, and `grade`. Use reflection to:
- Access and print all field names and types
- Set values for all fields
- Create a method to display student info and invoke it

### Exercise 2: Dynamic Method Invocation
Create a `Calculator` class with methods `add`, `subtract`, `multiply`. Use reflection to:
- Get user input for operation name
- Dynamically invoke the corresponding method
- Handle cases where method doesn't exist

### Exercise 3: Breaking Encapsulation
Create a class with private fields and methods. Use reflection to:
- Access private fields and modify their values
- Invoke private methods
- Compare performance with direct access

### Exercise 4: Annotation Processing
Create custom annotations and use reflection to:
- Find classes/methods with specific annotations
- Process annotation values
- Implement a simple dependency injection framework

---

## Real-world Use Cases

### 1. **Frameworks and Libraries**
- **Spring Framework**: Dependency injection, AOP
- **Hibernate**: ORM mapping
- **JUnit**: Test discovery and execution
- **Jackson**: JSON serialization/deserialization

### 2. **Development Tools**
- **IDEs**: Code completion, refactoring
- **Build Tools**: Annotation processing
- **Testing Frameworks**: Mock object creation

### 3. **Plugin Architectures**
- Loading classes dynamically
- Plugin discovery and instantiation
- Configuration-driven object creation

### 4. **Serialization Libraries**
- Converting objects to/from JSON, XML
- Accessing private fields for serialization
- Creating objects without calling constructors

---

## Best Practices & Pitfalls

### ✅ Best Practices

1. **Use Sparingly**: Only when absolutely necessary
2. **Cache Reflection Objects**: Store `Method`, `Field`, `Constructor` objects to avoid repeated lookups
3. **Handle Exceptions**: Always handle `ReflectiveOperationException`
4. **Security Checks**: Use `SecurityManager` in production
5. **Performance Consideration**: Use direct calls when possible

```java
// Cache reflection objects
private static final Method FLY_METHOD;
static {
    try {
        FLY_METHOD = Eagle.class.getMethod("fly");
    } catch (NoSuchMethodException e) {
        throw new RuntimeException(e);
    }
}
```

### ❌ Common Pitfalls

1. **Performance Issues**: Reflection is 10-100x slower than direct calls
2. **Security Vulnerabilities**: Breaking encapsulation
3. **Code Complexity**: Harder to read and maintain
4. **Compile-time Safety**: No type checking
5. **Debugging Difficulty**: Stack traces are complex

### 🐛 Debugging Tips

1. **Use try-catch blocks** for all reflection operations
2. **Print class names** to verify correct classes are loaded
3. **Check method signatures** carefully when using `getMethod()`
4. **Use `getDeclaredMethod()`** for private methods
5. **Remember `setAccessible(true)`** for private members

---

## Comparisons with Related Concepts

### Reflection vs Direct Access
| Aspect | Reflection | Direct Access |
|--------|------------|---------------|
| **Performance** | Slow (runtime resolution) | Fast (compile-time resolution) |
| **Type Safety** | Runtime checking | Compile-time checking |
| **Code Readability** | Complex | Simple |
| **Flexibility** | High | Low |
| **Security** | Can break encapsulation | Respects access modifiers |

### Reflection vs Generics
| Feature | Reflection | Generics |
|---------|------------|----------|
| **Purpose** | Runtime type inspection | Compile-time type safety |
| **When Used** | Dynamic behavior | Type parameterization |
| **Performance** | Runtime overhead | No runtime overhead |
| **Type Erasure** | Can work around it | Affected by it |

---

## Memory Hooks & Mnemonics

### 🧠 Memory Aids

1. **"R.E.F.L.E.C.T"**:
   - **R**untime inspection
   - **E**xamine classes
   - **F**ields and methods
   - **L**oad dynamically
   - **E**ncapsulation breaking
   - **C**onstructor access
   - **T**ype information

2. **"3 Ways to Get Class"**:
   - **C**lass.forName() - **C**lass name as string
   - **.class** - **C**ompile-time known
   - **g**etClass() - **G**ot object already

3. **"Private Access Pattern"**:
   - **G**et the member (field/method/constructor)
   - **S**et accessible to true
   - **U**se it (set/invoke/newInstance)

---

## Quick Revision Cheat Sheet

### Essential Classes & Packages
```java
java.lang.Class           // Main reflection class
java.lang.reflect.Method  // For method reflection
java.lang.reflect.Field   // For field reflection
java.lang.reflect.Constructor // For constructor reflection
```

### Key Method Patterns
```java
// Getting Class object
Class.forName("ClassName")
ClassName.class
object.getClass()

// Methods
getMethods()        // Public methods (including inherited)
getDeclaredMethods() // All methods of current class only

// Fields  
getFields()         // Public fields (including inherited)
getDeclaredFields() // All fields of current class only

// Constructors
getConstructors()         // Public constructors
getDeclaredConstructors() // All constructors

// For private access
member.setAccessible(true)
```

### Common Exceptions
- `ClassNotFoundException` - Class not found
- `NoSuchMethodException` - Method not found
- `NoSuchFieldException` - Field not found
- `IllegalAccessException` - Access denied
- `InvocationTargetException` - Exception in invoked method

### Performance Tips
- Cache reflection objects
- Use direct access when possible
- Consider alternatives (interfaces, design patterns)
- Benchmark reflection vs alternatives

---

## Summary

**Reflection** is a powerful but expensive feature in Java that allows runtime inspection and manipulation of classes. While it enables dynamic behavior and is essential for frameworks, it should be used judiciously due to performance implications and security concerns. The key is understanding when reflection is truly necessary versus when simpler alternatives exist.

**Key Takeaway**: *"With great power comes great responsibility"* - Use reflection only when the benefits outweigh the costs in terms of performance, maintainability, and security.