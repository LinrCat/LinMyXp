package lin.my;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import lin.test.IOUtils.FileUtils;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            FileUtils.r("hl");
        } catch (IOException e) {
            Toast.makeText(this, "文件写入失败" + e, Toast.LENGTH_LONG).show();
            //throw new RuntimeException(e);
        }
    }
}