package lin.xposed;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import lin.xposed.Utils.ClassUtils;

public class Hook implements IXposedHookLoadPackage {

    @Override//实现反射其他appClass的主要方法
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        //查找已经挂钩的包名
        if (!loadPackageParam.packageName.equals("com.tencent.mobileqq")) return;
        ClassUtils.setLoadPackageParam(loadPackageParam);//更新挂钩参数包
        SettingHook.startHookSetTing();//注入设置页
    }

}
