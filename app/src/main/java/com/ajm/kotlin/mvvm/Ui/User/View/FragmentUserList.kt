package com.ajm.kotlin.mvvm.Ui.User.View

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajm.kotlin.mvvm.Data.Remote.Response.UserApiResponse.DataItem
import com.ajm.kotlin.mvvm.R
import com.ajm.kotlin.mvvm.Ui.User.Adapter.UserListAdapter

import com.ajm.kotlin.mvvm.Ui.User.ViewModel.UserViewModel
import com.ajm.kotlin.mvvm.Utils.ExtensionFunctions.dp
import com.ajm.kotlin.mvvm.Utils.ExtensionFunctions.hideLoader
import com.ajm.kotlin.mvvm.Utils.ExtensionFunctions.safeNavigate
import com.ajm.kotlin.mvvm.Utils.ExtensionFunctions.showLoader

import com.ajm.kotlin.mvvm.Utils.ExtensionFunctions.showToast
import com.ajm.kotlin.mvvm.Utils.Resource
import com.ajm.kotlin.mvvm.Utils.SpaceItemDecoration
import com.ajm.kotlin.mvvm.databinding.FragmentUserListBinding


class FragmentUserList : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentUserListBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)


        callApi()
        setRecyclerView()
    }

    private fun callApi() {
        if (viewModel.userLIst.value == null || viewModel.userLIst.value?.size == 0) {
            viewModel.getUserListApiState.asLiveData().observe(viewLifecycleOwner){
                when (it) {
                    is Resource.Error -> {
                        showToast(it.errorMessage)
                        hideLoader()
                    }
                    is Resource.Loading -> {
                        showLoader()
                    }
                    is Resource.Success -> {
                        hideLoader()
                    }
                }
            }
            viewModel.getUserList()
        }
    }

    private fun setRecyclerView() {
        binding.rcyView.layoutManager = LinearLayoutManager(requireContext())
        binding.rcyView.adapter = adapter
        binding.rcyView.apply {
            adapter = adapter
            itemAnimator = null
            addItemDecoration(
                SpaceItemDecoration(
                    space = 12.dp,
                    includeEdge = false
                )
            )
        }
        viewModel.userLIst.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private val adapter by lazy {
        UserListAdapter(onItemClick = {
            safeNavigate(
                FragmentUserListDirections.actionUserListToUserDeatails(
                    id = it.id!!
                )
            )

        }, onDelete = {
            deleteItem(it)
        })
    }

    private fun deleteItem(item: DataItem) {
        val popup = android.app.AlertDialog.Builder(context)
        popup
            .setMessage(getString(R.string.are_you_sure_to_delete_item))
            .setPositiveButton(
                getString(R.string.yes),
                DialogInterface.OnClickListener { dialoginterface, i ->
                    viewModel.delateItem(item)

                    showToast(getString(R.string.deleted_success))
                }).setNegativeButton(
                getText(R.string.No),
                DialogInterface.OnClickListener { dialoginterface, i ->

                })
        popup.show()

    }

}