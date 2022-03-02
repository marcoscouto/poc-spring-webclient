package com.marcoscouto.webclient.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Joke {

    private String joke;

    @JsonProperty("joke")
    public String getJoke() {
        return joke;
    }

    @JsonProperty("value")
    public void setJoke(String joke) {
        this.joke = joke;
    }

}
