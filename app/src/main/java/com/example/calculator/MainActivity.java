package com.example.calculator;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private TextView result;

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    switch (func) {
                        case "sqrt":
                            x = Math.sqrt(x);
                            break;
                        case "sin":
                            x = Math.sin(Math.toRadians(x));
                            break;
                        case "cos":
                            x = Math.cos(Math.toRadians(x));
                            break;
                        case "tan":
                            x = Math.tan(Math.toRadians(x));
                            break;
                        default:
                            throw new RuntimeException("Unknown function: " + func);
                    }
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
    public static String clearStrInput(String str) {
        String[] c= {"\\+", "-", "\\*", "/"};
        for (String s : c) str = str.replaceAll(s + "+", s);
        return str;
    }
    public static boolean evalException(String str) {
        return Pattern.matches("/0|0/", str);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainv2);

        TextView textView = findViewById(R.id.textResult);
        result = findViewById(R.id.result);

        Button btn_plus = findViewById(R.id.plus);
        Button btn_minus = findViewById(R.id.minus);
        Button btn_divide = findViewById(R.id.divide);
        Button btn_multiplication = findViewById(R.id.multiplication);
        Button btn_equal = findViewById(R.id.equal);
        Button btn_one = findViewById(R.id.one);
        Button btn_two = findViewById(R.id.two);
        Button btn_free = findViewById(R.id.free);
        Button btn_four = findViewById(R.id.four);
        Button btn_five = findViewById(R.id.five);
        Button btn_six = findViewById(R.id.six);
        Button btn_seven = findViewById(R.id.seven);
        Button btn_eight = findViewById(R.id.eight);
        Button btn_nine = findViewById(R.id.nine);
        Button btn_zero = findViewById(R.id.zero);
        Button btn_clear = findViewById(R.id.clear);
        Button btn_backspace = findViewById(R.id.backspace);
        Button activity_switch = findViewById(R.id.activity_switch);

        activity_switch.setOnClickListener(view -> {
            Intent fp=new Intent(getApplicationContext(),SecondaryActivity.class);
            if (!textView.getText().toString().isEmpty()) fp.putExtra("history",textView.getText().toString());
            startActivityForResult(fp, 1);
        });

        result.setOnClickListener(view ->
        {
            Toast.makeText(getApplicationContext(), "Введенные данные: " + result.getText(), Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

            builder.setMessage("Введенные данные: " + result.getText())
                    .setCancelable(true)
                    .setPositiveButton("Я это увидел", (dialogInterface, i) -> Toast.makeText(getApplicationContext(), "ОК", Toast.LENGTH_LONG))
                    .setTitle("Проверка введенных данных")
                    .show();
        });


        btn_plus.setOnClickListener(view -> result.setText(result.getText().toString()+'+'));
        btn_minus.setOnClickListener(view -> {
            String str = result.getText().toString();
            result.setText(str+'-');
        });
        btn_divide.setOnClickListener(view -> {
            String str = result.getText().toString();
            result.setText(str+'/');
        });
        btn_multiplication.setOnClickListener(view -> {
            String str = result.getText().toString();
            result.setText(str+'*');
        });
        btn_one.setOnClickListener(view -> {
            String str = result.getText().toString();
            result.setText(str+'1');
        });
        btn_two.setOnClickListener(view -> {
            String str = result.getText().toString();
            result.setText(str+'2');
        });
        btn_free.setOnClickListener(view -> {
            String str = result.getText().toString();
            result.setText(str+'3');
        });
        btn_four.setOnClickListener(view -> {
            String str = result.getText().toString();
            result.setText(str+'4');
        });
        btn_five.setOnClickListener(view -> {
            String str = result.getText().toString();
            result.setText(str+'5');
        });
        btn_six.setOnClickListener(view -> {
            String str = result.getText().toString();
            result.setText(str+'6');
        });
        btn_seven.setOnClickListener(view -> {
            String str = result.getText().toString();
            result.setText(str+'7');
        });
        btn_eight.setOnClickListener(view -> {
            String str = result.getText().toString();
            result.setText(str+'8');
        });
        btn_nine.setOnClickListener(view -> {
            String str = result.getText().toString();
            result.setText(str+'9');
        });
        btn_zero.setOnClickListener(view -> {
            String str = result.getText().toString();
            result.setText(str+'0');
        });
        btn_clear.setOnClickListener(view -> result.setText(""));
        btn_backspace.setOnClickListener(view -> {
            String str = result.getText().toString();
            if (str.length()>0){
            str = str.substring(0, str.length() - 1);
            result.setText(str);
            }
        });
        btn_equal.setOnClickListener(view -> {
            String str = result.getText().toString();
            double res;
            str = clearStrInput(str);
            try {
                if (str.length() < 3) {
                    Toast.makeText(getApplicationContext(), "Слишком маленькое выражение", Toast.LENGTH_LONG).show();
                }
                else {
                    res = eval(str);
                    if (Double.isNaN(res) || Double.isInfinite(res)) {
                        Toast.makeText(getApplicationContext(), "Деление на 0 невозможно", Toast.LENGTH_LONG).show();
                    } else {
                        String num = new BigDecimal(res).toPlainString();
                        result.setText(num);
                    }
                }
            } catch (Exception exception) {
                Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("result", findViewById(R.id.result).toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TextView text = findViewById(R.id.result);
        text.setText(savedInstanceState.getString("result"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView textView = findViewById(R.id.textResult);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String num1 = data.getStringExtra("num1");
                if (num1.isEmpty()) num1 = "-";
                String num2= data.getStringExtra("num2");
                if (num2.isEmpty()) num2 = "-";
                String res = data.getStringExtra("res");
                if (res.isEmpty()) res = "-";
                textView.setText(num1 + ";" + num2 + ";" + res);
            }
        }
    }
}