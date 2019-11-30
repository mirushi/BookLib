package luubieunghi.lbn.booklib.Model;

import java.io.Serializable;

//dùng để test cho list view của activity list song

public class BaiHat implements Serializable {

    private String tenBaiHat;
    private String tenCaSi;
    private int hinh;
    private int resSourceId;


    public int getResSourceId() {
        return resSourceId;
    }

    public void setResSourceId(int resSourceId) {
        this.resSourceId = resSourceId;
    }

    public BaiHat() {
    }

    public BaiHat(String tenBaiHat, String tenCaSi, int hinh, int id) {
        this.tenBaiHat = tenBaiHat;
        this.tenCaSi = tenCaSi;
        this.hinh = hinh;
        this.resSourceId=id;
    }

    public String getTenBaiHat() {
        return tenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        this.tenBaiHat = tenBaiHat;
    }

    public String getTenCaSi() {
        return tenCaSi;
    }

    public void setTenCaSi(String tenCaSi) {
        this.tenCaSi = tenCaSi;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }
}
