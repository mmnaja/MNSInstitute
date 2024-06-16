package utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import entities.MyClass;
import entities.User;


public class FirebaseUtils {
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;
    DatabaseReference usersTable;

    //Init firebase params.
    public FirebaseUtils() {
        //instantiate database connection
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        usersTable = FirebaseDatabase.getInstance().getReference("users");
        user = auth.getCurrentUser();
    }

    public void saveUser(String name, String grade, String phoneNo, String email, String postalAddress, String role) {
        String uID = user.getUid();
        User user = new User(uID, name, grade, phoneNo, email, postalAddress, role);
        usersTable.child(uID).setValue(user);
    }

    public boolean authEmailUser(String email, String password) {
        AtomicBoolean isAuthenticated = new AtomicBoolean(false);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        authResult -> {
                            isAuthenticated.set(authResult.isSuccessful());
                        });
        return isAuthenticated.get();
    }

    public void addNewClass (MyClass myClass) {
        db.collection("class")
                .add(myClass)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Firestore", "DocumentSnapshot added with ID: "
                                + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error adding document", e);
                    }
                });
    }


    public void saveUserProfileData(User user) {
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Firestore", "DocumentSnapshot added with ID: "
                                + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error adding document", e);
                    }
                });
    }

    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                users.add(user);
                                Log.d("Firestore", "User: " + user.getName() + ", Email: " + user.getEmail());
                            }
                        } else {
                            Log.w("Firestore", "Error getting documents.", task.getException());
                        }
                    }
                });
        return users;
    }

    public void updateData() {
        DocumentReference docRef = db.collection("users").document("user_id");

        // Set the "born" field of the user
        docRef.update("born", 1816)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error updating document", e);
                    }
                });
    }

    public void deleteData() {
        DocumentReference docRef = db.collection("users").document("user_id");

        // Delete the document
        docRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error deleting document", e);
                    }
                });
    }


}
