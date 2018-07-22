package shahin.simplequadraticsolver;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.MobileAds;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_a) EditText et_a;
    @BindView(R.id.et_b) EditText et_b;
    @BindView(R.id.et_c) EditText et_c;
    @BindView(R.id.tv_sol_one) TextView tv_sol_one;
    @BindView(R.id.tv_sol_two) TextView tv_sol_two;
    @BindView(R.id.tv_reason) TextView tv_reason;
    @BindView(R.id.btn_round) TextView btn_round;
    @BindView(R.id.adView) AdView adView;
    @BindView(R.id.tv_info) ShimmerTextView tv_info;

    private MediaPlayer mPlayer;
    private double sol1, sol2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MobileAds.initialize(this, "Your Ad ID");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        tv_sol_one.setVisibility(View.INVISIBLE);
        tv_sol_two.setVisibility(View.INVISIBLE);
        tv_reason.setVisibility(View.INVISIBLE);

        Shimmer shimmer = new Shimmer();
        shimmer.start(tv_info);
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

            case R.id.action_report_suggest:
                sendEmail(getString(R.string.action_suggestion));
                return true;

            case R.id.action_equation_info:
                String url = "https://en.wikipedia.org/wiki/Quadratic_equation";
                Uri webPage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                return true;

            case R.id.action_rate:
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=shahin.simplequadraticsolver")));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Getting it in separate method
    private double discriminant(double a, double b, double c) {
        double disc;
        //b2 – 4ac
        disc = Math.pow(b, 2) - (4 * a * c);
        return disc;
    }

    //The solution
    public void solve(View view) {
        String reason;

        //Nested If statement also if statement within if statement to control the entries (Error Trap)
        if (!et_a.getText().toString().isEmpty() && !et_b.getText().toString().isEmpty() && !et_c.getText().toString().isEmpty() &&
                !et_a.getText().toString().equals(".") && !et_b.getText().toString().equals(".") && !et_c.getText().toString().equals(".") &&
                !et_a.getText().toString().equals("-") && !et_b.getText().toString().equals("-") && !et_c.getText().toString().equals("-")) {

            double a = Double.parseDouble(et_a.getText().toString());
            double b = Double.parseDouble(et_b.getText().toString());
            double c = Double.parseDouble(et_c.getText().toString());

            if (a == 0) {
                mPlayer = MediaPlayer.create(this, R.raw.error);
                mPlayer.start();
                snackBarShort(getString(R.string.str_coefficient));

            } else {
                if (discriminant(a, b, c) > 0) {
                    sol1 = (-b + Math.sqrt(discriminant(a, b, c))) / (2 * a);
                    sol2 = (-b - Math.sqrt(discriminant(a, b, c))) / (2 * a);
                    reason = getString(R.string.str_two_solution);
                    tv_sol_one.setText(String.format("%s %s", getString(R.string.str_positive_equal), Double.toString(sol1)));
                    tv_sol_two.setText(String.format("%s %s", getString(R.string.str_negative_equal), Double.toString(sol2)));
                    tv_reason.setText(reason);
                    tv_sol_one.setVisibility(View.VISIBLE);
                    tv_sol_two.setVisibility(View.VISIBLE);
                    tv_reason.setVisibility(View.VISIBLE);
                    mPlayer = MediaPlayer.create(this, R.raw.clicks137);
                    mPlayer.start();
                } else if (discriminant(a, b, c) == 0) {
                    sol1 = (-b + Math.sqrt(discriminant(a, b, c))) / (2 * a);
                    reason = getString(R.string.str_one_solution);
                    tv_sol_one.setText(String.format("%s%s", getString(R.string.str_x), Double.toString(sol1)));
                    tv_reason.setText(reason);
                    tv_sol_one.setVisibility(View.VISIBLE);
                    tv_reason.setVisibility(View.VISIBLE);
                    mPlayer = MediaPlayer.create(this, R.raw.clicks137);
                    mPlayer.start();
                } else {
                    reason = getString(R.string.str_no_solution);
                    tv_reason.setText(reason);
                    tv_sol_one.setVisibility(View.INVISIBLE);
                    tv_sol_two.setVisibility(View.INVISIBLE);
                    tv_reason.setVisibility(View.VISIBLE);
                }
            }

        } else {

            mPlayer = MediaPlayer.create(this, R.raw.error);
            mPlayer.start();
            tv_sol_one.setVisibility(View.INVISIBLE);
            tv_sol_two.setVisibility(View.INVISIBLE);
            tv_reason.setVisibility(View.INVISIBLE);
            snackBarShort(getString(R.string.str_enter_cofficient));

        }
    }


    public void roundNo(View view) {

        String roundSol1 = String.format("%.2f", sol1);
        String roundSol2 = String.format("%.2f", sol2);

        tv_sol_one.setText(String.format("%s %s", getString(R.string.str_positive_equal), roundSol1));
        tv_sol_two.setText(String.format("%s %s", getString(R.string.str_negative_equal), roundSol2));
        mPlayer = MediaPlayer.create(this, R.raw.clicks148);
        mPlayer.start();
    }

    public void clearAll(View view) {
        tv_sol_one.setText("");
        tv_sol_two.setText("");
        tv_reason.setText("");
        et_a.getText().clear();
        et_b.getText().clear();
        et_c.getText().clear();
        et_a.requestFocus();
    }

    private void snackBarShort(String message){
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.show();
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
