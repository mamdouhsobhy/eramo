package com.example.eramo.core.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.eramo.R
import com.example.eramo.databinding.LayoutCloseAppDialogeBinding

class CloseAppDialog(private val itemSelectedForAction: () -> Unit) :
    DialogFragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        LayoutCloseAppDialogeBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
    }

    private fun initClickListeners() {

        binding.btnOk.setOnClickListener {
            itemSelectedForAction.invoke()
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }
}