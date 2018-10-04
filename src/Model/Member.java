package Model;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.*;
import java.util.ArrayList;

public class Member extends Person {

    private static final String JSON_PATH = "database\\members.json";

    private Integer id;

    public Member(String nama, Integer umur, String tempatTglLahir, String jenisKelamin) {
        this.setNama(nama);
        this.setTempatTanggalLahir(tempatTglLahir);
        this.setUmur(umur);
        this.setGender(jenisKelamin);
        this.id = getLastId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private static int getLastId() {
        JsonReader reader = getReader(JSON_PATH);
        Member[] members = gson.fromJson(reader, Member[].class);
        if (members == null) {
            return 1;
        } else {
            return (members[members.length - 1].getId()) + 1;
        }

    }

    private static void writeToJson(ArrayList<Member> members) {
        String jsonString = gson.toJson(members);

        FileWriter writer = getWriter(JSON_PATH);
        try {
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void store(Member member) throws IOException {

        BufferedReader buffer = new BufferedReader(new FileReader(JSON_PATH));
        if (buffer.readLine() == null) {
            ArrayList<Member> members_2 = new ArrayList<Member>();
            members_2.add(member);
            writeToJson(members_2);
        } else {
            JsonReader reader = getReader(JSON_PATH);
            ArrayList<Member> members = gson.fromJson(reader, new TypeToken<ArrayList<Member>>() {
            }.getType());
            members.add(member);
            writeToJson(members);
        }
    }

    public static ArrayList getAll() {
        BufferedReader buffer = null;
        ArrayList<Member> members = null;

        try {
            buffer = new BufferedReader(new FileReader(JSON_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (buffer.readLine() == null) {

                return members;
            } else {
                JsonReader reader = getReader(JSON_PATH);

                members = gson.fromJson(reader, new TypeToken<ArrayList<Member>>() {
                }.getType());

                return members;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void update(Member member) throws IOException {
        ArrayList<Member> members = getAll();

        int index = 0;

        for (Member mmbr : members) {
            if (mmbr.getId() == member.getId()) {
                members.set(index, member);
                break;
            }
            index++;
        }

        writeToJson(members);

    }

    public static void delete(int id) throws IOException {
        ArrayList<Member> members = getAll();

        int index = 0;
        for (Member mbr : members) {
            if (mbr.getId() == id) {
                members.remove(index);
                break;
            }
            index++;
        }

        writeToJson(members);

    }

    public static Member findId(int id) {
        ArrayList<Member> members = getAll();

        for (Member member : members) {
            if (member.getId() == id) {
                return member;
            }
        }

        return null;
    }

}
