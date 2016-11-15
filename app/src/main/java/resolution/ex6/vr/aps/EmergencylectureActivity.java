package resolution.ex6.vr.aps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;



public class EmergencylectureActivity extends AppCompatActivity {

    Fragment emergency1Fragment;
    Fragment emergency2Fragment;
    Fragment emergency3Fragment;
    Fragment emergency4Fragment;
    Button emergency_button1;
    Button emergency_button2;
    Button emergency_button3;
    Button emergency_button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencylecture);
        emergency_button1 = (Button)findViewById(R.id.emergency_button1);
        emergency_button2 = (Button)findViewById(R.id.emergency_button2);
        emergency_button3 = (Button)findViewById(R.id.emergency_button3);
        emergency_button4 = (Button)findViewById(R.id.emergency_button4);
        if (findViewById(R.id.container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            emergency1Fragment = new Emergency1Fragment();
            emergency1Fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.container,
                    emergency1Fragment).commit();
        }

        emergency2Fragment = new Emergency2Fragment();
        emergency3Fragment = new Emergency3Fragment();
        emergency4Fragment = new Emergency4Fragment();


        emergency_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.container,emergency1Fragment)
                        .commit();
            }
        });

        emergency_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.container,emergency2Fragment)
                        .commit();
            }
        });
        emergency_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.container,emergency3Fragment)
                        .commit();
            }
        });
        emergency_button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.container,emergency4Fragment)
                        .commit();
            }
        });

    }

}
