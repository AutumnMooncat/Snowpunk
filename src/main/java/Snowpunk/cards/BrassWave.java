package Snowpunk.cards;

import Snowpunk.cardmods.ClockworkMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SparePartsPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class BrassWave extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BrassWave.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, DMG = 8, BLOCK = 8, UP_DMG = 4, UP_BLOCK = 4;

    public BrassWave() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
        block = baseBlock = BLOCK;
        CardModifierManager.addModifier(this, new ClockworkMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
//        int effect = this.energyOnUse;
//
//        if (p.hasRelic("Chemical X")) {
//            effect += ChemicalX.BOOST;
//            p.getRelic("Chemical X").flash();
//        }
//
//        if (info > 0) {
//            effect += info;
//        }
//
//        if (effect > 0) {
//            for (int i = 0; i < effect; i++) {
//                blck();
//                dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
//            }
//            if (magicNumber > 0)
//                Wiz.applyToSelf(new SparePartsPower(p, effect));
//        }
//
//        if (!this.freeToPlayOnce) {
//            p.energy.use(EnergyPanel.totalCount);
//        }
        blck();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> upgradeBlock(UP_BLOCK));
        setExclusions(1, 2);
    }
}