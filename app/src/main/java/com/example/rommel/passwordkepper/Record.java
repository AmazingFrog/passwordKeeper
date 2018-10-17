package com.example.rommel.passwordkepper;

public class Record {
    private String remark;
    private String name;
    private String password;
    private int id;

    public Record(String r,String p,String n){
        this.remark = r;
        this.password = p;
        if(n.equals("")){
            this.name = "no_user_name";
        }
        else{
            this.name = n;
        }
    }
    public boolean equals(Record a){
        return this.remark.equals(a.getRemark()) && this.password.equals(a.getPassword()) && this.name.equals(a.getName());
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getRemark() {
        return remark;
    }
    public int getId(){
        return id;
    }
    public void setId(int i){
        id = i;
    }
}
