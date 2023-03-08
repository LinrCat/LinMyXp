package lin.xposed;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import lin.xposed.LangReflectUtils.ClassUtils;
import lin.xposed.LangReflectUtils.ConstructorUtils;
import lin.xposed.LangReflectUtils.MethodUtils;
import lin.xposed.LangReflectUtils.PostMain;
import lin.xposed.mView.MainDialog;
import lin.xposed.mView.QQReflectData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class SettingHook {

    public static void startHookSetTing() {
        Class clazz = ClassUtils.getClass("com.tencent.mobileqq.activity.QQSettingSettingActivity");
        XposedHelpers.findAndHookMethod(clazz, "doOnCreate", Bundle.class, new XC_MethodHook(199) {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                PostMain.postDelayedMain(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Activity activity = null;
                            if (param.thisObject instanceof Activity)
                                activity = (Activity) param.thisObject;
                            else
                                activity = MethodUtils.findAndCallMethod(param.thisObject, "getActivity");
                            QQReflectData.ActivityMap.put(clazz.getName(),activity);
                            ViewGroup viewGroup = null;
                            //获得片段View-Item类
                            Class clz = ClassUtils.getClass("com.tencent.mobileqq.widget.FormSimpleItem");
                            Field[] fields = param.thisObject.getClass().getDeclaredFields();
                            for (Field field : fields) {
                                if (clz.equals(field.getType())) {
                                    try {
                                        field.setAccessible(true);
                                        View itemView = null;
                                        itemView = (View) field.get(param.thisObject);
                                        viewGroup = (ViewGroup) itemView.getParent();
                                        if (viewGroup instanceof LinearLayout) {
                                            break;
                                        }
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                            View view;
                            //获得片段View的Context构造方法
                            Constructor constructor = ConstructorUtils.findConstructor(clz, new Class[]{Context.class});
                            view = ConstructorUtils.NewInstance(constructor, activity);//
                            CharSequence charSequence = "Lin";
                            CharSequence text = "你不知道你有多可爱";
                            MethodUtils.findAndCallMethod(view, "setLeftText", charSequence);
                            MethodUtils.findAndCallMethod(view,"setRightText", "");
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //跳转或弹出模块功能
//                                    SettingMDialog.dialog.show();
                                    MainDialog.create((Activity) QQReflectData.ActivityMap.get("com.tencent.mobileqq.activity.QQSettingSettingActivity"));
                                }
                            });
                            viewGroup.addView(view, 0);//view的索引位置 不填就是最后
                            //TextView textView = null;
                            /*textView = FieIdUtils.getTypeFirstFieId(view, view.getClass(), TextView.class);
                            if (view != null) {
                                textView.setText("Lin");
                                ViewGroup.LayoutParams params = textView.getLayoutParams();
                                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                textView.setLayoutParams(params);
                            }*/
                        } catch (Exception e) {

                        }
                    }
                }, 100);
            }
        });
    }

}
