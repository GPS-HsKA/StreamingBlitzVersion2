package hska.streamingblitzv2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import hska.streamingblitzv2.R;
import hska.streamingblitzv2.model.Einstellungen;
import hska.streamingblitzv2.model.User;
import hska.streamingblitzv2.tasks.InsertUserTask;

public class RegisterActivity extends AppCompatActivity {

    public class CreateUserActivity extends AppCompatActivity {

        private User user = new User();

        @Override
        protected void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_register, menu);
            return super.onCreateOptionsMenu(menu);
        }

        private void saveUser() {
            Einstellungen einstellungen = new Einstellungen();

            user.setUsername(getStringValue(R.id.edit_register_username));
            user.setPasswort(getStringValue(R.id.edit_register_password));
            user.setMail(getStringValue(R.id.edit_register_email));
            user.setEinstellungen(einstellungen);

            InsertUserTask insertTask = new InsertUserTask(this);
            insertTask.execute(user);
        }

        private String getStringValue(int id) {
            View field = findViewById(id);
            if (field instanceof EditText) {
                EditText textField = (EditText) field;
                return textField.getText().toString();
            }
            return "";
        }
    }
}
