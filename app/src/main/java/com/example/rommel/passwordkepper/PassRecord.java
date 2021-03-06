package com.example.rommel.passwordkepper;

import android.os.Parcel;
import android.os.Parcelable;

public class PassRecord implements Parcelable {
    private String remark;
    private String name;
    private String password;
    public PassRecord(){
        this.remark = "";
        this.name = "no_user_name";
        this.password = "";
    }
    public PassRecord(String r,String p,String n){
        this.remark = r;
        this.password = p;
        this.name = n;
    }
    public PassRecord(Record r){
        this.remark = r.getRemark();
        this.password = r.getPassword();
        this.name = r.getName();
    }
    protected PassRecord(Parcel in){
        this.remark = in.readString();
        this.password = in.readString();
        this.name = in.readString();
    }
    public String getRemark() {
        return remark;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

    public static final Creator<PassRecord> CREATOR = new Creator<PassRecord>() {
        @Override
        public PassRecord createFromParcel(Parcel in) {
            return new PassRecord(in);
        }
        @Override
        public PassRecord[] newArray(int size) {
            return new PassRecord[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.remark);
        parcel.writeString(this.password);
        parcel.writeString(this.name);
    }
}
