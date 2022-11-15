package com.edpub.tesla

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SpinnerItemAdapter(private val list: List<String>, private val context: Context): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position];
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.default_spinner_item, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = list.get(position)

        return view
    }

    private class ItemHolder(row: View?) {
        val label: TextView

        init {
            label = row?.findViewById(R.id.tv_spinner_item) as TextView
        }
    }

}