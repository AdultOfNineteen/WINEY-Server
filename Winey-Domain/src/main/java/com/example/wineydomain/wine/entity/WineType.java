package com.example.wineydomain.wine.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WineType {
    RED("RED","레드"),
    WHITE("WHITE","화이트"),
    SPARKLING("SPARKLING","스파클링"),
    ROSE("ROSE","로제"),
    FORTIFIED("FORTIFIED","포르투"),
    OTHER("OTHER","기타");
    private final String value;
    private final String name;
}
