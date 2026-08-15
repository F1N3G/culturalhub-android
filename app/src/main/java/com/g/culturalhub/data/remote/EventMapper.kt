package com.g.culturalhub.data.remote

import com.g.culturalhub.model.Event
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun EventDto.toEvent(): Event = Event(
    id = id,
    title = title,
    description = description ?: "",
    category = category?.name ?: "",
    venue = venue?.name ?: "",
    city = "",
    date = formatEventDate(startDate),
    priceFrom = if (isFree) 0 else price?.toDoubleOrNull()?.toInt() ?: 0,
    imageUrl = normalizeImageUrl(image)
)

// API-ul poate întoarce imaginea cu orice host (culturalhub.test, 127.0.0.1, localhost).
// Din emulator, singurul host valid e 10.0.2.2:8000 -> rescriem host-ul, păstrăm calea /storage/...
private fun normalizeImageUrl(url: String?): String {
    if (url.isNullOrBlank()) return ""
    val idx = url.indexOf("/storage")
    return if (idx >= 0) "http://10.0.2.2:8000" + url.substring(idx) else url
}

private fun formatEventDate(iso: String?): String {
    if (iso.isNullOrBlank()) return ""
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        parser.timeZone = TimeZone.getTimeZone("UTC")
        val parsed = parser.parse(iso.take(19))
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        parsed?.let { formatter.format(it) } ?: iso.take(10)
    } catch (e: Exception) {
        iso.take(10)
    }
}