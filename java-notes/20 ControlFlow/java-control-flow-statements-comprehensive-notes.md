# Java Control Flow Statements - Comprehensive Notes

## 📚 Learning Objectives
After studying these notes, you should be able to:
- Understand and implement all three types of control flow statements in Java
- Write conditional logic using if-else statements and switch expressions
- Create and control loops (for, while, do-while, for-each)
- Use branching statements (break, continue) effectively
- Understand the difference between switch statements and switch expressions (Java 12+)
- Apply best practices for control flow in real-world scenarios

---

## 🔑 Key Concepts & Definitions

### Control Flow Statements Overview
Control flow statements determine the order in which program statements are executed. Java has three main types:

1. **Decision Making Statements** - Conditional execution (if, if-else, switch)
2. **Iterative Statements** - Repetitive execution (for, while, do-while, for-each)
3. **Branching Statements** - Control transfer (break, continue)

---

## 1️⃣ Decision Making Statements

### 1.1 Simple If Statement

**Syntax:**
```java
if (condition) {
    // code to execute if condition is true
}
```

**Example:**
```java
public class Main {
    public static void main(String[] args) {
        int value = 10;
        if (value > 8) {
            System.out.println("Value is greater than 8");
        }
        System.out.println("This always executes");
    }
}
```

**Key Points:**
- Condition must evaluate to boolean (true/false)
- If true, code block executes; if false, it's skipped
- Program continues with statements after the if block

### 1.2 If-Else Statement

**Syntax:**
```java
if (condition) {
    // code if condition is true
} else {
    // code if condition is false
}
```

**Example:**
```java
int value = 7;
if (value > 8) {
    System.out.println("Value is greater than 8");
} else {
    System.out.println("Value is less than or equal to 8");
}
```

### 1.3 If-Else-If Ladder

**Syntax:**
```java
if (condition1) {
    // code block 1
} else if (condition2) {
    // code block 2
} else if (condition3) {
    // code block 3
} else {
    // default code block
}
```

**Example:**
```java
int value = 13;
if (value == 1) {
    System.out.println("Value is 1");
} else if (value == 2) {
    System.out.println("Value is 2");
} else if (value == 3) {
    System.out.println("Value is 3");
} else {
    System.out.println("Value is " + value);
}
```

**Key Points:**
- Evaluates conditions from top to bottom
- First true condition executes its block
- `else` block is optional but executes if no condition is true
- Only one block executes per ladder

### 1.4 Nested If Statements

**Example:**
```java
int value = 13;
if (value > 8) {
    System.out.println("Value is greater than 8");
    if (value < 15) {
        System.out.println("Value is greater than 8 but less than 15");
    } else {
        System.out.println("Value is 15 or greater");
    }
}
```

**Key Points:**
- If-else blocks can be nested inside other if-else blocks
- No limit on nesting levels
- Inner conditions only evaluate if outer condition is true

---

## 2️⃣ Switch Statements

### 2.1 Traditional Switch Statement

**Syntax:**
```java
switch (expression) {
    case value1:
        // code
        break;
    case value2:
        // code
        break;
    default:
        // default code
        break;
}
```

**Example:**
```java
int a = 1, b = 2;
switch (a + b) {
    case 1:
        System.out.println("Sum is 1");
        break;
    case 2:
        System.out.println("Sum is 2");
        break;
    case 3:
        System.out.println("Sum is 3");
        break;
    default:
        System.out.println("Sum is " + (a + b));
        break;
}
```

### 2.2 Switch Without Break (Fall-through)

**Example:**
```java
int a = 1, b = 2;
switch (a + b) {
    case 3:
        System.out.println("Sum is 3");
        // No break - falls through
    case 4:
        System.out.println("Sum is 4");
        // No break - falls through
    default:
        System.out.println("Default case");
}
// Output: All three statements print
```

### 2.3 Combining Cases

**Method 1 - Separate Lines:**
```java
String month = "March";
switch (month) {
    case "January":
    case "February":
    case "March":
        System.out.println("Quarter 1");
        break;
    case "April":
    case "May":
    case "June":
        System.out.println("Quarter 2");
        break;
}
```

**Method 2 - Same Line:**
```java
switch (month) {
    case "January", "February", "March":
        System.out.println("Quarter 1");
        break;
}
```

### 2.4 Switch Expression (Java 12+)

**Arrow Label Syntax:**
```java
String result = switch (value) {
    case 1 -> "One";
    case 2 -> "Two";
    case 3 -> "Three";
    default -> "Other";
};
```

**With Yield Statement:**
```java
String result = switch (value) {
    case 1 -> {
        // Complex logic here
        yield "One";
    }
    case 2 -> {
        // More complex logic
        yield "Two";
    }
    default -> "Other";
};
```

### 2.5 Switch Statement Rules

1. **No Duplicate Cases:** Two cases cannot have the same value
2. **Data Type Consistency:** Expression and case values must be the same type
3. **Literals or Constants Only:** Case values must be compile-time constants
4. **Coverage for Expressions:** Switch expressions must handle all possible cases
5. **No Return in Cases:** Cannot use `return` statement inside switch cases

### 2.6 Supported Data Types

**Allowed Types (10 total):**
- **Primitive:** `int`, `short`, `byte`, `char`
- **Wrapper:** `Integer`, `Short`, `Byte`, `Character`
- **Others:** `String`, `enum`

**Not Allowed:** `float`, `double`, `long`, `boolean`, custom objects

---

## 3️⃣ Iterative Statements (Loops)

### 3.1 For Loop

**Syntax:**
```java
for (initialization; condition; increment/decrement) {
    // code to repeat
}
```

**Example:**
```java
for (int i = 1; i <= 10; i++) {
    System.out.println("Value: " + i);
}
```

**Execution Flow:**
1. Initialize variable once
2. Check condition before each iteration
3. Execute code block if condition is true
4. Increment/decrement after each iteration
5. Repeat steps 2-4 until condition is false

### 3.2 Nested For Loops

**Example:**
```java
for (int x = 1; x <= 3; x++) {
    for (int y = 1; y <= 3; y++) {
        System.out.println("x=" + x + ", y=" + y);
    }
}
```

**Output Pattern:**
```
x=1, y=1
x=1, y=2  
x=1, y=3
x=2, y=1
x=2, y=2
x=2, y=3
x=3, y=1
x=3, y=2
x=3, y=3
```

### 3.3 While Loop

**Syntax:**
```java
initialization;
while (condition) {
    // code
    increment/decrement;
}
```

**Example:**
```java
int value = 1;
while (value <= 5) {
    System.out.println("Value: " + value);
    value++;
}
```

### 3.4 Do-While Loop

**Syntax:**
```java
initialization;
do {
    // code (executes at least once)
    increment/decrement;
} while (condition);
```

**Example:**
```java
int value = 1;
do {
    System.out.println("Value: " + value);
    value++;
} while (value <= 5);
```

**Key Difference:** Do-while executes the code block at least once before checking the condition.

### 3.5 For-Each Loop (Enhanced For Loop)

**Syntax:**
```java
for (dataType variable : array/collection) {
    // code using variable
}
```

**Example:**
```java
int[] array = {1, 2, 3, 4, 5};
for (int value : array) {
    System.out.println("Value: " + value);
}
```

**Key Points:**
- Used for iterating over arrays and collections
- Automatically handles iteration logic
- Cannot modify array elements directly
- Read-only access to elements

---

## 4️⃣ Branching Statements

### 4.1 Break Statement

**Purpose:** Exits the immediate enclosing loop or switch statement

**Example in Loop:**
```java
for (int i = 1; i <= 10; i++) {
    if (i == 3) {
        break; // Exits loop when i equals 3
    }
    System.out.println(i);
}
// Output: 1, 2
```

**Example in Nested Loops:**
```java
outer: for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        if (j == 2) {
            break; // Breaks inner loop only
        }
        System.out.println("i=" + i + ", j=" + j);
    }
}
```

### 4.2 Continue Statement

**Purpose:** Skips the current iteration and moves to the next iteration

**Example:**
```java
for (int i = 1; i <= 10; i++) {
    if (i == 3) {
        continue; // Skips printing when i equals 3
    }
    System.out.println(i);
}
// Output: 1, 2, 4, 5, 6, 7, 8, 9, 10
```

---

## 📊 Comparison Diagrams

### Switch Statement vs Switch Expression

| Feature | Switch Statement | Switch Expression |
|---------|------------------|-------------------|
| **Return Value** | No | Yes |
| **Break Required** | Yes | No (with arrow) |
| **All Cases Required** | No | Yes |
| **Syntax** | `case value:` | `case value ->` |
| **Java Version** | All versions | Java 12+ |

### Loop Comparison

| Loop Type | When to Use | Entry Condition | Guaranteed Execution |
|-----------|-------------|-----------------|---------------------|
| **For** | Known iterations | Before each iteration | No |
| **While** | Unknown iterations | Before each iteration | No |
| **Do-While** | At least one execution | After first iteration | Yes |
| **For-Each** | Array/Collection iteration | Automatic | No (if empty) |

---

## 🎯 Common Interview Questions

### Q1: What's the difference between break and continue?
**Answer:** 
- `break` exits the entire loop immediately
- `continue` skips the current iteration and moves to the next iteration

### Q2: Can you use return statement in switch case?
**Answer:** No, `return` is not allowed in switch cases. Use switch expressions with arrow syntax or yield for returning values.

### Q3: What data types are supported in switch statements?
**Answer:** `int`, `short`, `byte`, `char`, `Integer`, `Short`, `Byte`, `Character`, `String`, and `enum` (10 types total).

### Q4: What happens if you forget break in switch case?
**Answer:** Fall-through occurs - execution continues to the next case until a break is encountered or the switch ends.

### Q5: Can switch expression be used without default case?
**Answer:** No, switch expressions must handle all possible input cases, either by covering all cases explicitly or using default.

---

## 🛠️ Hands-on Exercises

### Exercise 1: Grade Calculator
Create a program that converts numerical grades to letter grades:
- 90-100: A
- 80-89: B  
- 70-79: C
- 60-69: D
- Below 60: F

### Exercise 2: Multiplication Table
Write nested loops to print a multiplication table from 1 to 10.

### Exercise 3: Prime Number Checker
Use loops and conditional statements to check if a number is prime.

### Exercise 4: Menu-Driven Calculator
Create a calculator using switch expression (Java 12+) that performs basic arithmetic operations.

### Exercise 5: Pattern Printing
Use nested loops to print various patterns like pyramids, diamonds, etc.

---

## 🌍 Real-World Use Cases

### 1. **User Authentication System**
```java
switch (userRole) {
    case "ADMIN" -> grantFullAccess();
    case "MANAGER" -> grantManagerAccess();
    case "USER" -> grantUserAccess();
    default -> denyAccess();
}
```

### 2. **Game State Management**
```java
for (Player player : players) {
    if (player.getHealth() <= 0) {
        continue; // Skip dead players
    }
    if (player.hasWon()) {
        break; // End game
    }
    player.takeTurn();
}
```

### 3. **Data Processing Pipeline**
```java
while (dataProcessor.hasMoreData()) {
    Data data = dataProcessor.getNext();
    if (!data.isValid()) {
        continue; // Skip invalid data
    }
    processData(data);
}
```

---

## ✅ Best Practices

### 1. **Control Flow Best Practices**
- Always use braces `{}` even for single statements
- Keep nesting levels minimal for readability
- Use meaningful variable names in loop conditions

### 2. **Switch Statement Guidelines**
- Always include `default` case for safety
- Use `break` statements to prevent fall-through (unless intentional)
- Prefer switch expressions over switch statements when possible

### 3. **Loop Optimization**
- Use for-each loops for array/collection iteration
- Avoid modifying loop variables inside the loop body
- Consider using `break` to exit early when condition is met

---

## ⚠️ Common Pitfalls & Debugging Tips

### 1. **Missing Break Statements**
```java
// BAD - Unintentional fall-through
switch (day) {
    case "Monday":
        System.out.println("Start of work week");
        // Missing break!
    case "Tuesday":
        System.out.println("Tuesday tasks");
        break;
}

// GOOD - Explicit break
switch (day) {
    case "Monday":
        System.out.println("Start of work week");
        break; // Explicit break
    case "Tuesday":
        System.out.println("Tuesday tasks");
        break;
}
```

### 2. **Infinite Loops**
```java
// BAD - Missing increment
int i = 0;
while (i < 10) {
    System.out.println(i);
    // Missing i++; causes infinite loop
}

// GOOD - Proper increment
int i = 0;
while (i < 10) {
    System.out.println(i);
    i++; // Proper increment
}
```

### 3. **Off-by-One Errors**
```java
// BAD - Missing last element
for (int i = 0; i < array.length - 1; i++) {
    // Misses last element
}

// GOOD - Correct boundary
for (int i = 0; i < array.length; i++) {
    // Processes all elements
}
```

---

## 🧠 Memory Hooks & Mnemonics

### Control Flow Memory Aids

1. **"If-Else-If" Ladder:** Think of it as a **decision tree** - once you find a true path, you follow it and ignore the rest.

2. **Switch Cases:** Remember **"SCALA"**:
   - **S**tring
   - **C**har  
   - **A**ll integer types (int, short, byte)
   - **L**etter grades (enum)
   - **A**nd their wrapper classes

3. **Loop Selection:** Remember **"FWD"**:
   - **F**or: When you know the **F**requency (number of iterations)
   - **W**hile: **W**hen condition might be false from start
   - **D**o-while: When you need to **D**o at least once

4. **Break vs Continue:** 
   - **Break** = **B**ye-bye loop (exit completely)
   - **Continue** = **C**arry on (skip current, continue with next)

---

## 📋 Quick Reference Cheat Sheet

### Control Flow Syntax Quick Reference

```java
// IF STATEMENTS
if (condition) { /* code */ }
if (condition) { /* code */ } else { /* code */ }
if (cond1) { /* code */ } else if (cond2) { /* code */ } else { /* code */ }

// SWITCH STATEMENT (Traditional)
switch (expression) {
    case value1: /* code */ break;
    case value2: /* code */ break;
    default: /* code */
}

// SWITCH EXPRESSION (Java 12+)
String result = switch (value) {
    case 1 -> "One";
    case 2 -> "Two";
    default -> "Other";
};

// LOOPS
for (init; condition; update) { /* code */ }
while (condition) { /* code */ }
do { /* code */ } while (condition);
for (Type var : collection) { /* code */ }

// BRANCHING
break;      // Exit loop/switch
continue;   // Skip to next iteration
```

### Decision Matrix for Loop Selection

| Scenario | Recommended Loop |
|----------|------------------|
| Known number of iterations | `for` |
| Unknown iterations, may not execute | `while` |
| Unknown iterations, must execute once | `do-while` |
| Iterate over array/collection | `for-each` |
| Need index access | `for` |
| Complex condition checking | `while` |

### Switch Expression Requirements (Java 12+)
- ✅ Must cover all possible cases (exhaustive)
- ✅ No break statements needed with arrows
- ✅ Can return values directly
- ✅ Use `yield` for complex blocks
- ❌ Cannot use `return` statements

---

## 🔄 Version Compatibility Notes

| Feature | Java Version | Notes |
|---------|--------------|-------|
| Traditional Switch | All versions | Classic syntax with break |
| Switch Expressions | Java 12+ | Arrow syntax, yield keyword |
| Enhanced For Loop | Java 5+ | For-each syntax |
| String in Switch | Java 7+ | String literals in cases |

---

*This comprehensive guide covers all aspects of Java control flow statements. Practice the exercises and refer to this cheat sheet during coding interviews and development work.*