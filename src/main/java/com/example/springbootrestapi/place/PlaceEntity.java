package com.example.springbootrestapi.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.sql.Timestamp;

@Entity
@Table(name = "place", schema = "location")
public class PlaceEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "place_id")
    @NotNull
    @JsonProperty("placeId")
    private int placeId;
    @Basic
    @Column(name = "name")
    @NotBlank
    @JsonProperty("name")
    private String name;
    @Basic
    @Column(name = "category_id")
    @NotNull
    @JsonProperty("category_id")
    private int categoryId;
    @Basic
    @Column(name = "user_id")
    @NotBlank
    @JsonProperty("user_id")
    private String userId;
    @Basic
    @Column(name = "status")
    private Object status;
    @Basic
    @Column(name = "last_modified",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
    private Timestamp lastModified;
    @Basic
    @Column(name = "description")
    @NotBlank
    @JsonProperty("description")
    private String description;
    @Basic
    @Column(name = "latitude")
    @NotNull
    @JsonProperty("latitude")
    private double latitude;
    @Basic
    @Column(name = "longitude")
    @NotNull
    @JsonProperty("longitude")
    private double longitude;
    @Basic
    @Column(name = "coordinates")
    private Object coordinates;
    @Basic
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Timestamp createdAt;

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = Integer.parseInt(String.valueOf(categoryId));
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Object getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Object coordinates) {
        this.coordinates = coordinates;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceEntity that = (PlaceEntity) o;

        if (placeId != that.placeId) return false;
        if (categoryId != that.categoryId) return false;
        if (Double.compare(latitude, that.latitude) != 0) return false;
        if (Double.compare(longitude, that.longitude) != 0) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (lastModified != null ? !lastModified.equals(that.lastModified) : that.lastModified != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (coordinates != null ? !coordinates.equals(that.coordinates) : that.coordinates != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = placeId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + categoryId;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (coordinates != null ? coordinates.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
