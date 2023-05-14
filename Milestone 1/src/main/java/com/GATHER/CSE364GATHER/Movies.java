package com.GATHER.CSE364GATHER;

public class Movies {
    private Long MovieId;
    private String Title;
    private String Genre;

    public Movies(Long MovieId, String Title, String Genre){
        this.MovieId = MovieId;
        this.Title = Title;
        this.Genre = Genre;
    }
    public Long getMovieId(){
        return MovieId;
    }
    public String getTitle(){
        return Title;
    }
    public String getGenre(){
        return Genre;
    }
}
