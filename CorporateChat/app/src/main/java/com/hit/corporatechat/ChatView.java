package com.hit.corporatechat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by pasha on 11-Mar-16.
 * the class represent the user interface of the chat
 */
public class ChatView extends LinearLayout {
    private View mView;
    Context mycontext;

    public ChatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mycontext = context;
       // LayoutInflater inflater;
       // inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflate(mycontext, R.layout.chat_layout, this);
    }
}
