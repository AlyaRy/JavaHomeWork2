package HW12;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scan = new Scanner(System.in);
        System.out.println("Добро пожаловать в телефонный справочник!");
        System.out.println("Для поиска введите 'search'");
        System.out.println("Для добавления контакта введите 'add'");
        System.out.println("Для удаления контакта введите 'remove'");
        System.out.println("Для отображения списка контактов введите 'show'");
        System.out.println("Для сортировки по ФИО введите 'names'");
        System.out.println("Для сортировке по дате рождения введите 'dates'");
        System.out.println("Для редактирования пользователя введите 'modify");
        System.out.println("Для вывода книги в JSON введите 'output'");
        System.out.println("Для запись в книгу из JSON введите 'input'");
        System.out.println("Для выхода введите 'quit'");
        String input = scan.nextLine();
        boolean run = true;
        while (run) {
            switch (input) {
                case "add":
                    phoneBook.Add();
                    System.out.println("Введите новый запрос: ");
                    input = scan.nextLine();
                    break;
                case "search":
                    phoneBook.Search();
                    System.out.println("Введите новый запрос: ");
                    input = scan.nextLine();
                    break;
                case "remove":
                    phoneBook.Remove();
                    System.out.println("Введите новый запрос: ");
                    input = scan.nextLine();
                    break;
                case "modify":
                    phoneBook.Modify();
                    System.out.println("Введите новый запрос: ");
                    input = scan.nextLine();
                    break;
                case "show":
                    System.out.println(phoneBook.showPhoneBook());
                    System.out.println("Введите новый запрос: ");
                    input = scan.nextLine();
                    break;
                case "nameshow":
                    System.out.println(phoneBook.SortByName());
                    System.out.println("Введите новый запрос: ");
                    input = scan.nextLine();
                    break;
                case "dateshow":
                    System.out.println(phoneBook.SortByDateOfBirth());
                    System.out.println("Введите новый запрос: ");
                    input = scan.nextLine();
                    break;
                case "output":
                    phoneBook.toFile();
                    System.out.println("Введите новый запрос: ");
                    input = scan.nextLine();
                    break;
                case "input":
                    phoneBook.fromFile();
                    input = scan.nextLine();
                    break;
                case "quit":
                    run = false;
                    System.out.println("До встречи!");
                    break;
                default:
                    System.out.println("Неверный запрос. Попробуйте снова.");
                    input = scan.nextLine();
                    break;
            }
        }
    }
}