Exactly 👍

In Java:

* **Use interfaces** when you want to define a **contract (behavior)** that **multiple unrelated classes** can implement.
  * Example: `Comparable`, `Runnable`, `Serializable`.
  * Interfaces don’t provide implementation (except `default`/`static` methods in modern Java).

```java
interface Flyable {
    void fly();
}

class Bird implements Flyable {
    @Override
    public void fly() {
        System.out.println("Bird flaps wings to fly.");
    }
}

class Airplane implements Flyable {
    @Override
    public void fly() {
        System.out.println("Airplane uses engines to fly.");
    }
}
```

Here, **Bird** and **Airplane** are unrelated, but both can "fly".

By using an interface, you ensure both provide their own version of the behavior.

Would you like me to also contrast this with **when to use abstract classes** so you can see the difference side by side?



Perfect, let’s break it down side by side 👇

---

### 🔹 **Interface**

* **Purpose** : Define a *contract* for behavior.
* **Use case** : When unrelated classes need to share the same capability.
* **Multiple inheritance** : A class can implement many interfaces.
* **Code sharing** : No shared state/implementation (except `default`/`static` methods in Java 8+).

```java
interface Drivable {
    void drive();
}

class Car implements Drivable {
    public void drive() {
        System.out.println("Car drives on the road.");
    }
}

class Boat implements Drivable {
    public void drive() {
        System.out.println("Boat sails in water.");
    }
}
```

✅ Car and Boat are unrelated, but both can  **drive** .

---

### 🔹 **Abstract Class**

* **Purpose** : Provide a *base class* with common code + allow subclasses to fill in details.
* **Use case** : When classes are related and share state/implementation.
* **Single inheritance** : A class can extend only one abstract class.
* **Code sharing** : Can have fields, constructors, and fully implemented methods.

```java
abstract class Animal {
    String name;

    Animal(String name) {
        this.name = name;
    }

    abstract void sound(); // must be implemented

    void eat() {  // shared implementation
        System.out.println(name + " is eating.");
    }
}

class Dog extends Animal {
    Dog(String name) { super(name); }

    void sound() {
        System.out.println("Bark");
    }
}

class Cat extends Animal {
    Cat(String name) { super(name); }

    void sound() {
        System.out.println("Meow");
    }
}
```

✅ Dog and Cat are related (both are Animals) and share code like `eat()`.

---

### 📝 Rule of Thumb

* **Interface** → "What" something can do (behavior, capability).
* **Abstract Class** → "What it is" with partial implementation.

---

Do you want me to also make a **decision table/flowchart** (when to pick interface vs abstract class) so it’s easier to decide in practice?



Exactly 👍

That’s one of the main use cases for **abstract classes** in Java (and other OOP languages).

Here’s the breakdown:

* **Abstract class**
  * Can have **both abstract methods** (no implementation, just the contract) and **concrete methods** (with implementation).
  * Lets you put **shared/common code** in one place so that subclasses don’t have to repeat it.
  * Subclasses are *forced* to provide implementations for abstract methods.
  * Useful when classes share a common base but also have their own specific behavior.
* **Interface**
  * Defines only contracts (before Java 8, only abstract methods; from Java 8+, can also have `default` and `static` methods).
  * Best when you just want to **specify behavior** but don’t care about shared implementation.

---

✅ **Example (using abstract class with common code):**

```java
abstract class Vehicle {
    // Common code shared across all vehicles
    public void start() {
        System.out.println("Vehicle is starting...");
    }

    // Abstract method - must be implemented by subclasses
    public abstract void drive();
}

class Car extends Vehicle {
    @Override
    public void drive() {
        System.out.println("Car is driving on the road.");
    }
}

class Bike extends Vehicle {
    @Override
    public void drive() {
        System.out.println("Bike is driving on two wheels.");
    }
}
```

Usage:

```java
public class Main {
    public static void main(String[] args) {
        Vehicle car = new Car();
        car.start();   // common code from abstract class
        car.drive();   // subclass-specific

        Vehicle bike = new Bike();
        bike.start();
        bike.drive();
    }
}
```

---

👉 Rule of thumb:

* Use **abstract class** if you need to **share state/code** across related classes.
* Use **interface** if you just need to **enforce a contract** without sharing implementation.

Do you want me to also give you a **real-world guideline** on *when to pick abstract class vs interface* in design decisions?
