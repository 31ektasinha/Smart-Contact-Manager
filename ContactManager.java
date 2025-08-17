import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private static final String FILE_NAME = "contacts.dat";
    private List<Contact> contacts;

    public ContactManager() {
        contacts = loadContacts();
    }

    // Add new contact
    public void addContact(Contact contact) {
        contacts.add(contact);
        saveContacts();
    }

    // View all contacts
    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            contacts.forEach(System.out::println);
        }
    }

    // Search contact by name
    public void searchContact(String name) {
        boolean found = false;
        for (Contact c : contacts) {
            if (c.getName().equalsIgnoreCase(name)) {
                System.out.println("Found: " + c);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No contact found with name: " + name);
        }
    }

    // Delete contact by name
    public void deleteContact(String name) {
        contacts.removeIf(c -> c.getName().equalsIgnoreCase(name));
        saveContacts();
        System.out.println("Deleted contact (if existed) with name: " + name);
    }

    // Save contacts to file
    private void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load contacts from file
    @SuppressWarnings("unchecked")
    private List<Contact> loadContacts() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Contact>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
