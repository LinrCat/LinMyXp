package lin.xposed.mView;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import lin.xposed.R;

public class MainDialog {

    public static Dialog dialog;

    public static void create(Activity activity) {
        LinearLayout layout = new LinearLayout(activity);
        layout.setBackgroundResource(R.drawable.main_background);
        TextView textView = new TextView(activity);
        textView.setText("功能性测试模块");
        layout.addView(textView);
        dialog = new Dialog(activity,R.style.Dialog_style);
        {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//背景透明
            dialog.setCancelable(true);
        }
        dialog.setContentView(layout);



        dialog.show();
    }

/*    public static View getMainMenu(String name,View.OnClickListener onClickListener) {

    }*/
}
