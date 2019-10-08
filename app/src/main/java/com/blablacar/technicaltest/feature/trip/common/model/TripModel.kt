package com.blablacar.technicaltest.feature.trip.common.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * This files contains all model needed to fetch trips.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class SearchTrip(@JsonProperty("trips") val trips: List<Trip>)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Trip(
    @JsonProperty("user") val user: User,
    @JsonProperty("departure_place") val departure: Departure,
    @JsonProperty("arrival_place") val arrival: Arrival,
    @JsonProperty("price_with_commission") val price: Price
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
    @JsonProperty("display_name") val name: String,
    @JsonProperty("picture") val pictureUrl: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Departure(
    @JsonProperty("city_name") val city: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Arrival(
    @JsonProperty("city_name") val city: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Price(@JsonProperty("string_value") val price: String)