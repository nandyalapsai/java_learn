# Java Memory Management - Quick Reference Cheat Sheet

## 🧠 Memory Areas Overview
```
JVM MEMORY LAYOUT
├── STACK (Thread-specific)
│   ├── Method frames
│   ├── Local variables
│   ├── Primitive values
│   └── Object references
│
└── HEAP (Shared across threads)
    ├── YOUNG GENERATION
    │   ├── Eden Space (new objects)
    │   ├── Survivor S0
    │   └── Survivor S1
    ├── OLD GENERATION (tenured space)
    └── METASPACE (class metadata)
```

## 📊 Stack vs Heap Quick Comparison

| Feature | Stack | Heap |
|---------|-------|------|
| **Size** | Small | Large |
| **Speed** | Fast (LIFO) | Slower |
| **Thread** | Per-thread | Shared |
| **Stores** | References, primitives | Objects |
| **Cleanup** | Automatic (scope) | Garbage Collector |
| **Error** | StackOverflowError | OutOfMemoryError |

## 🔄 Object Lifecycle (Memory Flow)

```
NEW OBJECT → Eden Space → (GC) → Survivor S0 → (GC) → Survivor S1 
                                     ↓ Age++      ↓ Age++
                                     ↓            ↓
                               (Age < threshold) → OLD GENERATION
```

## 🗑️ Garbage Collection Types

| GC Type | Threads | Pause Time | Use Case |
|---------|---------|------------|----------|
| **Serial** | 1 | High | Small apps |
| **Parallel** | Multiple | Medium | Default Java 8 |
| **CMS** | Concurrent | Low | Low latency apps |
| **G1** | Concurrent | Predictable | Large heap apps |

## 🔗 Reference Types Hierarchy

```
Reference Strength (Strong → Weak)
    │
    ├── STRONG REF ──────────── Object obj = new Object();
    │   (Never GC'd while ref exists)
    │
    ├── SOFT REF ────────────── SoftReference<Object> soft = new SoftReference<>(obj);
    │   (GC'd when memory low)
    │
    ├── WEAK REF ────────────── WeakReference<Object> weak = new WeakReference<>(obj);
    │   (GC'd at next cycle)
    │
    └── PHANTOM REF ─────────── PhantomReference<Object> phantom = new PhantomReference<>(obj, queue);
        (For cleanup actions)
```

## ⚡ Common Memory Operations

### Object Creation & Cleanup
```java
// Stack allocation
int x = 10;                    // Primitive in stack
String s = "literal";          // Reference in stack, value in string pool

// Heap allocation
Object obj = new Object();     // Reference in stack, object in heap

// Reference management
obj = null;                    // Eligible for GC
System.gc();                   // Suggest GC (not guaranteed)
```

### Memory Monitoring
```java
Runtime runtime = Runtime.getRuntime();
long total = runtime.totalMemory();     // Total heap
long free = runtime.freeMemory();       // Free heap
long used = total - free;               // Used heap
```

## 🎯 Memory Optimization Quick Tips

### ✅ DO's
- Use `StringBuilder` for string concatenation
- Set initial capacity for collections
- Close resources with try-with-resources
- Use primitives instead of wrapper classes
- Clear collections when done
- Use object pooling for expensive objects

### ❌ DON'Ts
- Don't ignore OutOfMemoryError
- Don't create unnecessary objects in loops
- Don't use `new String("literal")`
- Don't keep strong references to large objects
- Don't rely on `System.gc()` for cleanup

## 🚨 Common Memory Issues

### Stack Overflow
```java
// Cause: Infinite recursion
public void recursive() {
    recursive(); // StackOverflowError
}
```

### Memory Leak
```java
// Cause: Static collection holding references
private static List<Object> cache = new ArrayList<>(); // Never cleared!

// Solution: Use weak references or clear cache
private static Map<String, WeakReference<Object>> cache = new HashMap<>();
```

### OutOfMemoryError
```java
// Heap space exhausted
while(true) {
    list.add(new LargeObject()); // Eventually throws OutOfMemoryError
}
```

## 🔧 JVM Tuning Quick Commands

```bash
# Heap size
-Xms2g -Xmx4g                 # Initial/Max heap size

# GC selection
-XX:+UseG1GC                  # G1 garbage collector
-XX:+UseParallelGC            # Parallel garbage collector

# GC logging
-XX:+PrintGC                  # Basic GC info
-XX:+PrintGCDetails           # Detailed GC info

# Memory dump on error
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=/path/to/dump

# Metaspace
-XX:MetaspaceSize=256m        # Initial metaspace
-XX:MaxMetaspaceSize=512m     # Max metaspace
```

## 🎓 Interview Question Flash Cards

**Q: Difference between stack and heap?**
A: Stack stores method calls and local variables (thread-specific, LIFO). Heap stores objects (shared, GC managed).

**Q: When does garbage collection happen?**
A: When heap memory is low, periodically by JVM, or when explicitly suggested (not guaranteed).

**Q: What causes OutOfMemoryError?**
A: Heap space exhausted, metaspace full, or unable to create new native threads.

**Q: Can you force garbage collection?**
A: `System.gc()` suggests GC, but JVM decides when to actually run it.

**Q: What's the difference between Minor and Major GC?**
A: Minor GC cleans Young generation (fast, frequent). Major GC cleans Old generation (slow, infrequent).

**Q: How do you prevent memory leaks?**
A: Close resources, remove listeners, clear collections, use weak references for caches.

## 📱 Memory Mnemonics

**STEP Rule for Stack:**
- **S**cope-limited
- **T**emporary variables  
- **E**ach thread owns
- **P**rimitives & references

**HEAP Rule for Heap:**
- **H**uge space
- **E**veryone shares
- **A**ctual objects
- **P**ermanent until GC

**SWSP for References:**
- **S**trong (stays)
- **W**eak (weak grip)
- **S**oft (soft hold)
- **P**hantom (post-mortem)

## 🛠️ Debugging Memory Issues

### Tools
- **JVisualVM** - Visual profiling
- **Eclipse MAT** - Memory analysis
- **JConsole** - Real-time monitoring
- **jstat** - GC statistics

### Quick Diagnostics
```java
// Memory usage
MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();

// GC information
List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
```

---

## 🎯 Key Takeaways

1. **JVM manages memory automatically** - No manual malloc/free
2. **Stack is fast and thread-local** - For method calls and variables
3. **Heap stores objects** - Shared among threads, cleaned by GC
4. **Young objects start in Eden** - Move to survivors, then old generation
5. **Strong references prevent GC** - Use weak references for caches
6. **Choose appropriate GC** - G1 for large heaps, Parallel for throughput
7. **Monitor memory usage** - Prevent leaks and optimize performance
8. **Tune JVM parameters** - Based on application requirements

**Remember**: Good memory management = Better performance + Fewer crashes!