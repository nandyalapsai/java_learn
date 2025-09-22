# Functional Interface & Lambda Expressions - Quick Reference Cheat Sheet

## 📋 Core Definitions

**Functional Interface**: Interface with exactly ONE abstract method
**Lambda Expression**: Concise way to implement functional interfaces (Java 8+)
**SAM Interface**: Single Abstract Method interface (same as functional interface)

## 🎯 Key Rules

✅ **Allowed in Functional Interface:**
- 1 abstract method (required)
- Multiple default methods
- Multiple static methods  
- Object class methods (toString, equals, hashCode)

❌ **Not Allowed:**
- More than 1 abstract method
- Zero abstract methods

## 📝 Lambda Syntax Patterns

```java
// No parameters
() -> expression
() -> { statements; }

// One parameter  
param -> expression
(param) -> expression
param -> { statements; }

// Multiple parameters
(p1, p2) -> expression
(p1, p2) -> { statements; return value; }
```

## 🏗️ Three Implementation Methods

```java
// 1. Traditional Class
class Eagle implements Bird {
    public boolean canFly() { return true; }
}

// 2. Anonymous Class
Bird bird = new Bird() {
    public boolean canFly() { return true; }
};

// 3. Lambda Expression
Bird bird = () -> true;
```

## 🔧 Built-in Functional Interfaces

| Interface | Input | Output | Method | Example |
|-----------|-------|--------|--------|---------|
| `Consumer<T>` | T | void | `accept(T)` | `x -> System.out.println(x)` |
| `Supplier<T>` | none | T | `get()` | `() -> "Hello"` |
| `Function<T,R>` | T | R | `apply(T)` | `x -> x.toString()` |
| `Predicate<T>` | T | boolean | `test(T)` | `x -> x > 5` |

## 💡 Quick Examples

```java
// Consumer - process input, no return
Consumer<String> logger = msg -> System.out.println("Log: " + msg);
logger.accept("Hello");

// Supplier - no input, return output  
Supplier<Integer> random = () -> (int)(Math.random() * 100);
int value = random.get();

// Function - transform input to output
Function<String, Integer> length = str -> str.length();
int len = length.apply("Hello");

// Predicate - test input, return boolean
Predicate<Integer> isEven = num -> num % 2 == 0;
boolean even = isEven.test(4);
```

## 🔗 Interface Inheritance Rules

| Scenario | Result | Example |
|----------|--------|---------|
| Functional extends Non-functional | ❌ Error | Too many abstract methods |
| Non-functional extends Functional | ✅ OK | Becomes non-functional |
| Functional extends Functional (same method) | ✅ OK | Method override |
| Functional extends Functional (different method) | ❌ Error | Too many abstract methods |

## 🚀 Common Usage Patterns

```java
// Event Handling
button.setOnAction(event -> handleClick());

// Stream Operations
list.stream()
    .filter(x -> x > 0)
    .map(x -> x * 2)
    .forEach(x -> System.out.println(x));

// Optional Processing
optional.ifPresent(value -> process(value));

// Async Operations
CompletableFuture.supplyAsync(() -> getData())
                 .thenAccept(result -> handleResult(result));
```

## ⚡ Best Practices

### ✅ DO
- Use `@FunctionalInterface` annotation
- Keep lambdas short and readable
- Use method references when possible: `String::length`
- Prefer built-in functional interfaces
- Use meaningful parameter names

### ❌ DON'T
- Add multiple abstract methods to functional interface
- Forget return statements in multi-line lambdas
- Use lambda for complex logic (use methods instead)
- Ignore exception handling in lambdas

## 🐛 Common Pitfalls & Fixes

| Problem | Cause | Solution |
|---------|-------|----------|
| Compilation error with multiple methods | Added 2+ abstract methods | Keep only 1 abstract method |
| Lambda not working | Used with regular interface | Use with functional interface only |
| Missing return | Multi-line lambda without return | Add explicit return statement |
| Complex lambda hard to read | Too much logic in lambda | Extract to separate method |

## 🧠 Memory Tricks

**CSFF**: **C**onsumer, **S**upplier, **F**unction, **F**ilter(Predicate)
- **C**onsumer: **C**onsumes input (no output)
- **S**upplier: **S**upplies output (no input)  
- **F**unction: **F**ull conversion (input → output)
- **F**ilter: **F**ilters with true/false

**PAB**: **P**arameters **A**rrow **B**ody → `(params) -> {body}`

**OAMO**: **O**ne **A**bstract **M**ethod **O**nly

## 📊 Comparison Quick Reference

| Aspect | Lambda | Anonymous Class | Method Reference |
|--------|--------|-----------------|------------------|
| Syntax | Concise | Verbose | Most concise |
| Performance | Fast | Slower | Fast |
| File generation | No | Yes | No |
| Interface requirement | Functional only | Any | Functional only |
| Complex logic | Limited | Full support | Not applicable |

## 🎯 Interview Essentials

**Q: What is functional interface?**
A: Interface with exactly one abstract method

**Q: Can lambda work with any interface?**  
A: No, only functional interfaces

**Q: Four built-in functional interfaces?**
A: Consumer, Supplier, Function, Predicate

**Q: Lambda vs Anonymous class?**
A: Lambda is more concise, better performance, functional interfaces only

**Q: Can functional interface have multiple methods?**
A: Yes, but only ONE can be abstract

## 🔍 Debugging Checklist

1. ✅ Interface has exactly 1 abstract method?
2. ✅ Using @FunctionalInterface annotation?
3. ✅ Lambda parameters match method signature?
4. ✅ Return statement present in multi-line lambda?
5. ✅ Proper braces for multi-line implementation?
6. ✅ Exception handling considered?

## 📚 Quick Syntax Reference Card

```java
// Functional Interface Declaration
@FunctionalInterface
interface MyInterface {
    ReturnType methodName(ParameterType param);
    // default, static, Object methods allowed
}

// Lambda Implementation
MyInterface impl = (param) -> {
    // implementation
    return result;
};

// Usage
ReturnType result = impl.methodName(value);
```

---
**💡 Pro Tip**: When in doubt, remember that lambda expressions are just a shorter way to write what you would normally write with anonymous classes, but they only work with functional interfaces!