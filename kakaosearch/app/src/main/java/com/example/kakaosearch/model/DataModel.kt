package com.example.kakaosearch.model

import com.example.kakaosearch.model.enum.KakaoSearchSortEnum
import com.example.kakaosearch.model.response.ImageSearchResponse
import io.reactivex.Single

interface DataModel {
    fun getData(query:String, sort: KakaoSearchSortEnum, page:Int, size:Int): Single<ImageSearchResponse>
}