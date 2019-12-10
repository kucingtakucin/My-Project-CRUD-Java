package CRUD;

import javax.swing.*;
import java.util.*;
import java.io.*;

public class Operasi {

    public static void listData() throws IOException{
        FileReader fileInput;
        BufferedReader bufferInput;

        // Kita cek file database nya (inventory.txt) ada atau tidak
        try {
            fileInput = new FileReader("inventory.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null,"Database tidak ditemukan!!!","Wah error nih",JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(null,"Silakan tambah data terlebih dahulu","Pemberitahuan Penting",JOptionPane.INFORMATION_MESSAGE);
            tambahData();
            return;
        }

        // Kita buat header nya secara manual
        System.out.print("____________________________________________________________________________________________");
        System.out.println("\n| No |\tTahun |\tJenis Barang\t|      Merk     |\tSeri Barang");
        System.out.println("--------------------------------------------------------------------------------------------");

        String data = bufferInput.readLine(); // Akan memulai pembacaaan file di kalimat pertama
        int nomor = 0;
        while (data != null){ // Jika data tidak kosong, maka kita baca isi nya
            nomor++;
            StringTokenizer masukan = new StringTokenizer(data, ","); // Membaca per kata di baris pertama

            masukan.nextToken(); // Kita skip bagian primary keys nya
            String nomer = String.format("| %2d ",nomor); // Kita tambahkan nomor secara manual
            String tahun = String.format("|\t%4s  ",masukan.nextToken()); // Bagian tahun
            String jenisBarang = String.format("|\t%-16s",masukan.nextToken()); // Bagian jenis barang
            String Merk = String.format("|     %-10s",masukan.nextToken()); // Bagian merk barang
            String Seri = String.format("|\t%-30s",masukan.nextToken()); // Bagian seri barang
            System.out.println(nomer + tahun + jenisBarang + Merk + Seri); // Mencetak data keseluruhan

            data = bufferInput.readLine(); // Akan memulai pembacaan file di baris selanjutnya
        }
        System.out.println("--------------------------------------------------------------------------------------------");
    }

    public static void cariData() throws IOException{
        Scanner inputUser = new Scanner(System.in);

        // Mengecek database kita (inventory.txt) ada atau tidak
        try {
            File file = new File("inventory.txt"); // Untuk mengecek file kita ada atau tidak
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null,"Database tidak ditemukan!!!","Wah error nih",JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(null,"Silakan tambah data terlebih dahulu","Pemberitahuan",JOptionPane.INFORMATION_MESSAGE);
            tambahData();
            return;
        }

        // Kita ambil inputan keyword dari user
        System.out.print("Masukan kata kunci untuk mencari barang : ");
        String cariString = inputUser.nextLine();
        String[] kataKunci = cariString.split("\\s+"); // Kita ubah menjadi Array  dengan tipe data String

        // Kita cek keyword di database
        Utility.cekBarangDiDatabase(kataKunci, true);
    }

    public static void tambahData() throws IOException{
        FileWriter fileOutput = new FileWriter("inventory.txt",true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        // Mengambil input dari user untuk menambah data
        Scanner inputUser = new Scanner(System.in);
        String jenis, merk, seri, tahun;

        System.out.print("Masukkan jenis barang : ");
        jenis = inputUser.nextLine();
        System.out.print("Masukan merk barang : ");
        merk = inputUser.nextLine();
        System.out.print("Masukan seri barang : ");
        seri = inputUser.nextLine();
        System.out.print("Masukan tahun barang (YYYY) : ");
        tahun = Utility.ambilTahun();

        // Cek barang di database (inventory.txt)
        String[] keywords = {tahun + "," + jenis + "," + merk + "," + seri}; // Kita ubah menjadi array
        System.out.println(Arrays.toString(keywords));

        boolean isExist = Utility.cekBarangDiDatabase(keywords,false);

        // Menulis barang di database (inventory.txt)
        if (!isExist){
            long nomorEntry = Utility.ambilEntry(merk, tahun) + 1; // Menciptakan nomorEntry

            String merkTanpaSpasi = merk.replaceAll("\\s+",""); // Kita hapus semua spasi
            String primaryKey = merkTanpaSpasi + "_" + tahun + "_" + nomorEntry;
            System.out.println("\n---- Data yang akan anda masukkan : ----");
            System.out.println("----------------------------------------");
            System.out.println("Primary key  : " + primaryKey);
            System.out.println("Tahun        : " + tahun);
            System.out.println("Jenis Barang : " + jenis);
            System.out.println("Merk Barang  : " + merk);
            System.out.println("Seri Barang  : " + seri);

            boolean isTambah = Utility.GET_YES_OR_NO("Apakah anda ingin menambahkan data tersebut? "); // Sebagai konfirmasi
            if(isTambah){
                bufferOutput.write(primaryKey + "," + tahun + "," + jenis + "," + merk + "," + seri);
                bufferOutput.newLine(); // Menciptakan baris baru (enter)
                bufferOutput.flush(); // Menuliskan di database (inventory.txt)
            }
        } else {
            System.out.println("Barang yang anda akan masukan sudah tersedia di database dengan data berikut : ");
            Utility.cekBarangDiDatabase(keywords,true);
        }

        // Jangan lupa untuk menutup file
        bufferOutput.close(); // Menutup file
    }

    public static void updateData() throws IOException{
        // Kita ambil file database original (inventory.txt)
        File database = new File("inventory.txt"); // Mengecek file kita ada atau tidak
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        // Kita buat file database sementara (temporary.txt)
        File temporary = new File("temporary.txt"); // Mengecek file kita ada atau tidak
        FileWriter fileOutput = new FileWriter(temporary);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        // Tampilkan data
        System.out.println("-_-_-_-_- LIST BARANG -_-_-_-_-");
        listData();

        // Kita ambil input dari user
        Scanner inputUser = new Scanner(System.in);
        System.out.print("\nMasukan nomor barang yang akan diupdate : ");
        int updateNum = inputUser.nextInt();

        // Tampilkan data yang ingin diupdate
        String data = bufferedInput.readLine();
        int entryCounts = 0;

        while (data != null){
            entryCounts++;
            StringTokenizer masukan = new StringTokenizer(data,","); // Memulai pembacaan file per kata

            // Tampilkan jika entrycounts == updateNum
            if (updateNum == entryCounts){
                System.out.println("\n------ Data yang ingin anda update adalah : ------");
                System.out.println("--------------------------------------------------");
                System.out.println("Primary Keys   : " + masukan.nextToken()); // Bagian primary keys
                System.out.println("Tahun Barang   : " + masukan.nextToken()); // Bagian tahun barang
                System.out.println("Jenis Barang   : " + masukan.nextToken()); // Bagian jenis barang
                System.out.println("Merk Barang    : " + masukan.nextToken()); // Bagian merk barang
                System.out.println("Seri Barang    : " + masukan.nextToken()); // Bagian seri barang

                // Update data dengan cara mengambil input dari user
                String[] fieldData = {"tahun","jenis","merk","seri"};
                String[] tempData = new String[4];

                masukan = new StringTokenizer(data,","); // Kita refresh data
                String originalData;
                for(int i=0; i < fieldData.length ; i++) {
                    boolean isUpdate = Utility.GET_YES_OR_NO("Apakah anda ingin mengubah " + fieldData[i]);
                    originalData = masukan.nextToken();
                    if (isUpdate){
                        // Mengambil input dari user
                        if (fieldData[i].equalsIgnoreCase("tahun")){
                            System.out.print("Masukkan tahun barang, format = (YYYY) : ");
                            tempData[i] = Utility.ambilTahun();
                        } else {
                            inputUser = new Scanner(System.in);
                            System.out.print("\nMasukkan " + fieldData[i] + " baru : ");
                            tempData[i] = inputUser.nextLine();
                        }
                    } else {
                        tempData[i] = originalData;
                    }
                }

                // Tampilkan data baru ke layar
                masukan = new StringTokenizer(data,",");
                masukan.nextToken(); // Kita skip bagian primary keys nya
                System.out.println("\n------- Data baru anda adalah : -----");
                System.out.println("-------------------------------------");
                System.out.println("Tahun Barang  : " + masukan.nextToken() + " --> " + tempData[0]);
                System.out.println("Jenis Barang  : " + masukan.nextToken() + " --> " + tempData[1]);
                System.out.println("Merk Barang   : " + masukan.nextToken() + " --> " + tempData[2]);
                System.out.println("Seri Barang   : " + masukan.nextToken() + " --> " + tempData[3]);

                boolean isUpdate = Utility.GET_YES_OR_NO("Apakah anda yakin ingin mengupdate data tersebut");
                if (isUpdate){
                    // Cek data baru di database
                    boolean isExist = Utility.cekBarangDiDatabase(tempData,false);
                    if(isExist){
                        System.err.println("Data barang sudah ada di database, proses update dibatalkan, \nsilahkan delete data yang bersangkutan");
                        // Keseluruhan data tetap kita copy ke dalam temporary database (temporary.txt)
                        bufferedOutput.write(data);
                    } else {
                        // Format data baru ke dalam database
                        String tahun = tempData[0];
                        String jenis = tempData[1];
                        String merk = tempData[2];
                        String seri = tempData[3];

                        // Kita bikin primary key lagi
                        long nomorEntry = Utility.ambilEntry(merk, tahun) + 1;
                        String merkTanpaSpasi = merk.replaceAll("\\s+","");
                        String primaryKey = merkTanpaSpasi+"_"+tahun+"_"+nomorEntry;

                        // Tulis data kedalam database sementara (temporary.txt)
                        bufferedOutput.write(primaryKey + "," + tahun + "," + jenis + "," + merk + "," + seri);
                    }
                } else {
                    // Copy data
                    bufferedOutput.write(data);
                }
            } else {
                // Copy data
                bufferedOutput.write(data);
            }
            bufferedOutput.newLine();
            data = bufferedInput.readLine(); // Memulai pembacaan file per kata di baris selanjutnya
        }

        // Menulis data kedalam file temporary database (temporary.txt)
        bufferedOutput.flush();

        // Kita delete original database (inventory.txt)
        database.delete();

        // Rename file temporary.txt menjadi inventory.txt
        temporary.renameTo(database);
    }

    public static void hapusData() throws IOException{
        // Kita ambil database original (inventory.txt)
        File database = new File("inventory.txt"); // Mengecek file kita ada atau tidak
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        // Kita buat temporary database (temporary.txt)
        File temporary = new File("temporary.txt"); // Mengecek file kita ada atau tidak
        FileWriter fileOutput = new FileWriter(temporary);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        // Tampilkan data
        System.out.println("-_-_-_-_- LIST BARANG -_-_-_-_-");
        listData();

        // Kita ambil input dari user untuk menghapus data
        Scanner inputUser = new Scanner(System.in);
        System.out.print("\nMasukkan nomor barang yang akan dihapus : ");
        int deleteNum = inputUser.nextInt();

        // Looping untuk membaca data tiap baris dan skip data yang akan dihapus
        boolean isFound = false;
        int entryCounts = 0;
        String data = bufferedInput.readLine(); // Akan memulai pembacaan file di baris pertama

        while (data != null){
            entryCounts++;
            boolean isDelete = false;

            StringTokenizer masukan = new StringTokenizer(data,","); // Memulai pembacaan file per kata
            // Tampilkan terlebih dahulu data yang ingin di hapus
            if (deleteNum == entryCounts){
                System.out.println("\n----- Data yang akan anda hapus : -----");
                System.out.println("---------------------------------------");
                System.out.println("Primary Keys  : " + masukan.nextToken()); // Bagian Primary Keys
                System.out.println("Tahun Barang  : " + masukan.nextToken()); // Bagian tahun barang
                System.out.println("Jenis Barang  : " + masukan.nextToken()); // Bagian jenis barang
                System.out.println("Merk Barang   : " + masukan.nextToken()); // Bagian merk barang
                System.out.println("Seri Barang   : " + masukan.nextToken()); // Bagian seri barang

                isDelete = Utility.GET_YES_OR_NO("Apakah anda yakin untuk menghapus?");
                isFound = true;
            }
            if(isDelete){
                /* Data yang akan kita hapus tetap berada di database original (inventory.txt),
                 * sedangkan data yang lainnya kita pindahkan ke database sementara (temporary.txt) */
                System.out.println("Data barang berhasil dihapus");
            } else {
                /* kita pindahkan semua data dari database original (inventory.txt)
                 * ke database sementara (temporary.txt) */
                bufferedOutput.write(data);
                bufferedOutput.newLine();
            }
            data = bufferedInput.readLine();
        }
        if(!isFound){
            System.err.println("Barang tidak ditemukan");
        }

        // Menuliskan data ke file database sementara (temporary.txt)
        bufferedOutput.flush();

        // Delete original file database (inventory.txt)
        database.delete();

        // Rename file temporary (temporary.txt) menjadi file database asli (inventory.txt)
        temporary.renameTo(database);
    }
}
