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

        while (lanjutkan) {
            System.out.println("====== DATABASE TIKET KERETA API STASIUN SOLO BALAPAN ======");
            System.out.println("1.\t List seluruh tiket");
            System.out.println("2.\t Cari tiket");
            System.out.println("3.\t Tambah tiket");
            System.out.println("4.\t Update tiket");
            System.out.println("5.\t Hapus tiket");

            System.out.print("\nPilihan kamu : ");
            pilihanUser = inputUser.nextLine();

            switch (pilihanUser) {
                case "1" :
                    System.out.println("========================");
                    System.out.println("====== LIST TIKET ======");
                    System.out.println("========================");
                    Operasi.listTiket();;
                    break;
                case "2" :
                    System.out.println("========================");
                    System.out.println("====== CARI TIKET ======");
                    System.out.println("========================");
                    Operasi.cariTiket();
                    break;
                case "3" :
                    System.out.println("==========================");
                    System.out.println("====== TAMBAH TIKET ======");
                    System.out.println("==========================");
                    Operasi.tambahTiket();
                    break;
                case "4" :
                    System.out.println("==========================");
                    System.out.println("====== UPDATE TIKET ======");
                    System.out.println("==========================");
                    Operasi.updateTiket();
                    break;
                case "5" :
                    System.out.println("=========================");
                    System.out.println("====== HAPUS TIKET ======");
                    System.out.println("=========================");
                    Operasi.hapusTiket();
                    break;
                default :
                    break;
            }
        }
    }
}
