package com.example.listviewexcercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listviewexcercise.databinding.ActivityListviewBinding;
import com.example.listviewexcercise.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    ActivityListviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int[] imageId = {R.drawable.girl1 , R.drawable.girl2 , R.drawable.girl3 , R.drawable.girl4 , R.drawable.girl5 , R.drawable.boy1 , R.drawable.boy2};
        String[] name = {"Thanh Ngân" ,
                "Thanh Rít" ,
                "Thanh Nga" ,
                "Hoàng Yến" ,
                "Hoàng Nhi" ,
                "David Beckham" ,
                "Franking Danis"};
        String[] lastMessage = {"Hey yooo" ,
                "What's up man" ,
                "Chuyên sỉ lẻ quần áo" ,
                "Xin chào mọi người !" ,
                "Rất hân hạnh được làm quen ! " ,
                "My favorite is football , love !" ,
                "I'm a photographer !"};
        String[] lastmsgTime = {"4:45 pm" ,
                "4:46 pm",
                "8:30 am",
                "7:20 am",
                "7:50 am",
                "3:30 pm",
                "2:30 am"};
        String[] phoneNo = {"0977156563" , "0122777111", "0977156563" , "0977156563" , "0977156563" , "0977156563" , "0977156563"};
        String[] country = {"Viet Nam" , "Japanese" , "Viet Nam" , "Viet Nam" , "Viet Nam" , "England" , "Indi"};

        ArrayList<User> userArrayList = new ArrayList<>();

        for (int i = 0 ; i < imageId.length ; i ++) {
                User user = new User(name[i] , lastMessage[i] , lastmsgTime[i] , phoneNo[i] , country[i] , imageId[i]);
                userArrayList.add(user);
        }

        ListAdapter listAdapter = new ListAdapter(ListViewActivity.this , userArrayList);

        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);

        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ListViewActivity.this , UserActivity.class);
                intent.putExtra("name" , name[position]);
                intent.putExtra("phone" , phoneNo[position]);
                intent.putExtra("country" , country[position]);
                intent.putExtra("imageId" , imageId[position]);
                startActivity(intent);
            }
        });

    }





}
