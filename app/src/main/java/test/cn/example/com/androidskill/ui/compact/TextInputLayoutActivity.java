package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/6/24.
 */

public class TextInputLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textinputlayout);
        final TextInputLayout textInputLayout = findViewById(R.id.textInputLayout);
//        app:errorEnabled="true"
//        app:counterMaxLength="10"
//        app:counterEnabled="true"
//        app:counterTextAppearance="@style/AppTheme"
//        app:counterOverflowTextAppearance="@style/EditTextSytle"
//        android:hint="zheshi？"
        textInputLayout.setCounterEnabled(true);//设置字数统计的功能
        textInputLayout.setHint("新的提示信息，覆盖edittext中的hint");
        textInputLayout.setEnabled(true);
        textInputLayout.setCounterMaxLength(10);
        textInputLayout.setError("字数最大长度不能超过10");
        EditText editText = textInputLayout.getEditText();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>10){
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("长度不能吃超过10");
                }else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
    }
}
