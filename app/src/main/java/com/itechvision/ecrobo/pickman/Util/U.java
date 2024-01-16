package com.itechvision.ecrobo.pickman.Util;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.provider.Settings.System.DATE_FORMAT;


public class U {

    protected static ToneGenerator sToneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM, ToneGenerator.MAX_VOLUME);

    public static SoundPool mSoundPool = new SoundPool( 1, AudioManager.STREAM_MUSIC, 0 );
    public static int mSoundId1;	// なんでやねん
    public static int mSoundId2;	// 正解です
    public static int mSoundId3;	// まだまだ
    public static int mSoundId4;	// 終了
    public static int mSoundId5;	// もう少し、がんばれ
    public static int mSoundId6;	// よっしゃー
    public static int mSoundId7;	// お疲れ様でした
    public static int mSoundId8;	// 入荷確定（機械音）
    public static int mSoundId9;	// ビープ代替（機械音）

    public static String plusTo(String a, String b) {
        return String.valueOf(Long.parseLong(a) + Long.parseLong(b));
    }

    public static String plusToArr(String[] arr) {
        Long res = 0l;
        for(String s : arr){
            res += Long.parseLong(s);
        }
        return String.valueOf(res);
    }

    public static String minusTo(String a, String b) {
        if (a == null) a = "0";
        if (b == null) b = "0";
        return String.valueOf(Long.parseLong(a) - Long.parseLong(b));
    }

    public static String minusTo(long a, String b) {
        if (b == null) b = "0";
        return String.valueOf(a - Long.parseLong(b));
    }

    public static String minusTo(String a, long b) {
        if (a == null) a = "0";
        return String.valueOf(Long.parseLong(a) - b);
    }

    public static boolean isNumber(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException nfex) {
            return false;
        }
    }

//    public static void sendLog(String title, String message) {
//    new ECZaikoClient(null).bugreport(title, message);
//    }

    public static void beepError(Activity activity, String message) {

        if (activity != null) {
            if(BaseActivity.getErrorSound()== 0)
            mSoundId1 = mSoundPool.load(activity, R.raw.wrong_buzzer, 1);
            else if(BaseActivity.getErrorSound()== 1)
                mSoundId1 = mSoundPool.load(activity, R.raw.mistake, 1);
            else if(BaseActivity.getErrorSound()== 2)
                mSoundId1 = mSoundPool.load(activity, R.raw.ng_3, 1);
            else if(BaseActivity.getErrorSound()== 3)
                mSoundId1 = mSoundPool.load(activity, R.raw.ng, 1);
            try {
                Thread.sleep(500);
                mSoundPool.play(mSoundId1, 1.0F, 1.0F, 0, 0, 1.0F);
                //mSoundPool.release();
            } catch (InterruptedException e1) {
                // TODO 自動生成された catch ブロック
                e1.printStackTrace();
            }
        }

        try {
            Log.d("MSG12", "[" + activity.getClass().getName() + "] " + message);
            synchronized (sToneGenerator) {
                vib(activity);
				/*
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				Thread.sleep(50);
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				Thread.sleep(50);
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				*/
                Thread.sleep(50);
                //sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 160);
            }
            if (message != null) Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
        }

    }



    public static void beepNG(Activity activity, String message) {

        if (activity != null) {
            mSoundId1 = mSoundPool.load(activity, R.raw.wrong_buzzer, 1);
            try {
                Thread.sleep(500);
                mSoundPool.play(mSoundId1, 1.0F, 1.0F, 0, 0, 1.0F);
                //mSoundPool.release();
            } catch (InterruptedException e1) {
                // TODO 自動生成された catch ブロック
                e1.printStackTrace();
            }
        }

        try {
            Log.d("MSG12", "[" + activity.getClass().getName() + "] " + message);
            synchronized (sToneGenerator) {
                vib(activity);
				/*
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				Thread.sleep(50);
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				Thread.sleep(50);
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				*/
                Thread.sleep(50);
                //sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 160);
            }
            if (message != null) Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
        }

    }
    public static void beepNGbox(Activity activity, String message) {

        if (activity != null) {
            mSoundId1 = mSoundPool.load(activity, R.raw.boxerror, 1);
            try {
                Thread.sleep(500);
                mSoundPool.play(mSoundId1, 1.0F, 1.0F, 0, 0, 1.0F);
                //mSoundPool.release();
            } catch (InterruptedException e1) {
                // TODO 自動生成された catch ブロック
                e1.printStackTrace();
            }
        }

        try {
            Log.d("MSG12", "[" + activity.getClass().getName() + "] " + message);
            synchronized (sToneGenerator) {
                vib(activity);
				/*
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				Thread.sleep(50);
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				Thread.sleep(50);
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				*/
                Thread.sleep(50);
                //sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 160);
            }
            if (message != null) Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
        }

    }


  public static void beepOK(Activity activity, String message) {

        if (activity != null) {
            mSoundId1 = mSoundPool.load(activity, R.raw.ok, 1);
            try {
                Thread.sleep(500);
                mSoundPool.play(mSoundId1, 1.0F, 1.0F, 0, 0, 1.0F);
                //mSoundPool.release();
            } catch (InterruptedException e1) {
                // TODO 自動生成された catch ブロック
                e1.printStackTrace();
            }
        }

        try {
            Log.d("MSG12", "[" + activity.getClass().getName() + "] " + message);
            synchronized (sToneGenerator) {
                vib(activity);
				/*
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				Thread.sleep(50);
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				Thread.sleep(50);
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				*/
                Thread.sleep(50);
                //sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 160);
            }
            if (message != null) Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
        }

    }




    public static void boxOK(Activity activity, String message) {

        if (activity != null) {
            mSoundId1 = mSoundPool.load(activity, R.raw.boxokay, 1);
            try {
                Thread.sleep(500);
                mSoundPool.play(mSoundId1, 1.0F, 1.0F, 0, 0, 1.0F);
                //mSoundPool.release();
            } catch (InterruptedException e1) {
                // TODO 自動生成された catch ブロック
                e1.printStackTrace();
            }
        }

        try {
            Log.d("MSG12", "[" + activity.getClass().getName() + "] " + message);
            synchronized (sToneGenerator) {
                vib(activity);
				/*
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				Thread.sleep(50);
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				Thread.sleep(50);
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				*/
                Thread.sleep(50);
                //sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 160);
            }
            if (message != null) Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
        }

    }


    public static void TasNewBeep(Activity activity, String message) {

        if (activity != null) {
            mSoundId1 = mSoundPool.load(activity, R.raw.tasnewtune, 1);
            try {
                Thread.sleep(500);
                mSoundPool.play(mSoundId1, 1.0F, 1.0F, 0, 0, 1.0F);
                //mSoundPool.release();
            } catch (InterruptedException e1) {
                // TODO 自動生成された catch ブロック
                e1.printStackTrace();
            }
        }
        else {
            try {
                Log.d("MSG12", "[" + activity.getClass().getName() + "] " + message);
                synchronized (sToneGenerator) {
                    vib(activity);
				/*
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				Thread.sleep(50);
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				Thread.sleep(50);
				sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 80);
				*/
                    Thread.sleep(50);
                    //sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 160);
                }
                if (message != null) Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            } catch (InterruptedException e) {
            }
        }
    }

    public static void beepErrorVoice(Activity activity, String message , int id) {

        if (activity != null) {
            mSoundId1 = mSoundPool.load(activity,  id, 1);
            try {
                Thread.sleep(500);
                mSoundPool.play(mSoundId1, 1.0F, 1.0F, 0, 0, 1.0F);
                //mSoundPool.release();
            } catch (InterruptedException e1) {
                // TODO 自動生成された catch ブロック
                e1.printStackTrace();
            }
        }

        try {
            Log.d("MSG12", "[" + activity.getClass().getName() + "] " + message);
            synchronized (sToneGenerator) {
                vib(activity);

                Thread.sleep(50);
                //sToneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 160);
            }
            if (message != null) Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
        }

    }

    public static StringBuffer chop(StringBuffer sb) {
        if (sb != null && sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb;
    }

    public static String chop(String s) {
        if (s != null && s.length() > 0) {
            return s.substring(0, s.length() - 1);
        }
        return s;
    }

    public static void beepSuccess() {
        beepSuccess(null, null);
    }

    public static void beepSuccess(int type) {
        beepSuccess(null, null, type);
    }

    public static void beepSuccess(Activity activity, String message) {
        if (activity != null) {
//            mSoundId2 = mSoundPool.load(activity, R.raw.ok, 2);
//            try {
//                Thread.sleep(500);
//                mSoundPool.play(mSoundId2, 1.0F, 1.0F, 0, 0, 1.0F);
//                //mSoundPool.release();
//            } catch (InterruptedException e1) {
//                // TODO 自動生成された catch ブロック
//                e1.printStackTrace();
//            }
        }

        synchronized (sToneGenerator) {
            sToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 150);
        }
        if (message != null)CommonDialogs.customToast(activity, message);
    }


    public static void beepSuccess(Activity activity, String message, int type) {
        if (activity != null) {
            mSoundId2 = mSoundPool.load(activity, R.raw.finish, 1);
            try {
                Thread.sleep(500);
                mSoundPool.play(mSoundId2, 1.0F, 1.0F, 0, 0, 1.0F);
                //mSoundPool.release();
            } catch (InterruptedException e1) {
                // TODO 自動生成された catch ブロック
                e1.printStackTrace();
            }
        }

        synchronized (sToneGenerator) {
            sToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 150);
        }
        if (message != null) Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void vib(Activity activity) {
        long[] pattern = {0, 80, 100, 80};
        ((Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(pattern, -1);
    }

    public static void beepNext() {
        beepNext(null, null, 0);
    }

    public static void beepNext(int type) {
        beepNext(null, null, type);
    }

    public static void beepNext(Activity activity, String message, int type) {
        synchronized (sToneGenerator) {
            switch (type) {
                case 1:
                    if (activity != null) {
                        mSoundId3 = mSoundPool.load(activity, R.raw.finish, 1);
                        try {
                            Thread.sleep(500);
                            mSoundPool.play(mSoundId3, 1.0F, 1.0F, 0, 0, 1.0F);
                        } catch (InterruptedException e1) {
                            // TODO 自動生成された catch ブロック
                            e1.printStackTrace();
                        }
                    }
                    sToneGenerator.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 500);
                    break;
                default:
                    if (activity != null) {
                        mSoundId3 = mSoundPool.load(activity, R.raw.finish, 1);
                        try {
                            Thread.sleep(500);
                            mSoundPool.play(mSoundId3, 1.0F, 1.0F, 0, 0, 1.0F);
                        } catch (InterruptedException e1) {
                            // TODO 自動生成された catch ブロック
                            e1.printStackTrace();
                        }
                    }
                    sToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP2, 500);
            }
        }
        if (message != null) Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static int compareNumeric(String src, String dest) {
        try {
            long s = Long.parseLong(src);
            long d = Long.parseLong(dest);
            if (s == d) return 0;
            else return (s > d ? -1 : 1);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String join(String c, String[] row) {
        StringBuffer sb = new StringBuffer();
        for (String s: row) {
            sb.append(c).append(s);
        }
        return sb.substring(c.length());
    }

    public static void beepSended(Activity activity, String message, int type) {
        synchronized (sToneGenerator) {
            try {
                switch (type) {
                    case 1:
                        if (activity != null) {
                            mSoundId4 = mSoundPool.load(activity, R.raw.finish, 1);
                            try {
                                Thread.sleep(500);
                                mSoundPool.play(mSoundId4, 1.0F, 1.0F, 0, 0, 1.0F);
                            } catch (InterruptedException e1) {
                                // TODO 自動生成された catch ブロック
                                e1.printStackTrace();
                            }
                        }
                        vib(activity);
                        sToneGenerator.startTone(ToneGenerator.TONE_DTMF_7, 160);
                        Thread.sleep(160);
                        sToneGenerator.startTone(ToneGenerator.TONE_DTMF_9, 160);
                        Thread.sleep(160);
                        sToneGenerator.startTone(ToneGenerator.TONE_DTMF_7, 160);
                        Thread.sleep(160);
                        break;
                    default:
                        if (activity != null) {
                            mSoundId4 = mSoundPool.load(activity, R.raw.finish, 1);
                            try {
                                Thread.sleep(500);
                                mSoundPool.play(mSoundId4, 1.0F, 1.0F, 0, 0, 1.0F);
                            } catch (InterruptedException e1) {
                                // TODO 自動生成された catch ブロック
                                e1.printStackTrace();
                            }
                        }
                        vib(activity);
                        sToneGenerator.startTone(ToneGenerator.TONE_DTMF_0, 160);
                        Thread.sleep(160);
                        sToneGenerator.startTone(ToneGenerator.TONE_DTMF_1, 160);
                        Thread.sleep(160);
                        sToneGenerator.startTone(ToneGenerator.TONE_DTMF_0, 160);
                        Thread.sleep(160);
                }
                if (message != null) Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            } catch (InterruptedException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
    }
//
//    public static void beepMousukoshi(Activity activity, String message) {
//        if (activity != null) {
//            mSoundId5 = mSoundPool.load(activity, R.raw.mousukosi, 1);
//            try {
//                Thread.sleep(500);
//                mSoundPool.play(mSoundId5, 1.0F, 1.0F, 0, 0, 1.0F);
//            } catch (InterruptedException e1) {
//                // TODO 自動生成された catch ブロック
//                e1.printStackTrace();
//            }
//        } else {
//            synchronized (sToneGenerator) {
//                sToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 150);
//            }
//        }
//        if (message != null) Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
//    }
//
//    public static void beepYossha(Activity activity, String message) {
//        if (activity != null) {
//            mSoundId6 = mSoundPool.load(activity, R.raw.yoxsya, 1);
//            try {
//                Thread.sleep(500);
//                mSoundPool.play(mSoundId6, 1.0F, 1.0F, 0, 0, 1.0F);
//            } catch (InterruptedException e1) {
//                // TODO 自動生成された catch ブロック
//                e1.printStackTrace();
//            }
//        } else {
//            synchronized (sToneGenerator) {
//                sToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 150);
//            }
//        }
//        if (message != null) Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
//    }
//
//    public static void beepOtsukare(Activity activity, String message) {
//        if (activity != null) {
//            mSoundId7 = mSoundPool.load(activity, R.raw.otukaresama, 1);
//            try {
//                Thread.sleep(500);
//                mSoundPool.play(mSoundId7, 1.0F, 1.0F, 0, 0, 1.0F);
//            } catch (InterruptedException e1) {
//                // TODO 自動生成された catch ブロック
//                e1.printStackTrace();
//            }
//        } else {
//            synchronized (sToneGenerator) {
//                sToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 150);
//            }
//        }
//        if (message != null) Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
//    }
//
//    public static void beepFinish(Activity activity, String message) {
//        if (activity != null) {
//            mSoundId4 = mSoundPool.load(activity, R.raw.syuryo01kawamoto, 1);
//            try {
//                Thread.sleep(500);
//                mSoundPool.play(mSoundId4, 1.0F, 1.0F, 0, 0, 1.0F);
//            } catch (InterruptedException e1) {
//                // TODO 自動生成された catch ブロック
//                e1.printStackTrace();
//            }
//        } else {
//            synchronized (sToneGenerator) {
//                sToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 150);
//            }
//        }
//        if (message != null) Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
//    }
//

    public static void beepFinish(Activity activity, String message) {

        if (activity != null) {
            mSoundId8 = mSoundPool.load(activity, R.raw.finish, 2);
            try {
                Thread.sleep(500);
                mSoundPool.play(mSoundId8, 1.0F, 1.0F, 0, 0, 1.0F);
                //mSoundPool.release();
            } catch (InterruptedException e1) {
                // TODO 自動生成された catch ブロック
                e1.printStackTrace();
            }
        }

        synchronized (sToneGenerator) {
            sToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 150);
        }
        if (message != null) Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void beepKakutei(Activity activity, String message) {
        Log.e("beepKakutei","BaseActivity.getOkSound()   "+BaseActivity.getOkSound());
        if (activity != null) {
            if(BaseActivity.getOkSound() == 0)
            mSoundId8 = mSoundPool.load(activity, R.raw.kakutei, 2);
            else if(BaseActivity.getOkSound() == 1)
                mSoundId8 = mSoundPool.load(activity, R.raw.finish, 2);
             else if(BaseActivity.getOkSound() == 2)
                mSoundId8 = mSoundPool.load(activity, R.raw.ok_1, 2);
            else if(BaseActivity.getOkSound() == 3)
                mSoundId8 = mSoundPool.load(activity, R.raw.ok_2, 2);
            try {
                Thread.sleep(500);
                mSoundPool.play(mSoundId8, 1.0F, 1.0F, 0, 0, 1.0F);
                //mSoundPool.release();
            } catch (InterruptedException e1) {
                // TODO 自動生成された catch ブロック
                e1.printStackTrace();
            }
        }

        synchronized (sToneGenerator) {
            sToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 150);
        }
        if (message != null) Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
//
    public static void beepBigsound(Activity activity, String message) {
        if (activity != null) {
            mSoundId9 = mSoundPool.load(activity, R.raw.maoudamashii, 2);
            try {
                Thread.sleep(500);
                mSoundPool.play(mSoundId9, 1.0F, 1.0F, 0, 0, 1.0F);
            } catch (InterruptedException e1) {
                // TODO 自動生成された catch ブロック
                e1.printStackTrace();
            }
        }

        if (message != null) Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
//
//    public static void shipFinish(Activity activity, String message) {
//        if (activity != null) {
//            mSoundId4 = mSoundPool.load(activity, R.raw.owari, 1);
//            try {
//                Thread.sleep(500);
//                mSoundPool.play(mSoundId4, 1.0F, 1.0F, 0, 0, 1.0F);
//            } catch (InterruptedException e1) {
//                // TODO 自動生成された catch ブロック
//                e1.printStackTrace();
//            }
//        } else {
//            synchronized (sToneGenerator) {
//                sToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 150);
//            }
//        }
//        if (message != null) Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
//    }
//
//    public static void beepYoukoso(Activity activity, String message) {
//        if (activity != null) {
//            mSoundId6 = mSoundPool.load(activity, R.raw.youkoso, 1);
//            try {
//                Thread.sleep(500);
//                mSoundPool.play(mSoundId6, 1.0F, 1.0F, 0, 0, 1.0F);
//            } catch (InterruptedException e1) {
//                // TODO 自動生成された catch ブロック
//                e1.printStackTrace();
//            }
//        } else {
//            synchronized (sToneGenerator) {
//                sToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 150);
//            }
//        }
//        if (message != null) Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
//    }
//
    public static void beepSiekai(Activity activity, String message) {
        if (activity != null) {
            mSoundId2 = mSoundPool.load(activity, R.raw.scanning, 2);
            try {
                Thread.sleep(500);
                mSoundPool.play(mSoundId2, 1.0F, 1.0F, 0, 0, 1.0F);
                //mSoundPool.release();
            } catch (InterruptedException e1) {
                // TODO 自動生成された catch ブロック
                e1.printStackTrace();
            }
        }

        synchronized (sToneGenerator) {
            sToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 150);
        }
        if (message != null) Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void beepRecord(Activity activity, String message) {
        if (activity != null) {
//            mSoundId2 = mSoundPool.load(activity, R.raw.record, 1);
            mSoundId7 = mSoundPool.load(activity, R.raw.scanning, 1);
            try {
                Thread.sleep(500);
                mSoundPool.play(mSoundId2, 1.0F, 1.0F, 0, 0, 1.0F);
                //mSoundPool.release();
            } catch (InterruptedException e1) {
                // TODO 自動生成された catch ブロック
                e1.printStackTrace();
            }
        }

        synchronized (sToneGenerator) {
            sToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 150);
        }
        if (message != null) Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

	/*public static Integer validateLocation(String location){
		if(location.length() > 0)
			return location.indexOf("-");
		return -1;
	}*/

    public static int getDaysDifference(Date fromDate,Date toDate)
    {
        if(fromDate==null||toDate==null)
            return 0;

        return (int)( (toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
    }
    public static String MultiplyTo(String a, String b) {
        if (a == null) a = "1";
        if (b == null) b = "1";
        return String.valueOf(Integer.parseInt(a)*Integer.parseInt(b));
    }
}

