package com.falconssoft.app_pos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.app_pos.category.CategoryActivity;
import com.falconssoft.app_pos.models.Tables;
import com.falconssoft.app_pos.models.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username, password;
    private TextView english, arabic;
    private Button login;

    private DatabaseHandler databaseHandler;
    private List<Users> users = new ArrayList<>();
    private List<Tables> tables = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHandler = new DatabaseHandler(this);

        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        english = findViewById(R.id.login_language_english);
        arabic = findViewById(R.id.login_language_arabic);
        login = findViewById(R.id.login_button);

        login.setOnClickListener(this);
        english.setOnClickListener(this);
        arabic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_button:
//                SendSocket send =new SendSocket(LoginActivity.this);
//                send.sendMessage();
                users = databaseHandler.getAllUSER();
                boolean found = false;
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();
//                if (!usernameText.equals("") && !passwordText.equals("")) {
                    for (int i = 0; i < users.size(); i++)
                        if (usernameText.equals(users.get(i).getUsername()))
                            if (passwordText.equals(users.get(i).getPassword())) {
                                found = true;
                                Intent categoryIntent = new Intent(LoginActivity.this, CategoryActivity.class);
//                                categoryIntent.putExtra("userName", usernameText);
                                startActivity(categoryIntent);
                            }

                    if (found == false) {
                        if (usernameText.equals("")) {
                            if (passwordText.equals("")) {
                                Intent categoryIntent = new Intent(LoginActivity.this, CategoryActivity.class);
                                categoryIntent.putExtra("userName", usernameText);
                                startActivity(categoryIntent);
                            } else {
                                Toast.makeText(this, "Wrong in username or password!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Wrong in username or password!", Toast.LENGTH_SHORT).show();
                        }
                    }
//                } else {
//                    username.setError("Required field!");
//                    password.setError("Required field!");
//                }
                break;
            case R.id.login_language_english:
                LocaleAppUtils.setLocale(new Locale("en"));
                LocaleAppUtils.setConfigChange(this);
                finish();
                startActivity(getIntent());
                break;
            case R.id.login_language_arabic:
                LocaleAppUtils.setLocale(new Locale("ar"));
                LocaleAppUtils.setConfigChange(this);
                finish();
                startActivity(getIntent());
                break;
        }
    }
}
