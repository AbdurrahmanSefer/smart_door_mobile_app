package com.seferapp.animals_and_pepol_app;

public class door_hestory {
   private   Integer Id;
   private String person_photo;
    private String Time ;
   private Integer Door_case;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getPerson_photo() {
        return person_photo;
    }

    public void setPerson_photo(String person_photo) {
        this.person_photo = person_photo;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public Integer getDoor_case() {
        return Door_case;
    }

    public void setDoor_case(Integer door_case) {
        Door_case = door_case;
    }
    public door_hestory() {

    }
    public door_hestory(Integer id, String person_photo, String time, Integer door_case) {
        Id = id;
        this.person_photo = person_photo;
        Time = time;
        Door_case = door_case;
    }
}
