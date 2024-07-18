package Snowpunk.cards;

import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.HollyPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class DeckTheHalls extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(DeckTheHalls.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public DeckTheHalls() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 5;
        magicNumber = baseMagicNumber = 5;
        CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        Wiz.applyToSelf(new HollyPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(3));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> upgradeMagicNumber(3));
    }
}