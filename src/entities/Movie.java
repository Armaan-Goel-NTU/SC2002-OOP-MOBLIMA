package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import enums.MovieAgeRating;
import enums.MovieStatus;
import enums.MovieType;

public class Movie implements Serializable, IMovie {
    private static final long serialVersionUID = 1L;
    private int id = -1;
    private String title = "N/A";
    private String synopsis = "N/A";
    private String director = "N/A";
    private MovieStatus status = MovieStatus.COMING_SOON;
    private MovieAgeRating ageRating = MovieAgeRating.G;
    private MovieType type = MovieType.NORMAL;
    private List<String> cast = new ArrayList<>();
    private final FeedbackHelper feedback;

    public Movie(int id) {
        this.id = id;
        feedback = new FeedbackHelper();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public MovieStatus getStatus() {
        return status;
    }

    public void setStatus(MovieStatus status) {
        this.status = status;
    }

    public MovieAgeRating getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(MovieAgeRating ageRating) {
        this.ageRating = ageRating;
    }

    public MovieType getType() {
        return type;
    }

    public void setType(MovieType type) {
        this.type = type;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public FeedbackHelper getFeedback() {
        return feedback;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Movie) {
            return ((Movie) obj).getId() == id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
