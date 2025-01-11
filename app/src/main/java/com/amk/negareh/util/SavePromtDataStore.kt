package com.amk.negareh.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

suspend fun savePrompt(context: Context, prompt: String) {
    context.dataStore.edit { preferences ->
        preferences[PROMPT_KEY] = prompt
    }
}

fun getPrompt(context: Context): Flow<String?> {
    return context.dataStore.data.map { preferences ->
        preferences[PROMPT_KEY]
    }
}