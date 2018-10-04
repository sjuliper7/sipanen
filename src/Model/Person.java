package Model;

public abstract class Person extends Model {

    private String nama;
    private String tempatTanggalLahir;
    private Integer umur;
    private String gender;

    public Person() {
    }

    public Person(String nama, String tempatTanggalLahir, Integer umur, String gender) {
        this.nama = nama;
        this.tempatTanggalLahir = tempatTanggalLahir;
        this.umur = umur;
        this.gender = gender;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTempatTanggalLahir() {
        return tempatTanggalLahir;
    }

    public void setTempatTanggalLahir(String tempatTanggalLahir) {
        this.tempatTanggalLahir = tempatTanggalLahir;
    }

    public Integer getUmur() {
        return umur;
    }

    public void setUmur(Integer umur) {
        this.umur = umur;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
