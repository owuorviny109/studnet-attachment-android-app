package com.attachmentplatform.student.data


import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date // Note: Timestamp is preferred for firestore

data class Opportunity(
    @DocumentId var id: String = "",
    val companyName: String = "",
    val title: String = "",
    val description: String = "",
    val requirements: String = "",
    val location: String = "",
    val duration: String = "",
    val availableSlots: Int = 1,
    val applicationDeadline: String = "",
    val companyId: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null
)