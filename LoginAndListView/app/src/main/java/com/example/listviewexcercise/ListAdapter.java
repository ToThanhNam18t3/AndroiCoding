package com.example.listviewexcercise;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<User> userList;

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    public ListAdapter(Context context, int layout, ArrayList<User> userList) {
        this.context = context;
        this.layout = layout;
        this.userList = userList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView personName, lastMessage;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout , null);

            holder.personName = (TextView) row.findViewById(R.id.personName);
            holder.lastMessage = (TextView) row.findViewById(R.id.lastMessage);
            holder.imageView = (ImageView) row.findViewById(R.id.profile_picture);
            row.setTag(holder);
        }

        else {
            holder = (ViewHolder) row.getTag();
        }

        User user = userList.get(position);

        holder.personName.setText(user.getName());
        holder.lastMessage.setText(user.getLastMessage());

        byte[] userImage = user.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(userImage , 0 , userImage.length);
        holder.imageView.setImageBitmap(bitmap);


        return row;
    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        User user = getItem(position);
//
//        if(convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item , parent , false);
//        }
//
//        ImageView imageView = convertView.findViewById(R.id.profile_picture);
//        TextView username = convertView.findViewById(R.id.personName);
//        TextView lastMsg = convertView.findViewById(R.id.lastMessage);
//        TextView time = convertView.findViewById(R.id.msgtime);
//
//        imageView.setImageResource(user.imageId);
//        username.setText(user.name);
//        lastMsg.setText(user.lastMessage);
//
//
//
//        return convertView;
//    }

//    public ListAdapter(Context context , ArrayList<User> userArrayList) {
//        super(context , R.layout.list_item , userArrayList);
//    }
}
