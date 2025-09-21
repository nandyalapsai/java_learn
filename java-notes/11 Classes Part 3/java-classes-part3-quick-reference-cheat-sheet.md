# Java Classes Part 3 - Quick Reference Cheat Sheet

## POJO (Plain Old Java Object)

### Definition
**POJO** = Simple Java class with minimal restrictions

### Requirements ✅❌
- ✅ Public class
- ✅ Public default constructor  
- ✅ Variables with getter/setter methods
- ❌ No annotations (@Entity, @Table, etc.)
- ❌ Doesn't extend any class
- ❌ Doesn't implement any interface

### Quick Template
```java
public class Student {
    private String name;
    private int age;
    
    public Student() {} // Public default constructor
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
```

### Memory Hook: "POJO is PLAIN"
- **P**ublic class
- **L**ight-weight (no annotations)
- **A**ccessible (getters/setters)
- **I**ndependent (no inheritance)
- **N**ormal constructor (public default)

---

## ENUM

### Definition
**Enum** = Collection of constants (implicitly static final)

### Properties
- ❌ Cannot extend classes (extends java.lang.Enum internally)
- ✅ Can implement interfaces
- ✅ Can have variables, constructors, methods
- ❌ Cannot be instantiated (private constructor)
- ✅ Can have abstract methods

### Common Methods
```java
// Built-in methods (from java.lang.Enum)
EnumName.values()           // Returns array of all constants
EnumName.valueOf("STRING")  // Returns enum by name
enumInstance.ordinal()      // Returns position (0-based)
enumInstance.name()         // Returns constant name
```

### Quick Templates

#### Basic Enum
```java
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
}
```

#### Enum with Custom Values
```java
public enum Status {
    ACTIVE(1, "Active"), INACTIVE(0, "Inactive");
    
    private final int code;
    private final String description;
    
    Status(int code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public int getCode() { return code; }
}
```

#### Enum with Abstract Method
```java
public enum Operation {
    PLUS {
        public double calculate(double a, double b) { return a + b; }
    },
    MINUS {
        public double calculate(double a, double b) { return a - b; }
    };
    
    public abstract double calculate(double a, double b);
}
```

#### Enum Implementing Interface
```java
interface Printable {
    void print();
}

public enum Color implements Printable {
    RED, GREEN, BLUE;
    
    @Override
    public void print() {
        System.out.println("Color: " + this.name());
    }
}
```

### Memory Hook: "ENUM = Exclusive Named Unique Members"
- **E**xclusive (cannot extend)
- **N**amed constants
- **U**nique ordinal values
- **M**ethods allowed

---

## FINAL CLASS

### Definition
**Final Class** = Class that cannot be inherited/extended

### Syntax
```java
public final class UtilityClass {
    // Implementation
    // Cannot be extended by other classes
}

// This causes compilation error:
// class AnotherClass extends UtilityClass {} // ERROR!
```

### Memory Hook: "FINAL = Forever Inheritance Negated And Locked"

---

## Comparison Quick Table

| Feature | POJO | Enum | Final Class |
|---------|------|------|-------------|
| **Extend Classes** | ✅ Yes | ❌ No (extends Enum) | ✅ Yes |
| **Be Extended** | ✅ Yes | ❌ No | ❌ No |
| **Implement Interface** | ✅ Yes | ✅ Yes | ✅ Yes |
| **Instantiation** | ✅ Public constructor | ❌ Private only | ✅ Normal |
| **Main Purpose** | Data transfer | Constants + behavior | Prevent inheritance |

---

## Enum vs Static Final Constants

### ❌ Static Final (Traditional)
```java
public class Constants {
    public static final int MONDAY = 0;
    public static final int TUESDAY = 1;
    
    public static boolean isWeekend(int day) {
        return day == 5 || day == 6; // What if someone passes 100?
    }
}
```

**Problems**: No type safety, can pass invalid values

### ✅ Enum (Better)
```java
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
    
    public static boolean isWeekend(Day day) {
        return day == SATURDAY || day == SUNDAY;
    }
}
```

**Benefits**: Type safe, readable, controlled values

---

## Quick Interview Answers

### Basic Questions
1. **POJO characteristics?** → Public class, default constructor, getters/setters, no annotations/inheritance
2. **Can enum extend class?** → No, already extends java.lang.Enum
3. **Enum constructor access?** → Always private (even if not specified)

### Advanced Questions
4. **Enum advantages over static final?** → Type safety, readability, controlled values, can have methods
5. **Can enum have abstract methods?** → Yes, all constants must implement them
6. **Why use final class?** → Prevent inheritance, ensure immutability, security

---

## Common Use Cases

### POJO
- Data Transfer Objects (DTOs)
- Request/Response mapping in APIs
- Database entity mapping
- Configuration objects

### Enum
- Status codes (ACTIVE, INACTIVE)
- Days of week, months
- Error codes
- Operation types
- Priority levels (HIGH, MEDIUM, LOW)

### Final Class
- Utility classes (Math, Collections)
- Immutable classes (String)
- Security-sensitive classes
- Framework/library classes

---

## Best Practices Checklist

### POJO ✅❌
- ✅ Use meaningful field names
- ✅ Follow JavaBean conventions (get/set)
- ✅ Override toString() for debugging
- ❌ Don't add business logic
- ❌ Don't make it complex

### Enum ✅❌
- ✅ Use ALL_CAPS for constant names
- ✅ Use enum instead of constants for fixed sets
- ✅ Implement toString() for better logging
- ❌ Don't use ordinal() in business logic
- ❌ Don't make constructor public

### Final Class ✅❌
- ✅ Document why class is final
- ✅ Use for utility classes
- ❌ Don't overuse final keyword
- ❌ Consider composition over inheritance

---

## Code Templates for Quick Use

### Standard POJO Template
```java
public class EntityName {
    private DataType fieldName;
    
    public EntityName() {}
    
    public DataType getFieldName() { return fieldName; }
    public void setFieldName(DataType fieldName) { this.fieldName = fieldName; }
    
    @Override
    public String toString() {
        return "EntityName{fieldName=" + fieldName + "}";
    }
}
```

### Standard Enum Template
```java
public enum EnumName {
    CONSTANT1(value1), CONSTANT2(value2);
    
    private final DataType field;
    
    EnumName(DataType field) {
        this.field = field;
    }
    
    public DataType getField() { return field; }
}
```

### Final Utility Class Template
```java
public final class UtilityClassName {
    private UtilityClassName() {} // Prevent instantiation
    
    public static ReturnType utilityMethod(Parameters params) {
        // Utility logic
        return result;
    }
}
```

---

## Quick Debugging Tips

### POJO Debug
```java
@Override
public String toString() {
    return "ClassName{field1=" + field1 + ", field2=" + field2 + "}";
}
```

### Enum Debug
```java
@Override
public String toString() {
    return name() + "(" + customValue + ")";
}
```

---

**Print this cheat sheet for quick reference during coding and interviews!**