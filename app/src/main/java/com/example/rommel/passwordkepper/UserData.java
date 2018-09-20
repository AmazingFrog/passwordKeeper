package com.example.rommel.passwordkepper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class UserData {
    List<Record> l;
    JSONArray array;
    public UserData(){
        l = new LinkedList<>();
        array = new JSONArray();
    }
    public UserData(List<Record> li){
        l = li;
        array = new JSONArray();
        try{
            for(Record i:l){
                JSONObject temp = new JSONObject();
                temp.put("remake",i.getRemake());
                temp.put("password",i.getPassword());
                temp.put("name",i.getName());
                array.put(temp);
            }

        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    public List<Record> getL() {
        return l;
    }
    public void add(Record r){
        l.add(r);
        try{
            JSONObject a = new JSONObject();
            a.put("remake",r.getRemake());
            a.put("password",r.getPassword());
            a.put("name",r.getName());
            array.put(a);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void add(JSONObject a){
        array.put(a);
        try{
            l.add(new Record(a.getString("remake"),a.getString("password"),a.getString("name")));
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
    public void delete(Record d){
        for(Record i :l){
            if(i.equals(d)){
                l.remove(i);
            }
        }
        for(int i=0;i<array.length();++i){
            try{
                JSONObject temp = (JSONObject) array.get(i);
                if(temp.getString("remake").equals(d.getRemake()) && temp.getString("password").equals(d.getPassword()) && temp.getString("name").equals(d.getName())){
                    array.remove(i);
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }

        }
    }
    public void delete(JSONObject d){
        for(int i=0;i<array.length();++i){
            try {
                JSONObject temp = (JSONObject) array.get(i);
                if (temp.getString("remake").equals(d.getString("remake")) && temp.getString("password").equals(d.getString("password")) && temp.getString("name").equals(d.getString("name"))) {
                    array.remove(i);
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
        try{
            Record del = new Record(d.getString("remake"),d.getString("password"),d.getString("name"));
            for(Record i:l){
                if(i.equals(del)){
                    l.remove(i);
                }
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
}
