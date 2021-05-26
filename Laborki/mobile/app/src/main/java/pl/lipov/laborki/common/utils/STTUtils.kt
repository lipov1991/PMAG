package pl.lipov.laborki.common.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import pl.lipov.laborki.R
import java.util.*

class STTUtils {

    fun listenForSpeechRecognize(
        activity: Activity,
        requestCode: Int
    ) {
        val recognizeSpeechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, activity.getString(R.string.app_name))
        }
        try {
            activity.startActivityForResult(recognizeSpeechIntent, requestCode)
        } catch (exception: ActivityNotFoundException) {
            Toast.makeText(activity, "Brak STT w urządzeniu", Toast.LENGTH_SHORT).show()
        }
    }

    fun handleSpeechRecognizeResult(
        data: Intent,
        context: Context,
        mapUtils: MapUtils
    ) {
        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            ?.firstOrNull()?.let { recognizeResult ->
                mapUtils.lastRecognizedVoiceResult = recognizeResult
                mapUtils.changeVisibilityBasedOnVoice()
            }
    }

}