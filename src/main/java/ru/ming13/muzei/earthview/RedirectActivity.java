/*
 * Copyright 2015 Artur Dryomov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.ming13.muzei.earthview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.apps.muzei.api.MuzeiContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

/**
 * This activity's sole purpose is to redirect users to Muzei, which is where they should
 * activate Muzei and then select the Earth View source.
 *
 * You'll note the usage of the `enable_launcher` boolean resource value to only enable
 * this on API 29+ devices as it is on API 29+ that a launcher icon becomes mandatory for
 * every app.
 */
public class RedirectActivity extends Activity
{
    private static final String EARTH_VIEW_AUTHORITY = "ru.ming13.muzei.earthview";
    private static final String MUZEI_PACKAGE_NAME = "net.nurik.roman.muzei";
    private static final String PLAY_STORE_LINK =
            "https://play.google.com/store/apps/details?id=" + MUZEI_PACKAGE_NAME;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // First check whether Earth View is already selected
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(MUZEI_PACKAGE_NAME);
        if (MuzeiContract.Sources.isProviderSelected(this, EARTH_VIEW_AUTHORITY)
                && launchIntent != null) {
            // Already selected so just open Muzei
            startActivityForResult(launchIntent, 1);
            return;
        }
        // Earth View isn't selected, so try to deep link into Muzei's Sources screen
        Intent deepLinkIntent = MuzeiContract.Sources.createChooseProviderIntent(EARTH_VIEW_AUTHORITY);
        if (tryStartIntent(deepLinkIntent, R.string.toast_enable)) {
            return;
        }
        // createChooseProviderIntent didn't work, so try to just launch Muzei
        if (launchIntent != null && tryStartIntent(launchIntent, R.string.toast_enable_source)) {
            return;
        }
        // Muzei isn't installed, so try to open the Play Store so that
        // users can install Muzei
        Intent playStoreIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(PLAY_STORE_LINK));
        if (tryStartIntent(playStoreIntent, R.string.toast_muzei_missing_error)) {
            return;
        }
        // Only if all Intents failed do we show a 'everything failed' Toast
        Toast.makeText(this, R.string.toast_play_store_missing_error, Toast.LENGTH_LONG).show();
        finish();
    }

    private boolean tryStartIntent(@NonNull Intent intent, @StringRes int toastResId) {
        try {
            // Use startActivityForResult() so that we get a callback to
            // onActivityResult() if the user hits the system back button
            startActivityForResult(intent, 1);
            Toast.makeText(this, toastResId, Toast.LENGTH_LONG).show();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // It doesn't matter what the result is, the important part is that the
        // user hit the back button to return to this activity. Since this activity
        // has no UI of its own, we can simply finish the activity.
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
