package shahin.simplequadraticsolver;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Views Declaration
    EditText txtA, txtB, txtC;
    TextView tvSol1, tvSol2, vReason, tvInfo;
    MediaPlayer mPlayer;
    Button btnRound;
    LinearLayout linearLayout_extra;

    //Variable Declaration
    double sol1, sol2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find the views and cast them
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvSol1 = (TextView) findViewById(R.id.tvSol1);
        tvSol2 = (TextView) findViewById(R.id.tvSol2);
        vReason = (TextView) findViewById(R.id.tvReason);
        txtA = (EditText) findViewById(R.id.txtA);
        txtB = (EditText) findViewById(R.id.txtB);
        txtC = (EditText) findViewById(R.id.txtC);
        btnRound = (Button) findViewById(R.id.btnRound);
        linearLayout_extra = (LinearLayout) findViewById(R.id.linearLayout_extra);

        tvSol1.setVisibility(View.INVISIBLE);
        tvSol2.setVisibility(View.INVISIBLE);
        vReason.setVisibility(View.INVISIBLE);
        linearLayout_extra.animate().alpha(0).setDuration(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_options items for use in the action bar
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnSuggest:
                sendEmail("Suggest a feature, recommendation or report a bug");
                return true;
            case R.id.btnExit:
                finish();
                System.exit(0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Getting it in separate method
    private double discriminant(double a, double b, double c) {
        double disc = 0;
        //b2 – 4ac
        disc = Math.pow(b, 2) - (4 * a * c);
        return disc;
    }

    //The solution
    public void solve(View view) {

        String reason;

        //Nested If statement also if statement within if statement to control the entries (Error Trap)
        if (!txtA.getText().toString().isEmpty() && !txtB.getText().toString().isEmpty() && !txtC.getText().toString().isEmpty() &&
                !txtA.getText().toString().equals(".") && !txtB.getText().toString().equals(".") && !txtC.getText().toString().equals(".") &&
                !txtA.getText().toString().equals("-") && !txtB.getText().toString().equals("-") && !txtC.getText().toString().equals("-")) {

            double a = Double.parseDouble(txtA.getText().toString());
            double b = Double.parseDouble(txtB.getText().toString());
            double c = Double.parseDouble(txtC.getText().toString());

            if (a == 0) {
                mPlayer = MediaPlayer.create(this, R.raw.error);
                mPlayer.start();
                Toast.makeText(getApplicationContext(), R.string.str_coefficient, Toast.LENGTH_SHORT).show();
            } else {
                if (discriminant(a, b, c) > 0) {
                    sol1 = (-b + Math.sqrt(discriminant(a, b, c))) / (2 * a);
                    sol2 = (-b - Math.sqrt(discriminant(a, b, c))) / (2 * a);
                    reason = getString(R.string.str_two_solution);
                    tvSol1.setText(getString(R.string.str_x_positive) + Double.toString(sol1));
                    tvSol2.setText(getString(R.string.str_x_negative) + Double.toString(sol2));
                    vReason.setText(reason);
                    tvSol1.setVisibility(View.VISIBLE);
                    tvSol2.setVisibility(View.VISIBLE);
                    vReason.setVisibility(View.VISIBLE);
                    linearLayout_extra.animate().alpha(1).setDuration(2000);
                    mPlayer = MediaPlayer.create(this, R.raw.clicks137);
                    mPlayer.start();
                } else if (discriminant(a, b, c) == 0) {
                    sol1 = (-b + Math.sqrt(discriminant(a, b, c))) / (2 * a);
                    reason = getString(R.string.str_one_solution);
                    tvSol1.setText(getString(R.string.str_x) + Double.toString(sol1));
                    vReason.setText(reason);
                    tvSol1.setVisibility(View.VISIBLE);
                    vReason.setVisibility(View.VISIBLE);
                    linearLayout_extra.animate().alpha(1).setDuration(2000);
                    mPlayer = MediaPlayer.create(this, R.raw.clicks137);
                    mPlayer.start();
                } else {
                    reason = getString(R.string.str_no_solution);
                    vReason.setText(reason);
                    tvSol1.setVisibility(View.INVISIBLE);
                    tvSol2.setVisibility(View.INVISIBLE);
                    linearLayout_extra.animate().alpha(0).setDuration(2000);
                    vReason.setVisibility(View.VISIBLE);
                }
            }

        } else {

            mPlayer = MediaPlayer.create(this, R.raw.error);
            mPlayer.start();
            tvSol1.setVisibility(View.INVISIBLE);
            tvSol2.setVisibility(View.INVISIBLE);
            vReason.setVisibility(View.INVISIBLE);
            linearLayout_extra.animate().alpha(0).setDuration(0);
            Toast.makeText(getApplicationContext(), R.string.str_enter_cofficient, Toast.LENGTH_SHORT).show();

        }
    }


    public void roundNo(View view) {

        String roundSol1 = String.format("%.2f", sol1);
        String roundSol2 = String.format("%.2f", sol2);

        tvSol1.setText(getString(R.string.str_positive_equal) + roundSol1);
        tvSol2.setText(getString(R.string.str_negative_equal) + roundSol2);
        mPlayer = MediaPlayer.create(this, R.raw.clicks148);
        mPlayer.start();
    }

    public void clearAll(View view) {

        tvSol1.setText("");
        tvSol2.setText("");
        vReason.setText("");
        txtA.setText("");
        txtB.setText("");
        txtC.setText("");
        linearLayout_extra.animate().alpha(0).setDuration(0);

    }

    private void sendEmail(String text) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"M7edshin@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, text);
        i.putExtra(Intent.EXTRA_TEXT, "Your Message");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients or apps installed on your device.", Toast.LENGTH_SHORT).show();
        }
    }
}
