/**
 * Copyright (C) 2002-2022   The FreeCol Team
 * <p>
 * This file is part of FreeCol.
 * <p>
 * FreeCol is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * FreeCol is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with FreeCol.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.freecol.client.gui;

import java.awt.*;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.logging.Logger;

import net.sf.freecol.client.ClientOptions;
import net.sf.freecol.client.FreeColClient;
import net.sf.freecol.client.control.FreeColClientHolder;
import net.sf.freecol.common.model.Map;


/**
 * Listens to the mouse being moved at the level of the Canvas.
 */
public final class CanvasMouseMotionListener extends FreeColClientHolder implements MouseMotionListener {

    private static final Logger logger = Logger.getLogger(CanvasMouseMotionListener.class.getName());

    private final Scrolling scrolling;

    private FreeColClient freeColClient;
    int tileSize;


    /**
     * Creates a new listener for mouse movement.
     *
     * @param freeColClient The {@code FreeColClient} for the game.
     */
    public CanvasMouseMotionListener(FreeColClient freeColClient, Scrolling scrolling) {
        super(freeColClient);
        freeColClient = freeColClient;
        this.scrolling = scrolling;
        tileSize = 4 * freeColClient.getClientOptions()
                .getInteger(ClientOptions.DEFAULT_ZOOM_LEVEL);
    }


    // Interface MouseMotionListener

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseMoved(MouseEvent me) {
        //este metodo controla apenas onde anda o rato

        scrolling.performAutoScrollIfActive(me);
        getGUI().updateGoto(me.getX(), me.getY(), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseDragged(MouseEvent me) {
        // getButton does not work here, TODO: find out why
        // arrastar o boneco do jogo
        if ((me.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != MouseEvent.BUTTON1_DOWN_MASK) {
            return;
        }

        scrolling.performDragScrollIfActive(me);

        getGUI().updateGoto(me.getX(), me.getY(), true);

        int x = me.getX();
        int y = me.getY();
        setNewFocusPoint(x, y);
    }

    public int setNewFocusPoint(int x,int y) {
        final Point focusPoint = getGUI().getFocusMapPoint();
        final int novoX = focusPoint.x + ((x - getGUI().getMapViewDimension().width / 2) / (tileSize * 2));
        final int novoY = focusPoint.y + ((y - getGUI().getMapViewDimension().height / 2) / tileSize);
        getGUI().setFocusMapPoint(new Point(novoX, novoY));
        return novoX;
    }

}
