package com.example.myapplication2

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    private val READ_EXTERNAL_STORAGE_PERMISSION_CODE = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val timeTextView = findViewById<TextView>(R.id.textView)
        val updateTimeButton = findViewById<Button>(R.id.button)

        // Comprobar si hay permiso de leer el almacenamiento externo
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si no hay permiso, se solicita el permiso en tiempo de ejecución
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_PERMISSION_CODE
            )
        } else {


            val contentResolver = contentResolver

            val uri = Uri.parse("content://com.example.myapplication.timeprovider/time")
            val cursor = contentResolver.query(uri, null, null, null, null)
            Log.d("appTwo", "cursorTest: $cursor")

            val movetoLast = cursor?.moveToLast()
            Log.d("appTwo", "movecursor: $movetoLast")

            if (cursor != null && cursor.moveToLast()) {
                val time = cursor.getLong(cursor.getColumnIndexOrThrow("time"))
                Log.d("appTwo", "resultime: $time")

                val seconds = time
                timeTextView.text = "Tiempo transcurrido: $seconds segundos"

            } else {
                timeTextView.text = "No hay tiempo guardado aún"
            }
            cursor?.close()


        }

        updateTimeButton.setOnClickListener {
            val uri = Uri.parse("content://com.example.myapplication.timeprovider/time")
            val cursorButton = contentResolver.query(uri, null, null, null, null)
            Log.d("apptwo", "cursorIs: $cursorButton")
            val movetoLastButton = cursorButton?.moveToLast()
            if (cursorButton != null && cursorButton.moveToLast()) {
                val time = cursorButton.getLong(cursorButton.getColumnIndexOrThrow("time"))
                val seconds = time
                timeTextView.text = "Tiempo transcurrido: $seconds segundos"
                Log.d("apptwo", "timein: $timeTextView")

            } else {
                timeTextView.text = "No hay tiempo guardado aún"
            }
            cursorButton?.close()
        }
    }

}

