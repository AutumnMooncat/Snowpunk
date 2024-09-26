package Snowpunk.cardmods;

import Snowpunk.util.TexLoader;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
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

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.SnowpunkMod.modID;

public class HiddenMagicNumberMod extends AbstractCardModifier {
    public static final String ID = makeID(HiddenMagicNumberMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public int amount = 0;

    public HiddenMagicNumberMod() {
        this(0);
    }

    public HiddenMagicNumberMod(int amount) {
        priority = 1;
        this.amount += amount;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (usesMagic(card)) {
            return magic + amount;
        }
        return magic;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            HiddenMagicNumberMod hiddenMagicNumberMod = (HiddenMagicNumberMod) CardModifierManager.getModifiers(card, ID).get(0);
            hiddenMagicNumberMod.amount += amount;
            if (hiddenMagicNumberMod.amount < 0)
                hiddenMagicNumberMod.amount = 0;
            return false;
        }
        return true;
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
        return new HiddenMagicNumberMod(amount);
    }
}