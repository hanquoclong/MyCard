package com.example.korealong.qrcodescanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.korealong.qrcodescanner.Adapter.SearchAdapter;
import com.example.korealong.qrcodescanner.Databases.DatabaseGetData;
import com.example.korealong.qrcodescanner.Sqlite.AdapterCard;
import com.example.korealong.qrcodescanner.Sqlite.Database;
import com.example.korealong.qrcodescanner.Sqlite.UserCard;
import com.google.firebase.database.DatabaseReference;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    public static RecyclerView lvShow;
    private ProgressBar circular_progress;

    final String DATABASE_NAME = "MCardDB.sqlite";
    //SQLiteDatabase database;
    ArrayList<UserCard> listUC;
    AdapterCard adapterCard;


    DatabaseReference dref;

    List<User> list = new ArrayList<>();
    ListViewAdapter adapter;

    MaterialSearchBar searchBar;
    DatabaseGetData databaseGetData;

    private RecyclerView.LayoutManager layoutManager;
    SearchAdapter searchAdapter;
    List<String> suggestList = new ArrayList<>();
    // Create ImageButton on title bar(optionmenu)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_first,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("QRCode Scanner");
        setSupportActionBar(toolbar);

        addControl();
        //addItemClickListView();
        addListview_SQLite();
        //readData_SQLite();
        //addListview_FB();
        //0986780999

        //lvAdapter = new ListViewAdapter(MainActivity.this,list_users);
        //list_data.setAdapter(lvAdapter);
        /*list_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User)parent.getItemAtPosition(position);
                selectedUser = user;
                edtName.setText(user.getName());
                edtNumber.setText(user.getNumber());
            }
        });*/
        //initFirebase();
        //addEventFirebaseListener();



        layoutManager = new LinearLayoutManager(this);
        lvShow.setLayoutManager(layoutManager);
        searchAdapter = new SearchAdapter(this,listUC);
        lvShow.setAdapter(searchAdapter);

        loadSuggestList();
        addEventSearchBar();

    }

    private void loadSuggestList() {
        suggestList = databaseGetData.getNameCard();
        searchBar.setLastSuggestions(suggestList);
    }

    private void addEventSearchBar() {
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for (String search:suggestList)
                {
                    if (search.toLowerCase().contains(searchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                searchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                searchAdapter = new SearchAdapter(getBaseContext(),databaseGetData.getCard());
                lvShow.setAdapter(searchAdapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
        searchAdapter = new SearchAdapter(this,databaseGetData.getCard());
        lvShow.setAdapter(searchAdapter);
    }

    private void startSearch(String text) {
        searchAdapter = new SearchAdapter(this,databaseGetData.getCardByName(text));
        lvShow.setAdapter(searchAdapter);
    }

    /*private void addItemClickListView() {
        lvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserCard userCard = listUC.get(position);
                Intent intent = new Intent(MainActivity.this,Detail.class);
                intent.putExtra("ID",userCard.id);
                startActivity(intent);
            }
        });
    }*/
    private void addListview_SQLite() {
        listUC = new ArrayList<>();
        adapterCard = new AdapterCard(this,listUC);
        lvShow.setAdapter(searchAdapter);
    }
    /*private void addListview_FB() {
        adapter = new ListViewAdapter(this,list);
        lvShow.setAdapter(adapter);
        dref = FirebaseDatabase.getInstance().getReference();
        dref.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User value = dataSnapshot.getValue(User.class);
                list.add(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    private void addControl() {

        searchBar = findViewById(R.id.search_bar);
        searchBar.setHint("Search");
        searchBar.setCardViewElevation(10);

        databaseGetData = new DatabaseGetData(this);

        lvShow = (RecyclerView) findViewById(R.id.lvShow);
        lvShow.setHasFixedSize(true);

        circular_progress = (ProgressBar) findViewById(R.id.circular_progress);



    }

    /*private void addEventFirebaseListener() {
        circular_progress.setVisibility(View.VISIBLE);
        list_data.setVisibility(View.INVISIBLE);

        mDatabaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(list_users.size()>0)
                    list_users.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    list_users.add(user);
                }
                ListViewAdapter adapter = new ListViewAdapter(MainActivity.this,list_users);
                list_data.setAdapter(adapter);
                circular_progress.setVisibility(View.INVISIBLE);
                list_data.setVisibility(View.VISIBLE);
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
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuAddFirst)
        {
            Intent intent = new Intent(MainActivity.this,Add.class);
            startActivity(intent);
        }
        return true;
    }

    /*private void deleteUser(User selectedUser) {
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
    }

    private void clearEditText() {
        edtName.setText("");
        edtNumber.setText("");
    }*/

}
