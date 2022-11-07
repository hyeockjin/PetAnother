package com.lx.map

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.lx.project5.R
import java.security.AccessControlContext

class ListAdapter(val context: Context, val UserList: ArrayList<UserData>) : BaseAdapter() {
    override fun getCount(): Int {
        return UserList.size
    }

    override fun getItem(position: Int): Any {
        return UserList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("ResourceType")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.id.cardView, null)
        val name = view.findViewById<TextView>(R.id.className)
        val value = view.findViewById<TextView>(R.id.classValue)
        val address = view.findViewById<TextView>(R.id.classAddress)
        val teacher = view.findViewById<TextView>(R.id.subName)
        val mobile = view.findViewById<TextView>(R.id.mobile)
        val price = view.findViewById<TextView>(R.id.price)
        //profile, name, email, content 각 변수를 만들어 item_user.xml의 각 정보를 담을 곳의 위치를 가지게 한다.

        val user = UserList[position]
        //user 변수에 배열(또는 서버에서 받아온 데이터)에 담긴 profile, name, email, content 정보를 담아준다.

        //profile.setImageResource(user.profile)
        name.text = user.name
        value.text = user.value
        address.text = user.address
        teacher.text = user.teacher
        mobile.text = user.mobile
        price.text = user.price
        //위에서 가져온 profile, name, email, content 각각의 변수를 만들어둔 카드뷰에 연결시켜준다.

        return view
        //연결이 완료된 뷰를 돌려준다.
    }

}