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
    }

    public static void cariTiket() throws IOException{}

    public static void tambahTiket() throws IOException{}

    public static void updateTiket() throws IOException{}

    public static void hapusTiket() throws IOException{}
}
