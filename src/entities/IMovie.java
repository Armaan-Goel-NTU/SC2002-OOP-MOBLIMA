package entities;

import java.util.List;

import enums.MovieAgeRating;
import enums.MovieStatus;
import enums.MovieType;

public interface IMovie {
	public int getId();

	public String getTitle();

	public String getSynopsis();

	public String getDirector();

	public MovieStatus getStatus();

	public MovieAgeRating getAgeRating();

	public MovieType getType();

	public List<String> getCast();

	public FeedbackHelper getFeedback();
}
