package Snowpunk.cards;

import Snowpunk.cardmods.EverburnMod;
import Snowpunk.cardmods.VentMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Overblown extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Overblown.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 18;
    private static final int UP_DMG = 6;

    public Overblown() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        CardModifierManager.addModifier(this, new VentMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new EverburnMod()));
    }
}