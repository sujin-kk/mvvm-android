package com.example.kakaosearch.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kakaosearch.base.BaseViewModel
import com.example.kakaosearch.model.DataModel
import com.example.kakaosearch.model.enum.KakaoSearchSortEnum
import com.example.kakaosearch.model.response.ImageSearchResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val model: DataModel): BaseViewModel() {
    private val TAG = "MainViewModel"

    // 내부에서는 LiveData 변경 가능
    private val _imageSearchResponseLiveData = MutableLiveData<ImageSearchResponse>()

    // 외부에서는 LiveData 변경 불가, 외부에서 참조하는 변수, observe만 가능
    val imageSearchResponseLiveData: LiveData<ImageSearchResponse>
        get() = _imageSearchResponseLiveData

    fun getImageSearch(query: String, page:Int, size:Int) {
        addDisposable(model.getData(query, KakaoSearchSortEnum.Accuracy, page, size)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    if (documents.size > 0) {
                        // ViewModel에서 데이터 번경
                        _imageSearchResponseLiveData.postValue(this)
                    }
                }
            }, {
                Log.d(TAG, "response error, message : ${it.message}")
            }))
    }

}