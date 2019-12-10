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
            System.out.println("1.\tList seluruh barang");
            System.out.println("2.\tCari data barang");
            System.out.println("3.\tTambah data barang");
            System.out.println("4.\tUpdate data barang");
            System.out.println("5.\tHapus data barang");

            System.out.print("\nPilihan kamu : ");
            pilihanUser = inputUser.nextLine();

            switch (pilihanUser) {
                case "1":
                    System.out.println("\n==============================");
                    System.out.println("====== LIST DATA BARANG ======");
                    System.out.println("==============================");
                    Operasi.listData();
                    break;
                case "2":
                    System.out.println("\n=========================");
                    System.out.println("====== CARI BARANG ======");
                    System.out.println("=========================");
                    Operasi.cariData();
                    break;
                case "3":
                    System.out.println("\n================================");
                    System.out.println("====== TAMBAH DATA BARANG ======");
                    System.out.println("================================");
                    Operasi.tambahData();
                    Operasi.listData();
                    break;
                case "4":
                    System.out.println("\n==============================");
                    System.out.println("====== UPDATE DATA BARANG ======");
                    System.out.println("==============================");
                    Operasi.updateData();
                    break;
                case "5":
                    System.out.println("\n===============================");
                    System.out.println("====== HAPUS DATA BARANG ======");
                    System.out.println("===============================");
                    Operasi.hapusData();
                    break;
                default:
                    System.err.println("\nInput anda tidak ditemukan\nSilahkan pilih 1 s.d 5");
                    JOptionPane.showMessageDialog(null,"Input anda tidak ditemukan\nSilahkan pilih 1 s.d 5","Wah error nih",JOptionPane.ERROR_MESSAGE);
            }
            lanjutkan = Utility.GET_YES_OR_NO("Apakah anda ingin melanjutkan?"); // Sebagai konfirmasi
        }
    }
}
