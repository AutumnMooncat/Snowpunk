package Snowpunk.icons;

import Snowpunk.SnowpunkMod;
import Snowpunk.util.TexLoader;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;

public class IconContainer {
    public static class GearIcon extends AbstractCustomIcon {
        public static final String ID = SnowpunkMod.makeID("Gear");
        private static AbstractCustomIcon singleton;

        public GearIcon() {
            super(ID, TexLoader.getTexture("SnowpunkResources/images/icons/Gear.png"));
        }

        public static AbstractCustomIcon get() {
            if (singleton == null) {
                singleton = new GearIcon();
            }
            return singleton;
        }
    }

    public static class SnowIcon extends AbstractCustomIcon {
        public static final String ID = SnowpunkMod.makeID("Snow");
        private static AbstractCustomIcon singleton;

        public SnowIcon() {
            super(ID, TexLoader.getTexture("SnowpunkResources/images/icons/Snow.png"));
        }

        public static AbstractCustomIcon get() {
            if (singleton == null) {
                singleton = new SnowIcon();
            }
            return singleton;
        }
    }

    public static class TempIcon extends AbstractCustomIcon {
        public static final String ID = SnowpunkMod.makeID("Temp");
        private static AbstractCustomIcon singleton;

        public TempIcon() {
            super(ID, TexLoader.getTexture("SnowpunkResources/images/icons/Temp.png"));
        }

        public static AbstractCustomIcon get() {
            if (singleton == null) {
                singleton = new TempIcon();
            }
            return singleton;
        }
    }
}
