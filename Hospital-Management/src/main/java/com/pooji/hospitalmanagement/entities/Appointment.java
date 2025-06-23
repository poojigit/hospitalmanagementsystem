package com.pooji.hospitalmanagement.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String time;
    private String details;

    @ManyToOne
    private User patient;

    @ManyToOne
    private Doctor doctor;
}
