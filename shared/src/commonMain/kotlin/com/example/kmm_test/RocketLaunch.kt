package com.example.kmm_test

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RocketLaunch : RealmObject {
    @SerialName("flight_number")
    var flightNumber: Int? = null

    @SerialName("name")
    @PrimaryKey
    var missionName: String? = null

    @SerialName("date_utc")
    var launchDateUTC: String? = null

    @SerialName("details")
    var details: String? = null

    @SerialName("success")
    var launchSuccess: Boolean? = null

    @SerialName("links")
    var links: Links? = null
}

@Serializable
class Links : RealmObject {
    @SerialName("patch")
    var patch: Patch? = null

    @SerialName("article")
    var article: String? = null
}

@Serializable
class Patch : RealmObject {
    @SerialName("small")
    var small: String? = null

    @SerialName("large")
    var large: String? = null
}