package com.example.administrator.customviewtest.softkeyboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.administrator.customviewtest.R;

public class KeyBoardActivity extends AppCompatActivity {

    private MentionEditText mentionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_board);

        mentionEditText = (MentionEditText) findViewById(R.id.et_input);
        if (mentionEditText != null) {
            mentionEditText.getMentionList(true);
        }

        mentionEditText.setText("@aaaaaa aaaaa");

        mentionEditText.setOnMentionInputListener(new MentionEditText.OnMentionInputListener() {
            @Override
            public void onMentionCharacterInput() {
                Toast.makeText(KeyBoardActivity.this,"click mention here",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
