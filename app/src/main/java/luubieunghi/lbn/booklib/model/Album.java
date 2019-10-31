package luubieunghi.lbn.booklib.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
    private String tenAlbum;
    private ArrayList<BaiHat> dsBaiHat=new ArrayList<>();

    private void themBaiHat(BaiHat baihat){
        dsBaiHat.add(baihat);
    }

    public Album() {
    }

    public Album(String tenAlbum, ArrayList<BaiHat> dsBaiHat) {
        this.tenAlbum = tenAlbum;
        this.dsBaiHat = dsBaiHat;
    }

    public String getTenAlbum() {
        return tenAlbum;
    }

    public void setTenAlbum(String tenAlbum) {
        this.tenAlbum = tenAlbum;
    }

    public ArrayList<BaiHat> getDsBaiHat() {
        return dsBaiHat;
    }

    public void setDsBaiHat(ArrayList<BaiHat> dsBaiHat) {
        this.dsBaiHat = dsBaiHat;
    }
}
