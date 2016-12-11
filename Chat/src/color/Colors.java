package color;

import java.awt.Color;
import java.util.*;

/**
 *
 * @author marco
 */
public enum Colors {

    red_400("#EF5350"),
    red_500("#F44336"),
    red_600("#E53935"),
    red_700("#D32F2F"),
    red_800("#C62828"),
    red_900("#B71C1C"),
    red_accent_400("#FF1744"),
    red_accent_700("#D50000"),
    pink_400("#EC407A"),
    pink_500("#E91E63"),
    pink_600("#D81B60"),
    pink_700("#C2185B"),
    pink_800("#AD1457"),
    pink_900("#880E4F"),
    pink_accent_400("#F50057"),
    pink_accent_700("#C51162"),
    purple_400("#AB47BC"),
    purple_500("#9C27B0"),
    purple_600("#8E24AA"),
    purple_700("#7B1FA2"),
    purple_800("#6A1B9A"),
    purple_900("#4A148C"),
    purple_accent_400("#D500F9"),
    purple_accent_700("#AA00FF"),
    deep_purple_400("#7E57C2"),
    deep_purple_500("#673AB7"),
    deep_purple_600("#5E35B1"),
    deep_purple_700("#512DA8"),
    deep_purple_800("#4527A0"),
    deep_purple_900("#311B92"),
    deep_purple_accent_400("#651FFF"),
    deep_purple_accent_700("#6200EA"),
    indigo_400("#5C6BC0"),
    indigo_500("#3F51B5"),
    indigo_600("#3949AB"),
    indigo_700("#303F9F"),
    indigo_800("#283593"),
    indigo_900("#1A237E"),
    indigo_accent_400("#3D5AFE"),
    indigo_accent_700("#304FFE"),
    blue_400("#42A5F5"),
    blue_500("#2196F3"),
    blue_600("#1E88E5"),
    blue_700("#1976D2"),
    blue_800("#1565C0"),
    blue_900("#0D47A1"),
    blue_accent_400("#2979FF"),
    blue_accent_700("#2962FF"),
    light_blue_400("#29B6FC"),
    light_blue_500("#03A9F4"),
    light_blue_600("#039BE5"),
    light_blue_700("#0288D1"),
    light_blue_800("#0277BD"),
    light_blue_900("#01579B"),
    light_blue_accent_400("#00B0FF"),
    light_blue_accent_700("#0091EA"),
    cyan_400("#26C6DA"),
    cyan_500("#00BCD4"),
    cyan_600("#00ACC1"),
    cyan_700("#0097A7"),
    cyan_800("#00838F"),
    cyan_900("#006064"),
    cyan_accent_400("#00E5FF"),
    cyan_accent_700("#00B8D4"),
    teal_400("#26A69A"),
    teal_500("#009688"),
    teal_600("#00897B"),
    teal_700("#00796B"),
    teal_800("#00695C"),
    teal_900("#004D40"),
    teal_accent_400("#1DE9B6"),
    teal_accent_700("#00BFA5"),
    green_400("#66BB6A"),
    green_500("#4CAF50"),
    green_600("#43A047"),
    green_700("#388E3C"),
    green_800("#2E7D32"),
    green_900("#1B5E20"),
    green_accent_400("#00E676"),
    green_accent_700("#00C853"),
    light_green_400("#9CCC65"),
    light_green_500("#8BC34A"),
    light_green_600("#7CB342"),
    light_green_700("#689F38"),
    light_green_800("#558B2F"),
    light_green_900("#33691E"),
    light_green_accent_400("#76FF03"),
    light_green_accent_700("#64DD17"),
    lime_400("#D4E157"),
    lime_500("#CDDC39"),
    lime_600("#C0CA33"),
    lime_700("#A4B42B"),
    lime_800("#9E9D24"),
    lime_900("#827717"),
    lime_accent_700("#AEEA00"),
    yellow_400("#FFEE58"),
    yellow_500("#FFEB3B"),
    yellow_600("#FDD835"),
    yellow_700("#FBC02D"),
    yellow_800("#F9A825"),
    yellow_900("#F57F17"),
    yellow_accent_400("#FFEA00"),
    yellow_accent_700("#FFD600"),
    amber_400("#FFCA28"),
    amber_500("#FFC107"),
    amber_600("#FFB300"),
    amber_700("#FFA000"),
    amber_800("#FF8F00"),
    amber_900("#FF6F00"),
    amber_accent_400("#FFC400"),
    amber_accent_700("#FFAB00"),
    orange_400("#FFA726"),
    orange_500("#FF9800"),
    orange_600("#FB8C00"),
    orange_700("#F57C00"),
    orange_800("#EF6C00"),
    orange_900("#E65100"),
    orange_accent_400("#FF9100"),
    orange_accent_700("#FF6D00"),
    deep_orange_500("#FF5722"),
    deep_orange_600("#F4511E"),
    deep_orange_700("#E64A19"),
    deep_orange_800("#D84315"),
    deep_orange_900("#BF360C"),
    deep_orange_accent_400("#FF3D00"),
    deep_orange_accent_700("#DD2600"),
    brown_500("#795548"),
    brown_600("#6D4C41"),
    brown_700("#5D4037"),
    brown_800("#4E342E"),
    brown_900("#3E2723"),
    grey_700("#616161"),
    grey_800("#424242"),
    grey_900("#212121"),
    black_1000("#000000"),
    white_1000("#ffffff"),
    blue_grey_500("#607D8B"),
    blue_grey_600("#546E7A"),
    blue_grey_700("#455A64"),
    blue_grey_800("#37474F"),
    blue_grey_900("#263238");

    private final Color mColor;
    private final String mStringColor;

    Colors(final String mStringColor) {
        this.mStringColor = mStringColor;
        this.mColor = Color.decode(mStringColor);
    }
    
    /**
     * @return Colore selezionato.
     */
    public Color asColor() {
        return mColor;
    }
    
    /**
     * @return Stringa colore selezionato.
     */
    public String asString() {
        return mStringColor;
    }

    private static final Set<Integer> generated = new LinkedHashSet<>();
    
    /**
     * @return Colore random fra quelli presenti.
     */
    public static String randomColors() {
         return values()[(int) (Math.random() * values().length)].asString();
    }

}
