package mum.cs401.cinemabooking.rules;

import java.util.regex.Pattern;
import javafx.stage.Stage;
import mum.cs401.cinemabooking.window.TicketWindow;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
final class TicketRuleSet implements InputRules {

    private static final String REGEX_NUM = "^\\d+$";
    private static final String REGEX_ALP = "^[A-Z]+[a-z]+\\s+$";

    TicketRuleSet() {
    }

    @Override
    public void applyRules(Stage stage) throws RuleException {
        TicketWindow ticketWindow = (TicketWindow) stage;
        validateSeat(ticketWindow.getAdultSeat());
        validateSeat(ticketWindow.getChildSeat());
        validateName(ticketWindow.getFirstname());
    }

    private void validateSeat(String seat) throws RuleException {
        if (seat == null || seat.isEmpty()) {
            throw new RuleException("'Seat' cannot be null or empty.");
        }
        Pattern pattern = Pattern.compile(REGEX_NUM);
        if (!pattern.matcher(seat).matches()) {
            throw new RuleException("'Seat' must be numeric.");
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
