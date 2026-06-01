package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;
    private int tamil;
private int english;
private int maths;
private int science;
private int social;

    private String gender;
    @Column(name = "reg_no")
    private String regNo;
    private String password;
  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}
public int getTamil() {
return tamil;
}

public void setTamil(int tamil) {
this.tamil = tamil;
}

public int getEnglish() {
return english;
}

public void setEnglish(int english) {
this.english = english;
}

public int getMaths() {
return maths;
}

public void setMaths(int maths) {
this.maths = maths;
}

public int getScience() {
return science;
}

public void setScience(int science) {
this.science = science;
}

public int getSocial() {
return social;
}

public void setSocial(int social) {
this.social = social;
}

}
