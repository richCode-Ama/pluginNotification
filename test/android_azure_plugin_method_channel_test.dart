import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:android_azure_plugin/android_azure_plugin_method_channel.dart';

void main() {
  MethodChannelAndroidAzurePlugin platform = MethodChannelAndroidAzurePlugin();
  const MethodChannel channel = MethodChannel('android_azure_plugin');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
