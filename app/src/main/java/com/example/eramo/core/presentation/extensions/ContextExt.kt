package com.example.eramo.core.presentation.extensions

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.example.eramo.R
import es.dmoral.toasty.Toasty

fun Context.showToast(message: String, connectionError: Boolean = false) {
    if (connectionError) {
        showErrorMessage(getString(R.string.check_internet_connections))
    } else {
        //Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        showErrorMessage(message)

    }
    Log.d("error", message)
}

fun Context.showGenericAlertDialog(parentFragment: FragmentManager ? = null,code: Int = 0, message: String = "") {

//    if (code == 403 || code == 401) {
//        parentFragment?.let {
//            BetterLoginDialog() {
//                startActivity(Intent(this, ActivityAuthorization::class.java))
//            }.show(it,"show dialoge")
//        }
//        return
//    }

    AlertDialog.Builder(this).apply {
        setMessage(message)
        setPositiveButton(getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }
    }.show()

}

fun Context.loadFullScreenImg(url: String = "" , imgUrl : List<String> = emptyList()) {
    val urls = ArrayList<String>()
    if(url.isNotEmpty()){
        urls.add(url)
    }else{
        urls.addAll(imgUrl)
    }

}

fun Context.showSuccessMessage(message: String = "") {
    Toasty.success(this, message, Toast.LENGTH_SHORT, true).show()
}

fun Context.showErrorMessage(message: String = "") {
    Toasty.error(this, message, Toast.LENGTH_SHORT, true).show()
}

fun Context.showInfoMessage(message: String = "") {
    Toasty.info(this, message, Toast.LENGTH_SHORT, true).show()
}