package com.example.myapplication;

//this is the object class for the User object

public class User
{
    int id;
    String username, imageurl;

    /**
     *
     * @param id
     * @param username
     * @param imageurl
     */
    public User(int id, String username, String imageurl) {
        this.id = id;
        this.username = username;
        this.imageurl = imageurl;
    }

    /**
     *
     * @param id
     * @param username
     */
    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     *
     * @param username
     */
    public User(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getImageurl() {
        return imageurl;
    }

    /**
     *
     * @param imageurl
     */
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
