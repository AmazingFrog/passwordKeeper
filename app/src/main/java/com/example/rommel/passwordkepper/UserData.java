package com.example.rommel.passwordkepper;

import java.util.LinkedList;
import java.util.List;

public class UserData {
    List<Record> l;
    public UserData(){
        l = new LinkedList<>();
    }
    public UserData(List<Record> li){
        l = li;
    }

    public List<Record> getL() {
        return l;
    }
    public void add(Record r){
        l.add(r);
    }
    public void delete(Record d){
        for(Record i :l){
            if(i.equals(d)){
                l.remove(i);
            }
        }
    }
    public void update(int position,Record r){
        l.set(position,r);
    }
    public int getRecordId(int subscript){
        return l.get(subscript).getId();
    }
}
