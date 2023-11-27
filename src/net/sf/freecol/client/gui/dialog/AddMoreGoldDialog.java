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

import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import net.miginfocom.swing.MigLayout;

import net.sf.freecol.client.FreeColClient;
import net.sf.freecol.client.gui.panel.*;
import net.sf.freecol.common.model.StringTemplate;


/**
 * The panel that allows to save point.
 */
public final class AddMoreGoldDialog
    extends FreeColInputDialog<Integer> {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(AddMoreGoldDialog.class.getName());

    private static final int COLUMNS = 5;

    /** The field to contain the input. */
    private final JTextField input;

    /** The maxumum amount allowed. */
    private final int maximum =1000;


    /**
     * The constructor to use.
     *
     * @param freeColClient The enclosing {@code FreeColClient}.
     * @param frame The owner frame.
     * @param question A {@code StringTemplate} describing the input required.
     * @param maximum The inclusive maximum integer input value.
     */
    public AddMoreGoldDialog(FreeColClient freeColClient, JFrame frame, StringTemplate question, int maximum) {
        super(freeColClient, frame);

        this.maximum = maximum;
        this.input = new JTextField(Integer.toString(maximum), COLUMNS);

        /**
         * Adiciona um DocumentFilter para filtrar os caracteres.
         */
        AbstractDocument doc = (AbstractDocument) this.input.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {

            /**
             * Verifica se o texto contém apenas números.
             */
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d{0,3}")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            /**
             * Verifica se a string contém apenas números.
             */
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d{0,3}")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
        });

        JPanel panel = new MigPanel(new MigLayout("wrap 2"));

        panel.add(Utility.localizedTextArea(question));
        panel.add(this.input);

        panel.setSize(panel.getPreferredSize());

        initializeInputDialog(frame, true, panel, null, "Sim", "Não");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getInputValue() {
        int result;
        try {
            result = Integer.parseInt(input.getText());
        } catch (NumberFormatException nfe) {
            return null;
        }
        return (result <= 0 || result > maximum) ? null : result;
    }


    // Override Component

    /**
     * {@inheritDoc}
     */
    @Override
    public void requestFocus() {
        this.input.requestFocus();
    }
}
