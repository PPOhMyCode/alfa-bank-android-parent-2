package com.example.alfa_bank_android_app_parent_2.ui.receipt

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentReceiptBinding
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentReceiptsBinding
import com.example.alfa_bank_android_app_parent_2.ui.downloader.AndroidDownloader

class ReceiptFragment : Fragment() {

    companion object {
        fun newInstance() = ReceiptFragment()
    }

    private lateinit var viewModel: ReceiptViewModel


    private lateinit var binding:FragmentReceiptBinding

    var myDownload: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
        binding = FragmentReceiptBinding.inflate(inflater, container, false)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.materialCardView.setOnClickListener {

            val downloader = AndroidDownloader(requireActivity())
            downloader.downloadFile("https://storage.yandexcloud.net/pdf01/%D0%94%D0%BE%D0%BA%D1%83%D0%BC%D0%B5%D0%BD%D1%82%20Microsoft%20Word.pdf?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=YCAJEliNX3eZ-DIFmtMz-Emzq%2F20230611%2Fru-central1%2Fs3%2Faws4_request&X-Amz-Date=20230611T031659Z&X-Amz-Expires=3600&X-Amz-Signature=B261358C0B92CED108BF2FDB9216C196A08BED439266EC787938A4FCB1CE3494&X-Amz-SignedHeaders=host")

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReceiptViewModel::class.java)
        // TODO: Use the ViewMode
    }

}