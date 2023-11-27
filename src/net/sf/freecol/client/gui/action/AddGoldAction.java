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
import java.util.Set;

import net.sf.freecol.client.FreeColClient;
import net.sf.freecol.client.gui.GUI;
import net.sf.freecol.common.debug.FreeColDebugger;
import net.sf.freecol.common.model.*;
import net.sf.freecol.common.option.GameOptions;
import net.sf.freecol.server.FreeColServer;
import net.sf.freecol.server.control.InGameController;
import net.sf.freecol.common.debug.DebugUtils;
import net.sf.freecol.server.model.ServerPlayer;


/**
 * An action for testing the view on the active unit.
 */
public class AddGoldAction extends UnitAction {

    public static final String id = "addGoldAction";


    /**
     * Creates a new {@code CenterAction}.
     *
     * @param freeColClient The {@code FreeColClient} for the game.
     */
    public AddGoldAction(FreeColClient freeColClient) {
        super(freeColClient, id);
    }


    // Interface ActionListener

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        final GUI gui = getGUI();
        final Unit active = gui.getActiveUnit();

        if (active != null) {

            final FreeColServer server = freeColClient.getFreeColServer();

            final Player player = freeColClient.getMyPlayer();
            final Game sGame = server.getGame();

            final Player sPlayer = sGame.getFreeColGameObject(player.getId(),
                    Player.class);
            player.modifyGold(1000);
            sPlayer.modifyGold(1000);

        }


        }

}
