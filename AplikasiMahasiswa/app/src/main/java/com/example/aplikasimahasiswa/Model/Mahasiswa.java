package com.example.aplikasimahasiswa.Model;

import java.io.Serializable;
import java.util.Date;

public class Mahasiswa implements Serializable {
    private String _name;
    private String _nim;

    public String get_role() {
        return _role;
    }

    public void set_role(String _role) {
        this._role = _role;
    }

    private String _role;
    public String get_photo() {
        return _photo;
    }

    public void set_photo(String _photo) {
        this._photo = _photo;
    }

    private String _photo;
    public String get_nim() {
        return _nim;
    }

    public void set_nim(String _nim) {
        this._nim = _nim;
    }

    private String _email;
    private String _password;
    private String _gender;
    private String _jurusan;
    private Date _date;
    private String _placeOfBirth;
    private Float _ipk;
    private String _curriculumVitae;

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_gender() {
        return _gender;
    }

    public void set_gender(String _gender) {
        this._gender = _gender;
    }

    public String get_jurusan() {
        return _jurusan;
    }

    public void set_jurusan(String _jurusan) {
        this._jurusan = _jurusan;
    }

    public Date get_date() {
        return _date;
    }

    public void set_date(Date _date) {
        this._date = _date;
    }

    public String get_placeOfBirth() {
        return _placeOfBirth;
    }

    public void set_placeOfBirth(String _placeOfBirth) {
        this._placeOfBirth = _placeOfBirth;
    }

    public Float get_ipk() {
        return _ipk;
    }

    public void set_ipk(Float _ipk) {
        this._ipk = _ipk;
    }

    public String get_curriculumVitae() {
        return _curriculumVitae;
    }

    public void set_curriculumVitae(String _curriculumVitae) {
        this._curriculumVitae = _curriculumVitae;
    }
}
