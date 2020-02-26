package com.ibsvalleyn.missvenue.helper;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.ibsvalleyn.missvenue.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DecimalFormat;

public class AppFunctions {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static KProgressHUD getKProgressHUD(Context context) {
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getString(R.string.please_wait))
                .setCancellable(false)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .setBackgroundColor(context.getColor(R.color.colorPrimary1));
    }

    public static void NoStackFragment(Fragment fragment, AppCompatActivity context, int idContainer) {
        context.getSupportFragmentManager().beginTransaction()
                .replace(idContainer, fragment)
                .commit();

    }

    public static void StackFragment(Fragment fragment, AppCompatActivity context, int idContainer) {
        context.getSupportFragmentManager().beginTransaction()
                .replace(idContainer, fragment).addToBackStack(null)
                .commit();

    }

    public static String Rails(Context context, Double price) {

        if (price!=0)
        {
            return   new DecimalFormat("##.00").format(price) + "  " +context.getResources().getString(R.string.sr);

        }

       return new DecimalFormat("00.00").format(price) + "  " +context.getResources().getString(R.string.sr);
    }

    // Calculate Grid RecyclerView Columns No.
//    public static int calculateNoOfColumns(Context context) {
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
//        int noOfColumns = (int) (dpWidth / 180);
//        return noOfColumns;
//    }

    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthDp / columnWidthDp + 0.5);
    }

    //    public static tostMessage(String s){
//        Toast.makeText(, "", Toast.LENGTH_SHORT).show();
//    }
//
//    public static void guestAlertDialog(Activity context) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        LayoutInflater layoutInflater = context.getLayoutInflater();
//        View view = layoutInflater.inflate(R.layout.guest_dialog_layout, null);
//        builder.setView(view);
//        AlertDialog loading;
//        loading = builder.create();
//        loading.setCancelable(false);
//        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        Button dialogLoginBtn = view.findViewById(R.id.dialogLoginBtn);
//        Button dialogDismissBtn = view.findViewById(R.id.dialogDismissBtn);
//        dialogLoginBtn.setOnClickListener(v -> {
//            Intent intent = new Intent(context, SigningActivity.class);
////            intent.putExtra("key", "signUpBtn");
//            context.startActivity(intent);
//            context.finish();
//            loading.cancel();
//        });
//        dialogDismissBtn.setOnClickListener(v -> {
//
//            loading.cancel();
//        });
//
//        loading.show();
//    }
//
//
//
//    public static CircularProgressDrawable getCircularProgressDrawable(Context context) {
//
//        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
//        circularProgressDrawable.setStrokeWidth(5f);
//        circularProgressDrawable.setCenterRadius(30f);
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////            circularProgressDrawable.setColorSchemeColors(context.getColor(R.color.colorAccent));
////        }
//        circularProgressDrawable.start();
//        return circularProgressDrawable;
//    }
//
//    public static void logiMesage(String tag, String message) {
//        Log.i(tag, message);
//    }
//
//
    public static void toastMessage(final Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
//
//    public static void loadImage(Context context, String url, View view) {
//        Glide.with(context).load(url).into((ImageView) view);
//
//    }
//
//    public static void Show(View view) {
//        view.setVisibility(View.VISIBLE);
//    }
//
//    public static void Hide(View view) {
//        view.setVisibility(View.GONE);
//    }
//
//
//    public static void SetSpinnerAdapter(final Context context, Spinner spinner, List<String> serviceName, int view) {
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, view, serviceName) {
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View v = super.getView(position, convertView, parent);
//
////                Typeface externalFont = Typeface.createFromAsset(context.getAssets(), "font/dinnext.otf");
////                ((TextView) v).setTypeface(externalFont);
//
//                return v;
//            }
//
//
//            public View getDropDownView(int position, View convertView, ViewGroup parent) {
//                View v = super.getDropDownView(position, convertView, parent);
//
////                Typeface externalFont = Typeface.createFromAsset(context.getAssets(), "font/dinnext.otf");
////                ((TextView) v).setTypeface(externalFont);
//
//                return v;
//            }
//
//        };
//        spinner.setAdapter(adapter);
//    }
//
//    public static final void setAppLocale(String language, Activity activity) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            Resources resources = activity.getResources();
//            Configuration configuration = resources.getConfiguration();
//            configuration.setLocale(new Locale(language));
//            activity.getApplicationContext().createConfigurationContext(configuration);
//        } else {
//            Locale locale = new Locale(language);
//            Locale.setDefault(locale);
//            Configuration config = activity.getResources().getConfiguration();
//            config.locale = locale;
//            activity.getResources().updateConfiguration(config,
//                    activity.getResources().getDisplayMetrics());
//        }
//
//    }
//
//
//    public static void stringToWebView(WebView webview, String data, Context context) {
//
//
//        String data2 = "<div style='direction:rtl !important; '>" + data + "</div>";
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        webview.loadDataWithBaseURL("", data2, "text/html", "UTF-8", "");
//        webview.getSettings().getTextZoom();
////        webview.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
//    }
//
//
//    public static void sendLocation() {
//
//    }
//
//    public static void gotoCallUs(Context context, String phoneNum) {
//
//
//        Intent intent = new Intent(Intent.ACTION_DIAL);
//        intent.setData(Uri.parse("tel:" + phoneNum));
//
//        context.startActivity(intent);
//
//    }

    public static void stringToWebView(WebView webview, String data, Context context, String rtl) {


        String data2 = "<div style='direction:" + rtl + " !important; '>" + data + "</div>";
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webview.loadDataWithBaseURL("", data2, "text/html", "UTF-8", "");
        webview.getSettings().getTextZoom();
//        webview.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
    }

    public static DisplayMetrics getDeviceMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }

    public static Dialog setMargins(Dialog dialog, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        Window window = dialog.getWindow();
        if (window == null) {
            // dialog window is not available, cannot apply margins
            return dialog;
        }
        Context context = dialog.getContext();

        // set dialog to fullscreen
        RelativeLayout root = new RelativeLayout(context);
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ///dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView(root);
        // set background to get rid of additional margins
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        // apply left and top margin directly
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.x = marginLeft;
        attributes.y = marginTop;
        window.setAttributes(attributes);

        // set right and bottom margin implicitly by calculating width and height of dialog
        Point displaySize = getDisplayDimensions(context);
        int width = displaySize.x - marginLeft - marginRight;
        int height = displaySize.y - marginTop - marginBottom;
        window.setLayout(width, height);

        return dialog;
    }

    @NonNull
    public static Point getDisplayDimensions(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        // find out if status bar has already been subtracted from screenHeight
        display.getRealMetrics(metrics);
        int physicalHeight = metrics.heightPixels;
        int statusBarHeight = getStatusBarHeight(context);
        int navigationBarHeight = getNavigationBarHeight(context);
        int heightDelta = physicalHeight - screenHeight;
        if (heightDelta == 0 || heightDelta == navigationBarHeight) {
            screenHeight -= statusBarHeight;
        }

        return new Point(screenWidth, screenHeight);
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return (resourceId > 0) ? resources.getDimensionPixelSize(resourceId) : 0;
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return (resourceId > 0) ? resources.getDimensionPixelSize(resourceId) : 0;
    }
}
