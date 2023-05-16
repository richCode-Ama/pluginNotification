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
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Context context;
  private String TAG = "MainActivity";
  private int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
//


  // public static void registerWith(FlutterPluginBinding flutterPluginBinding) {
  //   AndroidAzurePlugin instance = new AndroidAzurePlugin();
  //         instance.onAttachedToEngine(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger() );

  // }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    Log.d(TAG, "ontachateched triggered");
    this.context = flutterPluginBinding.getApplicationContext();
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "android_azure_plugin");
    channel.setMethodCallHandler(this);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(RecieverService.ACTION_TOKEN);
    intentFilter.addAction(RecieverService.ACTION_REMOTE_MESSAGE);
    Log.d(TAG, "Intent about to register ");
    LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
    manager.registerReceiver(this, intentFilter);

    Log.d(TAG, "intentn registeration done ");
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } 
    else if (call.method.equals("configure")) {

      registerService();
      RecieverService.createChannelAndHandleNotifications(context);
    


    }
    
    else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }


  private void onAttachedToEngine(Context context, BinaryMessenger binaryMessenger) {
    this.context = context;
    channel = new MethodChannel(binaryMessenger, "android_azure_plugin");
    channel.setMethodCallHandler(this);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(RecieverService.ACTION_TOKEN);
    intentFilter.addAction(RecieverService.ACTION_REMOTE_MESSAGE);
    LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
    manager.registerReceiver(this, intentFilter);
  }




  @Override
  public void onReceive(Context context, Intent intent) {

    Log.d(TAG, "Message Receiveed  ");
    String action = intent.getAction();
    if(action == null){
      return;
    }

    if(action.equals(RecieverService.ACTION_TOKEN)){
      String token =  intent.getStringExtra(RecieverService.EXTRA_TOKEN);
      channel.invokeMethod("onToken", token);

    }

    if(action.equals(RecieverService.ACTION_MESSAGE)){
      RemoteMessage message =  intent.getParcelableExtra(RecieverService.EXTRA_MESSAGE);
      Map<String, Object> content = RecieverService.parseRemoteMessage(message);
      channel.invokeMethod("onMessage", content);
    }
  }


  public void registerService(){

    if (checkPlayService()) {
    Intent intent =  new Intent(context, RegistrationIntentService.class);
    context.startService(intent);
  }

  }

  public Boolean checkPlayService(){
    GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
    int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
    if (resultCode != ConnectionResult.SUCCESS) {
        if (apiAvailability.isUserResolvableError(resultCode)) {
            apiAvailability.getErrorDialog((Activity) context, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
        } else {
          System.out.println("djsjddjjdsjsjsdjwdjdjd");
        }
        return false;
    }
    return true;
  }
}
