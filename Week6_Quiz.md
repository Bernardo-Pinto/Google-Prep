# Week 6 Quiz — Testing, Code Quality & OOD/OOP

Fill in your answers, then ask for a review.

---

## 1. Testing Fundamentals

**Q1. What categories of inputs should you always check when designing test cases?**

A:
edge cases:
    -empty
    -single
    -null
    -max
    -min
    -overflow
happy case
corner cases

correction:
also add: duplicates, negatives, very large input
---

**Q2. What is the difference between an edge case and a corner case?**

A:
edge cases are when a single variable is pushed to an extreme value.
corner cases are cases where the conbimnation of variables produces an unlkely or hard to thoerize case where the result is not obvious

---

**Q3. You write a function `divide(int a, int b)`. List the minimal set of test cases that fully verify it.**

A:
a == 0, result is 0 for any b != 0
b == 0, an exception is thrown or result is undefined(depending on choice of implementation)
a != 0, b !=  0, result * b == a
a < 0, b < 0, result is positive
a > 0, b < 0, result is negative
a < 0, b > 0, result is negative
a > 0, b > 0, result is positive
---

**Q4. What is the difference between a unit test and an integration test?**

A:A unit tests tests a method inside the own application, even if it uses other methods of the application.
Integration tests integrate other systems in the tests 

Correction: Unit tests are run in ISOLATION, with dependencies mocked. Moving away from mocked dependencies already makes them integration tests
---

## 2. JUnit 5

**Q5. What is the purpose of `@BeforeEach`? When would you use `@BeforeAll` instead?**

A:BeforeEach runs the code inside its method before every single test is called.
I would use BeforeAll if i needed to initialize expensive objects that would be used throughout the tests. If the lifecycle is per method, they would need to be static

---

**Q6. What does `assertAll` do differently from multiple plain `assertEquals` calls? When is it worth using?**

A: assertAll resolves all the assertions inside its code block instead of stopping when ana assertion fails. Its worth using when there are multiple properties that must be assessed we want to see which failed and which passed.

---

**Q7. You have a method that produces one of several valid outputs (e.g. topological sort). How do you test it?**

A: Test the properties that the solution must obey

---

**Q8. What is `@ParameterizedTest` and why is it useful? Name two source annotations you can use with it.**

A: ParameterizedTest indicates a set of tests will be used for this test, which makes sense to use when the test is testing the same properties but for a different set of inputs.
To supply the tests, @CsvSource or @MethodSource can be used for example

---

## 3. OOD / OOP

**Q9. When should you use an abstract class instead of an interface? When should you use an interface instead of an abstract class?**

A:an abstract class for when there is shared fields and methods that other classes will use, and they share an "is-a" relationship between them. Abstract in case it doesnt make sense for that class to be able to be instanciated. For example, a LibraryItem class should be abstract, as there is no such thing in the real world. But a book for example is a LibraryItem. In case the class "can-Do" do something, an interface is preffered. For example, a Movie is a libraryItem, and it can be Played, while the Book cannot be played. So Movie would implement a Playable interface

---

**Q10. In your library system, `Library` operates on `LibraryItem` references even though the list contains `Book` and `Movie` objects. What OOP concept is this, and what does it buy you?**

A: polymorphism. It buys me the option to reuse code, leaving it up to the runtime to figure out the exact type of the object. It also allows me to add a new type (Magazine or TabletopGame for example) wihtout having to change the Library class.

---

**Q11. `LendableLibraryItem` adds lending behaviour by extending `LibraryItem`. Describe an alternative design using composition. What would change?**

A: By using a Lendable interface, but in this case it doesnt make sense because state is saved inside LendableLibraryItem, and a book IS A LendableLibraryItem. Yes it would also be correct to that that Book IS A LibraryItem that can be lent, but then that state that would otherwise be inside LendableLibraryItem would need to either be in each lendable item, or in the LibraryItem base class, which would be unused by non lendable items.

Correction:
Composition describes an "Has-a" relationship, not an interface. Using composition, LibraryItem would have a field with an object that would hold the fields of "LendableLibraryItem", such as a "LendingInfo", which would be null for non lendable objects.
---

**Q12. What is the Liskov Substitution Principle? Does your `LendableLibraryItem` satisfy it? Justify.**

A: 
LSP says that can you subsitute a subtype for any of its parent types and vice versa without altering the correctness of the code. In this case, LendableLibraryItem is abstract, so it is not possible to instantiate it.
Correction: 
1- LSP only goes one way: a subtype can replace its parent.
2- LendableLibraryItem satisfies LSP because it extends the methods LibraryItem has instead of contradicting them. Anywhere LibraryItem is expected, a LendableLibraryItem works correctly.
---

## 4. API Design

**Q13. What are three properties of a well-designed API?**

A:
1- It is clear about its functioning
2- Good defaults
3- It should expose the bare minimum

---

**Q14. Why is `checkout(UUID itemId, UUID clientId)` a better signature than `checkout(boolean isLending, UUID id1, UUID id2)`?**

A:
1- the variables are better named for the item and client, making it clear whose UUIds we are passsing.
2- checkout should handle only the responsability of checking out, not checking out and lending (naming is irrelevant here, the issue is that the second signature indicates the method will have more than 1 responsability)
3- isLending will be passed as true or false, which can be confusing in the long term

---

**Q15. Your `getLendingTime()` in `Client` returns `-1` for an unknown tier instead of throwing. Is that a good API decision? Argue both sides.**

A: 
It is good in the sense that in the case that something went wrong, there is a clear issue where the lending time is set to a negative number, and on top of that, it is specifically -1, which points to a intended error value
It could be improved by throwing an exception and not forcing the user of that method to check if the return value is valid.
