package com.joyyou.zhaojiaqizhao.notes_login;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zhaojiaqizhao on 2017/1/22.
 */
class MyAdapter extends RecyclerView.Adapter {    //给recylerview 填充内容


    class ViewHolder extends RecyclerView.ViewHolder {
        private View root;

        private TextView title,text,author,createtime,updatetime;
        public ViewHolder(View root) {
            super(root);

            title = (TextView) root.findViewById(R.id.tv_title);
            text = (TextView) root.findViewById(R.id.tv_text);
            author = (TextView) root.findViewById(R.id.tv_author);
            createtime = (TextView) root.findViewById(R.id.tv_createtime);
            updatetime = (TextView)root.findViewById(R.id.tv_updatetime);

        }

        public TextView getTitle() {
            return title;
        }

        public TextView getText() {
            return text;
        }

        public TextView getAuthor() {
            return author;
        }

        public TextView getUpdatetime() {
            return updatetime;
        }

        public TextView getCreatetime() {
            return createtime;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //创建一个布局
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {   //绑定


        ViewHolder vh = (ViewHolder) holder;
        CellData cd = ViewMyNote.data[position];
        vh.getTitle().setText(cd.title);
        vh.getText().setText(cd.text);
        vh.getAuthor().setText(cd.author);
        vh.getCreatetime().setText(cd.createtime);
        vh.getUpdatetime().setText(cd.updatetime);

    }

    @Override
    public int getItemCount() {  //获取子对象的数量
        return ViewMyNote.celldata_len;
    }

    //如何从服务器获取数据
}
