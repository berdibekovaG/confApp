package kz.kolesateam.confapp.events.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class SpeakerApiData (
        @JsonProperty(value = "fullName")
    val fullName: String?
)