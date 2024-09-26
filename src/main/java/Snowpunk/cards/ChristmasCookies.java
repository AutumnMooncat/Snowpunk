package Snowpunk.cards;

import Snowpunk.actions.AddHotToRandomCardsAction;
import Snowpunk.actions.AddHotToRandomEvaporatedCardAction;
import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ChristmasCookiePower;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ChristmasCookies extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ChristmasCookies.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public ChristmasCookies() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = 5;
        magicNumber = baseMagicNumber = 1;
        CardTemperatureFields.addInherentHeat(this, 1);
        CardModifierManager.addModifier(this, new HatMod());
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractCard card = this;
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                CardTemperatureFields.addHeat(card, CardTemperatureFields.HOT);
                isDone = true;
            }
        });
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        //Wiz.applyToSelf(new ChristmasCookiePower(p, magicNumber));
        //addToBot(new AddHotToRandomEvaporatedCardAction(magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(3));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
        addUpgradeData(() -> upgradeDamage(3));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
        setDependencies(true, 2, 0);
        setDependencies(true, 3, 1);
        setExclusions(2, 3);
    }
}