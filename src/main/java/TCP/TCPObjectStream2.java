package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Customer implements Serializable {
    private static final long serialVersionUID = 20170711L;

    private int id;
    private String code, name, dayOfBirth, userName;

    public Customer(int id, String code, String name, String dayOfBirth, String userName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }

    public String getName() { return name; }

    public String getDayOfBirth() { return dayOfBirth; }

    public void setName(String name) { this.name = name; }

    public void setDayOfBirth(String dayOfBirth) { this.dayOfBirth = dayOfBirth; }

    public void setUserName(String userName) { this.userName = userName; }
}

public class TCPObjectStream2 {
    public static void main(String[] args) {
        String serverIP = "36.50.135.242";
        int serverPort = 2209;

        String studentCode = "B23DCCN756";
        String qCode = "VNr1O2SY";

        try(
            Socket socket = new Socket(serverIP, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ){

            socket.setSoTimeout(5000);
            oos.writeObject(studentCode + ";" + qCode);
            oos.flush();

            Customer customer = (Customer) ois.readObject();

            String name = customer.getName();
            customer.setName(formatName(name));
            customer.setUserName(generateUserName(name));

            String dob = customer.getDayOfBirth();
            customer.setDayOfBirth(formatDate(dob));

            oos.writeObject(customer);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String formatName(String name) {

        String[] words = name.trim().toLowerCase().split("\\s+");
        if (words.length == 0) {
            return name;
        }

        String lastName = words[words.length - 1].toUpperCase();

        StringBuilder remainingName = new StringBuilder();
        for (int i = 0; i < words.length - 1; i++) {
            String word = words[i];
            String formatted =
                    Character.toUpperCase(word.charAt(0))
                            + word.substring(1);
            if (!remainingName.isEmpty()) {
                remainingName.append(" ");
            }
            remainingName.append(formatted);
        }

        return lastName + ", " + remainingName;
    }

    public static String formatDate(String date) {

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate returnDate = LocalDate.parse(date, inputFormatter);
        return returnDate.format(outputFormatter);

    }

    public static String generateUserName(String name){

        String[] words = name.trim().toLowerCase().split("\\s+");
        if (words.length == 1) {
            return words[0];
        }

        StringBuilder userName = new StringBuilder();
        for (int i = 0; i < words.length - 1; i++) {
            userName.append(words[i].charAt(0));
        }

        userName.append(words[words.length - 1]);
        return userName.toString();

    }
}
