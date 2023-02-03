import com.sun.xml.internal.ws.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static String throwData(String data){
        try {
            data += " 00";
            LocalDateTime datetime = LocalDateTime.parse(data, DateTimeFormatter.ofPattern("dd.MM.yyyy HH"));
            return datetime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
            catch (DateTimeParseException e){
            return null;
            }
    }
    
    public static String readText(String str){
        System.out.printf(str);
        return String.valueOf(new Scanner(System.in).nextLine());
    }

    public static boolean isName(String name){
        for (int index = 0; index < name.length(); index++) {
            if (!Character.isLetter(name.charAt(index)))
                return false;
        }
        return true;
    }

    public static boolean isNumber(String number){
        if (number.length() != 11)
            return false;
        for (int index = 0; index < number.length(); index++) {
            if (!Character.isDigit(number.charAt(index)))
                return false;
        }
        return true;
    }

    public static String title(String str){
        str = str.toLowerCase();
        return StringUtils.capitalize(str);
    }

    public static void writeFile(String nameFile, String output){
        nameFile += ".txt";
        try {
            Files.createDirectories(Paths.get("dataBase"));
            File file = new File("dataBase", nameFile);
            boolean exists = file.exists();
            if(exists){
                List<String> list = Files.readAllLines(file.toPath());
                list.add(output);
                PrintWriter writer = new PrintWriter(file);
                for (String str: list) {
                    writer.write(str + "\n");
                }
                writer.close();
            }
            else {
                PrintWriter writer = new PrintWriter(file);
                writer.write(output);
                writer.close();
            }
            System.out.println("Запись добавлена...");

        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }

    public static void main(String[] args){
        String name, lastName = "", patronymic, output = "", data = "";
        long numberPhone = 0;
        char pol = 'm';
        boolean propriety[] = {false, false, false, false}, flag = true;
        String condition[] = {"Фамилия Имя Отчество", "Дата рождения",
                "Номер телефона", "Пол (m или f)", "Сохранение данных"};



        while (flag) {
            for (int i = 0; i < condition.length; i++)
                System.out.println((i+1) + " - " + condition[i]);
            String parameter = String.valueOf(new Scanner(System.in).nextLine());
            switch (parameter) {
                case "1":
                    lastName = readText("Фамилия:  ");
                    name = readText("Имя:\t  ");
                    patronymic = readText("Отчество: ");
                    if (isName(name) && isName(lastName) && isName(patronymic)){
                        name = title(name);
                        lastName = title(lastName);
                        patronymic = title(patronymic);
                        output = "<" + lastName + "><" + name + "><" + patronymic + "><";
                        propriety[0] = true;
                        System.out.println("Принято!\n");
                    }
                    else {
                        System.out.println("ФИО не может содержать ничего кроме букв!\n");
                    }
                    break;
                case "2":
                    String happy = readText("Дата рождения: ");
                    if (throwData(happy) != null){
                        data = happy;
                        propriety[1] = true;
                        System.out.println("Принято!\n");
                    }
                    else {
                        System.out.println("Неверный формат даты (dd.MM.yyyy)\n");
                    }
                    break;
                case "3":
                    String number = readText("Номер телефона: ");
                    if (isNumber(number)){
                        numberPhone = Long.valueOf(number);
                        propriety[2] = true;
                        System.out.println("Принято!\n");
                    }
                    else {
                        System.out.println("Неверный формат номера телефона " +
                                "(целое беззнаковое число, например: 79991234567)\n");
                    }
                    break;
                case "4":
                    System.out.print("Пол: ");
                    char ppol = new Scanner(System.in).next().charAt(0);
                    if (ppol == 'm' || ppol == 'f'){
                        pol = ppol;
                        propriety[3] = true;
                        System.out.println("Принято!\n");
                    }
                    else {
                        System.out.println("Невозможно определить пол!\n");
                    }
                    break;
                case "5":
                    int i;
                    for (i = 0; i < propriety.length; i++) {
                        if (!propriety[i]){
                            System.out.println("Вы ввели не все данные!");
                            break;
                        }
                    }
                    if (i == 4){
                        output += data + "><" + numberPhone + "><" + pol + ">";
                        writeFile(lastName, output);
                        flag = false;
                    }
                    else {
                        for (i = 0; i < propriety.length; i++) {
                            if (!propriety[i])
                                System.out.println("Вы не ввели - " + condition[i]);
                        }
                        System.out.println();
                    }

            }
        }
    }
}