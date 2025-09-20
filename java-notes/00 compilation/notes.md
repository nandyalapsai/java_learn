---

# 🔹 Scenario

We will have:

* One class on **D drive** (`Hello.java`) inside package `mypackage`.
* Another class on **C drive** (`Test.java`) inside package `mainpackage` that uses `Hello`.

---

## 📂 Folder Structure

```
D:\drive\mypackage\Hello.java
C:\drive\mainpackage\Test.java
```

---

## 1. Create the `Hello.java` file on D drive

`D:\drive\mypackage\Hello.java`

```java
package mypackage;

public class Hello {
    public void sayHello() {
        System.out.println("Hello from another drive!");
    }
}
```

---

## 2. Create the `Test.java` file on C drive

`C:\drive\mainpackage\Test.java`

```java
package mainpackage;

import mypackage.Hello;  // Importing class from D drive

public class Test {
    public static void main(String[] args) {
        Hello h = new Hello();
        h.sayHello();
    }
}
```

---

## 3. Compile `Hello.java` on D drive

Open CMD and run:

```bat
cd D:\drive
javac mypackage\Hello.java
```

👉 This creates `Hello.class` inside `D:\drive\mypackage`.

---

## 4. Compile `Test.java` on C drive with classpath

Now go to C drive:

```bat
cd C:\drive
javac -cp .;D:\drive mainpackage\Test.java
```

* `.` → current directory (C:\drive)
* `D:\drive` → where compiled `Hello.class` is located

---

## 5. Run the program

```bat
java -cp .;D:\drive mainpackage.Test
```

✅ Output:

```
Hello from another drive!
```

---

# 🔹 Summary of Commands

```bat
cd D:\drive
javac mypackage\Hello.java

cd C:\drive
javac -cp .;D:\drive mainpackage\Test.java
java  -cp .;D:\drive mainpackage.Test
```

---


Perfect! Let’s do it step by step using a **JAR file** so your `Hello` class can be reused easily without referencing the D: drive every time.

---

# 🔹 Step-by-Step: Using a JAR File

## 1. Folder structure

Same as before:

```
D:\drive\mypackage\Hello.java
C:\drive\mainpackage\Test.java
```

---

## 2. Compile `Hello.java` and create a JAR

Open CMD and run:

```bat
cd D:\drive
javac mypackage\Hello.java
```

This generates:

```
D:\drive\mypackage\Hello.class
```

Now create a JAR file:

```bat
jar cvf mylib.jar -C D:\drive .
```

✅ This creates `mylib.jar` containing `mypackage.Hello`.

---

## 3. Copy `mylib.jar` to C drive (optional, for convenience)

You can copy it to `C:\drive`:

```bat
copy D:\drive\mylib.jar C:\drive\
```

---

## 4. Compile `Test.java` with JAR in classpath

```bat
cd C:\drive
javac -cp .;mylib.jar mainpackage\Test.java
```

* `.` → current directory
* `mylib.jar` → JAR containing `Hello.class`

---

## 5. Run the program

```bat
java -cp .;mylib.jar mainpackage.Test
```

✅ Output:

```
Hello from another drive!
```

---

# 🔹 Advantages of Using JAR

* No need to keep pointing to the original D: drive.
* Easily shareable with other projects.
* Supports multiple classes in a single archive.
* Keeps your project clean and portable.

---