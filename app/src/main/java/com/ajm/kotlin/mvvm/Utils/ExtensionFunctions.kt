package com.ajm.kotlin.mvvm.Utils

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load

object ExtensionFunctions {
    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.GONE
    }

    fun View.invisible() {
        visibility = View.INVISIBLE
    }

    fun View.disable() {
        this.animate().apply {
            duration = 200
            alpha(0.5f)
            start()
        }
        isEnabled = false
    }

    fun View.enable() {
        this.animate().apply {
            duration = 200
            alpha(1f)
            start()
        }
        isEnabled = true
    }



    fun Context.showToast(message: String?) {
        if (!message.isNullOrBlank())
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.showToast(message: String?) {
        requireContext().showToast(message)
    }



    fun Fragment.showSnackBar(message: String?) {
        requireView().showSnackBar(message)
    }

    fun View.showSnackBar(message: String?) {
        if (!message.isNullOrBlank())
            Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
    }




    fun Fragment.safeNavigate(
        directions: NavDirections,
        extras: Navigator.Extras? = null,
        @IdRes fromDest: Int? = null
    ) {
        try {
            val currDest = findNavController().currentDestination as? FragmentNavigator.Destination
            if (javaClass.name == currDest?.className ||
                (fromDest != null && currDest?.id == fromDest)
            ) {
                if (extras != null) {
                    findNavController().navigate(directions, extras)
                } else {
                    findNavController().navigate(directions)
                }
            }
        } catch (e: Exception) {
           e.stackTrace
        }
    }

    fun Fragment.safeNavigate(
        directions: NavDirections,
        navOptions: NavOptions,
        @IdRes fromDest: Int? = null
    ) {
        try {
            val currDest = findNavController().currentDestination as? FragmentNavigator.Destination
            if (javaClass.name == currDest?.className ||
                (fromDest != null && currDest?.id == fromDest)
            ) {
                findNavController().navigate(directions, navOptions)
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }



    fun ImageView.loadUrl(url: String?) {
        if (!url.isNullOrBlank()) {
            val drawable =
                CircularProgressDrawable(this.context).apply {
                    setStyle(CircularProgressDrawable.DEFAULT)
                    start()
                }
            this.load(url) {
                placeholder(drawable)
            }
        }
    }

    fun AppCompatImageView.loadUrl(url: String?) {
        if (!url.isNullOrBlank()) {
            val drawable =
                CircularProgressDrawable(this.context).apply {
                    setStyle(CircularProgressDrawable.DEFAULT)
                    start()
                }

            this.load(url) {
                placeholder(drawable)
            }
        }
    }
    fun Fragment.requireGrandParentFragment() = this.requireParentFragment().requireParentFragment()
    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    fun Fragment.showLoader() {
        CustomLoader.showLoader(requireContext())
    }

    fun Fragment.hideLoader() {
        CustomLoader.hideLoader()
    }
}