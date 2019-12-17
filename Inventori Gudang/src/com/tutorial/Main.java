package com.tutorial;

import java.util.*;
import java.io.*;
import javax.swing.*;

import CRUD.*;
public class Main {

    public static void main(String[] MbahPutih) throws IOException {
        Scanner inputUser = new Scanner(System.in);
        String pilihanUser;
        boolean lanjutkan = true;

        Utility.clearScreen();
        Utility.sistemLogin();
        while (lanjutkan) {
            Utility.clearScreen();
            System.out.println("====== DATABASE INVENTARIS GUDANG UNIVERSITAS SEBELAS MARET =======\n");
            System.out.println("1.\tList Seluruh Barang");
            System.out.println("2.\tSearch Barang");
            System.out.println("3.\tBarang Masuk");
            System.out.println("4.\tBarang Keluar");
            System.out.println("5.\tData Supplier");
            System.out.println("6.\tData Peminjam");
            System.out.println("7.\tUpdate Barang");
            System.out.println("8.\tDelete Barang");

            System.out.print("\nPilihan kamu : ");
            pilihanUser = inputUser.nextLine();

            switch (pilihanUser) {
                case "1" :
                    System.out.print("\n");
                    System.out.println("                                ==============================");
                    System.out.println("                                ====== LIST DATA BARANG ======");
                    System.out.println("                                ==============================\n");
                    Operasi.listBarang();
                    break;
                case "2" :
                    System.out.print("\n");
                    System.out.println("                                ===========================");
                    System.out.println("                                ====== SEARCH BARANG ======");
                    System.out.println("                                ===========================\n");
                    Operasi.searchData();
                    break;
                case "3" :
                    System.out.print("\n");
                    System.out.println("                                ==========================");
                    System.out.println("                                ====== BARANG MASUK ======");
                    System.out.println("                                ==========================\n");
                    Operasi.barangMasuk();
                    break;
                case "4" :
                    System.out.print("\n");
                    System.out.println("                                ===========================");
                    System.out.println("                                ====== BARANG KELUAR ======");
                    System.out.println("                                ===========================\n");
                    Operasi.barangKeluar();
                    break;
                case "5" :
                    System.out.print("\n");
                    System.out.println("                                ===========================");
                    System.out.println("                                ====== DATA SUPPLIER ======");
                    System.out.println("                                ===========================\n");
                    Operasi.dataSupplier();
                    break;
                case "6" :
                    System.out.print("\n");
                    System.out.println("                                =============================");
                    System.out.println("                                ====== DATA PEMINJAMAN ======");
                    System.out.println("                                =============================\n");
                    Operasi.dataPeminjaman();
                    break;
                case "7" :
                    System.out.print("\n");
                    System.out.println("                                ===========================");
                    System.out.println("                                ====== UPDATE BARANG ======");
                    System.out.println("                                ===========================\n");
                    Operasi.updateBarang();
                    break;
                case "8" :
                    System.out.print("\n");
                    System.out.println("                                ===========================");
                    System.out.println("                                ====== DELETE BARANG ======");
                    System.out.println("                                ===========================\n");
                    Operasi.deleteBarang();
                    break;
                default :
                    System.err.println("\nInput anda tidak ditemukan\nSilahkan pilih 1 s.d 5");
                    JOptionPane.showMessageDialog(null,"Input anda tidak ditemukan\nSilahkan pilih 1 s.d 5","Wah error nih",JOptionPane.ERROR_MESSAGE);
            }
            lanjutkan = Utility.GET_YES_OR_NO("Apakah anda ingin melanjutkan?"); // Sebagai konfirmasi
        }
    }
}
