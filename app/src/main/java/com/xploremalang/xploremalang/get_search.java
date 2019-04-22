package com.xploremalang.xploremalang;

import com.xploremalang.xploremalang.AccountActivity.User;

public class get_search {


    public String namakonten,deksripsikonten,image;


    public get_search(){

    }

    public String getNamakonten(){
        return namakonten;
    }

    public void setNamakonten(String namakonten) {
        this.namakonten = namakonten;
    }

    public String getDeksripsikonten(){
        return deksripsikonten;
    }
    public void setDeksripsikonten(String deksripsikonten){
        this.deksripsikonten = deksripsikonten;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public get_search(String namakonten, String deksripsikonten, String fotokonten){
        this.namakonten = namakonten;
        this.deksripsikonten = deksripsikonten;
        this.image = image;

    }

}
