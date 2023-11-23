/**
 *  Copyright (C) 2002-2022   The FreeCol Team
 *
 *  This file is part of FreeCol.
 *
 *  FreeCol is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  FreeCol is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with FreeCol.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.freecol.client.gui.action;

import java.awt.event.ActionEvent;

import net.sf.freecol.client.FreeColClient;


/**
 * An action to allow the user to add more point during the game.
 */
public class BonusPointAction extends FreeColAction {

    public static final String id = "bonusPointAction";


    /**
     * Creates a new {@code BonusPointAction}.
     *
     * @param freeColClient The main controller object for the client.
     */
    public BonusPointAction(FreeColClient freeColClient) {
        super(freeColClient, id);
    }


    // Override FreeColAction

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean shouldBeEnabled() {
        if (freeColClient.isMapEditor()) return true;

        // In game
        if (!freeColClient.canSaveCurrentGame()) return false;
        return !getGUI().isPanelShowing();
    }


    // Interface ActionListener

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (freeColClient.isMapEditor()) {
            freeColClient.getMapEditorController().saveMapEditorGame();
            freeColClient.quit();
        } else {
            if (!igc().saveAndQuit()) return;
        }
    }
}

