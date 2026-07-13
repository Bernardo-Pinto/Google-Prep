package DataStructures.Week6;

import java.util.UUID;

public class Employee {
    private UUID id;

    public Employee(){
        this.id = UUID.randomUUID();
    }
    public UUID getId(){
        return this.id;
    }
}
