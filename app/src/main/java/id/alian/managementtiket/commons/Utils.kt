package id.alian.managementtiket.commons

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import id.alian.managementtiket.commons.Constants.PREFERENCES_NAME

// auth validation
fun validateEmail(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return email.matches(emailPattern.toRegex())
}

fun validatePasswordLength(password: Int): Boolean {
    return password >= 8
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

// show, hide, enable & disable view
fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.remove() {
    this.visibility = View.GONE
}

fun View.disable() {
    this.isEnabled = false
}

fun View.enable() {
    this.isEnabled = true
}

// alert dialog
fun Context.showMaterialAlertDialog(
    positiveButtonLabel: String = "Ok",
    actionOnPositiveButton: () -> Unit,
    negativeButtonLabel: String = "Cancel",
    title: String,
    message: String
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton(negativeButtonLabel) { dialog, _ ->
            // Respond to neutral button press
            dialog.cancel()
        }
        .setPositiveButton(positiveButtonLabel) { _, _ ->
            // Respond to positive button press
            actionOnPositiveButton()
        }
        .show()
}

// toast, snackBar
fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun View.showShortSnackBar(message: String, colorHex: Int) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(colorHex)
        .show()
}

fun View.showShortSnackBarWithAction(
    message: String,
    actionLabel: String,
    block: (Snackbar) -> Unit,
    colorHex: Int,
    actionLabelColor: Int
) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).also { snackBar ->
        snackBar.setAction(actionLabel) {
            block(snackBar)
        }
        snackBar.setBackgroundTint(colorHex)
        snackBar.setActionTextColor(actionLabelColor)
    }.show()

}

fun View.showLongSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun View.showLongSnackBarWithAction(
    message: String, actionLabel: String,
    block: () -> Unit,
    colorHex: String
) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .setAction(actionLabel) {
            block()
        }
        .setBackgroundTint(Color.parseColor(colorHex)).show()
}

// Intent
fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

// color
fun Context.getColorCompat(id: Int) = ContextCompat.getColor(this, id)
