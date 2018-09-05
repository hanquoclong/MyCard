package com.example.korealong.qrcodescanner;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.korealong.qrcodescanner.Sqlite.AdapterCard;
import com.example.korealong.qrcodescanner.Sqlite.Database;
import com.example.korealong.qrcodescanner.Sqlite.UserCard;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class Add extends AppCompatActivity {

    public static Button  btnCreate;
    public static ImageButton btnScan;
    public static EditText edtName,edtNumber;
    public static ImageView imgShow;
    private User selectedUser;
    //private ListView list_data;

    AdapterCard adapterCard;
    ArrayList<UserCard> listUC;
    public static List<User> list_users = new ArrayList<>();
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;

    final String DATABASE_NAME = "MCardDB.sqlite";
    int i = -1;
    // Create ImageButton on title bar(option menu)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);



        addToolbar();
        addControls();
        //addItemClickListView();

        addEventButton();
        addEventEditText();
        //initFirebase();
        //addEventFirebaseListener();

        //initUI();

    }

    private void initUI() {
        Intent intent = getIntent();
        i = intent.getIntExtra("ID",-1);
        i = i+1;
        SQLiteDatabase database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Card WHERE id = ?",new String[]{i +""});
        cursor.moveToFirst();
        String nameCard = cursor.getString(1);
        String nameStore = cursor.getString(2);
        byte[] image = cursor.getBlob(3);

        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        imgShow.setImageBitmap(bitmap);
        edtName.setText(nameCard);
        edtNumber.setText(nameStore);
    }

    private byte[] getByteArrayFromImageView(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    private void addToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Add Barcode");
        setSupportActionBar(toolbar);
    }

//    private void addItemClickListView() {
//        list_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                User user = (User)parent.getItemAtPosition(position);
//                selectedUser = user;
//                edtName.setText(user.getName());
//                edtNumber.setText(user.getNumber());
//            }
//        });
//    }

    private void addControls() {
        btnScan = (ImageButton) findViewById(R.id.btnScan);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        edtName = (EditText) findViewById(R.id.name);
        edtNumber = (EditText) findViewById(R.id.number);
        imgShow = (ImageView) findViewById(R.id.imgShow);
        //list_data = (ListView) findViewById(R.id.list_data);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuAdd)
        {
            //createUser();
            addUser_Sqlite();
        }else if(item.getItemId() == R.id.mnuSave)
        {
            //User user = new User(selectedUser.getId(),edtName.getText().toString(),edtNumber.getText().toString());
            //UpdateUser(user);
            updateUser_Sqlite();
        }else if (item.getItemId() == R.id.mnuDel)
        {
            //deleteUser(selectedUser);
            //Delete_SQLite();

        }
        return true;
    }

    private void Delete_SQLite() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Delete this?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserCard userCard = listUC.get(which);
                //deleteUser_Sqlite(userCard.id);
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteUser_Sqlite(int idCard) {
        SQLiteDatabase database = Database.initDatabase(this, "databases/MCardDB.sqlite");
        database.delete("Card", "id = ?", new String[]{idCard + ""});
        listUC.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM Card",null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nameCard = cursor.getString(1);
            String nameStore = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            listUC.add(new UserCard(id,nameCard,nameStore,image));
        }
        adapterCard.notifyDataSetChanged();
    }

    private void updateUser_Sqlite(){
        String nameCard = edtName.getText().toString();
        String nameStore = edtNumber.getText().toString();
        byte[] image = getByteArrayFromImageView(imgShow);

        ContentValues contentValues = new ContentValues();
        contentValues.put("nameCard",nameCard);
        contentValues.put("nameStore",nameStore);
        contentValues.put("image",image);
        SQLiteDatabase database = Database.initDatabase(this, "MCardDB.sqlite");
        database.update("Card",contentValues,"id = ?",new String[]{ i + ""});
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    private void addUser_Sqlite() {
        String nameCard = edtName.getText().toString();
        String nameStore = edtNumber.getText().toString();
        byte[] image = getByteArrayFromImageView(imgShow);

        ContentValues contentValues = new ContentValues();
        contentValues.put("nameCard",nameCard);
        contentValues.put("nameStore",nameStore);
        contentValues.put("image",image);

        SQLiteDatabase database = Database.initDatabase(this, "MCardDB.sqlite");
        database.insert("Card",null,contentValues);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void deleteUser(User selectedUser) {
        mDatabaseReference.child("users").child(selectedUser.getId()).removeValue();
        clearEditText();
    }

    private void UpdateUser(User user) {
        mDatabaseReference.child("users").child(user.getId()).child("name").setValue(user.getName());
        mDatabaseReference.child("users").child(user.getId()).child("number").setValue(user.getNumber());
        clearEditText();
    }

    private void createUser() {
        User user = new User(UUID.randomUUID().toString(),edtName.getText().toString(),edtNumber.getText().toString());
        mDatabaseReference.child("users").child(user.getId()).setValue(user);
        clearEditText();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void clearEditText() {
        edtName.setText("");
        edtNumber.setText("");
    }

    private void addEventFirebaseListener() {
        mDatabaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(list_users.size()>0)
                    list_users.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    list_users.add(user);
                }
                ListViewAdapter adapter = new ListViewAdapter(Add.this,list_users);
                //list_data.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

    private void addEventEditText() {
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length()==0)
                {
                    btnScan.setVisibility(View.VISIBLE);
                }else
                {
                    btnScan.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void addEventButton() {

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this,Scan.class);
                startActivity(intent);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text2Qr = edtName.getText().toString().trim();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.CODE_128, 200, 50);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imgShow.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
