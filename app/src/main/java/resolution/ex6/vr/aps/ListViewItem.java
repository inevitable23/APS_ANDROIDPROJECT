package resolution.ex6.vr.aps;

import android.graphics.drawable.Drawable;

/**
 * Created by 강승규 on 2016-11-13.
 */
public class ListViewItem {
    private String jusoStr;
    private String jangsoStr;
    private Drawable iconDrawable;

    public void setJuso(String juso) { jusoStr = juso; }
    public void setJangso(String jangso) { jangsoStr = jangso; }
    public void setIcon(Drawable icon) { iconDrawable = icon; }
    public String getJuso() { return this.jusoStr; }
    public String getJangso() { return this.jangsoStr; }
    public Drawable getIcon() { return this.iconDrawable; }

}
