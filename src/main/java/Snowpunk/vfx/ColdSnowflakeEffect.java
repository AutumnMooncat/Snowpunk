package Snowpunk.vfx;

import Snowpunk.SnowpunkMod;
import Snowpunk.util.TexLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ColdSnowflakeEffect extends AbstractGameEffect {

    // Settings

    // Textures.
    private static final Texture texture_red = TexLoader.getTexture(SnowpunkMod.makeImagePath("vfx/Snowflake1.png"));
    private static final Texture texture_green = TexLoader.getTexture(SnowpunkMod.makeImagePath("vfx/Snowflake2.png"));
    private static final Texture texture_blue = TexLoader.getTexture(SnowpunkMod.makeImagePath("vfx/Snowflake3.png"));
    private static final Texture texture_back = TexLoader.getTexture(SnowpunkMod.makeImagePath("vfx/Snowflake4.png"));

    private Texture TextureUsed;
    private Texture FinalTexture;

    // Duration.
    private float startingDuration;
    private float duration;

    // Position
    private float x;
    private float y;

    // Where go
    private float speed;
    private float direction = 270.0F;

    // How move
    private float rotation;
    private float rotation_speed;
    private float flip_speed;
    private float flip_counter;
    private float scale;
    private float totalscale;

    public ColdSnowflakeEffect(AbstractCard card) {
        int randomtex = MathUtils.random(0, 2);

        if (randomtex == 0)
            this.TextureUsed = texture_red;
        else if (randomtex == 1)
            this.TextureUsed = texture_green;
        else if (randomtex == 2)
            this.TextureUsed = texture_blue;

        this.FinalTexture = this.TextureUsed;
        this.startingDuration = MathUtils.random(1.0F, 3.0F);
        this.duration = this.startingDuration;
        this.startingDuration = this.duration;
        this.renderBehind = false;
        this.rotation = MathUtils.random(0.0F, 360.0F);
        this.rotation_speed = MathUtils.random(-24.0F, 24.0F) * Settings.scale;
        this.flip_speed = MathUtils.random(-2.0F, 2.0F) * Settings.scale;
        this.flip_counter = MathUtils.random(0.0F, 6.4F);
        this.speed = MathUtils.random(100.0F, 150.0F) * Settings.scale;
        this.scale = MathUtils.random(-1.0F, 1.0F) * Settings.scale * .025f;
        this.totalscale = MathUtils.random(0.7F, 1.0F);
        if (MathUtils.random(0, 4) == 0 && this.totalscale >= 0.9F)
            this.totalscale = MathUtils.random(0.9F, 2F);


        // Location
        this.y = card.hb.y + card.hb.height - MathUtils.random(card.hb.height / 2) + MathUtils.random(-32 * Settings.scale, 32 * Settings.scale);
        this.x = MathUtils.random(card.hb.x, card.hb.x + card.hb.width);

        this.color = new Color(1, 1, 1, 1F);
    }

    @Override
    public void render(SpriteBatch sb) {
        if (duration / startingDuration < .8)
            color.a = duration / startingDuration;
        else
            color.a = (1 - (duration / startingDuration)) * 4;
        sb.setColor(this.color);

        final int w = FinalTexture.getWidth();
        final int h = FinalTexture.getHeight();
        final int w2 = FinalTexture.getWidth();
        final int h2 = FinalTexture.getHeight();
        sb.draw(FinalTexture, x - w2 / 2f, y - h2 / 2f,
                w / 2f, h / 2f,
                w2, h2,
                (float) (scale) * Settings.scale * totalscale, 1.0F * Settings.scale * totalscale,
                rotation,
                0, 0,
                w2, h2,
                false, false);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void update() {
        final float dt = Gdx.graphics.getDeltaTime();

        this.y += MathUtils.sinDeg(this.direction) * this.speed * dt;
        this.x += MathUtils.cosDeg(this.direction) * this.speed * dt;

        this.rotation += this.rotation_speed * dt;
        this.flip_counter += this.flip_speed * dt;

        this.scale = MathUtils.sin(this.flip_counter);

        if (this.scale > 0.0F) {
            this.FinalTexture = this.TextureUsed;
        } else
            this.FinalTexture = texture_back;

        this.duration -= dt;
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }
}
