package pt.isel.pdm.Yamba;

import winterwell.jtwitter.Twitter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class StatusActivity extends MenuActivity implements OnClickListener, TextWatcher {
	private static final String TAG = "PDM";
	private Button submit;
	private EditText text;
	private TextView charsCount;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        submit = (Button) findViewById(R.id.buttonUpdate);
        submit.setOnClickListener(this);
        text = (EditText) findViewById(R.id.editText);
        text.addTextChangedListener(this);
        charsCount = (TextView) findViewById(R.id.charLeft);
        
        updateCharCount(text.getText());       
    }
        
    private static final long TOTAL_TM = 10000; // 10 secs.
    
	public void onClick(View v) {
    	Log.d(TAG,"onClick");
    	disableSubmit();
    	updateStatus();
    	enableSubmit();
	}

	private void updateStatus() {
		long startTm = System.currentTimeMillis();
		Twitter twitter = new Twitter("pdmstudent", "pdmpass");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api");
		twitter.updateStatus(text.getText().toString());
		long elapsedTm = System.currentTimeMillis()-startTm;
		if (elapsedTm < TOTAL_TM)
			try { Thread.sleep(TOTAL_TM-elapsedTm); } 
			catch (InterruptedException e) {}
    	Log.d(TAG,"Submited. Elapsed time="+elapsedTm+", text="+text);
	}

	private void enableSubmit() {
		submit.setEnabled(true);
    	submit.setText(R.string.buttonUpdate);
	}

	private void disableSubmit() {
		submit.setEnabled(false);
    	submit.setText(R.string.buttonBusy);
	}

	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		updateCharCount(arg0);
	}
	
	private void updateCharCount(CharSequence text) {
		final int maxChar = 140;
		int charsLeft = maxChar - text.length();
		charsCount.setText(String.format("%d", charsLeft));
		
		setCharsLeftColor(charsLeft);
	}
	
	private void setCharsLeftColor(int charsLeft) {
		if(charsLeft < 0)
		{
			charsCount.setTextColor(getResources().getColor(R.color.chars_left_error));
		}
		else if(charsLeft < 20)
		{
			charsCount.setTextColor(getResources().getColor(R.color.chars_left_danger));
		}
		else
		{
			charsCount.setTextColor(getResources().getColor(R.color.chars_left_normal));
		}
	}
}