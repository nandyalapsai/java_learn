# 🚀 Java Classes Part 5: Quick Reference Cheat Sheet
*Singleton, Immutable & Wrapper Classes - Interview Ready*

---

## ⚡ Singleton Patterns - Quick Comparison

| Pattern | Code Template | When to Use | Thread Safe |
|---------|---------------|-------------|-------------|
| **Eager** | `private static final X instance = new X();` | Small objects, always needed | ✅ |
| **Lazy** | `if(instance==null) instance=new X();` | Large objects, might not be needed | ❌ |
| **Synchronized** | `public static synchronized X getInstance()` | Thread safety required, low performance OK | ✅ |
| **Double-Checked** | `if(null) { sync { if(null) create } }` | High performance + thread safety | ✅ |
| **Bill Pugh** | `private static class Helper` | **BEST CHOICE** - lazy + fast + safe | ✅ |
| **Enum** | `enum X { INSTANCE; }` | Simple cases, reflection protection | ✅ |

---

## 🔥 Most Asked Interview Questions

### 🎯 Singleton Questions (30-second answers)

**Q: Why Singleton?**
**A:** Control object creation for expensive resources (DB connections, caches). Ensures single instance, saves memory, provides global access point.

**Q: Problems with lazy initialization?**
**A:** Race condition - multiple threads can create multiple objects simultaneously when both check null at same time.

**Q: Why volatile in double-checked locking?**
**A:** Prevents CPU caching issues and instruction reordering. Without volatile, object creation by one thread might not be visible to other threads.

**Q: Best singleton pattern?**
**A:** Bill Pugh (initialization-on-demand) - provides lazy loading, thread safety, high performance without synchronization overhead.

**Q: Enum vs Class singleton?**
**A:** Enum: Simpler, reflection-proof, serialization-safe. Class: More flexible, can implement interfaces, easier to mock in tests.

### 🎯 Immutable Class Questions

**Q: Rules for immutable class?**
**A:** 1) Final class 2) Private final fields 3) No setters 4) Constructor-only initialization 5) Defensive copying for mutable objects.

**Q: Why defensive copying?**
**A:** Final collections prevent reassignment but allow content modification. Return copies to prevent external changes to internal state.

**Q: Can immutable class have mutable objects?**
**A:** Yes, but handle with defensive copying in constructor and getters. Clone mutable objects to prevent external modification.

---

## 💻 Code Templates (Copy-Paste Ready)

### 🏆 Best Singleton Pattern
```java
public class DatabaseConnection {
    private DatabaseConnection() {}
    
    private static class ConnectionHelper {
        private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    }
    
    public static DatabaseConnection getInstance() {
        return ConnectionHelper.INSTANCE;
    }
}
```

### 🏆 Perfect Immutable Class
```java
public final class Person {
    private final String name;
    private final List<String> hobbies;
    
    public Person(String name, List<String> hobbies) {
        this.name = name;
        this.hobbies = new ArrayList<>(hobbies);  // Defensive copy
    }
    
    public String getName() { return name; }
    
    public List<String> getHobbies() {
        return new ArrayList<>(hobbies);  // Return copy
    }
}
```

### 🏆 Double-Checked Locking (with volatile)
```java
public class Singleton {
    private static volatile Singleton instance;
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

---

## 🧠 Memory Tricks

### Singleton Patterns Order
**"Every Lazy Student Definitely Builds Excellence"**
- **E**ager, **L**azy, **S**ynchronized, **D**ouble-checked, **B**ill Pugh, **E**num

### Immutable Rules
**"PFNDC"** - **P**rivate fields, **F**inal class/fields, **N**o setters, **D**efensive copying, **C**onstructor only

### Volatile Remember
**"Volatile Variables Visit Main Memory"** - No CPU cache, direct memory access

---

## ⚠️ Common Mistakes (Don't Do This!)

### ❌ Singleton Mistakes
```java
// Wrong: Missing volatile in double-checked locking
private static Singleton instance;  // Should be volatile

// Wrong: Not thread-safe lazy initialization
if (instance == null) instance = new Singleton();  // Race condition

// Wrong: Synchronizing entire method unnecessarily
public static synchronized Singleton getInstance()  // Performance hit
```

### ❌ Immutable Mistakes
```java
// Wrong: Returning mutable reference
public List<String> getList() { return list; }  // Breaks immutability

// Wrong: Allowing inheritance
public class ImmutableClass {}  // Should be final

// Wrong: Having setter methods
public void setName(String name) {}  // No setters allowed
```

---

## 🎪 Quick Practice Challenges

### Challenge 1: Spot the Bug
```java
public class Logger {
    private static Logger instance;
    public static Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }
}
```
**Bug:** Not thread-safe! Fix with synchronization or better pattern.

### Challenge 2: Make It Immutable
```java
public class Student {
    private String name;
    private List<String> subjects;
    // Add constructor and methods to make this immutable
}
```

### Challenge 3: Best Singleton Choice
**Scenario:** Logger class used throughout application, might not be needed immediately, performance critical.
**Answer:** Bill Pugh pattern (lazy + fast + thread-safe)

---

## 📊 Performance Ranking

### Singleton Performance (Best to Worst)
1. **Bill Pugh** - Lazy, thread-safe, no synchronization overhead
2. **Enum** - Simple, JVM optimized
3. **Eager** - Fast access, but immediate memory allocation
4. **Double-Checked** - Good balance, but volatile overhead
5. **Lazy** - Fast but not thread-safe
6. **Synchronized** - Thread-safe but slow due to method-level locking

---

## 🏗️ Real-World Examples

### When to Use Singleton
- **Database Connection Pool** - Expensive resource, shared across app
- **Configuration Manager** - Single source of settings
- **Logger** - Centralized logging service
- **Cache Manager** - Shared cache instance

### When to Use Immutable
- **Value Objects** - Money, coordinates, configuration
- **Thread-Safe Objects** - Multiple threads can safely access
- **API Response Objects** - Data that shouldn't change after creation

---

## 🔧 Debugging Checklist

### Singleton Issues
- [ ] Constructor is private?
- [ ] Using proper synchronization?
- [ ] Volatile keyword where needed?
- [ ] No memory leaks from singleton holding references?

### Immutable Issues  
- [ ] Class declared final?
- [ ] All fields private and final?
- [ ] No setter methods?
- [ ] Defensive copying for mutable objects?
- [ ] Constructor validates inputs?

---

## 🎯 Final Interview Tips

### What Interviewers Look For
1. **Understanding of thread safety** - Critical for senior roles
2. **Knowledge of trade-offs** - When to use which pattern
3. **Real-world application** - Can you explain use cases?
4. **Code quality** - Clean, readable implementations
5. **Problem-solving** - Can you identify and fix issues?

### Top 3 Things to Remember
1. **Bill Pugh is usually the best singleton pattern**
2. **Always use defensive copying for mutable objects in immutable classes**
3. **Volatile prevents memory visibility issues in concurrent programming**

---

*🔥 Pro Tip: Practice writing these patterns from memory. In interviews, you might be asked to implement them on a whiteboard!*

---

## 📚 Quick Links to Full Notes
- **Detailed Implementation Examples** → Main notes file
- **Memory Model Diagrams** → Volatile section
- **Complete Code Samples** → Each pattern section
- **Advanced Scenarios** → Real-world use cases section