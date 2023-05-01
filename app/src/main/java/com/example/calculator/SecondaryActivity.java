package com.example.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SecondaryActivity extends AppCompatActivity {

    private TextView res;
    private EditText num1, num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

//              builder.setMessage("Ты перешел на 2 активность")
//                .setCancelable(false)
//                .setPositiveButton("Я это увидел", (dialogInterface, i) -> Toast.makeText(getApplicationContext(), "Ты внимательный", Toast.LENGTH_LONG))
//                .setNegativeButton("Не заметил", (dialogInterface, i) -> Toast.makeText(getApplicationContext(), "Ну такое", Toast.LENGTH_LONG))
//                .setTitle("Проверка на внимательность").show();

        num1 =  findViewById(R.id.dec1);
        num2 =  findViewById(R.id.dec2);
        Button mod_btn = findViewById(R.id.mod);
        res = findViewById(R.id.res);
        ImageButton activity_switch = findViewById(R.id.return_button);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            String[] data = arguments.get("history").toString().split(";");
            if (data[0].equals("-")) {
                data[0] = " ";
            }
            num1.setText(data[0]);
            if (data[1].equals("-")) {
                data[1] = " ";
            }
            num2.setText(data[1]);
            if (data[2].equals("-")) {
                data[2] = " ";
            }
            res.setText(data[2]);
        }

        activity_switch.setOnClickListener(view -> {
            Intent intent_result = new Intent();
            intent_result.putExtra("num1",num1.getText().toString());
            intent_result.putExtra("num2",num2.getText().toString());
            intent_result.putExtra("res",res.getText().toString());
            setResult(Activity.RESULT_OK, intent_result);
            finish();
        });

        mod_btn.setOnClickListener(view -> {
            try {
                int num3 = Integer.parseInt(num1.getText().toString());
                int num4 = Integer.parseInt(num2.getText().toString());
                int result = num3 % num4;
                res.setText(String.valueOf(result));
            } catch (Exception exception) {
                Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}