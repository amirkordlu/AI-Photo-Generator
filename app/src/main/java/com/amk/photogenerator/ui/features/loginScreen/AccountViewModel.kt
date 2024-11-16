package com.amk.photogenerator.ui.features.loginScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
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

class AccountViewModel(

) : ViewModel() {

    val userID = mutableStateOf("")
    val hasLogin = mutableStateOf(false)

    val savedData = mutableStateOf("")
    val points = mutableStateOf(0)


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

    fun loadPointsFromBazaar(context: Context, lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch(coroutineExceptionHandler) {
            BazaarStorage.getSavedData(
                context,
                lifecycleOwner,
                callback = BazaarStorageCallback { response ->
                    val currentPoints = response?.data?.toReadableString()?.toIntOrNull() ?: 0
                    points.value = currentPoints
                }
            )
        }
    }

    fun addPoints(context: Context, lifecycleOwner: LifecycleOwner, increment: Int) {
        val newPoints = points.value + increment
        saveDataInBazaar(context, lifecycleOwner, newPoints.toString())
        points.value = newPoints
    }

    fun subtractPoints(context: Context, lifecycleOwner: LifecycleOwner, decrement: Int) {
        val newPoints = points.value - decrement
        if (newPoints >= 0) {
            saveDataInBazaar(context, lifecycleOwner, newPoints.toString())
            points.value = newPoints
        } else {
            Log.v("AccountViewModel", "امتیاز ناکافی")
        }
    }

}