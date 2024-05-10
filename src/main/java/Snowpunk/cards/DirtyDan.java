package Snowpunk.cards;

import Snowpunk.actions.DirtyDanAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class DirtyDan extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(DirtyDan.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, DMG = 18;

    public DirtyDan() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
        CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.atb(new DirtyDanAction());
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(5));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 2));
    }
}