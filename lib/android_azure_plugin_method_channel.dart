// import 'dart:async';
// import 'dart:io';
// import 'package:flutter/foundation.dart';
// import 'package:flutter/services.dart';

// import 'android_azure_plugin_platform_interface.dart';

// /// An implementation of [AndroidAzurePluginPlatform] that uses method channels.
// typedef MessageHandler = Future<dynamic> Function(Map<String, dynamic> message);
// typedef TokenHandler = Future<dynamic> Function(String token);

// class MethodChannelAndroidAzurePlugin extends AndroidAzurePluginPlatform {
//   /// The method channel used to interact with the native platform.
//   @visibleForTesting
//   final methodChannel = const MethodChannel('android_azure_plugin');
//   late MessageHandler _onMessage;
//   late MessageHandler _onResume;
//   late MessageHandler _onLaunch;
//   late TokenHandler _onToken;
//   @override
//   Future<String?> getPlatformVersion() async {
//     final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
//     return version;
//   }

//   @override
//   void configure({
//     required MessageHandler message,
//     required MessageHandler resume,
//     required MessageHandler onLaunch,
//     required TokenHandler onToken,
//   }) {
//     _onMessage = message;
//     _onResume = resume;
//     _onLaunch = onLaunch;
//     _onToken = onToken;
//     methodChannel.setMethodCallHandler(_handleMethod);
//     methodChannel.invokeMethod<void>('configure');
//   }


// Future<dynamic> _handleMethod(MethodCall call) async {
//     switch (call.method) {
//       case "onToken":
//         return _onToken(call.arguments);
//       case "onMessage":
//         if (Platform.isAndroid) {
//           Map<String, dynamic> args = Map<String, dynamic>.from(call.arguments);
//           return _onMessage(Map<String, dynamic>.from(args['data']));
//         }
//         return _onMessage(call.arguments.cast<String, dynamic>());
//       case "onLaunch":
//         if (Platform.isAndroid) {
//           Map<String, dynamic> args = Map<String, dynamic>.from(call.arguments);
//           return _onMessage(Map<String, dynamic>.from(args['data']));
//         }
//         return _onLaunch(call.arguments.cast<String, dynamic>());
//       case "onResume":
//         if (Platform.isAndroid) {
//           Map<String, dynamic> args = Map<String, dynamic>.from(call.arguments);
//           return _onMessage(Map<String, dynamic>.from(args['data']));
//         }
//         return _onResume(call.arguments.cast<String, dynamic>());
//       default:
//         throw UnsupportedError("Unrecognized JSON message");
//     }
//   }

// }
