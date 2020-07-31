package contacts;

import java.util.Scanner;

public class Company extends Contact{
    String organizationName;
    String address;

    public Company(String number, String organizationName, String address) {
        super(number);
        this.organizationName = organizationName;
        this.address = address;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return
                "Organization name: " + organizationName + '\n' +
                "Address: " + address + '\n' +
                "Number: " + number + '\n' +
                "Time created: " + timeCreated.format(formatter)  + '\n' +
                "Time last edit: " + lastEdit.format(formatter)  ;
    }

    @Override
    public void edit(Scanner scanner) {
        System.out.print("Select a field (organizationName, address, number): > ");
        String area = scanner.nextLine();
        System.out.print("Enter " + area +" : > ");
        String update = scanner.nextLine();
        switch (area) {
            case "number":
                if (Company.validateNumber(update)) this.setNumber(update);
                else System.out.println("Wrong number format!");
                break;
            case "organizationName":
                this.setOrganizationName(update);
                break;
            case "address":
                this.setAddress(update);
                break;
            default:
        }
    }

    @Override
    public String getFullname() {
        return getOrganizationName();
    }

    public static void add(Scanner scanner) {
        System.out.print("Enter the organization name: > ");
        String organizationName = scanner.nextLine();
        System.out.print("Enter the address: > ");
        String address = scanner.nextLine();
        System.out.print("Enter the number: > ");
        String number = scanner.nextLine();
        if (!Company.validateNumber(number)) System.out.println("Wrong number format!");

        Company company = new Company(number, organizationName, address);

        Contact.add(company);
        System.out.println("The record added.");
    }
}
