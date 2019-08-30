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

//    lateinit var imeiSIM1:String
//    lateinit var imeiSIM2:String
    var SIM1OperatorName:String? = null
    var SIM2OperatorName:String? = null
    var isSIM1Ready = false
    var isSIM2Ready = false
    var isDualSIM = false

//    fun getImeiSIM1():String {
//        return imeiSIM1
//    }
//
//    fun getImeiSIM2():String {
//        return imeiSIM2
//    }
//
//    public String getSIM1OperatorName() {
//        return SIM1OperatorName;
//    }
//
//    public String getSIM2OperatorName() {
//        return SIM2OperatorName;
//    }
//
//    public boolean isSIM1Ready() {
//        return isSIM1Ready;
//    }
//
//    public boolean isSIM2Ready() { return isSIM2Ready; }
//
//    public boolean isDualSIM() {
//        return isDualSIM;
//    }

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

//        Toast.makeText(context, "SIM1: $SIM1OperatorName, SIM2: $SIM2OperatorName", Toast.LENGTH_LONG).show()
    }


//    public static SIMManager getInstance(Context context) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//            if(simManager == null) {
//                simManager = new SIMManager();
//                SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                        List activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
//                        if (activeSubscriptionInfoList.size() >= 2) {
//                            simManager.isDualSIM = true;
//
//                            try {
//                                SubscriptionInfo sim1 = (SubscriptionInfo) activeSubscriptionInfoList.get(0);
//                                sim1.getSimSlotIndex();
//                                simManager.isSIM1Ready = true;
//                                simManager.SIM1OperatorName = (String) sim1.getDisplayName();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            try {
//                                SubscriptionInfo sim2 = (SubscriptionInfo) activeSubscriptionInfoList.get(1);
//                                sim2.getSimSlotIndex();
//                                simManager.isSIM1Ready = true;
//                                simManager.SIM2OperatorName = (String) sim2.getDisplayName();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                        } else if (activeSubscriptionInfoList.size() == 1) {
//                            SubscriptionInfo sim = (SubscriptionInfo) activeSubscriptionInfoList.get(0);
//                            int simSlotIndex = sim.getSimSlotIndex();
//                            if (simSlotIndex == 0) {
//                                simManager.isSIM1Ready = true;
//                                simManager.SIM1OperatorName = (String) sim.getDisplayName();
//                            } else {
//                                simManager.isSIM2Ready = true;
//                                simManager.SIM2OperatorName = (String) sim.getDisplayName();
//                            }
//                        }
//                    }
//                } else {
//                    List activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
//                    if (activeSubscriptionInfoList.size() >= 2) {
//                        simManager.isDualSIM = true;
//
//                        try {
//                            SubscriptionInfo sim1 = (SubscriptionInfo) activeSubscriptionInfoList.get(0);
//                            sim1.getSimSlotIndex();
//                            simManager.isSIM1Ready = true;
//                            simManager.SIM1OperatorName = (String) sim1.getDisplayName();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        try {
//                            SubscriptionInfo sim2 = (SubscriptionInfo) activeSubscriptionInfoList.get(1);
//                            sim2.getSimSlotIndex();
//                            simManager.isSIM1Ready = true;
//                            simManager.SIM2OperatorName = (String) sim2.getDisplayName();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    } else if (activeSubscriptionInfoList.size() == 1) {
//                        SubscriptionInfo sim = (SubscriptionInfo) activeSubscriptionInfoList.get(0);
//                        int simSlotIndex = sim.getSimSlotIndex();
//                        if (simSlotIndex == 0) {
//                            simManager.isSIM1Ready = true;
//                            simManager.SIM1OperatorName = (String) sim.getDisplayName();
//                        } else {
//                            simManager.isSIM2Ready = true;
//                            simManager.SIM2OperatorName = (String) sim.getDisplayName();
//                        }
//                    }
//                }
//            }
//        }else{
//            if (simManager == null) {
//                simManager = new SIMManager();
//                TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
//
//                simManager.SIM1OperatorName = null;
//                simManager.SIM2OperatorName = null;
//
//                try {
//                    simManager.SIM1OperatorName = getSimOperatorNameBySlot(context, "getSimOperatorNameGemini", 0);
//                    simManager.SIM2OperatorName = getSimOperatorNameBySlot(context, "getSimOperatorNameGemini", 1);
//                }catch (GeminiMethodNotFoundException e){
//                    e.printStackTrace();
//                    try{
//                        simManager.SIM1OperatorName = getSimOperatorNameBySlot(context, "getSimOperatorName", 0);
//                        simManager.SIM2OperatorName = getSimOperatorNameBySlot(context, "getSimOperatorName", 1);
//                    }catch (GeminiMethodNotFoundException e1){
//                        e1.printStackTrace();
//                        try {
//                            simManager.SIM1OperatorName = getSimOperatorNameBySlot(context, "getSimOperatorNameDs", 0);
//                            simManager.SIM2OperatorName = getSimOperatorNameBySlot(context, "getSimOperatorNameDs", 1);
//                        }catch (GeminiMethodNotFoundException e2){
//                            e2.printStackTrace();
//
//                            try {
//                                simManager.SIM1OperatorName = getSimOperatorNameBySlot(context, "getDefault", 0);
//                                simManager.SIM2OperatorName = getSimOperatorNameBySlot(context, "getDefault", 1);
//                            }catch (GeminiMethodNotFoundException e3){
//                                e3.printStackTrace();
//                            }
//                        }
//                    }
//                }
//
//                simManager.isSIM1Ready = telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY;
//                simManager.isSIM2Ready = false;
//
//                try{
//                    simManager.isSIM1Ready = getSIMStateBySlot(context, "getSimStateGemini", 0);
//                    simManager.isSIM2Ready = getSIMStateBySlot(context, "getSimStateGemini", 1);
//                }catch (GeminiMethodNotFoundException e){
//                    e.printStackTrace();
//
//                    try{
//                        simManager.isSIM1Ready = getSIMStateBySlot(context, "getSimState", 0);
//                        simManager.isSIM2Ready = getSIMStateBySlot(context, "getSimState", 1);
//                    }catch (GeminiMethodNotFoundException e1){
//                        e1.printStackTrace();
//                    }
//                }
//            }
//        }
//
//        return simManager;
//    }


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
