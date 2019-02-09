package com.text_to_speech;

import java.util.Locale;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity implements
		TextToSpeech.OnInitListener {
	private static TextToSpeech tts;
	private static Button btnSpeak;
	private static EditText speak_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {

		tts = new TextToSpeech(MainActivity.this, this);

		btnSpeak = (Button) findViewById(R.id.btnSpeak);

		speak_text = (EditText) findViewById(R.id.speak_text);

		// button on click event
		btnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				speakOut();
			}

		});
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();

	}

	//Speak Out Method to speak the text
	private void speakOut() {

		String text = speak_text.getText().toString();

		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	
	@Override
	public void onInit(int status) {
		
		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.US);// Set Language

			// tts.setPitch(5); // set pitch level

			// tts.setSpeechRate(2); // set speech speed rate

			//Language is not Supported
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "Language is not supported");
			} else {
				btnSpeak.setEnabled(true);
				speakOut();
			}

		} else {
			Log.e("TTS", "Initialization Failed");
		}
	}

}
