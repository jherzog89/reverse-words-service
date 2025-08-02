package com.herzog.words.reverse_words.model;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ManipulatedString {

    @Id
    @GeneratedValue
    Long id;
    String apiUsed;
    String microserviceId;
    String originalString;
    String manipulatedString;
    Date timeCompleted;

    public ManipulatedString(Long id, String apiUsed, String microserviceId, String originalString, String manipulatedString,
        Date timeCompleted) {
        this.id = id;
        this.apiUsed = apiUsed;
        this.microserviceId = microserviceId;
        this.originalString = originalString;
        this.manipulatedString = manipulatedString;
        this.timeCompleted = timeCompleted;
    }

    public ManipulatedString(){};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiUsed() {
        return apiUsed;
    }
    public void setApiUsed(String apiUsed) {
        this.apiUsed = apiUsed;
    }
    public String getMicroserviceId() {
        return microserviceId;
    }
    public void setMicroserviceId(String microserviceId) {
        this.microserviceId = microserviceId;
    }
    public String getOriginalString() {
        return originalString;
    }
    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }
    public String getManipulatedString() {
        return manipulatedString;
    }
    public void setManipulatedString(String manipulatedString) {
        this.manipulatedString = manipulatedString;
    }
    public Date getTimeCompleted() {
        return timeCompleted;
    }
    public void setTimeCompleted(Date timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    @Override
    public String toString() {
        return "ManipulatedString [id=" + id + ", apiUsed=" + apiUsed + ", microserviceId=" + microserviceId
                + ", originalString=" + originalString + ", manipulatedString=" + manipulatedString + ", timeCompleted="
                + timeCompleted + "]";
    }

}
