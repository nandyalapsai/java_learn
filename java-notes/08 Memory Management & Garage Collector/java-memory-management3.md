# Java Memory Management: Comprehensive Notes

## 1. Introduction to Java Memory Management

Java applications run on a Java Virtual Machine (JVM), which manages system memory for the application. Automatic Memory Management is a key feature of Java, where the JVM automatically handles the allocation and de-allocation of memory, primarily through a process called **Garbage Collection**.

## 2. Types of Memory in JVM

The JVM divides the memory it manages into two main parts: **Stack** and **Heap**.

| Feature | Stack Memory | Heap Memory |
| :--- | :--- | :--- |
| **Purpose** | Stores temporary variables, primitive types, and references to heap objects. Manages method execution. | Stores objects, arrays, and static content (via Metaspace). |
| **Size** | Smaller and fixed in size. | Larger and dynamically resizable. |
| **Allocation** | LIFO (Last-In, First-Out) via stack frames. | Complex and managed by the Garbage Collector. |
| **Lifecycle** | Short-lived. Memory is freed as soon as the method/scope ends. | Exists as long as the object is referenced. |
| **Threads** | Each thread has its own private stack. | All threads share a single heap. |
| **Error** | `StackOverflowError` if full. | `OutOfMemoryError` if full. |

---

## 3. Stack Memory in Detail

The stack is used for static memory allocation and thread execution. When a method is invoked, a new block of memory, called a **stack frame**, is created on the stack for that method.

### Key Characteristics
- **Scope-based:** Variables are only accessible within their defining scope (the method's stack frame).
- **LIFO (Last-In, First-Out):** The last method to be invoked is the first one to finish and have its memory freed.
- **Fast Access:** Memory allocation and de-allocation are very fast.

### What is stored in Stack?
- **Local Primitive Variables:** `int`, `double`, `boolean`, etc., declared inside a method.
- **References to Heap Objects:** The actual object is in the heap, but the variable that refers to it is on the stack.
- **Method Execution Frames:** Information about the method call, its parameters, and local variables.

### Stack Memory Allocation Example

Consider this simple Java class:

```java
public class MemoryExample {
    public static void main(String[] args) {
        int id = 10; // Stored in main's stack frame
        Person person = new Person("Alex"); // Object in Heap, 'person' reference in Stack
        anotherMethod();
    }

    public static void anotherMethod() {
        double salary = 5000.50; // Stored in anotherMethod's stack frame
    }
}

class Person {
    String name;
    public Person(String name) {
        this.name = name;
    }
}
```

**Execution Flow & Diagram:**

1.  `main()` method is called. A stack frame for `main` is pushed onto the stack.
    -   `int id = 10;` is stored directly inside the `main` frame.
    -   `new Person("Alex")` creates a `Person` object on the **Heap**.
    -   The reference variable `person` is created on the **Stack** and points to the `Person` object on the Heap.
2.  `anotherMethod()` is called. A new stack frame for `anotherMethod` is pushed on top of the `main` frame.
    -   `double salary = 5000.50;` is stored inside the `anotherMethod` frame.
3.  `anotherMethod()` finishes. Its stack frame is popped from the stack, and `salary` is destroyed.
4.  `main()` finishes. Its stack frame is popped, and `id` and the `person` reference are destroyed. The `Person` object on the heap is now unreferenced.

```
      Stack Memory                  Heap Memory
+---------------------+
| anotherMethod()     |
| [double salary]     |
+---------------------+
| main()              |
| [int id]            |
| [Person person] ------> [ Person Object ("Alex") ]
+---------------------+
```

---

## 4. Heap Memory and Garbage Collection

The heap is used for dynamic memory allocation for all Java objects.

### String Pool
A special area within the heap called the **String Pool** (or String Constant Pool) stores String literals. When you create a string literal (e.g., `String s = "hello";`), the JVM checks the pool first. If the string already exists, it returns a reference to it; otherwise, it creates a new string in the pool. Using `new String("hello")` forces the creation of a new object outside the pool.

### Garbage Collector (GC)
The Garbage Collector is the heart of Java's automatic memory management. Its job is to identify and delete objects that are no longer referenced by the application, freeing up heap memory.

-   **Unreferenced Object:** An object is eligible for garbage collection if it can no longer be accessed. This happens when:
    -   The reference variable goes out of scope.
    -   The reference is assigned `null`.
    -   The reference is assigned to another object.
-   **`System.gc()`:** This method *suggests* that the JVM run the Garbage Collector. However, the JVM is not guaranteed to run it. It's a request, not a command.

---

## 5. Types of References

| Reference Type | Description | GC Behavior |
| :--- | :--- | :--- |
| **Strong** | The default type. `Person p = new Person();` | The object will **NOT** be garbage collected as long as a strong reference points to it. |
| **Weak** | `WeakReference<Person> weakP = new WeakReference<>(p);` | The object will be garbage collected in the next GC cycle, even if a weak reference points to it. |
| **Soft** | `SoftReference<Person> softP = new SoftReference<>(p);` | The object will be garbage collected only when the JVM is low on memory. Useful for caches. |
| **Phantom** | Rarely used. Used for pre-mortem cleanup actions. | The object is garbage collected, and the reference is put in a `ReferenceQueue`. |

---

## 6. Heap Memory Structure: Generational GC

The heap is divided into generations to improve GC efficiency. The core idea is that most objects die young.

```
+-----------------------------------------------------------------+
|                               Heap                              |
+----------------------------------+------------------------------+
|         Young Generation         |       Old (Tenured) Gen      |
+----------------+--------+--------+------------------------------+
|      Eden      |   S0   |   S1   |                              |
|                | (From) |  (To)  |                              |
+----------------+--------+--------+------------------------------+
```

### Object Lifecycle
1.  **Creation:** All new objects are first allocated in the **Eden** space.
2.  **Minor GC:** When Eden space fills up, a **Minor GC** is triggered.
    -   Referenced objects are moved from Eden to one of the Survivor spaces (e.g., S0).
    -   Unreferenced objects in Eden are deleted.
    -   The age of the surviving objects is incremented.
3.  **Survival:** On subsequent Minor GCs, surviving objects are moved between S0 and S1, and their age is incremented.
4.  **Promotion:** Once an object reaches a certain age threshold, it is **promoted** from the Young Generation to the **Old (Tenured) Generation**.
5.  **Major GC:** When the Old Generation fills up, a **Major GC** (or Full GC) is triggered, which is a slower process that cleans up the Old Generation.

### Metaspace
-   Since Java 8, **PermGen** (Permanent Generation) has been replaced by **Metaspace**.
-   It is **not** part of the heap. It resides in native memory.
-   It stores class metadata, static variables, and constants.
-   It is expandable, reducing the risk of `OutOfMemoryError` for class metadata.

---

## 7. Garbage Collection Algorithms

| Algorithm | Description | Use Case |
| :--- | :--- | :--- |
| **Serial GC** | Uses a single thread for garbage collection. Pauses the application (stop-the-world). | Simple applications with small data sets running on single-processor machines. |
| **Parallel GC** | Uses multiple threads for Young Generation GC. Still pauses the application. It is the default GC in Java 8. | Throughput-oriented applications running on multi-core hardware. |
| **CMS GC** | (Concurrent Mark Sweep) Performs most of its work concurrently with the application, minimizing pause times. | Latency-sensitive applications where responsiveness is critical. |
| **G1 GC** | (Garbage-First) Divides the heap into regions and prioritizes collecting from regions with the most garbage. Aims for predictable pause times. | Large heap sizes (>4GB) and applications requiring low latency and high throughput. Default from Java 9+. |

---

## 8. Interview Questions

1.  **What is the difference between Stack and Heap?**
    -   Stack is for static memory allocation (primitives, references) and has a LIFO structure. Each thread has its own stack. Heap is for dynamic memory allocation (objects) and is shared among all threads.
2.  **What is Garbage Collection? Can you force it?**
    -   It's the process of automatically freeing up heap memory by deleting unreferenced objects. You cannot force it, but you can suggest it with `System.gc()`.
3.  **Explain the difference between Minor and Major GC.**
    -   Minor GC cleans the Young Generation and is fast. Major GC cleans the Old Generation and is much slower, often causing noticeable application pauses.
4.  **What is `StackOverflowError` vs. `OutOfMemoryError`?**
    -   `StackOverflowError` occurs when the stack becomes full, usually due to deep recursion. `OutOfMemoryError` occurs when the heap runs out of space to create new objects.
5.  **What are the different types of GC references?**
    -   Strong, Soft, Weak, and Phantom. Strong is the default, preventing GC. Soft is for memory-sensitive caches. Weak is for canonicalizing mappings, allowing GC to reclaim objects when no strong references exist.

## 9. Best Practices & Common Pitfalls

-   **Avoid `System.gc()`:** Trust the JVM to handle GC. Explicit calls can degrade performance.
-   **Use Correct Data Structures:** Using the right collection can significantly impact memory usage.
-   **Be Mindful of String Concatenation:** In loops, prefer `StringBuilder` over `+` to avoid creating many intermediate String objects.
-   **Close Resources:** Always close streams, connections, and other resources in a `finally` block or use try-with-resources to prevent memory leaks.
-   **Beware of Static Fields:** Objects held by static fields live for the entire application lifecycle and can cause memory leaks if not managed carefully.
