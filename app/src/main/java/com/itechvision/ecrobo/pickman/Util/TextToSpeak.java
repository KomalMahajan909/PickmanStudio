package com.itechvision.ecrobo.pickman.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;

import java.util.Locale;

/**
 * Created by lenovo on 10/19/2018.
 */

public class TextToSpeak implements TextToSpeech.OnInitListener{

    private TextToSpeech tts;
    public Boolean isSpeechReady = false;
    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCE = "MyPrefs";

    public TextToSpeak(Context context){
        tts = new TextToSpeech(context, this);
        sharedPreferences = context.getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
    }

    public void onStopSpeaking(){
        if(tts != null) {
            Log.d("TTS", "Stopped");
            tts.stop();
            tts.shutdown();
        }
    }

    public Boolean getIsSpeechReady(){
        return isSpeechReady;
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            int i = BaseActivity.getlangpos();
            int result = 0;
            Log.e("TextToSpeak",">>>>>>>>>>   "+i);
            if(i == 1) {
                result = tts.setLanguage(Locale.ENGLISH);
                Log.e("TextToSpeak",">>>>>>>>>>resilt1111JAPANESE  "+result);
            } else {
                result = tts.setLanguage(Locale.JAPANESE);
                Log.e("TextToSpeak",">>>>>>>>>>resilt22222ENGLISH   "+result);

            }

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                isSpeechReady = true;
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    public void resetQueue(){
        Log.e("TexttoSpeak","Queue_Flush");
        tts.speak("", TextToSpeech.QUEUE_FLUSH, null);
    }

    public void startSpeaking(String toRead) {
        Log.d("SPEAKS", toRead);
        if(toRead.length() > 0)
            tts.speak(toRead, TextToSpeech.QUEUE_ADD, null);
    }

    public void startSpeaking(String textLabel, String textValue) {
        if(isNumeric(textValue))
            textValue = textValue.replace(""," ");
        Log.d("SPEAK", textValue);
        if(tts.isSpeaking()) {
            tts.speak(textLabel, TextToSpeech.QUEUE_ADD, null);
        }
        else{
            tts.speak(textLabel, TextToSpeech.QUEUE_FLUSH, null);
        }
        if(textValue.indexOf("-") != -1){
            /*If hyphen in value then silence for 0.5s*/
            String s[] = textValue.split("-");
            tts.speak(s[0], TextToSpeech.QUEUE_ADD, null);
            for(int i=1 ; i<s.length ; i++){
                tts.playSilence(500, TextToSpeech.QUEUE_ADD, null);
                tts.speak(s[i], TextToSpeech.QUEUE_ADD, null);
            }
        } else {
            tts.speak(textValue, TextToSpeech.QUEUE_ADD, null);
        }
    }

    public void  playsilence(){
        tts.playSilence(1000, TextToSpeech.QUEUE_ADD, null);
    }
    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

//    private String addHyphen(String textValue) {
//        return textValue.replace("-",". のー .");
//    }
}
