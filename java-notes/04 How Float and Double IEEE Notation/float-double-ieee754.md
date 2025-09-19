# Float & Double in Java (IEEE 754) – Interview & Revision Notes

---
## 1. Why This Matters
Floating‑point behavior (e.g., `0.7f` printing as `0.69999987`) is a classic Java interview topic. Understanding IEEE 754 explains precision limits, rounding, comparisons, and when to use `BigDecimal` instead.

---
## 2. Key Concepts & Definitions
- **IEEE 754**: International standard for binary floating‑point representation.
- **float**: 32-bit single precision (1 sign + 8 exponent + 23 fraction bits). Approx ~7 decimal digits precision.
- **double**: 64-bit double precision (1 sign + 11 exponent + 52 fraction bits). Approx ~15–16 decimal digits precision.
- **Sign bit (S)**: `0` = positive, `1` = negative.
- **Exponent (E)**: Stored with a bias so negative exponents don’t need two’s complement.
  - Bias = 127 for float, 1023 for double.
- **Mantissa/Fraction (F)**: Fractional part after normalization (hidden leading 1 for normal numbers).
- **Normalized form**: `value = (-1)^S * (1 + F) * 2^(E - bias)`.
- **Denormal/Subnormal**: Exponent field all zeros → no hidden 1 → `value = (-1)^S * F * 2^(1 - bias)`.
- **Specials**:
  - `E=all 1s & F=0` → ±Infinity
  - `E=all 1s & F≠0` → NaN
- **Precision vs Scale**: Precision = number of significant digits representable; floating types sacrifice exactness for range.
- **Rounding mode**: Java uses IEEE 754 round to nearest, ties to even.

---
## 3. Bit Layout Diagrams
### float (32 bits)
```
| 31 | 30 ........ 23 | 22 .............................. 0 |
| S  |  Exponent (8)  |        Fraction / Mantissa (23)     |
```
### double (64 bits)
```
| 63 | 62 ........ 52 | 51 ........................................ 0 |
| S  |  Exponent (11) |            Fraction / Mantissa (52)          |
```

---
## 4. Encoding Example: 4.125 (float)
1. Decimal → Binary: `4 = 100`, `.125 = .001` ⇒ `100.001`
2. Normalize: `1.00001 × 2^2`
3. Exponent stored = `2 + 127 = 129` → binary `10000001`
4. Fraction bits = after the leading 1 → `00001000000000000000000`
5. Assemble:
```
Sign: 0
Exponent: 10000001
Mantissa: 00001000000000000000000
```
6. Reconstruct: `(-1)^0 * (1 + 1*2^-5) * 2^(129-127)` = `(1 + 1/32) * 4 = 33/8 = 4.125`
✅ Exact (terminates in binary).

---
## 5. Why 0.7f != 0.7
- 0.7 (decimal) has a repeating binary fraction (like 1/3 in decimal):
  - Approx binary: `0.1011001100110011… (repeats 1001 1 0011)`
- After cutting to 23 fraction bits → stored approximation.
- Reconstruction yields ~`0.69999987f`.
- Limitation: Not all finite decimals have finite base‑2 expansions.

### Mini Table
```
Decimal  | Binary (truncated)        | Stored Approx (float)
0.5      | 0.1                       | 0.5 (exact)
0.25     | 0.01                      | 0.25 (exact)
0.1      | 0.0001100110011…          | 0.10000000149...
0.7      | 0.101100110011…           | 0.69999987...
```

---
## 6. Java Code Snippets
```java
public class FloatDemo {
    public static void main(String[] args) {
        float a = 0.7f;
        double b = 0.7; // double literal
        System.out.println(a); // 0.69999987
        System.out.println(b); // 0.7 (still approximation, just more precision)

        float x = 4.125f;
        System.out.println(x); // 4.125 (exact)

        // Avoid direct equality with floats
        float target = 0.7f;
        if (Math.abs(a - target) < 1e-6) {
            System.out.println("Approximately equal");
        }

        // Prefer BigDecimal for exact decimal math
        BigDecimal bd1 = new BigDecimal("0.7");
        BigDecimal bd2 = new BigDecimal("0.1");
        System.out.println(bd1.add(bd2)); // 0.8 exactly
    }
}
```

### Pitfall: Using new BigDecimal(double)
```java
System.out.println(new BigDecimal(0.7));
// 0.699999999999999955591... (captures the binary inaccuracy)
System.out.println(new BigDecimal("0.7"));
// 0.7
```

---
## 7. Flow: Converting Decimal to IEEE 754 (Normalized Number)
```
Decimal → Split (integer + fraction)
      ↓
Convert integer to binary & fraction via ×2 extraction
      ↓
Combine → Raw binary form
      ↓
Normalize to 1.x * 2^e
      ↓
Store (e + bias) in exponent field
      ↓
Drop leading 1, fill fraction bits (pad/truncate)
      ↓
Assemble sign | exponent | fraction
```

---
## 8. Interview Q&A
1. Q: Why does 0.1 + 0.2 != 0.3 in floating point?  
   A: 0.1, 0.2, 0.3 are non‑terminating in binary; each is rounded; sum of approximations differs slightly.
2. Q: What is IEEE 754 bias for double?  
   A: 1023.
3. Q: How many bits of precision in float?  
   A: 24 (including the implicit leading 1). ~7 decimal digits.
4. Q: When do you get NaN?  
   A: Undefined operations (e.g., `0.0/0.0`, sqrt of negative (without using complex types)).
5. Q: Difference between Infinity and NaN?  
   A: Infinity = well-defined overflow; NaN = invalid/indeterminate result.
6. Q: Why use BigDecimal?  
   A: Exact decimal arithmetic (finance, currency) where rounding errors are unacceptable.
7. Q: What are subnormal numbers?  
   A: Numbers where exponent bits are all zero; allow gradual underflow with reduced precision.
8. Q: Is double always precise enough for money?  
   A: No—rounding drift accumulates; use `BigDecimal` with proper scale and rounding mode.
9. Q: How to compare floats safely?  
   A: Use an epsilon: `Math.abs(a-b) < epsilon`.
10. Q: What is ULP?  
    A: Unit in the Last Place—the gap between adjacent representable numbers at a magnitude.

---
## 9. Real‑World Use Cases
- Graphics pipelines (colors, transforms) → `float`
- Scientific computations, simulations → `double`
- Machine learning (tensors, often even `float16` or `bfloat16`)
- Financial calculations → `BigDecimal` (not float/double)
- Geospatial coordinates → `double` preferred
- Time series analytics → double for speeds; BigDecimal if exactness matters

---
## 10. Best Practices
- Use `double` by default in Java unless memory/bandwidth critical.
- Use `BigDecimal(String)` for money or exact decimal logic.
- Avoid `==` for float/double comparisons; define tolerance.
- Predefine a constant EPS for domain, e.g., `private static final double EPS = 1e-9;`.
- Be explicit with suffixes: `1.0f` for float.
- Beware of accumulation error in large loops (consider Kahan summation for high precision sums).
- Convert user input to `BigDecimal` early if exactness required.
- Never rely on printed representation for correctness—use logical checks.

---
## 11. Common Pitfalls
| Pitfall | Example | Fix |
|---------|---------|-----|
| Equality check | `if (a == 0.7)` | Use epsilon comparison |
| Using `new BigDecimal(double)` | `new BigDecimal(0.1)` | Use string literal or `BigDecimal.valueOf(0.1)` |
| Rounding surprises | `Math.round(2.5)` vs bankers rounding | Understand `BigDecimal` rounding modes |
| Overflow to Infinity | Large exponent multiplications | Check ranges before scaling |
| Loss in casting | `(float) largeDouble` | Keep in double or scale appropriately |

---
## 12. Comparison Table
| Feature | float | double | BigDecimal |
|---------|-------|--------|------------|
| Bits | 32 | 64 | variable (object) |
| Precision | ~7 digits | ~15–16 digits | Arbitrary (limited by memory) |
| Performance | Fast | Fast (slightly heavier) | Slower |
| Deterministic decimal? | No | No | Yes |
| Use cases | Graphics, large arrays | General numeric work | Finance, exact decimals |
| Memory (per value) | 4 bytes | 8 bytes | Object overhead (≥16 bytes) |

---
## 13. Additional Notes
- Hidden leading 1 gives an extra bit of precision for normalized numbers.
- Rounding errors accumulate linearly or worse depending on algorithm stability.
- Use `StrictMath` for reproducible results across platforms if needed.
- `Float.MIN_VALUE` ≠ smallest negative; it’s the smallest positive subnormal.

---
## 14. Quick Revision Cheat Sheet
- Formula (normal): `(−1)^S * (1 + F) * 2^(E - bias)`
- Float bias = 127, Double bias = 1023
- Exact decimals in binary: denominators power of 2 only (e.g., 1/2, 1/4, 1/8)
- 0.1, 0.2, 0.7 → repeating binary → approximation
- Use `BigDecimal` for money, tax, interest, billing

---
## 15. Practice Exercise Ideas
1. Manually encode 5.75f.
2. Decode the bit pattern: `0 10000001 01100000000000000000000`.
3. Show why `sum += 0.1` loop drifts after 1e7 iterations.
4. Implement Kahan summation vs naive summation.

---
## 16. Suggested Interview One-Liner Answers
- Why floating error? Non‑terminating binary expansion + finite bits.
- Why BigDecimal? Exact decimal arithmetic with controlled scale.
- What is ULP? Distance to next representable value.
- Why bias? Avoid sign bit for exponent; simplifies sorting & hardware.

---
## 17. When NOT to Use BigDecimal
- High-performance numeric loops (tight ML/graphics)
- Trigonometric heavy computations (use double)
- Memory-sensitive large arrays (prefer primitive doubles/floats)

---
## 18. Glossary
- **ULP**: Unit in Last Place
- **Normalization**: Adjusting binary point to 1.x * 2^e
- **Subnormal**: Gradual underflow region
- **NaN**: Not a Number (payload can carry diagnostic bits)
- **Infinity**: Result of overflow or division by zero (non-zero ÷ 0)

---
### End
Focus on: normalization, bias, why decimal ≠ binary exact, and when to switch to BigDecimal.
