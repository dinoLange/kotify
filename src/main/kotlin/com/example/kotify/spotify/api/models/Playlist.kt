package com.example.kotify.spotify.api.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Playlist (
    @SerializedName("href"     ) var href     : String?          = null,
    @SerializedName("limit"    ) var limit    : Int?             = null,
    @SerializedName("next"     ) var next     : String?          = null,
    @SerializedName("offset"   ) var offset   : Int?             = null,
    @SerializedName("previous" ) var previous : String?          = null,
    @SerializedName("total"    ) var total    : Int?             = null,
    @SerializedName("items"    ) var items    : ArrayList<Items> = arrayListOf()
)

@Serializable
data class Items (
    @SerializedName("collaborative" ) var collaborative : Boolean?          = null,
    @SerializedName("description"   ) var description   : String?           = null,
    @SerializedName("external_urls" ) var externalUrls  : ExternalUrls?     = ExternalUrls(),
    @SerializedName("href"          ) var href          : String?           = null,
    @SerializedName("id"            ) var id            : String?           = null,
    @SerializedName("images"        ) var images        : ArrayList<Images> = arrayListOf(),
    @SerializedName("name"          ) var name          : String?           = null,
    @SerializedName("owner"         ) var owner         : Owner?            = Owner(),
    @SerializedName("public"        ) var public        : Boolean?          = null,
    @SerializedName("snapshot_id"   ) var snapshotId    : String?           = null,
    @SerializedName("tracks"        ) var tracks        : Tracks?           = Tracks(),
    @SerializedName("type"          ) var type          : String?           = null,
    @SerializedName("uri"           ) var uri           : String?           = null
)

@Serializable
data class Images (
    @SerializedName("url"    ) var url    : String? = null,
    @SerializedName("height" ) var height : Int?    = null,
    @SerializedName("width"  ) var width  : Int?    = null
)

@Serializable
data class ExternalUrls (
    @SerializedName("spotify" ) var spotify : String? = null
)

@Serializable
data class Followers (
    @SerializedName("href"  ) var href  : String? = null,
    @SerializedName("total" ) var total : Int?    = null
)

@Serializable
data class Owner (
    @SerializedName("external_urls" ) var externalUrls : ExternalUrls? = ExternalUrls(),
    @SerializedName("followers"     ) var followers    : Followers?    = Followers(),
    @SerializedName("href"          ) var href         : String?       = null,
    @SerializedName("id"            ) var id           : String?       = null,
    @SerializedName("type"          ) var type         : String?       = null,
    @SerializedName("uri"           ) var uri          : String?       = null,
    @SerializedName("display_name"  ) var displayName  : String?       = null
)

@Serializable
data class Tracks (
    @SerializedName("href"  ) var href  : String? = null,
    @SerializedName("total" ) var total : Int?    = null
)