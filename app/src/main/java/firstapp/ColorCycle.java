package firstapp;

import android.content.res.Resources;

import java.util.ArrayList;

import ooqle.firstapp.R;

/**
 * Created by augiedoebling on 12/28/15.
 */
public class ColorCycle {

    private int neongreen;
    private int whiteblue;
    private int purple;
    private int orange;
    private int hotpink;
    private int blue;

    private ArrayList<Integer> list = new ArrayList<>();
    private int position;


    public ColorCycle(Resources res)
    {
        list.add(res.getColor(R.color.neongreen));
        list.add(res.getColor(R.color.whiteblue));
        list.add(res.getColor(R.color.purple));
        list.add(res.getColor(R.color.orange));
        list.add(res.getColor(R.color.hotpink));
        list.add(res.getColor(R.color.blue));

        position = 0;
    }

    public int next()
    {
        return list.get(position++ % 5);
    }
}
