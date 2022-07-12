package Snowpunk.cards;

import Snowpunk.cardmods.parts.ReshuffleMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
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

    private static final int COST = -1;
    private static final int DMG = 5, BLOCK = 5, UP_DMG = 3, UP_BLOCK = 3;

    public BrassWave() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
        block = baseBlock = BLOCK;
        info = baseInfo = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.energyOnUse;

        if (p.hasRelic("Chemical X")) {
            effect += ChemicalX.BOOST;
            p.getRelic("Chemical X").flash();
        }

        if (info > 0) {
            effect += info;
        }

        if (effect > 0) {
            for (int i = 0; i < effect; i++) {
                dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
                blck();
            }
        }

        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeDamage(UP_DMG));
        addUpgradeData(this, () -> upgradeBlock(UP_BLOCK));
        addUpgradeData(this, () -> upgradeInfo(1), 0, 1);
    }
}