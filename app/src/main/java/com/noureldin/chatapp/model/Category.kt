package com.noureldin.chatapp.model

import com.noureldin.chatapp.R

data class Category(
    val id: String? = null,
    val name: String? = null,
    val image: Int? = null,
){
    companion object{
        val MOVIES = "MOVIES"
        val SPORTS = "SPORTS"
        val MUSICS = "MUSICS"
        fun getCategoriesList(): List<Category>{
            return listOf(
                fromId(MUSICS),
                fromId(SPORTS),
                fromId(MUSICS)

            )
        }
        fun fromId(id: String): Category{
            return when (id){
                MOVIES ->  Category("MOVIES","Movies", R.drawable.movies)
                SPORTS ->  Category("MUSICS","Musics", R.drawable.music)
                MUSICS ->  Category("SPORTS","Sports",R.drawable.sports)
                else ->    Category("SPORTS","Sports",R.drawable.sports)

            }
        }
    }
}
