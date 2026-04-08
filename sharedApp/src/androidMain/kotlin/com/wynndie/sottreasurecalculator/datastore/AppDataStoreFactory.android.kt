package com.wynndie.sottreasurecalculator.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.wynndie.sottreasurecalculator.datastore.DATA_STORE_FILE_NAME
import okio.Path.Companion.toPath

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
actual class AppDataStoreFactory(private val context: Context) {
    actual fun create(): DataStore<Preferences> {
        return PreferenceDataStoreFactory.createWithPath {
            context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath.toPath()
        }
    }
}