package com.example.dylan.contactcardapp;
public class Person {
    private String title;
    private String first;
    private String last;
    private String location;
    private String timeZone;
    private String email;
    private String dateOfBirth;
    private String phone;
    private String cellphone;
    private String profileLarge;
    private String profileMedium;
    private String profileThumbNail;
    private int age;

    public Person(String title, String first, String last, String profileThumbNail, String profileMedium) {
        this.title = title;
        this.first = first;
        this.last = last;
        this.profileThumbNail = profileThumbNail;
        this.profileMedium = profileMedium;
    }
    public String getProfileThumbNail() {
        return profileThumbNail;
    }

    public String getProfileMedium() {
        return profileMedium;
    }
    @Override
    public String toString() {
        return title + " " + first + " " + last;
    }


}
