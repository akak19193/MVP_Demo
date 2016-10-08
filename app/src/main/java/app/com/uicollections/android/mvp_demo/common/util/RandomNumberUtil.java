package app.com.uicollections.android.mvp_demo.common.util;

import java.util.Random;

public class RandomNumberUtil {
    public RandomNumberUtil(){}

    public int getRandomNumber(int min, int max){
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
