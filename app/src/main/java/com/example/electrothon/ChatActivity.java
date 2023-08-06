package com.example.electrothon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.samsao.messageui.views.MessagesWindow;

import java.util.List;

public class ChatActivity extends AppCompatActivity {




    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        final MessagesWindow messagesWindow=findViewById(R.id.customized_messages_window);
        final EditText message=messagesWindow.getWritingMessageView().findViewById(com.samsao.messageui.R.id.message_box_text_field);
        message.setHint("Type here...");
        message.setHintTextColor(R.color.black);
        message.setTextColor(R.color.black);

        messagesWindow.setBackgroundResource(R.color.black);
        messagesWindow.getWritingMessageView().findViewById(com.samsao.messageui.R.id.message_box_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                messagesWindow.sendMessage(message.getText().toString());
                Python py=Python .getInstance();
                PyObject pyobj=py.getModule("Self_Help_bot_direct");
                PyObject obj=pyobj.callAttr("main",message.getText().toString());
                messagesWindow.receiveMessage(obj.toString());
                message.setText("");
            }

        });

    }
}