package com.base5.workouttimer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class TimerActivity extends Activity {

	 	private AudioManager am;
	    private ComponentName mRemoteControlResponder;
	 	//private RemoteControlReceiver rcr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mRemoteControlResponder = new ComponentName(getPackageName(),
                RemoteControlReceiver.class.getName());
        setContentView(R.layout.activity_timer);
        
        NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker1);
        String[] nums = new String[600];
        for(int i=0; i<nums.length; i++)
               nums[i] = Integer.toString(i+1);
        np.setMinValue(1);
        np.setMaxValue(600);
        np.setWrapSelectorWheel(true);
        np.setDisplayedValues(nums);
        np.setValue(1);
        
    }
    
    public void onResume() {
        super.onResume();
        am.registerMediaButtonEventReceiver(mRemoteControlResponder);
    }
    
    public void onDestroy() {
        super.onDestroy();
        am.unregisterMediaButtonEventReceiver(mRemoteControlResponder);
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_timer, menu);
        return true;
    }
    public void startTimer(View view) {
        // startTimer button pressed
    	//timer.start();
    	final TextView timerView = (TextView) findViewById(R.id.timerView);
    	//Toast toast = Toast.makeText(getApplicationContext(), "Button Push", Toast.LENGTH_SHORT);
    	//toast.show();
    	NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker1);
    	int time = np.getValue();
    	new CountDownTimer(time*1000, 1000) {

    	     public void onTick(long millisUntilFinished) {
    	         timerView.setText("seconds remaining: " + millisUntilFinished / 1000);
    	     }

    	     public void onFinish() {
    	         timerView.setText("seconds remaining: 0");
    	         MediaPlayer mediaPlayer = MediaPlayer.create(TimerActivity.this, R.raw.zentemplebell);
    	         mediaPlayer.start();
    	     }
    	  }.start();
    }
    
    public class RemoteControlReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
        	Toast toast = Toast.makeText(getApplicationContext(), "onReceive", Toast.LENGTH_SHORT);
        	toast.show();
            if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
                /* handle media button intent here by reading contents */
                /* of EXTRA_KEY_EVENT to know which key was pressed    */
            	toast = Toast.makeText(getApplicationContext(), Intent.EXTRA_KEY_EVENT, Toast.LENGTH_SHORT);
            	toast.show();
            	KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
                if (KeyEvent.KEYCODE_MEDIA_PLAY == event.getKeyCode()) {
                    // Handle key press.
                	toast = Toast.makeText(getApplicationContext(), "play!", Toast.LENGTH_SHORT);
                	toast.show();
                }
            }
        }
    }
}
