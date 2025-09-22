# Java Annotations - Quick Reference Cheat Sheet

## 🎯 What are Annotations?
- **Metadata** that provides additional information about Java code
- Start with `@` symbol (e.g., `@Override`)
- **Optional** - won't break code if not used
- Can be accessed at **runtime** using **reflection**

## 📝 Predefined Annotations

### Basic Annotations
| Annotation | Purpose | Usage |
|------------|---------|--------|
| `@Override` | Validates method override | Methods only |
| `@Deprecated` | Marks as outdated | Classes, methods, fields |
| `@SuppressWarnings` | Suppresses compiler warnings | Multiple targets |
| `@FunctionalInterface` | Validates single abstract method | Interfaces |
| `@SafeVarargs` | Suppresses varargs warnings | Methods, constructors |

### Meta-Annotations (for creating annotations)
| Annotation | Purpose | Values |
|------------|---------|---------|
| `@Target` | Where annotation can be used | TYPE, METHOD, FIELD, etc. |
| `@Retention` | How long annotation is kept | SOURCE, CLASS, RUNTIME |
| `@Inherited` | Child classes inherit annotation | No values |
| `@Documented` | Include in JavaDoc | No values |
| `@Repeatable` | Allow multiple uses | Container class |

## 🔧 Creating Custom Annotations

### Basic Syntax
```java
public @interface MyAnnotation {
    // annotation body
}
```

### With Members
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyAnnotation {
    String name();
    int value() default 0;
    String[] tags() default {};
}
```

### Allowed Member Types
- ✅ Primitive types (int, boolean, etc.)
- ✅ String
- ✅ Class (using `.class`)
- ✅ Enum
- ✅ Another annotation
- ✅ Arrays of above types
- ❌ Objects, collections, etc.

## 🔄 Retention Policies

| Policy | Description | Use Case |
|--------|-------------|----------|
| `SOURCE` | Compilation only | Code generation, compile-time checks |
| `CLASS` | .class files only | Bytecode processing |
| `RUNTIME` | Available at runtime | Reflection, frameworks |

**Memory Trick:** **S**ource → **C**lass → **R**untime (SCR)

## 🎯 Target Elements

| Element | Description | Example |
|---------|-------------|---------|
| `TYPE` | Class, interface, enum | `@Entity` |
| `METHOD` | Methods | `@Override` |
| `FIELD` | Member variables | `@Autowired` |
| `PARAMETER` | Method parameters | `@Param` |
| `CONSTRUCTOR` | Constructors | `@Inject` |
| `LOCAL_VARIABLE` | Local variables | `@SuppressWarnings` |
| `ANNOTATION_TYPE` | Other annotations | `@Target` |

## 🔍 Accessing Annotations with Reflection

### Basic Access
```java
// Check if present
boolean hasAnnotation = clazz.isAnnotationPresent(MyAnnotation.class);

// Get annotation
MyAnnotation ann = clazz.getAnnotation(MyAnnotation.class);

// Get all annotations
Annotation[] all = clazz.getAnnotations();
```

### Field/Method Annotations
```java
// Fields
Field field = clazz.getDeclaredField("fieldName");
MyAnnotation ann = field.getAnnotation(MyAnnotation.class);

// Methods
Method method = clazz.getDeclaredMethod("methodName");
MyAnnotation ann = method.getAnnotation(MyAnnotation.class);
```

## 📚 Common Examples

### 1. Override Validation
```java
@Override
public void method() {
    // Validates override at compile-time
}
```

### 2. Deprecation Warning
```java
@Deprecated
public void oldMethod() {
    // Shows warning when used
}

@SuppressWarnings("deprecation")
public void useOldMethod() {
    oldMethod(); // No warning
}
```

### 3. Functional Interface
```java
@FunctionalInterface
public interface Calculator {
    int calculate(int a, int b);
    // Only one abstract method allowed
}
```

### 4. Custom Validation
```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
    String message() default "Cannot be null";
}

public class User {
    @NotNull(message = "Name is required")
    private String name;
}
```

### 5. Repeatable Annotations
```java
@Repeatable(Tags.class)
@interface Tag {
    String value();
}

@interface Tags {
    Tag[] value();
}

@Tag("important")
@Tag("urgent")
public class Task {
}
```

## 🚀 Real-World Use Cases

### Framework Development
- **Spring:** `@Component`, `@Autowired`, `@RequestMapping`
- **JUnit:** `@Test`, `@BeforeEach`, `@AfterEach`
- **JPA:** `@Entity`, `@Table`, `@Column`

### Configuration
```java
@RestController("/api/users")
public class UserController {
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return userService.findById(id);
    }
}
```

### Validation
```java
public class User {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    
    @Email
    private String email;
    
    @Range(min = 18, max = 100)
    private int age;
}
```

## ⚡ Quick Tips

### Memory Hooks
- **TRID:** Target, Retention, Inherited, Documented (meta-annotations)
- **SCR:** Source, Class, Runtime (retention policies)
- **@:** All annotations start with @ symbol

### Best Practices
- ✅ Always specify `@Retention` for custom annotations
- ✅ Use `@Target` to restrict usage
- ✅ Provide default values for optional members
- ✅ Use meaningful names
- ❌ Don't overuse annotations
- ❌ Don't suppress warnings unnecessarily

### Common Mistakes
1. **Wrong retention:** Using SOURCE when you need RUNTIME
2. **Missing target:** Not specifying where annotation can be used
3. **Forgetting container:** For `@Repeatable` annotations
4. **Complex members:** Using unsupported types in annotation members

## 🎯 Interview Quick Answers

**Q: What are annotations?**
A: Metadata that provides additional information about Java code, optional, start with @, accessible via reflection.

**Q: Difference between SOURCE, CLASS, RUNTIME?**
A: SOURCE (compilation only), CLASS (.class files only), RUNTIME (available at runtime).

**Q: How to create custom annotation?**
A: Use `public @interface Name { }` with appropriate meta-annotations.

**Q: What is @SafeVarargs?**
A: Suppresses heap pollution warnings for methods with varargs parameters.

## 📖 Further Reading
- Oracle Java Documentation on Annotations
- Effective Java by Joshua Bloch (Item 39-41)
- Spring Framework Annotation Reference
- Custom Annotation Processing with APT

---
*Master annotations to understand how modern Java frameworks work! 🚀*