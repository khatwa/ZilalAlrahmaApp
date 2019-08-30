package com.khatwa.zilalalrahmaapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build

/**
 * Created by SimbaAdeeb on 30/8/2019.
 */


class DialManager(var context: Context){


    var SIMManager: SIMManager = SIMManager(context)

    fun dial(dialNumber: String) {
        val mDialNumber = if (dialNumber.contains("#")) dialNumber.replace("#", "%23") else dialNumber
        val callIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$mDialNumber"))
        context.startActivity(callIntent)
    }

    fun dial(dialNumber: String, simSlotNo: Int) {
        val mDialNumber = if (dialNumber.contains("#")) dialNumber.replace("#", "%23") else dialNumber
        if (SIMManager.isDualSIM) {
            val callIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$mDialNumber"))
            callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            callIntent.putExtra("com.android.phone.force.slot", true)
            callIntent.putExtra("Cdma_Supp", true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                callIntent.putExtra("android.telecom.extra.PHONE_ACCOUNT_HANDLE", " here You have to get phone account handle list by using telecom manger for both sims:- using this method getCallCapablePhoneAccounts()")
                val simSlotNames = arrayOf(
                        "extra_asus_dial_use_dualsim",
                        "com.android.phone.extra.slot",
                        "slot",
                        "simslot",
                        "sim_slot",
                        "subscription",
                        "Subscription",
                        "phone",
                        "com.android.phone.DialingMode",
                        "simSlot",
                        "slot_id",
                        "simId",
                        "simnum",
                        "phone_type",
                        "slotId",
                        "slotIdx"
                )

                for (s in simSlotNames)
                    callIntent.putExtra(s, simSlotNo)

                context.startActivity(callIntent)
            }
        }
    }
}


