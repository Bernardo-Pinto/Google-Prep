package DataStructures.Week6;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Library{

    private List<LibraryItem> items;
    private List<Employee> employees;
    private List<Client> clients;

    public Library(){
        this.items = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.clients = new ArrayList<>();
    }
    
    public boolean addItem(LibraryItem item){
        if(item == null) throw new IllegalArgumentException();
        if (items.contains(item)) return false;
        else return this.items.add(item);
    }

    public boolean addEmployee(Employee employee){
        if(employee == null)  throw new IllegalArgumentException();
        if(this.employees.contains(employee)) return false;
        else return this.employees.add(employee);
    }

    public boolean addClient(Client client){
        if(client == null) throw new IllegalArgumentException();
        if(clients.contains(client)) return false;
        else return clients.add(client);
    }

    //checkout means using IN THE LIBRARY
    public boolean checkoutItem(UUID itemId, UUID clientId){
        if(itemId == null || clientId == null) throw new IllegalArgumentException();
        Client client = this.clients.stream().filter((c)->c.getId().equals(clientId)).findFirst().orElse(null);
        if(client == null) return false;
        LibraryItem item = this.items.stream().filter((i)->i.getId().equals(itemId)).findFirst().orElse(null);
        if(item == null) return false; 
        //assign client to item
        if(item.isAvailable()){
            item.setClientUsingThis(clientId);
            return true;
        }
        return false;
    }

    public boolean checkInItem(UUID itemId){
        if(itemId == null) throw new IllegalArgumentException();
        LibraryItem item = this.items.stream().filter((i)->i.getId().equals(itemId)).findFirst().orElse(null);
        if(item == null) return false; 

        item.setClientUsingThis(null);
        return true;
    }
    
    // lending an item means the client will take it with them outside the library
    public boolean lendItem(UUID itemId, UUID clientId){
        if(itemId == null || clientId == null) throw new IllegalArgumentException();
        Client client = this.clients.stream().filter((c)->c.getId().equals(clientId)).findFirst().orElse(null);
        if(client == null) return false;
        LibraryItem item = this.items.stream().filter((i)->i.getId().equals(itemId)).findFirst().orElse(null);
        if(item == null || !(item instanceof LendableLibraryItem)) return false; 

        LendableLibraryItem lendableItem = (LendableLibraryItem) item; 
        if(!lendableItem.lendToClient(client.getId(),client.getLendingTime())) return false;
        return client.borrowItem(lendableItem.getId());
    }

    public boolean returnItem(UUID itemId, UUID clientId){
        if(itemId == null || clientId == null) throw new IllegalArgumentException();
        Client client = this.clients.stream().filter((c)->c.getId().equals(clientId)).findFirst().orElse(null);
        if(client == null) return false;
        LibraryItem item = this.items.stream().filter((i)->i.getId().equals(itemId)).findFirst().orElse(null);
        if(item == null || !(item instanceof LendableLibraryItem)) return false; 

        LendableLibraryItem lendableItem = (LendableLibraryItem) item; 
        lendableItem.checkedIn();
        client.returnItem(itemId);
        return true;
    }

    public LibraryItem findItemById(UUID id){
        if(id == null) throw new IllegalArgumentException();
        return this.items.stream().filter(i->i.getId().equals(id)).findFirst().orElseGet(null);
    }

    public List<LibraryItem> getAvailableItems(){
        return this.items.stream().filter(i->i.isAvailable()).collect(Collectors.toList());
    }

}