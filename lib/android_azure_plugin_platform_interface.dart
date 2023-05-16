// import 'package:plugin_platform_interface/plugin_platform_interface.dart';

// import 'android_azure_plugin_method_channel.dart';
// import 'package:flutter/services.dart';
// import 'dart:async';
// import 'dart:io';

// typedef MessageHandler = Future<dynamic> Function(Map<String, dynamic> message);
// typedef TokenHandler = Future<dynamic> Function(String token);

// abstract class AndroidAzurePluginPlatform extends PlatformInterface {
//   /// Constructs a AndroidAzurePluginPlatform.
//   AndroidAzurePluginPlatform() : super(token: _token);

//   static final Object _token = Object();

//   static AndroidAzurePluginPlatform _instance =
//       MethodChannelAndroidAzurePlugin();

//   /// The default instance of [AndroidAzurePluginPlatform] to use.
//   ///
//   /// Defaults to [MethodChannelAndroidAzurePlugin].
//   static AndroidAzurePluginPlatform get instance => _instance;

//   /// Platform-specific implementations should set this with their own
//   /// platform-specific class that extends [AndroidAzurePluginPlatform] when
//   /// they register themselves.
//   static set instance(AndroidAzurePluginPlatform instance) {
//     PlatformInterface.verifyToken(instance, _token);
//     _instance = instance;
//   }

//   Future<String?> getPlatformVersion() {
//     throw UnimplementedError('platformVersion() has not been implemented.');
//   }

//   void configure({
//     required MessageHandler message,
//     required MessageHandler resume,
//     required MessageHandler onLaunch,
//     required TokenHandler onToken,
//   }) {
//     throw UnimplementedError('platformVersion() has not been implemented.');
//   }
// }
