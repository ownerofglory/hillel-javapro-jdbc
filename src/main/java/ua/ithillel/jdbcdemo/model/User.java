package ua.ithillel.jdbcdemo.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data // getter/ setter, equals hash code, to String
@ToString(exclude = {"birthDate", "bookings"})
@EqualsAndHashCode(exclude = {"id", "bookings"})
@Builder
public class User {
    private Integer id; // default null
    private String name;
    private String email;
    private Date birthDate;

    private List<HotelBooking> bookings = new ArrayList<>();
}
