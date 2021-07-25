package com.example.listviewexcercise;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.listviewexcercise.databinding.ActivityListviewBinding;
import com.example.listviewexcercise.databinding.ActivityMainBinding;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<User> list;
    ListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        gridView = (GridView)  findViewById(R.id.listview);

        list = new ArrayList<>();
        adapter = new ListAdapter(this , R.layout.list_item , list);

        gridView.setAdapter(adapter);

        //get data from sqlite

        Cursor cursor = AddUserActivity.sqLiteHelper.getData("SELECT * FROM USER");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);

            String name = cursor.getString(1);
            String lastMessage = cursor.getString(2);

            byte[] image = cursor.getBlob(3);

            list.add(new User(id , name , lastMessage , image ));
        }
        adapter.notifyDataSetChanged();

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CharSequence[] items = {"Update" , "Delete"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(ListViewActivity.this);

                dialog.setTitle("Choose an action ");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if(item == 0) {
                            //update
                            Cursor c = AddUserActivity.sqLiteHelper.getData("SELECT ID FROM USER");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();

                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            //show dialog update
                            showDialogUpdate(ListViewActivity.this , arrID.get(position));

                        } else {
                            //delete
                            Cursor c = AddUserActivity.sqLiteHelper.getData("SELECT ID FROM USER");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();

                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }

                    }
                });
                dialog.show();
                return true;
            }
        });
    }
    ImageView imageView;
    private void showDialogUpdate(Activity activity ,int position){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.activity_user);
        dialog.setTitle("Update");

        imageView = (ImageView) dialog.findViewById(R.id.image_update);
        final EditText edtName = (EditText) dialog.findViewById(R.id.name_update);
        final EditText edtLastmessage = (EditText) dialog.findViewById(R.id.lastMessage_update);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);

        //Set width Dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.9);
        //Set height
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.75 );
        dialog.getWindow().setLayout(width , height);

        dialog.show();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ListViewActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

         btnUpdate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 try {
                     AddUserActivity.sqLiteHelper.updateData(
                             edtName.getText().toString().trim(),
                             edtLastmessage.getText().toString().trim(),
                             AddUserActivity.imageViewToByte(imageView),
                             position
                     );
                     dialog.dismiss();
                     Toast.makeText(getApplicationContext(), "Upgrade Successfully!", Toast.LENGTH_SHORT).show();
                 }
                 catch (Exception error){
                     Log.e("Update error : " , error.getMessage());
                 }
                 updateUserList();
             }
         });
    }

    private void showDialogDelete(final int idUser) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ListViewActivity.this);

        dialogDelete.setTitle("Warning !");
        dialogDelete.setMessage("Are U sure about DELETE action ?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    AddUserActivity.sqLiteHelper.deleteData(idUser);
                    Toast.makeText(getApplicationContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateUserList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if (grantResults.length  > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent , 888);
            }

            else {
                Toast.makeText(getApplicationContext(), "You don't have permisstion to access file location! " , Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();


            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUserList(){
        // get all data from sqlite
        Cursor cursor = AddUserActivity.sqLiteHelper.getData("SELECT * FROM USER");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String lastMessage = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new User(id, name, lastMessage, image));
        }
        adapter.notifyDataSetChanged();
    }
}
