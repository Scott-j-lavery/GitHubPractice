package utility;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by scott on 10/9/15.
 */
public class FileManager {

    private static final String TAG = "FileManager.TAG";

    public File getAlbumStorageDir(Context context, String albumName) {
        File file = new File(context.getExternalFilesDir
                (Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }

    public void saveFile(Context context, String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES), fileName);

        if (Environment.isExternalStorageRemovable(file)) {
            try {
                FileOutputStream fos = new FileOutputStream(file, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}
