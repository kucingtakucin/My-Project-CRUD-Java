package CRUD;

import java.util.*;
import java.io.*;
import java.time.*;
import javax.swing.*;

public class Utility {

    protected static boolean cekBarangDiDatabase(String[] kataKunci,boolean isDisplay) throws IOException {
        // Kita ambil file database inventory.txt
        FileReader fileInput = new FileReader("inventory.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine(); // Akan memulai pembacaan file di baris pertama
        boolean isExist = false; // isExist berfungsi sebagai status apakah keywords kita ada atau tidak
        int nomorData = 0;

        if (isDisplay) {
            System.out.print("________________________________________________________________________________________________");
            System.out.println("\n| No |\tTahun |\tJenis Barang\t|      Merk     |             Seri Barang            |  Stok  |");
            System.out.println("------------------------------------------------------------------------------------------------");
        }

        while (data != null) { // Ketika data memiliki isi, maka kita baca data tersebut
            // Cek keywords didalam baris
            isExist = true;
            for (String keyword : kataKunci) { // Looping for each
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }
            // Jika keywordsnya cocok/true maka tampilkan
            if (isExist) {
                nomorData++;
                StringTokenizer masukan = new StringTokenizer(data, ","); // Membaca file per kata di baris pertama

                masukan.nextToken(); // Kita skip bagian Primary Keys nya
                masukan.nextToken(); // Kita skip bagian Supplier nya
                String nomer = String.format("| %2d ", nomorData); // Kita tambahkan nomor secara manual
                String stok = String.format("|   %-5s|", masukan.nextToken()); // Bagian stok barang
                String tahun = String.format("|\t%4s  ", masukan.nextToken()); // Bagian tahun
                String jenisBarang = String.format("|\t%-16s", masukan.nextToken()); // Bagian jenis barang
                String Merk = String.format("|     %-10s", masukan.nextToken()); // Bagian merk barang
                String Seri = String.format("| %-35s", masukan.nextToken()); // Bagian seri barang

                if (isDisplay) {
                    System.out.println(nomer + tahun + jenisBarang + Merk + Seri + stok); // Mencetak data keseluruhan
                }
            }
            data = bufferInput.readLine(); // Akan memulai pembacaan file di baris selanjutnya
        }
        if (isDisplay) {
            System.out.println("------------------------------------------------------------------------------------------------");
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
                JOptionPane.showMessageDialog(null,"Format tahun yang anda masukan salah! (format = YYYY)","Error",JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(null,"Silahkan masukkan tahun barang lagi!","Pemberitahuan",JOptionPane.INFORMATION_MESSAGE);
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

    public static int cekNomorDiDatabase(String[] kataKunci) throws IOException{
        // Kita ambil file database inventory.txt
        FileReader fileInput = new FileReader("inventory.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine(); // Akan memulai pembacaan file di baris pertama
        boolean isExist = false;
        int count = 0;
        int nomorData = 0;
        while (data != null) { // Jika data memiliki isi/tidak kosong, maka kita baca isinya
            count++;
            isExist = true;
            for (String keyword : kataKunci) { // Loopinng for each untuk search keyword
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }
            if (isExist) {
                nomorData = count; // Jika ketemu, maka kita ambil nomor data nya saja
            }
            data = bufferInput.readLine(); // Akan memulai pembacaan file di baris selanjutnya
        }
        return nomorData;
    }

    public static void tambahStok(String[] kataKunci,int stok) throws IOException {
        // Kita ambil file database original (inventory.txt)
        File database = new File("inventory.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferInput = new BufferedReader(fileInput);

        // Kita buat file database sementara (temporary.txt)
        File temporary = new File("temporary.txt");
        FileWriter fileOutput = new FileWriter(temporary);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        String data = bufferInput.readLine(); // Akan memulai pembacaan file di baris pertama
        int stokNum = cekNomorDiDatabase(kataKunci); // Kita ambil nomor barang yang ingin ditambah stok nya
        int nomorData = 0;
        int stokAwal = 0;

        while (data != null) { // Ketika data memiliki isi/tidak kosong, maka
            nomorData++;
            StringTokenizer masukan = new StringTokenizer(data, ",");

            if (stokNum == nomorData) {
                String[] fieldData = {"Supplier","Stok","Tahun","Jenis","Merk","Seri"};
                String[] tempData = new String[6];

                masukan = new StringTokenizer(data, ","); // Kita baca file di baris pertama
                String originalData = masukan.nextToken(); // Skip bagian primary keys
                for (int i = 0;i < fieldData.length;i++) {
                    originalData = masukan.nextToken();
                    if (i == 1) { // Kita ubah di bagian stok barang nya
                        stokAwal = Integer.parseInt(originalData); // Mengubah string menjadi integer
                        tempData[i] = String.valueOf(stokAwal + stok); // Tambah stok
                    } else {
                        tempData[i] = originalData; // Selain bagian stok, data tidak kita ubah
                    }
                }

                // Tampilkan data baru ke layar
                masukan = new StringTokenizer(data, ","); // Refresh data
                System.out.println("\n---- Data yang akan anda masukkan : ----");
                System.out.println("----------------------------------------");
                System.out.println("Primary key       : " + masukan.nextToken());
                System.out.println("Supplier          : " + masukan.nextToken());
                masukan.nextToken(); // Kita skip bagian stok nya
                System.out.println("Tahun Barang      : " + masukan.nextToken());
                System.out.println("Jenis Barang      : " + masukan.nextToken());
                System.out.println("Merk Barang       : " + masukan.nextToken());
                System.out.println("Seri Barang       : " + masukan.nextToken());
                System.out.println("Stok awal         : " + stokAwal);
                System.out.println("Pertambahan stok  : " + stok);
                System.out.println("Total stok        : " + tempData[1]);

                boolean isTambah = GET_YES_OR_NO("Apakah anda ingin menambahkan data tersebut?");
                if (isTambah) {
                    // Format data baru ke dalam database
                    String supplier = tempData[0];
                    String stokBaru = tempData[1];
                    String tahun = tempData[2];
                    String jenis = tempData[3];
                    String merk = tempData[4];
                    String seri = tempData[5];

                    // Kita bikin primary keys nya
                    long nomorEntry = ambilEntry(merk, tahun);
                    String merkTanpaSpasi = merk.replaceAll("\\s+", "");
                    String primaryKey = merkTanpaSpasi + "_" + tahun + "_" + nomorEntry;

                    // Tulis data kedalam database sementara (temporary.txt)
                    bufferOutput.write(primaryKey + "," + supplier + "," + stokBaru + "," + tahun + "," + jenis + "," + merk + "," + seri);
                } else {
                    // Copy data
                    bufferOutput.write(data);
                }
            } else {
                // Copy data
                bufferOutput.write(data);
            }
            bufferOutput.newLine();
            data = bufferInput.readLine();
        }

        // Menulis data kedalam file temporary database (temporary.txt)
        bufferOutput.flush();

        // Kita delete original database (inventory.txt)
        database.delete();

        // Rename file temporary.txt menjadi inventory.txt
        temporary.renameTo(database);
    }

    static ArrayList<String> peminjamList = new ArrayList<>();
    public static void tambahPeminjam(String peminjam) throws IOException{
        peminjamList.add(peminjam); // Kita masukkan nama peminjam ke ArrayList
        Collections.sort(peminjamList); // Kita lakukan sorting untuk jaga-jaga
//        System.out.println(peminjamList);

//      Kita buat file untuk data peminjam (pinjamInventory.txt)
        File dataPeminjam = new File("pinjamInventory.txt");
        FileWriter fileOutput = new FileWriter(dataPeminjam,true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        // Kita gunakan for di dalam for untuk menghapus indeks yang memiliki kesamaan isi dengan indeks yang lainnya
        for (int i = 0;i < peminjamList.size();i++) {
            for (int j = 0; j < peminjamList.size() - 1; j++) {
                if (peminjamList.get(j).equals(peminjamList.get(j + 1))) {
                    peminjamList.remove(j); // Hapus indeks
                }
            }
        }

        int indeks = 0;
        Collections.sort(peminjamList); // Kita urutkan
        peminjamList.trimToSize(); // Kita hapus indeks yang kosong

        // Kita tulis data peminjam per indeks ke dalam file supplyInventory.txt
        while (indeks < peminjamList.size()) {
            bufferOutput.write(String.valueOf(peminjamList.get(indeks)));
            bufferOutput.newLine();
            indeks++;
        }
        bufferOutput.flush(); // Tulis data
        bufferOutput.close();
    }

    public static boolean GET_YES_OR_NO(String message){
        Scanner inputUser = new Scanner(System.in);
        System.out.print("\n" + message + " (Y/N) ");
        String konfirmasi = inputUser.nextLine();

        // equalsIgnoreCase tidak mempedulikan huruf kecil maupun huruf besar
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
            if (System.getProperty("os.name").contains("Windows")) { // Jika sistem operasi yang kita gunakan adalah windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.println("\033\143"); // Jika sistem operasi yang kita gunakan adalah Linux/Unix
            }
        } catch (Exception ex) {
            System.err.println("Wah engga bisa clear screen gais awokwokwowk!!!"); // Selain Windows dan Linux/Unix
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
