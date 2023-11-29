package net.sf.freecol.client.gui;

import net.sf.freecol.common.model.Game;
import net.sf.freecol.server.ServerTestHelper;
import net.sf.freecol.server.model.ServerPlayer;
import net.sf.freecol.util.test.FreeColTestCase;


public class AddGoldActionTest extends FreeColTestCase {

    public void testAddGold(){

        Game game = ServerTestHelper.startServerGame(getTestMap());

        ServerPlayer player = getServerPlayer(game, "model.nation.dutch");

        assertTrue(player.checkGold(0));

        simulateControlKeyPress(player);  //simulaçao de um evento de clique com Crtl-B por exemplo

        assertTrue(player.checkGold(1000));
    }

    private void simulateControlKeyPress(ServerPlayer player){
        player.modifyGold(1000);
    }


}
