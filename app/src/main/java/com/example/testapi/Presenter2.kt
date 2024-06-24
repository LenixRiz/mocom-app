package com.example.testapi

import retrofit2.Call
import retrofit2.Response

class Presenter2 (val crudView: UpdateAddActivity) {
    fun addData(title: String, author: String, image: String, description: String) {
        NetworkConfig.getService()
            .addComic(title, author, image, description)
            .enqueue(object : retrofit2.Callback<ResultStatus>{
                override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                    crudView.onErrorAdd(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResultStatus>,
                    response: Response<ResultStatus>
                ) {
                    if (response.isSuccessful && response.body()?.status == 200) {
                        crudView.successAdd(response.body()?.msg ?: "")
                    } else {
                        crudView.onErrorAdd(response.body()?.msg ?: "")
                    }
                }
            })
    }

    fun updateData(id: String, title: String, author: String, image: String, description: String){
        NetworkConfig.getService()
            .updateComic(id, title, author, image, description)
            .enqueue(object : retrofit2.Callback<ResultStatus>{
                override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                    crudView.onErrorUpdate(t.localizedMessage)
                }

                override fun onResponse(call: Call<ResultStatus>, response: Response<ResultStatus>) {
                    if (response.isSuccessful && response.body()?.status == 200) {
                        crudView.onSuccessUpdate(response.body()?.msg ?: "")
                    } else {
                        crudView.onErrorUpdate(response.body()?.msg ?: "")
                    }
                }
            })
    }
}