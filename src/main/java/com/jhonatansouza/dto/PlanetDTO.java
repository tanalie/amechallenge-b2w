package com.jhonatansouza.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PlanetDTO implements Serializable  {

    private String hashId;
    @Valid
    @NotNull
    private String name;
    @Valid
    @NotNull
    private String climate;
    @Valid
    @NotNull
    private String terrain;
    private Integer moviesAmount;


    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public Integer getMoviesAmount() {
        return moviesAmount;
    }

    public void setMoviesAmount(Integer moviesAmount) {
        this.moviesAmount = moviesAmount;
    }
}
