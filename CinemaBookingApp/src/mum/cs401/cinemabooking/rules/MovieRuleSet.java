package mum.cs401.cinemabooking.rules;

import java.util.regex.Pattern;
import javafx.stage.Stage;
import mum.cs401.cinemabooking.window.MovieWindow;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
final class MovieRuleSet implements InputRules {

    private static final String REGEX_NUM = "^\\d+$";
    private static final String REGEX_ALP = "^[A-Z]+[a-z]+\\s+$";
    private static final String REGEX_ACTOR = "^[A-Z]+[a-z]+\\s+,+$";

    MovieRuleSet() {
    }

    @Override
    public void applyRules(Stage stage) throws RuleException {
        MovieWindow movieWindow = (MovieWindow) stage;
        validateName(movieWindow.getName());
        validateActor(movieWindow.getActors());
        validatePrice(movieWindow.getAdultPrice());
        validatePrice(movieWindow.getChildPrice());
        validateDuration(movieWindow.getDuration());
        validateName(movieWindow.getDirector());
    }

    private void validateDuration(String duration) throws RuleException {
        if (duration == null || duration.isEmpty()) {
            throw new RuleException("'Duration' cannot be null or empty.");
        }
        Pattern pattern = Pattern.compile(REGEX_NUM);
        if (!pattern.matcher(duration).matches()) {
            throw new RuleException("'Duration' must be alphabet.");
        }
    }

    private void validateActor(String actor) throws RuleException {
        if (actor == null || actor.isEmpty()) {
            throw new RuleException("'Actor' cannot be null or empty.");
        }
        Pattern pattern = Pattern.compile(REGEX_ACTOR);
        if (!pattern.matcher(actor).matches()) {
            throw new RuleException("'Actor' must be alphabet.");
        }
    }

    private void validatePrice(String price) throws RuleException {
        if (price == null || price.isEmpty()) {
            throw new RuleException("'Price' cannot be null or empty.");
        }
        Pattern pattern = Pattern.compile(REGEX_NUM);
        if (!pattern.matcher(price).matches()) {
            throw new RuleException("'Price' must be numeric.");
        }
    }

    private void validateName(String name) throws RuleException {
        if (name == null || name.isEmpty()) {
            throw new RuleException("'Name' cannot be null or empty.");
        }
        Pattern pattern = Pattern.compile(REGEX_ALP);
        if (!pattern.matcher(name).matches()) {
            throw new RuleException("'Name' must be alphabet.");
        }
    }

}
