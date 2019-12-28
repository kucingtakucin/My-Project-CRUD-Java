/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package CRUD;

import java.util.*;
import java.io.*;
import javax.swing.*;

public class Operasi {

    public static void listTiket() throws IOException{
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("tiket.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Database tidak ditemukan!!!","Wah error nih",JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(null,"Silakan tambah data terlebih dahulu","Pemberitahuan Penting",JOptionPane.INFORMATION_MESSAGE);
            tambahTiket();
            return;
        }

        // Kita buat header nya secara manual
        System.out.print("____________________________________________________________________________________________");
        System.out.println("\n| No |      Kereta      |   Kelas   | Berangkat |   Tiba   |    Tujuan    |\tHarga");
        System.out.println("--------------------------------------------------------------------------------------------");

        String data = bufferInput.readLine(); // Akan memulai pembacaan file di baris pertama
        int nomor = 0;
        while (data != null) {
            nomor++;
            StringTokenizer masukan = new StringTokenizer(data, ",");

            masukan.nextToken(); // Kita skip bagian primary keys nya
            String nomer = String.format("| %2d ",nomor); // Kita tambahkan nomor secara manual
            String kereta = String.format("| %-17s",masukan.nextToken()); // Bagian nama kereta
            String kelas = String.format("| %-10s",masukan.nextToken()); // Bagian kelas kereta
            String berangkat = String.format("|   %-8s",masukan.nextToken()); // Bagian waktu saat keberangkatan
            String tiba = String.format("|  %-8s",masukan.nextToken()); // Bagian waktu saat tiba
            String tujuan = String.format("| %-13s",masukan.nextToken()); // Bagian tujuan
            String harga = String.format("| %-11s",masukan.nextToken());
            System.out.println(nomer + kereta + kelas + berangkat + tiba + tujuan + harga); // Mencetak data keseluruhan

            data = bufferInput.readLine(); // Akan memulai pembacaan file di baris selanjutnya
        }
        System.out.println("--------------------------------------------------------------------------------------------");
    }

    public static void cariTiket() throws IOException{
        Scanner inputUser = new Scanner(System.in);

        // Mengecek database kita (tiket.txt) ada atau tidak
        try {
            File file = new File("tiket.txt");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Database tidak ditemukan!!!","Wah error nih!",JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(null,"Silakan tambah data terlebih dahulu","Pemberitahuan",JOptionPane.INFORMATION_MESSAGE);
            tambahTiket();
            return;
        }

        // Kita ambil inputan keyword dari user
        System.out.print("Masukkan kata kunci untuk mencari tiket : ");
        String cariString = inputUser.nextLine();
        String[] kataKunci = cariString.split("\\s+");

        // Kita cek keyword di database
        Utility.cekTiketDiDatabase(kataKunci,true);
    }

    public static void tambahTiket() throws IOException{
        FileWriter fileOutput = new FileWriter("tiket.txt",true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        // Mengambil input dari user untuk menambah data
        Scanner inputUser = new Scanner(System.in);
        String kereta,kelas,berangkat,tiba,tujuan,harga;

        System.out.print("Masukkann nama kereta : ");
        kereta = inputUser.nextLine();
        System.out.print("Masukkan kelas kereta : ");
        kelas = inputUser.nextLine();
        System.out.print("Masukkan jam keberangkatan : ");
        berangkat = inputUser.nextLine();
        System.out.print("Masukkan jam tiba : ");
        tiba = inputUser.nextLine();
        System.out.println("Masukkan stasiun tujuan : ");
        tujuan = inputUser.nextLine();
        System.out.println("Masukkan harga tiket : ");
        harga = inputUser.nextLine();

        // Cek barang di database (tiket.txt)
        String[] keywords = {kereta + "," + kelas + "," + berangkat + "," + tiba + "," + tujuan + "," + harga}; // Kita ubah menjadi Array
        System.out.println(Arrays.toString(keywords));

        boolean isExist = Utility.cekTiketDiDatabase(keywords, false);

        // Menulis barang di database (tiket.txt)
        if (!isExist) {
            long nomorEntry = Utility.ambilEntry(kereta,kelas) + 1;

            String keretaTanpaSpasi = kereta.replaceAll("\\s+","");
            String primaryKey = keretaTanpaSpasi + "_" + kelas + "_" + nomorEntry;
            System.out.println("\n---- Data yang akan anda masukkan : ----");
            System.out.println("----------------------------------------");
            System.out.println("Primary key        : " + primaryKey);
            System.out.println("Nama kereta        : " + kereta);
            System.out.println("Kelas kereta       : " + kelas);
            System.out.println("Jam keberangkatan  : " + berangkat);
            System.out.println("Jam tiba           : " + tiba);
            System.out.println("Stasiun tujuan     : " + tujuan);
            System.out.println("Harga tiket        : " + harga);

            boolean isTambah = Utility.GET_YES_OR_NO("Apakah anda ingin menambahka data tersebut?");
            if (isTambah) {
                bufferOutput.write(primaryKey + "," + kereta + "," + kelas + "," + berangkat + "," + tiba + "," + tujuan + "," + harga);
                bufferOutput.newLine();
                bufferOutput.flush();
            }
        } else {
            System.out.println("Tiket yang anda masukkan sudah tersedia di database dengan data berikut : ");
            Utility.cekTiketDiDatabase(keywords,true);
        }

        // Jangan lupa untuk menutup file
        bufferOutput.close();
    }

    public static void updateTiket() throws IOException{}

    public static void hapusTiket() throws IOException{}
}
