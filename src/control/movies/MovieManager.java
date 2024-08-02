package control.movies;

import java.util.List;

import control.MainApp;
import control.SavedManager;
import entities.IMovie;
import entities.Movie;

public class MovieManager extends SavedManager {
    private int selectedMovieIndex;

    private MovieAccessors movieAccessors;
    private MovieDisplay display;
    private MovieFeedback feedback;
    private MovieEditor editor;

    public MovieManager(List<Movie> movies) {
        this.movieAccessors = new MovieAccessors(this);
        this.display = new MovieDisplay(this);
        this.feedback = new MovieFeedback(this);
        this.editor = new MovieEditor(this, movies);
    }

    public List<IMovie> getAllMovies() {
        return editor.getMovies();
    }

    public void recordStaffMovieSelection(int selectedMovieIndex) {
        this.selectedMovieIndex = selectedMovieIndex;
    }

    public void recordCurrentMovieSelection(int selectedCurrentMovieIndex) {
        this.selectedMovieIndex = editor.getMovies().indexOf(getCurrentMovies().get(selectedCurrentMovieIndex));
    }

    public int getSelectedIndex() {
        return this.selectedMovieIndex;
    }

    public void addMovie() {
        editor.addMovie();
    }

    public void changeTitle() {
        editor.changeTitle();
    }

    public void changeSynopsis() {
        editor.changeSynopsis();
    }

    public void changeDirector() {
        editor.changeDirector();
    }

    public void changeStatus() {
        editor.changeStatus();
    }

    public void changeAgeRating() {
        editor.changeAgeRating();
    }

    public void changeType() {
        editor.changeType();
    }

    public void addCast() {
        editor.addCast();
    }

    public void deleteCast() {
        editor.deleteCast();
    }

    public void displayMovieInfo(boolean forceShowType) {
        display.displayMovieInfo(forceShowType, selectedMovieIndex);
    }

    public void displayFeedback() {
        display.displayFeedback(selectedMovieIndex);
    }

    public boolean canViewShowtimes() {
        return feedback.canViewShowtimes();
    }

    public boolean canReview() {
        return feedback.canReview();
    }

    public void storeAllGuestReviews(String userEmail) {
        feedback.storeAllGuestReviews(userEmail);
    }

    public void resetGuestReviews() {
        feedback.resetGuestReviews();
    }

    public void addReview() {
        feedback.addReview();
    }

    public void incrementSales(IMovie movie, int amount) {
        feedback.incrementSales(movie, amount);
    }

    public String[] getAllMovieTitles() {
        return movieAccessors.getAllMovieTitles();
    }

    public List<IMovie> getCurrentMovies() {
        return movieAccessors.getCurrentMovies();
    }

    public String[] getCurrentMovieTitles() {
        return movieAccessors.getCurrentMovieTitles();
    }

    public List<IMovie> getCurrentlyPurchasable() {
        return movieAccessors.getCurrentlyPurchasable();
    }

    public int movieIdFromCurrentIndex(int currentIndex) {
        return movieAccessors.movieIdFromCurrentIndex(currentIndex);
    }

    public int currentMovieIndexFromId(int id) {
        return movieAccessors.currentMovieIndexFromId(id);
    }

    public IMovie movieFromId(int id) {
        return movieAccessors.movieFromId(id);
    }

    @Override
    public String getSaveFilePath() {
        return MainApp.MOVIES_FILE;
    }

    @Override
    public Object getObjectToSave() {
        return getAllMovies();
    }

}
