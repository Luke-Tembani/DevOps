package com.codedev.pharmacy.dialogs

import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.codedev.pharmacy.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.setBottomDialog(
    onSendClick:(String) -> Unit
){

    val dialog = BottomSheetDialog(requireContext(),R.style.DialogStyle)
    val view = layoutInflater.inflate(R.layout.password_reset,null)
    dialog.setContentView(view)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val reset_email = view.findViewById<EditText>(R.id.reset_email)
    val btnReset = view.findViewById<Button>(R.id.btnReset)
    val btnCancel = view.findViewById<Button>(R.id.btnCancel)

    btnReset.setOnClickListener {
        val email = reset_email.text.toString().trim()
        onSendClick(email)
        dialog.dismiss()
    }

    btnCancel.setOnClickListener {
        dialog.dismiss()
    }

}
