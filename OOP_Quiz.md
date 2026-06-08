# OOD/OOP Quiz

Fill in your answers, then ask for a review.

---

## 1. Core Principles

**Q1. Name the four core OOP principles and give a one-sentence definition of each.**

A:
Encapsulation: Bundles attributes and methods into a single entity, normally called a class. Allows control and visibility of the attributes and methods of the object, and how it interacts with other objects.
Abstraction: Hides the implementation details of an entity, allowing the user to focus on what it does, rather than how it does it.
Inheritance: Object can inherit attributes, contracts and methods of other objects. It defines a "Is-A" contract between objects, and it allows re-use of code and establish hierarchies.
Polymorhism: It allows objects to be treated as objects that they inherit from. 

Worth mentionning: Composition: Complex objects are built by having multiple simpler objects. It creates a "Has-A" contract between the objects.

---

**Q2. Give a concrete code example (in any language) that demonstrates encapsulation and explain why it matters.**

A:
class UserBankAccount{
    ...
    private double money = 10.5;

    public void withdrawMoney(double amount){
        if (money - amount < 0.0){
            System.out.println("not enough money");
        }
    }
}


For example, if a user wanted to withdraw money, they can only do so by using the withdrawMoney method, since the money attribute is private. This is important to prevent any operation that is not allowed on the money field/attribute.

---

**Q3. What is the difference between method overriding and method overloading? Give an example of each.**

A:
Method overriding: A class that extends another, that has a method implemented, can override the method implemented by its parent class.
class A{
    method A1(){}
}
class B extends A{
    @override
    method A1(){System.out.println("override")}
}
Method overloading: A method with the same name but different parameters can exist.
class A{
    method A1(){}
    method A1(String s){}
    method A1(int i){}
}

---

**Q4. What is runtime polymorphism? How does Java achieve it?**

A: A class of a base type can be treated the same as the base type. I don't know how Java achieves it. 

AI correction:
Runtime polymorphism in Java works through dynamic dispatch: the JVM maintains a virtual method table (vtable) per class. When you call a method on a base-type reference, the JVM looks up the actual object's type at runtime and calls the correct overridden method. All non-static, non-private, non-final methods in Java are virtual by default.

---

## 2. Inheritance vs Composition

**Q5. What does "favour composition over inheritance" mean? Why is it generally recommended?**

A: It means to prefer having a class that aggregates others, establisghing a "Has-A" contract, instead of having a complex hierarchy system that establishes a "Is-A" contract over multiple levels. It is reccomended because using a lot of inheritance will result in tightly coupled code, which is also complex and hard to maintain/extend

---

**Q6. Give a scenario where inheritance is the right choice, and a different scenario where composition is the right choice. Justify each.**

A:I can't think of an example where both approaches would work. Usually when inherictance is used its because it makes sense to do so, i don't a situation where i could use either and it would still work.
AI Answer: For example, a Logger that writes to a file. The Logger could extend FileWriter (Logger is-a FileWriter), but composition is better: A Logger has-a Writer, which can be swapped for any Writer (a database or console writer for example), without changing the Logger. Rule of thumb: If subsituting the parent for the child in any context makes sense, use inheritance. If the relationship is about using something, use composition
---

**Q7. What problem does the "diamond problem" refer to? How does Java handle it?**

A: i don't know what that problem is
AI answer: If D extends both B and C, and both B and C extend A and override a method from A, callin the method from D is ambiguous. So Java disallows inheriting from multiple classes. The same problem appears with default methods on interfaces (Java 8+), so Java enforces that the class overrides the method to resolve it. 
---

## 3. Interfaces vs Abstract Classes

**Q8. What are the key differences between an interface and an abstract class in Java?**

A: An interface does not implement any methods, and is seen as establishing a contract that classes implementing them must adhere to.
An abstract class can have methods implemented, and other classes can extend it and make use of it.

AI correction: "An interface does not implement any methods" is outdated. Since Java 8, interfaces can have default and static methods with implementations. Other missing differences:

A class can implement multiple interfaces, but extend only one abstract class
Abstract classes can have constructors and instance fields; interfaces cannot
Interface methods are public by default

---

**Q9. When would you choose an abstract class over an interface, and vice versa? Give a real-world example for each.**

A: I would choose an abstract class if i want to establish an "Is-A" contract, and if i want some base behaviour to exist. For example, both Dog and Cat are animals, and all animals must drink water. I could have a base behaviour for drinking water, that is shared between them. I would prefer an interface if I didn't have any base behaviour, and wanted a way to define "this class can do this X things defined in this interface". This is especially useful because a class can implement various interfaces in Java, but can only extend 1 class. For example, a Robot and a Human can both walk, but only the Human needs to sleep and eat. So I could define 3 interfaces for those actions, and the Human would implement all 3, while the Robot implements only the walk interface.

---

**Q10. Can a class implement multiple interfaces? Can it extend multiple abstract classes? Why or why not?**

A: Answered above.
AI correction: You can't extend multiple abstract classes because of the diamond problem (see Q7). You can implement multiple interfaces because traditionally there's no ambiguity — since Java 8, if two interfaces have the same default method, the implementing class must override it to resolve the conflict.


---

## 4. SOLID Principles

**Q11. What does each letter in SOLID stand for? Give a one-sentence description of each.**

A:
Single responsibility: A class should not have to change for more than a single reason. In other words, a class should have a single responsiblity.
Open closed: open for extension, closed for modification
Liskov substitution: A base class should be able to be subsituted by a class that implements the base class and vice-versa without breaking the code.
Interface segregation: A class that implements an interface should not have to implement useless methods. (should not depend on methods they do not use). Prefer multiple smaller interfaces.
Dependency Inversion:It says that high level modules and low level modules should not depend on each other, but rather on abstractions like an interface. 

---

**Q12. Give an example of a class that violates the Single Responsibility Principle, then show how you would fix it.**

A: A class that handles the user choice for payment, and also processes all payment types logic. In this case, the class should only be responsible for processing the user's choice, and passing the responsability of the payment logic to a class responsible for it. This could be done with the Strategy pattern.

---

**Q13. What is the Open/Closed Principle? Give a concrete example of applying it.**

A: Code should be open for extension but not for modification. Using the previous example, if a new payment method were to be introduced, a new interface and class should be created to handle the new payment, while keeping the rest of the code untouched and working.

---

**Q14. Explain the Liskov Substitution Principle. Give an example of a violation and explain why it is a problem.**

A: A base class should be able to be subsituted by a class that implements the base class and vice-versa without breaking the code. A class Rectangle with a method to set height and width and a method to return the width. Then, a class Square which extends Rectangle, where the method to set the height and width is overriden, and in this case, sets the height and the width the same value (height). If the code was using the rectangle's method that sets height and width with (2,4) for example, and after ask it's width. If we then substitute it with a square, when we ask it's width, it will return the height value. This is wrong.

---

**Q15. What is the Dependency Inversion Principle? How does it relate to dependency injection?**

A:It says that high level modules and low level modules should not depend on each other, but rather on abstractions like an interface. It relates to dependency injection in the sense that the responsibility for passing objects that the classes need is passed in the reverse direction that is usually used (at least when this principle was created)
AI correction: The DIP link to DI is: instead of a class creating its dependencies internally (new EmailService() inside NotificationService), the concrete implementation is injected from outside — usually via constructor. The class only knows about the abstraction (interface), not the concrete type. Your Q16 answer actually demonstrates this perfectly without naming it.
---

## 5. Design in Practice

**Q16. You are designing a notification system that can send emails, SMS, and push notifications. A new channel may be added in the future. Sketch the class/interface design and explain your choices.**

A:
interface INotificationChannel{
    public void send(String text);
}

class NotificationService{
    List<INotificationChannel> channels;

    public void sendNotification(String text){
        for(INotificationChannel channel : channels){
            channel.send(text);
        }
    }
}

So in this example, the NotificationService could have the channels the user is subscribed to, and when a notification is ready to be sent, it goes through all channels the user is subscribed to and sends a notification using the interface "channel.send()". The "channels" list would have classes that implement the INotificationChannel interface. When a new notification method needs to be added, a new class just needs to be created and implenent that interface.


---

**Q17. What is a "God class"? Why is it a problem and how do you fix it?**

A: Its a class that does way too many things. Its a problem becuase the code will be tightly coupled, complex and hard to maintain/extend. To fix it, it would need to be broken up into more components while keeping functionality the same.

---

**Q18. What makes a good API? List at least four properties and briefly explain each.**

A: I don't know. Maybe it handles all edge cases, gives clear error messages, is fast and it is clear? 
AI answer:
- Clear and intuitive naming
- Minimal surface area: expose only what is necessary
- Consistent conventions: uniform naming, error handling and return types
- Good defaults: works out of the box for the common case, configurable for edge cases. For example HttpClient.get() has good defaults, but you can use HttpClient.Builder() to configure fields like timeout, retries, etc. Or in Java, the ArrayList can be constructed with 0 arguments, but it gives you the option to create it with arguments.
- Single responsibility
- Stable/backwards compatible: consumers shouldn't break when you update internals

---

## 6. Practical OOP Reasoning

**Q19. You have a `Bird` base class with a `fly()` method. You now need to add `Penguin` which is a bird but cannot fly. What is wrong with this design and how would you fix it?**

A:Either have another class called FlyingBird that extends Bird, and as many SomethingBird classes as types of Birds exist and need to be differentiated. But this would have a very high amount of classes and possibly lots of hierarchy levels and weird coupling. Instead, I could create a IFlyable or IFlies interface, and birds who can fly implement that interface.

---

**Q20. What is the difference between coupling and cohesion? What is the desired state for each in a well-designed system?**

A: I'd say cohesion is when elements/objects who are very related to eachother to fulfill a purpose exist together, and coupling is when an object depends on another to fulfull a task, but they are not really very well related to eachother
AI Correction: Cohesion is correct. Coupling is not about being "unrelated" — it's the degree of interdependence between modules. Two very related classes can still be tightly coupled (bad). Tight coupling means a change in one class forces changes in another.

Desired: high cohesion (each class is focused on one clear purpose), low coupling (classes depend on each other as little as possible, ideally through abstractions).

