package cuongdev.app.smartview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cuongdev.app.smartview.printer.PrinterActivity
import cuongdev.app.smartview.tracking.TrackingActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ObsoleteCoroutinesApi


@ObsoleteCoroutinesApi
class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btCheckIn.setOnClickListener(this)
        btPrint.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btCheckIn -> {
                startActivity(Intent(this, TrackingActivity::class.java))
            }
            btPrint -> {
                startActivity(Intent(this, PrinterActivity::class.java))
            }
        }
    }


}