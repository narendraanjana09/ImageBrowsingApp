package com.nsa.imagebrowsingapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nsa.imagebrowsingapp.adapters.HomeImagesAdapter
import com.nsa.imagebrowsingapp.adapters.ImageLoadAdapter
import com.nsa.imagebrowsingapp.adapters.OnClickListener
import com.nsa.imagebrowsingapp.databinding.FragmentHomeBinding
import com.nsa.imagebrowsingapp.extra.Keyboard
import com.nsa.imagebrowsingapp.models.image_model
import com.nsa.imagebrowsingapp.ui.description_dialog
import com.nsa.imagebrowsingapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), OnClickListener {

    private val viewModel by viewModels<MainViewModel>()
    private var _binding : FragmentHomeBinding ?= null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)
        viewModel.orientation.value="all"



        val adapter = HomeImagesAdapter(this)

        binding.apply {

            mainRv.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
            mainRv.setHasFixedSize(true)
            mainRv.itemAnimator = null
            mainRv.adapter=adapter.withLoadStateHeaderAndFooter(
                header = ImageLoadAdapter{ },
                footer = ImageLoadAdapter{ adapter.retry() },
            )
            buttonRetry.setOnClickListener {
                adapter.retry()
            }
            clearImg.setOnClickListener {
                searchEdt.setText("")
            }
            searchEdt.setOnEditorActionListener(object : TextView.OnEditorActionListener{
                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                    if(actionId == EditorInfo.IME_ACTION_SEARCH){
                        viewModel.searchImage(searchEdt.text.toString())
                        mainRv.scrollToPosition(0)
                        Keyboard.hide(searchEdt)
                        return true
                    }
                    return false
                }

            })
            searchEdt.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    s.toString().trim()
                    if(s.isNullOrEmpty()){
                        clearImg.alpha=0f
                    }else{
                        clearImg.alpha=1f
                    }
                }
            })
            searchBtn.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action) {
                        MotionEvent.ACTION_UP ->{
                            if(arrowImG.isVisible){
                                arrowImG.isVisible=false
                                searchImg.isVisible=true
                                searchEdt.hint=""
                                flterVisible(true)
                                Keyboard.hide(view)
                            }else{
                                arrowImG.isVisible=true
                                searchImg.isVisible=false
                                flterVisible(false)
                                 var timer = Timer()
                                 val DELAY: Long = 400
                                timer.cancel()
                                timer = Timer()
                                timer.schedule(object : TimerTask() {
                                    override fun run() {
                                        searchEdt.hint="Search here..."
                                    }
                                }, DELAY)
                            }
                        }
                    }

                    return v?.onTouchEvent(event) ?: true
                }
            })
            filterBTN.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action) {
                        MotionEvent.ACTION_UP ->{
                            if(cancelFilterIMG.isVisible){
                                cancelFilterIMG.isVisible=false
                                filterIMG.isVisible=true

                                searchVisible(true)
                            }else{
                                searchVisible(false)
                                cancelFilterIMG.isVisible=true
                                filterIMG.isVisible=false
                            }
                        }
                    }

                    return v?.onTouchEvent(event) ?: true
                }
            })
            orientationBtn.setOnClickListener {
                if(portraitImg.isVisible){
                    portraitImg.isVisible=false;
                    landImg.isVisible=true;
                    allMixOrient.isVisible=false;
                    viewModel.orientation.value="horizontal"
                }else if(landImg.isVisible){
                    portraitImg.isVisible=false;
                    landImg.isVisible=false;
                    allMixOrient.isVisible=true;
                    viewModel.orientation.value="all"
                }else{
                    portraitImg.isVisible=true;
                    landImg.isVisible=false;
                    allMixOrient.isVisible=false;
                    viewModel.orientation.value="vertical"
                }
                viewModel.searchImage(viewModel.currentQuery.value.toString())
            }
            qualityBtn.setOnClickListener {
                if(sdBtn.isVisible){
                    sdBtn.isVisible=false;
                    hdBtn.isVisible=true;
                    adapter.quality(true);
                }else{
                    adapter.quality(false);
                    sdBtn.isVisible=true;
                    hdBtn.isVisible=false;
                }
                adapter.notifyDataSetChanged()
            }
        }



        viewModel.images.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
        viewModel.totalResult.observe(viewLifecycleOwner){

            binding.totalResultsTV.setText(it.toString()+" results ")

        }

        adapter.addLoadStateListener { loadState->

                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                binding.mainRv.isVisible = loadState.source.refresh is LoadState.NotLoading
                binding.buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                binding.textViewError.isVisible = loadState.source.refresh is LoadState.Error

                //for empty view
                if(loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1){
                    binding.mainRv.isVisible=false
                    binding.noResultTv.isVisible=true
                }else{
                    binding.noResultTv.isVisible=false
                }


        }
    }

    private fun searchVisible(b: Boolean) {
       binding.apply {
           if(b){
               searchBtn.alpha=1f
               searchLayout.alpha=1f
           }else{
               searchBtn.alpha=0f
               searchLayout.alpha=0f
           }
       }
    }

    private fun flterVisible(b: Boolean) {
        binding.apply {
            if(!b){
            filterBTN.alpha=0f
            orientationBtn.alpha=0f
            qualityBtn.alpha=0f
        }else{
                filterBTN.alpha=1f
                orientationBtn.alpha=1f
                qualityBtn.alpha=1f
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(imageModel: image_model) {


         val dialog=description_dialog(imageModel)
        dialog.show(parentFragmentManager,"test")


    }

}