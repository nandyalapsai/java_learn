# Java Classes Part 3: POJO, Enum, and Final Classes - Comprehensive Notes

## Learning Objectives
By the end of these notes, you should be able to:
- Define and create POJO (Plain Old Java Object) classes
- Understand when and why to use POJOs in enterprise applications
- Create and use enums with custom values, methods, and abstract methods
- Implement interfaces in enum classes
- Override methods in enum constants
- Understand the advantages of enums over static final constants
- Create and understand final classes and their inheritance restrictions
- Apply these concepts in real-world scenarios

## Table of Contents
1. [POJO Classes](#pojo-classes)
2. [Enum Classes](#enum-classes)
3. [Final Classes](#final-classes)
4. [Interview Questions](#interview-questions)
5. [Practice Exercises](#practice-exercises)
6. [Best Practices](#best-practices)
7. [Quick Reference Cheat Sheet](#quick-reference-cheat-sheet)

---

## POJO Classes

### Definition
**POJO** = **Plain Old Java Object**
- Simple Java class with minimal restrictions
- Contains variables and their getter/setter methods
- No fancy annotations or complex inheritance

### POJO Requirements
1. ✅ **Public class**
2. ✅ **Public default constructor**
3. ✅ **Variables with getter/setter methods**
4. ❌ **No annotations** (like @Entity, @Table)
5. ❌ **Doesn't extend any class**
6. ❌ **Doesn't implement any interface**

### Example POJO Class
```java
public class Student {
    // Variables with different access modifiers allowed
    private String name;
    protected int age;
    String address;  // default
    public String email;
    
    // Public default constructor (implicit or explicit)
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

#### 1. Request-Response Mapping
```
Client Request → Component → Internal POJO → Business Logic
```

**Scenario**: API receives user data and maps to internal objects
```java
// External Request Object
class UserRequest {
    public String id;
    public String name;
}

// Internal POJO for mapping
public class Customer {
    private String customerId;
    private String customerName;
    
    // Getters and setters...
}
```

**Benefits**:
- **Decoupling**: Changes in external API don't affect internal classes
- **Flexibility**: Internal naming conventions independent of external contracts
- **Maintainability**: Single point of change for mappings

#### 2. Data Layer Entities
```
Controller → Service → Repository → Database Entity (POJO)
```

### Memory Hook for POJO
**"POJO = Simple & Clean"**
- **P**ublic class
- **O**nly basic features
- **J**ust variables + getters/setters
- **O**rdinary (no fancy stuff)

---

## Enum Classes

### Definition
**Enum** = Collection of constants
- Constants are implicitly `static` and `final`
- Cannot extend other classes (extends `java.lang.Enum` internally)
- Can implement interfaces
- Constructor is always private

### Basic Enum Properties
1. ❌ **Cannot extend any class** (already extends `java.lang.Enum`)
2. ✅ **Can implement interfaces**
3. ✅ **Can have variables, constructors, and methods**
4. ❌ **Cannot be instantiated** (private constructor)
5. ❌ **No other class can extend enum**
6. ✅ **Can have abstract methods**

### 1. Simple Enum
```java
public enum DaysOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
}
```

**Internal ordinal values**: MONDAY=0, TUESDAY=1, ..., SUNDAY=6

### 2. Common Enum Methods
```java
public class EnumExample {
    public static void main(String[] args) {
        // values() - returns array of all enum constants
        for (DaysOfWeek day : DaysOfWeek.values()) {
            System.out.println(day + " ordinal: " + day.ordinal());
        }
        
        // valueOf() - returns enum constant by name
        DaysOfWeek day = DaysOfWeek.valueOf("FRIDAY");
        System.out.println("Day: " + day.name()); // prints: FRIDAY
    }
}
```

**Output**:
```
MONDAY ordinal: 0
TUESDAY ordinal: 1
WEDNESDAY ordinal: 2
THURSDAY ordinal: 3
FRIDAY ordinal: 4
SATURDAY ordinal: 5
SUNDAY ordinal: 6
Day: FRIDAY
```

### 3. Enum with Custom Values
```java
public enum DaysOfWeek {
    MONDAY(101, "First day of the week"),
    TUESDAY(102, "Second day of the week"),
    WEDNESDAY(103, "Middle of the week"),
    THURSDAY(104, "Almost there"),
    FRIDAY(105, "TGIF"),
    SATURDAY(106, "Weekend starts"),
    SUNDAY(107, "Last day of weekend");
    
    private final int value;
    private final String comment;
    
    // Private constructor
    DaysOfWeek(int value, String comment) {
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
    public static DaysOfWeek getEnumFromValue(int value) {
        for (DaysOfWeek day : DaysOfWeek.values()) {
            if (day.getValue() == value) {
                return day;
            }
        }
        return null;
    }
}

// Usage
DaysOfWeek day = DaysOfWeek.getEnumFromValue(107);
System.out.println(day.getComment()); // prints: Last day of weekend
```

### 4. Method Override by Constants
```java
public enum DaysOfWeek {
    MONDAY {
        @Override
        public void dummyMethod() {
            System.out.println("Monday dummy method");
        }
    },
    TUESDAY {
        @Override
        public void dummyMethod() {
            System.out.println("Tuesday dummy method");
        }
    },
    WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
    
    // Default implementation
    public void dummyMethod() {
        System.out.println("Default dummy method");
    }
}

// Usage
DaysOfWeek.MONDAY.dummyMethod();    // prints: Monday dummy method
DaysOfWeek.FRIDAY.dummyMethod();    // prints: Default dummy method
```

### 5. Enum with Abstract Methods
```java
public enum Operation {
    PLUS {
        @Override
        public double calculate(double x, double y) {
            return x + y;
        }
    },
    MINUS {
        @Override
        public double calculate(double x, double y) {
            return x - y;
        }
    },
    MULTIPLY {
        @Override
        public double calculate(double x, double y) {
            return x * y;
        }
    };
    
    public abstract double calculate(double x, double y);
}

// Usage
double result = Operation.PLUS.calculate(5, 3); // result = 8.0
```

### 6. Enum Implementing Interface
```java
interface Convertible {
    String toLowerCase();
}

public enum DaysOfWeek implements Convertible {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
    
    @Override
    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}

// Usage
String lowerDay = DaysOfWeek.MONDAY.toLowerCase(); // prints: monday
```

### Enum vs Static Final Constants

#### Traditional Approach (Static Final)
```java
public class WeekConstants {
    public static final int MONDAY = 0;
    public static final int TUESDAY = 1;
    public static final int WEDNESDAY = 2;
    public static final int THURSDAY = 3;
    public static final int FRIDAY = 4;
    public static final int SATURDAY = 5;
    public static final int SUNDAY = 6;
    
    public static boolean isWeekend(int day) {
        return day == SATURDAY || day == SUNDAY;
    }
}

// Usage - Problems
WeekConstants.isWeekend(2);    // false (works)
WeekConstants.isWeekend(6);    // true (works)
WeekConstants.isWeekend(100);  // false (no control - problematic!)
```

#### Enum Approach (Better)
```java
public enum DaysOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
    
    public static boolean isWeekend(DaysOfWeek day) {
        return day == SATURDAY || day == SUNDAY;
    }
}

// Usage - Type Safe
DaysOfWeek.isWeekend(DaysOfWeek.WEDNESDAY); // false (works)
DaysOfWeek.isWeekend(DaysOfWeek.SUNDAY);    // true (works)
// DaysOfWeek.isWeekend(100);               // Compilation error!
```

### Advantages of Enum over Static Final
1. **Type Safety**: Cannot pass invalid values
2. **Readability**: Self-documenting code
3. **Control**: Restricted to predefined values only
4. **Functionality**: Can have methods and behaviors
5. **Maintenance**: Centralized constant management

### Memory Hook for Enum
**"ENUM = Everyone Needs Unique Methods"**
- **E**ach constant can have unique behavior
- **N**o inheritance allowed (final nature)
- **U**nique ordinal values assigned
- **M**ethods can be abstract or concrete

---

## Final Classes

### Definition
**Final Class** = Class that cannot be inherited/extended by other classes

### Syntax
```java
public final class TestClass {
    // Class implementation
    private String data;
    
    public void someMethod() {
        System.out.println("This class cannot be extended");
    }
}

// This will cause compilation error
class AnotherClass extends TestClass {  // ERROR!
    // Cannot inherit from final class
}
```

### Compilation Error
```
Error: Cannot inherit from final class TestClass
```

### Real-World Examples of Final Classes
- `String` class
- `Integer`, `Double`, `Boolean` (wrapper classes)
- `Math` class

### Use Cases for Final Classes
1. **Immutable Classes**: Prevent modification through inheritance
2. **Utility Classes**: Prevent extension of utility methods
3. **Security**: Prevent malicious extension
4. **Design Integrity**: Maintain intended class behavior

---

## Comparison Table

| Feature | POJO | Enum | Final Class |
|---------|------|------|-------------|
| **Inheritance** | Can extend one class | Cannot extend (extends Enum) | Cannot be extended |
| **Interfaces** | Can implement | Can implement | Can implement |
| **Instantiation** | Public constructor | Private constructor only | Normal instantiation |
| **Purpose** | Data transfer/mapping | Constants with behavior | Prevent inheritance |
| **Methods** | Regular methods | Can have abstract methods | Regular methods |
| **Variables** | Instance variables | Constants (static final) | Any type |

---

## Interview Questions

### Basic Level
1. **Q: What does POJO stand for and what are its characteristics?**
   - **A**: Plain Old Java Object. Characteristics: public class, public default constructor, getter/setter methods, no annotations, doesn't extend/implement anything.

2. **Q: Can enum extend another class?**
   - **A**: No, enum cannot extend another class because it internally extends `java.lang.Enum`.

3. **Q: What is the default access modifier for enum constructor?**
   - **A**: Private. Even if you don't specify, it becomes private in bytecode.

### Intermediate Level
4. **Q: How do you create an enum with custom values?**
   ```java
   enum Status {
       ACTIVE(1, "Active User"),
       INACTIVE(0, "Inactive User");
       
       private final int code;
       private final String description;
       
       Status(int code, String description) {
           this.code = code;
           this.description = description;
       }
   }
   ```

5. **Q: Can enum have abstract methods?**
   - **A**: Yes, enum can have abstract methods, and all enum constants must provide implementation.

6. **Q: What are the advantages of enum over static final constants?**
   - **A**: Type safety, readability, controlled values, can have methods, better maintenance.

### Advanced Level
7. **Q: Can you override methods in enum constants?**
   - **A**: Yes, each enum constant can override methods defined in the enum class.

8. **Q: Where are POJOs commonly used in enterprise applications?**
   - **A**: Request/response mapping, data transfer objects (DTOs), entity classes, API contracts.

9. **Q: Why would you make a class final?**
   - **A**: To prevent inheritance, maintain immutability, ensure security, preserve design integrity.

---

## Practice Exercises

### Exercise 1: POJO Creation
Create a `Product` POJO with fields: id, name, price, category. Include proper constructors and getter/setter methods.

### Exercise 2: Enum with Business Logic
Create a `PaymentStatus` enum with values: PENDING, COMPLETED, FAILED, REFUNDED. Add a method to check if payment can be refunded.

### Exercise 3: Enum with Abstract Methods
Create a `Shape` enum with CIRCLE, RECTANGLE, TRIANGLE. Each should implement an abstract method `calculateArea()`.

### Exercise 4: Real-world Scenario
Design a system using POJO for user registration:
- Create `UserRequest` POJO (external)
- Create `User` POJO (internal)
- Write a mapper method

---

## Best Practices

### POJO Best Practices
1. ✅ **Use meaningful names** for fields and methods
2. ✅ **Follow JavaBean conventions** for getter/setter naming
3. ✅ **Keep it simple** - avoid complex logic
4. ✅ **Use appropriate access modifiers**
5. ❌ **Don't add business logic** in POJOs

### Enum Best Practices
1. ✅ **Use ALL_CAPS** for enum constant names
2. ✅ **Implement `toString()`** for better logging
3. ✅ **Use enum instead of constants** when you have fixed set of values
4. ✅ **Keep enum constructors private**
5. ❌ **Don't use ordinal()** for business logic (it can change)

### Final Class Best Practices
1. ✅ **Document why class is final**
2. ✅ **Use for utility classes**
3. ✅ **Consider composition over inheritance**
4. ❌ **Don't make everything final** without reason

---

## Common Pitfalls

### POJO Pitfalls
1. **Adding business logic** → Keep POJOs simple
2. **Missing default constructor** → Always provide public default constructor
3. **Complex inheritance** → POJOs should be simple

### Enum Pitfalls
1. **Using ordinal() in business logic** → Use custom values instead
2. **Making constructor public** → Always keep private
3. **Comparing with ==** → Use equals() or == (both work for enums)

### Final Class Pitfalls
1. **Overusing final** → Only use when inheritance should be prevented
2. **Not documenting reasoning** → Always explain why class is final

---

## Debugging Tips

### Enum Debugging
```java
// Good: Debug enum values
public enum Status {
    ACTIVE(1), INACTIVE(0);
    
    @Override
    public String toString() {
        return name() + "(" + value + ")";
    }
}
```

### POJO Debugging
```java
// Good: Override toString() for better debugging
public class User {
    @Override
    public String toString() {
        return "User{name='" + name + "', age=" + age + "}";
    }
}
```

---

## Quick Reference Cheat Sheet

### POJO Checklist
- [ ] Public class
- [ ] Public default constructor
- [ ] Private fields with getters/setters
- [ ] No annotations
- [ ] No inheritance/interfaces

### Enum Quick Reference
```java
// Basic enum
enum Day { MON, TUE, WED, THU, FRI, SAT, SUN; }

// Custom values
enum Status {
    ACTIVE(1), INACTIVE(0);
    private final int value;
    Status(int value) { this.value = value; }
}

// Abstract method
enum Operation {
    PLUS { double calc(double a, double b) { return a + b; } };
    abstract double calc(double a, double b);
}
```

### Key Methods
- `values()` - returns array of all constants
- `valueOf(String)` - returns enum constant by name
- `ordinal()` - returns position (0-based)
- `name()` - returns constant name

### Final Class
```java
public final class UtilityClass {
    // Cannot be extended
}
```

---

## Memory Hooks & Mnemonics

### POJO Memory Hook
**"POJO is PLAIN"**
- **P**ublic class
- **L**ight-weight (no annotations)
- **A**ccessible (getters/setters)
- **I**ndependent (no inheritance)
- **N**ormal constructor (public default)

### Enum Memory Hook
**"ENUM = Exclusive Named Unique Members"**
- **E**xclusive (cannot extend)
- **N**amed constants
- **U**nique ordinal values
- **M**ethods allowed

### Final Memory Hook
**"FINAL = Forever Inheritance Negated And Locked"**
- **F**orever unchangeable inheritance
- **I**nheritance blocked
- **N**o extending allowed
- **A**bsolutely final
- **L**ocked from extension

---

## Real-World Scenarios

### Scenario 1: E-commerce Application
```java
// Request POJO
public class OrderRequest {
    private String productId;
    private int quantity;
    private String customerId;
    // getters/setters
}

// Internal POJO
public class Order {
    private String orderId;
    private String productCode;
    private int orderQuantity;
    private String customerCode;
    // getters/setters
}

// Order Status Enum
public enum OrderStatus {
    PLACED(1, "Order placed successfully"),
    CONFIRMED(2, "Order confirmed"),
    SHIPPED(3, "Order shipped"),
    DELIVERED(4, "Order delivered"),
    CANCELLED(-1, "Order cancelled");
    
    private final int statusCode;
    private final String message;
    
    OrderStatus(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    
    public boolean canBeCancelled() {
        return this == PLACED || this == CONFIRMED;
    }
}
```

### Scenario 2: Banking Application
```java
// Account POJO
public class Account {
    private String accountNumber;
    private String customerName;
    private double balance;
    private AccountType accountType;
    // getters/setters
}

// Account Type Enum
public enum AccountType {
    SAVINGS(0.04), CURRENT(0.01), FIXED_DEPOSIT(0.07);
    
    private final double interestRate;
    
    AccountType(double interestRate) {
        this.interestRate = interestRate;
    }
    
    public double calculateInterest(double principal) {
        return principal * interestRate;
    }
}

// Final utility class
public final class BankingUtils {
    private BankingUtils() {} // Prevent instantiation
    
    public static boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && accountNumber.length() == 10;
    }
}
```

---

This comprehensive guide covers all aspects of POJO, Enum, and Final classes with practical examples, best practices, and real-world applications. Use this for both learning and interview preparation!