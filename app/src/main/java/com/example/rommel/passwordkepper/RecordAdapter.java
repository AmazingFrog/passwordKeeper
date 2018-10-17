package com.example.rommel.passwordkepper;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    List<Record> l;

    static class ViewHolder extends RecyclerView.ViewHolder{
        Button deleteButton;
        Button showButton;
        TextView remarkMessage;
        public ViewHolder(View v){
            super(v);
            deleteButton = v.findViewById(R.id.delete);
            showButton = v.findViewById(R.id.show);
            remarkMessage = v.findViewById(R.id.remark);
        }
    }
    public RecordAdapter(List<Record> li) {
        l = li;
    }

    /**
     * 在position位置显示添加动画
     * @param position “添加”动画要展示的位置
     */
    public void add(int position){
        notifyItemInserted(position);
    }

    /**
     * 在position位置显示删除动画
     * @param position “删除“动画要展示的位置
     */
    public void delete(int position){
        notifyItemRemoved(position);
    }

    /**
     * 在position位置显示改变动画
     * @param position ”改变“动画要展示的位置
     */
    public void change(int position){
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recode,parent,false);
        final ViewHolder holder = new ViewHolder(v);
        holder.showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Record re = l.get(position);
                Intent intent = new Intent(v.getContext(),ShowDataActivity.class);
                PassRecord passRecord = new PassRecord(re);
                intent.putExtra("data_pass",passRecord);
                intent.putExtra("subscript",position);
                ((Activity)v.getContext()).startActivityForResult(intent,Define.DATA_UPDATE);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Record re = l.get(position);
                position = l.indexOf(re);
                l.remove(re);
                MainActivity.db.del(re);
                delete(position);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Record r = l.get(position);
        holder.remarkMessage.setText(r.getRemark());
    }
    @Override
    public int getItemCount(){
        return l.size();
    }

}
