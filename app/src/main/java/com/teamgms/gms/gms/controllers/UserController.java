package com.teamgms.gms.gms.controllers;

import com.google.firebase.database.FirebaseDatabase;
import com.teamgms.gms.gms.models.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yunjeonghwang on 2017. 3. 3..
 */

public class UserController {

    public static void createUser(String providerId, String firebaseUid, String userEmail, String userName, String photoUrl) {
        User user = new User(providerId, firebaseUid, userEmail, userName, photoUrl);

        Map<String, Object> userValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/users/" + firebaseUid, userValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }

    public static void createUser(User user) {

        Map<String, Object> userValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/users/" + user.firebaseUid, userValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }
}
