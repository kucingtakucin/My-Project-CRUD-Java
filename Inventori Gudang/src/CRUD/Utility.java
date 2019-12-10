package CRUD;

import java.util.*;
import java.io.*;
import java.time.*;
import javax.swing.*;

public class Utility {

    protected static boolean cekBarangDiDatabase(String[] kataKunci,boolean isDisplay) throws IOException {
        FileReader fileInput = new FileReader("inventory.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine(); // Akan memulai pembacaan file di baris pertama
        boolean isExist = false; // isExist berfungsi sebagai status apakah keywords kita ada atau tidak
        int nomorData = 0;

        if (isDisplay) {
            System.out.print("____________________________________________________________________________________________");
            System.out.println("\n| No |\tTahun |\tJenis Barang\t|      Merk     |\tSeri Barang");
            System.out.println("--------------------------------------------------------------------------------------------");
        }

        while (data != null) {
            // Cek keywords didalam baris
            isExist = true;
            for (String keyword : kataKunci) { // Looping for each
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }
            // Jika keywordsnya cocok/true maka tampilkan
            if (isExist) {
                nomorData++;
                StringTokenizer masukan = new StringTokenizer(data, ",");

                masukan.nextToken(); // Kita skip bagian Primary Keys nya
                String nomer = String.format("| %2d ",nomorData); // Kita tambahkan nomor secara manual
                String tahun = String.format("|\t%4s  ",masukan.nextToken()); // Bagian tahun
                String jenisBarang = String.format("|\t%-16s",masukan.nextToken()); // Bagian jenis barang
                String Merk = String.format("|     %-10s",masukan.nextToken()); // Bagian merk barang
                String Seri = String.format("|\t%-30s",masukan.nextToken()); // Bagian seri barang

                System.out.println(nomer + tahun + jenisBarang + Merk + Seri); // Mencetak data keseluruhan
            }
            data = bufferInput.readLine(); // Akan memulai pembacaan file di baris selanjutnya
        }
        if (isDisplay) {
            System.out.println("--------------------------------------------------------------------------------------------");
        }
        return isExist;
    }

    protected static String ambilTahun(){
        Scanner inputUser = new Scanner(System.in);

        boolean tahunValid = false; // Sebagai status apakah tahunnya valid atau tidak
        String tahunInput = inputUser.nextLine();
        while(!tahunValid) {
            try {
                Year.parse(tahunInput); // Untuk mengecek apakah inputan tahun kita berupa 4 digit angka
                tahunValid = true;
            } catch (Exception ex) {
                System.out.println("Format tahun yang anda masukan salah (format = YYYY)");
                System.out.print("Silahkan masukkan tahun barang lagi : ");
                tahunValid = false;
                tahunInput = inputUser.nextLine();
            }
        }
        return tahunInput;
    }

    protected static long ambilEntry(String merk,String tahun) throws IOException{
        FileReader fileInput = new FileReader("inventory.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        long entry = 0;
        String primaryKey;
        Scanner dataScanner; // Untuk membaca file per kalimat
        String data = bufferInput.readLine(); // Persiapan untuk membaca file di baris pertama

        while (data != null) {
            dataScanner = new Scanner(data); // Akan memulai pembacaan file di kalimat pertama
            dataScanner.useDelimiter(","); // Akan kita akhiri pembacaan file setelah bertemu tanda koma (,)
            primaryKey = dataScanner.next(); // Mulai pembacaan file di kalimat pertama
            dataScanner = new Scanner(primaryKey);  // Kita baca lagi hasil potongan kalimat nya
            dataScanner.useDelimiter("_"); // Dan akan kita akhiri pembacaan file setelah bertemu tanda underscore (_)

            merk = merk.replaceAll("\\s+",""); // Kita hapus semua spasi
            if (merk.equalsIgnoreCase(dataScanner.next()) && tahun.equalsIgnoreCase(dataScanner.next()) ) {
                entry = dataScanner.nextInt();
            }
            data = bufferInput.readLine(); // Akan memulai pembacaan di baris selanjutnya
        }
        return entry;
    }

    public static boolean GET_YES_OR_NO(String message){
        Scanner inputUser = new Scanner(System.in);
        System.out.print("\n" + message + " (Y/N) ");
        String konfirmasi = inputUser.nextLine();

        // equalsIgnoreCase tidak mempedulikan huruf kecil maupun huruf besar
        while (!konfirmasi.equalsIgnoreCase("Y") && !konfirmasi.equalsIgnoreCase("N")) {
            System.err.println("Pilihan kamu salah");
            System.out.print("\n" + message + " (Y/N) ");
            konfirmasi = inputUser.nextLine();
        }
        return konfirmasi.equalsIgnoreCase("Y");
    }

    public static void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows")) { // Jika sistem operasi yang kita gunakan adalah windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.println("\033\143"); // Jika sistem operasi yang kita gunakan adalah Linux/Unix
            }
        } catch (Exception ex) {
            System.out.println("Wah engga bisa clear screen gais awokwokwowk!!!"); // Selain Windows dan Linux/Unix
        }
    }

    public static void sistemLogin(){
        String username,password; // Untuk inputan user
        String ID = "admin";
        String pass = "admin";

        // Mengambil input username dan password dari user
        username = JOptionPane.showInputDialog(null,"Masukkan username anda ","Login dulu gais",JOptionPane.INFORMATION_MESSAGE);
        password = JOptionPane.showInputDialog(null,"Masukkan password anda ","Login dulu gais",JOptionPane.INFORMATION_MESSAGE);

        while (!username.equals(ID) || !password.equals(pass)) {
            JOptionPane.showMessageDialog(null,"Login gagal, silakan coba lagi","Error gais",JOptionPane.ERROR_MESSAGE);
            username = JOptionPane.showInputDialog(null,"Masukkan username anda ","Login dulu gais",JOptionPane.INFORMATION_MESSAGE);
            password = JOptionPane.showInputDialog(null,"Masukkan password anda ","Login dulu gais",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
