package net.sf.freecol.client.gui;

import net.sf.freecol.client.ClientTestHelper;
import net.sf.freecol.server.FreeColServer;
import net.sf.freecol.server.ServerTestHelper;
import net.sf.freecol.util.test.FreeColTestCase;
import net.sf.freecol.client.FreeColClient;

import java.awt.*;

public class MapDragTest extends FreeColTestCase {

    private Scrolling scrolling;

    public void testDragMap() {


       /* FreeColClient freeColClient = null;

        try {
            freeColClient = ClientTestHelper
                    .startClient(ServerTestHelper.getServer(), spec());
        } finally {
            if (freeColClient != null) {
                ClientTestHelper.stopClient(freeColClient);
            }
        }

        Scrolling scrolling1 = new Scrolling(freeColClient, freeColClient.);

        CanvasMouseMotionListener cm = new CanvasMouseMotionListener(freeColClient, scrolling);
        final Point focusPoint = freeColClient.getGUI().getFocusMapPoint();

        int x = 2;
        int y = 2;


        cm.setNewFocusPoint(x, y);
        assertEquals(x, focusPoint.x);
        assertEquals(y, focusPoint.y);*/
    }
}
