# Java Public Class and File Name Rules

## Learning Objectives
After reading these notes, you should be able to:
- Understand why Java allows only one public class per file
- Explain the relationship between public class names and file names
- Describe how JVM locates and invokes the main method
- Answer common interview questions about Java class restrictions

## Key Concepts & Definitions

### Main Method
- **Entry Point**: The starting point of any Java application
- **Signature**: `public static void main(String[] args)`
- **Purpose**: Called by JVM to begin program execution

### Public Class Restrictions
- **One per file**: Only one public class allowed per Java file
- **Name matching**: Public class name must match the file name exactly
- **JVM accessibility**: Public classes are accessible from outside their package

## Step-by-Step Explanation

### Why Main Method Must Be in a Public Class

1. **JVM Invocation**: JVM needs to call the main method from outside
2. **Access Requirements**: JVM requires access to the class containing main
3. **Package Boundaries**: Public modifier allows access across packages
4. **Static Nature**: JVM calls main using class name (e.g., `ClassName.main()`)

### Why Public Class Name Must Match File Name

1. **JVM Confusion Prevention**: Without this rule, JVM wouldn't know which class contains main
2. **Unique Identification**: File name becomes the identifier for the public class
3. **Compilation Efficiency**: Compiler can quickly locate classes

## Examples with Code Snippets

### Correct Implementation
````java
public class Employee {
    public static void main(String[] args) {
        System.out.println("Employee application started");
    }
}

class Manager {
    // Other code...
}

class Developer {
    // Other code...
}
````

### Incorrect Implementation (Won't Compile)
````java
public class Manager {  // ERROR: Class name doesn't match file name
    public static void main(String[] args) {
        System.out.println("This won't work");
    }
}
````

## Diagrams

### File Structure Diagram
```
Employee.java
├── public class Employee (✓ matches file name)
│   └── main method (entry point)
├── class Manager (✓ non-public, allowed)
├── class Developer (✓ non-public, allowed)
└── class Assistant (✓ non-public, allowed)
```

### JVM Class Loading Process
```
1. JVM receives: java Employee
2. JVM looks for: Employee.java
3. JVM expects: public class Employee
4. JVM finds: main method in Employee class
5. JVM executes: Employee.main()
```

## Common Interview Questions

### Q1: Why can a Java file have only one public class?
**Answer**: To avoid JVM confusion when locating the main method. JVM uses the file name to identify which public class contains the entry point.

### Q2: Can we have multiple non-public classes in one file?
**Answer**: Yes, you can have multiple non-public (default, package-private) classes in a single file.

### Q3: What happens if public class name doesn't match file name?
**Answer**: Compilation error occurs. Java compiler enforces this rule strictly.

### Q4: Why is the main method static?
**Answer**: So JVM can call it without creating an instance of the class, using just the class name.

## Hands-on Exercises

### Exercise 1: Basic Implementation
Create a file `Calculator.java` with:
- One public class `Calculator`
- Main method that prints "Calculator started"
- Two additional non-public classes: `Addition` and `Subtraction`

### Exercise 2: Error Identification
Identify what's wrong with this code:
````java
public class Calculator {  // What's the error here?
    public static void main(String[] args) {
        System.out.println("Running calculator");
    }
}
````

## Real-world Use Cases

### Large Applications
- **Spring Boot**: Main application class follows this pattern
- **Enterprise Applications**: Entry point classes in microservices
- **Utility Libraries**: Public API classes that serve as entry points

### Development Scenarios
- **Testing**: Each test class can have its own main method for standalone testing
- **Batch Processing**: Multiple utility classes with specific entry points
- **Configuration**: Main classes that initialize application settings

## Best Practices

### Do's ✅
- Always match public class name with file name
- Keep main method in the primary public class
- Use meaningful class and file names
- Group related non-public classes in the same file

### Don'ts ❌
- Don't create multiple public classes in one file
- Don't mismatch class and file names
- Don't put main method in non-public classes for application entry points

## Common Pitfalls & Debugging Tips

### Compilation Errors
- **Error**: "class X is public, should be declared in a file named X.java"
- **Solution**: Rename file to match public class name

### Runtime Issues
- **Error**: "Could not find or load main class"
- **Solution**: Ensure public class name matches file name exactly (case-sensitive)

### IDE Warnings
- Most IDEs automatically enforce naming conventions
- Pay attention to refactoring tools when renaming classes

## Memory Hooks & Mnemonics

### "One File, One Public Star"
- **One** file can have only **One** **Public** class
- That class is the "**Star**" (main character) of the file

### "Name Game Rule"
- **File** name = **Public** class name
- No exceptions, no variations

### "JVM's GPS System"
- JVM uses file name as GPS coordinates
- Public class name must match the address (file name)
- Otherwise, JVM gets lost and can't find the main method

## Cheat Sheet / Quick Revision

### Core Rules
| Rule | Description | Example |
|------|-------------|---------|
| One Public Class | Only one public class per file | `public class Employee` |
| Name Matching | Public class name = File name | `Employee.java` → `public class Employee` |
| Main Method Location | Must be in public class | `public static void main(String[] args)` |
| Multiple Non-Public | Allowed unlimited non-public classes | `class Manager`, `class Developer` |

### Quick Syntax Check
````java
// Template for any Java file
// filepath: ClassName.java
public class ClassName {  // Must match file name
    public static void main(String[] args) {  // Entry point
        // Application logic starts here
    }
}

// Additional non-public classes allowed
class HelperClass1 { }
class HelperClass2 { }
````

### Interview Quick Points
1. **Why restriction exists**: JVM needs clear path to main method
2. **How JVM finds main**: File name → Public class → Main method
3. **Compilation rule**: Public class name must match file name exactly
4. **Multiple classes**: Only non-public classes can coexist with one public class