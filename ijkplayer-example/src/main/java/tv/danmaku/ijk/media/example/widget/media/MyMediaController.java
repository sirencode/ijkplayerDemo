package tv.danmaku.ijk.media.example.widget.media;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.MediaController;

/**
 * create by shenyonghe at 2018/12/14
 */
public class MyMediaController extends MediaController {


    public MyMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);
    }

    public MyMediaController(Context context) {
        super(context);
    }
}
