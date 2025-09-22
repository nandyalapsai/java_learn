# Java Interfaces - Practice Examples & Exercises

## 🎯 Practical Code Examples

### Example 1: Basic Interface Implementation
```java
// Interface definition
interface Vehicle {
    void start();
    void stop();
    void accelerate();
}

// Implementation
class Car implements Vehicle {
    private String brand;
    
    public Car(String brand) {
        this.brand = brand;
    }
    
    @Override
    public void start() {
        System.out.println(brand + " car is starting...");
    }
    
    @Override
    public void stop() {
        System.out.println(brand + " car has stopped.");
    }
    
    @Override
    public void accelerate() {
        System.out.println(brand + " car is accelerating!");
    }
}

class Motorcycle implements Vehicle {
    private String model;
    
    public Motorcycle(String model) {
        this.model = model;
    }
    
    @Override
    public void start() {
        System.out.println(model + " motorcycle is roaring to life!");
    }
    
    @Override
    public void stop() {
        System.out.println(model + " motorcycle has stopped.");
    }
    
    @Override
    public void accelerate() {
        System.out.println(model + " motorcycle is speeding up!");
    }
}

// Demonstration of polymorphism
public class VehicleDemo {
    public static void main(String[] args) {
        Vehicle[] vehicles = {
            new Car("Toyota"),
            new Motorcycle("Harley"),
            new Car("BMW")
        };
        
        for (Vehicle vehicle : vehicles) {
            vehicle.start();      // Polymorphic call
            vehicle.accelerate(); // Polymorphic call
            vehicle.stop();       // Polymorphic call
            System.out.println("---");
        }
    }
}
```

### Example 2: Multiple Inheritance through Interfaces
```java
// Multiple interfaces
interface Swimmer {
    void swim();
}

interface Flyer {
    void fly();
}

interface Runner {
    void run();
}

// Class implementing multiple interfaces
class Duck implements Swimmer, Flyer, Runner {
    @Override
    public void swim() {
        System.out.println("Duck swims gracefully in water");
    }
    
    @Override
    public void fly() {
        System.out.println("Duck flies to migrate south");
    }
    
    @Override
    public void run() {
        System.out.println("Duck waddles quickly on land");
    }
}

class Penguin implements Swimmer, Runner {
    @Override
    public void swim() {
        System.out.println("Penguin swims underwater hunting fish");
    }
    
    @Override
    public void run() {
        System.out.println("Penguin slides on ice belly-first");
    }
    // Note: Penguin doesn't implement Flyer because it can't fly
}

// Demonstration
public class AnimalDemo {
    public static void main(String[] args) {
        Duck duck = new Duck();
        Penguin penguin = new Penguin();
        
        // Duck can do all three activities
        testSwimmer(duck);
        testFlyer(duck);
        testRunner(duck);
        
        // Penguin can only swim and run
        testSwimmer(penguin);
        testRunner(penguin);
        // testFlyer(penguin); // Won't compile - Penguin doesn't implement Flyer
    }
    
    static void testSwimmer(Swimmer s) {
        s.swim();
    }
    
    static void testFlyer(Flyer f) {
        f.fly();
    }
    
    static void testRunner(Runner r) {
        r.run();
    }
}
```

### Example 3: Nested Interfaces
```java
public interface Building {
    void construct();
    
    // Nested interface for specialized buildings
    interface SmartBuilding {
        void automateSystem();
        void monitorSecurity();
    }
}

// Regular building implementation
class House implements Building {
    @Override
    public void construct() {
        System.out.println("Building a traditional house");
    }
}

// Smart building implementation
class SmartHome implements Building, Building.SmartBuilding {
    @Override
    public void construct() {
        System.out.println("Building a smart home with IoT devices");
    }
    
    @Override
    public void automateSystem() {
        System.out.println("Automating lights, temperature, and security");
    }
    
    @Override
    public void monitorSecurity() {
        System.out.println("24/7 security monitoring with cameras and sensors");
    }
}

// Interface within a class
class ElectronicDevice {
    protected interface Programmable {
        void program();
        void execute();
    }
}

class SmartWatch extends ElectronicDevice implements ElectronicDevice.Programmable {
    @Override
    public void program() {
        System.out.println("Programming smartwatch apps and notifications");
    }
    
    @Override
    public void execute() {
        System.out.println("Running health monitoring and fitness tracking");
    }
}
```

### Example 4: Interface Constants
```java
interface MathConstants {
    // All are implicitly public static final
    double PI = 3.14159;
    int MAX_VALUE = Integer.MAX_VALUE;
    String MATH_LIBRARY = "StandardMath";
}

interface GameConstants {
    int MAX_PLAYERS = 4;
    int MIN_PLAYERS = 2;
    String GAME_VERSION = "1.0";
}

class GeometryCalculator implements MathConstants {
    public double calculateCircleArea(double radius) {
        return PI * radius * radius; // Using interface constant
    }
    
    public void printLibraryInfo() {
        System.out.println("Using: " + MATH_LIBRARY);
    }
}

class BoardGame implements GameConstants {
    private int currentPlayers = 0;
    
    public boolean addPlayer() {
        if (currentPlayers < MAX_PLAYERS) {
            currentPlayers++;
            return true;
        }
        return false;
    }
    
    public boolean canStartGame() {
        return currentPlayers >= MIN_PLAYERS;
    }
}
```

---

## 🏋️ Practice Exercises

### Exercise 1: Shape Calculator
**Create a shape calculation system using interfaces**

Requirements:
1. Create an interface `Shape` with methods:
   - `double calculateArea()`
   - `double calculatePerimeter()`
   - `String getShapeType()`

2. Implement the interface in classes:
   - `Circle` (radius)
   - `Rectangle` (length, width)
   - `Triangle` (three sides)

3. Create a `ShapeCalculator` class that can calculate total area and perimeter for an array of shapes.

**Solution Template:**
```java
interface Shape {
    double calculateArea();
    double calculatePerimeter();
    String getShapeType();
}

// Implement your classes here
class Circle implements Shape {
    // Your implementation
}

class Rectangle implements Shape {
    // Your implementation
}

class Triangle implements Shape {
    // Your implementation
}

class ShapeCalculator {
    public void analyzeShapes(Shape[] shapes) {
        // Calculate and display total area, perimeter
        // Show individual shape details
    }
}
```

### Exercise 2: E-commerce Payment System
**Create a flexible payment processing system**

Requirements:
1. Create interfaces:
   - `PaymentMethod` with methods: `processPayment(double amount)`, `getPaymentType()`
   - `Refundable` with method: `refund(String transactionId, double amount)`

2. Implement payment methods:
   - `CreditCard` (implements both interfaces)
   - `PayPal` (implements both interfaces)
   - `Cash` (implements only PaymentMethod)

3. Create a `PaymentProcessor` that can handle different payment types.

### Exercise 3: Media Player System
**Design a multimedia system using interfaces**

Requirements:
1. Create interfaces:
   - `Playable` with methods: `play()`, `pause()`, `stop()`
   - `Downloadable` with methods: `download()`, `getFileSize()`
   - `Streamable` with method: `stream()`

2. Implement media types:
   - `AudioFile` (implements Playable and Downloadable)
   - `VideoFile` (implements all three interfaces)
   - `LiveStream` (implements only Playable and Streamable)

3. Create a `MediaPlayer` class that can handle different media types.

### Exercise 4: Smart Home System
**Create a smart home automation system**

Requirements:
1. Create interfaces:
   - `Controllable` with methods: `turnOn()`, `turnOff()`, `getStatus()`
   - `Dimmable` with methods: `setBrightness(int level)`, `getBrightness()`
   - `Programmable` with methods: `setSchedule(String schedule)`, `executeSchedule()`

2. Implement smart devices:
   - `SmartLight` (implements all three)
   - `SmartFan` (implements Controllable and Programmable)
   - `SmartTV` (implements Controllable)

3. Create a `HomeAutomationSystem` to manage all devices.

---

## 🎯 Challenge Problems

### Challenge 1: Restaurant Management System
Create a comprehensive restaurant system with interfaces for:
- `Cookable` (items that can be cooked)
- `Servable` (items that can be served)
- `Billable` (items that can be billed)
- `Customizable` (items that can be customized)

Different menu items should implement appropriate interfaces.

### Challenge 2: Transportation Network
Design a transportation network with interfaces for:
- `Drivable` (land vehicles)
- `Flyable` (air vehicles)
- `Floatable` (water vehicles)
- `Eco-friendly` (environmentally friendly)

Create various vehicle types and a routing system.

### Challenge 3: Gaming Framework
Build a gaming framework with interfaces for:
- `Movable` (entities that can move)
- `Attackable` (entities that can attack)
- `Defendable` (entities that can defend)
- `Upgradeable` (entities that can be upgraded)

Create different game entities (Player, Enemy, NPC) with appropriate behaviors.

---

## 🧪 Testing Your Understanding

### Quick Quiz
1. Can an interface have a constructor? Why or why not?
2. What happens if you don't implement all methods of an interface in a concrete class?
3. Can two interfaces have methods with the same signature? How is this resolved?
4. Why can't interface methods be declared as `final`?
5. What is the difference between `extends` and `implements`?

### Code Review Exercise
Review this code and identify issues:
```java
interface Animal {
    private void eat();  // Issue 1
    final void sleep();  // Issue 2
    static String name = "Animal";  // Issue 3
}

class Dog extends Animal {  // Issue 4
    protected void eat() {  // Issue 5
        System.out.println("Dog eating");
    }
}
```

**Issues to find:**
1. Interface methods cannot be private (before Java 9)
2. Interface methods cannot be final
3. Interface fields should be explicitly initialized as constants
4. Should use `implements`, not `extends`
5. Implementing method cannot have more restrictive access

---

## 🎓 Advanced Topics for Further Study

### Future Learning Path
1. **Java 8 Interface Features:**
   - Default methods
   - Static methods in interfaces
   - Functional interfaces

2. **Java 9+ Interface Features:**
   - Private methods in interfaces
   - Private static methods

3. **Design Patterns with Interfaces:**
   - Strategy Pattern
   - Observer Pattern
   - Command Pattern

4. **Best Practices:**
   - Interface Segregation Principle
   - Dependency Inversion Principle
   - Programming to interfaces, not implementations

---

## 💡 Tips for Success

### Study Strategy
1. **Practice coding**: Don't just read - implement the examples
2. **Draw diagrams**: Visualize the relationships between interfaces and classes
3. **Real-world thinking**: Always connect concepts to real-world scenarios
4. **Interview prep**: Practice explaining concepts out loud

### Common Mistakes to Avoid
1. Confusing interface inheritance with class inheritance
2. Forgetting that interface methods are public by default
3. Trying to instantiate interfaces directly
4. Making interfaces too complex (fat interfaces)

### Memory Techniques
- **"Interface = Contract"**: Think of interfaces as contracts that classes must fulfill
- **"Multiple hats"**: A class can wear multiple interface "hats" (multiple inheritance)
- **"Public promise"**: Interface methods are public promises to implement functionality

---

*Happy coding! Master these concepts through practice, and you'll have a solid foundation in Java interfaces.*