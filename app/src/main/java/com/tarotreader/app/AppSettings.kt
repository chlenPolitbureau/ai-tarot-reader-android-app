package com.tarotreader.app

import com.tarotreader.app.model.Currency
import com.tarotreader.app.model.CurrencyType
import com.tarotreader.app.model.Prediction
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Serializable
data class AppSettings (
    @Serializable(with = MyPersistentListSerializer::class)
    val predictions: PersistentList<Prediction> = persistentListOf(),
    val language: Language = Language.ENGLISH,
    val userName: String = "",
    val gender: String = "",
    val dateOfBirth: Long? = null,
    val lastSessionDateTimeMilliSec: Long = 0L,
    @Serializable(with = MyPersistentListSerializer::class)
    val currencies: PersistentList<Currency> = persistentListOf(
        Currency(
            CurrencyType.MANA,
            30
        )
    )
)

enum class Language {
    ENGLISH, RUSSIAN, SPANISH
}

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = PersistentList::class)
class MyPersistentListSerializer(
    private val serializer: KSerializer<String>,
) : KSerializer<PersistentList<String>> {

    private class PersistentListDescriptor :
        SerialDescriptor by serialDescriptor<List<String>>() {
        @ExperimentalSerializationApi
        override val serialName: String = "kotlinx.serialization.immutable.persistentList"
    }

    override val descriptor: SerialDescriptor = PersistentListDescriptor()

    override fun serialize(encoder: Encoder, value: PersistentList<String>) {
        return ListSerializer(serializer).serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): PersistentList<String> {
        return ListSerializer(serializer).deserialize(decoder).toPersistentList()
    }
}


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = PersistentMap::class)
class MyPersistentMapSerializer(
    private val keySerializer: KSerializer<String>,
    private val valueSerializer: KSerializer<Prediction>
) : KSerializer<PersistentMap<String, Prediction>> {

    private class PersistentMapDescriptor :
        SerialDescriptor by serialDescriptor<Map<String, Prediction>>() {
        @ExperimentalSerializationApi
        override val serialName: String = "kotlinx.serialization.immutable.persistentMap"
    }

    override val descriptor: SerialDescriptor = PersistentMapDescriptor()

    override fun serialize(encoder: Encoder, value: PersistentMap<String, Prediction>) {
        return MapSerializer(keySerializer, valueSerializer).serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): PersistentMap<String, Prediction> {
        return MapSerializer(keySerializer, valueSerializer).deserialize(decoder).toPersistentMap()
    }

}

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDateTime::class)
class MyLocalDateTimeSerializer {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "LocalDateTime",
        PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val stringValue = value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        encoder.encodeString(stringValue)
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val stringValue = decoder.decodeString()
        return LocalDateTime.parse(stringValue, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

}