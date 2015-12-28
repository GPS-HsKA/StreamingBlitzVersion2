package hska.streamingblitzv2.activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.PopupMenu;

import hska.streamingblitzv2.R;
// import hska.streamingblitzv2.tasks.PopUpMenuEventHandler;

public class SucheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suche);


        Button suchergebnis_blitzsuche = (Button) findViewById(R.id.button_suche_blitzsuche);
        suchergebnis_blitzsuche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SucheActivity.this, ContentListActivity.class));
            }
        });


        Button suchergebnis_blitzscan = (Button) findViewById(R.id.button_suche_blitzscan);
        suchergebnis_blitzscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SucheActivity.this, ScanActivity.class));
            }
        });
    }

    /*public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        PopUpMenuEventHandler popUpMenuEventHandle=new PopUpMenuEventHandler(getApplicationContext());
        popup.setOnMenuItemClickListener(popUpMenuEventHandle);
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.show();
    }*/


}
