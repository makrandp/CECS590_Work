package edu.csulb.android.cameraappassignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

/**
 * Created by Mak on 3/1/2017.
 */

public class DialogFragment extends android.app.DialogFragment {

    ImageView imageView;
    TextView textView;
    Button dismiss;
    String caption;
    String path;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_fragment, container, false);
        getDialog().setTitle("Hello");
        textView = (TextView)rootView.findViewById(R.id.textView);
        imageView=(ImageView)rootView.findViewById(R.id.imageView2);
      //  File file = new File(db.getPath());
        Bundle myArguments = getArguments();
        caption = myArguments.getString("captionText");
        textView.setText(caption);

        DatabaseHandler db = new DatabaseHandler(getActivity());
        path= db.getPath(caption);
        path = path.substring(7);
           Log.e("PATHH =>", path);

       //File file = new File(db.getPath(caption));
        //Uri uri = Uri.fromFile(file);
     //   Log.e("URI", String.valueOf(uri));
       // imageView.setImageURI(uri);
       Bitmap bmp = BitmapFactory.decodeFile(path);


        try {
            imageView.setImageBitmap(modifyOrientation(bmp,path));
        } catch (IOException e) {
            e.printStackTrace();
        }


        dismiss = (Button) rootView.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

return rootView;
    }


    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
