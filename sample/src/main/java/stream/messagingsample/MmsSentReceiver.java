/*
 * Copyright (C) 2015 Jacob Klinker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stream.messagingsample;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Telephony;

import com.google.android.mms.util_alt.SqliteWrapper;
import com.stream.custommessaging.Transaction;

import android.util.Log;

import java.io.File;

public class MmsSentReceiver extends BroadcastReceiver {

    private static final String TAG = "MmsSentReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG, "MMS has finished sending, marking it as so in the database");

        Uri uri = Uri.parse(intent.getStringExtra(Transaction.EXTRA_CONTENT_URI));
        Log.v(TAG, uri.toString());

        ContentValues values = new ContentValues(1);
        values.put(Telephony.Mms.MESSAGE_BOX, Telephony.Mms.MESSAGE_BOX_SENT);
        SqliteWrapper.update(context, context.getContentResolver(), uri, values,
                null, null);

        String filePath = intent.getStringExtra(Transaction.EXTRA_FILE_PATH);
        Log.v(TAG, filePath);
        new File(filePath).delete();
    }
}
