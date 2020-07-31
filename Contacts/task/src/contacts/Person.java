package contacts;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Person extends Contact{
    String name;
    String surname;
    String birthDate;
    String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        lastEdit = LocalDateTime.now();

    }
    @Override
    public String getFullname() {
        return name + " " + surname;
    }
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
        lastEdit = LocalDateTime.now();

    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        lastEdit = LocalDateTime.now();

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        lastEdit = LocalDateTime.now();
    }

    public Person(String number, String name, String surname, String birthDate, String gender) {
        super(number);
        this.name = name;
        this.surname = surname;
        this.birthDate = validateBirthDate(birthDate)? birthDate : "[no data]";
        this.gender = validateGender(gender)? gender : "[no data]";
    }

    public static boolean validateGender(String gender) {
        if (gender.equals("M") || gender.equals("F")) return true;
        return false;
    }

    public static boolean validateBirthDate(String birthDate) {
        return !birthDate.isBlank();
    }

    @Override
    public String toString() {
        return
                "Name: " + name  + '\n' +
                "Surname: " + surname + '\n' +
                "Birth date: " + birthDate + '\n' +
                "Gender: " + gender + '\n' +
                "Number: " + number + '\n' +
                "Time created: " + timeCreated.format(formatter) + '\n' +
                "Time last edit: " + lastEdit.format(formatter) ;
    }

    @Override
    public void edit(Scanner scanner) {
        System.out.print("Select a field (name, surname, birth, gender, number): > ");
        String area = scanner.nextLine();
        System.out.print("Enter " + area +" : > ");
        String update = scanner.nextLine();
        switch (area) {
            case "name":
                this.setName(update);
                break;
            case "surname":
                this.setSurname(update);
                break;
            case "number":
                if (Person.validateNumber(update)) this.setNumber(update);
                else System.out.println("Wrong number format!");
                break;
            case "birth":
                if (Person.validateBirthDate(update)) this.setBirthDate(update);
                else System.out.println("Bad birth date");
                break;
            case "gender":
                if (Person.validateGender(update)) this.setGender(update);
                else System.out.println("Bad gender");
                break;
            default:
        }
    }

    public static void add(Scanner scanner) {
        System.out.print("Enter the name: > ");
        String name = scanner.nextLine();
        System.out.print("Enter the surname: > ");
        String surname = scanner.nextLine();
        System.out.print("Enter the birth date: > ");
        String birthDate = scanner.nextLine();
        if (!Person.validateBirthDate(birthDate)) System.out.println("Bad birth date!");
        System.out.print("Enter the gender (M, F): > ");
        String gender = scanner.nextLine();
        if (!Person.validateGender(gender)) System.out.println("Bad gender");
        System.out.print("Enter the number: > ");
        String number = scanner.nextLine();
        Person person = new Person(number, name, surname, birthDate, gender);
        if (!person.hasNumber()) System.out.println("Wrong number format!");

        Contact.add(person);
        System.out.println("The record added.");
    }
}
