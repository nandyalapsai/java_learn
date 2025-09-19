# Java Memory Management & Garbage Collection (Interview-Focused Notes)

> Quick revision + deep understanding + interview prep.

---
## 1. Key Concepts & Definitions

| Term | Definition |
|------|------------|
| JVM | Java Virtual Machine – executes bytecode, manages memory (stack, heap, metaspace), runs GC. |
| Stack Memory | Per-thread memory storing method frames, local variables, primitive values, and object references. LIFO lifecycle. |
| Heap Memory | Shared runtime memory storing all objects & arrays; managed by GC. Divided into Young & Old (Tenured) generations. |
| Young Generation | Newly created objects; subdivided into Eden + Survivor Spaces (S0, S1). Frequent (Minor) GC. |
| Survivor Spaces (S0/S1) | Two buffers used to copy live objects back and forth during Minor GC while aging them. |
| Old (Tenured) Generation | Holds long‑lived objects promoted after surviving enough Minor GCs. Collected by Major/Full GC. |
| Metaspace (Non-Heap) | Stores class metadata, method info, static vars, constant pool. Replaced PermGen (since Java 8). Native memory & auto-expands. |
| GC (Garbage Collection) | Automatic reclamation of memory from unreachable objects. |
| Minor GC | Collection in Young Gen (Eden + one Survivor). Fast and frequent. |
| Major / Full GC | Collection involving Old Gen (and possibly entire heap). Slower, can cause longer pauses. |
| Strong Reference | Normal reference; object not eligible for GC while reachable. |
| Weak Reference | Collected eagerly once only weak references remain. Useful for caches (java.lang.ref.WeakReference). |
| Soft Reference | Collected only under memory pressure. Better for memory-sensitive caches. (java.lang.ref.SoftReference). |
| Phantom Reference | Used for post-mortem cleanup; enqueued after finalization & before memory reclaim (java.lang.ref.PhantomReference). |
| Reachability | Eligibility criteria for GC (strong > soft > weak > phantom > unreachable). |
| Mark-Sweep(-Compact) | Core GC phases: mark live objects → sweep dead → (optional) compact to defragment. |
| Stop-The-World (STW) | Pause where all application (mutator) threads halt so GC can perform certain phases safely. |
| Promotion | Moving an object from Young Gen to Old Gen after surviving threshold (age). |
| Aging | Incrementing object age when it survives a Minor GC copy between Survivor spaces. |
| Fragmentation | Non-contiguous free memory making allocation harder; fixed by compaction. |

---
## 2. Memory Layout Overview

```
Thread 1 Stack   Thread 2 Stack   ...  Thread N Stack
     |                 |                      |
   Frames            Frames                Frames
      \               |                     /
                       Shared Heap
                +--------------------+
                | Young Gen          |  -> Eden | S0 | S1
                | Old (Tenured) Gen  |
                +--------------------+

        Non-Heap: Metaspace (class metadata, static, constants)
```

---
## 3. Stack vs Heap (Comparison)

| Aspect | Stack | Heap |
|--------|-------|------|
| Ownership | Per thread | Shared across all threads |
| Stores | Frames, local vars, primitives, refs | Objects, arrays, String pool (logical), interned strings |
| Lifetime | Scope-bound (method/block) | Until unreachable + GC |
| Access Speed | Very fast | Slower (indirection) |
| Errors | StackOverflowError (deep recursion) | OutOfMemoryError (heap exhausted) |
| Management | Automatic push/pop | Managed by GC |

---
## 4. Young Generation Lifecycle (Minor GC)

1. New objects allocated in Eden.
2. Minor GC runs: mark live → copy survivors Eden+from-Survivor → to other Survivor (S0 ⇄ S1).
3. Age field increments per successful copy.
4. Objects exceeding threshold (e.g., age ≥ 15 default, JVM-tunable) promoted to Old Gen early if Survivor space fills.
5. After GC: Eden cleared; one Survivor full of live objects; the other empty.

ASCII Flow:
```
Allocation: Eden fills → Minor GC → Copy live to S0 → Next GC → Copy to S1 (age++) → Promote when aged
```

---
## 5. Old Generation & Major GC
- Contains long-lived & large objects.
- Major (a.k.a. Full) GCs less frequent, more expensive.
- Uses marking + sweeping + (often) compaction to mitigate fragmentation.
- Excessive promotion → Premature Old Gen pressure → Full GC spikes.

---
## 6. Metaspace (vs Old PermGen)
| Aspect | PermGen (≤ Java 7) | Metaspace (Java 8+) |
|--------|--------------------|---------------------|
| Location | Heap | Native memory |
| Limit | Fixed max (needed -XX:MaxPermSize) | Grows until system memory limit (configurable via -XX:MaxMetaspaceSize) |
| OOME Cause | Classloader leak, many dynamic classes | Same possibility but more flexible growth |

---
## 7. Reference Types & GC Behavior

| Reference | Collected When | Typical Use |
|-----------|----------------|-------------|
| Strong | Never (while strongly reachable) | Regular logic |
| Soft | Under memory pressure | Memory-sensitive caches |
| Weak | Next GC cycle when only weak refs remain | Canonicalization maps, listeners |
| Phantom | After finalization, before reclaim | Resource tracking / cleanup hooks |

Code Snippet:
```java
Person strong = new Person();              // Strong
SoftReference<Person> soft = new SoftReference<>(new Person());
WeakReference<Person> weak = new WeakReference<>(new Person());
PhantomReference<Person> phantom = new PhantomReference<>(
        new Person(), new ReferenceQueue<>());
```

---
## 8. Example: Stack & Heap Interaction
```java
public class MemoryDemo {
    static class Person { String name; }

    public static void main(String[] args) {
        int count = 10;                  // Primitive on stack
        Person p = new Person();         // Ref on stack, object in heap
        p.name = "Alice";               // Field lives in heap inside Person

        MemoryDemo demo = new MemoryDemo();
        demo.process(p);
    }

    void process(Person input) {          // New frame, 'input' ref copied
        Person alias = input;             // Another ref to same object
        String s1 = "Hi";                // Interned literal (String pool)
        String s2 = new String("Hi");    // New heap object + possible pool ref
    }                                     // Frame pops: local refs gone
}
```
After `process` returns, object referenced by `p` still reachable → survives.

---
## 9. Changing Reachability
```java
Person a = new Person();  // Strong ref a → Obj1
Person b = new Person();  // Strong ref b → Obj2
a = b;                    // Obj1 becomes unreachable → eligible for GC
b = null;                 // Obj2 now unreachable as well
System.gc();              // Hint only; not guaranteed immediate collection
```

---
## 10. Mark-Sweep(-Compact) Visual
```
Before: [Live][Dead][Live][Dead][Live]
Mark:   *     X    *     X    *
Sweep:  [Live]     [Live]     [Live]   (holes remain)
Compact:[Live][Live][Live][Free....]
```

---
## 11. GC Algorithms / Collectors
| Collector | Model | Notes | Use Case |
|-----------|-------|-------|----------|
| Serial GC | Single-thread, STW | Simpler, small heaps | Embedded, ≤ ~100MB heaps |
| Parallel GC | Multi-threaded Young (and Old) | Higher throughput, longer pauses | Default in many JDK 8 distros |
| CMS (Concurrent Mark-Sweep) | Concurrent mark/sweep, no compact | Reduced pauses, fragmentation risk | Deprecated; replaced by G1 |
| G1 (Garbage First) | Region-based, incremental, compaction | Predictable pauses, targets pause goal | Default since JDK 9 |
| ZGC / Shenandoah | Concurrent, low-latency | Very low pause times (ms range) | Large heaps, latency-critical |

Simplified Phase (G1 example):
1. Initial Mark (STW, quick)
2. Concurrent Mark
3. Remark (short STW)
4. Copy/Compact selected regions

---
## 12. Interview-Focused Q&A
1. What lives on the stack vs heap?  
   Stack: frames, locals, primitives, refs. Heap: objects/arrays, interned strings, object graphs.
2. When does an object become eligible for GC?  
   When no reachable chain of strong refs exists from GC roots (threads, static fields, JNI handles, etc.).
3. Difference: Weak vs Soft Reference?  
   Weak collected aggressively; Soft retained until memory pressure.
4. Why is GC needed?  
   Prevents manual memory management bugs (leaks, double free), improves safety.
5. What causes a memory leak in Java?  
   Unwanted strong references retained (e.g., static collections, caches without eviction, listeners not removed).
6. Minor vs Major GC?  
   Minor: Young gen only, fast. Major/Full: Includes Old (and possibly Young), slower pause.
7. PermGen vs Metaspace?  
   PermGen fixed & heap-resident; Metaspace native & expandable.
8. What is Stop-The-World?  
   Pause of all application threads for certain GC phases.
9. Explain promotion.  
   After surviving sufficient Minor GCs or Survivor overflow, object moved to Old Gen.
10. How to reduce GC pauses?  
    Tune heap, choose appropriate collector (G1/ZGC), reduce allocation rate, pool objects, optimize data structures.
11. Can `System.gc()` force a GC?  
    No – only a suggestion to JVM.
12. What are GC roots?  
    Active thread stacks, static fields, JNI refs, system class loader, etc.

---
## 13. Real-World Use Cases
| Scenario | Memory Concern | Mitigation |
|----------|----------------|------------|
| High-throughput REST API | Allocation churn → frequent Minor GCs | Reuse buffers, object pooling cautiously |
| Caching Layer | Risk of OOME | Use `SoftReference`/bounded caches (Caffeine) |
| Large Batch Processing | Promotion pressure | Increase Young size / tune Survivor ratios |
| Dynamic Class Loading (e.g., OSGi) | Metaspace growth | Ensure proper classloader unloading |
| Event Listeners | Leaks via strong refs | Use weak listeners / explicit deregistration |

---
## 14. Best Practices
- Favor immutable & small objects for safety; but avoid excessive temporary object churn in tight loops.
- Prefer modern collectors (G1/ZGC) for large heaps or latency-sensitive systems.
- Monitor with tools: `jvisualvm`, `jcmd`, `jmap`, `jstat`, GC logs.
- Enable GC logging (JDK 11+): `-Xlog:gc*:file=gc.log:tags,uptime,time,level`.
- Right-size the heap: too small → frequent GC; too large → long Full GC pauses.
- Tune Survivor spaces only after profiling (avoid premature tuning).
- Use `String.intern()` cautiously (memory overhead).
- Clear references in long-lived collections when no longer needed.
- Avoid finalizers (deprecated); use `Cleaner` / try-with-resources.
- For caches: prefer established libraries (Caffeine) over DIY with weak refs alone.

---
## 15. Common Pitfalls
| Pitfall | Impact | Fix |
|---------|--------|-----|
| Holding strong refs in static maps | Prevents GC | Use bounded caches / weak keys/values |
| Excessive string concatenation in loops | Allocation churn | Use `StringBuilder` |
| Large object graphs retained unintentionally | High Old Gen usage | Break references, null out fields if needed |
| Overusing `System.gc()` | Unpredictable pauses | Remove; rely on JVM | 
| Ignoring memory profiling | Late-stage failures | Integrate monitoring early |
| Misusing weak refs for core logic | Null surprises | Only for auxiliary references |

---
## 16. Simple Diagrams
### Object Aging & Promotion
```
Eden --> S0 (age=1) --> S1 (age=2) --> S0 (age=3) --> Promotion → Old Gen
```
### Reference Strength
```
Strong > Soft > Weak > Phantom > (Collected)
```

---
## 17. Collector Selection Cheat Sheet
| Need | Choose |
|------|--------|
| Small footprint, simple | Serial / Parallel |
| Throughput priority | Parallel / G1 |
| Mixed workload, predictable pause | G1 |
| Large heap (multi-GB), very low pause | ZGC / Shenandoah |

---
## 18. Tuning Entry Points (Only After Profiling!)
| Option | Purpose |
|--------|---------|
| -Xms / -Xmx | Initial / max heap size |
| -XX:MaxGCPauseMillis | G1 pause target |
| -XX:NewRatio / -Xmn | Young vs Old sizing (Parallel/Serial) |
| -XX:SurvivorRatio | Eden:Survivor sizing |
| -XX:MaxTenuringThreshold | Promotion age threshold |
| -XX:MetaspaceSize / -XX:MaxMetaspaceSize | Metaspace tuning |

---
## 19. Quick Revision Flash Cards
- Stack = frames; Heap = objects.
- Minor GC copies; Major GC compacts (often).
- Promotion = survived threshold.
- Metaspace ≠ Heap.
- SoftReference survives low pressure; WeakReference doesn’t.
- G1 = region-based, predictable pauses.

---
## 20. Practice Exercise
Predict which objects are eligible for GC after execution:
```java
List<Object> list = new ArrayList<>();
Object a = new Object();      // (1)
Object b = new Object();      // (2)
list.add(a);
a = null;                     // (1) still reachable via list
b = new Object();             // (2) eligible (prior instance) if no other refs
System.gc();
```
Answer: Original (2) eligible; (1) not (list holds it).

---
## 21. Key Takeaways
- Understand lifecycles: allocation → survival → promotion → reclamation.
- GC tuning is workload-specific; always measure before & after.
- Memory leaks in Java = lingering strong references, not freed space.
- Right abstractions + profiling tools > premature micro-tuning.

---
## 22. Further Reading / Tools
- GC Logs: Analyze with GC Easy / GCeasy.io, JITWatch
- Tools: `jcmd`, `jmap -histo`, `jps`, `jstat`, VisualVM, Java Flight Recorder, Mission Control
- Official docs: OpenJDK GC Tuning Guide

---
> Mastering these fundamentals gives you leverage in performance debugging, scalability planning, and confident interview explanations.
