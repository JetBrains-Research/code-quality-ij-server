package org.jetbrains.research.ij.headless.server

import com.intellij.lang.Language
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class CodeServerConfig(
    val port: Int,
    @Serializable(LanguageSerializer::class)
    val language: Language,
    val templatesPath: String,
) {
    private object LanguageSerializer : KSerializer<Language> {
        override val descriptor = PrimitiveSerialDescriptor("Language", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder) =
            Language.findLanguageByID(decoder.decodeString()) ?: throw SerializationException("Unknown language.")

        override fun serialize(encoder: Encoder, value: Language) {
            encoder.encodeString(value.id)
        }
    }
}
