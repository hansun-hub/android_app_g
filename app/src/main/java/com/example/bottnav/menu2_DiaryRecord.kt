package com.example.bottnav

import android.content.Context

class menu2_DiaryRecord(context: Context, title: String, date: String) {
    var title: String
    var date: String?
    var contents: String?
    var selectedChallenge: String?
    var rate:Int = 0
    val dbManager = DBManager(context)

    init {
        this.title = title
        this.date = date
        contents = dbManager.getContents(date).toString()
        selectedChallenge = dbManager.getSelectedChallenge(date, title)
        rate = dbManager.getRate(date)

    }
}