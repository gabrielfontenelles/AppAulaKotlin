package com.example.aulaandroid.Models

data class Item(
    var id: String ,
    var title: String ,
    var desc: String ,
    var priority: String
) {
    constructor() : this("", "", "", "") // Construtor vazio necessário para Firebase
}



