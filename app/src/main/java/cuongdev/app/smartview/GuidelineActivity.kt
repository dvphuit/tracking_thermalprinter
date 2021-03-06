package cuongdev.app.smartview

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import cuongdev.app.smartview.tracking.TrackingActivity
import cuongdev.app.smartview.tracking.Utils
import kotlinx.android.synthetic.main.activity_guildline.*

const val IS_READ_GUIDE = "IS_READ_GUIDE"

class GuidelineActivity : AppCompatActivity() {

    private val pref: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    private var slideIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guildline)
        updateSlide()
        btNext.setOnClickListener {
            updateSlide()
        }
        btPermission.setOnClickListener {
            Utils.requestGPSPermissions(this)
        }
    }

    private fun updateSlide() {
        when (slideIndex) {
            0 -> {
                btPermission.visibility = View.GONE
                ivBackground.setImageResource(R.drawable.intro_1)
                slideIndex++
            }
            1 -> {
                checkPermission()
                btPermission.visibility = View.VISIBLE
                ivBackground.setImageResource(R.drawable.intro_2)
            }
            2 -> {
                btPermission.visibility = View.GONE
                ivBackground.setImageResource(R.drawable.intro_3)
                slideIndex++
            }
            3 -> {
                gotoMain()
                pref.edit {
                    putBoolean(IS_READ_GUIDE, true).apply()
                }
            }
        }
    }

    private fun gotoMain() {
        startActivity(Intent(this, PolicyActivity::class.java))
        finish()
    }

    private fun checkPermission() {
        if (Utils.canAccessGPS(this)) {
            btPermission.isEnabled = false
            btNext.isEnabled = true
            slideIndex = 2
            btPermission.isEnabled = false
        } else {
            btNext.isEnabled = false
            btPermission.isEnabled = true
            slideIndex++
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Utils.GPS_REQUEST_CODE -> {
                if (Utils.canAccessGPS(this)) {
                    checkPermission()
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        Snackbar.make(
                            activity_intro,
                            "B???n c???n c???p quy???n truy c???p v??? tr?? ????? c?? th??? truy c???p ???????c ???ng d???ng!",
                            Snackbar.LENGTH_INDEFINITE
                        )
                            .setAction("C???p quy???n") {
                                btPermission.performClick()
                            }
                            .show()
                    } else {


                        Snackbar.make(
                            activity_intro,
                            "B???n c???n c???p quy???n truy c???p v??? tr?? l???i trong ph???n c??i ?????t!",
                            Snackbar.LENGTH_INDEFINITE
                        )
                            .setAction("C??i ?????t") {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri: Uri = Uri.fromParts("package", packageName, null)
                                intent.data = uri
                                startActivityForResult(intent, 33333)
                            }
                            .show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 33333) {
            checkPermission()
        }
    }

}