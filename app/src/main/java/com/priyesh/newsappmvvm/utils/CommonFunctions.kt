package com.priyesh.newsappmvvm.utils

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object CommonFunctions {

    const val EEEE_MMM_dd_yyyy = "EEEE, MMM dd, yyyy"

    fun convertISOToRequiredFormat(
        isoDate: String?,
        requiredFormat: String = "MMM dd, yyyy HH:mm"
    ): String? {
        if (isoDate == null) return null
        return try {
            val correctedISO = isoDate.substringBefore(".") + "Z"
            val isoFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }
            val date = isoFormat.parse(correctedISO)
            val outputFormat = SimpleDateFormat(requiredFormat, Locale.getDefault())
            outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun timeMillisToRequiredFormat(
        timeInMillis: Long,
        requiredFormat: String = EEEE_MMM_dd_yyyy
    ): String {
        val sdf = SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.ENGLISH)
        return sdf.format(Date(timeInMillis))
    }

    fun createSingleViewAdapter(view: View): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                return object : RecyclerView.ViewHolder(view) {}
            }

            override fun onBindViewHolder(
                holder: RecyclerView.ViewHolder,
                position: Int
            ) { /*Not in use*/
            }

            override fun getItemCount(): Int = 1
        }
    }
}