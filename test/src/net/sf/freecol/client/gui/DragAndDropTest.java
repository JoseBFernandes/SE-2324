package net.sf.freecol.client.gui;

import java.awt.event.ActionEvent;
import net.sf.freecol.client.gui.panel.FreeColButton;
import net.sf.freecol.common.i18n.Messages;
import net.sf.freecol.common.model.Game;
import net.sf.freecol.common.model.Map;
import net.sf.freecol.util.test.FreeColTestCase;
import javax.swing.*;

public class DragAndDropTest extends FreeColTestCase {

    public void testEventOnMap() {
        int width = 40;
        int heigth = 30;

        Game game = getStandardGame();
        MapBuilder builder = new MapBuilder(game);
        Map map = builder.setDimensions(width, heigth).build();


        /*
        final JButton randomMap = new FreeColButton(Messages.message("mapGeneratorOptions.landGenerator.name"));
        randomMap.addActionListener((ActionEvent ae) -> {
            // TODO: fazer algo...
            assertTrue(true);
        });
        */
    }
}
