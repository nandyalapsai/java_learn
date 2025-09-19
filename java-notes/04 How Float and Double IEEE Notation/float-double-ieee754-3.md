# Java Notes: Understanding Float and Double (IEEE 754)

These notes summarize the internal storage mechanism of `float` and `double` primitive types in Java, based on the IEEE 754 standard. This is a frequent topic in technical interviews.

---

### 1. The Problem: Floating-Point Inaccuracy

A common observation is that floating-point numbers sometimes behave unexpectedly. For example, a `float` initialized to `0.7f` doesn't print as `0.7`.

```java
float myFloat = 0.7f;
System.out.println(myFloat); // Prints something like 0.699999988079071
```

This happens because some decimal fractions cannot be represented perfectly in binary, leading to precision loss. This document explains how this representation works and why the inaccuracy occurs.

---

### 2. Key Concepts & Definitions

*   **IEEE 754**: The standard that defines how floating-point numbers are represented in binary. Java's `float` and `double` types adhere to this standard.
*   **Float**: A 32-bit single-precision floating-point number.
*   **Double**: A 64-bit double-precision floating-point number.
*   **Sign Bit**: A single bit indicating if the number is positive (0) or negative (1).
*   **Exponent**: A component that represents the scale (magnitude) of the number. It determines the range of values that can be represented.
*   **Mantissa (or Significand)**: The part of the number that stores the actual significant digits (the precision).
*   **Bias**: A fixed value added to the exponent. This allows the exponent to represent both positive and negative values without needing a separate sign bit for the exponent itself.

---

### 3. Floating-Point Storage Format

#### Float (32-Bit)
The 32 bits are allocated as follows:

*   **Sign**: 1 bit
*   **Exponent**: 8 bits
*   **Mantissa**: 23 bits

```
[S | EEEEEEEE | MMMMMMMMMMMMMMMMMMMMMMM]
 ^      ^               ^
Sign  Exponent        Mantissa
(1)     (8)             (23)
```
*   **Bias**: The bias for a float's exponent is **127**.

#### Double (64-Bit)
The 64 bits are allocated as follows:

*   **Sign**: 1 bit
*   **Exponent**: 11 bits
*   **Mantissa**: 52 bits

*   **Bias**: The bias for a double's exponent is **1023**.

---

### 4. How a Decimal Number is Stored (Step-by-Step)

The process involves converting a decimal number into its IEEE 754 binary representation.

**Step 1: Convert to Binary**
Convert the integer and fractional parts of the number into binary.
*   **Integer part**: Use successive division by 2.
*   **Fractional part**: Use successive multiplication by 2.

**Step 2: Normalize the Binary Number**
Rewrite the binary number in scientific notation form: `1.mantissa × 2^exponent`.
*   The decimal point is moved until there is only one `1` to its left.
*   The number of places the point moved determines the `exponent`. Moving left creates a positive exponent; moving right creates a negative exponent.

**Step 3: Calculate the Biased Exponent**
Add the bias to the exponent from Step 2.
*   `Stored Exponent = Actual Exponent + Bias`
*   For `float`, `Stored Exponent = Actual Exponent + 127`.

**Step 4: Assemble the Final Binary Representation**
Combine the three parts:
1.  **Sign Bit**: `0` for positive, `1` for negative.
2.  **Exponent**: The binary representation of the biased exponent (from Step 3).
3.  **Mantissa**: The fractional part of the normalized number (from Step 2), padded with zeros to fill its allocated bits.

---

### 5. Examples

#### Example 1: `4.125f` (A number that can be represented precisely)

1.  **Convert to Binary**:
    *   `4` in decimal is `100` in binary.
    *   `0.125` in decimal is `0.001` in binary.
    *   So, `4.125` is `100.001` in binary.

2.  **Normalize**:
    *   Move the decimal point two places to the left: `1.00001 × 2^2`.
    *   **Exponent**: `2`
    *   **Mantissa**: `00001`

3.  **Calculate Biased Exponent**:
    *   `2 + 127 (bias) = 129`.
    *   `129` in binary is `10000001`.

4.  **Assemble**:
    *   **Sign**: `0` (since `4.125` is positive).
    *   **Exponent**: `10000001`.
    *   **Mantissa**: `00001000000000000000000` (padded to 23 bits).

#### Example 2: `0.7f` (A number that cannot be represented precisely)

1.  **Convert to Binary**:
    *   `0.7 × 2 = 1.4` -> `1`
    *   `0.4 × 2 = 0.8` -> `0`
    *   `0.8 × 2 = 1.6` -> `1`
    *   `0.6 × 2 = 1.2` -> `1`
    *   `0.2 × 2 = 0.4` -> `0`
    *   The pattern `100` starts repeating from here (`0.4`).
    *   Binary is `0.1011001100110...` (a repeating fraction).

2.  **Normalize**:
    *   Move the decimal point one place to the right: `1.011001100110... × 2^-1`.
    *   **Exponent**: `-1`
    *   **Mantissa**: `011001100110...`

3.  **Calculate Biased Exponent**:
    *   `-1 + 127 (bias) = 126`.
    *   `126` in binary is `01111110`.

4.  **Assemble**:
    *   **Sign**: `0` (positive).
    *   **Exponent**: `01111110`.
    *   **Mantissa**: `01100110011001100110011` (truncated to 23 bits).

Because the binary representation is infinitely repeating, it must be truncated to fit into the 23-bit mantissa. This truncation is the source of the precision error. When the value is read back, it's the decimal equivalent of this truncated binary, which is `0.699999988...`, not exactly `0.7`.

---

### 6. Interview Questions & Answers

**Q1: Why does printing `0.7f` in Java not give `0.7`?**
**A:** Because the decimal `0.7` cannot be represented perfectly in binary. It results in an infinitely repeating binary fraction. Since the `float` type has a limited number of bits for storage (23 for the mantissa), this binary fraction is truncated, leading to a small precision loss.

**Q2: How are floating-point numbers stored in memory?**
**A:** They are stored using the IEEE 754 standard, which divides the bits into three parts: a sign bit (for positive/negative), an exponent (for the number's magnitude), and a mantissa (for its precision).

**Q3: What is the purpose of the exponent bias?**
**A:** The bias allows the exponent to store both positive and negative values without requiring a separate sign bit for the exponent itself. The actual exponent is found by subtracting the bias from the stored exponent value.

---

### 7. Best Practices & Common Pitfalls

*   **Pitfall**: Using `float` or `double` for financial calculations or any other domain where exact precision is critical. The rounding errors can accumulate and lead to significant miscalculations.
*   **Best Practice**: For precise decimal arithmetic, **always use the `java.math.BigDecimal` class**. It can represent decimal numbers exactly, avoiding the pitfalls of binary floating-point arithmetic.

```java
// Don't do this for money
double price = 0.1;
double discount = 0.2;
double finalPrice = price + discount; // May not be exactly 0.3

// Do this instead
BigDecimal priceBd = new BigDecimal("0.1");
BigDecimal discountBd = new BigDecimal("0.2");
BigDecimal finalPriceBd = priceBd.add(discountBd); // Exactly 0.3
```

### 8. Real-World Use Cases

*   **Where `float`/`double` are used**: Scientific computing, graphics, machine learning, and other fields where a high degree of precision is less important than a wide range of values and computational speed.
*   **Where they are avoided**: Financial applications, banking systems, and billing software, where `BigDecimal` is the standard choice.
