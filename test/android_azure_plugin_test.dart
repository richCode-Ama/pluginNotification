import 'package:flutter_test/flutter_test.dart';
import 'package:android_azure_plugin/android_azure_plugin.dart';
import 'package:android_azure_plugin/android_azure_plugin_platform_interface.dart';
import 'package:android_azure_plugin/android_azure_plugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockAndroidAzurePluginPlatform
    with MockPlatformInterfaceMixin
    implements AndroidAzurePluginPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final AndroidAzurePluginPlatform initialPlatform = AndroidAzurePluginPlatform.instance;

  test('$MethodChannelAndroidAzurePlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelAndroidAzurePlugin>());
  });

  test('getPlatformVersion', () async {
    AndroidAzurePlugin androidAzurePlugin = AndroidAzurePlugin();
    MockAndroidAzurePluginPlatform fakePlatform = MockAndroidAzurePluginPlatform();
    AndroidAzurePluginPlatform.instance = fakePlatform;

    expect(await androidAzurePlugin.getPlatformVersion(), '42');
  });
}
