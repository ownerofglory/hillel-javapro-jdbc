package ua.ithillel.jdbcdemo.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class HotelBooking {
    private Integer id;
    private Date checkInDate;
    private Date checkOutDate;

    private User user;
}
