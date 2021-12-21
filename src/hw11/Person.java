package hw11;

import java.io.Serializable;
import java.time.*;
import java.util.Arrays;

public class Person implements Serializable {
    private String fullName, address;
    private String[] phones;
    private transient LocalDate dateOfBirth;
    private transient LocalDateTime modified;

    public Person(String fullName, String address, String[] phones,
                  LocalDate dateOfBirth, LocalDateTime modified) {
        this.fullName = fullName;
        this.address = address;
        this.phones = phones;
        this.dateOfBirth = dateOfBirth;
        this.modified = modified;
    }
    // дефолтный конструктор, без которого сериализация не работала
    public Person() {

    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setPhones(String[] phones) {
        this.phones = phones;
    }
    public void setDateOfBirth(int y, int m, int d) {
        this.dateOfBirth = LocalDate.of(y, m, d);
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String[] getPhones() {
        return phones;
    }

    public String getDateOfBirth() {
        return dateOfBirth.toString();
    }

    public String getModified() {
        return modified.toString();
    }

    @Override
    public String toString() {
        return "\n{ФИО: " + fullName + "\n" +
                "Адрес: " + address + "\n" +
                "Дата рождения: " + dateOfBirth + "\n" +
                "Телефоны: " + Arrays.toString(phones) + "\n" +
                "Последнее изменение: " + modified + "}";
    }
}