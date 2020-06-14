package com.seferapp.animals_and_pepol_app;

public class kapi_durumu {
  private Integer Id;
   private Integer Door_case;
    private String Person_photo;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getDoor_case() {
        return Door_case;
    }

    public void setDoor_case(Integer door_case) {
        Door_case = door_case;
    }

    public String getPerson_photo() {
        return Person_photo;
    }

    public void setPerson_photo(String person_photo) {
        Person_photo = person_photo;
    }

    public kapi_durumu(Integer id, Integer door_case, String person_photo) {
        Id = id;
        Door_case = door_case;
        Person_photo = person_photo;
    }
    public kapi_durumu() {

    }
}
