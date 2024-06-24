package com.example.testapi

import android.util.Log
import retrofit2.Call
import retrofit2.Response

class Presenter(val crudView: MainActivity) {

    fun getData() {
        NetworkConfig.getService().getData()
            .enqueue(object : retrofit2.Callback<ResultComic>{
                override fun onFailure(call: Call<ResultComic>, t: Throwable) {
                    crudView.onFailedGet(t.localizedMessage)
                    Log.d("Error", "Error Data")
                }

                override fun onResponse(call: Call<ResultComic>, response: Response<ResultComic>) {
                    if (response.isSuccessful) {
                        val status = response.body()?.status
                        if (status == 200) {
                            val data = response.body()?.comic
                            crudView.onSuccessGet(data ?: emptyList())
                        } else {
                            crudView.onFailedGet("Error $status")
                        }
                    }
                }
            })
    }

    fun deleteData(id: String?){
        NetworkConfig.getService()
            .deleteComic(id ?: "")
            .enqueue(object: retrofit2.Callback<ResultStatus>{
                override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                    crudView.onErrorDelete(t.localizedMessage)
                }

                override fun onResponse(call: Call<ResultStatus>, response: Response<ResultStatus>) {
                    if (response.isSuccessful && response.body()?.status == 200) {
                        crudView.onSuccessDelete(response.body()?.msg ?: "")
                    } else {
                        crudView.onErrorDelete(response.body()?.msg ?: "")
                    }
                }
            })
    }

}