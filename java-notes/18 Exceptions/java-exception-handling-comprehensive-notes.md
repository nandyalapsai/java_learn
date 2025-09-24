# Java Exception Handling - Comprehensive Notes

## Learning Objectives

After studying these notes, you should be able to:

1. **Understand** what exceptions are and why they occur
2. **Differentiate** between Error, Checked, and Unchecked exceptions
3. **Implement** try-catch-finally blocks effectively
4. **Use** throw and throws keywords appropriately
5. **Create** custom exceptions for specific use cases
6. **Handle** multiple exceptions using various patterns
7. **Debug** applications using stack traces
8. **Apply** exception handling best practices in real-world scenarios

---

## Key Concepts & Definitions

### What is an Exception?
- **Exception**: An event that occurs during program execution that disrupts the normal flow of instructions
- **Exception Object**: Created by runtime system containing:
  - Exception type
  - Error message
  - Stack trace (sequence of method calls leading to exception)

### Exception Hierarchy
```
Object
  └── Throwable
      ├── Error (JVM-related issues)
      └── Exception
          ├── RuntimeException (Unchecked)
          └── Other Exceptions (Checked)
```

### Key Terminology
- **Stack Trace**: Path from where error occurred to the starting point
- **Exception Propagation**: Process of searching for exception handler up the call stack
- **Exception Handler**: Code block that catches and processes exceptions

---

## Exception Classification

### 1. Error vs Exception

| Aspect | Error | Exception |
|--------|--------|-----------|
| **Control** | Cannot be handled by programmer | Can be handled by programmer |
| **Cause** | JVM-related issues | Code-related issues |
| **Examples** | OutOfMemoryError, StackOverflowError | NullPointerException, IOException |
| **Recovery** | Program should terminate | Program can recover |

### 2. Checked vs Unchecked Exceptions

| Type | Checked (Compile-time) | Unchecked (Runtime) |
|------|----------------------|-------------------|
| **Detection** | At compile time | At runtime |
| **Handling** | Mandatory | Optional |
| **Compilation** | Won't compile if not handled | Compiles fine |
| **Parent Class** | Exception (excluding RuntimeException) | RuntimeException |

---

## Types of Runtime Exceptions

### Common Runtime Exceptions with Examples

#### 1. ClassCastException
```java
Object val = 123;
String str = (String) val; // ClassCastException
```

#### 2. ArithmeticException
```java
int result = 5 / 0; // ArithmeticException: / by zero
```

#### 3. ArrayIndexOutOfBoundsException
```java
int[] arr = new int[2];
System.out.println(arr[3]); // ArrayIndexOutOfBoundsException
```

#### 4. StringIndexOutOfBoundsException
```java
String str = "Hello";
char ch = str.charAt(10); // StringIndexOutOfBoundsException
```

#### 5. NullPointerException
```java
String str = null;
int length = str.length(); // NullPointerException
```

#### 6. NumberFormatException
```java
int num = Integer.parseInt("ABC"); // NumberFormatException
```

---

## Exception Handling Mechanisms

### 1. Try-Catch Block

#### Basic Syntax
```java
try {
    // Code that might throw exception
} catch (ExceptionType e) {
    // Handle exception
}
```

#### Multiple Catch Blocks
```java
try {
    methodThatThrowsExceptions();
} catch (ClassNotFoundException e) {
    System.out.println("Class not found: " + e.getMessage());
} catch (InterruptedException e) {
    System.out.println("Thread interrupted: " + e.getMessage());
} catch (Exception e) {
    System.out.println("General exception: " + e.getMessage());
}
```

#### Multi-Catch (Java 7+)
```java
try {
    riskyMethod();
} catch (ClassNotFoundException | InterruptedException e) {
    System.out.println("Either class not found or interrupted: " + e.getMessage());
}
```

### 2. Finally Block

#### Characteristics
- **Always executes** (except in extreme cases like system shutdown)
- **Used for cleanup** (closing resources, logging)
- **Only one finally block** allowed per try-catch

```java
try {
    // Risky code
} catch (Exception e) {
    // Handle exception
} finally {
    // Cleanup code - always executes
    System.out.println("Cleanup completed");
}
```

### 3. Throws Declaration

#### Method Declaration
```java
public void riskyMethod() throws IOException, SQLException {
    // Method might throw these exceptions
    // Caller must handle them
}
```

#### Propagation Example
```java
public class ExceptionPropagation {
    public static void main(String[] args) {
        try {
            methodA();
        } catch (IOException e) {
            System.out.println("Caught in main: " + e.getMessage());
        }
    }
    
    static void methodA() throws IOException {
        methodB(); // Delegates exception handling to caller
    }
    
    static void methodB() throws IOException {
        throw new IOException("File not found");
    }
}
```

### 4. Throw Keyword

#### Throwing New Exception
```java
public void validateAge(int age) {
    if (age < 0) {
        throw new IllegalArgumentException("Age cannot be negative");
    }
}
```

#### Re-throwing Exception
```java
try {
    riskyOperation();
} catch (SQLException e) {
    // Log the error
    logger.error("Database error occurred", e);
    // Re-throw for caller to handle
    throw e;
}
```

---

## Custom Exceptions

### Creating Custom Exception
```java
// Custom checked exception
public class MyCustomException extends Exception {
    public MyCustomException(String message) {
        super(message);
    }
    
    public MyCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}

// Custom unchecked exception
public class MyRuntimeException extends RuntimeException {
    public MyRuntimeException(String message) {
        super(message);
    }
}
```

### Using Custom Exception
```java
public class BankAccount {
    private double balance;
    
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException(
                "Insufficient funds. Available: " + balance + ", Requested: " + amount
            );
        }
        balance -= amount;
    }
}
```

---

## Exception Handling Flow Diagram

```
Program Execution
       ↓
   Exception Occurs
       ↓
Exception Object Created
       ↓
Search for Handler in Current Method
       ↓
   Handler Found? ──Yes──→ Execute Handler ──→ Continue/Exit
       ↓ No
   Move to Caller Method
       ↓
   Handler Found? ──Yes──→ Execute Handler ──→ Continue/Exit
       ↓ No
   Continue Up Call Stack
       ↓
   Reach Main Method?
       ↓ Yes (No Handler)
   Program Terminates
   Print Stack Trace
```

---

## Practical Examples

### Example 1: File Processing with Exception Handling
```java
public class FileProcessor {
    public String readFile(String fileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
            return null;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error closing file: " + e.getMessage());
                }
            }
        }
    }
}
```

### Example 2: Exception Handling in Method Chain
```java
public class ExceptionChainExample {
    public static void main(String[] args) {
        try {
            method1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void method1() throws Exception {
        method2();
    }
    
    static void method2() throws Exception {
        method3();
    }
    
    static void method3() throws Exception {
        throw new Exception("Error in method3");
        // Stack trace will show: method3 → method2 → method1 → main
    }
}
```

---

## Common Interview Questions

### Q1: What's the difference between Error and Exception?
**Answer**: Errors are serious problems caused by JVM (like OutOfMemoryError) that applications shouldn't try to handle. Exceptions are conditions that applications can catch and handle to recover gracefully.

### Q2: What's the difference between checked and unchecked exceptions?
**Answer**: 
- **Checked**: Compile-time exceptions that must be handled or declared (IOException, SQLException)
- **Unchecked**: Runtime exceptions that don't require mandatory handling (NullPointerException, ArrayIndexOutOfBoundsException)

### Q3: Can we have try without catch?
**Answer**: Yes, try can be used with finally block without catch: `try { } finally { }`

### Q4: What happens if an exception occurs in finally block?
**Answer**: If an exception occurs in finally block, it will suppress any exception from try/catch block. The finally block exception becomes the primary exception.

### Q5: Can we have multiple catch blocks for the same exception?
**Answer**: No, you cannot have multiple catch blocks for the same exception type. It will result in compilation error.

### Q6: What's the difference between throw and throws?
**Answer**:
- **throw**: Used to explicitly throw an exception from method/block
- **throws**: Used in method declaration to specify exceptions that method might throw

---

## Hands-on Exercises

### Exercise 1: Basic Exception Handling
```java
// Complete this method to handle division by zero
public static double safeDivide(double a, double b) {
    // Your code here
    // Return -1 if division by zero occurs
}
```

### Exercise 2: Custom Exception
```java
// Create a custom exception for invalid email format
// Write a method that validates email and throws this exception
```

### Exercise 3: Exception Propagation
```java
// Create a chain of 3 methods where method3 throws exception,
// method2 propagates it, and method1 handles it
```

### Exercise 4: Resource Management
```java
// Write a method that reads from a file and ensures
// the file is closed even if an exception occurs
```

---

## Real-world Use Cases

### 1. Database Operations
```java
public class DatabaseService {
    public User getUserById(int id) throws UserNotFoundException {
        try {
            // Database query logic
            User user = database.findById(id);
            if (user == null) {
                throw new UserNotFoundException("User with ID " + id + " not found");
            }
            return user;
        } catch (SQLException e) {
            logger.error("Database error while fetching user", e);
            throw new DatabaseException("Unable to fetch user", e);
        }
    }
}
```

### 2. API Response Handling
```java
public class ApiClient {
    public String makeApiCall(String url) throws ApiException {
        try {
            HttpResponse response = httpClient.get(url);
            if (response.getStatusCode() != 200) {
                throw new ApiException("API call failed with status: " + response.getStatusCode());
            }
            return response.getBody();
        } catch (IOException e) {
            throw new ApiException("Network error during API call", e);
        }
    }
}
```

### 3. Input Validation
```java
public class UserRegistration {
    public void registerUser(String email, int age) throws ValidationException {
        if (email == null || !email.contains("@")) {
            throw new ValidationException("Invalid email format");
        }
        if (age < 18) {
            throw new ValidationException("User must be at least 18 years old");
        }
        // Registration logic
    }
}
```

---

## Best Practices

### ✅ Do's

1. **Be Specific**: Catch specific exceptions rather than generic Exception
```java
// Good
try {
    // code
} catch (FileNotFoundException e) {
    // handle file not found
} catch (IOException e) {
    // handle other IO issues
}

// Avoid
try {
    // code  
} catch (Exception e) {
    // too generic
}
```

2. **Clean Up Resources**: Always close resources in finally or use try-with-resources
```java
// Java 7+ try-with-resources
try (FileReader fr = new FileReader("file.txt")) {
    // File automatically closed
} catch (IOException e) {
    // handle exception
}
```

3. **Log Exceptions**: Always log exceptions with sufficient context
```java
catch (SQLException e) {
    logger.error("Failed to save user data for userId: " + userId, e);
    throw new ServiceException("Unable to save user", e);
}
```

4. **Fail Fast**: Validate inputs early and throw exceptions immediately
```java
public void processOrder(Order order) {
    if (order == null) {
        throw new IllegalArgumentException("Order cannot be null");
    }
    // Process order
}
```

### ❌ Don'ts

1. **Don't Ignore Exceptions**
```java
// Bad
try {
    riskyOperation();
} catch (Exception e) {
    // Ignoring exception - very bad practice
}
```

2. **Don't Catch and Re-throw Without Adding Value**
```java
// Bad
try {
    service.call();
} catch (Exception e) {
    throw e; // No value added
}
```

3. **Don't Use Exceptions for Control Flow**
```java
// Bad
try {
    return array[index];
} catch (ArrayIndexOutOfBoundsException e) {
    return null;
}

// Good
if (index >= 0 && index < array.length) {
    return array[index];
}
return null;
```

---

## Common Pitfalls & Debugging Tips

### Pitfall 1: Catching Parent Exception First
```java
// Wrong order - will cause compilation error
try {
    riskyMethod();
} catch (Exception e) {        // Parent class first
    // handle
} catch (IOException e) {      // Child class - unreachable code
    // handle
}

// Correct order
try {
    riskyMethod();
} catch (IOException e) {      // Specific exception first
    // handle
} catch (Exception e) {        // Parent exception last
    // handle
}
```

### Pitfall 2: Resource Leaks
```java
// Potential resource leak
FileInputStream fis = null;
try {
    fis = new FileInputStream("file.txt");
    // Process file
} catch (IOException e) {
    // If exception occurs here, file might not be closed
} finally {
    if (fis != null) {
        try {
            fis.close();  // Always close in finally
        } catch (IOException e) {
            // Log closing error
        }
    }
}
```

### Debugging Tips
1. **Read Stack Trace Bottom-Up**: Start from where you called the method
2. **Use IDE Debugging**: Set breakpoints in catch blocks
3. **Log with Context**: Include relevant variable values
4. **Check Exception Cause Chain**: Use `getCause()` for wrapped exceptions

---

## Memory Hooks & Mnemonics

### Exception Hierarchy Mnemonic
**"Object Throws Two: Errors Except Runtime"**
- **Object** → Throwable → **Two** branches → **Errors** and **Except**ions → **Runtime** exceptions

### Try-Catch-Finally Order
**"Try First, Catch Problems, Finally Always"**
- **Try** block comes first
- **Catch** handles problems  
- **Finally** always executes

### Checked vs Unchecked
**"Checked = Compile-time Complaint"**
- **Checked** exceptions cause **Compile-time** errors if not handled
- **Unchecked** exceptions are discovered at **Runtime**

### Throw vs Throws
**"Throw Objects, Throws Declares"**
- **Throw** actually throws an exception object
- **Throws** declares what exceptions a method might throw

---

## Performance Considerations

### Exception Handling Costs
1. **Stack Trace Creation**: Expensive operation
2. **Exception Propagation**: Cost increases with call stack depth
3. **Object Creation**: Exception objects consume memory

### Optimization Tips
```java
// Avoid exceptions in loops
// Bad
for (int i = 0; i < 1000000; i++) {
    try {
        riskyOperation();
    } catch (Exception e) {
        // Handle
    }
}

// Better
try {
    for (int i = 0; i < 1000000; i++) {
        riskyOperation();
    }
} catch (Exception e) {
    // Handle once outside loop
}
```

---

## Quick Revision Cheat Sheet

### Exception Types
- **Error**: JVM issues (OutOfMemoryError, StackOverflowError)
- **Checked**: Must handle at compile time (IOException, SQLException)
- **Unchecked**: Optional handling, found at runtime (NullPointerException, IllegalArgumentException)

### Keywords Summary
- **try**: Block containing risky code
- **catch**: Handles specific exception types
- **finally**: Always executes (cleanup code)
- **throw**: Explicitly throws an exception
- **throws**: Declares method can throw exceptions

### Exception Handling Patterns
```java
// Basic pattern
try { } catch (SpecificException e) { } finally { }

// Multiple exceptions
try { } catch (Exception1 | Exception2 e) { }

// Re-throwing
catch (Exception e) { 
    log(e); 
    throw e; 
}

// Custom exception
throw new CustomException("message");
```

### Best Practices Checklist
- ✅ Catch specific exceptions
- ✅ Use try-with-resources for cleanup
- ✅ Log exceptions with context
- ✅ Validate inputs early
- ❌ Don't ignore exceptions
- ❌ Don't use exceptions for control flow
- ❌ Don't catch parent exceptions first

---

## Advanced Topics for Further Study

1. **Exception Suppression** (Java 7+)
2. **Try-with-Resources** enhancements
3. **Exception Translation** patterns
4. **Asynchronous Exception Handling**
5. **Exception Handling in Multithreading**
6. **Performance Monitoring** of exception-heavy code

---

*Remember: Exception handling is about graceful degradation - your application should handle unexpected situations elegantly while maintaining system stability and providing meaningful feedback.*