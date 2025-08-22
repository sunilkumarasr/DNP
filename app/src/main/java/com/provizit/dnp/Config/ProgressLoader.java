package com.provizit.dnp.Config;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import com.provizit.dnp.R;

public class ProgressLoader {

    private static Dialog dialog;

    public static void show(Activity activity) {
        dialog  = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_progress_loader);
/*        ImageView gifImageView = dialog.findViewById(R.id.custom_loading_imageView);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(gifImageView);
        Glide.with(activity)
                .load(R.drawable.loadgid)
                .placeholder(R.drawable.loadgid)
                .centerCrop()
                .into(imageViewTarget);*/
        dialog.show();
    }

    public static void hide(){
        if(dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

}
