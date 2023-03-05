//package com.example.signupsignin;
//
//import static android.content.ContentValues.TAG;
//
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public
//class Notification {
//    FirebaseAuth ma =FirebaseAuth.getInstance();
////    Log.e(TAG, "onCreate: "+ma.getCurrentUser() );
//    public void Notification(String hisID) {
//    }
//            FirebaseDatabase.getInstance().getReference("Users/"+id).child("token")
//                    .addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public
//                        void onDataChange(@NonNull DataSnapshot snapshot) {
//                            String token= snapshot.getValue().toString();
//                            Log.e(TAG, "onDataChange: "+token );
//                            prepareforNotification(titleTxt,message,token);
//
//                        }
//
//                        @Override
//                        public
//                        void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//        }
//    });
//
//}
//
//
//    private
//    void prepareforNotification(String titleTxt, String message, String token) {
//        JSONObject to =new JSONObject();
//        JSONObject data =new JSONObject();
//        try {
//            data.put("title",titleTxt);
//            data.put("message",message);
//            to.put("data",data);
//            to.put("to",token);
//            sendNotification(to);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private
//    void sendNotification(JSONObject to) {
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, NOTIFICATION_URL,to, response -> {
//            Log.d("notification", "sendNotification: " + response);
//        }, error -> {
//            Log.d("notification", "sendNotification: " + error);
//        }) {
//            @Override
//            public
//            Map<String, String> getHeaders() throws AuthFailureError {
//
//                Map<String, String> map = new HashMap<>();
//                map.put("Authorization", "key=" + SERVER_KEY);
//                map.put("Content-Type", "application/json");
//                return map;
//            }
//
//            @Override
//            public String getBodyContentType() {
//                return "application/json";
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        request.setRetryPolicy(new DefaultRetryPolicy(30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(request);
//
//    }
//}
