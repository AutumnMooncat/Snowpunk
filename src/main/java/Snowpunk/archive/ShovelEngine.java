package Snowpunk.archive;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FireballPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class ShovelEngine extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ShovelEngine.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, DAMAGE = 16;

    public ShovelEngine() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardModifierManager.addModifier(this, new GearMod(0));
        damage = baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        if (getGears() > 0)
            Wiz.applyToSelf(new FireballPower(p, getGears()));
        Wiz.atb(new ApplyCardModifierAction(this, new GearMod(1)));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(5));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(1)));
    }
}