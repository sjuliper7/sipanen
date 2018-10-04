package View;

import Controller.*;

public class HomeView extends View {

    public static void showHomeMenu(){
        System.out.println("Selamat datang di Sistem Informasi Panen");
        System.out.println("Berikut adalah pilihan menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");

        System.out.print("Masukkan pilihan menu: ");
        option = scanner.nextInt();

        if(option == 1){
            AuthenticationController.login();
        } else if(option == 2){
            System.out.println("Terimakasih telah menggunakan sistem ini. Sampai jumpa kembali :)");
        }
    }

    public static void showDashboard(){
        System.out.println("Selamat datang, Admin");
        System.out.println("Berikut adalah pilihan menu:");
        System.out.println("1. Member");
        System.out.println("2. Komoditas");
        System.out.println("3. Alat");
        System.out.println("4. Bahan");
        System.out.println("5. Penjualan");
        System.out.println("6. Peminjaman");
        System.out.println("9. Logout");

        System.out.print("Masukkan pilihan menu: ");
        option = scanner.nextInt();

        if(option == 1){
            MemberController.getMenu();
        } else if(option == 2){
            KomoditasController.getSubMenu();
        } else if(option == 3){
            AlatController.getMenu();
        } else if(option == 4){
            BahanController.getMenu();
        } else if(option == 5){
            PenjualanController.getMenu();
        } else if(option == 6){
            PeminjamanController.getMenu();
        } else if(option == 9){
            AuthenticationController.logout();
        }
    }
}
