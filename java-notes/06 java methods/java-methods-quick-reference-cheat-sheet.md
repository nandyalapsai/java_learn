# Java Methods - Quick Reference Cheat Sheet

## 🚀 Method Declaration Syntax
```java
[access_specifier] [return_type] methodName([parameters]) [throws exception] {
    // method body
    return value; // if not void
}
```

## 🚪 Access Specifiers Quick Reference

| Specifier | Scope | Memory Hook |
|-----------|-------|-------------|
| `public` | Everywhere | **P**lanet-wide access |
| `protected` | Same package + subclasses | **P**ackage + **P**rogeny |
| `private` | Same class only | **P**ersonal space |
| `default` | Same package only | **D**omain restricted |

### Quick Code Example
```java
public class AccessDemo {
    public void globalMethod() { }        // Accessible everywhere
    protected void familyMethod() { }     // Package + children  
    private void personalMethod() { }     // This class only
    void packageMethod() { }              // Package only
}
```

## 🔄 Method Types Cheat Sheet

### 1. **Overloading** (Compile-time)
```java
// Same name, different parameters
public void print() { }
public void print(String msg) { }
public void print(int num, String msg) { }
```
**Rules**: ✅ Different params | ❌ Return type alone

### 2. **Overriding** (Runtime)
```java
class Parent {
    public void speak() { System.out.println("Parent speaks"); }
}
class Child extends Parent {
    @Override
    public void speak() { System.out.println("Child speaks"); }
}
```
**Rules**: ✅ Same signature | ✅ Inheritance required

### 3. **Static Methods**
```java
public class MathUtils {
    public static int add(int a, int b) { return a + b; }
}
// Usage: MathUtils.add(5, 3);
```
**Rules**: ❌ No instance variables | ❌ Cannot override | ✅ Class-level

### 4. **Final Methods**
```java
public final void criticalMethod() {
    // Cannot be overridden
}
```

### 5. **Abstract Methods**
```java
public abstract class Shape {
    public abstract double calculateArea(); // No implementation
}
```

### 6. **Variable Arguments (Varargs)**
```java
public int sum(int... numbers) {
    int total = 0;
    for (int num : numbers) total += num;
    return total;
}
// Usage: sum(1, 2, 3, 4, 5);
```

## ⚡ Quick Rules Reference

### Static Method Rules
- ✅ Call with class name
- ❌ Cannot access instance variables
- ❌ Cannot call instance methods directly
- ❌ Cannot be overridden
- ✅ Can access static variables only

### Overriding Rules  
- ✅ Same method signature
- ✅ Parent-child relationship
- ❌ Cannot override: private, static, final
- ✅ Can have same/wider access modifier
- ✅ Use @Override annotation

### Varargs Rules
- ✅ Only one per method: `method(int... nums)`
- ✅ Must be last parameter: `method(String prefix, int... nums)`
- ❌ Cannot have: `method(int... nums, String suffix)`

## 🎯 Common Interview Quick Answers

**Q: Static vs Instance method?**
- Static: Class-level, no object needed
- Instance: Object-level, requires object

**Q: Overloading vs Overriding?**
- Overloading: Same name, different params (compile-time)
- Overriding: Same signature, inheritance (runtime)

**Q: Can static method be overridden?**
- No, static methods can only be hidden, not overridden

**Q: Method signature components?**
- Method name + parameter types + parameter order
- (Return type NOT included)

## 🧠 Memory Hooks

### **"SOFA-VS"** - Method Types
- **S**ystem-defined, **O**verloaded, **F**inal, **A**bstract, **V**arargs, **S**tatic

### **"Public Protects Private Defaults"** - Access Specifiers
- **P**ublic = Planet-wide, **P**rotected = Package+Progeny, **P**rivate = Personal, **D**efault = Domain

### **"Static Stands Alone"** - Static Rules
- Cannot access instance variables/methods
- Cannot be overridden
- Belongs to class, not objects

## ⚠️ Common Pitfalls

```java
// ❌ WRONG: Trying to access instance from static
public class Pitfall {
    private int instanceVar = 10;
    
    public static void badMethod() {
        // System.out.println(instanceVar); // ERROR!
    }
}

// ❌ WRONG: Overloading based on return type only
public int calculate(int a) { return a; }
public double calculate(int a) { return a; } // ERROR!

// ❌ WRONG: Multiple varargs
public void method(int... nums1, String... strings) { } // ERROR!

// ❌ WRONG: Varargs not last
public void method(int... nums, String suffix) { } // ERROR!
```

## 🔧 Best Practices Checklist

- [ ] Use meaningful method names (verbs)
- [ ] Follow camelCase naming
- [ ] Keep methods small (single responsibility)
- [ ] Use appropriate access modifiers
- [ ] Add @Override when overriding
- [ ] Validate method parameters
- [ ] Use static for utility methods only
- [ ] Don't modify input parameters

## 💡 Quick Examples

### Method Naming Convention
```java
// ✅ GOOD
public void calculateTotalPrice() { }
public boolean isValidUser() { }
public String getUserName() { }

// ❌ BAD  
public void Calculate() { }      // Capital start
public void calc_price() { }     // Underscore
public void getData() { }        // Too generic
```

### Parameter Validation
```java
public double calculateArea(double length, double width) {
    if (length <= 0 || width <= 0) {
        throw new IllegalArgumentException("Dimensions must be positive");
    }
    return length * width;
}
```

### Proper Static Usage
```java
// ✅ GOOD: Utility method
public static boolean isValidEmail(String email) {
    return email != null && email.contains("@");
}

// ❌ BAD: Should be instance method
public static void updateBalance(Account account, double amount) {
    // This modifies object state - should be instance method
}
```

## 🎯 Quick Test Yourself

1. Can you override a private method? **No**
2. Can static methods access instance variables? **No**
3. What makes methods overloaded? **Same name, different parameters**
4. Where must varargs be placed? **Last parameter**
5. What annotation is used for overriding? **@Override**
6. Can final methods be overridden? **No**
7. What access does default give? **Same package only**
8. Do constructors have return types? **No**

## 🚀 Quick Implementation Templates

### Overloaded Methods Template
```java
public class Calculator {
    public int add(int a, int b) { return a + b; }
    public double add(double a, double b) { return a + b; }
    public int add(int a, int b, int c) { return a + b + c; }
}
```

### Abstract Class Template
```java
public abstract class Animal {
    public abstract void makeSound();
    public void eat() { System.out.println("Eating..."); }
}
```

### Varargs Template
```java
public void processData(String operation, int... values) {
    System.out.println("Operation: " + operation);
    for (int value : values) {
        System.out.print(value + " ");
    }
}
```

---

## 📝 Final Quick Reminders

- **Methods = Reusable code blocks**
- **Access specifiers control visibility**
- **Static = Class level, Instance = Object level**
- **Overloading = Compile-time, Overriding = Runtime**
- **Final = Cannot change, Abstract = Must implement**
- **Varargs = Variable number of arguments**

**Remember**: Practice coding examples to solidify concepts! 🚀