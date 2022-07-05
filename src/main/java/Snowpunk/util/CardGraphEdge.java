package Snowpunk.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.util.ArrayList;

public class CardGraphEdge {

    public static final float ICON_SRC_RADIUS = 29.0F * Settings.scale;
    private static final float ICON_DST_RADIUS = 20.0F * Settings.scale;
    private static final float SPACING = 17.0F * Settings.xScale;
    private final ArrayList<EdgeDot> dots = new ArrayList<>();
    public Color color = Color.LIGHT_GRAY.cpy();

    public CardGraphEdge(float srcX, float srcY, float dstX, float dstY) {
        Vector2 vec2 = (new Vector2((dstX), dstY)).sub(new Vector2((srcX), srcY));
        float length = vec2.len();
        float START = SPACING * MathUtils.random() / 2.0F;

        for(float i = START + ICON_DST_RADIUS; i < length - ICON_SRC_RADIUS; i += SPACING) {
            vec2.clamp(length - i, length - i);
            this.dots.add(new EdgeDot((srcX) + vec2.x, srcY + vec2.y, (new Vector2((srcX) - (dstX), srcY - dstY)).nor().angle() + 90.0F, false));
        }

    }

    public void render(SpriteBatch sb) {
        for (EdgeDot d : this.dots) {
            d.render(sb);
        }
    }

    public static class EdgeDot {
        private float x;
        private float y;
        private float rotation;
        private static final int RAW_W = 16;
        private static final float DIST_JITTER = 4.0F * Settings.scale;

        public EdgeDot(float x, float y, float rotation, boolean jitter) {
            if (jitter) {// 16
                this.x = x + MathUtils.random(-DIST_JITTER, DIST_JITTER);
                this.y = y + MathUtils.random(-DIST_JITTER, DIST_JITTER);
                this.rotation = rotation + MathUtils.random(-20.0F, 20.0F);
            } else {
                this.x = x;
                this.y = y;
                this.rotation = rotation;
            }

        }

        public void render(SpriteBatch sb) {
            sb.draw(ImageMaster.MAP_DOT_1, this.x - 8.0F, this.y - 8.0F, 8.0F, 8.0F, 16.0F, 16.0F, Settings.scale, Settings.scale, this.rotation, 0, 0, 16, 16, false, false);
        }
    }
}
