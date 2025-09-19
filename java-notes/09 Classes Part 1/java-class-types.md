# Java Class Types - Part 1 (Concrete, Abstract, Super/Subclass, Object, Nested & Anonymous)

> Quick revision + interview-ready notes distilled from the session.

---
## 1. Overview
This part covers foundational Java class categories before Generics, POJOs, Enums, Final, Singleton, Immutable & Wrapper (those will be in Part 2).

Covered in Part 1:
- Concrete Class
- Abstract Class & Abstraction levels
- Superclass & Subclass (Inheritance basics) + `Object` class
- Nested Classes
  - Static Nested Class
  - Inner (Non-static) Class (aka Member Inner Class)
  - Local Inner Class
  - Anonymous Inner Class
- Inheritance nuances with nested classes

---
## 2. Key Concepts & Definitions
| Concept | Definition | Key Property |
|---------|------------|--------------|
| Concrete Class | A class you can instantiate with `new` | All methods have implementation |
| Abstract Class | Class declared with `abstract`; may contain abstract + concrete methods | Cannot be instantiated |
| Abstract Method | Method without implementation ending in `;` | Must be implemented by first concrete subclass |
| Superclass | Parent class being extended | Provides reusable members |
| Subclass | Child class using `extends` | Inherits + can override |
| `Object` Class | Root of all Java classes | Provides common methods: `toString()`, `equals()`, `hashCode()`, `wait()`, `notify()` |
| Static Nested Class | Static class inside another class | Does NOT need outer instance |
| Inner Class (Member) | Non-static class inside another class | Requires outer instance |
| Local Inner Class | Class declared inside a block (method/loop/if) | Scope confined to that block |
| Anonymous Inner Class | Class without a name defined inline | Used to override behavior quickly |

---
## 3. Important Points (Essentials)
- Only top-level access modifiers: `public` or package-private (no modifier). `private/protected` allowed ONLY for nested classes.
- Abstract class supports 0–100% abstraction (can mix implemented & abstract methods).
- Interfaces (pre-Java 8) = 100% abstraction; abstract class more flexible.
- `Object` is implicitly the superclass if no `extends` is declared.
- You can store a concrete object in any parent reference (polymorphism): `Car c = new Audi();`
- Static nested classes behave like regular top-level classes scoped within outer class.
- Inner classes capture outer instance; can access both instance & static members.
- Local inner classes cannot have access modifiers or `static` declarations (except constant fields).
- Anonymous classes are compiled into synthetic names like `Outer$1.class`.

---
## 4. Code Examples
### 4.1 Concrete Class
```java
class Person {
    private String name;
    public Person(String name) { this.name = name; }
    public void greet() { System.out.println("Hi, I'm " + name); }
}
Person p = new Person("Alice");
p.greet();
```

### 4.2 Abstract Class
```java
abstract class Car {
    abstract void applyBrake();
    void getWheels() { System.out.println(4); }
}
class Audi extends Car {
    @Override void applyBrake() { System.out.println("ABS engaged"); }
}
Car car = new Audi();
car.applyBrake(); // ABS engaged
```

### 4.3 Superclass, Subclass & `Object`
```java
class A {}
class B extends A {}
class Person {}
Object o1 = new Person();
Object o2 = new B();
System.out.println(o1.getClass()); // class Person
System.out.println(o2.getClass()); // class B
```

### 4.4 Static Nested Class
```java
class Outer {
    static int staticVal = 10;
    int instanceVal = 5;

    static class Nested {
        void print() { System.out.println(staticVal); }
        // Cannot access instanceVal directly
    }
}
Outer.Nested n = new Outer.Nested();
n.print();
```

### 4.5 Inner (Member) Class
```java
class Outer {
    int instanceVal = 42;
    static int staticVal = 7;

    class Inner {
        void show() { System.out.println(instanceVal + staticVal); }
    }
}
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
inner.show(); // 49
```

### 4.6 Local Inner Class
```java
void process() {
    int factor = 2; // effectively final
    class Multiplier { int apply(int x) { return x * factor; } }
    System.out.println(new Multiplier().apply(5)); // 10
}
```

### 4.7 Anonymous Inner Class
```java
abstract class Car { abstract void brake(); }
Car c = new Car() { // anonymous subclass
    @Override void brake() { System.out.println("Hydraulic brake"); }
};
c.brake();
```

### 4.8 Inheritance with Nested Classes
```java
class Outer {
    class Inner1 { void m1() { System.out.println("m1"); } }
    class Inner2 extends Inner1 { void m2() { System.out.println("m2"); } }
}
Outer o = new Outer();
Outer.Inner2 obj = o.new Inner2();
obj.m1(); obj.m2();
```

---
## 5. Diagrams (ASCII)
### 5.1 Class Hierarchy with `Object`
```
            Object
          /   |    \
       Car  Person  A
        |             \
      Audi             B
```

### 5.2 Nested vs Inner Access
```
Outer
 ├─ static int staticVal
 ├─ int instanceVal
 ├─ static class Nested (access: static only)
 └─ class Inner (access: static + instance)
```

### 5.3 Anonymous Class Creation Flow
```
Abstract Type --> new Type() { override methods } --> Compiler generates Outer$1.class
```

---
## 6. Interview Questions (with Brief Answers)
1. What is a concrete class?  - A class that can be instantiated; all methods implemented.
2. Difference: abstract class vs interface?  - Abstract class: state + partial impl.; interface (pre-Java 8) only contracts; supports multiple inheritance of type via interfaces.
3. Can abstract class have constructor?  - Yes, for initialization used by subclasses.
4. Why can't we instantiate abstract class?  - Because it may have unimplemented (abstract) methods.
5. Is every class a subclass of `Object`?  - Yes, directly or transitively.
6. What methods come from `Object`?  - `toString()`, `equals()`, `hashCode()`, `clone()`, `wait()/notify()/notifyAll()`, `getClass()`, `finalize()` (deprecated).
7. Static nested vs inner class?  - Static nested: no outer instance needed; inner: binds to outer instance.
8. When use anonymous inner class?  - One-off implementation / behavior override, especially before lambdas.
9. Why local inner classes can't be public?  - Scope restricted to the block; visibility modifiers meaningless.
10. Can nested class be private?  - Yes (only nested ones can be private/protected).
11. Access outer class private members from inner?  - Yes; compiler adds synthetic bridge.
12. Anonymous class vs lambda?  - Lambda targets functional interface; anonymous class creates a new subtype (can add state, override multiple methods if implementing abstract class with multiple abstract methods—though abstract class normally). Lambdas don't define a new type.
13. Memory impact of inner class?  - Holds implicit reference to outer instance; potential for memory leaks if stored long-term (e.g., in static collections).
14. Can inner class have static members?  - Only static final constants.
15. Order of initialization with inner class inheritance?  - Outer instance constructed → inner superclass constructor → inner subclass constructor.

---
## 7. Real-World Use Cases
- Abstract class: Base service adapters (e.g., `HttpHandler` with default hooks).
- Anonymous class: Event listeners pre-Java 8 (`new ActionListener() {...}`).
- Static nested class: Builder pattern (`Outer.Builder`), helper utilities tightly coupled to outer.
- Inner class: Iterators inside collection classes (e.g., `ArrayList.Itr`).
- Local inner class: Parsing, on-the-fly strategy encapsulation within a single method.
- Using `Object` reference: Generic containers, reflection utilities.

---
## 8. Best Practices
- Prefer static nested classes unless you need outer instance state.
- Keep inner/anonymous classes short; extract if they grow.
- Use anonymous class only when lambda not applicable or need a named type override beyond single method.
- Avoid leaking inner classes from long-lived outer instances (risk of memory retention).
- Override `toString()`, `equals()`, `hashCode()` meaningfully for domain objects.
- Use abstraction (abstract classes/interfaces) to enforce contracts & promote extensibility.

---
## 9. Common Pitfalls
| Pitfall | Explanation / Fix |
|---------|-------------------|
| Accessing outer instance from static nested class | Not possible; pass reference explicitly |
| Memory leak via inner class in static context | Inner keeps outer reference → use static nested |
| Forgetting semicolon after anonymous class block | Syntax error |
| Overusing anonymous classes | Reduces readability; prefer named class or lambda |
| Not marking methods `@Override` | Risk of accidental overloading |
| Assuming local inner class accessible outside method | Scope limited – redesign if needed |

---
## 10. Comparisons
| Topic | Static Nested | Inner (Member) | Local Inner | Anonymous |
|-------|---------------|----------------|-------------|----------|
| Needs outer instance? | No | Yes | Yes (enclosing block) | Yes (enclosing context) |
| Has name? | Yes | Yes | Yes | No |
| Access outer instance members | Only static | All | All in scope | All |
| Use case | Helper/Builder | Tight coupling to outer state | Temporary logic | One-off override |
| Can have access modifiers? | Yes | Yes | No (except default) | N/A |

---
## 11. Quick Revision Sheet
- Concrete: Instantiable; full implementations.
- Abstract: May contain abstract + concrete; cannot instantiate.
- `Object`: Root; polymorphism storage; reflection via `getClass()`.
- Static Nested: No outer instance; behaves like namespaced class.
- Inner: Captures outer instance; use sparingly.
- Local Inner: Scoped to block; rarely used.
- Anonymous: Inline subclass; replaced by lambdas for functional interfaces.
- Rule of thumb: If it doesn't need outer instance → make it static.

---
## 12. Practice Checklist
- Write abstract base + 2 concrete subclasses.
- Convert inner class to static nested & observe compile errors where outer members were used.
- Implement a Builder using static nested class.
- Replace an anonymous listener with a lambda (where interface is functional).

---
## 13. Additional Notes
- Compilation artifact naming: `Outer$Inner.class`, `Outer$1.class` (anonymous), etc.
- Reflection: `obj.getClass().getName()` returns runtime type even if reference is parent.
- Anonymous class cannot define constructor but can use instance initializer block `{ }` for setup.

---
Happy learning – proceed to Part 2 for Generics, POJO, Enum, Final, Singleton, Immutable & Wrapper Classes.
