Got it—here’s a clean, structured, interview‑ready set of Java OOP notes distilled from the transcript.

---

# Java OOP Fundamentals (Interview + Revision Notes)

## 1. What Is OOP?
Object-Oriented Programming (OOP) is a paradigm where software is modeled using objects that bundle state (data) and behavior (methods). In Java, everything (except primitives) revolves around classes and objects.

### Key Goals
- Model real-world entities
- Organize code for reuse, modularity, extensibility
- Provide data protection and abstraction
- Enable polymorphic behavior

---

## 2. Objects & Classes

| Term | Meaning |
|------|---------|
| Object | Real-world entity with state + behavior (e.g., Car, Dog) |
| Class | Blueprint/template to create objects |
| State / Properties | Data fields (variables) |
| Behavior | Methods (functions operating on state) |

### Example
```java
class Student {
    private String name;
    private int age;

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public void celebrateBirthday() { this.age++; }
}

Student s1 = new Student();
s1.setAge(21);
s1.celebrateBirthday(); // age = 22
```

---

## 3. Procedural vs Object-Oriented

| Aspect | Procedural (C style) | OOP (Java) |
|--------|----------------------|------------|
| Focus | Functions | Objects (data + methods) |
| Data Handling | Data passed freely between functions | Data controlled via encapsulation |
| Coupling | Often tightly coupled | Encourages loose coupling |
| Reusability | Harder | Inheritance, composition |
| Security | Minimal data hiding | Access modifiers, encapsulation |

---

## 4. Four Pillars of OOP

### 4.1 Abstraction
Hiding internal implementation while exposing only essential operations.

Definition: “Show what it does, hide how it does.”

Example (Car brake):
```java
interface BrakingSystem {
    void applyBrake();
}

class ABSBrakingSystem implements BrakingSystem {
    @Override
    public void applyBrake() {
        // step1: read wheel speed
        // step2: modulate pressure
        // step3: release/apply pulses
    }
}
```

Advantages:
- Simplicity
- Security / confidentiality
- Reduces cognitive load
- Allows changing internals without breaking clients

How Achieved in Java:
- Interfaces
- Abstract classes (partially implemented templates)

### 4.2 Encapsulation (Data Hiding)
Bundling data (fields) and methods operating on it inside a class and controlling access.

Example:
```java
class Account {
    private double balance;          // hidden state

    public double getBalance() {     // controlled read
        return balance;
    }
    public void deposit(double amt) {
        if (amt > 0) balance += amt;
    }
}
```

Benefits:
- Control invariants
- Prevent misuse
- Enables refactoring
- Foundation for loose coupling

Encapsulation vs Abstraction:
| Abstraction | Encapsulation |
|-------------|---------------|
| Focus: Hide complexity | Focus: Restrict direct access |
| Design-level | Implementation-level |
| What to expose | How to protect |

### 4.3 Inheritance (is-a)
Mechanism where a child class derives properties and behaviors from a parent.

Syntax: `class Car extends Vehicle {}`

Example:
```java
class Vehicle {
    boolean hasEngine = true;
    boolean hasEngine() { return hasEngine; }
}

class Car extends Vehicle {
    String type; // SUV, Sedan
}
```

Types:
- Single: A → B
- Multilevel: A → B → C
- Hierarchical: A → B and A → C
- Multiple (A & B → C): Not allowed with classes (diamond problem)
- Multiple via Interfaces: Allowed (`class C implements A, B`)

Diamond Problem (Why multiple class inheritance is disallowed):
Ambiguity: If both parents define same method, child call is unclear.

### 4.4 Polymorphism (Many Forms)
Same operation behaves differently depending on context.

Two Types:
1. Compile-Time (Static) – Method Overloading  
2. Runtime (Dynamic) – Method Overriding

#### Method Overloading (Same name, different parameters)
```java
class Adder {
    int add(int a, int b) { return a + b; }
    int add(int a, int b, int c) { return a + b + c; }
    String add(String a, String b) { return a + b; }
    // Not valid: only differing by return type
}
```

Rules:
- Same method name
- Different parameter list (type, number, order)
- Cannot overload by return type alone

#### Method Overriding (Runtime dispatch)
```java
class Animal {
    void speak() { System.out.println("Generic sound"); }
}

class Dog extends Animal {
    @Override
    void speak() { System.out.println("Bark"); }
}

Animal a = new Dog();
a.speak(); // Bark (dynamic dispatch)
```

Overriding Rules:
- Same signature + return type (covariant allowed)
- Access cannot be more restrictive
- Only instance methods; `static` methods are hidden (not overridden)

Overloading vs Overriding:

| Feature | Overloading | Overriding |
|---------|-------------|-----------|
| Binding | Compile-time | Runtime |
| Scope | Same class | Parent–child |
| Signature | Must differ | Must match |
| Use Case | Convenience | Specialization |

---

## 5. Relationships: is-a vs has-a

### is-a
- Achieved via inheritance or interface implementation
- Example: `Car is a Vehicle`

### has-a (Association)
- One class holds reference to another
- Forms:
  - Association (generic link)
  - Aggregation (weak ownership; parts can outlive whole)  
  - Composition (strong ownership; parts die with whole)

ASCII Diagram:
```
Composition: House *-- Room
Aggregation:  Team  o-- Player
Association:  Teacher --> Course
```

Example Aggregation:
```java
class Course {}
class Student {
    List<Course> courses; // student can exist without courses
}
```

Example Composition:
```java
class Engine {}
class Car {
    private final Engine engine = new Engine(); // engine lifecycle tied to car
}
```

---

## 6. Aggregation vs Composition

| Aspect | Aggregation | Composition |
|--------|-------------|------------|
| Life Dependency | Independent | Dependent |
| Ownership | Shared / weak | Exclusive / strong |
| Example | University–Student | Human–Heart |
| UML | Hollow diamond | Filled diamond |

---

## 7. Interfaces vs Abstract Classes

| Aspect | Interface (pre-Java 8) | Abstract Class |
|--------|------------------------|----------------|
| Purpose | Contract | Partial implementation |
| Methods | Abstract (now: + default/static) | Abstract + concrete |
| Fields | public static final | Any (with access modifiers) |
| Inheritance | Multiple allowed | Single |
| Use When | Behavior role | “Is-a” with shared code |

---

## 8. Code Snippet Summary

Create object:
```java
Student engStudent = new Student();
```

Inheritance:
```java
class A {}
class B extends A {}
```

Polymorphism:
```java
A ref = new B(); // dynamic method dispatch
```

Encapsulation with access control:
```java
private int age;
public int getAge() { return age; }
```

Overloading ambiguity (invalid):
```java
int f(int a, int b)
String f(int a, int b) // INVALID
```

---

## 9. Common Interview Questions (With Brief Answers)

1. Difference between Abstraction and Encapsulation?  
   - Abstraction hides complexity; encapsulation hides data & enforces access rules.

2. Why no multiple inheritance in Java classes?  
   - To avoid diamond problem ambiguity (method conflict).

3. How does Java simulate multiple inheritance?  
   - Through interfaces (class can implement multiple interfaces).

4. Overloading vs Overriding?  
   - Overloading: compile-time, same class, different params. Overriding: runtime, subclass changes behavior.

5. Can we overload `main`?  
   - Yes (but JVM calls only `public static void main(String[] args)`).

6. Can constructors be overridden?  
   - No (not inherited). They can be overloaded.

7. What is polymorphism in real-world analogy?  
   - Person: father, employee, customer roles.

8. What is composition vs aggregation?  
   - Composition: strong ownership (House–Room). Aggregation: weak (Class–Student).

9. Can a private method be overridden?  
   - No (not visible to subclass).

10. Why use getters/setters instead of public fields?  
    - Validation, encapsulation, future-proofing.

11. What is dynamic binding?  
    - Method resolution at runtime based on actual object.

12. What happens if you change method return type only?  
    - Compile error: duplicate method.

13. Is “data hiding” same as “encapsulation”?  
    - Data hiding is a subset/result of encapsulation.

14. What’s the diamond problem?  
    - Ambiguity when inheriting from two parents with same method signature.

15. When to favor composition over inheritance?  
    - When relationship is “has-a” / to avoid tight coupling / increase flexibility.

---

## 10. Real-World Use Cases

| Concept | Example |
|---------|---------|
| Abstraction | PaymentGateway API hides network logic |
| Encapsulation | Banking account balance protection |
| Inheritance | GUI frameworks (JComponent → JButton) |
| Polymorphism | Strategy pattern (e.g., compression algorithms) |
| Composition | Car has Engine, Wheels |
| Aggregation | Library with independent Books |
| Overloading | Utility math operations |
| Overriding | Customizing framework lifecycle methods |

---

## 11. Best Practices

### Do
- Favor composition over inheritance for flexibility
- Keep fields `private`; expose via getters/setters
- Use interfaces for roles/behavioral contracts
- Mark overriding methods with `@Override`
- Keep classes cohesive (Single Responsibility Principle)

### Avoid
- Deep inheritance chains (fragile base class problem)
- Public mutable fields
- Overusing inheritance where delegation suffices
- Mixing abstraction levels in one class
- Exposing internal collections directly (return unmodifiable or copies)

### Pitfalls
- Assuming overloading is runtime polymorphism (it’s compile-time)
- Forgetting to make base methods `protected/public` for overriding
- Shadowing fields (field hiding is not polymorphic)
- Mistaking aggregation for composition (lifecycle mismatch)

---

## 12. Comparison Summaries

| Pair | Key Difference |
|------|----------------|
| Abstraction vs Encapsulation | Hiding complexity vs restricting access |
| Inheritance vs Composition | “Is-a” reuse vs “Has-a” flexible assembly |
| Overloading vs Overriding | Compile-time vs runtime binding |
| Aggregation vs Composition | Independent lifecycles vs dependent |
| Interface vs Abstract Class | Pure contract vs partial implementation |

---

## 13. ASCII Diagrams

Inheritance (Hierarchical):
```
        Vehicle
        /     \
     Car     Bike
```

Multilevel:
```
Animal
  |
Mammal
  |
Dog
```

Composition vs Aggregation:
```
Car *-- Engine      (Composition: Engine dies with Car)
Team o-- Player     (Aggregation: Player can join another Team)
```

Polymorphism:
```
Animal a = new Dog();
a.speak();  // Dog's implementation
```

Diamond Problem (Not allowed with classes):
```
   A
  / \
 B   C
  \ /
   D  (Ambiguous A/B method)
```

---

## 14. Memory / Conceptual Flow (Lifecycle Sketch)
Object creation:
1. Class loaded
2. Memory allocated
3. Fields default-initialized
4. Explicit initializers run
5. Constructor executes
6. Reference returned

---

## 15. When to Use Each Pillar

| Goal | Use |
|------|-----|
| Hide implementation | Abstraction |
| Protect state | Encapsulation |
| Reuse + specialize | Inheritance |
| Flexible behavior | Polymorphism |

---

## 16. Quick Revision Flash Points

- Object = state + behavior
- Class = blueprint
- Abstraction = “What”
- Encapsulation = “How protected”
- Inheritance enables overriding
- Overloading ≠ Overriding
- Prefer composition over inheritance
- Interfaces solve multiple inheritance issues
- Aggregation vs Composition = lifecycle dependency
- Polymorphism = runtime dispatch (overriding)

---

## 17. Micro Cheat Sheet

| Topic | 1-Liner |
|-------|---------|
| new | Allocates object |
| this | Current instance reference |
| super | Parent reference (constructor/method) |
| final class | Cannot be subclassed |
| final method | Cannot be overridden |
| final variable | Single assignment |
| private | Class-only visibility |
| protected | Package + subclasses |
| default (no modifier) | Package-private |
| public | Everywhere |

---