package com.khatwa.zilalalrahmaapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager;
import java.lang.Exception


/**
 * Created by SimbaAdeeb on 30/8/2019.
 */

class SIMManager(context: Context) {

    var SIM1OperatorName:String? = null
    var SIM2OperatorName:String? = null
    var isSIM1Ready = false
    var isSIM2Ready = false
    var isDualSIM = false


    init {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

            val subscriptionManager = context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

                    val activeSubscriptionInfoList = subscriptionManager.activeSubscriptionInfoList
                    if (activeSubscriptionInfoList.size >= 2) {
                        isDualSIM = true

                        try {
                            val sim1 = activeSubscriptionInfoList[0]
                            sim1.simSlotIndex
                            isSIM1Ready = true
                            SIM1OperatorName = sim1.carrierName.toString()
                        } catch (e:Exception) {
                            e.printStackTrace()
                        }

                        try {
                            val sim2 = activeSubscriptionInfoList[1]
                            sim2.simSlotIndex
                            isSIM1Ready = true
                            SIM2OperatorName = sim2.carrierName.toString()
                        } catch (e:Exception) {
                            e.printStackTrace()
                        }

                    } else if (activeSubscriptionInfoList.size == 1) {
                        val sim = activeSubscriptionInfoList[0]
                        val simSlotIndex = sim.simSlotIndex
                        if (simSlotIndex == 0) {
                            isSIM1Ready = true
                            SIM1OperatorName = sim.carrierName.toString()
                        } else {
                            isSIM2Ready = true
                            SIM2OperatorName = sim.carrierName.toString()
                        }
                    }
                }
            } else {
                val activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList()
                if (activeSubscriptionInfoList.size >= 2) {
                    isDualSIM = true

                    try {
                        val sim1 = activeSubscriptionInfoList[0]
                        sim1.simSlotIndex
                        isSIM1Ready = true
                        SIM1OperatorName = sim1.carrierName.toString()
                    } catch (e:Exception) {
                        e.printStackTrace()
                    }

                    try {
                        val sim2 = activeSubscriptionInfoList[1]
                        sim2.simSlotIndex
                        isSIM1Ready = true
                        SIM2OperatorName = sim2.carrierName.toString()
                    } catch (e:Exception) {
                        e.printStackTrace()
                    }

                } else if (activeSubscriptionInfoList.size == 1) {
                    val sim = activeSubscriptionInfoList[0]
                    val simSlotIndex = sim.simSlotIndex
                    if (simSlotIndex == 0) {
                        isSIM1Ready = true
                        SIM1OperatorName = sim.carrierName.toString()
                    } else {
                        isSIM2Ready = true
                        SIM2OperatorName = sim.carrierName.toString()
                    }
                }
            }

        }else{

            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            try {
                SIM1OperatorName = getSimOperatorNameBySlot(context, "getSimOperatorNameGemini", 0)
                SIM2OperatorName = getSimOperatorNameBySlot(context, "getSimOperatorNameGemini", 1)
            }catch (e:GeminiMethodNotFoundException){
                e.printStackTrace()
                try{
                    SIM1OperatorName = getSimOperatorNameBySlot(context, "getSimOperatorName", 0)
                    SIM2OperatorName = getSimOperatorNameBySlot(context, "getSimOperatorName", 1)
                }catch (e1:GeminiMethodNotFoundException){
                    e1.printStackTrace()
                    try {
                        SIM1OperatorName = getSimOperatorNameBySlot(context, "getSimOperatorNameDs", 0)
                        SIM2OperatorName = getSimOperatorNameBySlot(context, "getSimOperatorNameDs", 1)
                    }catch (e2:GeminiMethodNotFoundException){
                        e2.printStackTrace()

                        try {
                            SIM1OperatorName = getSimOperatorNameBySlot(context, "getDefault", 0)
                            SIM2OperatorName = getSimOperatorNameBySlot(context, "getDefault", 1)
                        }catch (e3:GeminiMethodNotFoundException){
                            e3.printStackTrace()
                        }
                    }
                }
            }

            isSIM1Ready = telephonyManager.simState == TelephonyManager.SIM_STATE_READY
            isSIM2Ready = false

            try{
                isSIM1Ready = getSIMStateBySlot(context, "getSimStateGemini", 0)
                isSIM2Ready = getSIMStateBySlot(context, "getSimStateGemini", 1)
            }catch (e:GeminiMethodNotFoundException){
                e.printStackTrace()

                try{
                    isSIM1Ready = getSIMStateBySlot(context, "getSimState", 0)
                    isSIM2Ready = getSIMStateBySlot(context, "getSimState", 1)
                }catch (e1:GeminiMethodNotFoundException){
                    e1.printStackTrace()
                }
            }

        }
    }

    
    private fun getSimOperatorNameBySlot(context:Context, predictedMethodName:String, slotID:Int):String?{
        var operatorName:String? = null

        val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        try{
            val telephonyClass = Class.forName(telephony.javaClass.name)

            val parameter = ArrayList<Class<*>>(1)
            parameter[0] = Int::class.java
            val getSimID = telephonyClass.getMethod(predictedMethodName, parameter[0])

            val obParameter = ArrayList<Any>(1)
            obParameter[0] = slotID
            val obPhone = getSimID.invoke(telephony, obParameter)

            if (predictedMethodName == "getDefault"){
                val first = getSimID.invoke(null,obParameter) as TelephonyManager
                operatorName = first.simOperatorName
                return operatorName
            }

            if (obPhone != null){
                operatorName = obPhone.toString()
            }

        }catch (e:Exception){
            e.printStackTrace()
            throw GeminiMethodNotFoundException(predictedMethodName)
        }
        return operatorName
    }

    private fun getSIMStateBySlot(context:Context, predictedMethodName:String, slotID:Int):Boolean{
        var isReady = false

        val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        try {
            val telephonyClass = Class.forName(telephony.javaClass.name)

            val parameter = ArrayList<Class<*>>(1)
            val getSimStateGemini = telephonyClass.getMethod(predictedMethodName, parameter[0])

            val obParameter = ArrayList<Any>(1)
            obParameter[0] = slotID
            val ob_phone = getSimStateGemini.invoke(telephony, obParameter)

            if (ob_phone != null){
               val simState = Integer.parseInt(ob_phone.toString())
                if (simState == TelephonyManager.SIM_STATE_READY){
                    isReady = true
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            throw GeminiMethodNotFoundException(predictedMethodName)
        }

        return isReady
    }

    private class GeminiMethodNotFoundException(info:String): Exception(info){
        private val serialVersionUID = -996812356902545308L
    }

    fun printTelephonyManagerMethodNamesForThisDevice(context:Context){
        val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val  telephonyClass:Class<*>
        try {
            telephonyClass = Class.forName(telephony.javaClass.name)
            val methods = telephonyClass.methods
            for (idx in  0..methods.size)
                println("\n" + methods[idx])

        }catch (e:ClassNotFoundException){
            e.printStackTrace()
        }
    }
}
