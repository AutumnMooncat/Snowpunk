package Snowpunk.cards;

import Snowpunk.actions.SmithingAnvilAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class HammerTime extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(HammerTime.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, DMG = 6, UP_DMG = 2, TIMES = 3;

    public HammerTime() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        exhaust = true;
        CardModifierManager.addModifier(this, new GearMod(3));
        CardTemperatureFields.addInherentHeat(this, 2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int numTimes = getGears();
        if (numTimes > 0) {
            for (int i = 0; i < numTimes; i++)
                addToBot(new SmithingAnvilAction(this));
        }
    }


    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT));
    }
}