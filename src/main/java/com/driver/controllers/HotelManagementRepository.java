package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Repository
public class HotelManagementRepository {

    HashMap<String,Hotel> hotelDb = new HashMap<>();

    HashMap<String,User> userdb = new HashMap<>();

    HashMap<Integer,List<Booking>> bookingdb = new HashMap<>();

    public String addHotel(Hotel hotel) {
        if(hotel.getHotelName() == null || hotel == null)
        {
            return "FAILURE";
        }

        if(hotelDb.containsKey(hotel.getHotelName())) return "FAILURE";
        hotelDb.put(hotel.getHotelName(),hotel);
        return "SUCCESS";
    }

    public void addUser(User user) {
        userdb.put(user.getName(), user);
    }

    public String getHotelWithMostFacilities() {
        int max = 0;

        for(Hotel h : hotelDb.values())
        {
            List<Facility> f = h.getFacilities();
            int curr = f.size();
            if(f.size() > max)
            {
                max = f.size();

            }
        }

        if(max == 0) return "";
        List<String> ans = new ArrayList<>();
        for(Hotel h : hotelDb.values())
        {
            List<Facility> f = h.getFacilities();
            int currsize = f.size();
            if(currsize == max)
            {
                ans.add(h.getHotelName());
            }
        }

        Collections.sort(ans);
        return ans.get(0);
    }

    public int bookARoom(Booking booking) {
        String h = booking.getHotelName();
        Hotel curr = hotelDb.get(h);

        if(curr.getAvailableRooms() < booking.getNoOfRooms()) return -1;

        int totalAmount = booking.getNoOfRooms()* curr.getPricePerNight();
        int adhar = booking.getBookingAadharCard();
        List<Booking> list = new ArrayList<>();
        if(bookingdb.containsKey(adhar))
        {
            list = bookingdb.get(adhar);
        }
        list.add(booking);
        bookingdb.put(adhar,list);
        return totalAmount;
    }

    public int getBookings(Integer aadharCard) {

        List<Booking> list = new ArrayList<>();
        if(bookingdb.containsKey(aadharCard))
        {
            list = bookingdb.get(aadharCard);
        }
        return list.size();
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel h = hotelDb.get(hotelName);
        List<Facility> f = h.getFacilities();

        for(Facility f1 : newFacilities)
        {
            if(!f.contains(f1))
            {
                f.add(f1);
            }
        }

        h.setFacilities(f);
        return h;
    }
}
