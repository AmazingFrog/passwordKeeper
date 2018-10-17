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
        /*
        array = new JSONArray();
        try{
            for(Record i:l){
                JSONObject temp = new JSONObject();
                temp.put("remark",i.getRemark());
                temp.put("password",i.getPassword());
                temp.put("name",i.getName());
                array.put(temp);
            }

        }
        catch(JSONException e){
            e.printStackTrace();
        }
        */
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
}
