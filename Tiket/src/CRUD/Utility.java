package CRUD;

import java.util.*;
import java.io.*;
import javax.swing.*;

public class Utility {

    public static boolean GET_YES_OR_NO(String message) {
        Scanner inputUser = new Scanner(System.in);
        System.out.print("\n" + message + " (Y/N) ");
        String konfirmasi = inputUser.nextLine();

        while (!konfirmasi.equalsIgnoreCase("Y") && !konfirmasi.equalsIgnoreCase("N")) {
            System.err.println("Pilihan kamu salah");
            JOptionPane.showMessageDialog(null,"Pilihan kamu salah!!!","Wah error nih",JOptionPane.ERROR_MESSAGE);
            System.out.print("\n" + message + " (Y/N) ");
            konfirmasi = inputUser.nextLine();
        }
        return konfirmasi.equalsIgnoreCase("Y");
    }

    public static void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows")) { // Jika sistem operasi yang kita gunakan adalah Windows
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143"); // Jika sistem operasi yang kita gunakan adalah Linux/Unix
            }

        } catch (Exception ex) {
            System.err.println("Wah engga bisa clear screen gais awokwokwok!!!"); // Selain Windows dan Linux/Unix
        }
    }

    public static void sistemLogin(){
        String username,password; // Untuk inputan user
        String ID = "admin";
        String pass = "admin";

        // Mengambil input username dan password dari user
        username = JOptionPane.showInputDialog(null, "Masukkan username kamu ", "Login dulu gais", JOptionPane.INFORMATION_MESSAGE);
        password = JOptionPane.showInputDialog(null, "Masukkan password kamu ", "Login dulu gais", JOptionPane.INFORMATION_MESSAGE);

        while (!username.equals(ID) || !password.equals(pass)) {
            JOptionPane.showMessageDialog(null,"Login gagal, silakan coba lagi","Wah error nih",JOptionPane.ERROR_MESSAGE);
            username = JOptionPane.showInputDialog(null, "Masukkan username kamu ", "Login dulu gais", JOptionPane.INFORMATION_MESSAGE);
            password = JOptionPane.showInputDialog(null, "Masukkan password kamu ", "Login dulu gais", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
