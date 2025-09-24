# Java Operators - Quick Reference Cheat Sheet

## 🚀 Quick Lookup Table

| Category | Operators | Example | Result |
|----------|-----------|---------|---------|
| **Arithmetic** | `+ - * / %` | `5 + 3`, `7 % 3` | `8`, `1` |
| **Relational** | `== != < > <= >=` | `5 > 3` | `true` |
| **Logical** | `&& \|\| !` | `true && false` | `false` |
| **Unary** | `++ -- + - ! ~` | `++a`, `!true` | varies, `false` |
| **Assignment** | `= += -= *= /= %=` | `a += 5` | `a = a + 5` |
| **Bitwise** | `& \| ^ ~` | `5 & 3` | `1` |
| **Shift** | `<< >> >>>` | `4 << 1` | `8` |
| **Ternary** | `? :` | `a > b ? a : b` | larger value |
| **Type** | `instanceof` | `obj instanceof String` | boolean |

## ⚡ Bitwise Quick Reference

### Truth Tables
```
AND (&):  OR (|):   XOR (^):  NOT (~):
1&1 = 1   1|0 = 1   1^1 = 0   ~1 = 0
1&0 = 0   1|1 = 1   1^0 = 1   ~0 = 1
0&1 = 0   0|1 = 1   0^1 = 1
0&0 = 0   0|0 = 0   0^0 = 0
```

### Bitwise Tricks
```java
n & 1           // Check if odd (1) or even (0)
n & (n-1)       // Check if power of 2 (result = 0)
n << k          // Multiply by 2^k
n >> k          // Divide by 2^k  
~n              // Equals -(n+1)
a ^ b ^ b       // Equals a (XOR with same number = 0)
```

## 🎯 Operator Precedence (High → Low)
1. `()` `[]` `.` 
2. `++` `--` `!` `~` `+` `-` (unary)
3. `*` `/` `%`
4. `+` `-` (binary)
5. `<<` `>>` `>>>`
6. `<` `<=` `>` `>=` `instanceof`
7. `==` `!=`
8. `&` (bitwise AND)
9. `^` (XOR)
10. `|` (bitwise OR)
11. `&&` (logical AND)
12. `||` (logical OR) 
13. `?:` (ternary)
14. `=` `+=` `-=` `*=` `/=` `%=`

## 📝 Memory Aids

### Direction Memory
- `<<` LEFT arrow = LEFT shift
- `>>` RIGHT arrow = RIGHT shift  
- `>>>` More arrows = unsigned RIGHT

### Pre/Post Fix
- **PREFIX** (`++a`): Action BEFORE return
- **POSTFIX** (`a++`): Action AFTER return

### Logical Shortcuts
- `&&`: Both must be TRUE → if first FALSE, stop
- `||`: Any can be TRUE → if first TRUE, stop

## 🔥 Common Interview Patterns

### Swap Without Temp
```java
a = a ^ b;
b = a ^ b;  // b = (a^b)^b = a
a = a ^ b;  // a = (a^b)^a = b
```

### Find Single Number (others appear twice)
```java
int result = 0;
for(int num : nums) {
    result ^= num;  // Duplicates cancel out
}
return result;
```

### Check Power of 2
```java
return n > 0 && (n & (n-1)) == 0;
```

### Get/Set/Clear Bits
```java
getBit(n, i):    (n >> i) & 1
setBit(n, i):    n | (1 << i)  
clearBit(n, i):  n & ~(1 << i)
```

## ⚠️ Common Pitfalls

### Integer Division
```java
int result = 5 / 2;  // = 2 (not 2.5!)
double result = 5.0 / 2;  // = 2.5 ✓
```

### Precedence Confusion
```java
// WRONG assumption
int x = 5 + 2 * 3;  // Might think = 21
// ACTUAL result = 11 (multiplication first!)

// CLEAR version  
int x = 5 + (2 * 3);  // Obviously = 11
```

### Assignment vs Equality
```java
if (x = 5) // WRONG! Assignment, not comparison
if (x == 5) // Correct comparison
```

### Bitwise vs Logical
```java
if (true & false)  // Bitwise: always evaluates both sides
if (true && false) // Logical: short-circuit, faster
```

## 🎪 Practice Challenges

### Challenge 1: Operator Precedence
```java
int a = 5, b = 2, c = 3;
int result = a + b * c++ / --a;
// Calculate step by step!
```

### Challenge 2: Bit Manipulation
```java
// Using only bitwise operations:
// 1. Multiply by 8
// 2. Check if number is odd
// 3. Swap two variables
// 4. Find missing number in array [1,2,4,5] (missing 3)
```

### Challenge 3: Ternary Chain
```java
// Convert to nested ternary:
String grade = score >= 90 ? "A" : 
               score >= 80 ? "B" :
               score >= 70 ? "C" : "F";
```

## 🚨 Debug Checklist

When operators don't work as expected:
- [ ] Check operator precedence - use parentheses!
- [ ] Verify data types (int vs double division)
- [ ] Watch for overflow in arithmetic operations  
- [ ] Confirm assignment (`=`) vs equality (`==`)
- [ ] Check bitwise vs logical operators
- [ ] Validate short-circuit behavior in logical ops

## 💡 Pro Tips

### Performance
- Use `<<` and `>>` for powers of 2 multiplication/division
- Use `&` instead of `%` for modulo with powers of 2
- Prefer prefix `++i` when return value not needed

### Readability  
- Use parentheses for complex expressions
- Break complex expressions into multiple lines
- Choose ternary only for simple conditions

### Best Practices
```java
// ✓ Good
if ((x > 0) && (y < 10)) { }
int fast = n << 2;  // multiply by 4

// ✗ Avoid  
if (x > 0 && y < 10) { }  // unclear precedence
int slow = n * 4;  // when bit shift possible
```

---
**Remember**: Practice these operators in small programs first, then apply to DSA problems. Bitwise operations are especially powerful for competitive programming! 🏆