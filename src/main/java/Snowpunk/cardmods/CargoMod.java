package Snowpunk.cardmods;

import CardAugments.cardmods.common.ReshuffleMod;
import Snowpunk.cards.Cryogenizer;
import Snowpunk.patches.MultiPreviewFieldPatches;
import Snowpunk.util.TexLoader;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.SnowpunkMod.modID;

public class CargoMod extends AbstractCardModifier {
    public static final String ID = makeID(CargoMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(CargoMod.ID).EXTENDED_DESCRIPTION;

    public ArrayList<AbstractCard> cards;
    private static final Texture tex = TexLoader.getTexture(modID + "Resources/images/ui/FuseIcon.png");

    public CargoMod(AbstractCard card) {
        cards = new ArrayList<>();
        cards.add(card);
        priority = 1;
    }

    public CargoMod(ArrayList<AbstractCard> cards) {
        this.cards = new ArrayList<>();
        this.cards.addAll(cards);
        priority = 1;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        for (AbstractCard c : cards)
            MultiPreviewFieldPatches.addPreview(card, c.makeStatEquivalentCopy());
    }

//    @Override
//    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
//        for (AbstractCard c : cards) {
//            AbstractMonster m = null;
//            if (action.target instanceof AbstractMonster)
//                m = (AbstractMonster) action.target;
//            else
//                m = AbstractDungeon.getRandomMonster();
//
//            AbstractCard tmp = c.makeStatEquivalentCopy();
//            AbstractDungeon.player.limbo.addToBottom(tmp);
//            tmp.current_x = card.current_x;
//            tmp.current_y = card.current_y;
//            tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
//            tmp.target_y = Settings.HEIGHT / 2.0F;
//            if (m != null)
//                tmp.calculateCardDamage(m);
//            tmp.purgeOnUse = true;
//            tmp.energyOnUse = card.energyOnUse;
//            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, tmp.energyOnUse, true, true), false);
//        }
//    }

    /*
        @Override
        public String modifyDescription(String rawDescription, AbstractCard card) {
            if (rawDescription.equals("") || rawDescription.endsWith(" NL "))
                return rawDescription + TEXT[0];
            return rawDescription + " NL " + TEXT[0];
        }
    */

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            CargoMod copyMod = (CargoMod) CardModifierManager.getModifiers(card, ID).get(0);
            copyMod.cards.addAll(cards);
            for (AbstractCard c : cards)
                MultiPreviewFieldPatches.addPreview(card, c.makeStatEquivalentCopy());
            return false;
        }
        return true;
    }

    public void updatePreviews(AbstractCard card) {
        MultiPreviewFieldPatches.ExtraPreviews.previews.get(card).clear();
        for (AbstractCard c : cards)
            MultiPreviewFieldPatches.addPreview(card, c.makeStatEquivalentCopy());
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        ExtraIcons.icon(tex).render(card);
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        ExtraIcons.icon(tex).render(card);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CargoMod(cards);
    }
}