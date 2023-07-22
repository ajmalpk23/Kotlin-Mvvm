package com.ajm.kotlin.mvvm.Ui.User.View

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ajm.kotlin.mvvm.R

import com.ajm.kotlin.mvvm.Ui.User.ViewModel.UserViewModel
import com.ajm.kotlin.mvvm.Utils.ExtensionFunctions.loadUrl
import com.ajm.kotlin.mvvm.Utils.ExtensionFunctions.showToast
import com.ajm.kotlin.mvvm.databinding.FragmentUserDetailsBinding


class FragmentUserDetails : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentUserDetailsBinding
    private val args by navArgs<FragmentUserDetailsArgs>()
    private var selectedItemId: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }


    private fun initView() {
        selectedItemId = args.id
        viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        binding.imgEdit.setOnClickListener(View.OnClickListener {
            editItem()
        })
        binding.btnSave.setOnClickListener(View.OnClickListener {
            saveEditedItem()
        })
        setView()
    }

    @SuppressLint("SetTextI18n")
    private fun setView() {
        if ((viewModel.getItem(selectedItemId)?.id?.rem(2) ?: 0) == 0) {
            binding.txtLable.isVisible = true
            binding.txtLable.text =
                viewModel.getItem(selectedItemId)?.firstName?.get(0).toString() + viewModel.getItem(
                    selectedItemId
                )?.lastName?.get(0).toString()
        } else {
            binding.imageView.isVisible = true
            binding.imageView.loadUrl(viewModel.getItem(selectedItemId)?.avatar)

        }
        binding.txtEmail.text = viewModel.getItem(selectedItemId)?.email
        binding.textViewName.text =
            viewModel.getItem(selectedItemId)?.firstName + " " + viewModel.getItem(selectedItemId)?.lastName
    }

    private fun saveEditedItem() {
        viewModel.updateNames(selectedItemId,binding.etFirstName.text.toString(), binding.etLastName.text.toString())
        showToast(getString(R.string.update_success))
        findNavController().popBackStack()
    }

    private fun editItem() {
        val popup = android.app.AlertDialog.Builder(context)
        popup

            .setMessage(getString(R.string.are_you_sure_to_edit_item))
            .setPositiveButton(
                getString(R.string.yes),
                DialogInterface.OnClickListener { dialoginterface, i ->
                    binding.etFirstName.setText( viewModel.getItem(selectedItemId)?.firstName)
                    binding.etLastName.setText( viewModel.getItem(selectedItemId)?.lastName)
                    binding.carviewEdit.isVisible = true
                    binding.btnSave.isVisible = true
                }).setNegativeButton(
                getString(R.string.No),
                DialogInterface.OnClickListener { dialoginterface, i ->

                })
        popup.show()

    }


}