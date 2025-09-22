# Java Annotations - Comprehensive Study Notes

## Learning Objectives
After studying these notes, you should be able to:
- Understand what annotations are and their purpose in Java
- Use predefined annotations (@Override, @Deprecated, @SuppressWarnings, etc.)
- Understand meta-annotations and their role
- Create custom annotations
- Access annotations using reflection
- Apply annotations in real-world scenarios
- Answer interview questions about annotations

## 1. What are Annotations?

### Definition
- **Annotations** are a form of **metadata** that provide additional information about Java code
- They start with the `@` symbol (e.g., `@Override`)
- **Optional** - code works without them, but they provide extra functionality
- Can be accessed at **runtime** using **reflection**

### Key Characteristics
- **Metadata**: Data about data
- **Optional usage**: Won't break code if not used
- **Runtime access**: Can be read during program execution
- **Flexible placement**: Can be applied to classes, methods, fields, parameters, etc.

### Basic Example
```java
public interface Bird {
    void fly();
}

public class Eagle implements Bird {
    @Override  // This is an annotation
    public void fly() {
        System.out.println("Eagle is flying");
    }
}
```

## 2. Types of Annotations

### 2.1 Predefined Annotations (Built-in)

#### A. Annotations Used on Java Code

##### 1. @Deprecated
- **Purpose**: Marks classes, methods, or fields as deprecated
- **Usage**: Warns developers that the code is outdated and shouldn't be used
- **Compiler behavior**: Shows warnings when deprecated code is used

```java
public class Mobile {
    @Deprecated
    public void oldMethod() {
        // This method is deprecated
    }
    
    public void newMethod() {
        // Use this instead
    }
}

public class Main {
    public static void main(String[] args) {
        Mobile mobile = new Mobile();
        mobile.oldMethod(); // Compiler shows warning
    }
}
```

##### 2. @Override
- **Purpose**: Indicates that a method overrides a parent class method
- **Compiler behavior**: Validates that the method actually overrides something
- **Usage**: Only on methods

```java
public class Bird {
    public void fly() { }
}

public class Eagle extends Bird {
    @Override
    public void fly() {  // Compiler validates this override
        System.out.println("Eagle flying");
    }
    
    @Override
    public void fly1() {  // Compilation error - no such method in parent
        // This will cause compile-time error
    }
}
```

##### 3. @SuppressWarnings
- **Purpose**: Tells compiler to ignore specific warnings
- **Common values**: "deprecation", "unused", "all"
- **Usage**: On classes, methods, fields, parameters, constructors, local variables

```java
public class Example {
    @Deprecated
    public void oldMethod() { }
    
    @SuppressWarnings("deprecation")
    public void useOldMethod() {
        oldMethod(); // No warning shown
    }
    
    @SuppressWarnings({"unused", "deprecation"})
    public void multipleSuppressions() {
        String unused = "test";
        oldMethod();
    }
    
    @SuppressWarnings("all")
    public void suppressAllWarnings() {
        // Suppresses all warnings
    }
}
```

##### 4. @FunctionalInterface
- **Purpose**: Marks an interface as functional (having only one abstract method)
- **Compiler behavior**: Validates that interface has exactly one abstract method
- **Usage**: On interfaces

```java
@FunctionalInterface
public interface Calculator {
    int calculate(int a, int b);
    // Only one abstract method allowed
    
    // int anotherMethod(); // This would cause compilation error
}
```

##### 5. @SafeVarargs
- **Purpose**: Suppresses heap pollution warnings for methods with varargs
- **Usage**: Only on static, final, or private methods (Java 9+)
- **When used**: Methods accepting variable arguments with generic types

```java
public class VarargsExample {
    @SafeVarargs
    public static void printLists(List<String>... lists) {
        for (List<String> list : lists) {
            System.out.println(list);
        }
    }
    
    public static void main(String[] args) {
        List<String> list1 = Arrays.asList("a", "b");
        List<String> list2 = Arrays.asList("c", "d");
        printLists(list1, list2);
    }
}
```

**Understanding Heap Pollution:**
- Occurs when a variable of parameterized type refers to an object of different type
- Example: `List<String>` variable pointing to `List<Integer>` object

#### B. Meta-Annotations (Used on Other Annotations)

##### 1. @Target
- **Purpose**: Specifies where an annotation can be applied
- **Element Types**:
  - `TYPE`: Class, interface, enum
  - `METHOD`: Methods
  - `FIELD`: Fields/member variables
  - `PARAMETER`: Method parameters
  - `CONSTRUCTOR`: Constructors
  - `LOCAL_VARIABLE`: Local variables
  - `ANNOTATION_TYPE`: Other annotations
  - `TYPE_USE`: Anywhere a type is used (Java 8+)

```java
@Target(ElementType.METHOD)
public @interface MyMethodAnnotation {
    // Can only be used on methods
}

@Target({ElementType.TYPE, ElementType.METHOD})
public @interface MyMultiTargetAnnotation {
    // Can be used on classes or methods
}
```

##### 2. @Retention
- **Purpose**: Defines how long annotation information is retained
- **Retention Policies**:
  - `SOURCE`: Discarded by compiler, not in .class files
  - `CLASS`: Recorded in .class files, ignored by JVM at runtime
  - `RUNTIME`: Available at runtime via reflection

```java
@Retention(RetentionPolicy.RUNTIME)
public @interface RuntimeAnnotation {
    // Available at runtime
}

@Retention(RetentionPolicy.SOURCE)
public @interface SourceAnnotation {
    // Only available during compilation
}
```

**Retention Policy Examples:**
```java
// @Override has SOURCE retention
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Override {
}

// Custom runtime annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyRuntimeAnnotation {
}

// Usage and access
@MyRuntimeAnnotation
public class TestClass {
}

// Accessing at runtime
public class Main {
    public static void main(String[] args) {
        Class<?> clazz = TestClass.class;
        MyRuntimeAnnotation annotation = clazz.getAnnotation(MyRuntimeAnnotation.class);
        System.out.println(annotation != null ? "Found" : "Not found");
    }
}
```

##### 3. @Documented
- **Purpose**: Includes annotation in JavaDoc documentation
- **Without @Documented**: Annotation won't appear in generated docs
- **With @Documented**: Annotation appears in JavaDoc

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DocumentedAnnotation {
}

public class Example {
    @DocumentedAnnotation
    public void method() {
        // This annotation will appear in JavaDoc
    }
}
```

##### 4. @Inherited
- **Purpose**: Allows child classes to inherit annotations from parent classes
- **Default behavior**: Annotations are NOT inherited
- **With @Inherited**: Child classes automatically have parent's annotations

```java
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InheritedAnnotation {
}

@InheritedAnnotation
public class Parent {
}

public class Child extends Parent {
    // Automatically has @InheritedAnnotation
}

// Testing inheritance
public class Test {
    public static void main(String[] args) {
        Class<?> childClass = Child.class;
        InheritedAnnotation annotation = childClass.getAnnotation(InheritedAnnotation.class);
        System.out.println(annotation != null ? "Inherited" : "Not inherited");
    }
}
```

##### 5. @Repeatable (Java 8+)
- **Purpose**: Allows same annotation to be used multiple times
- **Requirements**: Need a container annotation

```java
// Step 1: Create the repeatable annotation
@Repeatable(Categories.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Category {
    String name();
}

// Step 2: Create container annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Categories {
    Category[] value();
}

// Step 3: Use multiple times
@Category(name = "Bird")
@Category(name = "Living Thing")
@Category(name = "Carnivorous")
public class Eagle {
}

// Step 4: Access repeated annotations
public class Test {
    public static void main(String[] args) {
        Category[] categories = Eagle.class.getAnnotationsByType(Category.class);
        for (Category category : categories) {
            System.out.println(category.name());
        }
    }
}
```

## 3. Custom Annotations

### Creating Custom Annotations

#### Basic Custom Annotation
```java
// Empty annotation
public @interface MyAnnotation {
}

// Usage
@MyAnnotation
public class MyClass {
}
```

#### Annotation with Members
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyCustomAnnotation {
    String name();
    int value();
    String description() default "No description";
}

// Usage
@MyCustomAnnotation(name = "Test", value = 10)
public class TestClass {
}

@MyCustomAnnotation(name = "Test2", value = 20, description = "Custom description")
public class TestClass2 {
}
```

#### Member Type Restrictions
Annotation members can only be:
- **Primitive types**: int, boolean, char, etc.
- **String**
- **Class** (using Class.class)
- **Enum**
- **Another annotation**
- **Arrays** of above types

```java
public enum Priority {
    LOW, MEDIUM, HIGH
}

public @interface ComplexAnnotation {
    // Primitive
    int number();
    boolean enabled() default true;
    
    // String
    String message();
    
    // Class
    Class<?> type();
    
    // Enum
    Priority priority() default Priority.MEDIUM;
    
    // Array
    String[] tags() default {};
    
    // Another annotation
    MyAnnotation nested();
}
```

## 4. Accessing Annotations with Reflection

### Basic Reflection Access
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyAnnotation {
    String value();
}

@MyAnnotation("Test Class")
public class TestClass {
}

public class AnnotationReader {
    public static void main(String[] args) {
        Class<?> clazz = TestClass.class;
        
        // Check if annotation exists
        if (clazz.isAnnotationPresent(MyAnnotation.class)) {
            // Get the annotation
            MyAnnotation annotation = clazz.getAnnotation(MyAnnotation.class);
            System.out.println("Value: " + annotation.value());
        }
        
        // Get all annotations
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println("Found: " + annotation.annotationType().getSimpleName());
        }
    }
}
```

### Method and Field Annotations
```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Info {
    String description();
    int priority() default 1;
}

public class Example {
    @Info(description = "Important field", priority = 5)
    private String importantData;
    
    @Info(description = "Process method")
    public void process() {
        // method implementation
    }
}

public class AnnotationProcessor {
    public static void processClass(Class<?> clazz) {
        // Process field annotations
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Info.class)) {
                Info info = field.getAnnotation(Info.class);
                System.out.println("Field: " + field.getName() + 
                    ", Description: " + info.description() + 
                    ", Priority: " + info.priority());
            }
        }
        
        // Process method annotations
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Info.class)) {
                Info info = method.getAnnotation(Info.class);
                System.out.println("Method: " + method.getName() + 
                    ", Description: " + info.description());
            }
        }
    }
}
```

## 5. Real-World Use Cases

### 1. Validation Framework
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotNull {
    String message() default "Field cannot be null";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MinLength {
    int value();
    String message() default "Field too short";
}

public class User {
    @NotNull(message = "Name is required")
    @MinLength(value = 2, message = "Name must be at least 2 characters")
    private String name;
    
    @NotNull
    private String email;
    
    // constructors, getters, setters
}

public class Validator {
    public static boolean validate(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                
                if (field.isAnnotationPresent(NotNull.class) && value == null) {
                    NotNull notNull = field.getAnnotation(NotNull.class);
                    System.out.println(notNull.message());
                    return false;
                }
                
                if (field.isAnnotationPresent(MinLength.class) && value instanceof String) {
                    MinLength minLength = field.getAnnotation(MinLength.class);
                    if (((String) value).length() < minLength.value()) {
                        System.out.println(minLength.message());
                        return false;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
```

### 2. Configuration Framework
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigProperty {
    String key();
    String defaultValue() default "";
}

public class DatabaseConfig {
    @ConfigProperty(key = "db.url", defaultValue = "localhost:3306")
    private String url;
    
    @ConfigProperty(key = "db.username", defaultValue = "root")
    private String username;
    
    @ConfigProperty(key = "db.password")
    private String password;
}
```

### 3. REST API Framework (Spring-like)
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RestController {
    String path() default "";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GetMapping {
    String value();
}

@RestController(path = "/api/users")
public class UserController {
    
    @GetMapping("/list")
    public List<User> getUsers() {
        // return user list
    }
    
    @GetMapping("/{id}")
    public User getUser(int id) {
        // return specific user
    }
}
```

## 6. Memory Hooks & Mnemonics

### Annotation Types Mnemonic: "PM-CM"
- **P**redefined **M**eta: Meta-annotations for other annotations
- **C**ustom **M**ade: User-defined annotations

### Meta-Annotations Mnemonic: "TRID"
- **T**arget: Where to apply
- **R**etention: How long to keep
- **I**nherited: Pass to children
- **D**ocumented: Show in docs

### Retention Policy Mnemonic: "SCR"
- **S**ource: Compilation only
- **C**lass: .class files only
- **R**untime: Available during execution

## 7. Common Interview Questions

### Q1: What are annotations in Java?
**Answer:** Annotations are metadata that provide additional information about Java code. They start with @ symbol, are optional, and can be accessed at runtime using reflection.

### Q2: Difference between @Override and method overriding?
**Answer:** Method overriding is the concept of redefining parent class methods. @Override is an annotation that validates the override at compile-time and provides documentation.

### Q3: What is the difference between @Retention SOURCE, CLASS, and RUNTIME?
**Answer:**
- SOURCE: Available only during compilation
- CLASS: Stored in .class files but not available at runtime
- RUNTIME: Available at runtime for reflection

### Q4: How do you create a custom annotation?
**Answer:** Use `public @interface AnnotationName { }` syntax with appropriate meta-annotations like @Target and @Retention.

### Q5: What is heap pollution and how does @SafeVarargs help?
**Answer:** Heap pollution occurs when a variable of one parameterized type references an object of different type. @SafeVarargs suppresses warnings for methods with varargs parameters.

### Q6: Can you use the same annotation multiple times?
**Answer:** Yes, using @Repeatable annotation (Java 8+). You need to create a container annotation that holds an array of the repeatable annotation.

### Q7: What happens if you don't specify @Retention?
**Answer:** Default retention policy is CLASS, meaning annotation is stored in .class files but not available at runtime.

## 8. Best Practices

### Do's ✅
- Use meaningful names for custom annotations
- Always specify @Retention for custom annotations
- Use @Target to restrict annotation usage
- Provide default values for optional members
- Document your custom annotations
- Use annotations for cross-cutting concerns

### Don'ts ❌
- Don't overuse annotations
- Don't create annotations without clear purpose
- Don't forget to handle null values in annotation processors
- Don't suppress warnings unnecessarily
- Don't make annotation members complex

### Performance Tips
- Annotations with RUNTIME retention have slight performance overhead
- Use SOURCE or CLASS retention when runtime access isn't needed
- Cache reflection results for frequently accessed annotations

## 9. Common Pitfalls & Debugging

### Pitfall 1: Wrong Retention Policy
```java
// Won't work - SOURCE retention but trying to access at runtime
@Retention(RetentionPolicy.SOURCE)
public @interface MyAnnotation { }

// Fix: Use RUNTIME retention
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation { }
```

### Pitfall 2: Missing @Target
```java
// No target specified - can be used anywhere
public @interface MyAnnotation { }

// Better: Specify where it can be used
@Target(ElementType.METHOD)
public @interface MyAnnotation { }
```

### Pitfall 3: Forgetting Container for @Repeatable
```java
// Incomplete - missing container
@Repeatable(/* missing container */)
public @interface Tag { }

// Complete implementation
@Repeatable(Tags.class)
public @interface Tag {
    String value();
}

public @interface Tags {
    Tag[] value();
}
```

## 10. Quick Reference Cheat Sheet

| Annotation | Purpose | Target | Retention |
|------------|---------|---------|-----------|
| @Override | Method override validation | METHOD | SOURCE |
| @Deprecated | Mark as outdated | TYPE, METHOD, FIELD, etc. | RUNTIME |
| @SuppressWarnings | Suppress compiler warnings | Multiple | SOURCE |
| @FunctionalInterface | Validate functional interface | TYPE | RUNTIME |
| @SafeVarargs | Suppress varargs warnings | METHOD, CONSTRUCTOR | RUNTIME |

### Meta-Annotations Quick Reference
- **@Target**: WHERE the annotation can be used
- **@Retention**: HOW LONG the annotation is kept
- **@Inherited**: WHETHER child classes inherit the annotation
- **@Documented**: WHETHER to include in JavaDoc
- **@Repeatable**: WHETHER annotation can be repeated

### Reflection Methods for Annotations
```java
// Check if annotation present
boolean present = clazz.isAnnotationPresent(MyAnnotation.class);

// Get single annotation
MyAnnotation ann = clazz.getAnnotation(MyAnnotation.class);

// Get all annotations
Annotation[] anns = clazz.getAnnotations();

// Get repeated annotations (Java 8+)
MyAnnotation[] repeated = clazz.getAnnotationsByType(MyAnnotation.class);
```

## 11. Hands-on Exercises

### Exercise 1: Create a Logging Annotation
Create an annotation that can log method entry and exit:
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    String level() default "INFO";
    boolean logParameters() default false;
}
```

### Exercise 2: Build a Simple Test Framework
Create annotations for test methods:
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
    String description() default "";
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeTest {
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterTest {
}
```

### Exercise 3: Validation Framework
Implement a complete validation framework with:
- @NotNull
- @Range(min, max)
- @Email
- @Size(min, max)

## Conclusion

Annotations are a powerful feature in Java that enable:
- **Metadata attachment** to code elements
- **Compile-time validation** and code generation
- **Runtime processing** through reflection
- **Framework development** and configuration
- **Code documentation** and readability

Master annotations to build better, more maintainable Java applications and understand how popular frameworks like Spring, Hibernate, and JUnit work internally.