package com.example.eramo.core.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.eramo.R
import com.example.eramo.databinding.FragmentBaseBinding
import com.example.eramo.core.presentation.extensions.hideKeyboard
import com.facebook.shimmer.ShimmerFrameLayout
import es.dmoral.toasty.Toasty
import com.example.eramo.core.presentation.dialog.ViewDialog

import java.lang.reflect.ParameterizedType

abstract class BaseFragmentBinding<VB : ViewBinding> : Fragment() {

    val binding by lazy { initBinding() }
    private val baseBinding by lazy { FragmentBaseBinding.inflate(layoutInflater) }
    private val loadingDialog by lazy { activity?.let { ViewDialog(it) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseBinding.flContainer.apply {
            removeAllViews()
            addView(binding.root)
        }
        dismissLoading()
        return baseBinding.root
    }

    override fun onDestroy() {
        // Dismiss dialog before fragment destroyed otherwise IllegalArgumentException will arise.
        dismissLoading()
        super.onDestroy()
    }

    /**
     * Show loading dialog
     */
    fun showLoading() {
        loadingDialog?.showDialog()
    }

    /**
     * Dismiss loading dialog
     */
    fun dismissLoading() {
        // Make sure that fragment is alive otherwise IllegalArgumentException will arise.
        if (isDetached.not()) loadingDialog?.hideDialog()
    }

    /**
     * Show Toast
     */
    fun generalToast(message: String) {
        makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun successToast(message: String) {
        Toasty.success(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun errorToast(message: String) {
        Toasty.error(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun animateUp(view: View) {
        val animation = android.view.animation.AnimationUtils.loadAnimation(
            requireActivity(),
            R.anim.slide_anim_up_slow
        )
        view.startAnimation(animation)
    }

    fun animateDown(view: View) {
        val animation = android.view.animation.AnimationUtils.loadAnimation(
            requireActivity(),
            R.anim.slide_anim_down_slow
        )
        view.startAnimation(animation)
    }

    fun animateIn(view: View) {
        val animation = android.view.animation.AnimationUtils.loadAnimation(
            requireActivity(),
            R.anim.fade_out
        )
        view.startAnimation(animation)
    }

    /**
     * start Shimmer
     */
    fun startShimmer(shimmerView: ShimmerFrameLayout, view: View) {
        shimmerView.isVisible = true
        view.isVisible = false
        shimmerView.startShimmerAnimation()
    }

    /**
     * stop Shimmer
     */
    fun stopShimmer(shimmerView: ShimmerFrameLayout, view: View) {
        shimmerView.isVisible = false
        view.isVisible = true
        shimmerView.stopShimmerAnimation()
    }


    @Suppress("UNCHECKED_CAST")
    private fun initBinding(): VB {
        val superclass = javaClass.genericSuperclass as ParameterizedType
        val method = (superclass.actualTypeArguments[0] as Class<Any>)
            .getDeclaredMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as VB
    }


    fun <T : Any> setBackStackResult(key: String, data: T, doBack: Boolean = true) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
        if (doBack) {
            findNavController().popBackStack()
        }
    }

    fun <T : Any> getBackStackResult(key: String, result: (T) -> (Unit)) {
        val observer =
            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
        observer?.observe(viewLifecycleOwner) {
            it?.let {
                result(it)
            }
        }
        observer?.postValue(null)
    }

    override fun onDestroyView() {
        view?.hideKeyboard()
        loadingDialog?.hideDialog()
        super.onDestroyView()
//        _binding = null
    }

}