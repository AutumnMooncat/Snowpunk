package Snowpunk.cardmods;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.interfaces.GearMultCard;
import Snowpunk.patches.CustomTags;
import Snowpunk.powers.BrassPower;
import Snowpunk.powers.SnowpunkPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.TexLoader;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.PanicButton;
import com.megacrit.cardcrawl.cards.purple.Halt;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.SnowpunkMod.modID;

public class OverclockMod extends AbstractCardModifier {
    public static final String ID = makeID(OverclockMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public int amount = 0;
    private static ArrayList<TooltipInfo> GearTip;
    private static final Texture tex = TexLoader.getTexture(modID + "Resources/images/ui/OverIcon.png");
    private static float MINOR_BUFF = 0.25f;

    public OverclockMod() {
        this(0);
    }

    public OverclockMod(int amount) {
        priority = 1;
        this.amount += amount;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0) {
            return damage * (1 + MINOR_BUFF * amount);
        }
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0) {
            return block * (1 + MINOR_BUFF * amount);
        }
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (usesMagic(card)) {
            return magic * (1 + MINOR_BUFF * amount);
        }
        return magic;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            OverclockMod overclockMod = (OverclockMod) CardModifierManager.getModifiers(card, ID).get(0);
            overclockMod.amount += amount;
            if (overclockMod.amount < 0)
                overclockMod.amount = 0;
            return false;
        }
        return true;
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        ExtraIcons.icon(tex).text(String.valueOf(amount)).render(card);
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        if (amount > 0)
            ExtraIcons.icon(tex).text(String.valueOf(amount)).render(card);
    }

    public static boolean usesMagic(AbstractCard card) {
        final boolean[] usesMagicBool = {false};
        if (card.baseMagicNumber > 0 && StringUtils.containsIgnoreCase(card.rawDescription, "!M!") && !(card instanceof PanicButton) && !(card instanceof Halt)) {
            ClassPool pool = Loader.getClassPool();
            try {
                CtClass ctClass = pool.get(card.getClass().getName());
                ctClass.defrost();
                CtMethod[] methods = ctClass.getDeclaredMethods();
                for (CtMethod method : methods) {
                    try {
                        method.instrument(new ExprEditor() {
                            @Override
                            public void edit(FieldAccess f) {
                                if (f.getFieldName().equals("magicNumber") && f.isReader()) {
                                    usesMagicBool[0] = true;
                                }
                            }
                        });
                    } catch (Exception ignored) {
                    }
                }
            } catch (NotFoundException ignored) {
                return false;
            }
        }
        return usesMagicBool[0];
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new OverclockMod(amount);
    }
}