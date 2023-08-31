package com.example.wineydomain.wine.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WineType {
    RED("RED"),
    WHITE("WHITE"),
    SPARKLING("SPARKLING"),
    ROSE("ROSE"),
    FORTIFIED("FORTIFIED"),
    OTHER("OTHER");
    private final String value;
}
