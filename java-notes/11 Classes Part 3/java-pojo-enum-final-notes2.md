# Java POJOs, Enums, and Final Classes - Comprehensive Notes

## Table of Contents
1. [POJO Classes](#pojo-classes)
2. [Enum Classes](#enum-classes)
3. [Final Classes](#final-classes)
4. [Interview Questions](#interview-questions)

---

## POJO Classes

### Definition
**POJO** = **Plain Old Java Object**
- A simple Java class with minimal restrictions
- Contains variables and their getter/setter methods
- No fancy annotations or complex inheritance

### POJO Requirements
1. ✅ **Public class**
2. ✅ **Public default constructor**
3. ✅ **Variables with getter/setter methods**
4. ❌ **No annotations** (like @Entity, @Table)
5. ❌ **Does not extend any class**
6. ❌ **Does not implement any interface**

### POJO Example
```java
public class Student {
    private String name;
    private int age;
    protected String address;
    String defaultField;
    
    // Public default constructor (implicitly present)
    public Student() {}
    
    // Getter and Setter methods
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
}
```

### Real-World Use Cases

#### 1. **Request/Response Mapping**
```
Client Request → Component → Internal POJO → Business Logic
```

**Example Scenario:**
```java
// External Request Object
class RequestObject {
    String id;
    String name;
}

// Internal POJO for mapping
class CustomerPojo {
    String customerId;
    String customerName;
    
    // Map external request to internal representation
    // Benefits: Isolation from external changes
}
```

#### 2. **Layered Architecture**
```
REST API → Controller → Business Logic → Repository → Database
     ↓           ↓              ↓             ↓
   DTOs      POJOs        Entities      POJOs
```

### Benefits of POJOs
- **Separation of Concerns**: Isolate external dependencies
- **Maintainability**: Changes in external APIs don't affect internal code
- **Flexibility**: Easy to modify internal structure
- **Testability**: Simple objects are easier to test

---

## Enum Classes

### Definition
**Enum** = A collection of constants
- Variables whose values cannot be changed
- All constants are implicitly `static` and `final`
- Internally extends `java.lang.Enum` class

### Basic Enum Declaration
```java
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
}
```

### Enum Properties
1. ❌ **Cannot extend any class** (already extends java.lang.Enum)
2. ✅ **Can implement interfaces**
3. ✅ **Can have variables, constructors, and methods**
4. ❌ **Cannot be instantiated** (constructor is always private)
5. ❌ **No other class can extend enum**
6. ✅ **Can have abstract methods**

### Enum Methods (Built-in)

#### 1. **values()** - Returns array of all constants
```java
Day[] days = Day.values();
for (Day day : days) {
    System.out.println(day + " - " + day.ordinal());
}
// Output: MONDAY - 0, TUESDAY - 1, ...
```

#### 2. **ordinal()** - Returns index position (0-based)
```java
System.out.println(Day.MONDAY.ordinal()); // 0
System.out.println(Day.FRIDAY.ordinal()); // 4
```

#### 3. **valueOf()** - Returns enum constant by name
```java
Day day = Day.valueOf("FRIDAY");
System.out.println(day.name()); // FRIDAY
```

#### 4. **name()** - Returns the name of the constant
```java
System.out.println(Day.MONDAY.name()); // MONDAY
```

### Enum with Custom Values
```java
public enum Day {
    MONDAY(101, "First day of week"),
    TUESDAY(102, "Second day"),
    WEDNESDAY(103, "Mid week"),
    THURSDAY(104, "Almost weekend"),
    FRIDAY(105, "TGIF"),
    SATURDAY(106, "Weekend starts"),
    SUNDAY(107, "Rest day");
    
    private final int value;
    private final String comment;
    
    // Private constructor (always private in enums)
    Day(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }
    
    // Getter methods
    public int getValue() {
        return value;
    }
    
    public String getComment() {
        return comment;
    }
    
    // Static method to find enum by value
    public static Day getByValue(int value) {
        for (Day day : Day.values()) {
            if (day.getValue() == value) {
                return day;
            }
        }
        return null;
    }
}
```

### Method Override in Enums
```java
public enum Day {
    MONDAY {
        @Override
        public void dummyMethod() {
            System.out.println("Monday dummy method");
        }
    },
    TUESDAY,
    WEDNESDAY;
    
    // Default implementation
    public void dummyMethod() {
        System.out.println("Default dummy method");
    }
}
```

### Abstract Methods in Enums
```java
public enum Day {
    MONDAY {
        @Override
        public void work() {
            System.out.println("Start of work week");
        }
    },
    FRIDAY {
        @Override
        public void work() {
            System.out.println("End of work week");
        }
    };
    
    // Abstract method - all constants must implement
    public abstract void work();
}
```

### Enum Implementing Interface
```java
interface Convertible {
    String toLowerCase();
}

public enum Day implements Convertible {
    MONDAY, TUESDAY, WEDNESDAY;
    
    @Override
    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}
```

### Enum vs Static Final Constants

#### Static Final Approach
```java
public class Constants {
    public static final int MONDAY = 0;
    public static final int TUESDAY = 1;
    // ... more constants
    
    public static boolean isWeekend(int day) {
        return day == 5 || day == 6; // Saturday or Sunday
    }
}

// Usage - Problem: No type safety
Constants.isWeekend(100); // Compiles but logically wrong
```

#### Enum Approach
```java
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
    
    public static boolean isWeekend(Day day) {
        return day == SATURDAY || day == SUNDAY;
    }
}

// Usage - Type safe
Day.isWeekend(Day.SUNDAY); // ✅ Correct
// Day.isWeekend(100); // ❌ Compilation error
```

### Advantages of Enums
1. **Type Safety**: Cannot pass invalid values
2. **Readability**: Self-documenting code
3. **Maintainability**: Centralized constant management
4. **IDE Support**: Auto-completion and refactoring
5. **Built-in Methods**: values(), ordinal(), valueOf(), name()

---

## Final Classes

### Definition
**Final Class** = A class that cannot be inherited/extended
- Prevents inheritance using the `final` keyword
- Used when you want to restrict class extension

### Final Class Example
```java
public final class TestClass {
    public void someMethod() {
        System.out.println("This class cannot be extended");
    }
}

// This will cause compilation error
public class AnotherClass extends TestClass { // ❌ Error!
    // Cannot inherit from final class
}
```

### Real-World Examples of Final Classes
- `String` class
- `Integer`, `Double`, and other wrapper classes
- `Math` class

### When to Use Final Classes
1. **Immutable Classes**: Like String, wrapper classes
2. **Utility Classes**: Classes with only static methods
3. **Security**: Prevent malicious inheritance
4. **Design Integrity**: Maintain specific behavior

---

## Interview Questions

### POJO Questions

**Q1: What is a POJO and what are its characteristics?**
**A:** POJO stands for Plain Old Java Object. It's a simple Java class with:
- Public class declaration
- Public default constructor
- Private fields with public getter/setter methods
- No annotations, inheritance, or interface implementations

**Q2: Where are POJOs commonly used?**
**A:** POJOs are used in:
- Data Transfer Objects (DTOs) between layers
- Request/Response mapping in REST APIs
- Entity objects in ORM frameworks
- Configuration objects

### Enum Questions

**Q3: Why use enums instead of static final constants?**
**A:** Enums provide:
- Type safety (compile-time checking)
- Better readability
- Built-in methods (values(), ordinal(), etc.)
- Cannot pass invalid values
- IDE support for auto-completion

**Q4: Can enums have constructors? Are they public?**
**A:** Yes, enums can have constructors, but they are always private (even if not explicitly declared). This prevents external instantiation.

**Q5: Can enums implement interfaces?**
**A:** Yes, enums can implement interfaces but cannot extend classes (they already extend java.lang.Enum).

**Q6: What is the ordinal() method in enums?**
**A:** ordinal() returns the position of the enum constant (0-based index) in its declaration order.

### Final Class Questions

**Q7: What is a final class and why would you use it?**
**A:** A final class cannot be extended/inherited. Used for:
- Immutable classes (String)
- Utility classes (Math)
- Security reasons
- Maintaining design integrity

**Q8: Can you override methods in a final class?**
**A:** No, you cannot override methods in a final class because you cannot extend a final class.

### Best Practices

#### POJO Best Practices
- Keep fields private and provide public getters/setters
- Implement `equals()` and `hashCode()` if needed
- Consider implementing `toString()` for debugging
- Use meaningful names for fields and methods

#### Enum Best Practices
- Use enums for fixed sets of constants
- Add custom methods for business logic
- Consider using enum sets for multiple selections
- Document the purpose of each constant

#### Final Class Best Practices
- Use final for utility classes
- Make constructor private for utility classes
- Document why the class is final
- Consider composition over inheritance

### Common Pitfalls

#### POJO Pitfalls
- Adding business logic (should be pure data objects)
- Missing default constructor
- Making fields public without getters/setters

#### Enum Pitfalls
- Using ordinal() values in persistence (order might change)
- Not handling null values in valueOf()
- Overusing enums for dynamic data

#### Final Class Pitfalls
- Making classes final unnecessarily
- Forgetting that final classes cannot be mocked easily
- Not understanding the inheritance implications

---

## Summary

| Concept | Purpose | Key Features | Use Cases |
|---------|---------|--------------|-----------|
| **POJO** | Simple data objects | No inheritance, annotations | DTOs, Request/Response mapping |
| **Enum** | Type-safe constants | Built-in methods, custom values | Status codes, Categories, Options |
| **Final** | Prevent inheritance | Cannot be extended | Utility classes, Immutable objects |

These concepts form the foundation for creating robust, maintainable Java applications with proper separation of concerns and type safety.