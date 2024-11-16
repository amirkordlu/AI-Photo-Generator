package com.amk.photogenerator.ui.features.loginScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amk.photogenerator.util.coroutineExceptionHandler
import com.farsitel.bazaar.core.BazaarSignIn
import com.farsitel.bazaar.core.model.BazaarSignInOptions
import com.farsitel.bazaar.core.model.SignInOption
import com.farsitel.bazaar.storage.BazaarStorage
import com.farsitel.bazaar.storage.callback.BazaarStorageCallback
import com.farsitel.bazaar.util.ext.toReadableString
import kotlinx.coroutines.launch
import kotlin.math.log

class AccountViewModel(

) : ViewModel() {

    val userID = mutableStateOf("")
    val hasLogin = mutableStateOf(false)

    val savedData = mutableStateOf("")
    val getSavedData = mutableStateOf("")

    // Bazaar InApp Login
    fun signInBazaar(context: Context, activity: Activity, lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {

            val signInOption = BazaarSignInOptions.Builder(SignInOption.DEFAULT_SIGN_IN).build()
            val client = BazaarSignIn.getClient(context, signInOption)

            val intent = client.getSignInIntent()

            ActivityCompat.startActivityForResult(activity, intent, 1, null)

        }

    }

    fun getBazaarLogin(context: Context, lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch(coroutineExceptionHandler) {
            BazaarSignIn.getLastSignedInAccount(
                context, lifecycleOwner
            ) { response ->

                val account = response?.data

                if (account?.accountId?.isNotEmpty() == true) {
                    userID.value = account.accountId
                    hasLogin.value = true
                    Log.v("AccountViewModel", account.accountId.toString())
                }


            }
        }
    }

    // خواندن داده‌های ذخیره شده از Bazaar
    fun getSavedDataFromBazaar(context: Context, lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch(coroutineExceptionHandler) {
            BazaarStorage.getSavedData(
                context,
                lifecycleOwner,
                callback = BazaarStorageCallback { response ->
                    getSavedData.value = response?.data?.toReadableString().toString()
                }

            )
        }
    }

    // ذخیره داده در Bazaar و سپس بازیابی آن
    fun saveDataInBazaar(context: Context, lifecycleOwner: LifecycleOwner, data: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            BazaarStorage.saveData(context,
                lifecycleOwner,
                data = data.toByteArray(),
                BazaarStorageCallback { savedResponse ->
                    savedData.value = savedResponse?.data?.toReadableString().toString()
                })
        }
    }

}