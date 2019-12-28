/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.tutorial;

import java.util.*;
import java.io.*;
import javax.swing.*;

import CRUD.*;
public class Main {

    public static void main(String[] MbahPutih) throws IOException {
        Scanner inputUser = new Scanner(System.in);
        String pilihanUser;
        boolean isLanjutkan = true;

        Utility.clearScreen();
        Utility.sistemLogin();
        while (isLanjutkan) {
            Utility.clearScreen();
            System.out.println("------ DATABASE PERPUSTAKAAN DAERAH KAB.KARANGANYAR ------\n");
            System.out.println("1.\tLihat seluruh buku");
            System.out.println("2.\tCari data buku");
            System.out.println("3.\tTambah data buku");
            System.out.println("4.\tUbah data buku");
            System.out.println("5.\tHapus data buku");

            System.out.print("\nPilihan kamu : ");
            pilihanUser = inputUser.next();

            switch (pilihanUser) {
                case "1":
                    System.out.println("\n=================");
                    System.out.println("LIST SELURUH BUKU");
                    System.out.println("=================");
                    Operasi.tampilkanData();
                    break;
                case "2":
                    System.out.println("\n=========");
                    System.out.println("CARI BUKU");
                    System.out.println("=========");
                    Operasi.cariData();
                    break;
                case "3":
                    System.out.println("\n================");
                    System.out.println("TAMBAH DATA BUKU");
                    System.out.println("================");
                    Operasi.tambahData();
                    Operasi.tampilkanData();
                    break;
                case "4":
                    System.out.println("\n==============");
                    System.out.println("UBAH DATA BUKU");
                    System.out.println("==============");
                    Operasi.updateData();
                    break;
                case "5":
                    System.out.println("\n===============");
                    System.out.println("HAPUS DATA BUKU");
                    System.out.println("===============");
                    Operasi.hapusData();
                    break;
                default:
                    System.err.println("\nInput anda tidak ditemukan\nSilahkan pilih 1 s.d 5");
                    JOptionPane.showMessageDialog(null,"Input anda tidak ditemukan\nSilahkan pilih 1 s.d 5","Error",JOptionPane.ERROR_MESSAGE);
            }
            isLanjutkan = Utility.GET_YES_OR_NO("Apakah anda ingin melanjutkan?");
        }
    }
}
