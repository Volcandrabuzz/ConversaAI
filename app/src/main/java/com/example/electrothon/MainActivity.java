package com.example.electrothon;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editMessage;
    ImageButton btnsend;
    List<Message> msglist;
    LottieAnimationView logout;

    MessageAdapter messageAdapter;

    FirebaseAuth mAuth;


    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();
        recyclerView=findViewById(R.id.recyclerView);
        editMessage=findViewById(R.id.messageEditText);
        btnsend=findViewById(R.id.sendButton);
        logout=findViewById(R.id.anim);
        mAuth=FirebaseAuth.getInstance();

        logout.setOnClickListener(view -> {
            mAuth.signOut();
            Intent intent =new Intent(MainActivity.this,login.class);
            startActivity(intent);
            finish();
        });
        msglist=new ArrayList<>();


        messageAdapter=new MessageAdapter(msglist);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnsend.setOnClickListener(view -> {
            String question=editMessage.getText().toString().trim();
            if(question.isEmpty()){
                Toast.makeText(MainActivity.this,"write something",Toast.LENGTH_SHORT).show();
            }
            else {
                addToChat(question, Message.SENT_BY_ME);
                editMessage.setText("");
                CallAPI(question);
            }
        });
    }

    void addToChat(String message,String sentBy){
        runOnUiThread(() -> {
            msglist.add(new Message(message,sentBy));
            messageAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
        });
    }

    void addResponse(String response){
        msglist.remove(msglist.size()-1);
        addToChat(response,Message.SENT_BY_BOT);
    }

    private void CallAPI(String question){
        msglist.add(new Message("Typing...", Message.SENT_BY_BOT));
        JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put("model","gpt-3.5-turbo");
            JSONArray jsonArray = new JSONArray();
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", question);

            JSONObject assistantMessage = new JSONObject();
            assistantMessage.put("role", "assistant");
            assistantMessage.put("content", "Talk like a devil. Talk like someone who trats everyone inferior." +
                    "Make your response maximum upto 3 lines. ");

            jsonArray.put(userMessage);
            jsonArray.put(assistantMessage);

            jsonObject.put("messages", jsonArray);
        }
        catch(JSONException e){
            throw new RuntimeException(e);
        }
        RequestBody body = RequestBody.create(jsonObject.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Fail to Load Response due to" + e.getMessage());

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject jsonObject1;
                    try{
                        assert response.body() != null;
                        jsonObject1=new JSONObject(response.body().string());
                        JSONArray jsonArray;
                        jsonArray=jsonObject1.getJSONArray("choices");
                        String result=jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
                        addResponse(result.trim());
                    }catch(JSONException e){
                        throw new RuntimeException(e);
                    }
                }
                else{
                    assert response.body() != null;
                    addResponse("Failed to response due to" + response.body().string().trim());
                }

            }
        });


    }
}