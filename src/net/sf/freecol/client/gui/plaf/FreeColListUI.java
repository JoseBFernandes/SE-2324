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

package net.sf.freecol.client.gui.plaf;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicListUI;

import net.sf.freecol.client.gui.ImageLibrary;
import net.sf.freecol.common.util.ImageUtils;


/**
 * UI-class for lists, such as the drop down list of colonies in the
 * Colony panel.
 */
public class FreeColListUI extends BasicListUI {

    public static ComponentUI createUI(@SuppressWarnings("unused") JComponent c) {
        return new FreeColListUI();
    }


    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        ImageUtils.drawTiledImage(ImageLibrary.getListBackground(),
                                  g, c, null);
        LAFUtilities.setProperties(g, c);
        super.paint(g, c);
    }
}
