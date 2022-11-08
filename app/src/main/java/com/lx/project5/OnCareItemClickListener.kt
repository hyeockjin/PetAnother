package com.lx.project5

import android.view.View

interface OnCareItemClickListener {

    fun onItemClick(holder: CareListAdapter.ViewHolder?, view: View?, position: Int)

}