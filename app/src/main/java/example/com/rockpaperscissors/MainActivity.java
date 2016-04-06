package example.com.rockpaperscissors;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView title;
    Button playButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView) findViewById(R.id.title);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            title.setText(getText(R.string.title_portrait));
        else
            title.setText(getText(R.string.title_landscape));
        final Animation fadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        playButton = (Button) findViewById(R.id.playButton);

        if (playButton != null) {
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation fadeout = new AlphaAnimation(1.f, 0.f);
                    fadeout.setDuration(1500);
                    fadeout.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}
                        public void onAnimationRepeat(Animation a){}
                        public void onAnimationEnd(Animation a){
                            playButton.setVisibility(View.INVISIBLE);
                            getFragmentManager().beginTransaction()
                                .add(R.id.fragment_holder, new GameFragment())
                                .commit();
                        }
                    });
                    playButton.startAnimation(fadeout);
                }
            });
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            title.setText(getText(R.string.title_landscape));
        else
            title.setText(getText(R.string.title_portrait));
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().findFragmentById(R.id.fragment_holder) != null) {
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fragment_holder)).commit();
            final Animation fadeIn = new AlphaAnimation(0.f, 1.f);
            fadeIn.setDuration(1500);
            fadeIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                public void onAnimationRepeat(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    playButton.setVisibility(View.VISIBLE);
                }
            });
            playButton.startAnimation(fadeIn);
        }
    }
}
