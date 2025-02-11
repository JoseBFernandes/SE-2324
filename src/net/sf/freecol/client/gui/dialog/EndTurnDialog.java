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

package net.sf.freecol.client.gui.dialog;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.PanelUI;

import net.miginfocom.swing.MigLayout;
import net.sf.freecol.client.FreeColClient;
import net.sf.freecol.client.gui.panel.MigPanel;
import net.sf.freecol.client.gui.panel.Utility;
import net.sf.freecol.client.gui.plaf.FreeColSelectedPanelUI;
import net.sf.freecol.common.i18n.Messages;
import net.sf.freecol.common.model.Player;
import net.sf.freecol.common.model.StringTemplate;
import net.sf.freecol.common.model.Unit;


/**
 * Centers the map on a known settlement or colony.  Pressing ENTER
 * opens a panel if appropriate.
 */
public final class EndTurnDialog extends FreeColConfirmDialog {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(EndTurnDialog.class.getName());

    /**
     * We need to wrap the Unit class in order to make the JList
     * support keystroke navigation.  JList.getNextMatch uses the
     * toString() method, but the toString() method of FreeCol objects
     * provides debugging information rather than a searchable name.
     */
    private static class UnitWrapper {

        public final Unit unit;
        public final String name;
        public final String location;


        public UnitWrapper(Unit unit) {
            this.unit = unit;
            this.name = unit.getDescription(Unit.UnitLabelType.NATIONAL);
            this.location = Messages.message(unit.getLocation()
                .getLocationLabelFor(unit.getOwner()));
        }


        // Override Object

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return name;
        }
    }

    private class UnitCellRenderer implements ListCellRenderer<UnitWrapper> {

        public UnitCellRenderer() {
            
        }


        /**
         * {@inheritDoc}
         */
        @Override
        public Component getListCellRendererComponent(JList<? extends UnitWrapper> list,
                                                      UnitWrapper value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {
            final JLabel imageLabel = new JLabel();
            imageLabel.setIcon(new ImageIcon(getImageLibrary().getSmallerUnitImage(value.unit)));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            
            final JLabel nameLabel = new JLabel();
            nameLabel.setText(value.name);
            
            final JLabel locationLabel = new JLabel();
            locationLabel.setFont(locationLabel.getFont().deriveFont(Font.ITALIC));
            locationLabel.setText(value.location);
            
            final JPanel panel;
            if (isSelected) {
                panel = new MigPanel(new MigLayout("", "[fill]"));
                panel.setOpaque(false);
                panel.setUI((PanelUI)FreeColSelectedPanelUI.createUI(panel));
            } else {
                panel = new MigPanel(new MigLayout("", "[fill]"));
                panel.setOpaque(false);
            }
            
            final Dimension largestIconSize = largestIconSize(list);
            panel.add(imageLabel, "center, width " + largestIconSize.width + "px!, height " + largestIconSize.height + "px!");
            panel.add(nameLabel, "split 2, flowy, grow");
            panel.add(locationLabel, "grow");
            
            return panel;
        }
        
        private Dimension largestIconSize(JList<? extends UnitWrapper> list) {
            final ListModel<? extends UnitWrapper> model = list.getModel();
            int largestWidth = 0;
            int largestHeight = 0;
            for (int i=0; i<model.getSize(); i++) {
                final UnitWrapper value = model.getElementAt(i);
                final BufferedImage image = getImageLibrary().getSmallerUnitImage(value.unit);
                if (image.getWidth() > largestWidth) {
                    largestWidth = image.getWidth();
                }
                if (image.getHeight() > largestHeight) {
                    largestHeight = image.getHeight(); 
                }
            }
            return new Dimension(largestWidth, largestHeight);
        }
    }


    /** The list of units to display. */
    private final JList<UnitWrapper> unitList;


    /**
     * The constructor to use.
     * 
     * @param freeColClient The freecol client.
     * @param frame The owner frame.
     * @param units The unit list.
     */
    public EndTurnDialog(FreeColClient freeColClient, JFrame frame, List<Unit> units) {
        super(freeColClient, frame);

        final Player player = getMyPlayer();

        JLabel header = Utility.localizedHeader(Messages.nameKey("endTurnDialog"),
                                                Utility.FONTSPEC_TITLE);
        JTextArea text = Utility.localizedTextArea(StringTemplate
            .template("endTurnDialog.areYouSure")
            .addAmount("%number%", units.size()));

        DefaultListModel<UnitWrapper> model = new DefaultListModel<>();
        for (Unit unit : units) {
            model.addElement(new UnitWrapper(unit));
        }

        this.unitList = new JList<>(model);
        this.unitList.setCellRenderer(new UnitCellRenderer());
        this.unitList.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),
                                        "select");
        this.unitList.getActionMap().put("select", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    selectUnit();
                }
            });
        this.unitList.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"),
                                        "quit");
        this.unitList.getActionMap().put("quit", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    EndTurnDialog.this.setValue(options.get(1));
                }
            });
        this.unitList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) return;
                    selectUnit();
                }
            });
        JScrollPane listScroller = new JScrollPane(this.unitList);

        JPanel panel = new MigPanel(new MigLayout("wrap 1, fill",
                                                  "[align center]"));
        panel.add(header);
        panel.add(text, "newline 20");
        panel.add(listScroller, "newline 10");
        panel.setSize(panel.getPreferredSize());

        ImageIcon icon = new ImageIcon(
            getImageLibrary().getScaledNationImage(player.getNation()));
        initializeConfirmDialog(frame, false, panel, icon, "ok", "cancel");
    }

    /**
     * Select the current unit in the list.
     */
    private void selectUnit() {
        UnitWrapper wrapper = this.unitList.getSelectedValue();
        if (wrapper != null && wrapper.unit != null) {
            if (wrapper.unit.isInEurope()) {
                getGUI().showEuropePanel();
            } else {
                getGUI().changeView(wrapper.unit, false);
                if (wrapper.unit.getColony() != null) {
                    getGUI().showColonyPanel(wrapper.unit.getColony(),
                                             wrapper.unit);
                }
            }
        }
    }
}
