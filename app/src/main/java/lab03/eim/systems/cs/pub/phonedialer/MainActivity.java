package lab03.eim.systems.cs.pub.phonedialer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText phoneNumberEditText;
    Button genericButton;
    ImageButton backspaceButton;
    ImageButton callButton;
    ImageButton hangupButton;

    private GenericButtonClickListener genericButtonClickListener = new GenericButtonClickListener();
    private class GenericButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + ((Button)view).getText().toString());
        }
    }

    private BackspaceButtonClickListener backspaceButtonClickListener = new BackspaceButtonClickListener();
    private class BackspaceButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (phoneNumber.length() > 0) {
                phoneNumberEditText.setText(phoneNumber.substring(0, phoneNumber.length() - 1));
            }
        }
    }

    private CallButtonClickListener callButtonClickListener = new CallButtonClickListener();
    private class CallButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private HangupButtonClickListener hangupButtonClickListener = new HangupButtonClickListener();
    private class HangupButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        for (int index = 0; index < Constants.Buttons.length; index++) {
            genericButton = (Button)findViewById(Constants.Buttons[index]);
            genericButton.setOnClickListener(genericButtonClickListener);
        }
        phoneNumberEditText = (EditText)findViewById(R.id.number);
        backspaceButton = (ImageButton)findViewById(R.id.imageButton);
        backspaceButton.setOnClickListener(backspaceButtonClickListener);

        callButton = (ImageButton)findViewById(R.id.imageButton2);
        callButton.setOnClickListener(callButtonClickListener);

        hangupButton = (ImageButton)findViewById(R.id.imageButton3);
        hangupButton.setOnClickListener(hangupButtonClickListener);
    }
}