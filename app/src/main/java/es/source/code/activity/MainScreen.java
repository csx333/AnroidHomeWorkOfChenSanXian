package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainScreen extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Button buttonOrder = (Button)findViewById(R.id.button_order);
        Button buttonOrderForm = (Button)findViewById(R.id.button_order_form);
        Button buttonAccount = (Button)findViewById(R.id.button_account);
        Button buttonHelp = (Button)findViewById(R.id.button_help);

        buttonAccount.setOnClickListener(this);

        if(SCOSEntry.message.equals("Return")|| SCOSEntry.message.equals("FromEntry")){
            buttonOrder.setVisibility(View.INVISIBLE);
            buttonOrderForm.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainScreen.this,LoginOrRegister.class);
        startActivity(intent);
    }
}
