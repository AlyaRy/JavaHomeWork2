package hw11;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
/*
Использовал библиотеку Jackson Databind
зависимости в pom.xml прописаны:
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.13.0</version>
    </dependency>
*/
public class PhoneBook {
    private List<Person> phoneBook = new ArrayList<>();
    // добавить пользователя
    public void Add() {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("Введите ФИО: ");
            String fullname = scan.nextLine();
            System.out.println("Введите адрес: ");
            String address = scan.nextLine();
            System.out.println("Сколько у пользователя номеров?");
            int size = scan.nextInt();
            scan.nextLine();
            if (size <= 0)
                throw new IllegalArgumentException();
            String[] phones = new String[size];
            for (int i = 0; i < size; i++) {
                System.out.println("Введите номер:");
                phones[i] = scan.nextLine();
            }
            System.out.println("Поочередно введите день, месяц и год рождения: ");
            int day = scan.nextInt();
            int month = scan.nextInt();
            int year = scan.nextInt();
            phoneBook.add(new Person(fullname, address, phones, LocalDate.of(year, month, day), LocalDateTime.now()));
        } catch (Exception e) {
            System.err.println("Ошибка создания пользователя.");
        }
    }
    // общий вывод
    public String showPhoneBook() {
        return phoneBook.toString();
    }
    // удаление элемента
    public void Remove() {
        Scanner scan = new Scanner(System.in);
        try {
            // алгоритм аналогичен Search()
            System.out.println("Введите ФИО для удаления контакта: ");
            String str = scan.nextLine();
            String[] ph = new String[1];
            Person def = new Person(str, "default", ph,
                    LocalDate.of(1970, 1, 1), LocalDateTime.now());
            for (int i = 0; i < phoneBook.size(); i++) {
                if (phoneBook.get(i).getFullName().equals(def.getFullName()))
                    phoneBook.remove(i);
                System.out.println("Контакт успешно удалён!");
            }
        } catch (Exception e) {
            System.err.println("Ошибка удаления пользователя.");
        }
    }
    // поиск элементов по имени
    public String Search() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите ФИО для поиска: ");
        String str = scan.nextLine();
        String[] p = new String[0];
        // создаём дефолтный объект, куда вписываем str
        // и сравниваем значение str со списком пользователей
        Person def = new Person(str, "default", p,
                LocalDate.of(1970, 1, 1), LocalDateTime.now());
        for (int i = 0; i < phoneBook.size(); i++) {
            if (phoneBook.get(i).getFullName().equals(def.getFullName()))
                return phoneBook.get(i).toString();
        }
        return "Пользователь не найден.";
    }
    // редактирование пользователя
    public void Modify() {
        try {
            // алгоритм аналогичен Search()
            Scanner scan = new Scanner(System.in);
            System.out.println("Введите ФИО пользователя для редактирования: ");
            String[] p = new String[0];
            String str = scan.nextLine();
            Person def = new Person(str, "default", p,
                    LocalDate.of(1970, 1, 1), LocalDateTime.now());
            for (int i = 0; i < phoneBook.size(); i++) {
                if (phoneBook.get(i).getFullName().equals(def.getFullName())){
                    System.out.println("Пользователь найден.");
                    System.out.println(phoneBook.get(i));
                    System.out.println("Для изменения ФИО введите 'name'");
                    System.out.println("Для изменения адреса введите 'address'");
                    System.out.println("Для изменения даты рождения введите 'date'");
                    System.out.println("Для изменения номера введите 'phone'");
                    String choice = scan.nextLine();
                    LocalDateTime changes = LocalDateTime.now();
                    switch (choice) {
                        case "name":
                            System.out.println("Введите новое ФИО: ");
                            String newName = scan.nextLine();
                            phoneBook.get(i).setFullName(newName);
                            phoneBook.get(i).setModified(changes);
                            System.out.println("ФИО успешно изменено!");
                            break;
                        case "address":
                            System.out.println("Введите новый адрес: ");
                            String newAdress = scan.nextLine();
                            phoneBook.get(i).setAddress(newAdress);
                            phoneBook.get(i).setModified(changes);
                            System.out.println("Адрес успешно изменен!");
                            break;
                        case "phone":
                            System.out.println("Сколько номеров у пользователя?");
                            int size = scan.nextInt();
                            for (int n = 0; n < size; n++) {
                                System.out.println("Введите номер: ");
                                scan.nextLine();
                                System.out.println("Номер успешно изменён");
                            }
                            break;
                        case "date":
                            System.out.println("Введите поочередно день, месяц и год рождения: ");
                            int dd = scan.nextInt();
                            int mm = scan.nextInt();
                            int yy = scan.nextInt();
                            phoneBook.get(i).setDateOfBirth(yy, mm, dd);
                            phoneBook.get(i).setModified(changes);
                            System.out.println("Дата рождения успешно изменена!");
                            break;
                        default:
                            System.out.println("Неверный запрос.");
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка редактирования пользователя.");
        }
    }
    // вывод с сортировкой по дате рождения
    public String SortByDateOfBirth() {
        // немного лямбд
        Comparator<Person> cmp = (p1, p2) ->
                p1.getDateOfBirth().compareTo(p2.getDateOfBirth());
        phoneBook.sort(cmp);
        return phoneBook.toString();
    }
    // вывод с сортировкой по ФИО
    public String SortByName() {
        Comparator<Person> cmp = Comparator.comparing(Person::getModified);
        phoneBook.sort(cmp);
        return phoneBook.toString();
    }
    // запись в файл всех данных
    public void toFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            SimpleModule module = new SimpleModule();
            module.addSerializer(LocalDate.class, new LocalDateSerializer());
            module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
            module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
            module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
            mapper.registerModule(module);
            Path file = Files.createFile(Paths.get("phonebook.json"));
            String json = mapper.writeValueAsString(phoneBook);
            try (BufferedWriter wr = Files.newBufferedWriter(file)) {
                wr.write(json);
                System.out.println("Данные успешно экспортированы!");
            }
        } catch (Exception e) {
            System.err.println("Ошибка записи в файл.");
        }
    }
    // чтение из файла всех данных
    public void fromFile() {
        // чтение файла
        try {
            File file = Paths.get("phonebook.json").toFile();
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            SimpleModule module = new SimpleModule();
            module.addSerializer(LocalDate.class, new LocalDateSerializer());
            module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
            module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
            module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
            mapper.registerModule(module);
            List<Person> persons = mapper.readValue(file, new TypeReference<List<Person>>() {});
            phoneBook.addAll(persons);
            System.out.println("Данные успешно импортированы!");
        } catch (Exception e) {
            System.err.println("Ошибка записи файла.");
        }
    }
}