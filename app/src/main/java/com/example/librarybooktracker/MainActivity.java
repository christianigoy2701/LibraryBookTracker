package com.example.librarybooktracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView titleBook, author, totalPrice;
    EditText bCodeInput, daysBorrowed;
    Button borrowbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bCodeInput = findViewById(R.id.bookCode);
        daysBorrowed = findViewById(R.id.daysBorrowed);
        titleBook = findViewById(R.id.titleBook);
        author = findViewById(R.id.author);
        totalPrice = findViewById(R.id.totalPrice);
        borrowbtn = findViewById(R.id.borrowbtn);

        borrowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bCode = bCodeInput.getText().toString();
                int numberOfDays = Integer.parseInt(daysBorrowed.getText().toString());

                Query premium = db.collection("Library Books").whereEqualTo("bookCode", bCode);
                premium.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            QuerySnapshot querySnapshot = task.getResult();
                            if(!querySnapshot.isEmpty()){
                                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                                String title = document.getString("title");
                                String author1 = document.getString("author");
                                Boolean isBorrowed = document.getBoolean("isBorrowed");

                                if(isBorrowed){
                                    Toast.makeText(MainActivity.this, "Book borrowed.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Book book = new Premium(bCode, title, author1);
                                    book.setDaysBorrowed(numberOfDays);

                                    double borrowCost = book.calculatePrice();

                                    if(titleBook != null){
                                        titleBook.setText(title);
                                    }

                                    if(author != null){
                                        author.setText(author1);
                                    }

                                    if(totalPrice != null){
                                        totalPrice.setText(String.valueOf(borrowCost));
                                    }
                                }
                            }
                            else{
                                Query regular = db.collection("Library Books").whereEqualTo("bookCode", bCode);
                                regular.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            QuerySnapshot querySnapshot1 = task.getResult();
                                            if(!querySnapshot1.isEmpty()){
                                                DocumentSnapshot document1 = querySnapshot1.getDocuments().get(0);
                                                String title1 = document1.getString("title");
                                                String author2 = document1.getString("author");
                                                Boolean isBorrowed1 = document1.getBoolean("isBorrowed");

                                                if(isBorrowed1){
                                                    Toast.makeText(MainActivity.this, "Book borrowed.", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    Book book = new Regular(bCode, title1, author2);
                                                    book.setDaysBorrowed(numberOfDays);

                                                    double borrowCost1 = book.calculatePrice();

                                                    if(titleBook != null){
                                                        titleBook.setText(title1);
                                                    }

                                                    if(author != null){
                                                        author.setText(author2);
                                                    }

                                                    if(totalPrice != null){
                                                        totalPrice.setText(String.valueOf(borrowCost1));
                                                    }
                                                    return;
                                                    }
                                                }

                                        }
                                        else{
                                            Toast.makeText(MainActivity.this, "Error fetching books.", Toast.LENGTH_SHORT).show();
                                        }
                                        Toast.makeText(MainActivity.this, "No records or book not found.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Error fetching books.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}