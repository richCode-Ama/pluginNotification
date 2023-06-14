package com.example.android_azure_plugin;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.RemoteMessage;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.iid.FirebaseInstanceId;
// import com.google.firebase.iid.InstanceIdResult;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
/** AndroidAzurePlugin */
public class AndroidAzurePlugin extends BroadcastReceiver implements FlutterPlugin, MethodCallHandler {
   private static Context applicationContext;
    private MethodChannel channel;

    //    This static function is optional and equivalent to onAttachedToEngine. It supports the old
    //    pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    //    plugin registration via this function while apps migrate to use the new Android APIs
    //    post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    //    It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    //    them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    //    depending on the user's project. onAttachedToEngine or registerWith must both be defined
    //    in the same class.
    //    public static void registerWith(PluginRegistry.Registrar registrar) {
    //        AzureNotificationhubsFlutterPlugin instance = new AzureNotificationhubsFlutterPlugin();
    //        instance.onAttachedToEngine(registrar.context(), registrar.messenger());
    //    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        onAttachedToEngine(binding.getApplicationContext(), binding.getFlutterEngine().getDartExecutor());
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        LocalBroadcastManager.getInstance(binding.getApplicationContext()).unregisterReceiver(this);
    }

    private void onAttachedToEngine(Context context, BinaryMessenger binaryMessenger) {
        this.applicationContext = context;
        channel = new MethodChannel(binaryMessenger, "android_azure_plugin");
        channel.setMethodCallHandler(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NotificationService.ACTION_TOKEN);
        intentFilter.addAction(NotificationService.ACTION_REMOTE_MESSAGE);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(applicationContext);
        manager.registerReceiver(this, intentFilter);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("configure")) {
            Map<?, ?> arguments = (Map<?, ?>) call.arguments;
            String notificationId = (String) arguments.get("notificationId");
           
            registerWithNotificationHubs(notificationId);
            NotificationService.createChannelAndHandleNotifications(applicationContext);
        } else {
            result.notImplemented();
        }
    }

    public void registerWithNotificationHubs(String tag) {
        Intent intent = new Intent(applicationContext, RegistrationIntentService.class);
        intent.putExtra("tag", tag );
        applicationContext.startService(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) {
            return;
        }
        if (action.equals(NotificationService.ACTION_TOKEN)) {
            String token = intent.getStringExtra(NotificationService.EXTRA_TOKEN);
            channel.invokeMethod("onToken", token);
        } else if (action.equals(NotificationService.ACTION_REMOTE_MESSAGE)) {
            RemoteMessage message = intent.getParcelableExtra(NotificationService.EXTRA_REMOTE_MESSAGE);
            Map<String, Object> content = NotificationService.parseRemoteMessage(message);
            channel.invokeMethod("onMessage", content);
        }
    }


}
