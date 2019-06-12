package try1.asa.walinker;
import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText phone;
    EditText text;
    EditText res;

    protected ClipboardManager myClipboard;
    protected ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone = findViewById(R.id.PhoneInput);
        text = findViewById(R.id.TextInput);
        res = findViewById(R.id.ResText);
    }

    private boolean CheckPhone(String num)
    {
        if(num.length() >= 10 && num.length() <= 12)
        {
            for(int i = 0 ; i < num.length() ; i++)
            {
                if(!Character.isDigit(num.charAt(i)) && num.charAt(i) == '-' && num.charAt(i) == ' ')
                {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @SuppressLint("SetTextI18n")
    public void CreateButtonOnClick(View v)
    {
        String phoneStr = phone.getText().toString();
        String textStr = text.getText().toString();

        res.setText("https://api.whatsapp.com/send?phone=" + phoneStr  + "&text=" + textStr);

        if(phoneStr.length() == 0 || textStr.length() == 0)
        {
            res.setText("error! the input fields are empty!");
        }
        else if (CheckPhone(phoneStr))
        {
            res.setText("error! invalid phone number!");
        }
        else
        {
            phoneStr = phoneStr.replace("-", "");
            phoneStr = phoneStr.replace(" ", "");
            phoneStr = "+972" + phoneStr.substring(1);
            textStr = textStr.replace(" ", "%20");

            res.setText("https://api.whatsapp.com/send?phone=" + phoneStr  + "&text=" + textStr);
        }
        //res.setVisibility(View.VISIBLE);
    }
    public void CopyOnClick(View v)
    {

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        String text;
        text = res.getText().toString();

        myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);

        Toast.makeText(getApplicationContext(), "Text Copied",Toast.LENGTH_SHORT).show();
    }
}
