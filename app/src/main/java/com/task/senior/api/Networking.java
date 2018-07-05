package com.task.senior.api;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.task.senior.Model.Item;
import com.task.senior.callback.OnFirebaseDataListener;
import com.task.senior.callback.OnFirebaseOperationListener;
import com.task.senior.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class Networking {

    public static void login(String email, String password, final OnFirebaseOperationListener onFirebaseOperationListener) {

        FirebaseHelper.getAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onFirebaseOperationListener.onSuccess();
                        } else {
                            onFirebaseOperationListener.onFailed(task.getException().getMessage());
                        }
                    }
                });
    }


    public static void signUp(String email, String password, final OnFirebaseOperationListener onFirebaseOperationListener) {

        FirebaseHelper.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onFirebaseOperationListener.onSuccess();
                        } else {
                            onFirebaseOperationListener.onFailed(task.getException().getMessage());
                        }
                    }
                });
    }


    public static void resetPassword(String email, final OnFirebaseOperationListener onFirebaseOperationListener) {

        FirebaseHelper.getAuth().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            onFirebaseOperationListener.onSuccess();
                        } else {
                            onFirebaseOperationListener.onFailed(task.getException().getMessage());
                        }
                    }
                });
    }

    public static void addItem(Item item, final OnFirebaseOperationListener listener) {

        String key = FirebaseHelper.getDatabase()
                .getReference().child(Constant.Firebase.ITEMS_NODE)
                .push().getKey();

        item.setKey(key);

        FirebaseHelper.getDatabase()
                .getReference().child(Constant.Firebase.ITEMS_NODE)
                .child(key)
                .setValue(item)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess();
                        } else {
                            listener.onFailed(task.getException().getMessage());
                        }
                    }
                });

    }

    public static void getAllItems(final OnFirebaseDataListener listener) {

        FirebaseHelper.getDatabase()
                .getReference()
                .child(Constant.Firebase.ITEMS_NODE)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Item> items = new ArrayList<>();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Item item = child.getValue(Item.class);
                            items.add(item);
                        }

                        listener.onSuccess(items);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        listener.onError(databaseError.getMessage());
                    }
                });

    }


    public static void deleteItem(final Item item, final OnFirebaseOperationListener listener) {
        FirebaseHelper.getDatabase()
                .getReference()
                .child(Constant.Firebase.ITEMS_NODE)
                .child(item.getKey())
                .setValue(null)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess();
                        } else {
                            listener.onFailed(task.getException().getMessage());
                        }
                    }
                });
    }

}
