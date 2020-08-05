package try1.asa.walinker;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // variables
    private EditText phone;
    private EditText text;
    private TextView res;
    protected ClipboardManager myClipboard;
    protected ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get inputs and outputs
        phone = findViewById(R.id.PhoneInput);
        text = findViewById(R.id.TextInput);
        res = findViewById(R.id.ResText);
    }

    private boolean phoneIsValid(String num) {
        // check if phone num is valid
        Matcher m = Pattern.compile("^(\\+972 *|0)[0-9]{2}-*[0-9]{3}-*[0-9]{4}$").matcher(num);
        return m.find();
    }

    @SuppressLint("SetTextI18n")
    public void CreateButtonOnClick(View v) {
        // get strings
        String phoneStr = phone.getText().toString();
        String textStr = text.getText().toString();
        boolean valid = true;

        // phone valid?
        if (!phoneIsValid(phoneStr)) {
            phone.setBackground(getDrawable(R.drawable.error_text));
            valid = false;
        } else {
            phone.setBackground(getDrawable(R.drawable.normal_text));
        }

        // message valid?
        if (textStr.length() == 0) {
            text.setBackground(getDrawable(R.drawable.error_text));
            valid = false;
        } else {
            text.setBackground(getDrawable(R.drawable.normal_text));
        }

        // create URL
        if (valid) {
            phoneStr = phoneStr.replace("-", "").replace(" ", "");
            textStr = textStr.replace(" ", "%20").replace("\n", "%0A");

            phoneStr = phoneStr.startsWith("0") ? "+972" + phoneStr.substring(1) : phoneStr;
            textStr += Character.isLetter(textStr.charAt(textStr.length() - 1)) ? "" : "%00";

            res.setText("https://api.whatsapp.com/send?phone=" + phoneStr + "&text=" + textStr);
        } else {
            res.setText("");
        }
    }

    public void CopyOnClick(View v) {
        // copy url
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        String text;
        text = res.getText().toString();

        myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);

        Toast.makeText(getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT).show();
    }
}
