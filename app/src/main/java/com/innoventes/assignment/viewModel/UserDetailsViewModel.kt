package com.innoventes.assignment.viewModel

import android.content.Context
import android.text.Editable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.innoventes.assignment.comman.Constants
import com.innoventes.assignment.comman.Utility
import com.innoventes.assignment.model.UserDetails


class UserDetailsViewModel : ViewModel() {
    private val _userDetails = MutableLiveData<UserDetails>()
    val userDetails: LiveData<UserDetails> = _userDetails
    val panNumber: MutableLiveData<String> = MutableLiveData("")
    private val birthDate: MutableLiveData<String> = MutableLiveData("")
    var day: MutableLiveData<String> = MutableLiveData("")
    var month: MutableLiveData<String> = MutableLiveData("")
    var year: MutableLiveData<String> = MutableLiveData("")

    private val _isNextButtonEnabled = MutableLiveData(false)
    val isNextButtonEnabled: LiveData<Boolean> = _isNextButtonEnabled


    fun dateSelected(context:Context){
            Utility.showDatePicker(context) { selectDate ->
                val parts = selectDate.split("/")
                // Extract day, month, and year
                day.value = parts[0]
                month.value = parts[1]
                year.value = parts[2]
                updateBirthDate()
            }
    }

    fun afterPANNumberTextChanged(editable: Editable?) {
        editable?.let {
            panNumber.value = it.toString()
            updateUserDetails(panNumber.value,birthDate.value)
        }
    }

    private fun updateBirthDate() {
        // Check if all parts of the birthdate are available
        if (day.value != null && month.value != null && year.value != null) {
            // Construct the complete birthdate string
            val fullBirthDate = "${day.value}-${month.value}-${year.value}"

            birthDate.value = fullBirthDate
            // Update user details with the new value
            updateUserDetails(panNumber.value, fullBirthDate)
        }
    }

    private fun isPANValid(panNumber: String): Boolean {
        val panRegex = Constants.PAN_REGEX_PATTERN
        return panRegex.matches(panNumber)
    }

   //  Function to validate birthdate
   private fun isBirthDateValid(birthDate: String): Boolean {
       val dateRegex = Constants.BIRTH_REGEX_PATTERN
       return dateRegex.matches(birthDate)
    }

    // Function to update user details
    private fun updateUserDetails(userDetails: String?, birthDate: String?) {
        _userDetails.value?.panNumber = userDetails.toString()
        _userDetails.value?.birthDate = birthDate.toString()

        // Update Next button state based on user details validity
        _isNextButtonEnabled.value = userDetails?.let { isPANValid(it)}!! && birthDate?.let { isBirthDateValid(it) }!!
    }


}