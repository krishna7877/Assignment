package com.innoventes.assignment.comman

import android.app.DatePickerDialog
import android.content.Context
import com.innoventes.assignment.R
import java.text.SimpleDateFormat
import java.util.Calendar

object Utility {

    fun showDatePicker(mContext: Context, onSelectDate: (selectDate: String) -> Unit){
        val myCalendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            dateFormat(Constants.DATE_FORMAT_DDMMYY,Constants.DATE_FORMAT_DDMMYY,day.toString()+"/"+(month+1)+"/"+year).let {
                onSelectDate.invoke(it)
            }
        }
        val mDialog = DatePickerDialog(
            mContext, R.style.DialogTheme, date,
            myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH], myCalendar[Calendar.DAY_OF_MONTH]
        )
        val yy = myCalendar.get(Calendar.YEAR)
        myCalendar.set(yy.minus(17),11,31)
        mDialog.datePicker.maxDate = myCalendar.timeInMillis
        myCalendar.set(1950, 0, 1)
        mDialog.datePicker.minDate = (myCalendar.timeInMillis)



        if (mDialog.isShowing) {
            mDialog.dismiss()
            mDialog.show()
        } else {
            mDialog.show()
        }
    }

    private fun dateFormat(provideFormat : String, requireFormat : String, date : String) : String{
        return SimpleDateFormat(requireFormat).format(SimpleDateFormat(provideFormat).parse(date)?.time)
    }

}