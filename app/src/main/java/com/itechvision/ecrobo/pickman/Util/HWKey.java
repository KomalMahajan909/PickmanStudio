package com.itechvision.ecrobo.pickman.Util;

import android.util.Log;

public  enum HWKey {
    KEYCODE_UNKNOWN ("", 0),
    /*
    KEYCODE_SOFT_LEFT ("SOFT_LEFT", 1),
    KEYCODE_SOFT_RIGHT ("SOFT_RIGHT", 2),
    KEYCODE_HOME ("HOME", 3),
    KEYCODE_BACK ("BACK", 4),
    KEYCODE_CALL ("CALL", 5),
    KEYCODE_ENDCALL ("ENDCALL", 6),
    */
    KEYCODE_0 ("0", 7),
    KEYCODE_1 ("1", 8),
    KEYCODE_2 ("2", 9),
    KEYCODE_3 ("3", 10),
    KEYCODE_4 ("4", 11),
    KEYCODE_5 ("5", 12),
    KEYCODE_6 ("6", 13),
    KEYCODE_7 ("7", 14),
    KEYCODE_8 ("8", 15),
    KEYCODE_9 ("9", 16),

    /*
    KEYCODE_STAR ("STAR", 17),
    KEYCODE_POUND ("POUND", 18),
    KEYCODE_DPAD_UP ("DPAD_UP", 19),
    KEYCODE_DPAD_DOWN ("DPAD_DOWN", 20),
    KEYCODE_DPAD_LEFT ("DPAD_LEFT", 21),
    KEYCODE_DPAD_RIGHT ("DPAD_RIGHT", 22),
    KEYCODE_DPAD_CENTER ("DPAD_CENTER", 23),
    KEYCODE_VOLUME_UP ("VOLUME_UP", 24),
    KEYCODE_VOLUME_DOWN ("VOLUME_DOWN", 25),
    KEYCODE_POWER ("POWER", 26),
    KEYCODE_CAMERA ("CAMERA", 27),
    KEYCODE_CLEAR ("CLEAR", 28),
    */
    KEYCODE_A ("a", 29),
    KEYCODE_B ("b", 30),
    KEYCODE_C ("c", 31),
    KEYCODE_D ("d", 32),
    KEYCODE_E ("e", 33),
    KEYCODE_F ("f", 34),
    KEYCODE_G ("g", 35),
    KEYCODE_H ("h", 36),
    KEYCODE_I ("i", 37),
    KEYCODE_J ("j", 38),
    KEYCODE_K ("k", 39),
    KEYCODE_L ("l", 40),
    KEYCODE_M ("m", 41),
    KEYCODE_N ("n", 42),
    KEYCODE_O ("o", 43),
    KEYCODE_P ("p", 44),
    KEYCODE_Q ("q", 45),
    KEYCODE_R ("r", 46),
    KEYCODE_S ("s", 47),
    KEYCODE_T ("t", 48),
    KEYCODE_U ("u", 49),
    KEYCODE_V ("v", 50),
    KEYCODE_W ("w", 51),
    KEYCODE_X ("x", 52),
    KEYCODE_Y ("y", 53),
    KEYCODE_Z ("z", 54),
    KEYCODE_COMMA (",", 55),
    KEYCODE_PERIOD (".", 56),
    KEYCODE_MINUS ("-", 69),
    KEYCODE_EQUALS ("$", 70),
    KEYCODE_SLASH ("/", 76),
    KEYCODE_PLUS ("+", 81),
    KEYCODE_SPACE (" ", 62),

    /*
    KEYCODE_ALT_LEFT ("ALT_LEFT", 57),
    KEYCODE_ALT_RIGHT ("ALT_RIGHT", 58),
    KEYCODE_SHIFT_LEFT ("SHIFT_LEFT", 59),
    KEYCODE_SHIFT_RIGHT ("SHIFT_RIGHT", 60),
    KEYCODE_TAB ("TAB", 61),
    KEYCODE_SPACE ("SPACE", 62),
    KEYCODE_SYM ("SYM", 63),
    KEYCODE_EXPLORER ("EXPLORER", 64),
    KEYCODE_ENVELOPE ("ENVELOPE", 65),
//	KEYCODE_ENTER ("ENTER", 66),
//	KEYCODE_DEL ("DEL", 67),
    KEYCODE_GRAVE ("GRAVE", 68),
    KEYCODE_MINUS ("MINUS", 69),
    KEYCODE_EQUALS ("EQUALS", 70),
    KEYCODE_LEFT_BRACKET ("LEFT_BRACKET", 71),
    KEYCODE_RIGHT_BRACKET ("RIGHT_BRACKET", 72),
    KEYCODE_BACKSLASH ("BACKSLASH", 73),
    KEYCODE_SEMICOLON ("SEMICOLON", 74),
    KEYCODE_APOSTROPHE ("APOSTROPHE", 75),
    KEYCODE_SLASH ("SLASH", 76),
    KEYCODE_AT ("AT", 77),
    KEYCODE_NUM ("NUM", 78),
    KEYCODE_HEADSETHOOK ("HEADSETHOOK", 79),
    KEYCODE_FOCUS ("FOCUS", 80),
    KEYCODE_PLUS ("PLUS", 81),
    KEYCODE_MENU ("MENU", 82),
    KEYCODE_NOTIFICATION ("NOTIFICATION", 83),
    KEYCODE_SEARCH ("SEARCH", 84),
    KEYCODE_MEDIA_PLAY_PAUSE ("MEDIA_PLAY_PAUSE", 85),
    KEYCODE_MEDIA_STOP ("MEDIA_STOP", 86),
    KEYCODE_MEDIA_NEXT ("MEDIA_NEXT", 87),
    KEYCODE_MEDIA_PREVIOUS ("MEDIA_PREVIOUS", 88),
    KEYCODE_MEDIA_REWIND ("MEDIA_REWIND", 89),
    KEYCODE_MEDIA_FAST_FORWARD ("MEDIA_FAST_FORWARD", 90),
    KEYCODE_MUTE ("MUTE", 91),
     */
    KEYCODE_a ("A", 88),
    KEYCODE_b ("B", 89),
    KEYCODE_c ("C", 90),
    KEYCODE_d ("D", 91),
    KEYCODE_e ("E", 92),
    KEYCODE_f ("F", 93),
    KEYCODE_g ("G", 94),
    KEYCODE_h ("H", 95),
    KEYCODE_i ("I", 96),
    KEYCODE_j ("J", 97),
    KEYCODE_k ("K", 98),
    KEYCODE_l ("L", 99),
    KEYCODE_m ("M", 100),
    KEYCODE_n ("N", 101),
    KEYCODE_o ("O", 102),
    KEYCODE_p ("P", 103),
    KEYCODE_q ("Q", 104),
    KEYCODE_r ("R", 105),
    KEYCODE_s ("S", 106),
    KEYCODE_t ("T", 107),
    KEYCODE_u ("U", 108),
    KEYCODE_v ("V", 109),
    KEYCODE_w ("W", 110),
    KEYCODE_x ("X", 111),
    KEYCODE_y ("Y", 112),
    KEYCODE_z ("Z", 113),
//	KEYCODE_MINUS ("-", 120)
    ;
    private String name;
    private int		keyCode;

    private HWKey(String name, int keyCode) {
        this.name = name;
        this.keyCode = keyCode;
    }

    public String getName() {
        return name;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public static HWKey valueOf(int keyCode) {
        for (HWKey keyCodeMap : values()) {
            if (keyCodeMap.getKeyCode() == keyCode) {
                Log.e("HWKEyyyyyyyyyy","value of Keycodesss  "+keyCode+" keycodeMAP "+keyCodeMap);
                return keyCodeMap;
            }
        }
        return HWKey.KEYCODE_UNKNOWN;
    }

}