package com.example.beta;

import android.os.Parcel;
import android.os.Parcelable;

public class Kho implements Parcelable {
    private String maKho;
    private String tenKho;
    private String diaChi;
    private float taiTrong;
    private boolean isChecked;

    public Kho(String maKho, String tenKho, String diaChi, float taiTrong, boolean isChecked) {
        this.maKho = maKho;
        this.tenKho = tenKho;
        this.diaChi = diaChi;
        this.taiTrong = taiTrong;
        this.isChecked = isChecked;
    }

    public Kho() {}

    public String getMaKho() {
        return maKho;
    }

    public void setMaKho(String maKho) {
        this.maKho = maKho;
    }

    public String getTenKho() {
        return tenKho;
    }

    public void setTenKho(String tenKho) {
        this.tenKho = tenKho;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public float getTaiTrong() {
        return taiTrong;
    }

    public void setTaiTrong(float taiTrong) {
        this.taiTrong = taiTrong;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maKho);
        dest.writeString(tenKho);
        dest.writeString(diaChi);
        dest.writeFloat(taiTrong);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    protected Kho(Parcel in) {
        maKho = in.readString();
        tenKho = in.readString();
        diaChi = in.readString();
        taiTrong = in.readFloat();
        isChecked = in.readByte() != 0;
    }

    public static final Creator<Kho> CREATOR = new Creator<Kho>() {
        @Override
        public Kho createFromParcel(Parcel in) {
            return new Kho(in);
        }

        @Override
        public Kho[] newArray(int size) {
            return new Kho[size];
        }
    };
}
