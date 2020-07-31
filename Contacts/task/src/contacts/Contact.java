package contacts;

import java.awt.print.Pageable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Contact implements Serializable {
    String number;
    LocalDateTime timeCreated;
    LocalDateTime lastEdit;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final long serialVersionUID = 1L;

    static List<Contact> contactList = new LinkedList<>();




    public abstract void edit(Scanner scanner);
    public abstract String getFullname();

    static public int getSize() {
        return  contactList.size();
    }

    static public void add(Contact contact) {
        contactList.add(contact);
    }

    public String getNumber() {
        return number;
    }

    public boolean setNumber(String number) {
        lastEdit = LocalDateTime.now();
        if(validateNumber(number)) {
            this.number = number;
            return true;
        }
        else this.number = "";
        return false;
    }


    public Contact(String number) {
        if (validateNumber(number)) this.number = number;
        else this.number = "";
        timeCreated =  LocalDateTime.now();
        lastEdit = LocalDateTime.now();
    }

    public boolean hasNumber() {
        return !number.isEmpty();
    }

    public static boolean validateNumber(String phoneNumber) {
        if (phoneNumber.startsWith("+")) {
            phoneNumber = phoneNumber.substring(1);
        }
        String[] sp = phoneNumber.split("[-\\s]");
        Pattern group = Pattern.compile("[0-9a-zA-Z]+");
        int countOfParentheses = 0;
        for (int i = 0; i < sp.length; i++) {
            String s = sp[i];
            if (s.startsWith("(")) {
                if (i != 0 && i != 1) return false;
                if (s.endsWith(")")) {
                    s = s.substring(1, s.length() - 1);
                    countOfParentheses++;
                }
                if (countOfParentheses == 2) return false;
            }
            if (s.length() < 2) {
                if (i == 0 && s.length() == 1) {}
                else return false;
            }
            Matcher matcher = group.matcher(s);
            if (matcher.matches()) {}
            else return false;

        }
        return true;
    }



    public static void printAll() {
        for (int i = 0; i < Contact.getSize(); i++) {
            Contact contact = Contact.contactList.get(i);
            if (contact instanceof Company) System.out.println((i+1) + ". " +((Company) contact).getOrganizationName());
            else if (contact instanceof Person) System.out.println((i+1) + ". " +((Person) contact).getFullname());
        }
    }
    public static boolean remove(int i) {
        contactList.remove(i);
        return true;
    }
    public static boolean remove(Contact contact) {
        contactList.remove(contact);
        return true;
    }
    public static Contact get(int i) {
        if (contactList.size() < i+1) return null;
        return contactList.get(i);

    }

    public static List<Contact> search(String reg) {
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        List<Contact> result = new LinkedList<>();
        for (Contact contact : contactList) {
            Matcher matcher = pattern.matcher(contact.toString());
            if (matcher.find()) {
                result.add(contact);
            }
        }
        for (int i = 0; i < result.size(); i++) {
            System.out.println((i+1) + ". " + result.get(i).getFullname());
        }
        return result;
    }


}
