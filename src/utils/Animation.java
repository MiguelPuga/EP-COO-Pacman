package utils;

import elements.Element;

import java.io.Serializable;
import java.util.ArrayList;

public class Animation implements Serializable {

    private Element parent;
    private long startAnim = 0;
    private int frame = 0;

    private int lastClip = 0;

    public Animation(Element parent)
    {
        this.parent = parent;
    }

    public void play(int clip, int frameRate)
    {
        Integer[] animationClip = parent.animationsClips.get(clip);

        long t = 1000/frameRate;

        if(startAnim == 0)
        {
            startAnim = System.currentTimeMillis();
        }

        long elapsed = System.currentTimeMillis() - startAnim;

        if(lastClip != clip)
        {
            elapsed = t;
            lastClip = clip;
        }

        if(elapsed >= t) {

            startAnim = 0;

            if(frame < animationClip.length)
            {
                parent.changeImage(parent.sprites.get(animationClip[frame]));
                frame ++;
            }else
            {
                frame = 0;
                parent.changeImage(parent.sprites.get(animationClip[frame]));
            }

        }
    }
}
