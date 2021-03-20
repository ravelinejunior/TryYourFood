package br.com.tryyourfood.binding_adapter

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import br.com.tryyourfood.data.database.entities.FoodJokeEntity
import br.com.tryyourfood.model.FoodJoke
import br.com.tryyourfood.utils.NetworkResult
import com.google.android.material.card.MaterialCardView

class FoodJokeBinding {
    companion object {

        @BindingAdapter("readApiResponseJoke", "readDatabaseJoke", requireAll = false)
        @JvmStatic
        fun setCardAndProgressVisibility(
            view: View,
            apiResponse: NetworkResult<FoodJoke>?,
            database: List<FoodJokeEntity>?
        ) {
            when (apiResponse) {
                is NetworkResult.Loading -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.VISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.GONE
                        }
                    }
                }
                is NetworkResult.Error -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.GONE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                            if (database != null) {
                                if (database.isEmpty()) {
                                    view.visibility = View.GONE
                                }
                            }
                        }
                    }
                }

                is NetworkResult.Success -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.GONE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        @BindingAdapter("apiResponseErrorJoke","readDatabaseErrorJoke",requireAll = false)
        @JvmStatic
        fun setErrorViewsVisibility(view: View,apiResponse:NetworkResult<FoodJoke>?,database: List<FoodJokeEntity>?){
            if (database != null ){
                if(database.isEmpty()){
                    view.visibility = View.VISIBLE
                    if(view is TextView){
                        if(apiResponse != null){
                            view.text = apiResponse.message.toString()
                        }
                    }
                }
            }

            if(apiResponse is NetworkResult.Success){
                view.visibility = View.GONE
            }
        }
    }
}