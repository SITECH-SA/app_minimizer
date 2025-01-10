package dev.codecity.flutter_app_minimizer

import android.app.Activity
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FlutterAppMinimizerPlugin */
class FlutterAppMinimizerPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    private var activity: Activity? = null
    private lateinit var channel: MethodChannel

    // Called when the plugin is attached to the Flutter engine
    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_app_minimizer")
        channel.setMethodCallHandler(this)
    }

    // Called when the plugin is detached from the Flutter engine
    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    // Called when the plugin is attached to an activity
    override fun onAttachedToActivity(@NonNull binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    // Called when the activity is detached from the plugin
    override fun onDetachedFromActivity() {
        activity = null
    }

    // Called when the activity configuration changes (e.g., screen rotation)
    override fun onReattachedToActivityForConfigChanges(@NonNull binding: ActivityPluginBinding) {
        onAttachedToActivity(binding)
    }

    // Called when the activity is detached for configuration changes
    override fun onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity()
    }

    // Handles method calls from Flutter
    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "minimize" -> {
                if (activity != null) {
                    activity?.moveTaskToBack(true)
                    result.success(null)
                } else {
                    result.error("NO_ACTIVITY", "Activity is not available", null)
                }
            }
            else -> result.notImplemented()
        }
    }
}
