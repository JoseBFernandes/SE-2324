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
import net.sf.freecol.client.gui.GUI;
import net.sf.freecol.common.model.Game;
import net.sf.freecol.common.model.Player;
import net.sf.freecol.common.model.Tile;
import net.sf.freecol.common.model.Unit;
import net.sf.freecol.server.FreeColServer;
import net.sf.freecol.server.control.InGameController;


/**
 * An action for testing the view on the active unit.
 */
public class TesteAction extends UnitAction {

    public static final String id = "testeAction";


    /**
     * Creates a new {@code CenterAction}.
     *
     * @param freeColClient The {@code FreeColClient} for the game.
     */
    public TesteAction(FreeColClient freeColClient) {
        super(freeColClient, id);
    }


    // Interface ActionListener

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        //System.out.println("ola eu sou um teste");
        final GUI gui = getGUI();
        final Unit active = gui.getActiveUnit();
        if (active != null) {
            System.out.println("a ver se entro aqui");

            final FreeColServer server = freeColClient.getFreeColServer();
            final Player player = freeColClient.getMyPlayer();
            final Game sGame = server.getGame();

            final Player sPlayer = sGame.getFreeColGameObject(player.getId(),
                    Player.class);
            player.modifyGold(1000);
            sPlayer.modifyGold(1000);
        }
            //System.out.println("explorado tile "+game.getMap().getTile(500,100).isExplored());
            //System.out.println("tipo do tile "+game.getMap().getTile(500,100).getType());

    }
}
