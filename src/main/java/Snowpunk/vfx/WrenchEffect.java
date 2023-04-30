package Snowpunk.vfx;

import Snowpunk.util.TexLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class WrenchEffect extends AbstractGameEffect {
    private static Texture TEXTURE = TexLoader.getTexture("SnowpunkResources/images/vfx/Wrench.png");
    public static final float DURATION = 0.75f; //Duration of the action, can lengthen or shorten as you wish. All interpolations are relative to this, so changing this is safe
    private static final float HALF_DUR = DURATION / 2F;
    private final float sx;
    private final float sy;
    private float tx;
    private float ty;
    private final float sa, ta, ea;
    private final TextureAtlas.AtlasRegion img;
    private float x, y;
    private float t;
    AbstractCard target;
    private boolean sounded;

    public WrenchEffect(AbstractCard target) {
        super();
        this.renderBehind = false; //Render over the card
        img = new TextureAtlas.AtlasRegion(TEXTURE, 0, 0, TEXTURE.getWidth(), TEXTURE.getHeight()); //Load the image
        this.target = target;
        sa = (float) Math.random() * 360; //Angle scythe spawns at
        sx = (float) (target.current_x - Math.cos(Math.toRadians(sa)) * Settings.WIDTH / 4);// x coord scythe spawns at and swings back to
        sy = (float) (target.current_y - Math.sin(Math.toRadians(sa)) * Settings.WIDTH / 4);// y coord scythe spawns at and swings back to
        tx = target.current_x;// x coord scythe pauses at
        ty = target.current_y;// y coord scythe pauses at
        ta = sa + 80; //Angle scythe pauses at
        ea = sa - 160; //Angle scythe swings to
        duration = startingDuration = DURATION;
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F); //Start invisible and not null
        sounded = false;
    }

    @Override
    public void update() {
        //Play sfx at the start
        if (duration == startingDuration) {
            CardCrawlGame.sound.playA("snowpunk:unclank", MathUtils.random(.8f, 1.1f));
        }
        //Update Target
        tx = target.current_x;
        ty = target.current_y;
        //Increment interpolation timer
        t += Gdx.graphics.getDeltaTime();
        //Second half of the animation swings back
        if (t >= HALF_DUR) {
            this.color.a = Interpolation.pow5In.apply(1.0F, 0.0F, (t - HALF_DUR) / HALF_DUR);
            rotation = Interpolation.pow5In.apply(ta, ea, (t - HALF_DUR) / HALF_DUR);

            if (t >= DURATION * .75 && !sounded) {
                sounded = true;
                CardCrawlGame.sound.playA("snowpunk:wrench", MathUtils.random(.8f, 1.1f));
            }
        } else { //First half of the animation grows larger and pauses
            this.color.a = Interpolation.pow5Out.apply(0.0F, 1.0F, t / HALF_DUR);
            x = Interpolation.pow5Out.apply(sx, tx, t / HALF_DUR);
            y = Interpolation.pow5Out.apply(sy, ty, t / HALF_DUR);
            rotation = Interpolation.pow5Out.apply(sa, ta, t / HALF_DUR);
        }
        //Reduce duration and end action if duration is 0
        duration -= Gdx.graphics.getDeltaTime();
        if (duration <= 0.0F) {
            isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        //Set the color so your alpha is applied
        sb.setColor(color);
        //Draw the image
        sb.draw(img, x - img.packedWidth / 2.0F, y - img.packedHeight / 2.0F, img.packedWidth / 2.0F, img.packedWidth / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    @Override
    public void dispose() {
    }
}