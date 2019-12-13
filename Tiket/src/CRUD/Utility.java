package CRUD;

import java.util.*;
import java.io.*;
import javax.swing.*;

public class Utility {

    protected static boolean cekTiketDiDatabase(String[] kataKunci,boolean isDisplay) throws IOException{
        FileReader fileInput = new FileReader("tiket.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine(); // Akan memulai pembacaan file di baris pertama
        boolean isExist = false; // isExist berfungsi sebagai status apakah keyword kita ada atau tidak
        int nomorData = 0;

        if (isDisplay) {
            System.out.print("____________________________________________________________________________________________");
            System.out.println("\n| No |      Kereta      |   Kelas   | Berangkat |   Tiba   |    Tujuan    |\tHarga");
            System.out.println("--------------------------------------------------------------------------------------------");
        }

        while (data != null) {
            // Cek keywords didalam baris
            isExist = true;
            for  (String keyword : kataKunci) {
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }

            // Jika keywordsnya cocok/true maka tampilkan
            if (isExist) {
                nomorData++;
                StringTokenizer masukan = new StringTokenizer(data,",");

                masukan.nextToken(); // Kita skip bagian primary keys nya
                String nomer = String.format("| %2d ",nomorData); // Kita tambahkan nomor secara manual
                String kereta = String.format("| %-17s",masukan.nextToken()); // Bagian nama kereta
                String kelas = String.format("| %-10s",masukan.nextToken()); // Bagian kelas kereta
                String berangkat = String.format("|   %-8s",masukan.nextToken()); // Bagian waktu saat keberangkatan
                String tiba = String.format("|  %-8s",masukan.nextToken()); // Bagian waktu saat tiba
                String tujuan = String.format("| %-13s",masukan.nextToken()); // Bagian tujuan
                String harga = String.format("| %-11s",masukan.nextToken());

                System.out.println(nomer + kereta + kelas + berangkat + tiba + tujuan + harga); // Mencetak data keseluruhan
            }
            data = bufferInput.readLine(); // Akan memulai pembacaan file di baris selanjutnya
        }
        if (isDisplay) {
            System.out.println("--------------------------------------------------------------------------------------------");

        }
        return isExist;
    }

    protected static long ambilEntry(String kereta, String kelas) throws IOException{
        FileReader fileInput = new FileReader("tiket.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        long entry = 0;
        String primaryKey;
        Scanner dataScanner; // Untuk membaca file per kalimat
        String data = bufferInput.readLine();

        while (data != null) {
            dataScanner = new Scanner(data);
            dataScanner.useDelimiter(",");
            primaryKey = dataScanner.next();
            dataScanner = new Scanner(primaryKey);
            dataScanner.useDelimiter("_");

            kereta = kereta.replaceAll("\\s+","");
            if (kereta.equalsIgnoreCase(dataScanner.next()) && kelas.equalsIgnoreCase(dataScanner.next())) {
                entry = dataScanner.nextInt();
            }
            data = bufferInput.readLine();
        }
        return entry;
    }

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
