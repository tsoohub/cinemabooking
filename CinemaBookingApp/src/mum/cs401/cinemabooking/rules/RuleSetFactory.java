package mum.cs401.cinemabooking.rules;

import javafx.stage.Stage;
import mum.cs401.cinemabooking.window.MovieWindow;
import mum.cs401.cinemabooking.window.TicketWindow;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public final class RuleSetFactory {

    private final static InputRules movieRuleSet = new MovieRuleSet();
    private final static InputRules ticketRuleSet = new TicketRuleSet();

    private RuleSetFactory() {
    }

    public static InputRules getRuleSet(Stage stage) {
        if (stage instanceof MovieWindow) {
            return movieRuleSet;
        } else if (stage instanceof TicketWindow) {
            return ticketRuleSet;
        } else {
            throw new IllegalArgumentException("RuleSet is not found.");
        }
    }

}
