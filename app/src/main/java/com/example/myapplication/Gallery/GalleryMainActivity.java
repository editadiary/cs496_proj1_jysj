package com.example.myapplication.Gallery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Common;
import com.example.myapplication.R;

import static com.example.myapplication.Common.GALLERY_JSON_FILE_NAME;
import static com.example.myapplication.Common.galleryToJson;
import static com.example.myapplication.Common.goIntent;
import static com.example.myapplication.Common.mGalleryList;
import static com.example.myapplication.Common.galleryAdapter;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GalleryMainActivity extends AppCompatActivity {

    GridView gridView;
    ImageView addImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_main);

        gridView = findViewById(R.id.gridView);

        gridView.setAdapter(galleryAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goIntent(7);
                startActivity(new Intent(GalleryMainActivity.this,
                        ClickedItemActivity.class).putExtra("index", i));


            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(GalleryMainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Gallery");
                builder.setMessage("Delete "+mGalleryList.get(i).getName()+"?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                galleryAdapter.deleteItem(i);
                                galleryWrite();
                                Toast.makeText(getApplicationContext(), "???????????? ?????????????????????", Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });

        addImage = findViewById(R.id.ic_plus);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Not implemented yet

                mGalleryList.add(new ImageFile(0,"?????????", R.drawable.image1, 4, "20180702", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(1,"????????????", R.drawable.image2, 4, "20190701", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(2,"???????????????", R.drawable.image3, 2, "20200627", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(3,"?????????", R.drawable.image4, 2, "20210404", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(4,"?????????", R.drawable.image5, 0, "20211111", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(5,"?????????", R.drawable.image6, 3, "20211201", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(6,"???????????????", R.drawable.image7, 3, "20211203", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(7,"?????????", R.drawable.image8, 3, "20220110", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(8,"????????????", R.drawable.image9, 0, "20220212", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(9,"??????????????????", R.drawable.image10, 0, "20220330", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(10,"??????", R.drawable.image11, 0, "20220524", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(11,"????????????", R.drawable.image12, 4, "20220611", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(12,"???????????????", R.drawable.image14, 0, "20220629", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(13,"???????????????", R.drawable.image15, 0, "20220701", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(14,"??????", R.drawable.image16, 0, "20220702", -1, -1, -1, -1));

                galleryWrite();
                galleryAdapter.notifyDataSetChanged();

            }
        });
    }
    @Override
    public void onBackPressed() {
        Common.toPrev(this);
    }
    private void galleryWrite() {
        try{
            FileOutputStream os = openFileOutput(GALLERY_JSON_FILE_NAME, MODE_PRIVATE);
            JSONObject jsonFile = galleryToJson();

            assert jsonFile != null;
            os.write(jsonFile.toString().getBytes());
            os.flush();
            os.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}


