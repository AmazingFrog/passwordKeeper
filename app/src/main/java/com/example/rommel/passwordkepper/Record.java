package com.example.rommel.passwordkepper;

public class Record {
    private String remake;
    private String name;
    private String password;

    public Record(String r,String p,String n){
        this.remake = r;
        this.password = p;
        if(n.equals("")){
            this.name = "no_user_name";
        }
        else{
            this.name = n;
        }
    }
    public boolean equals(Record a){
        return this.remake.equals(a.getRemake()) && this.password.equals(a.getPassword()) && this.name.equals(a.getName());
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getRemake() {
        return remake;
    }
}
