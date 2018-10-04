package Controller;

import Model.Member;
import View.MemberView;
import java.io.IOException;
import java.util.ArrayList;

public class MemberController {

    public static void getMenu() {
        ArrayList<Member> members;
        try {
            members = Member.getAll();
            MemberView.memberShowAll(members);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MemberView.showMenu();

    }

    public static void Create() {
        MemberView.showCreateForm();
    }

    public static void Store(String nama, Integer jumlah, String tempatTglLahir, String jenisKelamin) {
        Member member = new Member(nama, jumlah, tempatTglLahir, jenisKelamin);
        try {
            Member.store(member);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getMenu();
    }

    public static void getEditForm() {
        MemberView.editMember();
    }

    public static void Edit(int id) {
        Member member;
        member = Member.findId(id);
        MemberView.updateMember(member);

    }

    public static void Update(Member member) {
        try {
            Member.update(member);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getMenu();
    }

    public static void Delete(int id) {
        try {
            Member.delete(id);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getMenu();
    }
}
