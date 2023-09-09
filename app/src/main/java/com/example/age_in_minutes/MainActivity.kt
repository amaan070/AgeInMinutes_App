package com.example.age_in_minutes

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
   private var selectedDate : TextView? = null
    private var ageInMinutes : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val datePickButton : Button = findViewById(R.id.btn_date)
        //we now have access to the button using the datePickButton variable
        selectedDate=findViewById(R.id.selected_date)
        ageInMinutes=findViewById(R.id.viewMinutes)
        /* we now have access to the Text Views of Selected date and total minutes
        using the variables selectedDate and ageInMinutes  */
        datePickButton.setOnClickListener {
            clickDatePicker()
        }

    }
    @SuppressLint("SetTextI18n")
    private fun clickDatePicker(){

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            {_, selected_year, selected_month, selected_dayOfMonth ->

                selectedDate?.text="$selected_dayOfMonth/${selected_month+1}/$selected_year"
                //setting the selected date to display

                //the next lines are to calculate the number of minutes
                //first we will put dates into specific formats
                val format = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                //defining the format in variable 'format'
                val theDate = format.parse("$selected_dayOfMonth/${selected_month+1}/$selected_year")
                //converting the string into actual date
                //we are using date as a data type
                theDate?.let {
                    val selectedDateInMinutes = theDate.time /60000
                    val currentDate = format.parse(format.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes=currentDate.time/60000
                        //the property .time gives time in milliseconds, we converted it to minutes
                        val differenceInMinutes=currentDateInMinutes-selectedDateInMinutes
                        ageInMinutes?.text="$differenceInMinutes"
                    }
                }
            },
            year,month,day
        )
        dpd.datePicker.maxDate=System.currentTimeMillis()-86400000
        //max date that can be picked is set to current time - 24 hours i.e. previous day
        //we can select the latest date of yesterday
        dpd.show()
    }
}