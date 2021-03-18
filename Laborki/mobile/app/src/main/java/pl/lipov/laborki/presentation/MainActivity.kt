package pl.lipov.laborki.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMainBinding

private const val DEBUG_TAG = "Gestures"


class MainActivity :
        AppCompatActivity(),
        LoginCallback,
    SensorEventListener
        //GestureDetector.SimpleOnGestureListener,
        //GestureDetector.OnDoubleTapListener
{

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    private lateinit var mDetector: GestureDetectorCompat

    private lateinit var sensorManager: SensorManager

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment())
            .commit()

        mDetector = GestureDetectorCompat(this, MyGestureListener(this))
        setUp()



    }

    override fun onLoginSuccess() {
        Toast.makeText(this@MainActivity, "abc", Toast.LENGTH_LONG).show()
    }

//        viewModel.run {
//            onAccelerometerNotDetected.observe(::getLifecycle) {
//                binding.info.text = getString(R.string.no_accelerometer_detected)
//            }
//            onGestureEvent.observe(::getLifecycle) {
//                binding.info.text = it.name
//            }
//            onSensorEvent.observe(::getLifecycle) {
//                binding.info.text = it.name
//            }
//        }
//    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }


    private class MyGestureListener(private var activ: AppCompatActivity) :
        GestureDetector.SimpleOnGestureListener() {

        override fun onLongPress(event: MotionEvent) {
            Toast.makeText(activ, "LONG_PRESS", Toast.LENGTH_LONG).show()
            println("LONG")


        }

        override fun onDoubleTap(event: MotionEvent): Boolean {
            Toast.makeText(activ, "DOUBLE_TAP", Toast.LENGTH_SHORT).show()
            println("D_TAP")
            return true
        }

    }

    private fun setUp() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(
                this, it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return // we do not use this
    }

    override fun onSensorChanged(event: SensorEvent?) {

        //Log.d("SENSORS", "onSensorChanged: The values are }")

        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

            val currentX = event.values[0]
            val currentY = event.values[1]
            val currentZ = event.values[2]


            if (currentX.toInt() > 5  ) {
                //println("ACCif")
                Toast.makeText(this, "ACCELERATION_CHANGE", Toast.LENGTH_SHORT).show()
            }
                else {
                    //Toast.makeText(this, "Nie wykryto akcelerometra.", Toast.LENGTH_SHORT).show()
            }

        }
    }

}