package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.EnhanceCardInHardAction;
import Snowpunk.actions.UpgradeInHandAction;
import Snowpunk.actions.UpgradeRandomInHardWithVisualAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BrassPower;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Forge extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Forge.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;

    public Forge() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 3;
        magicNumber = baseMagicNumber = 1;
        CardTemperatureFields.addInherentHeat(this, 2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
//        if(random){
//            addToTop(new SFXAction("snowpunk:clank"));
//            addToBot(new UpgradeRandomInHardWithVisualAction(magicNumber));
//        }
//        else
        if (magicNumber == 1)
            addToBot(new ArmamentsAction(false));
        else
            addToBot(new UpgradeInHandAction(magicNumber));
//        Wiz.applyToSelf(new BrassPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
            upgradeDamage(1);
        });
        addUpgradeData(() -> {
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
            upgradeDamage(1);
        });
        addUpgradeData(() -> {
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
            upgradeDamage(1);
        });
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }
}