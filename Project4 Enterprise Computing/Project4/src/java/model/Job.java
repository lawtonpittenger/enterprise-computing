/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/* Name:
Course: CNT 4714 – Spring 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: April 23, 2023
*/
package model;

public class Job {
    private String jnum,jname,city;
    private int numworkers;

    public String getJnum() {
        return jnum;
    }

    public void setJnum(String jnum) {
        this.jnum = jnum;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumworkers() {
        return numworkers;
    }

    public void setNumworkers(int numworkers) {
        this.numworkers = numworkers;
    }

    
}