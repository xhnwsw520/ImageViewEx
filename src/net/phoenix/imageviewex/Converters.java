package net.phoenix.imageviewex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Converters class, it's abstract and all of its methods are static.
 * 
 * @author Francesco Pontillo
 * @author Sebastiano Poggi
 *
 */
public abstract class Converters {
	private static final String TAG = Converters.class.getSimpleName();

    /**
     * Converts a byte array into a BitmapDrawable, using the provided options.
     *
     * @param image 	The byte array representing the image.
     * @param opts  	The decoding options to use, or null if you'd like to use predefined
     * 					options (scaling will be not active).
     * @param context 	The Context for getting the Resources.
     * @return			The initialized BitmapDrawable.
     */
    public static BitmapDrawable byteArrayToDrawable(byte[] image, Options opts, Context context) {
        if (opts == null) {
            Log.v(TAG, "opts is null, initializing without scaling");
            opts = new Options();
            opts.inScaled = false;
        }
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length, opts);
        // bmp.setDensity(DisplayMetrics.DENSITY_HIGH);
        return new BitmapDrawable(context.getResources(), bmp);
    }

    /**
     * Converts a byte array into a Bitmap, using the provided options.
     *
     * @param image 	The byte array representing the image.
     * @param opts  	The decoding options to use, or null if you'd like to use predefined
     * 					options (scaling will be not active).
     * @return			The initialized BitmapDrawable.
     */
    public static Bitmap byteArrayToBitmap(byte[] image, Options opts) {
        if (opts == null) {
            Log.v(TAG, "opts is null, initializing without scaling");
            opts = new Options();
            opts.inScaled = false;
        }
        return BitmapFactory.decodeByteArray(image, 0, image.length, opts);
    }

    /**
     * Covnerts a Bitmap into a byte array.
     *
     * @param image 	The Bitmap to convert.
     * @return 			The byte array representing the Bitmap (compressed in PNG).
     */
    public static byte[] bitmapToByteArray(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    /**
     * Converts a Drawable into a byte array.
     *
     * @param image 	The Drawable to convertLa Drawable da convertire.
     * @return			The byte array representing the Drawable (compressed in PNG).
     */
    public static byte[] drawableToByteArray(Drawable image) {
        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        return bitmapToByteArray(bitmap);
    }
    
    /**
     * Gets an asset from a provided AssetManager and its name in the directory and returns a
     * byte array representing the object content.
     * 
     * @param assetManager	An {@link AssetManager}.
     * @param asset			String of the file name.
     * @return				byte[] representing the object content.
     */
    public static byte[] assetToByteArray(AssetManager assetManager, String asset) {
    	byte[] image = null;
		int b;
    	ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		try {
			InputStream is = assetManager.open(asset);
			while ((b = is.read()) != -1) {
				outStream.write(b);
			}
			image = outStream.toByteArray();
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	return image;
    }
}
