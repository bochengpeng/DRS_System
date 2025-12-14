package org.example.drs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "machine")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Machine
{
    @Id
    private String id;
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL)
    private List<ReturnTransaction> transactions = new ArrayList<>();

    public void activate() {
        this.status = Status.ACTIVE;
    }

    public void deactivate() {
        this.status = Status.INACTIVE;
    }

    public void reportError() {
        this.status = Status.ERROR;
    }

    public boolean isActive() {
        return this.status == Status.ACTIVE;
    }

    public boolean isInError() {
        return this.status == Status.ERROR;
    }

    public void updateLocation(String newLocation)
    {
        this.location = newLocation;
    }

    public Status isOperational()
    {
        return this.status;
    }
}
