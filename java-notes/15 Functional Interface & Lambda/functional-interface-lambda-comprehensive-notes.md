# Functional Interface & Lambda Expressions - Comprehensive Notes

## Learning Objectives

After studying these notes, you should be able to:
- Understand what functional interfaces are and why they're important
- Explain the relationship between functional interfaces and lambda expressions
- Implement functional interfaces using three different methods
- Use built-in functional interfaces (Consumer, Supplier, Function, Predicate)
- Write lambda expressions with proper syntax
- Handle interface inheritance scenarios with functional interfaces
- Apply functional programming concepts in real-world Java applications
- Debug common issues with functional interfaces and lambda expressions

## Key Concepts & Definitions

### Functional Interface
- **Definition**: An interface that contains **exactly one abstract method**
- **Alternative name**: SAM Interface (Single Abstract Method)
- **Annotation**: `@FunctionalInterface` (optional but recommended)
- **Key rule**: Only ONE abstract method allowed, but can have:
  - Multiple default methods
  - Multiple static methods
  - Methods inherited from Object class (toString, equals, hashCode)

### Lambda Expression
- **Definition**: A concise way to implement functional interfaces
- **Purpose**: Reduces boilerplate code and verbosity
- **Syntax**: `(parameters) -> { implementation }`
- **Java version**: Introduced in Java 8
- **Restriction**: Can only be used with functional interfaces

## Step-by-Step Explanation

### 1. Understanding Functional Interface

#### Basic Structure
```java
@FunctionalInterface  // Optional but recommended
public interface Bird {
    boolean canFly();  // Only one abstract method allowed
    
    // These are allowed:
    default void sleep() { System.out.println("Sleeping..."); }
    static void migrate() { System.out.println("Migrating..."); }
    String toString();  // Object class method
}
```

#### Why Use @FunctionalInterface Annotation?
- **Without annotation**: Interface works as functional interface but doesn't prevent adding more abstract methods
- **With annotation**: Compiler enforces single abstract method rule

```java
@FunctionalInterface
public interface Bird {
    boolean canFly();
    // boolean canSwim();  // Compilation error - violates functional interface rule
}
```

### 2. Three Ways to Implement Functional Interface

#### Method 1: Traditional Implementation (Using implements keyword)
```java
class Eagle implements Bird {
    @Override
    public boolean canFly() {
        return true;
    }
}

// Usage
Bird bird = new Eagle();
boolean result = bird.canFly();
```

#### Method 2: Anonymous Class Implementation
```java
Bird bird = new Bird() {
    @Override
    public boolean canFly() {
        System.out.println("Eagle is flying");
        return true;
    }
};
```

#### Method 3: Lambda Expression (Java 8+)
```java
// Multi-line implementation
Bird bird = () -> {
    System.out.println("Eagle is flying");
    return true;
};

// Single-line implementation
Bird bird = () -> true;
```

### 3. Lambda Expression Syntax Rules

#### Basic Syntax Components
```java
(parameters) -> { implementation }
```

#### Parameter Rules
- **No parameters**: `() -> implementation`
- **One parameter**: `param -> implementation` or `(param) -> implementation`
- **Multiple parameters**: `(param1, param2) -> implementation`

#### Implementation Rules
- **Single statement**: No braces needed, automatic return for expressions
- **Multiple statements**: Braces required, explicit return needed

#### Examples
```java
// No parameters, single line
Supplier<String> supplier = () -> "Hello World";

// One parameter, single line
Consumer<String> consumer = message -> System.out.println(message);

// Multiple parameters, multiple lines
Function<Integer, String> converter = (num) -> {
    if (num > 0) {
        return "Positive: " + num;
    }
    return "Non-positive: " + num;
};
```

### 4. Built-in Functional Interfaces

#### Consumer\<T>
- **Package**: `java.util.function`
- **Purpose**: Accepts one input, returns nothing
- **Method**: `accept(T t)`

```java
Consumer<Integer> logger = value -> {
    if (value > 10) {
        System.out.println("Value is greater than 10: " + value);
    }
};
logger.accept(15);  // Prints: Value is greater than 10: 15
```

#### Supplier\<T>
- **Purpose**: No input, produces one output
- **Method**: `T get()`

```java
Supplier<String> dataSupplier = () -> "This is the data";
// Or with block
Supplier<String> dataSupplier = () -> {
    return "This is the data";
};
String data = dataSupplier.get();
```

#### Function\<T, R>
- **Purpose**: Accepts one input, produces one output
- **Method**: `R apply(T t)`

```java
Function<Integer, String> converter = num -> num.toString();
String result = converter.apply(42);  // "42"
```

#### Predicate\<T>
- **Purpose**: Accepts one input, returns boolean
- **Method**: `boolean test(T t)`

```java
Predicate<Integer> isEven = num -> {
    return num % 2 == 0;
};
boolean result = isEven.test(4);  // true
```

### 5. Interface Inheritance Scenarios

#### Case 1: Functional Interface Extending Non-Functional Interface
```java
interface LivingThing {
    boolean canBreathe();
    boolean canMove();  // Two abstract methods
}

// This causes compilation error
@FunctionalInterface
interface Bird extends LivingThing {
    boolean canFly();  // Now has 3 abstract methods total
}
```

#### Case 2: Non-Functional Interface Extending Functional Interface
```java
@FunctionalInterface
interface LivingThing {
    boolean canBreathe();  // One abstract method
}

interface Bird extends LivingThing {  // No @FunctionalInterface
    boolean canFly();  // Now has 2 abstract methods - this is allowed
}
```

#### Case 3: Functional Interface Extending Functional Interface
```java
@FunctionalInterface
interface LivingThing {
    boolean canBreathe();
}

// This works - same method signature (override)
@FunctionalInterface
interface Bird extends LivingThing {
    boolean canBreathe();  // Same method - override
}

// This fails - different method
@FunctionalInterface
interface Bird extends LivingThing {
    boolean canFly();  // Different method - compilation error
}
```

## Examples with Code Snippets

### Complete Example: Consumer Usage
```java
import java.util.function.Consumer;

public class ConsumerExample {
    public static void main(String[] args) {
        // Consumer that processes integers
        Consumer<Integer> numberProcessor = num -> {
            System.out.println("Processing number: " + num);
            if (num % 2 == 0) {
                System.out.println(num + " is even");
            } else {
                System.out.println(num + " is odd");
            }
        };
        
        numberProcessor.accept(5);
        numberProcessor.accept(8);
    }
}
```

### Complete Example: Function Chaining
```java
import java.util.function.Function;

public class FunctionExample {
    public static void main(String[] args) {
        Function<String, Integer> stringLength = str -> str.length();
        Function<Integer, String> intToString = num -> "Length is: " + num;
        
        // Function composition
        Function<String, String> combined = stringLength.andThen(intToString);
        String result = combined.apply("Hello World");
        System.out.println(result);  // "Length is: 11"
    }
}
```

### Custom Functional Interface Example
```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

public class CalculatorExample {
    public static void main(String[] args) {
        Calculator add = (a, b) -> a + b;
        Calculator multiply = (a, b) -> a * b;
        Calculator subtract = (a, b) -> a - b;
        
        System.out.println("Addition: " + add.calculate(5, 3));
        System.out.println("Multiplication: " + multiply.calculate(5, 3));
        System.out.println("Subtraction: " + subtract.calculate(5, 3));
    }
}
```

## Diagrams

### Functional Interface Implementation Methods Flow
```
Functional Interface
        |
    ┌───┴───┬───────┬──────────┐
    │       │       │          │
   Class   Anon   Lambda    Method
 Implements Class Expression Reference
    │       │       │          │
    └───────┼───────┼──────────┘
            │       │
         Verbose   Concise
```

### Lambda Expression Syntax Structure
```
(parameters) -> { implementation }
     │              │
     │              └── Body (single line or block)
     │
     └── Input parameters (0 or more)
```

### Built-in Functional Interfaces Hierarchy
```
java.util.function
│
├── Consumer<T>     : T → void
├── Supplier<T>     : () → T
├── Function<T,R>   : T → R  
└── Predicate<T>    : T → boolean
```

## Common Interview Questions

### Q1: What is a functional interface?
**Answer**: A functional interface is an interface that contains exactly one abstract method. It can have multiple default methods, static methods, and methods inherited from Object class, but only one abstract method.

### Q2: Why do we use lambda expressions?
**Answer**: Lambda expressions reduce boilerplate code when implementing functional interfaces. Since functional interfaces have only one abstract method, lambda expressions eliminate the need to write method names and class declarations.

### Q3: Can we use lambda expressions with any interface?
**Answer**: No, lambda expressions can only be used with functional interfaces (interfaces with exactly one abstract method).

### Q4: What's the difference between anonymous class and lambda expression?
**Answer**: 
- Anonymous class creates a new .class file, lambda doesn't
- Lambda expressions are more concise and readable
- Anonymous classes can implement interfaces with multiple abstract methods
- Lambda expressions work only with functional interfaces

### Q5: What happens if we add another abstract method to a functional interface?
**Answer**: If the interface is annotated with @FunctionalInterface, it will cause a compilation error. Without the annotation, it becomes a regular interface with multiple abstract methods.

### Q6: Can functional interfaces extend other interfaces?
**Answer**: Yes, but with restrictions:
- If extending non-functional interface: Results in compilation error for @FunctionalInterface
- If extending functional interface with same method: Allowed (method override)
- If extending functional interface with different method: Compilation error

## Hands-on Exercises

### Exercise 1: Basic Implementation
Create a functional interface `StringProcessor` with method `process(String input)` and implement it using all three methods.

### Exercise 2: Built-in Functional Interfaces
Write a program using all four built-in functional interfaces to:
- Accept a list of numbers (Consumer)
- Generate random numbers (Supplier)  
- Convert numbers to strings (Function)
- Filter even numbers (Predicate)

### Exercise 3: Custom Functional Interface
Create a functional interface `MathOperation` that takes two integers and returns an integer. Implement addition, subtraction, multiplication, and division using lambda expressions.

### Exercise 4: Interface Inheritance
Create examples demonstrating all three inheritance scenarios mentioned in the video.

### Exercise 5: Real-world Application
Create a simple event handling system using functional interfaces where different types of events are processed using lambda expressions.

## Real-world Use Cases

### 1. Event Handling
```java
Button button = new Button();
button.setOnAction(event -> System.out.println("Button clicked!"));
```

### 2. Stream API Operations
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.stream()
     .filter(name -> name.length() > 3)
     .forEach(name -> System.out.println(name));
```

### 3. Callback Functions
```java
public void processAsync(String data, Consumer<String> callback) {
    // Process data asynchronously
    CompletableFuture.supplyAsync(() -> data.toUpperCase())
                    .thenAccept(callback);
}
```

### 4. Configuration and Validation
```java
Predicate<User> isAdult = user -> user.getAge() >= 18;
Predicate<User> hasValidEmail = user -> user.getEmail().contains("@");

boolean isValidUser = isAdult.and(hasValidEmail).test(user);
```

### 5. Factory Pattern Implementation
```java
Map<String, Supplier<Animal>> animalFactory = Map.of(
    "dog", () -> new Dog(),
    "cat", () -> new Cat(),
    "bird", () -> new Bird()
);
```

## Best Practices, Common Pitfalls, and Debugging Tips

### Best Practices
1. **Always use @FunctionalInterface annotation** for custom functional interfaces
2. **Keep lambda expressions short and readable** - if too complex, use method references
3. **Use method references when possible** - `String::length` instead of `s -> s.length()`
4. **Prefer existing functional interfaces** over creating new ones
5. **Use meaningful variable names** in lambda parameters

### Common Pitfalls
1. **Adding multiple abstract methods** to functional interface
2. **Forgetting return statements** in multi-line lambda expressions
3. **Incorrect parameter types** - let compiler infer when possible
4. **Using lambda where method reference is cleaner**
5. **Not handling exceptions** properly in lambda expressions

### Debugging Tips
1. **Add explicit parameter types** if compiler inference fails
2. **Use parentheses around parameters** for clarity
3. **Break complex lambdas into multiple lines** with proper braces
4. **Use debugger breakpoints** inside lambda expressions
5. **Test functional interfaces separately** before combining with streams

## Comparisons with Related Concepts

### Lambda vs Anonymous Classes
| Aspect | Lambda Expression | Anonymous Class |
|--------|-------------------|-----------------|
| Syntax | Concise | Verbose |
| Performance | Better (no new class file) | Slower |
| Memory | Less overhead | More overhead |
| Usage | Functional interfaces only | Any interface/class |
| `this` reference | Enclosing class | Anonymous class |

### Lambda vs Method References
| Aspect | Lambda Expression | Method Reference |
|--------|-------------------|------------------|
| Syntax | `x -> method(x)` | `Class::method` |
| Readability | Good for complex logic | Better for simple calls |
| Performance | Same | Same |
| Flexibility | More flexible | Limited to existing methods |

### Functional vs Regular Interfaces
| Aspect | Functional Interface | Regular Interface |
|--------|---------------------|-------------------|
| Abstract methods | Exactly one | Zero or more |
| Lambda usage | Yes | No |
| Default methods | Allowed | Allowed |
| Static methods | Allowed | Allowed |

## Memory Hooks & Mnemonics

### CSFF - Consumer, Supplier, Function, Function
- **C**onsumer: **C**onsumes input, returns nothing
- **S**upplier: **S**upplies output, takes nothing  
- **F**unction: **F**ull transformation (input → output)
- **F**ilter (Predicate): **F**ilters with boolean result

### Lambda Syntax Memory Hook
"**P**arameters **A**rrow **B**ody" → `(P) -> {B}`

### Functional Interface Rule
"**O**ne **A**bstract **M**ethod **O**nly" → **OAMO**

### Interface Inheritance Memory Hook
"**S**ame **S**ignature **S**ucceeds, **D**ifferent **D**ies" → When functional interface extends another functional interface

## Cheat Sheet / Quick Revision

### Syntax Quick Reference
```java
// Basic lambda syntax
() -> expression                    // No parameters
param -> expression                 // Single parameter
(param1, param2) -> expression     // Multiple parameters
() -> { statements; }              // Block body
(params) -> { return value; }      // Block with return
```

### Built-in Functional Interfaces
```java
Consumer<T>:   T → void           accept(T)
Supplier<T>:   () → T             get()
Function<T,R>: T → R              apply(T)
Predicate<T>:  T → boolean        test(T)
```

### Implementation Methods Comparison
```java
// 1. Traditional class
class MyClass implements MyInterface {
    public void method() { /* implementation */ }
}

// 2. Anonymous class  
MyInterface obj = new MyInterface() {
    public void method() { /* implementation */ }
};

// 3. Lambda expression
MyInterface obj = () -> { /* implementation */ };
```

### Common Patterns
```java
// Event handling
button.addActionListener(e -> handleClick());

// Stream operations
list.stream().filter(x -> x > 5).map(x -> x * 2);

// Optional operations
optional.ifPresent(value -> process(value));

// CompletableFuture
future.thenAccept(result -> logResult(result));
```

### Key Rules to Remember
1. Functional interface = Exactly 1 abstract method
2. @FunctionalInterface annotation is optional but recommended
3. Lambda expressions only work with functional interfaces
4. Default and static methods don't count as abstract methods
5. Object class methods don't count as abstract methods
6. Same method signature in inheritance = override (allowed)
7. Different method signature in inheritance = compilation error

### Interview Must-Knows
- Definition of functional interface
- Four built-in functional interfaces and their purposes
- Lambda expression syntax and rules
- Difference between lambda and anonymous classes
- Interface inheritance scenarios
- When to use which functional interface type
- Performance benefits of lambda expressions