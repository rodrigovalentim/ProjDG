/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Rodrigo
 */
/*
 * Created on Aug 27, 2003
 *
 */
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

/**
 * @author tlucca
 *
 */
public class NoBorder extends MetalInternalFrameUI {

    private NoBorderInternalFrameTitlePane titlePane;
    private static final Border handyEmptyBorder = new EmptyBorder(0, 0, 0, 0);
    protected static String IS_PALETTE = "JInternalFrame.isPalette";
    private static String FRAME_TYPE = "JInternalFrame.frameType";
    private static String NORMAL_FRAME = "normal";
    private static String PALETTE_FRAME = "palette";
    private static String OPTION_DIALOG = "optionDialog";

    public NoBorder(JInternalFrame b) {
        super(b);
        frame = b;
        createNorthPane(b);
        stripContentBorder(b);
    }

    public static ComponentUI createUI(JComponent c) {
        return new NoBorder((JInternalFrame) c);
    }

    private void stripContentBorder(Object c) {
        if (c instanceof JComponent) {
            JComponent contentComp = (JComponent) c;
            Border contentBorder = contentComp.getBorder();
            if (contentBorder == null || contentBorder instanceof UIResource) {
                contentComp.setBorder(handyEmptyBorder);
            }
        }
    }

    protected JComponent createNorthPane(JInternalFrame w) {
        titlePane = new NoBorderInternalFrameTitlePane(w);
        return titlePane;
    }

    private void setFrameType(String frameType) {
        if (frameType.equals(OPTION_DIALOG)) {
            LookAndFeel.installBorder(frame, "InternalFrame.optionDialogBorder");
            titlePane.setPalette(false);
        } else if (frameType.equals(PALETTE_FRAME)) {
            LookAndFeel.installBorder(frame, "InternalFrame.paletteBorder");
            titlePane.setPalette(true);
        } else {
            LookAndFeel.installBorder(frame, "InternalFrame.border");
            titlePane.setPalette(false);
        }
    }

    public void setPalette(boolean isPalette) {
        if (isPalette) {
            LookAndFeel.installBorder(frame, "InternalFrame.paletteBorder");
        } else {
            LookAndFeel.installBorder(frame, "InternalFrame.border");
        }
        titlePane.setPalette(isPalette);

    }

    class NoBorderInternalFrameTitlePane extends MetalInternalFrameTitlePane {

        private int paletteTitleHeight = 10;

        public NoBorderInternalFrameTitlePane(JInternalFrame j) {
            super(j);
        }

        protected void installDefaults() {
            super.installDefaults();
            setFont(UIManager.getFont("InternalFrame.font"));
            UIManager.put("InternalFrame.paletteTitleHeight", new Integer(0));
            paletteTitleHeight = UIManager.getInt("InternalFrame.paletteTitleHeight");
            paletteCloseIcon = UIManager.getIcon("InternalFrame.paletteCloseIcon");
            setOpaque(true);
            setBackground(new Color(0, 0, 0, 0));
        }
    }
}
