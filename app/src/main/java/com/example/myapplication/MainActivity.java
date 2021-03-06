package com.example.myapplication;

import static com.example.myapplication.Common.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.Contact.Contact;
import com.example.myapplication.Gallery.GalleryAdapter;
import com.example.myapplication.Gallery.ImageFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initVal();
        initGallery();
    }

    void initVal() {
        allContacts = new ArrayList<>();
        mContactList = new ArrayList<>();
        stack_page = new Stack<>();
        stack_page.push(0);
        currentTab = 0;
        findMaxIdNum();
        getContacts();

        mGalleryList = new ArrayList<>();
        getGallery();
        initGallery();
    }

    private void findMaxIdNum() {
        int maxId = -1;
        if(allContacts != null && allContacts.size() != 0) {
            for(Contact contact: allContacts) {
                if(maxId < contact.getId())
                    maxId = contact.getId();
            }
        }
        id_num = maxId+1;
    }

    private void getContacts() {
        String contactsJsonString = readContactJson();
        if(!contactsJsonString.equals("")) {
            allContacts = parseContact(contactsJsonString);
            contactCopy(allContacts, mContactList);
        }
    }


    private String readContactJson() {
        try {
            // 파일 이름을 이용해 우리가 원하는 파일을 가져옴.
            File file = getFileStreamPath(CONTACT_JSON_FILE_NAME);

            // 만약 파일이 존재한다면
            if(file.exists()) {

                // 아래와 같은 코드를 통해 파일을 버퍼로 받아들이기.
                FileInputStream is = openFileInput(CONTACT_JSON_FILE_NAME);
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bufferedReader = new BufferedReader(isr);

                // 읽어온 문자열을 저장할 StringBuilder
                StringBuilder sb = new StringBuilder();

                // bufferedReader에서 한 줄씩 읽어올 때, 마지막 줄 이후이면 null 반환.
                // 즉 line이 null이면 끝까지 다 읽은 것.
                // line을 StringBuilder 변수에 append해주고 줄바꿈 넣어주기. (줄바꿈은 읽어들이지 않기 떄문)
                while(true) {
                    String line = bufferedReader.readLine();
                    if(line == null) break;
                    sb.append(line).append("\n");
                }

                // 완성된 StringBuilder를 String으로 만들어준 후 반환.
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 파일이 존재하지 않거나, 파일을 열고 읽는데 에러가 났을 경우에는 빈 문자열 반환.
        return "";
    }

    // Json 파일에서 읽어온 String 을 이용해 Contact 형식에 맞게 저장하여 ArrayList 반환하는 함수.
    private ArrayList<Contact> parseContact(String json) {
        try {
            ArrayList<Contact> newArrayList = new ArrayList<>(); // 반환할 ArrayList 변수
            JSONObject jsonObject = new JSONObject(json); // json String 을 이용해 JSONObject 를 만듦.
            JSONArray contactArray = jsonObject.getJSONArray("contacts"); // 위에서 만든 JSONObject에서 contacts 의 내용만 가져옴. (배열이라 JSONArray)

            // JSONArray 에 들어있는 개수만큼
            for(int i = 0; i < contactArray.length(); ++i) {
                JSONObject contactObj = contactArray.getJSONObject(i); // i 번째 배열 요소를 가져옴.

                String name = contactObj.getString("name"); // nameField
                String phone = contactObj.getString("phone"); // phoneField
                String tags = contactObj.getString("tags"); // tagField
                String profileImage = contactObj.getString("profileImage"); // profileImageField
                String lastMeet = contactObj.getString("lastMeet"); // lastMeetField
                boolean isStar = contactObj.getBoolean("isStar");

                Contact data = new Contact(id_num++, name, phone, tags, profileImage, lastMeet, isStar); // 위에서 얻어온 field를 통해 Contact 변수 생성

                newArrayList.add(data); // 반환할 ArrayList 변수에 add.
            }
            
            return newArrayList; // ArrayList 반환
        } catch(JSONException e) { // parsing 하다가 에러가 날 경우 null 반환.
            e.printStackTrace();
            return null;
        }
    }

    private void initGallery() {
//        mGalleryList = Common.initGallery();
        galleryAdapter = new GalleryAdapter(mGalleryList, this);

    }

    private void findMaxIdGallery() {
        int maxId = -1;
        for(ImageFile imageFile: mGalleryList) {
            if(maxId < imageFile.getId())
                maxId = imageFile.getId();
        }
        id_num_img = maxId+1;
    }

    private void getGallery() {
        String galleryJsonString = readGalleryJson();
        if(!galleryJsonString.equals("")) {
            mGalleryList = parseImageFile(galleryJsonString);
        }
        findMaxIdGallery();
        // String contactsJsonString = getJsonString(this, CONTACT_JSON_FILE_NAME);
    }

    private String readGalleryJson() {
        try {
            File file = getFileStreamPath(GALLERY_JSON_FILE_NAME);

            if(file.exists()) {
                FileInputStream is = openFileInput(GALLERY_JSON_FILE_NAME);
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();

                while(true) {
                    String line = bufferedReader.readLine();
                    if(line == null) break;
                    sb.append(line).append("\n");
                }

                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private ArrayList<ImageFile> parseImageFile(String json) {
        try {
            ArrayList<ImageFile> newArrayList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray galleryArray = jsonObject.getJSONArray("gallery");

            for(int i = 0; i < galleryArray.length(); ++i) {
                JSONObject galleryObj = galleryArray.getJSONObject(i);

                String name = galleryObj.getString("name");
                int image = galleryObj.getInt("image");
                int tag = galleryObj.getInt("tag");
                String date = galleryObj.getString("date");
                int f1 = galleryObj.getInt("f1");
                int f2 = galleryObj.getInt("f2");
                int f3 = galleryObj.getInt("f3");
                int f4 = galleryObj.getInt("f4");

                ImageFile data = new ImageFile(id_num_img++, name, image, tag, date, f1, f2, f3, f4);

                newArrayList.add(data);
            }

            return newArrayList;
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}