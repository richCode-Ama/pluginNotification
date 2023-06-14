// import 'android_azure_plugin_platform_interface.dart';
import 'dart:async';
import 'dart:io';
import 'package:flutter/services.dart';
// import 'android_azure_plugin_method_channel.dart';

typedef MessageHandler = Future<dynamic> Function(Map<String, dynamic> message);
typedef TokenHandler = Future<dynamic> Function(String token);

class AndroidAzurePlugin {
static const MethodChannel methodChannel =  MethodChannel('android_azure_plugin');
  late MessageHandler _onMessage;
  late MessageHandler _onResume;
  late MessageHandler _onLaunch;
  late TokenHandler _onToken;
 
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

 
  void configure(String notitficationId,
    
    {
    required MessageHandler onMessage,
    required MessageHandler onResume,
    required MessageHandler onLaunch,
    required TokenHandler onToken,
  }) {
    _onMessage = onMessage;
    _onResume = onResume;
    _onLaunch = onLaunch;
    _onToken = onToken;
    methodChannel.setMethodCallHandler(_handleMethod);
    methodChannel.invokeMethod<void>('configure', {
        'notificationId': notitficationId},   );
  }


Future<dynamic> _handleMethod(MethodCall call) async {
    switch (call.method) {
      case "onToken":
        return _onToken(call.arguments);
      case "onMessage":
        if (Platform.isAndroid) {
          Map<String, dynamic> args = Map<String, dynamic>.from(call.arguments);
          return _onMessage(Map<String, dynamic>.from(args['data']));
        }
        return _onMessage(call.arguments.cast<String, dynamic>());
      case "onLaunch":
        if (Platform.isAndroid) {
          Map<String, dynamic> args = Map<String, dynamic>.from(call.arguments);
          return _onMessage(Map<String, dynamic>.from(args['data']));
        }
        return _onLaunch(call.arguments.cast<String, dynamic>());
      case "onResume":
        if (Platform.isAndroid) {
          Map<String, dynamic> args = Map<String, dynamic>.from(call.arguments);
          return _onMessage(Map<String, dynamic>.from(args['data']));
        }
        return _onResume(call.arguments.cast<String, dynamic>());
      default:
        throw UnsupportedError("Unrecognized JSON message");
    }
  }
}
