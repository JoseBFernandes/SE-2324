package net.sf.freecol.server.model;

import net.sf.freecol.common.model.Game;
import net.sf.freecol.common.model.Map;
import net.sf.freecol.common.model.Tile;
import net.sf.freecol.server.ServerTestHelper;
import net.sf.freecol.util.test.FreeColTestCase;

import static net.sf.freecol.util.test.FreeColTestCase.getTestMap;


public class AllMapRevealedTest extends FreeColTestCase {


    public void testMapRevealComplete() {

        Game game = ServerTestHelper.startServerGame(getTestMap());

        Map map = game.getMap();

        ServerPlayer player = getServerPlayer(game, "model.nation.dutch");

        player.revealMap();

        Tile tile1 = map.getTile(6, 8);
        Tile tile2 = map.getTile(8, 6);

        assertTrue("Setup error, tile1 should be explored by player", player.hasExplored(tile1));
        assertTrue("Setup error, tile2 should be explored by player", player.hasExplored(tile2));


    }

}
