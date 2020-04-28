package com.example.travelog01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;

public class photoHelper {

    public static Bitmap getBitmap(String pathString)
    {
        Bitmap bitmap = null;
        try
        {
            File file = new File(pathString);
            if(file.exists())
            {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e)
        {
            // TODO: handle exception
        }
        return bitmap;
    }
}
