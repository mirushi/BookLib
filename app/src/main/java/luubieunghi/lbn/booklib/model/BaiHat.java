package luubieunghi.lbn.booklib.model;

import java.io.Serializable;

public class BaiHat implements Serializable {
    private String tenBaiHat;
    private String tenCaSi;
    private String hinh;

    public BaiHat() {
    }

    public BaiHat(String tenBaiHat, String tenCaSi, String hinh) {
        this.tenBaiHat = tenBaiHat;
        this.tenCaSi = tenCaSi;
        this.hinh = hinh;
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

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}
