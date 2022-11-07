package com.lx.map

 data class UserData (
    val name:String? = null,
    val value:String? = null,
    val address:String? = null,
    val teacher:String? = null,
    val mobile:String? = null,
    val price:String? = null,

)
//                var name = findViewById<TextView>(R.id.className)
//                var value = findViewById<TextView>(R.id.classValue)
//                var address = findViewById<TextView>(R.id.classAddress)
//                var teacher = findViewById<TextView>(R.id.subName)
//                var number = findViewById<TextView>(R.id.mobile)
//                var price = findViewById<TextView>(R.id.price)
//                var arr = it.tag.toString().split("/") //마커에 붙인 태그, arr= List
//                name.text = it.title
//                value.text = it.snippet
//                address.text = arr[0]
//                teacher.text = arr[1]
//                number.text = arr[2]
//                price.text = arr[3]