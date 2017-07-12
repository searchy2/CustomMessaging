[![GitHub release](https://img.shields.io/github/release/searchy2/CustomMessaging.svg?style=flat-square)](https://github.com/searchy2/CustomMessaging/releases) [![Libraries.io for GitHub](https://img.shields.io/librariesio/github/searchy2/CustomMessaging.svg?style=flat-square)](https://github.com/searchy2/CustomMessaging) 
[![Github file size](https://img.shields.io/badge/Size-655%20KB-e91e63.svg)](http://www.methodscount.com/?lib=com.github.searchy2%3ACustomMessaging%3A1.0)
# Custom Messaging

![Custom Messaging Icon](https://raw.githubusercontent.com/searchy2/CustomMessaging/custom/demo/app_icon.png)

Custom Messaging is a custom SMS/MMS sending and receiving library forked from https://github.com/klinker41/android-smsmms.
This library exposes critical files within the Send folder so they can be overridden and customized. 

## Library Overview

Sending messages is very easy to do.

First, create a settings object with all of your required information for what you want to do. If you don't set something, then it will just be set to a default and that feature may not work. For example, if you need MMS, set the MMSC, proxy, and port, or else you will get an error every time.

``` java
Settings sendSettings = new Settings();
```

Next, attach that settings object to the sender

``` java
Transaction sendTransaction = new Transaction(mContext, sendSettings);
```

Now, create the Message you want to send

``` java
Message mMessage = new Message(textToSend, addressToSendTo);
mMessage.setImage(mBitmap);   // not necessary for voice or sms messages
```

And then all you have to do is send the message

``` java
sendTransaction.sendNewMessage(message, threadId)
```

Note: threadId can be nullified (using Transaction.NO_THREAD_ID), but this sometimes results in a new thread being created instead of the message being added to an existing thread

If you want to send MMS messages, be sure to add this to your manifest:

``` xml
<service android:name="com.android.mms.transaction.TransactionService"/>
```

That's it, you're done sending :)

You'll also need to register a few receivers for when the messages have been sent and for delivery reports to mark them as read... In your manifest, add these lines:

```xml
<receiver
    android:name="SentReceiver"
    android:taskAffinity="[insert package name here].SMS_SENT"/>

<receiver
    android:name="DeliveredReceiver"
    android:taskAffinity="[insert package name here].SMS_DELIVERED"/>

<!-- Your custom receiver which is child of MmsSentReceiver -->
<receiver
    android:name="[insert your custom receiver here. eg. com.example.sms_mms.receivers.MyMmsSentReceiver]"
    android:taskAffinity="com.stream.custommessaging.MMS_SENT"/>
```

Be sure to replace the [insert package name here] with your package name defined in the manifest. For example, Sliding Messaging's is com.stream.custommessaging_donate.

Lastly, you'll need to include permissions in your manifest depending on what you want to do. Here are all of them (there are a lot unfortunately):

```xml
<uses-permission android:name="android.permission.SEND_SMS"/>
<uses-permission android:name="android.permission.READ_SMS" />
<uses-permission android:name="android.permission.WRITE_SMS"/>
<uses-permission android:name="android.permission.RECEIVE_SMS"/>
<uses-permission android:name="android.permission.RECEIVE_MMS"/>
<uses-permission android:name="android.permission.VIBRATE"/>
<uses-permission android:name="android.provider.Telephony.SMS_RECEIVED" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.WRITE_SETTINGS" />
```

### Using on Lollipop+ devices

If you are using this on devices with Android 5.0 or higher, then a simpler method of sending is available.

``` java
Settings settings = new Settings();
settings.setUseSystemSending(true);
Transaction transaction = new Transaction(mContext, settings);
Message message = new Message(textToSend, addressToSendTo);
message.setImage(mBitmap);
transaction.sendNewMessage(message, threadId)
```

This will invoke the MMS APIs that were made available in SDK 21. It is recommended over the old approach since you no longer need to ask the user for APNs and much less setup is necessary. The option will only be applied when the device has SDK 21 or higher, otherwise it will fall back to the old method.

For full details on how to implement, please check out the sample application. It has more information on permissions, creating a transaction and sending a message.

# Gradle Dependency

Add this line to your `build.gradle` project. Use the latest release version for the version code. 

```java
repositories {
    maven { url 'https://jitpack.io' }
}
compile 'com.github.searchy2:CustomMessaging:latest-version'
```
## License

    Copyright 2017 Jacob Klinker

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
