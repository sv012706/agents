package com.example.theatermanagement.venue;

public record VenueResponse(Long id, String name, String address, int capacity) {

    public static VenueResponse from(Venue venue) {
        return new VenueResponse(venue.getId(), venue.getName(), venue.getAddress(), venue.getCapacity());
    }
}
