# Java Exception Handling - Quick Reference Cheat Sheet

## Exception Hierarchy at a Glance
```
Object
  └── Throwable
      ├── Error (JVM issues - don't handle)
      │   ├── OutOfMemoryError
      │   └── StackOverflowError
      └── Exception
          ├── RuntimeException (Unchecked)
          │   ├── NullPointerException
          │   ├── ArrayIndexOutOfBoundsException
          │   ├── ClassCastException
          │   ├── ArithmeticException
          │   └── IllegalArgumentException
          └── Checked Exceptions
              ├── IOException
              ├── SQLException
              ├── ClassNotFoundException
              └── InterruptedException
```

## Quick Comparison Table

| Aspect | Checked | Unchecked | Error |
|--------|---------|-----------|--------|
| **When Detected** | Compile-time | Runtime | Runtime |
| **Handling** | Mandatory | Optional | Don't handle |
| **Compilation** | Won't compile if unhandled | Compiles fine | Compiles fine |
| **Parent** | Exception (not RuntimeException) | RuntimeException | Error |

## Essential Keywords

| Keyword | Purpose | Example |
|---------|---------|---------|
| `try` | Contains risky code | `try { riskyCode(); }` |
| `catch` | Handles exceptions | `catch (IOException e) { }` |
| `finally` | Always executes | `finally { cleanup(); }` |
| `throw` | Throws an exception | `throw new Exception("error");` |
| `throws` | Declares exceptions | `void method() throws IOException` |

## Common Exception Patterns

### 1. Basic Try-Catch
```java
try {
    // risky code
} catch (SpecificException e) {
    // handle specific exception
} catch (Exception e) {
    // handle any other exception
} finally {
    // cleanup code
}
```

### 2. Multiple Exceptions (Java 7+)
```java
try {
    // code
} catch (IOException | SQLException e) {
    // handle both types
}
```

### 3. Try-with-Resources (Java 7+)
```java
try (FileReader fr = new FileReader("file.txt")) {
    // use resource
} catch (IOException e) {
    // handle exception
}
// resource automatically closed
```

### 4. Custom Exception
```java
public class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
}

// Usage
throw new MyException("Something went wrong");
```

## Most Common Runtime Exceptions

```java
// NullPointerException
String str = null;
int len = str.length(); // NPE

// ArrayIndexOutOfBoundsException  
int[] arr = {1, 2, 3};
int val = arr[5]; // AIOOBE

// ArithmeticException
int result = 10 / 0; // ArithmeticException

// ClassCastException
Object obj = "Hello";
Integer num = (Integer) obj; // CCE

// NumberFormatException
int num = Integer.parseInt("abc"); // NFE
```

## Exception Handling Best Practices

### ✅ DO
- Catch specific exceptions first, general ones last
- Use try-with-resources for auto-cleanup
- Log exceptions with meaningful context
- Validate inputs early (fail-fast)
- Close resources in finally block
- Create custom exceptions for business logic

### ❌ DON'T
- Catch and ignore exceptions silently
- Use exceptions for normal control flow
- Catch Exception (too generic) unless necessary
- Have empty catch blocks
- Re-throw without adding value
- Put catch blocks for impossible exceptions

## Memory Aids

### Exception Order Mnemonic
**"Specific First, General Last"** - Always catch specific exceptions before general ones

### Try-Catch-Finally Flow
**"Try → Catch Problems → Finally Always"**

### Checked vs Unchecked
- **Checked** = **C**ompile-time **C**omplaint
- **Unchecked** = **R**untime **R**ealization

## Quick Debugging Checklist

1. **Read stack trace from bottom up** (your code usually at bottom)
2. **Look for line numbers** in your classes
3. **Check for null values** (most common cause)
4. **Verify array/string bounds** 
5. **Check method signatures** for throws clauses
6. **Use IDE debugger** to step through code

## Exception Handling Decision Tree

```
Exception Occurred?
    ├─ Can I prevent it with validation? → Use if/else instead
    ├─ Is it a business logic error? → Create custom exception
    ├─ Can I recover from it? → Handle with try-catch
    └─ Should caller handle it? → Use throws declaration
```

## Performance Tips

- **Avoid exceptions in loops** - move try-catch outside
- **Don't use exceptions for control flow** - use conditional logic
- **Cache exception instances** if throwing frequently
- **Prefer return codes** for expected error conditions

## Interview Quick Answers

**Q: Difference between Error and Exception?**
A: Error = JVM issues (don't handle), Exception = recoverable issues (can handle)

**Q: Checked vs Unchecked?**
A: Checked = must handle at compile-time, Unchecked = optional, detected at runtime

**Q: Can we have try without catch?**
A: Yes, with finally: `try { } finally { }`

**Q: What happens if exception in finally?**
A: Finally block exception suppresses original exception

**Q: Multiple catch for same exception?**
A: No, compilation error - each exception type can only be caught once

## Code Templates

### Template 1: Safe Method Call
```java
public ResultType safeMethodCall() {
    try {
        return riskyMethod();
    } catch (SpecificException e) {
        logger.error("Error occurred: ", e);
        return defaultValue;
    } finally {
        cleanup();
    }
}
```

### Template 2: Input Validation
```java
public void validateInput(String input) throws ValidationException {
    if (input == null || input.isEmpty()) {
        throw new ValidationException("Input cannot be null or empty");
    }
    // process input
}
```

### Template 3: Resource Management
```java
public String readFile(String filename) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
        return reader.lines()
                     .collect(Collectors.joining("\n"));
    }
}
```

## Common Error Messages & Solutions

| Error Message | Likely Cause | Solution |
|---------------|--------------|----------|
| NullPointerException | Accessing null object | Check for null before use |
| ArrayIndexOutOfBoundsException | Invalid array index | Validate index bounds |
| ClassCastException | Invalid type casting | Use instanceof before casting |
| ArithmeticException: / by zero | Division by zero | Check divisor before division |
| NumberFormatException | Invalid number format | Validate string before parsing |

---

**Remember**: Exception handling is about graceful failure - handle what you can, propagate what you can't, and always clean up resources!