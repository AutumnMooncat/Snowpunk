package Snowpunk.cardmods;

import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.patches.CustomTags;
import Snowpunk.powers.SnowballPower;
import Snowpunk.powers.interfaces.SnowAmountModifier;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class WhistolMod extends AbstractCardModifier {
    public static final String ID = makeID(WhistolMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public WhistolMod() {
        this.priority = -3;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(CustomTags.GUN);
        CardModifierManager.addModifier(card, new PrefixManager());
    }

/*    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return TEXT[0] + rawDescription;
    }*/

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        int evaporated = 0;
        if (EvaporatePanel.evaporatePile != null && EvaporatePanel.evaporatePile.size() > 0) {
            evaporated = EvaporatePanel.evaporatePile.size();
        }
        return damage + evaporated;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (!(card instanceof AssembledCard) && card.type != AbstractCard.CardType.ATTACK) {
            return false;
        }
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new WhistolMod();
    }
}