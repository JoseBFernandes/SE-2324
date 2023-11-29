package net.sf.freecol.client.gui;
import junit.framework.TestSuite;
import net.sf.freecol.client.FreeColClient;
import net.sf.freecol.client.gui.action.AddGoldAction;
import net.sf.freecol.common.FreeColException;
import net.sf.freecol.common.model.Game;
import net.sf.freecol.common.model.Player;
import net.sf.freecol.util.test.FreeColTestCase;
import org.junit.Test;

import java.awt.*;
import java.io.File;

import static org.junit.Assert.*;
public class AddGoldActionTest extends FreeColTestCase {

    @Test
    public void AddGoldActionTest() {


        //Player p = game.getClientPlayer();

        assertTrue(getServerPlayer(getStandardGame(), "19").checkGold(0));
        /*
        System.out.println(getTestMap(true));
        System.out.println(getTestMap(false));
        System.out.println(game.getFreeColGameObject("19"));
        System.out.println(game.getClientPlayer().checkGold(0));
        System.out.println(getServerPlayer(game, "19").revealMap());

        System.out.println(p);
*/
        //AddGoldAction addGoldAction = new AddGoldAction(p);
    }


}
