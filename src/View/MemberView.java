package View;

import Controller.HomeController;
import Controller.MemberController;
import Model.Member;
import java.util.ArrayList;

public class MemberView extends View {

    public static void showMenu() {
        System.out.println("1. Tambah");
        System.out.println("2. Ubah");
        System.out.println("3. Hapus");
        System.out.println("9. Kembali ke Menu Utama");
        System.out.print("Masukkan pilihan menu: ");
        Integer option = scanner.nextInt();

        if (option == 1) {
            MemberController.Create();
        } else if (option == 2) {
            MemberController.getEditForm();
        } else if (option == 3) {
            delete();
        } else if (option == 9){
            HomeController.getHomeMenu();
        }

    }

    public static void showCreateForm() {
        System.out.println("===> Create From Member");
        String buffer = scanner.nextLine();
        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();

        System.out.print("Masukkan Umur: ");
        Integer umur = scanner.nextInt();
        buffer = scanner.nextLine();

        System.out.print("Masukkan Tempat Tanggal Lahir: ");
        String tempatTglLahir = scanner.nextLine();

        System.out.print("Masukkan Jenis Kelamin: ");
        String jenisKelamin = scanner.nextLine();

        MemberController.Store(nama, umur, tempatTglLahir, jenisKelamin);
    }

    public static void memberShowAll(ArrayList<Member> members) {
        System.out.println("=== Daftar Semua Member ===");
        System.out.println("ID \tNama \t\tUmur \t\tTempat Tanggal Lahir\t\tJenis Kelamin");
        if (members == null) {
            System.out.println("Daftar Alat Kosong");
        } else {
            for (Member member : members) {

                System.out.print(member.getId() + "\t");
                System.out.print(member.getNama() + "\t\t");
                System.out.print(member.getUmur() + "\t\t");
                System.out.print(member.getTempatTanggalLahir() + "\t\t");
                System.out.println(member.getGender());
            }
        }

    }

    public static void editMember() {
        System.out.print("Masukkan ID Member yang ingin di ubah: ");
        int id = scanner.nextInt();

        MemberController.Edit(id);
    }

    public static void delete() {
        System.out.print("Masukkan ID Member yang ingin di hapus: ");
        int id = scanner.nextInt();

        MemberController.Delete(id);
    }

    public static void updateMember(Member member) {
        System.out.println("Update Member");
        System.out.print(member.getId() + "\t");
        System.out.print(member.getNama() + "\t\t");
        System.out.print(member.getUmur() + "\t\t");
        System.out.print(member.getTempatTanggalLahir() + "\t\t");
        System.out.println(member.getGender());

        System.out.println("Jika tidak ingin mengubah isi dengan -");
        String buffer = scanner.nextLine();
        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();

        System.out.print("Masukkan Umur: ");
        String umur = scanner.next();
        buffer = scanner.nextLine();

        System.out.print("Masukkan Tempat Tanggal Lahir: ");
        String tempatTglLahir = scanner.nextLine();

        System.out.print("Masukkan Jenis Kelamin: ");
        String jenisKelamin = scanner.nextLine();

        if (!nama.equalsIgnoreCase("-")) {
            member.setNama(nama);
        }
        if (!umur.equalsIgnoreCase("-")) {
            Integer umur2 = Integer.parseInt(umur);
            member.setUmur(umur2);
        }
        if (!tempatTglLahir.equalsIgnoreCase("-")) {
            member.setTempatTanggalLahir(tempatTglLahir);
        }

        if (!jenisKelamin.equalsIgnoreCase("-")) {
            member.setGender(jenisKelamin);
        }

        MemberController.Update(member);
    }
}
