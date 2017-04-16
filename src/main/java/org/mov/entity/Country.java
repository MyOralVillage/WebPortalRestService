package org.mov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Country extends BaseEntity {
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "POPULATION")
    private Double population;

    @Column(name = "SUB_PER_HUNDRED")
    private Double subscriptionsPerHundred;

    @Column(name = "ADULTS")
    private Integer Adults;

    @Column(name = "PER_ILL_WOMEN")
    private Double percentIlliterateWomen;

    @Column(name = "PER_ILL_YOUTH")
    private Double percentIlliterateYouth;

    @Column(name = "FINANCIAL_INCLUSION")
    private Double fi;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPopulation() {
        return population;
    }

    public void setPopulation(Double population) {
        this.population = population;
    }

    public Double getSubscriptionsPerHundred() {
        return subscriptionsPerHundred;
    }

    public void setSubscriptionsPerHundred(Double subscriptionsPerHundred) {
        this.subscriptionsPerHundred = subscriptionsPerHundred;
    }

    public Integer getAdults() {
        return Adults;
    }

    public void setAdults(Integer adults) {
        Adults = adults;
    }

    public Double getPercentIlliterateWomen() {
        return percentIlliterateWomen;
    }

    public void setPercentIlliterateWomen(Double percentIlliterateWomen) {
        this.percentIlliterateWomen = percentIlliterateWomen;
    }

    public Double getPercentIlliterateYouth() {
        return percentIlliterateYouth;
    }

    public void setPercentIlliterateYouth(Double percentIlliterateYouth) {
        this.percentIlliterateYouth = percentIlliterateYouth;
    }

    public Double getFi() {
        return fi;
    }

    public void setFi(Double fi) {
        this.fi = fi;
    }
}
