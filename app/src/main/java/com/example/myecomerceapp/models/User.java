package com.example.myecomerceapp.models;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    String email;
    String username;
    String password;
    Map<Product,Integer> products;

}
