package com.jpmorgan.mds.example;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;

public class Event {
    private long id;
    private Date date;

    @JsonCreator
    public Event(@JsonProperty("id") long id, @JsonProperty("date") Date date) {
        this.setId(id);
        this.setDate(date);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id &&
                Objects.equals(date, event.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}