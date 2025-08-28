package control;

import Exceptions.ObjectAlreadyExistsException;
import model.*;

public class GuiService {
    private final Court court;
    public GuiService(Court court) { this.court = court; }

    public void addLawyerOrThrow(Lawyer l) throws ObjectAlreadyExistsException {
        if (l == null || court.getRealLawyer(l.getId()) != null) {
            throw new ObjectAlreadyExistsException("Lawyer with ID " + (l != null ? l.getId() : "?"));
        }
        if (!court.addLawyer(l)) {
            throw new ObjectAlreadyExistsException("Lawyer with ID " + l.getId());
        }
    }
}
