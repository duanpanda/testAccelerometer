package ca.lakeheadu.whi.falldetectorexp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_GRAVITY) {
            return;
        }
        float ax = event.values[0];
        float ay = event.values[1];
        float az = event.values[2];
        TextView tvAx = (TextView)findViewById(R.id.ax);
        TextView tvAy = (TextView)findViewById(R.id.ay);
        TextView tvAz = (TextView)findViewById(R.id.az);
        tvAx.setText("ax=" + ax);
        tvAy.setText("ay=" + ay);
        tvAz.setText("az=" + az);

        String filename = "acc.csv";
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        try {
            File file = new File(getFilesDir(), filename);
            Log.d("MainActivity", String.valueOf(getFilesDir()));
            FileWriter fw = new FileWriter(file, true);
            fw.append(date.toString() + ',');
            fw.append(Float.toString(ax) + ',');
            fw.append(Float.toString(ay) + ',');
            fw.append(Float.toString(az) + '\n');
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.d("FallDetectorExp", "ax="+ax);
//        Log.d("FallDetectorExp", "ay="+ay);
//        Log.d("FallDetectorExp", "az="+az);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
