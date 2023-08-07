package com.example.electrothon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView txtWelcome;
    EditText editMessage;
    ImageButton btnsend;
    List<Message> msglist;
    ImageView logout;

    MessageAdapter messageAdapter;

    FirebaseAuth mAuth;


    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        editMessage=findViewById(R.id.messageEditText);
        btnsend=findViewById(R.id.sendButton);
        logout=findViewById(R.id.logout);
        mAuth=FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent =new Intent(MainActivity.this,login.class);
                startActivity(intent);
                finish();
            }
        });
        msglist=new ArrayList<>();


        messageAdapter=new MessageAdapter(msglist);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question=editMessage.getText().toString().trim();
                addToChat(question,Message.SENT_BY_ME);
                editMessage.setText("");
                CallAPI(question);
            }
        });
    }

    void addToChat(String message,String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                msglist.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response){
        msglist.remove(msglist.size()-1);
        addToChat(response,Message.SENT_BY_BOT);
    }

    void CallAPI(String question){
        msglist.add(new Message("Typing...", Message.SENT_BY_BOT));
        JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put("model","gpt-3.5-turbo");
            JSONArray messagearr=new JSONArray();
            JSONObject obj=new JSONObject();
            obj.put("role","assistant");
            obj.put("content","question");
            messagearr.put(obj);

            jsonObject.put("messages",messagearr);
        }
        catch(JSONException e){
            throw new RuntimeException(e);
        }
        RequestBody body = RequestBody.create(jsonObject.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization","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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Fail to Load Response due to"+e.getMessage());

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try{
                        JSONObject jsonObject=new JSONObject(response.body().string());
                        JSONArray jsonArray;
                        jsonArray=jsonObject.getJSONArray("choices");
                        String result=jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
                        addResponse(result.trim());
                    }catch(JSONException e){
                        throw new RuntimeException(e);
                    }
                }
                else{
                    addResponse("Failed to response due to" + response.body().string().trim());
                }

            }
        });


    }
}