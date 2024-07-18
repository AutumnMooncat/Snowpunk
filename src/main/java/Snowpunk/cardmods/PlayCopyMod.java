package Snowpunk.cardmods;

import Snowpunk.cards.Cryogenizer;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.damageMods.ChillDamageMod;
import Snowpunk.patches.MultiPreviewFieldPatches;
import Snowpunk.powers.ChillPower;
import Snowpunk.powers.GearNextPower;
import Snowpunk.powers.SnowpunkPower;
import Snowpunk.util.TexLoader;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.SnowpunkMod.modID;

public class PlayCopyMod extends AbstractCardModifier {
    public static final String ID = makeID(PlayCopyMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(Cryogenizer.ID).EXTENDED_DESCRIPTION;

    public ArrayList<AbstractCard> cards;
    private static final Texture tex = TexLoader.getTexture(modID + "Resources/images/ui/FuseIcon.png");

    public PlayCopyMod(AbstractCard card) {
        cards = new ArrayList<>();
        cards.add(card);
        priority = 1;
    }

    public PlayCopyMod(ArrayList<AbstractCard> cards) {
        this.cards = new ArrayList<>();
        this.cards.addAll(cards);
        priority = 1;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        for (AbstractCard c : cards)
            MultiPreviewFieldPatches.addPreview(card, c.makeStatEquivalentCopy());
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        for (AbstractCard c : cards) {
            AbstractMonster m = null;
            if (action.target instanceof AbstractMonster)
                m = (AbstractMonster) action.target;
            else
                m = AbstractDungeon.getRandomMonster();

            AbstractCard tmp = c.makeStatEquivalentCopy();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = Settings.HEIGHT / 2.0F;
            if (m != null)
                tmp.calculateCardDamage(m);
            tmp.purgeOnUse = true;
            tmp.energyOnUse = card.energyOnUse;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, tmp.energyOnUse, true, true), false);
        }
    }

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
            PlayCopyMod copyMod = (PlayCopyMod) CardModifierManager.getModifiers(card, ID).get(0);
            copyMod.cards.addAll(cards);
            for (AbstractCard c : cards)
                MultiPreviewFieldPatches.addPreview(card, c.makeStatEquivalentCopy());
            return false;
        }
        return true;
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
        return new PlayCopyMod(cards);
    }
}