package contacts;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    static String status = "menu";
    static String file = null;
    static Contact contact = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (args.length == 0) file = null;
        else file = args[0];

        read();
        while (true) {
            System.out.print("[" + status + "]");
            switch (status) {
                case "menu":
                    menu(scanner);
                    break;
                case "search":
                    searchLoop(scanner);
                    break;
                case "record":
                    modify(scanner,contact);
                    save();
                    break;
                case "list":
                    info(scanner);
                    break;
                case "exit":
                    return;
                default:
            }
        }
    }







    private static void menu(Scanner scanner) {
        System.out.print(" Enter action (add, list, search, count, exit): > ");
        String action = scanner.nextLine();
        switch (action) {
            case "add":
                status = "record";
                add(scanner);
                break;
            case "count":
                count();
                break;
            case "list":
                status = "list";
                info(scanner);
                break;
            case "exit":
                status = "exit";
                break;
            case "search":
                status = "search";
                searchLoop(scanner);
                break;
            default:
        }
        System.out.println();
    }



    private static void read() {
        if (file != null) {
            try {
                File f = new File(file);
                if (f.exists()) Contact.contactList = (List<Contact>) SerializationUtils.deserialize(file);
                else f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }





    private static void save(){
        if (file != null) {
            try {
                SerializationUtils.serialize(Contact.contactList, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    private static void add(Scanner scanner) {
        System.out.print("Enter the type (person, organization): > ");
        String type = scanner.nextLine();
        switch (type) {
            case "person" :
                Person.add(scanner);
                break;
            case "organization" :
                Company.add(scanner);
                break;
            default:
        }
        status = "menu";
    }
    private static void searchLoop(Scanner scanner) {
        List<Contact> result = search(scanner);
        System.out.print("[search] Enter action ([number], back, again): > ");
        String cmd = scanner.nextLine();
        switch (cmd) {
            case "back":
                status = "menu";
                break;
            case "again":
                //result = search(scanner);
                break;
            default: {
                int index = Integer.parseInt(cmd)-1;
                Contact contact = result.get(index);
                System.out.println(contact.toString());
                modify(scanner, contact);
                break;
            }

        }
        System.out.println();

    }

    private static void modify(Scanner scanner, Contact contact) {

        System.out.print("[record] Enter action (edit, delete, menu): > ");
        String cmd = scanner.nextLine();
        switch (cmd) {
            case "edit":
                edit(scanner, contact);
                status = "record";
                break;
            case "delete":
                remove(scanner, contact);
                status = "menu";
                break;
            case "menu":
                status = "menu";
                break;
            default:
        }
    }


    private static List<Contact> search(Scanner scanner) {
        System.out.print("Enter search query: > ");
        String keyword = scanner.nextLine();
        return Contact.search(keyword);
    }
    private static void list(){
        Contact.printAll();

    }

    private static void count() {
        System.out.println("The Phone Book has " +Contact.getSize() + " records.");
    }


    private static void info(Scanner scanner) {
        Contact.printAll();
        System.out.println();
        System.out.print("[list] Enter action ([number], back): > ");
        String cmd = scanner.nextLine();
        switch (cmd) {
            case "back" :
                status = "menu";
                break;
            default:{
                int index = Integer.parseInt(cmd) - 1;
                Contact contact = Contact.contactList.get(index);
                System.out.println(contact.toString());
                System.out.println();
                modify(scanner, contact);
                break;
            }
        }
        System.out.println();

    }

    private static void edit(Scanner scanner, Contact contact) {
        contact.edit(scanner);
        System.out.println("Saved");
        System.out.println(contact.toString());
    }

    private static void remove(Scanner scanner, Contact contact) {

        Contact.remove(contact);
        System.out.println("The record removed!");
    }
}
