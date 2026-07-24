package app.ui.util;

import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public final class TooltipUtils {

    private TooltipUtils() {
        // Impede instanciação
    }

    public static Tooltip create(String text) {
        Tooltip tooltip = new Tooltip(text);
        applyDefaultConfig(tooltip);
        return tooltip;
    }

    public static void applyDefaultConfig(Tooltip tooltip) {
        tooltip.setShowDelay(Duration.millis(200));
        tooltip.setShowDuration(Duration.seconds(10));
        tooltip.setHideDelay(Duration.millis(100));
    }

}
