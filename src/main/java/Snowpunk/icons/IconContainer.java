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

    public static class FireIcon extends AbstractCustomIcon {
        public static final String ID = SnowpunkMod.makeID("Fire");
        private static AbstractCustomIcon singleton;

        public FireIcon() {
            super(ID, TexLoader.getTexture("SnowpunkResources/images/icons/Fire.png"));
        }

        public static AbstractCustomIcon get() {
            if (singleton == null) {
                singleton = new FireIcon();
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

    public static class HollyIcon extends AbstractCustomIcon {
        public static final String ID = SnowpunkMod.makeID("Holly");
        private static AbstractCustomIcon singleton;

        public HollyIcon() {
            super(ID, TexLoader.getTexture("SnowpunkResources/images/icons/Holly.png"));
        }

        public static AbstractCustomIcon get() {
            if (singleton == null) {
                singleton = new HollyIcon();
            }
            return singleton;
        }
    }

    public static class ColdIcon extends AbstractCustomIcon {
        public static final String ID = SnowpunkMod.makeID("Cold");
        private static AbstractCustomIcon singleton;

        public ColdIcon() {
            super(ID, TexLoader.getTexture("SnowpunkResources/images/icons/Cold.png"));
        }

        public static AbstractCustomIcon get() {
            if (singleton == null) {
                singleton = new ColdIcon();
            }
            return singleton;
        }
    }

    public static class HotIcon extends AbstractCustomIcon {
        public static final String ID = SnowpunkMod.makeID("Hot");
        private static AbstractCustomIcon singleton;

        public HotIcon() {
            super(ID, TexLoader.getTexture("SnowpunkResources/images/icons/Hot.png"));
        }

        public static AbstractCustomIcon get() {
            if (singleton == null) {
                singleton = new HotIcon();
            }
            return singleton;
        }
    }

    public static class PlateIcon extends AbstractCustomIcon {
        public static final String ID = SnowpunkMod.makeID("Plate");
        private static AbstractCustomIcon singleton;

        public PlateIcon() {
            super(ID, TexLoader.getTexture("SnowpunkResources/images/icons/Plate.png"));
        }

        public static AbstractCustomIcon get() {
            if (singleton == null) {
                singleton = new PlateIcon();
            }
            return singleton;
        }
    }

    public static class OverIcon extends AbstractCustomIcon {
        public static final String ID = SnowpunkMod.makeID("Over");
        private static AbstractCustomIcon singleton;

        public OverIcon() {
            super(ID, TexLoader.getTexture("SnowpunkResources/images/ui/OverIcon.png"));
        }

        public static AbstractCustomIcon get() {
            if (singleton == null) {
                singleton = new OverIcon();
            }
            return singleton;
        }
    }
}
