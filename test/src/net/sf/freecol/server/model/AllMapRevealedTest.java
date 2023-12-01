package net.sf.freecol.server.model;

import net.sf.freecol.common.model.Game;
import net.sf.freecol.common.model.Map;
import net.sf.freecol.common.model.Tile;
import net.sf.freecol.server.ServerTestHelper;
import net.sf.freecol.util.test.FreeColTestCase;

import static net.sf.freecol.util.test.FreeColTestCase.getTestMap;


public class AllMapRevealedTest extends FreeColTestCase {

    int i=1;
    int j=1;
    public void testMapRevealComplete() {

        Game game = ServerTestHelper.startServerGame(getTestMap());

        Map map = game.getMap();

        ServerPlayer player = getServerPlayer(game, "model.nation.dutch");

        /*
         * mapa nao esta revelado ainda. tem que dar False
         */
        for(i=1;i<map.getWidth();i++){
            for (j=1;j<map.getHeight();j++){
                Tile tile = map.getTile(i,j);
                assertFalse("Setup error, tile should be explored by player", player.hasExplored(tile));
            }
        }

        player.revealMap();  //revelar mapa

        /*
         * mapa revelado tem que dar True
         */

        for(i=1;i<map.getWidth();i++){
            for (j=1;j<map.getHeight();j++){
                Tile tile = map.getTile(i,j);
                assertTrue("Setup error, tile should be explored by player", player.hasExplored(tile));
            }
        }

    }

}
